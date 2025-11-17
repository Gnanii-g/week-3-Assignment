📌 Employee Services API

A fully functional RESTful Employee Management API built using Spring Boot.
This project supports complete CRUD operations and several advanced search mechanisms, including:

🔍 JPA Specifications

🔍 HQL (JPQL)

🔍 Native SQL Queries

The application uses an H2 in-memory database for testing/demo purposes and follows clean REST design principles.

🚀 Tech Stack

| Layer      | Technology                  |
| ---------- | --------------------------- |
| Backend    | Spring Boot 3+              |
| Database   | H2 In-Memory Database       |
| ORM        | Spring Data JPA (Hibernate) |
| Validation | Spring Boot Validation      |
| Testing    | JUnit 5, Mockito            |
| Build Tool | Maven                       |

---
🔧 Setup & Run
1️⃣ Clone the Repository
git clone https://github.com/Gnanii-g/employeeapi.git

2️⃣ Open in IDE

Use IntelliJ IDEA or Eclipse → open as a Maven Project.

3️⃣ Run the Application

Run the main class:

EmployeeapiApplication.java

🌐 Application URLs
▶ Base API URL
http://localhost:8080/api/employees

▶ H2 Database Console
http://localhost:8080/h2-console


JDBC URL

jdbc:h2:mem:employeedb


Username: sa
Password: (leave blank)

📚 API Endpoints

| Method     | Endpoint                              | Description                          |
| ---------- | ------------------------------------- | ------------------------------------ |
| **POST**   | `/api/employees`                      | Create a new employee                |
| **GET**    | `/api/employees`                      | Get all employees                    |
| **GET**    | `/api/employees/by-email?email=`      | Get employee by email                |
| **GET**    | `/api/employees/by-name?name=`        | Get employees by name                |
| **PUT**    | `/api/employees/{id}`                 | Update last name, phone, and address |
| **PATCH**  | `/api/employees/{id}/phone?phone=`    | Update only phone number             |
| **DELETE** | `/api/employees/by-email?email=`      | Delete employee by email             |
| **GET**    | `/api/employees/by-email-hql?email=`  | Search using HQL (JPQL)              |
| **GET**    | `/api/employees/by-name-native?name=` | Search using Native SQL              |
| **GET**    | `/api/employees/search?email=&name=`  | Search using JPA Specifications      |


🧪 Testing

This project includes unit tests and integration tests for:

Controller Layer

Service Layer

Using:

✔ JUnit 5

✔ Mockito

🧾 Sample Employee JSON
{
  "firstName": "Gnani",
  "lastName": "G",
  "email": "Gnani@example.com",
  "phone": "9666710934",
  "address": "Bhimvaram kovada"
}

Postman Collection
All endpoints are tested and verified using Postman.


