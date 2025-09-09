<%@page import="java.util.List"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>

<%@ taglib prefix="s" uri="/struts-tags"%>



<script type="text/javascript">

	function valPersonalInfo()
	{
		//alert($("#labelId").val());
		$("#labelId").val('-1');
		
	}
	
	function updateDisciplineDetails()
	{
		if($("#selectDisciplineContainer").validationEngine('validate')==false)
			return;
		
		var comboValue  = $("#labelId").val();
		//var dataString = "comboValue="+comboValue;
		var dataString = "disciplineId="+comboValue;
		$.ajax({
			url: "EnrollmentAction_updateDiscipline.action",
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
				responseText = $.trim(responseText);//alert(responseText);
				message = responseText.substring(2, responseText.length);
				textMessae = responseText.split(',');
				if(textMessae[0]=='9'){
					alert(message);
					return false;
				}else{
					if(responseText.length > 0)
					{
						var index = ($("#hDisciplineDivInd").val()*1)+1;
						//alert(index + "------" + responseText.charAt(0) + "---------------" + textMessae[2]);
						if(responseText.charAt(0) == "0")
						{
							if($("#actionNameDisp").val()=="preview" || $("#actionNameDisp").val()=="view")
							{
								submitPersonalDetailsForPreview();
							}
							else
							{
								$("#hddDisciplineId").val($("#labelId").val());
								$("#enrollValue").val(textMessae[2]);
								changeTabsTestEnrollments(index);
							}
						}
					}
				}
			}
		});
	}
	$(document).ready(function(){

		$("#selectDisciplineContainer").validationEngine({promptPosition : "bottomLeft", scroll: false});
		//$("#labelId").val('-1');
		$("#btnDisciplineInfoNext").click(function() {
			updateDisciplineDetails();
		});
		
		$("#btnEditDisciplineInfo").click(function() {
			updateDisciplineDetails();
		});
	});
</script>

<!-- Select Discipline Start -->
<form method="post" name="upload" id="selectDisciplineContainer">
<input type="hidden" id="hDisciplineDivInd" value="1"/>
<s:hidden name="actionNameDisp" id="actionNameDisp" value="%{actionName}"></s:hidden>
<div id="TabDiv1" class="tab_content" style="display:block">
<div class="field-label"><s:text name="selectdiscipline.title"/></div>
<div class="hr-dashline"></div>
<div>
<div class="fl-left preCentreWidth">
<div class="field-label"><s:text name="selectdiscipline.discipline"/>&nbsp;<span class="manadetory-fields">*</span></div>

<div>
		<s:select label="Discipline" name="disciplineId" cssClass="validate[required]" cssClass = "enrollFinal validate[required]"
		headerKey="" headerValue="Select Discipline" list="disciplineList" id="labelId" listValue="labelValue" listKey="labelId" 
		value="%{disciplineId}" 
		errRequired="<%=ResourceUtil.getValidationErrorMessageProperty(ValidationMessageConstants.DISCIPLINE_SELECT_REQUIRE)%>"/>
</div>
<div id="err2" class="errorMessage"><s:text name="selectdiscipline.errorlabel"/></div>
</div>

</div>

<div class="height10 clear"></div>
<div class="clear">
<!-- for validation message -->
<br/>
<s:if test="%{actionName=='' || actionName == null}">
	<input type="button" value="<s:text name="selectdiscipline.cancel"/>" title="Cancel" class="submitBtn button-gradient cancelBtn"  />&nbsp;&nbsp;
	<input type="button" value="<s:text name="selectdiscipline.next"/>" title="Next" class="submitBtn button-gradient enrollFinal" id="btnDisciplineInfoNext"/>
</s:if>
<s:elseif test="%{actionName=='preview' || actionName=='view'}">
	<input type="button" value="<s:text name="selectdiscipline.update"/>" class="submitBtn button-gradient" id="btnEditDisciplineInfo"/>
</s:elseif>

</div>

</div>
</form>
<!-- Select Discipline End -->