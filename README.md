# KaraSginal

KaraSignal is a financial market Notification service that saves people from wasting time checking asset prices daily.

## Description

In the first step, this time service is designed for cryptocurrency markets and uses Binance APIs to access prices. We are trying to add other financial markets to the system in the next steps. Also, the project has a UI where users can choose their asset type and target by registering and logging in, so that the system sends them a notification on this target. The project is implemented with microservices architecture, the list of services is given below.

- **Financial Markets Signal:** This is for reading Assets Live Price from markets end point and caching the data.
- **Server Discovery:** Service Discover Registration for register name and IP address of all microservices.
- **Signal Notification Manager:** This service has to endpoints to send email and telegram notifications.
- **Signal Subscription Manager:** The Signal Subscription Management service is a service for managing the user subscriptions’ info.
- **User Dashboard (API):** This service is a middleware for interacting with user services and frontend.
- **User Dashboard (UI):** This repository contains all of the UI/UX of the project from login to dashboard, etc.
- **User Membership Manager:** This service is for user authentication based on JWT and also provides basic information about the users.

## Getting Started

### Dependencies

* All backend services are developed with Java and Spring Boot version 3 framework. The only service UI is developed with React and TypeScript framework.
* Java 17, 20
* Maven 3
* Redis
* MySQL 8 and PostgreSQL
* React
* TypeScript

### Installing

* To install, it is enough to download all the services. The program execution process is explained in the next section. Before running, please install all project dependencies, including Java and Maven.
* Before running, it is necessary to set configurations for each service, such as connection string, database, etc., which are explained in the README of each service separately.

### Executing program

The execution process of each service is listed separately in the README of that service. You should follow the path of running the services from the following, which service should be run first.
* First, you need to run Server Discovery. This service is like a DNS for hosting other microservices. Each microservice introduces itself to Server Discovery at the time of startup, as a result, other services can access its host and port to communicate with each other through the service name, which prevents hard-coding of the services' IP.
* You can run the rest of the services in any order, except for the User Dashboard (API) service, which must be run last.
* After running all the services, you can run the UI so that you can access the user interface of the program.

## Help

Please review the README of each service separately in case of problems. You may be able to find a solution there.

## Authors

Masoud Zare  
[msdzir@gmail.com](mailto:msdzir@gmail.com)

## Version History

* 0.1
    * Initial Release

## License

This project is licensed under the MIT License - see the LICENSE.md file for details
