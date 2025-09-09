<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">
	<script type="text/javascript">
	
		function frm_onsubmit(){

			var uploadType = $("#uploadType").val();
			var uploadFile = $("#uploadFile").val();
			var message = "";
			var categoryChecked = false;
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
			}

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

	<s:form action="UploadManagementAction_uploadMarksCSV.action" enctype="multipart/form-data" method="post" id="uploadMarksCSV" onsubmit="return frm_onsubmit();">
		<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
		<input type="hidden" name = "userIdForImage" value="<s:property value = "userIdForImage"/>" id = "test"/>
		<div class="fade" id="block7"></div>
		<div class="fade" id="pop3"></div>
		<div id="dashboard" style="display:block; min-height:300px; height:auto;">
			<h1 class="pageTitle" title="Upload Candidate Marks">Upload Candidate Marks</h1>
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
						<td colspan="3">
							<s:radio label="Upload Type" name="uploadType"  cssClass="categoryClass" list="#{'GD':'Group Discussion','PI':'Personal Interview'}"/><br/><br/><br/>
							<s:file  name="fileUpload" id = "uploadFile"></s:file>
							<s:submit type="submit" value="Upload" cssClass="submitBtn news-gradient"/>
						</td></tr>
					<tr>
						<td colspan="3" width="100%">
    						<s:set var="webFramework" value="uploaded"/>
     						<s:if test="%{#webFramework=='true'}">
				    			<table width="100%">
				    				<tr>
				    					<td widhth="100%"><div class="hr-underline2"></div></td></tr>
				    				<tr>
								    	<td widhth="100%" style="background:#ccc;"><strong>Updated Marks Details</strong></td></tr>
								    <tr>
								    	<td widhth="100%" style="background:#eee;" colspan="2">
								    		<table width="100%" style="background:#eee;">
								    			<tr width="100%" style="background:#eee;">
								    				<td width="40%" style="height:15px; overflow:auto;background:#eee;">Total number of rows available for update</td>
								    				<td width="60%" style="height:15px; overflow:auto;background:#eee;"><s:property value="csvRecordCountTotal"/></td>
								    			</tr>
								    			<tr widhth="100%" style="background:#eee;">
								    				<td width="40%" style="height:15px; overflow:auto;background:#eee;">Total number of rows updated successfully</td>
								    				<td width="60%" style="height:15px; overflow:auto;background:#eee;"><s:property value="csvRecordCountSuccess"/></td>
								    			</tr>
								    			<tr widhth="100%" style="background:#eee;">
								    				<td width="40%" style="height:15px; overflow:auto;background:#eee;">Total number of rows not updated</td>
								    				<td width="60%" style="height:15px; overflow:auto;background:#eee;"><s:property value="csvRecordCountFailure"/></td>
								    			</tr>
								    		</table>
								    	</td></tr>
				    				<tr>
				    					<td widhth="100%" style="background:#ccc;"><strong>Error Uploading Marks</strong></td></tr>
				    				<tr>
				    					<td widhth="100%" style="background:#eee;" >
				    						<div style="height:105px; overflow:auto;">
				    							<s:property value="marksSummary" escape="false"/>
				    						</div>	
				    					</td></tr>
				    				<tr>
				    					<td align="center"><strong>Total Time On Execution :: </strong><s:property value="executionTime"/></td></tr>
		    					</table>
		    				</s:if>
		 				</td>
		 			</tr>
				</table>    
			</div>
		</div>
	</s:form>
</div>