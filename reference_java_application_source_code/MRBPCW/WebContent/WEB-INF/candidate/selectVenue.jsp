
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%><%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">

	$(document).ready(function(){
	
		$("#selectVenueContainer").validationEngine({promptPosition : "bottomLeft", scroll: false});

		$("#btnEditVenue").click(function() {
			updateVenueDetails();
		});
		
		$("#btnVenueNext").click(function() {
			updateVenueDetails();
		});

		$("#btnVenuePrev").click(function() {

			$("#selectVenueContainer").validationEngine('hide');
			
			var index = ($("#hVenueDivInd").val()*1);
			index = index - 1;
			btnBack_Click(index);
		});
	});

	function updateVenueDetails()
	{
		if($("#selectVenueContainer").validationEngine('validate')==false)
			return;
		//var dataString = "beanpropertieshere";
		var thirdTestCenterId  = $("#thirdTestCenterId").val();
		var secondTestCenterId  = $("#secondTestCenterId").val();
		var firstTestCenterId  = $("#firstTestCenterId").val();
		
		if(firstTestCenterId > 0 && secondTestCenterId > 0 && thirdTestCenterId > 0)
		{
			if((firstTestCenterId == secondTestCenterId) && (firstTestCenterId == thirdTestCenterId))
			{
					alert("Preferred centers should have different values.");
					return 0;
			}
			else if(firstTestCenterId == secondTestCenterId)
			{
				alert("First and second preferred centers should have different values.");
				return 0;
			}
			else if(firstTestCenterId == thirdTestCenterId)
			{
				alert("First and third preferred centers should have different values.");
				return 0;
			}
			else if(secondTestCenterId == thirdTestCenterId)
			{
				alert("second and third preferred centers should have different values.");
				return 0;
			}
			
			else
			{

			}
		}
		var dataString = "firstTestCenterId="+firstTestCenterId+"&secondTestCenterId="+secondTestCenterId+"&thirdTestCenterId="+thirdTestCenterId;
		
		var index = ($("#hVenueDivInd").val()*1);
		
		$.ajax({
			url: "EnrollmentAction_insertVenueDetails.action",
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
				responseText = $.trim(responseText);
				textMessae = responseText.split(',');
				message = responseText.substring(2, responseText.length);
				if(textMessae[0]=="9"){
					alert(message);
					return false;
				}
				else
				{
					if(responseText.length > 0)
					{
						if(responseText.charAt(0) == "0")
						{
							if($("#actionNameVenue").val() == "preview" || $("#actionNameVenue").val() == "view")
							{
								submitPersonalDetailsForPreview();
							}
							else
							{
								submitPersonalDetailsForPreview();
							}
						}
					}
				}
			}
		});
	}
</script>

<form method="post" name="upload" id="selectVenueContainer">

<input type="hidden" id="hVenueDivInd" value="5" />

<s:hidden name="actionNameVenue" id="actionNameVenue" value="%{actionName}"></s:hidden>

<s:if test="%{actionName==null || actionName==''}">
	<div id="TabDiv5" class="tab_content" style="display:none">
</s:if>
<s:elseif test="%{actionName=='preview' || actionName=='view'}">
	<div id="TabDiv5" class="tab_content" style="display:block">
</s:elseif>

<div class="field-label"><s:text name="venue.venue"/></div>
<div class="hr-dashline"></div>
<div>
<div class="fl-left preCentreWidth">
<div class="field-label"><s:text name="venue.firstPreferedTestCentre"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div>
	<s:select name="firstTestCenterId" cssClass="validate[required] enrollFinal"
		headerKey=""  errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VENUE_FIRSTPRETSTCNR_REQUIRE)%>" 
		headerValue="Select Test Center" cssClass="mid-dropdown validate[required]" list="testCenterList" id="firstTestCenterId" 
		listValue="labelValue" listKey="labelId" value="%{firstTestCenterId}"/>
</div>
<div id="err2" class="errorMessage"><s:text name="venue.errorlabelfirstPreferedTestCentre"/></div>
</div>


<div class="fl-left preCentreWidth">
<div class="field-label"><s:text name="venue.secondPreferedTestCentre"/>&nbsp;<span class="manadetory-fields">*</span></div>
	<div>
		<s:select name="secondTestCenterId" 
		headerKey=""  errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VENUE_SECONDPRETSTCNR_REQUIRE)%>" 
		headerValue="Select Test Center" cssClass="mid-dropdown validate[required] enrollFinal" list="testCenterList" id="secondTestCenterId" 
		listValue="labelValue" listKey="labelId" value="%{secondTestCenterId}"/>
	</div>
<div id="err3" class="errorMessage"><s:text name="venue.errorlabelsecondPreferedTestCentre"/><</div>
</div>

<div class="fl-left preCentreWidth">
<div class="field-label"><s:text name="venue.thirdPreferedTestCentre"/>&nbsp;<span class="manadetory-fields">*</span></div>
	<div>
		<s:select name="thirdTestCenterId" 
		headerKey="" headerValue="Select Test Center" cssClass="mid-dropdown validate[required] enrollFinal" 
		errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.VENUE_THIRDPRETSTCNR_REQUIRE)%>" 
		list="testCenterList" id="thirdTestCenterId" listValue="labelValue" listKey="labelId" value="%{thirdTestCenterId}"/>
	</div>
<div id="err4" class="errorMessage"><s:text name="venue.errorlabelthirdPreferedTestCentr"/></div>
</div>
</div>



<div class="height10 clear"></div>

<div class="clear">

<!-- For validation messages -->
<br/>
<br/>
<s:if test="%{actionName=='' || actionName == null}">
	<input type="button" value="<s:text name="venue.back"/>" title="Back" class="submitBtn button-gradient enrollFinal" id="btnVenuePrev"/>&nbsp;&nbsp;
	<input type="button" value="<s:text name="venue.cancel"/>" title="Cancel" class="submitBtn button-gradient cancelBtn"/>&nbsp;&nbsp;
	<input type="button" value="<s:text name="personaldetails.submit"/>" title="Next" class="submitBtn button-gradient enrollFinal" id="btnVenueNext" />
</s:if>
<s:elseif test="%{actionName=='preview' || actionName=='view'}">
	<input type="button" value="<s:text name="viewupdatepersonalinfo.update"/>" class="submitBtn button-gradient" id="btnEditVenue"/>
</s:elseif>
</div>

</div>
</form>