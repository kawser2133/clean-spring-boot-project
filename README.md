# Clean Spring Boot Project

This project is a template for a Spring Boot application with complete authentication and API CRUD operations. It includes features such as user signup with email verification, login, password reset, product management with paginated lists, and detailed unit tests. The project is built using Maven and follows a clean architecture.

## Features

- **User Authentication**
    - Signup with email verification
    - Login with JWT-based authentication
    - Password reset functionality

- **Product Management**
    - Create, Read, Update, and Delete (CRUD) operations
    - Paginated list of products
    - Caching for improved performance

- **Additional Features**
    - Comprehensive logging
    - Unit tests
    - OpenAPI documentation (Swagger UI)
    - Database migration using Flyway
    - Email notifications

## Project Structure

```
my-spring-boot-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── kawser/
│   │   │           └── cleanspringbootproject/
│   │   │               ├── api/
│   │   │               │   ├── controllers/
│   │   │               │   ├── models/
│   │   │               │   │   └── dto/
│   │   │               │   ├── repositories/
│   │   │               │   ├── services/
│   │   │               │   │   └── impl/
│   │   │               ├── auth/
│   │   │               │   ├── config/
│   │   │               │   ├── controllers/
│   │   │               │   ├── models/
│   │   │               │   │   └── dto/
│   │   │               │   ├── repositories/
│   │   │               │   ├── services/
│   │   │               │   │   └── impl/
│   │   │               │   ├── util/
│   │   │               ├── exception/
│   │   │               │   ├── api/
│   │   │               │   ├── auth/
│   │   │               │   ├── global.handler/
│   │   │               └── CleanSpringBootProjectApplication.java
│   │   ├── resources/
│   │   │   ├── application.properties
│   │   │   ├── schema.sql
│   │   │   └── messages.properties
│   └── test/
│       ├── java/
│       │   └── com/
│       │       └── kawser/
│       │           └── cleanspringbootproject/
│       │               ├── api/
│       │               │   ├── controllers/
│       │               │   ├── repositories/
│       │               │   ├── services/
│       │               ├── auth/
│       │               │   ├── controllers/
│       │               │   ├── repositories/
│       │               │   ├── services/
│       │               └── CleanSpringBootProjectApplicationTests.java
│       └── resources/
│           └── application-test.properties
├── .gitignore
├── README.md
└── pom.xml
```

## Dependencies

- **Spring Boot Starter Data JPA**
    - Provides integration with Spring Data JPA for database access and ORM functionality.

- **Spring Boot Starter Security**
    - Adds security features such as authentication and authorization using Spring Security.

- **Spring Boot Starter Web**
    - Builds web applications, including RESTful services, using Spring MVC.

- **Spring Boot Starter Mail**
    - Supports sending emails using JavaMailSender.

- **Spring Boot DevTools**
    - Enhances the development experience with features such as automatic restarts and live reload.

- **Flyway Core**
    - Provides database version control for schema migrations.

- **PostgreSQL JDBC Driver**
    - Allows the application to connect to PostgreSQL databases.

- **Java JWT**
    - Library for creating and verifying JSON Web Tokens (JWT) for authentication.

- **Lombok**
    - Reduces boilerplate code by generating getters, setters, and other methods at compile time.

- **Spring Boot Actuator**
    - Adds production-ready features such as monitoring and metrics to your application.

- **SpringDoc OpenAPI**
    - Integrates OpenAPI 3 specifications with Spring Boot, providing a UI for API documentation.

- **Spring Boot Starter Validation**
    - Supports validation of user input using JSR-303/JSR-380 annotations.

- **Spring Boot Starter Cache**
    - Provides caching support to improve application performance.

- **SLF4J and Log4j**
    - Logging framework that supports various logging implementations.

- **Java Faker**
    - Library for generating fake data such as names, addresses, and phone numbers for testing purposes.

- **Spring Boot Starter Test**
    - Includes testing libraries such as JUnit, Hamcrest, and Mockito for unit and integration tests.

- **Spring Security Test**
    - Provides support for testing Spring Security features.

- **Testcontainers**
    - Enables testing with containerized dependencies such as databases and web servers.

- **H2 Database**
    - In-memory database used for testing purposes.

## Getting Started

### Prerequisites

- Java 21
- Maven 3.6+
- PostgreSQL

### Installation

1. Clone the repository:

   ```sh
   git clone https://github.com/yourusername/clean-spring-boot-project.git
   cd clean-spring-boot-project
   ```

2. Update the database configurations in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/yourdatabase
   spring.datasource.username=yourusername
   spring.datasource.password=yourpassword

   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true

   spring.mail.host=smtp.example.com
   spring.mail.port=587
   spring.mail.username=your-email@example.com
   spring.mail.password=your-email-password
   spring.mail.properties.mail.smtp.auth=true
   spring.mail.properties.mail.smtp.starttls.enable=true

   jwt.secret=your-jwt-secret
   ```

3. Run the application:

   ```sh
   mvn spring-boot:run
   ```

- **The API documentation is available at http://localhost:8080/swagger-ui/index.html after the application is started.**
    - It provides detailed information about the available endpoints, their request parameters, and response formats.
