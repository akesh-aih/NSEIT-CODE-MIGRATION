<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>



<script>

$(document).ready(function() {
	$("a:contains('Schedule Report')").html('<span class="fadeSubmenu">Schedule Report</span>');


});


</script>


<div class="container">

<div class="main-body">


<s:form action="ReportAction">
<div class="fade" id="pop3"></div>

	<div style="height:300px;" align="center">
					<br/><br/><br/>
					
					<div align="center" class="field-label"><s:text name="Disciplinewise Registration Report Filter"/>&nbsp;</div>
				
					<br/>
					
					<div align="center">
						<s:radio list="formatList" name="format"></s:radio>
					</div>
					<br/><br/><br/>
					<div align="center">

							<s:submit value = "Submit" method = "getDisciplinePieChartReportDetails" ></s:submit>
						</div>
	</div>
</s:form>
</div>
</div>    
   