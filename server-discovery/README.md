# Introduction 
As the name of this service suggests, it is a Service Registration and Discovery. Client-side service discovery allows services to find and communicate with each other without hard-coding the hostname and port. The only ‘fixed point’ in such an architecture is the service registry, with which each service has to register.

# Getting Started
This service must be run before running other Kara Signal services so that other microservices can register themselves in this service.

To run this service, you have a simple task ahead of you, just specify the server port in the "src/main/resources/application.properties" file. It is set to "8761" by default. If this port is occupied in your system, choose another port.
# Build and Run
You need Java 17 and Maven 3 to build and run. You can build the service using the following command:

`.\mvnw clean package`

After building and testing, run the service using the following command:

`.\mvnw spring-boot:run`
Or
`java -jar target/server-discovery-0.0.1-SNAPSHOT.jar`

> It should be noted that in order to run other Karasignal services, the Bytesy host and the port related to this service must be set in other services so that they can register themselves in this discovery service.

After running all the services, you should see the instances by entering "http://localhost:8761/".

