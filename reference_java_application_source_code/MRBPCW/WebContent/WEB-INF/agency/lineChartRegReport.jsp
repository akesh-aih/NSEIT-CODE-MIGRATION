<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>



<script>

$(document).ready(function() {
	$("a:contains('Registration Report')").html('<span class="fadeSubmenu">Registration Report</span>');

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


</script>


<div class="container">

<div class="main-body">


<s:form action="ReportAction">
<div class="fade" id="pop3"></div>

	<div style="height:300px;" align="center">
					<br/><br/><br/>
					
					<div align="center" class="field-label"><s:text name="Registration Linechart Report Filter"/>&nbsp;</div>
					<br/><br/>
					<div align="center">
					<s:text name="From Date  : "/>
						<s:textfield name="startDate" id = "datepicker1" cssClass="datePicker" readonly="true" ></s:textfield>
					</div>

					<br/>
					
					<div align="center">
					<s:text name="To Date  : "/>
						<s:textfield name="endDate" id = "datepicker2" cssClass="datePicker" readonly="true" ></s:textfield>
					</div>
					
					<br/>
					
					<div align="center">
						<s:radio list="formatList" name="format"></s:radio>
					</div>
					<br/><br/><br/>
				<div align="center">

							<s:submit value = "Submit" method = "getRegistrationReportDetails" ></s:submit>
						</div>
	</div>
</s:form>
</div>
</div>    
   