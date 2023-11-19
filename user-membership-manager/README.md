## About
This service is for user authentication based on JWT and also provides basic information about the users.

## Requirements
- Java 17
- Spring Boot 3.1.0
- MySQL 8.0.34

## Configurations
- Database
    - spring.datasource.url= your mysql database url
    - spring.datasource.username= your mysql database username
    - spring.datasource.password= your mysql database password
- Server
  - server.port= 8080
- JWT
  - jwt.token.secret= a random generated token
- Mail
  - spring.mail.username= your email address
  - spring.mail.password= a generated password for your email address
  - mail.service.message.verification.link=http://localhost:8080/api/v1/auth/confirmation?token=%s
  - mail.service.message.password-reset.link=http://localhost:8080/api/v1/auth/reset-password

## Build And Run
Just use this command ```./mvnw spring-boot:run``` in maven.

## Endpoints
There two types of endpoints that can be used in this service:
- Auth endpoints
- User endpoints

| Name  | Type  | Endpoint  |  Header |  Body | Param |
|--:|---|---|---|---|---|
|  sign up |  POST | /api/v1/auth/signup  |   | "fullName": "user", "email": "user@main.com", "phone": "09000001111", "password": "1234", "confirmPassword": "1234"  |
|  sign in | POST  |  /api/v1/auth/signin |   | "email": "user@mail.com", "password": "1234"  |
|  refresh token | POST  |  /api/v1/auth/token |  Authorization: Bearer Token |   |
|  forgot password | POST  | /api/v1/auth/forgot-password  |   |   | email|
| reset password  |  POST | /api/v1/auth/reset-password  |   |  "token": "7ed9fa35-5cd6-4ac4-9060-8423a9060e3f", "password": "12345", "confirmPassword": "12345" |
|  confirm email |  GET | /api/v1/auth/confirmation  |   |   | token |
|  me |  GET |  /api/v1/users/me |  Authorization: Bearer Token |   |

[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/19780121-1d802e0e-db1c-4e33-b58f-1b272b4a5dea?action=collection%2Ffork&source=rip_markdown&collection-url=entityId%3D19780121-1d802e0e-db1c-4e33-b58f-1b272b4a5dea%26entityType%3Dcollection%26workspaceId%3Dd33b0ecf-e77e-452b-ab49-cc8b994c38be)

## Services
  - User Dashboard API uses this service.
