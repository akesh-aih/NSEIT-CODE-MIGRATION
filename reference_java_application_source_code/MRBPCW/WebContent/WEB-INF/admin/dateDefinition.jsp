<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
	$(document).ready(function() {
	
		getDatesOnLoad();
		// For Application Form 
		$("#btnUpdateFormDates").click(function() {
				updateDateDetails($(this).prev().val(), $(this));
		});
	
		// For Approve Candidate
		$("#btnUpdateAppCan").click(function() {
			updateDateDetails($(this).prev().val(), $(this));
		});
		
		// For payment
		$("#btnUpdatePayment").click(function() {
			updateDateDetails($(this).prev().val(), $(this));
		});
		// For OTBS	
		$("#btnUpdateOTBS").click(function() {
			updateDateDetails($(this).prev().val(), $(this));
		});
	
		// For Attempt One
		$("#btnUpdateAtt1").click(function() {
			updateDateDetails($(this).prev().val(), $(this));
		});

		// For Attempt Two
		$("#btnUpdateAtt2").click(function() {
			updateDateDetails($(this).prev().val(), $(this));
		});

		//Date Pickers
		$("#datepicker1").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			onSelect: function(selected) {
	         $("#datepicker2").datepicker("option","minDate", selected);
	          $("#datepicker3").datepicker("option","minDate", selected)
	        }
	    });
	    $("#datepicker2").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        onSelect: function(selected) {
	         $("#datepicker1").datepicker("option","maxDate", selected)
	           $("#datepicker4").datepicker("option","minDate", selected)
	        }
	    }); 
	    
	    
	    $("#datepicker3").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			onSelect: function(selected) {
	          $("#datepicker4").datepicker("option","minDate", selected);
	           //$("#datepicker1").datepicker("option","maxDate", selected);
	           $("#datepicker5").datepicker("option","minDate", selected);
	        }
	    });
	    
	    $("#datepicker4").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
			yearRange: range,
			minDate:0,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        onSelect: function(selected) {
	           //$("#datepicker3").datepicker("option","maxDate", selected)
	           ///$("#datepicker2").datepicker("option","maxDate", selected)
	           //$("#datepicker6").datepicker("option","minDate", selected)
	        }
	    }); 
	    
	    
	    $("#datepicker5").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			minDate:0,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			onSelect: function(selected) {
	          $("#datepicker6").datepicker("option","minDate", selected);
	          //$("#datepicker3").datepicker("option","maxDate", selected);
	           //$("#datepicker7").datepicker("option","minDate", selected);
	        }
	    });
	    $("#datepicker6").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        onSelect: function(selected) {
	         //  $("#datepicker5").datepicker("option","maxDate", selected);
	           //$("#datepicker4").datepicker("option","maxDate", selected);
	          // $("#datepicker10").datepicker("option","minDate", selected);
	            //$("#datepicker9").datepicker("option","minDate", selected);
	             //$("#datepicker8").datepicker("option","minDate", selected)
	        }
	    }); 
	    
	    $("#datepicker7").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			onSelect: function(selected) {
	         // $("#datepicker10").datepicker("option","minDate", selected);
	          // $("#datepicker5").datepicker("option","maxDate", selected);
	            $("#datepicker8").datepicker("option","minDate", selected);
	        }
	    });
	    $("#datepicker8").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        onSelect: function(selected) {
	          // $("#datepicker9").datepicker("option","maxDate", selected);
	          // $("#datepicker6").datepicker("option","maxDate", selected);
	          // $("#datepicker10").datepicker("option","minDate", selected);
	        }
	    });
	    
	    $("#datepicker9").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			onSelect: function(selected) {
	          $("#datepicker10").datepicker("option","minDate", selected);
		      //$("#datepicker7").datepicker("option","maxDate", selected);
	        }
	    });
	    $("#datepicker10").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        onSelect: function(selected) {
	           //$("#datepicker9").datepicker("option","maxDate", selected);
	           // $("#datepicker8").datepicker("option","maxDate", selected);
	            // $("#datepicker9").datepicker("option","minDate", selected)
	        }
	    }); 
	    $("#datepicker11").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        onSelect: function(selected) {
	         $("#datepicker12").datepicker("option","minDate", selected);
	           //$("#datepicker9").datepicker("option","maxDate", selected);
	           // $("#datepicker8").datepicker("option","maxDate", selected);
	            // $("#datepicker9").datepicker("option","minDate", selected)
	        }
	    }); 
	    $("#datepicker12").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
			minDate:0,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        onSelect: function(selected) {
	        }
	    }); 
	    
	    $("a:contains('Date Definition')").html('<span class="fadeSubmenu">Date Definition</span>');
	});
	
	function getDatesOnLoad()
	{
		if( $("#datepicker1").datepicker( "getDate" )!=null)
		{
			$("#datepicker2").datepicker("option","minDate", $("#datepicker1").datepicker( "getDate" ));
			$("#datepicker3").datepicker("option","minDate", $("#datepicker1").datepicker( "getDate" ));
			$("#datepicker4").datepicker("option","minDate", $("#datepicker1").datepicker( "getDate" ));
		}
		if( $("#datepicker2").datepicker( "getDate" )!=null)
		{
			$("#datepicker4").datepicker("option","minDate", $("#datepicker2").datepicker( "getDate" ));
		}
		if( $("#datepicker3").datepicker( "getDate" )!=null)
		{
			$("#datepicker5").datepicker("option","minDate", $("#datepicker3").datepicker( "getDate" ));
			 $("#datepicker1").datepicker("option","maxDate", $("#datepicker3").datepicker( "getDate" ));
		}
		if( $("#datepicker4").datepicker( "getDate" )!=null)
		{
			 $("#datepicker6").datepicker("option","minDate", $("#datepicker4").datepicker( "getDate" ));
			 $("#datepicker2").datepicker("option","maxDate", $("#datepicker4").datepicker( "getDate" ));
		}
		if( $("#datepicker5").datepicker( "getDate" )!=null)
		{
			$("#datepicker3").datepicker("option","maxDate", $("#datepicker5").datepicker( "getDate" ));
			 $("#datepicker7").datepicker("option","minDate", $("#datepicker5").datepicker( "getDate" ));
		}
		if( $("#datepicker6").datepicker( "getDate" )!=null)
		{
			 $("#datepicker4").datepicker("option","maxDate", $("#datepicker6").datepicker( "getDate" ));
			  $("#datepicker8").datepicker("option","minDate", $("#datepicker6").datepicker( "getDate" ));
		}
		if( $("#datepicker7").datepicker( "getDate" )!=null)
		{
			$("#datepicker5").datepicker("option","maxDate", $("#datepicker7").datepicker( "getDate" ));
			 $("#datepicker9").datepicker("option","minDate", $("#datepicker7").datepicker( "getDate" ));
			
		}
		if( $("#datepicker8").datepicker( "getDate" )!=null)
		{
			 $("#datepicker6").datepicker("option","maxDate", $("#datepicker8").datepicker( "getDate" ));
			  $("#datepicker10").datepicker("option","minDate", $("#datepicker8").datepicker( "getDate" ));
		}
		if( $("#datepicker9").datepicker( "getDate" )!=null)
		{
		 	$("#datepicker10").datepicker("option","minDate", $("#datepicker9").datepicker( "getDate" ));
		    $("#datepicker7").datepicker("option","maxDate", $("#datepicker9").datepicker( "getDate" ));
		    
		}
		if( $("#datepicker10").datepicker( "getDate" )!=null)
		{
			 $("#datepicker8").datepicker("option","maxDate", $("#datepicker10").datepicker( "getDate" ));
			  $("#datepicker9").datepicker("option","maxDate", $("#datepicker10").datepicker( "getDate" ));
		}
		 
	}
	
	function validateDates(){
		var msg = "";
		//alert( $("#datepicker1").datepicker( "getDate" )+"--"+ $("#datepicker3").datepicker( "getDate" ));
		
		if(($("#datepicker1").datepicker( "getDate" ) > $("#datepicker3").datepicker( "getDate" ) )&& $("#datepicker3").datepicker( "getDate" ) !=null)
				msg = msg + "Application Form start date cannot be greater than Approve Candidate start date."+"##";
		else
			if($("#datepicker3").datepicker( "getDate" ) > $("#datepicker5").datepicker( "getDate" ) && $("#datepicker5").datepicker( "getDate" ) !=null)
				msg = msg + "Approve Candidate start date cannot be greater than OTBS start date."+"##";
			else
				if($("#datepicker5").datepicker( "getDate" )  >$("#datepicker7").datepicker( "getDate" ) && $("#datepicker7").datepicker( "getDate" ) !=null )
					msg = msg + "OTBS start date cannot be greater than Attempt One start date."+"##";
				else
					if($("#datepicker7").datepicker( "getDate" )  > $("#datepicker9").datepicker( "getDate" ) && $("#datepicker9").datepicker( "getDate" ) !=null)
						msg = msg + "Attempt One start date start date cannot be greater than Attempt Two start date."+"##";
						
		if($("#datepicker2").datepicker( "getDate" )  > $("#datepicker4").datepicker( "getDate" ) && $("#datepicker4").datepicker( "getDate" ) !=null )
				msg = msg + "Application Form end date cannot be greater than Approve Candidate end date."+"##";
		else
			if($("#datepicker4").datepicker( "getDate" )  > $("#datepicker6").datepicker( "getDate" ) && $("#datepicker6").datepicker( "getDate" ) !=null )
				msg = msg + "Approve Candidate end date cannot be greater than OTBS end date."+"##";
			else
				if($("#datepicker6").datepicker( "getDate" )  >$("#datepicker8").datepicker( "getDate" ) && $("#datepicker8").datepicker( "getDate" ) !=null)
					msg = msg + "OTBS end date cannot be greater than Attempt One end date."+"##";
				else
					if($("#datepicker8").datepicker( "getDate" ) > $("#datepicker10").datepicker( "getDate" ) && $("#datepicker10").datepicker( "getDate" ) !=null)
						msg = msg + "Attempt One end date cannot be greater than Attempt Two end date."+"##";
		
		return msg;
	}
	
	function updateDateDetails(desc,activebutton){
		
		var startDate = "";
		var endDate = "";
		var message = "";
		
		if(desc == "ApplicationForm"){
			startDate = $("#datepicker1").val();
			endDate = $("#datepicker2").val();
			message = validateDates();
		}
		
		if(desc == "Candidate"){
			startDate = $("#datepicker3").val();
			endDate = $("#datepicker4").val();
			message = validateDates();
		}
		
		if(desc == "OTBS"){
			startDate = $("#datepicker5").val();
			endDate = $("#datepicker6").val();
			message = validateDates();
		}
		
		if(desc == "AttemptOne"){
			startDate = $("#datepicker7").val();
			endDate = $("#datepicker8").val();
			message = validateDates();
		}
		
		if(desc == "AttemptTwo"){
			startDate = $("#datepicker9").val();
			endDate = $("#datepicker10").val();
			message = validateDates();
		}

		if(desc == "Payment"){
			startDate = $("#datepicker11").val();
			endDate = $("#datepicker12").val();
			message = validateDates();
		}
		
		if(startDate == null || startDate == '')
		{
			message = message + "Please select Start Date."+"##";
		}
		if(endDate == null || endDate == '')
		{
			message = message + "Please select End Date."+"##";
		}
		
		if (Date.parse(startDate) > Date.parse(endDate )) {
	        message = message + "Invalid date range!## Please choose a date that is not later than today!"+"##";
	    }
			
			if(message != ""){
				var ulID = "error-ul-DateDef";
				var divID = "error-DateDef";
				createErrorList(message, ulID, divID); 
				return false;
			}else{
				$("#error-DateDef").hide();
			}
				
			var dataString = "regStartDate="+startDate+"&regEndDate="+endDate+"&desc="+desc;
			
			$.ajax({
				url: "SettingsAction_updateDateRangeDetails.action",
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
					
					$("#btnCloseDateDefResult").unbind();
					if(textMessae[0]=='9'){
						alert(message);
						return false;
					}
					else{
						//alert("Date range updated successfully.");
						$("#lblDateDefMessage").html("Date range updated successfully.");
						$("#btnCloseDateDefResult").bind('click', function() {
							HideAll();
							PopHideAll();
						});
						PopHideAll();
						ShowPop('block9');
					}
				}
			});
		
		
	}

	
	
</script>

<div class="container">
<div class="main-body">
	<div class="fade" id="block7"></div>
	<div id="dashboard">
		<h1 class="pageTitle" title="Date Definition">Date Definition</h1>
		<div class="hr-underline2"></div>
		<div id="error-DateDef" style="display:none">
	      	<div class="error-massage-text">
	      	<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul-DateDef">
	      	
	      	
	      	</ul>
	      	
	      	 </div>
      	</div>
		
		<!-- Box Container Start -->
		<s:form action="SettingsAction">
		<s:token/>
			<div style="display:block; min-height:250px; height:auto;">
			  <table border="0" cellpadding="0" cellspacing="0" width="80%" >
			    <!-- [Start] Date row for Application Form -->
			    <tr>
			      <td style="width:15%"><strong>Apply Post Form</strong></td>
			      <td ><label for="select2"></label>
			        <span class="lighttext">Start Date</span><span class="manadetory-fields">*</span></td>
			      <td ><span class="lighttext">End Date</span><span class="manadetory-fields">*</span></td>
			      <td >&nbsp;</td>
			    </tr>
			    <tr>
			      <td width="249" height="40" valign="top">&nbsp;</td>
			      <td width="210" valign="top" ><label for="select17"></label>
			        <s:textfield name="appFormStartDt" readonly="true"  cssClass="s" id="datepicker1" />
			      </td>
			      <td width="210" valign="top" ><s:textfield name="appFormEndDt" readonly="true" cssClass="s" id="datepicker2" />
			      </td>
			      <td width="229" valign="top" >
			      	<div>
			      		<s:hidden name="appFrm" id="%{appFrm}" value="ApplicationForm"></s:hidden>
			      		<input type="button" name="btnUpdateFormDates" class="submitBtn button-gradient" id="btnUpdateFormDates" value="Update" class="submitBtn news-gradient" />
			      	</div>
			      </td>
			    </tr>
			    <!-- [End] Date row for Application Form -->
			    
			    <!-- [Start] Date row for Aproove Candidate -->
			    <s:if test='%{candidateApproveRejectStatus=="A"}'>
			    <tr>
			      <td style="width:15%"><strong>Approve Candidate</strong></td>
			      <td ><span class="lighttext">Start Date</span><span class="manadetory-fields">*</span><br /></td>
			      <td ><span class="lighttext">End Date</span><span class="manadetory-fields">*</span><br /></td>
			      <td >&nbsp;</td>
			    </tr>
			    <tr>
			      <td height="40" valign="top">&nbsp;</td>
			      <td valign="top" ><s:textfield name="aprvCandStartDt" readonly="true" cssClass="s" id="datepicker3" />
			      </td>
			      <td valign="top" ><s:textfield name="aprvCandEndDt" readonly="true" cssClass="s" id="datepicker4" />
			      </td>
			      <td valign="top" >
			      	<div>
			      		<s:hidden name="canFrm" id="%{canFrm}" value="Candidate"></s:hidden>
			      		<input type="button" name="btnUpdateAppCan" id="btnUpdateAppCan" value="Update" class="submitBtn news-gradient" />
			      	</div>
			      </td>
			    </tr>
			    </s:if>
			    <!-- [End] Date row for Aproove Candidate -->
			    
			    <s:if test='%{paymentStatus=="A"}'>
				    <tr>
				      <td style="width:15%"><strong>Payment</strong></td>
				      <td ><span class="lighttext">Start Date</span><span class="manadetory-fields">*</span></td>
				      <td ><span class="lighttext">End Date</span><span class="manadetory-fields">*</span></td>
				      <td >&nbsp;</td>
				    </tr>
				    <tr>
				      <td height="40" valign="top">&nbsp;</td>
				      <td valign="top" ><s:textfield name="paymentStartDate" readonly="true" cssClass="s" id="datepicker11" />
				      </td>
				      <td valign="top" ><s:textfield name="paymentEndDate" readonly="true" cssClass="s" id="datepicker12" />
				      </td>
				      <td valign="top" >
				      	<div>
				      		<s:hidden name="otbsFrm" id="%{paymentFrm}" value="Payment"></s:hidden>
				      		<input type="button" class="submitBtn button-gradient" name="btnUpdatePayment" id="btnUpdatePayment" value="Update" class="submitBtn news-gradient" />
				      	</div>
				      </td>
				    </tr>
			    </s:if>
			    <!-- [Start] Date row for OTBS -->
			    
			    <s:if test='%{otbsStatus=="A"}'>
				    <tr>
				      <td><strong>OTBS</strong></td>
				      <td ><span class="lighttext">Start Date</span><span class="manadetory-fields">*</span></td>
				      <td ><span class="lighttext">End Date</span><span class="manadetory-fields">*</span></td>
				      <td >&nbsp;</td>
				    </tr>
				    <tr>
				      <td height="40" valign="top"><span class="lighttext">(Online Test Booking System)</span></td>
				      <td valign="top" ><s:textfield name="otbsStartDt" readonly="true" cssClass="s" id="datepicker5" />
				      </td>
				      <td valign="top" ><s:textfield name="otbsEndDt" readonly="true" cssClass="s" id="datepicker6" />
				      </td>
				      <td valign="top" >
				      	<div>
				      		<s:hidden name="otbsFrm" id="%{otbsFrm}" value="OTBS"></s:hidden>
				      		<input type="button" name="btnUpdateOTBS" id="btnUpdateOTBS" value="Update" class="submitBtn news-gradient" />
				      	</div>
				      </td>
				    </tr>
			    </s:if>
			    <!-- [End] Date row for OTBS -->
			    
			    <!-- [Start] Date row for Attempt One -->
			    <s:if test='%{attemp1=="A" && otbsStatus=="A"}'>
				    <tr>
				      <td><strong>Exam Date for Attempt One </strong></td>
				      <td ><span class="lighttext">Start Date</span><span class="manadetory-fields">*</span></td>
				      <td ><span class="lighttext">End Date</span><span class="manadetory-fields">*</span></td>
				      <td >&nbsp;</td>
				    </tr>
				    <tr>
				      <td height="40" valign="top">&nbsp;</td>
				      <td valign="top" ><s:textfield name="attempt1StartDt" readonly="true" cssClass="s" id="datepicker7" />
				      </td>
				      <td valign="top" ><s:textfield name="attempt1EndDt" readonly="true" cssClass="s" id="datepicker8" />
				      </td>
				      <td valign="top" >
				      	<div>
				      		<s:hidden name="attempt1" id="%{attempt1}" value="AttemptOne"></s:hidden>
				      		<input type="button" name="btnUpdateAtt1" id="btnUpdateAtt1" value="Update" class="submitBtn news-gradient" />
				      	</div>
				      </td>
				    </tr>
			    </s:if>
			    <!-- [End] Date row for Attempt One -->
			    
			    <!-- [Start] Date row for Attempt Two -->
			    <s:if test='%{attemp2=="A" && otbsStatus=="A"}'>
				    <tr>
				      <td><strong>Exam Date for Attempt Two </strong></td>
				      <td ><span class="lighttext">Start Date</span><span class="manadetory-fields">*</span></td>
				      <td ><span class="lighttext">End Date</span><span class="manadetory-fields">*</span></td>
				      <td >&nbsp;</td>
				    </tr>
				    <tr>
				      <td height="40" valign="top">&nbsp;</td>
				      <td valign="top" ><s:textfield name="attempt2StartDt" readonly="true" cssClass="s" id="datepicker9" />
				      </td>
				      <td valign="top" ><s:textfield name="attempt2EndDt" readonly="true" cssClass="s" id="datepicker10" />
				      </td>
				      <td valign="top" >
				      	<div>
				      		<s:hidden name="attempt2" id="%{attempt2}" value="AttemptTwo"></s:hidden>
				      		<input type="button" name="btnUpdateAtt2" id="btnUpdateAtt2" value="Update" class="submitBtn news-gradient" />
				      	</div>
				      </td>
				    </tr>
				  </s:if>  
			    <!-- [End] Date row for Attempt Two -->
			    
			  </table>
			</div>
		</s:form>

</div></div>
</div>
<div class="forgot-pass box-gradient" id="block9">
<div><a href="javascript:void(0);" onclick="HideAll();PopHideAll();"><img
				src="images/Close.png" align="right" border="0" title="Close" /> </a></div>
<br/>
<div class="pad12">
<div class="titleBox fl-left"><h4 id="lblDateDefMessage" class="pageTitle" title="Date Definition"></h4></div>
</div>
<br/><br/><br/><br/>
<div align="center">
	<input type="button" value="Close" class="submitBtn button-gradient" title="Close" id="btnCloseDateDefResult"/>
<br/>
<br/>
</div>

</div>
