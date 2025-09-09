<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>

		<script type="text/javascript">
$(document).ready(function() {
	//$("#block9").hide();
	$("#displayRefresh").hide();
	$("#cmbCityList").change(
	function (){
		$("#errorMessage").hide();
	getTestCenterForCity($(this).val());
	}
	);
	
	//$('#block9');
	$("#cmbTestCenters").change(
	function (){
	getAddress($(this).val());
	}
	);
	});

var counterActive = true;
var sec =10 ;   // set the seconds
var min = 00;   // set the minutes
var targetSeconds= 0;

	function countDown() 
	{
		var currSeconds = Math.floor(new Date().getTime() / 1000);
		
		sec = targetSeconds - currSeconds;
		
		if (sec < 0) 
		{
			
		}
		else
		{
			if (sec <= 9) 
			{
				sec = "0" + sec;
			}

			time = sec + " sec ";

			if (document.getElementById) 
			{
				document.getElementById('theTime').innerHTML = time;
			}

			if (sec > 0 && counterActive==true)
			{
				SD = window.setTimeout("countDown();", 990);
			}
			//alert(SD);
			if (sec == '00') 
			{
				sec = 0;
				window.clearTimeout(SD);
				HideAll();
				
				callToSeatFunctionWithSeatStatus('Cancel');
				//window.close();
			}
		}

	}

	function getTestCenterForCity(testVar) {
		var menuKey = $('#menuKey').val();
		var bookingAttempt=$('#bookingAttempt').val();
		var dataString = "cmbTstCenter=" + testVar+"&bookingAttempt="+bookingAttempt+"&menuKey="+menuKey;
		
		$
				.ajax( {
					url : "otbs/sessionTime.jsp",
					async : true,
					error : function(ajaxrequest) {
						alert('Error refreshing available seats. Server Response: ' + ajaxrequest.responseText);
					},
					success : function(responseText) {	
						responseText = $.trim(responseText);
						
						if(responseText == '0')
						{
							alert('Session has been expired.');
							window.location = '<%= GenericConstants.SESSION_TIME_OUT_URL%>';
						}
						else
						{
							$
							.ajax( {
								url : "SchedulingTestAction_getTestCentreForCity.action",
								async : true,
								data : dataString,
								beforeSend : function() {
									$('#cmbTestCenters').empty();
									$('#cmbTestCenters').append(
											$('<option></option>').val('-1').html(
													'Select Test Centre'));
								},
								error : function(ajaxrequest) {
									alert('Error refreshing available seats. Server Response: ' + ajaxrequest.responseText);
								},
								success : function(responseText) {
									responseText = $.trim(responseText);

									if (responseText.length > 0 && responseText != 'null'
											&& responseText != null) {
										var tmpRow = responseText.split('##');

										for(var k =0;k<tmpRow.length;k++)
										{
											var element = tmpRow[k].split("$$");
					
											$.each(element, function(val) {
				
												$('#cmbTestCenters').append(
														$('<option></option>')
																.val(element[val]).html(
																		element[val + 1]));
											});
										}
			
									}
								}
							});
						}

					}
				});
		
		

		
	}
	
	function callFunc(){
	$("#frmConfirmSeat").attr("action","CandidateAction_home.action");
	$("#frmConfirmSeat").submit();
		//$("#block11").hide();
	}
	
	function validateForm(){
		var message = "";
		var ulID = "errorUl";
		var divID = "errorMessage";
		var testCity = $("#cmbCityList").val();
		var testCenter = $("#cmbTestCenters").val();
		var testFromDt = $("#cmbFromTestDate").val();
		//var testToDt = $("#cmbToTestDate").val();
		
		if(testCity == "" || testCity == "-1")
			message = message + " Please select Test City."+"##";
			
			
		if(testCenter == "" || testCenter == "-1")
			message = message + " Please select Test Center."+"##";

		
		if(testFromDt == "" || testFromDt == "-1")
			message = message + " Please select Test Date."+"##";
		/*
		if(testToDt == "" || testToDt == "-1")
			message = message + " Please select To Test Date."+"##";

		*/
		/*
		var fromDatWithSplit = testFromDt.split("-");		
		var frmDate =  new Date(fromDatWithSplit[2],getMonthNoByMonthName(fromDatWithSplit[1]),fromDatWithSplit[0]);
		
		var toDatWithSplit = testToDt.split("-");		
		var toDate =  new Date(toDatWithSplit[2],getMonthNoByMonthName(toDatWithSplit[1]),toDatWithSplit[0]);
		
		if(frmDate.getTime() > toDate.getTime())
			message = message + " From Date cannot be greater than To Date."+"##";
		*/
		if(message != ""){
			createErrorList(message, ulID, divID); 
			return false;
		}
		else
			return true;
	}
	
	function getBatchDetails() {
		var menuKey = $('#menuKey').val();
		var validFlag = validateForm();
		if(validFlag == false)
			return false;
		else{
			$("#errorMessage").hide();
			var tstCenreId = $("#cmbTestCenters").val();
			var fromTestDate = $("#cmbFromTestDate").val();
			//var toTestDate = $("#cmbToTestDate").val();
			var bookingAttempt= $('#bookingAttempt').val();
			var dataString = "cmbTstCenter=" + tstCenreId + "&cmbTestFromDate="+ fromTestDate + "&cmbTestToDate=" + fromTestDate +"&bookingAttempt="+bookingAttempt+"&menuKey="+menuKey;

			$
			.ajax( {
				url : "otbs/sessionTime.jsp",
				async : true,
				error : function(ajaxrequest) {
					alert('Error refreshing available seats. Server Response: ' + ajaxrequest.responseText);
				},
				success : function(responseText) {	
					responseText = $.trim(responseText);
					
					if(responseText == '0')
					{
						alert('Session has been expired.');
						window.location = '<%= GenericConstants.SESSION_TIME_OUT_URL%>';
					}
					else{
								$
									.ajax( {
										url : "SchedulingTestAction_getBatchDetailsToBookSeat.action",
										async : true,
										data : dataString,
										beforeSend : function() {
										
											$("#tablediv").empty();
										},
										error : function(ajaxrequest) {
											alert('Error refreshing available seats. Server Response: ' + ajaxrequest.responseText);
										},
										success : function(responseText) {
											var batchPK;
											responseText = $.trim(responseText);
											//alert(responseText);
											if(responseText == 'empty')
											{
												//alert('No Slots available.');
												$("#tablediv").attr('align', 'center').html('<label > No slots available for the selected criteria. </label>' ).addClass('error-massage-text');;
												return false;
												
											}else{
											$("#tablediv").removeClass('error-massage-text');
										var rowData = responseText.split("##");
					
										var $tbl = $('<table>').attr('id', 'basicTable').attr(
												'width', '100%').attr('border', '0').attr(
												'cellspacing', '0').attr('cellpadding', '0').attr(
												'id', 'basicTable').addClass('table_2');
					
										var headersRow = $("<tr>");
										$("<th>").text('Test Date').attr('width', '14%').appendTo(
												headersRow);
										$("<th>").text('Test Day').attr('width', '16%').appendTo(
												headersRow);
										$("<th>").text('Test Start Time').attr('width', '20%')
												.appendTo(headersRow);
										$("<th>").text('Duration (in minutes)').attr('width', '18%')
												.appendTo(headersRow);
										$("<th>").text('Available Seats').attr('width', '15%')
												.appendTo(headersRow);
										$("<th>").text('Book').attr('width', '17%').appendTo(
												headersRow);
										headersRow.appendTo($tbl);
										for ( var tableRow = 0; tableRow < rowData.length - 1; tableRow++) {
											var trow = $("<tr>");
											//alert('rowData[tableRow]'+rowData[tableRow]);
											var columnData = rowData[tableRow].split(',');
											batchPK = columnData[0];
											
											var $hiddenTime = $('<input/>', {
												type : 'hidden',
												id : "timeSlot_" + batchPK,
												value : columnData[3] 
											});
											var $hiddenDuration = $('<input/>', {
												type : 'hidden',
												id : "duration_" + batchPK,
												value : columnData[4]
											});
											var $hiddenDate = $('<input/>', {
												type : 'hidden',
												id : "date_" + batchPK,
												value : columnData[1]
											});
											var $hiddenReportingTime = $('<input/>', {
												type : 'hidden',
												id : "reportingTime_" + batchPK,
												value : columnData[6]
											});
											
											$hiddenTime.appendTo('body');
											$hiddenDate.appendTo('body');
											$hiddenDuration.appendTo('body');
											$hiddenReportingTime.appendTo('body');
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
										$("#displayRefresh").show();
										//alert("tableDiv  " +$(document).find("#tableDiv").tagName);
										$("#tablediv").append($tbl);
										$(".bookClass").each(function() {
											$(this).click(function() {
											
											isSeatsAvailableToBook($(this).attr('id'));
											
											});
										});
											}
					
							}
							});
						}
					}
				});
			}
	}
	function isSeatsAvailableToBook(batchID)
	{
		$
		.ajax( {
			url : "otbs/sessionTime.jsp",
			async : true,
			error : function(ajaxrequest) {
				alert('Error refreshing available seats. Server Response: ' + ajaxrequest.responseText);
			},
			success : function(responseText) {	
				responseText = $.trim(responseText);
				
				if(responseText == '0')
				{
					alert('Session has been expired.');
					window.location = '<%= GenericConstants.SESSION_TIME_OUT_URL%>';
				}
				else
				{
					
					var dataString = "batchPK=" + batchID ;
					/*
					$.ajax( {
								url : "SchedulingTestAction_isSeatsAvailableForBooking.action",
								async : true,
								data : dataString,
								beforeSend : function() {
								},
								error : function(ajaxrequest) {
									alert('Error refreshing available seats. Server Response: ' + ajaxrequest.responseText);
								},
								success : function(responseText) {
									responseText = $.trim(responseText);
									//alert(responseText);
									if(responseText=='true'){
									
									showSlotDetails(batchID);
									}else{
									alert('No Seats Available for Booking. Please click on refresh first to check seat availability');
									}
									
									
								}
							});
							*/
					showSlotDetails(batchID);
				}
			}
			});
				
	}
	function showSlotDetails(batchID) {

		$('#testDate').val($('#date_' + batchID).val());
		$('#testCenter').val($('#cmbTestCenters option:selected').text());
		$('#testTime').val($('#timeSlot_' + batchID).val());
		$('#testDuration').val($('#duration_' + batchID).val());
		
		$('#reportingTime').val($('#reportingTime_' + batchID).val());
		  
		$('#lblSeatDivAddress').html($('#lblAddress').text());
		$('#lblSeatTestTime').html($('#timeSlot_' + batchID).val());
		$('#lblDuration').html($('#duration_' + batchID).val());
		$('#lblSeatTestDate').html($('#date_' + batchID).val());
		$('#lblSeatDivTstCntr').html(
				$('#cmbTestCenters option:selected').text());

		$('#hiddenBatchPK').val(batchID);
		

		var startSeconds = Math.floor(new Date().getTime() / 1000);
		
		targetSeconds = (startSeconds * 1) + (<%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.HALL_TICKET_TIMER_IN_SECONDS)%>*1);
		//alert(targetSeconds);
		counterActive = true;
		
		blockSeatForCandidate(batchID, 'Book')
		
	}
	function callToSeatFunctionWithSeatStatus(seatStatus) {
		var batch = $('#hiddenBatchPK').val();
		
		counterActive = false;
		blockSeatForCandidate(batch, seatStatus);
	}
	function blockSeatForCandidate(batchID, seatStatus) {
	var menuKey = $('#menuKey').val();
		$.ajax( {
			url : "otbs/sessionTime.jsp",
			async : false,
			error : function(ajaxrequest) {
				alert('Error refreshing available seats. Server Response: ' + ajaxrequest.responseText);
			},
			success : function(responseText) {	
				responseText = $.trim(responseText);
				
				if(responseText == '0')
				{
					alert('Session has been expired.');
					window.location = '<%= GenericConstants.SESSION_TIME_OUT_URL%>';
				}
				else
				{
					var bookingAttempt= $('#bookingAttempt').val();
					//alert('bookingAttempt    '+bookingAttempt); 
					var dataString = "batchPK=" + batchID + "&seatStatus=" + seatStatus +"&bookingAttempt="+bookingAttempt+"&menuKey="+menuKey;
					$.ajax({
								url : "SchedulingTestAction_updateSeatDetailsOfBatch.action",
								async : false,
								data : dataString,
								beforeSend : function() {
								},
								error : function(ajaxrequest) {
									alert('Error refreshing available seats. Server Response: ' + ajaxrequest.responseText);
								},
								success : function(responseText) {
									responseText = $.trim(responseText);
									//alert(responseText);
									if(responseText == 'Error')
									{
										//alert();
										ShowBlock('block8');
									}
									
									if(seatStatus == 'Book')
									{
										if(responseText == 'No Seat Available')
										{
											//alert('No Seats Available for Booking. Please click on refresh first to check seat availability');
											ShowBlock('block10');
										}
										else
										{
											ShowBlock('block9');
											countDown();
										}
									}
									
									if (responseText == 'Confirmed') {
										//$('#frmConfirmSeat').submit();
										//ShowBlock('success');
										HideAll();
									

										$("#success").show();	
										$("#pop3").show();
									}
								
								}
							});
				}
			}
		});
	}

	function getAddress(testVar) {
		//alert('datae Selected'+testVar);
		$
			.ajax( {
				url : "otbs/sessionTime.jsp",
				async : true,
				error : function(ajaxrequest) {
					alert('Error refreshing available seats. Server Response: ' + ajaxrequest.responseText);
				},
				success : function(responseText) {	
					responseText = $.trim(responseText);
					
					if(responseText == '0')
					{
						alert('Session has been expired.');
						window.location = '<%= GenericConstants.SESSION_TIME_OUT_URL%>';
					}
					else{
						var bookingAttempt= $('#bookingAttempt').val();
						var menuKey = $('#menuKey').val();
						var dataString = "cmbTstCenter=" + testVar +"&bookingAttempt="+bookingAttempt+"&menuKey="+menuKey;
				
						$
								.ajax( {
									url : "SchedulingTestAction_getTestCenterAddress.action",
									async : true,
									data : dataString,
									beforeSend : function() {
										$('#lblAddress').empty();
				
										$('#cmbToTestDate').empty();
										$('#cmbToTestDate').append(
												$('<option></option>').val('-1').html(
														'Select Test Date'));
										$('#cmbFromTestDate').empty();
										$('#cmbFromTestDate').append(
												$('<option></option>').val('-1').html(
														'Select Test Date'));
				
									},
									error : function(ajaxrequest) {
										alert('Error refreshing available seats. Server Response: ' + ajaxrequest.responseText);
									},
									success : function(responseText) {
				
										responseText = $.trim(responseText);
										var element = responseText.split("##");
										$("#lblAddress").html(element[0]);
										$("#tstCenterAddress").val(element[0]);
										var dates = element[1].split(",");
										$.each(dates, function(val) {
				
											if (dates[val] != null && dates[val] != '') {
												$('#cmbFromTestDate').append(
														$('<option></option>').val(dates[val])
																.html(dates[val]));
				
												/*$('#cmbToTestDate').append(
														$('<option></option>').val(dates[val])
																.html(dates[val]));*/
											}
										});
									}
								});
					}
				}
			});
					
	}
	function resetValue()
	{
		
		$("#lblAddress").empty();
		$("#displayRefresh").hide();
		$("#tablediv").empty();
		$("#errorMessage").hide();
	}
	function disableConfirm()
	{
		
		$('#btnConfirm').attr('disabled', true);
		$('#btnCancel').attr('disabled', true);
	}
				
		</script>

<div class="main-body">
	
	<div class="fade" id="pop3"></div>
	<div id="dashboard">

		<h1 class="pageTitle" title="Book a Seat">Book Seat</h1>
		<div class="hr-underline2"></div>

		<!-- Box Container Start -->
		<s:form action="SchedulingTestAction" id="frmTestDtls">
			<s:hidden id='bookingAttempt' name="bookingAttempt" value="%{bookingAttempt}" />
			<input type="hidden" name="menuKey" id="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
			<div style="display: block; min-height: 300px; height: auto;">
				<table class="contenttable">
					<tr>
						<td colspan="3">
							<div id="error-massage" class="error-massage" style="margin: 0; margin-left: 0px; padding: 0;">
								<div class="error-massage-text" >
									Availability displayed here changes every second. Please complete your booking quickly. It is possible that seats that are shown as available may not be available when you click the Book link.
								</div>
							</div>
							<br/>
								<!--<div class="lighttext">
									<b>Best score from Attempt 1 and Attempt 2 will be considered as your Final score</b>
								</div>
						--></td>
					</tr>
					<tr>
					<td colspan="3">

					<div id="errorMessage" style="display: none" class="error-massage">
						<div class="error-massage-text" style="margin: 0; margin-left: -34px; padding: 0;">
							<ul style="margin: 0; margin-left: 20px; padding: 0;" id="errorUl">

							</ul>
					</div>
					</div>
					
					</td>
					</tr>
					<tr>
						<td height="15" colspan="3"></td>
					</tr>
					<tr>
						<td width="100">
							Test Name
							<span class="manadetory-fields"></span>
						</td>
						<td colspan="2">
							<s:property value="discipline" />
						</td>
					</tr>
					<tr>
						<td>
							Test City
						</td>
						<td colspan="2">
							<s:select name="cmbCity" id="cmbCityList" headerKey="-1"
								headerValue="Select City" list="cityList" listValue="labelValue"
								listKey="labelId" value="%{cmbCity}" />
						</td>
					</tr>
					<tr>
						<td>
							Test Center Name
						</td>
						<td colspan="2">
							<s:select name="cmbTstCenter" id="cmbTestCenters" headerKey=""
								headerValue="Select Test Centre" list="testCenterList"
								listValue="labelValue" listKey="labelId" value="%{cmbTstCenter}" />
						</td>

					</tr>
					<tr>
						<td>
							Test Center Address
						</td>
						<td colspan="2">
							<label id="lblAddress" ></label>
						</td>
					</tr>
					<tr>
						<td>
							Date
						</td>
						<td width="227">
							<span class="lighttext"></span>
							
							<s:select name="cmbTestFromDate" id="cmbFromTestDate"
								headerKey="" headerValue="Select  Test Date"
								list="testCenterList" listValue="labelValue" listKey="labelId"
								value="%{cmbTestFromDate}" />
						</td>
						<!-- 
						<td width="431" >
							<span class="lighttext">To</span>
							<br />
							<s:select name="cmbTestToDate" id="cmbToTestDate" headerKey=""
								headerValue="Select Test Date" list="testCenterList"
								listValue="labelValue" listKey="labelId"
								value="%{cmbTestToDate}" />
						</td>
						  -->
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr>
						<td>
							&nbsp;
						</td>
						<td colspan="2">
							<input type="button" name="button"
								class="submitBtn button-gradient" id="button" value="Submit"
								onclick="getBatchDetails()" />
							<input type="reset" name="button2" id="button2" value="Reset"
								class="submitBtn button-gradient" onclick="resetValue()" />
						</td>
					</tr>



					<tr>
						<td>
							&nbsp;
						</td>
						<td colspan="2">
							&nbsp;
						</td>
					</tr>
					<tr id="displayRefresh">
						<td colspan="3">
							<a href="#" onclick="getBatchDetails()"><strong>Click
									here</strong> </a> to refresh before you click the
							<strong>Book</strong> link.
						</td>
					</tr>

					<tr>
						<td colspan="3" id="tablediv">

						</td>
					</tr>

				</table>

			</div>
		</s:form>
		<div class="book-a-seat box-gradient" id="block9"
			style="display: none;">
			<div>
				<a href="javascript:void(0);"
					onclick="HideAll();callToSeatFunctionWithSeatStatus('Cancel')"><img
						src="images/Close.png" align="right" border="0" title="Close" />
				</a>
			</div>
			<div class="pad12">
				<div class="titleBox fl-left">
					<h1 class="pageTitle" title="Test Details">
						&nbsp;Test Details
					</h1>
				</div>
				<div class="closebtnBox fl-rigt"></div>
				<div class="hr-underline clear"></div>
				<br />
				<s:form id="frmConfirmSeat"	action="SchedulingTestAction_showOtbsSuccessPage">
				<input type="hidden" name="menuKey" id="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
					<div class="fogot-cont">
						<div class="book-a-seat-top">
							The booking is done in real time hence you will be given only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.HALL_TICKET_TIMER_IN_SECONDS)%>
							seconds to confirm your booking
						</div>
						<table width="500" border="0" align="center" cellpadding="5"
							cellspacing="0">

							<tr>
								<td height="30" colspan="2" align="center" valign="top">
									<strong>Time remaining (sec): <span id="theTime"></span>
									</strong>
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td width="201" valign="top">
									User Id
								</td>
								<td width="329" valign="top">
									<strong><s:label value="%{userId}" /> </strong>
								</td>
							</tr>
							<tr>
								<td valign="top">
									Name
								</td>
								<td valign="top">
									<s:label value="%{candidateName}" />
								</td>
							</tr>
							<tr>
								<td valign="top">
									Test
								</td>
								<td valign="top">
									<s:property value="discipline" />
								</td>
							</tr>
							<tr>
								<td valign="top">
									Test Center
								</td>
								<td valign="top">
									<label id="lblSeatDivTstCntr"></label>
								</td>
							</tr>
							<tr>
								<td valign="top">
									Test Center Address
								</td>
								<td valign="top">
									<label id="lblSeatDivAddress"></label>
								</td>
							</tr>
							<tr>
								<td valign="top">
									Test Date
								</td>
								<td valign="top">
									<label id="lblSeatTestDate"></label>
								</td>
							</tr>
							<tr>
								<td>
									Test Start Time
								</td>
								<td>
									<label id="lblSeatTestTime"></label>
								</td>
							</tr>
							<tr>
								<td>
									Duration
								</td>
								<td>
									<label id="lblDuration"></label> minutes
								</td>
							</tr>
							<tr>
								<td>
									&nbsp;
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td></td>
								<td>
									<input type="button"  value="Confirm" 
										Class="submitBtn button-gradient" title="Submit" id='btnConfirm' 
										onclick="disableConfirm();callToSeatFunctionWithSeatStatus('Confirm')" />
									&nbsp;&nbsp;
									<input type="button" value="Cancel"
										class="submitBtn button-gradient" title="Cancel" id = 'btnCancel'
										onclick="HideAll();callToSeatFunctionWithSeatStatus('Cancel')" />
								</td>
							</tr>
						</table>

					</div>
					<s:hidden id='testDate' name="testDate" value="" />
					<s:hidden id='testCenter' name="testCenter" value="" />
					<s:hidden id='testTime' name="testTime" value="" />
					<s:hidden id='testDuration' name="testDuration" value="" />
					<s:hidden id='discipline' name="discipline" value="%{discipline}" />
					<s:hidden id='candidateName' name="candidateName"
						value="%{candidateName}" />
					<s:hidden id='userId' name="userId" value="%{userId}" />
					<s:hidden id='bookingAttempt' name="bookingAttempt"
						value="%{bookingAttempt}" />
					<s:hidden id='tstCenterAddress' name="tstCenterAddress"
						value="%{tstCenterAddress}" />
						<s:hidden id='reportingTime' name="reportingTime"
						value="" />		
				</s:form>
			</div>

		</div>
		<div class="change-pass box-gradient" id="block8" style="vertical-align: top" >
<div><a href="javascript:void(0);" onclick="HideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox fl-left"><h1 class="pageTitle" title="Seat Booking">&nbsp;<s:label value="Seat Booking" /></h1></div>
<div class="closebtnBox fl-rigt"></div>
<div class="hr-underline clear"></div>
<br/>
	
	<div style = "text-align:center">Seat booking failed. Please re-try.</div><br/>
	<div style = "text-align:center"><input type="button" value="Back"
										class="submitBtn button-gradient" title="Cancel" id = 'btnCancel'
										onclick="HideAll();" />
										</div>
	
</div></div>

<div id="success" style="display:none" class="change-pass box-gradient" >
<table align="center">
<tr>
	<td align="center">
	<h1 class="pageTitle" title="Seat Booking">Seat Booking</h1>
		<div class="hr-underline2"></div>
	</td>
</tr>
<tr>
	<td align="center">
		<div style = "text-align:center">
			Congratulations!! You have successfully booked a seat.
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



<div class="change-pass box-gradient" id="block10" style="vertical-align: top" >
<div><a href="javascript:void(0);" onclick="HideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox fl-left"><h1 class="pageTitle" title="Login">&nbsp;<s:label value="Seat Booking" /></h1></div>
<div class="closebtnBox fl-rigt"></div>
<div class="hr-underline clear"></div>
<br/>
	
	<div style = "text-align:center">No Seats Available for Booking. Please click on refresh first to check seat availability.</div><br/>
	<div style = "text-align:center"><input type="button" value="Back"
										class="submitBtn button-gradient" title="Cancel" id = 'btnCancel'
										onclick="HideAll();" />
										</div>
	
</div></div>
		
		<div class="fade" id="block7"></div>
	</div>
</div>
			<s:hidden   id='hiddenBatchPK' />
