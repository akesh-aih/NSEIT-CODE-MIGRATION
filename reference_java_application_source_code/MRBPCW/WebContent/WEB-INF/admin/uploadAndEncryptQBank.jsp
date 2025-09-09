<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
		
	})
	
	function checkExtn(){

		var filetype = document.getElementById("attachedQbank").value;

		var extn = filetype.split (".");

				//alert ("extn --->  "+extn[extn.length-1]);

		var valid_extensions = /(.docx|.doc|.pdf|.xls|.xlsx|.htm|.html)$/i;
		$("#fileNameForUpload").val(extn[extn.length-1]);
		
	}
		
	
</script>
	
<div class="container">
<s:form id="uploadEncryptForm" name="uploadEncryptForm" action="AdminAction" enctype="multipart/form-data">
<s:hidden name="fileNameForUpload" id="fileNameForUpload"></s:hidden>
	<div class="main-body">
		<div class="fade" id="pop3"></div>
		<div id="dashboard">

			<h1 class="pageTitle" title="Photograph Upload">Encrypt Q-Bank</h1>
				<div class="hr-underline2"></div>
				
				<!-- Box Container Start -->
				<div style="display:block; min-height:300px; height:auto;">
				  <div id=""><table class="contenttable">
				    <tr>
				      <td width="292">File</td>
				      <td width="614" colspan="2"><s:file name="attachedQbank" id="attachedQbank" label="Attachment QBank " size="25"/> </td>
				    </tr>
				    <tr>
				      <td>Password</td>
				      <td colspan="2"><s:password name="qbankPassword" id="qbankPassword" /></td>
				    </tr>
				    <tr>
				      <td>&nbsp;</td>
				      <td colspan="2"><s:submit name="btnUpload" id="btnUpload" value="Upload" method="uploadQBank" cssClass="submitBtn button-gradient" onclick="checkExtn();"/>
				        <input type="submit" name="btnBack" id="btnBack" value="Back" class="submitBtn button-gradient" /></td>
				    </tr>
				    </table></div>
				  <br />
				  
				  <s:if test='%{fileUploaded=="Y"}'>
				  <div id="" style="border:1px solid #999;">
				  <table class="contenttable">
				    <tr>
				      <td height="45" align="center" class="fadeSubmenu"><strong><br />
				        File Uploaded and Encrypted successfully</strong></td>
				      </tr>
				    
				    <tr>
				      <td height="40" align="center"><a href="AdminAction_downloadEncryptedFile.action">Download Encrypted File</a></td>
				    </tr>
				  </table>
				  </div>
				  </s:if>
				</div>
				<br />
			
		</div>
	</div>
</s:form>
</div>
