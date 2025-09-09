<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<script>
	function getTestSlots(){
			var testDate = $("#testDate").val();
			
			var dataString = "testDate="+testDate;
	
		$.ajax({
			url: "ReportAction_getTestSlots.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
				
			},
			error:function(ajaxrequest)
			{
				alert('Error in refreshing . Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
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
				if(responseText!='null'&& responseText.length > 0)
				{
				
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
	
	
	
	function getTestDates(){
			var testCenter = $("#testCenterVal").val();
			
			var dataString = "testCenterVal="+testCenter;
	
		$.ajax({
			url: "ReportAction_getTestDates.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
				
			},
			error:function(ajaxrequest)
			{
				alert('Error in refreshing . Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
			$('#testDate').empty();
				
				responseText = $.trim(responseText);
				textMessae = responseText.split(',');
				message = responseText.substring(2, responseText.length);
				if(textMessae[0]=='9'){
						alert(message);
						return false;
				}else{
				$('#testDate').append(  
						            $('<option></option>').val("").html("All")
						     );
				if(responseText !='null' && responseText.length > 0)
				{
				
					 var element = responseText.split(",");
					
	
	
					 $.each(element, function(val) {
						    $('#testDate').append(  
						            $('<option></option>').val(element[val]).html(element[val])
						     );
					 }); 
					
				}
			}
			}
		});
	
	}
	
function open_win()
{
	
	window.open("ReportAction_generateAttendanceReport.action?disciplineVal="+$("#disciplineVal").val()+
		"&testCenterVal="+$("#testCenterVal").val()+"&testDate="+$("#testDate").val()+
		"&testSlot="+$("#testSlot").val());
	
}
</script>
  <div id="dashboard" style="display:block; min-height:300px; height:auto;">
  <s:form action="ReportAction">
  <div class="fade" id="pop3"></div>
  
  <h1 class="pageTitle" title="Attendance Sheet">Attendance Sheet</h1>
 <div class="hr-underline2"></div>
  
  <table class="contenttable">
  
  	<tr>
  		<td>
  			Discipline
  		</td>
  		<td>
  			<s:select label="Discipline" name="disciplineVal"   id = "disciplineVal" 
		headerKey="All"  headerValue="All" list="disciplineListMap" value="%{disciplineVal}"/>
  		</td>
  	
  	</tr>
  	
  	
  	
  	
  	<tr>
  		<td>
  			Test Center
  		</td>
  		<td>
  			<s:select label="Test Center" name="testCenterVal"   id = "testCenterVal" 
		headerKey="All"  headerValue="All" list="testCenterListMap" value="%{testCenterVal}" onchange="getTestDates()"/>
  		</td>
  	
  	</tr>
  	
  	<tr>
  		<td>
  			Test Date
  		</td>
  		<td>
  			<s:select name="testDate" id="testDate"
								headerKey="All"  headerValue="All"
								list="testCenterList" listValue="labelValue" listKey="labelId"
								value="%{testDate}" onchange="getTestSlots()"/>
  		</td>
  	
  	</tr>
  
  
  <tr>
  		<td>
  			Test Slots
  		</td>
  		<td>
  			<s:select name="testSlot" id="testSlot"
								headerKey="All"  headerValue="All"
								list="testCenterList" listValue="labelValue" listKey="labelId"
								value="%{testSlot}" />
  		</td>
  	
  	</tr>
  

<tr>
<td>&nbsp;</td>
	<td>
		<input type = "button" value = "Search"  class="submitBtn button-gradient" onclick = "open_win();"/>
	</td>
	
</tr>  
  	
  </table>
  
  
  
  
  </s:form>
  </div>
