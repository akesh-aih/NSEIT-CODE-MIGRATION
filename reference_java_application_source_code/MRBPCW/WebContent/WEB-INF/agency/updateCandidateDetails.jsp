<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>

<script type="text/javascript">
	$(document).ready(function() {
			$("#frmUpdateCandidateDetails").validationEngine({promptPosition : "bottomLeft", scroll: false});
			
			$("#stateId").change(function() {
				refreshCityList($(this).val(), $("#cityId"))
			});
			
			$("#alternateStateId").change(function() {
				refreshCityList($(this).val(), $("#alternateCityId"))
			});
			
			$("#btnSearch").click(function() {
				
				if($('#frmUpdateCandidateDetails').validationEngine('validateField', '#enrollmentId')==false)
				{
					$("#frmUpdateCandidateDetails").validationEngine('detach');
					$("#frmUpdateCandidateDetails").attr('action', "CandidateMgmtAction_getCandidateDetailsById.action");
					$("#frmUpdateCandidateDetails").submit();
				}
			});
		});
	$(document).ready(function() { 
	$("a:contains('Update Candidate Details')").html('<span class="fadeSubmenu">Update Candidate Details</span>')}); 

	function isGreaterThanMaxLength(propertyId, length) {
		var fieldValue = $("#permanentAddressId").val();
		if(fieldValue.value.length > length){
			return false;		
		}
		return true;
	}	

	function imposeMaxLength(Event, Object, MaxLen)
	{
	    return (Object.value.length <= MaxLen)||(Event.keyCode == 8 ||Event.keyCode==46||(Event.keyCode>=35&&Event.keyCode<=40))
	}

	function numbersonly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8){
		if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number 
			return false;
		}
	}

	function alphabats(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && unicode != 9 && unicode != 8 && unicode != 46 && unicode != 37 && unicode != 39)
			return false;
	}
	
	var minyear = "<%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DATE_OF_BIRTH_YEAR)%>";
	
	var maxyear = "<%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DATE_OF_BIRTH_YEAR)%>";
	
	var minyearnew = "-";
	
	minyearnew = minyearnew + minyear + "Y";
	
	var maxyearnew = "-";
	
	maxyearnew = maxyearnew + maxyear + "Y";
	
	$("#dateOfBirth").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: maxyearnew,
		maxDate: minyearnew,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy'
    });
    
	function refreshCityList(stateId, cmbCity)
	{
		dataString = "stateFK="+stateId
		
		$.ajax({
			url: "EnrollmentAction_getCityList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
				$(cmbCity).empty();
				
				$(cmbCity).append(  
					           $('<option></option>').val("").html("Select City"));
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				responseText = $.trim(responseText);
				//alert(responseText);
				if(responseText.length > 0)
				{
				 	var element = responseText.split(",");

					$.each(element, function(val){
						var nameAndIDArr = element[val].split("#");
					  $(cmbCity).append(
					           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
					     );
				 	}); 
				}
			}
		});
	}
</script>
<div class="main-body">

<div class="fade" id="pop3"></div>

<div id="AgencyPublishing">

<h1 class="pageTitle" title="Update Candidate Details"><s:text name="agencyupdatecandidatedetails.title"/></h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div class="AgencyUpCan">
<s:form name="frmUpdateCandidateDetails" id="frmUpdateCandidateDetails" method="post" action="CandidateMgmtAction" enctype="multipart/form-data">
<s:token/>
<s:hidden name="candidateImageStatus"></s:hidden>
<div class="errorMessageActive" id="dataError">
	<s:actionmessage/>
</div>
<br/>
<div class="field-label"><s:text name="agencyupdatecandidatedetails.userID"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div><s:textfield id="enrollmentId" name="enrollmentId" cssClass="inputField validate[required]" maxlength="15" onkeypress="return numbersonly(event);" errRequired="Please enter Enrollment ID"></s:textfield></div>
<div id="errorID1" class="errorMessage">Please enter valid Candidate User ID.</div>

<s:if test="%{#request['showCandidateDetails']==true}">
	<input type="button" class="submitBtn button-gradient" value="<s:text name="agencyupdatecandidatedetails.search"/>" id="btnSearch" />
</s:if>
<s:else>

<!-- for validation message -->
<br/>

<div>
	<s:submit type="submit" key="agencypaymentapproval.search" title="Search" 
	 cssClass="submitBtn button-gradient" method="getCandidateDetailsById" onclick="">
	</s:submit>
</div>
</s:else>

<s:if test="%{#request['showCandidateDetails']==true}">
<s:hidden name="userPK" value="%{userPK}"></s:hidden>
<div class="clear height10"></div>
<div style="border:1px solid #C4B890; padding:10px;">
<!-- Candidate Information Start -->
<div id="TabDiv2" class="tab_content">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.candidatedetails"/></div>
<div class="hr-dashline"></div>
<!-- Row One Start -->
<div class="padleft40">
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.firstname"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div><s:textfield name="firstName" cssClass="inputField validate[required]" maxlength="15" onkeypress="return alphabats(event);" size="10" id="firstName" errRequired="Please enter first name" disabled="true"/></div>
<div class="height10"></div>
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.lastname"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div><s:textfield name="lastName" cssClass="inputField validate[required]" maxlength="15" onkeypress="return alphabats(event);" size="10" id="lastName" errRequired="Please enter last name" disabled="true"/></div>
<div class="height10"></div>
</div>
</div>
<!-- Row One End -->

<!-- Row Two Start -->
<div class="padleft40">
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.fathersname"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div><s:textfield name="fatherName" cssClass="inputField validate[required]" size="10" maxlength="20" onkeypress="return alphabats(event);" id="fatherNameId" errRequired="Please enter father's name" disabled="true"/></div>
<div class="height10"></div>
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.dateofbirth"/>&nbsp;<span class="manadetory-fields">*</span> <br/>
	      <span class="lighttext">Age should be equal or greater than <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DATE_OF_BIRTH_YEAR)%> years</span>
</div>
<div>
	<s:textfield name="dateOfBirth" cssClass="inputDate validate[required]" size="10" readonly="true" id="dateOfBirth" errRequired="Please select date of birth" disabled="true"/>
<div class="height10"></div>
</div>
</div>
</div>
<!-- Row Two End -->

<!-- Row Third Start -->
<div class="padleft40 clear">
<div class="fl-left fifty">
<div class="field-label"><s:text name=""/><s:text name="agencyupdatecandidatedetails.gender"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div class="height10 clear"></div>
<div class="clear">
<s:radio list="genderList" name="gender" id="updateGender" cssClass="inputRadio validate[required]"  disabled="true"/>
</div>
</div>

<div class="fl-rigt fifty">
&nbsp;
</div>

</div>
<!-- Row Third End -->



<!-- Row Forth Start -->
<div class="padleft40 clear">
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

<div class="field-label clear"><s:text name="agencyupdatecandidatedetails.candidatecontactdetails"/></div>
<div class="hr-dashline"></div>

<!-- Row Five Start -->
<div class="padleft40 clear">
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.permanentaddress"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div><s:textarea cssClass="AddressArea" name="address" id="permanentAddressId" cssClass="AddressArea validate[required] maxSize[100]" onkeypress="return imposeMaxLength(event,this,100);" errRequired="Please enter permanent address"/></div>
<div class="errorMessage" id="InfoErr1">Please type your address.</div>
<div class="height10"></div>
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.contactaddress"/>&nbsp;</div>
<div> <s:textarea cssClass="AddressArea" name="AlternateAddress" id="alternateAddressId"  cssClass="AddressArea  validate[maxSize[100]]"  onkeypress="return imposeMaxLength(event,this,100);" /></div>
<div class="errorMessage" id="InfoErr2">Please type your address.</div>
<div class="height10"></div>
</div>
</div>
<!-- Row Five End -->

<!-- Row Six Start -->
<div class="padleft40 clear">
<div class="fl-left fifty">
<div class="field-label">State&nbsp;<span class="manadetory-fields">*</span></div>
<div>
<s:select name="stateListId"
		headerKey="" headerValue="Select State" list="statList" key="labelId" id="stateId" cssClass="validate[required]" errRequired="Please select state"
		listValue="labelValue" listKey="labelId" value="%{stateListId}"/>
</div>
<div class="errorMessage" id="InfoErr3">Please select city.</div>
<div class="height10"></div>
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.contactstate"/>&nbsp;</div>
<div>
		<s:select name="alternateStateListId"
		headerKey="-1" headerValue="Select State" list="statList" key="labelId" id="alternateStateId" 
		listValue="labelValue" listKey="labelId" value="%{alternateStateListId}"/>
</div>
<div class="errorMessage" id="InfoErr4">Please select state.</div>
<div class="height10"></div>
</div>
</div>
<!-- Row Six End -->

<!-- Row Seven Start -->
<div class="padleft40 clear">
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.permanentaddresscity"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div>
<s:select name="cityListId"
		headerKey="" headerValue="Select City" list="cityList" key="labelId" id="cityId" cssClass="validate[required]" errRequired="Please select city"
		listValue="labelValue" listKey="labelId" value="%{cityListId}"/>
</div>
<div class="errorMessage" id="InfoErr5">Please type valid pincode.</div>
<div class="height10"></div>
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.contactcity"/>&nbsp;</div>
<div>

			<s:select name="alternateCityListId"
		headerKey="-1" headerValue="Select City" list="altCityList" key="labelId" id="alternateCityId" 
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
<div class="field-label"><s:text name="agencyupdatecandidatedetails.permanentaddresspincode"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div>
 <s:textfield name="pincode" cssClass="inputField" size="10" id="pincodeId" cssClass="inputField validate[required]" onkeypress="return numbersonly(event);" maxlength="6" errRequired="Please enter pincode"/></div>
<div class="errorMessage" id="InfoErr5">Please type valid pincode.</div>
<div class="height10"></div>
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.contactpincode"/>&nbsp;</div>
<div>
<s:textfield name="alternatePincode" cssClass="inputField" size="10" id="alternatePincodeId" onkeypress="return numbersonly(event);" maxlength="6" /></div>
<div class="errorMessage" id="InfoErr5">Please type valid pincode.</div>
<div class="height10"></div>
</div>
</div>
<!-- Row Seven End -->

<!-- Row Eight Start -->
<div class="padleft40 clear">
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.permanentemail"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div><s:textfield  name="emailAddress" id="emailAddress" maxlength="50" cssClass="inputField validate[required,custom[email]]" errRequired="Please enter Email Address"/></div>
<div class="errorMessage" id="InfoErr6">Please enter valid email address.</div>
<div class="height10"></div>
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.contactmobilenumber"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div> <s:textfield  name="mobileNO" id="mobileNO" cssClass="inputField validate[required,minSize[10]]" maxlength="10" onkeypress="return numbersonly(event);" errRequired="Please enter mobile no."/></div>
<div class="errorMessage" id="InfoErr7">Please enter valid mobile number.</div>
<div class="height10"></div>
</div>
</div>
<!-- Row Eight End -->

        </div>
<!-- Candidate Information End -->

<div id="TabDiv4" class="tab_content">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.uploadimages"/></div>
<div class="hr-dashline"></div>

<!-- Row One Start -->
<div class="padleft40 clear">
<s:if test="%{true}"> 
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.uploadphotograph"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div class="height10"></div>
	<div class="uploadImg">
		 <img width="200" height="150" src="PhotoImage.jpg?enrollmentId=<s:text name="enrollmentId"></s:text>" name="inputStreamForImage" id="inputStreamForImage" />
	</div>
<div>
	<s:file name="attachmentPhoto" label="Attachment File" cssClass="BrowseFile"  size="25"/> 
</div>
<div class="uploadIns"><s:text name="agencyupdatecandidatedetails.uploadformat"/><br /><s:text name="agencyupdatecandidatedetails.filesizelessthan"/></div>
<div id="UpErr1" class="errorMessage">Please select valid photograph.</div>
<div class="height5"></div>

</div>

<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyupdatecandidatedetails.uploadsignature"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div class="height10"></div>
<div class="uploadImg">
	<img width="200" height="150" src="SignatureImage.jpg?enrollmentId=<s:text name="enrollmentId"></s:text>"  name="inputStreamForSignature" id="inputStreamForSignature " />
</div>
<div><s:file name="attachmentSignature" label="Attachment Signature " cssClass="BrowseFile"  size="25"/></div>
<div class="uploadIns"><s:text name="agencyupdatecandidatedetails.uploadformat"/><br /><s:text name="agencyupdatecandidatedetails.filesizelessthan"/></div>
<div id="UpErr2" class="errorMessage">Please select valid signature.</div>
<div class="height5"></div>

</div>
</s:if>
<s:else>
	<div class="padleft40 clear">
		<div class="fl-left fifty">
		<div class="field-label"><s:text name="agencyupdatecandidatedetails.uploadphotograph"/>&nbsp;<span class="manadetory-fields">*</span></div>
		<div class="height10"></div>
		<div class="uploadImg"><img src="images/Dummy-Photo.jpg" /></div>
		<div><input type="file" size="25" class="BrowseFile" name="photograph"/></div>
		<div class="uploadIns"><s:text name="agencyupdatecandidatedetails.uploadformat"/><br /><s:text name="agencyupdatecandidatedetails.filesizelessthan"/></div>
		<div id="UpErr1" class="errorMessage">Please select valid photograph.</div>
		<div class="height5"></div>
		<div><input type="submit" title="Upload" class="submitBtn button-gradient" value="<s:text name="agencyupdatecandidatedetails.upload"/>"/></div>
		</div>
		
		<div class="fl-rigt fifty">
		<div class="field-label"><s:text name="agencyupdatecandidatedetails.uploadsignature"/>&nbsp;<span class="manadetory-fields">*</span></div>
		<div class="height10"></div>
		<div class="uploadImg"><img src="images/Dummy-Signature.jpg" /></div>
		<div><input type="file" size="25" class="BrowseFile" name="signature"/></div>
		<div class="uploadIns"><s:text name="agencyupdatecandidatedetails.uploadformat"/><br /><s:text name="agencyupdatecandidatedetails.filesizelessthan"/></div>
		<div id="UpErr2" class="errorMessage">Please select valid signature.</div>
		<div class="height5"></div>
		<div><input type="submit" title="Upload" class="submitBtn button-gradient" value="<s:text name="agencyupdatecandidatedetails.upload"/>"/></div>
		</div>
	</div>
</s:else>

</div>
<!-- Row One End -->

<div class="clear height20"></div>
<div class="clear padleft40 field-label"><s:text name="agencyupdatecandidatedetails.remarks"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div class="clear height5"></div>
<div class="padleft40 clear">
<s:textarea  cssClass="AddressArea2 validate[required,maxSize[100]]" name="remarks" id="remarks" onkeypress="return imposeMaxLength(event,this,100);" errRequired="Please enter your remarks"></s:textarea>

</div>

<div class="height20 clear"></div>

<!-- Row Two Start -->
<div class="clear padleft40">
<s:submit type="submit" key="agencyupdatecandidatedetails.submit" title="Search"  cssClass="submitBtn button-gradient" method="updateCandidateDetails"></s:submit>
</div>
<!-- Row Two End -->

</div>
</div>
</s:if>


</s:form>
</div>
</div>
<!-- Box Container End -->
</div>


