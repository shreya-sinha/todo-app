# Todo Application
Simple Todo REST-Service made using Dropwizard

How to start the todo application
---
Note: Application expects a mongodb server running at localhost:27017
1. Run `mvn -s settings.xml clean install` to build your application
2. Start application with `java -jar target/todo-1.0-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080`

Health Check
---

To see your applications health enter url `http://localhost:8080/admin/healthcheck`
