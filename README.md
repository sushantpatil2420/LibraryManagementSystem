# 📚 Library Management System

A full-featured **Library Management System** REST API built with **Spring Boot**, **Spring Data JPA**, and **MySQL**. This project supports managing books, members, categories, and authors, along with a complete borrowing/return workflow, overdue fine calculation, and interactive API documentation via Swagger.

---

## 🚀 Features

### Core CRUD
- Full **Create, Read, Update, Delete** operations for:
  - Books
  - Members
  - Categories
  - Authors

### Borrowing System
- Borrow and return books, with automatic tracking of available copies
- Prevents borrowing a book with zero available copies
- Prevents returning a book that was already returned

### Search
- Search books by **title**, **ISBN**, **category**, or **author**
- List all **currently available** books (at least 1 copy in stock)

### Borrow History & Analytics
- Full borrow history for a specific member
- Full borrow history for a specific book
- List of all **currently active** (not yet returned) borrows
- List of all **overdue** borrows
- Count of a member's currently active borrows

### Fine Calculation
- Automatic overdue fine calculation (₹5/day) based on due date vs. return date (or today's date, if still borrowed)

### Robust Error Handling
- Centralized global exception handling with clean, structured JSON responses
- Distinct handling for:
  - `404 Not Found` — resource doesn't exist
  - `400 Bad Request` — invalid business operation (e.g. book unavailable)
  - `409 Conflict` — database constraint violations (e.g. deleting a linked record)
  - `500 Internal Server Error` — unexpected failures

### Input Validation
- Server-side validation on all entity fields (`@NotBlank`, `@Email`, `@Min`, etc.)
- Structured field-by-field validation error responses

### Pagination
- Paginated endpoints for book and member listings, with page number and size support

### API Documentation
- Interactive Swagger UI, auto-generated from the codebase — test every endpoint directly from the browser

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot |
| Data Access | Spring Data JPA (Hibernate) |
| Database | MySQL |
| Validation | Jakarta Bean Validation (Hibernate Validator) |
| API Docs | springdoc-openapi (Swagger UI) |
| Build Tool | Maven |

---

## 🗂️ Project Structure

```
src/main/java/com/project/library
├── controller/       REST API endpoints
├── service/          Business logic
├── repository/        Spring Data JPA repositories
├── entity/            JPA entity classes (database models)
├── dto/               Data Transfer Objects (API-facing models)
├── exception/         Custom exceptions + global exception handler
└── LibraryManagementApplication.java
```

---

## 🧩 Entity Relationships

- A **Book** belongs to one **Category** and can have multiple **Authors** (many-to-many)
- A **BorrowRecord** links one **Member** to one **Book**, tracking borrow date, due date, and return date
- A **Member** can have many **BorrowRecords** (borrow history)

---

## 📡 API Endpoints Overview

### Books — `/api/books`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/books` | Add a new book |
| GET | `/api/books?page=0&size=10` | Get all books (paginated) |
| GET | `/api/books/{bookId}` | Get book by ID |
| PUT | `/api/books/{bookId}` | Update a book |
| DELETE | `/api/books/{bookId}` | Delete a book |
| GET | `/api/books/title/{bookTitle}` | Search by title |
| GET | `/api/books/isbn/{isbn}` | Search by ISBN |
| GET | `/api/books/category/{categoryName}` | Search by category |
| GET | `/api/books/author/{authorName}` | Search by author |
| GET | `/api/books/available` | List available books |

### Members — `/api/members`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/members` | Add a new member |
| GET | `/api/members?page=0&size=10` | Get all members (paginated) |
| GET | `/api/members/{memberId}` | Get member by ID |
| PUT | `/api/members/{memberId}` | Update a member |
| DELETE | `/api/members/{memberId}` | Delete a member |

### Categories — `/api/categories`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/categories` | Add a new category |
| GET | `/api/categories` | Get all categories |
| GET | `/api/categories/{categoryId}` | Get category by ID |
| PUT | `/api/categories/{categoryId}` | Update a category |
| DELETE | `/api/categories/{categoryId}` | Delete a category |

### Authors — `/api/authors`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/authors` | Add a new author |
| GET | `/api/authors` | Get all authors |
| GET | `/api/authors/{authorId}` | Get author by ID |
| PUT | `/api/authors/{authorId}` | Update an author |
| DELETE | `/api/authors/{authorId}` | Delete an author |

### Borrowing — `/api/borrow`
| Method | Endpoint | Description |
|---|---|---|
| POST | `/api/borrow/{memberId}/{bookId}` | Borrow a book |
| PUT | `/api/borrow/return/{borrowRecordId}` | Return a book |
| GET | `/api/borrow/history/member/{memberId}` | Full borrow history for a member |
| GET | `/api/borrow/history/book/{bookId}` | Full borrow history for a book |
| GET | `/api/borrow/active` | All currently borrowed books |
| GET | `/api/borrow/overdue` | All overdue books |
| GET | `/api/borrow/count/member/{memberId}` | Count of active borrows for a member |

---

## ⚙️ Getting Started

### Prerequisites
- Java 21+
- Maven
- MySQL Server

### 1. Clone the repository
```bash
git clone https://github.com/sushantpatil2420/LibraryManagementSystem.git
cd LibraryManagementSystem
```

### 2. Configure the database
Create a MySQL database, then update `src/main/resources/application.properties` with your credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Run the application
```bash
mvn spring-boot:run
```
The application starts on `http://localhost:8080`.

### 4. Explore the API
Open the interactive Swagger UI in your browser:
```
http://localhost:8080/swagger-ui/index.html
```
Every endpoint can be tested directly from this page — no separate tool like Postman required.

---

## 🧪 Example: Borrow & Return Flow

1. Create a category → `POST /api/categories`
2. Create an author → `POST /api/authors`
3. Create a book, linking category and author → `POST /api/books`
4. Create a member → `POST /api/members`
5. Borrow the book → `POST /api/borrow/{memberId}/{bookId}`
6. Check active borrows → `GET /api/borrow/active`
7. Return the book → `PUT /api/borrow/return/{borrowRecordId}`
8. Check borrow history → `GET /api/borrow/history/member/{memberId}` (includes calculated fine, if any)

---

## 📌 Notes

- Fine rate is currently fixed at ₹5 per day overdue (configurable in `BorrowRecordService`)
- Default loan period is 14 days from the borrow date
- All list responses that support pagination default to page `0`, size `10` if not specified

---

## 📄 License

This project is open for educational and portfolio purposes.
