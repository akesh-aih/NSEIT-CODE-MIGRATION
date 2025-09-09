<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">
<script type="text/javascript">

	function frm_onsubmit(){
		var uploadType = $("#uploadType").val();
		var uploadFile = $("#uploadFile").val();
		var message = "";

		if(uploadFile == ""){
			message = message + "Please select a File to Upload."+"##";
		}else{
			var extension = uploadFile.substr( (uploadFile.lastIndexOf('.') +1) );
			extension = extension.toLowerCase();
			if(extension!="csv"){
				message = message + "Please select a CSV file for upload."+"##";
			}
		}
		
		if(message != ""){
			var ulID = "error-ul1";
			var divID = "error-massage1";
			createErrorList(message, ulID, divID);
			return false;
		}else{
			return true;
		}
		
	}
</script>

<s:form action="UploadManagementAction_uploadOfflinePayments.action" enctype="multipart/form-data" method="post" id="uploadOfflinePayments"  onsubmit="return frm_onsubmit();">
<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>

<input type="hidden" name = "userIdForImage" value="<s:property value = "userIdForImage"/>" id = "test"/>
<div class="fade" id="block7"></div>
<div class="fade" id="pop3"></div>
<div id="dashboard" style="display:block; min-height:300px; height:auto;">

<h1 class="pageTitle" title="Upload Offline Payments">Upload Offline Payments</h1>
<div class="hr-underline2"></div>
<s:if test="hasActionMessages()">
   <div id="error-massage1">
	<div class="error-massage-text">
		<ul style="margin:0; margin-left:-20px; padding:0;" id="error-ul1">
			<s:actionmessage cssStyle="color:red"/>	
		</ul>
	</div>
	</div>
</s:if>
<div id="error-massage1" style="display:none">
	<div class="error-massage-text">
		<ul style="margin:0; margin-left:0px; padding:0;" id="error-ul1"></ul>
	</div>
</div>
<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;" >
	<table class="contenttable">
		<tr><td colspan="3">
			<div id="error-massage" style="display:none">
			<div class="error-massage-text">
				<ul style="margin:0; margin-left:20px; padding:0;" id="errorMessages"></ul>
			</div>
			</div>
		</td></tr>
		<tr>
			<s:file  name="fileUpload" id = "uploadFile"></s:file>
			<s:submit type="submit" value="Upload" cssClass="submitBtn news-gradient"/>
		</tr>
		<tr>
			<td colspan="2">
    			<s:set var="webFramework" value="uploaded"/>
     			<s:if test="%{#webFramework=='true'}">
    			<table width="100%">
    				<tr>
    					<td colspan="1"><div class="hr-underline2"></div></td></tr>
    				<tr>
				    	<td style="background:#ccc;"><strong>Updated Payment Details</strong></td></tr>
				    <tr>
				    	<td style="background:#eee;" >
				    		<div style="height:20px; overflow:auto;">Updated Record Count :: <s:property value="csvRecordCountSuccess"/>
				    		</div>
				    	</td></tr>
    				<tr>
				    	<td style="background:#ccc;"><strong>Error Uploading Payments</strong></td></tr>
				    <tr>
				    	<td style="background:#eee;" >
				    	<div style="height:200px; overflow:auto;">
				    		<s:property value="paymentSummary" escape="false"/>
				    	</div>	
				    	</td></tr>
				    <tr>
				    	<td colspan="1" align="center"><strong>Total Time On Execution :: </strong><s:property value="executionTime"/></td></tr>
		    	</table>
		    	</s:if>
		 	</td>
		 </tr>
	</table>    
</div>
</div>
</s:form>
</div>