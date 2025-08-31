# Java Spring Boot Banking App

This project is a simple banking application built with Java and Spring Boot.  
It provides RESTful APIs for managing bank accounts and transactions, including account creation, balance inquiries, transfers, and transaction history.  
The architecture follows a layered structure with controllers, services, repositories, and DTOs for clean separation of concerns and maintainability.

---

## Project Structure

```
banking-app/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── banking/
│   │   │           ├── BankingApplication.java
│   │   │           ├── controller/
│   │   │           ├── dto/
│   │   │           ├── entity/
│   │   │           ├── exception/
│   │   │           ├── repository/
│   │   │           └── service/
│   │   └── resources/
│   │       └── application.properties
└── pom.xml
```

- **controller/**: Handles incoming HTTP requests and maps them to service methods.
- **dto/**: Data Transfer Objects for encapsulating and transferring data.
- **entity/**: JPA entities that represent database tables.
- **exception/**: Custom exceptions and global error handling.
- **repository/**: Interfaces for database operations.
- **service/**: Business logic and service layer.
- **application.properties**: Application configuration.

---

## How to Start the Application in IntelliJ IDEA

1. **Clone the Repository**
   ```bash
   git clone https://github.com/S-hissam/java-spring-boot-banking-app.git
   ```

2. **Open the Project in IntelliJ**
    - Go to **File > Open** and select the project folder.
    - IntelliJ will automatically detect the `pom.xml` and import dependencies if using Maven.

3. **Run the Application**
    - Open `BankingApplication.java`.
    - Right-click the file and select **Run 'BankingApplication'**.
    - Alternatively, use the green play button at the top.

4. **Access the Application**
    - By default, the app runs on `http://localhost:8080/`.

---

## For Junior Developers

- **Learn Java Syntax**: Get comfortable with core Java concepts before diving into Spring Boot (classes, objects, inheritance, interfaces).
- **Understand Spring Boot**: Learn about annotations like `@RestController`, `@Service`, `@Repository`, and how dependency injection works.
- **Explore REST APIs**: Experiment with tools like Postman to test your endpoints.
- **Database Integration**: This app uses Spring Data JPA for database operations—explore `entity` and `repository` packages.
- **Error Handling**: Look at custom exceptions and global handlers in the `exception` package.
- **Read the Code**: Start with the controllers, then follow how requests flow to services and repositories.
- **Experiment**: Try adding new features, such as more endpoints or custom validations.

---

## Useful Resources

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Java Tutorial for Beginners](https://www.w3schools.com/java/)
- [Maven Introduction](https://maven.apache.org/guides/getting-started/)
- [IntelliJ IDEA Guide](https://www.jetbrains.com/idea/guide/)

---

Feel free to use and extend this project for learning or as a starting point for more advanced banking solutions.
