<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head runat="server">
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <meta http-equiv="CACHE-CONTROL" content="NO-CACHE">
    <meta http-equiv="EXPIRES" content="0">
    <meta http-equiv="PRAGMA" content="NO-CACHE">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
    <title>Welcome to Online Exam</title>
    <link href="css/common.css" rel="stylesheet" type="text/css" media="screen"/>
    <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<script src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>

<script type="text/javascript" src="js/login.js"></script>

    <script>
    $(document).ready(function() {
    	$("#datepicker3").datepicker({
			showOn: "button",
			buttonImageOnly: true,
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			maxDate: 0,
	        numberOfMonths: 1,
	        onSelect: function(selected) {
	          $("#datepicker4").datepicker("option","minDate", selected)
	        }
	    });
	    $("#datepicker4").datepicker({
	    	showOn: "button",
			buttonImageOnly: true,
			changeMonth: true,
			changeYear: true,
			yearRange: range,
			buttonImage: "images/cale-img.gif",
			buttonImageOnly: true,
			dateFormat: 'dd-M-yy',
			maxDate: 0,
	        numberOfMonths: 1,
	        onSelect: function(selected) {
	           $("#datepicker3").datepicker("option","maxDate", selected)
	        }
	    });
    
    });
    
    function callFunc(){
        $("#error-massageAppForm").hide();
    }
    function validateForm(){
	    var ulID = "error-ulAppForm";
		var divID = "error-massageAppForm";
		
		var message = "";
		
			
		var isCheckedVal = false;
		$(".degreeTypeClass").each(function(){
			if($(this).is(':checked'))
			{
				isCheckedVal = true;
			}
		});

		if(isCheckedVal == false)
		{
			message = message + "Please select Export Format Type."+"##";
		}
			
			if(message != ""){
				createErrorList(message, ulID, divID); 
				$("#error-massageAppForm").focus();
				$('html, body').animate({ scrollTop: 0 }, 0);
				//$('html, body').animate({ scrollTop: 0 }, 'slow');
			}else{
				//$("#summaryForm").attr('action',"AgencyAction_generateReport.action");
				//$("#summaryForm").submit();
				open_win();
			}

}

function on_click()
{
	 $("#error-massageAppForm").hide();

	var resultStatus = "";
	$(".degreeTypeClass").each(function(){
		if($(this).is(':checked'))
		{
			//isCheckedVal = true;
			 var id = $(this).attr("id");
			 resultStatus = $('label[for='+id+']').text();
			 $("#format").val(resultStatus);
			 
		}
		
		
	});
			
			
						 
						 
}
function open_win()
{

	//window.open("AgencyAction_generateReport.action");
	
	window.open("AgencyAction_generateShortlistedCandidateReport.action?programName="+$("#programName").val()+
		"&category="+$("#category").val()+"&interviewType="+$("#interviewType").val());
}
    
    
    </script>
</head>





<body>
<div class="container">


<div class="main-body">

<div class="fade" id="pop3"></div>


<s:form action="AgencyAction" id = "summaryForm" >

<div id="dashboard">

<h1 class="pageTitle" title="Personal Details">Shortlisted Candidates Report</h1>

<div class="hr-underline2"></div>

<div id="error-massageAppForm" style="display:none" class="error-massage">
      	<div class="error-massage-text">
      	<ul style="margin:0; margin-left:20px; padding:0;" id="error-ulAppForm">
      	
      	</ul>
      	 </div>
      </div>


<!-- Box Container Start -->
<div style="display:block; min-height:250px; height:auto;">
  <table class="contenttable">
    <tr>
      <td width="238">Name of Program</td>
      <td width="668" ><label for="select17"></label>
        <label for="textfield">
        <s:select list = "programDetailsList" name="programName" label = "Name"    
						headerKey="0" headerValue = "All" id="programName"  value="%{programName}" />
        </label></td>
      </tr>
    <tr>
      <td>Category</td>
      <td ><s:select list = "categoryList" name="category" label = "Name"    
						headerKey="0" headerValue = "All" id="category"  value="%{category}" /></td>
      </tr>
    <tr>
      <td>Interview Type</td>
      <td ><s:select list = "interviewTypeList" name="interviewType" label = "Name"    
						headerKey="All" headerValue = "All" id="interviewType"  value="%{interviewType}" /></td>
      </tr>
    <!--<tr>
      <td>Registration From Date</td>
      
      <td ><s:textfield name="registrationFromDate" value="%{registrationFromDate}" id="datepicker3" disabled="disabled" readonly="true" cssClass="inputDate disableEnroll" errRequired="Please select From date"></s:textfield></td>
      
    </tr>
    <tr>
      <td>Registration To Date</td>
      <td ><s:textfield name="registrationToDate" value="%{registrationToDate}" id="datepicker4" disabled="disabled" readonly="true" cssClass="inputDate disableEnroll" errRequired="Please select To date"></s:textfield></td>
    </tr>
    
    
    --><tr>
      <td>&nbsp;</td>
      
      <td >
    	  <input type = "button" value = "Generate EXCEL Report"  class="submitBtn button-gradient" onclick = "open_win();"/>
        &nbsp;
			<input type="reset" name="button2" id="button2" value="Clear" class="submitBtn button-gradient" onclick="callFunc();"/></td>
    </tr>
    </table>
  <label for="select"></label>
</div>

</div>
</s:form>
</div>
</div>
</body>
</html>