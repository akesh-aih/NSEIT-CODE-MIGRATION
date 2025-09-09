<%@ taglib prefix="s" uri="/struts-tags"%>

<script>


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
});

	
	
	
	function insertDateDetails(desc, activebutton){
		var regStartDate  = $(activebutton).parent().prev().prev().find(":nth-child(2)").find(":nth-child(1)").val();
		var regEndDate = $(activebutton).parent().prev().find(":nth-child(2)").find(":nth-child(1)").val();

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
				if(textMessae[0]=='2'){
					alert("Error : \n"+textMessae[1]);
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

	
	function updateDateDetails(desc,activebutton){
	
	alert (desc);
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
			
			var dataString = "regStartDate="+startDate+"&regEndDate="+endDate+"&desc="+desc;
			
			alert (dataString);
			
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
					if(textMessae[0]=='2'){
						alert("Error : \n"+textMessae[1]);
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


	$(function() {
		$( ".datePicker" ).datepicker({
			showOn: "button",
			buttonImageOnly: true,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd/mm/yy'
		});
	});
	
	
	
	
	
</script>
<form id="defineDates">
<!-- Row One Start -->
<div class="clear">
		<div class="fl-left field-label ActivityLable"><s:text name="agencydatedef.registration"/></div>
			<div class="fl-left date1">
				<div class="field-label"><s:text name="agencydatedef.startdate"/>&nbsp;<span class="manadetory-fields">*</span></div>
					<div>
						<s:textfield name="regStartDate" id = "datepicker1" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select start date" disabled="%{regDateFlag}" />
					</div>
					
				<div id="defineError3" class="errorMessage">Please select test start date.</div>
			</div>
		<div class="fl-left date2">
			<div class="field-label"><s:text name="agencydatedef.enddate"/>&nbsp;<span class="manadetory-fields">*</span></div>
				<div>
					<s:textfield name="regEndDate"  id = "datepicker2" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select end date" disabled="%{regDateFlag}"/>
				</div>
			<div id="defineError3" class="errorMessage">Please select test start date.</div>
		</div>
		
		<div class="fl-left EditSetBtn">
			<s:if test='%{regDateStatus=="Set Date"}'> 
				<input type="hidden" name="registrationStage" id="<s:property value="registrationStage" />" value="Registration"/>
				<input type="button" value="Set Date" class="submitBtn button-gradient"  class="inputDate" id="setRegDate"/>
			 </s:if>
			 <s:else>
			 	<input type="hidden" name="registrationStage" id="<s:property value="registrationStage" />" value="Registration"/>
				<input type="button" value="Edit Date" class="submitBtn button-gradient disabled" class="inputDate" id="editRegDate"/>
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
			<input type="button" value="Set Date" class="submitBtn button-gradient"  id="setEnrollDate" />
		</s:if>
		<s:else>
			<input type="hidden" name="enrollmentStage" id="<s:property value="enrollmentStage" />" value="Enrollment"/>
			<input type="button" value="Edit Date"   class="submitBtn button-gradient disabled" id="editEnrollDate"/>
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
		<input type="button" value="Set Date" class="submitBtn button-gradient"  id="setPaymentDate"/>
	</s:if>
	<s:else>
		<input type="hidden" name="paymentStage" id="<s:property value="paymentStage" />" value="Payment"/>
		<input type="button" value="Edit Date" class="submitBtn button-gradient disabled"  id="editPaymentDate"/>
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
				<s:textfield name="admitcardStartDate" id = "datepicker9" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select start date" disabled="%{admitcardDateFlag}"/>
			</div>
			<div id="defineError3" class="errorMessage">Please select test start date.</div>
		</div>
	
	<div class="fl-left date2">
		<div class="field-label"><s:text name="agencydatedef.enddate"/>&nbsp;<span class="manadetory-fields">*</span></div>
		<div>
		
			<s:textfield name="admitcardEndDate" id = "datepicker10" readonly="true" cssClass="validate[required]" cssClass='datePicker' errRequired="Please select end date" disabled="%{admitcardDateFlag}"/>
		</div>
		<div id="defineError3" class="errorMessage">Please select test start date.</div>
	</div>
	<div class="fl-left EditSetBtn" >
	<s:if test='%{admitcardDateStatus=="Set Date"}'>
		<input type="hidden" name="admitcardStage" id="<s:property value="admitcardStage" />" value="generateAdmitCard"/>
		<input type="button" value="Set Date" class="submitBtn button-gradient"  id="setAdmitCardDate" />
		</s:if>
		
		<s:else>
		<input type="hidden" name="admitcardStage" id="<s:property value="admitcardStage" />" value="generateAdmitCard"/>	
		<input type="button" value="Edit Date" class="submitBtn button-gradient disabled"  id="editAdmitCardDate"/>
		</s:else>
	</div>
</div>
	<div class="clear hr-underline"></div>
	<!-- Row Four End -->

	
	
</form>