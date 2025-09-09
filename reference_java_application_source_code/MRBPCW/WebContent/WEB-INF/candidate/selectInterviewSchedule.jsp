<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
	$(document).ready(function() {
		$(".ajaxRefresh").click(function() {
			if($(this).attr("id")=="btnRefreshSeatAvailibility")
			{
				refreshAvailaibleSeats();
			}
		});
		
		$(document).ready(function() { 
    	$("a:contains('Select Interview Slot')").html('<span class="fadeSubmenu">Select Interview Slot</span>')}); 
	});
	
	function refreshAvailaibleSeats()
	{
		var dataString = "beanpropertieshere";

		$.ajax({
			url: "SchedulingAction_getSeatsAvailabilityDetailsForInterview.action",
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
				if(textMessae[0]=='2'){
					alert("Error : \n"+textMessae[1]);
					return false;
				}else{
					if(responseText.length > 0)
					{
						/*
						Repopulate Seats Availibility Table Here
						*/
						$("#test").html(responseText);
					}
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
	<h1 class="pageTitle" title="Interview Slot"><s:text name="interviewschedule.selectInterviewSlot"/></h1>
</div>
<div class="hr-underline2 clear"></div>

<!-- Content Start -->
<div class="SelectSlot-cont">
<div class="height10"></div>
	<div class="field-label">
		<s:text name="interviewschedule.venue"/>:
		<s:property value="interivewCentername"/> 
	</div>
<div>
<div class="field-label fifty fl-left"><s:text name="interviewschedule.seatAvailability"/></div>
<div class="field-label fifty fl-rigt" align="right">
<input type="button" title="Refresh" value="<s:text name="interviewschedule.Refresh"/>" class="submitBtn button-gradient ajaxRefresh" id="btnRefreshSeatAvailibility" /></div>
<div class="clear"></div>
</div>



<div class="height5"></div>
<div class="SelectSlotGrid">
<table cellspacing="0" cellpadding="0" border="0" class="colHeader">
<tbody><tr>
<td><div class="colHead1 WeightBold"><s:text name="interviewschedule.testDate"/></div></td>
<td><div class="colHead2 WeightBold"><s:text name="interviewschedule.batch"/></div></td>
<td><div class="colHead3 WeightBold"><s:text name="interviewschedule.seatAvailable"/></div></td>
<td><div class="colHead4 WeightBold"><s:text name="interviewschedule.bookSeat"/></div></td>
</tr>
</tbody></table>
</div>
<div class="SelectSlotRowGrid">

<s:form action="SchedulingAction">
<s:token></s:token>
<!-- 
<table cellspacing="0" cellpadding="0" border="0" id="test">
</table>
 -->
<div id="test"></div>
</s:form>
</div>

</div>
<!-- Content End -->

</div>

</div>
