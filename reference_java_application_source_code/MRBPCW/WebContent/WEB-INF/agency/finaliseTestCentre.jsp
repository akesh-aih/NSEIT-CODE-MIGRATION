<%
	try{ 
%>
<script type="text/javascript">
$(document).ready(function() { 
    	$("a:contains('Finalise Test Centre')").html('<span class="fadeSubmenu">Finalise Test Centre</span>')}); 

function validate(actionName) {
	  
		var selected="false";	
		var status="";	
		$(document).find(".checkBoxClass").each(function(index, curr){
			
			if(($(curr).is(":checked")+"")=="true")
			{
				
				$(curr).parent().parent().find(".classStatusDiv").each(function(innerIndex, innerCurr) {
	
						status = $(innerCurr).html();
						
	
						if((status == "Active" && actionName=="active") || (status == "Inactive" && actionName=="inactive"))
						{
							selected = "sameStatus";
						}
					});
	
					if(selected == "sameStatus")
					{
						alert("Test Center cannot be updated with same status.");
						return false;
					}
					
				selected="true";
			}
		});
			if(selected=="false")
			{
				alert("Please Select Atleast One Checkbox !!!!");
			}
		
			return selected=="true";
	}


	

</script>
<SCRIPT language="javascript">
$(function(){
 
    // add multiple select / deselect functionality
    $("#checkMe").click(function () {
          $('.checkBoxClass').attr('checked', this.checked);
    });
 
    // if all checkbox are selected, check the selectall checkbox
    // and viceversa
    $(".checkBoxClass").click(function(){
 
        if($(".checkBoxClass").length == $(".checkBoxClass:checked").length) {
            $("#selectall").attr("checked", "checked");
        } else {
            $("#selectall").removeAttr("checked");
        }
 
    });
});
</SCRIPT>

<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main-body">

<!-- Pending Candidate with Second Preference Start -->
<!-- Fade Container Start -->

<div class="fade" id="pop3"></div>
<!-- Fade Container End -->

<div id="AgencyTestSchedule">

<h1 class="pageTitle" title="Finalise Test Centre"><s:text name="agencyfinalisetestcentre.title"/></h1>
<div class="hr-underline2"></div>

<!-- For Activities Start-->
<s:form name="testmanagment" action="TestMgmtAction">
 <s:actionmessage/>

<!-- Grid Start -->
<div class="TestScheduleGrid">
<div class="height10"></div>

<div class="flexigrid">

<s:token/>
<table width="100%" class="table_2" >
    <thead>
    <tr >
    <th rowspan="2" style="width:40px;">
    <s:checkbox name="checkMe" fieldValue="true" id="checkMe" onclick="toggleChecked(this.checked)"/>
    </th>
    <th rowspan="2" style="width:150px;">
    <s:text name="agencyfinalisetestcentre.headertestcentre"/>
    </th>
    <th colspan="2" >
    <s:text name="agencyfinalisetestcentre.headercapacity"/>
    </th>
    <th colspan="3" >
    <s:text name="agencyfinalisetestcentre.headercandidatereq"/>
    </th>
    <th rowspan="2">
    <s:text name="agencyfinalisetestcentre.headerstatus"/>
    </th>
    </tr>
    
    <tr>
    <th><s:text name="agencyfinalisetestcentre.subheadertotal"/></th>
    <th ><s:text name="agencyfinalisetestcentre.subheaderavailable"/></th>
    <th ><s:text name="agencyfinalisetestcentre.subheaderfirstpreference"/></th>
    <th ><s:text name="agencyfinalisetestcentre.subheadersecondpreference"/></th>
    <th ><s:text name="agencyfinalisetestcentre.subheaderthirdpreference"/>
    </th>
    </tr>
    </thead>

<s:if test="%{testMgmtBeanList!=null}"> 
<s:iterator value="testMgmtBeanList">
<tr>
<td width="20" align="center"><s:checkbox name="checkedValue" fieldValue="%{testCenterPK}" cssClass="checkBoxClass" id="checkBoxClass"/></td>
<td width="80" align="left"><s:property value="%{testCentre}"/></td>
<td width="70" align="right"><s:property value="%{total}"/></td>
<td width="70" align="right"><s:property value="%{available}"/></td>
<td width="100" align="right"><s:property value="%{firstPreference}"/></td>
<td width="120" align="right"><s:property value="%{secondPreference}"/></td>
<td width="100" align="right"><s:property value="%{thirdPreference}"/></td>

<s:if test='%{status=="Active"}'>
	<td align="center" ><div style="width: 80px;" class="classStatusDiv">Active</div></td>
</s:if>
<s:elseif test='%{status=="Inactive"}'>
	<td class="erow" align="center" > <div style="width: 80px;" class="classStatusDiv">Inactive</div></td>
</s:elseif>
</tr>
 </s:iterator>
 </s:if>
 <s:else>
	<tr>
		<td calspan="7">
				<b ><s:text name="global.norecordfound"/></b>
		</td>
</tr>
</s:else> 
</table>



<!--<div class="pDiv2"><div class="pGroup"><select name="rp"><option selected="selected" value="10">10&nbsp;&nbsp;</option><option value="15">15&nbsp;&nbsp;</option><option value="20">20&nbsp;&nbsp;</option><option value="25">25&nbsp;&nbsp;</option><option value="40">40&nbsp;&nbsp;</option></select></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pFirst pButton"><span></span></div><div class="pPrev pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol"><s:text name="agencyfinalisetestcentre.footerpage"/> <input type="text" value="1" size="4"> <s:text name="agencyfinalisetestcentre.footerof"/> <span>29</span></span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton"><span></span></div><div class="pLast pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pReload pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pPageStat"><s:text name="agencyfinalisetestcentre.footerdisplaying"/> 1 <s:text name="agencyfinalisetestcentre.footerto"/> 10 <s:text name="agencyfinalisetestcentre.footerof"/> 290 <s:text name="agencyfinalisetestcentre.footeritems"/></span></div></div><div style="clear: both;"></div></div>-->
</div>

<!-- Button Start -->

<div class="height20"></div>
<div class="clear activateBtn">
<s:submit title="Activate"   cssClass="submitBtn button-gradient"  key ="agencyfinalisetestcentre.active"   method="setActiveTestCentreDetails" onclick="return validate('active');"/>&nbsp;&nbsp;
<s:submit title="Deactivate" cssClass="submitBtn button-gradient"  key ="agencyfinalisetestcentre.deactive" method="setDeActiveTestCentreDetails" onclick="return validate('inactive');"/>
</div>
<!-- Button End -->
</s:form>
</div>
<!-- Grid End -->
<!-- Box Container End -->
</div>
</div>

<%
}catch(Exception e){
	e.printStackTrace();
 } 
%>
