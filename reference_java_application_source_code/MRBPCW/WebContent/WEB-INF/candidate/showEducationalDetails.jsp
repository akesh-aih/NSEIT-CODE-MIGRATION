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
 		  	pec = pec.toFixed(2); 
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
  
  <s:form action="CandidateAction" name="register" id="register">
  <s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
  <s:hidden name="testFlag" value='<s:property value="testFlag"/>'/>
  
  	<!--<input type = "hidden" name="degreeTypeVal" id="degreeTypeVal"/>
  	--><s:hidden name ="degreeTypeVal" id ="degreeTypeVal" value="56"/>
  <table width="100%" class="personsl-dtl">
				     <tr>
	        						<th rowspan="2" width="140" align="left" bgcolor="#FFFFFF"><strong><s:text name="academicDetails.examination"/></strong></th>
	        						<th rowspan="2" width="140" align="left" bgcolor="#FFFFFF"><strong><s:text name="academicDetails.certificateNumber"/></strong></th>
	     							 <th rowspan="2" width="230" align="left" bgcolor="#FFFFFF"><strong><s:text name="academicDetails.monthandYearofPassing"/></strong></th>
	   							     <th rowspan="2" width="140" align="left" bgcolor="#FFFFFF"><strong><s:text name="academicDetails.borad"/></strong></th>
	   							     <th style="display:none;" rowspan="2" width="179" align="left" bgcolor="#FFFFFF"><strong>Result Awaited</strong></th>
	        						<th colspan="3" align="center" bgcolor="#FFFFFF"><strong><s:text name="academicDetails.totalmarks"/></strong></th>
	        							<th rowspan="2" width="120" align="left" bgcolor="#FFFFFF"><strong><s:text name="academicDetails.medium"/></strong></th>	
       						 </tr>
				    			<tr>
								    <th style="width: 80px;" align="left"><strong><s:text name="academicDetails.obtained"/> </strong></th>
									<th style="width: 80px;" align="left"><strong><s:text name="academicDetails.outOf"/></strong></th>
									<th style="width: 80px;" align="left"><strong><s:text name="academicDetails.percentage"/></strong></th>
								</tr>
  		<s:iterator value="educationDtlsList" status="stat" var="currentObject">
  			<tr>
  				<td >
  				<s:hidden name="educationDtlsList[%{#stat.index}].degreeSelected" value="%{degreeSelected}" label="degreeSelected" id = "degreeSelectedUser"/>
  						<s:hidden id="outOfTr%{#stat.count}" name="educationDtlsList[%{#stat.index}].examination" cssClass="small" value="%{examination}" label="examination" readonly="true"></s:hidden>
  						<s:label value="%{examination}"></s:label><s:if test='%{educationDtlsList[#stat.index].mandatory == "Y"}'><span class="manadetory-fields">*</span></s:if>
  				</td>
  				
  				<td>
  						<s:textfield name="educationDtlsList[%{#stat.index}].specialization" value="%{specialization}" label="specialization" cssClass="tb_big" maxlength="100" onblur = "testFunc(outOfTr%{#stat.count})" onkeypress="return educationValidator(event);"></s:textfield>
  				</td>
  				
  				<td>
  						<s:select name="educationDtlsList[%{#stat.index}].monthOfPassing" id="outOfTr%{#stat.count}" value="%{monthOfPassing}" label="monthOfPassing" cssClass="smallb" 
  						list="monthList" headerKey="" headerValue="MM" />
  						<s:select name="educationDtlsList[%{#stat.index}].yearOfPassing" value="%{yearOfPassing}" label="yearOfPassing" cssClass="smallb" 
  						list="yearList" headerKey="" headerValue="YYYY" />
  				</td>
  				
  				<td>
  						<s:textfield name="educationDtlsList[%{#stat.index}].university" value="%{university}" label="university" cssClass="tb_big" maxlength="200" onkeypress="return educationValidator(event);"></s:textfield>
  				</td>
  				<td style="display:none;">
  						<s:checkbox name="educationDtlsList[%{#stat.index}].resultChkBox" id="resultChkBox%{#stat.count}" onclick="enableMarks(obtndMarks%{#stat.count},outOfMarks%{#stat.count},resultChkBox%{#stat.count},percentage%{#stat.count});"></s:checkbox>
  				</td>
  				
  				<td>
  						<s:textfield name="educationDtlsList[%{#stat.index}].obtndMarks" value="%{obtndMarks}" label="obtndMarks" disabled="%{resultChkBox}" onkeypress="return numbersonly(event);" cssClass="smallb" id="obtndMarks%{#stat.count}" onblur="calcPerc(obtndMarks%{#stat.count},outOfMarks%{#stat.count},percentage%{#stat.count});" maxlength="5" onpaste="return false;"></s:textfield>
  				</td>
  				
  				<td>
  						<s:textfield name="educationDtlsList[%{#stat.index}].outOfMarks" value="%{outOfMarks}" label="outOfMarks" disabled="%{resultChkBox}" cssClass="smallb"  onkeypress="return numbersonly(event);"  id="outOfMarks%{#stat.count}" onblur="calcPerc(obtndMarks%{#stat.count},outOfMarks%{#stat.count},percentage%{#stat.count});"  maxlength="5" onpaste="return false;"></s:textfield>
  				</td>
  				
  				<td>
  						<s:textfield name="educationDtlsList[%{#stat.index}].percentage" value="%{percentage}" label="percentage" cssClass="smallb" id="percentage%{#stat.count}" readonly="true"></s:textfield>
  				</td>
  				<td>
  						<s:select name="educationDtlsList[%{#stat.index}].partTimeFullTimeSelected" value="%{partTimeFullTimeSelected}" label="partTimeFullTimeSelected" cssClass="small" 
  						list="partTimeFullTimeList" headerKey="" headerValue="Select Type" />
  						<s:hidden name="educationDtlsList[%{#stat.index}].mandatory" value="%{mandatory}"></s:hidden>
  				</td>
  			</tr>
  		</s:iterator>
  
  </table>
  <s:if test='%{postGraduationActive}'>
  	<br/>
  	<br/>
	    <h1 class="pageTitle" title="Dashboard">Post Graduation Details</h1>
		<div class="hr-underline2"></div>
		<table>
				<tr>
					<td width="170">
						Post Graduate
					</td>
					<td width="750">
						<s:select name="postGraduateValue" headerKey="" headerValue="Select Degree" list="postGraduateMasterMap"  id="postGraduateValue" cssClass="s" value="%{postGraduateValue}" onchange = "disableField();"/>
					</td>
					</tr>
					
					<tr>
						<td>
							If Other,Please specify 
						</td>
						<td>
							<s:textfield name = "educationDetailsBean.degreeOther" id = "degreeOther" disabled="true"/>
							<br /><br />
						</td>
					</tr>
					
					<tr>
						<td colspan="2">
							<table width="900" class="personsl-dtl" id="postGraduationDetails">
							  <tr>
	        						<th rowspan="2" width="128" align="left" bgcolor="#FFFFFF"><strong>Examination </strong></th>
	        						<th rowspan="2" width="171" align="left" bgcolor="#FFFFFF"><strong>Specialization</strong></th>
	     							 <th rowspan="2" width="171" align="left" bgcolor="#FFFFFF"><strong>Year of Passing</strong></th>
	   							     <th rowspan="2" width="179" align="left" bgcolor="#FFFFFF"><strong>University / Institute</strong></th>	        						
	   							     <th colspan="3" align="left" bgcolor="#FFFFFF"><strong>Grand Total Marks</strong></td>
	   							     <th rowspan="2" width="179" align="left" bgcolor="#FFFFFF"><strong>Full Time / Part Time</strong></th>
	   							     
       						 </tr>
				    			<tr>
								    <th style="width: 220px;" align="left"><strong>Obtained </strong></th>
									<th style="width: 95px;" align="left"><strong>Out of</strong></th>
									<th style="width: 80px;" align="left"><strong>Percentage</strong></th>
								</tr>
								<tr>
									<td>
										<s:label value = "Post Graduate"/>
									</td>
									<td>
										<s:textfield name="educationDetailsBean.specialization" onkeypress="return educationValidator(event);"/>
									</td>
									<td>
					  						<s:select name="educationDetailsBean.yearOfPassing" value="%{educationDetailsBean.yearOfPassing}" label="yearOfPassing" cssClass="small" 
					  						list="yearList" headerKey="" headerValue="Select Year" />
					  				</td>
					  				
					  				<td>
					  						<s:textfield name="educationDetailsBean.university"  label="university" cssClass="small" maxlength="200" onkeypress="return educationValidator(event);"></s:textfield>
					  				</td>
					  				
					  				<td>
					  						<s:textfield name="educationDetailsBean.obtndMarks"  id="obtndMarks" onkeypress="return numbersonly(event);" cssClass="small" maxlength="5" onblur ="calcPerc(obtndMarks,outOfMarks,percentage)" onpaste="return false;"></s:textfield>
					  				</td>
					  				
					  				<td>
					  						<s:textfield name="educationDetailsBean.outOfMarks" id="outOfMarks" cssClass="small" onkeypress="return numbersonly(event);"   maxlength="5" onblur ="calcPerc(obtndMarks,outOfMarks,percentage)" onpaste="return false;"></s:textfield>
					  				</td>
					  				
					  				<td>
					  					<s:textfield name="educationDetailsBean.percentage" id = "percentage" cssClass="small" readonly="true"   maxlength="5"></s:textfield>	
					  				</td>
					  				<td>
  						<s:select name="educationDetailsBean.partTimeFullTimeSelected" value="%{educationDetailsBean.partTimeFullTimeSelected}" label="partTimeFullTimeSelected" cssClass="small" 
  						list="partTimeFullTimeList" headerKey="" headerValue="Select Type" />
  				</td>
								</tr>
								
							</table>
						</td>
						
					</tr>
		
		</table>
  </s:if>
  <div align="center" style="padding: 10px;">
  	<s:submit value="Save" cssClass="submitBtn button-gradient" method="saveAcademicDetails" id="saveBtnValue" onclick ="allFunc();"></s:submit>
   </div>
  </s:form>
</html>
