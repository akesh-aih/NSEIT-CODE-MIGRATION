<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function() {
	
		$("#selectRetestContainer").validationEngine({promptPosition : "bottomLeft", scroll: false});
		
		$(document).ready(function() { 
    	$("a:contains('Request Retest')").html('<span class="fadeSubmenu">Request Retest</span>')}); 
		
		});


	$(document).ready(function() { 
		$("#reasonId").change(
				function (){
				enableReasonArea($(this).val());
				}
				);
		
		});
	function validateReasonArea(){

		alert ("func called");
		var reason = $("#reasonId").val();
		alert 
		return false;
	}


function checkValue(){
	var reason = $("#retestReason").val();

	var reasonTrim  = jQuery.trim(reason);
	if (reasonTrim == ""){
		return "Please enter text in textarea";
	}

	
}
	
	function enableReasonArea(reasonID){
		if (reasonID == 3){
			$("#retestReason").removeAttr("disabled");
			$("#reasonFieldMessage").val("Y");
		}

		if (reasonID == 2 || reasonID == 1){
			$("#retestReason").attr("disabled","true");
			$("#reasonFieldMessage").val("N");
		}
	}
	</script>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>

<div class="main-body">


<!-- Fade Container Start -->
<div class="fade" id="pop3"></div>
<!-- Fade Container End -->



<div id="SelectTest">

<div class="fl-left fifty">
<h1 class="pageTitle" title="Request Retest"><s:text name="requestretest.retest"/></h1>
</div>

<div class="hr-underline2 clear"></div>



<!-- Box Container Start -->
<s:form action="SchedulingAction" id="selectRetestContainer">
<s:hidden name="reasonFieldMessage" id ="reasonFieldMessage" value="N"></s:hidden>
<s:token></s:token>
<div class="errorMessageActive" id="scheduleError">		
				<s:actionmessage/>
			</div>

	<div class="SelectContainer">
<s:if test='%{retestDisplayFlag=="true"}'>
	<s:if test='%{retestStageCompareFlag=="true"}'>

	<!-- Row One Start -->
	<div class="clear">
	<div class="fl-left fifty">
		<div class="field-label">
			<s:text name="requestretest.testcentre"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<s:property value="retestCenterName"/>
		</div>
	</div>
	
	<div class="fl-rigt fifty">
		<div class="field-label">
			<s:text name="requestretest.testdate"/>&nbsp;&nbsp;&nbsp;&nbsp;
			<s:property value="reTestDateStr"/>
		</div>
	</div>
	
	</div>
	<br/>
		<br/>
	<!-- row two -->
	<div class="clear">
			<div class="fl-left fifty">
				<div class="field-label">
					<s:text name="dashboard.discipline"/> : &nbsp;&nbsp;&nbsp;&nbsp;
					<s:property value="testName"/>
				</div>
			</div>
			
			<div class="fl-rigt fifty">
				<div class="field-label">
					<s:text name="dashboard.enrollmentId"/> : &nbsp;&nbsp;&nbsp;&nbsp;
					<s:property value="enrollmentPK"/>
				</div>
			</div>
	
	</div>
	
	<!-- row three -->
	<div class="clear">
		<div class="fl-left fifty">
			<div class="field-label">
				<s:text name="agencyeligiblenoneligible.date"/> : &nbsp;&nbsp;&nbsp;&nbsp;
				<s:property value="testDate"/>
			</div>
		</div>
		
		<div class="fl-rigt fifty">
			<div class="field-label">
				<s:text name="agencyviewattendance.headertestslot"/> : &nbsp;&nbsp;&nbsp;&nbsp;
				<s:property value="startTimeStr"/> - <s:property value="endTimeStr"/>
			</div>
		</div>
	
	</div>
	
	<s:if test='%{requestRetstApprovalFlag=="true"}'>
		<br/>
		<br/>
			<b><s:text name="requestretest.message"/></b>
		<br/>
		<br/>  
	</s:if>
	
	<div class="clear">
	<div class="field-label"><s:text name="requestretest.reason"/>&nbsp;<span class="manadetory-fields">*</span>&nbsp;&nbsp;
		<s:select name="reasonListId"
			headerKey="" cssClass="validate[required]" 
			errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.RETEST_REQUIRE)%>" 
			headerValue="Select Reason" list="remarksDtailsList" key="labelId" id="reasonId" disabled="%{requestRetstApprovalFlag}"
			listValue="labelValue" listKey="labelId" value="%{reasonListId}"/>
	</div>
	
	<!-- for validation message -->
	<br/>
	<br/>
	
	
	
	<div>
	
		 <s:textarea  name="retestReason" id="retestReason"  cssStyle="resize:none;"
		 cssClass="validate[required]" errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.RETEST_REASON_REQUIRE)%>" 
		 rows="8" cols="59" disabled="%{requestRetstApprovalFlag}"/>
	  
	</div>
	<div id="errorID4" class="errorMessage"><s:text name="requestretest.errormessage"/></div>
	
	
	<div class="clear">
	
	<!-- for validation message -->
	<br/>
	
	<input type="button" value="<s:text name="requestretest.back"/>" title="Back" class="submitBtn button-gradient" onclick="window.location='CandidateAction_home.action'"/>&nbsp;&nbsp;
	<s:submit  key="requestretest.submit" title="Submit" cssClass="submitBtn button-gradient" method="insertReTestDetailsForCandidate" disabled="%{requestRetstApprovalFlag}"></s:submit>
	</div>
	
	</div>
	
</s:if>
<s:if test='%{retestStageCompareFlag=="false"}'>
		<s:text name="requestretest.messageforschedulingRetest"/>
</s:if>

</s:if>
	</div>
<s:if test='%{retestDisplayFlag=="false"}'>
	<div class="main-body">
		Retest not applicable
	</div>
</s:if>
</s:form>
<!-- Box Container End -->

</div>

</div>
