<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript" src="js/moment.min.js"></script>
<link type="text/css"
	href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/bootstrap-select.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/common.css">
<script src="js/jquery-3.6.3.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/candidateJS/personaldetails.js"></script>
<script type="text/javascript" src="js/moment-precise-range.js"></script>

<script type="text/javascript">
	function errorField() {
		<s:iterator value="errorField">
			jQuery("select[id='<s:property />']").addClass('red-border');
			jQuery("input[id='<s:property />']").addClass('red-border');
			$("textarea[id='<s:property />']").addClass('red-border');
		</s:iterator>
	}

	function alphanumericSlash(e) {
		var k;
		document.all ? k = e.keyCode : k = e.which;
		return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57) || k == 47 || k == 92 || k == 45 || k == 46 || k == 32);
	}
	
	function alphanumericSlash1(e) {
		var k;
		document.all ? k = e.keyCode : k = e.which;
		return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57) || k == 47 || k == 92 || k == 45 || k == 46 || k == 32 || k == 44 || k == 38);
	}
	
	/*function populateDistrict() {
		var state = $("" + currUTStateId).val();
		if (state == "") {
			$("#districtValother").val("");
			//$("#cityNameother").val("");
			$("#otherStateFiled").val("");
		}
		dataString = "stateVal=" + state
		$.ajax({
			url : "CandidateAction_getDistrictList.action",
			async : true,
			data : dataString,
			type : 'POST',
			beforeSend : function() {
				$('#districtList').empty();
				$("#districtList").append($('<option></option>').val("").html("Select District / City"));
			},
			error : function(ajaxrequest) {
				window.reload();
			},
			success : function(responseText) {
				responseText = $.trim(responseText);
				if (responseText.length > 0) {
					element = responseText.split(",");
					message = responseText.substring(2, responseText.length);
					if (element[0] == "9") {
						//alert(message);
						//return false;
					} else {
						$.each(element, function(val) {
							var nameAndIDArr = element[val].split("#");
							$("#districtList").append($('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0]));
						});
					}
				}
				var districtValBean = '<s:property value="districtVal"/>';
				//alert(districtValBean);
				$("#districtList").val(districtValBean);
			}
		});
	}*/
	
	/*function populateAltDistrict() {
		var state = $("" + currUTStateId).val();
		if (state == "") {
			$("#altDistrictValOthers").val("");
			$("#otherAlternateStateField").val("");
		}
		dataString = "stateVal=" + state
		$.ajax({
			url : "CandidateAction_getAltDistrictList.action",
			async : true,
			data : dataString,
			type : 'POST',
			beforeSend : function() {
				$('#altDistrictList').empty();
				$("#altDistrictList").append($('<option></option>').val("").html("Select District / City"));
			},
			error : function(ajaxrequest) {
				window.reload();
			},
			success : function(responseText) {
				responseText = $.trim(responseText);
				if (responseText.length > 0) {
					element = responseText.split(",");
					message = responseText.substring(2, responseText.length);
					if (element[0] == "9") {
						//alert(message);
						//return false;
					} else {
						$.each(element, function(val) {
							var nameAndIDArr = element[val].split("#");
							$("#altDistrictList").append($('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0]));
						});
					}
				}
				var altDistrictValBean = '<s:property value="altDistrictVal"/>';
				//alert(altDistrictValBean);
				$("#altDistrictList").val(altDistrictValBean);
			}
		});
	}*/

	function calculateAge() {
		var dateString1 = '<s:property value="ageAsOn"/>';
		var momentObj1 = moment(dateString1, 'DD-MM-YYYY');
		var ageAsOn = momentObj1.format('YYYY-MM-DD');
		var startDate1 = ageAsOn.split("-").map(Number);
		if (startDate1[1] != "" && startDate1[1] != null) {
			var startDate = [ startDate1[0], startDate1[1] - 1, startDate1[2] ];
		}

		var dateString2 = $("#dateOfBirth").val();
		var momentObj2 = moment(dateString2, 'DD-MM-YYYY');
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
	
	function calculateGovtAge() {
		debugger;
	    var dateString1 = $("#govtDate").val();
	    var dateString2 = '<s:property value="notificationEndDate"/>';

	    if (!dateString1 || !dateString2) return;

	    // Parse input dates
	    var startDate = moment(dateString1, 'DD-MMM-YYYY');
	    var endDate = moment(dateString2, 'DD-MMM-YYYY').add(1, 'days'); // Add 1 day to the END date

	    // Compute the difference in years, months, and days
	    var years = endDate.diff(startDate, 'years');
	    startDate.add(years, 'years');

	    var months = endDate.diff(startDate, 'months');
	    startDate.add(months, 'months');

	    var days = endDate.diff(startDate, 'days');

	    //console.log("Final Age Calculation -> Years:", years, "Months:", months, "Days:", days);

	    // Assign values to input fields
	    $("#ageInYears").val(years);
	    $("#ageInMonths").val(months);
	    $("#ageInDays").val(days);
	}








</script>
<script type="text/javascript">
	//  Accordion Panels
	if (document.documentElement.clientWidth < 500) {
		$(document).ready(function() {
			/* added br afte label for mobile to break line to view better */
			$("#disciplinName-2, #pwdSubCategory2-3, ,#pwdSubCategory2-5, #pwdSubCategory2-7").before("<div class='visible-xs mt10'></div>");

			/* .........Accordions */
			$("#PersonalDiv .accordion").show();
			$('#AddressDiv .accordion').slideToggle('slow');
			$('#PreferenceDiv .accordion').slideToggle('slow');
			$('#PersonalDiv h3').addClass("current");
			$(".accordions h3").click(function() {
				$(this).next(".accordion").slideToggle("slow").siblings(".accordion:visible").slideUp("slow");
				$(this).toggleClass("current");
				$(this).siblings("h3").removeClass("current");
			});
		});
	}
	
	$(document).ready(function() {
		
		<s:if test='%{altStateVal=="1"}'>
			$("#districtSelect1").show();
			$("#districtTextarea1").hide();
			$("#policeTextarea1").show();
		</s:if>
		<s:else>
			$("#districtTextarea1").show();
			$("#policeTextarea1").show();
			$("#districtSelect1").hide();
		</s:else>

		<s:if test='%{stateVal=="1"}'>
			$("#districtSelect").show();
			$("#districtTextarea").hide();
		</s:if>
		<s:else>
			$("#districtTextarea").show();
			$("#policeTextarea").show();
			$("#districtSelect").hide();
		</s:else>
		
		
		$('#govtDate').datepicker('destroy');
		$("#govtDate").datepicker({
			showOn : "button",
			changeMonth : true,
			changeYear : true,
			yearRange : '1966:<s:property value = "notificationEndDate" />',
			minDate : new Date('Jul 01,1966'),
			maxDate : new Date('<s:property value="notificationEndDate"/>'),
			buttonImageOnly : true,
			buttonImage : "images/cale-img.png",
			buttonText : "Date of Joining in the Service",
			dateFormat : 'dd-M-yy',
			onSelect : function(dateText, inst) {
				calculateGovtAge();
			}
		});
	})
</script>
<style type="text/css">
.msgg li:first-child {
	list-style: none;
}

.contenttableNew td {
	padding-top: 15px;
}

.contenttableNew td table td {
	padding-top: 2px;
}

#msgg li {
	float: left;
}

#msgg br {
	height: 1px;
	float: left;
}

.postPrefDisabled {
	pointer-events: none;
	opacity: 0.56;
}
</style>
<body class="personal">
	<div class="row">
		<div class="col-sm-12">
			<s:if test="%{#attr.dataNotFound!=''}">
				<div class="error-massage-text">
					<s:property value="#attr.dataNotFound" />
				</div>
			</s:if>
		</div>
	</div>
	<div class="container titlebg">
		<h1 class="pageTitle">
			<strong> Personal Details <%-- <s:text name="header.personaldetails" /> --%></strong>
			<span class="userid_txt"> <s:if
					test="%{#session['SESSION_USER'] != null}">
					<strong>User ID <%-- <s:text name="applicationForm.userId" /> --%>
					</strong> -	<s:label value="%{#session['SESSION_USER'].username}" />
				</s:if> <%--<s:if test='%{instrReqd}'>
			<div style="float:right; width:400px; text-align:right;">
				<a onclick='ShowPop("pop8");' style="cursor: pointer;" >Click here to read the instructions again</a>
			</div>
		</s:if> --%>
			</span>
		</h1>
	</div>
	<s:form id="applicationForm" method="post" autocomplete="off">
		<div>
			<div class="unwrapForm">
				<s:hidden name="handicappedValue" id="handicappedValue" />
				<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<s:hidden name="isDataFound"
					value='<s:property value="candidateDataFilled"/>' />
				<s:hidden name="degreeVal" id="degreeVal" />
				<s:hidden name="cateoryValue" id="cateoryValue" />
				<s:hidden name="candidateStatusId" id="candidateStatusId"
					value="%{statusID}"></s:hidden>
				<s:hidden name="candidateSpecialOption" id="candidateSpecialOption"></s:hidden>
				<s:if test='%{statusID >=5}'>
					<s:hidden name="genderVal" id="genderVal" value="%{genderVal}"></s:hidden>
					<s:hidden name="religionBelief" id="religionBelief1"
						value="%{religionBelief}"></s:hidden>
					<s:hidden name="IDproof" id="IDproof" value="%{IDproof}"></s:hidden>
					<s:hidden name="categoryVal" id="categoryVal1"
						value="%{categoryVal}"></s:hidden>
					<s:hidden name="Subcaste" id="SubcategoryList1" value="%{Subcaste}"></s:hidden>
					<s:hidden name="DesignationIssuingAuthority"
						id="DesignationscategoryList1"
						value="%{DesignationIssuingAuthority}"></s:hidden>
				</s:if>
				<div id="dashboard" class="padding_leftright">
					<s:actionerror />
					<s:hidden id='hddAddressChkBox'></s:hidden>

					<s:if test='%{candidateDetailsSuccFlag=="true"}'>
						<div class="container"
							style="border: #999 1px solid; padding: 3px; color: green;"
							id="successMessage">
							<s:property value="candidateDetailsSuccMsg" />
						</div>
					</s:if>

					<!-- Box Container Start -->
					<div class="container common_dashboard tabDiv effect2">
						<div id="error-massageAppForm" style="display: none; color: red;"
							class="error-massage"></div>
						<s:actionmessage escape="false" cssClass="msgg" />
						<div class="accordions">
							<div class="row">
								<div class="col-sm-12">
									<p class="orgNote">
										<strong> Note: </strong>
										<s:text name="applicationForm.note" />
										<span class="manadetory-fields">*</span>
										<s:text name="applicationForm.note1" />
										<strong> <%-- <s:text name="applicationFormT.note" /> --%>
										</strong>
										<%-- <span class="manadetory-fields">*</span> --%>
										<%-- <s:text name="applicationFormT.note1" /> --%>
									</p>
								</div>
							</div>
							<div id="PersonalDiv">
								<h3 title="Personal Details">
									Personal Details
									<%-- <s:text name="header.personaldetails" /> --%>
								</h3>
								<div class="accordion">
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Post Applying for <s:text
														name="login.courses" /> <%-- <span class="manadetory-fields">*</span> --%>
													<br />
												</label>
												<s:textfield id="courses" name="courses" readonly="true"
													cssClass="form-control" value="Prosthetic Craftsman"
													onpaste="return false;" ondragstart="return false;"
													ondrop="return false;" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<div class="hidden-xs"></div>
												<label class="control-label">Name<s:text
														name="applicationForm.name"></s:text> <span
													class="manadetory-fields"></span>
												</label>
												<s:textfield name="personalDetailsBean.candidateName"
													cssClass="form-control nonEditable"
													value="%{personalDetailsBean.candidateName}"
													id="candidateName" ondrop="return false;"
													onkeypress=" return alphabetsWithSpaceForName(event);"
													maxlength="45" onpaste="return false;" readonly="true"
													autocomplete="anyrandomstring"></s:textfield>
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Nationality <s:text
														name="applicationForm.nationality" /><span
													class="manadetory-fields"></span>
												</label>
												<s:textfield value="%{nationality}" onpaste="return false;"
													id="nationality" name="nationality" label="nationality"
													cssClass="form-control nonEditable"
													onkeypress="return lettersOnly(event);"
													ondrop="return false;" readonly="true" />
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Gender <%-- <s:text
													name="applicationForm.maritalStatus" /> --%>
												</label>
												<s:textfield name="genderValDesc" cssClass="form-control"
													value="%{genderValDesc}" id="gender" readonly="true" />
											</div>
										</div>
									</div>
									<!-- ...2nd row.. -->
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Are you an
													Ex-Servicemen <%-- <span
												class="manadetory-fields">*</span> --%>
												</label>
												<s:textfield name="exServiceMen" id="exServiceMen"
													readonly="true" value="%{exServiceMen}"
													cssClass="form-control"></s:textfield>
											</div>
										</div>
										<s:if test="%{exServiceMen=='Yes'}">
											<div class="col-sm-4" id="dischargeDiv">
												<div class="form-group">
													<label class="control-label">Date of Discharge /
														Probable Discharge <s:text
															name="applicationForm.dateOfBirth" /> <%-- <span class="manadetory-fields">*</span> --%>
													</label>
													<s:textfield id="dischargeDate" name="dischargeDate"
														readonly="true"
														onkeypress="return dateFormatCheck(event);"
														cssClass="form-control">
													</s:textfield>
												</div>
											</div>
											<div class="col-sm-4" id="ppoNumberDiv">
												<div class="form-group">
													<label class="control-label">PPO Number<s:text
															name="login.ppoNumber" /> <%-- <span class="manadetory-fields">*</span> --%>
													</label>
													<s:textfield id="ppoNumber" name="ppoNumber"
														value="%{ppoNumber}"
														onkeypress="return numericOnly(event);"
														cssClass="form-control" readonly="true">
													</s:textfield>
												</div>
											</div>
										</s:if>
									</div>

									<!-- ....3rd line... -->
									<div class="row">
										<div class="col-sm-4" id="communityDiv">
											<div class="form-group">
												<label class="control-label">Do you have community
													certificate issued by Tamil Nadu Government? <s:text
														name="login.ppoNumber" /> <span class="manadetory-fields"></span>
												</label> <br />
												<s:textfield label="commCertYesNo" name="commCertYesNo"
													id="commCertYesNo" value="%{commCertYesNo}"
													cssClass="form-control" readonly="true" />
											</div>
										</div>

										<s:if test="%{community=='OC'}">
											<div class="col-sm-4" id="generalCommunity">
												<div class="form-group">
													<label class="control-label"> Community <s:text
															name="login.ppoNumber" /> <span
														class="manadetory-fields"></span></label>
													<s:textfield name="categoryVal1" value="OC"
														cssClass="form-control mt20" readonly="true" />
												</div>
											</div>
										</s:if>
										<s:if test="%{community!='OC'}">
											<div class="col-sm-4" id="chechCommunity">
												<div class="form-group">
													<label class="control-label">Community<s:text
															name="login.ppoNumber" /> <span
														class="manadetory-fields"></span></label> <br />
													<s:textfield label="community" name="community"
														id="community" value="%{community}"
														cssClass="form-control mt20" readonly="true" />
												</div>
											</div>
											<div class="col-sm-4" id="subCasteDiv">
												<div class="form-group">
													<label class="control-label">Sub Caste<s:text
															name="login.ppoNumber" /> <span
														class="manadetory-fields"></span>
													</label>
													<s:textfield id="subCaste" name="subCaste"
														value="%{subCaste}" cssClass="form-control mt20"
														readonly="true" />
												</div>
											</div>
										</s:if>
									</div>
									<s:if test="%{community!='OC'}">
										<div class="row">
											<div class="col-sm-4" id="issueAuthCommCertDiv">
												<div class="form-group">
													<label class="control-label">Issuing Authority of
														Community Certificate<s:text name="login.ppoNumber" /> <span
														class="manadetory-fields"></span>
													</label>
													<s:textfield id="issueAuthCommCert"
														name="issueAuthCommCert" cssClass="form-control"
														readonly="true" value="%{issueAuthCommCert}" />
												</div>
											</div>
											<div class="col-sm-4" id="commCertNoDiv">
												<div class="form-group">
													<label class="control-label">Community Certificate
														Number <s:text name="login.ppoNumber" /> <span
														class="manadetory-fields"></span>
													</label>
													<s:textfield id="commCertNo" name="commCertNo"
														cssClass="form-control" readonly="true" />
												</div>
											</div>
											<div class="col-sm-4" id="commCertPlaceDiv">
												<div class="form-group">
													<label class="control-label">Community Certificate
														Place of Issue<s:text name="login.ppoNumber" /> <span
														class="manadetory-fields"></span>
													</label>
													<s:textfield id="commCertPlace" name="commCertPlace"
														cssClass="form-control" readonly="true"
														value="%{commCertPlace}" />
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-4" id="commIssueDateDiv">
												<div class="form-group">
													<label class="control-label">Community Certificate
														Issuing Date<s:text name="applicationForm.dateOfBirth" />
														<span class="manadetory-fields"></span>
													</label>
													<s:textfield id="commIssueDate" name="commIssueDate"
														readonly="true"
														onkeypress="return dateFormatCheck(event);"
														cssClass="form-control">
													</s:textfield>
												</div>
											</div>
										</div>
									</s:if>
									<!-- ----Community fields ends here---- -->
									<div class="row">
										<div class="col-sm-4" id="communityDiv">
											<div class="form-group">
												<label class="control-label">Are you differently
													abled? <%-- <s:text name="login.ppoNumber" /> --%> <span
													class="manadetory-fields"></span>
												</label> <br />
												<s:textfield name="disableYesNo" readonly="true"
													id="disableYesNo" value="%{disableYesNo}"
													cssClass="form-control" />
											</div>
										</div>
										<s:if test="%{disableYesNo=='Yes'}">
											<div class="col-sm-4" id="disableTypeDiv">
												<div class="form-group">
													<label class="control-label disability">
														Differently Abled Category <%-- <span class="manadetory-fields">*</span> --%>
													</label>
													<s:textfield name="disableType" cssClass="form-control"
														id="disableType" value="%{disableType}" readonly="true">
													</s:textfield>
												</div>
											</div>
											<div class="col-sm-4" id="disablityPercentDiv">
												<div class="form-group">
													<label class="control-label">Percentage of
														Disability <%-- <span
												class="manadetory-fields">*</span> --%>
													</label>
													<s:textfield id="disablityPercent" name="disablityPercent"
														cssClass="form-control" value="%{disablityPercent}"
														readonly="true">
													</s:textfield>
												</div>
											</div>
											<div class="col-sm-4 ">
												<input type="checkbox" checked disabled /> <span
													class="font12 english_ins"> I agree to provide
													Differently Abled Person Certificate at the time of
													Certificate Verification </span>
												<s:hidden id="diffAbledChkBox" value="%{diffAbledChkBox}"
													name="diffAbledChkBox" />
											</div>

										</s:if>
									</div>

									<s:if test="%{genderValDesc=='Female'}">
										<div class="row mb10">
											<div class="col-sm-4" id="widowDiv">
												<div class="form-group">
													<label class="control-label">Are you a Destitute
														Widow? <%-- <s:text name="login.community" />  --%>
													</label> <br />
													<s:textfield label="widowYesNo" name="widowYesNo"
														id="widowYesNo" readonly="true" cssClass="form-control"
														headerKey="" />
												</div>
												<%-- <s:if test="%{widowYesNo=='Yes'}">
													<div id="widowChkBoxDiv">
														<input type="checkbox" name="widowChkBox" id="widowChkBox"
															checked disabled /> <span class="font12 english_ins">
															I agree to provide Destitute Widow Certificate at the
															time of Certificate Verification </span>
													</div>
												</s:if> --%>
											</div>
										</div>
									</s:if>
									<s:if test="%{widowYesNo=='Yes'}">
										<div class="row" id="widowRowOne">
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">Destitute Widow
														Certificate Number </label>
													<s:textfield id="desWidowCertNo" name="desWidowCertNo"
														cssClass="form-control" size="100"
														onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
														onpaste="return false;" ondragstart="return false;"
														ondrop="return false;" maxlength="100" readonly="true" />
												</div>
											</div>

											<div class="col-sm-4" id="">
												<div class="form-group">
													<label class="control-label">Destitute Widow
														Certificate Issuing Date </label>
													<s:textfield id="widowIssueDate" name="widowIssueDate"
														readonly="true"
														onkeypress="return dateFormatCheck(event);"
														cssClass="form-control">
													</s:textfield>
												</div>
											</div>
											<div class="col-sm-4" id="widowIssueAuthDiv">
												<div class="form-group">
													<label class="control-label">Issuing Authority of
														Destitute Widow Certificate </label>
													<s:textfield id="widowIssueAuth" name="widowIssueAuth"
														cssClass="form-control" size="50"
														onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
														onpaste="return false;" ondragstart="return false;"
														ondrop="return false;" maxlength="100" readonly="true" />
												</div>
											</div>
										</div>
										<div class="row" id="widowRowTwo">
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">Destitute Widow
														Certificate Issued District <%-- <span class="tamil"> <s:text name="applicationForm.stateUnion" /> </span> --%>
													</label>
													<s:textfield list="districtList" id="widowDistrict"
														name="widowDistrict" cssClass="form-control"
														readonly="true"></s:textfield>
												</div>
											</div>

										<s:if test="%{widowOtherDistrict!='' && widowOtherDistrict!=null}">
											<div class="col-sm-4" id="widowIssueAuthDiv">
												<div class="form-group">
													<label class="control-label">Destitute Widow
														Certificate Issued Other District </label>
													<s:textfield id="widowOtherDistrict"
														name="widowOtherDistrict" cssClass="form-control"
														size="50"
														onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
														onpaste="return false;" ondragstart="return false;"
														ondrop="return false;" maxlength="100" readonly="true" />
												</div>
											</div>
											</s:if>

											<div class="col-sm-4" id="widowIssueAuthDiv">
												<div class="form-group">
													<label class="control-label">Destitute Widow
														Certificate Issued Sub Division </label>
													<s:textfield id="widowSubDivision" name="widowSubDivision"
														cssClass="form-control" size="50"
														onkeypress=" return alpha_numeric_fbslash_com_dot_hyp_ampersand(event);"
														onpaste="return false;" ondragstart="return false;"
														ondrop="return false;" maxlength="100" readonly="true" />
												</div>
											</div>
										</div>
									</s:if>
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Date of Birth (as per
													SSLC mark Sheet) <s:text name="applicationForm.dateOfBirth" />
													<%-- <span
												class="manadetory-fields">*</span> --%>
												</label>
												<s:textfield id="dateOfBirth" name="dateOfBirth"
													readonly="true" onkeypress="return dateFormatCheck(event);"
													cssClass="form-control nonEditable">
												</s:textfield>
												<div class="clear"></div>
											</div>
										</div>
										<div class="clear visible-xs"></div>
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Age as on <s:property
														value="%{ageAsOn}" /> <s:text name="login.ageason" />
												</label>
												<div class="clear"></div>
												<s:textfield id="age" readonly="true"
													onpaste="return false;" name="age" value="%{age}"
													cssClass="form-control"></s:textfield>
											</div>
										</div>
									</div>
									<div class="row">
									<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Email ID <s:text
														name="applicationForm.emailId" /><span
													class="manadetory-fields"></span>
												</label>
												<s:textfield id="email" readonly="true"
													onkeypress="return alphaNumericwithSplCharEmail(event)"
													onpaste="return false;" name="personalDetailsBean.email"
													value="%{personalDetailsBean.email}" maxlength="30"
													cssClass="form-control"></s:textfield>
												<s:hidden id="emailAddressHidden" name="emailHdn"
													value="%{emailHdn}" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Mobile Number <s:text
														name="applicationForm.mobileNo" /><span
													class="manadetory-fields"></span>
												</label>
												<s:textfield id="mobileNo2"
													name="personalDetailsBean.mobileNo" readonly="true"
													value="%{personalDetailsBean.mobileNo}"
													cssClass="form-control weightBold" size="15" maxlength="10"
													onkeypress="return numbersonly(event);"
													onpaste="return false;" />
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Would you like to give
													Father and Mother name or Guardian name <span
													class="manadetory-fields">*</span>
												</label>
												<s:select list="parentAndGuardianList"
													name="parentAndGuardian" id="parentAndGuardian"
													headerValue="Select Would you like to give Father and Mother name or Guardian name"
													headerKey="" value="%{parentAndGuardian}"
													cssClass="form-control" onchange="showParentOrGuardian();" />
											</div>
										</div>
										<div id="parentDiv">
											<div class="col-sm-4">
												<div class="form-group">
													<div class="hidden-xs"></div>
													<label class="control-label mb20">Father's Name <%-- <s:text
													name="applicationForm.fatherName" /> --%> <span
														class="manadetory-fields">*</span>
													</label>
													<s:textfield name="fathersName" id="fathersName"
														value="%{fathersName}" cssClass="form-control nonEditable"
														maxlength="75"
														onkeypress="return alphabetsWithSpaceDotApostrophe(event);"
														onpaste="return false;" ondrop="return false;"
														autocomplete="anyrandomstring" />
												</div>
											</div>
											<div class="col-sm-4">
												<div class="hidden-xs"></div>
												<div class="form-group">
													<label class="control-label mb20">Mother's Name <%-- <s:text
													name="applicationForm.motherName" /> --%> <span
														class="manadetory-fields">*</span>
													</label>
													<s:textfield name="mothersName" id="mothersName"
														value="%{mothersName}" cssClass="form-control nonEditable"
														maxlength="75"
														onkeypress="return alphabetsWithSpaceDotApostrophe(event);"
														onpaste="return false;" ondrop="return false;"
														autocomplete="anyrandomstring" />
												</div>
											</div>
										</div>
										<div id="guardianDiv">
											<div class="col-sm-4">
												<div class="form-group">
													<div class="hidden-xs"></div>
													<label class="control-label mb20">Guardian's Name <%-- <s:text
													name="applicationForm.fatherName" /> --%> <span
														class="manadetory-fields">*</span>
													</label>
													<s:textfield name="guardianName" id="guardianName"
														value="%{guardianName}"
														cssClass="form-control nonEditable" maxlength="75"
														onkeypress="return alphabetsWithSpaceDotApostrophe(event);"
														onpaste="return false;" ondrop="return false;"
														autocomplete="anyrandomstring" />
												</div>
											</div>
										</div>
									</div>

									<div class="row">
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Are you married <%-- <s:text
													name="applicationForm.maritalStatus" /> --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:select list="yesNo" name="mariatalStatus"
													label="Are you married" id="mariatalStatus"
													headerValue="Select Are you married" headerKey=""
													value="%{mariatalStatus}" cssClass="form-control"
													onchange="showSpouseName();" />
											</div>
										</div>
										<div class="col-sm-4" id="spouseNameDiv">
											<div class="form-group">
												<div class="hidden-xs"></div>
												<label class="control-label">Name of Spouse <%-- <s:text
													name="applicationForm.maritalStatus" /> --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:textfield id="spouseName"
													cssClass="form-control nonEditable" value="%{spouseName}"
													onpaste="return false;" name="spouseName" maxlength="75"
													onkeypress="return alphabetsWithSpaceDotApostrophe(event);" />
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<div class="hidden-xs"></div>

												<label class="control-label">Nativity <%-- <s:text
													name="applicationForm.maritalStatus" /> --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:select list="nativityList" name="nativity"
													label="Nativity" headerKey="" headerValue="Select Nativity"
													id="nativity" value="%{nativity}"
													cssClass="form-control nonEditable"
													onchange="enableotherNativity();" />
											</div>
										</div>
									</div>

									<div class="row">

										<div class="col-sm-4" id="otherNativityDiv">
											<div class="form-group">
												<div class="hidden-xs"></div>
												<label class="control-label">Other Native <%-- <s:text
													name="applicationForm.maritalStatus" />  --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:select list="stateListWoTamil" id="otherNativity"
													cssClass="form-control nonEditable" headerKey=""
													headerValue="Select Other Native" value="%{otherNativity}"
													onpaste="return false;" name="otherNativity" maxlength="45"
													onkeypress="return alphabetsWithSpaceForName(event);" />
											</div>
										</div>

										<div class="col-sm-4">
											<div class="form-group">
												<div class="hidden-xs"></div>
												<label class="control-label">Religion <%-- <s:text
													name="applicationForm.religionBelief" /> --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:select list="religionBeliefList" name="religionBelief"
													label="Religion" headerKey="" headerValue="Select Religion"
													id="religionBelief" value="%{religionBelief}"
													cssClass="form-control nonEditable"
													onchange="showOtherReligion();setCategoryValues();clearCatVal();" />
											</div>
										</div>
										<div class="col-sm-4" id="otherReligionDiv">
											<div class="form-group">
												<div class="hidden-xs"></div>
												<label class="control-label">Other Religion <%-- <s:text
													name="applicationForm.otherReligionBelief" /> --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:textfield id="religionBeliefOthers"
													cssClass="form-control nonEditable"
													value="%{religionBeliefOthers}" onpaste="return false;"
													name="religionBeliefOthers" maxlength="20"
													onkeypress="return alphabetsWithSpaceForName(event);" />
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-4" id="isGovtServiceDiv">
											<div class="form-group">
												<label class="control-label">Are you already in
													Government Service? <span class="manadetory-fields">*</span>
												</label>
												<s:select list="yesNo" name="isGovtService" headerKey=""
													headerValue="Select Are you already in
												Government Service?"
													id="isGovtService" value="%{isGovtService}"
													cssClass="form-control"
													onchange="showGovtFields(); showPopUpForGovt();" />

												<div class="row" id="chkBoxDiv">
													<div class="col-sm-12 ">
														<s:checkbox name="govtServChkBox" id="govtServChkBox" />
														<span class="font12 english_ins"> I agree to
															provide No Objection Certificate from the concerned
															Government Department at the time of Certificate
															Verification. </span>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="col-sm-4" id="orgNameDiv">
											<div class="form-group">
												<div class="hidden-xs"></div>
												<label class="control-label">Department Name <%-- <s:text
													name="applicationForm.religionBelief" /> --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:textfield id="orgName"
													cssClass="form-control nonEditable" value="%{orgName}"
													onpaste="return false;" name="orgName" maxlength="50"
													onkeypress="return alphanumericWithSpaceAndDot(event);" />
											</div>
										</div>
										<div class="col-sm-4" id="currentDesigDiv">
											<div class="form-group">
												<div class="hidden-xs"></div>
												<label class="control-label">Current Designation <%-- <s:text
													name="applicationForm.orgName" />  --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:textfield id="currentDesig"
													cssClass="form-control nonEditable" value="%{currentDesig}"
													onpaste="return false;" name="currentDesig" maxlength="50"
													onkeypress="return alphanumericWithSpaceAndDot(event);" />
											</div>
										</div>
										<div class="col-sm-4" id="placeOfWorkeDiv">
											<div class="form-group">
												<div class="hidden-xs"></div>
												<label class="control-label">Place of Work <%-- <s:text
													name="applicationForm.religionBelief" /> --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:textfield id="placeOfWork"
													cssClass="form-control nonEditable" value="%{placeOfWork}"
													onpaste="return false;" name="placeOfWork" maxlength="50"
													onkeypress="return alphanumericWithSpaceAndDot(event);" />
											</div>
										</div>
									</div>
									<div class="row" id="govtDateDiv">
										<div class="col-sm-4">
											<div class="form-group dateInput">
												<label class="control-label mb8">Date of Joining in
													the Service <%-- <s:text
													name="applicationForm.commIssueDate" /> --%> <span
													class="manadetory-fields">*</span>
												</label>
												<s:textfield id="govtDate" name="govtDate" readonly="true"
													onkeypress="return dateFormatCheck(event);"
													cssClass="form-control">
												</s:textfield>
											</div>
										</div>
										<div class="col-sm-4">
											<div class="form-group">
												<label class="control-label">Period of Service as on
													the Date of Notification <s:property value="%{notificationEndDate}" />
													<br /> <%-- <s:text name="login.ageason" /> --%>
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
													<s:textfield id="ageInDays" name="ageInDays"
														readonly="true" onkeypress="return restrictEnter(event);"
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
									<div class="row">
										<div class="row">
											<div class="col-sm-12">
												<h3 title="Photo ID Proof Details" class="subHead2">Photo
													ID Proof Details</h3>
											</div>
										</div>
										<div class="accordion">
											<div class="row">
												<div class="col-sm-4" id="photoIDProof1Div">
													<div class="form-group">
														<label class="control-label">Photo ID Proof <span
															class="manadetory-fields">*</span><br> <span
															class="tamil"> <s:text
																	name="applicationForm.photoidproof" />
														</span></label>
														<s:select list="photoIdProof" name="photoIDProof1"
															label="Name" headerValue="Select Photo ID Proof"
															headerKey="" id="photoIDProof1" value="%{photoIDProof1}"
															onchange="enableDisablephotoIDProof('photoID1');"
															cssClass="form-control" />
													</div>
												</div>
												<div class="col-sm-4" id="photoIDProof1Td">
													<div class="form-group">
														<label class="control-label"> Photo ID Proof
															Number <span class="manadetory-fields">*</span> <%-- <br> <span
															class="tamil"> <s:text
																	name="applicationForm.photoidproofval" />
														</span> --%>
														</label>
														<s:textfield id="photoIDProof1Val" name="photoIDProof1Val"
															maxlength="50"
															onkeypress="return alphaNumSpaceComAmberHyphenFbSlashDot(event);"
															onpaste="return false;" cssClass="form-control"
															ondragstart="return false;" ondrop="return false;"></s:textfield>
													</div>
												</div>
											</div>
										</div>
									</div>
									<div class="row">
										<div class="accordions">
											<div id="section1">
												<div class="clear"></div>
												<div class="clear mt10"></div>
												<div class="accordions">
													<div id="AddressDiv">
														<h3 title="Address Details">
															Address Details
															<%-- 	<s:text name="header.AddressDetails" /> --%>
														</h3>
														<div class="accordion">
															<div class="row mt10">
																<div class="col-sm-12">
																	<p class="WeightBold">
																		Permanent Address
																		<%-- <s:text name="applicationForm.permanentAddress" /> --%>
																		<span class="manadetory-fields"></span>
																	</p>
																</div>
															</div>
															<div class="row">
																<div class="col-sm-8">
																	<div class="form-group">
																		<label class="control-label ">Address <%-- <s:text
																			name="applicationForm.add1" />  --%> <span
																			class="manadetory-fields">*</span></label>
																		<s:textarea id="addressFiled1" rows="3" cols="45"
																			cssClass="form-control nonEditable"
																			onpaste="return false;"
																			name="addressBean.addressFiled1" maxlength="250"
																			onkeypress="return alphaNumSpaceComAmberHyphenFbSlashDot(event);"
																			ondragstart="return false;" ondrop="return false;"
																			autocomplete="anyrandomstring"></s:textarea>
																	</div>
																</div>
																<div class="col-sm-4" id="stateDropdown">
																	<div class="form-group">
																		<label class="control-label">State <%-- <span
																		class="tamil"> <s:text
																				name="applicationForm.stateUnion" />
																	</span> --%> <span class="manadetory-fields">*</span>
																		</label>
																		<s:select list="stateList" id="stateList"
																			name="stateVal" cssClass="form-control"
																			onchange="populateDistrictForState();" headerKey=""
																			headerValue="Select State"></s:select>
																	</div>
																</div>
															</div>
															<div class="row">
																<div class="col-sm-4" id="stateDropdown">
																	<div class="form-group">
																		<label class="control-label">District <%-- <span
																	class="tamil"> <s:text name="additional.district" />
																</span> --%> <span class="manadetory-fields">*</span></label>
																		<div id="districtSelect" style="display: none;">
																			<s:select list="districtList" id="districtList"
																				name="districtVal"
																				onchange="populateCityForDistrict();"
																				cssClass="form-control" headerKey=""
																				headerValue="Select District"></s:select>
																		</div>
																		<div id="districtTextarea" style="display: none;">
																			<s:textfield id="districtValother"
																				name="districtValother" value="%{districtValother}"
																				onkeypress="return lettersOnly(event);"
																				cssClass="form-control" maxlength="50"
																				onpaste="return false;" ondragstart="return false;"
																				ondrop="return false;"></s:textfield>
																		</div>
																	</div>
																</div>
																<div class="col-sm-4">
																	<div class="form-group">
																		<label class="control-label">City / Village <%-- <span
																	class="tamil"> <s:text name="applicationForm.city" />
																</span> --%> <span class="manadetory-fields">*</span></label>
																		<div id="policeTextarea">
																			<s:textfield id="cityName"
																				name="addressBean.cityName" cssClass="form-control"
																				onkeypress="return alphaNumSpaceComDotUnderScHyphen(event);"
																				value="%{addressBean.cityName}"
																				onpaste="return false;" maxlength="50"
																				ondragstart="return false;" ondrop="return false;"></s:textfield>
																		</div>
																	</div>
																</div>
																<div class="col-sm-4">
																	<div class="form-group">
																		<label class="control-label">Pincode <%-- <span
																	class="tamil"> <s:text name="applicationForm.pincode" />
																</span> --%> <span class="manadetory-fields">*</span></label>
																		<s:textfield id="pinCode" name="addressBean.pinCode"
																			cssClass="form-control" maxlength="6"
																			onkeypress="return numbersonly(event);"
																			onpaste="return false;"
																			onmouseover="disableDragDrop(this,'pinCode')"
																			ondragstart="return false;" ondrop="return false;"></s:textfield>
																	</div>
																</div>
															</div>
															<div class="row">
																<div class="col-sm-4 mt10">
																	<p class="WeightBold">
																		Correspondence Address
																		<%-- <s:text name="applicationForm.commAddress" /> --%>
																	</p>
																</div>
																<div class="col-sm-7 mt10">
																	<p class="WeightBold"
																		style="color: #777777 !important;">
																		<s:checkbox name="addressChkBox" id="addressChkBox"
																			onkeypress="return noEnter(event);"
																			onclick="copyPermenantAddress();resetAlternateAddress();"
																			cssClass="mr5 nonEditable" />
																		Same as Permanent Address
																		<%-- <s:text name="applicationForm.commAddressCheck" /> --%>
																	</p>
																</div>
															</div>

															<div class="communicationAddress">

																<div class="row">
																	<div class="col-sm-8">
																		<div class="form-group">
																			<label class="control-label">Address <%-- <s:text
																				name="applicationForm.add1" /> --%> <span
																				class="manadetory-fields">*</span>
																			</label>
																			<s:textarea id="alternateAddressFiled1" rows="3"
																				cols="45" cssClass="form-control nonEditable"
																				onpaste="return false;"
																				name="addressBean.alternateAddressFiled1"
																				maxlength="250"
																				onkeypress="return alphaNumSpaceComAmberHyphenFbSlashDot(event);"
																				ondragstart="return false;" ondrop="return false;"
																				autocomplete="anyrandomstring"></s:textarea>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="form-group">
																			<label class="control-label">State <%-- <s:text
																				name="applicationForm.stateUnionC" /> --%> <span
																				class="manadetory-fields">*</span>
																			</label>
																			<s:select list="stateList" id="altStateList"
																				name="altStateVal" cssClass="form-control"
																				onchange="populateAltDistrictForState();"
																				headerKey="" headerValue="Select State"></s:select>
																		</div>
																	</div>
																</div>
																<div class="row">
																	<div class="col-sm-4" id="alternateStateDisplay">
																		<div class="form-group">
																			<label class="control-label">District <%-- <span
																			class="tamil"> <s:text name="additional.district" />
																		</span> --%> <span class="manadetory-fields">*</span></label>
																			<div id="districtSelect1" style="display: none;">
																				<s:select list="altDistrictList"
																					id="altDistrictList" name="altDistrictVal"
																					cssClass="form-control"
																					onchange="populateAltCityForDistrict();"
																					headerKey="" headerValue="Select District"></s:select>
																				&nbsp;
																			</div>
																			<div id="districtTextarea1" style="display: none;">
																				<s:textfield id="altDistrictValOthers"
																					name="altDistrictValOthers"
																					value="%{altDistrictValOthers}"
																					cssClass="form-control"
																					onkeypress="return lettersOnly(event);"
																					ondragstart="return false;" ondrop="return false;"
																					maxlength="50"></s:textfield>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="form-group">
																			<label class="control-label">City / Village <%-- <span
																			class="tamil"> <s:text name="applicationForm.city" />
																		</span> --%> <span class="manadetory-fields">*</span></label>
																			<div id="policeTextarea1">
																				<s:textfield name="addressBean.alternateCity"
																					value="%{addressBean.alternateCity}"
																					cssClass="form-control" id="alternateCityother"
																					onkeypress="return alphaNumSpaceComDotUnderScHyphen(event);"
																					onpaste="return false;" ondragstart="return false;"
																					ondrop="return false;" maxlength="50"></s:textfield>
																			</div>
																		</div>
																	</div>
																	<div class="col-sm-4">
																		<div class="form-group">
																			<label class="control-label">Pincode <%-- <span
																			class="tamil"> <s:text
																					name="applicationForm.pincode" />
																		</span> --%> <span class="manadetory-fields">*</span></label>
																			<s:textfield id="alternatePinCode"
																				name="addressBean.alternatePinCode"
																				cssClass="form-control" maxlength="6"
																				onkeypress="return numbersonly(event);"
																				onpaste="return false;"
																				onmouseover="disableDragDrop(this,'alternatePinCode')"
																				ondragstart="return false;" ondrop="return false;"></s:textfield>
																		</div>
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="clear mt10"></div>
												<div class="clear mt10"></div>
												<div class="accordions">
													<div id="PreferenceDiv">
														<div class="accordion">
															<div class="row">
																<div class="col-sm-4" id="postPref1">
																	<div class="form-group">
																		<label class="control-label">Mother Tongue <span
																			class="manadetory-fields">*</span>
																		</label>
																		<s:select list="motherTongueList"
																			cssClass="form-control" name="motherTongue"
																			headerKey="" headerValue="Select Mother Tongue"
																			id="motherTongue" value="%{motherTongue}" />
																	</div>
																</div>
															</div>
														</div>
													</div>
												</div>
												<%-- <div class="row">
												<div class="col-sm-12">
													<div class="font12 WeightBold orgNote mb0">
														<span> <b>Note:</b> Selection of the preferred test
															city do not assure you the same test city. You will be
															scheduled as per the availability of the seats.
														</span>
													</div>
												</div>
											</div> --%>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div id="overlay1" class="web_dialog_overlay_declr"></div>
				<div class="countinuebg row mt20">
					<div class="container">
						<div class="row">
							<div
								class="col-sm-2 col-sm-offset-10 col-xs-6 col-xs-offset-3 text-right padding0">
								<div id="applSaveCont" align="right">
									<s:submit value="Save & Continue"
										cssClass="submitBtn btn btn-warning btn-block"
										onClick="validatePersonalDetails();"></s:submit>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="clear"></div>
				<div class="change-pass box-gradient" id="pop9"
					style="vertical-align: top">
					<div>
						<a href="javascript:void(0);" onclick="PopHideAll();"><img
							src="images/Close.png" align="right" border="0" /></a>
					</div>
				</div>
			</div>
			<s:token />
			<div id='block7'></div>
		</div>
	</s:form>

	<!-- <div class="clear"></div>
<div id="overlay5" class="web_dialog_overlay_declr"></div>
<div class="fullscreen-container" id="dialog5">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onclick="HideDialog()">&times;</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 text-center">
						<h4 class="pageTitle" id="govtId">You must provide No Objection Certificate from department during Certificate Verification</h4>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">
						<input type="button" name="accept" id="accept" value="OK"
							class="ripple1 btn btn-warning btn-block mt10"
							onClick="hidePopUpForGovt();" />
					</div>
					<div class="col-sm-3">
						<input type="button" name="decline" id="decline" value="Cancel"
							class="ripple1 btn btn-default btn-block mt10"
							onClick="hidePopUpForGovt();" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div> -->

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

	<div class="clear"></div>
	<div id="overlay" class="web_dialog_overlay_declr"></div>
	<div class="fullscreen-container" id="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<!-- <button type="button" class="close" onclick="HideDialog()">&times;</button> -->
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
								onclick="HideDialog()"
								class="ripple1 btn btn-warning btn-block mt10" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- <div class="clear"></div>
<div id="overlay2" class="web_dialog_overlay_declr"></div>
<div class="fullscreen-container" id="dialog1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onclick="HideDialog1()">&times;</button>
				<h2 class="modal-title">Confirmation</h2>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 text-center">
						<h4 class="pageTitle" id="dynamicContent5">Consecutive dots
							are not allowed.</h4>
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
</div> -->

	<s:hidden id='hidConfirnmation' name='hidConfirnmation' value="-1" />
</body>