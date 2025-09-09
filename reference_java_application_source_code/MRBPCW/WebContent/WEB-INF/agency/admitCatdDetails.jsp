<script type="text/javascript">
	$(document).ready(function() {
		$("#register").validationEngine({promptPosition : "centerRight", scroll: false});
	});
			
	$(function() {
		$( "#datepicker1" ).datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			minDate:0,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
		});
	});
 $(document).ready(function() { 
 $("a:contains('Generate Admit Card')").html('<span class="fadeSubmenu">Generate Admit Card</span>')}); 


 
 function numbersonly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8){
		if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number
			return false //disable key press
		}
	}


 function validate() {
		
		var selected="false";		
		$(document).find(".checkBoxClass").each(function(index, curr){
			
			if(($(curr).is(":checked")+"")=="true")
			{
			
				selected="true";
			}
		});

		if(selected=="false")
		{
			alert("Please Select Atleast One Checkbox !!!!");
			return false;
		}

		return true;
	}





	
	function resetAdmitSearch(){
		$("#enrollmentId").val("");
		$("#labelId").val("");
		$("#datepicker1").val("");
		
		if ($('#labelId').is(':disabled') == true) {
            $("#labelId").removeAttr("disabled");
        } 
        
        if ($('#datepicker1').is(':disabled') == true) {
            $("#datepicker1").removeAttr("disabled");
        }
        
        if ($('#enrollmentId').is(':disabled') == true) {
            $("#enrollmentId").removeAttr("disabled");
        } 
		
	}
	
	function disableDropDownsForSearch()
	{
		var enrlmntId=$("#enrollmentId").val();

		if (enrlmntId!=""){
			$("#labelId").attr("disabled", true); 
			$("#datepicker1").attr("disabled", true); 
		}
		
		var labelId=$("#labelId").val();
		var datepicker1=$("#datepicker1").val();

		if (labelId!="" || datepicker1!=""){
			$("#enrollmentId").attr("disabled", true); 
		}
	}
	
	</script>

<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main-body">
<div class="fade" id="pop3"></div>

<div id="AgencyAdmitCard">

<h1 class="pageTitle" title="Generate Admit Card"><s:text name="agencygenerateadmitcard.title"/></h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<form method="post" name="register" id="register">

<div class="errorMessageActive" id="admitCardError">		
				<s:actionmessage/>
			</div>
<div class="AgencyAdmitCont">


<div>

<div>
<div class="field-label"><s:text name="agencygenerateadmitcard.enrollmentID"/>&nbsp;</div>
<div>
	<s:textfield name="enrollmentId" id="enrollmentId" maxlength="15" onkeypress="return numbersonly(event);" onblur="disableDropDownsForSearch()" cssClass="inputField" errRequired="Please enter Enrollment ID"/>
</div>
</div>

<div class="height10"></div>
<div class="WeightBold"><s:text name="agencygenerateadmitcard.or"/></div>
<div class="height10"></div>

<!-- Row One Start -->
<div class="clear">
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencygenerateadmitcard.discipline"/>&nbsp;</div>
<div>
	<s:select label="Discipline" name="disciplineId"  onblur="disableDropDownsForSearch()" 
		headerKey="" headerValue="All" list="disciplineListMap" id="labelId" 	value="%{disciplineId}"/>
</div>
<div id="fnameID" class="errorMessage"></div>
</div>

<div class="fl-rigt fifty">
<div class="field-label">Test Center&nbsp;</div>
<div>
<s:select label="Discipline" name="testCenterVal"  onblur="disableDropDownsForSearch()" 
		headerKey="" headerValue="All" list="testCenterListMap" value="%{testCenterVal}"/>
	<!--<s:textfield name="enrollmentDate" id = "datepicker1" readonly="true"  onblur="disableDropDownsForSearch()" />
-->
</div>
<div class="field-label">Attempts &nbsp;</div>
<div>
<s:select label="Discipline" name="attemptVal"  onblur="disableDropDownsForSearch()" 
		headerKey="" headerValue="All" list="attemptsList" value="%{attemptVal}"/>

</div>

<div id="fnameID" class="errorMessage"></div>
</div>

</div>
<!-- Row One End -->

<!-- Row Two Start -->
<div class="clear">
<div class="fl-left fifty">
<div>
	
	<s:submit key="agencygenerateadmitcard.submit" cssClass="submitBtn button-gradient" method="getAdmitCardList"></s:submit>
	<input type="button" value="<s:text name="agencygenerateadmitcard.clear"/>" title="Clear" class="submitBtn button-gradient" onclick="resetAdmitSearch();"/>
	
</div>
</div>
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Two End -->

<div class="height20"></div>

</div>

</div>

<!-- Dash Line Start -->
<div class="clear hr-dashline"></div>
<!-- Dash Line End -->
</form>
<!-- Box Container End -->
</div>
</div>
<s:if test='%{admitCardDisplayFlag=="true"}'>
<s:form action="CandidateMgmtAction">
<s:token/>
<div class="EnrollmentGrid">
<s:if test='%{admitCardHeaderDisplayFlag=="true"}'>
<table cellspacing="0" cellpadding="3" width="800" border="1" class="table_2" bordercolor="#CCCCCC" >
    <thead>
    <tr>
    <th style="width: 45px;">&nbsp;</th>
    <th style="width: 125px;"><s:text name="agencyenrollmentdetails.headerenrollmetID"/></th>
    <th style="width: 170px;"><s:text name="agencyenrollmentdetails.headertest"/></th>
    <th style="width: 220px;"><s:text name="agencyenrollmentdetails.headercenter"/> </th>
	<th style="width: 120px;"><s:text name="agencyenrollmentdetails.headertestdate"/></th>
    <th style="width: 120px;"><s:text name="agencyenrollmentdetails.headeradmitcardstatus"/></th>
    </tr></thead>
    <tbody>
<s:if test="%{admitCardList!=null}">
<%int i = 1; %> 
<s:iterator value="admitCardList">
	<tr>
			<td align="center"><s:checkbox name="userPKForAdmitCard" cssClass="checkBoxClass" fieldValue="%{userPK}" value="" ></s:checkbox></td>
		<td><s:property value="enrollmentPK"/></td>
		<td><s:property value="testName"/></td>
		<td><s:property value="testCenterName"/></td>
		<td><s:property value="testDate"/></td>
		<td>Pending</td>
	
	</tr>
</s:iterator>

</s:if>
<s:else>
	<tr >
	<td colspan="6" align="center"><b>No Record Found</b></td>
	</tr>
</s:else>
</tbody> 
</table>
</s:if>
<s:if test="%{!admitCardList.isEmpty()}">
<div class="height20"></div>
<div class="clear" align="center">
<div>
	<s:submit method="generateAdmitCard" key="register.submit" cssClass="submitBtn button-gradient"  onclick="return validate();"></s:submit>
</div>

<div class="fl-rigt fifty">&nbsp;</div>
</div>
</s:if>
</s:form>

<!--<div class="pGroup">-->
<!--<select name="rp"><option selected="selected" value="10">10&nbsp;&nbsp;</option><option value="15">15&nbsp;&nbsp;</option><option value="20">20&nbsp;&nbsp;</option><option value="25">25&nbsp;&nbsp;</option><option value="40">40&nbsp;&nbsp;</option></select></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pFirst pButton"><span></span></div><div class="pPrev pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol"><s:text name="agencyenrollmentdetails.footerpage"/> <input type="text" value="1" size="4"> <s:text name="agencyenrollmentdetails.footerof"/> <span>29</span></span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton"><span></span></div><div class="pLast pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pReload pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pPageStat"><s:text name="agencyenrollmentdetails.footerdisplaying"/> 1 <s:text name="agencyenrollmentdetails.footerto"/> 10 <s:text name="agencyenrollmentdetails.footerof"/> 290 <s:text name="agencyenrollmentdetails.footeritems"/></span></div></div><div style="clear: both;"></div></div>-->
<!--</div>-->


<!-- Button Start -->
<div class="height20"></div>

<!-- Button End -->

</s:if>

<s:if test='%{admitCardDisplayFlag=="false"}'>
		<s:text name="agencyenrollmentdetails.windownotavailablemessage"/> 
</s:if>

