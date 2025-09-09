package com.nseit.generic.models;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.nseit.generic.util.CommonUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Users implements Serializable, HttpSessionBindingListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String isGovtService;
	private boolean diffAbledChkBox;
	private String BenchmarkDisability;
	private String getCategoryValDesc;
	private String userDistrict;
	private String candidateFullName;
	private String candidateName;
	private long userId;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String fatherName;
	private String motherMaidenName;
	private Timestamp dateOfBirth;
	private String gender;
	private String transgender;
	private String Isdisabled;
	private String mobile;
	private String emailAddress;
	private String confirmEmailAddress;
	private String captcha;
	private String ugSubject;
	private String pgSubject;
	private String testCenter1;
	private String testCenter2;
	private String testCenter3;
	private String testCenter4;
	private String testCenter5;
	private int ug_acdm_pk;
	private int pg_acdm_pk;
	private int work_exp_pk;
	private String isphdchecked;
	private String categorycertificate;
	private String nativityCertDist;
	private String nationality;
	private String date_Of_Birth;
	private String genderValDesc;
	private String initials;
	private String isExServicemen;
	private String knowmstsc;
	private String disciplinName;
	private String changeInName;
	private String changeedName;
	private String scribe;
	private String skillTest;
	private String postSelected;
	private String pwdSubCategory;
	private String pwdSubCategory1;
	private String pwdSubCategoryForCerebal;
	private String scribe_flag;
	private String statevalue;// created to get value from personal page to
								// education page for validation purpose

	private int invalidCount;
	private String activeStatus;
	private Long userPk;
    private boolean editFlag;
	

	private String alternetEmailAddress;
	private Date dateofBirth;

	private Timestamp createdDate;
	private String createdBy;
	private Timestamp updatedDate;
	private String updatedBy;
	private String status;
	private String userType;

	private String enrollmentPK;
	private String firstLogin;
	private Integer statusID;

	private String stage;
	private String ipin;
	private String disciplineID;
	private String discipline;
	private Double currentStage;
	private String nextStage;

	private String paymentValidated;
	private String errorMsg;
	private Integer candidateStatusId;
	private Long userFk;
	private String tempStage;
	private String currentMenuKey;
	private String loginPageReq;
	private String userTitle;
	private String roleFK;
	private String subcasteForDoc;
	private String subjects;

	private String diplomaSub;
	private String graduationDegreeSub;
	private String postGraduationSub;
	private String additionalDiplomaSub;
	private String additionalGraduationDegreeSub;
	private String additionalPostGraduationSub;
	private String diplomaSubField;
	private String graduationDegreeSubField;
	private String postGraduationSubField;
	private String additionalDiplomaSubField;
	private String additionalGraduationDegreeSubField;
	private String additionalPostGraduationSubField;

	private String aDD1Sub;
	private String aDD2Sub;
	private String aDD3Sub;
	private String aDD4Sub;
	private String aDD5Sub;

	private String aDD1SubFeild;
	private String aDD2SubFeild;
	private String aDD3SubFeild;
	private String aDD4SubFeild;
	private String aDD5SubFeild;

	private boolean exServicemanFlag;
	private boolean wardQuotaFlag;
	private String merchantTxnSeqNumber = "";
	private String merchantTxnRefNumber = "";

	private String agequotaradiocheck;
	private String underQuotaOption;
	private String candidateChgName;

	private String physicalDisability;
	private String disabilityType;
	private String categoryVal;
	private String categoryValDesc;
	private String age;

	private String scribeRequired;

	private String disabilityPerc;
	private boolean isexempted;
	private String yesNoA;
	private String institutionType;
	private String candidateFirstName;
	private String candidateMiddleName;
	private String candidateLastName;
	private String durationOfYears;
	// private static Map<Users, HttpSession> logins = new HashMap<Users,
	// HttpSession>();

	// created by sanket for OPAL

	private String govtEmployee;
	private String domicileUp;
	private String freedomFighter;
	private String homeGuard;

	//created by sanket for MRB
	private String exServiceMen;
	private String dischargeDate;
	private String ppoNumber;
	private String commCertYesNo;
	private String community;
	private String subCaste;
	private String issueAuthCommCert;
	private String commCertNo;
	private String commCertPlace;
	private String commIssueDate;
	private String disableYesNo;
	private String disableType;
	private String disablityPercent;
	
	private String declaration;
	
	private String widowYesNo;
	private String desWidowCertNo;
	private String widowIssueDate;
	private String widowIssueAuth;
	private String widowDistrict;
	private String widowOtherDistrict;
	private String widowSubDivision;
	private boolean widowCheckbox;

	
	private Integer category;
	private List<Integer> academic_selection;

	private String remoteIp;
	private String covidDutyCertificate;

		@Override
	public boolean equals(Object other) {
		Long usId = Long.valueOf(userId);
		return (other instanceof Users) && (usId != null) ? usId.equals((Long.valueOf(((Users) other).userId))) : (other == this);
	}

	@Override
	public int hashCode() {
		Long usId = Long.valueOf(userId);
		return (usId != null) ? (this.getClass().hashCode() + usId.hashCode()) : super.hashCode();
	}

	@Override
	public void valueBound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		/*
		 * HttpSession session = logins.remove(this); if (session != null) {
		 * session.invalidate(); } logins.put(this, arg0.getSession());
		 */

	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		// TODO Auto-generated method stub
		// logins.remove(this);

	}
	
	public String getCurrentDateForHeaderDisplay() {
		return CommonUtil.getHeaderDisplayDate();
	}

	}
