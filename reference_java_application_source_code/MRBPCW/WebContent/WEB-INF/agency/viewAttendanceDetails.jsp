<script type="text/javascript">
$(document).ready(function() { 
    	$("a:contains('View Attendance')").html('<span class="fadeSubmenu">View Attendance</span>')}); 
</script>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function() {
		$("#testmanagment").validationEngine({promptPosition : "centerRight", scroll: false});

		$("#datepicker1").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			maxDate: 0,
	        numberOfMonths: 1,
	        onSelect: function(selected) {
	        $("#datepicker1").validationEngine('hide');
	          $("#datepicker2").datepicker("option","minDate", selected)
	        }
	    });
	    $("#datepicker2").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
	    	changeYear: true,
	    	yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			maxDate: 0,
	        numberOfMonths: 1,
	        onSelect: function(selected) {
	        	$("#datepicker2").validationEngine('hide');
	           $("#datepicker1").datepicker("option","maxDate", selected)
	        }
	    });  
	});
	function searchClicked()
	{
		alert($("#searchClicked").val());
		$("#searchClicked").val("Y");
		alert($("#searchClicked").val());
		return true;
	}
	
	
	/*$(function() {
		$( "#datepicker1" ).datepicker({
			showOn: "button",
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
		});
	});

	$(function() {
		$( "#datepicker2" ).datepicker({
			showOn: "button",
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy'
		});
	});*/
	
function resetEnrollmentDetailsSearch()
{
	 
		$('#testCenterPK option:first-child').attr("selected", "selected");
		$('#testCenterPK')[0].selectedIndex = 0;
		$('#discipilinePK option:first-child').attr("selected", "selected");
		$('#discipilinePK')[0].selectedIndex = 0;
		$('#datepicker1').val('');
		$('#datepicker2').val('');
		
}

function numbersonly(e){
	var unicode=e.charCode? e.charCode : e.keyCode
	if (unicode!=8){
	if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number
		return false //disable key press
	}
}
</script>
 
<!-- Fade Container Start -->
<div class="fade" id="pop3"></div>
<!-- Fade Container End -->
<div id="ViewAttendance">
<s:form method="post" name="testmanagment" id="testmanagment" action="TestMgmtAction">
<input type="hidden" id="searchClicked" name="searchClicked" value="N"/>
<s:token></s:token>
<s:actionerror/>
<h1 class="pageTitle" title="View Attendance"><s:text name="agencyviewattendance.title"/></h1>
<div class="hr-underline2"></div>

<div class="ViewAttendanceCont">
<div>

<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyviewattendance.discipline"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div>
		<s:select name="discipilinePK" id="discipilinePK"  cssClass="validate[required]" errRequired="Please select discipline"
		headerKey="" headerValue="--Select--" list="disciplineList" listValue="labelValue" listKey="labelId" />
		
</div>
<div class="errorMessage">&nbsp;</div>
</div>

<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyviewattendance.testcenter"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div>
		<s:select name="testCenterPK" id="testCenterPK" cssClass="validate[required]" errRequired="Please select test center"
		headerKey="" headerValue="--Select--" list="testCenterList" listValue="labelValue" listKey="labelId" />
		
</div>
<div class="errorMessage">&nbsp;</div>
</div>

</div>
<!-- Row One End -->

<!-- Row Two Start -->
<div class="clear">
<div>

<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyviewattendance.testfromdate"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div><s:textfield   cssClass="inputDate validate[required]" errRequired="Please select from date" id="datepicker1" readonly="true" onclick="dateChangeValue('datepicker1');" name="startDate" disabled="disabled"/></div>
<div id="defineError3" class="errorMessage">Please select test start date.</div>
</div>

<div class="fifty fl-rigt">
<div class="field-label"><s:text name="agencyviewattendance.testtodate"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div><s:textfield cssClass="inputDate validate[required]" errRequired="Please select to date" id="datepicker2" readonly="true" onclick="dateChangeValue('datepicker2');" name="endDate" disabled="disabled"/></div>
<div id="defineError4" class="errorMessage">Please select test end date.</div>
</div>
</div>
</div>

<!-- Row Two End -->

<!-- Row Three Start -->
<div class="clear">
<div class="fl-left fifty">
<div>
 <s:submit type="submit" id ="btnSearch" key="agencyenrollmentdetails.search" title="Search"  cssClass="submitBtn button-gradient" method="getCandidateAttendanceSearch" onclick="return searchClicked();"></s:submit>&nbsp;&nbsp;
 <input type="button" value="<s:text name="global.clear"/>" title="Clear" class="submitBtn button-gradient"onclick="resetEnrollmentDetailsSearch();" /> 


</div>
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
<div class="ViewAttendanceGrid clear">

<s:if test='%{viewAttendanceDisplay=="true"}'>
<div class="flexigrid">

<table width="100%" class="table_2">
    <tr>
    <th ><s:text name="agencyviewattendance.headerdiscipline"/></th>
    <th ><s:text name="agencyviewattendance.headertestcentre"/></th>
    <th ><s:text name="agencyviewattendance.headertestdate"/></th>
    <th ><s:text name="agencyviewattendance.headertestslot"/></th>
    <th ><s:text name="agencyviewattendance.headertotalenrollment"/></th>
    <th ><s:text name="agencyviewattendance.headertotalappeared"/></th>
    </tr>
    
<s:if test='%{searchClickedFlag=="true"}'>
<s:if test="%{!testMgmtBeanList.isEmpty}">
<s:iterator value="testMgmtBeanList" >   
<tr>
<td><s:property value="discipiline"/></td>
<td><s:property value="testCentre"/></td>
<td><s:property value="testDate"/></td>
<td><s:property value="testSlot"/></td>
<td><s:property value="totalEnrollment"/></td>
<td><s:property value="totalAppered"/></td>
</tr>
</s:iterator>
</s:if>
<s:else>
	<tr >
		<td colspan="6" align="center">
				<b><s:text name="global.norecordfound"/></b>
		</td>
</tr>
</s:else> 
</s:if>

</table>

<%--<div class="pDiv"><div class="pDiv2"><div class="pGroup"><select name="rp"><option selected="selected" value="10">10&nbsp;&nbsp;</option><option value="15">15&nbsp;&nbsp;</option><option value="20">20&nbsp;&nbsp;</option><option value="25">25&nbsp;&nbsp;</option><option value="40">40&nbsp;&nbsp;</option></select></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pFirst pButton"><span></span></div><div class="pPrev pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol"><s:text name="agencyviewattendance.footerpage"/> <input type="text" value="1" size="4"> <s:text name="agencyviewattendance.footerof"/> <span>29</span></span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton"><span></span></div><div class="pLast pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pReload pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pPageStat"><s:text name="agencyviewattendance.footerdisplaying"/> 1 <s:text name="agencyviewattendance.footerto"/> 10 <s:text name="agencyviewattendance.footerof"/> 290 <s:text name="agencyviewattendance.footeritems"/></span></div></div><div style="clear: both;"></div></div>--%>
</div>
</s:if>
</div>
</s:form>
<!-- Grid End -->

<!-- Box Container End -->
</div>
