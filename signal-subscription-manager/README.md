# Introduction 
The Signal Subscription Management service is a service for managing the user subscriptions’ info. It stores the users’ subscription information in the database tables and provides some APIs to other services to access this database info.
All of the markets and available assets are stored in this database and other services use this service to get global information about assets and markets.

> Java version 17
> Postgresql 8 and above 

> Dashboard API uses this service 
> Assets_Live_Price service use this service

# Getting Started
1. Before starting this service, you should run the PostgreSQL and restore the UserSubscriptionDB.sql file.
   This service connects to PostgreSQL with the 'Developer' user name and following configuration:
    - URL : "jdbc:postgresql://localhost:5432/FinitXListOfSignalSubscription"
    - UserName : "Developer"
    - PassWord : "123456789"
2. Then you can run the service. It would be launched on 8091 port number.

# Properties:
    server.port = 8091

# Configs:
    DB configuration path: com.finitX.karasignal.constant.DBConfig

# Build and Test
TODO: Describe and show how to build your code and run the tests. 
