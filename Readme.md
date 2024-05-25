Library Management System
This is a web application for managing a library, built with Spring Boot, Thymeleaf, Hibernate, and PostgreSQL. The application allows creating, reading, updating, and deleting book records, as well as performing book lending and return operations.
Features

View a list of all books in the library
Search books by title, author, or genre
Sort books by author, genre, or publication year
Create new book records
Update information for existing books
Delete books from the library
Book lending and return operations with tracking of dates and users
Access restrictions for regular users (no CRUD operations on books)

Technical Requirements:
Java 17
Spring Boot 2.7.x
Thymeleaf
Hibernate
PostgreSQL

Deployment:
Clone the repository containing the application source code.
Install PostgreSQL and create a database for the application.
Update the database connection settings in the application.properties or application.yml file.
Run the application using the ./mvnw spring-boot:run command or build a JAR file with ./mvnw package and then run it.
The application will be available at http://localhost:8080.

Default Users:
The application comes with two built-in users:

Admin: username="admin", password="admin"
Regular User: username="user", password="password"

The admin has full access to the application's functionality, while the regular user cannot perform CRUD operations on books.
Design and Responsiveness
The web interface of the application is built using Thymeleaf and Bootstrap. It has a responsive design and automatically adjusts to different screen sizes of user devices.
