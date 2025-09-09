<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java"
	import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<link rel="stylesheet"
	href="css/ui-lightness/jquery-ui-1.8.16.custom.css">
<%@ page language="java" import="com.nseit.generic.util.CommonUtil"%>
<%@ page language="java" import="java.time.ZonedDateTime"%>
<%@page import="java.util.*"%>
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/style.css">
<script src="js/jquery-3.6.3.min.js"></script>
<script src="js/jquery-ui.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/candidateJS/registration.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>
<script type="text/javascript" src="js/moment-precise-range.js"></script>

<script type="text/javascript">
	function errorField() {
		<s:iterator value="errorField">
		$("input[name='<s:property />']").addClass('red-border');
		$("select[name='<s:property />']").addClass('red-border');
		$("#<s:property />").addClass('red-border');
		</s:iterator>
	}
	function errorField1() {
		<s:iterator value="errorField1">
		$("input[name='<s:property />']").addClass('red-border');
		$("select[name='<s:property />']").addClass('red-border');
		$("#<s:property />").addClass('red-border');
		</s:iterator>
	}
	$(document).ready(function() {
		calculateAge();
		$('#dateOfBirth').datepicker('destroy');
		$("#dateOfBirth").datepicker({
			showOn : "button",
			changeMonth : true,
			changeYear : true,
			yearRange : '1966:2007',
			minDate : new Date('Jul 01,1966'),
			maxDate : new Date('Jul 01,2007'),
			buttonImageOnly : true,
			buttonImage : "images/cale-img.png",
			buttonText : "Date of Birth (as per SSLC mark Sheet)",
			dateFormat : 'dd-M-yy',
			onSelect : function(dateText, inst) {
				calculateAge();
			}
		});

		$('#commIssueDate').datepicker('destroy');
		$("#commIssueDate").datepicker({
			showOn : "button",
			changeMonth : true,
			changeYear : true,
			yearRange : '1966:' + new Date().getFullYear(),
			minDate : new Date('Jul 01,1966'),
			maxDate : new Date(),
			buttonImageOnly : true,
			buttonImage : "images/cale-img.png",
			buttonText : "Community Certificate Issuing Date",
			dateFormat : 'dd-M-yy',
			onSelect : function(dateText, inst) {
			}
		});

		$('#dischargeDate').datepicker('destroy');
		$("#dischargeDate").datepicker({
			showOn : "button",
			changeMonth : true,
			changeYear : true,
			yearRange : '1966:<s:property value = "dischargeEndYear" />',
			minDate : new Date('Jul 01,1966'),
			maxDate : new Date('<s:property value = "dischargeEndDate" />'),
			buttonImageOnly : true,
			buttonImage : "images/cale-img.png",
			buttonText : "Date of Discharge / Probable Discharge",
			dateFormat : 'dd-M-yy',
			onSelect : function(dateText, inst) {
			}
		});

		$('#widowIssueDate').datepicker('destroy');
		$("#widowIssueDate").datepicker({
			showOn : "button",
			changeMonth : true,
			changeYear : true,
			yearRange : '1966 : c',
			minDate : new Date('Jul 01,1966'),
			maxDate : new Date(),
			buttonImageOnly : true,
			buttonImage : "images/cale-img.png",
			buttonText : "Destitute Widow Certificate Issuing Date",
			dateFormat : 'dd-M-yy',
			onSelect : function(dateText, inst) {
			}
		});

	});

	function ageMatrix() {
		debugger;
		var commCertYesNo = $('input[name=commCertYesNo]:checked').val();
		var categoryvalue = '';
		if (commCertYesNo == 7) {
			categoryvalue = 7;
		} else {
			categoryvalue = $("#community option:selected").val();
		}
		var disabilityVal = $("#disableYesNo option:selected").val();
		var exServicemenVal = $("#exServiceMen option:selected").val();
		var coursesVal = 1;

		var destiWidow = $("#widowYesNo option:selected").val();
		var dataString = "categoryvalue=" + categoryvalue + "&disabilityVal="
				+ disabilityVal + "&exServicemenVal=" + exServicemenVal
				+ "&widowYesNo=" + destiWidow + "&coursesVal=1";
		console.log("ageMatrix dataString :" + dataString);
		if (disabilityVal != null && disabilityVal != ""
				&& disabilityVal != undefined
				&& disabilityVal != "Select Are you differently abled?") {
			$.ajax({
				url : "RegistrationAction_getAgeMatrixDetails.action",
				async : false,
				data : dataString,
				error : function(ajaxrequest) {
					console.log('Error refreshing. Server ageMatrix Response: '
							+ ajaxrequest.responseText);
				},
				success : function(responseText) {
					responseText = $.trim(responseText);
					if (responseText != "null" && responseText.length > 0) {
						console.log("ageMatrix responseText minDate:"
								+ responseText);
						var minDate = responseText;
						console.log("mindate in response" + minDate);
						setMinDateIntoDatePicker(minDate)

					}
				}

			});
		}
	}

	function resetDateAgeAsOn() {
		$("#dateOfBirth").val("");
		$("#ageInYears").val("");
		$("#ageInMonths").val("");
		$("#ageInDays").val("");
	}

	function setMinDateIntoDatePicker(newDate) {
		var newMinDate = new Date();
		var a = new Date();
		var isIE = /*@cc_on!@*/false || !!document.documentMode;
		var isFirefox = typeof InstallTrigger !== 'undefined';
		if (isIE == true) {
			if (newDate.indexOf('-')) {
				a = newDate.split("-");
				newMinDate = a[1] + " " + a[0] + " " + a[2];
			} else {
				newMinDate = newdate;
			}
		} else if (isFirefox == true) {
			if (newDate.includes("-")) {
				a = newDate.split("-");
				newMinDate = a[1] + " " + a[0] + " " + a[2];
			} else {
				newMinDate = newdate;
			}
		} else {
			if (newDate.includes("-")) {
				a = newDate.split("-");
				newMinDate = a[1] + " " + a[0] + " " + a[2];
			} else {
				newMinDate = newdate;
			}
		}

		var dob = $("#dateOfBirth").val();
		var oldMinDate = $("#dateOfBirth").datepicker("option", "minDate");
		$("#dateOfBirth").datepicker("option", "minDate", new Date(newMinDate));
		var newMinDate = $("#dateOfBirth").datepicker("option", "minDate");
		console.log("ageMatrix oldMinDate:" + oldMinDate + " newMinDate:"
				+ newMinDate);

		if (dob == null || dob == "" || dob == undefined) {

		} else {
			var diff = dates.compare(newMinDate, oldMinDate);
			if (diff > 0) {
				console.log("ageMatrix reset dob diff:" + diff);
				$("#dateOfBirth").datepicker("option", "minDate", newMinDate);
				//$("#dateOfBirth").val("");
				//$("#age").val("");
				//$("#ageInMonths").val("");
				//$("#ageInDays").val("");
			} else {
				$("#dateOfBirth").datepicker("option", "minDate", newMinDate);
			}
		}
	}

	function calculateAge() {
		var dateString1 = '<s:property value="ageAsOn"/>';
		var momentObj1 = moment(dateString1, 'DD.MM.YYYY');
		var ageAsOn = momentObj1.format('YYYY-MM-DD');
		var startDate1 = ageAsOn.split("-").map(Number);
		if (startDate1[1] != "" && startDate1[1] != null) {
			var startDate = [ startDate1[0], startDate1[1] - 1, startDate1[2] ];
		}

		var dateString2 = $("#dateOfBirth").val();
		var momentObj2 = moment(dateString2, 'DD-MMM-YYYY');
		var dateOfBirth = momentObj2.format('YYYY-MM-DD');
		var endDate = dateOfBirth.split("-").map(Number);
		if (endDate[1] != "" && endDate[1] != null) {
			var endDate2 = [ endDate[0], endDate[1] - 1, endDate[2] ];
		}
		var ageAsOn = moment(startDate);
		var dateOfBirth = moment(endDate2);

		var diff = moment.preciseDiff(ageAsOn, dateOfBirth, true);

		if (dateString1 != "" && dateString2 != "") {
			$("#ageInYears").val(diff.years);
			$("#ageInMonths").val(diff.months);
			$("#ageInDays").val(diff.days);
		}
	}
</script>

<style type="text/css">
header, .subNavBg {
	display: none !important;
}

.banner {
	height: 85px;
}

.actionMessage li {
	margin-left: 30px;
}

.actionMessage li:first-child {
	display: none;
}

ul.actionMessage br {
	display: none;
}

.login-container {
	padding-top: 20px !important;
}

#txtCaptcha {
	background-image: url(images/captcha-gradient.png);
	text-align: center;
	border: none;
	font-family: 'Roboto Medium';
	height: 50px;
	line-height: 50px;
	float: left;
	width: 200px;
	font-size: 37px;
	color: #333;
}

.header, .footer {
	display: none;
}

.error-massage2-text {
	padding: 0 3px;
}

.error-massage2-text ul li {
	font-size: 11.5px !important;
}

.loginDetails {
	margin-bottom: 0px !important;
}

.header {
	display: none;
}

.custBk {
	background: #fff !important;
	box-shadow: 0 0px 10px rgba(0, 0, 0, 0.2);
	border-radius: 5px;
}
</style>

<div class="container" id="container">
	<div class="main-body">
		<div class="container" role="main">
			<%
				String prodServer = ConfigurationConstants.getInstance()
						.getPropertyVal(GenericConstants.Is_this_Live_Server);
				if (prodServer != null && !prodServer.equals("")) {
					if (prodServer.equals("N")) {
			%>
			<div class="clear"></div>
			<div class="text-center mt20">
				<img src="images/demo_test.gif" alt="This is Demo/Test Instance" />
			</div>
			<%
				}
				}
			%>
			<div class="row">
				<div class="col-md-12 loginDetails custBk">
					<img src="images/logo.png" class="logo img-responsive" />
				</div>
			</div>
			<div class="row">
				<div class="col-sm-12 loginBox registrationBox">
					<div class="row rowMargin0">
						<div class="col-sm-12 rightDiv">
							<s:form id="frmFirstLoginChangePassword"
								action="LoginAction_input.action" method="post"
								autocomplete="off">
								<s:hidden name="generatedOtp" id="generatedOtp"
									autocomplete="false" />
								<s:hidden name="emailAddressHid" id="emailAddressHid" />
								<s:hidden name="mobileNoHid" id="mobileNoHid" />
								<s:hidden name="newmobileNo" id="newmobileNo" />
								<s:hidden name="confirmPasswordHid" id="confirmPasswordHid" />
								<s:hidden name="io" id="io" value="0" />
								<s:hidden name="answerHid" id="answerHid" />
								<s:hidden name="newPasswordHid" id="newPasswordHid" />
								<s:hidden name="questionHid" id="questionHid" />
								<s:hidden name="verfiedStatus" id="verfiedStatus" />
								<s:hidden name="verfiedEmail" id="verfiedEmail" />
								<s:hidden name="generatedEmailOtp" id="generatedEmailOtp" />
								<s:hidden name="txtCaptchaHid" id="txtCaptchaHid" />
								<s:hidden name="registeredCaptchaHid" id="registeredCaptchaHid" />
								<s:hidden name="signupFormShow" id="signupFormShow" />
								<input type="hidden" name="hidCaptchaID"
									value="<%=session.getId()%>" />

								<%
									Map<Integer, List<Long>> dateWindowMap = ConfigurationConstants.getInstance().getDateWindowMap();
										List<Long> dateList = dateWindowMap.get(1);
										Long startDate = dateList.get(0);
										Long endDate = dateList.get(1);
										long today = ZonedDateTime.now().toInstant().toEpochMilli();
										if ((today == startDate || today > startDate) && (today == endDate || today < endDate)) {
								%>

								<div class="pageTitle text-center mt20">Online Application
									Registration for Prosthetic Craftsman 2025</div>
								<div class="row">
									<div class="col-sm-12">
										<h3 class="secondH3" title="Sign Up Form">
											Sign Up Form
											<%-- <s:text name="header.signUpForm"></s:text> --%>
										</h3>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-12"></div>
								</div>
								<div class="row">
									<div class="col-sm-12">
										<div id="error-massage3" style="display: none"
											class="error-massage">
											<div class="error-massage-text">
												<ul id="error-ul3">
												</ul>
											</div>
										</div>
										<s:if test="hasActionMessages()">
											<div id="error-massage4" class="error-massage">
												<div class="error-massage-text"
													style="margin: 0; margin-left: -26px; padding: 0;">
													<ul style="margin: 1; margin-left: -7px; padding: 0;"
														id="error-ul3">
														<s:actionmessage escape="false" />
													</ul>
												</div>
											</div>
										</s:if>
									</div>
								</div>
								<div class="row mb10">
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Post Applying for <s:text
													name="login.courses" />
												<%-- <span class="manadetory-fields">*</span> --%> <br />
											</label>
											<s:textfield id="courses" name="courses" readonly="true"
												cssClass="form-control" value="Prosthetic Craftsman"
												onpaste="return false;" ondragstart="return false;"
												ondrop="return false;" />
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Name <%-- <s:text name="login.candidateName" />  --%>
												<span class="manadetory-fields">*</span>
											</label>
											<s:textfield id="candidateName" name="candidateName"
												cssClass="form-control" size="100"
												onkeypress=" return alphabetSpaceDotApostrophe(event);"
												onpaste="return false;" ondragstart="return false;"
												ondrop="return false;" maxlength="100" />
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Nationality <%-- <s:text
													name="login.nationality" />  --%> <span
												class="manadetory-fields">*</span></label>
											<s:select list="nationalityList" name="nationVal"
												headerKey="" headerValue="Select Nationality"
												id="nationality" value="%{nationVal}"
												cssClass="form-control" />
										</div>
									</div>
								</div>
								<div class="row mb10">
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Gender <%-- <s:text
													name="applicationForm.maritalStatus" /> --%> <span
												class="manadetory-fields">*</span></label>
											<s:select list="genderList" name="gender"
												headerValue="Select Gender" headerKey=""
												cssClass="form-control"
												onchange="showWidowYesNo();genderPrompt();"
												value="%{gender}" id="gender" />
										</div>
									</div>
								</div>

								<!-- ...2nd line... -->
								<div class="row mb10">
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Are you an Ex-Servicemen
												<span class="manadetory-fields">*</span>
											</label>
											<s:select list="yesNo" name="exServiceMen" id="exServiceMen"
												value="%{exServiceMen}"
												headerValue="Select Are you an Ex-Servicemen" headerKey=""
												cssClass="form-control"
												onchange="ageMatrix();resetDateAgeAsOn();showHideExServiceMan();"></s:select>
										</div>
									</div>
									<div class="col-sm-4" id="dischargeDiv">
										<div class="form-group dateInput">
											<label class="control-label">Date of Discharge /
												Probable Discharge <%-- <s:text name="applicationForm.dateOfBirth" /> --%>
												<span class="manadetory-fields">*</span>
											</label>
											<s:textfield id="dischargeDate" name="dischargeDate"
												readonly="true" onkeypress="return dateFormatCheck(event);"
												cssClass="form-control" onpaste="return false;" ondragstart="return false;"
													ondrop="return false;">
											</s:textfield>
										</div>
										<div class="clear"></div>
									</div>
									<div class="col-sm-4" id="ppoNumberDiv">
										<div class="form-group">
											<label class="control-label">PPO Number <%-- <s:text name="login.ppoNumber" />  --%>
												<span class="manadetory-fields">*</span></label>
											<s:textfield id="ppoNumber" name="ppoNumber"
												value="%{ppoNumber}" onpaste="return false;" ondragstart="return false;"
													ondrop="return false;"
												onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
												cssClass="form-control" maxlength="50">
											</s:textfield>
										</div>
									</div>
								</div>

								<!-- ....3rd line... -->
								<div class="row mb10">
									<div class="col-sm-4" id="communityDiv">
										<div class="form-group">
											<label class="control-label">Do you have community
												certificate issued by Tamil Nadu Government? <%-- <s:text
													name="login.community" />  --%> <span
												class="manadetory-fields">*</span>
											</label>
											<div class="radioBtn">
												<s:radio list="yesNo" name="commCertYesNo"
													value="%{commCertYesNo}" id="commCertYesNo"
													label="commCertYesNo"
													onchange="ageMatrix();resetDateAgeAsOn();disableCategoryField();"
													cssStyle="display: inline-block; max-width: 50%;" />
											</div>
										</div>
									</div>
									<div class="col-sm-4" id="chechCommunity">
										<div class="form-group">
											<label class="control-label">Community <%-- <s:text
													name="login.community" /> --%> <span
												class="manadetory-fields">*</span></label> <br />
											<s:select label="community" name="community" id="community"
												list="categoryList" value="%{community}"
												headerValue="Select Community" headerKey=""
												cssClass="form-control"
												onchange="disableCategoryField();resetDateAgeAsOn();hideFieldsOnOC();ageMatrix();" />
										</div>
									</div>
									<div class="col-sm-4" id="generalCommunity"
										style="display: none">
										<div class="form-group">
											<label class="control-label"> Community <%-- <s:text
													name="login.category" /> --%> <span
												class="manadetory-fields">*</span></label>
											<s:textfield name="categoryVal1" value="OC"
												cssClass="form-control" readonly="true" />
										</div>
									</div>
								</div>

								<div id="dependentCommCertDiv">
									<!-- ....4th row... -->
									<div class="row mb10">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Sub Caste <%-- <s:text
													name="login.candidateName" /> --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:textfield id="subCaste" name="subCaste"
													cssClass="form-control" size="50"
													onkeypress=" return alphabetsOnly(event);"
													onpaste="return false;" ondragstart="return false;"
													ondrop="return false;" maxlength="50" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Issuing Authority of
													Community Certificate <%-- <s:text name="login.candidateName" /> --%>
													<span class="manadetory-fields">*</span>
												</label>
												<s:textfield id="issueAuthCommCert" name="issueAuthCommCert"
													cssClass="form-control" size="100"
													onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
													onpaste="return false;" ondragstart="return false;"
													ondrop="return false;" maxlength="100" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Community Certificate
													Number <%-- <s:text name="login.candidateName" />  --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:textfield id="commCertNo" name="commCertNo"
													cssClass="form-control" size="20"
													onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
													onpaste="return false;" ondragstart="return false;"
													ondrop="return false;" maxlength="20" />
											</div>
										</div>
									</div>

									<!-- ...5th row... -->
									<div class="row mb10">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Community Certificate
													Place of Issue <%-- <s:text name="login.candidateName" />  --%>
													<span class="manadetory-fields">*</span>
												</label>
												<s:textfield id="commCertPlace" name="commCertPlace"
													cssClass="form-control" size="50"
													onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
													onpaste="return false;" ondragstart="return false;"
													ondrop="return false;" maxlength="50" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group dateInput">
												<label class="control-label mb8">Community
													Certificate Issuing Date <%-- <s:text
													name="applicationForm.commIssueDate" /> --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:textfield id="commIssueDate" name="commIssueDate"
													readonly="true" onkeypress="return dateFormatCheck(event);"
													cssClass="form-control">
												</s:textfield>
											</div>
										</div>
									</div>
								</div>

								<!-- ...6th row... -->
								<div class="row mb10">
									<div class="col-sm-4" id="disabilityDiv">
										<div class="form-group">
											<label class="control-label">Are you differently
												abled ? <%-- <s:text name="login.community" />  --%> <span
												class="manadetory-fields">*</span>
											</label> <br />
											<s:select label="disableYesNo" name="disableYesNo"
												id="disableYesNo"
												headerValue="Select Are you differently abled?" list="yesNo"
												value="%{disableYesNo}" cssClass="form-control" headerKey=""
												onchange="ageMatrix();resetDateAgeAsOn();showDisType()" />
										</div>
									</div>

									<div class="col-sm-4" id="disableTypeDiv">
										<div class="form-group">
											<label class="control-label disability"> Differently
												Abled Category <span class="manadetory-fields">*</span>
											</label>
											<s:select list="personDisabilityList" name="disableType"
												label="Name"
												headerValue="Select  Differently Abled Category"
												headerKey="" cssClass="form-control" id="disableType"
												value="%{disableType}" />
										</div>
									</div>
									<div class="col-sm-4" id="disablityPercentDiv">
										<div class="form-group">
											<label class="control-label">Percentage of Disability
												<%-- <s:text name="applicationForm.dateOfBirth" />  --%> <span
												class="manadetory-fields">*</span>
											</label>
											<s:textfield id="disablityPercent" name="disablityPercent"
												onkeypress="return numbersonly(event);"
												cssClass="form-control">
											</s:textfield>
										</div>
									</div>

									<div id="diffAbledChkBoxDiv">
										<div class="col-sm-4 ">
											<s:checkbox name="diffAbledChkBox" id="diffAbledChkBox" />
											<span class="font12 english_ins"> I agree to provide
												Differently Abled Person Certificate at the time of
												Certificate Verification </span>
										</div>
									</div>

								</div>

								<div class="row mb10" id="widowDiv">
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Are you a Destitute
												Widow? <%-- <s:text name="login.community" />  --%> <span
												class="manadetory-fields">*</span>
											</label> <br />
											<s:select label="widowYesNo" name="widowYesNo"
												id="widowYesNo"
												headerValue="Select Are you a Destitute Widow?" list="yesNo"
												cssClass="form-control" headerKey=""
												onchange="ageMatrix();resetDateAgeAsOn();showWidowFields()" />
										</div>
										<%-- <div id="widowChkBoxDiv">
											<s:checkbox name="widowCheckbox" id="widowCheckbox" />
											<span class="font12 english_ins"> I agree to provide
												Destitute Widow Certificate at the time of Certificate
												Verification </span>
										</div> --%>
									</div>
								</div>
								<div id="dependentWidowDiv">
									<div class="row mb10">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Destitute Widow
													Certificate Number <%-- <s:text name="login.candidateName" /> --%>
													<span class="manadetory-fields">*</span>
												</label>
												<s:textfield id="desWidowCertNo" name="desWidowCertNo"
													cssClass="form-control" size="50"
													onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
													onpaste="return false;" ondragstart="return false;"
													ondrop="return false;" maxlength="50" />
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group dateInput">
												<label class="control-label">Destitute Widow
													Certificate Issuing Date <%-- <s:text name="applicationForm.commIssueDate" /> --%>
													<span class="manadetory-fields">*</span>
												</label>
												<s:textfield id="widowIssueDate" name="widowIssueDate"
													readonly="true" onkeypress="return dateFormatCheck(event);"
													cssClass="form-control">
												</s:textfield>
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Issuing Authority of
													Destitute Widow Certificate <%-- <s:text name="login.candidateName" /> --%>
													<span class="manadetory-fields">*</span>
												</label>
												<s:textfield id="widowIssueAuth" name="widowIssueAuth"
													cssClass="form-control" size="50"
													onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
													onpaste="return false;" ondragstart="return false;"
													ondrop="return false;" maxlength="50" />
											</div>
										</div>
									</div>

									<div class="row mb10">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Destitute Widow
													Certificate Issued District <%-- <span class="tamil"> <s:text name="applicationForm.stateUnion" /> </span> --%>
													<span class="manadetory-fields">*</span>
												</label>
												<s:select list="districtList" id="widowDistrict"
													name="widowDistrict" cssClass="form-control"
													onchange="showWidowDistOther();" headerKey=""
													headerValue="Select Destitute Widow Certificate Issued District"></s:select>
											</div>
										</div>
										<div class="col-sm-4" id="widowOtherDiv">
											<div class="form-group">
												<label class="control-label">Destitute Widow
													Certificate Issued Other District <%-- <s:text name="login.candidateName" /> --%>
													<span class="manadetory-fields">*</span>
												</label>
												<s:textfield id="widowOtherDistrict"
													name="widowOtherDistrict" cssClass="form-control" size="50"
													onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
													onpaste="return false;" ondragstart="return false;"
													ondrop="return false;" maxlength="50" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Destitute Widow
													Certificate Issued Sub Division <%-- <s:text name="login.candidateName" /> --%>
													<span class="manadetory-fields">*</span>
												</label>
												<s:textfield id="widowSubDivision" name="widowSubDivision"
													cssClass="form-control" size="50"
													onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
													onpaste="return false;" ondragstart="return false;"
													ondrop="return false;" maxlength="50" />
											</div>
										</div>
									</div>
								</div>
								<!-- ...7th row.. -->
								<div class="row mb10">
									<div class="col-sm-4" id="">
										<div class="form-group dateInput">
											<label class="control-label mb8">Date of Birth (as
												per SSLC mark Sheet) <%-- <s:text
													name="applicationForm.dateOfBirth" />  --%> <span
												class="manadetory-fields">*</span>
											</label>
											<s:textfield id="dateOfBirth" name="dateOfBirth"
												readonly="true" cssClass="form-control">
											</s:textfield>
										</div>
										<div class="clear"></div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Age as on <s:property
													value="%{ageAsOn}" /> <br /> <%-- <s:text name="login.ageason" /> --%>
											</label>
											<div class="clear"></div>
											<div class="col-sm-4 col-xs-4 padleft0">
												<s:textfield id="ageInYears" name="ageInYears"
													readonly="true" onkeypress="return restrictEnter(event);"
													cssClass="form-control">
												</s:textfield>
												<span class="note">Years</span>
											</div>
											<div class="col-sm-4 col-xs-4 padleft0">
												<s:textfield id="ageInMonths" name="ageInMonths"
													readonly="true" onkeypress="return restrictEnter(event);"
													cssClass="form-control">
												</s:textfield>
												<span class="note">Months</span>
											</div>
											<div class="col-sm-4 col-xs-4 padleft0">
												<s:textfield id="ageInDays" name="ageInDays" readonly="true"
													onkeypress="return restrictEnter(event);"
													cssClass="form-control">
												</s:textfield>
												<span class="note">Days </span>
											</div>
											<s:hidden id="ageHidden" value="%{registrationBean.age}"></s:hidden>
											<s:hidden id="ageInMonthsHidden"
												value="%{registrationBean.ageInMonths}"></s:hidden>
											<s:hidden id="ageInDaysHidden"
												value="%{registrationBean.ageInDays}"></s:hidden>
										</div>
									</div>
								</div>
								<div class="row mb10">
									<div class="col-sm-3">
										<div class="form-group">
											<label class="control-label">Mobile Number <%-- <s:text name="applicationForm.mobileNo" />  --%>
												<span class="manadetory-fields">*</span>
											</label>
											<div class="row">
												<div class="col-sm-2 col-xs-2" style="padding: 0 0 0 10px;">
													<p style="margin-top: 8px;">+91</p>
												</div>
												<div class="col-sm-8 col-xs-8" style="padding: 0 0 0 0;">
													<s:textfield id="mobileNo" name="mobileNo"
														cssClass="form-control" size="10" maxlength="10"
														onkeypress="return numbersonly(event);"
														onpaste="return false;" ondragstart="return false;" ondrop="return false;"
														onmouseenter="isOndropContainsSpecialCharRestrict(this,this.id)"
														autocomplete="anyrandomstring" />
												</div>
											</div>
											<div id="error-massage2" class="error-massage2">
												<div class="error-massage2-text">
													<ul id="error-ul2">
													</ul>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-3 mt10" id="sendOTPMobDiv">
										<input type="button" value="Send OTP"
											class="btn btn-xs btn-block btn-warning" id="sendOTPMobile"
											onClick="this.disabled=true; sendOTPMob();" /> <span
											id="resendSpan" style="display: none">Please wait for
											Resend OTP.</span> <span id="mobregister" style="color: red;"></span>
										<span id="countdownTimerForMob" style="color: red;"></span>
									</div>
									<div class="col-sm-3" id="otpTrMob" style="display: none">
										<div class="form-group">
											<label class="control-label">Enter OTP <span
												class="manadetory-fields">*</span></label>
											<s:textfield id="mobotp" name="mobotp"
												cssClass="form-control WeightBold" size="15" maxlength="6"
												onkeypress="return numbersonly(event);"
												onpaste="return false;" />
											<s:hidden id="verifyMobileOTPFlag" name="verifyMobileOTPFlag"
												value="%{verifyMobileOTPFlag}"></s:hidden>

											<p id="otpSuccess" class="otp-success" style="display: none;">
												<i class="glyphicon glyphicon-ok"></i> Mobile OTP
												Verification Successful.
											</p>
											<p id="otpFailed" class="otp-success" style="display: none;">
												<i class="glyphicon glyphicon-remove"></i> Mobile OTP
												Verification Failed. Please Enter Valid OTP.
											</p>
											<p id="noBlockedMobile" class="otp-success"
												style="display: none;">OTP limit exceeded.</p>
										</div>
									</div>
									<div class="col-sm-3 mt10" id="verifymobileOTP"
										style="display: none">
										<input type="button" value="Verify Mobile"
											class="btn btn-xs btn-block btn-warning"
											onclick="return verifyMobileOTP();" />
									</div>
								</div>
								<div class="row mb10">
									<div class="col-sm-3">
										<div class="form-group">
											<label class="control-label">Email ID <%-- <s:text
													name="applicationForm.emailId" />  --%> <span
												class="manadetory-fields">*</span>
											</label>
											<s:textfield id="emailAddress" name="emailAddress"
												onpaste="return false;"
												cssClass="text-lowercase form-control" size="15"
												maxlength="50" ondragstart="return false;"
												ondrop="return false;" autocomplete="off"
												onkeypress="return alphaNumericwithSplCharEmail(event)"
												oninput="this.value = this.value.toLowerCase()" />
											<div id="error-massage5" style="display: none"
												class="error-massage4">
												<div class="error-massage2-text">
													<ul id="error-ul4">
													</ul>
												</div>
											</div>
										</div>
									</div>
									<div class="col-sm-3 mt10" id="sendOTPEmailDiv">
										<input type="button" value="Send OTP"
											class="btn btn-xs btn-block btn-warning" id="sendOTP"
											onClick="this.disabled=true; sendOTPEmail();" /> <span
											id="resendSpan" style="display: none">Please wait for
											Resend OTP.</span> <span id="emailregister" style="color: red;"></span>
										<span id="countdownTimer" style="color: red;"></span>
									</div>
									<div class="col-sm-3" id="otpTr" style="display: none">
										<div class="form-group">
											<label class="control-label">Enter OTP <span
												class="manadetory-fields">*</span></label>
											<s:textfield id="emailotp" name="emailotp"
												cssClass="form-control WeightBold" size="15" maxlength="6"
												onkeypress="return numbersonly(event);"
												onpaste="return false;" />
											<s:hidden id="verifyemailOTPFlag" name="verifyemailOTPFlag"
												value="%{verifyemailOTPFlag}"></s:hidden>

											<p id="emailotpSuccess" class="otp-success"
												style="display: none;">
												<i class="glyphicon glyphicon-ok"></i> Email OTP
												Verification Successful.
											</p>
											<p id="emailotpFailed" class="otp-success"
												style="display: none;">
												<i class="glyphicon glyphicon-remove"></i> Email OTP
												Verification Failed. Please Enter Valid OTP.
											</p>
											<p id="noBlockedEmail" class="otp-success"
												style="display: none;">OTP limit exceeded.</p>
										</div>
									</div>
									<div class="col-sm-3 mt10" id="verifyemailOTP"
										style="display: none">
										<input type="button" value="Verify Email"
											class="btn btn-xs btn-block btn-warning"
											onclick="return verifyemailOTP();" />
									</div>
								</div>
								<div class="row mb10">
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Set Password <%-- <s:text
													name="register.password" />  --%> <span
												class="manadetory-fields">*</span></label>
											<s:password id="newRegPassword" name="newPassword"
												value="%{newPassword}" onpaste="return false;"
												cssClass="form-control" size="12" maxlength="12"
												ondragstart="return false;"
												onkeyup="CheckPasswordStrength(this.value)"
												ondrop="return false;" autocomplete="new-password"
												onkeypress="return alphanumericForPass(event)" />
											<span id="password_strength"></span> <span
												id="password_suggestion"></span>
											<div id="error-messagePass1" class="error-massage2">
												<div class="error-massage2-text">
													<ul id="error-ulPass1">
													</ul>
												</div>
											</div>
										</div>

									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Confirm your Password <%-- <s:text
													name="forgot.reenterpwd" /> --%> <span
												class="manadetory-fields">*</span></label>
											<s:password id="confirmRegPassword" name="confirmPassword"
												value="%{confirmPassword}" onpaste="return false;"
												cssClass="form-control" size="12" maxlength="12"
												ondragstart="return false;" ondrop="return false;"
												autocomplete="off"
												onkeypress="return alphanumericForPass(event)" />
											<div id="error-messagePass2" class="error-massage2">
												<div class="error-massage2-text">
													<ul id="error-ulPass2">
													</ul>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="row mb10">
									<div class="col-sm-4">
										<div class="form-group mt30">
											<s:label id="txtCaptcha"></s:label>
											<img src="images/refreshCaptcha.png" width="22" height="22"
												onClick="DrawCaptcha()" class="captcharefresh" />
										</div>
									</div>
									<!-- below code added for mobile compatibility issue -->

									<div class="clear visible-xs"></div>
									<!-- 
										above code added for mobile compatibility issue -->
									<div class="col-sm-4">
										<div class="form-group mt30">
											<label class="control-label">Please enter text shown
												in image <%-- <s:text name="login.captcha" />  --%> <span
												class="manadetory-fields"> *</span>
											</label>
											<div class="row">
												<div class="col-sm-9">
													<s:textfield name="captcha"
														onkeypress="return alphaNumeric(event);" maxlength="5"
														cssClass="form-control"
														onpaste="return alphaNumeric(event);" size="20"
														id="registerCaptcha" ondragstart="return false;"
														ondrop="return false;" />
												</div>
											</div>
										</div>
									</div>
								</div>

								<div class="row">
									<div class="col-sm-3 col-sm-offset-3">
										<input type="button" value="Submit" id="submitBtn"
											class="ripple1 btn btn-sm btn-xs btn-warning btn-block"
											title="Submit"
											onclick="this.disabled=true; return validateFirstLoginForm();" />
									</div>
									<div class="col-sm-3">
										<s:submit value="Cancel"
											cssClass="ripple2 btn btn-sm btn-xs btn-default btn-block"
											title="Cancel"></s:submit>
									</div>
								</div>
								<div class="row mt20">
									<div class="col-sm-12">
										<div class="form-group">
											<span class="note">Please give correct Email ID and
												Mobile Number. All future communications will be sent to
												this Email ID and Mobile number only. <s:text
													name="register.emailnote" />
											</span>
										</div>
									</div>
								</div>
								<div class="clear"></div>
								<%
									} else {
								%><div class="loginboxbg">
									<div class="row">
										<div class="col-sm-12 col-xs-12">
											<%
												Map<Integer, List<Long>> dateWindowMap1 = ConfigurationConstants.getInstance().getDateWindowMap();
														List<Long> dateList1 = dateWindowMap1.get(7);
														Long startDate1 = dateList1.get(0);
														Long endDate1 = dateList1.get(1);
														long today1 = ZonedDateTime.now().toInstant().toEpochMilli();
														String endDateString1 = CommonUtil.formatDate(new Date(endDate1),
																GenericConstants.DATE_FORMAT_DEFAULT1);
														String newStartDate = CommonUtil.formatDate(new Date(startDate1),
																GenericConstants.DATE_FORMAT_DEFAULT1);
														String[] endDateTime1 = endDateString1.split(" ");
														String[] startDateFinal = newStartDate.split(" ");
											%>

											<div>
												<p>
													<b style="color: black"> New User creation window is
														not open, New User Creation Window - <%=startDateFinal[1]%>
														hrs on <%=startDateFinal[0]%> to <%=endDateTime1[1]%> hrs
														on <%=endDateTime1[0]%>.
													</b>
												</p>
											</div>

										</div>
									</div>
								</div>
								<%
									}
								%>
							</s:form>
						</div>
					</div>
				</div>
			</div>
			<br>
		</div>
	</div>
	<s:token />

	<div class="clear"></div>
	<div id="overlay1" class="web_dialog_overlay_declr"></div>
	<div class="fullscreen-container" id="dialog1">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<!-- <button type="button" class="close" onclick="HideDialog1()">&times;</button> -->
					<!-- <h2 class="modal-title">Confirmation</h2> -->
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12 text-center">
							<h4 class="pageTitle" id="dynamicContent"></h4>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-6 col-sm-offset-3">
							<input type="button" name="btnCancel" value="OK"
								onclick="HideDialog1()"
								class="ripple1 btn btn-warning btn-block mt10" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="clear"></div>
	<div id="overlay3" class="web_dialog_overlay_declr"></div>
	<div class="fullscreen-container" id="genderConfDialog">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<!-- <button type="button" class="close" onclick="HideDialog()">&times;</button> -->
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12 text-center">
							<h4 class="pageTitle" id="genderConfTD"></h4>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-3 col-sm-offset-3">
							<input type="button" name="accept" id="accept" value="OK"
								class="ripple1 btn btn-warning btn-block mt10"
								onClick="hideGenderConfDialog(1);" />
						</div>
						<div class="col-sm-3">
							<input type="button" name="decline" id="decline" value="Cancel"
								class="ripple1 btn btn-default btn-block mt10"
								onClick="hideGenderConfDialog(2);" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


</div>