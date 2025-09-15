# Detailed Migration Plan for `iiiexams.org`

## 1. Introduction

This document provides a granular, task-oriented plan for migrating the legacy `.NET` application (`iiiexams.org`) to a new Java application based on Struts 2, Spring, and Tiles. It expands on the initial "Divide and Conquer" strategy by breaking down each implementation phase into detailed sub-tasks.

The core challenge of this migration is that the majority of the business logic resides in **database stored procedures**. Our strategy is centered around extracting this logic and re-implementing it in a proper Java service layer.

## 2. Overall Strategy

1.  **Phase 1: Foundation Setup:** Build the foundational skeleton of the new Java application. This is a prerequisite for all subsequent development.
2.  **Phase 2: Unit-based Implementation:** Tackle the application's functionality unit by unit. This allows for parallel development.

## 3. Phase 1: Foundation Setup (Detailed Sub-tasks)

This phase must be completed before work on the functional units can begin.

*   **Task 1: Java Project Setup**
    *   1.1. Initialize a new Maven project in your IDE.
    *   1.2. Create the standard Maven directory structure: `src/main/java`, `src/main/resources`, `src/main/webapp`.
    *   1.3. Create the `pom.xml` file.

*   **Task 2: Dependency Management (`pom.xml`)**
    *   2.1. Add dependencies for **Struts 2**: `struts2-core`, `struts2-spring-plugin`, `struts2-tiles-plugin`.
    *   2.2. Add dependencies for **Spring Framework**: `spring-core`, `spring-context`, `spring-beans`, `spring-web`, `spring-jdbc`, `spring-tx`.
    *   2.3. Add dependency for **Apache Tiles**: `tiles-core`, `tiles-jsp`.
    *   2.4. Add **Database Drivers**: `mssql-jdbc` (for SQL Server) and `ojdbc` (for Oracle).
    *   2.5. Add dependency for **Logging**: `log4j-core`, `log4j-api`.
    *   2.6. Add dependency for a **Connection Pool**: `HikariCP`.
    *   2.7. Add dependency for **Jakarta Servlet API** (version 6.0.0).

*   **Task 3: Core Framework Configuration**
    *   3.1. **`web.xml`**: In `src/main/webapp/WEB-INF/`, create the `web.xml` file. Configure the `StrutsPrepareAndExecuteFilter` and the Spring `ContextLoaderListener`.
    *   3.2. **`struts.xml`**: In `src/main/resources/`, create the `struts.xml` file. Define the basic package structure and result types (e.g., for Tiles).
    *   3.3. **`tiles.xml`**: In `src/main/webapp/WEB-INF/`, create the `tiles.xml` file. Define a base layout with attributes for header, body, and footer.
    *   3.4. **`SpringBeans.xml`**: In `src/main/resources/`, create the main Spring configuration file. This file will contain all bean definitions.

*   **Task 4: Database Configuration**
    *   4.1. In `SpringBeans.xml`, configure a `DataSource` bean for each database connection string found in the `.NET` `Web.config`.
    *   4.2. For each `DataSource`, configure a corresponding `JdbcTemplate` bean.
    *   4.3. Configure a `DataSourceTransactionManager` bean.

*   **Task 5: Base Classes and Utilities**
    *   5.1. Create a `BaseDao` class with a `JdbcTemplate` instance.
    *   5.2. Create a `log4j2.xml` file in `src/main/resources/` with a basic configuration for console and file logging.
    *   5.3. Create a `QueryConstants.java` interface in a `util` package to hold all SQL queries.

## 4. Phase 2: Unit-based Implementation (Detailed Sub-tasks)

---

### Unit 1: User Management

*   **.NET Components:** `UsersController.cs`, `IIIBL/Users.cs`, `IIIDL/Users.cs`

*   **Sub-tasks:**
    1.  **Analyze Stored Procedures:**
        *   Identify all stored procedures related to user management by examining `IIIDL/Users.cs`.
        *   For each stored procedure (e.g., `sp_get_user_details`), document its purpose, input/output parameters, and the business logic it contains.
    2.  **Create Model:**
        *   Create a `User.java` POJO with fields corresponding to the user table in the database.
    3.  **Create DAO:**
        *   Create a `UserDao.java` interface with method signatures (e.g., `findUserByUsername`, `updatePassword`).
        *   Create a `UserDaoImpl.java` class that extends `BaseDao` and implements `UserDao`.
        *   Add the SQL queries for user management to `QueryConstants.java`.
        *   Implement the methods in `UserDaoImpl` using `JdbcTemplate` and a `RowMapper` to map the results to `User` objects.
    4.  **Create Service:**
        *   Create a `UserService.java` interface with method signatures for business logic (e.g., `authenticate`, `changePassword`).
        *   Create a `UserServiceImpl.java` class that implements `UserService`.
        *   In `UserServiceImpl`, re-implement the business logic from the stored procedures, calling the `UserDao` for database access.
        *   In `SpringBeans.xml`, define the `userServiceImpl` bean and wrap it in a `TransactionProxyFactoryBean` to manage transactions.
    5.  **Create Action:**
        *   Create a `LoginAction.java` class that extends `ActionSupport` and implements `ModelDriven<User>`.
        *   Inject the `UserService` into the `LoginAction` via `SpringBeans.xml`.
        *   Implement the `execute()` method to handle the login logic.
        *   In `struts.xml`, define the `loginAction` and its results (e.g., `success`, `error`).
    6.  **Create Views:**
        *   Create `login.jsp` and `changePassword.jsp`.
        *   In `tiles.xml`, define the views for `login` and `changePassword`, extending the base layout.

---

### Unit 2: URN Management

*   **.NET Components:** `CandidatesController.cs`, `IIIBL/URN.cs`, `IIIDL/URN.cs`, `IIIDL/URN2.cs`

*   **Sub-tasks:**
    1.  **Analyze Stored Procedures:**
        *   This is the most complex unit. Thoroughly analyze and document the logic in `STP_LIC_SaveSponsorshipForm_New`, `sp_validate_PAN`, and other related stored procedures.
    2.  **Create Models:**
        *   Create `Candidate.java`, `Application.java`, and other necessary model classes.
    3.  **Create DAO:**
        *   Create a `UrnDao.java` interface.
        *   Create a `UrnDaoImpl.java` implementation.
        *   Add all URN-related SQL queries to `QueryConstants.java`.
    4.  **Create Service:**
        *   Create a `UrnService.java` interface.
        *   Create a `UrnServiceImpl.java` to house the complex business logic for URN creation, validation, and modification.
        *   Configure the `UrnService` for transaction management in `SpringBeans.xml`.
    5.  **Create Action:**
        *   Create a `CandidateAction.java` to handle all URN-related web requests.
        *   Use the `ModelDriven` approach to map form data to your model classes.
    6.  **Create Views:**
        *   Create the JSP pages for URN creation, modification, and lookup.

---

### Unit 3: Payments

*   **.NET Components:** `PaymentsController.cs`, `PayTM.cs`, `TPSL.cs`

*   **Sub-tasks:**
    1.  **Analyze Payment Gateway Integration:**
        *   Deconstruct the logic in `PayTM.cs` and `TPSL.cs` to understand how the application interacts with the payment gateways.
        *   Document the request/response formats and the configuration parameters from `Web.config`.
    2.  **Create Models:**
        *   Create `Payment.java` and `Transaction.java` model classes.
    3.  **Create DAO:**
        *   Create a `PaymentDao.java` interface and implementation to save and update payment information.
    4.  **Create Service:**
        *   Create a `PaymentService.java` to handle the payment processing logic, including interacting with the payment gateway APIs.
    5.  **Create Action:**
        *   Create a `PaymentAction.java` to handle the payment requests and responses.
    6.  **Create Views:**
        *   Create the JSP pages for the payment process.

---

### Unit 4: Reporting

*   **.NET Components:** `ReportsController.cs`, `ReportsController2.cs`, `IIIBL/ExamReports.cs`

*   **Sub-tasks:**
    1.  **Identify Reports:**
        *   List all the reports generated by the application by examining the `ReportsController` methods.
    2.  **Integrate JasperReports:**
        *   Add the `struts2-jasperreports-plugin` dependency to `pom.xml`.
        *   Define the `jasper` result type in `struts.xml`.
    3.  **Create Report Templates:**
        *   For each report, create a `.jrxml` template file.
    4.  **Create Service:**
        *   Create a `ReportService.java` that fetches the data needed for each report.
    5.  **Create Action:**
        *   Create a `ReportAction.java`.
        *   For each report, create a method that calls the `ReportService` to get the data and then returns a `jasper` result, passing the data to the report template.

---

### Other Units

This same detailed process should be repeated for all other logical units of the application, including:

*   **Batch Management** (`BatchesController.cs`)
*   **Branch Management** (`BranchesController.cs`)
*   **Scheduling** (`SchedulerController.cs`)
*   **Master Data Management** (e.g., `DPRangeController.cs`, `ExamCenters.cs`)
*   **Notifications** (`Notification.cs`)
