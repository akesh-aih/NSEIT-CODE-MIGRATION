<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript" src="js/moment.min.js"></script>
<link type="text/css"
	href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/style.css">

<link rel="stylesheet" href="css/bootstrap-select.min.css">

<script src="js/jquery-3.6.3.min.js"></script>
<script src="js/candidateJS/additionaldetails.js"></script>
<script src="js/jquery-ui.js"></script>
<script type="text/javascript" src="js/moment-precise-range.js"></script>

<script type="text/javascript">

function errorField() {
	<s:iterator value="errorField">
	jQuery("select[id='<s:property />']").addClass('red-border');
	jQuery("input[id='<s:property />']").addClass('red-border');
	jQuery("button[data-id='<s:property />']").addClass('red-border');
	</s:iterator>
}	

function populateRef1District() {
	var ref1State = $("" + currUTStateId).val();
	//alert("ajax called");
	dataString = "ref1StateVal=" + ref1State
	$.ajax({
		url : "CandidateAction_getRef1DistrictList.action",
		async : true,
		data : dataString,
		type : 'POST',
		beforeSend : function() {
			$('#ref1DistrictList').empty();
			$("#ref1DistrictList").append(
					$('<option></option>').val("").html(
							"--Select District--"));

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

						$("#ref1DistrictList").append(
								$('<option></option>').val(nameAndIDArr[1])
										.html(nameAndIDArr[0]));
					});
				}
			}

			var ref1DistrictValBean = '<s:property value="additionalDetailsBean.ref1District"/>';
			//alert(ref1DistrictValBean);
			$("#ref1DistrictList").val(ref1DistrictValBean);

		}
	});

}

function populateRef2District() {

	var ref2state = $("" + currAltUTStateId).val();

	dataString = "ref2StateVal=" + ref2state
	$
			.ajax({
				url : "CandidateAction_getRef2DistrictList.action",
				async : true,
				data : dataString,
				type : 'POST',
				beforeSend : function() {
					$('#ref2DistrictList').empty();
					$("#ref2DistrictList").append(
							$('<option></option>').val("").html(
									"--Select District--"));

				},
				error : function(ajaxrequest) {
					window.reload();
				},
				success : function(responseText) {
					responseText = $.trim(responseText);
					if (responseText.length > 0) {
						element = responseText.split(",");

						message = responseText.substring(2,
								responseText.length);
						if (element[0] == "9") {
							//alert(message);
							//return false;
						} else {
							$.each(element, function(val) {
								var nameAndIDArr = element[val].split("#");

								$("#ref2DistrictList").append(
										$('<option></option>').val(
												nameAndIDArr[1]).html(
												nameAndIDArr[0]));
							});
						}
					}
					var ref2DistrictValBean = '<s:property value="additionalDetailsBean.ref2District"/>';
					//alert(ref2DistrictValBean);
					$("#ref2DistrictList").val(ref2DistrictValBean);
				}
			});

}

function numbersonly(e){
	var unicode=e.charCode? e.charCode : e.keyCode
	if (unicode==9){
		return true;
	}
		
	if ((unicode < 48 || unicode > 57) && unicode!=8 && unicode != 52 ){ //if not a number
		return false; //disable key press
	}
}
function validateEmail(e) {
	var filter = /^([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
	if (!filter.test(e)) {
		return false;
	} 
}
function alphaNumeric(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9 && unicode != 46  && unicode != 39)
		return false;
}
function alphaNumericWithSpace(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9 && unicode != 46  && unicode != 39 && unicode != 32)
		return false;
}
function alphabetswithspace(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && unicode != 8 && unicode != 32  && unicode != 9)
		return false;
}

function alphaNumericwithSplChar(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 38 && unicode != 8 && unicode != 9 && unicode != 46 && unicode != 40 && unicode != 41 && unicode != 32 && (unicode<44 || unicode>47) && unicode != 92  && unicode != 95)
		return false;
}
function alphaNumericwithSplCharNew(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 38 && unicode != 32 && unicode != 44 && unicode != 47 && unicode != 46  && unicode != 92)
		return false;
}

function alphaNumericwithSplCharEmail(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57)&& unicode != 46  && unicode != 95 && unicode != 64)
		return false;
}

function numericWithHyphenOnly(e) 
{
            var charCode = e.charCode? e.charCode : e.keyCode;
            if ((charCode > 47 && charCode < 58) || charCode == 8 || charCode == 45)

                return true;
            else
                return false;
}

function showPastFields()
{
	
	var appliedInPast = $("#appliedInPast  option:selected" ).val();
	if(appliedInPast==6){
		$("#yearsOfApplyDiv").show();
		$("#reasonDiv").show();
	}
	else{
		$("#yearsOfApplyDiv").hide();
		$("#reasonDiv").hide();
		$("#yearsOfApply").val("");
		$("#reasonForNotJoining").val("");
	}
	
	}
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
</style>
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
		Additional Details
		<%-- <s:text name="header.additionaldetails" /> --%>
		<span class="userid_txt"> <s:if
				test="%{#session['SESSION_USER'] != null}">
				<strong>Reference Number</strong> -
										<s:label value="%{#session['SESSION_USER'].username}" />
			</s:if> <%--<s:if test='%{instrReqd}'>
			<div style="float:right; width:400px; text-align:right;">
				<a onclick='ShowPop("pop8");' style="cursor: pointer;" >Click here to read the instructions again</a>
			</div>
		</s:if> --%>
		</span>
	</h1>
</div>

<s:form id="additionalDetailsForm" action="CandidateAction_saveCandidateAdditionalDetails"
	method="post" autocomplete="off">

	<s:hidden name="handicappedValue" id="handicappedValue" />
	<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
	<s:hidden name="isDataFound"
		value='<s:property value="candidateDataFilled"/>' />
	<s:hidden name="disciplineId" id="disciplineIdHidden" />
	<s:hidden name="degreeVal" id="degreeVal" />
	<s:hidden name="candidateStatusId" id="candidateStatusId"
		value="%{statusID}"></s:hidden>
	<s:hidden name="genderValDesc" id="genderValDesc"
		value="%{genderValDesc}"></s:hidden>
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
							<strong> <s:text name="applicationForm.note" /> :
							</strong><span class="manadetory-fields">*</span>
							<s:text name="applicationForm.note1" />
							<%-- <span class="tamil"> <strong> <s:text
														name="applicationFormT.note" />
											</strong> <span class="manadetory-fields"></span>
											</span> --%>
						</p>
					</div>
				</div>
				<div id="PersonalDiv">
					<h3 title="Other Details">Other Details</h3>
					<div class="accordion">
						<div class="row">
							<div class="col-sm-4">
								<div class="form-group">
									<label class="control-label">Academic Awards (If any)<span
										class="manadetory-fields"></span>
									</label>
									<s:textfield name="additionalDetailsBean.academicAward"
										id="academicAward"
										value="%{additionalDetailsBean.academicAward}"
										cssClass="form-control nonEditable" maxlength="50"
										onkeypress="return alphaNumericWithSpace(event);"
										onpaste="return false;" ondrop="return false;"
										autocomplete="anyrandomstring" />
								</div>
							</div>
							<div class="col-sm-4">
								<div class="form-group">
									<label class="control-label">Where did you see the
										advertisement?<span class="manadetory-fields">*</span>
									</label>
									<s:select list="advertList"
										name="additionalDetailsBean.advertisement" id="advertisement"
										value="%{additionalDetailsBean.advertisement}"
										cssClass="form-control nonEditable"
										headerValue="Where did you see the
										advertisement?"
										headerKey=""></s:select>
								</div>
							</div>
							<div class="col-sm-4">
								<div class="form-group">
									<label class="control-label">Have you applied for this
										programme in the past? <span class="manadetory-fields">*</span>
									</label>
									<s:select list="yesNo"
										name="additionalDetailsBean.appliedInPast" id="appliedInPast"
										value="%{additionalDetailsBean.appliedInPast}"
										onchange="showPastFields();"
										cssClass="form-control nonEditable"
										headerValue="Have you applied for this programme in the past?"
										headerKey=""></s:select>
								</div>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-4" id="yearsOfApplyDiv">
								<div class="form-group">
									<label class="control-label mb20">Year(s) of
										application <span class="manadetory-fields">*</span>
									</label>
									<s:select list="yearsAppliedList"
										title="Year(s) of application"
										name="additionalDetailsBean.yearsOfApply" id="yearsOfApply"
										value="%{additionalDetailsBean.yearsOfApplyList}"
										data-size="5" cssClass="selectpicker form-control nonEditable"
										headerKey="" multiple="true"></s:select>
								</div>
							</div>
							<div class="col-sm-4" id="reasonDiv">
								<div class="form-group">
									<label class="control-label">If you were offered
										admission, give brief reasons for not joining the programme.<span
										class="manadetory-fields">*</span>
									</label>
									<s:textfield id="reasonForNotJoining" rows="4" cols="45"
										cssClass="form-control nonEditable" onpaste="return false;"
										name="additionalDetailsBean.reasonForNotJoining"
										maxlength="1000"
										onkeypress="return alphaNumericwithSplCharNew(event);"
										ondragstart="return false;" ondrop="return false;"
										autocomplete="anyrandomstring"></s:textfield>
								</div>
							</div>
							<s:if test='%{disciplineId == 2}'>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Any other information
											that you think will help us to better assess your performance
											and potential<span class="manadetory-fields"></span>
										</label>
										<s:textfield id="otherInfo" rows="4" cols="45"
											cssClass="form-control nonEditable" onpaste="return false;"
											name="additionalDetailsBean.otherInfo" maxlength="1000"
											onkeypress="return alphaNumericwithSplCharNew(event);"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="anyrandomstring"></s:textfield>
									</div>
								</div>
							</s:if>
						</div>
					</div>
					<s:if test='%{disciplineId == 1}'>
						<h3 title="Referee Details">Referee Details</h3>
						<div class="accordion">
							<div class="row">
								<div class="col-sm-12">
									<p class="WeightBold">Referee 1</p>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Name <span
											class="manadetory-fields">*</span>
										</label>
										<s:textfield name="additionalDetailsBean.ref1Name"
											id="ref1Name" value="%{additionalDetailsBean.ref1Name}"
											cssClass="form-control nonEditable" maxlength="50"
											onkeypress="return alphabetswithspace(event);"
											onpaste="return false;" ondrop="return false;"
											autocomplete="anyrandomstring" />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Designation <span
											class="manadetory-fields">*</span>
										</label>
										<s:textfield name="additionalDetailsBean.ref1Desig"
											id="ref1Desig" value="%{additionalDetailsBean.ref1Desig}"
											cssClass="form-control nonEditable" maxlength="50"
											onkeypress="return alphabetswithspace(event);"
											onpaste="return false;" ondrop="return false;"
											autocomplete="anyrandomstring" />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Is he/she an academician?<span
											class="manadetory-fields">*</span>
										</label>
										<s:select list="yesNo"
											name="additionalDetailsBean.ref1IsAcademician"
											id="ref1IsAcademician"
											value="%{additionalDetailsBean.ref1IsAcademician}"
											cssClass="form-control nonEditable"
											headerValue="Select Is he/she an academician?" headerKey=""></s:select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<p>Address</p>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label ">Address Line 1 <span
											class="manadetory-fields">*</span>
										</label>
										<s:textfield id="ref1Add1" rows="4" cols="45"
											cssClass="form-control nonEditable" onpaste="return false;"
											name="additionalDetailsBean.ref1Add1" maxlength="75"
											onkeypress="return alphaNumericwithSplCharNew(event);"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="anyrandomstring"></s:textfield>
									</div>
								</div>
								<div class="col-sm-4 permAddressFields">
									<div class="form-group">
										<label class="control-label ">Address Line 2 </label>
										<s:textfield id="ref1Add2" onpaste="return false;"
											name="additionalDetailsBean.ref1Add2"
											cssClass="form-control nonEditable" maxlength="75"
											onkeypress="return alphaNumericwithSplCharNew(event);"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="anyrandomstring"></s:textfield>
									</div>
								</div>
								<div class="col-sm-4" id="stateDropdown">
									<div class="form-group">
										<label class="control-label">State / Union Territory <span
											class="manadetory-fields">*</span>
										</label>
										<s:select list="refStateList" id="ref1StateList"
											name="additionalDetailsBean.ref1State"
											cssClass="form-control nonEditable"
											onchange="populateRef1DistrictForState();" headerKey=""
											headerValue="--Select State / Union Territory--"></s:select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4" id="stateDropdown">
									<div class="form-group">
										<label class="control-label"> District <span
											class="manadetory-fields">*</span>
										</label>
										<div id="districtSelect">
											<s:select list="ref1DistrictListMap" id="ref1DistrictList"
												name="additionalDetailsBean.ref1District"
												cssClass="form-control nonEditable" headerKey=""
												headerValue="--Select District--"></s:select>
										</div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">City / Village / Town<span
											class="manadetory-fields">*</span>
										</label>
										<div id="policeTextarea">
											<s:textfield id="cityNameother1"
												name="additionalDetailsBean.ref1City"
												cssClass="form-control nonEditable"
												onkeypress="return alphaNumericwithSplCharNew(event);"
												value="%{additionalDetailsBean.ref1City}"
												onpaste="return false;" maxlength="50"
												ondragstart="return false;" ondrop="return false;"
												autocomplete="anyrandomstring"></s:textfield>
										</div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label"> Pincode <span
											class="manadetory-fields">*</span>
										</label>
										<s:textfield id="pinCode"
											name="additionalDetailsBean.ref1Pincode"
											cssClass="form-control nonEditable" maxlength="6"
											onkeypress="return numbersonly(event);"
											onpaste="return false;"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="anyrandomstring"></s:textfield>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Email ID <span
											class="tamil"> <s:text name="login.emailId" />
										</span><span class="manadetory-fields">*</span></label>
										<s:textfield id="ref1EmailAddress"
											name="additionalDetailsBean.ref1EmailAddress"
											onpaste="return false;" cssClass="form-control nonEditable"
											size="15" maxlength="50"
											onkeypress="return alphaNumericwithSplCharEmail(event);"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="off" />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label ">Telephone number/
											International Phone Number/Mobile number (If international
											number, please enter number along with country code)<span
											class="manadetory-fields">*</span>
										</label>
										<s:textfield id="ref1Contact"
											cssClass="form-control nonEditable" onpaste="return false;"
											name="additionalDetailsBean.ref1Contact" maxlength="15"
											onkeypress="return numericWithHyphenOnly(event);"
											pattern="[0-9]{2,4}-[0-9]{8,10}" ondragstart="return false;"
											ondrop="return false;" autocomplete="anyrandomstring"></s:textfield>
										<span class="Smallnote">(Country code 2-4 digits and
											mobile number 8-10 digits only separated by hyphen, without
											leading 0)</span>
									</div>
								</div>
							</div>


							<div class="row">
								<div class="col-sm-12">
									<p class="WeightBold">Referee 2</p>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Name <span
											class="manadetory-fields">*</span>
										</label>
										<s:textfield name="additionalDetailsBean.ref2Name"
											id="ref2Name" value="%{additionalDetailsBean.ref2Name}"
											cssClass="form-control nonEditable" maxlength="50"
											onkeypress="return alphabetswithspace(event);"
											onpaste="return false;" ondrop="return false;"
											autocomplete="anyrandomstring" />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Designation <span
											class="manadetory-fields">*</span>
										</label>
										<s:textfield name="additionalDetailsBean.ref2Desig"
											id="ref2Desig" value="%{additionalDetailsBean.ref2Desig}"
											cssClass="form-control nonEditable" maxlength="50"
											onkeypress="return alphabetswithspace(event);"
											onpaste="return false;" ondrop="return false;"
											autocomplete="anyrandomstring" />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Is he/she an academician?<span
											class="manadetory-fields">*</span>
										</label>
										<s:select list="yesNo"
											name="additionalDetailsBean.ref2IsAcademician"
											id="ref2IsAcademician"
											value="%{additionalDetailsBean.ref2IsAcademician}"
											cssClass="form-control nonEditable"
											headerValue="Select Is he/she an academician?" headerKey=""></s:select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<p>Address</p>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label ">Address Line 1 <span
											class="manadetory-fields">*</span>
										</label>
										<s:textfield id="ref2Add1" rows="4" cols="45"
											cssClass="form-control nonEditable" onpaste="return false;"
											name="additionalDetailsBean.ref2Add1" maxlength="75"
											onkeypress="return alphaNumericwithSplCharNew(event);"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="anyrandomstring"></s:textfield>
									</div>
								</div>
								<div class="col-sm-4 permAddressFields">
									<div class="form-group">
										<label class="control-label ">Address Line 2 </label>
										<s:textfield id="ref2Add2" onpaste="return false;"
											name="additionalDetailsBean.ref2Add2"
											cssClass="form-control nonEditable" maxlength="75"
											onkeypress="return alphaNumericwithSplCharNew(event);"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="anyrandomstring"></s:textfield>
									</div>
								</div>
								<div class="col-sm-4" id="alternateStateDisplay">
									<div class="form-group">
										<label class="control-label">State / Union Territory <span
											class="manadetory-fields">*</span>
										</label>
										<s:select list="refStateList" id="ref2StateList"
											name="additionalDetailsBean.ref2State"
											cssClass="form-control nonEditable"
											onchange="populateRef2DistrictForState();" headerKey=""
											headerValue="--Select State / Union Territory--"></s:select>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4" id="alternateStateDisplay">
									<div class="form-group">
										<label class="control-label"> District <span
											class="manadetory-fields">*</span>
										</label>
										<div id="districtSelect1">
											<s:select list="ref2DistrictListMap" id="ref2DistrictList"
												name="additionalDetailsBean.ref2District"
												cssClass="form-control nonEditable" headerKey=""
												headerValue="--Select District--"></s:select>
										</div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">City / Village / Town <span
											class="manadetory-fields">*</span>
										</label>
										<div id="policeTextarea">
											<s:textfield id="cityNameother2"
												name="additionalDetailsBean.ref2City"
												cssClass="form-control nonEditable"
												onkeypress="return alphaNumericwithSplCharNew(event);"
												value="%{additionalDetailsBean.ref2City}"
												onpaste="return false;" maxlength="50"
												ondragstart="return false;" ondrop="return false;"
												autocomplete="anyrandomstring"></s:textfield>
										</div>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label"> Pincode<span
											class="manadetory-fields">*</span>
										</label>
										<s:textfield id="pinCode2"
											name="additionalDetailsBean.ref2Pincode"
											cssClass="form-control nonEditable" maxlength="6"
											onkeypress="return numbersonly(event);"
											onpaste="return false;"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="anyrandomstring"></s:textfield>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Email ID <span
											class="tamil"> <s:text name="login.emailId" />
										</span><span class="manadetory-fields">*</span></label>
										<s:textfield id="ref2EmailAddress"
											name="additionalDetailsBean.ref2EmailAddress"
											onpaste="return false;"
											onkeypress="return alphaNumericwithSplCharEmail(event);"
											cssClass="form-control nonEditable" size="15" maxlength="50"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="off" />
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label ">Telephone number/
											International Phone Number/Mobile number (If international
											number, please enter number along with country code)<span
											class="manadetory-fields">*</span>
										</label>
										<s:textfield id="ref2Contact"
											cssClass="form-control nonEditable" onpaste="return false;"
											maxlength="15" name="additionalDetailsBean.ref2Contact"
											onkeypress="return numericWithHyphenOnly(event);"
											ondragstart="return false;" ondrop="return false;"
											pattern="[0-9]{2,4}-[0-9]{8,10}"
											autocomplete="anyrandomstring"></s:textfield>
										<span class="Smallnote">(Country code 2-4 digits and
											mobile number 8-10 digits only separated by hyphen, without
											leading 0)</span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12">
									<div class="font12 WeightBold orgNote mb0">
										<span> <b>Note:</b> At least one of the two Referees
											should be Academician. Candidates shortlisted for interview
											will be required to furnish two Referee Reports. The format
											for this Referee Reports will be sent to the Referees as per
											the details given by the candidates in the Application Form
											for the shortlisted candidates selected for interview. Hence
											give correct email id and contact details of the referees
										</span>
									</div>
								</div>
							</div>
						</div>
						<h3 title="Statement of Purpose">Statement of Purpose</h3>
						<div class="accordion">
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Write your "Statement of
											Purpose" in not more than 200 words indicating why you want
											to join the Ph.D Programme at the NCFE and what are your
											future research plans <span class="manadetory-fields">*</span>
										</label>
										<s:textfield id="stmtOfPurpose" rows="4" cols="45"
											cssClass="form-control nonEditable" onpaste="return false;"
											name="additionalDetailsBean.stmtOfPurpose" maxlength="1000"
											onkeypress="return alphaNumericwithSplCharNew(event);"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="anyrandomstring"></s:textfield>
									</div>
								</div>
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label mb20">Any other
											information that you think will help us to better assess your
											performance and potential<span class="manadetory-fields"></span>
										</label>
										<s:textfield id="otherInfo" rows="4" cols="45"
											cssClass="form-control nonEditable" onpaste="return false;"
											name="additionalDetailsBean.otherInfo" maxlength="1000"
											onkeypress="return alphaNumericwithSplCharNew(event);"
											ondragstart="return false;" ondrop="return false;"
											autocomplete="anyrandomstring"></s:textfield>
									</div>
								</div>
							</div>
						</div>
					</s:if>
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
								method="saveCandidateAdditionalDetails"></s:submit>
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
</s:form>
<div class="fullscreen-container" id="dialog">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onclick="HideDialog()">&times;</button>
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
							onclick="HideDialog()"
							class="ripple1 btn btn-default btn-block mt10" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<s:hidden id='hidConfirnmation' name='hidConfirnmation' value="-1" />