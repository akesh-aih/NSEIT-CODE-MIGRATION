
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">


//function 

$(document).ready(function(){

	<s:if test="%{#session['SESSION_USER'].stage >= 4}">
		$("#btnPaymentPrev").attr('disabled', 'disabled');
	</s:if>

	<s:if test="%{#session['SESSION_USER'].stage >= 4}">
	$(".enrollFinal").each(function(currIndex, currElement) {
		$(currElement).attr('disabled', 'disabled');
	});
	</s:if>
	
	$("#btnFinalSubmit").click(function() {

		var ans = confirm ("Once the Enrollment form is submitted, it will not be allowed to be modified.\n Do you want to submit?");	
		$("#frmTestEnrollment").submit();	
	});
	
	$(".btnEditClass").click(function() {
		var btnId = $(this).attr("id")+"";
		
		if(btnId=="btnDiscipline")
		{
			$("#currSectionToDisplay").val("1");
		}
		else if(btnId=="btnPersonalDetails")
		{
			$("#currSectionToDisplay").val("2");
		}
		else if(btnId=="btnAcademicDetails")
		{
			$("#currSectionToDisplay").val("3");
		}
		else if(btnId=="btnImageDetails")
		{
			$("#currSectionToDisplay").val("4");
		}
		else if(btnId=="btnVenueDetails")
		{
			$("#currSectionToDisplay").val("5");
		}
	});

	$("#btnPrint").click(function() {
		//prePrint();
	//	window.print();
		//postPrint();
		
		$("#frmTestEnrollment").attr('action',"ReportAction_printEnrollments.action");
		$("#frmTestEnrollment").submit();
		
	});
});
</script>

<div class="main-body clear">

<!-- Fade Container Start -->
<div class="fade" id="pop3"></div>
<!-- Fade Container End -->

<s:form action="EnrollmentAction_updateFinalDetails" name="frmTestEnrollment" id="frmTestEnrollment">
	<s:token></s:token>
</s:form>

<iframe id="iframeForPrint" style="height: 0px; width: 0px; position: absolute"></iframe>


<table cellpadding="0" cellspacing="0" border="0" width="100%">
<s:hidden name="enrolValue" id="enrollValue"></s:hidden>
<tr><td>
<div id="PersonalInfo">

<h1 class="pageTitle" title="Test Enrollment">
	<s:if test="%{actionName=='preview'}">
		<s:text name="preview.previewenrollment"/>
	</s:if>
	<s:elseif test="%{actionName=='view'}">
		<s:text name="preview.viewenrollment"/>
	</s:elseif>
</h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<s:form action="EnrollmentAction">
<s:token></s:token>
<div class="PersonalInfoCont">

<s:hidden id="hddDisciplineId" name="hddDisciplineId" value="%{disciplineId}"></s:hidden>
<s:hidden id="currSectionToDisplay" name="currSectionToDisplay"></s:hidden>
<s:hidden id="actionName" name="actionName" value="%{actionName}"></s:hidden>

<!-- Discipline Start -->
<fieldset>
<legend><s:label key="preview.disciplineframe" /></legend>
<div class="height10 clear"></div>
<div class="padleft40">
	<div class="fl-left preCentreWidth">
		<div class="field-label"><s:text name="selectdiscipline.discipline"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{testName}" ></s:label></span></div>
	
		<div class="height10"></div>
	</div>
</div>

<s:if test="%{!(#request['CANDIDATE_STAGE']>=4 || enrollmentDisplayFlag=='false')}">
	<div class="padleft40">
		<div class="fl-center preCentreWidth">
			<s:submit key="preview.edit" method="editDetails" id="btnDiscipline" cssClass="submitBtn button-gradient btnEditClass"></s:submit>
		</div>
	</div>
</s:if>
<div class="height10"></div>
</fieldset>
<!-- Discipline End -->
<!-- ----------------------------------------------------------------------------------------------------------------------------------- -->

<!-- Personal Details Start -->
<fieldset>
<legend><s:label key="preview.personalinfoframe" /></legend>
<!-- Row One Start -->
<div class="padleft40">
	<div class="fl-left fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfo.firstname"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{firstName}"></s:label></span></div>
		
		<div class="height10"></div>
	</div>

	<div class="fl-rigt fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfo.lastname"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{lastName}"></s:label></span></div>
		
		<div class="height10"></div>
	</div>
</div>
<!-- Row One End -->

<!-- Row Two Start -->
<div class="padleft40">
	<div class="fl-left fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfo.fathersname"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{fatherName}"></s:label></span></div>
		
		<div class="height10"></div>
	</div>

	<div class="fl-rigt fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfo.gender"/>&nbsp;:&nbsp;<span class="fieldValue"><s:property value="gender"/></span></div>
		
		<div class="height10"></div>
	</div>
</div>
<!-- Row Third End -->

<!-- Row Forth Start -->
<div class="padleft40">
	<div class="fl-left fifty">
		<div class="height10"></div>
	</div>

	<div class="fl-rigt fifty">
	</div>
</div>
<!-- Row Forth End -->

<div class="field-label clear"><s:text name="viewupdatepersonalinfo.contactdetails"/></div>
<div class="hr-dashline"></div>

<!-- Row Five Start -->
<div class="padleft40 clear">
	<div class="fl-left fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfo.permanentaddress"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{address}"></s:label></span></div>
		
		<div class="height10"></div>
	</div>

	<div class="fl-rigt fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfo.contactaddress"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{AlternateAddress}"></s:label></span></div>
		
		<div class="height10"></div>
	</div>
</div>
<!-- Row Five End -->

<!-- Row Six Start -->
<div class="padleft40 clear">
	<div class="fl-left fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfoperaddress.state"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{stateName}"></s:label></span></div>
		<div class="height10"></div>
	</div>

	<div class="fl-rigt fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfoaltaddress.state"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{altStateName}"></s:label></span></div>
		<div class="height10"></div>
	</div>
</div>
<!-- Row Six End -->

<!-- Row Seven Start -->
<div class="padleft40 clear">
	<div class="fl-left fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfoperaddress.city"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{cityName}"></s:label></span></div>
		
		<div class="height10"></div>
	</div>

	<div class="fl-rigt fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfoaltaddress.city"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{altCityName}"></s:label></span></div>
		
		<div class="height10"></div>
	</div>
</div>
<!-- Row Seven End -->

<!-- Row Seven Start -->
<div class="padleft40 clear">
	<div class="fl-left fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfoperaddress.pincode"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{pincode}"></s:label></span></div>
		<div class="height10"></div>
	</div>


	<div class="fl-rigt fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfoaltaddress.pincode"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{alternatePincode}"></s:label></span></div>	
		<div class="height10"></div>
	</div>
</div>
<!-- Row Seven End -->

<!-- Row Eight Start -->
<div class="padleft40 clear">
	<div class="fl-left fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfoperaddress.emailaddress"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{emailAddress}"></s:label></span></div>	
		<div class="height10"></div>
	</div>

	<div class="fl-rigt fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfoperaddress.mobilenumber"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{mobileNO}"></s:label></span></div>	
		<div class="height10"></div>
	</div>
</div>
<!-- Row Eight End -->
<s:if test="%{!(#request['CANDIDATE_STAGE']>=4 || enrollmentDisplayFlag=='false')}">
	<div class="padleft40 clear">
		<div class="f1-center fifty">
			<s:submit key="preview.edit" method="editDetails" id="btnPersonalDetails" cssClass="submitBtn button-gradient btnEditClass"></s:submit>
		</div>
	</div>
</s:if>
<div class="height10"></div>
</fieldset>
<!-- Personal Details End -->
<!-- --------------------------------------------------------------------------------------------------------------------------------------- -->
<!-- Academic Details Start -->
<fieldset>
<legend><s:label key="preview.academicinfoframe" /></legend>

<table id='tblAcademicDetails' width="100%">
<%int currRow = 1; %>
<s:iterator value="academicDetailList"  >
<tr id='rowid<%= currRow %>'>
	<td>
		<div class="hr-dashline"></div>
		
		<!-- Row One Start -->
		<div class="padleft40 clear">
			<div class="fl-left fifty">
				<div class="field-label"><s:text name="viewupdateacademicdetails.qualifiedExam"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{examNameList[0]}"></s:label></span></div>
				<div class="height10"></div>
			</div>
			
			<div class="fl-rigt fifty">
				<div class="field-label"><s:text name="viewupdateacademicdetails.yearOfQualifyingExam"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{selectYear[0]}"></s:label></span></div>
				<div class="height10"></div>
			</div>
		</div>
		<!-- Row One End -->
		
		<!-- Row Two Start -->
		<div class="padleft40 clear">
			<div class="fl-left fifty">
				<div class="field-label"><s:text name="viewupdateacademicdetails.percentageSecured"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{percentage}"></s:label></span></div>
				<div class="height10"></div>
			</div>
			
			<div class="fl-rigt fifty">
			<div class="field-label"><s:text name="viewupdateacademicdetails.boardUniversity"/>&nbsp;:&nbsp;<span class="fieldValue">
				<s:if test="%{otherBoard[0] != null}">
						<s:label value="%{otherBoard[0]}"></s:label>
				</s:if>
				<s:else>
						<s:label value="%{boardUniversityNameList[0]}"></s:label>
				</s:else>
			</span></div>
			<div class="height10"></div>
		</div>
		<!-- Row Two End -->
		</div>
	</td>
</tr>
</s:iterator>
</table>

<s:if test="%{!(#request['CANDIDATE_STAGE']>=4 || enrollmentDisplayFlag=='false')}">
	<div class="padleft40 clear">
		<div class="f1-center fifty">
			<s:submit key="preview.edit" method="editDetails" id="btnAcademicDetails" cssClass="submitBtn button-gradient btnEditClass"></s:submit>
		</div>
	</div>
</s:if>

<div class="height10"></div>
</fieldset>
<!-- Academic Details End -->
<!-- ------------------------------------------------------------------------------------------------------------------------------- -->
<!-- Image Start -->
<fieldset>
<legend><s:label key="preview.imagesframe"></s:label></legend>
	<div class="height10"></div>
	<div class="padleft40 clear">
		<div class="fl-left fifty">
			<div class="field-label"><s:text name="viewupdateimagedetails.photograph"/>&nbsp;</div>
			<div class="height10"></div>
			<div class="uploadImg">
				<img width="200" height="150" src="PhotoImage.jpg" name="inputStreamForImage" id="inputStreamForImage"  />
			</div>
			<div class="height10"></div>
		</div>
		
		<div class="fl-rigt fifty">
			<div class="field-label"><s:text name="viewupdateimagedetails.signature"/>&nbsp;</div>
			<div class="height10"></div>
			<div class="uploadImg">
				<img width="200" height="150" src="SignatureImage.jpg"  name="inputStreamForSignature" id="inputStreamForSignature" />
			</div>
			<div class="height10"></div>
		</div>
	</div>
	
	<s:if test="%{!(#request['CANDIDATE_STAGE']>=4 || enrollmentDisplayFlag=='false')}">
		<div class="padleft40 clear">
			<div class="f1-center fifty">
				<s:submit key="preview.edit" method="editDetails" id="btnImageDetails" cssClass="submitBtn button-gradient btnEditClass"></s:submit>
			</div>
		</div>
	</s:if>
	
	<div class="height10"></div>
</fieldset>
<!-- Image End -->
<!-- --------------------------------------------------------------------------------------------------------------------------------- -->
<!-- Preferred Venue Start -->
<fieldset>
<legend><s:label key="preview.preferredvenuframe"></s:label></legend>
<div class="height10"></div>

<div class="padleft40 clear">
	<div class="fl-left">
		<div class="field-label"><s:text name="venue.firstPreferedTestCentre"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{preferredTestcentre1Name}"></s:label></span></div>
		<div class="height10"></div>
	</div>
</div>

<div class="padleft40 clear">
	<div class="fl-left">
		<div class="field-label"><s:text name="venue.secondPreferedTestCentre"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{preferredTestcentre2Name}"></s:label></span></div>
		<div class="height10"></div>
	</div>
</div>

<div class="padleft40 clear">
	<div class="fl-left">
		<div class="field-label"><s:text name="venue.thirdPreferedTestCentre"/>&nbsp;:&nbsp;<span class="fieldValue"><s:label value="%{preferredTestCentre3Name}"></s:label></span></div>
		<div class="height10"></div>
	</div>
</div>

<s:if test="%{!(#request['CANDIDATE_STAGE']>=4 || enrollmentDisplayFlag=='false')}">
	<div class="padleft40 clear">
		<div class="f1-center fifty">
			<s:submit key="preview.edit" method="editDetails" id="btnVenueDetails" cssClass="submitBtn button-gradient btnEditClass"></s:submit>
		</div>
	</div>
</s:if>
<div class="height10"></div>
</fieldset>
<!-- Preferred Venue Start -->

<div class="height10"></div>
<div class="height10"></div>

<div class="f1-center width100" align="center">
	<s:if test="%{actionName=='preview'}">
		<s:submit key="preview.submit" method="updateFinalDetails" id="btnSubmitEnrollment" cssClass="submitBtn button-gradient"></s:submit>
		&nbsp;&nbsp;&nbsp;&nbsp;
	</s:if>
	
	<input type = "button" value = "Print" class = "submitBtn button-gradient" id="btnPrint"/>
	
</div>

</div>
</s:form>
<!-- Box Container End -->

</div>
</td></tr></table>

</div>
