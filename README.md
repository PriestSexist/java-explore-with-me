# java-explore-with-me
A RESTful web-service for posting and sharing events and find companions to participate in them. Users can post events, leave comments, register as participans, explore collections of events.

## Stack
Java, Spring Boot, PostgreSQL, JPA(Hibernate), Maven, Docker.

## Architecture
_______________________________________________________________
The app consists of two docker microservises: main API and statistics service. The main API is divided into three parts: public, for authentificated users and andministrator endpoints.
statistics service is divided into three micromodule:
- main API
-     |micromodule public
-     |micromodule authentificated users
-     |micromodule andministrator
- statistics service: 
-     |micromodule Client
-     |micromodule Dto
-     |micromodule Server

The interaction of modules is made through RestTemplate;

The service is also ready for use on the Docker platform;
_______________________________________________________________

## Endpoints
- [API сервис статистики](./ewm-stats-service-spec.json)
- [API основной сервис](./ewm-main-service-spec.json)

## Модель схемы базы данных сервиса
![Screenshot](schema.png)

### Ссылка на PR:
https://github.com/PriestSexist/java-explore-with-me/pull/4
