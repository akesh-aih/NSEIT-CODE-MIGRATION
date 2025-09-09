
package com.nseit.payment.dao.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.nseit.generic.dao.BaseDAO;
import com.nseit.generic.models.AddressBean;
import com.nseit.generic.models.CandidateBean;
import com.nseit.generic.models.CandidateMgmtBean;
import com.nseit.generic.models.CommonBean;
import com.nseit.generic.models.EnrollmentBean;
import com.nseit.generic.models.Pagination;
import com.nseit.generic.models.PaymentApprovalBean;
import com.nseit.generic.models.PaymentBean;
import com.nseit.generic.models.PersonalDetailsBean;
import com.nseit.generic.models.Users;
import com.nseit.generic.queries.CandidateQueries;
import com.nseit.generic.queries.CommonQueries;
import com.nseit.generic.util.CommonUtil;
import com.nseit.generic.util.ConfigurationConstants;
import com.nseit.generic.util.GenericConstants;
import com.nseit.generic.util.GenericException;
import com.nseit.generic.util.LoggerHome;
import com.nseit.generic.util.ValidatorUtil;
import com.nseit.payment.dao.PaymentDAO;
import com.nseit.payment.models.AtomResponseBean;
import com.nseit.payment.models.OesBankMaster;
import com.nseit.payment.models.PaymentOnlineBean;
import com.nseit.payment.models.SBIResponseBean;
import com.nseit.payment.models.SBIePayResponseBean;
import com.nseit.payment.models.SafexResponseBean;
import com.nseit.payment.models.TechProcessResponseBean;
import com.nseit.payment.queries.PaymentOfflineQueries;
import com.nseit.payment.queries.PaymentQueries;

public class PaymentDaoImpl extends BaseDAO implements PaymentDAO {

	private String ENC_KEY = ConfigurationConstants.getInstance().getENC_KEY();

	private JdbcTemplate writeJdbcTemplate;

	public void setWriteJdbcTemplate(JdbcTemplate writeJdbcTemplate) {
		this.writeJdbcTemplate = writeJdbcTemplate;
	}

	public List<PaymentBean> getBankDetails() throws Exception {
		List<PaymentBean> bankNameList = null;
		try {
			bankNameList = getJdbcTemplate().query(PaymentOfflineQueries.GET_BANK_MASTER, new RowMapper<PaymentBean>() {
				public PaymentBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentBean paymentBean = new PaymentBean();
					// paymentBean.setBankID(rs.getString("BANK_PK"));
					paymentBean.setBankName(rs.getString("BANK_NAME"));
					return paymentBean;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bankNameList;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<OesBankMaster> getBankList() throws Exception {
		return getJdbcTemplate().query(PaymentQueries.BANK_DETAILS, new BeanPropertyRowMapper(OesBankMaster.class));
	}

	public String getFeesDetail(String userId) throws Exception {
		return (String) getJdbcTemplate().queryForObject(PaymentQueries.GET_FEES_DETAIL, String.class, new Object[] { Integer.parseInt(userId) });
	}

	// Here get cityDetails
	// Note Add Extra method
	public List<PaymentBean> getCityDetails() throws Exception {
		List<PaymentBean> cityList = null;
		cityList = getJdbcTemplate().queryForList(PaymentOfflineQueries.GET_CITY_MASTER, PaymentBean.class);
		return cityList;
	}

	public List<PaymentBean> getOnlinePaymentDetails(Users users) throws Exception {
		List<PaymentBean> onLinePaymentDetailsList = null;
		try {
			onLinePaymentDetailsList = getJdbcTemplate().query(PaymentOfflineQueries.GET_PG_DETAILS_FOR_ENROLLMENT, new RowMapper<PaymentBean>() {
				public PaymentBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentBean paymentBean = new PaymentBean();
					paymentBean.setTransactionDate(rs.getString("OPD_TRANSACTION_DATE"));
					paymentBean.setTransactionNo(rs.getString("OPD_TRANSACTION_NO"));
					paymentBean.setAmount(rs.getString("OPD_AMOUNT"));
					paymentBean.setCreatedDate(rs.getString("OPD_CREATED_DATE"));
					paymentBean.setValidatedStatus(rs.getString("opd_validated_status"));
					paymentBean.setUpdated_trnx_date(rs.getString("OPD_UPDATED_DATE"));
					paymentBean.setCheckStatusDate(rs.getString("opd_payment_status_date"));
					return paymentBean;
				}
			}, new Object[] { users.getUserFk() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return onLinePaymentDetailsList;

	}

	public PaymentBean getDemandDraftDetailsForEnrollment(Users users) throws Exception {
		List<PaymentBean> demandDraftDetailsForEnrollmentList = null;
		try {
			demandDraftDetailsForEnrollmentList = getJdbcTemplate().query(PaymentOfflineQueries.GET_DD_DETAILS_FOR_ENROLLMENT, new RowMapper<PaymentBean>() {
				public PaymentBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentBean paymentBean = new PaymentBean();
					paymentBean.setPaymentPK(rs.getString("OES_PAYMENT_DTL_PK"));
					paymentBean.setPaymentModeDD(rs.getString("OPTM_PAYMENT_DESC"));
					paymentBean.setUserFK(rs.getLong("OPD_USER_FK"));
					paymentBean.setTransactionDate(rs.getString("OPD_TRANSACTION_DATE"));
					paymentBean.setTransactionNo(rs.getString("OPD_TRANSACTION_NO"));
					paymentBean.setDdNumber(rs.getString("OPD_DD_CHALLAN_RECEIPT_NO"));
					// paymentBean.setDdDate("28-12-2011");
					paymentBean.setDdDate(rs.getString("OPD_DD_DATE"));
					paymentBean.setDdBankCode(rs.getString("OPD_BANK_NAME"));
					paymentBean.setDdCityCode(rs.getString("OPD_CITY"));
					paymentBean.setAmount(rs.getString("OPD_AMOUNT"));
					return paymentBean;
				}
			}, new Object[] { users.getUserFk() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (demandDraftDetailsForEnrollmentList != null && !demandDraftDetailsForEnrollmentList.isEmpty()) {
			return demandDraftDetailsForEnrollmentList.get(0);
		} else {
			return null;
		}

	}

	public PaymentBean getChallanDetailsForEnrollment(Users users) throws Exception {
		List<PaymentBean> challanDetailsForEnrollmentList = null;
		try {
			challanDetailsForEnrollmentList = getJdbcTemplate().query(PaymentOfflineQueries.GET_CHALLAN_DETAILS_FOR_ENROLLMENT, new RowMapper<PaymentBean>() {
				public PaymentBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentBean paymentBean = new PaymentBean();
					paymentBean.setPaymentPK(rs.getString("OES_PAYMENT_DTL_PK"));
					paymentBean.setPaymentModeCH(rs.getString("OPD_PAYMENT_FK"));
					paymentBean.setUserFK(rs.getLong("OPD_USER_FK"));
					paymentBean.setTransactionDate(rs.getString("OPD_TRANSACTION_DATE"));
					paymentBean.setScrollNumber(rs.getString("OPD_DD_CHALLAN_RECEIPT_NO"));
					paymentBean.setChallanDate(rs.getString("OPD_DD_DATE"));
					paymentBean.setChallanBankCode(rs.getString("OPD_BANK_NAME"));
					paymentBean.setAmount(rs.getString("OPD_AMOUNT"));
					paymentBean.setChallanBranchName(rs.getString("OPD_BRANCH_NAME"));
					return paymentBean;
				}
			}, new Object[] { users.getUserFk() });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (challanDetailsForEnrollmentList != null && !challanDetailsForEnrollmentList.isEmpty()) {
			return challanDetailsForEnrollmentList.get(0);
		} else {
			return null;
		}

	}

	/**
	 * Check whether the combination of DD no and Bank already exist
	 * 
	 * @author Pankaj
	 * @throws Exception
	 */

	public int isDDExist(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception {
		int existingDD = 0;
		try {
			existingDD = getJdbcTemplate().queryForObject(PaymentOfflineQueries.CHECK_IF_DD_EXIST, Integer.class, new Object[] { paymentBean.getDdNumber() });
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return existingDD;
	}

	public int isChallanExist(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception {
		int existingDD = 0;
		try {
			existingDD = getJdbcTemplate().queryForObject(PaymentOfflineQueries.CHECK_IF_CHALLAN_EXIST, Integer.class, new Object[] { paymentBean.getScrollNumber() });
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return existingDD;
	}

	public int insertChallanDetails(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception {
		int insertChallanDetailsFlagVal = 0;
		String amount = null;
		try {
			if (paymentBean.getChallanFeesAmount() != null && !paymentBean.getChallanFeesAmount().equals("")) {
				if (!paymentBean.getChallanFeesAmount().contains(".")) {
					amount = paymentBean.getChallanFeesAmount().concat(".00");
				}
			}
			try {
				insertChallanDetailsFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.INSERT_CHALLAN_DETAILS_FOR_ENROLLMENT,
						new Object[] { paymentBean.getUserFK(), CommonUtil.convertToTimestamp(paymentBean.getChallanDate(), GenericConstants.DATE_FORMAT_DEFAULT),
								paymentBean.getChallanBankCode(), paymentBean.getChallanBranchName(), paymentBean.getChallanBranchCode(), paymentBean.getScrollNumber(), amount,
								loggedInUser.getUsername(), });
			} catch (DataIntegrityViolationException ex) {
				try {
					insertChallanDetailsFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_CHALLAN_DETAILS_FOR_ENROLLMENT, new Object[] {
							// paymentBean.getUserFK(),
							CommonUtil.convertToTimestamp(paymentBean.getChallanDate(), GenericConstants.DATE_FORMAT_DEFAULT), amount, paymentBean.getChallanBankCode(),
							paymentBean.getChallanBranchName(), paymentBean.getChallanBranchCode(), paymentBean.getScrollNumber(), loggedInUser.getUsername(),
							paymentBean.getUserFK() });
				} catch (Exception exc) {
					throw new GenericException(exc);
				}
				return insertChallanDetailsFlagVal;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new GenericException(e);
		}
		return insertChallanDetailsFlagVal;
	}

	@Override
	public int insertChallanBarcodeDetails(CandidateBean candidateBean) throws Exception {
		int insertChallanBarcodeDetailsFlagVal = 0;
		try {
			insertChallanBarcodeDetailsFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.INSERT_CHALLAN_BARCODE_DETAILS, new Object[] { candidateBean.getUserfk(),
					candidateBean.getPersonalDetailsBean().getUserId(), candidateBean.getAppNumber(), candidateBean.getChallanbarcodeno() });
		} catch (Exception e) {
			e.printStackTrace();
			throw new GenericException(e);
		}
		return insertChallanBarcodeDetailsFlagVal;
	}

	public int updateChallanDetails(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception {
		// UPDATE_CHALLAN_DETAILS_FOR_ENROLLMENT
		int updateChallanDetailsFlagVal = 0;
		try {
			updateChallanDetailsFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_CHALLAN_DETAILS_FOR_ENROLLMENT, new Object[] {
					// paymentBean.getUserFK(),
					CommonUtil.convertToTimestamp(paymentBean.getChallanDate(), GenericConstants.DATE_FORMAT_DEFAULT), paymentBean.getChallanFeesAmount(),
					paymentBean.getChallanBankCode(), paymentBean.getChallanBranchName(), paymentBean.getChallanBranchCode(), paymentBean.getScrollNumber(),
					loggedInUser.getUsername(), paymentBean.getUserFK() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateChallanDetailsFlagVal;
	}

	public int insertDDDetails(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception {
		// insertDDDetails
		int insertDDDetailsFlagVal = 0;
		String amount = null;
		try {
			if (paymentBean.getChallanFeesAmount() != null && !paymentBean.getChallanFeesAmount().equals("")) {
				// if (!paymentBean.getChallanFeesAmount().contains(".")){
				// amount = paymentBean.getChallanFeesAmount().concat(".00");
				amount = paymentBean.getChallanFeesAmount();
				// }
			}
			try {
				insertDDDetailsFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.INSERT_DD_DETAILS_FOR_ENROLLMENT,
						new Object[] { paymentBean.getUserFK(), CommonUtil.convertToTimestamp(paymentBean.getDdDate(), GenericConstants.DATE_FORMAT_DEFAULT),
								paymentBean.getBankName(), paymentBean.getDdCityName(), paymentBean.getDdNumber(), amount, loggedInUser.getUsername()
						// paymentBean.getUserFK()
						});
			} catch (DataIntegrityViolationException ex) {
				try {
					insertDDDetailsFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_DD_DETAILS_FOR_ENROLLMENT,
							new Object[] { CommonUtil.convertToTimestamp(paymentBean.getDdDate(), GenericConstants.DATE_FORMAT_DEFAULT), paymentBean.getBankName(),
									paymentBean.getDdCityName(), amount, paymentBean.getDdNumber(), loggedInUser.getUsername(), paymentBean.getUserFK() });
				} catch (Exception exc) {
					throw new GenericException(exc);
				}
				return insertDDDetailsFlagVal;
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return insertDDDetailsFlagVal;
	}

	public int updateDDDetailsForRegisteredID(PaymentApprovalBean paymentApprovalBean, Users users) throws Exception {
		// insertDDDetails
		int insertDDDetailsFlagVal = 0;
		try {
			try {
				insertDDDetailsFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_DD_DETAILS_FOR_ENROLLMENT,
						new Object[] { CommonUtil.convertToTimestamp(paymentApprovalBean.getDdDate(), GenericConstants.DATE_FORMAT_DEFAULT), paymentApprovalBean.getBankName(),
								paymentApprovalBean.getBankCity(), paymentApprovalBean.getAmount(), paymentApprovalBean.getDdChalanReciptNO(), users.getUsername(), // user
																																									// name
								paymentApprovalBean.getUserPK() });
			} catch (Exception exc) {
				throw new GenericException(exc);
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return insertDDDetailsFlagVal;
	}

	public int updateChallanDetailsForRegisteredID(PaymentApprovalBean paymentApprovalBean, Users users) throws Exception {
		int updateChallanDetailsFlagVal = 0;
		try {
			try {
				updateChallanDetailsFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_CHALLAN_DETAILS_FOR_ENROLLMENT, new Object[] {
						// paymentBean.getUserFK(),
						CommonUtil.convertToTimestamp(paymentApprovalBean.getDdDate(), GenericConstants.DATE_FORMAT_DEFAULT), paymentApprovalBean.getAmount(),
						paymentApprovalBean.getBankName(), paymentApprovalBean.getBranchName(), paymentApprovalBean.getBranchCode(), paymentApprovalBean.getDdChalanReciptNO(),
						users.getUsername(), paymentApprovalBean.getUserPK() });
			} catch (Exception exc) {
				throw new GenericException(exc);
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateChallanDetailsFlagVal;
	}

	public int updateDDDetails(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception {
		// updateDDDetails
		int updateDDDetailsFlagVal = 0;
		try {
			updateDDDetailsFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_DD_DETAILS_FOR_ENROLLMENT,
					new Object[] { CommonUtil.convertToTimestamp(paymentBean.getDdDate(), GenericConstants.DATE_FORMAT_DEFAULT), paymentBean.getBankName(),
							paymentBean.getDdCityName(), paymentBean.getChallanFeesAmount(), paymentBean.getDdNumber(), loggedInUser.getUsername(), paymentBean.getUserFK() });

		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateDDDetailsFlagVal;
	}

	public int insertOnlinepaymentDetails(PaymentOnlineBean paymentBean) throws Exception {
		int insertOnlinepaymentFlagVal = 0;
		try {
			insertOnlinepaymentFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.INSERT_ONLINE_PAYMENT_DETAILS,
					new Object[] { paymentBean.getPaymentModeOnline(), paymentBean.getUserFK(), paymentBean.getTransactionNumber(), paymentBean.getNetBankingBankName(),
							paymentBean.getNetBankingAmount(), paymentBean.getCreatedBy() });

		} catch (Exception e) {
			insertOnlinepaymentFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_ONLINE_PAYMENT_DETAILS,
					new Object[] { paymentBean.getPaymentModeOnline(), paymentBean.getTransactionNumber(), paymentBean.getNetBankingBankName(), paymentBean.getNetBankingAmount(),
							paymentBean.getCreatedBy(), paymentBean.getUserFK() });
			logger.fatal("e " + e.getMessage());
		}
		return insertOnlinepaymentFlagVal;

	}

	public int insertOnlinepaymentDetailsForNetBankingOrCreditCard(PaymentOnlineBean paymentBean) throws Exception {
		int insertOnlinepaymentFlagVal = 0;
		try {
			insertOnlinepaymentFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.INSERT_ONLINE_PAYMENT_DETAILS_FOR_NETBANKING_OR_CREDIR_CARD,
					new Object[] { paymentBean.getPaymentModeOnline(), paymentBean.getUserFK(), paymentBean.getTransactionNumber(), paymentBean.getNetBankingBankName(),
							paymentBean.getNetBankingAmount(), paymentBean.getCreatedBy() });
		} catch (Exception e) {
			insertOnlinepaymentFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_ONLINE_PAYMENT_DETAILSFOR_NETBANKING_OR_CREDIR_CARD,
					new Object[] { paymentBean.getPaymentModeOnline(), paymentBean.getTransactionNumber(), paymentBean.getNetBankingBankName(), paymentBean.getNetBankingAmount(),
							paymentBean.getCreatedBy(), paymentBean.getUserFK() });
			logger.fatal("e " + e.getMessage());
		}
		return insertOnlinepaymentFlagVal;

	}

	public String getFeesForOnlinePayment(long enrollmentPK) {
		int fees = 0;
		try {
			fees = getJdbcTemplate().queryForObject(PaymentOfflineQueries.GET_FESS_DETAILS_FOR_ENROLLMENT, Integer.class, new Object[] { enrollmentPK });
		} catch (Exception e) {
			// TODO 2: handle exception
			e.printStackTrace();
		}
		return Integer.toString(fees);
	}

	public int insertpagedetails(PaymentBean paymentBean, String enrollmentpk, Users loggedInUsers) throws Exception {
		int insertpagedetails = 0;
		try {
			insertpagedetails = writeJdbcTemplate.update(PaymentOfflineQueries.INSERT_ONLINE_PAYMENT_DETAILS_FOR_ENROLLMENT, new Object[] { paymentBean.getPRN(),
					paymentBean.getBid(), paymentBean.getItc(), paymentBean.getCurrency(), paymentBean.getAMT(), loggedInUsers.getUsername(), paymentBean.getBank() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return insertpagedetails;
	}

	// for logo of bank when challan receipt on dash board
	public String getBankCodeForChallanReceipt(Users loggedInUser) throws Exception {
		String enrollmentId = null;
		if (loggedInUser != null) {
			enrollmentId = loggedInUser.getEnrollmentPK();
		}
		List<PaymentBean> getBankCode = null;
		try {
			getBankCode = getJdbcTemplate().query(PaymentOfflineQueries.GET_BANK_CODE_FOR_CHALLAN_RECEIPT, new RowMapper<PaymentBean>() {
				public PaymentBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentBean paymentBean = new PaymentBean();
					paymentBean.setBank_fk(rs.getString("BANK_FK"));
					return paymentBean;
				}
			}, new Object[] { enrollmentId });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (getBankCode != null && !getBankCode.isEmpty()) {
			return getBankCode.get(0).getBank_fk();
		} else {
			return null;
		}
	}

	// for upload bank logo image
	public PaymentBean getBankLogoImage(String bankID) throws Exception {
		List<PaymentBean> lstBankLogoImage;
		try {
			lstBankLogoImage = getJdbcTemplate().query(PaymentOfflineQueries.GET_BANK_LOGO_IMAGE, new RowMapper<PaymentBean>() {
				public PaymentBean mapRow(ResultSet rs, int rowNum) {
					PaymentBean imageDetailBean = new PaymentBean();
					ByteArrayInputStream byteArrayInputStreamForImage = null;
					try {
						if (rs.getBlob("OBM_BANK_IMAGES") != null) {
							byteArrayInputStreamForImage = new ByteArrayInputStream(rs.getBlob("OBM_BANK_IMAGES").getBytes(1, (int) (rs.getBlob("OBM_BANK_IMAGES").length())));
							imageDetailBean.setBankLogoImage((InputStream) byteArrayInputStreamForImage);
						}
					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e, e);
					}
					return imageDetailBean;
				}
			}, new Object[] { "" == bankID ? null : bankID });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (lstBankLogoImage != null && !lstBankLogoImage.isEmpty()) {
			return lstBankLogoImage.get(0);
		} else {
			return null;
		}
	}

	public List<CommonBean> getBankDetails(Users loggedInUser) throws Exception {
		List<CommonBean> bankDetialList = new ArrayList<CommonBean>();
		try {
			bankDetialList = getJdbcTemplate().query(PaymentOfflineQueries.GET_BANK_MASTER, new RowMapper<CommonBean>() {
				public CommonBean mapRow(ResultSet rs, int rowNum) {
					CommonBean bankDetialBean = new CommonBean();
					try {
						bankDetialBean.setLabelId(rs.getString("OBM_BANK_PK"));
						bankDetialBean.setLabelValue(rs.getString("OBM_BANK_NAME"));
					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e);
					}
					return bankDetialBean;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return bankDetialList;
	}

	public List<CommonBean> getCityDetails(Users users) throws Exception {
		List<CommonBean> cityList = new ArrayList<CommonBean>();
		try {
			EnrollmentBean enrollmentBean = new EnrollmentBean(); // TODO VIJAYA
			// CandidateBean candidateBean = new CandidateBean()
			String status = "A";
			enrollmentBean.setStatus(status);
			cityList = getJdbcTemplate().query(PaymentOfflineQueries.GET_CITY_MASTER, new RowMapper<CommonBean>() {
				public CommonBean mapRow(ResultSet rs, int rowNum) {
					CommonBean cityDetialBean = new CommonBean();
					try {
						cityDetialBean.setLabelId(rs.getString("OCM_CITY_PK"));
						cityDetialBean.setLabelCode(rs.getString("OCM_CITY_CODE"));
						cityDetialBean.setLabelValue(rs.getString("OCM_CITY_NAME"));
					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e);
					}
					return cityDetialBean;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return cityList;
	}

	public boolean isStageActive(String stageId) {
		int count = getJdbcTemplate().queryForObject(CommonQueries.CHECK_IF_STAGE_ACTIVE, Integer.class, new Object[] { stageId });
		return count > 0;
	}

	public CandidateBean getCandidateDetailsForPayment(Users loggedInUser, int id) throws Exception {
		List<CandidateBean> getCandidateDetailsList = null;
		Long userId = 0L;
		if (loggedInUser != null) {
			userId = loggedInUser.getUserId();
		}
		try {
			getCandidateDetailsList = getJdbcTemplate().query(PaymentOfflineQueries.SELECT_CANDIDATE_DETAILS_FOR_CHALLAN_RECEIPT, new RowMapper<CandidateBean>() {
				public CandidateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					CandidateBean candidateBean = new CandidateBean();
					PersonalDetailsBean personalBean = new PersonalDetailsBean();
					// AddressBean address = new AddressBean();
					// personalBean.setCandidateName(rs.getString("OCD_NAME"));
					// OCD_CAND_TITLE, OCD_FIRST_NAME, OCD_MIDDLE_NAME,
					// OCD_LAST_NAME
					personalBean.setCandidateFirstName(rs.getString("OCD_FIRST_NAME"));
					personalBean.setCandidateMiddleName(rs.getString("OCD_MIDDLE_NAME"));
					personalBean.setCandidateLastName(rs.getString("OCD_LAST_NAME"));
					personalBean.setUserId(rs.getString("oum_user_id"));
					personalBean.setDateOfBirth(rs.getString("OCD_DATE_OF_BIRTH"));
					personalBean.setMobileNo(rs.getString("OUM_MOBILE_NO"));
					candidateBean.setCategoryVal(rs.getString("octm_category_code"));
					candidateBean.setReceiptAmount(rs.getString("otm_fees_open"));
					candidateBean.setReceiptAmountInWords(rs.getString("feesInWords"));
					candidateBean.setAppNumber(rs.getString("ocjm_applicationnumber"));
					candidateBean.setCandidateDocPk3(rs.getString("feesInWords1"));
					candidateBean.setPersonalDetailsBean(personalBean);
					candidateBean.setUserfk(rs.getString("oum_user_pk"));
					/*
					 * commented for e-post challan data candidateBean.setTitleValue(rs.getString( "OCD_CAND_TITLE")); personalBean.setEmail(rs.getString("OUM_EMAIL_ID" ));
					 * address.setAlternatePinCode(rs.getString( "OCD_COMM_PIN_CODE")); address.setAlternateCity(rs.getString( "OCD_COMM_CITY"));
					 * address.setAlternateAddress(rs.getString( "OCD_COMM_ADDRESS")); address.setOtherAlternateStateField(rs.getString( "OCD_COMM_STATE_OTS"));
					 * 
					 * candidateBean.setAltCountryVal(rs.getString( "OCD_COMM_COUNTRY_FK")); candidateBean.setAltStateVal(rs.getString( "OCD_COMM_STATE_FK"));
					 * candidateBean.setAltUnionTerritoryVal(rs. getString("OCD_COMM_UNION_TERR_FK")); candidateBean.setCategoryVal(rs.getString( "OCD_CATEGORY_FK"));
					 * 
					 * 
					 * candidateBean.setReceiptAmount(rs.getString( "OFM_FEES")); candidateBean.setReceiptAmountInWords(rs. getString("OFM_FEES_IN_WORDS"));
					 * candidateBean.setAddressBean(address);
					 */

					return candidateBean;
				}
			}, new Object[] { userId, id });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (getCandidateDetailsList != null && !getCandidateDetailsList.isEmpty()) {
			return getCandidateDetailsList.get(0);
		} else {
			return null;
		}
	}

	public List<CommonBean> getDisciplineList() throws Exception {
		List<CommonBean> testDetailsList = null;
		try {
			testDetailsList = getJdbcTemplate().query(PaymentOfflineQueries.GET_TEST_DETAILS, new RowMapper<CommonBean>() {
				public CommonBean mapRow(ResultSet rs, int rowNum) {
					CommonBean testDetailsBean = new CommonBean();
					try {
						testDetailsBean.setLabelId(rs.getString("OTM_TEST_PK"));
						testDetailsBean.setLabelValue(rs.getString("OTM_TEST_NAME"));

					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e);
					}
					return testDetailsBean;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return testDetailsList;
	}

	public List<CommonBean> getPaymentModeList() throws Exception {
		List<CommonBean> paymentModeList = null;
		try {
			paymentModeList = getJdbcTemplate().query(PaymentOfflineQueries.GET_PAYMENT_MODE_DETAILS, new RowMapper<CommonBean>() {
				public CommonBean mapRow(ResultSet rs, int rowNum) {
					CommonBean paymentModeDetailsBean = new CommonBean();
					try {
						paymentModeDetailsBean.setLabelId(rs.getString("OPTM_PAYMENT_PK"));
						paymentModeDetailsBean.setLabelValue(rs.getString("OPTM_PAYMENT_DESC"));

					} catch (SQLException e) {
						LoggerHome.getLogger(getClass()).fatal(e);
					}
					return paymentModeDetailsBean;
				}
			});
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return paymentModeList;
	}

	public int getDDDetailsCountForPaymentApproval(CandidateMgmtBean candidateMgmtBean) throws Exception {
		int totalNumberOfRecord = 0;
		try {
			StringBuffer strBld = new StringBuffer();
			strBld.append(PaymentOfflineQueries.GET_COUNT_PAYMENT_APPROVAL_FOR_DD_TEST_PAYMENT_DATE_RANGE);
			if (candidateMgmtBean.getDisciplineId() != null) {
				if (candidateMgmtBean.getDisciplineId().equals("")) {
					candidateMgmtBean.setDisciplineId("0");
				}
				strBld.append(" AND tm.otm_test_pk = CASE WHEN " + candidateMgmtBean.getDisciplineId() + " = 0 THEN tm.OTM_TEST_PK ELSE " + candidateMgmtBean.getDisciplineId()
						+ " END ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("0")) {
				strBld.append(" AND pd.opd_validated_status ='R' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("1")) {
				strBld.append(" AND pd.opd_validated_status ='A' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("-1")) {
				strBld.append(" AND pd.opd_validated_status IS NULL ");
			}
			if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
				// strBld.append(" AND TRUNC(pd.OPD_DD_DATE) BETWEEN
				// "+candidateMgmtBean.getFromDate()+" AND
				// "+candidateMgmtBean.getToDate());
				strBld.append(" AND to_char(pd.OPD_CREATED_DATE,'HH12:MI:SS') BETWEEN '" + candidateMgmtBean.getFromDate() + "' AND '" + candidateMgmtBean.getToDate() + "'");
			}
			/* strBld.append(" ORDER BY usm.oum_user_id"); */ // comment by
																// Siddhesh
																// 09/03/2016
			totalNumberOfRecord = getJdbcTemplate().queryForObject(strBld.toString(), Integer.class);
		} catch (Exception ex) {
			totalNumberOfRecord = 0;
			throw new GenericException(ex);
		}
		return totalNumberOfRecord;
	}

	public List<PaymentApprovalBean> getDDDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		List<PaymentApprovalBean> ddPaymentApprovalList = null;
		try {
			StringBuffer strBld = new StringBuffer();
			strBld.append(PaymentOfflineQueries.GET_PAYMENT_APPROVAL_FOR_DD_TEST_PAYMENT_DATE_RANGE);
			if (candidateMgmtBean.getDisciplineId() != null) {
				if (candidateMgmtBean.getDisciplineId().equals("")) {
					candidateMgmtBean.setDisciplineId("0");
				}
				strBld.append(" AND tm.otm_test_pk = CASE WHEN " + candidateMgmtBean.getDisciplineId() + " = 0 THEN tm.OTM_TEST_PK ELSE " + candidateMgmtBean.getDisciplineId()
						+ " END ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("0")) {
				strBld.append(" AND pd.opd_validated_status ='R' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("1")) {
				strBld.append(" AND pd.opd_validated_status ='A' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("-1")) {
				strBld.append(" AND pd.opd_validated_status IS NULL ");
			}
			if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
				// strBld.append(" AND TRUNC(pd.OPD_DD_DATE) BETWEEN
				// "+candidateMgmtBean.getFromDate()+" AND
				// "+candidateMgmtBean.getToDate());
				strBld.append(" AND to_char(pd.OPD_CREATED_DATE,'HH12:MI:SS') BETWEEN '" + candidateMgmtBean.getFromDate() + "' AND '" + candidateMgmtBean.getToDate() + "'");
			}
			strBld.append(" ORDER BY usm.oum_user_id )");

			strBld.append("as aliasTEST WHERE num BETWEEN ").append(pagination.getStart()).append(" AND ").append(pagination.getEnd());

			ddPaymentApprovalList = getJdbcTemplate().query(strBld.toString(), new RowMapper<PaymentApprovalBean>() {
				public PaymentApprovalBean mapRow(ResultSet rs, int rowNum) {
					PaymentApprovalBean paymentApprovalBean = new PaymentApprovalBean();
					StringBuilder stringBuilder = new StringBuilder();
					try {
						stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
						stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
						if (rs.getString("OCD_MIDDLE_NAME") != null) {
							stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
						}
						stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");
						paymentApprovalBean.setEnrollment_pk(rs.getString("OUM_USER_ID"));
						paymentApprovalBean.setFirstName(stringBuilder.toString());
						paymentApprovalBean.setTestName(rs.getString("OTM_TEST_NAME"));
						paymentApprovalBean.setDdChalanReciptNO(rs.getString("OPD_DD_CHALLAN_RECEIPT_NO"));
						paymentApprovalBean.setDdDate(rs.getString("OPD_DD_DATE"));
						paymentApprovalBean.setAmount(rs.getString("OPD_AMOUNT"));
						paymentApprovalBean.setApplicableFee(rs.getString("OFM_FEES"));
						paymentApprovalBean.setBankName(rs.getString("OPD_BANK_NAME"));
						paymentApprovalBean.setPaymentPK(rs.getString("OES_PAYMENT_DTL_PK"));
						paymentApprovalBean.setUserPK(rs.getString("OUM_USER_PK"));
						paymentApprovalBean.setPaymentMode("DD");
						paymentApprovalBean.setRemark(rs.getString("OPD_REMARKS"));
						paymentApprovalBean.setContactNumber(rs.getString("OUM_MOBILE_NO"));
						paymentApprovalBean.setBankCity(rs.getString("OPD_CITY"));
						paymentApprovalBean.setValidatedStatus(rs.getString("opd_validated_status"));
					} catch (Exception ex) {
						LoggerHome.getLogger(getClass()).fatal(ex);
					}
					return paymentApprovalBean;
				}
			});

			if (ddPaymentApprovalList != null && !ddPaymentApprovalList.isEmpty()) {
				return ddPaymentApprovalList;
			} else {
				return null;
			}
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
	}

	public PaymentOnlineBean getPaymentDetails(Users users) throws Exception {
		List<PaymentOnlineBean> paymentList = null;
		try {
			StringBuffer strBld = new StringBuffer();
			strBld.append(PaymentQueries.GET_DATE);
			paymentList = getJdbcTemplate().query(strBld.toString(), new RowMapper<PaymentOnlineBean>() {
				public PaymentOnlineBean mapRow(ResultSet rs, int rowNum) {
					PaymentOnlineBean paymentBean = new PaymentOnlineBean();
					try {
						paymentBean.setPaymentStartDate(String.valueOf(CommonUtil.getDate(rs.getString("OTM_PAYMENT_START_DATE"), "dd-MMM-yyyy").getTime()));
						paymentBean.setPaymentEndDate(String.valueOf(CommonUtil.getDate(rs.getString("OTM_PAYMENT_END_DATE"), "dd-MMM-yyyy").getTime()));

					} catch (Exception ex) {
						LoggerHome.getLogger(getClass()).fatal(ex);
					}
					return paymentBean;
				}
			}, new Object[] { Integer.parseInt(String.valueOf(users.getUserId())) });
			if (paymentList != null && !paymentList.isEmpty()) {
				return paymentList.get(0);
			} else {
				return null;
			}
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
	}

	public List<PaymentApprovalBean> getChalanDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		List<PaymentApprovalBean> chalanPaymentApprovalList = null;
		try {
			StringBuilder strBldr = new StringBuilder();
			strBldr.append(PaymentOfflineQueries.GET_PAYMENT_APPROVAL_FOR_CHALLAN_TEST_PAYMENT_DATE_RANGE);
			if (candidateMgmtBean.getDisciplineId() != null) {
				if (candidateMgmtBean.getDisciplineId().equals("")) {
					candidateMgmtBean.setDisciplineId("0");
				}
				strBldr.append(" AND tm.otm_test_pk = CASE WHEN " + candidateMgmtBean.getDisciplineId() + " = 0 THEN tm.OTM_TEST_PK ELSE " + candidateMgmtBean.getDisciplineId()
						+ " END ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("0")) {
				strBldr.append(" AND pd.opd_validated_status ='R' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("1")) {
				strBldr.append(" AND pd.opd_validated_status ='A' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("-1")) {
				strBldr.append(" AND pd.opd_validated_status IS NULL ");
			}
			if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
				strBldr.append(" AND to_char(pd.OPD_CREATED_DATE,'HH12:MI:SS') BETWEEN '" + candidateMgmtBean.getFromDate() + "' AND '" + candidateMgmtBean.getToDate() + "'");
			}
			strBldr.append(" ORDER BY usm.oum_user_id ) as oesAlias");
			strBldr.append(" WHERE num BETWEEN ").append(pagination.getStart()).append(" AND ").append(pagination.getEnd());

			chalanPaymentApprovalList = getJdbcTemplate().query(strBldr.toString(),
					// PaymentOfflineQueries.GET_PAYMENT_APPROVAL_FOR_CHALLAN_TEST_PAYMENT_DATE_RANGE,
					new RowMapper<PaymentApprovalBean>() {
						public PaymentApprovalBean mapRow(ResultSet rs, int rowNum) {
							PaymentApprovalBean paymentApprovalBean = new PaymentApprovalBean();
							StringBuilder stringBuilder = new StringBuilder();
							try {
								stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
								stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
								if (rs.getString("OCD_MIDDLE_NAME") != null) {
									stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
								}
								stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");
								paymentApprovalBean.setEnrollment_pk(rs.getString("OUM_USER_ID"));
								paymentApprovalBean.setFirstName(stringBuilder.toString());
								paymentApprovalBean.setTestName(rs.getString("OTM_TEST_NAME"));
								paymentApprovalBean.setDdChalanReciptNO(rs.getString("OPD_DD_CHALLAN_RECEIPT_NO"));
								paymentApprovalBean.setDdDate(rs.getString("OPD_DD_DATE"));
								paymentApprovalBean.setAmount(rs.getString("OPD_AMOUNT"));
								paymentApprovalBean.setApplicableFee(rs.getString("OFM_FEES"));
								paymentApprovalBean.setBankName(rs.getString("OPD_BANK_NAME"));
								paymentApprovalBean.setPaymentPK(rs.getString("OES_PAYMENT_DTL_PK"));
								paymentApprovalBean.setPaymentMode("CH");
								paymentApprovalBean.setBranchName(rs.getString("OPD_BRANCH_NAME"));
								paymentApprovalBean.setBranchCode(rs.getString("OPD_BRANCH_CODE"));
								paymentApprovalBean.setUserPK(rs.getString("OUM_USER_PK"));
								paymentApprovalBean.setRemark(rs.getString("OPD_REMARKS"));
								paymentApprovalBean.setContactNumber(rs.getString("OUM_MOBILE_NO"));
								paymentApprovalBean.setValidatedStatus(rs.getString("opd_validated_status"));
							} catch (Exception ex) {
								LoggerHome.getLogger(getClass()).fatal(ex);
							}
							return paymentApprovalBean;
						}
					});
			if (chalanPaymentApprovalList != null && !chalanPaymentApprovalList.isEmpty()) {
				return chalanPaymentApprovalList;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}

	public List<PaymentApprovalBean> getEPostDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean, Pagination pagination) throws Exception {
		List<PaymentApprovalBean> chalanPaymentApprovalList = null;
		try {
			StringBuilder strBldr = new StringBuilder();
			strBldr.append(PaymentOfflineQueries.GET_PAYMENT_APPROVAL_FOR_EPOST_PAYMENT_DATE_RANGE);
			if (candidateMgmtBean.getDisciplineId() != null) {
				if (candidateMgmtBean.getDisciplineId().equals("")) {
					candidateMgmtBean.setDisciplineId("0");
				}
				strBldr.append(" AND tm.otm_test_pk = CASE WHEN " + candidateMgmtBean.getDisciplineId() + " = 0 THEN tm.OTM_TEST_PK ELSE " + candidateMgmtBean.getDisciplineId()
						+ " END ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("0")) {
				strBldr.append(" AND pd.opd_validated_status ='R' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("1")) {
				strBldr.append(" AND pd.opd_validated_status ='A' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("-1")) {
				strBldr.append(" AND pd.opd_validated_status IS NULL ");
			}
			if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
				strBldr.append(" AND to_char(pd.OPD_CREATED_DATE,'HH12:MI:SS') BETWEEN '" + candidateMgmtBean.getFromDate() + "' AND '" + candidateMgmtBean.getToDate() + "'");
			}
			strBldr.append(" ORDER BY usm.oum_user_id ) as oesAlias");
			strBldr.append(" WHERE num BETWEEN ").append(pagination.getStart()).append(" AND ").append(pagination.getEnd());

			chalanPaymentApprovalList = getJdbcTemplate().query(strBldr.toString(),
					// PaymentOfflineQueries.GET_PAYMENT_APPROVAL_FOR_CHALLAN_TEST_PAYMENT_DATE_RANGE,
					new RowMapper<PaymentApprovalBean>() {
						public PaymentApprovalBean mapRow(ResultSet rs, int rowNum) {
							PaymentApprovalBean paymentApprovalBean = new PaymentApprovalBean();
							StringBuilder stringBuilder = new StringBuilder();
							try {
								stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
								stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
								if (rs.getString("OCD_MIDDLE_NAME") != null) {
									stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
								}
								stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");
								paymentApprovalBean.setEnrollment_pk(rs.getString("OUM_USER_ID"));
								paymentApprovalBean.setFirstName(stringBuilder.toString());
								paymentApprovalBean.setTestName(rs.getString("OTM_TEST_NAME"));
								paymentApprovalBean.setDdChalanReciptNO(rs.getString("OPD_DD_CHALLAN_RECEIPT_NO"));
								paymentApprovalBean.setDdDate(rs.getString("OPD_DD_DATE"));
								paymentApprovalBean.setAmount(rs.getString("OPD_AMOUNT"));
								paymentApprovalBean.setApplicableFee(rs.getString("OFM_FEES"));
								paymentApprovalBean.setBankName(rs.getString("OPD_BANK_NAME"));
								paymentApprovalBean.setPaymentPK(rs.getString("OES_PAYMENT_DTL_PK"));
								paymentApprovalBean.setPaymentMode("EP");
								paymentApprovalBean.setBranchName(rs.getString("OPD_BRANCH_NAME"));
								paymentApprovalBean.setBranchCode(rs.getString("OPD_BRANCH_CODE"));
								paymentApprovalBean.setUserPK(rs.getString("OUM_USER_PK"));
								paymentApprovalBean.setRemark(rs.getString("OPD_REMARKS"));
								paymentApprovalBean.setContactNumber(rs.getString("OUM_MOBILE_NO"));
								paymentApprovalBean.setValidatedStatus(rs.getString("opd_validated_status"));
							} catch (Exception ex) {
								LoggerHome.getLogger(getClass()).fatal(ex);
							}
							return paymentApprovalBean;
						}
					});

			if (chalanPaymentApprovalList != null && !chalanPaymentApprovalList.isEmpty()) {
				return chalanPaymentApprovalList;
			} else {
				return null;
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
	}

	public int getCountForChalanDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean) throws Exception {
		int totalNumberOfRecord = 0;
		try {
			StringBuilder strBldr = new StringBuilder();
			strBldr.append(PaymentOfflineQueries.GET_COUNT_PAYMENT_APPROVAL_FOR_CHALLAN_TEST_PAYMENT_DATE_RANGE);
			if (candidateMgmtBean.getDisciplineId() != null) {
				if (candidateMgmtBean.getDisciplineId().equals("")) {
					candidateMgmtBean.setDisciplineId("0");
				}
				strBldr.append(" AND tm.otm_test_pk = CASE WHEN " + candidateMgmtBean.getDisciplineId() + " = 0 THEN tm.OTM_TEST_PK ELSE " + candidateMgmtBean.getDisciplineId()
						+ " END ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("0")) {
				strBldr.append(" AND pd.opd_validated_status ='R' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("1")) {
				strBldr.append(" AND pd.opd_validated_status ='A' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("-1")) {
				strBldr.append(" AND pd.opd_validated_status IS NULL ");
			}
			if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
				strBldr.append(" AND to_char(pd.OPD_CREATED_DATE,'HH12:MI:SS') BETWEEN '" + candidateMgmtBean.getFromDate() + "' AND '" + candidateMgmtBean.getToDate() + "'");
			}
			// strBldr.append(" ORDER BY usm.oum_user_id");
			totalNumberOfRecord = getJdbcTemplate().queryForObject(strBldr.toString(), Integer.class);
		} catch (Exception e) {
			totalNumberOfRecord = 0;
			throw new GenericException(e);
		}
		return totalNumberOfRecord;
	}

	public int getCountForEPostDetailsForPaymentApproval(CandidateMgmtBean candidateMgmtBean) throws Exception {
		int totalNumberOfRecord = 0;
		try {
			StringBuilder strBldr = new StringBuilder();
			strBldr.append(PaymentOfflineQueries.GET_COUNT_PAYMENT_APPROVAL_FOR_EPOST_PAYMENT_DATE_RANGE);
			if (candidateMgmtBean.getDisciplineId() != null) {
				if (candidateMgmtBean.getDisciplineId().equals("")) {
					candidateMgmtBean.setDisciplineId("0");
				}
				strBldr.append(" AND tm.otm_test_pk = CASE WHEN " + candidateMgmtBean.getDisciplineId() + " = 0 THEN tm.OTM_TEST_PK ELSE " + candidateMgmtBean.getDisciplineId()
						+ " END ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("0")) {
				strBldr.append(" AND pd.opd_validated_status ='R' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("1")) {
				strBldr.append(" AND pd.opd_validated_status ='A' ");
			}
			if (candidateMgmtBean.getPaymentStatusValue() != null && candidateMgmtBean.getPaymentStatusValue().equals("-1")) {
				strBldr.append(" AND pd.opd_validated_status IS NULL ");
			}
			if (!ValidatorUtil.isEmpty(candidateMgmtBean.getFromDate()) && !ValidatorUtil.isEmpty(candidateMgmtBean.getToDate())) {
				strBldr.append(" AND to_char(pd.OPD_CREATED_DATE,'HH12:MI:SS') BETWEEN '" + candidateMgmtBean.getFromDate() + "' AND '" + candidateMgmtBean.getToDate() + "'");
			}
			// strBldr.append(" ORDER BY usm.oum_user_id");

			totalNumberOfRecord = getJdbcTemplate().queryForObject(strBldr.toString(), Integer.class);

		} catch (Exception e) {
			totalNumberOfRecord = 0;
			throw new GenericException(e);
		}
		return totalNumberOfRecord;
	}

	@Override
	public PaymentApprovalBean getPaymentApprovalByEnrollmentId(CandidateMgmtBean candidateMgmtBean) throws Exception {
		List<PaymentApprovalBean> ddPaymentApprovalList = null;

		String enrollmentid = null;
		if (candidateMgmtBean.getEnrollmentId() != null) {
			enrollmentid = candidateMgmtBean.getEnrollmentId().trim();
		}
		try {
			ddPaymentApprovalList = getJdbcTemplate().query(PaymentOfflineQueries.GET_PAYMENT_APPROVAL_FOR_ENROLLMENT_PK, new RowMapper<PaymentApprovalBean>() {
				public PaymentApprovalBean mapRow(ResultSet rs, int rowNum) {
					PaymentApprovalBean paymentApprovalBean = new PaymentApprovalBean();
					StringBuilder stringBuilder = new StringBuilder();
					try {
						// " select enroll.enrollment_pk,
						// candidate.first_name,
						// candidate.last_name,test.test_name,testcentre.test_centre_name,batch.test_start_time,"+
						// " batch.test_end_time,
						// payment.DD_CHALLAN_RECEIPT_NO,
						// payment.dd_date,payment.amount,
						// bank.bank_name "
						paymentApprovalBean.setEnrollment_pk(rs.getString("OUM_USER_ID"));
						stringBuilder.append(rs.getString("OCD_CAND_TITLE")).append(" ");
						stringBuilder.append(rs.getString("OCD_FIRST_NAME")).append(" ");
						if (rs.getString("OCD_MIDDLE_NAME") != null) {
							stringBuilder.append(rs.getString("OCD_MIDDLE_NAME")).append(" ");
						}
						stringBuilder.append(rs.getString("OCD_LAST_NAME")).append(" ");
						paymentApprovalBean.setEnrollment_pk(rs.getString("OUM_USER_ID"));
						paymentApprovalBean.setFirstName(stringBuilder.toString());
						// paymentApprovalBean.setFirstName(rs.getString("OCD_NAME"));
						paymentApprovalBean.setTestName(rs.getString("OTM_TEST_NAME"));
						paymentApprovalBean.setDdChalanReciptNO(rs.getString("OPD_DD_CHALLAN_RECEIPT_NO"));
						paymentApprovalBean.setDdDate(CommonUtil.formatTimeStamp(rs.getTimestamp("OPD_DD_DATE")));
						paymentApprovalBean.setAmount(rs.getString("OPD_AMOUNT"));
						paymentApprovalBean.setApplicableFee(rs.getString("OFM_FEES"));
						paymentApprovalBean.setBankName(rs.getString("OPD_BANK_NAME"));
						paymentApprovalBean.setPaymentPK(rs.getString("OES_PAYMENT_DTL_PK"));
						paymentApprovalBean.setPaymentMode(rs.getString("OPD_PAYMENT_FK"));
						paymentApprovalBean.setBranchName(rs.getString("OPD_BRANCH_NAME"));
						paymentApprovalBean.setUserPK(rs.getString("OUM_USER_PK"));
						paymentApprovalBean.setRemark(rs.getString("OPD_REMARKS"));
						paymentApprovalBean.setContactNumber(rs.getString("OUM_MOBILE_NO"));
						paymentApprovalBean.setBranchCode(rs.getString("OPD_BRANCH_CODE"));
						paymentApprovalBean.setBankCity(rs.getString("OPD_CITY"));
						paymentApprovalBean.setValidatedStatus(rs.getString("opd_validated_status"));
					} catch (Exception ex) {
						LoggerHome.getLogger(getClass()).fatal(ex);
					}
					return paymentApprovalBean;
				}
			}, new Object[] { enrollmentid });

			if (ddPaymentApprovalList != null && !ddPaymentApprovalList.isEmpty()) {
				return ddPaymentApprovalList.get(0);
			} else {
				return null;
			}

		} catch (Exception e) {
			throw new GenericException(e);
		}
	}

	@Override
	public int updateDDPaymentApproval(Users users, CandidateMgmtBean candidateMgmtBean) throws Exception {
		List<PaymentApprovalBean> paymentApprovalBeanList = null;
		int updateDDApproval = 0;
		String[] paymentPKNUserPKPair = null;
		String checkValue = null;
		try {
			if (candidateMgmtBean.getDdPaymentApprovalList() != null) {
				paymentApprovalBeanList = candidateMgmtBean.getDdPaymentApprovalList();
			}
			if (candidateMgmtBean.getChalanPaymentApprovalList() != null) {
				paymentApprovalBeanList = candidateMgmtBean.getChalanPaymentApprovalList();
			}
			if (paymentApprovalBeanList != null) {
				for (PaymentApprovalBean paymentApprovalBean : paymentApprovalBeanList) {
					if (paymentApprovalBean != null) {
						if (paymentApprovalBean.getPaymentCheckedValue() != null && !paymentApprovalBean.getPaymentCheckedValue().get(0).equals("false")) {
							checkValue = paymentApprovalBean.getPaymentCheckedValue().get(0);
							paymentPKNUserPKPair = checkValue.split("\\,");
							if (paymentPKNUserPKPair.length == 2) {
								updateDDApproval = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_PAYMENT_APPROVAL,
										new Object[] { "A", users.getUsername(), paymentApprovalBean.getRemark(), Integer.parseInt(paymentPKNUserPKPair[0]) });
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateDDApproval;
	}

	public int updateDDPaymentRejection(Users users, CandidateMgmtBean candidateMgmtBean) throws Exception {
		List<PaymentApprovalBean> paymentApprovalBeanList = null;
		int updateDDApproval = 0;
		String[] paymentPKNUserPKPair = null;
		String checkValue = null;
		try {

			if (candidateMgmtBean.getDdPaymentApprovalList() != null) {
				paymentApprovalBeanList = candidateMgmtBean.getDdPaymentApprovalList();
			}

			if (candidateMgmtBean.getChalanPaymentApprovalList() != null) {
				paymentApprovalBeanList = candidateMgmtBean.getChalanPaymentApprovalList();
			}

			if (paymentApprovalBeanList != null) {
				for (PaymentApprovalBean paymentApprovalBean : paymentApprovalBeanList) {
					if (paymentApprovalBean != null) {
						if (paymentApprovalBean.getPaymentCheckedValue() != null && !paymentApprovalBean.getPaymentCheckedValue().get(0).equals("false")) {
							checkValue = paymentApprovalBean.getPaymentCheckedValue().get(0);
							paymentPKNUserPKPair = checkValue.split("\\,");
							if (paymentPKNUserPKPair.length == 2) {
								updateDDApproval = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_PAYMENT_REJECTION,
										new Object[] { "R", users.getUsername(), paymentApprovalBean.getRemark(), Integer.parseInt(paymentPKNUserPKPair[0]) });
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return updateDDApproval;
	}

	public PaymentBean getPaymentDetailsForEnrollment(Users users) throws Exception {
		List<PaymentBean> challanDetailsForEnrollmentList = null;
		try {
			challanDetailsForEnrollmentList = getJdbcTemplate().query(PaymentOfflineQueries.GET_PAYMENT_DETAILS_FOR_ENROLLMENT, new RowMapper<PaymentBean>() {
				public PaymentBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentBean paymentBean = new PaymentBean();
					paymentBean.setPaymentPK(rs.getString("OES_PAYMENT_DTL_PK"));
					paymentBean.setPaymentModeType(rs.getString("OPTM_PAYMENT_DESC"));
					paymentBean.setUserFK(rs.getLong("OPD_USER_FK"));
					return paymentBean;
				}
			}, new Object[] { users.getUserFk() });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (challanDetailsForEnrollmentList != null && !challanDetailsForEnrollmentList.isEmpty()) {
			return challanDetailsForEnrollmentList.get(0);
		} else {
			return null;
		}
	}

	public int deletePrevPaymentDetailsForEnrollment(Users users) throws Exception {
		int deletePaymentFlag = 0;
		try {
			deletePaymentFlag = writeJdbcTemplate.update(PaymentOfflineQueries.DELETE_PAYMENT_DETAILS_FOR_ENROLLMENT, new Object[] { users.getUserFk() });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return deletePaymentFlag;
	}

	public int insertEPostDetails(PaymentOnlineBean paymentBean, Users loggedInUser) throws Exception {
		int insertEPostDetailsFlagVal = 0;
		String amount = null;
		try {
			if (paymentBean.getEPostFeesAmount() != null && !paymentBean.getEPostFeesAmount().equals("")) {
				if (!paymentBean.getEPostFeesAmount().contains(".")) {
					amount = paymentBean.getEPostFeesAmount().concat(".00");
				}
			}
			try {
				insertEPostDetailsFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.INSERT_EPOST_DETAILS_FOR_ENROLLMENT,
						new Object[] { paymentBean.getUserFK(), CommonUtil.convertToTimestamp(paymentBean.getEPostPaymentDate(), GenericConstants.DATE_FORMAT_DEFAULT),
								paymentBean.getEPostBranchName(), paymentBean.getEPostBranchCode(), paymentBean.getScrollNumber(), amount, loggedInUser.getUsername(), });
			} catch (DataIntegrityViolationException ex) {
				try {
					insertEPostDetailsFlagVal = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_EPOST_DETAILS_FOR_ENROLLMENT,
							new Object[] { CommonUtil.convertToTimestamp(paymentBean.getEPostPaymentDate(), GenericConstants.DATE_FORMAT_DEFAULT), amount,
									paymentBean.getEPostBranchName(), paymentBean.getEPostBranchCode(), paymentBean.getScrollNumber(), loggedInUser.getUsername(),
									paymentBean.getUserFK() });
				} catch (Exception exc) {
					throw new GenericException(exc);
				}
				return insertEPostDetailsFlagVal;
			}
		} catch (Exception e) {
			logger.error(e);
			throw new GenericException(e);
		}
		return insertEPostDetailsFlagVal;
	}

	@Override
	public int updateSbiResponseForCandidate(SBIResponseBean sbiResponseBean, Users users) throws Exception {
		logger.info("insertOnlinTransactionDetails called User Level User Id:" + users.getUsername());
		int saveUpdate = 0;
		String validate_status = "";
		String opd_status = "";
		String registrationId = "";
		if (("Y".equalsIgnoreCase(sbiResponseBean.getAuthStatus())) || "Success".equalsIgnoreCase(sbiResponseBean.getAuthStatus())) {
			validate_status = "A";
			opd_status = "A";
		} else if ("N".equalsIgnoreCase(sbiResponseBean.getAuthStatus()) || "Failure".equalsIgnoreCase(sbiResponseBean.getAuthStatus())) {
			validate_status = "R";
			opd_status = "N";
		} else if ("P".equalsIgnoreCase(sbiResponseBean.getAuthStatus()) || "Pending".equalsIgnoreCase(sbiResponseBean.getAuthStatus())) {
			validate_status = "P";
			opd_status = "N";
		} else {
			validate_status = null;
			opd_status = "N";
		}

		String amount = "0.00";
		if (sbiResponseBean.getTxnAmount() != null) {
			amount = sbiResponseBean.getTxnAmount();
			amount = amount.replaceAll("(?<=^\\d+)\\.0*$", "");
		} else {
			amount = ConfigurationConstants.getInstance().getPropertyVal("GENERAL_OBC_AMOUNT");
		}
		if (null != sbiResponseBean.getAuthStatus() && !"".equals(sbiResponseBean.getAuthStatus()) && sbiResponseBean.getAuthStatus().equalsIgnoreCase("Success")) {
			registrationId = sbiResponseBean.getCustomerId();
		} else {
			registrationId = null;
		}
		try {
			saveUpdate = writeJdbcTemplate.update(PaymentQueries.UPDATE_ONLINE_DETAILS,
					new Object[] { CommonUtil.convertToTimestamp(sbiResponseBean.getTransactionDate(), "MM/dd/yy"), validate_status, users.getUsername(),
							sbiResponseBean.getAuthStatus(), opd_status, sbiResponseBean.getErrorDescription(), "Online", sbiResponseBean.getCheckSum(),
							sbiResponseBean.getSbiRefNo(), amount, sbiResponseBean.getTxnType(), registrationId, users.getUserFk(), sbiResponseBean.getTxnReferenceNo() });
		} catch (Exception e) {
			logger.fatal(e, e);
			throw e;
		}
		return saveUpdate;
	}

	@Override
	public int insertOnlinTransactionDetails(TechProcessResponseBean techProcessResponseBean, Users users) throws Exception {
		logger.info("insertOnlinTransactionDetails Tech Process for User ID = " + users.getUsername() + " Trxn Id =" + techProcessResponseBean.getCustomerId());
		int saveUpdate = 0;
		String validate_status = "";

		if ("success".equalsIgnoreCase(techProcessResponseBean.getTransactionMessage())) {
			validate_status = "A";
		} else if ("failure".equals(techProcessResponseBean.getTransactionMessage())) {
			validate_status = "R";
		} else {
			validate_status = "R";
		}

		String transDate = techProcessResponseBean.getTxnDate();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date df1 = df.parse(transDate);
		String convDate = new SimpleDateFormat("dd-MMM-yyyy").format(df1);
		DateFormat fn = new SimpleDateFormat("dd-MMM-yyyy");
		Date txnDateF = fn.parse(convDate);
		String txnDate = fn.format(txnDateF);

		String amount = "0.00";
		if (techProcessResponseBean.getTxnAmount() != null) {
			amount = techProcessResponseBean.getTxnAmount();
			amount = amount.replaceFirst("^0+(?!$)", "");
		} else {
			logger.info("Transaction amount got null for Customer ID = " + techProcessResponseBean.getCustomerId());
			return 0;
		}

		try {
			saveUpdate = writeJdbcTemplate.update(PaymentQueries.UPDATE_ONLINE_DETAILS_TP,
					new Object[] { techProcessResponseBean.getTxnReferenceNo(), CommonUtil.convertToTimestamp(txnDate, GenericConstants.DATE_FORMAT_DEFAULT), validate_status,
							amount, techProcessResponseBean.getBankId(), users.getUsername(), techProcessResponseBean.getTransactionMessage(),
							techProcessResponseBean.getErrorDEsc(), techProcessResponseBean.getAuthStatus(), techProcessResponseBean.getCheckSum(),
							techProcessResponseBean.getClientReqMeta(), users.getUserFk(), techProcessResponseBean.getCustomerId() });

		} catch (Exception e) {
			e.printStackTrace();
		}

		return saveUpdate;
	}

	@Override
	public void insertTransactionIDForCandidate1(PaymentOnlineBean paymentBean, Users loggedInUser, String merchantTxnSeqNumber, String merchantTxnRefNumber) throws Exception {
		int insertDetails = 0;
		try {
			insertDetails = writeJdbcTemplate.update(PaymentQueries.INSERT_TRANSACTION_NUMBER_IN_OPD1, new Object[] { Integer.valueOf(merchantTxnSeqNumber), Integer.parseInt("5"), // echallan
																																													// entry
					loggedInUser.getUserFk(), merchantTxnRefNumber, "INR", "N", loggedInUser.getUsername(), Integer.valueOf(loggedInUser.getDisciplineID()),
					paymentBean.getNetBankingAmount()
					// Long.parseLong(paymentBean.getApplicationNum())
			});
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void updateTransactionIDForCandidate(Users loggedInUser, String merchantTxnRefNumber) throws Exception {
		int insertDetails = 0;
		try {
			insertDetails = writeJdbcTemplate.update(PaymentQueries.UPDATE_TRANSACTION_NUMBER_IN_OPD, new Object[] { loggedInUser.getUserFk(), merchantTxnRefNumber });
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getExistingMerchantTxnNumber(Users loggedInUser, PaymentOnlineBean paymentBean1) throws Exception {
		String merchantTxnNumber = null;
		PaymentBean paymentBean = new PaymentBean();
		try { // 1 means online
			paymentBean = (PaymentBean) getJdbcTemplate().queryForObject(PaymentQueries.GET_EXISTING_TXN_NUMBER, new BeanPropertyRowMapper(PaymentBean.class),
					new Object[] { loggedInUser.getUserFk(), 1 });
		} catch (Exception e) {
			merchantTxnNumber = null;
			// logger.error(e, e);
		}
		if (paymentBean != null) {
			merchantTxnNumber = paymentBean.getClientTransactionNo();
		}
		return merchantTxnNumber;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String getExistingMerchantTxnNumber1(Users loggedInUser, PaymentOnlineBean paymentBean1) throws Exception {
		String merchantTxnNumber = null;
		PaymentBean paymentBean = new PaymentBean();
		try {// 5 means challan
			paymentBean = (PaymentBean) getJdbcTemplate().queryForObject(PaymentQueries.GET_EXISTING_TXN_NUMBER1, new BeanPropertyRowMapper(PaymentBean.class),
					new Object[] { loggedInUser.getUserFk(), 5 });
		} catch (Exception e) {
			merchantTxnNumber = null;
			// logger.error(e, e);
		}
		if (paymentBean != null) {
			merchantTxnNumber = paymentBean.getClientTransactionNo();
		}
		return merchantTxnNumber;
	}

	@Override
	public String getCandidateCurrentJob(Users loggedInUser) throws Exception {
		String jobName = null;
		try {
			jobName = (String) getJdbcTemplate().queryForObject(PaymentQueries.GET_JOB_NAME, String.class, new Object[] { loggedInUser.getUserFk() });
		} catch (Exception e) {
			logger.error(e, e);
		}
		return jobName;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Map<Integer, String> getAppliedData(Long userFk, String disciplineID) {
		LinkedHashMap<Integer, String> getAppliedData = new LinkedHashMap<Integer, String>();
		try {
			getAppliedData = (LinkedHashMap<Integer, String>) getJdbcTemplate().query(PaymentQueries.GET_CANDIDATE_COURSE_APPLIED, new ResultSetExtractor() {
				@Override
				public Object extractData(ResultSet rs) {
					Map<Integer, String> appliedCourseMap = new LinkedHashMap<Integer, String>();
					try {
						while (rs.next()) {
							appliedCourseMap.put(rs.getInt("otm_test_pk"), rs.getString("otm_test_name"));
						}
					} catch (Exception e) {
						// TODO 2: handle exception
					}
					return appliedCourseMap;
				}
			}, new Object[] { userFk, disciplineID });
		} catch (Exception e) {
			logger.error(e, e);
		}
		return getAppliedData;
	}

	@Override
	public List<PaymentOnlineBean> getApplicationDetails(Long userFk) throws Exception {
		List<PaymentOnlineBean> applicationDetailsList = null;
		try {
			applicationDetailsList = getJdbcTemplate().query(PaymentQueries.GET_CANDIDATE_APPLICATION_DETAILS, new RowMapper<PaymentOnlineBean>() {
				public PaymentOnlineBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentOnlineBean bean = new PaymentOnlineBean();
					bean.setApplicationNum(rs.getString("ocjm_app_no"));
					bean.setDisciplineDesc(rs.getString("otm_test_name"));
					bean.setTestFK(rs.getString("ocjm_test_fk"));
					return bean;
				}
			}, new Object[] { userFk });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (applicationDetailsList != null && !applicationDetailsList.isEmpty()) {
			return applicationDetailsList;
		} else {
			return null;
		}
	}

	@Override
	public List<PaymentOnlineBean> getPendingPaymentRecords() throws Exception {
		List<PaymentOnlineBean> pendingPaymentDetailsList = null;
		try {
			
			/*getJdbcTemplate().query(PaymentQueries.GET_PENDING_PAYMENT_DETAILS_ENC1, (PreparedStatementSetter) preparedStatement -> {
				preparedStatement.setString(1, ConfigurationConstants.getInstance().getENC_KEY());
				preparedStatement.setString(2, ConfigurationConstants.getInstance().getENC_KEY());
			}, (ResultSetExtractor<PaymentOnlineBean>) rs -> {
				if (rs.next()) {
					PaymentOnlineBean bean = new PaymentOnlineBean();
					bean.setOpd_user_fk(rs.getString("opd_user_fk"));
					bean.setOpd_transaction_date(rs.getString("opd_transaction_date"));
					bean.setOpd_transaction_no(rs.getString("opd_transaction_no"));
					bean.setOpd_created_by(rs.getString("opd_created_by"));
					bean.setOpd_test_fk(rs.getString("opd_test_fk"));
					bean.setOpd_app_no(rs.getString("opd_app_no"));
					bean.setOpd_mobile_no(rs.getString("opd_mobile_no"));
					bean.setOpd_email_id(rs.getString("opd_email_id"));
					bean.setAmount(rs.getString("opd_amount"));
					return bean;
				}
				return null;
			});*/

			pendingPaymentDetailsList = getJdbcTemplate().query(PaymentQueries.GET_PENDING_PAYMENT_DETAILS_ENC1, new RowMapper<PaymentOnlineBean>() {
				public PaymentOnlineBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentOnlineBean bean = new PaymentOnlineBean();
					bean.setOpd_user_fk(rs.getString("opd_user_fk"));
					bean.setOpd_transaction_date(rs.getString("opd_transaction_date"));
					bean.setOpd_transaction_no(rs.getString("opd_transaction_no"));
					bean.setOpd_created_by(rs.getString("opd_created_by"));
					bean.setOpd_test_fk(rs.getString("opd_test_fk"));
					bean.setOpd_app_no(rs.getString("opd_app_no"));
					bean.setOpd_mobile_no(rs.getString("opd_mobile_no"));
					bean.setOpd_email_id(rs.getString("opd_email_id"));
					bean.setAmount(rs.getString("opd_amount"));
					return bean;
				}
			}, new Object[] { ENC_KEY, ENC_KEY });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (pendingPaymentDetailsList != null && !pendingPaymentDetailsList.isEmpty()) {
			return pendingPaymentDetailsList;
		} else {
			return null;
		}
	}

	@Override
	public List<PaymentOnlineBean> getPendingPaymentRecords(Long userPk) throws Exception {
		List<PaymentOnlineBean> pendingPaymentDetailsList = null;
		try {
			pendingPaymentDetailsList = getJdbcTemplate().query(PaymentQueries.GET_PENDING_PAYMENT_DETAILS_FOR_Check_STATUS, new RowMapper<PaymentOnlineBean>() {
				public PaymentOnlineBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentOnlineBean bean = new PaymentOnlineBean();
					bean.setOpd_user_fk(rs.getString("opd_user_fk"));
					bean.setOpd_transaction_date(rs.getString("opd_transaction_date"));
					bean.setOpd_transaction_no(rs.getString("opd_transaction_no"));
					bean.setOpd_created_by(rs.getString("opd_created_by"));
					bean.setAmount(rs.getString("opd_amount"));
					return bean;
				}
			}, new Object[] { userPk });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (pendingPaymentDetailsList != null && !pendingPaymentDetailsList.isEmpty()) {
			return pendingPaymentDetailsList;
		} else {
			return null;
		}
	}

	@Override
	public PaymentOnlineBean getCandidateTransactionDetailsForOfflineCall(String transactionNumber) throws Exception {
		List<PaymentOnlineBean> transactionDetails = null;
		try {

			transactionDetails = getJdbcTemplate().query(PaymentQueries.GET_TRANSACTION_DETAILS_FOR_SINGLE_OFFLINE_CALL, new RowMapper<PaymentOnlineBean>() {
				public PaymentOnlineBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentOnlineBean bean = new PaymentOnlineBean();
					bean.setOpd_user_fk(rs.getString("opd_user_fk"));
					bean.setOpd_transaction_date(rs.getString("opd_transaction_date"));
					bean.setOpd_transaction_no(rs.getString("opd_transaction_no"));
					bean.setOpd_created_by(rs.getString("opd_created_by"));
					bean.setOpd_test_fk(rs.getString("opd_test_fk"));
					bean.setOpd_app_no(rs.getString("opd_app_no"));
					bean.setOpd_mobile_no(rs.getString("opd_mobile_no"));
					bean.setOpd_email_id(rs.getString("opd_email_id"));
					bean.setOpd_validated_status(rs.getString("opd_validated_status"));
					return bean;
				}
			}, new Object[] { transactionNumber });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (transactionDetails != null && !transactionDetails.isEmpty()) {
			return transactionDetails.get(0);
		} else {
			return null;
		}
	}

	@Override
	public PaymentOnlineBean getApplicantProvidedInfo(String applicationid) throws Exception {
		List<PaymentOnlineBean> transactionDetails = null;
		try {
			transactionDetails = getJdbcTemplate().query(PaymentQueries.GET_APPLICANT_PROVODED_Info, new RowMapper<PaymentOnlineBean>() {
				public PaymentOnlineBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentOnlineBean bean = new PaymentOnlineBean();
					bean.setUserId(rs.getString("oum_user_id"));
					bean.setApplicationNo(rs.getString("opd_app_no"));
					bean.setMobileNo(rs.getString("oum_mobile_no"));
					bean.setOpd_transaction_no(rs.getString("opd_transaction_no"));
					bean.setTransactionNumber(rs.getString("opd_transaction_no"));
					bean.setOpd_validated_status(rs.getString("opd_validated_status"));
					return bean;
				}
			}, new Object[] { Long.parseLong(applicationid) });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (transactionDetails != null && !transactionDetails.isEmpty()) {
			return transactionDetails.get(0);
		} else {
			return null;
		}

	}

	// added by Prudhvi 18-09-2017
	@Override
	public PaymentOnlineBean getCandidateJobMappingInfo(Long UserFk) throws Exception {
		List<PaymentOnlineBean> transactionDetails = null;
		try {
			transactionDetails = getJdbcTemplate().query(PaymentQueries.GET_CANDIDATE_JOBMOPPING_Info, new RowMapper<PaymentOnlineBean>() {
				public PaymentOnlineBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentOnlineBean bean = new PaymentOnlineBean();
					bean.setApplicationNo(rs.getString("ocjm_app_no"));
					bean.setTestFK(rs.getString("ocjm_test_fk"));
					bean.setApplicationNum(rs.getString("ocjm_applicationnumber"));
					return bean;
				}
			}, new Object[] { UserFk });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (transactionDetails != null && !transactionDetails.isEmpty()) {
			return transactionDetails.get(0);
		} else {
			return null;
		}

	}

	@Override
	public PaymentOnlineBean getApplicant_OPD_Transaction_NO(String applicationid) throws Exception {
		List<PaymentOnlineBean> transactionDetails = null;
		try {
			transactionDetails = getJdbcTemplate().query(PaymentQueries.GET_OPD_TXN_NUMBER, new RowMapper<PaymentOnlineBean>() {
				public PaymentOnlineBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentOnlineBean bean = new PaymentOnlineBean();
					bean.setOpd_transaction_no(rs.getString("opd_transaction_no"));
					return bean;
				}
			}, new Object[] { Integer.parseInt(applicationid) });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (transactionDetails != null && !transactionDetails.isEmpty()) {
			return transactionDetails.get(0);
		} else {
			return null;
		}

	}

	@Override
	public void saveOfflineTPResponseSuccess(TechProcessResponseBean techProcessResponseBean) throws Exception {
		int saveDetails = 0;
		try {
			String transDate = techProcessResponseBean.getTxnDate();
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Date df1 = df.parse(transDate);
			String convDate = new SimpleDateFormat("dd-MMM-yyyy").format(df1);
			DateFormat fn = new SimpleDateFormat("dd-MMM-yyyy");
			Date txnDateF = fn.parse(convDate);
			String txnDate = fn.format(txnDateF);

			String amount = "0.00";
			if (techProcessResponseBean.getTxnAmount() != null) {
				amount = techProcessResponseBean.getTxnAmount();
				amount = amount.replaceFirst("^0+(?!$)", "");
			} else {
				System.out.println("Transaction amount got null for Customer ID = " + techProcessResponseBean.getCustomerId());
			}

			saveDetails = writeJdbcTemplate.update(PaymentQueries.UPDATE_OFFLINE_SUCCESS_RESPONSE,
					new Object[] { techProcessResponseBean.getTxnReferenceNo(), CommonUtil.convertToTimestamp(txnDate, GenericConstants.DATE_FORMAT_DEFAULT), "A", amount,
							techProcessResponseBean.getBankId(), techProcessResponseBean.getTransactionMessage(), techProcessResponseBean.getErrorDEsc(),
							techProcessResponseBean.getAuthStatus(), "Offline data pull with Success", techProcessResponseBean.getCustomerId() });
		} catch (Exception e) {
			logger.error(e, e);
		}
	}

	@Override
	public void saveOfflineTPResponse(TechProcessResponseBean techProcessResponseBean) throws Exception {
		int saveDetails = 0;
		try {
			saveDetails = writeJdbcTemplate.update(PaymentQueries.UPDATE_OFFLINE_RESPONSE,
					new Object[] { techProcessResponseBean.getAuthStatus(), techProcessResponseBean.getTransactionMessage(), techProcessResponseBean.getErrorDEsc(),
							"Offline data pull with Not Success", "R", techProcessResponseBean.getCustomerId() });
			logger.info("Transaction saveDetails in payment details = " + saveDetails);

			// if Record not found in OES_PAYMENT_DETAILS Update in
			// OES_PAYMENT_HISTORY
			if (saveDetails == 0) {
				saveDetails = writeJdbcTemplate.update(PaymentQueries.UPDATE_OFFLINE_RESPONSE_IN_HISTORY,
						new Object[] { techProcessResponseBean.getAuthStatus(), techProcessResponseBean.getTransactionMessage(), techProcessResponseBean.getErrorDEsc(),
								"Offline data pull with Not Success", "R", techProcessResponseBean.getCustomerId() });
				logger.info("Transaction saveDetails in OES_PAYMENT_HISTORY = " + saveDetails);
			}
		} catch (Exception e) {
			logger.error(e, e);
			throw new Exception();
		}
	}
	/*
	 * @Override public void saveOfflineTPResponse( TechProcessResponseBean techProcessResponseBean) throws Exception { int saveDetails = 0; try { saveDetails =
	 * writeJdbcTemplate.update(PaymentQueries.UPDATE_OFFLINE_RESPONSE, new Object[] { techProcessResponseBean.getAuthStatus(), techProcessResponseBean.getTransactionMessage(),
	 * techProcessResponseBean.getErrorDEsc(), "Offline data pull with Not Success", techProcessResponseBean.getCustomerId() });
	 * logger.info("Offline failure found for Customer ID = " +techProcessResponseBean.getCustomerId()); String transDate = techProcessResponseBean.getTxnDate(); DateFormat df =
	 * new SimpleDateFormat("dd-MM-yyyy"); Date df1 = df.parse(transDate); String convDate = new SimpleDateFormat("dd-MMM-yyyy").format(df1); DateFormat fn = new
	 * SimpleDateFormat("dd-MMM-yyyy"); Date txnDateF = fn.parse(convDate); String txnDate = fn.format(txnDateF);
	 * 
	 * String amount = "0.00"; if(techProcessResponseBean.getTxnAmount()!= null) { amount = techProcessResponseBean.getTxnAmount(); amount = amount.replaceFirst("^0+(?!$)", ""); }
	 * else { logger.info("Transaction amount got null for Customer ID = " +techProcessResponseBean.getCustomerId()); }
	 * 
	 * saveDetails = writeJdbcTemplate.update(PaymentQueries.UPDATE_OFFLINE_SUCCESS_RESPONSE, new Object[] { techProcessResponseBean.getTxnReferenceNo(),
	 * techProcessResponseBean.getAuthStatus(), CommonUtil.convertToTimestamp(txnDate, GenericConstants.DATE_FORMAT_DEFAULT), "R", //amount, techProcessResponseBean.getBankId(),
	 * techProcessResponseBean.getErrorDEsc(), "Offline data pull with Not Success", techProcessResponseBean.getTransactionMessage(), techProcessResponseBean.getCustomerId() });
	 * logger.info("Transaction saveDetails in Payment details = "+saveDetails);
	 * 
	 * // if Record not found in OES_PAYMENT_DETAILS Update in OES_PAYMENT_HISTORY if(saveDetails == 0) { saveDetails = writeJdbcTemplate.update(PaymentQueries.
	 * UPDATE_OFFLINE_SUCCESS_RESPONSE_IN_HISTORY, new Object[] { techProcessResponseBean.getTxnReferenceNo(), CommonUtil.convertToTimestamp(txnDate,
	 * GenericConstants.DATE_FORMAT_DEFAULT), "R", //amount, techProcessResponseBean.getBankId(), techProcessResponseBean.getTransactionMessage(),
	 * techProcessResponseBean.getErrorDEsc(), techProcessResponseBean.getAuthStatus(), "Offline data pull with Not Success", techProcessResponseBean.getCustomerId() });
	 * logger.info("Transaction saveDetails in OES_PAYMENT_DETAILS_HISTORY = " +saveDetails); } } catch (Exception e) { logger.error(e, e); } }
	 */

	@Override
	public int getDocVerified(Long userFk) throws Exception {
		int count = 0;
		try {
			count = getJdbcTemplate().queryForObject(PaymentOfflineQueries.No_Do_Verified, Integer.class, new Object[] { userFk, userFk });
		} catch (Exception ex) {
			throw new GenericException(ex);
		}
		return count;
	}

	@Override
	public int updateCandidateStatus(Users loggedInUser, String status) throws Exception {
		int updateCandidateStatus = 0;
		try {
			updateCandidateStatus = writeJdbcTemplate.update(CandidateQueries.UPDATE_CANDIDATE_STATUS, new Object[] { Integer.parseInt(status), loggedInUser.getUserId()

			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return updateCandidateStatus;
	}

	public CandidateBean getCandidateDetailsForPaymentReceipt(Users loggedInUser, int id) throws Exception {

		List<CandidateBean> getCandidateDetailsList = null;
		Long userId = 0L;
		if (loggedInUser != null) {
			userId = loggedInUser.getUserId();
		}
		try {
			getCandidateDetailsList = getJdbcTemplate().query(PaymentOfflineQueries.SELECT_CANDIDATE_DETAILS_FOR_PAYMENT_RECEIPT_ENC1, new RowMapper<CandidateBean>() {
				public CandidateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					CandidateBean candidateBean = new CandidateBean();
					PersonalDetailsBean personalBean = new PersonalDetailsBean();
					try {
						personalBean.setCandidateName(rs.getString("oum_full_name"));
					} catch (SQLException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					candidateBean.setIDproof(rs.getString("opd_amount"));
					personalBean.setPayment_date(CommonUtil.formatTimeStamp(rs.getTimestamp("opd_txn_date")));
					personalBean.setOrderId(rs.getString("opd_transaction_no"));
					candidateBean.setAppNumber(rs.getString("opd_txn_ref_no"));
					
					candidateBean.setReceiptAmountInWords(rs.getString("optm_payment_desc"));
					
					
					candidateBean.setPersonalDetailsBean(personalBean);
					if (rs.getString("opd_txn_type") != null && rs.getString("opd_txn_type").equals("CASH")) {
						candidateBean.setPaymentMode("Cash");
					} else {
						candidateBean.setPaymentMode("Online");
					}
					return candidateBean;
				}
			}, new Object[] { ENC_KEY, ENC_KEY, ENC_KEY, userId });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (getCandidateDetailsList != null && !getCandidateDetailsList.isEmpty()) {
			return getCandidateDetailsList.get(0);
		} else {
			return null;
		}
	}

	public CandidateBean getCandidateDetailsForChallanReceipt(Users loggedInUser, int id) throws Exception {

		List<CandidateBean> getCandidateDetailsList = null;
		Long userId = 0L;
		if (loggedInUser != null) {
			userId = loggedInUser.getUserId();
		}
		try {
			getCandidateDetailsList = getJdbcTemplate().query(PaymentOfflineQueries.SELECT_CANDIDATE_DETAILS_FOR_challan_RECEIPT, new RowMapper<CandidateBean>() {
				public CandidateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					CandidateBean candidateBean = new CandidateBean();
					PersonalDetailsBean personalBean = new PersonalDetailsBean();
					// AddressBean address = new AddressBean();

					personalBean.setCandidateFirstName(rs.getString("OCD_FIRST_NAME"));
					// personalBean.setCandidateMiddleName(rs.getString("OCD_MIDDLE_NAME"));
					personalBean.setCandidateLastName(rs.getString("OUM_INITIAL"));
					personalBean.setDateOfBirth(CommonUtil.formatTimeStamp(rs.getTimestamp("opd_transaction_date")));
					/*
					 * personalBean.setMobileNo(rs.getString( "opd_transaction_no"));
					 */

					personalBean.setMobileNo(rs.getString("oum_mobile_no"));
					// candidateBean.setCategoryVal(rs.getString("otm_test_name"));
					candidateBean.setReceiptAmount(rs.getString("opd_transaction_no"));
					candidateBean.setReceiptAmountInWords(rs.getString("optm_payment_desc"));
					// candidateBean.setAppNumber(rs.getString("ocjm_applicationnumber"));
					candidateBean.setScoreForInterview(rs.getString("opd_dd_challan_receipt_no"));
					// candidateBean.setImageCast(rs.getString("rec_trans_ser"));
					// candidateBean.setAltTalukaField(rs.getString("rec_rect_no"));
					candidateBean.setTestCity(rs.getString("ocd_date_of_birth"));
					candidateBean.setIDproof(rs.getString("opd_amount"));
					candidateBean.setPersonalDetailsBean(personalBean);

					if (rs.getString("opd_txn_type") != null && rs.getString("opd_txn_type").equals("CASH")) {
						candidateBean.setPaymentMode("Cash");
					} else {
						candidateBean.setPaymentMode("cash");
					}

					return candidateBean;
				}
			}, new Object[] { userId });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (getCandidateDetailsList != null && !getCandidateDetailsList.isEmpty()) {
			return getCandidateDetailsList.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<PaymentOnlineBean> newGetPendingPaymentRecords(String UserId) throws Exception {
		List<PaymentOnlineBean> pendingPaymentDetailsList = null;
		try {
			pendingPaymentDetailsList = getJdbcTemplate().query(PaymentQueries.NEW_GET_TRANSACTION_DETAILS_FOR_SINGLE_OFFLINE_CALL, new RowMapper<PaymentOnlineBean>() {
				public PaymentOnlineBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentOnlineBean bean = new PaymentOnlineBean();
					bean.setOpd_user_fk(rs.getString("opd_user_fk"));
					bean.setOpd_transaction_date(rs.getString("opd_transaction_date"));
					bean.setOpd_transaction_no(rs.getString("opd_transaction_no"));
					bean.setOpd_created_by(rs.getString("opd_created_by"));
					bean.setOpd_test_fk(rs.getString("opd_test_fk"));
					bean.setOpd_app_no(rs.getString("opd_app_no"));
					bean.setOpd_mobile_no(rs.getString("opd_mobile_no"));
					bean.setOpd_email_id(rs.getString("opd_email_id"));
					return bean;
				}
			}, new Object[] { UserId.toUpperCase().trim() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		if (pendingPaymentDetailsList != null && !pendingPaymentDetailsList.isEmpty()) {
			return pendingPaymentDetailsList;
		} else {
			return null;
		}
	}

	public int copyPaymentDetailsForEnrollment(Users loggedInUser) throws Exception {
		int paymentFlag = 0;
		int validateStatus = 0;
		try {
			paymentFlag = writeJdbcTemplate.update(PaymentOfflineQueries.COPY_PAYMENT_DETAILS_FOR_ENROLLMENT, new Object[] { loggedInUser.getUserFk() });
			if (paymentFlag > 0) {
				logger.info("setting validated status as null  of userfk " + loggedInUser.getUserFk());
				validateStatus = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_PAYMENT_DETAILS_FOR_Current_Transaction, new Object[] { loggedInUser.getUserFk() });
			}
		} catch (Exception e) {
			throw new GenericException(e);
		}

		return paymentFlag;
	}

	public int opd_paymentStatusDateCheckUpdate(Users loggedInUser) throws Exception {
		int opd_paymentStatusDateCheckUpdate = 0;
		try {
			logger.info("Updating opd_payment_status_date by for opd_fk=1 by " + loggedInUser.getUserFk());
			opd_paymentStatusDateCheckUpdate = writeJdbcTemplate.update(PaymentOfflineQueries.opd_paymentStatusDateCheckUpdate, new Object[] { loggedInUser.getUserFk() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return opd_paymentStatusDateCheckUpdate;
	}

	public int NoTransactionFoundFlagUpdate(Users loggedInUser) throws Exception {
		int NoTransactionFoundFlagUpdate = 0;
		try {
			logger.info("Updating opd_validated_status as R for not found Transaction from Sbi , check payment status clicked by " + loggedInUser.getUserFk());
			NoTransactionFoundFlagUpdate = writeJdbcTemplate.update(PaymentOfflineQueries.NoTransactionFoundFlagUpdate, new Object[] { loggedInUser.getUserFk() });
		} catch (Exception e) {
			throw new GenericException(e);
		}
		return NoTransactionFoundFlagUpdate;
	}

	public Long getUserPK(String registrationId) throws Exception {
		logger.debug("Calling getUserPK()");
		Long userId;
		try {
			// userId = (Long)
			// getJdbcTemplate().queryForObject(CandidateQueries.GET_USER_ID,
			// new Object[]{registrationId}, Long.class);
			userId = (Long) getJdbcTemplate().queryForObject(CandidateQueries.GET_USER_PK, Long.class, new Object[] { registrationId });
		} catch (Exception e) {
			userId = new Long(0);
		}
		return userId;
	}

	public int getApprovedPaymentStatusCount(Users loggedInUser) throws Exception {
		int approvPaymentCount = 0;
		try {
			approvPaymentCount = getJdbcTemplate().queryForObject(PaymentOfflineQueries.GET_APPROV_PAYMENT_COUNT, Integer.class, new Object[] { loggedInUser.getUserId() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return approvPaymentCount;
	}

	public int getApprovedPaymentStatusCount1(Users loggedInUser) throws Exception {
		int approvPaymentCount = 0;
		try {
			approvPaymentCount = getJdbcTemplate().queryForObject(PaymentOfflineQueries.GET_APPROV_PAYMENT_COUNT1, Integer.class, new Object[] { loggedInUser.getUserId() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return approvPaymentCount;
	}

	public int reconcilePaymentAjaxckeck(Users loggedInUser) throws Exception {
		int reconcilePaymentAjaxckeck = 0;
		try {
			reconcilePaymentAjaxckeck = getJdbcTemplate().queryForObject(PaymentOfflineQueries.reconcilePaymentAjaxckeck, Integer.class, new Object[] { loggedInUser.getUserId() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reconcilePaymentAjaxckeck;
	}

	public int getApprovedPaymentStatusCount2(Users loggedInUser) throws Exception {
		int approvPaymentCount = 0;
		try {
			approvPaymentCount = getJdbcTemplate().queryForObject(PaymentOfflineQueries.GET_APPROV_PAYMENT_COUNT2, Integer.class, new Object[] { loggedInUser.getUserId() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return approvPaymentCount;
	}

	public int opd_paymentStatusDateCheck(Users loggedInUser) throws Exception {
		int opd_paymentStatusDateCheck = 0;
		try {
			opd_paymentStatusDateCheck = getJdbcTemplate().queryForObject(PaymentOfflineQueries.opd_paymentStatusDateCheck, Integer.class,
					new Object[] { loggedInUser.getUserId() });
		} catch (Exception e) {
			e.printStackTrace();
		}
		return opd_paymentStatusDateCheck;
	}

	public int updatePaymentCreatedDate(Users loggedInUser) throws Exception {
		int approvPaymentCount = 0;// for update
		int copytohistory = 0;
		try {
			logger.info("copying challan details to payment history table from payment details");

			copytohistory = writeJdbcTemplate.update(PaymentOfflineQueries.Copytohistoryforchallan, new Object[] { loggedInUser.getUserId() });

			if (copytohistory == 1) {
				logger.info("copied challan details to payment history table from payment details");
				approvPaymentCount = writeJdbcTemplate.update(PaymentOfflineQueries.updatePaymentCreatedDate, new Object[] { loggedInUser.getUserId() });
				logger.info("updated created date of Challan download after 72 hours");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return approvPaymentCount;
	}

	public int getIncompleteOnlinePaymentCount(Users loggedInUser) throws Exception {
		int incompletePaymentCount = 0;
		incompletePaymentCount = getJdbcTemplate().queryForObject(PaymentOfflineQueries.GET_INCOMPLETE_ONLINE_PAYMENT_COUNT, Integer.class,
				new Object[] { loggedInUser.getUserId() });
		return incompletePaymentCount;
	}

	public int getIncompleteOnlinePaymentCount1(Users loggedInUser) throws Exception {
		int incompletePaymentCount = 0;
		incompletePaymentCount = getJdbcTemplate().queryForObject(PaymentOfflineQueries.GET_INCOMPLETE_ONLINE_PAYMENT_COUNT1, Integer.class,
				new Object[] { loggedInUser.getUserId() });
		return incompletePaymentCount;
	}

	public int checkSuccReInitiatePaymentCount(Users loggedInUser) throws Exception {
		int incompletePaymentCount = 0;
		incompletePaymentCount = getJdbcTemplate().queryForObject(PaymentOfflineQueries.GET_REINITIATE_ONLINE_PAYMENT_COUNT, Integer.class,
				new Object[] { loggedInUser.getUserId() });
		return incompletePaymentCount;
	}

	public int checkPaymentCashBookedCount(Users loggedInUser) throws Exception {
		int incompletePaymentCount = 0;
		incompletePaymentCount = getJdbcTemplate().queryForObject(PaymentOfflineQueries.GET_BOOKED_ONLINE_PAYMENT_COUNT, Integer.class, new Object[] { loggedInUser.getUserId() });
		return incompletePaymentCount;
	}

	public String getUnderQuota(Long userId) throws Exception {
		String underQuotaOption = null;
		try {
			underQuotaOption = (String) getJdbcTemplate().queryForObject(PaymentOfflineQueries.GET_UNDER_QUOTA_CHECK, String.class, new Object[] { userId });
		} catch (Exception e) {
			underQuotaOption = null;
		}
		if (underQuotaOption != null && underQuotaOption != "") {
			return underQuotaOption;
		} else {
			return underQuotaOption = "";
		}
	}

	@Override
	public List<PaymentOnlineBean> getPendingPaymentRecordsForTechProcess() throws Exception {
		List<PaymentOnlineBean> pendingPaymentDetailsList = null;
		try {
			pendingPaymentDetailsList = getJdbcTemplate().query(PaymentQueries.GET_PENDING_BULK_PAYMENT_DETAILS_TP, new RowMapper<PaymentOnlineBean>() {
				public PaymentOnlineBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					PaymentOnlineBean bean = new PaymentOnlineBean();
					bean.setOpd_table_check(rs.getString("OPD_TABLE_CHECK"));
					bean.setOpd_user_fk(rs.getString("OPD_USER_FK"));
					bean.setOpd_transaction_date(rs.getString("OPD_TRANSACTION_DATE"));
					bean.setOpd_transaction_no(rs.getString("OPD_TRANSACTION_NO"));
					bean.setOpd_created_by(rs.getString("OPD_CREATED_BY"));
					bean.setOpd_test_fk(rs.getString("OPD_TEST_FK"));
					// bean.setOpd_sub_test_fk(rs.getString("OPD_SUB_TEST_FK"));
					// bean.setOpd_app_no(rs.getString("OPD_APPLICATION_NO"));
					bean.setOpd_mobile_no(rs.getString("OPD_MOBILE_NO"));
					bean.setOpd_email_id(rs.getString("OPD_EMAIL_ID"));
					bean.setAmount(rs.getString("OPD_AMOUNT"));
					bean.setUserId(rs.getString("OUM_USER_ID"));
					return bean;
				}
			});

		} catch (Exception e) {
			pendingPaymentDetailsList = null;
			// throw new GenericException(e);
			logger.fatal(e, e);
		}
		if (pendingPaymentDetailsList != null && !pendingPaymentDetailsList.isEmpty()) {
			return pendingPaymentDetailsList;
		} else {
			return null;
		}
	}

	@Override
	public void saveOfflineTPResponseSuccess(TechProcessResponseBean techProcessResponseBean, String userFk) throws Exception {
		int saveDetails = 0;
		try {
			String transDate = techProcessResponseBean.getTxnDate();
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			Date df1 = df.parse(transDate);
			String convDate = new SimpleDateFormat("dd-MMM-yyyy").format(df1);
			DateFormat fn = new SimpleDateFormat("dd-MMM-yyyy");
			Date txnDateF = fn.parse(convDate);
			String txnDate = fn.format(txnDateF);

			String amount = "0.00";
			if (techProcessResponseBean.getTxnAmount() != null) {
				amount = techProcessResponseBean.getTxnAmount();
				amount = amount.replaceFirst("^0+(?!$)", "");
			} else {
				logger.info("Transaction amount got null for Customer ID = " + techProcessResponseBean.getCustomerId());
			}

			saveDetails = writeJdbcTemplate.update(PaymentQueries.UPDATE_OFFLINE_SUCCESS_RESPONSE,
					new Object[] { techProcessResponseBean.getTxnReferenceNo(), techProcessResponseBean.getAuthStatus(),
							CommonUtil.convertToTimestamp(txnDate, GenericConstants.DATE_FORMAT_DEFAULT), "A",
							// amount,
							techProcessResponseBean.getBankId(), techProcessResponseBean.getErrorDEsc(), "Offline data pull with Success",
							techProcessResponseBean.getTransactionMessage(), techProcessResponseBean.getCustomerId() });
			logger.info("Transaction saveDetails in Payment details = " + saveDetails);

			// if Record not found in OES_PAYMENT_DETAILS Update in
			// OES_PAYMENT_HISTORY
			if (saveDetails == 0) {
				saveDetails = writeJdbcTemplate.update(PaymentQueries.UPDATE_OFFLINE_SUCCESS_RESPONSE_IN_HISTORY,
						new Object[] { techProcessResponseBean.getTxnReferenceNo(), techProcessResponseBean.getAuthStatus(),
								CommonUtil.convertToTimestamp(txnDate, GenericConstants.DATE_FORMAT_DEFAULT), "A",
								// amount,
								techProcessResponseBean.getBankId(), techProcessResponseBean.getErrorDEsc(), "Offline data pull with Success",
								techProcessResponseBean.getTransactionMessage(), techProcessResponseBean.getCustomerId() });
				logger.info("Transaction saveDetails in OES_PAYMENT_DETAILS_HISTORY = " + saveDetails);

				// Code added to move Success record into payment details and
				// remove unsuccessful record from OPD to OPH
				// if Success record is already present in OPD then don't do
				// anything
				String count = null;
				String countApprove = null;
				try {
					count = (String) getJdbcTemplate().queryForObject(PaymentQueries.GET_CANDIDATE_RECORD_FROM_OPD, String.class, new Object[] { Long.parseLong(userFk) });

					countApprove = (String) getJdbcTemplate().queryForObject(PaymentQueries.GET_CANDIDATE_APPROVED_RECORD_FROM_OPD, String.class,
							new Object[] { Long.parseLong(userFk) });
				} catch (Exception e) {

				}
				// If Rejected or null record present in OPD
				if (count != null) {

					int insertDetails = writeJdbcTemplate.update(PaymentQueries.INSERT_INTO_PAYMENT_HISTORY_O_CALL, new Object[] { Long.parseLong(userFk) });

					if (insertDetails > 0) {
						int deletePaymentFlag = writeJdbcTemplate.update(PaymentQueries.DELETE_REJECTED_PAYMENT_DETAILS_FROM_OPD, new Object[] { Long.parseLong(userFk) });
					}

					int insertIntoOPD = writeJdbcTemplate.update(PaymentQueries.INSERT_INTO_OES_PAYMENT_DETAILS_O_CALL,
							new Object[] { Long.parseLong(userFk), techProcessResponseBean.getCustomerId() });
					if (insertIntoOPD > 0) {
						int deletePaymentFlag = writeJdbcTemplate.update(PaymentQueries.DELETE_APPROVED_PAYMENT_DETAILS_FROM_OPH,
								new Object[] { Long.parseLong(userFk), techProcessResponseBean.getCustomerId() });
					}
				} else {
					if (countApprove != null) {
						int insertIntoOPD = writeJdbcTemplate.update(PaymentQueries.INSERT_INTO_OES_PAYMENT_DETAILS_O_CALL,
								new Object[] { Long.parseLong(userFk), techProcessResponseBean.getCustomerId() });
						if (insertIntoOPD > 0) {
							int deletePaymentFlag = writeJdbcTemplate.update(PaymentQueries.DELETE_APPROVED_PAYMENT_DETAILS_FROM_OPH,
									new Object[] { Long.parseLong(userFk), techProcessResponseBean.getCustomerId() });
						}
					}
					logger.info("Double Approval Payment found for User:" + userFk + "Kindly Refund." + techProcessResponseBean.getCustomerName());
				}
			}

			if (saveDetails > 0) {
				Users users = new Users();

				users = getUserDetailsForEmailForApplicationNo(Long.parseLong(userFk));

				String staus = ConfigurationConstants.getInstance().getStatusKey(GenericConstants.PAYMENT_APPROVED) + "";
				Integer emailReq = ConfigurationConstants.getInstance().getEmailValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
				Integer smsReq = ConfigurationConstants.getInstance().getSmsValForStatusDesc(GenericConstants.PAYMENT_APPROVED);
				/*
				 * EmailSMSUtil.insertEmailNSMSForActivitynTestForPayment(ConfigurationConstants.getInstance().getActivtyKeyForName(GenericConstants.PAYMENT_APPROVED),
				 * Integer.parseInt(users.getDisciplineID()), users.getUsername(), users.getUsername(), staus, users, emailReq, smsReq);
				 */
				/*
				 * staus = ConfigurationConstants.getInstance().getStatusKey( GenericConstants.APPLICATION_COMPLETED)+""; emailReq =
				 * ConfigurationConstants.getInstance().getEmailValForStatusDesc (GenericConstants.APPLICATION_COMPLETED); smsReq =
				 * ConfigurationConstants.getInstance().getSmsValForStatusDesc( GenericConstants.APPLICATION_COMPLETED);
				 * 
				 * EmailSMSUtil.insertEmailNSMSForActivitynTestForApplicationNo( ConfigurationConstants.getInstance().getActivtyKeyForName(
				 * GenericConstants.APPLICATION_COMPLETED),Integer.parseInt( users.getDisciplineID()), users.getUsername(), users.getUsername(),staus,users,emailReq,smsReq);
				 */
			}

		} catch (Exception e) {
			logger.error(e, e);
			throw new Exception();
		}
	}

	@Override
	public Users getUserDetailsForEmailForApplicationNo(Long userPk) throws Exception {
		List<Users> lstUsers = null;
		try {
			lstUsers = getJdbcTemplate().query(PaymentQueries.SELECT_USER_FOR_APPLICATION_NO_EMAIL, new RowMapper<Users>() {
				public Users mapRow(ResultSet rs, int rowCount) throws SQLException {
					Users users = new Users();
					users.setUserId(rs.getLong("OUM_USER_PK"));
					users.setUserFk(rs.getLong("OUM_USER_PK"));
					users.setUsername(rs.getString("OUM_USER_ID"));
					users.setDisciplineID(rs.getString("OUM_TEST_FK"));
					return users;
				}
			}, new Object[] { userPk });
		} catch (Exception e) {
			throw new GenericException(e);
		}

		if (lstUsers.size() > 0)
			return lstUsers.get(0);
		else
			return null;
	}

	@Override
	public void saveOfflineTPOfflineFailedResponse(List<PaymentOnlineBean> failedTypeOList) throws Exception {
		int saveDetails = 0;
		try {
			for (PaymentOnlineBean paymentOnlineBean : failedTypeOList) {
				saveDetails = writeJdbcTemplate.update(PaymentQueries.INSERT_INTO_OES_PAYMENT_TYPE_O_HISTORY,
						new Object[] { Integer.parseInt(paymentOnlineBean.getOpd_user_fk()), paymentOnlineBean.getOpd_transaction_date(), paymentOnlineBean.getOpd_transaction_no(),
								paymentOnlineBean.getOpd_validated_status(), paymentOnlineBean.getOpd_created_by(), Integer.parseInt(paymentOnlineBean.getOpd_test_fk()) });
			}
			logger.info("O call Transaction details save in OES_PAYMENT_TYPE_O_HISTORY = " + saveDetails);
		} catch (Exception e) {
			logger.error(e, e);
			throw new Exception();
		}
	}

	@Override
	public String getTransactionUniqueNumber() throws Exception {
		// logger.info("Calling getTransactionUniqueNumber()");
		String txnNumber = "";
		try {
			txnNumber = (String) writeJdbcTemplate.queryForObject(PaymentQueries.MAX_PAYMENT_ID, String.class);
		} catch (Exception e) {
			txnNumber = "";
		}
		return txnNumber;
	}

	@Override
	public String getCandidateCaste(Users loggedInUser) throws Exception {
		String candidateCaste = "";
		try {
			candidateCaste = (String) getJdbcTemplate().queryForObject(PaymentQueries.GET_CANDIDATE_CATEGORY, String.class, new Object[] { loggedInUser.getUserFk() });
		} catch (Exception e) {
			candidateCaste = "";
		}
		return candidateCaste;
	}

	@Override
	public String getCandidateDisabilityForPayment(Users loggedInUser) throws Exception {
		String disability = "";
		try {
			disability = (String) getJdbcTemplate().queryForObject(PaymentQueries.GET_CANDIDATE_DISABILITY, String.class, new Object[] { loggedInUser.getUserFk() });
		} catch (Exception e) {
			disability = "";
		}
		return disability;
	}

	@Override
	public String getCandidatePaymentStatus(Users loggedInUser) throws Exception {
		String paymentStatus = "";
		try {
			paymentStatus = (String) getJdbcTemplate().queryForObject(PaymentQueries.GET_CANDIDATE_PAYMENT_STATUS, String.class, new Object[] { loggedInUser.getUserFk() });
		} catch (Exception e) {
			paymentStatus = "";
		}
		return paymentStatus;
	}

	public String getCandidateCasteForAdmin(String loggedInUser) throws Exception {
		String candidateCaste = "";
		try {
			candidateCaste = (String) getJdbcTemplate().queryForObject(PaymentQueries.GET_CANDIDATE_CATEGORY_ADMIN, String.class, new Object[] { Integer.parseInt(loggedInUser) });
		} catch (Exception e) {
			logger.fatal(e);
			candidateCaste = "";
		}
		return candidateCaste;
	}

	@Override
	public String getCandidateDisabilityForPaymentForAdmin(String userFK) throws Exception {
		String disability = "";
		try {
			disability = (String) getJdbcTemplate().queryForObject(PaymentQueries.GET_CANDIDATE_DISABILITY_FOR_ADMIN, String.class, new Object[] { Integer.parseInt(userFK) });
		} catch (Exception e) {
			disability = "";
		}
		return disability;
	}

	@Override
	public int insertOnlinTransactionDetails(SBIePayResponseBean sbiResponseBean, Users users) throws Exception {

		logger.info("insertOnlinTransactionDetails called User Level User Id:" + users.getUsername());
		int saveUpdate = 0;
		String validate_status = "";
		String opd_status = "";
		int updateCourseMapping = 0;
		String applicationNo = "";
		if ("Success".equals(sbiResponseBean.getAuthStatus())) {
			validate_status = "A";
			opd_status = "A";

		} else if ("Failure".equals(sbiResponseBean.getAuthStatus())) {
			validate_status = "R";
			opd_status = "N";
		} else {
			validate_status = "R";
			opd_status = "N";
		}

		String transDate = sbiResponseBean.getTxnDate();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
		Date df1 = df.parse(transDate);
		String convDate = new SimpleDateFormat("dd-MMM-yyyy").format(df1);
		DateFormat fn = new SimpleDateFormat("dd-MMM-yyyy");
		Date txnDateF = fn.parse(convDate);
		String txnDate = fn.format(txnDateF);
		String amount = "0.00";
		if (sbiResponseBean.getTxnAmount() != null) {
			amount = sbiResponseBean.getTxnAmount();
			amount = amount.replaceFirst("^0+(?!$)", "");
		} else {
			System.out.println("Transaction amount got null");
			return 0;
		}

		try {
			saveUpdate = writeJdbcTemplate.update(PaymentQueries.UPDATE_ONLINE_DETAILS,
					new Object[] { sbiResponseBean.getAdditionalInfo2(), CommonUtil.convertToTimestamp(transDate, "yyyy-MM-dd HH:mm:ss"), validate_status,
							sbiResponseBean.getBankId(), users.getUserId(), sbiResponseBean.getAuthStatus(), opd_status, sbiResponseBean.getErrorDEsc(), "Online",
							sbiResponseBean.getCheckSum(), sbiResponseBean.getAdditionalInfo2(), sbiResponseBean.getAdditionalInfo4(), // OPD_REMARKS
							users.getUserFk(), sbiResponseBean.getTxnReferenceNo() });
			if (saveUpdate > 0) {
				/*
				 * if(validate_status.equals("A")) { //applicationNo = (String) getJdbcTemplate().queryForObject(CandidateQueries. GET_TEST_FK_PAYMENT, new
				 * Object[]{users.getUserFk(),billDeskResponseBean. getAdditionalInfo1()}, String.class); updateCourseMapping = writeJdbcTemplate.update(PaymentQueries.
				 * UPDATE_COURSE_MAPPING_PAYMENT_STATUS, new Object[]{"A", users.getUserFk(), billDeskResponseBean.getAdditionalInfo1() }); }
				 */

			}
		} catch (Exception e) {
			logger.fatal(e, e);
			throw e;
		}
		return saveUpdate;

	}

	@Override
	public Boolean getCandidateApplicableForDualPost(Users loggedInUser) {
		Boolean applicableFlag = false;
		int cnt = getJdbcTemplate().queryForObject(PaymentQueries.GET_DUAL_POST_ELIGIBILITY, Integer.class, new Object[] { loggedInUser.getUserFk() });

		if (cnt > 0) {
			applicableFlag = true;
		}

		return applicableFlag;
	}

	@Override
	public String getPaymentUserId(String transNo) throws Exception {
		String result = null;
		try {
			result = (String) getJdbcTemplate().queryForObject(PaymentQueries.GET_USER_FOR_TRANS, String.class, new Object[] { transNo });
		} catch (Exception e) {
			logger.fatal(e, e);
		}
		return result;

	}

	@Override
	public void updateTransactionToNull(PaymentOnlineBean paymentBean, Users loggedInUser, String existmercntTxnNo) {
		try {
			writeJdbcTemplate.update(PaymentQueries.COPY_TO_PAYMENT_HISTORY, new Object[] { existmercntTxnNo });
			logger.info("Copied Reject Transaction to History table for USERID " + loggedInUser.getUsername());
			writeJdbcTemplate.update(PaymentQueries.UPDATE_OPD_TRANSACTION, new Object[] { loggedInUser.getUserId() });
			logger.info("updated existing transaction status to null " + loggedInUser.getUsername());
		} catch (Exception e) {
			logger.fatal(e, e);
		}
	}

	public CandidateBean getCandidateDetailsForPaymentUrl(Users loggedInUser) {
		List<CandidateBean> getCandidateDetailsList = null;
		Long userId = 0L;
		if (loggedInUser != null) {
			userId = loggedInUser.getUserId();
		}
		try {
			getCandidateDetailsList = getJdbcTemplate().query(PaymentOfflineQueries.SELECT_CANDIDATE_DETAILS_FOR_PAYMENT_URL, new RowMapper<CandidateBean>() {
				public CandidateBean mapRow(ResultSet rs, int rowCount) throws SQLException {
					CandidateBean candidateBean = new CandidateBean();
					AddressBean address = new AddressBean();
					candidateBean.setCandidateName(rs.getString("oum_full_name"));
					address.setAlternateAddress(rs.getString("OCD_COMM_ADDRESS"));
					candidateBean.setAltStateVal(rs.getString("OCD_COMM_STATE_FK"));
					candidateBean.setAltDistrictVal(rs.getString("OCD_COMM_DISTRICT_FK"));
					address.setAlternateCityother(rs.getString("OCD_COMM_CITY"));
					address.setAlternatePinCode(rs.getString("OCD_COMM_PIN_CODE"));
					candidateBean.setAddressBean(address);
					return candidateBean;
				}
			}, new Object[] { userId });
		} catch (Exception e) {
			logger.fatal(e, e);
		}

		if (getCandidateDetailsList != null && !getCandidateDetailsList.isEmpty()) {
			return getCandidateDetailsList.get(0);
		} else {
			return null;
		}
	}

	// ########################################################################################################################## TESTING

	@Override
	public int insertAutoApprovePayment(Users loggedInUser) throws Exception {
		int insert = 0;
		try {
			String recordCount = "select count(1) from oes_payment_details where opd_user_fk = ?";
			int count = getJdbcTemplate().queryForObject(recordCount, Integer.class, new Object[] { loggedInUser.getUserFk() });
			if (count > 0) {
				insert = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_AUTO_APPROVE_PAYMENT, new Object[] { loggedInUser.getUserId(), loggedInUser.getUsername() });
			} else {
				insert = writeJdbcTemplate.update(PaymentOfflineQueries.INSERT_AUTO_APPROVE_PAYMENT, new Object[] { loggedInUser.getUserId(), 0, loggedInUser.getUsername() });
			}

		} catch (Exception e) {
			throw new GenericException(e);
		}
		return insert;
	}

	@Override
	public int insertAutoRejectPayment(Users loggedInUser) throws Exception {
		int insert = 0;
		try {
			String recordCount = "select count(1) from oes_payment_details where opd_user_fk = ?";
			int count = getJdbcTemplate().queryForObject(recordCount, Integer.class, new Object[] { loggedInUser.getUserFk() });
			if (count > 0) {
				insert = writeJdbcTemplate.update(PaymentOfflineQueries.UPDATE_AUTO_REJECT_PAYMENT, new Object[] { loggedInUser.getUserId(), loggedInUser.getUsername() });
			} else {
				insert = writeJdbcTemplate.update(PaymentOfflineQueries.INSERT_AUTO_REJECT_PAYMENT, new Object[] { loggedInUser.getUserId(), 0, loggedInUser.getUsername() });
			}

		} catch (Exception e) {
			throw new GenericException(e);
		}
		return insert;
	}

	@Override
	public int updateSafexResponseForCandidate(SafexResponseBean safexResponseBean, Users users) throws Exception {
		logger.info("insertOnlinTransactionDetails called User Level User Id:" + users.getUsername());
		int saveUpdate = 0;
		String validate_status = "";
		String opd_status = "";
		/*String registrationId = "";*/
		if ("Successful".equalsIgnoreCase(safexResponseBean.getStatus())) {
			validate_status = "A";
			opd_status = "A";
		} else if ("Failed".equalsIgnoreCase(safexResponseBean.getStatus())) {
			validate_status = "R";
			opd_status = "N";
		} else if ("Pending".equalsIgnoreCase(safexResponseBean.getStatus())) {
			validate_status = "P";
			opd_status = "N";
		} else {
			validate_status = null;
			opd_status = "N";
		}

		String amount = "0.00";
		if (safexResponseBean.getAmount() != null) {
			amount = safexResponseBean.getAmount();
			amount = amount.replaceAll("(?<=^\\d+)\\.0*$", "");
		} else {
			amount = ConfigurationConstants.getInstance().getPropertyVal("GENERAL_OBC_AMOUNT");
		}
		try {
			saveUpdate = writeJdbcTemplate.update(PaymentQueries.UPDATE_ONLINE_DETAILS,
					new Object[] { CommonUtil.convertToTimestamp(safexResponseBean.getTxn_date(), "yyyy-MM-dd"), validate_status, users.getUsername(),
							safexResponseBean.getStatus(), opd_status, safexResponseBean.getRes_message(), safexResponseBean.getPaymode(), safexResponseBean.getPg_ref(),
							safexResponseBean.getAg_ref(), amount, safexResponseBean.getPg_name(), safexResponseBean.getPg_id(), users.getUserFk(),
							safexResponseBean.getOrder_no() });
		} catch (Exception e) {
			logger.fatal(e, e);
			throw e;
		}
		return saveUpdate;
	}

	@Override
	public int insertOnlinTransactionDetailsForAtom(AtomResponseBean atomResponseBean, Users users) throws Exception {
		logger.info("Atom Update Response Calling :For User ID [" + users.getUsername() + "] AuthStatus:" + atomResponseBean.getAuthStatus());
		int saveUpdate = 0;
		String validate_status = "";
		String opd_status = "";
		try {
			if (GenericConstants.AUTH_STATUS_SUCCESS.equals(atomResponseBean.getAuthStatus())) {
				validate_status = "A";
				opd_status = "A";
			} else if (GenericConstants.AUTH_STATUS_FAILURE.equals(atomResponseBean.getAuthStatus())) {
				validate_status = "R";
				opd_status = "N";
			} else {
				validate_status = "";
				opd_status = "N";
			}
			String amount = "0.00";
			if (atomResponseBean.getResponse_amt() != null) {
				amount = atomResponseBean.getResponse_amt();
				amount = amount.replaceFirst("^0+(?!$)", "");
			} else {
				logger.info("Transaction amount got null for Customer ID = " + atomResponseBean.getCandidateUserId());
				return 0;
			}
			String transDate = atomResponseBean.getTxn_resp_date();
			DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = (Date) formatter.parse(transDate);
			String convDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss").format(date);
			DateFormat fn = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
			Date txnDateF = fn.parse(convDate);
			String txnDate = fn.format(txnDateF);
			System.out.println("Formatted Date:" + txnDate);

			saveUpdate = writeJdbcTemplate.update(PaymentQueries.UPDATE_ONLINE_DETAILS1, new Object[] { atomResponseBean.getBank_txn(), // bank transaction no
					atomResponseBean.getMmp_txn(), // atom transaction no
					atomResponseBean.getBankname(), CommonUtil.convertToTimestamp(txnDate, GenericConstants.DATE_TIME_DEFAULT), validate_status,
					atomResponseBean.getDiscriminator(), users.getUsername(), opd_status, amount, atomResponseBean.getDesc(), users.getUserId(), atomResponseBean.getMer_txn(), });
			logger.info("Atom Update Response saveUpdate Cnt:" + saveUpdate);
		} catch (Exception e) {
			logger.info("Exception Atom Update Response Fail :For User ID [" + users.getUsername() + "] Error :" + e.getMessage());
			logger.fatal(e, e);
			saveUpdate = 0;
			// throw e;
		}
		return saveUpdate;
	}

	@Override
	public int updateAtomResponseForCandidate(AtomResponseBean atomResponseBean, Users users) throws Exception {
		logger.info("insertOnlinTransactionDetails called User Level User Id:" + users.getUsername());
		int saveUpdate = 0;
		String validate_status = "";
		String opd_status = "";
		if (atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0000")) {
			validate_status = "A";
			opd_status = "A";
		} else if (atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0101") || atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0201")
				|| atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0401") || atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0600")
				|| atomResponseBean.getAuthStatus().equalsIgnoreCase("OTS0301") || atomResponseBean.getAuthStatus().equalsIgnoreCase("OTS0351")) {
			validate_status = "R";
			opd_status = "N";
		} else if (atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0551") || atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0451")
				|| atomResponseBean.getStatusCode().equalsIgnoreCase("OTS0501")) {
			validate_status = null;
			opd_status = "N";
		} else {
			validate_status = "R";
			opd_status = "N";
		}

		String amount = "0.00";
		if (atomResponseBean.getResponse_amt() != null) {
			amount = atomResponseBean.getResponse_amt();
			amount = amount.replaceAll("(?<=^\\d+)\\.0*$", "");
		}
		if (atomResponseBean.getMmp_txn() != null && "null".equalsIgnoreCase(atomResponseBean.getMmp_txn())) {
			atomResponseBean.setMmp_txn(null);
		}
		if (atomResponseBean.getBankname() != null && "null".equalsIgnoreCase(atomResponseBean.getBankname())) {
			atomResponseBean.setBankname(null);
		}
		if (atomResponseBean.getDiscriminator() != null && "null".equalsIgnoreCase(atomResponseBean.getDiscriminator())) {
			atomResponseBean.setDiscriminator(null);
		}
		if (atomResponseBean.getTxn_resp_date() != null && "null".equalsIgnoreCase(atomResponseBean.getTxn_resp_date())) {
			atomResponseBean.setTxn_resp_date(null);
		}

		
		String transDate = atomResponseBean.getTxn_resp_date();
		
		if(transDate.length() == 16)
			transDate = transDate+":00";
		
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = (Date) formatter.parse(transDate);
		String convDate = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss").format(date);
		DateFormat fn = new SimpleDateFormat("dd-MMM-yyyy hh:mm:ss");
		Date txnDateF = fn.parse(convDate);
		String txnDate = fn.format(txnDateF);
		System.out.println("Formatted Date:" + txnDate);

		try {
			saveUpdate = writeJdbcTemplate.update(PaymentQueries.UPDATE_ONLINE_DETAILS,
					new Object[] { CommonUtil.convertToTimestamp(txnDate, GenericConstants.DATE_TIME_DEFAULT), validate_status, users.getUsername(),
							atomResponseBean.getAuthStatus(), opd_status, atomResponseBean.getDesc(), atomResponseBean.getDiscriminator(), atomResponseBean.getBank_txn(),
							atomResponseBean.getMmp_txn(), atomResponseBean.getBankname(), amount, users.getUserId(), atomResponseBean.getMer_txn() });
		} catch (Exception e) {
			logger.fatal(e, e);
			throw e;
		}
		return saveUpdate;
	}

	@Override
	public int insertTransactionIDForCandidate(PaymentOnlineBean paymentBean, Users loggedInUser, String merchantTxnSeqNumber, String merchantTxnRefNumber) throws Exception {
		int insertDetails = 0;
		try {
			insertDetails = writeJdbcTemplate.update(PaymentQueries.INSERT_TRANSACTION_NUMBER_IN_OPD, new Object[] { Integer.valueOf(merchantTxnSeqNumber), Integer.parseInt("1"),
					loggedInUser.getUserFk(), merchantTxnRefNumber, "INR", "N", loggedInUser.getUsername(), paymentBean.getAmount() });
		} catch (Exception e) {
			logger.error(e, e);
		}
		return insertDetails;
	}

	@Override
	public int deleteAndInsertTransactionIDForCandidate(PaymentOnlineBean paymentBean, Users loggedInUser, String merchantTxnSeqNumber, String merchantTxnRefNumber)
			throws Exception {
		int insertDetails = 0;
		try {
			// INSERT_INTO_PAYMENT_HISTORY before delete
			insertDetails = writeJdbcTemplate.update(PaymentQueries.INSERT_INTO_PAYMENT_HISTORY, new Object[] { loggedInUser.getUserFk() });

			// delete from OES_PAYMENT Details
			int deletePaymentFlag = writeJdbcTemplate.update(PaymentOfflineQueries.DELETE_PAYMENT_DETAILS_FOR_ENROLLMENT, new Object[] { loggedInUser.getUserFk() });

			// insert into OES_PAYMENT_Details
			insertDetails = writeJdbcTemplate.update(PaymentQueries.INSERT_TRANSACTION_NUMBER_IN_OPD, new Object[] { Integer.valueOf(merchantTxnSeqNumber), Integer.parseInt("1"),
					loggedInUser.getUserFk(), merchantTxnRefNumber, "INR", "N", loggedInUser.getUsername(), paymentBean.getAmount() });
		} catch (Exception e) {
			logger.error(e, e);
			throw new Exception();
		}
		return insertDetails;
	}

}
