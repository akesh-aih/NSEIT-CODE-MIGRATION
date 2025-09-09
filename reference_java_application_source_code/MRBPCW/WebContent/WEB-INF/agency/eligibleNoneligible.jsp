<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">

$(document).ready(function(){

	$("#error-massage").hide();
	hideAllDiv();
	
	var r = $("#approveFlag").val();
	
	if (r=="true"){
	//hideAllDiv();
		$("#block10").show();
	}
	
	var stageOneFlag  = $("#stageOneFlag").val();
	if (stageOneFlag == "true"){
	
	hideAllDiv();
		$("#block20").show();
	}
	
	
	var approveFlagForView  = $("#approveFlagForView").val();
	if (approveFlagForView == "true"){
//	hideAllDiv();
		//$("#block25").show();
	}
	
	var approveRejectFlag  = $("#approveRejectFlag").val();
	if (approveRejectFlag == "true"){
//	hideAllDiv();
		$("#block23").show();
	}
	
	
	
	var rejectFlagForView  = $("#rejectFlagForView").val();
	if (rejectFlagForView == "true"){
	hideAllDiv();
		$("#block9").show();
	}
	
	var nodalRejectFlag  = $("#nodalRejectFlag").val();
	if (nodalRejectFlag == "true"){
	hideAllDiv();
		$("#block30").show();
	}
	
	
	var attmpt1Flag  = $("#attmpt1Flag").val();
	if (attmpt1Flag == "true"){
	//hideAllDiv();
		$("#block21").show();
	}
	
	
	var attmpt2Flag  = $("#attmpt2Flag").val();
	//hideAllDiv();
	if (attmpt2Flag == "true"){
		$("#block22").show();
	}
	
	var rejectFlag  = $("#rejectFlag").val();
	if (rejectFlag == "true"){
		$("#block9").show();
	}
	
	
	

	$("#id").attr ("src","PhotoImage.jpg?userFk="+$("#test").val());
});



	function hideDiv (id){
		$("#"+id).hide();
	}

	function hideAllDiv(){
		$("#block9").hide();
		$("#block10").hide();
		$("#block20").hide();
		$("#block21").hide();
		$("#block22").hide();
		$("#block23").hide();
		$("#block30").hide();
	}

function changeAction(){
	var msg = validateUserId();
	
	if (msg){
		$("#eligible").attr('action',"CandidateAction_getCandidateDetailsForAdmin.action");
		$("#eligible").submit();
	}
}

function callFunction(){
		$("#userId").val($("#userId").val());
		$("#eligible").attr('action',"CandidateAction_approvalReceipt.action");
		$("#eligible").submit();
}

function setUserId(){

	$("#userPk").val($("#test").val());
}


function validateInputForSearch() {
	var message = "";
	
	var userId = $("#userId").val();
	var remarksForCandidate = $("#remarksForCandidate").val();
	
	if (userId ==""){
		message = message + "Please enter Candidate Id."+"##";
	}
	if (remarksForCandidate ==""){
		message = message + "Please enter Remarks for Rejection ."+"##";
	}
	
	if (userId !="" && remarksForCandidate !=""){
		$("#error-massage").hide();
	}
	
	
	if(message != ""){
			var ulID = "errorMessages";
			var divID = "error-massage";
			createErrorList(message, ulID, divID);

			return false;
		}
		else{
			return true;
		}
}  


function approveCandidate() {
		$("#userPk").val($("#test").val());
	$("#eligible").attr('action',"CandidateAction_approveCandidate.action");
	$("#eligible").submit();
	
}

function rejectCandidate() {
		$("#userPk").val($("#test").val());
	var flag = validateInputForSearch();
	if (flag == true){
		$("#eligible").attr('action',"CandidateAction_rejectCandidate.action");
		$("#eligible").submit();
	}else{
				$("#error-massage").focus();
				$('html, body').animate({ scrollTop: 0 }, 0);
	}
}
	function validateUserId(){
	var userId = $("#userId").val();
	var message = "";
	if (userId ==""){
		message = message + "Please enter Candidate Id."+"##";
	}

	
	if(message != ""){
		$("#showDiv").hide();
			var ulID = "errorMessages";
			var divID = "error-massage";
			createErrorList(message, ulID, divID); 
			return false;
		}
		else{
			return true;
		}
}

function clearFields(){
	$("#userId").val("");
	$("#block9").hide();
	$("#block10").hide();
	$("#block20").hide();
	$("#block22").hide();
	$("#block25").hide();
	$("#block21").hide();
	$("#block23").hide();
	$("#block30").hide();
	$("#error-massage").hide();
	$(".hr-underline2").hide();
	$("#showDiv").hide();
	

}
</script>

<div class="container">
<s:form action="CandidateMgmtAction" id="eligible">

<s:hidden name = "rejectFlagForView" id = "rejectFlagForView"></s:hidden>
<s:hidden name = "approveRejectFlag" id = "approveRejectFlag"></s:hidden>
<s:hidden name = "approveFlag" id = "approveFlag"></s:hidden>
<s:hidden name = "stageOneFlag" id = "stageOneFlag"></s:hidden>
<s:hidden name = "attmpt1Flag" id = "attmpt1Flag"></s:hidden>
<s:hidden name = "attmpt2Flag" id = "attmpt2Flag"></s:hidden>
<s:hidden name = "rejectFlag" id = "rejectFlag"></s:hidden>
<s:hidden name = "approveFlagForView" id = "approveFlagForView"></s:hidden>
<s:hidden name = "nodalRejectFlag" id = "nodalRejectFlag"></s:hidden>


<s:hidden name="userPrimaryKey" id="userPk"></s:hidden>
<s:hidden name="userName" id="userName"></s:hidden>
<input type="hidden" name = "userIdForImage" value="<s:property value = "userIdForImage"/>" id = "test"/>
<div class="fade" id="block7"></div>

<div class="fade" id="pop3"></div>
<div id="dashboard" style="display:block; min-height:300px; height:auto;">

<h1 class="pageTitle" title="Dashboard">Approve / Reject Candidate</h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;" >
  <table class="contenttable">
  <tr><td colspan="3">
  <div id="error-massage" style="display:none">
    <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="errorMessages">
    	</ul>
    </div>
</div>
  
  </td></tr>
    <tr>
	      <td width="226">
	      		User ID <span class="manadetory-fields">*</span>
	      </td>
	      
	      <td width="662" colspan="2">
	      <s:textfield name="userId" id="userId" onkeypress="return alphaNumeric(event)" ></s:textfield>
	      </td>
    </tr>
    
    <tr>
    	  <td>
    	  		&nbsp;
    	  </td>
    	  
      	  <td colspan="2">
      	  <input type="button" value="Search" class="submitBtn button-gradient" onclick="changeAction();"/>&nbsp;
      	  <input type="button" value="Clear" class="submitBtn button-gradient" onclick="clearFields();"/>
      	  </td>
      	  
      	  <s:actionmessage/>
    </tr>
    
   
    </table>
    
    
   
    
    <div  id="block20" style = "display:none;" >
   <div class="hr-underline2"></div>
     <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li>No record Found for <s:property value = "userId"/>.</li> 
    	</ul>
    	
    </div>
<br />
</div>

<div id="block21" style = "display:none;">
<div class="hr-underline2"></div>
<div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li><s:property value = "userId"/> is already been approved 3.</li> 
    	</ul>
    	
    </div>
</div>

<div id="block9" style = "display:none;">
<div class="hr-underline2"></div>
	<div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li> <s:property value = "userId"/> is rejected. </li> 
    	</ul>
    	
    </div>
</div>


<div id="block30" style = "display:none;">
<div class="hr-underline2"></div>
	<div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li> Please contact Central Office - Nodal Officer for further change . </li> 
    	</ul>
    	
    </div>
</div>


<div id="block23" style = "display:none;">
<div class="hr-underline2"></div>
 <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li> <s:property value = "userId"/>   is  approved.</li> 
    	</ul>
    	
    </div>
<br />
</div>


<div id="block25" style = "display:none;">
<div class="hr-underline2"></div>
 <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li> <s:property value = "userId"/> is already been approved 1.</li> 
    	</ul>
    	
    </div>
<br />
</div>

<div id="block22" style = "display:none;">
<div class="hr-underline2"></div>
<div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li> Please contact Central Office - Nodal Officer for further change .</li>
			     
    	</ul>
    </div>
<br />
    	 <a href="#" onclick="callFunction();">View Acknowledgement Receipt</a>
</div>

    
    <s:if test='%{flag=="true"}'><br/>
    <div class="hr-underline2"></div>
    	<div style="display:block; min-height:560px; height:auto;" id="showDiv">
					  <table class="contenttable">
					  
						    <tr>
							      <td width="286">Name
							      </td>
							      
						    	  <td >
							    	  	<strong>
							    	  			<s:label value="%{personalDetailsBean.candidateName}"></s:label>
							    	  			
							    	  	</strong>
						      	  </td>
						      	  
						      	  <td align="right" rowspan="5">
						      	  		<img id = "id" width="110" height="150"></img>
						      	  </td>
						    </tr>
						    <tr>
							      <td>Date of Birth </td>
							      <td >
							      	<strong><s:label value="%{personalDetailsBean.dateOfBirth}" id = "dateOfBirth"/></strong>
								  </td>
						    </tr>
					    	<tr>
						    	  <td>Nationality</td>
						      	  <td >
						      	  	<strong><s:label value="%{nationalityDesc}" id = "nationality"/></strong>
						      	  </td>
						    </tr>
					    	<tr>
							      <td>Gender</td>
							      <td>
							      	<strong><s:label value="%{genderValDesc}" id = "gender"/></strong>
								  </td>
						    </tr>
					    	<tr>
						    	  <td>Permanent Address </td>
							      <td>
							      	<strong><s:label value="%{addressBean.addressFiled1}" id = "addressFiled1"/></strong>
							      </td>
						    </tr>
					    	<tr>
							      <td>&nbsp;</td>
							      <td colspan="2">
							      	<strong><s:label value="%{addressBean.addressFiled2}" id = "addressFiled2"/></strong>
							      </td>
						    </tr>
					    	<tr>
							      <td>&nbsp;</td>
							   	  <td colspan="2">
							      	<strong><s:label value="%{addressBean.addressFiled3}" id = "addressFiled3"/></strong>
							      </td>
						    </tr>
					    	<tr>
							      <td>&nbsp;</td>
							      <td colspan="2">
							      	<strong><s:label value="%{addressBean.addressFiled4}" id = "addressFiled4"/></strong>
							      </td>
					    	</tr>
					    	<tr>
							      <td>&nbsp;</td>
							  	  <td width="227"><span class="lighttext">City / Town</span>
							       	
							        <strong><s:label value="%{addressBean.cityName}" id = "cityName"/></strong>
							      </td>
							      <td width="371"><span class="lighttext">Pincode</span>
							      	<strong><s:label value="%{addressBean.pinCode}" id = "pinCode"/></strong>   
							      </td>
							      
							     
							</tr>
					    
					<!-- For other countries -->    
						    <tr style="display:none;" id="otherStateVal">
						    	<td>
						    		&nbsp;
						    	</td>
						    	<td>
						    		<span class="lighttext">State</span><br />
						    		<strong><s:label value="%{addressBean.otherStateFiled}" id = "otherStateFiled"/></strong>  
							    </td>
						    </tr>
					    	<tr id = "stateDropdown">
							      <td>&nbsp;</td>
							      <td>
							      		<span class="lighttext">State / Union Territory</span>
							      		<strong><s:label value="%{stateValDesc}" id = "stateVal"/></strong>
										&nbsp; 
							      </td>
							</tr>
							<tr>
								<td></td>
								<s:if test='%{talukaFlag == "false"}'>
								 	<td width="371"><span class="lighttext">Taluka</span> <br />
								      		<strong><s:label value="%{talukaValDesc}" id = "alternateTaluka"/></strong>   
								      </td>
							     </s:if>
							      
							      <s:if test='%{talukaFlag == "true"}'>
									      <td ><span class="lighttext">Taluka</span> <br />
										      	<strong><s:label value="%{talukaField}" id = "taluka"/></strong>   
									 	  </td>
								 </s:if>
							 </tr>
					    
						    <tr id="otherDistrict" style="display:none;">
						    	<td>
						    		&nbsp;
						    	</td>
						    	<td>
						    		<span class="lighttext">District</span>
						    		<strong><s:label value="%{addressBean.otherDistrictField}" id = "otherDistrictField"/></strong>
						    	</td>
						    </tr>
					    	<tr id="districtId">
								 <td>&nbsp;</td>
								 <td colspan="2"><span class="lighttext">District</span>
								     <strong><s:label value="%{districtValDesc}" id = "districtVal"/></strong>
								</td>
						    </tr>
					    	<tr>
							      <td>&nbsp;</td>
							      <td colspan="2"><span class="lighttext">Country</span>
							      	<strong><s:label value="%{countryValDesc}" id = "countryVal"/></strong>
								  </td>
						    </tr>
					    	<tr>
							      <td rowspan="2">Communication Address  <br /></td>
							      <td colspan="2">
							      	<strong><s:label value="%{addressBean.alternateAddressFiled1}" id = "alternateAddressFiled1"/></strong>
						      	  </td>
						    </tr>
					    	<tr>
						    	  <td colspan="2">
						    	  	<strong><s:label value="%{addressBean.alternateAddressFiled2}" id = "alternateAddressFiled2"/></strong>
						      	  </td>
						    </tr>
					    	<tr>
							      <td>&nbsp;</td>
							      <td colspan="2">
							 		<strong><s:label value="%{addressBean.alternateAddressFiled3}" id = "alternateAddressFiled3"/></strong>
							      </td>
						    </tr>
					    	<tr>
							      <td>&nbsp;</td>
							      <td colspan="2">
									<strong><s:label value="%{addressBean.alternateAddressFiled4}" id = "alternateAddressFiled4"/></strong>
							      </td>
						    </tr>
					    	<tr>
							      <td>&nbsp;</td>
							      <td><span class="lighttext">City / Town</span>  
							      	<strong><s:label value="%{addressBean.alternateCity}" id = "alternateCity"/></strong>
							      </td>
							      <td>
							      	<span class="lighttext">Pincode</span> 
							      	<strong><s:label value="%{addressBean.alternatePinCode}" id = "alternatePinCode"/></strong>
							      </td>
							      
							       
						    </tr>
					    	<tr id = "alternateStateFieldDisplay" style="display:none;">
						    	<td>
						    		&nbsp;
						    	</td>
						    	<td>
						    		<span class="lighttext">State</span>
						    		<strong><s:label value="%{addressBean.otherAlternateStateField}" id = "otherAlternateStateField"/></strong>
						    	</td>
						    </tr>
					    	<tr id = "alternateStateDisplay">
						     	 <td>&nbsp;</td>
						      	<td>
						      		<span class="lighttext">State / Union Territory </span>
						      		<strong><s:label value="%{altStateValDesc}" id = "altStateVal"/></strong>
						        	&nbsp; 
						        </td>
						    </tr>
						    
						    <tr>
								<td></td>
								<s:if test='%{altTalukaFlag == "false"}'>
							 	<td width="371"><span class="lighttext">Taluka</span> <br />
							      		<strong><s:label value="%{altTalukaValDesc}" id = "alternateTaluka"/></strong>   
							      </td>
							      </s:if>
							      
							      <s:if test='%{altTalukaFlag == "true"}'>
							      <td ><span class="lighttext">Taluka</span> <br />
								      	<strong><s:label value="%{altTalukaField}" id = "taluka"/></strong>
								      	   
							 </td>
							 </s:if>
							 </tr>
					    	<tr id = "alternateDistrictFieldDisplay" style="display:none;">
						    	<td>
						    		&nbsp;
						    	</td>
						    	<td>
						    		<span class="lighttext">District</span>
						    		<strong><s:label value="%{addressBean.otherAlternateDistrictField}" id = "otherAlternateDistrictField"/></strong>
						    	</td>
						    </tr>
					    	<tr id = "alternateDistrictDisplay">
						      	<td>&nbsp;</td>
						      	<td colspan="2"><span class="lighttext">District</span>
							  		<strong><s:label value="%{altDistrictValDesc}" id = "altDistrictVal"/></strong>
							  	</td>
							</tr>
					    	<tr>
							      <td>&nbsp;</td>
								  <td colspan="2"><span class="lighttext">Country</span>
								  	<strong><s:label value="%{altCountryValDesc}" id = "altCountryVal"/></strong>
							      </td>
						    </tr>
					    	<tr>
							      <td>Email Id</td>
							      <td colspan="2">
							      	<strong><s:label value="%{personalDetailsBean.email}" id = "email"/></strong>
							      </td>
						    </tr>
					    	<tr>
						    	  <td>Mobile Number</td>
							      <td colspan="2">
							      	<strong><s:label value="%{personalDetailsBean.mobileNo}" id = "mobileNo"/></strong>
							      </td>
						    </tr>
					    	<tr>
							      <td>Alternate Number</td>
							      <td colspan="2">
							      	<strong><s:label value="%{personalDetailsBean.isdNo}" id = "conutryCode"/></strong>
							      	-
							      	<strong><s:label value="%{personalDetailsBean.stdCode}" id = "stdCode"/></strong>
							      	-
							      	<strong><s:label value="%{personalDetailsBean.phoneNo}" id = "phoneNo"/></strong>
								  </td>
						    </tr>
					    	<tr>
						    	  <td>Category Information</td>
							      <td colspan="2">
							      	<strong><s:label value="%{categoryValDesc}" id = "categoryVal"/></strong>
								  </td>
							</tr>
						    <tr>
							      <td>If any other Category mention here</td>
							      <td colspan="2">
							      	<strong><s:label value="%{personalDetailsBean.category}" id = "category"/></strong>
							      </td>
						    </tr>
					    	<tr>
						     	 <td>Are you Physically Handicapped?</td>
						      	 <td colspan="2">
						      	 	<strong><s:label value="%{personalDetailsBean.handicapped}" id = "handicapped"/></strong>
						      	 </td>
						    </tr>
						     <s:if test='%{personalDetailsBean.handicapped == "Yes"}'> 
								     <tr>
								      <td>&nbsp;</td>
								      <!--<td colspan="2"><span class="lighttext">Percentage of Disability (more than 40%)</span>
								      	<strong>
									        	<s:label id="handicappedPercentage" name="personalDetailsBean.handicappedPercentage"></s:label>
								        </strong>	
								       </td>
									--></tr>
						     </s:if>
						     
					 <tr>
				     	 <td>Do you belong to BPL (Below Poverty Line) card holder family?</td>
				      	 <td colspan="2">
				      	 	<strong><s:label value="%{personalDetailsBean.povertyDesc}" id = "poverty"/></strong>
				      	 </td>
					</tr>
					
					  </table>
					<br/>
					<br/>
					
					<h1 class="pageTitle" title="Dashboard">Education Details</h1>
					<div class="hr-underline2"></div>
					<table class="contenttable">
					  <tr>
						    <td >Is your bachelor's Degree under minimum of </td>
						    <td>
						    	<strong>
						    		<s:label value="%{educationDetailsBean.degreeTypeDesc}" id = "degreeType"/>
						    	</strong>
						    </td>
					  </tr>
					  <tr>
					   	 <td width="286">Bachelor's Degree <span class="lighttext"></span>
					   	 </td>
					     <td width="602">
					     	<strong><s:label value="%{educationDetailsBean.degreeDesc}" id = "academic"/></strong>
					     </td>
					  </tr>
					  <tr>
					  	<td>If Other, Please Specify</td>
					    <td>
					    	<strong><s:label value="%{educationDetailsBean.degreeOther}" id = "degreeOther"/></strong>
					    </td>
					  </tr>
					  <tr>
					    <td>Result of Graduation </td>
					    <td colspan="2">
					    	<strong><s:label value="%{educationDetailsBean.resultOfGraduationDesc}" id = "resultOfGraduation"/></strong>
					    </td>
					  </tr>
					  <tr>
						    <td>From where you have graduated? </td>
						    <td colspan="2">
						    	<strong><s:label value="%{educationDetailsBean.graduationFromUnivDesc}" id = "graduationFromUniv"/></strong>
						    </td>
					  </tr>
					  
					  <tr>
  	  <td colspan="3"><table width="900" class="personsl-dtl" >
    	  <tr>
	        <td rowspan="2" width="128" align="left" bgcolor="#FFFFFF"><strong>Examination </strong></td>
	        <td rowspan="2" width="171" align="left" bgcolor="#FFFFFF"><strong>Year of Passing</strong></td>
	        <td rowspan="2" width="179" align="left" bgcolor="#FFFFFF"><strong>University / Institute</strong></td>
	        <td colspan="3" align="left" bgcolor="#FFFFFF"><strong>Grand Total Marks</strong></td>
	        <td rowspan="2" width="87" align="left" bgcolor="#FFFFFF"><strong>SPI</strong></td>
	        <td rowspan="2" width="84" align="left" bgcolor="#FFFFFF"><strong>CPI</strong></td>
        </tr>
        <tr>
	        <td align="left" bgcolor="#FFFFFF"><strong>Obtained</strong></td>
	        <td  align="left" bgcolor="#FFFFFF"><strong>Out Of</strong></td>
	        <td  align="left" bgcolor="#FFFFFF"><strong>Percentage</strong></td>
	        
        </tr>
        
        <!-- start of iterator -->
            	
            	<tr>
	        		<td width="128">
		        		SSC / 10th
		        	</td>
		        	
		        	<td width="171">
		        		<strong>
			        		<s:label value="%{academicDetailsBean.sscYearOfPassing}" />
			        	</strong>
		        	</td>	
		        	
	        		<td width="179">
	        			<strong>
		        			<s:label value="%{academicDetailsBean.sscUniversity}" />
		        		</strong>
		        	</td>
		        	
		        	<td>
		        		<strong>
		        			<s:label value="%{academicDetailsBean.sscObtainedMarks}" />
		        		</strong>
		        	</td>
		        	
		        	<td>
		        		<strong>
		        			<s:label value="%{academicDetailsBean.sscMaxMarks}" />
		        		</strong>
		        	</td>
		        	
		        	<td>
		        		<strong>
		        			<s:label value="%{academicDetailsBean.sscPercentage}" />
		        		</strong>
		        	</td>
		        	
		        	<td width="87">
		        		&nbsp;
		        	</td>	
		        	
		        	<td width="84">
		        		&nbsp;
		        	</td>		
	        	</tr>
	    
	    		
            	<tr>
	        		<td width="128">
		        		HSC / 12th / DIPLOMA
		        	</td>
		        	
		        	<td width="171">
		        		<strong>
			        		<s:label value="%{academicDetailsBean.hscYearOfPassing}" />
			        	</strong>
		        	</td>	
		        	
	        		<td width="179">
	        			<strong>
		        			<s:label value="%{academicDetailsBean.hscUniversity}" />
		        		</strong>
		        	</td>
		        	
		        	<td>
		        		<strong>
		        			<s:label value="%{academicDetailsBean.hscObtainedMarks}" />
		        		</strong>
		        	</td>
		        	
		        	<td>
		        		<strong>
		        			<s:label value="%{academicDetailsBean.hscMaxMarks}" />
		        		</strong>
		        	</td>
		        	
		        	<td>
		        		<strong>
		        			<s:label value="%{academicDetailsBean.hscPercentage}" />
		        		</strong>
		        	</td>
		        	
		        	<td width="87">
		        		&nbsp;
		        	</td>	
		        	
		        	<td width="84">
		        		&nbsp;
		        	</td>		
	        	</tr>
	    
	    		
            	<tr>
	        		<td width="128">
		        		Graduation
		        	</td>
		        	
		        	<td width="171">
		        		<strong>
			        		<s:label value="%{academicDetailsBean.beYearOfPassing}" />
			        	</strong>
		        	</td>	
		        	
	        		<td width="179">
	        			<strong>
		        			<s:label value="%{academicDetailsBean.beUniversity}" />
		        		</strong>
		        	</td>
		        	
		        	<td>
		        		<strong>
		        			<s:label value="%{academicDetailsBean.beObtainedMarks}" />
		        		</strong>
		        	</td>
		        	
		        	<td>
		        		<strong>
		        			<s:label value="%{academicDetailsBean.beMaxMarks}" />
		        		</strong>
		        	</td>
		        	
		        	<td>
		        		<strong>
		        			<s:label value="%{academicDetailsBean.bePercentage}" />
		        		</strong>
		        	</td>
		        	
		        	<td width="87">
		        		<strong>
		        			<s:label value="%{academicDetailsBean.beSPI}" />
		        		</strong>
		        	</td>	
		        	
		        	<td width="84">
		        		<strong>
		        			<s:label value="%{academicDetailsBean.beCPI}" />
		        		</strong>
		        	</td>		
	        	</tr>
	    
      <tr>
        <td colspan="3">
        	Incase of SPI and CPI, mention base information
        </td>
        
        <td colspan="5">
        	<strong>
        		<s:label value="%{academicDetailsBean.baseSpiCpiInfo}" />
        	</strong>
        </td>
        
        </tr>
      </table>
    </td>
  </tr>
					  
					  <tr>
					    <td>Help Centre Name</td>
					    	<td colspan="2">
					    		<strong>
					    			<s:label value="%{HelpCenterDesc}" id = "helpCenter"/>
					    		</strong>
					       </td>
					  </tr>

					  <tr>
						   <td colspan="3">&nbsp;
						   </td>
					   </tr>

						<tr>
							<td colspan="3">
								<strong>Remark</strong>
							</td>
						
						</tr>					   
					   <tr>
					   		<td colspan="3">
					   			<s:textarea name="remarksForCandidate" id="remarksForCandidate" cssStyle="resize:none; width:890px; height:100px;"></s:textarea>
					   		</td>
					   </tr>
					   
					   <tr>
					   <s:if test='%{approveFlagForView=="true"}'>
					   <td colspan="3" align="center">
      							<input type="button" value="Approve" class="submitBtn button-gradient" onclick="approveCandidate();"/>
       							&nbsp; &nbsp; &nbsp;
       							<input type="button" value="Reject" class="submitBtn button-gradient" onclick="rejectCandidate();"/>
       						</td>
					   </s:if>
					   <s:else>
					   		
       						</s:else>
					   
					   </tr>
					 
					  </table>
					  </div>
			</s:if>
				</div>
</div>


</s:form>

<div class="fade" id="block7"></div>

</div>

<div class="forgot-pass box-gradient" id="block10" style = "display:none;">
<div><a href="javascript:void(0);" onclick="hideDiv('block10');"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox fl-left"><h1 class="pageTitle" title="Login">&nbsp;Message</h1></div>
<div class="closebtnBox fl-rigt"></div>
<div class="hr-underline clear"></div>
<div style="text-align:center" ><br/>
    <strong>Candidate  <s:property value = "userId"/>  is eligible to book a seat for exam </strong>
<br />
<br />
    
 <br />
<br />
    
<input type="button" value="Close" class="submitBtn button-gradient" title="Close" onclick="hideDiv('block10');"/>
</div>
</div>
</div>
<!-- PopUp Window End -->

<div class="forgot-pass box-gradient" id="block9" style = "display:none;">
<div><a href="javascript:void(0);" onclick="hideDiv('block9');"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox fl-left"><h1 class="pageTitle" title="Login">&nbsp;Message</h1></div>
<div class="closebtnBox fl-rigt"></div>
<div class="hr-underline clear"></div>
<div style="text-align:center" ><br/>
    <strong>Candidate <s:property value = "userId"/>  is not eligible to book a seat for exam </strong>
<br />
<br />
    
 <br />
<br />
    
<input type="button" value="Close" class="submitBtn button-gradient" title="Close" onclick="hideDiv('block9');"/>
</div>
</div>
</div>

