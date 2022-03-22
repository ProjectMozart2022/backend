to run docker container with database locally

```docker run --name mozart_database -e POSTGRES_PASSWORD=mozart123 -e POSTGRES_USER=mozart -d -p 5432:5432 postgres:alpine```