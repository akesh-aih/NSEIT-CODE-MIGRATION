<%@ taglib prefix="s" uri="/struts-tags"%>
<script>
$(document).ready(function(){
		 <s:if test='%{flagForNoRecord=="true"}'>
		 		//$("#block20").show();
		 </s:if>
	});
		function getTestDateDetails(){
		$("#userId").attr("disabled", true); 
			
			var testCenterValue = $("#testCenterValue").val();
			var dataString = "testCenterValue="+testCenterValue;
			
			$.ajax({
				url:"CandidateMgmtAction_getTestDatesForTestCenter.action",
				data:dataString,
				async:true,
			
				beforeSend: function()
				{
				
				$('#lbKeywords').empty();
				$('#lbKeywords').append(
						$('<option></option>').val('-1').html(
								'All'));
				},
				error:function(ajaxrequest)
				{
					alert('Error in refreshing . Server Response: '+ajaxrequest.responseText);
				},

				success: function(response){
					if(response.length > 0)
					{
						
						 var element = 	response.split(",");

						 $.each(element, function(val) {
							    $('#lbKeywords').append(  
							            $('<option></option>').val(element[val]).html(element[val])
							     );
						 }); 
					}
				}
				
			});
		}


	function getTestSlots(){

	$("#userId").attr("disabled", true);

		var testDateForSlot = $('#lbKeywords').val();

		var dataString = "";
		dataString = "testDateForSlot="+testDateForSlot;

		$.ajax({
			url:"CandidateMgmtAction_getTestSlots.action",
			data:dataString,
			aysnc:true,


			beforeSend: function(){
			$('#select3').empty();
			$('#select3').append(
					$('<option></option>').val('-1').html(
							'All'));
		},

		error : function(ajaxRequest){
			alert ("Error  "+ajaxRequest);
		},

		success: function(response){
			if(response.length > 0)
			{
				 var element = 	response.split(",");


				 $.each(element, function(val) {
					    $('#select3').append(  
					            $('<option></option>').val(element[val]).html(element[val])
					     );
				 }); 
			}
		}
		});
	}


function disableUserIdField(){
	$("#userId").attr("disabled", true); 
}


function enableUserID(){
	$("#userId").removeAttr("disabled");
}



function clearFields(){
	$("#userId").val("");

	$("#disciplineType").removeAttr("disabled"); 
	$("#testCenterValue").removeAttr("disabled"); 
	$("#lbKeywords").removeAttr("disabled"); 
	$("#select3").removeAttr("disabled"); 

	$("#disciplineType").val("");
	$("#testCenterValue").val("");
	$("#lbKeywords").val("");
	$("#select3").val("-1");
	//alert ("afters");
	$("#showTable").hide();
	$("#showTable2").hide();
	
	$("#underLine").hide();
	$("#block20").hide();	
}


function disableOtherFields(){

	$("#disciplineType").attr("disabled", true); 
	$("#testCenterValue").attr("disabled", true); 
	$("#lbKeywords").attr("disabled", true); 
	$("#select3").attr("disabled", true); 
}
		
</script>
 
<div class="container">

<div id="dashboard">

<div class="fade" id="pop3"></div>

<s:form action="CandidateMgmtAction">

<h1 class="pageTitle" title="Email / SMS Configuration">View Registration Details</h1>
<div class="hr-underline2"></div>



<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;">
  <table class="contenttable">
    
    <tr>
     
     	 <td width="295" ><div class="field-label"><strong>User ID</strong></div>
        	<div><s:textfield name="userId" id="userId" onkeypress="return alphaNumeric(event)" onblur="disableOtherFields();"></s:textfield></div>
       	</td>
       	
      	<td width="611" >
      			&nbsp;
      	</td>
      	
    </tr>
    
    <tr>
    
      <td>
      		OR
      </td>
    
      <td >
      		&nbsp;
      </td>
      	
    
    </tr>
    
    
    
    <tr>
    
	      <td><div class="field-label"><strong>Discipline</strong></div>
		        <div><s:select list = "discliplineList" name = "disciplineType" label = "Name" onchange = "disableUserIdField();"  
							headerKey="" headerValue = "All" id = "disciplineType" value="%{disciplineType}"/></div>
		  </td>
		 
	      <td ><div class="field-label"><strong>Test Center</strong></div>
	         <div><s:select list = "testCenterMasterDetails" name = "testCenterValue" label = "Name" onselect = "disableUserIdField()"   
						headerKey="" headerValue = "All" id = "testCenterValue" value="%{testCenterValue}" /></div>
		 </td>
		 
    </tr>
    
    <tr>
      <td><div class="field-label"><strong>Test Date</strong></div>
       <div><s:select list = "testDateListForAdminList" name="testDateForSlot" label = "Name" onselect = "disableUserIdField()"   
						headerKey="" headerValue = "All" id="lbKeywords"  value="%{testDateForSlot}" onchange="getTestSlots();"/>
        <!--<select name="testDateSelected" id="lbKeywords" onchange="getTestSlots();" >
        	<option value="">All</option>
        </select>--></div>
        </td>
      <td ><div class="field-label"><strong>Test Slot</strong></div>
      <div><s:select list = "testSlotListForAdminList" name="testSlotSelected" label = "Name" onchange = "disableUserIdField()"   
						headerKey="" headerValue = "All" id="select3"  value="%{testSlotSelected}" /></div>
        <!--<select name="testSlotSelected" id="select3" >
        	<option value="">All</option>
        </select>-->
        </td>
    </tr>
    <tr>
      <td colspan="2">
   	   	<s:submit value="Search" cssClass ="submitBtn button-gradient" method="getRegistrationDetailsByCandidateId"></s:submit>&nbsp;
        <input type="button" name="button2" id="button2" value="Clear" class="submitBtn button-gradient" onclick="enableUserID();clearFields();"/></td>
      </tr>
    </table>
  <br />
  
  <div  id="block20" style = "display:none;" >
     <div class="hr-underline2"></div>
     <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li>No record Found for <s:property value = "userId"/>.</li> 
    	</ul>
    	
    </div>
<br />
</div>
  
  <s:if test='%{regDtlsDisplayFlag=="true"}'>
  <div class="hr-underline2" id ="underLine"></div>
  <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2" id ="showTable">
    <tr>
      <th width="7%">Sr No.</th>
      <th width="10%">User ID</th>
      <th width="11%">Candidate Name</th>
      <th width="15%">Discipline</th>
      <th width="30%">Test Center</th>
      <th width="13%">Test Date</th>
      <th width="35%">Test Slot</th>
      </tr>
      <%int y=1; %>
      <s:iterator value="candidateDetailsList">
    		<tr>
			      <td align="center"><%=y%></td>
			      <td><s:property value="userId"/></td>
			      <td><s:property value="candidateName"/></td>
			      <td><s:property value="testName"/></td>
			      <td><s:property value="testCenterName"/></td>
			      <td align="center"><s:property value="testDates"/></td>
			      <td align = "center"><s:property value="candidateTestStartTime"/> - <s:property value="candidateTestEndTime"/></td>
      		</tr>
      		<%y++; %>
     </s:iterator> 
   
  </table>
  </s:if>
  <s:if test='%{regDtlsDisplayFlag=="false"}'>
  			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table_2" id ="showTable2">
  					<tr align = "left">
  						<td style = "color:red">
  								No record found
  						</td>
  					</tr>
  			</table>
  </s:if>
</div>
</s:form>		
</div>
</div>