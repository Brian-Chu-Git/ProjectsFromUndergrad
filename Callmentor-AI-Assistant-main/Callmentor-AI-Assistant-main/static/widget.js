(function () {
    // Ensure Session Storage is cleared between page refreshes
    // Inject TailwindCSS for styling
    document.head.insertAdjacentHTML(
        'beforeend',
        '<link href="https://cdnjs.cloudflare.com/ajax/libs/tailwindcss/2.2.16/tailwind.min.css" rel="stylesheet">'
    );
    // Inject Nunito Font from Google
    document.head.insertAdjacentHTML(
        'beforeend',
        '<link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300&display=swap" rel="stylesheet">'
    );

    // Inject CSS for typing animation and ensure display control
    const style = document.createElement('style');
    style.innerHTML = `
#chat-popup, #chat-popup * {
        font-family: 'Nunito', sans-serif;
        font-weight: 300; /* Ensures it's using Nunito Light */
    }
    .hidden {
      display: none;
    }
    #chat-widget-container {
      position: fixed;
      bottom: 20px;
      right: 20px;
      flex-direction: column;
    }
    #chat-popup {
      height: 70vh;
      max-height: 70vh;
      transition: all 0.3s;
      overflow: hidden;
      display: none; /* Ensure it is initially hidden */
    }
    @media (max-width: 768px) {
      #chat-popup {
        position: fixed;
        top: 0;
        right: 0;
        bottom: 0;
        left: 0;
        width: 100%;
        height: 100%;
        max-height: 100%;
        border-radius: 0;
      }
    }
    /* Typing Indicator Animation */
    .dot-animation {
        display: inline-block;
        font-weight: bold;
        animation: blink 1.4s infinite;
    }
    @keyframes blink {
        0% { opacity: 0.2; }
        20% { opacity: 1; }
        100% { opacity: 0.2; }
    }
    `;
    document.head.appendChild(style);

    // Create chat widget container
    const chatWidgetContainer = document.createElement('div');
    chatWidgetContainer.id = 'chat-widget-container';
    document.body.appendChild(chatWidgetContainer);

    chatWidgetContainer.innerHTML = `
    <div id="chat-bubble" class="w-16 h-16 bg-blue-600 hover:bg-blue-900 rounded-full flex items-center justify-center cursor-pointer text-3xl">
      <svg xmlns="http://www.w3.org/2000/svg" class="w-10 h-10 text-white" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
        <path stroke-linecap="round" stroke-linejoin="round" d="M8 10h.01M12 10h.01M16 10h.01M9 16H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-5l-5 5v-5z" />
      </svg>
    </div>
    <div id="chat-popup" class="hidden absolute bottom-20 right-0 w-96 bg-white rounded-md shadow-md flex flex-col transition-all text-sm">
     
      <!-- Callmentor Header Text -->
<div id="chat-header" class="flex flex-col items-center p-4 bg-blue-600 text-white rounded-t-md relative">
    <div class="mb-2">
        <svg viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg" class="h-8 w-8">
            <g>
                <path d="M26,27H12A11,11,0,0,1,12,5H26A11,11,0,0,1,37,16" fill="none" stroke="#FFFFFF" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                <path d="M26,23H12A7,7,0,0,1,12,9H26a7,7,0,0,1,7,7" fill="none" stroke="#FFFFFF" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                <ellipse cx="27.20455" cy="15.22653" rx="1.29545" ry="1.61932" fill="#FFFFFF"></ellipse>
                <ellipse cx="10.79545" cy="15.22653" rx="1.29545" ry="1.61932" fill="#FFFFFF"></ellipse>
                <path d="M14.68182,16.17885a5.49719,5.49719,0,0,0,8.63636,0" fill="none" stroke="#FFFFFF" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                <circle cx="37.99999" cy="26.87923" r="8.99897" fill="none" stroke="#FFFFFF" stroke-width="2" stroke-miterlimit="10"></circle>
                <line x1="33.33333" x2="30" y1="34.66667" y2="40.66667" fill="none" stroke="#FFFFFF" stroke-width="2" stroke-linecap="round"></line>
                <line x1="19" x2="19" y1="1" y2="5" fill="none" stroke="#FFFFFF" stroke-width="2" stroke-linecap="round"></line>
            </g>
        </svg>
    </div>
    <h3 class="m-0 text-lg">Callmentor AI Assistant</h3>
    <button id="close-popup" class="absolute top-4 right-4 bg-transparent border-none text-white cursor-pointer">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round" d="M6 18L18 6M6 6l12 12" />
          </svg>
        </button>
      </div>
      <div id="chat-messages" class="flex-1 p-4 overflow-y-auto"></div>
      <div id="chat-input-container" class="p-4 border-t border-gray-300 bg-white">
        <div class="flex">
          <div class="flex items-center border border-gray-300 rounded-md w-full">
            <textarea id="chat-input" rows="1" class="flex-1 px-4 py-2 rounded-l-md outline-none resize-none overflow-hidden" placeholder="Write a message..." style="line-height: 1.5;"></textarea>
            <button id="chat-submit" class="px-4 text-gray-500 hover:text-gray-700 transition">
              <svg xmlns="http://www.w3.org/2000/svg" class="w-5 h-5" fill="none" viewBox="0 0 24 24" stroke="currentColor" stroke-width="2">
                <path stroke-linecap="round" stroke-linejoin="round" d="M5 12h14M12 5l7 7-7 7" />
              </svg>
            </button>
          </div>
        </div>
      </div>
    </div>
  `;

    const chatInput = document.getElementById('chat-input');
    const chatSubmit = document.getElementById('chat-submit');
    const chatMessages = document.getElementById('chat-messages');
    const chatBubble = document.getElementById('chat-bubble');
    const chatPopup = document.getElementById('chat-popup');
    const closePopup = document.getElementById('close-popup');

    // Save a chat message into session storage
    function saveChatHistory() {
        const chatMessagesArray = [];
        document.querySelectorAll('#chat-messages > div').forEach((msg) => {
            chatMessagesArray.push(msg.outerHTML);
        });
        sessionStorage.setItem("chatHistory", JSON.stringify(chatMessagesArray));
    }

// Load the messages that are in session storage
    function loadChatHistory() {
        const savedHistory = JSON.parse(sessionStorage.getItem("chatHistory"));
        const chatMessages = document.getElementById('chat-messages');
        if (savedHistory) {
            chatMessages.innerHTML = savedHistory.join('');
            setTimeout(() => {
                chatMessages.scrollTop = chatMessages.scrollHeight;
            }, 10); // Delay to ensure everything is properly loaded before scrolling
        }
    }

    // Function to send a user message
    function sendMessage() {
        const message = chatInput.value.trim();
        if (!message) return;

        // Load current chat history from sessionStorage
        const currentHistory = JSON.parse(sessionStorage.getItem("chatHistory")) || [];

        // Append the user's message
        const messageElement = document.createElement('div');
        messageElement.className = 'flex items-start justify-end mb-3';
        messageElement.innerHTML = `
    <div class="bg-blue-600 text-white rounded-lg py-2 px-4 max-w-[70%]">
        ${message}
    </div>
    <div class="flex-shrink-0 ml-2">
        <div class="h-8 w-8 rounded-full border-2 border-blue-900 flex items-center justify-center bg-white">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="#122A88" class="h-6 w-6">
                <path stroke-linecap="round" stroke-linejoin="round" d="M15.75 6a3.75 3.75 0 1 1-7.5 0 3.75 3.75 0 0 1 7.5 0ZM4.501 20.118a7.5 7.5 0 0 1 14.998 0A17.933 17.933 0 0 1 12 21.75c-2.676 0-5.216-.584-7.499-1.632Z" />
            </svg>
        </div>
    </div>
`;
        chatMessages.appendChild(messageElement);
        chatMessages.scrollTop = chatMessages.scrollHeight;

        chatInput.value = '';
        saveChatHistory();

        // Add Typing Indicator
        const typingIndicator = document.createElement('div');
        typingIndicator.id = 'typing-indicator';
        typingIndicator.className = 'flex items-start mb-3';
        typingIndicator.innerHTML = `
            <div class="h-8 w-8 rounded-full border-2 border-blue-900 flex items-center justify-center bg-white mr-2">
            <svg viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg" class="h-6 w-6">
                <g>
                    <path d="M26,27H12A11,11,0,0,1,12,5H26A11,11,0,0,1,37,16" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                    <path d="M26,23H12A7,7,0,0,1,12,9H26a7,7,0,0,1,7,7" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                    <ellipse cx="27.20455" cy="15.22653" rx="1.29545" ry="1.61932" fill="#122A88"></ellipse>
                    <ellipse cx="10.79545" cy="15.22653" rx="1.29545" ry="1.61932" fill="#122A88"></ellipse>
                    <path d="M14.68182,16.17885a5.49719,5.49719,0,0,0,8.63636,0" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                    <circle cx="37.99999" cy="26.87923" r="8.99897" fill="none" stroke="#122A88" stroke-width="2" stroke-miterlimit="10"></circle>
                    <line x1="33.33333" x2="30" y1="34.66667" y2="40.66667" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round"></line>
                    <line x1="19" x2="19" y1="1" y2="5" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round"></line>
                </g>
            </svg>
            </div>
            <div class="bg-gray-200 text-black rounded-lg py-2 px-4 max-w-[70%]">
                <span class="dot-animation">...</span>
            </div>
        `;
        chatMessages.appendChild(typingIndicator);
        chatMessages.scrollTop = chatMessages.scrollHeight;

        // Send request to the backend
        fetch('http://localhost:5000/chat', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                message,
                chat_history: currentHistory
            }),
        })
            .then((response) => {
                if (!response.ok) {
                    if (response.status === 500) {
                        return response.json();;
                    }
                    throw new Error("Unexpected error");
                }
                return response.json();
            })
            .then((data) => {
                typingIndicator.remove(); // Remove typing indicator

                // Append AI response
                const replyElement = document.createElement('div');
                replyElement.className = 'flex items-start mb-3';
                replyElement.innerHTML = `
   <div class="flex-shrink-0 mr-2">
        <div class="h-8 w-8 rounded-full border-2 border-blue-900 flex items-center justify-center bg-white">
            <svg viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg" class="h-6 w-6">
                <g>
                    <path d="M26,27H12A11,11,0,0,1,12,5H26A11,11,0,0,1,37,16" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                    <path d="M26,23H12A7,7,0,0,1,12,9H26a7,7,0,0,1,7,7" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                    <ellipse cx="27.20455" cy="15.22653" rx="1.29545" ry="1.61932" fill="#122A88"></ellipse>
                    <ellipse cx="10.79545" cy="15.22653" rx="1.29545" ry="1.61932" fill="#122A88"></ellipse>
                    <path d="M14.68182,16.17885a5.49719,5.49719,0,0,0,8.63636,0" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                    <circle cx="37.99999" cy="26.87923" r="8.99897" fill="none" stroke="#122A88" stroke-width="2" stroke-miterlimit="10"></circle>
                    <line x1="33.33333" x2="30" y1="34.66667" y2="40.66667" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round"></line>
                    <line x1="19" x2="19" y1="1" y2="5" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round"></line>
                </g>
            </svg>
        </div>
    </div>
    <div class="bg-gray-200 text-black rounded-lg py-2 px-4 max-w-[70%]">
    ${formatMessageWithLinks(data.response)}
</div>
                `;
                chatMessages.appendChild(replyElement);
                chatMessages.scrollTop = chatMessages.scrollHeight;
                saveChatHistory();
            })
            .catch((error) => {
                console.error('Error:', error);
                typingIndicator.remove();
            });
    }

// Function to ensure the welcome message is always at the start of every conversation
    function insertWelcomeMessage() {
        const chatMessages = document.getElementById('chat-messages'); // Ensure chatMessages exists

        // Check if the welcome message already exists to prevent duplicates
        if (!document.getElementById('welcome-message')) {
            const welcomeMessage = document.createElement('div');
            welcomeMessage.id = 'welcome-message'; // Assign an ID to avoid duplicates
            welcomeMessage.className = 'flex items-start mb-3';
            welcomeMessage.innerHTML = `
            <div class="h-8 w-8 flex-shrink-0 rounded-full border-2 border-blue-900 flex items-center justify-center bg-white mr-2">
            <svg viewBox="0 0 48 48" xmlns="http://www.w3.org/2000/svg" class="h-6 w-6">
                <g>
                    <path d="M26,27H12A11,11,0,0,1,12,5H26A11,11,0,0,1,37,16" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                    <path d="M26,23H12A7,7,0,0,1,12,9H26a7,7,0,0,1,7,7" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                    <ellipse cx="27.20455" cy="15.22653" rx="1.29545" ry="1.61932" fill="#122A88"></ellipse>
                    <ellipse cx="10.79545" cy="15.22653" rx="1.29545" ry="1.61932" fill="#122A88"></ellipse>
                    <path d="M14.68182,16.17885a5.49719,5.49719,0,0,0,8.63636,0" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"></path>
                    <circle cx="37.99999" cy="26.87923" r="8.99897" fill="none" stroke="#122A88" stroke-width="2" stroke-miterlimit="10"></circle>
                    <line x1="33.33333" x2="30" y1="34.66667" y2="40.66667" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round"></line>
                    <line x1="19" x2="19" y1="1" y2="5" fill="none" stroke="#122A88" stroke-width="2" stroke-linecap="round"></line>
                </g>
            </svg>
            </div>
            <div class="bg-gray-200 text-black rounded-lg py-2 px-4 max-w-[70%]">
                Hello! I'm Callmentor AI Assistant.<br><br>
                I can assist you with:<br>
                - Information about how Callmentor works, its tools, and benefits for mentees and mentors.<br>
                - Tips on job searching, skill-building, and career planning.<br>
                - Insights into current trends and opportunities in various industries.<br>
                - Practical advice on crafting resumes and succeeding in interviews.<br>
                - Actionable tips for improving soft skills and advancing your career.<br>
                - Recommending mentors who best fit your needs.<br><br>
                Feel free to ask any specific questions you have!
            </div>
        `;
            chatMessages.prepend(welcomeMessage); // Add message at the top
        }
    }

// Ensure Welcome Message is inserted when chat opens
    chatBubble.addEventListener('click', function () {
        if (chatPopup.classList.contains('hidden')) {
            chatPopup.classList.remove('hidden'); // Show widget
            insertWelcomeMessage(); // Ensure welcome message is added
            loadChatHistory(); // Load chat history when the widget opens

            // Scroll to the bottom of the chat log after opening
            setTimeout(() => {
                chatMessages.scrollTop = chatMessages.scrollHeight;
            }, 10); // Delay to ensure everything is loaded properly before scrolling
        } else {
            chatPopup.classList.add('hidden'); // Hide widget
        }
    });

    // Toggle chat widget open/close
    chatBubble.addEventListener('click', function () {
        chatPopup.style.display = chatPopup.style.display === "none" || chatPopup.style.display === "" ? "flex" : "none";
        if (chatPopup.style.display === "flex") {
            chatInput.focus();
        }
    });

    closePopup.addEventListener('click', function () {
        chatPopup.style.display = "none";
    });
    // Check if
    chatSubmit.addEventListener('click', sendMessage);
    chatInput.addEventListener('keydown', function (event) {
        if (event.key === 'Enter' && !event.shiftKey) {
            event.preventDefault();
            sendMessage();
        }
    });

// Function to convert markdown-style links and raw URLs into clickable hyperlinks
    function formatMessageWithLinks(message) {
        // Convert Markdown links [text](url) to HTML <a> tags
        message = message.replace(/\[([^\]]+)\]\((https?:\/\/[^\)]+)\)/g, function(match, text, url) {
            return `<a href="${url}" target="_blank" class="text-blue-600 underline hover:text-blue-800">${text}</a>`;
        });

        // Convert raw URLs into clickable hyperlinks (but ignore those already converted)
        message = message.replace(/(?<!href=")(https?:\/\/[^\s]+)/g, function(url) {
            return `<a href="${url}" target="_blank" class="text-blue-600 underline hover:text-blue-800">${url}</a>`;
        });

        return message;
    }

    // Load the stored chat history in session storage
    loadChatHistory();
})();