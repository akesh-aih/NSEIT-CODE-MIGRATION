<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>



<script><!--

$(document).ready(function() {
	$("#selectRetestContainer").validationEngine({promptPosition : "bottomLeft", scroll: false});

	$("#labelId").change(
		function (){
		disableDates($(this).val());
		}
	);

	$("#datepicker1").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		buttonImageOnly: true,
		dateFormat: 'dd-M-yy',
    });

	$("#datepicker2").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		buttonImageOnly: true,
		dateFormat: 'dd-M-yy',
    });

});

	function disableDates(val){
	var selectedOption = $("#labelId option:selected").text();
	
	if (selectedOption=='Discipline-wise Report' ||selectedOption == 'Qualification-wise Report' || selectedOption == 'Stage-wise Report' ){
		$("#div2").hide();
		$("#div1").hide();
	}else{
	$("#div2").show();
	$("#div1").show();
	}
}

</script>

<div class="main-body">

<div class="fade" id="pop3"></div>


<div id="EnrollmentDetails">

<s:form action="ReportAction" id = "selectRetestContainer">
<s:hidden name="selectedOption" id = "selectedOption"></s:hidden>
<h1 class="pageTitle" title="Enrollment Details"><s:text name="Enrollment Report Filter"/></h1>
<div class="hr-underline2"></div>

	<div style="height:300px;" align="center">
					<br/>
					
					<table width="400" align="center" cellpadding="5">
						<tr>
							<td>
									<div align="left">
										<s:select label="Discipline" name="reportId"  cssClass="validate[required]"
										headerKey="" headerValue="Select Report Type" 
										list="reportTypeListForEnrollment" id="labelId" 
										listValue="labelValue" listKey="labelId" value="%{reportId}"/>
									</div>
									<!-- for validation messages -->
										<br/>
										<br/>
							</td>
						</tr>
						
			
						<tr>
							<td>
								<div align="left" id = "div1">
									<s:text name="From Date  : " />
									<s:text name="From Date  : " id = "fromDate"/>
									<s:textfield name="startDate" id = "datepicker1" cssClass="validate[required]" readonly="true" ></s:textfield>
								</div>
								<!-- for validation messages -->
										<br/>
										<br/>
							</td>
						</tr>
						
						
						
						<tr>
							<td>
								<div align="left" id = "div2">
									<s:text name="To Date    :" />&nbsp; &nbsp; &nbsp;
									<s:text name="To Date  : " id = "toDate"/>
									<s:textfield name="endDate" id = "datepicker2" cssClass="validate[required]" readonly="true" ></s:textfield>
								</div>
								<!-- for validation messages -->
										<br/>
										<br/>
							</td>
						</tr>
						
						
						<tr>
							<td>
								<div align="left">
									<s:text name="Format  :    " />
									<s:radio list="formatList" name="format" cssClass="validate[required]"></s:radio>
								</div>
								<!-- for validation messages -->
										<br/>
										<br/>
							</td>
						</tr>
						
						
						<tr>
							<td>
								<div align="left">
									<s:submit key="agencygenerateadmitcard.submit" cssClass="submitBtn button-gradient" method="getRegistrationReportDetails"></s:submit>&nbsp; &nbsp; &nbsp;
									<input type="reset" value="<s:text name="agencygenerateadmitcard.clear"/>" title="Clear" class="submitBtn button-gradient"/>
								</div>
							</td>
						</tr>
			</table>	
					
				
	</div>
</s:form>
</div>
</div>    
   