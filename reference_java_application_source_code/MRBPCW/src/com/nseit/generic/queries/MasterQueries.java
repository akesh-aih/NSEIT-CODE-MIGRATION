package com.nseit.generic.queries;

// Master related generic queries added by Tarka on 16-Dec-2011
public interface MasterQueries {
	String GET_DOWNLOAD_PATHS = " select gcp.OCPT_PATH_NAME,gcp.OCPT_PATH_VALUE from OES_CONFIG_PATHS gcp ";

	String GET_DATE_WINDOW_DATES = " SELECT ODW_DATE_WINDOW_PK,TO_CHAR(ODW_START_DATE,'DD-MON-YYYY HH24:MI:SS') AS START_DATE  ,TO_CHAR(ODW_END_DATE,'DD-MON-YYYY HH24:MI:SS') AS END_DATE from OES_DATE_WINDOW where ODW_STATUS = 'A'";

	/**
	 * @author Pankaj Sh
	 */
	String GET_COUNTRY_NAMES = " SELECT GCM.OCM_COUNTRY_PK,GCM.OCM_COUNTRY_NAME FROM OES_COUNTRY_MASTER GCM ORDER BY  GCM.OCM_COUNTRY_NAME ";

	String GET_QUESTIONS_NAMES = " SELECT OQM_QSTN_PK,OQM_QSTN_DESC FROM OES_QSTN_MASTER WHERE OQM_STATUS = 'A' ORDER BY OQM_QSTN_DESC ";

	String GET_COUNTRY_STATE_MAPPING = " SELECT GSM.OSM_STATE_PK, GSM.OSM_STATE_NAME, GSM.OSM_COUNTRY_FK FROM OES_STATE_MASTER GSM "
			+ " WHERE GSM.OSM_COUNTRY_FK = ? ORDER BY GSM.OSM_STATE_NAME ";

	String GET_ACDM_UNIV_MAPPING = "select oum_university_pk,oum_university_desc from oes_university_master where " + "oum_acdm_fk = ? and oum_status = 'A' order by oum_university_pk;";

	String GET_COUNTRY_UT_MAPPING = " SELECT GSM.OSM_STATE_PK, GSM.OSM_STATE_NAME, GSM.OSM_COUNTRY_FK FROM OES_STATE_MASTER GSM "
			+ " WHERE GSM.OSM_COUNTRY_FK = ? AND OSM_TYPE='U' ORDER BY GSM.OSM_STATE_NAME ";

	String GET_COUNTRY_IDS = " SELECT GCM.OCM_COUNTRY_PK FROM OES_COUNTRY_MASTER GCM ";

	String GET_STATE_IDS = " SELECT GSM.OSM_STATE_PK FROM OES_STATE_MASTER GSM ";

	String GET_DISTRICT_IDS = " SELECT GDM.ODM_DISTRICT_PK FROM OES_DISTRICT_MASTER GDM ";

	String GET_STATE_DISTRICT_MAPPING = " select opsr_opsm_fk,opsr_sub_rank from  oes_posting_sub_ranks where opsr_opm_group_cat=? order by opsr_sub_rank";

	String GET_DISTRICT_CITY_MAPPLING = "SELECT ops_stn_pk, ops_stn_name FROM oes_police_stations " + "WHERE ops_district_fk = ? ORDER BY ops_stn_name";

	String GET_DISTRICT_TALUKA_MAPPING = " SELECT TM.ODM_TALUKA_PK, TM.ODM_TALUKA_NAME FROM OES_TALUKA_MASTER TM " + " WHERE TM.ODM_DISTRICT_FK = ? ORDER BY TM.ODM_TALUKA_NAME ";

	String GET_TEST_CENTER_DETAILS = "select gctm.otcm_TEST_CENTRE_PK,gctm.otcm_city from OES_TEST_CENTER_MASTER gctm order by  upper(gctm.otcm_city)";

	String GET_TEST_MASTER_DETAILS = " SELECT OTM_TEST_PK,GTM.OTM_FEES_OPEN,GTM.OTM_FEES_SC_ST,GTM.OTM_TEST_NAME,GTM.OTM_DURATION,GTM.OTM_DESCRIPTION, GTM.OTM_FEES_IN_WORDS_OPEN, GTM.OTM_FEES_IN_WORDS_SC_ST FROM OES_TEST_MASTER GTM  ";

	String GET_DISCIPLINE_MAP = " SELECT GTM.OTM_TEST_PK,GTM.OTM_TEST_NAME FROM OES_TEST_MASTER GTM where OTM_STATUS='A' order by otm_required_percentage asc";

	String GET_APPLY_DISCIPLINE_MAP = " SELECT GTM.OTM_TEST_PK,GTM.OTM_TEST_NAME FROM OES_TEST_MASTER GTM where OTM_STATUS='A'";//and current_timestamp between otm_from_date and otm_to_date ";

	String GET_DATE_WINDOW_DATA = "SELECT odw.odw_description, odw.odw_date_window_pk FROM oes_date_window odw";

	String GET_TEST_CENTER_PK = "select otcm.OTCM_TEST_CENTRE_PK from oes_test_center_master otcm order by otcm.OTCM_TEST_CENTRE_PK asc";

	String GET_BATCH_DETAILS_FOR_TEST_CENTER = "select obd.OBD_BATCH_PK,obd.OBD_AVAILABLE_CAPACITY from oes_batch_details obd where obd.OBD_TEST_CENTRE_FK = ? and obd.OBD_STATUS = 'A' order by obd.OBD_BATCH_PK asc";

	String GET_TEST_DATES_FOR_TEST_CENTER = "select distinct(to_char(obd.OBD_TEST_DATE,'dd-Mon-yyyy')) as obd_test_date "
			+ " from oes_batch_details obd where obd.OBD_TEST_CENTRE_FK = ? " + " order by obd_test_date asc";

	String GET_TEST_DATES = " SELECT DISTINCT (TO_CHAR (obd.obd_test_date, 'dd-Mon-yyyy')) AS obd_test_date     FROM oes_batch_details obd";

	/** INSERT STATEMENTS FOR MASTER QUERIES **/

	/** UPDATE STATEMENTS FOR MASTER QUERIES **/

	/** UPDATE STATEMENTS FOR MASTER QUERIES **/

	/** DELETE STATEMENTS FOR MASTER QUERIES **/

	/** DELETE STATEMENTS FOR MASTER QUERIES **/

	String GET_MODULE_MASTER_MAP = " SELECT OTM_TEST_PK,otm_test_name FROM OES_TEST_MASTER GTM ";
	String GET_Unit_ID = "select opu_present_rank from oes_posting_units ";

	String GET_District_ID = "select  opm_name from  oes_posting_master";
	String GET_Unit = "select  * from  oes_posting_units where opu_present_rank=?";

	String GET_District = "select opsr_opsm_fk,opsr_sub_rank  from  oes_posting_sub_ranks where opsr_opm_group_cat in (select opm_group_cat from  oes_posting_master where opm_name =?) order by opsr_sub_rank";

	String GET_PoliceStationID = "select opsm_city_id from oes_police_station_master";
	String GET_PoliceStationList = "select opsm_pk,opsm_details from oes_police_station_master where opsm_city_id =? order by opsm_details";
	String GET_CategoryID = "select octm_category_pk from oes_category_master";
	String GET_SubCategoryList = "select osctm_subcategory_pk,osctm_subcategory_desc from oes_sub_category_master where osctm_subcategory_status='A' and octm_category_fk=? order by osctm_subcategory_desc ";

	String GET_AcademicID = "select oatm_acdm_pk from oes_acdm_type_master";
	String GET_AcademicList = "select osdm_sub_degree_pk,osdm_sub_degree_desc from oes_sub_degree_master where osdm_acdm_fk=? and osdm_sub_status='A'";
	String GET_Label_Map = "select odm_name,odm_abbreviation from oes_document_master where odm_status='A' order by odm_pk";

	String GET_APPLICATION_END_DATE_MAP = " SELECT GTM.OTM_TEST_PK,GTM.OTM_TO_DATE FROM OES_TEST_MASTER GTM where OTM_STATUS='A'";

	String GET_Issuing_auth_List = "select iss_aut_pk,iss_aut_name from oes_issuing_authority where iss_aut_status='A' and iss_aut_cat_fk=? order by iss_aut_name";

	String GET_RESIDENCE_LOCALITY_MAP = " SELECT orlm_residential_locality_pk,orlm_residential_locality_name FROM oes_residential_locality_master  where orlm_status='A'";

	String GET_DEPARTMENT_TYPE_MASTER = "select odtm_pk, odtm_department_type from oes_department_type_master order by odtm_pk";

	String GET_WARD_MASTER = "select owm_pk, owm_ward from oes_ward_master order by owm_pk";

	String GET_TEST_GROUP = "select otg_test_pk from oes_test_group ";

	String GET_AGE_MATRIX = "select ogm_category_fk,ogm_min_age,ogm_max_age  from  oes_age_matrix where ogm_test_fk=?";

}
