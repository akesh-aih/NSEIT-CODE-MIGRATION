<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <script>
    
    function calcPerc(obtId,outofId,percId){
  	  	var d = $(obtId).val();
  	  	var d1 = $(outofId).val();
  	  	if (d1!="" && d!=""){
  	  		var pec = (d/d1)*100;
 		  	pec = pec.toPrecision(4); 
			$(percId).val(pec)
  	  	}else{
  	  		$(percId).val("")
  	  	}
  }
     function frmSubmit(){
//	document.register.action="CandidateAction_updateCandidateStage.action";
	//document.register.submit();
	$("#register").attr('action','CandidateAction_updateCandidateStage.action');
	$("#register").submit();
}
    	function allFunc(){
   			$("#degreeTypeVal").val($("#degreeType").val());
    	}
	
		
	function enableMarks(obtId,outofId,result,percentageId){
		if (result.checked){
			$(obtId).attr('disabled','disabled');
			$(outofId).attr('disabled','disabled');
			$(obtId).val("");
			$(outofId).val("");
			$(percentageId).val("");
		}else{
			$(obtId).removeAttr('disabled');
			$(outofId).removeAttr('disabled');
		}
	}
 </script>
    <title>My JSP 'showEducationalDetails.jsp' starting page</title>
    
  </head>
  <table width="100%" class="personsl-dtl">
				     <tr>
	        						<td rowspan="2" width="128" align="left" bgcolor="#FFFFFF"><strong>Examination </strong></td>
	        						<td rowspan="2" width="171" align="left" bgcolor="#FFFFFF"><strong>Specialization</strong></td>
	     							 <td rowspan="2" width="171" align="left" bgcolor="#FFFFFF"><strong>Year of Passing</strong></td>
	   							     <td rowspan="2" width="179" align="left" bgcolor="#FFFFFF"><strong>University / Institute</strong></td>
	   							     <td rowspan="2" width="179" align="left" bgcolor="#FFFFFF"><strong>Result Status</strong></td>
	        						<td colspan="3" align="left" bgcolor="#FFFFFF"><strong>Grand Total Marks</strong></td>
       						 </tr>
				    			<tr>
								    <td style="width: 220px;" align="left"><strong>Obtained </strong></td>
									<td style="width: 95px;" align="left"><strong>Out of</strong></td>
									<td style="width: 80px;" align="left"><strong>Percentage</strong></td>
								</tr>
  		<s:iterator value="educationDtlsList" status="stat" var="currentObject">
  			<tr>
  				<td >
  				<s:hidden name="educationDtlsList[%{#stat.index}].degreeSelected" value="%{degreeSelected}" label="degreeSelected" id = "degreeSelectedUser"/>
  						<s:textfield id="outOfTr%{#stat.count}" name="educationDtlsList[%{#stat.index}].examination" value="%{examination}" label="examination" readonly="true"></s:textfield>
  						
  				</td>
  				
  				<td>
  						<s:textfield name="educationDtlsList[%{#stat.index}].specialization" value="%{specialization}" label="specialization" cssClass="small" maxlength="100" onblur = "testFunc(outOfTr%{#stat.count})"></s:textfield>
  				</td>
  				
  				<td>
  						<s:select name="educationDtlsList[%{#stat.index}].yearOfPassing" value="%{yearOfPassing}" label="yearOfPassing" cssClass="small" 
  						list="yearList" headerKey="" headerValue="Select Year" />
  				</td>
  				
  				<td>
  						<s:textfield name="educationDtlsList[%{#stat.index}].university" value="%{university}" label="university" cssClass="small" maxlength="200"></s:textfield>
  				</td>
  				<td>
  						<s:checkbox name="educationDtlsList[%{#stat.index}].resultChkBox" id="resultChkBox%{#stat.count}" value="true" onclick="enableMarks(obtndMarks%{#stat.count},outOfMarks%{#stat.count},resultChkBox%{#stat.count},percentage%{#stat.count});" ></s:checkbox>
  						
  					<s:if test="#stat.obtndMarks==1">
    					inject d...
					</s:if>	
  				</td>
  				
  				<td>
  						<s:textfield name="educationDtlsList[%{#stat.index}].obtndMarks" value="%{obtndMarks}" label="obtndMarks" onkeypress="return numbersonly(event);" cssClass="small" id="obtndMarks%{#stat.count}" onblur="calcPerc(obtndMarks%{#stat.count},outOfMarks%{#stat.count},percentage%{#stat.count});" maxlength="5"></s:textfield>
  				</td>
  				
  				<td>
  						<s:textfield name="educationDtlsList[%{#stat.index}].outOfMarks" value="%{outOfMarks}" label="outOfMarks" cssClass="small"  onkeypress="return numbersonly(event);"  id="outOfMarks%{#stat.count}" onblur="calcPerc(obtndMarks%{#stat.count},outOfMarks%{#stat.count},percentage%{#stat.count});"  maxlength="5"></s:textfield>
  				</td>
  				
  				<td>
  						<s:textfield name="educationDtlsList[%{#stat.index}].percentage" value="%{percentage}" label="percentage" cssClass="small" id="percentage%{#stat.count}" readonly="true"></s:textfield>
  				</td>
  			</tr>
  		</s:iterator>
  
  </table>

</html>
