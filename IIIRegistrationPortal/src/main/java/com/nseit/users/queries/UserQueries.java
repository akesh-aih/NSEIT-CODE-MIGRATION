package com.nseit.users.queries;
public interface UserQueries {

    String VALIDATE_LOGIN =
        "SELECT " +
        "    U.\"intUserID\", U.\"varUserLoginID\", U.\"varUserName\", U.\"varPassword\", U.\"bitIsActive\", U.\"bitIsLoggedIn\", " +
        "    COALESCE(U.\"sntIncorrectLoginAttempts\", 0) AS \"sntIncorrectLoginAttempts\", U.\"bitIsSuspended\", " +
        "    U.\"bitChgPwdOnNxtLogin\", R.\"sntRoleID\", COALESCE(R.\"varRoleName\", '') AS \"varRoleName\", " +
        "    COALESCE(R.\"role_code\", '') AS \"role_code\", " +
        "    COALESCE(I.\"intTblMstInsurerUserID\", D.\"intInsurerUserID\", A.\"intInsurerUserID\", PDPI.\"intTblMstInsurerUserID\", CU.\"CorporateUserID\", 0) AS \"intTblMstInsurerUserID\", " +
        "    COALESCE(I.\"varName\", D.\"varInsurerName\", A.\"varInsurerName\", PDPI.\"varName\", '') AS \"varInsurerName\", " +
        "    COALESCE(D.\"intDPUserID\", A.\"intDPUserID\", 0) AS \"intTblMstDPUserID\", " +
        "    COALESCE(D.\"varDPName\", A.\"varDPName\", '') AS \"varDPName\", " +
        "    COALESCE(A.\"intTblMstAgntCounselorUserID\", 0) AS \"intTblMstAgntCounselorUserID\", " +
        "    COALESCE(A.\"varCounselorName\", '') AS \"varCounselorName\", " +
        "    COALESCE(U.\"dtLastLoggedIn\", CURRENT_TIMESTAMP) AS \"dtLastLoggedIn\", " +
        "    COALESCE(AP.\"VcParamConstantValue\", D.\"InsurerType\", A.\"InsurerType\", APPDP.\"VcParamConstantValue\", '') AS \"InsurerType\", " +
        "    COALESCE(APN.\"VcParamConstantValue\", D.\"InsurerTypeNew\", A.\"InsurerTypeNew\", APPDP1.\"VcParamConstantValue\", CU.\"InsuranceTypeCode\", '') AS \"InsurerTypeNew\", " +
        "    U.\"varEmailID\", " +
        "    COALESCE(PDP.\"intTblMstPortabilityDPUserID\", 0) AS \"intTblMstPortabilityDPUserID\", " +
        "    COALESCE(PDP.\"varName\", '') AS \"varPortabilityDPName\", " +
        "    COALESCE(I.\"varInsurerID\", D.\"varInsurerID\", A.\"varInsurerID\", PDPI.\"varInsurerID\", '') AS \"varInsurerID\", " +
        "    0 AS \"CAid\", " +
        "    '' AS \"TopLevelUserLoginId\" " +
        "FROM \"TblMstUser\" U " +
        "INNER JOIN \"TblUserRoleMapping\" UR ON U.\"intUserID\" = UR.\"intUserID\" " +
        "INNER JOIN \"TblMstRole\" R ON UR.\"sntRoleID\" = R.\"sntRoleID\" " +
        "LEFT JOIN \"TblMstInsurerUser\" I ON I.\"intUserID\" = U.\"intUserID\" " +
        "LEFT JOIN \"_TblApplicationParameter\" AP ON AP.\"VcParamNameConstant\" = 'Insurer_Type' AND I.\"tntInsurerType\" = AP.\"BintParamConstantValue\" " +
        "LEFT JOIN \"_TblApplicationParameter\" APN ON APN.\"VcParamNameConstant\" = 'Insurer_Type_New' AND I.\"tntInsurerTypeNew\" = APN.\"BintParamConstantValue\" " +
        "LEFT JOIN ( " +
        "    SELECT T_D.\"intUserID\", T_D.\"intTblMstDPUserID\" AS \"intDPUserID\", T_D.\"varName\" AS \"varDPName\", " +
        "           T_I.\"intTblMstInsurerUserID\" AS \"intInsurerUserID\", T_I.\"varName\" AS \"varInsurerName\", " +
        "           AP.\"VcParamConstantValue\" AS \"InsurerType\", T_I.\"varInsurerID\", APN.\"VcParamConstantValue\" AS \"InsurerTypeNew\" " +
        "    FROM \"TblMstDPUser\" T_D " +
        "    INNER JOIN \"TblMstInsurerUser\" T_I ON T_D.\"intTblMstInsurerUserID\" = T_I.\"intTblMstInsurerUserID\" " +
        "    INNER JOIN \"_TblApplicationParameter\" AP ON AP.\"VcParamNameConstant\" = 'Insurer_Type' AND T_I.\"tntInsurerType\" = AP.\"BintParamConstantValue\" " +
        "    INNER JOIN \"_TblApplicationParameter\" APN ON APN.\"VcParamNameConstant\" = 'Insurer_Type_New' AND T_I.\"tntInsurerTypeNew\" = APN.\"BintParamConstantValue\" " +
        ") D ON D.\"intUserID\" = U.\"intUserID\" " +
        "LEFT JOIN ( " +
        "    SELECT T_A.\"intUserID\", T_A.\"intTblMstAgntCounselorUserID\", T_A.\"varName\" AS \"varCounselorName\", " +
        "           T_D.\"intTblMstDPUserID\" AS \"intDPUserID\", T_D.\"varName\" AS \"varDPName\", " +
        "           COALESCE(T_I.\"intTblMstInsurerUserID\", T_D_I.\"intTblMstInsurerUserID\") AS \"intInsurerUserID\", " +
        "           COALESCE(T_I.\"varName\", T_D_I.\"varName\") AS \"varInsurerName\", " +
        "           COALESCE(AP_I.\"VcParamConstantValue\", AP_D.\"VcParamConstantValue\") AS \"InsurerType\", " +
        "           COALESCE(T_I.\"varInsurerID\", T_D_I.\"varInsurerID\") AS \"varInsurerID\", " +
        "           COALESCE(AP_I1.\"VcParamConstantValue\", AP_D1.\"VcParamConstantValue\") AS \"InsurerTypeNew\" " +
        "    FROM \"TblMstAgentCounselorUser\" T_A " +
        "    LEFT JOIN \"TblMstDPUser\" T_D ON T_A.\"intTblMstDPUserID\" = T_D.\"intTblMstDPUserID\" " +
        "    LEFT JOIN \"TblMstInsurerUser\" T_D_I ON T_D.\"intTblMstInsurerUserID\" = T_D_I.\"intTblMstInsurerUserID\" " +
        "    LEFT JOIN \"_TblApplicationParameter\" AP_D ON AP_D.\"VcParamNameConstant\" = 'Insurer_Type' AND T_D_I.\"tntInsurerType\" = AP_D.\"BintParamConstantValue\" " +
        "    LEFT JOIN \"_TblApplicationParameter\" AP_D1 ON AP_D1.\"VcParamNameConstant\" = 'Insurer_Type_New' AND T_D_I.\"tntInsurerTypeNew\" = AP_D1.\"BintParamConstantValue\" " +
        "    LEFT JOIN \"TblMstInsurerUser\" T_I ON T_A.\"intTblMstInsurerUserID\" = T_I.\"intTblMstInsurerUserID\" " +
        "    LEFT JOIN \"_TblApplicationParameter\" AP_I ON AP_I.\"VcParamNameConstant\" = 'Insurer_Type' AND T_I.\"tntInsurerType\" = AP_I.\"BintParamConstantValue\" " +
        "    LEFT JOIN \"_TblApplicationParameter\" AP_I1 ON AP_I1.\"VcParamNameConstant\" = 'Insurer_Type_New' AND T_I.\"tntInsurerTypeNew\" = AP_I1.\"BintParamConstantValue\" " +
        ") A ON A.\"intUserID\" = U.\"intUserID\" " +
        "LEFT JOIN \"TblMstPortabilityDPUser\" PDP ON PDP.\"intUserID\" = U.\"intUserID\" " +
        "LEFT JOIN \"TblMstInsurerUser\" PDPI ON PDPI.\"intTblMstInsurerUserID\" = PDP.\"intTblMstInsurerUserID\" " +
        "LEFT JOIN \"_TblApplicationParameter\" APPDP ON APPDP.\"VcParamNameConstant\" = 'Insurer_Type' AND PDPI.\"tntInsurerType\" = APPDP.\"BintParamConstantValue\" " +
        "LEFT JOIN \"_TblApplicationParameter\" APPDP1 ON APPDP1.\"VcParamNameConstant\" = 'Insurer_Type_New' AND PDPI.\"tntInsurerTypeNew\" = APPDP1.\"BintParamConstantValue\" " +
        "LEFT JOIN \"TblMstCorporateUser\" CU ON CU.\"CorporateUserID\" = U.\"intUserID\" " +
        "WHERE U.\"varUserLoginID\" = ?";

    String INSERT_LOGIN_LOG =
        "INSERT INTO \"_TblUserLogin\" (\"intUserID\", \"varUserLoginID\", \"intCreatedBy\", \"dtCreatedOn\", \"dtLastLoggedIn\", \"bitIsLoggedIn\") " +
        "VALUES (?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, TRUE)";

    String UPDATE_USER_LOGIN_STATUS =
        "UPDATE \"TblMstUser\" SET \"bitIsLoggedIn\" = TRUE, \"sntIncorrectLoginAttempts\" = 0, \"dtLastLoggedIn\" = CURRENT_TIMESTAMP " +
        "WHERE \"varUserLoginID\" = ?";

    String FIND_USER_DETAILS_FOR_RESET =
        "SELECT " +
        "    U.\"intUserID\", U.\"varUserLoginID\", U.\"varUserName\", U.\"varPassword\", U.\"bitIsActive\", U.\"bitIsLoggedIn\", " +
        "    COALESCE(U.\"sntIncorrectLoginAttempts\", 0) AS \"sntIncorrectLoginAttempts\", U.\"bitIsSuspended\", " +
        "    U.\"bitChgPwdOnNxtLogin\", R.\"sntRoleID\", COALESCE(R.\"varRoleName\", '') AS \"varRoleName\", " +
        "    COALESCE(R.\"role_code\", '') AS \"role_code\", " +
        "    COALESCE(I.\"intTblMstInsurerUserID\", D.\"intInsurerUserID\", A.\"intInsurerUserID\", PDPI.\"intTblMstInsurerUserID\", CU.\"CorporateUserID\", 0) AS \"intTblMstInsurerUserID\", " +
        "    COALESCE(I.\"varName\", D.\"varInsurerName\", A.\"varInsurerName\", PDPI.\"varName\", '') AS \"varInsurerName\", " +
        "    COALESCE(D.\"intDPUserID\", A.\"intDPUserID\", 0) AS \"intTblMstDPUserID\", " +
        "    COALESCE(D.\"varDPName\", A.\"varDPName\", '') AS \"varDPName\", " +
        "    COALESCE(A.\"intTblMstAgntCounselorUserID\", 0) AS \"intTblMstAgntCounselorUserID\", " +
        "    COALESCE(A.\"varCounselorName\", '') AS \"varCounselorName\", " +
        "    COALESCE(U.\"dtLastLoggedIn\", CURRENT_TIMESTAMP) AS \"dtLastLoggedIn\", " +
        "    COALESCE(AP.\"VcParamConstantValue\", D.\"InsurerType\", A.\"InsurerType\", APPDP.\"VcParamConstantValue\", '') AS \"InsurerType\", " +
        "    COALESCE(APN.\"VcParamConstantValue\", D.\"InsurerTypeNew\", A.\"InsurerTypeNew\", APPDP1.\"VcParamConstantValue\", CU.\"InsuranceTypeCode\", '') AS \"InsurerTypeNew\", " +
        "    U.\"varEmailID\", " +
        "    COALESCE(PDP.\"intTblMstPortabilityDPUserID\", 0) AS \"intTblMstPortabilityDPUserID\", " +
        "    COALESCE(PDP.\"varName\", '') AS \"varPortabilityDPName\", " +
        "    COALESCE(I.\"varInsurerID\", D.\"varInsurerID\", A.\"varInsurerID\", PDPI.\"varInsurerID\", '') AS \"varInsurerID\", " +
        "    0 AS \"CAid\", " +
        "    '' AS \"TopLevelUserLoginId\" " +
        "FROM \"TblMstUser\" U " +
        "INNER JOIN \"TblUserRoleMapping\" UR ON U.\"intUserID\" = UR.\"intUserID\" " +
        "INNER JOIN \"TblMstRole\" R ON UR.\"sntRoleID\" = R.\"sntRoleID\" " +
        "LEFT JOIN \"TblMstInsurerUser\" I ON I.\"intUserID\" = U.\"intUserID\" " +
        "LEFT JOIN \"_TblApplicationParameter\" AP ON AP.\"VcParamNameConstant\" = 'Insurer_Type' AND I.\"tntInsurerType\" = AP.\"BintParamConstantValue\" " +
        "LEFT JOIN \"_TblApplicationParameter\" APN ON APN.\"VcParamNameConstant\" = 'Insurer_Type_New' AND I.\"tntInsurerTypeNew\" = APN.\"BintParamConstantValue\" " +
        "LEFT JOIN ( " +
        "    SELECT T_D.\"intUserID\", T_D.\"intTblMstDPUserID\" AS \"intDPUserID\", T_D.\"varName\" AS \"varDPName\", " +
        "           T_I.\"intTblMstInsurerUserID\" AS \"intInsurerUserID\", T_I.\"varName\" AS \"varInsurerName\", " +
        "           AP.\"VcParamConstantValue\" AS \"InsurerType\", T_I.\"varInsurerID\", APN.\"VcParamConstantValue\" AS \"InsurerTypeNew\" " +
        "    FROM \"TblMstDPUser\" T_D " +
        "    INNER JOIN \"TblMstInsurerUser\" T_I ON T_D.\"intTblMstInsurerUserID\" = T_I.\"intTblMstInsurerUserID\" " +
        "    INNER JOIN \"_TblApplicationParameter\" AP ON AP.\"VcParamNameConstant\" = 'Insurer_Type' AND T_I.\"tntInsurerType\" = AP.\"BintParamConstantValue\" " +
        "    INNER JOIN \"_TblApplicationParameter\" APN ON APN.\"VcParamNameConstant\" = 'Insurer_Type_New' AND T_I.\"tntInsurerTypeNew\" = APN.\"BintParamConstantValue\" " +
        ") D ON D.\"intUserID\" = U.\"intUserID\" " +
        "LEFT JOIN ( " +
        "    SELECT T_A.\"intUserID\", T_A.\"intTblMstAgntCounselorUserID\", T_A.\"varName\" AS \"varCounselorName\", " +
        "           T_D.\"intTblMstDPUserID\" AS \"intDPUserID\", T_D.\"varName\" AS \"varDPName\", " +
        "           COALESCE(T_I.\"intTblMstInsurerUserID\", T_D_I.\"intTblMstInsurerUserID\") AS \"intInsurerUserID\", " +
        "           COALESCE(T_I.\"varName\", T_D_I.\"varName\") AS \"varInsurerName\", " +
        "           COALESCE(AP_I.\"VcParamConstantValue\", AP_D.\"VcParamConstantValue\") AS \"InsurerType\", " +
        "           COALESCE(T_I.\"varInsurerID\", T_D_I.\"varInsurerID\") AS \"varInsurerID\", " +
        "           COALESCE(AP_I1.\"VcParamConstantValue\", AP_D1.\"VcParamConstantValue\") AS \"InsurerTypeNew\" " +
        "    FROM \"TblMstAgentCounselorUser\" T_A " +
        "    LEFT JOIN \"TblMstDPUser\" T_D ON T_A.\"intTblMstDPUserID\" = T_D.\"intTblMstDPUserID\" " +
        "    LEFT JOIN \"TblMstInsurerUser\" T_D_I ON T_D.\"intTblMstInsurerUserID\" = T_D_I.\"intTblMstInsurerUserID\" " +
        "    LEFT JOIN \"_TblApplicationParameter\" AP_D ON AP_D.\"VcParamNameConstant\" = 'Insurer_Type' AND T_D_I.\"tntInsurerType\" = AP_D.\"BintParamConstantValue\" " +
        "    LEFT JOIN \"_TblApplicationParameter\" AP_D1 ON AP_D1.\"VcParamNameConstant\" = 'Insurer_Type_New' AND T_D_I.\"tntInsurerTypeNew\" = AP_D1.\"BintParamConstantValue\" " +
        "    LEFT JOIN \"TblMstInsurerUser\" T_I ON T_A.\"intTblMstInsurerUserID\" = T_I.\"intTblMstInsurerUserID\" " +
        "    LEFT JOIN \"_TblApplicationParameter\" AP_I ON AP_I.\"VcParamNameConstant\" = 'Insurer_Type' AND T_I.\"tntInsurerType\" = AP_I.\"BintParamConstantValue\" " +
        "    LEFT JOIN \"_TblApplicationParameter\" AP_I1 ON AP_I1.\"VcParamNameConstant\" = 'Insurer_Type_New' AND T_I.\"tntInsurerTypeNew\" = AP_I1.\"BintParamConstantValue\" " +
        ") A ON A.\"intUserID\" = U.\"intUserID\" " +
        "LEFT JOIN \"TblMstPortabilityDPUser\" PDP ON PDP.\"intUserID\" = U.\"intUserID\" " +
        "LEFT JOIN \"TblMstInsurerUser\" PDPI ON PDPI.\"intTblMstInsurerUserID\" = PDP.\"intTblMstInsurerUserID\" " +
        "LEFT JOIN \"_TblApplicationParameter\" APPDP ON APPDP.\"VcParamNameConstant\" = 'Insurer_Type' AND PDPI.\"tntInsurerType\" = APPDP.\"BintParamConstantValue\" " +
        "LEFT JOIN \"_TblApplicationParameter\" APPDP1 ON APPDP1.\"VcParamNameConstant\" = 'Insurer_Type_New' AND PDPI.\"tntInsurerTypeNew\" = APPDP1.\"BintParamConstantValue\" " +
        "LEFT JOIN \"TblMstCorporateUser\" CU ON CU.\"CorporateUserID\" = U.\"intUserID\" " +
        "WHERE U.\"varUserLoginID\" = ? AND U.\"varEmailID\" = ?;

    String UPDATE_USER_PASSWORD_AND_FLAGS =
        "UPDATE \"TblMstUser\" SET " +
        "\"varPassword\" = ?, " +
        "\"varTrxnPassword\" = ?, " +
        "\"bitChgPwdOnNxtLogin\" = ?, " +
        "\"bitIsSuspended\" = ? " +
        "WHERE \"varUserLoginID\" = ?";



    String GET_MENU_PERMISSIONS = "SELECT * FROM \"STP_GetMenuPermission_New2\"(?)";

    String CHANGE_PASSWORD = "SELECT * FROM \"STP_CMN_ChangePassword_New\"(?, ?, ?, ?)";

    String SAVE_USER = "SELECT * FROM \"SP_SAVE_User\"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
}
