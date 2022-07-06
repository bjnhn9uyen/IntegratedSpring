# Taco Cloud modules

The multi-module Maven project is made up of the following modules:

• tacocloud-api : The REST API

• tacocloud-data : The persistence module

• tacocloud-domain : The domain types

• tacocloud-security : The security module (security isn’t working yet, so there’s no login page. Likewise, there’s not yet any way to register.)

• tacocloud-web : The web module

• tacocloud-ui : A Typescript Angular UI (chapter 6 - integrating with Angular)

• tacocloud-restclient : Client code that consumes the API exposed from tacocloud-api (chapter 7 - integrating with REST clients)

• tacocloud-kitchen : An application to be run in the Taco Cloud kitchen that will receive orders for kitchen staff to prepare (chapter 8 - integrating with Taco cloud kitchens application using messaging API)

• tacocloud-messaging-jms : The Taco Cloud messaging module that sends messages using JMS (chapter 8  - integrating with Taco cloud kitchens application using messaging API)

• tacocloud-messaging-kafka : The Taco Cloud messaging module that sends messages using Kafka (chapter 8  - integrating with Taco cloud kitchens application using messaging API)

• tacocloud-messaging-rabbitmq : The Taco Cloud messaging module that sends messages using RabbitMQ (chapter 8  - integrating with Taco cloud kitchens application using messaging API)

• tacocloud : The main module that pulls the other modules together and provides the Spring Boot main class

In order to use the email integration module, you will need to edit the src/main/resources/application/yml file in the tacocloud-email module, providing the configuration for an email server of your choosing. The values configured in there now are just placeholders and will not work.

The tacocloud-restclient module, while part of the Maven multi-module build, is otherwise separate from the rest of the Taco Cloud application. It contains sample code that demonstrates how to use RestTemplate and Traverson to consume the APIs exposed by the Taco Cloud application.

While the Taco Cloud application has been built and is running, you can run the client application. 
Review the source code in the tacocloud-restclient folder as you run the client to understand what is being emitted to the console.
