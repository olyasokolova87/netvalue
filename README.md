Hello, NetValue!

How to start:
Example for calling REST-api: /src/test/request

- Using command line in Docker (in project root directory)
    - Build ```./mvnw clean install```
    - Run  ```./environment.sh```
    - Application will be started at URL ```localhost:9080```
    - Swagger-ui will be available: ```localhost:9080/swagger-ui```

- Using Intellij IDEA (in project root directory)
    - Build ```./mvnw clean install```
    - Run saved configuration ```ChargePointsApplication```
    - Application will be started at URL ```localhost:8080```
    - Swagger-ui will be available: ```localhost:8080/swagger-ui```