package com.nseit.users.dao.impl;

import com.nseit.generic.dao.BaseDao;
import com.nseit.users.dao.LoginDao;
import com.nseit.users.models.UserBean;
import com.nseit.users.queries.UserQueries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.nseit.generic.util.GenericException;

public class LoginDaoImpl extends BaseDao implements LoginDao {
    private static final Logger logger = LogManager.getLogger(LoginDaoImpl.class);

    @Override
    public UserBean findByUsername(String username) {
        try {
            List<UserBean> users = getJdbcTemplate().query(UserQueries.VALIDATE_LOGIN, new UserRowMapper(), username);
            return users.isEmpty() ? null : users.get(0);
        } catch (DataAccessException e) {
            logger.error("Error finding user by username: {}", username, e);
            throw new GenericException("Error finding user by username", e);
        }
    }

    @Override
    public void logLogin(int userId, String loginId) {
        try {
            logger.debug("Inputs to log login {{}, {}, {}}", UserQueries.INSERT_LOGIN_LOG, userId, loginId, userId);
            getJdbcTemplate().update(UserQueries.INSERT_LOGIN_LOG, userId, loginId, userId);
        } catch (DataAccessException e) {
            logger.error("Error logging login for user: {}", loginId, e);
            throw new GenericException("Error logging login", e);
        }
    }

    @Override
    public void updateLoginStatus(String loginId) {
        try {
            getJdbcTemplate().update(UserQueries.UPDATE_USER_LOGIN_STATUS, loginId);
        } catch (DataAccessException e) {
            logger.error("Error updating login status for user: {}", loginId, e);
            throw new GenericException("Error updating login status", e);
        }
    }

    @Override
    public UserBean findUserByLoginIdAndEmail(String loginId, String emailId) {
        try {
            logger.info("Finding user by Login ID: {} and Email ID: {}", loginId, emailId);
            List<UserBean> users = getJdbcTemplate().query(UserQueries.FIND_USER_BY_LOGINID_AND_EMAIL, new UserRowMapper(), loginId, emailId);
            return users.isEmpty() ? null : users.get(0);
        } catch (DataAccessException e) {
            logger.error("Error finding user by login ID and email: {}/{}", loginId, emailId, e);
            throw new GenericException("Error finding user by login ID and email", e);
        }
    }

    @Override
    public void updatePassword(String loginId, String newEncryptedPassword, String newEncryptedTrxnPassword, boolean changePwdOnNextLogin, boolean isSuspended) {
        try {
            logger.info("Updating password for Login ID: {}", loginId);
            getJdbcTemplate().update(UserQueries.UPDATE_USER_PASSWORD_AND_FLAGS,
                    newEncryptedPassword,
                    newEncryptedTrxnPassword,
                    changePwdOnNextLogin,
                    isSuspended,
                    loginId);
        } catch (DataAccessException e) {
            logger.error("Error updating password for user: {}", loginId, e);
            throw new GenericException("Error updating password", e);
        }
    }

    private static class UserRowMapper implements RowMapper<UserBean> {
        @Override
        public UserBean mapRow(ResultSet rs, int rowNum) throws SQLException {
            UserBean user = new UserBean();
            user.setUserId(rs.getInt("intUserID"));
            user.setUserLoginId(rs.getString("varUserLoginID"));
            user.setUserName(rs.getString("varUserName"));
            user.setPassword(rs.getString("varPassword"));
            user.setTransactionPassword(rs.getString("varTrxnPassword"));
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
