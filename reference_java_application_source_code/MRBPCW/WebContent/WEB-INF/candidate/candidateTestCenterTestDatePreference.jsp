<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<script>
	
		function validateInput(){
			var message = "";
		
			var preferredTestDate1 = $("#preferredTestDate1").val();
			var preferredTestDate2 = $("#preferredTestDate2").val();
			var preferredTestDate3 = $("#preferredTestDate3").val();
			
			var testCntre1 = $("#testCenter1").val();
			var testCntre2 = $("#testCenter2").val();
			var testCntre3 = $("#testCenter3").val();
			
			if (testCntre1 =="" || testCntre1 =='' || testCntre1 == null){
				message = message+" Please select First Test Center "+"##";
			}
			
			
			if (testCntre2 =="" || testCntre2 =='' || testCntre2 == null){
				message = message+" Please select Second Test Center "+"##";
			}
			
			if (testCntre3 =="" || testCntre3 =='' || testCntre3 == null){
				message = message+" Please select Third Test Center "+"##";
			}
			
			
			
			if (testCntre1!="" && testCntre2!="" && testCntre3!=""){
					if ((testCntre1==testCntre2)&& (testCntre3==testCntre1)&&(testCntre2==testCntre3)){
						message = message+" All Test Centers should be different "+"##";
					}else{
							if (testCntre1!=""&& testCntre2!="" && testCntre1 == testCntre2){
								message = message+" First Test Center and Second Test Center should be different"+"##";
							}
			
							if (testCntre2!=""&& testCntre3!="" && testCntre2 == testCntre3){
								message = message+" Second Test Center and Third Test Center should be different "+"##";
							}
			
							if (testCntre1!=""&& testCntre3!="" && testCntre1 == testCntre3){
								message = message+" First Test Center and Third Test Center should be different "+"##";
							}
					}
			}
			
			if (preferredTestDate1 =="" || preferredTestDate1 =='' || preferredTestDate1 == null){
				message = message+" Please select First Test Date "+"##";
			}
			
			
			if (preferredTestDate2 =="" || preferredTestDate2 =='' || preferredTestDate2 == null){
				message = message+" Please select Second Test Date "+"##";
			}
			
			if (preferredTestDate3 =="" || preferredTestDate3 =='' || preferredTestDate3 == null){
				message = message+" Please select Third Test Date "+"##";
			}
			
			
			
			if (preferredTestDate1!="" && preferredTestDate2!="" && preferredTestDate3!=""){
					if ((preferredTestDate1==preferredTestDate2)&& (preferredTestDate3==preferredTestDate1)&&(preferredTestDate2==preferredTestDate3)){
						message = message+" All Test Dates should be different "+"##";
					}else{
							if (preferredTestDate1!=""&& preferredTestDate2!="" && preferredTestDate1 == preferredTestDate2){
								message = message+" First Test Date and Second Test Date should be different"+"##";
							}
			
							if (preferredTestDate2!=""&& preferredTestDate3!="" && preferredTestDate2 == preferredTestDate3){
								message = message+" Second Test Date and Third Test Date should be different "+"##";
							}
			
							if (preferredTestDate1!=""&& preferredTestDate3!="" && preferredTestDate1 == preferredTestDate3){
								message = message+" First Test Date and Third Test Date should be different "+"##";
							}
					}
			}
			
			
		if(message != ""){
			var ulID = "errorMessages";
			var divID = "errorMessagesDiv";
			createErrorList(message, ulID, divID); 
			return false;
		}else {
			//document.register.action="CandidateAction_saveCandidatePreferences.action";
			//document.register.submit();
			return true;		
		}
	}
	    
</script>

<script type="text/javascript">

function areYouSyre(){
	$('html, body').animate({ scrollTop: 0 }, 0);
	ShowPop('pop9');
}

	
function confirmReg()
{
	var r=confirm("Do you want to continue to next screen?");
		if (r==true)
		{
		  return true;
		}
		else
		{
		  return false;
		}
}

$(document).ready(function() {
	$('#block9').hide();
	if($("#testCenterUploaded").val() == "true")
	{
		$("#uploadedTestCenter").show();
	}
})

function frmSubmit(){
	document.register.action="CandidateAction_updateCandidateStage.action";
	document.register.submit();
}
</script>


<title>My JSP 'candidateTestCenterPreference.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<div class="container">
	<div class="main-body">
		<div id="dashboard" style="min-height:300px; height:auto;">

			<h1 class="pageTitle" title="Preferred Test Center">
				Preferred Test Center 
			</h1>
			<div class="hr-underline2"></div>
			<s:if test='%{testCenterSuccFlag=="true"}'>
				<div style="border:#999 1px solid;padding:3px;color:green;" id="successMessage">
				<s:property value="testCenterSuccMsg"/>
				</div>
			</s:if>
			<s:form action="CandidateAction" name="register" id="register">
			<s:hidden name="testCenterUploaded" id="testCenterUploaded"/>
			<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
			<input type="hidden" name="isDataFound" value='<s:property value="testCenterUploaded"/>'/>
			
			<s:if test='%{serverSideErrorMessage == "true"}'>
	 		 <div class="error-massage-text">
				  <s:actionmessage  escape="false"/>
	     		</div>
	    </s:if>
				<div id="errorMessagesDiv" class="error-massage" style="display: none">
					<div class="error-massage-text" style="margin:0; margin-left:-34px; padding:0;">
						<ul id="errorMessages" style="margin:1; margin-left:-23px; padding:2;">
						</ul>
					</div>
				</div>
				<s:if test="%{#attr.dataNotFound!=''}">
					<div class="error-massage-text">
						<s:property value="#attr.dataNotFound" />
					</div>
				</s:if>
				<table width="100%" border="0" cellspacing="0" cellpadding="5">
					<tr>
						<td width="170">
							Preferred Test Center 1
						</td>
						<td width="700">
							<s:select cssStyle="width:600px;" list="testCenterMasterDetails"
								name="testCenter1" label="Name" headerKey=""
								headerValue="Select Test Center" id="testCenter1"
								value="%{testCenter1}" />
						</td>
					</tr>
				<s:if test='%{showTestCenterFirst=="A"}'>
					<tr>
						<td>
							Preferred Test Center 2
						</td>
						<td>
							<s:select cssStyle="width:600px;" list="testCenterMasterDetails"
								name="testCenter2" label="Name" headerKey=""
								headerValue="Select Test Center" id="testCenter2"
								value="%{testCenter2}" />
						</td>
					</tr>
				</s:if>
				<s:if test='%{showTestCenterSecond=="A"}'>
					<tr>
						<td>
							Preferred Test Center 3
						</td>
						<td>
							<s:select cssStyle="width:600px;" list="testCenterMasterDetails"
								name="testCenter3" label="Name" cssClass="s" headerKey=""
								headerValue="Select Test Center" id="testCenter3"
								value="%{testCenter3}" />
						</td>
						</tr>
					</s:if>	
					
					<tr>
						<td colspan="2">
						<br/>
						<br/>
								<h1 class="pageTitle" title="Preferred Test Date">
									Preferred Test Date
								</h1>
								<div class="hr-underline2"></div>
						</td>
					</tr>
						<tr>
						<td>
							Preferred Test Date 1
						</td>
						<td>
						<s:select label="Test Date" name="preferredTestDate1"   id = "preferredTestDate1" 
						headerKey=""  headerValue="Select Test Date" list="testDates" value="%{preferredTestDate1}"/>
						</td>

					</tr>
					<s:if test='%{showTestDateFirst=="A"}'>
					<tr>
						<td>
							Preferred Test Date 2
						</td>
						<td>
							<s:select label="Test Date" name="preferredTestDate2"   id = "preferredTestDate2" 
						headerKey=""  headerValue="Select Test Date" list="testDates" value="%{preferredTestDate2}"/>
						</td>

					</tr>
					
					</s:if>
					<s:if test='%{showTestdateSecond=="A"}'>
					<tr>
						<td>
							Preferred Test Date 3
						</td>
						<td>
							<s:select label="Test Date" name="preferredTestDate3"   id = "preferredTestDate3" 
						headerKey=""  headerValue="Select Test Date" list="testDates" value="%{preferredTestDate3}"/>
						</td>

					</tr>
					</s:if>
					<tr>
						
						<td colspan="2" >
						
						<table width="100%">
						<tr>
  			<td colspan="5">
  			<hr/>
  			</td>
 		 </tr>
						<tr>
							<td align="center" width="90%">
								<!--<input type="button" value="Save" class="submitBtn button-gradient" onclick="validateInput()"/>
							-->
							<s:submit value="Save" cssClass="submitBtn button-gradient" method="saveCandidatePreferencesWithTestDates"></s:submit>
							</td>
							<td align="right" width="10%">
							<s:if test='%{preferenceTestCenterMandatory}'>
							   <s:if test="%{testCenterUploaded}">
								 <input type="button" value="Continue" class="submitBtn button-gradient" onclick="frmSubmit()"/>
							   </s:if><s:else>
							   <input type="button" value="Continue" class="submitBtn button-gradient" onclick="frmSubmit()" disabled="true"/>
							  </s:else>	
							</s:if>
							<s:else>
								<input type="button" value="Continue" class="submitBtn button-gradient" onclick="frmSubmit()"/>
							</s:else>
							</td>
						</tr>
						</table>
						
						</td>

					</tr>

				</table><br/>
			
			</s:form>
		</div>
	</div>
</div>
