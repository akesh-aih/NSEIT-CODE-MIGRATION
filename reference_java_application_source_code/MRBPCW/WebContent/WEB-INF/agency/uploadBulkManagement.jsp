<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<div class="container">
<s:form action="UploadManagementAction_bulkUpload.action" enctype="multipart/form-data" method="post" id="bulkUpload">
<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
<input type="hidden" name = "userIdForImage" value="<s:property value = "userIdForImage"/>" id = "test"/>
<div class="fade" id="block7">
</div>

<div class="fade" id="pop3"></div>
<div id="dashboard" style="display:block; min-height:300px; height:auto;">

<h1 class="pageTitle" title="Upload Photo / Signature">Upload Photo / Signature</h1>
<div class="hr-underline2"></div>
<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;" >
  <table class="contenttable">
  <tr><td colspan="3">
  <s:if test="hasActionMessages()">
   <div id="error-massage">
   <div class="error-massage-text" style="margin:0; margin-left:-34px; padding:0;">
    	<ul style="margin:1; margin-left:-23px; padding:2;" id="error-ulUploadForm">
    	<s:actionmessage cssStyle="color:red;"/>
    </ul>
  </div>
  </div>
  </s:if>
  
  </td></tr>
  <table class="contenttable">
   <tr> 
    	<td colspan="2">
	     <s:checkbox name="imageType" fieldValue="Image" label="Photograph"/>Photograph
	     <s:checkbox name="signType" fieldValue="Sign" label="Photograph"/>Signature
	     <%--<s:checkbox name="candidateDetailsType" fieldValue="CandDtls" label="Photograph"/>Candidate Details
	     --%><br/><br/><br/>
	      <s:file  name="fileUpload"  id = "uploadFile"></s:file>&nbsp;&nbsp;<s:submit type="submit" value="Upload" cssClass="submitBtn news-gradient"/>&nbsp;
    	</td>
    </tr>
    <tr>
    	<td style="background:#E5E0CC;"><strong>The  Maximum Image Size is <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_IMAGE_SIZE_IN_KB)%>KB.</strong></td>
    	<td style="background:#E5E0CC;"><strong>The  Maximum Signature Size is <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_SIGNATURE_SIZE_IN_KB)%>KB.</strong></td>
	</tr>
	<tr>
    	<td style="background:#E5E0CC;"><strong>Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.IMAGE_TYPE).toLowerCase()%> images are accepted</strong></td>
    	<td style="background:#E5E0CC;"><strong>Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SIGNATURE_TYPE).toLowerCase()%> signature are accepted</strong></td>
	</tr>
    <tr><td colspan="2">
    <s:set var="webFramework" value="uploaded"/>
     <s:if test="%{#webFramework=='true'}">
    	<table width="100%">
    		<tr><td colspan="2"><div class="hr-underline2"></div></td></tr>
    		<tr>
		    	<td style="background:#ccc;"><strong>Image Upload Details</strong></td>
		    	<td style="background:#ccc;"><strong>Signature Upload Details</strong></td>
		    </tr>
		    <tr>
		    	<td style="background:#eee;" width="50%">
		    		<div style="height:200px; overflow:auto;">Image Uploaded Count :: <s:property value="imageCount"/></br>
		    			<s:property value="imageSummary" escape="false"/>
		    		</div></td>
		    	<td style="background:#eee;" width="50%">
		    		<div style="height:200px; overflow:auto;">Signature Uploaded Count :: <s:property value="signCount"/></br>
		    			<s:property value="signSummary" escape="false"/>
		    		</div></td>
		    		<s:property value="message" escape="false"/>
		    </tr>
		    <tr>
		    	<td colspan="2" align="center"><strong>Total Time On Execution :: </strong><s:property value="executionTime"/></td>
		    </tr>
    	</table>
    </s:if>
    </td></tr>
   <div>
   </div>
    </table>    
</div>

</div>
</s:form>
</div>