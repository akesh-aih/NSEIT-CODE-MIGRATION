<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script>

function uploadDocument()
{
	var attachmentDoc = $("#eligibilityCriteriaUploadFile").val();
	var attachmentDoc1 = $("#eligibilityCriteriaUploadFile1").val();
	var attachmentDoc2 = $("#eligibilityCriteriaUploadFile2").val(); 
	
	if(attachmentDoc == "" && attachmentDoc1== "" && attachmentDoc2== ""){
		//var message = "Please select file to upload "+'<s:property value="docLabel1"/>';
		var message = "Please select alteast one file to upload ";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		return false;
	}else{
		$("#error-massage").hide();
	}
	var t = attachmentDoc.split('.');
	var extn = t[t.length-1];
	var lowrCaseExtn = extn.toLowerCase();
	//if (lowrCaseExtn =="pdf" || lowrCaseExtn =="xls" || lowrCaseExtn =="xlsx" || lowrCaseExtn == "doc" || lowrCaseExtn == "docx"){
		$("#uploadDocs").attr('action',"CandidateAction_insertCandidateMultipleDocuments.action");
		$("#uploadDocs").submit();
		//document.uploadDocs.submit();
	//}else{
	//	$("#test2").show();
	//}
}

$(document).ready(function() {
	var a = $("#documentUploaded").val();
	if($("#documentUploaded").val() == "true")
	{
		$("#uploadedDocuments").show();
	}
	var a = $("#documentUploaded1").val();
	if($("#documentUploaded1").val() == "true")
	{
		$("#uploadedDocuments1").show();
	}
	var a = $("#documentUploaded2").val();
	if($("#documentUploaded2").val() == "true")
	{
		$("#uploadedDocuments2").show();
	}
})
</script>
    
<div class="container">
<div class="main-body">
<div class="fade" id="pop3"></div>

<label id="divDocUploadResponse" style="display: none;"></label>

<div id="dashboard" style="min-height:300px; height:auto;">

<h1 class="pageTitle" title="Personal Details">Upload Document</h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div style="display:block; height:auto;">
<s:form action="CandidateAction" name="uploadDocs" enctype="multipart/form-data" method="post" id="uploadDocs">
<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
<s:hidden name="documentUploaded" id="documentUploaded"/>
<input type="hidden" name="candidateDocPk1" value='<s:property value="candidateDocPk1"/>'/>
<s:actionmessage escape="false" cssClass="msgg"/>

<div id="error-massage" style="display:none">
    <div class="error-massage-text" style="margin:0; margin-left:-34px; padding:0;">
    	<ul style="margin:1; margin-left:20px; padding:2;" id="errorMessages">
    	</ul>
    </div>
</div>

<s:if test="%{#attr.dataNotFound!=''}">
	<div class="error-massage-text">
		<s:property value="#attr.dataNotFound" />
	</div>
</s:if>

  <table class="contenttable">
    <tr>
      <td width="170"><s:text name="docLabel1"/></td>
      <td width="237" ><label for="select17"></label>
        <label for="textfield"></label>
        <s:file  name="data"  id = "eligibilityCriteriaUploadFile"></s:file>
        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
      </td>
      <td width="427" >
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td colspan="2" ><span class="lighttext"><strong>Note : </strong>The  Maximum File Size is <%=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB)%>KB.</span>
      <br />
       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files</span></td>
      
    </tr>
    <tr>
    	<td>&nbsp;</td>
    	<td style="color:#F00;" >
    		Please upload recent Document
    	</td>
    </tr>
    </table>
</div>
<div id="uploadedDocuments" style="display: none;" >
		<table cellspacing="0" cellpadding="0" style="clear:both;" >
		
		    <tr>
		    <td width="303"></td>
		      <td id='docId'><br/>
		      <a href="CandidateAction_getDocument.action?candidateDocId=<s:property value="candidateDocPk1"/>"><s:property value="documentFileName" /></a>
		      </td>
		    </tr>
		    <tr>
		      <td></td>
		      <td ><br/>
		      	<!-- <s:submit method="confirmRegistration" value="Confirm" cssClass="submitBtn button-gradient" 
						onclick="return confirmReg();"></s:submit>
				<input type="button" name="Confirm" id="true" value="Confirm" class="submitBtn button-gradient" onclick="areYouSyre();" />-->
		      </td>
		    </tr>
	  	</table>
<div id='block7'></div>
</div>
<div style="display:block; height:auto;">
<s:if test='%{showDocFirst=="A"}'>
<s:hidden name="documentUploaded1" id="documentUploaded1"/>
<input type="hidden" name="candidateDocPk2" value='<s:property value="candidateDocPk2"/>'/>
  <table class="contenttable">
    <tr>
      <td width="170"><s:text name="docLabel2"/></td>
      <td width="237" ><label for="select17"></label>
        <label for="textfield"></label>
        <s:file  name="data1"  id = "eligibilityCriteriaUploadFile1"></s:file>
        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
      </td>
      <td width="427">
   
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td colspan="2" ><span class="lighttext"><strong>Note : </strong>The  Maximum File Size is <%=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB)%>KB.</span>
      <br />
       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files</span></td>
      
    </tr>
    <tr>
    	<td>&nbsp;</td>
    	<td style="color:#F00;" >
    		Please upload recent Document
    	</td>
    </tr>
    </table>
</div>
<div id="uploadedDocuments1" style="display: none;" >
		<table cellspacing="0" cellpadding="0" style="clear:both;" >
		
		    <tr>
		    <td width="303"></td>
		      <td id='docId'><br/>
		      <a href="CandidateAction_getDocument.action?candidateDocId=<s:property value="candidateDocPk2"/>"><s:property value="documentFileName1" /></a>
		      </td>
		    </tr>
		    <tr>
		      <td></td>
		      <td ><br/>
		      	<!-- <s:submit method="confirmRegistration" value="Confirm" cssClass="submitBtn button-gradient" 
						onclick="return confirmReg();"></s:submit>
				<input type="button" name="Confirm" id="true" value="Confirm" class="submitBtn button-gradient" onclick="areYouSyre();" />-->
		      </td>
		    </tr>
	  	</table>
<div id='block7'></div>
</div>
</s:if>
<div style="display:block; height:auto;">

<s:if test='%{showDocSecond=="A"}'>
<s:hidden name="documentUploaded2" id="documentUploaded2"/>
<input type="hidden" name="candidateDocPk3" value='<s:property value="candidateDocPk3"/>'/>
  <table class="contenttable">
    <tr>
      <td width="170"><s:text name="docLabel3"/></td>
      <td width="237" ><label for="select17"></label>
        <label for="textfield"></label>
        <s:file  name="data2"  id = "eligibilityCriteriaUploadFile2"></s:file>
        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
      </td>
      <td width="427" >
      </td>
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td colspan="2" ><span class="lighttext"><strong>Note : </strong>The  Maximum File Size is <%=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB)%>KB.</span>
      <br />
       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files</span></td>
      
    </tr>
    <tr>
    	<td>&nbsp;</td>
    	<td style="color:#F00;" >
    		Please upload recent Document
    	</td>
    </tr>
    </table>
</div>
<div id="uploadedDocuments2" style="display: none;" >
		<table cellspacing="0" cellpadding="0" style="clear:both;" >
		
		    <tr>
		    <td width="303"></td>
		      <td id='docId'><br/>
		      <a href="CandidateAction_getDocument.action?candidateDocId=<s:property value="candidateDocPk3"/>"><s:property value="documentFileName2" /></a>
		      </td>
		    </tr>
		    <tr>
		      <td></td>
		      <td ><br/>
		      </td>
		    </tr>
	  	</table>
<div id='block7'></div>
</div>
</s:if>
 <input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" onclick="uploadDocument();" />
</s:form>
<s:form>
<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
<input type="hidden" name="isDataFound" value='<s:property value="dataFound"/>'/>
	<div align ="right">
		<s:if test='%{documentMandatory}'>
			<s:submit method="updateCandidateStage" value="Confirm & Continue" cssClass="submitBtn button-gradient" ></s:submit>
		</s:if><s:else>
			<s:submit method="updateCandidateStage" value="Continue" cssClass="submitBtn button-gradient" ></s:submit>
		</s:else>
	</div>
</s:form>
</div>
