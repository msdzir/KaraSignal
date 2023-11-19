## About

## Endpoints
This service has to endpoints to send email and telegram notifications
## Properties
server.port=8585 <br>
spring.mail.username= your email address <br>
spring.mail.password= a generated password for your email address<br>
telegram.bot.api.token= a telegram bot token <br>
telegram.bot.api.send-message=https://api.telegram.org/bot${telegram.bot.api.token}/sendMessage?chat_id=%s&parse_mode=Markdown&text=%s
## Postman collection

## About
This service is for sending notifications to user email and telegram.

## Requirements
- Java 17
- Spring Boot 3.1.0

## Configurations
- Server
    - server.port= 8585
- Mail
    - spring.mail.username= your email address
    - spring.mail.password= a generated password for your email address
    - mail.service.message.verification.link=http://localhost:8080/api/v1/auth/confirmation?token=%s
    - mail.service.message.password-reset.link=http://localhost:8080/api/v1/auth/reset-password
- Telegram
  - telegram.bot.api.token= a telegram bot token
    telegram.bot.api.send-message=https://api.telegram.org/bot${telegram.bot.api.token}/sendMessage?chat_id=%s&parse_mode=Markdown&text=%s

## Build And Run
Just use this command ```./mvnw spring-boot:run``` in maven.

## Endpoints
There two types of endpoints that can be used in this service:
- email
- telegram

|     Name | Type  | Endpoint                       |  Header | Body                                                                  | Param |
|---------:|---|--------------------------------|---|-----------------------------------------------------------------------|---|
|    email |  POST | /api/v1/notifications/email    |   | "to": "user@mail.com", "subject": "subject here", "text": "text here" |
| telegram | POST  | /api/v1/notifications/telegram |   | "to": "user@mail.com", "subject": "subject here", "text": "text here  |

## Services
- no service
