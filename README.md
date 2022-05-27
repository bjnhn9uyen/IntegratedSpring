# Taco Cloud modules

The multi-module Maven project is made up of the following modules:

• tacocloud-api : The REST API

• tacocloud-data : The persistence module

• tacocloud-domain : The domain types

• tacocloud-kitchen : An application to be run in the Taco Cloud kitchen that will receive orders for kitchen staff to prepare.

• tacocloud-messaging-jms : The Taco Cloud messaging module that sends messages using JMS.

• tacocloud-messaging-kafka : The Taco Cloud messaging module that sends messages using Kafka.

• tacocloud-messaging-rabbitmq : The Taco Cloud messaging module that sends messages using RabbitMQ.

• tacocloud-restclient : Client code that consumes the API exposed from tacocloud-api.

• tacocloud-security : The security module (security isn’t working yet, so there’s no login page. Likewise, there’s not yet any way to register.)

• tacocloud-ui : A Typescript Angular UI

• tacocloud-web : The web module

• tacocloud : The main module that pulls the other modules together and provides the Spring Boot main class.

The tacocloud-restclient module, while part of the Maven multi-module build, is otherwise separate from the rest of the Taco Cloud application. It contains sample code that demonstrates how to use RestTemplate and Traverson to consume the APIs exposed by the Taco Cloud application.

While the Taco Cloud application has been built and is running, you can run the client application. 
Review the source code in the tacocloud-restclient folder as you run the client to understand what is being emitted to the console.
