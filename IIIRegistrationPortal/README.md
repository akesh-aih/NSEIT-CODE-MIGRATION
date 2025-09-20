# IIIRegistrationPortal

This is a Java-based web application for the III Registration Portal.

## Prerequisites

*   Java 21
*   Maven 3.x
*   Tomcat 10.1.x
*   PostgreSQL 16.x

## Build

To build the project, run the following command from the root directory of the project:

```bash
mvn clean install
```

This will generate a `iiiexams.war` file in the `target` directory.

## Deploy

To deploy the application, copy the `iiiexams.war` file to the `webapps` directory of your Tomcat installation.

## Health Check

To check the health of the application, access the following URL in your browser:

```
http://localhost:8080/iiiexams/health.action
```
