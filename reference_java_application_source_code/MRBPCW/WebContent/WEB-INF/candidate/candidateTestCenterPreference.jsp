<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<script>
	
		function validateInput(){
		
			var message = "";
		
			var testCntre1 = $("#testCenter1").val();
			var testCntre2 = $("#testCenter2").val();
			var testCntre3 = $("#testCenter3").val();
			
			if (testCntre1 =="" || testCntre1 =='' || testCntre1 == null){
				message = message+" Please select First Test Centre "+"##";
			}
			
			
			if (testCntre2 =="" || testCntre2 =='' || testCntre2 == null){
				message = message+" Please select Second Test Centre "+"##";
			}
			
			if (testCntre3 =="" || testCntre3 =='' || testCntre3 == null){
				message = message+" Please select Third Test Centre "+"##";
			}
			
			
			
			if (testCntre1!="" && testCntre2!="" && testCntre3!=""){
					if ((testCntre1==testCntre2)&& (testCntre3==testCntre1)&&(testCntre2==testCntre3)){
						message = message+" All Test Centers should be different "+"##";
					}else{
							if (testCntre1!=""&& testCntre2!="" && testCntre1 == testCntre2){
								message = message+" First Test Centre and Second Test Centre should be different"+"##";
							}
			
							if (testCntre2!=""&& testCntre3!="" && testCntre2 == testCntre3){
								message = message+" Second Test Centre and Third Test Centre should be different "+"##";
							}
			
							if (testCntre1!=""&& testCntre3!="" && testCntre1 == testCntre3){
								message = message+" First Test Centre and Third Test Centre should be different "+"##";
							}
					}
			}
			
			
			
		if(message != ""){
			var ulID = "errorMessages";
			var divID = "errorMessagesDiv";
			createErrorList(message, ulID, divID); 
			return false;
		}else {
			document.register.action="CandidateAction_saveCandidatePreferences.action";
			document.register.submit();
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

function saveCandiadtePreferences(){
	$("#register").attr('action','CandidateAction_saveCandidatePreferences.action');
	$("#register").submit();
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

<div class="container" style="height: 400px;">
	<div class="main-body">
		<div id="dashboard">

			<h1 class="pageTitle" title="Preferred Test Centre">
				Preferred Test Centre
			</h1>
			<div class="hr-underline2"></div>
			<s:if test='%{testCenterSuccFlag=="true"}'>
				<div style="border: #999 1px solid; padding: 3px; color: green;"
					id="successMessage">
					<s:property value="testCenterSuccMsg" />
				</div>
			</s:if>
			<s:form action="CandidateAction" name="register" id="register">
				<s:hidden name="testCenterUploaded" id="testCenterUploaded" />
				<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<s:hidden name="isDataFound"
					value='<s:property value="testCenterUploaded"/>' />
				<s:actionmessage escape="false" cssClass="msgg" />
				<div id="errorMessagesDiv" class="error-massage"
					style="display: none">
					<div class="error-massage-text"
						style="margin: 0; margin-left: -34px; padding: 0;">
						<ul id="errorMessages"
							style="margin: 1; margin-left: -23px; padding: 2;">
						</ul>
					</div>
				</div>
				<s:if test="%{#attr.dataNotFound!=''}">
					<div class="error-massage-text">
						<s:property value="#attr.dataNotFound" />
					</div>
				</s:if>
				<table border="0" cellspacing="0" cellpadding="5">
					<tr>
						<td width="222">
							Preferred Test Centre 1
							<span class="manadetory-fields">*</span>
						</td>
						<td>
							<s:select cssStyle="width:600px;" list="testCenterMasterDetails"
								name="testCenter1" label="Name" headerKey=""
								headerValue="Select Test Centre" id="testCenter1"
								value="%{testCenter1}" />
						</td>
					</tr>
					<s:if test='%{showTestCenterFirst=="A"}'>
						<tr>
							<td>
								Preferred Test Centre 2
								<span class="manadetory-fields">*</span>
							</td>
							<td>
								<s:select cssStyle="width:600px;" list="testCenterMasterDetails"
									name="testCenter2" label="Name" headerKey=""
									headerValue="Select Test Centre" id="testCenter2"
									value="%{testCenter2}" />
							</td>
						</tr>
					</s:if>
					<s:if test='%{showTestCenterSecond=="A"}'>
						<tr>
							<td>
								Preferred Test Centre 3
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
						<td>
							Language For Written Test
							<span class="manadetory-fields">*</span>
							<br />
						<td colspan="2">
							<s:select list="languageWrittenTestList"
								name="languageWrittenTest" label="Name" headerKey=""
								headerValue="Select Language" id="languageWrittenTest"
								value="%{languageWrittenTest}" />
					<tr>
						<td colspan="5">
							<hr />
						</td>
					</tr>
					<tr>
						<td></td>
						<td align="center">
							<!--<input type="button" value="Save" class="submitBtn button-gradient" onclick="saveCandiadtePreferences();"/>-->
							<s:submit value="Save" cssClass="submitBtn button-gradient"
								method="saveCandidatePreferences"></s:submit>
						</td>
						<td align="left">
							<s:if test='%{preferenceTestCenterMandatory}'>
								<s:if test="%{testCenterUploaded}">
									<input type="button" value="Continue"
										class="submitBtn button-gradient" onclick="frmSubmit()" />
								</s:if>
								<s:else>
									<input type="button" value="Continue"
										class="submitBtn button-gradient" onclick="frmSubmit()"
										disabled="true" />
								</s:else>
							</s:if>
							<s:else>
								<input type="button" value="Continue"
									class="submitBtn button-gradient" onclick="frmSubmit()" />
							</s:else>
						</td>

					</tr>

				</table>
				<br />

			</s:form>
		</div>
	</div>
</div>
