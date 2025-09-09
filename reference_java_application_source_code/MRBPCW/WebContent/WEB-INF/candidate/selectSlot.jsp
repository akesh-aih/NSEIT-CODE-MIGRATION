<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<script>

function bookSeat(batch){
	//location.href = "CandidateAction_bookSeat.action";
	var menuKey = $('#menuKey').val();
	var dataString = "batchPk="+batch+"&menuKey="+menuKey;
	
	$.ajax({
		url:"CandidateAction_bookSeat.action",
		async: true,
		data:dataString,
		
		beforeSend:function(){
		},
		error:function(){
		},
		success:function(response){
			if (response!=null && response!='null' && response.length>0){
				$("#slotTable").hide();
				$("#noSeatDtlsTable").hide();
				$("#block11").show();
				$("#pop3").show();
				
			}
		}
	});
}


	function getTestSlots(){
			var testDate = $("#testDate").val();
			var dataString = "testDate="+testDate;
	
		$.ajax({
			url: "CandidateAction_getTestSlots.action",
			async: true,
			data: dataString,
			beforeSend: function(){
				
			},
			error:function(ajaxrequest){
				alert('Error in refreshing . Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText){
				$('#testSlot').empty();
				responseText = $.trim(responseText);
				textMessae = responseText.split(',');
				message = responseText.substring(2, responseText.length);
				if(textMessae[0]=='9'){
						alert(message);
						return false;
				}else{
				
				$('#testSlot').append(  
						            $('<option></option>').val("").html("All")
						     );
				if(responseText!='null'&& responseText.length > 0){
				
					 var element = responseText.split(",");
	
					 $.each(element, function(val) {
						    $('#testSlot').append(  
						            $('<option></option>').val(element[val]).html(element[val])
						     );
					 }); 
				}
			}
			}
		});
	}
	
	
	
	function getSeatDetails(){
			var testDate = $("#testDate").val();
			var testSlot = $("#testSlot").val();
			var testCenterId = $("#testCenterId").val();
			var dataString = "testDate="+testDate+"&testSlot="+testSlot+"&testCenterId="+testCenterId;
	
		$.ajax({
			url: "CandidateAction_getSeatAvailabilityDetails.action",
			async: true,
			data: dataString,
			beforeSend: function(){
				
			},
			error:function(ajaxrequest){
				alert('Error in refreshing . Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText){
				var $tbl = $('<table>').attr('id', 'basicTable').attr(
												'width', '100%').attr('border', '0').attr(
												'cellspacing', '0').attr('cellpadding', '0').attr(
												'id', 'basicTable').addClass('table_2');
												
				var rowData = responseText.split("#");
				alert ("responseText  "+responseText);
				
				
				var tbl = $('<table>').attr('id', 'basicTable').attr(
						'width', '100%').attr('border', '0').attr(
						'cellspacing', '0').attr('cellpadding', '0').attr(
						'id', 'basicTable').addClass('table_2');
						
				var headersRow = $("<tr>");
				
				headersRow.appendTo(tbl);
				
				alert ("headersRow   "+headersRow);
				$("<th>").text('Batch').attr('width', '14%').appendTo(headersRow);
				$("<th>").text('Available Seats').attr('width', '16%').appendTo(headersRow);
						
				$("#tablediv").append(headersRow);
				
					for ( var tableRow = 0; tableRow < rowData.length - 1; tableRow++) {
						var trow = $("<tr>");
						//alert('rowData[tableRow]'+rowData[tableRow]);
						var columnData = rowData[tableRow].split(',');
						batchPK = columnData[0];
						
						var $hiddenTime = $('<input/>', {
							type : 'hidden',
							id : "batchPk_" + batchPK,
							value : columnData[0] 
						});
						var $hiddenDuration = $('<input/>', {
							type : 'hidden',
							id : "availableSeats_" + batchPK,
							value : columnData[1]
						});
						
						$hiddenTime.appendTo('body');
						$hiddenDuration.appendTo('body');
						for ( var tableCol = 1; tableCol < columnData.length-1; tableCol++) {
							//alert('columnData[tableCol]'+columnData[tableCol]);
							if (columnData[tableCol] != null) {
								$("<td>").text(columnData[tableCol]).appendTo(
										trow);
							}
						}

						$("<td>").attr('id', batchPK).append(
								$("<a/>").attr("href", "#").text("Book"))
								.addClass("bookClass").appendTo(trow);

						trow.appendTo($tbl);
					}
			}
		});
	}
	
	
	
	function clearDivs(){
		$("#slotTable").hide();
		$("#noSeatDtlsTable").hide();
		$("#testDate").val("All");
		$("#testSlot").val("All");
	}
	
	function callFunc(){
		$("#selectTestSlot").attr("action","CandidateAction_home.action");
		$("#selectTestSlot").submit();
		//$("#block11").hide();
	}
</script>
<s:form action="CandidateAction" id="selectTestSlot">
<input type="hidden" name="menuKey" id="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
  <div style="display:block; min-height:300px; height:auto;" id="dashboard">
	   <div class="fade" id="pop3"></div>
	  
	 <h1 class="pageTitle" title="Generate Admit Card">Select Slot</h1>
	 <div class="hr-underline2"></div>
	<input type="hidden" id = "candidateTestCenterName" name="candidateTestCenterName" value='<s:property value="candidateTestCenterName"/>'/>
	<input type="hidden" id = "batchPk" name="batchPk" value='<s:property value="batchPk"/>'/>
	<input type="hidden" id = "testCenterId" name="testCenterId" value='<s:property value="testCenterId"/>'/>
	
	<table>
		<tr>
			<td>
				Allotted Test Center : <strong><s:label value = "%{candidateTestCenterName}"></s:label></strong>
			</td>
		</tr>
		
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		
		<tr>
			<td>
				<s:select label="Test Center" name="testDate"   id = "testDate" 
				headerKey="All"  headerValue="All" list="testDates" value="%{testDate}" onchange="getTestSlots()"/>
	  		</td>
		</tr>
		
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
	
	<tr>
		<td>
			<s:select name="testSlot" id="testSlot"	headerKey="All"  headerValue="All" list="testSlots" 
							value="%{testSlot}" />
		</td>
	
	</tr>
	
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>
	
	<tr>
		<td>
			<!--<input type="button" value="Search" onclick="getSeatDetails()">-->
			<s:submit method="getSeatAvailabilityDetails" value="Search" cssClass="submitBtn button-gradient"></s:submit>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="button" value="Clear" onclick="clearDivs()" class="submitBtn button-gradient">
		</td>
	</tr>
	<tr>
		<td>
			&nbsp;
		</td>
	</tr>
	</table>
	
	
	
	
		<s:if test='%{batchDetailsDisplayFlag=="true"}'>
				<table cellspacing="0" cellpadding="3" width="800" border="1" class="table_2" bordercolor="#CCCCCC" id="slotTable">
						<thead>
						    	<tr>
								    <th style="width: 40px;" align="left">Batch Id</th>
								    <th style="width: 30px;" align="left">Available Seats</th>
									<th style="width: 30px;" align="left">Test Date</th>
									<th style="width: 30px;" align="left">Test Day</th>
									<th style="width: 50px;" align="left"></th>
								</tr>
					    </thead>
					
						<tbody>
							 <s:iterator value="batchDetailsList">
									<tr>
										<td ><s:property value="batchPK"/></td>	
										<td><s:property value="availableSeats"/></td>
										<td><s:property value="testDate"/></td>
										<td><s:property value="testDay"/></td>
										<td><a href="#" onclick="bookSeat('<s:property value="batchPK"/>')" >Book Now</a></td>
									</tr>
							 </s:iterator>
						</tbody>
			 	</table>  	
		</s:if>

	  	<s:if test='%{noSlotMsg=="true"}'>
		  	<table cellspacing="0" cellpadding="3" width="800" border="1" class="table_2" bordercolor="#CCCCCC" id = "noSeatDtlsTable">
				<tr>
					<td colspan="6" align="center"><b>No seats available For selected criteria.</b></td>
				</tr>
			</table>
	  	</s:if>
   </div>
   
   
<div id="block11" style="display:none" class="change-pass box-gradient" >
		<table align="center">
			<tr>
				<td align="center">
				<h1 class="pageTitle" title="Dashboard">Seat Booking</h1>
					<div class="hr-underline2"></div>
				</td>
			</tr>

			<tr>
				<td align="center">
					<div style = "text-align:center">
						Congratulations!! You have successfully booked a seat .
				</div><br/>	
				</td>
			</tr>
		
			<tr>
				<td align="center">
					<div>
					<input type="button" value="Close" onclick="callFunc()" class="submitBtn button-gradient" >
				</div>	
				</td>
			</tr>
		
			<tr>
				<td>
					&nbsp;
				</td>
			</tr>
		</table>
</div>
</s:form>
