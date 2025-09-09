<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../security/PreventClickJacking.html"%>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
<script type="text/javascript">
	
	$(document).ready(function(){
	
		$("#alternateStateId").change(
		function (){
			getAlternateCityList($(this).val());
			}
		);
		
		$("#stateId").change(
		function (){
			getCityList($(this).val());
			}
		);
		
		$("#viewupdatePersonalInfoContainer").validationEngine({promptPosition : "bottomLeft", scroll: false});
		
		$("#btnEditPersonalInfo").click(function() {
			updatePersonalInformation();
		});
		
		$("#btnPersonalInfoNext").click(function() {
			updatePersonalInformation();
		});

		$("#btnPersonalInfoPrev").click(function() {
		
			/*changes for back click*/
		
			var index = ($("#hPersonalInfoDivInd").val()*1);
			index = index - 1;
			btnBack_Click(index);
		});
	});

	function updatePersonalInformation()
	{
		if($("#viewupdatePersonalInfoContainer").validationEngine('validate')==false)
			return;
		
		var stateValue  = $("#stateId").val();
		var alternateStateValue  = $("#alternateStateId").val();
		
		var cityValue  = $("#cityId").val();
		var alternateCityValue  = $("#alternateCityId").val();
		
		var permenantAddressValue   = $("#permanentAddressId").val().replace(/&/g,"-|-");//Added to replace first occurence of '&'
		//alert(permenantAddressValue.replace(/\&/g,"#*#"));
		var alternateAddressValue  = $("#alternateAddressId").val().replace(/&/g,"-|-");//Added to replace first occurence of '&'
		var fathersName  = $("#fatherName").val();
		var pincodeValue  = $("#pincodeId").val();
		var alternatePincodeValue  = $("#alternatePincodeId").val();
		var emailAddress = $("#emailAddress").val();
		var mobileNO = $("#mobileNO").val();
		
		
		dataString = "stateListId="+stateValue+"&alternateStateListId="+alternateStateValue+"&cityListId="+cityValue+
		"&address="+permenantAddressValue+ 
		"&AlternateAddress="+alternateAddressValue+
		"&fatherName="+fathersName+
		"&pincode="+pincodeValue+
		"&alternateCityListId="+alternateCityValue+
		"&alternatePincode="+alternatePincodeValue+
		"&emailAddress="+emailAddress+
		"&mobileNO="+mobileNO;
		//alert(dataString);
		
		$.ajax({
			url: "EnrollmentAction_updatePersonalDetails.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
				
			},
			error:function(ajaxrequest)
			{
				alert('Error registering user. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				//alert('responseText'+responseText);
				responseText = $.trim(responseText);
				
				textMessae = responseText.split(',');
				message = responseText.substring(2, responseText.length);
				if(textMessae[0]=='9'){
					alert(message);
					return false;
				}else{
					if(responseText.length > 0)
					{
						var index = ($("#hPersonalInfoDivInd").val()*1)+1;
						
						if(responseText.charAt(0) == "0")
						{
							if($("#actionNamePersonal").val() == "preview" || $("#actionNamePersonal").val() == "view")
							{
								submitPersonalDetailsForPreview();
							}
							else
							{
								changeTabsTestEnrollments(index);
							}
						}
					}
				}
			}
		});
	}
	
	function getCityList(stateId){
		//alert ("stateId " + stateId);
		dataString = "stateFK="+stateId
		//alert (dataString);
		$.ajax({
			url: "EnrollmentAction_getCityList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#cityId').empty();
					$("#cityId").append(
					           $('<option></option>').val("").html("Select City")
					     );
			},
			error:function(ajaxrequest)
			{
				window.reload();
			},
			success:function(responseText)
			{
			//alert (responseText);
				responseText = $.trim(responseText);
				
				if(responseText.length > 0)
				{
					 element = responseText.split(",");  
					 message = responseText.substring(2, responseText.length);
					 if(element[0] == "9")
					 {
						alert(message);
						return false;
					 }
					 else
					 {
						 $.each(element, function(val) {
							 var nameAndIDArr = element[val].split("#");
							  $("#cityId").append(
							           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
							     );
						 }); 
					 }
				}
			}
		});
	}
	
	function addressValidationForSpclChar(e){
		
		var unicode=e.charCode? e.charCode : e.keyCode
		
		if(unicode == 33 || unicode == 64 || (unicode >34 && unicode< 38) || unicode==60 || unicode==62 || unicode==63 || unicode==126
		|| unicode == 94)
			return false;
	}
	
	function checkValueForAdrress(){
		var permanentAddressId = $("#permanentAddressId").val();
	
		var permanentAddressIdTrim  = jQuery.trim(permanentAddressId);
		if (permanentAddressIdTrim == ""){
			return "Please enter text in textarea";
		}
	}



	function checkValueForAlternateAdrress(){
		var permanentAddressId = $("#permanentAddressId").val();
	
		var permanentAddressIdTrim  = jQuery.trim(permanentAddressId);
		if (permanentAddressIdTrim == ""){
			return "Please enter text in textarea";
		}
	}


	function getAlternateCityList(stateId){
		dataString = "stateFK="+stateId
		
		$.ajax({
			url: "EnrollmentAction_getCityList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#alternateCityId').empty();
					$("#alternateCityId").append(
					           $('<option></option>').val("").html("Select City")
					     );
				
			},
			error:function(ajaxrequest)
			{
				window.reload();
			},
			success:function(responseText)
			{
				responseText = $.trim(responseText);
				if(responseText.length > 0)
				{
					element = responseText.split(",");  
					message = responseText.substring(2, responseText.length);
					if(element[0] == "9")
					{
						//alert(message);
						//return false;
					}
					else
					{
						$.each(element, function(val) {
						  var nameAndIDArr = element[val].split("#");
						  $("#alternateCityId").append(
						           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
						     );
					 	}); 
					}
				}
			}
		});
	}
	
	function imposeMaxLength(Event, Object, MaxLen)
	{
		return addressValidationForSpclChar(Event);
	    return (Object.value.length <= MaxLen)||(Event.keyCode == 8 ||Event.keyCode==46||(Event.keyCode>=35&&Event.keyCode<=40))
	}
	
	function numbersonly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8){
		if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number 
			return false;
		}
	}
</script>

<form method="post" name="personal" id="viewupdatePersonalInfoContainer">
<input type="hidden" id="hPersonalInfoDivInd" value="2"/>
<s:hidden name="actionName" id="actionName" value="%{actionName}" ></s:hidden>
<s:if test="%{actionName==null || actionName==''}">
	<div id="TabDiv2" class="tab_content" style="display:none">
</s:if>
<s:elseif test="%{actionName=='preview' || actionName=='view'}">
	<div id="TabDiv2" class="tab_content" style="display:block">
</s:elseif>
<s:hidden name="actionNamePersonal" id="actionNamePersonal" value="%{actionName}"></s:hidden>
<!-- Row One Start -->
<div class="padleft40">
	<div class="fl-left fifty">
		<div class="field-label"><s:text name="viewupdatepersonalinfo.firstname"/>&nbsp;</div>
		<div>
			<s:textfield name="firstName" cssClass="inputField valdiate[required]" disabled="true" errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_FIRSTNAME_REQUIRE)%>" size="10" id="firstName" />
		</div>
	
	<!--<div class="field-label">First Name&nbsp;</div>
	<div><input type="text" class="inputField" name="fname" size="10" maxlength="50" value="Mukul" disabled="disabled"/></div>
	-->
	<div class="height10"></div>
</div>

<div class="fl-rigt fifty">
	<!--<div class="field-label">Last Name&nbsp;</div>
	<div><input type="text" class="inputField" value="Pathare" name="lname" maxlength="50" size="10" disabled="disabled" /></div>
	-->
	
	<div class="field-label"><s:text name="viewupdatepersonalinfo.lastname"/>&nbsp;</div>
	<div>
		<s:textfield name="lastName" cssClass="inputField valdiate[required]" disabled="true" errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_LASTNAME_REQUIRE)%>" size="10" id="lastName" />
	</div>
<div class="height10"></div>
</div>
</div>
<!-- Row One End -->

<!-- Row Two Start -->
<div class="padleft40">
	<div class="fl-left fifty">
		<!--<div class="field-label">Father's Name&nbsp;</div>
		<div><input type="text" class="inputField" name="fname" size="10" maxlength="50" value="" disabled="disabled"></div>
		-->
		<div class="field-label"><s:text name="viewupdatepersonalinfo.fathersname"/>&nbsp;</div>
		<div>
			<s:textfield name="fatherName" cssClass="inputField validate[required]" disabled="true" errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_FATHERNAME_REQUIRE)%>" size="10" id="fatherName" />
		</div>
		
		<div class="height10"></div>
	</div>


<div class="fl-rigt fifty">
	<!--<div class="field-label">Date Of Birth&nbsp;</div>
	<div><input type="text" class="inputField" value="" name="lname" size="10" maxlength="11" disabled="disabled" /></div>
	-->
	
	<div class="height10"></div>
</div>
</div>
<!-- Row Two End -->

<!-- Row Third Start -->
<div class="padleft40">
<div class="fl-left fifty">
	<!--<div class="field-label">Gender:&nbsp;<span class="fieldValue">Male</span></div>-->
	
<div class="field-label"><s:text name="viewupdatepersonalinfo.gender"/>&nbsp;<span class="fieldValue"><s:property value="gender"/></span></div>
	
<div class="height10"></div>
</div>



</div>
<!-- Row Third End -->



<!-- Row Forth Start -->
<div class="padleft40">
<div class="fl-left fifty">

<div class="height10"></div>
</div>


<div class="fl-rigt fifty">
<!--<div class="field-label">Mother's Name&nbsp;</div>
<div><input type="text" class="inputField" value="Shanti" name="lname" size="10" disabled="disabled"></div>
<div class="height10"></div> -->
</div>
</div>
<!-- Row Forth End -->

<div class="field-label clear"><s:text name="viewupdatepersonalinfo.contactdetails"/></div>
<div class="hr-dashline"></div>

<!-- Row Five Start -->
<div class="padleft40 clear">
	<div class="fl-left fifty">
		<!--<div class="field-label">Permenant Address&nbsp;<span class="manadetory-fields">*</span></div>
			--><!--<div><textarea class="AddressArea" name="address1" maxlength="75"></textarea></div>
			-->
		<div class="field-label"><s:text name="viewupdatepersonalinfo.permanentaddress"/>&nbsp;<span class="manadetory-fields">*</span></div>	
			
			<div>
				 <s:textarea cssClass="enrollFinal validate[required]" 
				 errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_PERADDRESS_REQUIRE)%>" name="address" id="permanentAddressId" 
				 cssStyle="resize:none" rows="5" cols="25" onkeypress="return imposeMaxLength(event,this,95);"/>
			</div>
			
			<div class="errorMessage" id="InfoErr1"><s:text name="viewupdatepersonalinfo.errorlabeladdress"/><</div>
		<div class="height10"></div>
	</div>

	<div class="fl-rigt fifty">
		
		<div class="field-label"><s:text name="viewupdatepersonalinfo.contactaddress"/></div>	
			
			<div>
				 <s:textarea cssClass="enrollFinal" 
				 errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_ALTERNATEADD_REQUIRE)%>" name="AlternateAddress" id="alternateAddressId"
				 cssStyle="resize:none" rows="5" cols="25" onkeypress="return imposeMaxLength(event,this,95);"/>
			</div>	
		
		<div class="errorMessage" id="InfoErr2"><s:text name="viewupdatepersonalinfo.errorlabelcontactaddress"/><</div>
		<div class="height10"></div>
	</div>
</div>
<!-- Row Five End -->

<!-- Row Six Start -->
<div class="padleft40 clear">
<div class="fl-left fifty">
<div class="field-label"><s:text name="viewupdatepersonalinfoperaddress.state"/>&nbsp;<span class="manadetory-fields">*</span></div>
	<div>
		<!--<select class="mid-dropdown" name="state">
		<option value="">State</option>
		<option value="Maharashtra" selected="selected">Maharashtra</option>
		</select>
	-->
	
	<s:select name="stateListId"
		headerKey="" cssClass="enrollFinal validate[required]" 
		errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_STATE_REQUIRE)%>" 
		headerValue="Select State" 
		list="statList" key="labelId" id="stateId" 
		listValue="labelValue" listKey="labelId" value="%{stateListId}"/>
	
	
	</div>
<div class="errorMessage" id="InfoErr3"><s:text name="viewupdatepersonalinfoperaddress.errorlabelstate"/></div>
<div class="height10"></div>
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="viewupdatepersonalinfoaltaddress.state"/>&nbsp;</div>
	<div>
		<s:select name="alternateStateListId"
			headerKey="" cssClass="enrollFinal" 
			errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_ALRSATE_REQUIRE)%>" 
			headerValue="Select State" list="statList" key="labelId" id="alternateStateId" 
			listValue="labelValue" listKey="labelId" value="%{alternateStateListId}"/>
	
	
	</div>
<div class="errorMessage" id="InfoErr4"><s:text name="viewupdatepersonalinfoaltaddress.errorlabelstate"/></div>
<div class="height10"></div>
</div>
</div>
<!-- Row Six End -->

<!-- Row Seven Start -->
<div class="padleft40 clear">
<div class="fl-left fifty">
<div class="field-label"><s:text name="viewupdatepersonalinfoperaddress.city"/>&nbsp;<span class="manadetory-fields">*</span></div>
	<div>
		<!--<select class="mid-dropdown" name="city">
		<option value="">City</option>
		<option value="Mumbai" selected="selected">Mumbai</option>
		</select>
		-->
		
		<s:select name="cityListId"
		headerKey="" cssClass="enrollFinal validate[required]" 
		errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_CITYFORPERADDR_REQUIRE)%>" 
		headerValue="Select City" 
		list="cityList" key="labelId" id="cityId" 
		listValue="labelValue" listKey="labelId" value="%{cityListId}"/>
	
	
	</div>
<div class="errorMessage" id="InfoErr5">Please type valid pincode.</div>
<div class="height10"></div>
</div>


<div class="fl-rigt fifty"><div class="field-label"><s:text name="viewupdatepersonalinfoaltaddress.city"/>&nbsp;</div>
	<div>
	<!--<select class="mid-dropdown" name="city">
	<option value="">City</option>
	<option value="Mumbai">Mumbai</option>
	</select>-->
	
			<s:select name="alternateCityListId"
		headerKey="" cssClass="enrollFinal" 
		errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_CITYALTADDR_REQUIRE)%>" 
		headerValue="Select City" list="cityList" key="labelId" id="alternateCityId" 
		listValue="labelValue" listKey="labelId" value="%{alternateCityListId}"/>
	</div>
<div class="errorMessage" id="InfoErr5">Please type valid pincode.</div>
<div class="height10"></div>
</div>
</div>
<!-- Row Seven End -->


<!-- Row Seven Start -->
<div class="padleft40 clear">
	<div class="fl-left fifty">
		<!--<div class="field-label">Pincode&nbsp;<span class="manadetory-fields">*</span></div>
		<div><input type="text" class="inputField" name="pincode" maxlength="6" size="10" value="400025"  /></div>
		-->
		
		<div class="field-label"><s:text name="viewupdatepersonalinfoperaddress.pincode"/>&nbsp;<span class="manadetory-fields">*</span></div>	
			
			<div>
				 <s:textfield name="pincode" 
				 cssClass="enrollFinal validate[required,custom[integer],minSize[6],maxSize[6],min[1]] " 
				 errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_PINCODE_REQUIRE)%>" size="10" 
				 id="pincodeId" onkeypress = "return numbersonly(event);"
				 maxlength="6" errCustom="Invalid Pincode"/>
				 <!-- for validation message -->
				 <br/>
				 <br/>
				 <br/>
				 <br/>
			</div>
		
		<div class="errorMessage" id="InfoErr5">Please type valid pincode.</div>
		<div class="height10"></div>
	</div>


<div class="fl-rigt fifty">
	
	<!--<div class="field-label">Pincode&nbsp;</div>
	<div><input type="text" class="inputField" name="pincode" maxlength="6" size="10" value="" /></div>
	-->
	<div class="field-label"><s:text name="viewupdatepersonalinfoaltaddress.pincode"/></div>	
			
			<div>
				<s:textfield name="alternatePincode" 
				cssClass="enrollFinal validate[custom[integer],minSize[6],maxSize[6],min[1]] " 
				errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_ALTPIN_REQUIRE)%>" 
				size="10" id="alternatePincodeId" onkeypress = "return numbersonly(event);"
				maxlength="6" />
			</div>
	
	<div class="errorMessage" id="InfoErr5">Please type valid pincode.</div>
<div class="height10"></div>
</div>
</div>
<!-- Row Seven End -->

<!-- Row Eight Start -->
<div class="padleft40 clear">
<div class="fl-left fifty"><!--

	<div class="field-label">Email Address&nbsp;<span class="manadetory-fields">*</span></div>
	<div><input type="text" class="inputField" name="emailID" maxlength="25" size="10" value="mukulpatahare@gmail.com" /></div>
	-->
	<div class="field-label"><s:text name="viewupdatepersonalinfoperaddress.emailaddress"/>&nbsp;<span class="manadetory-fields">*</span></div>	
			
			<div>
				 <s:textfield cssClass="enrollFinal validate[required,custom[email]] " errRequired="Please Enter Email Address" 
				 name="emailAddress"
				  maxlength="25" />
			</div>
	
	
<div class="errorMessage" id="InfoErr6">Please enter valid email address.</div>
<div class="height10"></div>
</div>


<div class="fl-rigt fifty">
	<div class="field-label"><s:text name="viewupdatepersonalinfoperaddress.mobilenumber"/>&nbsp;<span class="manadetory-fields">*</span></div>	
			
			<div>
				 <s:textfield cssClass="enrollFinal validate[required,minSize[10],maxSize[10],custom[integer]] "  
				 errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VIEWUPDATE_MOBILE_REQUIRE)%>" name="mobileNO"
				 maxlength="10" onkeypress = "return numbersonly(event);"/>
			</div> 
<div class="errorMessage" id="InfoErr7">Please enter valid mobile number.</div>
<div class="height10"></div>
</div>
</div>
<!-- Row Eight End -->

<div class="clear height20"></div>
<div class="padleft40 clear">
<s:if test="%{actionName=='' || actionName == null}">
	<input type="button" value="<s:text name="viewupdatepersonalinfo.back"/>" class="submitBtn button-gradient enrollFinal" title="Back" id="btnPersonalInfoPrev"/>&nbsp;&nbsp;
	<input type="button" value="<s:text name="viewupdatepersonalinfo.cancel"/>" class="submitBtn button-gradient cancelBtn" title="Save" onclick="valPersonalInfo();"/>
	&nbsp;<input type="button" value="<s:text name="viewupdatepersonalinfo.next"/>" class="submitBtn button-gradient enrollFinal" title="Next" id="btnPersonalInfoNext" />
</s:if>
<s:elseif test="%{actionName=='preview' || actionName=='view'}">
	<input type="button" value="<s:text name="viewupdatepersonalinfo.update"/>" class="submitBtn button-gradient" id="btnEditPersonalInfo"/>
</s:elseif>

</div>

        </div>
</form>