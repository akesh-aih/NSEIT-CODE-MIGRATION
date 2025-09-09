<script type="text/javascript">
$(document).ready(function() { 
    	$("a:contains('Block/Unblock IPs ')").html('<span class="fadeSubmenu">Block/Unblock IPs </span>')}); 


function validate() {
	  
		var selected="false";		 
		$(document).find(".checkBoxClass").each(function(index, curr){
			
			if(($(curr).is(":checked")+"")=="true")
			{
				//alert('1');
				selected="true";
			}
		});
	if(selected=="false")
	{
		alert("please select At least one Check box !!!!");
	}

	return selected=="true";
	}

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
</script>

<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main-body">
<div class="fade" id="pop3"></div>
<div id="BlockIP">
<div class="main-body">

<!-- Pending Candidate with Second Preference Start -->
<!-- Fade Container Start -->

<div class="fade" id="pop3"></div>
<!-- Fade Container End -->

<div id="AgencyTestSchedule">

<h1 class="pageTitle" title="Finalise Test Centre"><s:text name="agencytestmanagement.title"/></h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->

<div class="Tabcontainer">

    
<!-- Blacklist an IP Start -->
<s:form method="post" name="testmanagment"  action="TestMgmtAction">
<div class="errorMessageActive" id="EmailSmsError">		
				<s:actionmessage/>
</div>
<s:token></s:token>
<div class="tab_content" style="display:block">
<div class="field-label"></div>

<!-- Grid Start -->

<div class="BlockIPGridCont clear">
<div class="flexigrid">


<table width="100%" class="table_2" >
    <tr>
	    <th style="width:100px;" ><s:checkbox name="checkMe" fieldValue="true" id="checkMe" onclick="toggleChecked(this.checked)"/></th>
	    <th style="width:300px;"><s:text name="agencytestmanagement.headeripaddress"/></th>
	    <th style="width:300px;"><s:text name="agencytestmanagement.headernoofreg"/></th>
	    <!-- commented since not mentioned in brs.
	    <th align="left" class="sorted" style="width: 155px;">
	    	<div style="text-align: left;"><s:text name="agencytestmanagement.headermacAddress"/></div>
	    </th>
	     -->
	     <th style="width:250px;"><s:text name="agencytestmanagement.headerstatus"/></th>
    </tr>
    
<s:if test="%{testMgmtBeanList!=null}"> 
<s:iterator value="testMgmtBeanList" status="iterStatus">   

<tr>
<td align="center"><s:checkbox name="checkedValue"  fieldValue="%{addressPK}" cssClass="checkBoxClass" /></td>
<td><s:property value="ipAddress"/></td>
<td><s:property value="noRegistation"/></td>
<!-- commented since not mentioned in brs.
<td><s:property value="macAddress"/> </td>
-->
<s:if test='%{blackListed=="Y"}'>
	<td>Blocked</td>
	</s:if>
<s:elseif test='%{blackListed=="N"}'>
	<td class="erow">Un Blocked</td>
</s:elseif>


</tr>
</s:iterator>
</s:if>
<s:else>
	<tr>
		<td>
			  <div style="width: 562px;">
				<b >No record Founds</b>
			</div>
		</td>
</tr>
</s:else> 
</table>

<!-- 
<div class="pDiv"><div class="pDiv2"><div class="pGroup"><select name="rp"><option selected="selected" value="10">10&nbsp;&nbsp;</option><option value="15">15&nbsp;&nbsp;</option><option value="20">20&nbsp;&nbsp;</option><option value="25">25&nbsp;&nbsp;</option><option value="40">40&nbsp;&nbsp;</option></select></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pFirst pButton"><span></span></div><div class="pPrev pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol"><s:text name="agencytestmanagement.footerpage"/> <input type="text" value="1" size="4"> <s:text name="agencytestmanagement.footerof"/> <span>29</span></span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton"><span></span></div><div class="pLast pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pReload pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pPageStat"><s:text name="agencytestmanagement.footerdisplaying"/> 1 <s:text name="agencytestmanagement.footerto"/> 10 <s:text name="agencytestmanagement.footerof"/> 290 <s:text name="agencytestmanagement.footeritems"/></span></div></div><div style="clear: both;"></div></div>
 -->
</div>

</div>
<!-- Grid End -->


<div class="height10"></div>

<div class="activateBtn clear">
    <s:submit key ="agencytestmanagement.unblockip"  cssClass="submitBtn button-gradient" title="Unblock IP"  method="updateUnblockIP" onclick="return validate();"/>
    <s:submit key="agencytestmanagement.blockip" cssClass="submitBtn button-gradient" title="Block IP"      method="updateBlockIP"    onclick="return validate();"/>
</div>

 </div>
</s:form>
<!-- Blacklist an IP End -->

</div>

<!-- Box Container End -->

</div>
</div>
</div>
</div>

