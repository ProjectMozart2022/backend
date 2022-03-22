to run docker container with database locally

```docker run --name mozart_database -e POSTGRES_PASSWORD=mozart123 -e POSTGRES_USER=mozart -d -p 5432:5432 postgres:alpine```

to initialize database run ```resources/database_initialization.sql``` script in database manually

to start server build project with ```mvn clean install``` and run with  ```java -jar target/MozartBackend-1.0-SNAPSHOT.jar```

make sure you are using java 17. There is also possibility to run service using intellij

