<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">
	<script type="text/javascript">
	
		function frm_onsubmit(){

			var uploadType = $("#uploadType").val();
			var uploadFile = $("#uploadFile").val();
			var message = "";
			/*var categoryChecked = false;
			var value = "";
			$(".categoryClass").each(function(){
				if($(this).is(':checked')){
					var id = $(this).attr("id");
					categoryChecked = true;
					 value = $('label[for='+id+']').text();
				}
			});
	
			if(categoryChecked == false){
				message = message + "Please select an upload type."+"##";
			}*/

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
		
		function exportTemplate()
{
		window.open("UploadManagementAction_downloadWrittenTestTemplate.action");
		return false;
}  
	</script>

	<s:form action="UploadManagementAction_uploadWrittenTestScoreCSV.action" enctype="multipart/form-data" method="post" id="uploadWrittenTestScoreCSV" onsubmit="return frm_onsubmit();">
		<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
		<s:hidden name="userIdForImage" value="%{userIdForImage}" id = "test"></s:hidden>
		<div class="fade" id="block7"></div>
		<div class="fade" id="pop3"></div>
		<div id="dashboard" style="display:block; min-height:300px; height:auto;">
			<h1 class="pageTitle" title="Upload Candidate Written Test Score"><s:text name="agencyupload.uploadWrittenScore"/></h1>
			<div class="hr-underline2"></div>
			<s:if test="hasActionMessages()">
			   <div id="error-massage1">
				<div class="error-massage-text">
					<ul style="margin:0; margin-left:15px; padding:0;" id="error-ul1">
						<s:actionmessage cssStyle="color:red"/>	
					</ul>
				</div>
				</div>
			</s:if>
			<div id="error-massage1" style="display:none">
				<div class="error-massage-text">
					<ul style="margin:0; margin-left:15px; padding:0;" id="error-ul1"></ul>
				</div>
			</div>
			<!-- Box Container Start -->
			<div style="display:block; min-height:300px; height:auto;" >
				<table class="contenttable" width="100%">
					<tr>
						<td colspan="3">
							<div id="error-massage" style="display:none">
								<div class="error-massage-text">
									<ul style="margin:0; margin-left:20px; padding:0;" id="errorMessages"></ul>
								</div>
							</div>
						</td></tr>
					<tr>
						<td colspan="2">
							<s:file  name="fileUpload" id = "uploadFile"></s:file>
							<s:submit type="submit" value="Upload" cssClass="submitBtn button-gradient"/>
						</td>
						<td>
						<a href="javascript:void(0)" onclick="exportTemplate()" class="LinkAlignMiddle" ><s:text name="agencyupload.downloadWrittenScoreTemplate"/></a>
						</td>
					</tr>
					<tr>
						<td colspan="3" width="100%">
    						<s:set var="webFramework" value="uploaded"/>
     						<s:if test="%{#webFramework=='true'}">
				    			<table width="100%">
				    				<tr>
				    					<td widhth="100%"><div class="hr-underline2"></div></td></tr>
				    				<tr>
								    	<td widhth="100%" style="background:#56b4a2; color:#fff;"><strong><s:text name="agencyupload.updatedWrittenScore"/></strong></td></tr>
								    <tr>
								    	<td widhth="100%" style="background:#effbf9;" colspan="2">
								    		<table width="100%"  >
								    			<tr  ">
								    				<td width="40%" style="height:15px; overflow:auto; background:#effbf9; border-bottom:1px solid #bbede3;"><s:text name="agencyupload.noOfRowsAvailable"/></td>
								    				<td width="60%" style="height:15px; overflow:auto; background:#effbf9; border-bottom:1px solid #bbede3;"><s:property value="csvRecordCountTotal"/></td>
								    			</tr>
								    			<tr  >
								    				<td width="40%" style="height:15px; overflow:auto; background:#effbf9; border-bottom:1px solid #bbede3;"><s:text name="agencyupload.noOfRowsUpdated"/></td>
								    				<td width="60%" style="height:15px; overflow:auto; background:#effbf9; border-bottom:1px solid #bbede3;"><s:property value="csvRecordCountSuccess"/></td>
								    			</tr>
								    			<tr  >
								    				<td width="40%" style="height:15px; overflow:auto; background:#effbf9; border-bottom:1px solid #bbede3;"><s:text name="agencyupload.noOfRowsNotUpdated"/></td>
								    				<td width="60%" style="height:15px; overflow:auto; background:#effbf9; border-bottom:1px solid #bbede3;"><s:property value="csvRecordCountFailure"/></td>
								    			</tr>
								    		</table>
								    	</td></tr>
				    				<tr>
				    					<td widhth="100%" style="background:#56b4a2; color:#fff;"><strong><s:text name="agencyupload.errorWrittenScore"/></strong></td></tr>
				    				<tr>
				    					<td widhth="100%" style="background:#effbf9;  " >
				    						<div style="height:105px; overflow:auto;">
				    							<s:property value="marksSummary" escape="false"/>
				    						</div>	
				    					</td></tr>
				    				<tr>
				    					<td align="center"><strong><s:text name="agencyupload.totalTimeOnExecution"/> :: </strong><s:property value="executionTime"/></td></tr>
		    					</table>
		    				</s:if>
		 				</td>
		 			</tr>
				</table>    
			</div>
		</div>
		<s:token />
	</s:form>
</div>