**Hello, NetValue!**

How to start:

1. Open project as maven project
2. Execute ```./mvnw install``` (from root source folder) <br/>
   During installation process, controllers and request/response models will be generated from API specification
   ```src/main/resources/openapi/openapi.yaml```
3. Run application
    - Using command line in Docker <br/>
      Run ```./environment.sh``` (from root source folder)<br/>
      Application will be started at URL ```localhost:9080```<br/>
      Swagger-ui will be available: ```localhost:9080/swagger-ui```

    - Using Intellij IDEA <br/>
      Run saved configuration ```ChargePointsApplication```<br/>
      Application will be started at URL ```localhost:8080```<br/>
      Swagger-ui will be available: ```localhost:8080/swagger-ui``` <br/>

**Example for calling REST-api:```/src/test/request```** 