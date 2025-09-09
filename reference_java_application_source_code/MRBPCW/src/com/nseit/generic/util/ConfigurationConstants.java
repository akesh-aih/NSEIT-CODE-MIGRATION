package com.nseit.generic.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;


import org.apache.logging.log4j.Logger;

import com.nseit.generic.models.AgeMatrix;
import com.nseit.generic.models.ConfigParam;
import com.nseit.generic.models.EmailSMSMasterBean;
import com.nseit.generic.models.MenuBean;
import com.nseit.otbs.model.TestMasterBean;

public class ConfigurationConstants
{
	// for Covid Duty Certificate page
	private Map<Integer, String> signedByDataMap = new TreeMap<Integer, String>();
	private static ConfigurationConstants configurationConstants=null;
	private Map<String, ConfigParam> configParamsMap=null;
	private static Logger logger = LoggerHome.getLogger(ConfigurationConstants.class);
	private Map<String, String> SSL_NonSSL_Map = new HashMap<String, String>();
	private Map<String, String> nonSSL_SLL_Map = new HashMap<String, String>();
	private Set<String> sslEntryActionNames = new HashSet<String>();
	private Set<String> sslExitActionNames = new HashSet<String>();
	private Map<Integer, String> activityMap = new TreeMap<Integer, String>();
	private SortedMap<String, Integer> activityMapReverseMapping = new TreeMap<String, Integer>();
	private Map<String, EmailSMSMasterBean> emailSMSMasterMap = new HashMap<String, EmailSMSMasterBean>();
	private Map<String, String> systemParameterNDBParameterMap = null;
	private Map<String, SortedMap<String, MenuBean>> userTypenMenuMap = new HashMap<String, SortedMap<String,MenuBean>>();
	private Map<Integer, String> countryMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, Map<Integer, String>> countryStateMap = new LinkedHashMap<Integer, Map<Integer,String>>();
	private Map<String, Map<String, String>> SubUnitMap = new LinkedHashMap<String, Map<String,String>>();
	private Map<Integer, Map<Integer, String>> policeStationMap = new LinkedHashMap<Integer, Map<Integer,String>>();
	private Map<Integer, Map<Integer, String>> academicMap = new LinkedHashMap<Integer, Map<Integer,String>>();
	private Map<String, Map<Integer, String>> districtMap = new LinkedHashMap<String, Map<Integer,String>>();
	private Map<Integer, Map<Integer, String>> countryUnionTerrMap = new LinkedHashMap<Integer, Map<Integer,String>>();
	private Map<Integer, Map<Integer, String>> stateDistrictMap = new LinkedHashMap<Integer, Map<Integer,String>>();
	private Map<Integer, Map<Integer, String>> districtCity1Map = new LinkedHashMap<Integer, Map<Integer,String>>();
	private Map<Integer, List<String>> districtCityMap = new LinkedHashMap<Integer, List<String>>();
	private Map<Integer, Map<Integer, String>> districtTalukaMap = new LinkedHashMap<Integer, Map<Integer,String>>();
	private Map<Integer, String> referenceKeyValueMap = new HashMap<Integer, String>();
	private Map<String, Map<Integer, String>> referenceDomainNameNKeyValueMap = new HashMap<String, Map<Integer,String>>();
	private Map<Integer, String> stageMap = new TreeMap<Integer, String>();
	private Map<Integer, String> stageActionUrlMap = new TreeMap<Integer, String>();
	private SortedMap<String, Integer> stageMapReverseMapping = new TreeMap<String, Integer>();
	private Map<Integer, String> helpTestCenterMasterMap = new LinkedHashMap<Integer, String>();
	private Map<String, String> downloadPathMap = new LinkedHashMap<String, String>();
	private Map<Integer,List< Long>> dateWindowMap = new HashMap<Integer,List< Long>>();
	private SortedMap<String, Integer> disciplineMapReverseMapping = new TreeMap<String, Integer>();
	private SortedMap<String, Integer> applyDisciplineMapReverseMapping = new TreeMap<String, Integer>();
	private Map<Integer, String> nativityMap = new LinkedHashMap<Integer, String>();
	private Map<Integer,TestMasterBean> testDetailsMap = new  HashMap<Integer, TestMasterBean>();
	private Map<String, String> getLabelMap = new LinkedHashMap<String, String>();
	private Map<Integer, String> disciplineMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> applyDisciplineMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> applyNotificationMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> applicationEndDateMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, AgeMatrix> ageMatrixMapping = new HashMap<>();
	public Map<Integer, String> getApplicationEndDateMap() {
		return applicationEndDateMap;
	}

	public void setApplicationEndDateMap(Map<Integer, String> applicationEndDateMap) {
		this.applicationEndDateMap = applicationEndDateMap;
	}
	private Map<String, String> yearList = new LinkedHashMap<String, String>();
	private Logger candidateActivityAuditTrailLogger = null;
	private Logger importLogger = null;
	private Map<Integer, String> stageStatusMap = new TreeMap<Integer, String>();
	private SortedMap<Integer, String> stageStatusMapMapping = new TreeMap<Integer,String>();

	private Map<Integer, String> testCenterMasterMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> preferredCenterMasterMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, Integer> menuStageMap = new LinkedHashMap<Integer, Integer>();
	
	private Map<Integer, String> programMasterMap = new LinkedHashMap<Integer, String>();
	
	private Map<Integer, String> statusMasterMap = new LinkedHashMap<Integer, String>();
	
	private Map<Integer, String> paymentMasterMap = new LinkedHashMap<Integer, String>();
	private Map<String, Integer> paymentMasterReverseMap = new LinkedHashMap<String, Integer>();
	
	private Map<Integer, String> statusMasterMapForReport = new LinkedHashMap<Integer, String>();
	
	private List<MenuMasterParentBean> parentMenuList = new LinkedList<MenuMasterParentBean>();
	private List<MenuMasterChildBean> childMenuList = new LinkedList<MenuMasterChildBean>();
	private Map<String, List<MenuMasterChildBean>> menuMasterList =new LinkedHashMap<String, List<MenuMasterChildBean>>();
	private Map<String,String> menuKeyLinkMap = new LinkedHashMap<String, String>();
	private Map<String,String> menuKeyMenuLinkMap = new LinkedHashMap<String, String>();
	private Map<String,String> menuKeyNextStageMap = new LinkedHashMap<String, String>();
	private Map<String,String> menuKeyMandatoryMap = new LinkedHashMap<String, String>();
	private Map<String,String> menuKeyStatusMap = new LinkedHashMap<String, String>();
	private Map<Integer, String> statusMap = new TreeMap<Integer, String>();
	private SortedMap<String, Integer> statusMapReverseMapping = new TreeMap<String, Integer>();
	private List<String> statusFkList = new LinkedList<String>();
	private Map<String, List<StageOffBean>> stageOffList =new LinkedHashMap<String, List<StageOffBean>>();
	private Map<String, List<StageOnBean>> stageOnList =new LinkedHashMap<String, List<StageOnBean>>();
	private Map<String, Integer> smsStatusMap = new LinkedHashMap<String, Integer>();
	private Map<String, Integer> emailStatusMap = new LinkedHashMap<String, Integer>();;
	private Map<Integer, String> questionListMap = new LinkedHashMap<Integer, String>();
	private Map<String, String> menuDescMenuKeyMap = new LinkedHashMap<String, String>();
	private Map<String, String> menuDescActiveStatusMap = new LinkedHashMap<String, String>();
	private Map<Integer, String> categoryListMap = new LinkedHashMap<Integer, String>();
	
	private Map<Integer, Map<Integer, String>> SubCategoryListMap = new LinkedHashMap<Integer, Map<Integer,String>>();
	private Map<Integer, Map<Integer, String>> DesignationListMap = new LinkedHashMap<Integer, Map<Integer,String>>();
	
	private Map<Integer, String> residenceLocalityListMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> departmentTypeMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> wardMap = new LinkedHashMap<Integer, String>();
	
	private Map<Integer, Map<Integer, Map<String, Integer>>> ageMatrixMap=new LinkedHashMap<Integer, Map<Integer,Map<String,Integer>>>();
	
	private String ENC_KEY = "";

	public String getENC_KEY() {
		return ENC_KEY;
	}

	public void setENC_KEY(String eNC_KEY) {
		ENC_KEY = eNC_KEY;
	}
	public Map<Integer, String> getResidenceLocalityListMap() {
		return residenceLocalityListMap;
	}

	public void setResidenceLocalityListMap(
			Map<Integer, String> residenceLocalityListMap) {
		this.residenceLocalityListMap = residenceLocalityListMap;
	}

	public Map<Integer, Map<Integer, String>> getDesignationListMap() {
		return DesignationListMap;
	}

	public void setDesignationListMap(
			Map<Integer, Map<Integer, String>> designationListMap) {
		DesignationListMap = designationListMap;
	}
	public Map<Integer, String> getDesignationListMap1(Integer countryID){
		return getDesignationListMap().get(countryID);
	}
	//private Map<Integer, String> SubCategoryListMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> DesignationcategoryListMap = new LinkedHashMap<Integer, String>();
	
	private Map<Integer, String> recrumentListMap = new LinkedHashMap<Integer, String>();
	private Map<String, String> pcStageListMap = new LinkedHashMap<String, String>();
	private Map<String, String> siStageListMap = new LinkedHashMap<String, String>();
	private Map<String, String> pcRecrumentcategoryListMap = new LinkedHashMap<String, String>();
	private Map<String, String> siRecrumentcategoryListMap = new LinkedHashMap<String, String>();
	private Map<String, Map<Integer,String>> refKeyValMasterMap = new LinkedHashMap<String, Map<Integer,String>>();
	private Map<String, Map<Integer,String>> recrumentCityMap = new LinkedHashMap<String, Map<Integer,String>>();
	private Map<String, String> presentRankMap = new LinkedHashMap<String, String>();
	public Map<Integer, String> getDesignationcategoryListMap() {
		return DesignationcategoryListMap;
	}
	
	public Map<String, Map<Integer, String>> getDistrictMap() {
		return districtMap;
	}

	public void setDistrictMap(Map<String, Map<Integer, String>> districtMap) {
		this.districtMap = districtMap;
	}
	public Map<Integer, String> getDistrictMap1(String countryID){
		return getDistrictMap().get(countryID);
	}
	public Map<String, String> getPresentRankMap() {
		return presentRankMap;
	}

	public void setPresentRankMap(Map<String, String> presentRankMap) {
		this.presentRankMap = presentRankMap;
	}
	public Map<String, String> getSubUnitMap1(String countryID){
		return getSubUnitMap().get(countryID);
	}
	public void setDesignationcategoryListMap(
			Map<Integer, String> designationcategoryListMap) {
		DesignationcategoryListMap = designationcategoryListMap;
	}

	
	public Map<Integer, Map<Integer, String>> getPoliceStationMap() {
		return policeStationMap;
	}

	public void setPoliceStationMap(
			Map<Integer, Map<Integer, String>> policeStationMap) {
		this.policeStationMap = policeStationMap;
	}
	
	public Map<Integer, String> getPoliceStationMap1(Integer countryID){
		return getPoliceStationMap().get(countryID);
	}

	
	public Map<Integer, Map<Integer, String>> getSubCategoryListMap() {
		return SubCategoryListMap;
	}

	public void setSubCategoryListMap(
			Map<Integer, Map<Integer, String>> subCategoryListMap) {
		SubCategoryListMap = subCategoryListMap;
	}
	public Map<Integer, String> getSubCategoryListMap1(Integer countryID){
		return getSubCategoryListMap().get(countryID);
	}
	public Map<String, Map<String, String>> getSubUnitMap() {
		return SubUnitMap;
	}

	public void setSubUnitMap(Map<String, Map<String, String>> subUnitMap) {
		SubUnitMap = subUnitMap;
	}

	private Map<Integer, String> postListMap = new LinkedHashMap<Integer, String>();
	private Map<String, String> staticDataMap = new LinkedHashMap<String, String>();
	private Map<String, Integer> dateWindowMapData = new LinkedHashMap<String, Integer>();
	private Map<String, String> statusDescStatusMap = new LinkedHashMap<String, String>();
	private Map<String, String> templateMasterMap = new LinkedHashMap<String, String>();
	private Map<String, String> academicMasterMap = new LinkedHashMap<String, String>();
	private Map<String, String> academicMandatoryMap = new LinkedHashMap<String, String>();
	private Map<String, String> academicMasterReverseMap = new LinkedHashMap<String, String>();
	private Map<Integer, String> postGraduateMasterMap = new LinkedHashMap<Integer, String>();
	private Map<String, String> refernceValueMandatoryMap= new LinkedHashMap<String, String>();
	private Map<String, String> refernceValueActiveMap= new LinkedHashMap<String, String>();
	private Map degreeDetailsMap = new LinkedHashMap();
	private Map<String, String> paymentMasterMapForStatus = new LinkedHashMap<String, String>();
	private Map<Integer, String> boardMap=new LinkedHashMap<Integer, String>();
	
	private Map<Integer, String> degreeMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, Map<Integer, String>> universityMap = new LinkedHashMap<Integer, Map<Integer, String>>();
	private Map<Integer, String> subDegreeMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> yearMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> interviewLocMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> fieldOfIntrestMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> areaOfProjectMap = new LinkedHashMap<Integer, String>();
	private List<String> cityList = new ArrayList<String>();
	private List<String> districtCityList = new ArrayList<String>();
	
	private Map<String, Integer> paymentMasterMapForDesc = new LinkedHashMap<String, Integer>();
	private Map<Integer, String> examPatternMap = new LinkedHashMap<Integer, String>();
	private String patternVal;
	private Map<String, Integer> cityMasterMap = new LinkedHashMap<String, Integer>();
	private Map<Integer, String> refMap = new LinkedHashMap<Integer, String>();
	private Map<String, String> loginTemplateMap = new HashMap<String, String>();
	private Map<String, String> loginVersionMap = new HashMap<String, String>();
	private Map<Integer, String> sportsMasterMap = new HashMap<Integer, String>();
	
	public Map<String, String> getEligibiltyCriteriaMap() {
		return eligibiltyCriteriaMap;
	}

	public void setEligibiltyCriteriaMap(Map<String, String> eligibiltyCriteriaMap) {
		this.eligibiltyCriteriaMap = eligibiltyCriteriaMap;
	}

	private Map<String, String> eligibiltyCriteriaMap = new HashMap<String, String>();
	
	private Map<String, String> declarationMap = new HashMap<String, String>();
	private Map<String, String> declarationMap1 = new HashMap<String, String>();
	//shekharc apply page confirmation msg start
	private Map<String, String> applyPostConfirmationMap = new HashMap<String, String>();
	//shekharc apply page confirmation msg end
	private Map<String, String> paymentMasterReverseMapForDesc = new LinkedHashMap<String, String>();
	private Map<String, Integer> moduleMasterMap = new LinkedHashMap<String, Integer>();
	
	public Map<String, Integer> getModuleMasterMap() {
		return moduleMasterMap;
	}

	public Map<String, String> getGetLabelMap() {
		return getLabelMap;
	}

	public void setGetLabelMap(Map<String, String> getLabelMap) {
		this.getLabelMap = getLabelMap;
	}

	public void setModuleMasterMap(Map<String, Integer> moduleMasterMap) {
		this.moduleMasterMap = moduleMasterMap;
	}

	public Integer getModuleId(String moduleCode){
		return getModuleMasterMap().get(moduleCode);
	}
	
	public Map<String, String> getDeclarationMap() {
		return declarationMap;
	}

	public void setDeclarationMap(Map<String, String> declarationMap) {
		this.declarationMap = declarationMap;
	}
	//shekharc apply page confirmation msg start
	public Map<String, String> getapplyPostConfirmationMap() {
		return applyPostConfirmationMap;
	}

	public void setapplyPostConfirmationMap(Map<String, String> applyPostConfirmationMap) {
		this.applyPostConfirmationMap = applyPostConfirmationMap;
	}
	//shekharc apply page confirmation msg end
	public Map<String, String> getLoginVersionMap() {
		return loginVersionMap;
	}

	public void setLoginVersionMap(Map<String, String> loginVersionMap) {
		this.loginVersionMap = loginVersionMap;
	}

	public Map<String, String> getLoginTemplateMap() {
		return loginTemplateMap;
	}

	public void setLoginTemplateMap(Map<String, String> loginTemplateMap) {
		this.loginTemplateMap = loginTemplateMap;
	}

	public String getPatternVal(Integer patternVal){
		return examPatternMap.get(patternVal);
	}

	public Map<Integer, String> getExamPatternMap() {
		return examPatternMap;
	}

	public void setExamPatternMap(Map<Integer, String> examPatternMap) {
		this.examPatternMap = examPatternMap;
	}

	public ConfigurationConstants(){
		populateSystemParameternDBParameterMap();
	}
	
	static{
		configurationConstants = new ConfigurationConstants();
	}
	
	private void populateSystemParameternDBParameterMap(){
		systemParameterNDBParameterMap = new HashMap<String, String>();
		
		systemParameterNDBParameterMap.put("{USER_ID}","user_id");
		systemParameterNDBParameterMap.put("{USER_PASSWORD}","user_password");
		systemParameterNDBParameterMap.put("{USER_POST}","discipline");
		systemParameterNDBParameterMap.put("{DATE_OF_REG}","date_of_reg");
		systemParameterNDBParameterMap.put("{LOGIN_URL}","login_url");
		systemParameterNDBParameterMap.put("{REGISTRATION_REJECTION_DATE}","registration_rejection_date");
		systemParameterNDBParameterMap.put("{REMARKS}","rejection_remark");
		systemParameterNDBParameterMap.put("{BOOKED_DATE}","booked_date");
		systemParameterNDBParameterMap.put("{REGISTRATION_APPROVAL_DATE}","registration_approval_date");
		systemParameterNDBParameterMap.put("{REGISTRATION_SUBMISION_DATE}","registration_submision_date");
		systemParameterNDBParameterMap.put("{EXAM_TIME}","start_time");
		systemParameterNDBParameterMap.put("{EXAM_DATE}","testDate");
		systemParameterNDBParameterMap.put("{TEST_CENTER_NAME}","centerName");
		systemParameterNDBParameterMap.put("{TEST_CENTER_ADDRESS}","centerAddress");
		
		systemParameterNDBParameterMap.put("{AMOUNT}","amount");
		systemParameterNDBParameterMap.put("{PAYMENT_SUBMITTED_DATE}","payment_submitted_date");
		systemParameterNDBParameterMap.put("{PAYMENT_MODE}","payment_mode");
		systemParameterNDBParameterMap.put("{PAYMENT_REJECTION_REMARKS}","payment_rejection_remarks");
//		systemParameterNDBParameterMap.put("{TEST_CENTER_NAME}","test_center_name");
		systemParameterNDBParameterMap.put("{TEST_CENTER_NAME}","centerName");
		systemParameterNDBParameterMap.put("{ATTEMPT}","attempt");
		systemParameterNDBParameterMap.put(" {NAME}", "candidateName");
		systemParameterNDBParameterMap.put(" {DATE_OF_BIRTH}", "dateOfBirth");
		systemParameterNDBParameterMap.put(" {MOBILE_NO}", "mobile_no");
		systemParameterNDBParameterMap.put(" {ALTERNATE_MOBILE_NO}", "alternateMobileNo");
		systemParameterNDBParameterMap.put("{OTP_EMAIL}", "otp_email");
		systemParameterNDBParameterMap.put("{OTP_MOBILE}", "otp_mobile");
		systemParameterNDBParameterMap.put("{REGI_ID}", "regi_id");
		/*systemParameterNDBParameterMap.put("{Bank Name}","BANK_NAME");
		systemParameterNDBParameterMap.put("{Transaction Amount}","TRANSACTION_AMOUNT");
		systemParameterNDBParameterMap.put("{Transaction Date-Time}","TRANSACTION_DATE_TIME");
		systemParameterNDBParameterMap.put("{Transaction Reference Number}","TRANSACTION_NO");
		systemParameterNDBParameterMap.put("{Payment Mode}","PAYMENT_MODE");
		systemParameterNDBParameterMap.put("{Retest Centre}","RETEST_CENTRE");
		systemParameterNDBParameterMap.put("{Retest Request Status}","RETEST_REQUEST_STATUS");
		systemParameterNDBParameterMap.put("{Retest Date}","RETEST_DATE");
		systemParameterNDBParameterMap.put("{Retest Time}","RETEST_TIME");
		systemParameterNDBParameterMap.put("{Interview Location}","INTERVIEW_LOCATION");
		systemParameterNDBParameterMap.put("{Interview Time Slot}","INTERVIEW_TIME_SLOT");
		systemParameterNDBParameterMap.put("{Interview Date}","INTERVIEW_DATE");
		*/
		systemParameterNDBParameterMap = Collections.unmodifiableMap(systemParameterNDBParameterMap);
	}
	
	public void updateConfigParams(Map<String, ConfigParam> configParamsMap) throws Exception{
		this.configParamsMap = configParamsMap;
		
		if(configParamsMap.get(GenericConstants.SSL_ENABLED) != null
				&& configParamsMap.get(GenericConstants.SSL_ENABLED).getKeyValue() != null
				&& configParamsMap.get(GenericConstants.SSL_ENABLED).getKeyValue().compareTo("1")==0){
			if(configParamsMap.get(GenericConstants.NON_SSL_PORT) != null){
				String[] nonSSLPortArr = configParamsMap.get(GenericConstants.NON_SSL_PORT).getKeyValue().split("\\|");
				String[] SSLPortArr = configParamsMap.get(GenericConstants.SSL_PORT).getKeyValue().split("\\|");
				
				if(nonSSLPortArr.length == SSLPortArr.length){
					for(int iCurrIndex=0; iCurrIndex < nonSSLPortArr.length; iCurrIndex++){
						SSL_NonSSL_Map.put(SSLPortArr[iCurrIndex], nonSSLPortArr[iCurrIndex]);
						nonSSL_SLL_Map.put(nonSSLPortArr[iCurrIndex], SSLPortArr[iCurrIndex]);
					}
				}
				else{
					throw new GenericException("NON SSL ports and SSL ports do not match, but SSL is enabled");
				}
			}
			else{
				throw new GenericException("NON SSL ports or SSL ports no defined, but SSL is enabled");
			}
			
			if(CommonUtil.getParameter(configParamsMap.get(GenericConstants.SSL_ENTRY_POINTS).getKeyValue()).compareTo("")==0){
				String[] sslEntryPointsArr = CommonUtil.getParameter(configParamsMap.get(GenericConstants.SSL_ENTRY_POINTS).getKeyValue()).split("\\,", -1);
				String[] sslExitPointsArr = CommonUtil.getParameter(configParamsMap.get(GenericConstants.SSL_EXIT_POINTS).getKeyValue()).split("\\,", -1);
				
				for(String sslEntryPoint : sslEntryPointsArr){
					sslEntryActionNames.add(sslEntryPoint);
				}
				
				for(String sslExitPoint : sslExitPointsArr){
					sslExitActionNames.add(sslExitPoint);
				}
			}
		}
	}
	
	public static ConfigurationConstants getInstance(){
		return configurationConstants;
	}
	
	public String getPropertyVal(String keyName){
		String value = null;
		
		if(configParamsMap.get(keyName) == null){
			//logger.info("ALERT -> No Configuration constant defined for keyname : " + keyName);
		}
		else{
			value = configParamsMap.get(keyName).getKeyValue();
		}
		
		return value;
	}

	public Map<String, String> getSSL_NonSSL_Map(){
		return SSL_NonSSL_Map;
	}

	public Map<String, String> getNonSSL_SLL_Map(){
		return nonSSL_SLL_Map;
	}

	public Set<String> getSslEntryActionNames(){
		return sslEntryActionNames;
	}

	public Set<String> getSslExitActionNames(){
		return sslExitActionNames;
	}

	public void setActivityMap(Map<Integer, String> activityMap){
		this.activityMap = activityMap;
		
		for(Entry<Integer, String> entry : activityMap.entrySet()){
			getActivityMapReverseMapping().put(entry.getValue(), entry.getKey());
		}
	}

	public Map<Integer, String> getActivityMap(){
		return activityMap;
	}
	
	public String getActivityNameForKey(int activityKey){
		return activityMap.get(activityKey);
	}
	
	public Map<Integer, String> getCountryMap() {
		return countryMap;
	}

	public void setCountryMap(Map<Integer, String> countryMap) {
		this.countryMap = countryMap;
	}

	public Map<Integer, Map<Integer, String>> getCountryStateMap() {
		return countryStateMap;
	}

	public void setCountryStateMap(Map<Integer, Map<Integer, String>> countryStateMap) {
		this.countryStateMap = countryStateMap;
	}

	public Map<Integer, Map<Integer, String>> getStateDistrictMap() {
		return stateDistrictMap;
	}
	public void setStateDistrictMap(Map<Integer, Map<Integer, String>> stateDistrictMap) {
		this.stateDistrictMap = stateDistrictMap;
	}
	
	/** [Start]
	 * @author Pankaj Sh
	 */
	public Map<Integer, String> getStateMap(Integer countryID){
		return getCountryStateMap().get(1);
	}
	
	// for getting download paths 
	public String getDownloadPathForKey(String pathKey){
		return downloadPathMap.get(pathKey);
	}
	
	public Map<Integer, String> getUTMap(Integer countryID){
		return countryUnionTerrMap.get(countryID);
	}
	
	public Map<Integer, String> getDistrictMap(Integer stateID){
		return getStateDistrictMap().get(stateID);
	}
	
	public List<String> getDistrictCityList(Integer districtID) {
		return getDistrictCityMap().get(districtID);
	}
	
	public Map<Integer, String> getTalukaMap(Integer talukaID){
		return getDistrictTalukaMap().get(talukaID);
	}
	/** [End]
	 * @author Pankaj Sh
	 */
	
	public int getActivtyKeyForName(String activityName){
		return getActivityMapReverseMapping().get(activityName);
	}
	
	public void updateEmailSMSMasterMap(Map<String, EmailSMSMasterBean> emailSMSMasterMap){
		synchronized (this.emailSMSMasterMap){
			this.emailSMSMasterMap.clear();
			this.emailSMSMasterMap = emailSMSMasterMap;
		}
	}
	
	public EmailSMSMasterBean getEmailMasterBeanForActivitynTestId(int activityId, int testId){
		EmailSMSMasterBean emailSMSMasterBean = emailSMSMasterMap.get(testId + "_" + activityId);
		if(emailSMSMasterBean != null){
			if(!emailSMSMasterBean.isMailApplicable())
				emailSMSMasterBean = null;
		}
		
		return emailSMSMasterBean;
	}
	
	public EmailSMSMasterBean getSMSMasterBeanForActivitynTestId(int activityId, int testId){
		EmailSMSMasterBean emailSMSMasterBean = emailSMSMasterMap.get(testId + "_" + activityId);
		if(emailSMSMasterBean != null){
			if(!emailSMSMasterBean.isSmsApplicable())
				emailSMSMasterBean = null;
		}
		
		return emailSMSMasterBean;
	}
	
	public Map<String, String> getSystemParameterNDBParameterMap(){
		return systemParameterNDBParameterMap;
	}

	public void setUserTypenMenuMap(Map<String, SortedMap<String, MenuBean>> userTypenMenuMap){
		this.userTypenMenuMap = userTypenMenuMap;
	}

	public Map<String, SortedMap<String, MenuBean>> getUserTypenMenuMap(){
		return userTypenMenuMap;
	}

	public void setActivityMapReverseMapping(SortedMap<String, Integer> activityMapReverseMapping) {
		this.activityMapReverseMapping = activityMapReverseMapping;
	}

	public SortedMap<String, Integer> getActivityMapReverseMapping() {
		return activityMapReverseMapping;
	}
	
	public void setReferenceValueMaster(Map<String, Map<Integer, String>> referenceDomainNameNKeyValueMap){
		this.referenceDomainNameNKeyValueMap = referenceDomainNameNKeyValueMap;
		
		for(Entry<String, Map<Integer, String>> entry : referenceDomainNameNKeyValueMap.entrySet()){
			referenceKeyValueMap.putAll(entry.getValue());
		}
	}
	
	public String getParameterValueForKey(int parameterKey){
		return referenceKeyValueMap.get(parameterKey);
		//return getCategoryListMap().get(parameterKey);
	}
	
	public String getCategoryValueForKey(int parameterKey){
		//return referenceKeyValueMap.get(parameterKey);
		return getCategoryListMap().get(parameterKey);
	}
	
	public Map<Integer, String> getParameterMapForDomainName(String domainName){
		Map<Integer, String> returnMap = referenceDomainNameNKeyValueMap.get(domainName);;
		
		if(returnMap == null)
			returnMap = new HashMap<Integer, String>();
		
		return returnMap;
	}

	public Map<Integer, String> getStageMap(){
		return stageMap;
	}

	public void setStageMapReverseMapping(SortedMap<String, Integer> stageMapReverseMapping){
		this.stageMapReverseMapping = stageMapReverseMapping;
	}

	public SortedMap<String, Integer> getStageMapReverseMapping(){
		return stageMapReverseMapping;
	}
	
	public void setStageMap(Map<Integer, String> stageMap){
		this.stageMap = stageMap;
		
		for(Entry<Integer, String> entry : stageMap.entrySet()){
			getStageMapReverseMapping().put(entry.getValue(), entry.getKey());
		}
	}
	
	public Map<Integer, String> getStageActionUrlMap() {
		return stageActionUrlMap;
	}

	public void setStageActionUrlMap(Map<Integer, String> stageActionUrlMap) {
		this.stageActionUrlMap = stageActionUrlMap;
	}

	public int getStageKey(String stageName){
		return stageMapReverseMapping.get(stageName);
	}
	
	public String getActionUrl(String menuKey){
		return getMenuKeyMenuLinkMap().get(menuKey);
	}

	public void setHelpTestCenterMasterMap(Map<Integer, String> helpTestCenterMasterMap) {
		this.helpTestCenterMasterMap = Collections.unmodifiableMap(helpTestCenterMasterMap);
	}

	public Map<Integer, String> getHelpTestCenterMasterMap() {
		return helpTestCenterMasterMap;
	}

	
	public Map<Integer, String> getProgramMasterMap() {
		return programMasterMap;
	}

	public void setProgramMasterMap(Map<Integer, String> programMasterMap) {
		this.programMasterMap = programMasterMap;
		
	}

	public void setCountryUnionTerrMap(Map<Integer, Map<Integer, String>> countryUnionTerrMap) {
		this.countryUnionTerrMap = Collections.unmodifiableMap(countryUnionTerrMap);
	}

	public Map<Integer, Map<Integer, String>> getCountryUnionTerrMap() {
		return countryUnionTerrMap;
	}

	public Map<String, String> getDownloadPathMap() {
		return downloadPathMap;
	}

	public void setDownloadPathMap(Map<String, String> downloadPathMap) {
		this.downloadPathMap = downloadPathMap;
	}

	public void setDateWindowMap(Map<Integer,List< Long>> dateWindowMap) {
		this.dateWindowMap = dateWindowMap;
	}

	public Map<Integer,List< Long>> getDateWindowMap() {
		return dateWindowMap;
	}
	public List<Long> getDateListFromDateWindow(int dateWindowPK){
		return dateWindowMap.get(dateWindowPK);
	}

	public void setTestDetailsMap(Map<Integer,TestMasterBean> testDetailsMap) {
		this.testDetailsMap = testDetailsMap;
	}

	public Map<Integer,TestMasterBean> getTestDetailsMap() {
		return testDetailsMap;
	}

	public TestMasterBean getTestDetailsFromMapForTestPk(int testPk){
		return testDetailsMap.get(testPk);
	}

	public Map<Integer, String> getDisciplineMap() {
		return disciplineMap;
	}

	public void setDisciplineMap(Map<Integer, String> disciplineMap) {
		this.disciplineMap = disciplineMap;
		
		for(Entry<Integer, String> entry : disciplineMap.entrySet()){
			getDisciplineMapReverseMapping().put(entry.getValue(), entry.getKey());
		}
		
	}
	
	public int getDisciplineKeyForName(String disciplineName){
		if(getDisciplineMapReverseMapping().get(disciplineName) != null){
			return getDisciplineMapReverseMapping().get(disciplineName);
		}
		return 0;
	}

	
	
	
	public SortedMap<String, Integer> getDisciplineMapReverseMapping() {
		return disciplineMapReverseMapping;
	}

	public void setDisciplineMapReverseMapping(SortedMap<String, Integer> disciplineMapReverseMapping) {
		this.disciplineMapReverseMapping = disciplineMapReverseMapping;
	}

	public void setCandidateActivityAuditTrailLogger(Logger candidateActivityAuditTrailLogger){
		this.candidateActivityAuditTrailLogger = candidateActivityAuditTrailLogger;
	}

	public Logger getCandidateActivityAuditTrailLogger(){
		return candidateActivityAuditTrailLogger;
	}

	public Logger getImportLogger() {
		return importLogger;
	}

	public void setImportLogger(Logger importLogger) {
		this.importLogger = importLogger;
	}

	public Map<Integer, Map<Integer, String>> getDistrictTalukaMap() {
		return districtTalukaMap;
	}

	public void setDistrictTalukaMap(Map<Integer, Map<Integer, String>> districtTalukaMap) {
		this.districtTalukaMap = districtTalukaMap;
	}

	public Map<Integer, String> getStageStatusMap() {
		return stageStatusMap;
	}

	public void setStageStatusMap(Map<Integer, String> stageStatusMap) {
		this.stageStatusMap = stageStatusMap;
		for(Entry<Integer, String> entry : stageStatusMap.entrySet()){
			getStageStatusMapMapping().put(entry.getKey(), entry.getValue());
		}
	}
	
	public Map<Integer, String> getStageStatusMap(Integer stateID){
		return stageStatusMap;
	}
	
	public SortedMap<Integer, String> getStageStatusMapMapping() {
		return stageStatusMapMapping;
	}

	public void setStageStatusMapMapping(SortedMap<Integer, String> stageStatusMapMapping) {
		this.stageStatusMapMapping = stageStatusMapMapping;
	}

	public String getStageStatus(int stageId){
		return stageStatusMapMapping.get(stageId);
	}
	
	public Map<Integer, String> getTestCenterMasterMap() {
		return testCenterMasterMap;
	}

	public void setTestCenterMasterMap(Map<Integer, String> testCenterMasterMap) {
		this.testCenterMasterMap = testCenterMasterMap;
	}

	public String getTestCenterName(Integer testCenterId){
		Map<Integer,String> cntrNameMap = getTestCenterMasterMap();
		return cntrNameMap.get(testCenterId);
	}
	
	public String getPreferredCenterName(Integer testCenterId){
		Map<Integer,String> cntrNameMap = getPreferredCenterMasterMap();
		return cntrNameMap.get(testCenterId);
	}

	public Map<Integer, Integer> getMenuStageMap() {
		return menuStageMap;
	}

	public void setMenuStageMap(Map<Integer, Integer> menuStageMap) {
		this.menuStageMap = menuStageMap;
	}
	public Integer getNextStage(Integer currentStage){
		Map<Integer,Integer> stageMap = getMenuStageMap();
		return stageMap.get(currentStage);
	}

	public List<MenuMasterParentBean> getParentMenuList() {
		return parentMenuList;
	}

	public void setParentMenuList(List<MenuMasterParentBean> parentMenuList) {
		this.parentMenuList = parentMenuList;
	}

	public Map<String, List<MenuMasterChildBean>> getMenuMasterList() {
		return menuMasterList;
	}

	public void setMenuMasterList(Map<String, List<MenuMasterChildBean>> menuMasterList) {
		this.menuMasterList = menuMasterList;
	}

	public List<MenuMasterChildBean> getChildMenuList() {
		return childMenuList;
	}

	public void setChildMenuList(List<MenuMasterChildBean> childMenuList) {
		this.childMenuList = childMenuList;
	}
	
	public List<MenuMasterChildBean> getChildListForParent(String menuKey)
	{
		return menuMasterList.get(menuKey);
	}

	public Map<String, String> getMenuKeyLinkMap() {
		return menuKeyLinkMap;
	}

	public void setMenuKeyLinkMap(Map<String, String> menuKeyLinkMap) {
		this.menuKeyLinkMap = menuKeyLinkMap;
	}

	public String getMenuKeyForUrl(String actionUrl){
		return menuKeyLinkMap.get(actionUrl);
	}

	public Map<String, String> getMenuKeyMenuLinkMap() {
		return menuKeyMenuLinkMap;
	}

	public void setMenuKeyMenuLinkMap(Map<String, String> menuKeyMenuLinkMap) {
		this.menuKeyMenuLinkMap = menuKeyMenuLinkMap;
	}

	public Map<String, String> getMenuKeyNextStageMap() {
		return menuKeyNextStageMap;
	}

	public void setMenuKeyNextStageMap(Map<String, String> menuKeyNextStageMap) {
		this.menuKeyNextStageMap = menuKeyNextStageMap;
	}
	
	public String getNextStageForMenuKey(String currentStage){
		return getMenuKeyNextStageMap().get(currentStage);
	}

	public Map<String, String> getMenuKeyMandatoryMap() {
		return menuKeyMandatoryMap;
	}

	public void setMenuKeyMandatoryMap(Map<String, String> menuKeyMandatoryMap) {
		this.menuKeyMandatoryMap = menuKeyMandatoryMap;
	}
	
	public String getMenuKeyMandatory(String menuKey){
		return getMenuKeyMandatoryMap().get(menuKey);
	}

	public Map<Integer, String> getStatusMap() {
		return statusMap;
	}

	public void setStatusMap(Map<Integer, String> statusMap) {
		this.statusMap = statusMap;
		
		for(Entry<Integer, String> entry : statusMap.entrySet()){
			getStatusMapReverseMapping().put(entry.getValue(), entry.getKey());
		}
	}

	public SortedMap<String, Integer> getStatusMapReverseMapping() {
		return statusMapReverseMapping;
	}

	public void setStatusMapReverseMapping(SortedMap<String, Integer> statusMapReverseMapping) {
		this.statusMapReverseMapping = statusMapReverseMapping;
	}
	
	public int getStatusKey(String statusName){
		return statusMapReverseMapping.get(statusName);
	}

	public Map<String, String> getMenuKeyStatusMap() {
		return menuKeyStatusMap;
	}

	public void setMenuKeyStatusMap(Map<String, String> menuKeyStatusMap) {
		this.menuKeyStatusMap = menuKeyStatusMap;
	}
	public String getStatus(String menuKey)
	{
		return getMenuKeyStatusMap().get(menuKey);
	}

	public List<String> getStatusFkList() {
		return statusFkList;
	}

	public void setStatusFkList(List<String> statusFkList) {
		this.statusFkList = statusFkList;
	}

	public Map<String, List<StageOffBean>> getStageOffList() {
		return stageOffList;
	}

	public void setStageOffList(Map<String, List<StageOffBean>> stageOffList) {
		this.stageOffList = stageOffList;
	}
	
	public List<StageOffBean> getStageOffList(String statusFk){
		return getStageOffList().get(statusFk);
	}
	
	public Map<String, List<StageOnBean>> getStageOnList() {
		return stageOnList;
	}

	public void setStageOnList(Map<String, List<StageOnBean>> stageOnList) {
		this.stageOnList = stageOnList;
	}

	public List<StageOnBean> getStageOnList(String statusFk){
		return getStageOnList().get(statusFk);
	}
	
	public String getDisciplineValueForKey(Integer disciplineId){
		return getDisciplineMap().get(disciplineId);
	}

	public Map<String, Integer> getSmsStatusMap() {
		return smsStatusMap;
	}

	public void setSmsStatusMap(Map<String, Integer> smsStatusMap) {
		this.smsStatusMap = smsStatusMap;
	}

	public Map<String, Integer> getEmailStatusMap() {
		return emailStatusMap;
	}

	public void setEmailStatusMap(Map<String, Integer> emailStatusMap) {
		this.emailStatusMap = emailStatusMap;
	}
	
	public Integer getEmailValForStatusDesc(String statusDesc){
		return emailStatusMap.get(statusDesc);
	}
	
	public Integer getSmsValForStatusDesc(String statusDesc){
		return smsStatusMap.get(statusDesc);
	}

	public Map<Integer, String> getQuestionListMap() {
		return questionListMap;
	}

	public void setQuestionListMap(Map<Integer, String> questionListMap) {
		this.questionListMap = questionListMap;
	}

	public Map<String, String> getMenuDescMenuKeyMap() {
		return menuDescMenuKeyMap;
	}

	public void setMenuDescMenuKeyMap(Map<String, String> menuDescMenuKeyMap) {
		this.menuDescMenuKeyMap = menuDescMenuKeyMap;
	}
	
	public String getMenuKeyByMenuDesc(String menuKey){
		return getMenuDescMenuKeyMap().get(menuKey);
	}

	public Map<String, String> getMenuDescActiveStatusMap() {
		return menuDescActiveStatusMap;
	}

	public void setMenuDescActiveStatusMap(Map<String, String> menuDescActiveStatusMap) {
		this.menuDescActiveStatusMap = menuDescActiveStatusMap;
	}
	
	public String getActiveStatusByMenuDesc(String menuDesc){
		String menuStatus = null; 
		try {
			menuStatus = getMenuDescActiveStatusMap().get(menuDesc);
		} catch (Exception e) {
			menuStatus = null;
		}
		return menuStatus;
	}

	public Map<Integer, String> getCategoryListMap() {
		return categoryListMap;
	}

	public void setCategoryListMap(Map<Integer, String> categoryListMap) {
		this.categoryListMap = categoryListMap;
	}

	public Map<Integer, String> getPostListMap() {
		return postListMap;
	}

	public void setPostListMap(Map<Integer, String> postListMap) {
		this.postListMap = postListMap;
	}

	public Map<Integer, String> getStatusMasterMap() {
		return statusMasterMap;
	}

	public void setStatusMasterMap(Map<Integer, String> statusMasterMap) {
		this.statusMasterMap = statusMasterMap;
	}

	public Map<String, String> getStaticDataMap() {
		return staticDataMap;
	}

	public void setStaticDataMap(Map<String, String> staticDataMap) {
		this.staticDataMap = staticDataMap;
	}
	
	public Map<String, Integer> getDateWindowMapData() {
		return dateWindowMapData;
	}

	public void setDateWindowMapData(Map<String, Integer> dateWindowMapData) {
		this.dateWindowMapData = dateWindowMapData;
	}
	
	public Integer getDateWindowPkForDesc(String dateWindowDesc){
		return dateWindowMapData.get(dateWindowDesc);
	}

	public Map<Integer, String> getStatusMasterMapForReport() {
		return statusMasterMapForReport;
	}

	public void setStatusMasterMapForReport(Map<Integer, String> statusMasterMapForReport) {
		this.statusMasterMapForReport = statusMasterMapForReport;
	}

	public Map<String, String> getStatusDescStatusMap() {
		return statusDescStatusMap;
	}

	public void setStatusDescStatusMap(Map<String, String> statusDescStatusMap) {
		this.statusDescStatusMap = statusDescStatusMap;
	}
	
	public String checkStatusOfStatusMasterTableData(String desc){
		return getStatusDescStatusMap().get(desc);
	}

	public Map<String, String> getTemplateMasterMap() {
		return templateMasterMap;
	}

	public void setTemplateMasterMap(Map<String, String> templateMasterMap) {
		this.templateMasterMap = templateMasterMap;
	}
	
	public String getTemplateStatusForDesc(String templateDesc){
		return templateMasterMap.get(templateDesc);
	}

	public Map<String, String> getAcademicMasterMap() {
		return academicMasterMap;
	}

	public void setAcademicMasterMap(Map<String, String> academicMasterMap) {
		this.academicMasterMap = academicMasterMap;
	}
	
	public String getAcademicDetailsForPk(String academicPk){
		return academicMasterMap.get(academicPk);
	}

	public Map getDegreeDetailsMap() {
		return degreeDetailsMap;
	}

	public void setDegreeDetailsMap(Map degreeDetailsMap) {
		this.degreeDetailsMap = degreeDetailsMap;
	}

	public Map<String, String> getAcademicMasterReverseMap() {
		return academicMasterReverseMap;
	}

	public void setAcademicMasterReverseMap(
			Map<String, String> academicMasterReverseMap) {
		this.academicMasterReverseMap = academicMasterReverseMap;
	}
	
	
	public String getAcademicDetailsPkForDesc(String academicDesc){
		return academicMasterReverseMap.get(academicDesc);
	}

    public Map<Integer, String> getPaymentMasterMap()
    {
        return paymentMasterMap;
    }

    public void setPaymentMasterMap(Map<Integer, String> paymentMasterMap)
    {
        this.paymentMasterMap = paymentMasterMap;
    }

	public Map<Integer, String> getPostGraduateMasterMap() {
		return postGraduateMasterMap;
	}

	public void setPostGraduateMasterMap(Map<Integer, String> postGraduateMasterMap) {
		this.postGraduateMasterMap = postGraduateMasterMap;
	}
	public String getPostGraduationDescForKey(int postGraduateKey){
		return postGraduateMasterMap.get(postGraduateKey);
	}

	public Map<String, Integer> getPaymentMasterReverseMap() {
		return paymentMasterReverseMap;
	}

	public void setPaymentMasterReverseMap(
			Map<String, Integer> paymentMasterReverseMap) {
		this.paymentMasterReverseMap = paymentMasterReverseMap;
	}
	
	public Integer getPaymentKeyByDesc(String paymentDesc){
		return paymentMasterReverseMap.get(paymentDesc);
	}

	public Map<String, String> getRefernceValueMandatoryMap() {
		return refernceValueMandatoryMap;
	}

	public void setRefernceValueMandatoryMap(Map<String, String> refernceValueMandatoryMap) {
		this.refernceValueMandatoryMap = refernceValueMandatoryMap;
	}

	public Map<String, String> getRefernceValueActiveMap() {
		return refernceValueActiveMap;
	}

	public void setRefernceValueActiveMap(Map<String, String> refernceValueActiveMap) {
		this.refernceValueActiveMap = refernceValueActiveMap;
	}

	public String getMandatoryStatusForValue(String refernceValue){
		return refernceValueMandatoryMap.get(refernceValue);
	}
	
	public String getActiveStatusForValue(String refernceValue){
		return refernceValueActiveMap.get(refernceValue);
	}

	public Map<String, String> getPaymentMasterMapForStatus() {
		return paymentMasterMapForStatus;
	}

	public void setPaymentMasterMapForStatus(
			Map<String, String> paymentMasterMapForStatus) {
		this.paymentMasterMapForStatus = paymentMasterMapForStatus;
	}

	public String getPaymentMasterMapForStatusForDesc(String desc){
		return paymentMasterMapForStatus.get(desc);
	}

	public Map<Integer, String> getDegreeMap() {
		return degreeMap;
	}

	public void setDegreeMap(Map<Integer, String> degreeMap) {
		this.degreeMap = degreeMap;
	}

	public Map<Integer, Map<Integer, String>> getUniversityMap() {
		return universityMap;
	}

	public void setUniversityMap(Map<Integer, Map<Integer, String>> universityMap) {
		this.universityMap = universityMap;
	}

	public Map<Integer, String> getYearMap() {
		return yearMap;
	}

	public void setYearMap(Map<Integer, String> yearMap) {
		this.yearMap = yearMap;
	}

	public Map<Integer, String> getInterviewLocMap() {
		return interviewLocMap;
	}

	public void setInterviewLocMap(Map<Integer, String> interviewLocMap) {
		this.interviewLocMap = interviewLocMap;
	}

	public Map<Integer, String> getFieldOfIntrestMap() {
		return fieldOfIntrestMap;
	}

	public void setFieldOfIntrestMap(Map<Integer, String> fieldOfIntrestMap) {
		this.fieldOfIntrestMap = fieldOfIntrestMap;
	}

	public Map<Integer, String> getAreaOfProjectMap() {
		return areaOfProjectMap;
	}
	

	public Map<String, String> getYearList() {
		return yearList;
	}

	public void setYearList(Map<String, String> yearList) {
		this.yearList = yearList;
	}

	public void setAreaOfProjectMap(Map<Integer, String> areaOfProjectMap) {
		this.areaOfProjectMap = areaOfProjectMap;
	}

	/**
	 * @return the cityList
	 */
	public List<String> getCityList() {
		return cityList;
	}

	/**
	 * @param cityList the cityList to set
	 */
	public void setCityList(List<String> cityList) {
		this.cityList = cityList;
	}
	
	public String getYearVal(Integer year){
		return yearMap.get(year) ;
	}
	
	public String getInterviewVal(Integer interview){
		return interviewLocMap.get(interview) ;
	}
	
	public String getDegreeVal(Integer degree){
		return degreeMap.get(degree) ;
	}
	
	public String getFieldInterestVal(Integer field){
		return fieldOfIntrestMap.get(field) ;
	}
	
	public String getAreaProjectVal(Integer area){
		return areaOfProjectMap.get(area) ;
	}

	public Map<String, Integer> getPaymentMasterMapForDesc() {
		return paymentMasterMapForDesc;
	}

	public void setPaymentMasterMapForDesc(
			Map<String, Integer> paymentMasterMapForDesc) {
		this.paymentMasterMapForDesc = paymentMasterMapForDesc;
	}
	
	public Integer getPaymentMasterMapForDescId(String paymentType){
		return paymentMasterMapForDesc.get(paymentType) ;
	}

	public Map<String, Integer> getCityMasterMap() {
		return cityMasterMap;
	}

	public void setCityMasterMap(Map<String, Integer> cityMasterMap) {
		this.cityMasterMap = cityMasterMap;
	}
	
	
	public Integer getCityIdForVal(String cityDesc){
		return cityMasterMap.get(cityDesc) ;
	}

	public Map<Integer, String> getRefMap() {
		return refMap;
	}

	public void setRefMap(Map<Integer, String> refMap) {
		this.refMap = refMap;
	}
	
	public String getRefDesc(Integer refPk){
		return refMap.get(refPk) ;
	}
	public String getTemplateVal(String keyName){
		String value = null;
		
		if(loginTemplateMap.get(keyName) == null){
			//logger.info("ALERT -> No Configuration constant defined for keyname : " + keyName);
		}
		else{
			value = loginTemplateMap.get(keyName);
		}
		
		return value;
	}
	public String getVersionVal(String keyName){
		String value = null;
		
		if(loginVersionMap.get(keyName) == null){
			//logger.info("ALERT -> No Configuration constant defined for keyname : " + keyName);
		}
		else{
			value = loginVersionMap.get(keyName);
		}
		
		return value;
	}
	
	public String getEligibiltyCriteria(String keyName){
		String value = null;
		
		if(eligibiltyCriteriaMap.get(keyName) == null){
			//logger.info("ALERT -> No Configuration constant defined for keyname : " + keyName);
		}
		else{
			value = eligibiltyCriteriaMap.get(keyName);
		}
		
		return value;
	}
	
	public String getDeclarationVal(String keyName){
		String value = null;
		
		if(declarationMap.get(keyName) == null){
			//logger.info("ALERT -> No Configuration constant defined for keyname : " + keyName);
		}
		else{
			value = declarationMap.get(keyName);
		}
		
		return value;
	}
	
	public String getDeclarationVal1(String keyName){
		String value = null;
		
		if(declarationMap1.get(keyName) == null){
			//logger.info("ALERT -> No Configuration constant defined for keyname : " + keyName);
		}
		else{
			value = declarationMap1.get(keyName);
		}
		
		return value;
	}
	//shekharc apply page confirmation msg start
	public String getapplyPostConfirmation(String keyName){
		String value = null;
		
		if(applyPostConfirmationMap.get(keyName) == null){
			//logger.info("ALERT -> No Configuration constant defined for keyname : " + keyName);
		}
		else{
			value = applyPostConfirmationMap.get(keyName);
		}
		
		return value;
	}
	//shekharc apply page confirmation msg end
	
	public void setPaymentMasterReverseMapForDesc(
			Map<String, String> paymentMasterReverseMapForDesc) {
		this.paymentMasterReverseMapForDesc = paymentMasterReverseMapForDesc;
	}

	public Map<String, String> getPaymentMasterReverseMapForDesc() {
		return paymentMasterReverseMapForDesc;
	}

	public void setAcademicMandatoryMap(Map<String, String> academicMandatoryMap) {
		this.academicMandatoryMap = academicMandatoryMap;
	}

	public Map<String, String> getAcademicMandatoryMap() {
		return academicMandatoryMap;
	}

	public void setDistrictCityMap(Map<Integer, List<String>> districtCityMap) {
		this.districtCityMap = districtCityMap;
	}

	public Map<Integer, List<String>> getDistrictCityMap() {
		return districtCityMap;
	}

	public void setApplyDisciplineMap(Map<Integer, String> applyDisciplineMap) {
		this.applyDisciplineMap = applyDisciplineMap;
		
		for(Entry<Integer, String> entry : applyDisciplineMap.entrySet()){
			getApplyDisciplineMapReverseMapping().put(entry.getValue(), entry.getKey());
		}
	}

	public Map<Integer, String> getApplyDisciplineMap() {
		return applyDisciplineMap;
	}

	public void setApplyDisciplineMapReverseMapping(
			SortedMap<String, Integer> applyDisciplineMapReverseMapping) {
		this.applyDisciplineMapReverseMapping = applyDisciplineMapReverseMapping;
	}

	public SortedMap<String, Integer> getApplyDisciplineMapReverseMapping() {
		return applyDisciplineMapReverseMapping;
	}

	public void setDistrictCity1Map(Map<Integer, Map<Integer, String>> districtCity1Map) {
		this.districtCity1Map = districtCity1Map;
	}

	public Map<Integer, Map<Integer, String>> getDistrictCity1Map() {
		return districtCity1Map;
	}

	public Map<Integer, String> getSportsMasterMap() {
		return sportsMasterMap;
	}

	public void setSportsMasterMap(Map<Integer, String> sportsMasterMap) {
		this.sportsMasterMap = sportsMasterMap;
	}

	public Map<Integer, String> getRecrumentListMap() {
		return recrumentListMap;
	}

	public void setRecrumentListMap(Map<Integer, String> recrumentListMap) {
		this.recrumentListMap = recrumentListMap;
	}

	public Map<String, String> getPcStageListMap() {
		return pcStageListMap;
	}

	public void setPcStageListMap(Map<String, String> pcStageListMap) {
		this.pcStageListMap = pcStageListMap;
	}

	public Map<String, String> getSiStageListMap() {
		return siStageListMap;
	}

	public void setSiStageListMap(Map<String, String> siStageListMap) {
		this.siStageListMap = siStageListMap;
	}

	public Map<String, String> getPcRecrumentcategoryListMap() {
		return pcRecrumentcategoryListMap;
	}

	public void setPcRecrumentcategoryListMap(
			Map<String, String> pcRecrumentcategoryListMap) {
		this.pcRecrumentcategoryListMap = pcRecrumentcategoryListMap;
	}

	public Map<String, String> getSiRecrumentcategoryListMap() {
		return siRecrumentcategoryListMap;
	}

	public void setSiRecrumentcategoryListMap(
			Map<String, String> siRecrumentcategoryListMap2) {
		this.siRecrumentcategoryListMap = siRecrumentcategoryListMap2;
	}

	public Map<String, Map<Integer, String>> getRefKeyValMasterMap() {
		return refKeyValMasterMap;
	}

	public void setRefKeyValMasterMap(
			Map<String, Map<Integer, String>> refKeyValMasterMap) {
		this.refKeyValMasterMap = refKeyValMasterMap;
	}

	public Map<String, Map<Integer, String>> getRecrumentCityMap() {
		return recrumentCityMap;
	}

	public void setRecrumentCityMap(
			Map<String, Map<Integer, String>> recrumentCityMap) {
		this.recrumentCityMap = recrumentCityMap;
	}

	//added by sravyav
	public void setAcademicMap(Map<Integer, Map<Integer, String>> academicMap) {
		this.academicMap = academicMap;
	}

	public Map<Integer, Map<Integer, String>> getAcademicMap() {
		return academicMap;
	}

	public Map<Integer, String> getAcademicMap1(Integer examId){
		return getAcademicMap().get(examId);
	}

	public void setApplyNotificationMap(Map<Integer, String> applyNotificationMap) {
		this.applyNotificationMap = applyNotificationMap;
	}

	public Map<Integer, String> getApplyNotificationMap() {
		return applyNotificationMap;
	}

	public void setDepartmentTypeMap(Map<Integer, String> departmentTypeMap) {
		this.departmentTypeMap = departmentTypeMap;
	}

	public Map<Integer, String> getDepartmentTypeMap() {
		return departmentTypeMap;
	}

	public void setWardMap(Map<Integer, String> wardMap) {
		this.wardMap = wardMap;
	}

	public Map<Integer, String> getWardMap() {
		return wardMap;
	}
	
	public Map<Integer, Map<Integer, Map<String, Integer>>> getAgeMatrixMap() {
		return ageMatrixMap;
	}

	public void setAgeMatrixMap(
			Map<Integer, Map<Integer, Map<String, Integer>>> ageMatrixMap) {
		this.ageMatrixMap = ageMatrixMap;
	}

	public void setDeclarationMap1(Map<String, String> declarationMap1) {
		this.declarationMap1 = declarationMap1;
	}

	public Map<String, String> getDeclarationMap1() {
		return declarationMap1;
	}


	public Map<Integer, String> getNativityMap() {
		return nativityMap;
	}

	public void setNativityMap(Map<Integer, String> nativityMap) {
		this.nativityMap = nativityMap;
	}

	/**
	 * @return the preferredCenterMasterMap
	 */
	public Map<Integer, String> getPreferredCenterMasterMap() {
		return preferredCenterMasterMap;
	}

	/**
	 * @param preferredCenterMasterMap the preferredCenterMasterMap to set
	 */
	public void setPreferredCenterMasterMap(Map<Integer, String> preferredCenterMasterMap) {
		this.preferredCenterMasterMap = preferredCenterMasterMap;
	}

	public Map<Integer, String> getSubDegreeMap() {
		return subDegreeMap;
	}

	public void setSubDegreeMap(Map<Integer, String> subDegreeMap) {
		this.subDegreeMap = subDegreeMap;
	}

	public Map<Integer, AgeMatrix> getAgeMatrixMapping() {
		return ageMatrixMapping;
	}

	public void setAgeMatrixMapping(Map<Integer, AgeMatrix> ageMatrixMapping) {
		this.ageMatrixMapping = ageMatrixMapping;
	}
	
	public Map<Integer, String> getBoardMap() {
		return boardMap;
	}

	public void setBoardMap(Map<Integer, String> boardMap) {
		this.boardMap = boardMap;
	}

	public Map<Integer, String> getSignedByDataMap() {
		return signedByDataMap;
	}

	public void setSignedByDataMap(Map<Integer, String> signedByDataMap) {
		this.signedByDataMap = signedByDataMap;
	}
	
}