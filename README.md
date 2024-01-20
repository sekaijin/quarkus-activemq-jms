# Quarkus ActiveMQ JMS Extension

An extension which facilitates use of the Openwire JMS client from [Apache ActiveMQ](https://activemq.apache.org/components/classic/) as part of a [Quarkus](https://quarkus.io) application, including those using native executable builds.

Use JMS APIs with Openwire 1.0 servers such as ActiveMQ 5 and more.

### Sample Usage

See the [quarkus-activemq-jms-quickstart](https://github.com/amqphub/quarkus-qpid-jms-quickstart/) repository for sample application usage of the extension.

### Overview

To use the extension, add the `io.quarkiverse.activemq-jms:quarkus-activemq-jms` module as a dependency in your project, e.g:

    <dependency>
        <groupId>io.quarkiverse.activemq-jms</groupId>
        <artifactId>quarkus-activemq-jms</artifactId>
    </dependency>

The client can then be utilised though dependency injection of a JMS ConnectionFactory, e.g:

    @Inject
    ConnectionFactory connectionFactory;

### Configuration

The connection factory configuration is controlled using runtime config properties, e.g in your `application.properties` file:

| Config Property               | Description                             | Default                 |
| ----------------------------- | --------------------------------------- | ----------------------- |
| quarkus.activemq-jms.url      | Connection URL for the injected factory | "tcp://localhost:61616" |
| quarkus.activemq-jms.username | Optional username to set on the factory |                         |
| quarkus.activemq-jms.password | Optional password to set on the factory |                         |
| quarkus.activemq-jms.wrap     | Allow factory to be wrapped (see below) | false                   |

For full details of the client URL and its related options, consult the configuration documentation on the [Apache ActiveMQ](https://activemq.apache.org/connection-configuration-uri) website.

##### Factory wrapping, e.g. Pooling.

The `quarkus.activemq-jms.wrap` setting can allow the ActiveMQ JMS extension to apply a ConnectionFactory wrapper supplied by another extension to the injected ConnectionFactory. For example, setting this value to true and adding an additional dependency on [quarkus-pooled-jms](https://github.com/quarkiverse/quarkus-pooled-jms) to your application allows quarkus-pooled-jms to provide pooling for the injected ConnectionFactory.
