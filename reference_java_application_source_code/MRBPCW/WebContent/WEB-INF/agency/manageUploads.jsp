<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<script>
	function CheckExtnForEligibilityCriteriaUploadFile()
	{
		var filetype = document.getElementById("eligibilityCriteriaUploadFile").value;
		
		var message="";
		if (filetype==""||filetype==''){
			message = message + "Please select a File for upload."+"##";
		}
		if(message != ""){
			var ulID = "errorMessages";
			var divID = "error-massage";
			createErrorList(message, ulID, divID); 
			return false;
		}
		else{
			var valid_extensions = /(.docx|.doc| .pdf|.xls|.xlsx|.htm|.html)$/i;
			document.getElementById("fileNameEligibility").value = filetype;
			return true;
		}
		
	}

	function CheckExtnForTestInstructionUploadFile()
	{
	
		var filetype = document.getElementById("testInstructionUploadFile").value;
		var message="";
		if (filetype==""||filetype==''){
			message = message + "Please select a File for upload."+"##";
		}
		if(message != ""){
			var ulID = "errorMessages2";
			var divID = "error-massage2";
			createErrorList(message, ulID, divID); 
			return false;
		}else{
			var valid_extensions = /(.docx|.doc|.pdf|.xls|.xlsx|.htm|.html)$/i;
			document.getElementById("fileNameTestInstr").value = filetype;
			return true;
		}
	}

	function CheckExtnForFAQUploadFile()
	{
		var filetype = document.getElementById("faqUploadFile").value;
		var message="";
		if (filetype==""||filetype==''){
			message = message + "Please select a File for upload."+"##";
		}
		if(message != ""){
			var ulID = "errorMessages2";
			var divID = "error-massage2";
			createErrorList(message, ulID, divID); 
			return false;
		}
		else{
			var valid_extensions = /(.docx|.doc|.pdf|.xls|.xlsx|.htm|.html)$/i;
			document.getElementById("fileNameFaq").value = filetype;
			return true;
		}
	}
	
</script>
    
<div class="container">
<div class="main-body">
<div class="fade" id="pop3"></div>
<div id="dashboard">

<h1 class="pageTitle" title="Personal Details">Upload Management</h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;">
<s:form action="SettingsAction" enctype="multipart/form-data" >
<s:hidden name="fileNameEligibility" id = "fileNameEligibility"> </s:hidden>
<s:hidden name="fileNameFaq" id = "fileNameFaq"> </s:hidden>
<s:hidden name="fileNameTestInstr" id = "fileNameTestInstr"> </s:hidden>

<div id="error-massage" style="display:none">
    <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="errorMessages">
    	</ul>
    </div>
</div>

<div id="error-massage2" style="display:none">
    <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="errorMessages2">
    	</ul>
    </div>
</div>

  <table class="contenttable">
    <tr>
      <td width="238">Eligibility Criteria</td>
      <td width="237" ><label for="select17"></label>
        <label for="textfield"></label>
        <s:file  name="file2"  id = "eligibilityCriteriaUploadFile"></s:file>
      </td>
      <td width="427" >
      <s:submit value="Upload" cssClass="submitBtn news-gradient" method="insertSettingDetailsForEligibility" onclick="return CheckExtnForEligibilityCriteriaUploadFile();"></s:submit>&nbsp;
      <s:submit value="Download" cssClass="submitBtn news-gradient" method="downloadSettingDetailsForEligibility"></s:submit>
    
      </td>
    </tr>
    
    
    <tr>
      <td>Instruction</td>
      <td ><s:file  name="file3"  id = "testInstructionUploadFile"></s:file></td>
      <td > <s:submit value="Upload" cssClass="submitBtn news-gradient" method="insertSettingDetailsForTestInstruction" onclick="return CheckExtnForTestInstructionUploadFile();"></s:submit>&nbsp;
        <s:submit value="Download" cssClass="submitBtn news-gradient" method="downloadSettingDetailsForTestInstruction" ></s:submit>
      
        </td>
    </tr>
    <tr>
      <td>FAQ</td>
      <td ><s:file  name="file4"  id = "faqUploadFile"></s:file></td>
      <td ><s:submit value="Upload" cssClass="submitBtn news-gradient" method="insertSettingDetailsForTestFAQ" onclick="return CheckExtnForFAQUploadFile();"></s:submit> &nbsp;
        <s:submit value="Download" cssClass="submitBtn news-gradient" method="downloadSettingDetailsForFAQ" ></s:submit> 
       
       </td>
        
    </tr>
    <tr>
      <td>&nbsp;</td>
      <td colspan="2" ><span class="lighttext"><strong>Note : </strong>You can upload .pdf, .xls,.xlsx, .doc and .docx files</span></td>
    </tr>
    </table>
    </s:form>
</div>

</div></div>
