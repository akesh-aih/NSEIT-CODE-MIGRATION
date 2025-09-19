package com.nseit.users.dao.impl;

import com.nseit.generic.dao.BaseDao;
import com.nseit.users.dao.UserDao;
import com.nseit.users.models.User;
import com.nseit.users.queries.UserQueries;
import com.nseit.users.service.impl.UserServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDaoImpl extends BaseDao implements UserDao {
    private static final Logger logger = LogManager.getLogger(UserDaoImpl.class);

    @Override
    public User findByUsername(String username) {
        List<User> users = getJdbcTemplate().query(UserQueries.VALIDATE_LOGIN, new UserRowMapper(), username);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void logLogin(int userId, String loginId) {
    	System.out.println("Inputs to log login {" 
    		    + UserQueries.INSERT_LOGIN_LOG + ", "
    		    + userId + ", "
    		    + loginId + ", "
    		    + userId + "}");
        getJdbcTemplate().update(UserQueries.INSERT_LOGIN_LOG, userId, loginId, userId);
    }

    @Override
    public void updateLoginStatus(String loginId) {
        getJdbcTemplate().update(UserQueries.UPDATE_USER_LOGIN_STATUS, loginId);
    }

    @Override
    public List<Map<String, Object>> getMenuPermissions(int userId) {
        return getJdbcTemplate().queryForList(UserQueries.GET_MENU_PERMISSIONS, userId);
    }


    @Override
    public String saveUser(User user, int currentUserId) {
        // This will be implemented later, as it requires a function call
        return null;
    }

    @Override
    public User findUserByLoginIdAndEmail(String loginId, String emailId) {
        logger.info("Finding user by Login ID: {} and Email ID: {}", loginId, emailId);
        List<User> users = getJdbcTemplate().query(UserQueries.FIND_USER_BY_LOGINID_AND_EMAIL, new UserRowMapper(), loginId, emailId);
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public void updatePassword(String loginId, String newEncryptedPassword, String newEncryptedTrxnPassword, boolean changePwdOnNextLogin, boolean isSuspended) {
        logger.info("Updating password for Login ID: {}", loginId);
        getJdbcTemplate().update(UserQueries.UPDATE_USER_PASSWORD_AND_FLAGS,
                newEncryptedPassword,
                newEncryptedTrxnPassword,
                changePwdOnNextLogin,
                isSuspended,
                loginId);
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            User user = new User();
            user.setUserId(rs.getInt("intUserID"));
            user.setUserLoginId(rs.getString("varUserLoginID")); // Added missing line
            user.setUserName(rs.getString("varUserName"));
            user.setPassword(rs.getString("varPassword"));
            user.setTransactionPassword(rs.getString("varTrxnPassword")); // Added
            user.setActive(rs.getBoolean("bitIsActive"));
            user.setSuspended(rs.getBoolean("bitIsSuspended"));
            user.setChangePasswordOnNextLogin(rs.getBoolean("bitChgPwdOnNxtLogin"));
            user.setRoleName(rs.getString("varRoleName"));
            user.setRoleId(rs.getInt("sntRoleID"));
            user.setRoleCode(rs.getString("role_code"));
            user.setInsurerUserId(rs.getInt("intTblMstInsurerUserID"));
            user.setInsurerName(rs.getString("varInsurerName"));
            user.setDpUserId(rs.getInt("intTblMstDPUserID"));
            user.setDpName(rs.getString("varDPName"));
            user.setAgentCounselorUserId(rs.getInt("intTblMstAgntCounselorUserID"));
            user.setAgentCounselorName(rs.getString("varCounselorName"));
            user.setLastLoggedInDateTime(rs.getTimestamp("dtLastLoggedIn"));
            user.setInsurerType(rs.getString("InsurerType"));
            user.setInsurerTypeNew(rs.getString("InsurerTypeNew"));
            user.setUserEmailId(rs.getString("varEmailID"));
            user.setCaId(rs.getString("CAid").charAt(0));
            user.setTopUserLoginId(rs.getString("TopLevelUserLoginId"));
            return user;
        }
    }
    }
