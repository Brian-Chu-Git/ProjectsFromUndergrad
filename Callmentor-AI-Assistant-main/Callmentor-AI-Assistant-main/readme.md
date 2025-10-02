
# Callmentor AI Assistant

  

## Installation and Setup


### 1. Requirements

- Python 3

### 2. Dependencies

Install the necessary libraries by running:

```bash
pip  install  -r  requirements.txt
```

### 3. Configure Environment Variable

Make sure you have the following environment variables:

```
OPENAI_API_KEY=your_openai_api_key_here
DATABASE_HOST=database_host_here
DATABASE_NAME=database_name_here
DATABASE_USERNAME=database_username_here
PASSWORD=database_password_here
```

### 4. Add PDF Files
Place the FAQ PDFs that the chatbot will use in a `./faq_pdfs` directory. These PDFs should contain the knowledge base for Callmentor and other relevant topics.

## Running the Application

### 1. Start the Server

Run the Flask application by executing:

```bash
python app.py 
```

This will start the server on port `5000`.

### 2. Access the Web Interface

In order to add the widget to the existing webpage, first place the `widget.js` file into a folder called static and your main HTML file (often will be index.html) in a folder called templates. 

If the main HTML file already exists, open said HTML file, and add the following lines of code inside the `<head>` tag:  
  

```html
<!-- Load Tailwind CSS for styling -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.16/tailwind.min.css">

<!-- Load the Chat Widget -->
<script defer src="{{ url_for('static', filename='widget.js') }}"></script>
```

In widget.js, make sure the frontend fetches from the correct backend if deployed seperately from the frontend, the frontend currently uses `http://localhost:5000/chat`
 
**Tailwind does not need to be added if already existing.**
These can be placed after any meta tags, but before closing `<\head>`
  
These will load Tailwind CSS, and the Widget itself.

At the end, the directory should contain these files:

    .
    ├── static                  
    │   └── widget.js           
    ├── templates
    │   └──  index.html  
    ├── .env                # Only needed if your environment variables are stored here in the directory
    ├── faq_pdfs
    │   └── PDF Documents  
    ├── app.py
    └── requirements.txt
