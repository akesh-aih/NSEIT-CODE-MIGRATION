package com.nseit.generic.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.UserDao;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.UserBean;
import com.nseit.generic.queries.UserQueries;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;

public class UserDaoImpl extends BaseDAO implements UserDao {
	private JdbcTemplate writeJdbcTemplate;
	
	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	@Override
	public int insertUserDetails(UserBean userBean) throws Exception {
		int result = 0;
		String encryptPassword = null;
		try {
			if (userBean.getPassword() != null && !userBean.getPassword().equals("")) {
				encryptPassword = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt(userBean.getPassword());
			}
			result = writeJdbcTemplate.update(UserQueries.INSERT_NEW_USER, new Object[] { userBean.getUserId(), encryptPassword, userBean.getRoleType(), userBean.getEmail(),
					userBean.getMobile(), userBean.getRole(), userBean.getLoggedIn(), userBean.getUserName(), userBean.getStatus()

			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBean> getSearchUserDetails(UserBean userBean, Pagination pagination) throws Exception {
		List<UserBean> searchUserList = new ArrayList<UserBean>();
		try {
			StringBuffer strBld = new StringBuffer();

			strBld.append(UserQueries.GET_USER_SEARCH);
			if (userBean.getUserId() != null && !userBean.getUserId().equals("")) {
				strBld.append(" and oum_user_id = upper('" + userBean.getUserId() + "') ");
			}
			strBld.append(") as aliasTEST WHERE num BETWEEN ").append(pagination.getStart()).append(" AND ").append(pagination.getEnd());

			searchUserList = getJdbcTemplate().query(strBld.toString(), new Object[] { userBean.getRole(), userBean.getRole(), userBean.getStatus(), userBean.getStatus() },
					new RowMapper<UserBean>() {
						public UserBean mapRow(ResultSet rs, int rowNum) {
							UserBean user = new UserBean();
							try {
								user.setUserId(rs.getString("oum_user_id"));
								user.setUserName(rs.getString("oum_user_name"));
								user.setUserPk(rs.getString("oum_user_pk"));
								user.setEmail(rs.getString("oum_email_id"));
								user.setMobile(rs.getString("oum_mobile_no"));
								user.setStatus(rs.getString("oum_status"));
							} catch (SQLException e) {
								logger.error(e.getMessage());
							}
							return user;
						}
					});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return searchUserList;
	}

	@Override
	public int getUserCount(UserBean userBean) throws Exception {
		int count = 0;
		try {
			StringBuffer strBld = new StringBuffer();

			strBld.append(UserQueries.GET_USER_COUNT);
			if (userBean.getUserId() != null && !userBean.getUserId().equals("")) {
				strBld.append(" and oum_user_id = upper('" + userBean.getUserId() + "') ");
			}
			count = getJdbcTemplate().queryForObject(strBld.toString(), new Object[] { userBean.getRole(), userBean.getRole(), userBean.getStatus(), userBean.getStatus() },Integer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public UserBean getUserEditDetails(UserBean userBean) throws Exception {
		// logger.info("calling getUserEditDetails ");

		UserBean ResultUserBean = new UserBean();
		try {
			ResultUserBean = (UserBean) getJdbcTemplate().queryForObject(UserQueries.GET_USER_EDIT_DETAILS, new Object[] { Integer.parseInt(userBean.getUserPk()) },
					new RowMapper<UserBean>() {
						public UserBean mapRow(ResultSet rs, int rowNum) {
							UserBean user = new UserBean();
							try {
								user.setUserId(rs.getString("oum_user_id"));
								user.setUserName(rs.getString("oum_user_name"));
								user.setRole(rs.getString("oum_role_fk"));
								user.setUserPk(rs.getString("oum_user_pk"));
								user.setEmail(rs.getString("oum_email_id"));
								user.setMobile(rs.getString("oum_mobile_no"));
								user.setStatus(rs.getString("oum_status"));
								user.setPassword(rs.getString("oum_password"));
							} catch (SQLException e) {
								logger.error(e.getMessage());
							}
							return user;
						}
					});
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return ResultUserBean;
	}

	@Override
	public int updateUserDetails(UserBean userBean) throws Exception {
		int result = 0;
		String encryptPassword = null;
		try {
			if (userBean.getPassword() != null && !userBean.getPassword().equals("")) {
				encryptPassword = DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).encrypt(userBean.getPassword());
			}
			result = writeJdbcTemplate.update(UserQueries.UPDATE_USER, new Object[] { encryptPassword, userBean.getEmail(), userBean.getMobile(), userBean.getRole(),
					userBean.getUserName(), userBean.getStatus(), userBean.getLoggedIn(), userBean.getUserId() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int checkUserId(UserBean userBean) throws Exception {
		int userCount = 0;
		try {
			userCount = getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_USER_ID_EXISTS, new Object[] { userBean.getUserId().trim().toUpperCase() },Integer.class);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return userCount;
	}

	/*
	 * @Override public boolean isEmailInUse(String email) { int emailCount = 0;
	 * emailCount =
	 * getJdbcTemplate().queryForObject(UserQueries.CHECK_IF_EMAIL_EXISTS, new
	 * Object[] { email },Integer.class); return emailCount != 0; }
	 */

}