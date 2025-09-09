<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ include file="../security/PreventClickJacking.html"%>
<script type="text/javascript">



$(document).ready(function() {
	$("#defineDates").validationEngine({promptPosition : "centerRight", scroll: false});
		// for registration
		$("#setRegDate").click(
				function() {
			    insertDateDetails($(this).prev().val(), $(this));
			    
		});
		$("#editRegDate").click(function() {
				updateDateDetails($(this).prev().val(), $(this));
		});
	
	
	
		// for enrollment
		$("#setEnrollDate").click(function() {
			insertDateDetails($(this).prev().val(), $(this));
			
		});
		$("#editEnrollDate").click(function() {
			updateDateDetails($(this).prev().val(), $(this));
		});
	
	
		// for payment	
		$("#setPaymentDate").click(function() {
			insertDateDetails($(this).prev().val(), $(this));
		});
		$("#editPaymentDate").click(function() {
			updateDateDetails($(this).prev().val(), $(this));
		});
	


		// for Retest
		$("#setRetestDate").click(function() {
			insertDateDetails($(this).prev().val(), $(this));
		});
		$("#editRetestDate").click(function() {
			updateDateDetails($(this).prev().val(), $(this));
		});

	
		// for Generate Admit Card
		$("#setAdmitCardDate").click(function() {
			insertDateDetails($(this).prev().val(), $(this));
		});
		$("#editAdmitCardDate").click(function() {
			updateDateDetails($(this).prev().val(), $(this));
		});
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
	         $("#regEndDatePicker").datepicker("option","minDate", selected);
	          $("#datepicker3").datepicker("option","minDate", selected)
	        }
	    });
	    $("#regEndDatePicker").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
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
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			onSelect: function(selected) {
	          //$("#datepicker4").datepicker("option","minDate", selected);
	           $("#datepicker1").datepicker("option","maxDate", selected);
	           $("#datepicker5").datepicker("option","minDate", selected);
	        }
	    });
	    
	    $("#datepicker4").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        onSelect: function(selected) {
	           //$("#datepicker3").datepicker("option","maxDate", selected)
	           $("#regEndDatePicker").datepicker("option","maxDate", selected)
	           $("#datepicker6").datepicker("option","minDate", selected)
	        }
	    }); 
	    
	    
	    $("#datepicker5").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			onSelect: function(selected) {
	         // $("#datepicker6").datepicker("option","minDate", selected);
	          $("#datepicker3").datepicker("option","maxDate", selected);
	           $("#datepicker9").datepicker("option","minDate", selected);
	        }
	    });
	    $("#datepicker6").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        onSelect: function(selected) {
	         //  $("#datepicker5").datepicker("option","maxDate", selected);
	           $("#datepicker4").datepicker("option","maxDate", selected);
	          // $("#datepicker10").datepicker("option","minDate", selected);
	            //$("#datepicker9").datepicker("option","minDate", selected);
	             $("#datepicker10").datepicker("option","minDate", selected)
	        }
	    }); 
	    
	    $("#datepicker7").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			onSelect: function(selected) {
	        //  $("#datepicker8").datepicker("option","minDate", selected);
		      $("#datepicker9").datepicker("option","maxDate", selected);
	        }
	    });
	    $("#datepicker8").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        onSelect: function(selected) {
	           $("#datepicker7").datepicker("option","maxDate", selected);
	            $("#datepicker10").datepicker("option","maxDate", selected);
	            // $("#datepicker9").datepicker("option","minDate", selected)
	        }
	    }); 
	    
	    
	    $("#datepicker9").datepicker({
			showOn: "button",
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			onSelect: function(selected) {
	         // $("#datepicker10").datepicker("option","minDate", selected);
	           $("#datepicker5").datepicker("option","maxDate", selected);
	            $("#datepicker7").datepicker("option","minDate", selected);
	        }
	    });
	    $("#datepicker10").datepicker({
	    	showOn: "button",
	    	changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
	        onSelect: function(selected) {
	          // $("#datepicker9").datepicker("option","maxDate", selected);
	           $("#datepicker6").datepicker("option","maxDate", selected);
	           $("#datepicker8").datepicker("option","minDate", selected);
	        }
	    }); 
	    
	    
	    
	    
		$(document).ready(function() { 
		$("a:contains('Date Definition')").html('<span class="fadeSubmenu">Date Definition</span>')});
});


function getDateDefinition(testVar)
	{
		var dataString = "testId="+testVar;

		$.ajax({
			url: "SettingsAction_getDateRangeDetails.action",
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
				$("#testing").html(responseText);
			
					/*
					Repopulate Seats Availibility Table Here
					*/
				//	$("#activityBox").html(responseText);
				}
			}
		});
	}
	
	
	
	
	
	function updateDateDetails(desc,activebutton){
		if($(activebutton).hasClass("disabled")==true)
		{
			$(activebutton).removeClass("disabled");
			$(activebutton).parent().prev().prev().find(":nth-child(2)").find(":nth-child(1)").removeAttr('disabled');
			$(activebutton).parent().prev().find(":nth-child(2)").find(":nth-child(1)").removeAttr('disabled');
			$(activebutton).val('Update');
		}
		else
		{
			var startDate  = $(activebutton).parent().prev().prev().find(":nth-child(2)").find(":nth-child(1)").val();
			var endDate= $(activebutton).parent().prev().find(":nth-child(2)").find(":nth-child(1)").val();
			if(startDate == null || startDate == '')
			{
				alert('Please select Start Date.');
				return false;
			}
			if(endDate == null || endDate == '')
			{
				alert('Please select End Date.');
				return false;
			}
			
			if (Date.parse(startDate) > Date.parse(endDate )) {
	            alert("Invalid date range!\n Please choose a date that is not later than today!")
	            return false;
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
					if(textMessae[0]=='9'){
						alert(message);
						return false;
					}else{
					
						$(activebutton).addClass("disabled");
						$(activebutton).parent().prev().prev().find(":nth-child(2)").find(":nth-child(1)").attr("disabled","disabled");
						$(activebutton).parent().prev().find(":nth-child(2)").find(":nth-child(1)").attr("disabled","disabled");
						$(activebutton).val('Edit Date');
						if(responseText.length > 0)
						{
							/*
							Repopulate Seats Availibility Table Here
							*/
						}
					}
				}
			});
		
		}
	}
	
	
	
	
	
	function insertDateDetails(desc, activebutton){
		var regStartDate  = $(activebutton).parent().prev().prev().find(":nth-child(2)").find(":nth-child(1)").val();
		var regEndDate = $(activebutton).parent().prev().find(":nth-child(2)").find(":nth-child(1)").val();
		if(regStartDate == null || regStartDate == '')
		{
			alert('Please select Start Date');
			return false;
		}
		if(regEndDate == null || regEndDate == '')
		{
			alert('Please select End Date.');
			return false;
		}
		
		if (Date.parse(regStartDate) > Date.parse(regEndDate )) {
            alert("Invalid date range!\n Please choose a date that is not later than today!'Start date can not be less than end date.'")
            return false;
        }
		var dataString = "regStartDate="+regStartDate+"&regEndDate="+regEndDate+"&desc="+desc;
		$.ajax({
			url: "SettingsAction_insertDateRangeDetails.action",
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
			{//alert(responseText);
				responseText = $.trim(responseText);
				textMessae = responseText.split(',');
				message = responseText.substring(2, responseText.length);
				if(textMessae[0]=='9'){
					alert(message);
					return false;
				}else{
					
					$(activebutton).val("Edit Date");
					var attr = $(activebutton).attr("id");
					attr = attr.replace("set", "edit");
					$(activebutton).attr("id", attr);
					
					$(activebutton).unbind('click');
					$(activebutton).bind('click', function() {
						updateDateDetails($(this).prev().attr("id"), $(this));
					});
					
					$(activebutton).parent().prev().prev().find(":nth-child(2)").find(":nth-child(1)").attr('disabled','disabled');
					$(activebutton).parent().prev().find(":nth-child(2)").find(":nth-child(1)").attr('disabled','disabled');
					
					$(activebutton).addClass("disabled");
					if(responseText.length > 0)
					
					{
					
						
					}
				}
			}
		});
		
	}
	function getDatesOnLoad()
	{
		if( $("#datepicker1").datepicker( "getDate" )!=null)
		{
			$("#regEndDatePicker").datepicker("option","minDate", $("#datepicker1").datepicker( "getDate" ));
			$("#datepicker3").datepicker("option","minDate", $("#datepicker1").datepicker( "getDate" ));
			$("#datepicker4").datepicker("option","minDate", $("#datepicker1").datepicker( "getDate" ));
		}
		if( $("#regEndDatePicker").datepicker( "getDate" )!=null)
		{
			$("#datepicker4").datepicker("option","minDate", $("#regEndDatePicker").datepicker( "getDate" ));
		}
		if( $("#datepicker3").datepicker( "getDate" )!=null)
		{
			$("#datepicker5").datepicker("option","minDate", $("#datepicker3").datepicker( "getDate" ));
			 $("#datepicker1").datepicker("option","maxDate", $("#datepicker3").datepicker( "getDate" ));
		}
		if( $("#datepicker4").datepicker( "getDate" )!=null)
		{
			 $("#datepicker6").datepicker("option","minDate", $("#datepicker4").datepicker( "getDate" ));
			 $("#regEndDatePicker").datepicker("option","maxDate", $("#datepicker4").datepicker( "getDate" ));
		}
		if( $("#datepicker5").datepicker( "getDate" )!=null)
		{
			$("#datepicker3").datepicker("option","maxDate", $("#datepicker5").datepicker( "getDate" ));
			 $("#datepicker9").datepicker("option","minDate", $("#datepicker5").datepicker( "getDate" ));
		}
		if( $("#datepicker6").datepicker( "getDate" )!=null)
		{
			 $("#datepicker4").datepicker("option","maxDate", $("#datepicker6").datepicker( "getDate" ));
			  $("#datepicker10").datepicker("option","minDate", $("#datepicker6").datepicker( "getDate" ));
		}
		if( $("#datepicker7").datepicker( "getDate" )!=null)
		{
		 	$("#datepicker8").datepicker("option","minDate", $("#datepicker7").datepicker( "getDate" ));
		    $("#datepicker9").datepicker("option","maxDate", $("#datepicker7").datepicker( "getDate" ));
		    
		 }
		 if( $("#datepicker8").datepicker( "getDate" )!=null)
		{
			 $("#datepicker10").datepicker("option","maxDate", $("#datepicker8").datepicker( "getDate" ));
			  $("#datepicker7").datepicker("option","maxDate", $("#datepicker8").datepicker( "getDate" ));
		}
		 if( $("#datepicker9").datepicker( "getDate" )!=null)
		{
			$("#datepicker5").datepicker("option","maxDate", $("#datepicker9").datepicker( "getDate" ));
			 $("#datepicker7").datepicker("option","minDate", $("#datepicker9").datepicker( "getDate" ));
			
		}
		 if( $("#datepicker10").datepicker( "getDate" )!=null)
		{
			 $("#datepicker6").datepicker("option","maxDate", $("#datepicker10").datepicker( "getDate" ));
			//  $("#datepicker9").datepicker("option","maxDate", $("#datepicker10").datepicker( "getDate" ));
			  $("#datepicker8").datepicker("option","minDate", $("#datepicker10").datepicker( "getDate" ));
		}
		
		
	}
	 	
</script>
    
<body onload="getDatesOnLoad();">
<div class="container">

<div class="fade" id="pop3"></div>
<div id="AgencyDateDefine">

<h1 class="pageTitle" title="Date Definition"><s:text name="agencydatedef.title"/></h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div class="AgencyDateDefineCont">
<s:form action="SettingsAction">

<s:token></s:token>
<div>
<div class="fifty fl-left">

<div id="defineError1" class="errorMessage">Please select test name.</div>
</div>

<div class="fifty fl-rigt">
<!--<div class="field-label">Activity&nbsp;<span class="manadetory-fields">*</span></div>
<div>
<select class="mid-dropdown" name="activity">
<option value="">Activity</option>
<option value="Registration">Registration</option>
<option value="Enrollment">Enrollment</option>
<option value="Payment">Payment</option>
</select></div>
<div id="defineError2" class="errorMessage">Please select activity.</div>-->
</div>
</div>


<div class="clear hr-underline"></div>

<div>
	<div class="clear">
		<div class="fl-left field-label ActivityLable"><s:text name="agencydatedef.registration"/></div>
			<div class="fl-left date1">
				<div class="field-label"><s:text name="agencydatedef.startdate"/>&nbsp;<span class="manadetory-fields">*</span></div>
					<div>
						<s:textfield name="newRegStartDate" id = "datepicker1" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select start date" disabled="%{regDateFlag}" />
						<input type="hidden" name="registrationDate" id="<s:property value="regStartDate" />" value="<s:property value="regStartDate" />"/>
					</div>
					
				<div id="defineError3" class="errorMessage">Please select test start date.</div>
			</div>
		<div class="fl-left date2">
			<div class="field-label"><s:text name="agencydatedef.enddate"/>&nbsp;<span class="manadetory-fields">*</span></div>
				<div>
				<s:textfield name="newRegEndDate" id = "regEndDatePicker" disabled="%{regDateFlag}" cssClass="datePicker" readonly="true" ></s:textfield>
					<input type="hidden" name="registrationDate" id="<s:property value="regEndDate" />" value="<s:property value="regEndDate" />"/>
				</div>
			<div id="defineError3" class="errorMessage">Please select test start date.</div>
		</div>
		
		<div class="fl-left EditSetBtn">
			<s:if test='%{regDateStatus=="Set Date"}'> 
				<input type="hidden" name="registrationStage" id="<s:property value="registrationStage" />" value="Registration"/>
				<input type="button" value="<s:text name="agencydatedef.setdate"/>" class="submitBtn button-gradient"  class="inputDate" id="setRegDate"/>
			 </s:if>
			 <s:else>
			 	<input type="hidden" name="registrationStage" id="<s:property value="registrationStage" />" value="Registration"/>
				<input type="button" value="<s:text name="agencydatedef.editdate" />" class="submitBtn button-gradient disabled" class="inputDate" id="editRegDate"/>
			</s:else>
		</div>
</div>
	<div class="clear hr-underline"></div>
	<!-- Row One End -->
	
	<!-- Row Two Start -->
<div class="clear">
	<div class="fl-left field-label ActivityLable"><s:text name="agencydatedef.enrollment"/></div>
	<div class="fl-left date1">
		<div class="field-label"><s:text name="agencydatedef.startdate"/>&nbsp;<span class="manadetory-fields">*</span></div>
			<div>
				<s:textfield name="enrollmentStartDate" id = "datepicker3" readonly="true" cssClass="validate[required]"  cssClass='datePicker' errRequired="Please select start date" disabled="%{enrollDateFlag}"/>
			</div>
			<div id="defineError3" class="errorMessage">Please select test start date.</div>
	</div>
	
	<div class="fl-left date2">
		<div class="field-label"><s:text name="agencydatedef.enddate"/>&nbsp;<span class="manadetory-fields">*</span></div>
			<div>
				<s:textfield name="enrollmentEndDate" id = "datepicker4" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select end date" disabled="%{enrollDateFlag}"/>
			</div>
		<div id="defineError3" class="errorMessage">Please select test start date.</div>
	</div>
	<div class="fl-left EditSetBtn">
		<s:if test='%{enrollDateStatus=="Set Date"}'> 
			<input type="hidden" name="enrollmentStage" id="<s:property value="enrollmentStage" />" value="Enrollment"/>
			<input type="button" value="<s:text name="agencydatedef.setdate"/>" class="submitBtn button-gradient"  id="setEnrollDate" />
		</s:if>
		<s:else>
			<input type="hidden" name="enrollmentStage" id="<s:property value="enrollmentStage" />" value="Enrollment"/>
			<input type="button" value="<s:text name="agencydatedef.editdate"/>"  class="submitBtn button-gradient disabled" id="editEnrollDate"/>
		</s:else>
	</div>
</div>
	<div class="clear hr-underline"></div>
	<!-- Row Two End -->
	
	<!-- Row Three Start -->
<div class="clear">
	<div class="fl-left field-label ActivityLable"><s:text name="agencydatedef.payment"/></div>
		<div class="fl-left date1">
			<div class="field-label">
				<s:text name="agencydatedef.startdate"/>&nbsp;<span class="manadetory-fields">*</span>
			</div>
			<div>
				<s:textfield name="paymentStartDate" id = "datepicker5" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select start date" disabled="%{paymentDateFlag}"/>
			</div>
			<div id="defineError3" class="errorMessage">Please select test start date.</div>
		</div>
	
	<div class="fl-left date2">
		<div class="field-label"><s:text name="agencydatedef.enddate"/>&nbsp;<span class="manadetory-fields">*</span></div>
		<div>
			<s:textfield name="paymentEndDate" id = "datepicker6" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select end date" disabled="%{paymentDateFlag}"/>
		</div>
		<div id="defineError3" class="errorMessage">Please select test start date.</div>
	</div>
	<div class="fl-left EditSetBtn">
	<s:if test='%{paymentDateStatus=="Set Date"}'>
		<input type="hidden" name="paymentStage" id="<s:property value="paymentStage" />" value="Payment"/>
		<input type="button"  value="<s:text name="agencydatedef.setdate"/>"  class="submitBtn button-gradient"  id="setPaymentDate"/>
	</s:if>
	<s:else>
		<input type="hidden" name="paymentStage" id="<s:property value="paymentStage" />" value="Payment"/>
		<input type="button" value="<s:text name="agencydatedef.editdate"/>"  class="submitBtn button-gradient disabled"  id="editPaymentDate"/>
	</s:else>
	</div>
</div>
	<div class="clear hr-underline"></div>
	<!-- Row Three End -->
	
	<!-- Row Four Start -->
<div class="clear" >
	<div class="fl-left field-label ActivityLable"><s:text name="agencydatedef.admitcard"/></div>
		<div class="fl-left date1">
			<div class="field-label">
			        <s:text name="agencydatedef.startdate"/>&nbsp;<span class="manadetory-fields">*</span>
			</div>
			<div>
				<s:textfield name="admitCardStartDate" id = "datepicker9" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select start date" disabled="%{admitcardDateFlag}"/>
			</div>
			<div id="defineError3" class="errorMessage">Please select test start date.</div>
		</div>
	
	<div class="fl-left date2">
		<div class="field-label"><s:text name="agencydatedef.enddate"/>&nbsp;<span class="manadetory-fields">*</span></div>
		<div>
		
			<s:textfield name="admitCardEndDate" id = "datepicker10" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select end date" disabled="%{admitcardDateFlag}"/>
		</div>
		<div id="defineError3" class="errorMessage">Please select test start date.</div>
	</div>
	<div class="fl-left EditSetBtn" >
	<s:if test='%{admitCardDateStatus=="Set Date"}'>
		<input type="hidden" name="admitcardStage" id="<s:property value="admitcardStage" />" value="generateAdmitCard"/>
		<input type="button"  value="<s:text name="agencydatedef.setdate"/>"  class="submitBtn button-gradient"  id="setAdmitCardDate" />
		</s:if>
		
		<s:else>
		<input type="hidden" name="admitcardStage" id="<s:property value="admitcardStage" />" value="generateAdmitCard"/>	
		<input type="button" value="<s:text name="agencydatedef.editdate"/>" class="submitBtn button-gradient disabled"  id="editAdmitCardDate"/>
		</s:else>
	</div>
</div>
	<div class="clear hr-underline"></div>
	<!-- Row Four End -->
	
	<!-- Row Five Start -->
<div class="clear" >
	<div class="fl-left field-label ActivityLable"><s:text name="agencydatedef.reset"/></div>
		<div class="fl-left date1">
			<div class="field-label"><s:text name="agencydatedef.startdate"/>&nbsp;<span class="manadetory-fields">*</span></div>
			<div>
				<s:textfield name="retestStartDate" id = "datepicker7" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select start date" disabled="%{reTestDateFlag}"/>
			</div>
			<div id="defineError3" class="errorMessage">Please select test start date.</div>
		</div>
	
	<div class="fl-left date2">
	<div class="field-label"><s:text name="agencydatedef.enddate"/>&nbsp;<span class="manadetory-fields">*</span></div>
		<div>
		
			<s:textfield name="retestEndDate" id = "datepicker8" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select end date" disabled="%{reTestDateFlag}"/>
		</div>
	<div id="defineError3" class="errorMessage">Please select test start date.</div>
	</div>
	<div class="fl-left EditSetBtn" >
		<s:if test='%{reTestDateStatus=="Set Date"}'>
			<input type="hidden" name="retestStage" id="<s:property value="retestStage" />" value="Retest"/>
			<input type="button"  value="<s:text name="agencydatedef.setdate"/>"   class="submitBtn button-gradient"  id="setRetestDate" />
		</s:if>
		
		<s:else>
			<input type="hidden" name="retestStage" id="<s:property value="retestStage" />" value="Retest"/>	
			<input type="button" value="<s:text name="agencydatedef.editdate"/>" class="submitBtn button-gradient disabled" id="editRetestDate"/>
		</s:else>
	</div>
</div>
	<div class="clear hr-underline"></div>
	<!-- Row Five End -->
</div>

<div class="clear height10"></div>
<!--<div class="clear"><input type="button" value="Submit" title="Submit" class="submitBtn button-gradient"/></div>-->

</s:form>
</div>

<!-- Box Container End -->

</div>

</div>
</body>
