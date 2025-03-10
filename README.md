# budget-buddy-management
BudgetBuddy is a financial wellness application designed to help users manage their budgets, track spending, and achieve their financial goals. Built using Java 8 and Spring Boot, it leverages Investec's API and Programmable Cards to provide real-time spending insights, personalized budget recommendations, and automated savings features.

🚀 Features
Real-Time Spending Tracking: Fetch and categorize transactions using Investec's API.

Budget Management: Set and track monthly budgets for different categories.

Spending Insights: Visualize spending patterns with charts and analytics.

Programmable Card Integration: Enforce spending limits and automate savings.

User-Friendly Dashboard: Intuitive interface for managing finances.

Security: Secure APIs and data encryption for user privacy.

🧩 Key Components
1. Investec API Integration
   Fetch transaction data in real-time.

Categorize expenses (e.g., groceries, entertainment).

2. Budget Management
   Set monthly budgets for categories.

Track spending against budgets.

3. Programmable Card Integration
   Enforce spending limits for specific categories.

Automate savings transfers.

4. Dashboard
   Visualize spending with charts (e.g., pie charts, bar graphs).

Provide actionable insights and recommendations.

🚀 Getting Started
Prerequisites
Java 8 or higher

Maven/Gradle

MySQL/PostgreSQL

Investec API credentials

Programmable Cards API credentials

Step 1: Clone the Repository


git clone https://github.com/your-username/budgetbuddy.git
cd budgetbuddy
Step 2: Configure the Application
Database Configuration
Update application.properties with your database credentials:

properties

spring.datasource.url=jdbc:mysql://localhost:3306/budgetbuddy
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
Investec API Configuration
Add your Investec API credentials:

properties

investec.api.url=https://api.investec.com
investec.api.key=your-api-key
Programmable Cards API Configuration
Add your Programmable Cards API credentials:

properties

investec.card.api.url=https://api.investec.com
investec.card.api.key=your-card-api-key
Step 3: Build and Run the Application
Build the Project

mvn clean install
Run the Application

http://localhost:8080
Step 4: Use the Application
Sign Up/Log In
Create an account or log in to access the dashboard.

Link Investec Account
Connect your Investec account to fetch transactions.

Set Budgets
Define monthly budgets for different categories (e.g., groceries, entertainment).

Track Spending
View real-time spending insights and progress toward your budgets.

Set Spending Limits
Use Programmable Cards to enforce spending limits.

Automate Savings
Set savings goals and automate transfers.

🧪 Testing
Unit Tests
Run unit tests using:

mvn test
API Testing
Use Postman or cURL to test the REST APIs.

📄 License
This project is licensed under the MIT License. See the LICENSE file for details.

🙌 Contributing
Contributions are welcome! Please follow these steps:

Fork the repository.

Create a new branch (git checkout -b feature/your-feature).

Commit your changes (git commit -m 'Add some feature').

Push to the branch (git push origin feature/your-feature).

Open a pull request.

🎉 Acknowledgments
Investec for providing the API and Programmable Cards.

Happy coding! 🚀
