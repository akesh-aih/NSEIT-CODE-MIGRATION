
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function() {
		$("#changeRequest").validationEngine({promptPosition : "bottomLeft", scroll: false});
		$(document).ready(function() { 
    	$("a:contains('Request Change')").html('<span class="fadeSubmenu">Request Change</span>')}); 
		
		});

	function checkValue(){
		var attachmentSignature = $("#attachmentSignature").val();
		var attachmentPhoto = $("#attachmentPhoto").val();

		var remark = $("#remark").val();
		var remarkTrim  = jQuery.trim(remark);
		
		if (attachmentSignature == "" && attachmentPhoto == "" && remarkTrim == ""){
			return "One of the three filed is mandatory";
		}
	}

	
	
</script>
<div class="main-body">

<!-- Fade Container Start -->
<div class="fade" id="pop3"></div>
<!-- Fade Container End -->


<div id="PersonalInfo">

<h1 class="pageTitle" title="Request Change"><s:text name="requestchange.requestChange"/></h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div class="PersonalInfoCont">

    
<div class="tab_container">
    
<!-- Send Update Request Start -->
<s:form method="post" name="register" action="EnrollmentAction" enctype="multipart/form-data" id = "changeRequest">
<div class="errorMessageActive" id="changeRequestError">		
		<s:actionmessage/>
</div>
<s:if test='%{requestChangeDisplayFlag=="true"}'>

<s:token></s:token>
<div class="tab_content">
<!-- Row One Start -->
<div class="padleft40 clear">
<div class="fl-left fifty">
<div class="field-label"><s:text name="requestchange.photograph"/>&nbsp;</div>
<div><s:file name="attachmentPhoto" id="attachmentPhoto" label="Attachment File" cssClass="validate[custom[validImage],groupRequired[payments]] text-input" size="25" errRequired="Please select"/> </div>
<div class="uploadIns"><s:text name="requestchange.uploadimageesize" /></div>
<div class="height10"></div>
<div class="height5"></div>
<!-- <div>
	<s:submit title="Upload" cssClass="submitBtn button-gradient" value="Upload Photo" method="insertCandidateImagesAndSignature" ></s:submit>
</div> -->
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="requestchange.signature"/>&nbsp;</div>

<div><s:file name="attachmentSignature" id = "attachmentSignature" label="Attachment Signature " cssClass="validate[custom[validImage],groupRequired[payments]] text-input" size="25" errRequired="Please select"/></div>
<div class="uploadIns"><s:text name="requestchange.uploadimageesize" /></div>
<div class="height5"></div>
<div class="height10"></div>
<!-- <div><s:submit title="Upload" cssClass="submitBtn button-gradient" value="Upload Signature" method="insertCandidateImagesAndSignature"></s:submit></div> -->
</div>
</div>
<!-- Row One End -->

<!-- Row Two Start -->
<!--
<div class="padleft40 clear">
<div class="fl-left fifty">
<div><select class="mid-dropdown">
<option value="">Select Request</option><option value="Name">Name</option></select>
</div>	
<div class="height10"></div>
</div>
<div class="fl-rigt fifty"><div><textarea class="AddressArea"></textarea></div><div class="height10"></div>
</div>

-->
<!-- Row Two End -->
<div class="clear height20"></div>
<!-- Row Two Start -->
<div class="padleft40 clear">

<!-- for validation message -->
<br/>
<br/>

<div class="field-label"><s:text name="requestchange.other"/>&nbsp;</div>
<s:textarea name="remark"  id="remark" cssClass="AddressArea2 validate[groupRequired[payments]]" errRequired="Please select" />
</div>
<!-- Row Two End -->
<div class="clear height10">&nbsp;</div>
<div class="padleft40 clear">
<input type="button" value="<s:text name="requestchange.back"/>" title="Back" class="submitBtn button-gradient" onclick="window.location='CandidateAction_home.action'" />&nbsp;&nbsp;
<s:submit key="requestchange.sendrequest" title="Send Request" cssClass="submitBtn button-gradient" method="changeCandidateInformationRequest" ></s:submit>
</div>
</div>
</s:if>

<s:if test='%{requestChangeDisplayFlag=="false"}'>
	Request Change allowed only after submitting the enrollment 
</s:if>
</s:form>

</div>

<!-- Send Update Request End -->

    </div>
</div>

</div>

<!-- Box Container End -->
