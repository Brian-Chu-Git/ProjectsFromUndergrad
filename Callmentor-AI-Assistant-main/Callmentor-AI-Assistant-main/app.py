import os
import sys

try:
    # Third-party library imports
    from flask import Flask, request, jsonify, render_template
    from flask_cors import CORS
    from dotenv import load_dotenv
    from sqlalchemy import create_engine

    # LangChain and related imports
    from langchain_openai import ChatOpenAI, OpenAIEmbeddings
    from langchain_core.prompts import ChatPromptTemplate, MessagesPlaceholder
    from langchain_core.output_parsers import StrOutputParser
    from langchain_core.runnables import RunnablePassthrough
    from langchain_core.vectorstores import InMemoryVectorStore
    from langchain_community.utilities import SQLDatabase
    from langchain_community.document_loaders import PyPDFDirectoryLoader
    from langchain.chains import create_retrieval_chain
    from langchain.chains import create_history_aware_retriever
    from langchain.chains.combine_documents import create_stuff_documents_chain
except ImportError:
    print("Missing dependencies. Please install them using:")
    print("pip install -r requirements.txt")
    sys.exit(1)

load_dotenv()

#Database Connection
DATABASE_USERNAME = os.getenv('DATABASE_USERNAME')
PASSWORD = os.getenv('PASSWORD')
DATABASE_HOST = os.getenv('DATABASE_HOST')
DATABASE_NAME = os.getenv('DATABASE_NAME')

#API key
OPENAI_API_KEY = os.getenv('OPENAI_API_KEY')

app = Flask(__name__)
CORS(app)

def categorize_question(question):
    """Classify a user question into a predefined category using LLM."""
    llm = ChatOpenAI(model="gpt-4o-mini")
    
    template_classification = """
    Please classify the question given into one of the following - Find Mentor, Ask About Callmentor(the website for this chatbot), 
    General Job Hunting Help, or Off-Topic, All I want is the classification:
    Question: {question}
    Answer:
    """

    prompt = template_classification.format(question = question)

    classified = llm.invoke(prompt)

    return classified.content

def find_Mentor(question):
    """Generate and run a SQL query to find a suitable mentor based on a user question."""
    # Get database table information
    def get_schema(_):
        return db.get_table_info()

    # Function to run query
    def run_query(query):
        return db.run(query)

    llm = ChatOpenAI(model="gpt-4o-mini")

    #Setup the prompt and template for it
    template_sql = """
    Write me a SQL query that would answer the user's question:{schema}, just give me the query in plain text without sql formatting

    Question: {question}
    SQL Query:
    """

    prompt = ChatPromptTemplate.from_template(template_sql)

    prompt.format(schema="my schema", question="How many entries are there")

    #MySQL Connection
    #mysql_uri = f'mysql+mysqlconnector://{DATABASE_USERNAME}:{PASSWORD}@{DATABASE_HOST}/{DATABASE_NAME}'

    #Sql connection
    mysql_uri = f'mssql+pyodbc://{DATABASE_USERNAME}:{PASSWORD}@{DATABASE_HOST}/{DATABASE_NAME}?driver=ODBC+Driver+18+for+SQL+Server'
    engine = create_engine(mysql_uri)

    try:
        db = SQLDatabase(engine, view_support=True, schema="dbo")
    except Exception as e:
        print(str(e))

    #Chain for specifically getting the SQL query
    sql_chain = (
        RunnablePassthrough.assign(schema=get_schema)
        | prompt
        | llm.bind(stop="\nSQL Result:")
        | StrOutputParser()
    )

    #Template for the mentor chain
    template_mentor = """
    Based on the table schema below, question, sql query, and sql response, answer the question with who it is, at the end, only if 
    the mentor is found, add the mentor's first and last name. Give a short description of the mentor then tell the user to type it into the search bar and tell the user that 
    they may not find the mentor because of lack of availability. Do not give "Callmentor Test".
    Again, only answer the question if a mentor has been found:
    {schema}

    Question: {question}
    SQL Query: {query}
    SQL Response: {response}
    """

    prompt = ChatPromptTemplate.from_template(template_mentor)

    full_chain = (
        RunnablePassthrough.assign(query=sql_chain).assign(
            schema=get_schema,
            response=lambda variables: run_query(variables["query"]),
        )
        | prompt
        | llm
    )

    response = full_chain.invoke({"question": {question}})

    return response.content

# Directory containing FAQ PDFs to process
PDF_DIRECTORY = './faq_pdfs'  # Directory containing your FAQ PDFs

def load_and_process_pdfs():
    """Load all PDFs in the directory and convert them into an in-memory retriever."""

    # Load all documents from directory
    loader = PyPDFDirectoryLoader("faq_pdfs/")
    documents = loader.load()

    # Create vector store
    vectorstore = InMemoryVectorStore.from_documents(
        documents=documents, embedding=OpenAIEmbeddings(model="text-embedding-3-small")
    )
    retriever = vectorstore.as_retriever()
    
    return retriever

# Initialize the retriever
retriever = load_and_process_pdfs()

def question_help(question, chat_history):
    """Generate a helpful response to a job-hunting or Callmentor-related question using retrieval-augmented generation."""
    # Initialize the language model
    llm = ChatOpenAI(
        model='gpt-4o-mini', 
        temperature=0.5, 
    )

    # Prompt to contextualize questions using chat history
    contextualize_q_system_prompt = (
        "Given a chat history and the latest user question "
        "which might reference context in the chat history, "
        "formulate a standalone question which can be understood "
        "without the chat history. Do NOT answer the question, "
        "just reformulate it if needed and otherwise return it as is."
    )

    # Chat prompt template for contextualizing questions
    contextualize_q_prompt = ChatPromptTemplate.from_messages(
        [
            ("system", contextualize_q_system_prompt),
            MessagesPlaceholder("chat_history"),
            ("human", "{input}"),
        ]
    )

    # Create a history-aware retriever
    history_aware_retriever = create_history_aware_retriever(
        llm, retriever, contextualize_q_prompt
    )

    # Define a detailed system prompt for the assistant
    system_prompt = ( """
    You are a knowledgeable assistant specializing in Callmentor and career development. Your role is to answer user questions with accurate and concise information about Callmentor's services, career advice, and industry trends.

    Focus Areas:
    - Callmentor Services: Explain Callmentor's mission, tools, and benefits.
    - Career Advice: Offer concise tips on planning, skill-building, and job search strategies.
    - Industry Trends: Provide short insights into trends and opportunities in various industries.
    - Resumes and Interviews: Share brief, practical advice for crafting resumes and succeeding in interviews.
    - Professional Growth: Give quick, actionable tips on improving soft skills and advancing in a career.

    Guidelines:
    - Keep answers short and concise, only including the most relevant information.
    - Format the response in plain-text and make sure line breaks clearly separate paragraphs.
    - Match the user's language (e.g., respond in English if the user messages in English).
    - If a question is outside your knowledge base, clarify that you can only assist with Callmentor and career-related topics.
    - Do not generate lists with more than five bullet points.
    - You can provide links, if they are related to Callmentor.
    - If the request is asking for a mentor recommendation, ask for a more detailed description of what type of mentor the user is looking for. 

    Your primary goal is to provide clear, to-the-point assistance without unnecessary detail. Always prioritize brevity.
    
    {context}
    """
    )

    # Prompt template for question answering
    qa_prompt = ChatPromptTemplate.from_messages(
        [
            ("system", system_prompt),
            MessagesPlaceholder("chat_history"),
            ("human", "{input}"),
        ]
    )

    # Chain for handling question-answering with context
    question_answer_chain = create_stuff_documents_chain(llm, qa_prompt)

    # Combine retrieval and question-answering into a single chain
    rag_chain = create_retrieval_chain(history_aware_retriever, question_answer_chain)

    response = rag_chain.invoke({
        "input": question,
        "chat_history": chat_history
    })

    return response["answer"]

def process_string(input_string, chat_history):
    """Categorize the input string and respond using the appropriate handler."""
    question = input_string
    categorized = categorize_question(input_string)
    response = None

    if categorized == "Find Mentor":
        response = find_Mentor(question)
    elif categorized == "Ask About Callmentor" or categorized == "General Job Hunting Help" or categorized == "Off-Topic":
        response = question_help(question, chat_history)

    return response

# API route to test website
@app.route('/')
def index():
    """Render the main landing page."""
    return render_template('index.html')

# Route to handle chat functionality
@app.route('/chat', methods=['POST'])
def chat_endpoint():
    """Endpoint for handling chat requests with user message and history."""
    try:
        # Get user message and chat history from the request body
        data = request.json
        user_message = data.get('message', '')
        chat_history = data.get('chat_history', [])  # Get chat history from the request

        # Process the chat request using RAG chain
        result = process_string(user_message, chat_history)

        # # Return response as a JSON
        return jsonify({
            "response": result.replace('*', '').replace('#', '').replace('\n', '<br>'),
            "chat_history": [{"role": "user", "content": user_message}, {"role": "ai", "content": result}]
        })

    except Exception as e:
        print(f'error: {e}')
        error_message = (
            "Sorry, something went wrong on our end (Error 500). "
            "Please try again shortly or rephrase your request. "
            "If the issue persists, please contact info@call-mentor.com."
        )
        return jsonify({
            "response": error_message,
            "chat_history": [{"role": "ai", "content": error_message}]
        }), 500

if __name__ == '__main__':
    app.run(debug=True, host='0.0.0.0', port=5000)