## About
This service is for user registration and login that uses JWT for authentication.

## Endpoints
There two types of endpoints that can be used in this service:
- Auth endpoints
- User endpoints

To use auth endpoints you must uncomment the following line in the SecurityConfiguration class.
``
.requestMatchers("/api/v*/auth/**").permitAll()
.anyRequest().authenticated()
``
and then comment the other config.

## Properties
server.port=8080

spring.datasource.url= your mysql database url
spring.datasource.username= your mysql database username
spring.datasource.password= your mysql database password

jwt.token.secret= a random generated token

spring.mail.username= your email address
spring.mail.password= a generated password for your email address

mail.service.message.verification.link=http://localhost:8080/api/v1/auth/confirmation?token=%s
mail.service.message.verification.text=To confirm your account, please click here:\r\n${mail.service.message.verification.link}

mail.service.message.password-reset.link=http://localhost:8080/api/v1/auth/reset-password
mail.service.message.password-reset.text=To reset your password, please click here:\r\n${mail.service.message.password-reset.link}\r\nYour token:%s

## Postman collection
https://www.postman.com/cryosat-saganist-35224783/workspace/public/collection/19780121-1d802e0e-db1c-4e33-b58f-1b272b4a5dea?action=share&creator=19780121
