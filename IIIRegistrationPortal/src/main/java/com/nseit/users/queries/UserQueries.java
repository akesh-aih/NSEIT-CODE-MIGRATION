package com.nseit.users.queries;

public interface UserQueries {

    String VALIDATE_LOGIN = "SELECT U.intUserID, U.varUserName, U.varPassword, U.bitIsActive, U.bitIsSuspended, U.bitChgPwdOnNxtLogin, R.varRoleName, R.sntRoleID, R.role_code, " +
            "COALESCE(I.intTblMstInsurerUserID, D.intInsurerUserID, A.intInsurerUserID, PDPI.intTblMstInsurerUserID, CU.Corporateuserid, 0) AS intTblMstInsurerUserID, " +
            "COALESCE(I.varName, D.varInsurerName, A.varInsurerName, PDPI.varName, '''') AS varInsurerName, " +
            "ISNULL(D.intDPUserID, ISNULL(A.intDPUserID, 0)) AS intTblMstDPUserID, " +
            "ISNULL(D.varDPName, ISNULL(A.varDPName, '''')) AS varDPName, " +
            "ISNULL(A.intTblMstAgntCounselorUserID, 0) AS intTblMstAgntCounselorUserID, " +
            "ISNULL(A.varCounselorName, '''') AS varCounselorName, " +
            "ISNULL(U.dtLastLoggedIn, GETDATE()) AS dtLastLoggedIn, " +
            "COALESCE(AP.VcParamConstantValue, D.InsurerType, A.InsurerType, APPDP.VcParamConstantValue, '' '') AS InsurerType, " +
            "COALESCE(APN.VcParamConstantValue, D.InsurerTypeNew, A.InsurerTypeNew, APPDP1.VcParamConstantValue, '' '') AS InsurerTypeNew, " +
            "U.varEmailID, " +
            "ISNULL(CA.ca_register_id, 0) AS CAid, " +
            "dbo.FN_GET_TOP_PARENT_LOGIN_ID(U.intUserID) as TopLevelUserLoginId " +
            "FROM TblMstUser U WITH(NOLOCK) " +
            "INNER JOIN TblUserRoleMapping UR WITH(NOLOCK) ON U.intUserID = UR.intUserID " +
            "INNER JOIN TblMstRole R WITH(NOLOCK) ON UR.sntRoleID = R.sntRoleID " +
            "LEFT OUTER JOIN TblMstInsurerUser I WITH(NOLOCK) ON I.intUserID = U.intUserID " +
            "LEFT OUTER JOIN _TblApplicationParameter AP WITH(NOLOCK) ON AP.VcParamNameConstant = ''Insurer_Type'' AND I.tntInsurerType = AP.BintParamConstantValue " +
            "LEFT OUTER JOIN _TblApplicationParameter APN WITH(NOLOCK) ON APN.VcParamNameConstant = ''Insurer_Type_New'' AND I.tntInsurerTypeNew = APN.BintParamConstantValue " +
            "LEFT OUTER JOIN (SELECT T_D.intUserID, T_D.intTblMstDPUserID AS intDPUserID, T_D.varName AS varDPName, T_I.intTblMstInsurerUserID AS intInsurerUserID, T_I.varName AS varInsurerName, AP.VcParamConstantValue AS InsurerType, T_I.varInsurerID, APN.VcParamConstantValue AS InsurerTypeNew FROM TblMstDPUser T_D WITH(NOLOCK) INNER JOIN TblMstInsurerUser T_I WITH(NOLOCK) ON T_D.intTblMstInsurerUserID = T_I.intTblMstInsurerUserID INNER JOIN _TblApplicationParameter AP WITH(NOLOCK) ON AP.VcParamNameConstant = ''Insurer_Type'' AND T_I.tntInsurerType = AP.BintParamConstantValue INNER JOIN _TblApplicationParameter APN WITH(NOLOCK) ON APN.VcParamNameConstant = ''Insurer_Type_New'' AND T_I.tntInsurerTypeNew = APN.BintParamConstantValue) D ON D.intUserID = U.intUserID " +
            "LEFT OUTER JOIN (SELECT T_A.intUserID, T_A.intTblMstAgntCounselorUserID, T_A.varName AS varCounselorName, T_D.intTblMstDPUserID AS intDPUserID, T_D.varName AS varDPName, ISNULL(T_I.intTblMstInsurerUserID, ISNULL(T_D_I.intTblMstInsurerUserID, NULL)) AS intInsurerUserID, ISNULL(T_I.varName, ISNULL(T_D_I.varName, NULL)) AS varInsurerName, ISNULL(AP_I.VcParamConstantValue, ISNULL(AP_D.VcParamConstantValue, NULL)) AS InsurerType, ISNULL(T_I.varInsurerID, ISNULL(T_D_I.varInsurerID, NULL)) AS varInsurerID, ISNULL(AP_I1.VcParamConstantValue, ISNULL(AP_D1.VcParamConstantValue, NULL)) AS InsurerTypeNew FROM TblMstAgentCounselorUser T_A WITH(NOLOCK) LEFT OUTER JOIN TblMstDPUser T_D WITH(NOLOCK) ON T_A.intTblMstDPUserID = T_D.intTblMstDPUserID LEFT OUTER JOIN TblMstInsurerUser T_D_I WITH(NOLOCK) ON T_D.intTblMstInsurerUserID = T_D_I.intTblMstInsurerUserID LEFT OUTER JOIN _TblApplicationParameter AP_D WITH(NOLOCK) ON AP_D.VcParamNameConstant = ''Insurer_Type'' AND T_D_I.tntInsurerType = AP_D.BintParamConstantValue LEFT OUTER JOIN _TblApplicationParameter AP_D1 WITH(NOLOCK) ON AP_D1.VcParamNameConstant = ''Insurer_Type_New'' AND T_D_I.tntInsurerTypeNew = AP_D1.BintParamConstantValue LEFT OUTER JOIN TblMstInsurerUser T_I WITH(NOLOCK) ON T_A.intTblMstInsurerUserID = T_I.intTblMstInsurerUserID LEFT OUTER JOIN _TblApplicationParameter AP_I WITH(NOLOCK) ON AP_I.VcParamNameConstant = ''Insurer_Type'' AND T_I.tntInsurerType = AP_I.BintParamConstantValue LEFT OUTER JOIN _TblApplicationParameter AP_I1 WITH(NOLOCK) ON AP_I1.VcParamNameConstant = ''Insurer_Type_New'' AND T_I.tntInsurerTypeNew = AP_I1.BintParamConstantValue) A ON A.intUserID = U.intUserID " +
            "LEFT JOIN TblMstPortabilityDPUser PDP WITH(NOLOCK) ON PDP.intUserID = U.intUserID " +
            "LEFT JOIN TblMstInsurerUser PDPI WITH(NOLOCK) ON PDPI.intTblMstInsurerUserID = PDP.intTblMstInsurerUserID " +
            "LEFT JOIN _TblApplicationParameter APPDP WITH(NOLOCK) ON APPDP.VcParamNameConstant = ''Insurer_Type'' AND PDPI.tntInsurerType = APPDP.BintParamConstantValue " +
            "LEFT JOIN _TblApplicationParameter APPDP1 WITH(NOLOCK) ON APPDP1.VcParamNameConstant = ''Insurer_Type_New'' AND PDPI.tntInsurerTypeNew = APPDP1.BintParamConstantValue " +
            "LEFT JOIN TblCARegister CA ON CA.Corporate_UserID = U.intUserID " +
            "LEFT JOIN TblMstCorporateUser CU ON CU.CorporateUserID = U.intUserID " +
            "WHERE U.varUserLoginID = ?";

    String INSERT_LOGIN_LOG = "INSERT INTO _TblUserLogin(intUserID, varUserLoginID, intCreatedBy, dtCreatedOn, dtLastLoggedIn, bitIsLoggedIn) VALUES(?, ?, ?, GETDATE(), GETDATE(), 1)";

    String UPDATE_USER_LOGIN_STATUS = "UPDATE TblMstUser SET bitIsLoggedIn = 1, sntIncorrectLoginAttempts = 0, dtLastLoggedin = GETDATE() WHERE varUserLoginID = ?";

    String GET_MENU_PERMISSIONS = "EXEC STP_GetMenuPermission_New2 @IntSearchID=?, @IsRole=0";

    String CHANGE_PASSWORD = "EXEC STP_CMN_ChangePassword_New @varLoginID=?, @varPassword=?, @varNewPassword=?, @varResult=?";

    String SAVE_USER = "EXEC SP_SAVE_User @intUserID=?, @varLoginID=?, @varName=?, @varPassword=?, @varTrxnPassword=?, @varMobileNo=?, @varEMailID=?, @BitIsActive=?, @sntRoleID=?, @intCurrentUser=?, @Message=?";

}
