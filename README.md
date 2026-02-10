BFHL REST API - Java Spring Boot
A robust RESTful API developed for the Bajaj Finserv Health Challenge. This project features mathematical computation engines, AI integration, and production-ready error handling.

🚀 Live API Links
POST /bfhl: https://bajaj-production-6128.up.railway.app/bfhl

GET /health: https://bajaj-production-6128.up.railway.app/health

🛠 Features
Mathematical Operations: Calculation of Fibonacci series, Prime number filtering, LCM, and HCF.

AI Integration: Integrated with Google Gemini 1.5 Flash for single-word intelligence responses.

Security: Environment variable masking for sensitive API keys and input sanitation.

Robustness: Global exception handling to ensure no crashes on malformed input.

💻 Tech Stack
Language: Java 17

Framework: Spring Boot 3.x

Build Tool: Maven

Hosting: Railway

AI: Google Gemini API

📖 API Documentation
1. Health Check
GET /health Response:

JSON
{
  "is_success": true,
  "official_email": "ishika1249.be23@chitkarauniversity.edu.in"
}
2. Functional Operations
POST /bfhl

Fibonacci Example:

Request: { "fibonacci": 5 }

Response: { "is_success": true, "data": [0, 1, 1, 2, 3, 5] }

AI Example:

Request: { "AI": "Capital of France?" }

Response: { "is_success": true, "data": "Paris" }

⚙️ Local Setup
Clone the repo: git clone https://github.com/iisshh17/bajaj.git

Set Environment Variables:

GEMINI_API_KEY: AIzaSyA1qD80lJ0582TAbR2HvzSl3bGTATmDPnk

OFFICIAL_EMAIL: ishika1249.be23@chitkarauniversity.edu.in

Run the app: ./mvnw spring-boot:run
