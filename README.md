
# CRUD RESTfull Cinema Application
>__Details Web messenger clone__
>You can create/read/update/delete actors add movies.

## Used technologies
* **Java 17**
* Spring Boot
* Spring Data JPA(to work with entities)
* Spring AOP(for logging)
* **PostgreSQL**(main DBMS for this project)
* Maven(package manager to build .jaf-file and manipulate with dependencies)
* Docker(for run the application in container)
* Liquibase(for migration with database)
* OpenAPI(Swagger 3.0)
* Lombok

## Steps to Setup

**1. Clone the application**

```bash
git clone https://github.com/AlekseiKalashnik/CinemaApp.git
```

**2. Create PostgreSQL database**
```bash
create database cinema_db
```

**3. Change PostreSQL username and password as per your installation**

+ open `src/main/resources/application.properties`
+ change `spring.datasource.username` and `spring.datasource.password` as per your PostgreSQL installation

**4. Run the app using maven**

```bash
mvn spring-boot:run
```
The app will start running at <http://localhost:8080>

## Explore Rest APIs

The app defines following CRUD APIs:
http://localhost:8080/swagger-ui/index.html

