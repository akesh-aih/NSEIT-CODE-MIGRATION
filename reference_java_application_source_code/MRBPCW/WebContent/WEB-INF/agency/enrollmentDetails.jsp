<%@ taglib prefix="s" uri="/struts-tags"%>

<script>
$(document).ready(function() {
	$("#enrollmentDetailFrm").validationEngine({promptPosition : "centerRight", scroll: false});
	$("#testCenterIdNew").change(
	function (){
	getDateDefinition($(this).val());
	}
	);
	
	
	$("#mySelect").change(
	function (){
	getTimeSlot($(this).val());
	}
	);
	$(document).ready(function() { 
	$("a:contains('Enrollment Details')").html('<span class="fadeSubmenu">Enrollment Details</span>')}); 	
});
function disableDropDowns()
{
	var enrlmntId=$("#enrollemntId").val();
		if(enrlmntId == "")
		{
			$("#testCenterIdNew").attr("disabled", false); 
			$("#mySelect").attr("disabled", false); 
			$("#timeSlot").attr("disabled", false); 
			$("#labelId").attr("disabled", false); 
		}
		else
		{
			$("#testCenterIdNew").attr("disabled", true); 
			$("#mySelect").attr("disabled", true); 
			$("#timeSlot").attr("disabled", true); 
			$("#labelId").attr("disabled", true); 
		}
}

function enableDropDowns()
{
	$("#testCenterIdNew").attr("disabled", false); 
	$("#mySelect").attr("disabled", false); 
	$("#timeSlot").attr("disabled", false); 
	$("#labelId").attr("disabled", false); 
	
}
function getDateDefinition(testVar)
	{
		//alert('datae Selected'+testVar);
		var dataString = "testCenterId="+testVar;

		$.ajax({
			url: "CandidateMgmtAction_getDateDetailList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
			 $('#mySelect').empty();
			 $('#mySelect').append(  
			            $('<option></option>').val('-1').html('ALL')
			     );
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing available seats. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				responseText = $.trim(responseText);
				//alert(responseText);
				if(responseText.length > 0 && responseText != 'null' && responseText != null )
				{
					 var element = responseText.split(",");  
					$.each(element, function(val) {
				
					    $('#mySelect').append(  
					            $('<option></option>').val(element[val]).html(element[val])
					     );
					}); 
				
				}
			}
		});
	}
	
function numbersonly(e){
	disableDropDowns();
	var unicode=e.charCode? e.charCode : e.keyCode
	if (unicode!=8){ //if the key isn't the backspace key (which we should allow)
	if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number
	return false //disable key press
	}
	}
	
	function getTimeSlot(testVar)
	{
	
		var testCentersId = $("#testCenterIdNew").val();
		
		var dataString = "examDate="+testVar+"&testCenterId="+testCentersId;
		$.ajax({
			url: "CandidateMgmtAction_getTimeSlotList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
			 $('#timeSlot').empty();
				$("#timeSlot")								
		          .append($("<option></option>")
		          .attr("value",'')
		          .text('ALL'));
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing available seats. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				responseText = $.trim(responseText);
				textMessae = responseText.split(',');
				if(textMessae[0]=='2'){
					alert("Error : \n"+textMessae[1]);
					return false;
				}else{
					if(responseText.length > 0 && responseText != 'null' && responseText != null )
					{
					 var element = responseText.split(",");  
					$.each(element, function(val) {
				
					   $('#timeSlot').append(  
					            $('<option></option>').val(element[val]).html(element[val])
					   );
					}); 
				
				}
			}}
		});
	}
	/*
		$(function() {
		$( "#mySelect" ).datepicker({
			showOn: "button",
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
		});
	});
	*/
	function resetEnrollmentDetailsSearch(){
		
		$("#enrollemntId").val("");
		$("#labelId").val("");
		$("#testCenterIdNew").val("-1");
		$("#mySelect").val("-1");
		$("#timeSlot").val("");
	}
	
</script>





<div class="main-body">

<div class="fade" id="pop3"></div>


<div id="EnrollmentDetails">

<h1 class="pageTitle" title="Enrollment Details"><s:text name="agencyenrollmentdetails.title"/></h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<s:form method="post" action="CandidateMgmtAction" id="enrollmentDetailFrm">
<s:token></s:token>
<s:actionerror/>
<div class="EnrollmentCont">

<div>
<div class="field-label"><s:text name="agencyenrollmentdetails.enrollemtID"/>&nbsp;</div>
	<div>
		<s:textfield name="enrollemntId" id = "enrollemntId" maxlength="15"  onblur="disableDropDowns()" onkeypress="return numbersonly(event);"></s:textfield>
	</div>
</div>

<div class="height10"></div>
<div class="WeightBold"><s:text name="agencyenrollmentdetails.or"/></div>
<div class="height10"></div>

<!-- Row One Start -->
<div class="clear">
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyenrollmentdetails.discipline"/>&nbsp;</div>
<div>
<s:select label="Discipline" name="disciplineId"  
		headerKey="" headerValue="ALL" list="disciplineList" id="labelId" listValue="labelValue" listKey="labelId" value="%{disciplineId}"/>
</div>
<div id="fnameID" class="errorMessage">Please type your First Name.</div>
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyenrollmentdetails.testcenter"/>&nbsp;</div>
<div>
	<s:select name="testCenterId" 
		headerKey="-1" headerValue="ALL" list="testCenterList" id="testCenterIdNew" listValue="labelValue" listKey="labelId" value="%{testCenterId}"/>
</div>
<div id="lnameID" class="errorMessage">Please type your Last Name.</div>
</div>
</div>
<!-- Row One End -->

<!-- Row Two Start -->
<div class="clear">

<div>
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyenrollmentdetails.date"/>&nbsp;</div>
<div>
	
	<select id="mySelect" name="examDate" >
		<option value="">ALL</option>
	</select>
	<!--  commented by Raman to put it as datePicker.
	<s:textfield name="examDate" id="mySelect" disabled="disabled" cssClass="inputDate validate[required,custom[date]]" onfocus=""></s:textfield>
	-->
</div>
<div id="fnameID" class="errorMessage">Please type your First Name.</div>
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyenrollmentdetails.testslot"/>&nbsp;</div>
<div>
	
		<select id="timeSlot" name="timeSlotId" >
			<option value=""> ALL</option>
		</select>
</div>
<div id="lnameID" class="errorMessage">Please type your Last Name.</div>
</div>
</div>

</div>
<!-- Row Two End -->

<!-- Row Three Start -->
<div class="clear">
<div class="fl-left fifty">
<div>
	<s:submit type="submit" key="agencyenrollmentdetails.search" title="Search"  cssClass="submitBtn button-gradient" method="getEnrollmentDetails"></s:submit>&nbsp;&nbsp;
	<input type="button" value="<s:text name="agencyenrollmentdetails.clear"/>" title="Clear" class="submitBtn button-gradient" onclick="resetEnrollmentDetailsSearch();"/></div>
</div>
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Three End -->
<div class="height20"></div>

</div>

<!-- Dash Line Start -->
<div class="clear hr-dashline"></div>
<!-- Dash Line End -->

<!-- Grid Start -->
<div class="EnrollmentGrid">

<s:if test='%{enrollDisplayFlag=="true"}'>

<table cellspacing="0" cellpadding="3" class="table_2" border="1" width="800" >
    <thead>
    <tr>
    <th style="width: 45px;">Sr. No.</th>
    <th style="width: 85px;"><s:text name="agencyenrollmentdetails.headerenrollmetID"/></th>
    <th style="width: 140px;"><s:text name="agencyenrollmentdetails.headername"/></th>
    <th style="width: 150px;"><s:text name="agencyenrollmentdetails.headertest"/></th>
    <th style="width: 200px;"><s:text name="agencyenrollmentdetails.headercenter"/></th>
    <th style="width: 90px;"><s:text name="agencyenrollmentdetails.headertestdate"/></th>
    <th style="width: 90px;"><s:text name="agencyenrollmentdetails.headertestslot"/></th>
    </tr></thead>
    <tbody>

<s:if test="%{!enrollmentDetailsList.isEmpty}">
<%int i = 1; %> 
<s:iterator value="enrollmentDetailsList">
	<tr>
	<td align="center" ><%=i++ %></td>
	
	<td><!-- <a href="#"> </a>--><s:property value="enrollment_PK"/></td>
	<td><s:property value="firstName"/>&nbsp;&nbsp; <s:property value="lastName"/></td>
	<td><s:property value="testName"/></td>
	<td><s:property value="testCenterName"/></td>
	<td><s:property value="testDate"/></td>
	<td><s:property value="timeSlot"/></td>
	</tr>
</s:iterator>
</s:if>
<s:else>
	<tr>
		<td colspan="7" align="center"><b><s:text name="global.norecordfound"/></b></td>
</tr>
</s:else> 
</tbody>
</table>
</s:if>

<%--<div class="pDiv"><div class="pDiv2"><div class="pGroup"><select name="rp"><option selected="selected" value="10">10&nbsp;&nbsp;</option><option value="15">15&nbsp;&nbsp;</option><option value="20">20&nbsp;&nbsp;</option><option value="25">25&nbsp;&nbsp;</option><option value="40">40&nbsp;&nbsp;</option></select></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pFirst pButton"><span></span></div><div class="pPrev pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol"><s:text name="agencyenrollmentdetails.footerpage"/> <input type="text" value="1" size="4"> <s:text name="agencyenrollmentdetails.footerof"/> <span>29</span></span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton"><span></span></div><div class="pLast pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pReload pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pPageStat"><s:text name="agencyenrollmentdetails.footerdisplaying"/> 1 <s:text name="agencyenrollmentdetails.footerto"/> 10 <s:text name="agencyenrollmentdetails.footerof"/> 290 <s:text name="agencyenrollmentdetails.footeritems"/></span></div></div><div style="clear: both;"></div></div>--%>



<!-- Button Start -->
<div class="height20"></div>

<!-- Button End -->


</div>
<!-- Grid End -->


</s:form>
<!-- Box Container End -->
</div>
</div>

