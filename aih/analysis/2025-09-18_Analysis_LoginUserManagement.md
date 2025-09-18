
# Analysis for Login and User Management Migration

**Date:** 2025-09-18

This document outlines the analysis of the .NET application's user authentication and management functionality, which will guide the migration to the new Java-based application.

## 1. .NET Component Analysis

### 1.1. Controllers (`IIIRegistrationPortal2/Controllers/`)

*   **`UsersController.cs`**: This is the primary controller for user actions.
    *   **`Login(String UserId, String Password)`**: Handles the login request. It receives the username and password, encrypts the password using `LoginEncryptor`, and calls the business layer. Upon success, it populates the `PortalSession` and sets an authentication cookie.
    *   **`Logout()`**: Clears the session and signs the user out.
    *   **`ChangePassword(...)`**: Handles password change requests.
    *   **`ResetPassword(...)`**: Handles password reset requests.
    *   **`SaveUser(...)`**: Handles creating and updating user profiles (administrative function).
*   **`AccountsController.cs`**: This controller is **not** related to user login but handles financial aspects like "Credit Balance" and "Vouchers".

### 1.2. Business Logic Layer (`IIIBL/`)

*   **`Users.cs`**: This class acts as a simple pass-through layer. It receives calls from the `UsersController` and forwards them directly to the corresponding methods in the `IIIDL.Users` class. No significant business logic is present here.

### 1.3. Data Access Layer (`IIIDL/`)

*   **`Users.cs`**: This class contains the ADO.NET code to interact with the database. It builds `SqlParameter` arrays and executes stored procedures. This file is the key to understanding the database interaction.

## 2. Stored Procedure Analysis

Based on the `IIIDL.Users.cs` file, the following stored procedures are central to this module:

*   **`STP_CMN_ValidateLogin_ForExamPortal_New`**: The core procedure for validating user credentials.
    *   **Inputs**: `@varLoginID`, `@varPassword` (encrypted).
    *   **Output**: `@varResult` (a status string like `SUCCESS`, `INVALID_PWD`, etc.) and a `DataSet` containing full user details on success.
    *   **Logic**: Performs a series of checks (user exists, not suspended, password matches, active status, etc.) before returning the user's data.

*   **`SP_SAVE_User`**: Used to create and update users.
    *   **Inputs**: User details (LoginID, Name, Password, Role, etc.).
    *   **Logic**: Inserts a new record into `TblMstUser` and `TblUserRoleMapping` or updates existing ones.

*   **`STP_CMN_ChangePassword_New`**: Used to change a user's password.
    *   **Inputs**: `@varLoginID`, `@varPassword` (old encrypted password), `@varNewPassword` (new encrypted password).
    *   **Logic**: Validates the old password and updates the `varPassword` field in `TblMstUser`.

*   **`STP_GetMenuPermission_New2`**: Fetches the menu items a user is authorized to see.
    *   **Input**: `@IntSearchID` (which is the `UserID`).
    *   **Output**: A `DataSet` containing the menu structure and permissions.

## 3. Password Encryption Analysis

*   **File**: `IIIRegistrationPortal2/Classes/LoginEncryptor.cs`
*   **Algorithm**: Rijndael (AES) with a 256-bit key.
*   **Mode**: CBC (Cipher Block Chaining).
*   **Padding**: The .NET default is PKCS7, which is compatible with `PKCS5Padding` in Java.
*   **Key Derivation**: Uses the .NET `PasswordDeriveBytes` class (a PBKDF1 implementation).
*   **Parameters (Hardcoded)**:
    *   **Passphrase**: `"Pas5pr@se"`
    *   **Salt**: `"s@1tValue"` (as ASCII bytes)
    *   **Hash Algorithm**: `SHA1`
    *   **Iterations**: `2`
    *   **Initialization Vector (IV)**: `"@1B2c3D4e5F6g7H8"` (16 ASCII bytes)
*   **Output Format**: The final encrypted byte array is Base64 encoded.

**Conclusion for Java Implementation**: The encryption scheme is highly specific and must be replicated precisely in Java using the `javax.crypto` libraries. A standard password hashing library will not work for validating existing passwords.

## 4. Java Migration Plan

1.  **Model (`com.nseit.users.model.User.java`)**: Create a POJO to hold all user-related data returned from `STP_CMN_ValidateLogin_ForExamPortal_New`. This will include user ID, name, role, insurer details, etc.
2.  **Encryption Utility (`com.nseit.generic.util.LoginEncryptor.java`)**: Create a new class to replicate the .NET Rijndael/AES encryption logic.
3.  **DAO (`com.nseit.users.dao.UserDao.java` & Impl)**:
    *   Create a method `findByUsername(String username)` to fetch basic user details for validation.
    *   Create a method `getLoginDetails(String username)` to execute the main login query and populate the full `User` model.
    *   Add methods for `saveUser`, `changePassword`, etc., corresponding to the other stored procedures.
4.  **Service (`com.nseit.users.service.UserService.java` & Impl)**:
    *   Implement the `authenticate(String username, String password)` method. This method will:
        *   Call the DAO to get user data.
        *   Use the `LoginEncryptor` to encrypt the provided password.
        *   Perform the validation checks (active, suspended, etc.) in Java.
        *   If valid, call the DAO to get the full user details.
        *   Log the login attempt.
5.  **Action (`com.nseit.users.action.LoginAction.java`)**:
    *   Create a Struts action to handle the `/login` request.
    *   It will be `ModelDriven` with the `User` model.
    *   It will call the `UserService.authenticate` method and place the resulting `User` object into the session.
    *   It will return `SUCCESS` or `ERROR` based on the outcome.
6.  **View (`login.jsp`)**: Create a JSP page with a login form.
7.  **Configuration**:
    *   Add the new beans for the action, service, and DAO to `SpringBeans.xml`.
    *   Add the action mapping to `struts.xml`.
    *   Add all required SQL queries to a new `UserQueries.java` interface in the `com.nseit.users.queries` package.
