package com.nseit.generic.queries;

public interface TemplateDownloadQueries {
	
	String GET_TEMPLATE = "select otm.OTMP_TEMPLATE from OES_TEMPLATE_MASTER otm where otm.OTMP_TEMPLATE_DESC = ?";
	
	String GET_CANDIDATE_IMAGE = "select oci_photo_image, oci_candidate_image_pk, oum_user_id from oes_candidate_images oci,oes_user_master oum" +
			" where oum.OUM_USER_PK = oci.OCI_USER_FK ";
	
	
	String GET_CANDIDATE_IMAGE_FOR_SCHEDULING = "select * from oes_candidate_details ocd," +
	" oes_candidate_images oci,oes_user_master oum,oes_entrollment_details oed " +
	" where oum.OUM_USER_PK = ocd.OCD_USER_FK  and oum.OUM_USER_PK =oci.OCI_USER_FK " +
	" and oum.OUM_USER_PK = oed.OED_GCPD_USER_FK   and ocd.OCD_USER_FK = oed.OED_GCPD_USER_FK"+
	" and ocd.OCD_VALIDATED_STATUS = 1  and oci.OCI_PHOTO_IMAGE is not null";
	
	String GET_CANDIDATE_SIGN = "SELECT OCI_SIGN_IMAGE, OCI_CANDIDATE_IMAGE_PK, OUM_USER_ID FROM OES_CANDIDATE_IMAGES OCI,OES_USER_MASTER OUM" +
			" WHERE OUM.OUM_USER_PK = OCI.OCI_USER_FK";
	
	
	
	String GET_CANDIDATE_SIGN_FOR_SCHEDULING = "select * from oes_candidate_details ocd," +
	" oes_candidate_images oci,oes_user_master oum,oes_entrollment_details oed" +
	" where oum.OUM_USER_PK = ocd.OCD_USER_FK  and oum.OUM_USER_PK =oci.OCI_USER_FK " +
	" and oum.OUM_USER_PK = oed.OED_GCPD_USER_FK   and ocd.OCD_USER_FK = oed.OED_GCPD_USER_FK"+
	" and ocd.OCD_VALIDATED_STATUS = 1  and oci.OCI_SIGN_IMAGE is not null";

	
	
	String GET_CANDIDATE_DETAILS_WITH_SCHEDULING = "select OUM_USER_ID,oum.OUM_PASSWORD, OCD_FIRST_NAME, OCD_LAST_NAME,  OCD_PHONE_NUMBER, " +
			" OUM_MOBILE_NO,    OUM_EMAIL_ID, OTCM_CITY, to_char(ocd_date_of_birth,'dd/MM/yyyy') as ocd_date_of_birth , " +
			" OCD_COMM_ADDRESS, OCD_COMM_PIN_CODE, OCD_COMM_CITY, OSM_STATE_NAME, " +
			"  OTCM_TEST_CENTRE_NAME, to_char(OBD_TEST_DATE,'dd/MM/yyyy') as OBD_TEST_DATE, " +
			" to_char(OBD_TEST_START_TIME,'hh12:mi')||'-'|| to_char(OBD_TEST_END_TIME,'hh12:mi') as slot, " +
			"    OTM_TEST_NAME, OTCM_TEST_CENTRE_PK,     OED_ENROLLMENT_PK,  " +
			"  decode(dbms_lob.getlength(oci.oci_photo_image),null,null,oum_user_id || '.jpg') AS photo, " +
			"   decode(dbms_lob.getlength(oci.oci_sign_image),null,null,oum_user_id || '.jpg') AS SIGN," +
			"  OTM_TEST_PK,OED_ROLL_NO, decode(OCD_COMM_DISTRICT_FK, null,OCD_COMM_DISTRICT, " +
			" odm.ODM_DISTRICT_NAME) as district_name " +
			"  from oes_candidate_details ocd, oes_user_master oum, oes_state_master osm    , " +
			"     oes_entrollment_details oed, oes_batch_details obd, oes_test_center_master otcm,   " +
			"  oes_test_master otm, oes_candidate_images oci, oes_district_master odm  " +
			"  where ocd.OCD_USER_FK = oum.OUM_USER_PK     and ocd.OCD_TEST_FK = otm.OTM_TEST_PK    " +
			"  AND NVL(ocd.ocd_comm_state_fk,ocd.ocd_comm_union_terr_fk) = osm.osm_state_pk  " +
			"  and oed.OED_GCPD_USER_FK = ocd.OCD_USER_FK   " +
			" and oed.OED_BATCH_FK = obd.OBD_BATCH_PK  " +
			"  and obd.OBD_TEST_CENTRE_FK = otcm.OTCM_TEST_CENTRE_PK " +
			"    and oum.OUM_USER_PK = oci.OCI_USER_FK(+)  " +
			"  and ocd.OCD_COMM_DISTRICT_FK = odm.ODM_DISTRICT_PK(+)  " +
			" and ocd.OCD_VALIDATED_STATUS = 1 order by OCD_USER_FK";
	
	String GET_CANDIDATE_DETAILS="SELECT oum_user_id,ocd_first_name,ocd_last_name,oum_mobile_no,oum_email_id,otcm_city,to_char(ocd_date_of_birth, 'dd-Mon-yyyy') AS ocd_date_of_birth,ocd_comm_address,ocd_comm_pin_code,osm_state_name,otcm_test_centre_name,ocd_test_fk,otcm_test_centre_pk,ocd_phone_numeric"+ 
                                    " FROM oes_user_master"+
                                    "	INNER JOIN oes_candidate_details ON (oes_user_master.oum_user_pk = oes_candidate_details.ocd_user_fk)"+
                                    "	INNER JOIN oes_state_master ON (oes_state_master.osm_state_pk = oes_candidate_details.ocd_comm_state_fk)"+
                                    "	LEFT OUTER JOIN oes_cand_pref_details ON (oes_cand_pref_details.ocpd_user_fk = oes_user_master.oum_user_pk)"+
                                    "	LEFT OUTER JOIN oes_test_center_master ON (oes_test_center_master.otcm_test_centre_pk = oes_cand_pref_details.ocpd_pref_test_centre_1_fk)"+
                                    "	ORDER BY oum_user_id";
	  String GET_ALL_CANDIDATE_DOCUMENT="select OCD_USER_FK,oum_user_id,OCD_FLAG,OCD_DOC_FILE_NAME,OCD_DOCUMENT,OCD_CHECKBOX, ocd_doc_verify_status  "+
	  " FROM OES_CANDIDATE_DOC  "+ 
	  " inner join oes_user_master on oum_user_pk=ocd_user_fk  "+
	" WHERE OCD_STATUS='A' "+
	" union all  select ocad_user_fk,oum_user_id,ocad_flag,ocad_doc_file_name,ocad_document,ocad_checkbox,ocad_doc_verify_status  "+
	" from  oes_candidate_additional_doc  "+
	" inner join oes_user_master on ocad_user_fk=oum_user_pk  "+
	" where ocad_status='A' "+
	" order by OCD_USER_FK";
		/*"SELECT OCD_CAND_DOC_PK,OCD_FLAG,OCD_DOC_FILE_NAME,OCD_DOCUMENT,OCD_CHECKBOX, ocd_doc_verify_status,OCD_USER_FK,ocd_created_by FROM OES_CANDIDATE_DOC WHERE OCD_STATUS='A' order by OCD_USER_FK";*/
	  String GET_CANDID_DETAIL_VIEW = "SELECT * FROM vw_candidate_dump ";
	
}
