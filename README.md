# Read Me First

**Hello, NetValue!**
This is application that manages charge points used to charge electric vehicles.

# Getting Started

### 1. Open project as maven project
Use JDK 11

### 2. Run project installation

Execute ```./mvnw install```

During installation, controllers and it's models will be generated from API specification at
```src/main/resources/openapi/openapi.yaml```

### 3. Run application using Docker

Execute command ```./environment.sh``` <br/>
The application will be available at: ```localhost:8080```

**NOTE:_You have to first install and run [Docker](https://www.docker.com/) on your machine_**

### 4. Call REST-requests to application

You can use:

- Swagger-ui, it's available at: ```localhost:8080/swagger-ui```
- Samples of REST-api in source code at: ```/src/test/request```

### 5. Monitoring

When the application works, [prometheus](https://prometheus.io/) is scraping productivity metrics.
You can see all metrics in [Grafana](https://grafana.com/), that is available at: ```localhost:3000```

### 6. Local debug

You can also run application locally, for example using Intellij IDEA, just run saved
configuration ```ChargePointsApplication```<br/>

Application will be available at: ```localhost:8080```<br/>
Swagger-ui will be available at: ```localhost:8080/swagger-ui``` <br/>
**NOTE: Monitoring doesn't work, when use local start** 