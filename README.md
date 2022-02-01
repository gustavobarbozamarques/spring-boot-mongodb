# Spring Boot MongoDB

This project demonstrates a simple microservice that uses Spring Boot and MongoDB database.

### Run project

``` docker-compose build ```
``` docker-compose up ``` 

And access: ``` http://localhost:8080/v1/products ``` 

### Swagger

Access: ``` http://localhost:8080/swagger-ui.html ```

![Alt text](doc/swagger.png?raw=true "Swagger")

### Sonar

Access: ``` http://localhost:9000 ```

Username: ``` admin ```

Password: ``` admin ``` 

Create a new project, get and run gradle snippet.

Ex.:

```
./gradlew sonarqube \
  -Dsonar.projectKey=<PROJECT_KEY> \
  -Dsonar.host.url=http://localhost:9000 \
  -Dsonar.login=<PROJECT_TOKEN>
```

![Alt text](doc/sonar.png?raw=true "Sonar")