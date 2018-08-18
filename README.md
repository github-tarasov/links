Spring Boot + Postgresql/HSQLDB + Hibernate + Gradle
=========================

***

Requirements
------------
* [Java Platform (JDK) 8](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
* PostgreSQL by [link](https://www.postgresql.org/) or by docker commands: `docker pull postgres:9.4`
* [Gradle 4.0](https://gradle.org/)

Quick start
-----------
1. Run PostgreSQL process or use docker command: `docker run --name postgres-latest -v /opt/db/postgres-latest:/var/lib/postgresql/data -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=postgres -p 5432:5432 -d postgres:9.4`.
2. Configure all files in src/main/resources
3. Run in console `gradle jar`, `java -jar build/libs/links-0.0.1-SNAPSHOT.jar` or just `gradle bootRun`
