<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
		$(document).ready(function() {
		
		
		
		$(".bookTestSeatClass").click(function() {
			$("#batchPK").val($(this).prev().attr("id"));
		});
		});
	

</script>
<input type = "hidden" name="batchPK" id="batchPK"/>

<%int i = 1; %>
<table>
	<s:iterator value="listForInterview" >
		<tr>
			
		<input type="hidden" name="availableCapacity" id="<%="capacity"+String.valueOf(i)%>" value="<s:property value="availableCapacity" />"/>
		

			<td class="dashBottomBorder">
				<div class="cell1">
					<s:property value="testDate" />
				</div>
			</td>
			
			<td class="dashBottomBorder">
				<div class="cell2">
					<s:property value="startTimeStr" />
					-
					<s:property value="endTimeStr" />
				</div>
			</td>
			
			<td class="dashBottomBorder">
				<div class="cell3">
					<s:property value="availableCapacity"  />
				</div>
			</td>
			
			<td class="dashBottomBorder">
				<div class="cell4">
				<input type="hidden" name="batchid" id="<s:property value="batchPK" />"/>
					<s:submit type="button" id="btnBookNow" key="schedule.bookNow"  title="Book Now" cssClass="submitBtn button-gradient bookTestSeatClass" method="updateAvailableSeatsForInterview" />
				</div>
			</td>
			
		</tr>
	<%i++; %>
	
	</s:iterator>
	</table>
