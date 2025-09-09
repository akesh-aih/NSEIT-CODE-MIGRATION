<%@ taglib prefix="s" uri="/struts-tags"%>

<script>
$(document).ready(function() {

	$("#register").validationEngine({promptPosition : "centerRight", scroll: false});
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
	
	  $('.checkBoxClass').attr('checked', false); 
	  
	$("#tblEligibleNonEligible").find(".inputField").each(function(index, curr) {
		
		$(curr).attr("name", "reasontemp");
		
	});
});

function getDateDefinition(testVar)
	{
		var dataString = "testCenterId="+testVar;

		$.ajax({
			url: "CandidateMgmtAction_getDateDetailList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
				$('#mySelect').empty();
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing available seats. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				//alert("ddddddddddddd----"+responseText);
				responseText = $.trim(responseText);
				if(responseText.length > 0)
				{
					 var element = responseText.split(",");  
					$.each(element, function(val) {
				
					    $('#mySelect').append(  
					            $('<option></option>').val(element[val]).html(element[val])
					     );
					}); 
				
				}

				if(responseText=="null")
				{
					 $('#mySelect').empty();
					 $('#mySelect').append(  
						            $('<option value="">Select Date</option>')
						   );	
				   alert("No Test Date Available");
				}
			}
		});
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
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing available seats. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				//alert("tttttttttttt----"+responseText);
				responseText = $.trim(responseText);
				textMessae = responseText.split(',');
				if(textMessae[0]=='2'){
					alert("Error : \n"+textMessae[1]);
					return false;
				}else{
					if(responseText.length > 0)
					{
						 var element = responseText.split(",");  
						$.each(element, function(val) {
					
						   $('#timeSlot').append(  
						            $('<option></option>').val(element[val]).html(element[val])
						   );
						}); 
					
					}

					if(responseText=="null")
					{
						 $('#timeSlot').empty();
						 $('#timeSlot').append(  
							            $('<option value="">Select Test Slot</option>')
							   );	
					   alert("No Test Slot Available");
					}
					
				}
			}
		});
	}
	function numbersonly(e){
		var unicode=e.charCode? e.charCode : e.keyCode
		if (unicode!=8){
		if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number
			return false //disable key press
		}
	}
	function resetEligibleSearch(){
		
		$("#enrollemntId").val("");
		$("#labelId").val("");
		$("#testCenterIdNew").val("");
		$("#mySelect").val("");
		$("#timeSlot").val("");
		$("#eligibleListId").val("");
		$("#dataError").remove();
		
		$("#testCenterIdNew").attr("disabled", false); 
		$("#mySelect").attr("disabled", false); 
		$("#timeSlot").attr("disabled", false); 
		$("#labelId").attr("disabled", false); 
		$("#eligibleListId").attr("disabled", false); 
		
		 $('#mySelect').empty();
					 $('#mySelect').append(  
						            $('<option value="">Select Date</option>')
						   );	
		
	}
	$(document).ready(function() { 
	$("a:contains('Eligible/Non-Eligible Candidate')").html('<span class="fadeSubmenu">Eligible/Non-Eligible Candidate</span>')}); 	


	function disableDropDowns()
	{
		var enrlmntId=$("#enrollemntId").val();
			if(enrlmntId == "")
			{
				$("#testCenterIdNew").attr("disabled", false); 
				$("#mySelect").attr("disabled", false); 
				$("#timeSlot").attr("disabled", false); 
				$("#labelId").attr("disabled", false); 
				$("#eligibleListId").attr("disabled", false); 
				
			}
			else
			{
				$("#testCenterIdNew").attr("disabled", true); 
				$("#mySelect").attr("disabled", true); 
				$("#timeSlot").attr("disabled", true); 
				$("#labelId").attr("disabled", true); 
				$("#eligibleListId").attr("disabled", true); 
				
			}
	}
	
	
	function validate(actionName) {
		
		var selected="false";
		var status = "";
		var fieldNameToChange = new Array();
		var currArrayElement = 0*0;
		
		$(document).find(".checkBoxClass").each(function(index, curr){
			
			if(($(curr).is(":checked")+"")=="true")
			{
				
				$(curr).parent().parent().find(".classStatusDiv").each(function(innerIndex, innerCurr) {

					status = $(innerCurr).html() + "";
					//alert($.trim(status) == "Non-Eligible");
					if(($.trim(status) == "Non-Eligible" && actionName=="noneligible") || ($.trim(status) == "Eligible" && actionName=="eligible"))
					{
						selected = "sameStatus";
					}
				});

				if(selected == "sameStatus")
				{
					alert("Candidate cannot be updated with same status.");
					return false;
				}
				
				if(actionName=="noneligible")
				{
					//alert($(curr).parent().parent().parent().get(0).tagName);
					$(curr).parent().parent().find(".inputField").each(function(innerIndex, innerCurr) {

						fieldNameToChange[currArrayElement] = innerCurr;
						currArrayElement = currArrayElement + 1;

						$(innerCurr).val($.trim($(innerCurr).val()));
						//alert($(innerCurr).val());
						if($(innerCurr).val()=="")
						{
							selected = "noreason";
						}
					});					
				}
				else if(actionName=="eligible")
				{
					$(curr).parent().parent().parent().find(".inputField").each(function(innerIndex, innerCurr) {

						fieldNameToChange[currArrayElement] = innerCurr;
						currArrayElement = currArrayElement + 1;
					});					
				}

				if(selected == "noreason")
				{
					alert("Please Enter Reason for Non-Eligible Candidate(s).");
					return false;
				}
				selected="true";
			}
		});

		if(selected == "noreason" || selected == "sameStatus")
		{
			return false;
		}
		if(selected=="false")
		{
			alert("Please Select Atleast One Checkbox !!!!");
			return false;
		}

		if(currArrayElement > 0)
		{
			for(currElement=0; currElement<currArrayElement; currElement=currElement+1)
			{
				$(fieldNameToChange[currElement]).attr("name", "reason");
			}
		}

		return true;
	}

	function enableReason(reasonPk)
	{
	//alert ("start of func"+reasonPk.checked);
		if(reasonPk.checked)
		{
			$("#reasonId_"+reasonPk.value).attr("disabled", false);
		}else
		{
			$("#reasonId_"+reasonPk.value).attr("disabled", true);
		} 
	}

	function validate2() {
		
		var selected2="false";		
		$(document).find(".inputField").each(function(index, curr){
		
			if(($(curr).is(":text"))!="")
			{
				//alert('1');
				alert("checking "+$(this).val());
				
				selected2="true";
			}
		});
	if(selected2=="false")
	{
		alert("Please Enter The Reason !!!!");
		return false;
	}

	return selected2=="true";
	}
	
	
$(function(){
 
    // add multiple select / deselect functionality
    $("#checkMe").click(function () {
          $('.checkBoxClass').attr('checked', this.checked);
           $(".inputField").attr("disabled", !this.checked);
    });
 
    // if all checkbox are selected, check the selectall checkbox
    // and viceversa
    $(".checkBoxClass").click(function(){
 
        if($(".checkBoxClass").length == $(".checkBoxClass:checked").length) {
        	//alert('checked');
            $("#selectall").attr("checked", "checked");
           // $(".inputField").attr("disabled", false);
            
        } else {
            $("#selectall").removeAttr("checked");
            //$(".inputField").attr("disabled", true);
        }
 
    });
});

</script>



<div class="main-body">
<div class="fade" id="pop3"></div>

<div id="ElgCandidate">

<h1 class="pageTitle" title="Eligible/Non-Eligible Candidate"><s:text name="agencyeligiblenoneligible.title"/></h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<form method="post" name="register" id="register">
<s:token></s:token>
<div class="ElgCandidateCont" style="margin:30px 0px 20px 60px;">

<div>
<div class="field-label"><s:text name="agencyeligiblenoneligible.enrollemtID"/>&nbsp;</div>
	<div>
			<s:textfield name="enrollemntId" id="enrollemntId" maxlength="15"  onblur="disableDropDowns()" onkeypress="return numbersonly(event);" cssClass="inputField validate[]" errRequired="Please enter enrollment ID"></s:textfield>
	</div>
</div>

<div class="height10"></div>
<div class="WeightBold"><s:text name="agencyeligiblenoneligible.or"/></div>
<div class="height10"></div>

<!-- Row One Start -->
<div >
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyeligiblenoneligible.discipline"/>&nbsp;</div>
<div>
		<s:select label="Discipline" name="disciplineId"  cssClass="validate[]" errRequired="Please select discipline"
		headerKey="" headerValue="ALL" list="disciplineList" id="labelId" listValue="labelValue" listKey="labelId" value="%{disciplineId}"/>
</div>
<div id="fnameID" class="errorMessage">Please type your First Name.</div>
</div>

<!-- commented Since Candidate can be Marked as Eligible/non Eligible before Test Center Allocation. 
<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyeligiblenoneligible.testcentre"/>&nbsp;</div>

<div>
	<s:select name="testCenterId"  cssClass="validate[]" errRequired="Please select test center"
		headerKey="" headerValue="ALL" list="testCenterList" id="testCenterIdNew" listValue="labelValue" listKey="labelId" value="%{testCenterId}"/>
</div>

<div id="lnameID" class="errorMessage">Please type your Last Name.</div>
</div>
</div>
  -->
<!-- Row One End -->

<!-- Row Two Start -->
<!-- commented Since Candidate can be Marked as Eligible/non Eligible before Test Center Allocation.
<div class="clear">

<div>
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyeligiblenoneligible.date"/>&nbsp;</div>
	<div>
		<select id="mySelect" name="examDate" Class="validate[]" errRequired="Please select date">
			<option value=""> ALL</option>
		</select>
	</div>
<div id="fnameID" class="errorMessage">Please type your First Name.</div>
</div>


<div class="fl-rigt fifty">
<div class="field-label"><s:text name="agencyeligiblenoneligible.testslot"/>&nbsp;</div>
	<div>
			<select id="timeSlot" name="timeSlotId" Class="validate[]" errRequired="Please select test slot">
				<option value=""> ALL</option>
			</select>
	</div>
<div id="lnameID" class="errorMessage">Please type your Last Name.</div>
</div>
</div>

</div>
-->
<!-- Row Two End -->


<!-- Row Third Start -->
<div class="clear">
<div>
<div class="fl-left fifty">
<div class="field-label"><s:text name="agencyeligiblenoneligible.eligiblestatus"/>&nbsp;</div>
<div>
	<s:select name="eligibleListId" cssClass="validate[]" errRequired="Please select status"
		headerKey="" headerValue="ALL " list="eligibleList" id="eligibleListId" value="%{eligibleListId}"/>
</div>
<div id="fnameID" class="errorMessage">Please type your First Name.</div>
</div>


<div class="fl-rigt fifty">&nbsp;</div>

</div>
</div>
<!-- Row Third End -->

<!-- Row Three Start -->
<div class="clear">
<div class="fl-left fifty">
<div>
<s:submit type="submit" key="agencyeligiblenoneligible.search" title="Search"  cssClass="submitBtn button-gradient" method="getEligibleAndNonEligibleCandidate"></s:submit>&nbsp;&nbsp;
<input type="button" value="<s:text name="agencyeligiblenoneligible.clear"/>" title="Clear" class="submitBtn button-gradient" onclick="resetEligibleSearch();"/></div>
</div>
<div class="fl-rigt fifty">&nbsp;</div>
</div>
<!-- Row Three End -->
<div class="height20"></div>

</div>

</div>
<!-- Dash Line Start -->
<div class="clear hr-dashline"></div>
<!-- Dash Line End -->

<!-- Grid Start -->
<div class="errorMessageActive" id="dataError">		
				<s:actionmessage  id="dataErrorclear"/>
</div>
<div class="ElgCandidateGrid">
<div class="flexigrid">
<s:if test='%{eligibleDisplayFlag=="true"}'>
<table cellspacing="0" cellpadding="3" width="100%" class="table_2" border="1" bordercolor="#CCC" >
   <tr >
    <th style="width:40px;"><input type="checkbox" align="center"  id="checkMe" style="text-align: center;" value="0.000"></th>
    <th style="width:120px;"><s:text name="agencyeligiblenoneligible.enrollemtID"/></th>
    <th ><s:text name="agencyeligiblenoneligible.headername"/></th>
    <th ><s:text name="agencyeligiblenoneligible.discipline"/></th>
    <th ><s:text name="agencyeligiblenoneligible.status"/></th>
    <th style="width:180px;" ><s:text name="agencyeligiblenoneligible.headerreason"/></th>
    </tr>

<s:if test="%{eligibleNonElibileList!=null}">
<%int y=0; %>
<s:iterator value="eligibleNonElibileList">
	<tr>
	<td align="center" ><s:checkbox name="paymentCheckedValue" fieldValue="%{userPK}" id="paymentCheckedValue" cssClass="checkBoxClass" onclick="enableReason(this);"/></td>
	<td ><s:property value="enrollment_PK"/></td>
	<td ><s:property value="firstName"/>&nbsp;&nbsp; <s:property value="lastName"/></td>
	<td ><s:property value="testName"/></td>
	<td class="classStatusDiv"> <s:property value="eligible"/></td>
	<td align="center" ><s:textfield id = "reasonId_%{userPK}" name="reason" cssClass="inputField" maxlength="50" disabled="true"></s:textfield></td>
	</tr>
	<%y++; %>
	</s:iterator>
	</s:if>
	<s:else>
	<tr>
		<td colspan="6" align="center">
			<div>
				<b><s:text name="global.norecordfound"/> </b>
			</div>
		</td>
</tr>
</s:else> 

</table>
</s:if>
</div>

<!--</div>  -->

<!--<div class="pDiv"><div class="pDiv2"><div class="pGroup"><select name="rp"><option selected="selected" value="10">10&nbsp;&nbsp;</option><option value="15">15&nbsp;&nbsp;</option><option value="20">20&nbsp;&nbsp;</option><option value="25">25&nbsp;&nbsp;</option><option value="40">40&nbsp;&nbsp;</option></select></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pFirst pButton"><span></span></div><div class="pPrev pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pcontrol"><s:text name="agencyeligiblenoneligible.footerpage"/> <input type="text" value="1" size="4"> <s:text name="agencyeligiblenoneligible.footerof"/> <span>29</span></span></div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pNext pButton"><span></span></div><div class="pLast pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"> <div class="pReload pButton"><span></span></div> </div> <div class="btnseparator"></div> <div class="pGroup"><span class="pPageStat"><s:text name="agencyeligiblenoneligible.footerdisplaying"/> 1 <s:text name="agencyeligiblenoneligible.footerto"/> 10 <s:text name="agencyeligiblenoneligible.footerof"/> 290 <s:text name="agencyeligiblenoneligible.footeritems"/></span></div></div><div style="clear: both;"></div></div> -->

</div>
<s:if test="%{eligibleNonElibileList!=null}">
<!-- Button Start -->
<div class="height20"></div>
<div class="clear" style="text-align:center;">
<s:submit type="submit" key="agencyeligiblenoneligible.markeligible" title="Search"  cssClass="submitBtn button-gradient" method="updateNonEligibleToEligible" onclick="return validate('eligible');"></s:submit>&nbsp;&nbsp;
<s:submit type="submit" key="agencyeligiblenoneligible.marknoneligilve" title="Search"  cssClass="submitBtn button-gradient" method="updateEligibleToNonEligible" onclick="return validate('noneligible'); "></s:submit>&nbsp;&nbsp;
</div>
</s:if>

<!-- Button End -->

</div>
<!-- Grid End -->
</form>
<!-- Box Container End -->

</div>
