## About
This service is for sending notifications to user email and telegram.
## Endpoints
This service has to endpoints to send email and telegram notifications
## Properties
server.port=8585 <br>
spring.mail.username= your email address <br>
spring.mail.password= a generated password for your email address<br>
telegram.bot.api.token= a telegram bot token <br>
telegram.bot.api.send-message=https://api.telegram.org/bot${telegram.bot.api.token}/sendMessage?chat_id=%s&parse_mode=Markdown&text=%s
## Postman collection

