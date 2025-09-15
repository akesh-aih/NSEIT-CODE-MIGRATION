# Divide and Conquer: Migration Plan for `iiiexams.org`

## 1. Introduction

This document outlines a "divide and conquer" strategy for migrating the legacy `.NET` application (`iiiexams.org`) to a new Java application, following the client's required technology stack (Struts 2, Spring, Tiles, etc.) and conventions.

The core challenge of this migration is that the majority of the business logic resides in **database stored procedures**, not in the `.NET` code. Our strategy is therefore centered around extracting this logic and re-implementing it in a proper Java service layer.

## 2. Overall Strategy

We will follow a two-phased approach:

1.  **Phase 1: Foundation Setup:** First, we will build the foundational skeleton of the new Java application. This includes setting up the project structure, configuring the core frameworks (Spring, Struts, Tiles), and creating base classes and utilities. This phase can be done by one or two developers.
2.  **Phase 2: Unit-based Implementation:** Once the foundation is in place, we will tackle the application's functionality unit by unit. Each "unit" corresponds to a major feature of the application (e.g., User Management, URN Creation). This approach allows for parallel development, where different developers can work on different units simultaneously.

## 3. Phase 1: Foundation Setup

This phase must be completed before work on the functional units can begin.

*   **Task 1: Java Project Setup**
    *   Create a new Maven project.
    *   Create the `pom.xml` file and add all required dependencies (Struts 2, Spring, Tiles, JDBC drivers for SQL Server and Oracle, etc.).
    *   Create the standard directory structure (`src/main/java`, `src/main/resources`, `src/main/webapp`).

*   **Task 2: Core Framework Configuration**
    *   Create the `web.xml` file and configure the Struts 2 `StrutsPrepareAndExecuteFilter` and the Spring `ContextLoaderListener`.
    *   Create the `struts.xml` file for action mappings.
    *   Create the `tiles.xml` file for UI layouts.
    *   Create the main Spring configuration file (`SpringBeans.xml` or similar) to define the application's beans.

*   **Task 3: Database Configuration**
    *   In `SpringBeans.xml`, configure multiple `DataSource` beans to connect to the different SQL Server and Oracle databases identified in the `.NET` project's `Web.config`.
    *   Configure a `JdbcTemplate` bean for each `DataSource`.

*   **Task 4: Base Classes and Utilities**
    *   Create a `BaseDao` class that all other DAO classes will extend.
    *   Create a `BaseService` class.
    *   Set up a logging framework (Log4j 2) and create a `log4j2.xml` configuration file.
    *   Create a constants file (e.g., `QueryConstants.java`) to hold all SQL queries.

## 4. Phase 2: Unit-based Implementation

This phase involves breaking down the application into logical units and implementing them one by one. The following is a proposed breakdown based on the analysis of the `.NET` project.

---

### Unit 1: User Management

*   **.NET Components:**
    *   `Controllers/UsersController.cs`
    *   `IIIBL/Users.cs`
    *   `IIIDL/Users.cs`
*   **Key Stored Procedures:** (To be identified by analyzing `IIIDL/Users.cs`)
    *   `sp_get_user_details` (example name)
    *   `sp_authenticate_user` (example name)
    *   `sp_update_user_password` (example name)
*   **Java Implementation Tasks:**
    1.  **Analyze Stored Procedures:** Document the logic within the user-related stored procedures.
    2.  **Create Model:** Create a `User.java` model class to represent a user.
    3.  **Create DAO:**
        *   Create a `UserDao` interface.
        *   Create a `UserDaoImpl` that implements the interface and uses `JdbcTemplate` to execute the user-related SQL queries (which should be defined in `QueryConstants.java`).
    4.  **Create Service:**
        *   Create a `UserService` interface.
        *   Create a `UserServiceImpl` that re-implements the business logic from the stored procedures.
    5.  **Create Action:**
        *   Create a `UserAction` Struts 2 action class to handle user-related requests (login, logout, change password, etc.).
    6.  **Create Views:**
        *   Create the JSP pages for login, change password, etc.
        *   Define the views and layouts in `tiles.xml`.

---

### Unit 2: URN Management

*   **.NET Components:**
    *   `Controllers/CandidatesController.cs`
    *   `IIIBL/URN.cs`
    *   `IIIDL/URN.cs`, `IIIDL/URN2.cs`
*   **Key Stored Procedures:** (To be identified by analyzing `IIIDL/URN.cs`)
    *   `STP_LIC_SaveSponsorshipForm_New`
    *   `sp_get_urns_for_pan`
    *   `sp_validate_PAN`
    *   `sp_save_candidate_details2`
*   **Java Implementation Tasks:**
    1.  **Analyze Stored Procedures:** This is a critical unit. The logic for URN creation, validation, and modification is complex and spread across several stored procedures. This logic must be carefully documented.
    2.  **Create Models:** Create Java classes to represent a candidate, an application, and other related entities.
    3.  **Create DAO:** Create a `UrnDao` interface and implementation.
    4.  **Create Service:** Create a `UrnService` that contains the core business logic for URN management.
    5.  **Create Action:** Create a `CandidateAction` or `UrnAction` to handle all URN-related web requests.
    6.  **Create Views:** Create the JSP pages for URN creation, modification, and lookup.

---

### Unit 3: Payments

*   **.NET Components:**
    *   `Controllers/PaymentsController.cs`
    *   `Controllers/PayTM.cs`
    *   `Controllers/TPSL.cs`
*   **Key Stored Procedures:** (To be identified by analyzing the relevant DAL classes)
    *   `sp_update_batch_summary`
*   **Java Implementation Tasks:**
    1.  **Analyze Payment Gateway Integration:** The `.NET` project has code for integrating with multiple payment gateways. This logic needs to be understood and replicated.
    2.  **Create Models:** Create Java classes for payments, transactions, etc.
    3.  **Create DAO:** Create a `PaymentDao`.
    4.  **Create Service:** Create a `PaymentService` to handle payment processing.
    5.  **Create Action:** Create a `PaymentAction` to handle payment-related requests.
    6.  **Create Views:** Create the JSP pages for the payment process.

---

### Unit 4: Reporting

*   **.NET Components:**
    *   `Controllers/ReportsController.cs`
    *   `Controllers/ReportsController2.cs`
    *   `IIIBL/ExamReports.cs`
    *   `IIIBL/ReportsInfra.cs`
*   **Key Stored Procedures:** (To be identified from the DAL)
*   **Java Implementation Tasks:**
    1.  **Analyze Reports:** Identify all the reports generated by the application.
    2.  **Set up JasperReports:** Integrate JasperReports with the Java application.
    3.  **Create Report Templates:** Create `.jrxml` templates for each report.
    4.  **Create Service:** Create a `ReportService` that fetches the data for the reports.
    5.  **Create Action:** Create a `ReportAction` that calls the service and generates the reports using the JasperReports result type in Struts 2.

---

### Other Units

This same process should be repeated for all the other logical units of the application, including:

*   **Batch Management** (`BatchesController.cs`)
*   **Branch Management** (`BranchesController.cs`)
*   **Scheduling** (`SchedulerController.cs`)
*   **Master Data Management** (e.g., `DPRangeController.cs`, `ExamCenters.cs`)
*   **Notifications** (`Notification.cs`)

By breaking the project down into these manageable units, the development team can work in parallel and make steady, measurable progress towards completing the migration.
