package com.nseit.generic.dao.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Strings;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.dao.CommonDao;
import com.nseit.generic.models.AgeMatrix;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.ConfigParam;
import com.nseit.generic.models.EmailSMSMasterBean;
import com.nseit.generic.models.EmailSMSTransactionBean;
import com.nseit.generic.models.RegistrationBean;
import com.nseit.generic.models.UploadManagementBean;
import com.nseit.generic.models.UserDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.queries.CommonQueries;
import com.nseit.generic.queries.MasterQueries;
import com.nseit.generic.queries.UserQueries;
import com.nseit.generic.util.CategoryBean;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.DESEncrypter;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.MenuMasterChildBean;
import com.nseit.generic.util.MenuMasterParentBean;
import com.nseit.generic.util.OesMenuControlMaster;
import com.nseit.generic.util.PostBean;
import com.nseit.generic.util.StageOffBean;
import com.nseit.generic.util.StageOnBean;
import com.nseit.otbs.model.TestMasterBean;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class CommonDaoImpl extends BaseDAO implements CommonDao {

	private String ENC_KEY = ConfigurationConstants.getInstance().getENC_KEY();

	private JdbcTemplate writeJdbcTemplate;

	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	private Logger logger = LoggerHome.getLogger(getClass());

	public List<ConfigParam> getConfigParam() throws Exception {
		return getJdbcTemplate().query(CommonQueries.SELECT_CONFIG_ALL, new RowMapper<ConfigParam>() {
			public ConfigParam mapRow(ResultSet rs, int rowNumber) throws SQLException {
				return new ConfigParam(rs.getString("ocp_key_name"), rs.getString("ocp_key_value"), rs.getString("ocp_data_type"));
			}
		});
	}

	@Override
	public List<AgeMatrix> getAgeMatrixData() throws Exception {
		return getJdbcTemplate().query(CommonQueries.SELECT_ALL_AGEMATRIX_DATA, new RowMapper<AgeMatrix>() {
			public AgeMatrix mapRow(ResultSet rs, int rowNumber) throws SQLException {
				return new AgeMatrix(rs.getInt("oar_pk"), rs.getString("category"), rs.getString("isesm"), rs.getInt("maxage_yrs"), rs.getDate("mindate"),
						rs.getString("isupgovtemp"));
			}
		});
	}

	public Map<Integer, String> getActivityMap() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.SELECT_ACTIVITY_ALL, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> activityMap = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					activityMap.put(rs.getInt("osm_status_pk"), rs.getString("osm_status_desc"));
				}
				return activityMap;
			}
		});
	}

	public Map<Integer, String> getStageMap() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.SELECT_STAGE_ALL, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> activityMap = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					activityMap.put(rs.getInt("ocsm_stage_pk"), rs.getString("ocsm_stage_name"));
				}
				return activityMap;
			}
		});
	}

	/**
	 * @author shailendra sharma
	 * @return
	 * @throws Exception
	 */

	public Map<Integer, String> getStageStatusMap() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.SELECT_STAGE_STATUS_ALL, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> activityMap = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					activityMap.put(rs.getInt("ocsm_stage_pk"), rs.getString("ocsm_status"));
				}
				return activityMap;
			}
		});
	}

	/**
	 * @author shailendra sharma This method will load all the action url on the basis of stage id
	 * 
	 */

	public Map<Integer, String> getStageActionUrlMap() throws Exception {
		return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.SELECT_STAGE_ACTION_URL, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> stageActionUrlMap = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					stageActionUrlMap.put(rs.getInt("ocsm_stage_pk"), rs.getString("ocsm_stage_link"));
				}
				return stageActionUrlMap;
			}
		});
	}

	/**
	 * @author shailendra sharma This method will load all the action url on the basis of stage id
	 * 
	 */

	public int[] insertEmailSMSDetails(List<EmailSMSTransactionBean> emailSMS, String username) {
		return writeJdbcTemplate.batchUpdate(CommonQueries.INSERT_EMAIL_SMS_DETAIL, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pStatement, int currBatchIndex) throws SQLException {
				pStatement.setString(1, emailSMS.get(currBatchIndex).getEmailSubject());
				pStatement.setString(2, emailSMS.get(currBatchIndex).getSmsEmailContent());
				pStatement.setString(3, emailSMS.get(currBatchIndex).getCandidateMailAddress().toString());
				pStatement.setString(4, emailSMS.get(currBatchIndex).getSmsMailFlag());
				pStatement.setString(5, "N");
				pStatement.setString(6, "A");
				pStatement.setString(7, username);
				pStatement.setInt(8, Integer.parseInt(emailSMS.get(currBatchIndex).getStatusPk()));
			}

			public int getBatchSize() {
				return emailSMS.size();
			}
		});
	}

	@Override
	public Map<Integer, String> getBoardMasterMap() throws Exception {
		Map<Integer, String> boardMap = new LinkedHashMap<Integer, String>();
		try {
			boardMap = (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.GET_BOARD_NAME, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> boardMapTmp = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						boardMapTmp.put(rs.getInt("oebm_pk"), rs.getString("oebm_board_name"));
					}
					return boardMapTmp;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return boardMap;
	}

	public List<EmailSMSTransactionBean> getEmailForMailing(Integer failureCount, int maxRecords) {
		return getJdbcTemplate().query(CommonQueries.SELECT_EMAIL_FOR_MAILING, new RowMapper<EmailSMSTransactionBean>() {
			@Override
			public EmailSMSTransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
				EmailSMSTransactionBean emailSMSBean = new EmailSMSTransactionBean();
				emailSMSBean.setEmailSMSTransactionId(rs.getLong("osetm_sms_email_tran_pk"));
				if (StringUtils.isNotBlank(rs.getString("osetm_subject"))) {
					emailSMSBean.setEmailSubject(rs.getString("osetm_subject"));
				} else {
					emailSMSBean.setEmailSubject(rs.getString(""));
				}
				if (StringUtils.isNotBlank(rs.getString("osetm_mail_sms_object"))) {
					emailSMSBean.setSmsEmailContent(rs.getString("osetm_mail_sms_object"));
				} else {
					emailSMSBean.setSmsEmailContent(rs.getString(""));
				}
				if (StringUtils.isNotBlank(rs.getString("osetm_from_address"))) {
					emailSMSBean.setFromAddress(rs.getString("osetm_from_address"));
				} else {
					emailSMSBean.setFromAddress("");
				}
				if (StringUtils.isNotBlank(rs.getString("osetm_to_address"))) {
					emailSMSBean.setToAddress(Arrays.asList(rs.getString("osetm_to_address").split("\\,")));
				} else {
					emailSMSBean.setToAddress(Arrays.asList(rs.getString("")));
				}
				emailSMSBean.setEmailSmsFailureCount(rs.getInt("osetm_send_failure_count"));
				if (rs.getString("osetm_cc_address") != null) {
					emailSMSBean.setCcAddress(Arrays.asList(rs.getString("osetm_cc_address").split("\\,")));
				}
				return emailSMSBean;
			}
		}, new Object[] { failureCount, ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_EMAIL_VALUE), maxRecords });
	}

	public List<EmailSMSTransactionBean> getSMSForSending(Integer failureCount, int maxRecords) {
		return getJdbcTemplate().query(CommonQueries.SELECT_SMS_FOR_MAILING, new RowMapper<EmailSMSTransactionBean>() {
			@Override
			public EmailSMSTransactionBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
				EmailSMSTransactionBean emailSMSBean = new EmailSMSTransactionBean();

				emailSMSBean.setEmailSMSTransactionId(rs.getLong("osetm_sms_email_tran_pk"));
				if (StringUtils.isNotBlank(rs.getString("osetm_mail_sms_object"))) {
					emailSMSBean.setSmsEmailContent(rs.getString("osetm_mail_sms_object"));
				} else {
					emailSMSBean.setSmsEmailContent("");
				}
				if (StringUtils.isNotBlank(rs.getString("osetm_to_address"))) {
					emailSMSBean.setToAddress(Arrays.asList(rs.getString("osetm_to_address").split("\\,")));
				} else {
					emailSMSBean.setToAddress(Arrays.asList(""));
				}
				emailSMSBean.setEmailSmsFailureCount(rs.getInt("osetm_send_failure_count"));
				return emailSMSBean;
			}
		}, new Object[] { failureCount, ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.EMAIL_SMS_FLAG_SMS_VALUE), maxRecords });
	}

	public List<EmailSMSMasterBean> getEmailSMSMasterList() {
		return getJdbcTemplate().query(CommonQueries.SELECT_EMAIL_SMS_MASTER, new RowMapper<EmailSMSMasterBean>() {
			@Override
			public EmailSMSMasterBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
				EmailSMSMasterBean emailSMSMasterBean = new EmailSMSMasterBean();
				emailSMSMasterBean.setEmailSMSMasterId(rs.getInt("osem_sms_email_pk"));
				emailSMSMasterBean.setTestId(rs.getInt("osem_test_fk"));
				emailSMSMasterBean.setActivityId(rs.getInt("osem_activity_fk"));
				emailSMSMasterBean.setMailObject(rs.getString("osem_mail_object"));
				emailSMSMasterBean.setSmsObject(rs.getString("osem_sms_object"));
				emailSMSMasterBean.setEmailSubject(rs.getString("osem_subject"));
				emailSMSMasterBean.setCcAddress(rs.getString("osem_cc_address") == null ? null : Arrays.asList(rs.getString("osem_cc_address").split("\\,")));
				emailSMSMasterBean.setMailApplicable(rs.getString("osem_mail_applicable").compareTo("Y") == 0);
				emailSMSMasterBean.setSmsApplicable(rs.getString("osem_sms_applicable").compareTo("Y") == 0);
				emailSMSMasterBean.setLastUpdatedTimestamp(rs.getTimestamp("last_updated_date"));
				return emailSMSMasterBean;
			}
		});
	}

	public int updateCandidateStage(Users users, String stage) throws Exception {
		Long userId = 0L;
		String updatedBy = "";
		if (users != null) {
			userId = users.getUserId();
			updatedBy = users.getUsername();
		}
		int updateStage = 0;
		try {
			updateStage = writeJdbcTemplate.update(CommonQueries.UPDATE_STAGE, new Object[] { Float.parseFloat(stage), updatedBy, userId });
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return updateStage;
	}

	public Map<String, String> getCandidatesDataForEmailSMS(String userId) throws Exception {
		List list = getJdbcTemplate().queryForList(CommonQueries.SELECT_CANDIDATE_INFO_FOR_EMAIL_SMS, new Object[] { userId });
		Map<String, String> rowData = null;
		if (list != null && list.size() > 0) {
			rowData = (Map<String, String>) list.get(0);
			try {
				rowData.put("user_password", DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rowData.get("user_password")));
				rowData.put("mobile_no", String.valueOf(rowData.get("mobile_no")));
				if (String.valueOf(rowData.get("alternateMobileNo")) != null && !String.valueOf(rowData.get("alternateMobileNo")).equals("")
						&& !String.valueOf(rowData.get("alternateMobileNo")).equals("null")) {
					rowData.put("alternateMobileNo", String.valueOf(rowData.get("alternateMobileNo")));
				} else {
					rowData.put("alternateMobileNo", " ");
				}
			} catch (Exception ex) {
				throw new GenericException(ex);
			}
		}
		return rowData;
	}

	public Map<String, String> getCandidatesDataForEmailSMSForSeatBooking(String userId, int attempt) throws Exception {
		List list = getJdbcTemplate().queryForList(CommonQueries.SELECT_CANDIDATE_INFO_FOR_SEAT_BOOKING, new Object[] { userId, attempt });
		Map<String, String> rowData = null;
		if (list != null && list.size() > 0) {
			rowData = (Map<String, String>) list.get(0);
			try {
				rowData.put("user_password", DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rowData.get("user_password")));
			} catch (Exception ex) {
				throw new GenericException(ex);
			}
		}
		return rowData;
	}

	public List<Map<String, String>> getMenuForAll() {
		List<Map<String, Object>> listOfObjectMap = new ArrayList<Map<String, Object>>();
		listOfObjectMap = getJdbcTemplate().queryForList(CommonQueries.SELECT_MENU);
		List<Map<String, String>> listOfStringMap = new ArrayList<Map<String, String>>();
		for (Map<String, Object> map : listOfObjectMap) {
			Map<String, String> tempMap = new HashMap<String, String>();
			for (String key : tempMap.keySet()) {
				tempMap.put(key, map.get(key).toString());
			}
			listOfStringMap.add(tempMap);
		}
		return listOfStringMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.dao.CommonDao#getParentMenuList()
	 */
	public List<MenuMasterParentBean> getParentMenuList() {
		List<MenuMasterParentBean> parentMenuList = new ArrayList<MenuMasterParentBean>();
		List<Map<String, Object>> parentMenuMap = getJdbcTemplate().queryForList(CommonQueries.SELECT_PARENT);
		for (Map<String, Object> map : parentMenuMap) {
			MenuMasterParentBean masterParentBean = new MenuMasterParentBean();
			String menuKey = String.valueOf(map.get("omcm_menu_key"));
			if (menuKey != null) {
				if (menuKey.contains(".")) {
					menuKey = menuKey.split("\\.")[0].toString();
				}
			}
			masterParentBean.setMenuKey(menuKey);
			masterParentBean.setMenuMasterPk(Integer.parseInt(String.valueOf(map.get("OMCM_MENU_PK"))));
			masterParentBean.setDisplayName(String.valueOf(map.get("omcm_menu_desc")));
			masterParentBean.setMenuLink(String.valueOf(map.get("omcm_menu_link")));
			masterParentBean.setUserType(String.valueOf(map.get("omcm_user_type")));
			masterParentBean.setRoleFK(String.valueOf(map.get("OAC_ROLE_FK")));
			// masterParentBean.setParentStageOnValue(String.valueOf(map.get("OMCM_STAGE_ON")));
			parentMenuList.add(masterParentBean);
		}
		return parentMenuList;
	}

	public List<MenuMasterChildBean> getChildMenuList(String parentMenuKey, String roleFK) {
		List<MenuMasterChildBean> childMenuList = null;
		try {
			childMenuList = getJdbcTemplate().query(CommonQueries.SELECT_CHILD, new BeanPropertyRowMapper(MenuMasterChildBean.class), new Object[] { parentMenuKey, roleFK });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return childMenuList;
	}

	public List<MenuMasterChildBean> getChildList() {
		List<MenuMasterChildBean> childMenuList = null;
		try {
			childMenuList = getJdbcTemplate().query(CommonQueries.SELECT_ALL_CHILD, new BeanPropertyRowMapper(MenuMasterChildBean.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return childMenuList;
	}

	public Map<String, List<MenuMasterChildBean>> getMenuMasterMap() {
		Map<String, List<MenuMasterChildBean>> menuMasterMap = new LinkedHashMap<String, List<MenuMasterChildBean>>();
		List<MenuMasterParentBean> parentMenuList = getParentMenuList();
		for (MenuMasterParentBean menuMasterParentBean : parentMenuList) {
			List<MenuMasterChildBean> childMenuList = getChildMenuList(menuMasterParentBean.getMenuKey(), menuMasterParentBean.getRoleFK());
			String menuKey = menuMasterParentBean.getMenuKey();
			if (menuKey != null) {
				if (menuKey.contains(".")) {
					menuKey = menuKey.split("\\.")[0].toString();
				}
			}
			menuMasterMap.put(menuKey + "#" + menuMasterParentBean.getRoleFK(), childMenuList);
		}
		return menuMasterMap;
	}

	public Map<String, String> getMenuKeyLinkMap() throws GenericException {
		Map<String, String> menuKeyLinkMap = new LinkedHashMap<String, String>();
		try {
			menuKeyLinkMap = (Map<String, String>) getJdbcTemplate().query(CommonQueries.SELECT_MENU, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> menuKeyLinkMap = new LinkedHashMap<String, String>();
					while (rs.next()) {
						menuKeyLinkMap.put(rs.getString("omcm_menu_link"), rs.getString("omcm_menu_key"));
					}
					return menuKeyLinkMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return menuKeyLinkMap;
	}

	public Map<String, String> getMenuKeyMenuLinkMap() throws GenericException {
		Map<String, String> menuKeyMenuLinkMap = new LinkedHashMap<String, String>();
		try {
			menuKeyMenuLinkMap = (Map<String, String>) getJdbcTemplate().query(CommonQueries.SELECT_MENU, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> menuKeyLinkMap = new LinkedHashMap<String, String>();
					while (rs.next()) {
						menuKeyLinkMap.put(rs.getString("omcm_menu_key"), rs.getString("omcm_menu_link"));
					}
					return menuKeyLinkMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return menuKeyMenuLinkMap;
	}

	public Map<String, String> getMenuKeyNextStageMap() throws GenericException {
		Map<String, String> menuKeyNextStageMap = new LinkedHashMap<String, String>();
		try {
			menuKeyNextStageMap = (Map<String, String>) getJdbcTemplate().query(CommonQueries.SELECT_MENU, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> menuKeyLinkMap = new LinkedHashMap<String, String>();
					while (rs.next()) {
						menuKeyLinkMap.put(rs.getString("omcm_menu_key"), rs.getString("omcm_next_stage"));
					}
					return menuKeyLinkMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return menuKeyNextStageMap;
	}

	public Map<String, String> getMenuKeyMandatoryMap() throws GenericException {
		Map<String, String> menuKeyMandatoryMap = new LinkedHashMap<String, String>();
		try {
			menuKeyMandatoryMap = (Map<String, String>) getJdbcTemplate().query(CommonQueries.SELECT_MENU, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> menuKeyMandatoryMap = new LinkedHashMap<String, String>();
					while (rs.next()) {
						menuKeyMandatoryMap.put(rs.getString("omcm_menu_key"), rs.getString("omcm_menu_mandatory"));
					}
					return menuKeyMandatoryMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return menuKeyMandatoryMap;
	}

	public Map<String, String> getMenuKeyStatusMap() throws GenericException {
		Map<String, String> menuKeyStatusMap = new LinkedHashMap<String, String>();
		try {
			menuKeyStatusMap = (Map<String, String>) getJdbcTemplate().query(CommonQueries.SELECT_MENU, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> menuKeyStatusMap = new LinkedHashMap<String, String>();
					while (rs.next()) {
						menuKeyStatusMap.put(rs.getString("omcm_menu_key"), rs.getString("omcm_status_fk"));
					}
					return menuKeyStatusMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return menuKeyStatusMap;
	}

	public Map<String, String> getMenuDescMenuKeyMap() throws GenericException {
		Map<String, String> menuDescMenuKeyMap = new LinkedHashMap<String, String>();
		try {
			menuDescMenuKeyMap = (Map<String, String>) getJdbcTemplate().query(CommonQueries.SELECT_MENU, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> menuDescMenuKeyMap = new LinkedHashMap<String, String>();
					while (rs.next()) {
						menuDescMenuKeyMap.put(rs.getString("omcm_menu_desc"), rs.getString("omcm_menu_key"));
					}
					return menuDescMenuKeyMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return menuDescMenuKeyMap;
	}

	public Map<String, String> getMenuDescActiveStatusMap() throws GenericException {
		Map<String, String> menuDescActiveStatusMap = new LinkedHashMap<String, String>();
		List<OesMenuControlMaster> OesMenuControlMasterList = getJdbcTemplate().query(CommonQueries.SELECT_MENU, new BeanPropertyRowMapper(OesMenuControlMaster.class));
		for (OesMenuControlMaster oesMenuControlMaster : OesMenuControlMasterList) {
			menuDescActiveStatusMap.put(oesMenuControlMaster.getOMCM_MENU_DESC(), oesMenuControlMaster.getOMCM_ACTIVE_STATUS());
		}
		return menuDescActiveStatusMap;
	}

	public List<String> getStatusFkList() throws GenericException {
		List<String> statusFkList = new ArrayList<String>();
		List<Map<String, Object>> statusFkMap = getJdbcTemplate().queryForList(CommonQueries.SELECT_STATUS_FK);
		for (Map<String, Object> map : statusFkMap) {
			statusFkList.add(String.valueOf(map.get("oso_status_fk")));
		}
		return statusFkList;
	}

	public List<String> getStageOnStatusFkList() throws GenericException {
		List<String> statusFkList = new ArrayList<String>();
		List<Map<String, Object>> statusFkMap = getJdbcTemplate().queryForList(CommonQueries.SELECT_STAGE_ON_STATUS_FK);
		for (Map<String, Object> map : statusFkMap) {
			statusFkList.add(String.valueOf(map.get("oson_status_fk")));
		}
		return statusFkList;
	}

	public List<StageOffBean> getStageOffList(String statusFk) throws GenericException {
		List<StageOffBean> stageOffList = getJdbcTemplate().query(CommonQueries.SELECT_STAGE_OFF, new BeanPropertyRowMapper(StageOffBean.class), new Object[] { statusFk });
		return stageOffList;
	}

	public List<StageOnBean> getStageOnList(String statusFk) throws GenericException {
		List<StageOnBean> stageOnList = getJdbcTemplate().query(CommonQueries.SELECT_STAGE_ON, new BeanPropertyRowMapper(StageOnBean.class), new Object[] { statusFk });
		return stageOnList;
	}

	public Map<String, List<StageOffBean>> getStageOffListMap() throws GenericException {
		Map<String, List<StageOffBean>> stageOffListMap = new LinkedHashMap<String, List<StageOffBean>>();
		List<String> statusFkList = getStatusFkList();
		for (String string : statusFkList) {
			stageOffListMap.put(string, getStageOffList(string));
		}
		return stageOffListMap;
	}

	public Map<String, List<StageOnBean>> getStageOnListMap() throws GenericException {
		Map<String, List<StageOnBean>> stageOnListMap = new LinkedHashMap<String, List<StageOnBean>>();
		List<String> statusFkList = getStageOnStatusFkList();
		for (String string : statusFkList) {
			stageOnListMap.put(string, getStageOnList(string));
		}
		return stageOnListMap;
	}

	public void updateEmailSMSStatusPostSending(final List<EmailSMSTransactionBean> lstEmailSMSTransactionBeans) {
		writeJdbcTemplate.batchUpdate(CommonQueries.UPDATE_EMAIL_SMS_TRANSACTION_STATUS, new BatchPreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement pStatement, int currTransactionBeanIndex) throws SQLException {
				EmailSMSTransactionBean emailSMSTransactionBean = lstEmailSMSTransactionBeans.get(currTransactionBeanIndex);
				pStatement.setInt(1, emailSMSTransactionBean.getEmailSmsFailureCount());
				pStatement.setString(2, emailSMSTransactionBean.getSentStatus());
				pStatement.setLong(3, emailSMSTransactionBean.getEmailSMSTransactionId());
			}

			@Override
			public int getBatchSize() {
				return lstEmailSMSTransactionBeans.size();
			}
		});
	}

	@Override
	public String getCandidateStage(String username) {
		return (String) getJdbcTemplate().queryForObject(CommonQueries.SELECT_CANDIDATE_STAGE, String.class, new Object[] { username });
	}

	/**
	 * @author Pankaj Sh Date : 04 Apr 2012 Loads Country Map on servlet startup return Map
	 */

	public Map<Integer, String> getCountryMap() throws Exception {
		Map<Integer, String> countryMap = new LinkedHashMap<Integer, String>();
		try {
			countryMap = (Map<Integer, String>) getJdbcTemplate().query(MasterQueries.GET_COUNTRY_NAMES, new ResultSetExtractor() {

				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> countryMap = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						countryMap.put(rs.getInt("ocm_country_pk"), rs.getString("ocm_country_name"));
					}
					return countryMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return countryMap;
	}

	public Map<Integer, Map<Integer, String>> getCountryStateMapping() throws Exception {
		Map<Integer, Map<Integer, String>> countryStateMapping = new LinkedHashMap<Integer, Map<Integer, String>>();
		Map<Integer, String> stateMap = new LinkedHashMap<Integer, String>();
		// List<Integer> countryIDList = new ArrayList<Integer>();

		try {
			/*
			 * countryIDList = (List<Integer>) getJdbcTemplate().query(MasterQueries.GET_COUNTRY_IDS,new ResultSetExtractor (){
			 * 
			 * @Override public Object extractData(ResultSet rs) throws SQLException, DataAccessException { List<Integer> countryList = new ArrayList<Integer>(); while(rs.next()){
			 * countryList.add(rs.getInt("OCM_COUNTRY_PK")); } return countryList; }
			 * 
			 * });
			 */
			stateMap = (Map<Integer, String>) getJdbcTemplate().query(MasterQueries.GET_COUNTRY_STATE_MAPPING, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> stateMap = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						stateMap.put(rs.getInt("osm_state_pk"), rs.getString("osm_state_name"));
					}
					return stateMap;
				}
			}, new Object[] { 1 });
			countryStateMapping.put(1, stateMap);
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return countryStateMapping;
	}

	public Map<Integer, Map<Integer, String>> getStateDistrictMapping() throws Exception {
		Map<Integer, Map<Integer, String>> stateDistrictMapping = new LinkedHashMap<Integer, Map<Integer, String>>();
		Map<Integer, String> districtMap = new LinkedHashMap<Integer, String>();
		List<Integer> stateIDList = new ArrayList<Integer>();
		try {
			stateIDList = (List<Integer>) getJdbcTemplate().query(MasterQueries.GET_STATE_IDS, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Integer> districtList = new ArrayList<Integer>();
					while (rs.next()) {
						districtList.add(rs.getInt("osm_state_pk"));
					}
					return districtList;
				}
			});
			for (int i = 0; i < stateIDList.size(); i++) {
				districtMap = (Map<Integer, String>) getJdbcTemplate().query(MasterQueries.GET_STATE_DISTRICT_MAPPING, new ResultSetExtractor() {
					@Override
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<Integer, String> districtMap = new LinkedHashMap<Integer, String>();
						while (rs.next()) {
							districtMap.put(rs.getInt("opsr_opsm_fk"), rs.getString("opsr_sub_rank"));
						}
						return districtMap;
					}
				}, new Object[] { String.valueOf(stateIDList.get(i)) });
				stateDistrictMapping.put(stateIDList.get(i), districtMap);
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return stateDistrictMapping;
	}

	public Map<String, Map<Integer, String>> getReferenceDomainNameNKeyValueMap() throws Exception {
		Map<String, Map<Integer, String>> referenceDomainNameNKeyValueMap = new HashMap<String, Map<Integer, String>>();
		try {
			referenceDomainNameNKeyValueMap = (Map<String, Map<Integer, String>>) getJdbcTemplate().query(CommonQueries.SELECT_REFERENCE_VALUE_MASTER, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, Map<Integer, String>> referenceDomainNameNKeyValueMap = new HashMap<String, Map<Integer, String>>();
					Map<Integer, String> referenceKeyValueMap = null;
					String previousDomainName = "";
					while (rs.next()) {
						if (previousDomainName.compareTo(rs.getString("orvm_reference_name")) != 0) {
							if (referenceKeyValueMap != null) {
								referenceDomainNameNKeyValueMap.put(previousDomainName, referenceKeyValueMap);
							}
							referenceKeyValueMap = new LinkedHashMap<Integer, String>();
							previousDomainName = rs.getString("orvm_reference_name");
						}
						referenceKeyValueMap.put(rs.getInt("orvm_reference_pk"), rs.getString("orvm_reference_value"));
					}
					if (previousDomainName.compareTo("") != 0) {
						referenceDomainNameNKeyValueMap.put(previousDomainName, referenceKeyValueMap);
					}
					return referenceDomainNameNKeyValueMap;
				}
			});
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return referenceDomainNameNKeyValueMap;
	}

	public Map<Integer, String> getCategoryListMap() throws Exception {
		Map<Integer, String> categoryListMap = new LinkedHashMap<Integer, String>();
		List<CategoryBean> categoryList = getJdbcTemplate().query(CommonQueries.SELECT_CATEGORY, new BeanPropertyRowMapper(CategoryBean.class));
		for (CategoryBean categoryBean : categoryList) {
			categoryListMap.put(categoryBean.getOCTM_CATEGORY_PK(), categoryBean.getOCTM_CATEGORY_CODE());
		}
		return categoryListMap;
	}

	public Map<Integer, String> getPostMasterListMap() throws Exception {
		Map<Integer, String> postListMap = new HashMap<Integer, String>();
		List<PostBean> postList = getJdbcTemplate().query(CommonQueries.SELECT_POST, new BeanPropertyRowMapper(PostBean.class));
		for (PostBean postBean : postList) {
			postListMap.put(postBean.getOPM_POST_PK(), postBean.getOPM_POST_CODE());
		}
		return postListMap;
	}

	public int getCandidateStage(Users users) throws Exception {
		int stage = 0;
		try {
			stage = getJdbcTemplate().queryForObject(CommonQueries.SELECT_CANDIDATE_STAGE, Integer.class, new Object[] { users.getUserId() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return stage;
	}

	/**
	 * @throws Exception
	 */
	/*
	 * @author Raman Pawar
	 * 
	 * @see com.nseit.generic.dao.CommonDao#getDateWindowData()
	 */
	public Map<Integer, List<Long>> getDateWindowData() throws Exception {
		Map<Integer, List<Long>> dateWindowMap = new HashMap<Integer, List<Long>>();
		try {
			dateWindowMap = (Map<Integer, List<Long>>) getJdbcTemplate().query(MasterQueries.GET_DATE_WINDOW_DATES, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, List<Long>> dateWindowMap = new HashMap<Integer, List<Long>>();
					try {
						while (rs.next()) {
							List<Long> dateList = new ArrayList<Long>();
							dateList.add(CommonUtil.getDate(rs.getString("start_date"), "dd-MMM-yyyy HH:mm:ss").getTime());
							dateList.add(CommonUtil.getDate(rs.getString("end_date"), "dd-MMM-yyyy HH:mm:ss").getTime());
							dateWindowMap.put(rs.getInt("odw_date_window_pk"), dateList);
						}
					} catch (Exception e) {
						logger.error(e.getMessage());
					}
					return dateWindowMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return dateWindowMap;
	}

	public Map<Integer, TestMasterBean> getTestDetailsMap() throws Exception {
		Map<Integer, TestMasterBean> testMasterMap = null;
		try {
			testMasterMap = (Map<Integer, TestMasterBean>) getJdbcTemplate().query(MasterQueries.GET_TEST_MASTER_DETAILS, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, TestMasterBean> testMasterMap = new HashMap<Integer, TestMasterBean>();
					while (rs.next()) {
						TestMasterBean tmpTestMasterBean = new TestMasterBean();
						tmpTestMasterBean.setTestDescription(rs.getString("otm_description"));
						tmpTestMasterBean.setTestName(rs.getString("otm_test_name"));
						// tmpTestMasterBean.setTestFeesForOpen(rs.getLong("OTM_FEES_OPEN"));
						// tmpTestMasterBean.setTestFessForSCST(rs.getLong("OTM_FEES_SC_ST"));
						tmpTestMasterBean.setTestDuration(rs.getInt("otm_duration"));
						tmpTestMasterBean.setTestPK(rs.getInt("otm_test_pk"));
						tmpTestMasterBean.setFeesInWordsOpen(rs.getString("otm_fees_in_words_open"));
						tmpTestMasterBean.setFeesInWordsSCST(rs.getString("otm_fees_in_words_sc_st"));
						testMasterMap.put(rs.getInt("otm_test_pk"), tmpTestMasterBean);
					}
					return testMasterMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return testMasterMap;
	}

	public Map<Integer, String> getDisciplineMap() throws Exception {
		Map<Integer, String> disciplineMap = new LinkedHashMap<Integer, String>();
		try {
			disciplineMap = (Map<Integer, String>) getJdbcTemplate().query(MasterQueries.GET_DISCIPLINE_MAP, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> disciplineMap = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						disciplineMap.put(rs.getInt("OTM_TEST_PK"), rs.getString("OTM_TEST_NAME"));
					}
					return disciplineMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return disciplineMap;
	}

	public Map<Integer, String> getApplyDisciplineMap() throws Exception {
		Map<Integer, String> disciplineMap = new LinkedHashMap<Integer, String>();
		try {
			disciplineMap = (Map<Integer, String>) getJdbcTemplate().query(MasterQueries.GET_APPLY_DISCIPLINE_MAP, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> disciplineMap = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						disciplineMap.put(rs.getInt("otm_test_pk"), rs.getString("otm_test_name"));
					}
					return disciplineMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return disciplineMap;
	}

	/**
	 * @author Pankaj Sh
	 * @return
	 * @throws Exception
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.nseit.generic.dao.CommonDao#getTestCenterMaster()
	 */
	public Map<Integer, String> getTestCenterMaster() throws Exception {
		try {
			return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.SELECT_TEST_CENTER, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> helpTestCenterMasterMap = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						helpTestCenterMasterMap.put(rs.getInt("otcm_test_centre_pk"), rs.getString("otcm_test_centre_name"));
					}
					return helpTestCenterMasterMap;
				}
			});
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
	}

	@Override
	public Map<Integer, Integer> getSelectedPreferredCenterMaster(Users loggedInUser, String flag) throws Exception {
		List<CandidateBean> getCandidateDetailsList = null;
		Map<Integer, Integer> selectedCenterMap = new HashMap<Integer, Integer>();
		int userId = (int) loggedInUser.getUserId();
		try {
			getCandidateDetailsList = getJdbcTemplate().query(CommonQueries.preferred_center_SELECTED, new RowMapper<CandidateBean>() {
				public CandidateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					CandidateBean candidateBean = new CandidateBean();
					candidateBean.setPreferredCenter(Integer.toString(rs.getInt(2)));
					return candidateBean;
				}
			}, new Object[] { userId, flag });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (getCandidateDetailsList != null && !getCandidateDetailsList.isEmpty()) {
			return selectedCenterMap;
		} else {
			return null;
		}
	}

	@Override
	public List<String> getSelectedTestCntrListforPreview(Users loggedInUser, String flag) throws Exception {
		int userId = (int) loggedInUser.getUserId();
		return (List<String>) getJdbcTemplate().query(CommonQueries.CENTER_NAME, new ResultSetExtractor() {
			@Override
			public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<String> selectedCentreList = new LinkedList<String>();
				while (rs.next()) {
					selectedCentreList.add(rs.getString("opc_center_fk"));
				}
				return selectedCentreList;
			}
		}, new Object[] { userId, flag });
	}

	public Map<Integer, String> getStatusMasterMap() throws GenericException {
		try {
			return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.SELECT_STATUS_MASTER, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> programMasterMap = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						programMasterMap.put(rs.getInt("osm_status_pk"), rs.getString("osm_status_desc"));
					}
					return programMasterMap;
				}
			});
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
	}

	public Map<Integer, String> getPaymentMasterMap() throws GenericException {
		try {
			return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.SELECT_PAYMENT_MASTER, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> paymentMasterMap = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						paymentMasterMap.put(rs.getInt("optm_payment_pk"), rs.getString("optm_payment_desc"));
					}
					return paymentMasterMap;
				}
			});
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
	}

	public Map<String, Integer> getPaymentMasterReverseMap() throws GenericException {
		try {
			return (Map<String, Integer>) getJdbcTemplate().query(CommonQueries.SELECT_PAYMENT_MASTER, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, Integer> paymentMasterMap = new LinkedHashMap<String, Integer>();
					while (rs.next()) {
						paymentMasterMap.put(rs.getString("optm_payment_desc"), rs.getInt("optm_payment_pk"));
					}
					return paymentMasterMap;
				}
			});
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
	}

	public Map<String, String> getStatusDescStatusMap() throws GenericException {
		try {
			return (Map<String, String>) getJdbcTemplate().query(CommonQueries.SELECT_STATUS_MASTER, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> statusDescStatusMap = new LinkedHashMap<String, String>();
					while (rs.next()) {
						statusDescStatusMap.put(rs.getString("osm_status_desc"), rs.getString("osm_status"));
					}
					return statusDescStatusMap;
				}
			});
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
	}

	public Map<Integer, String> getStatusMasterMapForReport() throws GenericException {
		try {
			return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.SELECT_STATUS_MASTER_FOR_REPORTS, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> programMasterMap = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						programMasterMap.put(rs.getInt("osm_status_pk"), rs.getString("osm_status_desc"));
					}
					return programMasterMap;
				}
			});
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
	}

	public int updateCandidateStatus(Users users, Integer statusId) throws GenericException {
		Long userId = 0L;
		String updatedBy = "";
		int updateStage = 0;
		if (users != null) {
			userId = users.getUserId();
			updatedBy = users.getUsername();
		}
		if (statusId == ConfigurationConstants.getInstance().getStatusKey(GenericConstants.REGISTRATION_SUBMITED)) {
			updateStage = writeJdbcTemplate.update(CommonQueries.UPDATE_CANDIDATE_STATUS_FORM_SUBMISSION, new Object[] { statusId, updatedBy, userId });
		} else if (statusId == 40) {
			updateStage = 1;
		} else {
			updateStage = writeJdbcTemplate.update(CommonQueries.UPDATE_CANDIDATE_STATUS, new Object[] { statusId, updatedBy, userId });
		}
		logger.info("User with "+updatedBy+" has updated stage to "+statusId+" and UpdateStage value is "+updateStage);
		try {
			insertCandidateAuditrail(users,"User Stage Update","User with "+userId+" has updated stage to "+statusId+" and UpdateStage value is "+updateStage);
		} catch (Exception e) {
			logger.error(e,e);
		}
		return updateStage;
	}

	public Map<String, Integer> getEmailStatusMap() throws Exception {
		return (Map<String, Integer>) getJdbcTemplate().query(CommonQueries.GET_EMAIL_STATUS, new ResultSetExtractor() {
			@Override
			public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, Integer> emailStatusMap = new LinkedHashMap<String, Integer>();
				while (rs.next()) {
					emailStatusMap.put(rs.getString("osm_status_desc"), rs.getInt("osm_email"));
				}
				return emailStatusMap;
			}
		});
	}

	public Map<String, Integer> getSmsStatusMap() throws Exception {
		return (Map<String, Integer>) getJdbcTemplate().query(CommonQueries.GET_SMS_STATUS, new ResultSetExtractor() {
			@Override
			public Map<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, Integer> emailStatusMap = new LinkedHashMap<String, Integer>();
				while (rs.next()) {
					emailStatusMap.put(rs.getString("osm_status_desc"), rs.getInt("osm_sms"));
				}
				return emailStatusMap;
			}
		});
	}

	public Map<String, String> getCandidatesDataForEmailSMSForPayment(String userId) throws Exception {
		String postAppliedFor = null;
		List list = null;
		list = getJdbcTemplate().queryForList(CommonQueries.SELECT_CANDIDATE_DATA_FOR_PAYMENT_ENC1, new Object[] { ENC_KEY, ENC_KEY, userId });
//		postAppliedFor = (String) getJdbcTemplate().queryForObject(CommonQueries.GET_POST_APPLIED2, String.class, new Object[] { userId });
		postAppliedFor = (String) getJdbcTemplate().queryForObject(CommonQueries.GET_POST_APPLIED_PAYMENT, String.class, new Object[] {});
		Map<String, String> rowData = null;
		if (list != null && list.size() > 0) {
			rowData = (Map<String, String>) list.get(0);
			try {
				rowData.put("mail_address", String.valueOf(rowData.get("mail_address")));
				rowData.put("mobile_no", String.valueOf(rowData.get("mobile_no")));
				rowData.put("amount", String.valueOf(rowData.get("amount")));
				rowData.put("payment_mode", String.valueOf(rowData.get("payment_mode")));
				rowData.put("discipline", postAppliedFor);
				rowData.put("regi_id", String.valueOf(rowData.get("regi_id")));
			} catch (Exception ex) {
				throw new GenericException(ex);
			}
		}
		return rowData;
	}

	@Override
	public Map<String, String> getStaticDataMap() throws Exception {
		try {
			String GET_STATIC_DATA = "SELECT opc_page_key, opc_page_value FROM oes_page_content ORDER BY opc_page_id ASC";
			return (Map<String, String>) getJdbcTemplate().query(GET_STATIC_DATA, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> staticDataMap = new LinkedHashMap<String, String>();
					while (rs.next()) {
						staticDataMap.put(rs.getString("opc_page_key"), rs.getString("opc_page_value"));
					}
					return staticDataMap;
				}
			});
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	public Map<String, Integer> getDateWindowMap() throws Exception {
		Map<String, Integer> dateWindowMap = new LinkedHashMap<String, Integer>();
		try {
			dateWindowMap = (Map<String, Integer>) getJdbcTemplate().query(MasterQueries.GET_DATE_WINDOW_DATA, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, Integer> dateWindowMap = new LinkedHashMap<String, Integer>();
					while (rs.next()) {
						dateWindowMap.put(rs.getString("odw_description"), rs.getInt("odw_date_window_pk"));
					}
					return dateWindowMap;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return dateWindowMap;
	}

	public Map<String, String> getCandidatesDataForEmailSMSForAdmitCard(String userId, Integer attempt) throws Exception {
		List list = getJdbcTemplate().queryForList(CommonQueries.GET_CANDIDATE_DATA_FOR_ADMIT_CARD, new Object[] { userId, attempt });
		Map<String, String> rowData = null;
		if (list != null && list.size() > 0) {
			rowData = (Map<String, String>) list.get(0);
			try {
				rowData.put("attempt", String.valueOf(rowData.get("attempt")));
				// rowData.put("user_password",
				// DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rowData.get("user_password")));
			} catch (Exception ex) {
				throw new GenericException(ex);
			}
		}
		return rowData;
	}

	public Map<String, String> getCandidatesDataForNewReg(String userId) throws Exception {
		List list = null;
		Map<String, String> rowData = null;
		try {
			list = getJdbcTemplate().queryForList(CommonQueries.GET_CANDIDATE_DETAILS_FOR_NEW_REG_ENC1, new Object[] { ENC_KEY, ENC_KEY, userId });
			if (list != null && list.size() > 0) {
				rowData = (Map<String, String>) list.get(0);
				rowData.put("user_id", userId);
				rowData.put("user_password", DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rowData.get("user_password")));
				rowData.put("date_of_reg", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
				rowData.put("login_url", ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.LOGIN_URL));
			}
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return rowData;
	}

	public int[] insertEmailSMSDetailsForNewReg(List<EmailSMSTransactionBean> emailSMS, RegistrationBean registrationBean) {
		int[] updateCounts = null;
		updateCounts = writeJdbcTemplate.batchUpdate(CommonQueries.INSERT_EMAIL_SMS_DETAIL, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pStatement, int currBatchIndex) throws SQLException {
				pStatement.setString(1, emailSMS.get(currBatchIndex).getEmailSubject());
				pStatement.setString(2, emailSMS.get(currBatchIndex).getSmsEmailContent());
				pStatement.setString(3, emailSMS.get(currBatchIndex).getCandidateMailAddress().toString());
				pStatement.setString(4, emailSMS.get(currBatchIndex).getSmsMailFlag());
				pStatement.setString(5, "N");
				pStatement.setString(6, "A");
				pStatement.setString(7, registrationBean.getUserName());
				pStatement.setInt(8, Integer.parseInt(emailSMS.get(currBatchIndex).getStatusPk()));
			}

			@Override
			public int getBatchSize() {
				return emailSMS.size();
			}
		});
		return updateCounts;
	}

	public Map<String, String> getCandidatesDataForEmailSMSForPaymentFailure(String userId) throws Exception {
		List list = getJdbcTemplate().queryForList(CommonQueries.GET_CANDIDATE_DATA_FOR_TRANSACTION_FAILURE, new Object[] { userId });
		Map<String, String> rowData = null;
		if (list != null && list.size() > 0) {
			rowData = (Map<String, String>) list.get(0);
			try {
				// rowData.put("user_password",
				// DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rowData.get("user_password")));
				rowData.put("amount", String.valueOf(rowData.get("amount")));
			} catch (Exception ex) {
				throw new GenericException(ex);
			}
		}
		return rowData;
	}

	public UserDetailsBean getCandidateDetailsForTestCenter(Long userPk) throws Exception {
		UserDetailsBean testCenterUserDtls = null;
		try {
			testCenterUserDtls = (UserDetailsBean) getJdbcTemplate().queryForObject(CommonQueries.GET_CANDIDATE_DETAILS_FOR_TEST_CENTER,
					new BeanPropertyRowMapper(UserDetailsBean.class), new Object[] { userPk });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return testCenterUserDtls;
	}

	public Map<String, String> getCandidatesDataForEmailSMSForTestCenterAllocation(String userId) throws Exception {
		List list = getJdbcTemplate().queryForList(CommonQueries.GET_CANDIDATE_DETAILS_FOR_TEST_CENTER_EMAIL_SMS, new Object[] { userId });
		Map<String, String> rowData = null;
		if (list != null && list.size() > 0) {
			rowData = (Map<String, String>) list.get(0);
			try {
				// rowData.put("user_password",
				// DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rowData.get("user_password")));
				// rowData.put("amount", String.valueOf(rowData.get("amount")));
			} catch (Exception ex) {
				throw new GenericException(ex);
			}
		}
		return rowData;
	}

	public Map<String, String> getAcademicMasterMap() throws Exception {
		Map<String, String> academicMasterMap = new LinkedHashMap<String, String>();
		try {
			academicMasterMap = (Map<String, String>) getJdbcTemplate().query(CommonQueries.GET_ACADEMIC_TYPE_DETAILS_DATA, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> academicMasterMapTmp = new LinkedHashMap<String, String>();
					while (rs.next()) {
						academicMasterMapTmp.put(rs.getString("oatm_acdm_pk"), rs.getString("oatm_acdm_code"));
					}
					return academicMasterMapTmp;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return academicMasterMap;
	}

	public Map<String, String> getAcademicMandatoryMap() throws Exception {
		Map<String, String> academicMasterMap = new LinkedHashMap<String, String>();
		try {
			academicMasterMap = (Map<String, String>) getJdbcTemplate().query(CommonQueries.GET_ACADEMIC_MANDATORY_DETAILS_DATA, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> academicMasterMapTmp = new LinkedHashMap<String, String>();
					while (rs.next()) {
						academicMasterMapTmp.put(rs.getString("oatm_acdm_pk"), rs.getString("oatm_mandatory"));
					}
					return academicMasterMapTmp;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return academicMasterMap;
	}

	public Map getDegreeMappingDetails() throws Exception {
		Map<Integer, String> degreeDetailsMap = new MultiValueMap();
		try {
			degreeDetailsMap = (Map) getJdbcTemplate().query(CommonQueries.GET_DEGREE_MAPPING_DETAILS, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map degreeDetailsMapTmp = new MultiValueMap();
					while (rs.next()) {
						degreeDetailsMapTmp.put(rs.getInt("odmd_degree_fk"), String.valueOf(rs.getInt("odmd_acdm_fk")));
					}
					return degreeDetailsMapTmp;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return degreeDetailsMap;
	}

	public Map<String, String> getAcademicMasterReverseMap() throws Exception {
		Map<String, String> academicMasterMap = new LinkedHashMap<String, String>();
		try {
			academicMasterMap = (Map<String, String>) getJdbcTemplate().query(CommonQueries.GET_ACADEMIC_TYPE_DETAILS_REVERSE_DATA, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> academicMasterMapTmp = new LinkedHashMap<String, String>();
					while (rs.next()) {
						academicMasterMapTmp.put(rs.getString("oatm_acdm_code"), rs.getString("oatm_acdm_pk"));
					}
					return academicMasterMapTmp;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return academicMasterMap;
	}

	public int[] insertEmailSMSDetailsForForgotUserId(List<EmailSMSTransactionBean> emailSMS, String loggedInUserName) throws Exception {
		return writeJdbcTemplate.batchUpdate(CommonQueries.INSERT_EMAIL_SMS_DETAIL, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pStatement, int currBatchIndex) throws SQLException {
				pStatement.setString(1, emailSMS.get(currBatchIndex).getEmailSubject());
				pStatement.setString(2, emailSMS.get(currBatchIndex).getSmsEmailContent());
				pStatement.setString(3, emailSMS.get(currBatchIndex).getCandidateMailAddress().toString());
				pStatement.setString(4, emailSMS.get(currBatchIndex).getSmsMailFlag());
				pStatement.setString(5, "N");
				pStatement.setString(6, "A");
				pStatement.setString(7, loggedInUserName);
				pStatement.setInt(8, Integer.parseInt(emailSMS.get(currBatchIndex).getStatusPk()));
			}

			@Override
			public int getBatchSize() {
				return emailSMS.size();
			}
		});
	}

	public int[] insertEmailSMSDetailsForForgotPassword(List<EmailSMSTransactionBean> emailSMS, String loggedInUserName) throws Exception {
		return writeJdbcTemplate.batchUpdate(CommonQueries.INSERT_EMAIL_SMS_DETAIL, new BatchPreparedStatementSetter() {
			public void setValues(PreparedStatement pStatement, int currBatchIndex) throws SQLException {
				pStatement.setString(1, emailSMS.get(currBatchIndex).getEmailSubject());
				pStatement.setString(2, emailSMS.get(currBatchIndex).getSmsEmailContent());
				pStatement.setString(3, emailSMS.get(currBatchIndex).getCandidateMailAddress().toString());
				pStatement.setString(4, emailSMS.get(currBatchIndex).getSmsMailFlag());
				pStatement.setString(5, "N");
				pStatement.setString(6, "A");
				pStatement.setString(7, loggedInUserName);
				pStatement.setInt(8, Integer.parseInt(emailSMS.get(currBatchIndex).getStatusPk()));
			}

			@Override
			public int getBatchSize() {
				return emailSMS.size();
			}
		});
	}

	public Map<String, String> getCandidatesDataForForgotUserId(String userId) throws Exception {
		List list = getJdbcTemplate().queryForList(CommonQueries.GET_CANDIDATE_DATA_FOR_FORGOT_USER_ID_ENC1, new Object[] { ENC_KEY, ENC_KEY, userId });
		Map<String, String> rowData = null;
		if (list != null && list.size() > 0) {
			rowData = (Map<String, String>) list.get(0);
			try {
				// rowData.put("user_password",
				// DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rowData.get("user_password")));
			} catch (Exception ex) {
				throw new GenericException(ex);
			}
		}
		return rowData;
	}

	public Map<String, String> getCandidatesDataForForgotPassword(String userId) throws Exception {
		List list = getJdbcTemplate().queryForList(CommonQueries.GET_CANDIDATE_DATA_FOR_FORGOT_PASSWORD_ENC1, new Object[] { ENC_KEY, ENC_KEY, userId });
		Map<String, String> rowData = null;
		if (list != null && list.size() > 0) {
			rowData = (Map<String, String>) list.get(0);
			try {
				rowData.put("user_id", userId);
				rowData.put("user_password", DESEncrypter.getInstance(CommonUtil.getPasswordEncryptionKey()).decrypt(rowData.get("user_password")));
			} catch (Exception ex) {
				throw new GenericException(ex);
			}
		}
		return rowData;
	}

	public Map<String, String> getReferenceValueActiveMap() throws Exception {
		return (Map<String, String>) getJdbcTemplate().query(CommonQueries.GET_REFERENCE_VALUE_ACTIVE_STATUS, new ResultSetExtractor() {
			@Override
			public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<String, String> activityMap = new LinkedHashMap<String, String>();
				while (rs.next()) {
					activityMap.put(rs.getString("orvm_reference_value"), rs.getString("orvm_status"));
				}
				return activityMap;
			}
		});
	}

	public Map<Integer, String> getDegreeMap() throws Exception {
		Map<Integer, String> degreeMap = new LinkedHashMap<Integer, String>();
		try {
			degreeMap = (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.GET_DEGREE_LIST, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> degreeMapTmp = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						degreeMapTmp.put(rs.getInt("odm_degree_pk"), rs.getString("odm_degree_desc"));
					}
					return degreeMapTmp;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return degreeMap;
	}

	public Map<Integer, Map<Integer, String>> getUniversityMap() throws Exception {
		Map<Integer, Map<Integer, String>> acdmUnivMapping = new LinkedHashMap<Integer, Map<Integer, String>>();
		Map<Integer, String> univMap = new LinkedHashMap<Integer, String>();
		List<Integer> acdmFkList = new ArrayList<Integer>();
		try {
			acdmFkList = (List<Integer>) getJdbcTemplate().query(CommonQueries.GET_DEGREE_MAPPING_DETAILS, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Integer> acdmList = new ArrayList<Integer>();
					while (rs.next()) {
						acdmList.add(rs.getInt("odmd_acdm_fk"));
					}
					return acdmList;
				}
			});
			for (int i = 0; i < acdmFkList.size(); i++) {
				univMap = (Map<Integer, String>) getJdbcTemplate().query(MasterQueries.GET_ACDM_UNIV_MAPPING, new ResultSetExtractor() {
					@Override
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<Integer, String> univList = new LinkedHashMap<Integer, String>();
						while (rs.next()) {
							univList.put(rs.getInt("oum_university_pk"), rs.getString("oum_university_desc"));
						}
						return univList;
					}
				}, new Object[] { acdmFkList.get(i) });
				acdmUnivMapping.put(acdmFkList.get(i), univMap);
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return acdmUnivMapping;
	}

	public UploadManagementBean getPostDetails(Users loggedInUser) throws Exception {
		List<UploadManagementBean> uploadlist = new ArrayList<UploadManagementBean>();
		try {
			uploadlist = (List<UploadManagementBean>) getJdbcTemplate().query(CommonQueries.GET_CANDIDATE_DASHBOARD_FOR_WRITTEN_TEST, new RowMapper<UploadManagementBean>() {
				@Override
				public UploadManagementBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
					UploadManagementBean upload = new UploadManagementBean();
					upload.setTestDate(rs.getString("oed_test_date"));
					upload.setTestTime(rs.getString("oed_test_start_time") + " - " + rs.getString("oed_test_end_time"));
					upload.setTestCenterName(rs.getString("otcm_test_centre_name"));
					upload.setExamStatus(rs.getString("oed_exam_status"));
					upload.setMarks(rs.getString("oed_marks"));
					upload.setEligible(rs.getString("oed_eligible_for_field_test"));
					upload.setTestcity(rs.getString("ocm_city_name"));
					return upload;
				}
			}, new Object[] { loggedInUser.getUserFk(), Integer.parseInt(loggedInUser.getDisciplineID()) });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (uploadlist.size() > 0) {
			return uploadlist.get(0);
		} else {
			return null;
		}
	}

	public UploadManagementBean getPostEntrollmentTestDetails(Users loggedInUser) throws Exception {
		List<UploadManagementBean> uploadlist = new ArrayList<UploadManagementBean>();
		try {
			uploadlist = (List<UploadManagementBean>) getJdbcTemplate().query(CommonQueries.GET_CANDIDATE_DASHBOARD_FOR_OTHER_TEST, new RowMapper<UploadManagementBean>() {
				@Override
				public UploadManagementBean mapRow(ResultSet rs, int rowNumber) throws SQLException {
					UploadManagementBean upload = new UploadManagementBean();
					upload.setFieldTestStatus(rs.getString("oetd_field_test_status"));
					// upload.setTestTime(rs.getString("oed_test_start_time")
					// +" - "+rs.getString("oed_test_end_time"));
					upload.setFieldTestCity(rs.getString("oetd_test_centre_fk_for_field"));
					upload.setFieldTestDate(rs.getString("oetd_test_date_for_field"));
					upload.setFieldTestReportingTime(rs.getString("oetd_reporting_time_for_field"));
					upload.setInterviewStatus(rs.getString("oetd_interview_status"));
					upload.setInterviewLocation(rs.getString("oetd_interview_location"));
					upload.setInterviewDate(rs.getString("oetd_test_date_for_interview"));
					upload.setInterviewReportTime(rs.getString("oetd_reporting_time_for_interview"));
					upload.setMedicalStatus(rs.getString("oetd_medical_test_status"));
					upload.setMedicalTestLocation(rs.getString("oetd_medical_location"));
					upload.setMedicalTestDate(rs.getString("oetd_test_date_for_medical"));
					upload.setScoreForFieldTest(rs.getString("oetd_test_marks_for_field"));
					upload.setScoreForInterview(rs.getString("oetd_marks_for_interview"));
					return upload;
				}
			}, new Object[] { loggedInUser.getUserFk(), Integer.parseInt(loggedInUser.getDisciplineID()) });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (uploadlist.size() > 0) {
			return uploadlist.get(0);
		} else {
			return null;
		}
	}

	public int insertCandidateAuditrail(Users users, String action, String auditTrail) throws Exception {
		if (StringUtils.isBlank(ENC_KEY))
			ENC_KEY = ConfigurationConstants.getInstance().getENC_KEY();
		
		return writeJdbcTemplate.update(CommonQueries.INSERT_AUDITRAIL, (PreparedStatementSetter) ps -> {
			ps.setLong(1, users.getUserId());
			ps.setString(2, users.getRemoteIp());
			ps.setString(3, action);
			ps.setString(4, auditTrail);
			ps.setString(5, ENC_KEY);
		});
	}

	@Override
	public Map<Integer, String> getRecrumentStageMap() throws GenericException {
		return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.GET_STAGE_MAP, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> activityMap = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					activityMap.put(rs.getInt("orvm_reference_pk"), rs.getString("orvm_reference_value"));
				}
				return activityMap;
			}
		});
	}

	@Override
	public Map<Integer, String> getRecrumentCategoryMap1() throws GenericException {
		return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.GET_CATEGORY_MAP1, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> activityMap = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					activityMap.put(rs.getInt("orvm_reference_pk"), rs.getString("orvm_reference_value"));
				}
				return activityMap;
			}
		});
	}

	@Override
	public Map<Integer, String> getRecrumentCategoryMap2() throws GenericException {
		return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.GET_CATEGORY_MAP2, new ResultSetExtractor() {
			@Override
			public Map<Integer, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
				Map<Integer, String> activityMap = new LinkedHashMap<Integer, String>();
				while (rs.next()) {
					activityMap.put(rs.getInt("orvm_reference_pk"), rs.getString("orvm_reference_value"));
				}
				return activityMap;
			}
		});
	}

	@Override
	public String getTransactionUniqueNumber() throws Exception {
		String txnNumber = "";
		try {
			txnNumber = (String) getJdbcTemplate().queryForObject(CommonQueries.GET_TXN_NUMBER, String.class);
		} catch (Exception e) {
			txnNumber = "";
		}
		return txnNumber;
	}

	public Map<Integer, Map<Integer, String>> getAcademicMapping() throws Exception {
		Map<Integer, Map<Integer, String>> AcademicMapping = new LinkedHashMap<Integer, Map<Integer, String>>();
		Map<Integer, String> academicMap = new LinkedHashMap<Integer, String>();
		List<Integer> academicList = new ArrayList<Integer>();
		try {
			academicList = (List<Integer>) getJdbcTemplate().query(MasterQueries.GET_AcademicID, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					List<Integer> countryList = new ArrayList<Integer>();
					while (rs.next()) {
						countryList.add(rs.getInt("oatm_acdm_pk"));
					}
					return countryList;
				}
			});
			for (int i = 0; i < academicList.size(); i++) {
				academicMap = (Map<Integer, String>) getJdbcTemplate().query(MasterQueries.GET_AcademicList, new ResultSetExtractor() {
					@Override
					public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
						Map<Integer, String> getPresentUnitMap = new LinkedHashMap<Integer, String>();
						while (rs.next()) {
							getPresentUnitMap.put(rs.getInt("osdm_sub_degree_pk"), rs.getString("osdm_sub_degree_desc"));
						}
						return getPresentUnitMap;
					}
				}, new Object[] { academicList.get(i) });
				AcademicMapping.put(academicList.get(i), academicMap);
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return AcademicMapping;
	}

	public Map<String, String> getCandidateLabelMap() throws Exception {
		try {
			return (Map<String, String>) getJdbcTemplate().query(MasterQueries.GET_Label_Map, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<String, String> getPresentRankMap = new LinkedHashMap<String, String>();
					while (rs.next()) {
						getPresentRankMap.put(rs.getString("odm_abbreviation"), rs.getString("odm_name"));
					}
					return getPresentRankMap;
				}
			});
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	public int insertOTPDetails(final EmailSMSTransactionBean emailSMSBean, final String username) throws Exception {
		try {
			String toAddressStr = emailSMSBean.getToAddress().toString();
			String ccAddressStr = emailSMSBean.getCcAddress() != null ? emailSMSBean.getCcAddress().toString() : null;
			// String bccAddressStr = emailSMSBean.getBccAddress() != null ?
			// emailSMSBean.getBccAddress().toString() : null;
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			toAddressStr = toAddressStr.substring(1, toAddressStr.length() - 1);
			if (ccAddressStr != null)
				ccAddressStr = ccAddressStr.substring(1, ccAddressStr.length() - 1);
			/*
			 * if(bccAddressStr != null) bccAddressStr = bccAddressStr.substring(1, bccAddressStr.length()-1);
			 */
			final String to = toAddressStr;
			writeJdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement statement = con.prepareStatement(CommonQueries.INSERT_EMAIL_SMS_DETAIL, Statement.RETURN_GENERATED_KEYS);
					statement.setString(1, emailSMSBean.getEmailSubject());
					statement.setString(2, emailSMSBean.getSmsEmailContent());
					statement.setString(3, to);
					statement.setString(4, emailSMSBean.getSmsMailFlag());
					statement.setString(5, "N");
					statement.setString(6, "A");
					statement.setString(7, to);
					statement.setInt(8, Integer.parseInt(emailSMSBean.getStatusPk()));
					return statement;
				}
			}, holder);
			// new Object[] {emailSMSBean.getEmailSubject(),
			// emailSMSBean.getSmsEmailContent(), toAddressStr, ccAddressStr,
			// emailSMSBean.getSmsMailFlag(), "N", "A",
			// username,1,emailSMSBean.getStatusPk()});
			List h1 = holder.getKeyList();
			/*
			 * ListOrderedMap a = (ListOrderedMap) h1.get(0); BigDecimal pk = (BigDecimal) a.get("osetm_sms_email_tran_pk"); String pk1 = pk.toString(); return
			 * Integer.parseInt(pk1);
			 */
		} catch (Exception ex) {
			throw new Exception(ex);
		}
		return 1;
	}

	public int insertOTPDetails1(final EmailSMSTransactionBean emailSMSBean, final String username) {
		String toAddressStr = emailSMSBean.getToAddress().toString();
		String ccAddressStr = emailSMSBean.getCcAddress() != null ? emailSMSBean.getCcAddress().toString() : null;
		// String bccAddressStr = emailSMSBean.getBccAddress() != null ?
		// emailSMSBean.getBccAddress().toString() : null;
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		toAddressStr = toAddressStr.substring(1, toAddressStr.length() - 1);
		if (ccAddressStr != null)
			ccAddressStr = ccAddressStr.substring(1, ccAddressStr.length() - 1);
		/*
		 * if(bccAddressStr != null) bccAddressStr = bccAddressStr.substring(1, bccAddressStr.length()-1);
		 */
		final String to = toAddressStr;
		writeJdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				PreparedStatement statement = con.prepareStatement(CommonQueries.INSERT_EMAIL_SMS_DETAIL, Statement.RETURN_GENERATED_KEYS);
				statement.setString(1, emailSMSBean.getEmailSubject());
				statement.setString(2, emailSMSBean.getSmsEmailContent());
				statement.setString(3, to);
				statement.setString(4, null);
				statement.setString(5, emailSMSBean.getSmsMailFlag());
				statement.setString(6, "N");
				statement.setString(7, "A");
				statement.setString(8, username);
				statement.setInt(9, 1);
				statement.setString(10, emailSMSBean.getStatusPk());
				statement.setString(11, emailSMSBean.getFromAddress());
				return statement;
			}
		}, holder);
		// new Object[] {emailSMSBean.getEmailSubject(),
		// emailSMSBean.getSmsEmailContent(), toAddressStr, ccAddressStr,
		// emailSMSBean.getSmsMailFlag(), "N", "A",
		// username,1,emailSMSBean.getStatusPk()});
		List h1 = holder.getKeyList();
		ListOrderedMap a = (ListOrderedMap) h1.get(0);
		BigDecimal pk = (BigDecimal) a.get("osetm_sms_email_tran_pk");
		String pk1 = pk.toString();
		return Integer.parseInt(pk1);
	}

	@Override
	public List<CandidateBean> getCandidateAppliedPost(final Users loggedInUser, final int status) throws Exception {
		List<CandidateBean> getCandidatePostList = null;
		try {
			getCandidatePostList = getJdbcTemplate().query(CandidateQueries.GET_CANDIDATE_COURSE_APPLIED_ENC1, new RowMapper<CandidateBean>() {
				public CandidateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					CandidateBean candidateBean = new CandidateBean();
					candidateBean.setDisciplineId(1);
					candidateBean.setDisciplineType(rs.getString("ocd_discipline"));
					candidateBean.setStatus(rs.getString("payment_status"));
					candidateBean.setStageUpdate(rs.getString("stage_update"));
					candidateBean.setOpd_fk(rs.getInt("opd_payment_fk"));
					candidateBean.setOpd_fk_desc("Online");
					return candidateBean;
				}
			}, new Object[] { loggedInUser.getUserFk() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return getCandidatePostList;
	}

	@Override
	public int updateForgotPassword(String userid, String key) throws Exception {
		int emailCount = 0;
		try {
			emailCount = writeJdbcTemplate.update(UserQueries.UPDATE_FORGOT_PASSWORD_KEY, new Object[] { key, userid });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return emailCount;
	}

	public Long getUserFK(String customerId, String txnReferenceNo) throws Exception {
		Long userId;
		try {
			userId = (Long) getJdbcTemplate().queryForObject(UserQueries.GET_USER_FK_FOR_PAY_APP, Long.class, new Object[] { customerId, txnReferenceNo });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return userId;
	}

	public int updateEmailSmsOTPDetails(final EmailSMSTransactionBean emailSMSBean, final String username) throws Exception {
		try {
			String toAddressStr = emailSMSBean.getToAddress().toString();
			String ccAddressStr = emailSMSBean.getCcAddress() != null ? emailSMSBean.getCcAddress().toString() : null;
			// String bccAddressStr = emailSMSBean.getBccAddress() != null ?
			// emailSMSBean.getBccAddress().toString() : null;
			GeneratedKeyHolder holder = new GeneratedKeyHolder();
			toAddressStr = toAddressStr.substring(1, toAddressStr.length() - 1);
			if (ccAddressStr != null)
				ccAddressStr = ccAddressStr.substring(1, ccAddressStr.length() - 1);
			/*
			 * if(bccAddressStr != null) bccAddressStr = bccAddressStr.substring(1, bccAddressStr.length()-1);
			 */
			final String to = toAddressStr;
			writeJdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					PreparedStatement statement = con.prepareStatement(CommonQueries.UPDATE_EMAIL_SMS_DETAIL, Statement.RETURN_GENERATED_KEYS);
					statement.setString(1, emailSMSBean.getSmsEmailContent());
					statement.setString(2, to);
					statement.setString(3, to);
					return statement;
				}
			}, holder);
			// new Object[] {emailSMSBean.getEmailSubject(),
			// emailSMSBean.getSmsEmailContent(), toAddressStr, ccAddressStr,
			// emailSMSBean.getSmsMailFlag(), "N", "A",
			// username,1,emailSMSBean.getStatusPk()});
			List h1 = holder.getKeyList();
			ListOrderedMap a = (ListOrderedMap) h1.get(0);
			BigDecimal pk = (BigDecimal) a.get("osetm_sms_email_tran_pk");
			String pk1 = pk.toString();
			return Integer.parseInt(pk1);
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public boolean insertCheckboxValue(String checkBoxValue, long userId) throws Exception {
		// TODO Auto-generated method stub
		return writeJdbcTemplate.update(UserQueries.UPDATE_USER_CHECKBOX_STATUS, new Object[] { checkBoxValue, userId }) > 0;
	}

	public String getCandidateStatus(String userId) {
		return (String) getJdbcTemplate().queryForObject(CommonQueries.SELECT_CANDIDATE_STATUS, String.class, new Object[] { userId });
	}

	@Override
	public int getTrainingCentreFK(long userId) throws Exception {
		int trainingCentreFk = 0;
		try {
			trainingCentreFk = getJdbcTemplate().queryForObject(CommonQueries.GET_TRAINING_CENTRE_FK, Integer.class, new Object[] { userId });
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return trainingCentreFk;
	}

	@Override
	public Map<Integer, String> getNativityMap() throws Exception {
		try {
			return (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.GET_NATIVITYLIST, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> nativityMap = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						nativityMap.put(rs.getInt("onm_native_pk"), rs.getString("native_drop_down_values"));
					}
					return nativityMap;
				}
			});
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	@Override
	public Boolean getEligibilityToEditSubjectApplied(long userId) throws Exception {
		Boolean eligibilityFlag = false;
		String FlagVal = "";
		FlagVal = (String) getJdbcTemplate().queryForObject(CommonQueries.GET_ELIGIBILITY_FLAG, String.class, new Object[] { userId });
		if (FlagVal.equals("true"))
			eligibilityFlag = true;
		return eligibilityFlag;
	}

	public String getPostAppliedFor(Long usrId) throws Exception {
		String PostApplied = "";
		PostApplied = (String) getJdbcTemplate().queryForObject(CommonQueries.GET_POST_APPLIED, String.class, new Object[] { usrId });
		return PostApplied;
	}

	@Override
	public Map<String, String> getCandidatesDataAutoApprovePayment(String userId) throws Exception {
		List list = getJdbcTemplate().queryForList(CommonQueries.GET_CANDIDATE_DATA_FOR_EXEMPTED, new Object[] { ENC_KEY, userId });
		Map<String, String> rowData = null;
		if (list != null && list.size() > 0) {
			rowData = (Map<String, String>) list.get(0);
			try {
				rowData.put("category", String.valueOf(rowData.get("ocd_category_fk")));
				rowData.put("gender", String.valueOf(rowData.get("oum_genderfk")));
				/* rowData.put("disabled", String.valueOf(rowData.get("ocd_is_handicaped"))); */
				rowData.put("dobvalue", String.valueOf(rowData.get("ocd_date_of_birth")));
				rowData.put("isExserviceman", String.valueOf(rowData.get("ocd_ex_serviceman")));
				rowData.put("dateOfEnlistment", String.valueOf(rowData.get("ocd_esm_dt_of_enlistment")));
				rowData.put("dateOfDischarge", String.valueOf(rowData.get("ocd_esm_dt_of_discharge")));
				rowData.put("discipline", String.valueOf(rowData.get("ocd_discipline")));
				rowData.put("testCenter1", String.valueOf(rowData.get("ocd_test_center1")));
				rowData.put("testCenter2", String.valueOf(rowData.get("ocd_test_center2")));
				rowData.put("testCenter3", String.valueOf(rowData.get("ocd_test_center3")));
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new GenericException(ex);
			}
		}
		return rowData;
	}

	@Override
	public Map<Integer, String> getSubDegreeMap() throws Exception {
		Map<Integer, String> degreeMap = new LinkedHashMap<Integer, String>();
		try {
			degreeMap = (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.GET_SUB_DEGREE_LIST, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
					Map<Integer, String> degreeMapTmp = new LinkedHashMap<Integer, String>();
					while (rs.next()) {
						degreeMapTmp.put(rs.getInt("osdm_sub_degree_pk"), rs.getString("osdm_sub_degree_desc"));
					}
					return degreeMapTmp;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return degreeMap;
	}
	
	// for Covid Duty Certificate page

		@Override
		public Map<Integer, String> getSignedByDataMap() throws Exception {
			Map<Integer, String> signedByDataMap = new LinkedHashMap<Integer, String>();
			try {
				signedByDataMap = (Map<Integer, String>) getJdbcTemplate().query(CommonQueries.GET_SIGNED_BY_LIST,
						new ResultSetExtractor() {

							@Override
							public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
								Map<Integer, String> signedByDataMapTmp = new LinkedHashMap<Integer, String>();

								while (rs.next()) {
									signedByDataMapTmp.put(rs.getInt("osbd_institution_type_fk"),
											rs.getString("osbd_signed_by") + "," + rs.getString("osbd_counter_signed_by"));
								}
								return signedByDataMapTmp;
							}
						});
			} catch (Exception e) {
				throw new GenericException(e);
			}
			return signedByDataMap;
		}

	/*
	 * @Override public Boolean getcandidatEditStatus(long userId) throws Exception { Boolean flagval=false; String editflagval=
	 * (String)getJdbcTemplate().queryForObject(CommonQueries.GET_USER_EDIT_FLAG, new Object[] { userId }, String.class); if(editflagval.equals("Y")) { flagval=true; } return
	 * flagval; }
	 */
		
		@Override
		public String getCandidateDetailsByLoginId(String loginId) {
			final String email = "";
			try {
					return getJdbcTemplate().query(UserQueries.GET_DETAILS_BY_LOGIN_ID,
							(PreparedStatementSetter) preparedStatement -> {
								preparedStatement.setString(1, ConfigurationConstants.getInstance().getENC_KEY());
								preparedStatement.setString(2, loginId);
							}, (ResultSetExtractor<String>) resultSet -> {
								if (resultSet.next()) {
									return resultSet.getString("OUM_EMAIL_ID");
								}
								return email;
							});
			} catch (Exception e) {
				logger.info("inside catch block email not found with user id : {}  for forgot password ",loginId);
			}
			return email;
		}

}
