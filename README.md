
# Online Shop

Online Shop is a web application created with Java 17 and Spring Boot 3.0.4 on the backend, and [Angular](https://github.com/Zaitis/shop-frontend) on the frontend. It uses technologies such as Hibernate, Maven, JPA, JDBC/MySQL, Spring Security, JWT, Spring Boot Mail, Liquibase, Lombok, Commons, Slugify, Jsoup, and Apache Commons CSV. The application is divided into two main parts: an administrative page and a user interface. Furthermore, the application utilizes CI/CD tools such as GitHub Actions for automatic deployment to the server.

## User

The user interface implements features such as login and registration, browsing categories and products, adding comments to products, adding products to the shopping cart and making purchases. These functions are also available for unregistered users.


## Administrator
The administrative page allows you to manage the application by adding, removing and editing categories and products. The administrator has the ability to manage orders, export data to a file, display charts, as well as manage comments by accepting or deleting them.

## Links

### Live Application:
http://zaitis.alwaysdata.net

### API Documentation (Swagger):
http://zaitis.alwaysdata.net/z/swagger-ui/index.html

### Frontend (GitHub):
https://github.com/Zaitis/shop-frontend

## Tech Stack

* Java 17
* Spring Boot 3.0.4
* Hibernate/Jpa
* Hibernate Validator
* Maven
* MySQL
* Liquibase
* Git
* JUnit
* Lombok
* Swagger
* Slugify



## Feedback

If you have any question or feedback, please reach me at krzysztof@painm.pl


## Run Locally

# NOT IMPLEMENTED YET....
## Work in progress....

```bash
  git clone https://github.com/Zaitis/shop-backend
```

Open project in IDE. (InteliJ)


Go to:

```bash
  src\main\java\pl\zaitis\shop
```

Run

```bash
  ShopApplication.main()
```

Open Browser, the application will be start:

```bash
  https://localhost:8080
```

If you want run and connect database you should make new locally database and change
creditials in application.propertis: DATABASE_NAME, USERNAME, PASSWORD


```bash
  spring.datasource.url=jdbc:mysql://localhost:3306/DATABASE_NAME?useUnicode=true&serverTimezone=UTC
spring.datasource.username= USERNAME
spring.datasource.password= PASSWORD
```