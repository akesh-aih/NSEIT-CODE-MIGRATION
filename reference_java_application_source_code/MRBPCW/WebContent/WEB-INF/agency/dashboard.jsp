<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<script>

$(document).ready(function() {
	$("#agencyDashBoard").validationEngine({promptPosition : "centerRight", scroll: false});

	$("#yearId").change(
		function (){
			var yearId = $("#yearId").val();
			getMonthForYear();
		}
	);

	$("#monthId").change(
		function (){
			//alert('month change');
			getAgencyDashBoardData();
		}
	);
	$('#monthId').empty();
	$('#monthId').append(  
	           $('<option></option>').val(0).html("All")
	     );
});
/*
function getYearList(){
	var dataString = "";
	
	$.ajax({
			url: "AgencyAction_getYearList.action",
			async: true,
			data: dataString,
			beforeSend: function(){
				$('#yearId').empty();
				$('#yearId').append(  
				           $('<option></option>').val(0).html("All")
				     );
			},
			error:function(ajaxrequest){
				alert('Error refreshing yeasr. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText){
				alert(responseText);
				responseText = $.trim(responseText);
				if(responseText.length > 0 && responseText != null){
					var element = responseText.split(",");
					$.each(element, function(val) {
						alert (val);
						  $('#yearId').append(  
						           $('<option></option>').val(element[val]).html(element[val])
						     );
					 });
				}
			}
		})
}
*/
function getMonthForYear()
{
	var yearId = $("#yearId").val();
	var dataString = "year="+yearId;
	$.ajax({
		url: "AgencyAction_getMonthListYearWise.action",
		async: true,
		data: dataString,
		beforeSend: function()
		{
		$('#monthId').empty();
		$('#monthId').append(  
		           $('<option></option>').val(0).html("All")
		     );
		},
		error:function(ajaxrequest)
		{
			alert('Error refreshing months. Server Response: '+ajaxrequest.responseText);
		},
		success:function(responseText)
		{
			responseText = $.trim(responseText);
			if(responseText.length > 0 && responseText != null && yearId != 0){
				message = responseText.substring(2, responseText.length);
				textMessae = responseText.split(',');
				if(textMessae[0]=='9'){
					alert(message);
					return false;
				}
				element = responseText.split("#");
				$.each(element, function(val) {
					monthAndID = element[val].split(",");
					  $('#monthId').append(  
					           $('<option></option>').val(monthAndID[0]).html(monthAndID[1])
					     );
				 });

				
			}
			else{

			}
			getAgencyDashBoardData();
		}
	});
}

function getAgencyDashBoardData(){
	var yearId = $("#yearId").val();
	var monthId = $("#monthId").val();
	var dataString = "year="+yearId+"&month="+monthId;
	//alert ("reg data  ---->"+dataString);

	$.ajax({
			url: "AgencyAction_getCandidateRegistrationData.action",
			async: true,
			data: dataString,
			beforeSend: function(){
			},
			error:function(ajaxrequest){
				alert('Error refreshing months. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText){
				responseText = $.trim(responseText);
				if(responseText.length > 0){
					message = responseText.substring(2, responseText.length);
					element = responseText.split(",");
					if(element[0] == "E")
					{
						alert(message);
						return false;
					}
					else{
						$("#registeredCandidate").text(element[0]);
						$("#enrolledCandidate").text(element[1]);
						$("#approvedPayment").text(element[2]);
						$("#appearedCandidate").text(element[3]);
					}
				}
			}
		})
}

</script>
<div class="main-body">

<div class="fade" id="pop3"></div>
<div id="AgencyDashboard">

<h1 class="pageTitle" title="Dashboard"><s:text name="agencydashboard.title"/></h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div class="AgencyContainer">

<!-- First Row Start -->
<div class="clear">
<!-- Left Box Start -->
<div class="AgencyDashboard-box box-gradient fl-left">
<div class="box-header"><s:text name="agencydashboard.notification"/></div>
<div class="hr-underline"></div>
<div class="height10"></div>

<div class="AgencyNotification">
<table cellpadding="2" cellspacing="0" border="0" width="100%">
<!-- 
<tr>
<td width="80%"><a href="#">New Pending Request</a></td>
<td width="20%"><s:property value="pendingPayment"/></td>
</tr>
 -->
<tr>
<td><a href="CandidateMgmtAction_papulatePaymentApproval.action"><s:text name="agencydashboard.paymentapprovalpending"/></a></td>
<td><s:property value="pendingPayment"/></td>
</tr>
<tr>
<td><a href="CandidateMgmtAction_getCandidateDetailsForUpdation.action"><s:text name="agencydashboard.changeupdaterequest"/></a></td>
<td><!-- <s:property value="changeReq"/>  commented since candidate Change Requests will not be available-->NA</td>
</tr>
<tr>
 <!-- <td><a href="http://nseit.com/pdfs/CaseStudy_Tea_Auction.pdf">New Retest Requests</a></td> commented to be release in phase 2. -->
 <td><s:text name="agencydashboard.newretestrequest"/></td>
<td><s:property value="rescheduleReq"/></td>
</tr>
</table>
</div>

</div>
<!-- Left Box End -->

<!-- Right Box Start -->
<div class="AgencyDashboard-box box-gradient fl-rigt">
<s:form action="AgencyAction" id="agencyDashBoard">

<div class="box-header"><s:text name="agencydashboard.candidatesregistrationreport"/> </div>
<div class="hr-underline"></div>
<div class="height10"></div>

<div>
<div class="fifty fl-left">
<div class="field-label">Year&nbsp;</div>
	<div><%--
		<select class="mid-dropdown" id="yearId">
		<option selected="selected" value="0">All</option>
		<option value="2011">2011</option>
		<option value="2012">2012</option>
	</select>	
		--%>
		<s:select label="Year" name="yearVal" 
		headerKey="0" headerValue="All" 
		list="yearList" id="yearId" value="%{yearVal}"/>
		
	
	</div>
</div>

<div class="fifty fl-left">
<div class="field-label">Month&nbsp;</div>
<div><select class="mid-dropdown" id="monthId">
</select></div>
</div>


<!-- <div class="fifty fl-rigt">
<div class="field-label">Test Center&nbsp;</div>
<div><select class="mid-dropdown"><option value="">Select State</option><option selected="selected" value="Mumbai">Mumbai</option></select></div>
</div> -->
</div>

<div class="height5 clear"></div>

<div class="AgencyNotification">
<table cellpadding="2" cellspacing="0" border="0" width="100%">
<tr>
<td width="80%"><s:text name="agencydashboard.candidatesregistered"/></td>
<td width="20%"><s:label id="registeredCandidate" value="%{registeredCandidate}"/></td>
</tr>
<tr>
<td><s:text name="agencydashboard.candidatesenrolled"/></td>
<td><s:label id="enrolledCandidate" value="%{enrolledCandidate}"/></td>
</tr>
<tr>
<td><s:text name="agencydashboard.candidatespaymentapproved"/></td>
<td><s:label id="approvedPayment" value="%{approvedPayment}"/></td>
</tr>
<tr>
<td><s:text name="agencydashboard.candidatesappearedfortest"/></td>
<td><s:label id="appearedCandidate" value="%{appearedCandidate}"/></td>
</tr>
</table>
</div>

</s:form>

</div>
<!-- Right Box End -->
</div>
<!-- First Row End -->

</div>
<!-- Box Container End -->

</div>

</div>
