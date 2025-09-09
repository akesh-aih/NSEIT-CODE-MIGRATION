package com.nseit.generic.models;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CandidateBean {
	private boolean diffAbledChkBox;
	private int parentAndGuardian;
	private String guardianName;
	private String parentAndGuardianVal;
	private boolean govtServChkBox;
	private List<CovidDutyCertDetailsBean> covidDutyCertDtlsList = new ArrayList<CovidDutyCertDetailsBean>();
	private String totalDurationOfService;
	private String displayAppFlag;
	private String appFormStrtDate;
	private String appFormEndDate;
	private String altTelephoneNo1;
	private String altTelephoneNo2;
	private String telephoneNo1;
	private String telephoneNo2;
	private String telephoneNo;
	private String altTelephoneNo;
	private String physicalDisability;
	private String admitCardLabel;
	private String callLetterLabel;
	private String scoreCardLabel;
	private String practiceTestLabel;
	private String showPracticeTestLink;
	private String registrationId;
	private String CandidateFirstLogin;
	private Integer statusID;
	private String studiedMath;
	private String EmailAddress;
	private String applicableFee;
	private String photoupdateStatus;
	private String certiInsti;
	private String terriArmy;
	private String certiB;
	private String doeacc;
	private String ugcDegree;
	private String certiIssuing;
	private String certiNum;
	private String stage_1;
	private String stage_2;
	private String applicablefeeamt;
	private String challanDate;
	private String PopupMessage;
	private String challanNo;
	private String totalAmount;
	private int opd_fk;
	private String opd_fk_desc;
	private String minDate;
	private String maxDate;	
	private String fathersName;
	private String fathersInitial;
	private String fathersEducation;
	private String fathersOccupation;
	private String fathersDesignation;
	private String fathersOrganization;	
	private String mothersName;
	private String mothersInitial;
	private String mothersEducation;
	private String mothersOccupation;
	private String mothersDesignation;
	private String mothersOrganization;
	private String categoryCertIssDt;
	private boolean ageChkBox;
	private String ageInDays;
	private String ageInMonths;
	private String ageInYears;
	private String disablityCertNo;
	private String disabilityCertIssDt;
	private String signupdateStatus;
	private String nativity;
	private String otherNativity;
	private String disabilityType;
	private String cerebralPalsy;
	private String compensatoryTime;
	private String dominantwriting;
	private String needTime;
	private String serviceofscribe;
	private String bloodgroup;
	private String identityCardNumber;
	private String dateOfBirth;
	private String ageAsOn;
	private String logoCast;
	private String obc;
	private String sc;
	private String st;
	private String sca;
	private String mbc;
	private String bc;
	private String bcm;
	private String total;
	private String optLang;
	private String optPaper;
	private String admitCardExist;
	private String callLetterExist;
	private String scoreCardExist;
	private String admitcardDownloadDate;
	private String callLetterDownloadDate;
	private String scoreCardDownloadDate;
	private String subjects;
	private String langUG;
	private Integer monthsOfGovService;
	private String yearsOfGovService;
	private Boolean subjectAppliedFlag;
	private String mainSubjectApplied;
	private String ugSubject;
	private String pgSubject;
	private int ug_pk;
	private String workExp;
	private String mediumnetVal;
	private String mstcEmpNo;
	private String catCertNo;
	private String pwdSubCategoryForCerebal;
	private String pwdSubCategoryForCerebalPalsy;
	private String adhaarNo;
	private String domicileUp;
	private String domicileCertificateUp;
	private String domicileCertificate;
	private String domicileSerial;
	private String freedomFighter;
	private String freedomFighterDt;
	private String freedomFighterSerial;
	private String freedomFighterAuthority;
	private String homeGuardDt;
	private String homeGuard;
	private String durationOfMonths;
	private String durationOfYears;
	private String durationOfDays;
	private String catSerial;
	private String nocAuthority;
	private String nocDt;
	private String nocSerial;
	private String homeGuardSerial;
	private String governmentEmp;
	private String governmentEmpDt;
	private String empYear;
	private String empMonth;
	private String empDays;
	private String scribe_flag;
	private boolean isexempted;
	private String declarationCkeck;
	private String mstcEmpNumber;
	private String spouseName;
	private String spouseEducation;
	private String spouseOccupation;
	private String spouseDesignation;
	private String spouseOrganization;
	private String twinsibilings;
	private String nameoftwin;
	private String genderoftwin;
	private String empName;
	private String mstcSelected;
	private String yesNoMstc;
	private String yesOrNo;
	private String mstcWork;
	private String categorycertificate;
	private String isExServicemen;
	private String esmDateOfEnlistment;
	private String esmDateOfDischarge;
	private String defPersonDisabled;
	private String isWidow;
	private String isJKDom;
	private int esmDiff;
	private int preferredRank;
	private String preferredCenter;
	private String preferredCityList;// for rms
	private String preferredCityList1;// for postal
	private String preferredCityList2;// for administrative
	private Boolean editBtnFlag;
	private String profQual; 
	
	
	private String otmPostName;
	private String am_java;
	private String am_dotnet;
	private String categoryCertValDt;
	private String relationQues;
	private String transgender;
	private String pwdCategory;
	private String pwdSubCategory;
	private String pwdSubCategory1;
	private String pwdSubCategory2;
	private String userName;
	private String mobileNo;
	private String underQuotaOption;
	private String getAgeQuotaDetailsRadioChk;
	private String specialOptVal;
	private String GovernmntTamil;
	private String userIdForImage;
	private String userPrimaryKey;
	private String flag;
	private String flagForEdit;
	private String userId;
	private String candidatePk;
	private String photoImage;
	private int bookingAttempt;
	private String linkEncableStatusForattempt1;
	private String linkEncableStatusForAttempt2;
	private String checkCandidateAcceptance;
	private String applyNotify;
	private int Sr_no;
	private String afterApplyVeiwPayment;
	private String candidateDashboard;
	private String appNumber;
	private String advertisementnumber;
	private String instructions;
	private String appEndDate;
	private String appPrint;
	private String auditFlag;
	private String userfk;
	private String challanbarcodeno;
	private String nativeTamil;
	private String candidateAgeQuotacheck;
	private String candidateAgeQuotacheckHdn;
	private String candidatePhotoName;
	private String candidateSignatureName;
	private String presentRankMini;
	private String presentPostingUnitMini;
	private String miniExeStaff;
	private String appliedSubject;
	private String universityOth;
	private String scribe_yes_no;
	private String jsonDate;

	private String eduToDocUploadRedirectFlag;
	private String newPassword;
	private String oldPassword;
	private String firstName;
	private String middleName;
	private String lastName;
	private String cityName;
	private String imageCast;
	private String signatureCast;
	private String captcha;
	private String registrationIpAddress;
	private AddressBean addressBean;
	private PersonalDetailsBean personalDetailsBean;
	private EducationDetailsBean educationDetailsBean;
	private RegistrationBean registrationBean;
	private AcademicDetailsBean academicDetailsBean = new AcademicDetailsBean();
	private String candidatePhoto;
	private String policedept;

	private String declarationhid;
	private String declaration;
	private String claimPstm;
	private String pgTamilMed;
	private String bedtamilMed;
	private String eduCategory;
	private String AdditionSubjectName;
	private String deptpresentPostingUnit;
	private String yearsOfCompletion;
	private String certificateNumber;
	private String designationOfIssuingAuthority;
	private String nocDate;
	private String serviceAsOn;
	private boolean verifyDocFlag;
	private boolean exServicemanFlag;
	private boolean wardQuotaFlag;
	private boolean departmentQuotaFlag;
	private boolean arpdepartmentQuotaFlag;
	// For Normal with age Candidate
	private boolean showExServicemanFlag;
	private boolean showExServiceWomanFlag;
	private boolean showWidowFlag;
	private String candidateChgName;
	private String districtValother;
	private String cityNameother;

	private String altDistrictValOthers;
	private String alternateCityother;
	private String eduDetailRedirectFlag;

	private String docUploadRedirectFlag;

	private String exServiceFlag;
	private String sslcTamil;
	private String widowFlag;
	private String deptCandidateFlag;

	private String addDetailRedirectFlag;

	private String samePreferenceValueFlag;

	AdditionalDetailsBean additionalDetailsBean = new AdditionalDetailsBean();
	private String MediumInstrutionCaste;
	private String benchmarkDisability;
	private String exServiceman;
	private String departMentalCandidate;
	private String mariatalStatus;
	private String religionBelief;
	private String IDproof;
	private String languageWrittenTest;

	private String disciplinName;

	private String nationality;
	private String genderVal;
	private String countryVal;
	private String altCountryVal;

	private String stateVal;
	private String altStateVal;

	private String talukaValDesc;
	private Integer talukaVal;

	private String altTalukaValDesc;

	private Integer altTalukaVal;

	private String districtVal;
	private String otherDistrict;
	private String altDistrictVal;
	private String altOtherDistrict;
	private String unionTerritoryVal;
	private String altUnionTerritoryVal;

	private String categoryVal;

	private InputStream inputStreamForImage;
	private String candidateImagePK;
	private File attachmentPhoto;
	private String attachmentPhotoContentType;
	private String attachmentPhotoFileName;
	private String attachementPhotoHash;

	private File attachmentSignature;
	private String attachmentSignatureContentType;
	private String attachmentSignatureFileName;
	private String attachmentSignatureHash;

	private Integer academic;

	private List<Integer> yearVal;
	private List<Integer> selectYear;

	private List<String> universityName;
	private String checkCandidateAcceptance1;

	private int addDocCount;

	private String pwdPercentage;

	private String quotaData8FileName;

	private String ocdFlagDeptQuota;
	private boolean quotaDocumentUploaded8;
	private String candidateDocPk8;
	private String disabilityId;
	private String age;
	private String scribeRequired;

	private String nationalityDesc;
	private String genderValDesc;
	private String countryValDesc;
	private String altCountryValDesc;

	private String stateVaDescl;
	private String altStateValDesc;

	private String districtValDesc;
	private String altDistrictValDesc;
	private String unionTerritoryValDesc;
	private String altUnionTerritoryValDesc;
	private String stateValDesc;

	private String categoryValDesc;
	private boolean addressChkBox;
	private boolean isImageUploaded;
	private String successFlag;

	private String candidateStage;

	
	private String stageOneFlag;

	private String attmpt1Flag;
	private String attmpt2Flag;

	private String updatedBy;

	private Integer disciplineId;

	private String displayRegFlag;
	private String regStrtDate;
	private String regEndDate;

	// for approvL receipt
	private String regApprovalDate;
	private String candidateAddr;
	private String candidateName;
	private String candidateAddr1;
	private String candidateAddr2;
	private String candidateAddr3;
	private String candidateAddr4;

	private String commCity;
	private String commTaluka;
	private String commDistrict;
	private String commState;
	private String commCountry;
	private String commPinCode;

	private String helpCenterAddr;
	private String newDate;

	private String receiptType;

	private String talukaField;
	private String altTalukaField;

	private String talukaFlag;
	private String altTalukaFlag;

	private String nodalRejectFlag;

	// poverty Line map

	private String testCenter1;
	private String testCenter2;
	private String testCenter3;
	private String testCenter4;
	private String testCenter5;
	private String testCenterApply;

	private Integer testCenterId;

	private String fileNameFaq;
	private File file4;
	private String fileNameTestInstr;
	private File file3;
	private String fileNameEligibility;
	private byte[] file2;
	private byte[] file5;
	private byte[] file6;
	private byte[] file8;
	private byte[] deptagefile;
	private String data;
	private File data1;
	private String data1Hash;
	private String data2;
	private String dataFileName;
	private String data1FileName;
	private String data2FileName;

	private String data3FileName;
	private String data4FileName;
	private String data5FileName;
	private String data6FileName;
	private String data7FileName;
	private String data8FileName;
	private String data9FileName;
	private String data10FileName;
	private String data11FileName;
	private String data12FileName;
	private String data13FileName;
	private String data14FileName;
	private String dataContentType;
	private String data1ContentType;
	private String data2ContentType;
	private String handicappedValue;
	private String categoryValue;

	private String data3;
	private String data4;
	private String data5;
	private String data6;
	private String data7;
	private String data8;
	private String data9;
	private String data10;
	private String data11;
	private String data12;
	private String data13;
	private String data14;
	private int paymentSubmittedStage;
	private String paymentPkAsReceiptNo;
	private String paymentMode;
	private String paymentStatus;

	// private long receiptAmount;
	private String receiptAmount;
	private String receiptAmountInWords;
	private String paymentTransactionNo;
	private String draftOrChallanNo;
	private String paymentTransactionDate;

	private String degreeVal;

	private boolean isDocumentUploaded;
	private boolean isDocumentUploaded1;
	private boolean isDocumentUploaded2;
	private String documentFileName;
	private String documentFileName1;
	private String documentFileName2;
	private byte[] documentByte;
	private boolean isTestCenterUploaded;
	private String testCenterName1;
	private String testCenterName2;
	private String testCenterName3;
	private boolean imageMandatory;
	private boolean signatureMandatory;
	private boolean documentMandatory;
	private boolean preferenceTestCenterMandatory;
	private boolean educationalDetailsMandatory;
	private boolean candiateDetailsMandatory;

	private boolean progVal1;
	private boolean progVal2;
	private boolean progVal3;
	private boolean progVal4;
	private boolean instrReqd;
	private String disciplineType;
	private String postName;

	private String mobileOTPHdn;
	private boolean verifymobileOTPFlag;
	private String otp;
	private String emailHdn;

	private String pgDeg;
	private String impExp;
	private String tradeBus;
	private String compApp;
	private String lawDeg;
	private String taxation;
	private String translation;
	private String nameOfUniv;
	private String disciplineTypeDesc;
	private List<String> postMasterList;
	private boolean isFormConfirm;
	private String postType;

	private String approveVal;
	private String availableRecordFlag;
	private String approveFlag;
	private String rejectionRemarks;
	private String uploadImageStatus;
	private String uploadSignatureStatus;
	private String uploadDocumentStatus;
	private String testCenterStatus;

	private String candidateTestCenterName;
	private String testDate;
	private String testSlot;

	private String batchDetailsDisplayFlag;
	private String batchPk;
	private String noSlotMsg;
	private String candidateDocPk;
	private String candidateDocPk1;
	private String examName;
	private String ageData;
	private String ageDataFileName;
	private String ageData2;
	private String ageData2FileName;
	private boolean ageDocumentUploaded;
	private boolean ageDocumentUploaded2;
	private String checkAgeAcceptance;
	private String checkAgeAcceptance2;
	private String ageDocumentFileName;
	private String ageDocumentFileName2;
	private String idMarks1;
	private String candidateDocPk2;
	private String candidateDocPk3;
	private String ocdFlagValue;
	private String uploadDocMandatory;
	private String docLabel1;
	private String docLabel2;
	private boolean dataFound;
	private String showDocFirst;
	private String showDocSecond;
	// niraj
	private String assque1;
	private String assque2;
	private String assque3;

	private String action;
	private String audittrail;

	private String religionBeliefOthers;
	private String showDocThirteen;
	private String fromDate;
	private String toDate;

	private String preferredTestDate1;
	private String preferredTestDate2;
	private String preferredTestDate3;
	private Integer candidateStatusId;
	private Integer candidateListSize;
	private String degreeTypeVal;
	private String testFlag;
	private String saveFlag;
	private String stageUpdate;
	private String candidateApproveRejectMessage;
	private Integer educationalDetailsListSize;

	private String regFormSubmitedDate;

	private Boolean postGraduationActive;
	private String postGraduateValue;
	private String postGraduateValueDesc;
	private String postGraduateOtherField;
	private String serverSideErrorMessage;

	private String userNameForGraduationDetails;
	private String disabledFlagValue;

	private String testCenterVal;

	private String showTestCenterFirst;
	private String showTestCenterSecond;

	private String showTestDateFirst;
	private String showTestdateSecond;
	private String listDisplayFlag;
	private String initials;
	private String pstmMandatory;
	private String menuKey;
	private boolean educationDetailsDisplayFlag;
	private boolean testDatesDisplayFlag;
	private byte[] photoByte;
	private byte[] signByte;

	private boolean additionalDetailsDisplayFlag;
	private String candidateDetailsSuccMsg;
	private String candidateDetailsSuccFlag;
	private String educationDetailsDetailsHeaderFlag;

	private String testCenterSuccMsg;
	private String testCenterSuccFlag;
	private String districtOtherFlag;
	private String altDistrictOtherFlag;

	private String fieldsEnableFlag;
	private StipendDetailsBean stipendDetailsBean;
	private FormEducationDetailsBean formEducationDetailsBean;

	private String dbtVal;
	private String degreeValForm;
	private String universityVal;
	private String yearValForm;
	private String interviewVal;
	private String fieldOfIntrestVal;
	private String areaOfProjectVal;
	private String interviewLocation;

	private OtherDetailsBean otherDetailsBean;

	private String jrfVal;
	
	private Boolean phdFlag;
	private Boolean bEdFlag;
	private Boolean shastraFlag;
	private String phdSpecialization;
	private String shastraSubject;

	private String bitpTrainingVal;
	private String bitpTrainingCompName;
	private String bitpTrainingDispFlag;

	private String degreeValFormDesc;
	private String universityValDesc;
	private String yearValFormDesc;
	private String interviewValDesc;
	private String fieldOfIntrestValDesc;
	private String areaOfProjectValDesc;
	private String interviewLocationDesc;

	private String patternVal;
	private String lastExam;
	private String landline;
	private String alternateLandline;
	private String stdCode;
	private String alternateStdCode;
	private String examPatternDesc;
	private String Spouse;
	private String disabilitypercent;

	private String status;
	private boolean postGradDetailsDisplayFlag;
	private boolean displayPostDetails;

	private String testCenterName;
	private String testTime;
	private String marks;
	private String examStatus;
	private String eligibilityForFieldTest;
	private String testCity;

	private String scoreForFieldTest;
	private String scoreForInterview;
	private String candidateBirthPlace;
	private String fatherBirthPlace;
	private String motherTongue;
	private String idMarks;
	
	//private String Subcaste;
	private String ComCertificateNumber;
	private String DesignationIssuingAuthority;
	private String DateOfCertificate;
	private String addDegreeCourse;
	private String docVerify1;// preview doc verify status
	private boolean SubjectInEducation;
	private int firstDate;
	private int hscDate;
	private int diplomaDate;
	private int graduationDate;
	private int pgDate;
	private String community;
	private String parentName;
	private String JasperString;
	private String knowmstsc;
	private String governmentJoinDate;
	private String scribeName;
	private String scribeDt;
	private String scribeQualification;
	private String skillTest;

	private String appNoDomCert;
	private String appNoForCat;
	
	private String academicAward;
	private String advertisement;
	private String appliedInPast;
	private String yearsOfApply;
	private String reasonForNotJoining;
	private String otherInfo;
	//ref1 start
	private String ref1Name;
	private String ref1Desig;
	private String ref1IsAcademician;
	private String ref1Add1;
	private String ref1Add2;
	private String ref1State;
	private String ref1District;
	private String ref1City;
	private String ref1Pincode;
	private String ref1Contact;
	private String ref1EmailAddress;
	private String ref2EmailAddress;
	
	//ref2 start
	private String ref2Name;
	private String ref2Desig;
	private String ref2IsAcademician;
	private String ref2Add1;
	private String ref2Add2;
	private String ref2State;
	private String ref2District;
	private String ref2City;
	private String ref2Pincode;
	private String ref2Contact;
	private String stmtOfPurpose;
	
	private String totalYearExp;
	private String yesNoExp;
	private String sponsor;
	
	private  Users loggedInUser;
	private String exServiceMen;
	private String dischargeDate;
	private String ppoNumber;
	private String commCertYesNo;
	private String subCaste;
	private String issueAuthCommCert;
	private String commCertNo;
	private String commCertPlace;
	private String commIssueDate;
	private String disableYesNo;
	private String disableType;
	private String disablityPercent;
	
	private String photoIdProofNo;
	private String orgName;
	private String isGovtService;
	private String currentDesig;
	private String placeOfWork;
	
	private String photoIDProof1;
	private String photoIDProof1Val;
	
//	private String permAddressOnProof;
//	private String corrAddressOnProof;
	
	public CandidateBean() {
		personalDetailsBean = new PersonalDetailsBean();
		addressBean = new AddressBean();
	}

	public void copyPermenantToComm() {
		addressBean.setAlternateAddressFiled1(addressBean.getAddressFiled1());
		addressBean.setAlternateAddressFiled2(addressBean.getAddressFiled2());
		addressBean.setAlternateAddressFiled3(addressBean.getAddressFiled3());
		addressBean.setAlternateAddressFiled4(addressBean.getAddressFiled4());

		addressBean.setAlternateCity(addressBean.getCityName());
		addressBean.setAlternatePinCode(addressBean.getPinCode());
		addressBean.setAlternateCityother(addressBean.getCityNameother());
		addressBean.setOtherAlternateStateField(addressBean.getOtherStateFiled());
		addressBean.setAltTelephoneNo1(addressBean.getTelephoneNo1());
		addressBean.setAltTelephoneNo2(addressBean.getTelephoneNo2());
	//	corrAddressOnProof=permAddressOnProof;
		altTelephoneNo1=telephoneNo1;
		altTelephoneNo1=telephoneNo2;
		altDistrictValOthers=districtValother;
		altCountryVal=countryVal;
		altStateVal=stateVal;
		altDistrictVal=districtVal;
		altOtherDistrict=otherDistrict;
		unionTerritoryVal = altUnionTerritoryVal;
		talukaVal = altTalukaVal;
		talukaField = altTalukaField;
		landline = alternateLandline;
		stdCode = alternateStdCode;
	}

	private Map<Integer, String> photoIdProof = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> motherTongueList = new LinkedHashMap<Integer, String>();
	private List<String> titleList = new ArrayList<String>();
	private List<String> testCenterPrefList = new ArrayList<String>();
	private List<CandidateRelationBean> candiateRelationDetailsList = new ArrayList<CandidateRelationBean>();
	private List<WorkExperienceDetailsBean> workExperienceDetailsList;
	private List<String> yearOfExperienceList = new ArrayList<String>();
	private List<WorkExperienceDetailsBean> wkExperienceDtlsList = new ArrayList<WorkExperienceDetailsBean>();
	private List<String> monthList = new ArrayList<String>();

	private List<String> yearExperienceList = new ArrayList<String>();
	private List<String> monthExperienceList = new ArrayList<String>();

	private Map<Integer, String> degreeMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> universityMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> yearMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> districtListMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> altdistrictListMap = new LinkedHashMap<Integer, String>();
	private List academicDetailsList = new ArrayList();
	private Map<Integer, String> candidateStatusList = new LinkedHashMap<Integer, String>();
	private List<EducationDetailsBean> educationDtlsList = new ArrayList<EducationDetailsBean>();
	private List<CandidateApproveRejectBean> listOfRegisteredCandidate = new ArrayList<CandidateApproveRejectBean>();
	private Map<Integer, String> resultStatusList = new LinkedHashMap<Integer, String>();
	private List<AdditionalDetailsBean> additionalDtlsList = new ArrayList<AdditionalDetailsBean>();
	private List<EducationDetailsBean> saveEducationDtlsList = new ArrayList<EducationDetailsBean>();

	private Map<Integer, String> postGraduateMasterMap = new LinkedHashMap<Integer, String>();

	private Map<Integer, String> cityList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> altCityList = new LinkedHashMap<Integer, String>();
	private Map<String, String> sscSslcList = new LinkedHashMap<String, String>();
	private Map<Integer, String> hscUniversityList = new LinkedHashMap<Integer, String>();
	private List<EducationDetailsBean> saveAddQualEducationDtlsList = new ArrayList<EducationDetailsBean>();
	private List<EducationDetailsBean> addQualEducationDtlsList = new ArrayList<EducationDetailsBean>();
	private Map<String, String> hscItiList = new LinkedHashMap<String, String>();
	private Map<String, String> diplomaUniversityList = new LinkedHashMap<String, String>();
	private Map<String, String> underGraduateList = new LinkedHashMap<String, String>();
	private Map<String, String> postGraduateList = new LinkedHashMap<String, String>();
	private Map<Integer, String> disabilityTypeList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> bloodgroupList = new LinkedHashMap<Integer, String>();
	private Map<String, String> addDiplomaUniversityList;
	private Map<String, String> addUnderGraduateList;
	private Map<String, String> addPostGraduateList;
	private Map<String, String> emptyMapforUniversityBoard;
	private Map<Integer, String> bscMajSubList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> baMajSubList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> subjectGroupList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> SubcategoryList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> DesignationcategoryList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> DesignationscategoryList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> sscUnivList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> hscUnivList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> UGUnivList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> PGUnivList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> DipUnivList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> addexaminationList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> communityList = new LinkedHashMap<Integer, String>();
	private List<AdditionalAcademicDetailsBean> addAcademicDtlsList = new ArrayList<AdditionalAcademicDetailsBean>();
	private List<AdditionalAcademicDetailsBean> saveAddAcademicDtlsList = new ArrayList<AdditionalAcademicDetailsBean>();
	private List<AdditionalDetailsBean> crimeList = new ArrayList<AdditionalDetailsBean>();
	private List<String> postNameList = new ArrayList<String>();
	private List<CandidateBean> uploadList = new ArrayList<CandidateBean>();
	private List<CandidateBean> candidatePostList = new ArrayList<CandidateBean>();
	private Map<String, String> candidateAgeQuotacheckList;
	private Map<Integer, String> sscHscUniversityList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> pgUgUniversityList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> regularParttimeDistanceEdList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> appliedSubjectList = new LinkedHashMap<Integer, String>();
	private List<CandidateBean> prefCenterList;
	private List<PreferredTestCentreBean> preferredTestCenList;// for rms
	private List<PreferredTestCentreBean> preferredTestCenList1;// for postal
	private List<PreferredTestCentreBean> preferredTestCenList2;// for
	private List<String> discliplineSelectedList = new ArrayList<String>();
	private List<String> pwd1List = new ArrayList<String>();
	private List<String> pwd2List = new ArrayList<String>();
	private Map<Integer, String> markingSystemList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> nativityList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> nativityListOther = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> postPreferenceList = new LinkedHashMap<Integer, String>();

	private List<String> testCenterList = new ArrayList<String>();// for rms
	private List<String> testCenterList1 = new ArrayList<String>();// for postal
	private List<String> testCenterList2 = new ArrayList<String>();// for

	private Map<Integer, String> scribe_flagList = new LinkedHashMap<Integer, String>();

	private Map<Integer, String> nationalityList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> subjectList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> geCertificateList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> genderList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> countryList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> stateList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> stateListWoTamil = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> utList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> districtList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> altStateList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> altUtList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> altDistrictList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> categoryList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> altTalukaList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> talukaList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> phdStatusList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> AgencyList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> AgencyNewList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> PSTMList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> PGequivList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> SLETNETList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> phdTNList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> MotherTongueList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> phdsletQstnList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> degreeType = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> degree = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> resultOfGraduation = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> degreeNameModernGradList=new TreeMap<>();
	private Map<Integer, String> degreeNameTradPostGardList=new TreeMap<>();
	private Map<Integer, String> degreeNameModernPostGardList=new TreeMap<>();
	private Map<Integer, String> degreeNamePGList = new TreeMap<>();
	private Map<Integer, String> degreeNameTradGardList=new TreeMap<>();
	private Map<Integer, String> studiedStateList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> medOfInstructionList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> graduationFromUniv = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> marks_gradeList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> sslcTamilList = new TreeMap<Integer, String>();
	private Map<Integer, String> degreeEdu_subjectList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> degree_subjectList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> diploma_subjectList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> adddiploma_subjectList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> examinationList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> master_subjectList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> issue_authorityList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> idTypeList = new LinkedHashMap<Integer, String>();
	private List<String> handicappedList = new ArrayList<String>();
	private Map<Integer, String> academicDetailsMap = new LinkedHashMap<Integer, String>();
	private List<String> yearList = new ArrayList<String>();
	private Map<Integer, String> physicalDisabilityList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> issuingAuthorityList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> exServicemanList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> exServiceWomanList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> maritalStatusList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> religionBeliefList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> partTimeFullTimeList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> idProofList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> mainSubjectAppliedList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> hscEquivalentList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> hscDipQualList=new TreeMap<>();
	private Map<Integer, String> ugCourseList=new TreeMap<>();
	private Map<Integer, String> pgCourseList=new TreeMap<>();
	private Map<Integer, Map<Integer, String>> sscHscUniList=new TreeMap<Integer, Map<Integer, String>>();
	private Map<Integer, String> professionalList=new TreeMap<>();
	private Map<Integer, String> yesNo = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> cardTypeList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> yesOrNoMstc = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> knowmstcList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> discliplineList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> initialList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> testCenterMasterDetails = new LinkedHashMap<Integer, String>();
	private List<String> errorField;
	private Map<Integer, String> advertList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> refStateList = new LinkedHashMap<Integer, String>();
	private List<Integer> yearsAppliedList = new ArrayList<Integer>();
	private Map<Integer, String> ref1DistrictListMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> ref2DistrictListMap = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> ref1DistrictList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> ref2DistrictList = new LinkedHashMap<Integer, String>();
	private Map<Integer, String> parentAndGuardianList = new LinkedHashMap<Integer, String>();
	
	private String regValidUptoDate;
	private int regValidStartYr;
	private String regValidEndDate;
	private int regValidEndYr;
	
	private String widowYesNo;
	private String desWidowCertNo;
	private String widowIssueDate;
	private String widowIssueAuth;
	private String widowDistrict;
	private String widowOtherDistrict;
	private String widowSubDivision;
	private boolean widowCheckbox;
	private String govtDate;
	private String govtAge;
	private String notificationEndDate;
}