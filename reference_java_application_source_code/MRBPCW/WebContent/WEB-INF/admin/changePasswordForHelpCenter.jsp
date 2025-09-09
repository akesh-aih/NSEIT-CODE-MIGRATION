<%@ taglib prefix="s" uri="/struts-tags"%>

<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script type="text/javascript">

	$(document).ready(function() {

			
		$("#btnForgotPassword").click(function() {

			var flag = validateFormForPwd();
			if(flag == true){
				//alert("validated");
				var dataString = "";
			
				var password = $("#newPassword").val();
				var cmbHelpCenter =  $("#cmbHelpCenter").val();
				//alert(cmbHelpCenter);
				dataString = "newPassword="+encodeURIComponent(password)+"&cmbHelpCenter="+cmbHelpCenter;
				
				$.ajax({
					url: "AgencyAction_changeHelpCentrePassword.action",
					async: true,
					data: dataString,
					beforeSend: function(){
						//alert('before send');
					},
					error:function(ajaxrequest){
						alert('Error registering user. Server Response: '+ajaxrequest.responseText);
					},
					success:function(responseText){
						responseText = $.trim(responseText);
						
						textMessage = responseText.split(',');
						message = responseText.substring(2, responseText.length);
						
						if(textMessage[0]==0)
						{
							$("#successMessage").html("Password changed successfully.");
						}else{
						$("#successMessage").html("Error in change Password");
						}
							ShowBlock('block10');
							$("#error-massage1").hide();
					}
				});
			}
			else
				return false;
		});
	});

	
	function validateFormForPwd(){
		//alert('inside');
		
		var password = $("#newPassword").val();
		var rePass = $("#rePwd").val();
		var cmbHelpCenter = $("#cmbHelpCenter").val();
		
		
		var message = "";
		
		
		if(cmbHelpCenter == '-1')
			message = message + "Please select Help Center."+"##";
		
		if(password == "")
			message = message + "Please enter password."+"##";
		//if(rePass == "")
			//message = message + "Please re-enter password."+"##";
		if(password != rePass){
			//alert('sdf');
			message = message + "Passwords do not match."+"##";
		}
		var validPwd = "";
		if(password != "")
			validPwd = validatePassword(password);

		if(validPwd != true)
			message = message + validPwd;

		if(message != ""){
			var ulID = "error-ul1";
			var divID = "error-massage1";
			createErrorList(message, ulID, divID); 
			
			return false;
		}
		else
			return true;
	}

	function closePopUp(){
		$("#newPassword").val("");
		$("#rePwd").val("");
		$("#cmbHelpCenter").val("-1");
		$("#error-massage1").hide();
		HideAll();
	}

	function alphaNumeric(e)
	{
		var unicode=e.charCode? e.charCode : e.keyCode
		if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48||unicode>57) && unicode != 8 && unicode != 9 && unicode != 46  && unicode != 39)
			return false;
	}
	
	
</script>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="main-body">
<div id="dashboard">
<h1 class="pageTitle" title="Change Password For Help Center">Change Password</h1>
<div class="hr-underline2"></div>
<div id="error-massage1" style="display:none">
      	<div class="error-massage-text">
      	<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul1">
      	
      	
      	</ul>
      	
      	 </div>
      </div>
<s:form name="forgotpwd" id="forgotpwd">

	<div class="fogot-cont">

		<table width="300" border="0" cellspacing="0" cellpadding="4">
		  
		  <tr>
		  <td>Help Center <span class="manadetory-fields">*</span>
		  </td>
		  <td>
		 <!-- <s:select list="helpCenterList" name="helpCenterList" id="helpCenterList" listValue="labelValue" listKey="labelId" value ="%{helpCenterList}"></s:select> --> 
		  <s:select name="cmbHelpCenter" id="cmbHelpCenter" headerKey="-1"
								headerValue="Select Help Center" list="helpCenterList" listValue="labelValue"
								listKey="labelId" value="%{cmbHelpCenter}" />
		  </td>
		  </tr>
		  <tr>
		    <td><s:text name="forgot.newpassword"/>&nbsp;<span class="manadetory-fields">*</span></td>
		    <td><s:password name="newPassword" id="newPassword" size="10" onkeypress="" maxlength="12" cssClass="inputField" /></td>
		  </tr>
		  <tr>
		    <td><s:text name="forgot.reenterpwd"/>&nbsp;<span class="manadetory-fields">*</span></td>
		    <td><s:password name="rePwd" id="rePwd" size="10" onkeypress="" maxlength="12" cssClass="inputField" /></td>
		  </tr>
		  
		  <tr>
		  	<td></td>
		  	<td><input type="button" value="<s:text name="forgotpassword.submit"/>" class="submitBtn button-gradient" title="Submit" id="btnForgotPassword" />&nbsp;&nbsp;</td>
		  </tr>
		</table>
	</div>
	
	<div class="change-pass box-gradient" id="block10" style="vertical-align: top" >
<div><a href="javascript:void(0);" onclick="closePopUp();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox fl-left"><h1 class="pageTitle" title="Login">&nbsp;<s:label value="Change Password " /></h1></div>
<div class="closebtnBox fl-rigt"></div>
<div class="hr-underline clear"></div>
<br/>
	
	<div style = "text-align:center" id ="successMessage"></div><br/>
	<div style = "text-align:center"><input type="button" value="OK"
										class="submitBtn button-gradient" title="Cancel" id = 'btnCancel'
										onclick="closePopUp();" />
										</div>
	
</div></div>
		
		<div class="fade" id="block7"></div>
</s:form>
</div>
</div>	