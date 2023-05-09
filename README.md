
<<<<<<< HEAD
# Demo shop

This project is demo e-commerce shop. I am still developing, this Spring Boot aplication.   You can check and test this project on swagger at 
http://zaitis.alwaysdata.net/swagger-ui/index.html
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
=======
# E-shop

This project is demo e-commerce shop. I am still developing, this Spring Boot aplication.   You can check and test this project on swagger at
http://zaitis.alwaysdata.net/swagger-ui/index.html
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
>>>>>>> c13dd7f (added payment CRUD, paymet Table, some changes in README)



## Feedback

If you have any question or feedback, please reach me at krzysztof@painm.pl


<<<<<<< HEAD
## Lessons Learned

Not implemented yet...


## ToDo list:

- Not implemented yet...

- Not implemented yet...
=======
## ToDo list:

- Add frontend as an Angular application for visual testing.
- Implement user account creation functionality.
- Integrate Spring Security for authentication and authorization.
- Set up a mailing system for sending emails to users.
- Implement order processing functionality.
- Integrate a payment gateway for online payment processing.

>>>>>>> c13dd7f (added payment CRUD, paymet Table, some changes in README)


## Run Locally

```bash
  git clone https://github.com/Zaitis/shop-backend
```

Open project in IDE. (InteliJ)


Go to:

```bash
  src\main\java\pl\zaitis\shop
```

<<<<<<< HEAD
Run 
=======
Run
>>>>>>> c13dd7f (added payment CRUD, paymet Table, some changes in README)

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