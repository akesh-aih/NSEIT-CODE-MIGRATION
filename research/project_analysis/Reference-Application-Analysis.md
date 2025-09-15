# Reference Application Analysis: MRBPCW

## 1. Project Overview

This document provides a detailed analysis of the `MRBPCW` Java web application. The application is a monolithic, server-side rendered web application built on a classic Java EE stack, heavily utilizing the Spring and Struts 2 frameworks. It appears to be an online examination or registration portal, with features for candidates, agencies, and administrators.

The analysis was conducted by examining the project's file structure, configuration files, and dependencies. No build automation tool like Maven or Gradle is used; dependencies are managed manually.

## 2. Project Structure

The project follows a fairly standard structure for a legacy Java web application developed in an IDE like Eclipse.

```
MRBPCW/
├── src/
│   ├── com/nseit/...  (Java source code)
│   ├── SpringBeans.xml
│   ├── struts.xml
│   ├── log4j2.xml
│   ├── global.properties
│   └── ...
├── WebContent/
│   ├── WEB-INF/
│   │   ├── lib/ (JAR dependencies)
│   │   ├── web.xml
│   │   ├── tiles.xml
│   │   ├── classes/ (Compiled Java classes - not in source)
│   │   ├── admin/
│   │   ├── candidate/
│   │   ├── agency/
│   │   └── ... (JSP files and other resources)
│   ├── CSS/
│   ├── js/
│   └── ...
├── resource/
├── test/
└── ...
```

*   **`src/`**: Contains the Java source code and main application configuration files.
*   **`WebContent/`**: The web application root, containing JSPs, static assets, and the `WEB-INF` directory.
*   **`WebContent/WEB-INF/lib/`**: Contains all the JAR files for the project's dependencies.
*   **`resource/`**: Contains additional resources.
*   **`test/`**: Likely contains test code, although it appears to be empty.

## 3. Technology Stack

### Backend

| Category          | Technology                                       | Version(s) |
| ----------------- | ------------------------------------------------ | ---------- |
| **Core Framework**  | Spring Framework                                 | 5.3.23     |
| **Web Framework**   | Apache Struts 2                                  | 6.1.1      |
| **Database**        | Oracle / PostgreSQL                              | -          |
| **Data Access**     | Spring JDBC (`JdbcTemplate`)                     | -          |
| **Reporting**       | JasperReports                                    | 5.5.1      |
| **Logging**         | Apache Log4j 2                                   | 2.19.0     |
| **Email**           | JavaMail                                         | 1.4.4      |
| **JSON Processing** | Jackson, Gson                                    | -          |
| **XML Processing**  | XStream                                          | 1.4.19     |
| **Scheduling**      | Quartz                                           | 1.8.6      |

### Frontend

| Category         | Technology      | Version(s) |
| ---------------- | --------------- | ---------- |
| **View**           | JSP             | -          |
| **Templating**     | Apache Tiles    | 3.0.8      |
| **JavaScript**     | jQuery          | 3.5.1      |

### Application Server

*   **Apache Tomcat** (inferred from `server.xml` and `context.xml`)

## 4. Architecture

The application follows a classic, layered Model-View-Controller (MVC) architecture.

*   **Model:** The model is represented by the Java beans in the `com.nseit.generic.models` package.
*   **View:** The view layer is composed of JSPs, which are assembled into layouts using Apache Tiles.
*   **Controller:** The controller layer is implemented using Struts 2 Actions.

The backend is further divided into a service layer and a data access layer (DAO).

```
+-------------------+
|   Browser         |
+-------------------+
        |
+-------------------+
|   Apache Tomcat   |
+-------------------+
        |
+-------------------+
|   Struts 2 (Actions)|  <-- Presentation Layer
+-------------------+
        |
+-------------------+
|   Spring (Services) |  <-- Service Layer
+-------------------+
        |
+-------------------+
| Spring JDBC (DAOs)|  <-- Data Access Layer
+-------------------+
        |
+-------------------+
|   Database        |
| (PostgreSQL/Oracle)|
+-------------------+
```

## 5. Configuration

The application's behavior is defined by several XML configuration files.

### `web.xml`

*   **Entry Point:** Defines the `StrutsPrepareAndExecuteFilter` as the main entry point for all requests matching `*.action`.
*   **Spring Integration:** Configures the `ContextLoaderListener` to load the Spring context from `SpringBeans.xml`.
*   **Tiles Integration:** Configures the `StrutsTilesListener` and points to `tiles.xml` for layout definitions.
*   **Servlets:** Defines several custom servlets for tasks like image generation and captcha creation.

### `SpringBeans.xml`

*   **Bean Definitions:** Defines all the application's beans, including actions, services, and DAOs.
*   **Dependency Injection:** Wires the application's components together using dependency injection.
*   **Transaction Management:** Configures declarative transaction management using `TransactionProxyFactoryBean`.
*   **DataSources:** Defines JNDI lookups for the application's datasources.
*   **Bean Scope:** **Crucially, all beans are defined with `scope="prototype"`.** This means a new instance of each bean is created every time it is requested. This is a non-standard and important design decision.

### `struts.xml`

*   **Action Mappings:** Maps incoming requests to Struts 2 actions. It uses a wildcard mapping (`ActionName_*`) to invoke methods on the action classes.
*   **Spring Integration:** Sets the `struts.objectFactory` to `StrutsSpringObjectFactory` to allow Spring to manage Struts actions.
*   **Result Types:** Defines custom result types for Tiles (`tiles`) and JasperReports (`jasper`).
*   **Interceptors:** Defines a custom interceptor stack (`genericDefaultStack`) that includes interceptors for session management, login, and exception handling.
*   **Global Results:** Defines global results for common cases like `notloggedin` and `errorPage`.

### `tiles.xml`

*   **Layouts:** Defines the base layouts for the application's UI.
*   **Views:** Defines the different views of the application by extending the base layouts and specifying the JSP for the body content.

## 6. Key Components and Conventions

*   **Actions (Struts 2):**
    *   Located in `com.nseit.generic.action`.
    *   Managed by Spring.
    *   Use wildcard method invocation.
    *   Delegate business logic to the service layer.
*   **Services (Spring):**
    *   Located in `com.nseit.generic.service.impl`.
    *   Contain the application's business logic.
    *   Wrapped in transactional proxies.
    *   Depend on DAOs to interact with the database.
*   **DAOs (Spring JDBC):**
    *   Located in `com.nseit.generic.dao.impl`.
    *   Use `JdbcTemplate` to perform database operations.
    *   Injected with a `DataSource`.
*   **Models:**
    *   Located in `com.nseit.generic.models`.
    *   Simple Java beans (POJOs) that represent the application's data.

## 7. Data Model

*   The application is configured to connect to either an Oracle or PostgreSQL database.
*   The presence of a `POSTGRESS-DUMP` directory suggests that PostgreSQL is the primary or development database.
*   The data access layer is built with `JdbcTemplate`, meaning there is no ORM framework like Hibernate in use. SQL queries are likely embedded in the DAO classes.

## 8. UI/Frontend

*   The UI is built with JSPs.
*   Apache Tiles is used for templating, allowing for reusable layouts.
*   jQuery is used for client-side scripting.
*   The overall UI is likely a traditional, server-rendered multi-page application.

## 9. Migration Considerations

*   **Dependency Management:** The lack of a build automation tool like Maven or Gradle is a major issue. The first step in any migration should be to identify all the JARs and create a `pom.xml` or `build.gradle` file.
*   **Prototype Scope:** The use of `scope="prototype"` for all Spring beans is unusual and should be investigated. It may have been done to avoid thread-safety issues, but it can have a significant performance impact. A modern Spring application would typically use singleton-scoped beans and manage state carefully.
*   **Struts 2 to Spring MVC:** Migrating from Struts 2 to Spring MVC would be a significant undertaking, but it would modernize the web layer and consolidate the stack.
*   **DAO Layer:** The DAO layer is based on `JdbcTemplate`. Migrating to a more modern data access technology like Spring Data JPA would reduce boilerplate code and improve developer productivity.
*   **Configuration:** The XML-based configuration could be migrated to Java-based configuration, which is more type-safe and easier to refactor.
