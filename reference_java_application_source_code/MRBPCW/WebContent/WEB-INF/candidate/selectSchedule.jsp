<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function() {
		$(".ajaxRefresh").click(function() {
			if($(this).attr("id")=="btnRefreshSeatAvailibility")
			{
				//alert($("#testCentreFK").val());
				if($("#testCentreFK").val() != "" && $("#testCentreFK").val()!= null)
				{
					refreshAvailaibleSeats();
				}
			}
			
			
		});
		
		
		$(".bookTestSeatClass").click(function() {
			$("#batchPK").val($(this).prev().attr("id"));
		});
		
		$(document).ready(function() { 
    	$("a:contains('Select Test Slot')").html('<span class="fadeSubmenu">Select Test Slot</span>')}); 
		 
		});
	
	
	function refreshAvailaibleSeats()
	{
		var dataString = "testCentreFK="+$("#testCentreFK").val();

		$.ajax({
			url: "SchedulingAction_getSeatsAvailabilityDetails.action",
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
				responseText = $.trim(responseText);
				textMessae = responseText.split(',');
				message = responseText.substring(2, responseText.length);
				if(textMessae[0]=='9')
				{
					alert(message);
					return false;
				}
				else{
					if(responseText.length > 0)
					{
						$("#test").html(responseText);
					}
				}
			}
		});
	}
	
	
	function bookSeat()
	{
		var batchID = $("#batch1").val();
		var capacity = $("#capacity1").val();
		
		var dataString = "beanpropertieshere";

		$.ajax({
			url: "SchedulingAction_updateAvailableSeats.action",
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
				responseText = $.trim(responseText);
				
				if(responseText.length > 0)
				{
					/*
					Repopulate Seats Availibility Table Here
					*/
					$("#test").html(responseText);
				}
			}
		});
	}

	
</script>

<div class="main-body">

<!-- Fade Container Start -->
<div class="fade" id="pop3"></div>
<!-- Fade Container End -->

<div id="SelectSlot">

<div class="fl-left fifty">
	<h1 class="pageTitle" title="Test Slot"><s:text name="schedule.testSlot" /></h1>
</div>
<div class="hr-underline2 clear"></div>

<!-- Content Start -->
<div class="SelectSlot-cont">
<div class="field-label"><s:text name="schedule.discipline" />: <s:property value = "testName" /> </div>
<div class="height5"></div>
<div class="field-label"><s:text name="schedule.testCentre" />: <s:property value = "testCentreName"/></div>
<div class="height10"></div>

<div>
<s:hidden name="testCentreFK" id="testCentreFK"  ></s:hidden>
<div class="field-label fifty fl-left"><s:text name="schedule.seatAvailablility" /></div>

<br/>
<div class="field-label fl-left">
	<br/>
	<s:if test='%{batchSeatAllotedFlag=="true"}'>
		
		Seat Allotted for Test Date <s:property value="testDate"/> and Slot  <s:property value="startTimeStr"/> - <s:property value="endTimeStr"/>
	</s:if>
</div>
<s:else>
	<div class="field-label fifty fl-rigt" align="right">
		<input type="button" title="Refresh" value="<s:text name="schedule.refresh"/>" class="submitBtn button-gradient ajaxRefresh" id="btnRefreshSeatAvailibility" />
	</div>
</s:else>
<div class="clear"></div>

</div>


<div class="height5"></div>
<div class="SelectSlotGrid">
<table cellspacing="0" cellpadding="0" border="0" class="colHeader">
<tbody><tr>
<td><div class="colHead1 WeightBold" align="center"><s:text name="schedule.testDate" /></div></td>
<td><div class="colHead2 WeightBold" align="center"><s:text name="schedule.batch" /></div></td>
<td><div class="colHead3 WeightBold" align="center"><s:text name="schedule.seatAvailablility" /></div></td>
<td><div class="colHead4 WeightBold" align="center"><s:text name="schedule.bookSeat" /></div></td>
</tr>
</tbody></table>
</div>
<div class="SelectSlotRowGrid" align="center">

<s:form action="SchedulingAction">
<s:token></s:token>
<!--
 <table cellspacing="0" cellpadding="0" border="0" id="test">

</table>
 -->
<div id="test"></div>
<div class="errorMessageActive" id="scheduleError">		
				<s:actionmessage/>
			</div>
</s:form>
</div>

</div>
<!-- Content End -->

</div>

</div>
