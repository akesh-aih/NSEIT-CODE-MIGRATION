<%@ taglib prefix="s" uri="/struts-tags"%>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="CACHE-CONTROL" content="NO-CACHE" />
<meta http-equiv="EXPIRES" content="0" />
<meta http-equiv="PRAGMA" content="NO-CACHE" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>Eligible and Non Eligible Candidate Detail</title>
<link href="css/common.css" rel="stylesheet" type="text/css" media="screen" />
<link rel="stylesheet" href="css/font-awesome.min.css" type="text/css" media="screen">

<link href="css/Flexgrid.css" rel="stylesheet" type="text/css" media="screen"/>
<link type="text/css" href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="css/validationEngine.jquery.css" type="text/css"/>
<link REL="SHORTCUT ICON" HREF="images/gcet.ico" >
<script type="text/javascript" src="js/login.js"></script>
<script src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/jcarousellite_1.0.1c4.js"></script>
<script src="js/languages/jquery.validationEngine-en.js" type="text/javascript" charset="utf-8"></script>
<script src="js/jquery.validationEngine.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript" src="js/jquery.form.js"></script>
<script>
function onErrorMessage(id){
	$("#"+id).hide();
}
</script>
</head>
<div class="container">
<s:form action="CandidateMgmtAction" id="eligible">
<div id="dashboard" style="display:block; min-height:300px; height:auto;">

<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;" >
    <div style="display:block; min-height:560px; height:auto;" id="showDiv">
					  <table class="contenttable">
					  
					 		 <tr>
							      <td width="170">Discipline</td>
						    	  <td >
							    	  	<strong>
							    	  			<s:label id = "disciplineTypeDesc" value="%{disciplineTypeDesc}"></s:label>
							    	  	</strong>
						      	  </td>
						    
						      	  <td align="right" rowspan="5">
						      	  		<img id = "id" src="PhotoImage.jpg" width="110" height="150" onerror="onErrorMessage('id')"></img>
						      	  </td>
						    </tr>
						    <tr>
						    	<td>
						    		Title
						    	</td>
						    	<td>
						    		<strong>
						    			<s:label id = "titleValue" value="%{titleValue}"></s:label>
						    		</strong>
						    	</td>
						    </tr>
						    <tr>
							      <td width="170">Name
							      </td>
						    	  <td >
							    	  	<strong>
							    	  			<s:label value="%{personalDetailsBean.candidateFirstName}"></s:label> &nbsp;
							    	  			<s:label value="%{personalDetailsBean.candidateMiddleName}"></s:label>&nbsp;
							    	  			<s:label value="%{personalDetailsBean.candidateLastName}"></s:label>
							    	  	</strong>
						      	  </td>
						      	  
						      	  <td align="right" rowspan="5">
						      	  </td>
						    </tr>
						     <s:iterator value="candiateRelationDetailsList" status="stat" var="currentObject">
						    	<tr>
						    		<td>
											<s:label value="%{relationDesc}" name="candiateRelationDetailsList[%{#stat.index}].relationDesc"></s:label>
									</td>
									
									<td>
						    			<strong><s:label name="candiateRelationDetailsList[%{#stat.index}].firstName" value="%{firstName}" label="firstName"  ></s:label></strong>
						    			<strong>	<s:label name="candiateRelationDetailsList[%{#stat.index}].middleName" value="%{middleName}" label="middleName" ></s:label></strong>
						    			<strong>	<s:label name="candiateRelationDetailsList[%{#stat.index}].lastName" value="%{lastName}" label="lastName"  ></s:label></strong>
						    		</td>
						    
						   		 </tr>
	    					</s:iterator>
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
							
						    <tr id="otherDistrict" style="display:none;">
						    	<td>
						    		&nbsp;
						    	</td>
						    	<td>
						    		<span class="lighttext">District</span>
						    		<strong><s:label value="%{addressBean.otherDistrictField}" id = "otherDistrictField"/></strong>
						    	</td>
						    </tr>
					    	
					    	<tr>
							      <td>&nbsp;</td>
							      <td colspan="2"><span class="lighttext">Country</span>
							      	<strong><s:label value="%{countryValDesc}" id = "countryVal"/></strong>
								  </td>
						    </tr>
						    
						     <tr>
            <td>&nbsp;</td>
	      <td width="227"><span class="lighttext">Tel No.(Landline)</span>
	      <strong><s:label id="stdCode" value="%{stdCode}"></s:label>&nbsp;-</strong>
	     <strong><s:label id="landline" value="%{landline}"></s:label></strong>
	      </td>
	      </tr>
						
						<tr>
						    	  <td>Mobile Number</td>
							      <td colspan="2">
							      	<strong><s:label value="%{personalDetailsBean.mobileNo}" id = "mobileNo"/></strong>
							      </td>
						    </tr>
					    	<tr>
							      <td>Alternate Mobile Number</td>
							      <td colspan="2">
							      	<!--<strong><s:label value="%{personalDetailsBean.isdNo}" id = "conutryCode"/></strong>
							      	-
							      	<strong><s:label value="%{personalDetailsBean.stdCode}" id = "stdCode"/></strong>
							      	-
							      	--><strong><s:label value="%{personalDetailsBean.phoneNo}" id = "phoneNo"/></strong>
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
						    </tr><!--
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
					    	--><tr id = "alternateDistrictFieldDisplay" style="display:none;">
						    	<td>
						    		&nbsp;
						    	</td>
						    	<td>
						    		<span class="lighttext">District</span>
						    		<strong><s:label value="%{addressBean.otherAlternateDistrictField}" id = "otherAlternateDistrictField"/></strong>
						    	</td>
						    </tr>
					    	
					    	<tr>
							      <td>&nbsp;</td>
								  <td colspan="2"><span class="lighttext">Country</span>
								  	<strong><s:label value="%{altCountryValDesc}" id = "altCountryVal"/></strong>
							      </td>
						    </tr>
						    
						    <tr>
            <td>&nbsp;</td>
	      <td width="227"><span class="lighttext">Tel No.(Landline)</span>
	      <strong><s:label id="altstdCode" value="%{alternateStdCode}"></s:label>&nbsp;-</strong>
	     <strong><s:label id="altlandline" value="%{alternateLandline}"></s:label></strong>
	      </td>
	      </tr>
	      
	       <tr>
							      <td>Communication Permanent Mobile Number</td>
							      <td colspan="2">
							      	<strong><s:label value="%{personalDetailsBean.commPermMobileNo}"/></strong>
							      </td>
    						</tr>
    
						    <tr>
							      <td>Communication Alternate Mobile Number</td>
							      <td colspan="2">
							      		<strong><s:label value="%{personalDetailsBean.commAltMobileNo}" /></strong>
							      </td>
						    </tr>
						    
					    	<tr>
							      <td>Email Id</td>
							      <td colspan="2">
							      	<strong><s:label value="%{personalDetailsBean.email}" id = "email"/></strong>
							      </td>
						    </tr>
					    	
						    
					    	<tr>
						    	  <td>Category Information</td>
							      <td colspan="2">
							      	<strong><s:label value="%{categoryValDesc}" id = "categoryVal"/></strong>
								  </td>
							</tr>
						    
						     
					 <!--<tr>
				     	 <td>Do you belong to BPL (Below Poverty Line) card holder family?</td>
				      	 <td colspan="2">
				      	 	<strong><s:label value="%{personalDetailsBean.povertyDesc}" id = "poverty"/></strong>
				      	 </td>
					</tr>
					
					  --></table>
					<br/>
					<br/>
					
					
							
<div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Educational Details">Educational Details</h1></div>
<div class="hr-underline2"></div>
<table class="contenttable">
<tr>
	<td style="width:35%">
		Name of Highest Degree
	</td>
	<td style="width:65%">
		<strong><s:label value="%{degreeValFormDesc}"></s:label></strong>
	</td>
	</tr>
	<tr>
	<td style="width:35%">
		Name of Institute/Department/College
	</td>
	<td style="width:65%">
	<strong><s:label value="%{formEducationDetailsBean.institute}"></s:label></strong>
	</td>
</tr>

<tr>
	<td style="width:35%">
		Name of University
	</td>
	<td style="width:65%">
		<strong><s:label value="%{universityValDesc}"></s:label></strong>
	</td>
	</tr>
	<tr>
	<td style="width:35%">
		Name of Head of the Department/Co ordinator
	</td>
	<td style="width:65%">
	<strong><s:label value="%{formEducationDetailsBean.headOfDept}"></s:label></strong>
	</td>
</tr>

<tr>
	<td style="width:35%">
		Year of Passing
	</td>
	<td style="width:65%">
				<strong>	<s:label value="%{yearValFormDesc}"></s:label></strong>
	</td>
	</tr>
	
<tr>
<td>
Semester System/Yearly System
</td>
<td><strong><s:label value="%{examPatternDesc}"></s:label></strong>
	</td>
</tr>
<tr>
<td>
		Last Semester/Year upto which <br/>result has been declared
	</td>
<td>
		<strong><s:label value="%{lastExam}"></s:label></strong>
	</td>

</tr>
<tr>
	<td style="width:35%">
		Aggregate % of marks of all semesters/years<br/>upto which result has been declared 
	</td>
	<td style="width:65%">
	<strong><s:label value="%{formEducationDetailsBean.aggregate}"></s:label></strong>
	</td>
</tr>
</table>


<br/>
<br/>
<div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Other Details">Other Details</h1></div>
<div class="hr-underline2"></div>
<table class="contenttable">
<tr>
	<td style="width:35%">
		Remarks
	</td>
	<td class="wordWrap">
		<strong><s:label value="%{otherDetailsBean.projDesc}" ></s:label></strong>
		
	</td>
	</tr>
	<tr>
	<td>
		Area of Project
	</td>
	<td  class="wordWrap">
	<strong><s:label value="%{areaOfProjectValDesc}" ></s:label></strong>
	</td>
</tr>

<tr>
	<td>
		Applying for JRF(CSIR / UCG / DBT / ICMR)
	</td>
	<td  class="wordWrap">
	<strong><s:label value="%{jrfVal}"></s:label></strong>
	</td>
	</tr>
	<tr>
	<td>
		Field of Interest
	</td>
	<td  class="wordWrap">
	<strong><s:label value="%{fieldOfIntrestValDesc}"></s:label></strong>
	</td>
</tr>


<tr>
	<td>
	Have you been earlier trained under BITP
	</td>
	<td  class="wordWrap">
	<strong><s:label value="%{bitpTrainingVal}" ></s:label></strong>
	</td>
</tr>


<tr>
	<td>
		If Yes, Name of Company
	</td>
	<td  class="wordWrap">
	<strong><s:label value="%{bitpTrainingCompName}" ></s:label></strong>
	</td>
	</tr>
	<tr>
	<td>
		Period of Training<font color="red"> (in months)</font>
	</td>
	<td  class="wordWrap">
		<strong><s:label value="%{otherDetailsBean.trainingPeriod}" ></s:label></strong>
	</td>
</tr>

<tr>
	<td>
		Any Award/Scholarship
	</td>
	<td  class="wordWrap">
	<strong><s:label value="%{otherDetailsBean.award}"></s:label></strong>
	</td>
</tr>


<tr>
	<td>
		Prefered venue for Interview
	</td>
	<td  class="wordWrap">
	<strong><s:label value="%{interviewValDesc}"></s:label></strong>
	</td>
</tr>


<tr>
	<td>
		Any other relevant Information
	</td>
	<td  class="wordWrap">
	<strong><s:label value="%{otherDetailsBean.othRelevantInfo}" ></s:label></strong>
	</td>
</tr>

</table>





<br/>
<br/>
					
				<div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Bank Account Details">Bank Account Details</h1></div>
<div class="hr-underline2"></div>
<table class="contenttable">
<tr>
	<td style="width:35%">
		Name as per Bank Account records:
	</td>
	<td  class="wordWrap">
		<strong><s:label value="%{stipendDetailsBean.bankNameAsPerRec}" /></strong>
	</td>
</tr>

<tr>
	<td>
		Name of Bank:
	</td>
	<td  class="wordWrap">
		<strong><s:label value="%{stipendDetailsBean.bankName}" /></strong>
	</td>
</tr>

<tr>
	<td>
		Bank Branch Address:
	</td>
	<td  class="wordWrap">
		<strong><s:label value="%{stipendDetailsBean.bankBranchAddr}" /></strong>
	</td>
</tr>

<tr>
	<td>
		A/C No.:
	</td>
	<td  class="wordWrap">
		<strong><s:label value="%{stipendDetailsBean.accountNo}" /></strong>
	</td>
</tr>

<tr>
	<td>
		NEFT/IFSC  Code:
	</td>
	<td  class="wordWrap">
		<strong><s:label value="%{stipendDetailsBean.ifsCode}" /></strong>
	</td>
</tr>
</table>
					
					
					<table class="contenttable">
					<s:if test='%{educationDetailsDisplayFlag}'>
					<tr>
		<td>
		<h1 class="pageTitle" title="Dashboard">Other Qualification Details</h1>
		<div class="hr-underline2"></div>
			<table width="900" class="personsl-dtl" id="educationformDetails">
			
			<tr>
	     			<td colspan="7">
	     				Degree Selected &nbsp;&nbsp;&nbsp;&nbsp; <s:label  value="%{degreeTypeVal}" label="degreeTypeVal" cssClass="small" ></s:label>
	     			</td>
	     		</tr>
	     		
				     <tr>
	        						<th rowspan="2" width="128" align="left" bgcolor="#FFFFFF"><strong>Examination </strong></th>
	        						<th rowspan="2" width="171" align="left" bgcolor="#FFFFFF"><strong>Specialization</strong></th>
	     							 <th rowspan="2" width="171" align="left" bgcolor="#FFFFFF"><strong>Year of Passing</strong></th>
	   							     <th rowspan="2" width="179" align="left" bgcolor="#FFFFFF"><strong>University / Institute</strong></th>
	        						<th colspan="3" align="left" bgcolor="#FFFFFF"><strong>Grand Total Marks</strong></th>
       						 </tr>
				    			<tr>
								    <th style="width: 220px;" align="left"><strong>Obtained </strong></th>
									<th style="width: 95px;" align="left"><strong>Out of</strong></th>
									<th style="width: 80px;" align="left"><strong>Percentage</strong></th>
								</tr>
  		<s:iterator value="educationDtlsList" status="stat" var="currentObject">
  		
  			<tr >
  				<td >
  				<s:hidden name="educationDtlsList[%{#stat.index}].degreeSelected" value="%{degreeSelected}" label="degreeSelected" id = "degreeSelectedUser"/>
  						<s:label  value="%{examination}" label="examination" ></s:label>
  						
  				</td>
  				
  				<td >
  						<s:label  value="%{specialization}" label="specialization" cssClass="small" ></s:label>
  				</td>
  				
  				<td>
  						<s:label  value="%{yearOfPassing}" label="yearOfPassing" cssClass="small" 
  						 />
  				</td>
  				
  				<td>
  						<s:label value="%{university}" label="university" cssClass="small" ></s:label>
  				</td>
  				
  				<td>
  						<s:label value="%{obtndMarks}" label="obtndMarks" onkeypress="return numbersonly(event);" cssClass="small" ></s:label>
  				</td>
  				
  				<td>
  						<s:label value="%{outOfMarks}" label="outOfMarks" cssClass="small"></s:label>
  				</td>
  				
  				<td>
  						<s:label  value="%{percentage}" ></s:label>
  				</td>
  			</tr>
  		</s:iterator>
  
  </table>
  <br />
		</td>
	
	</tr>
				</s:if>	
				<s:if test='%{educationDetailsDisplayFlag}'>	
					<tr>
		<td>
		    <s:if test='%{postGraduationActive}'>
	    <h1 class="pageTitle" title="Dashboard">Post Graduation Details</h1>
		<div class="hr-underline2"></div>
		<table class="contenttable">
				<tr>
					<td width="25%">
						Post Graduate
					</td>
					<td width="75%">
						<strong><s:label value = "%{educationDetailsBean.degree}" id = "degreeOther" disabled="true"/></strong>
					</td>
					</tr>
					
					<tr>
						<td>
							If Other,Please specify 
						</td>
						<td>
							<s:label value = "%{educationDetailsBean.degreeOther}" id = "degreeOther" disabled="true"/>
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
	        						<th colspan="3" align="left" bgcolor="#FFFFFF"><strong>Grand Total Marks</strong></th>
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
										<s:label value="%{educationDetailsBean.specialization}"/>
									</td>
									<td>
										<s:label value="%{educationDetailsBean.yearOfPassing}"  cssClass="small"/>
					  				</td>
					  				
					  				<td>
					  						<s:label value="%{educationDetailsBean.university}"  />
					  				</td>
					  				
					  				<td>
					  						<s:label value="%{educationDetailsBean.obtndMarks}"  />
					  				</td>
					  				
					  				<td>
					  						<s:label value="%{educationDetailsBean.outOfMarks}"  cssClass="small"/>
					  				</td>
					  				
					  				<td>
					  					<s:label value="%{educationDetailsBean.percentage}"  cssClass="small"/>
					  				</td>
								</tr>
								
							</table>
						</td>
						
					</tr>
		
		</table>
  </s:if>
		</td>
	</tr>
	</s:if>
	<br/>
	<div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Preferred Test Centre">Preferred Test Centre</h1></div>
<div class="hr-underline2"></div>
		<tr>
		<td>
			<s:if test="%{testCenterName1!=null}">
					 	<tr>
					  	<td>
					  			<table width="100%" class="contenttable">
					  				<tr >
					  					<td width = "170">
					  					Preferred Test Centre 1
					  					</td>
					  					<td width = "600">
					  					<strong><s:label value="%{testCenterName1}" /></strong>
					  					</td>
					  				</tr>
					  			
					  			</table>
					  	
					  			
					  	</td>
					  	
					  </tr>
					 </s:if><s:else></s:else>
			 
			 <s:if test="%{testCenterName2!=null}">
					 	<tr>
					  	<td>
					  			<table width="100%" class="contenttable">
					  				<tr>
					  					<td width = "170">
					  					Preferred Test Centre 2
					  					</td>
					  					<td width = "600">
					  					<strong><s:label value="%{testCenterName2}" /></strong>
					  					</td>
					  				</tr>
					  			
					  			</table>
					  	
					  			
					  	</td>
					  </tr>
					 </s:if><s:else></s:else>
			 
	<s:if test="%{testCenterName3!=null}">
					 	<tr>
					  	<td>
					  			<table width="100%" class="contenttable">
					  				<tr>
					  					<td width = "170">
					  					Preferred Test Center 3
					  					</td>
					  					<td width = "600">
					  					<strong><s:label value="%{testCenterName3}" /></strong>
					  					</td>
					  				</tr>
					  			
					  			</table>
					  	
					  			
					  	</td>
					  </tr>
					 </s:if><s:else></s:else>
					 
					  <s:if test="%{preferredTestDate1!=null}">
					 	<tr>
					  	<td colspan="2">
					  			<table width="100%" class="contenttable">
					  				<tr>
					  					<td width = "170">
					  					Preferred Test Date 1
					  					</td>
					  					<td width = "600">
					  					<strong><s:label value="%{preferredTestDate1}" /></strong>
					  					</td>
					  				</tr>
					  			
					  			</table>
					  	
					  			
					  	</td>
					  </tr>
					 </s:if><s:else></s:else>
					 
					 <s:if test="%{preferredTestDate2!=null}">
					 	<tr>
					  	<td colspan="2">
					  			<table width="100%" class="contenttable">
					  				<tr>
					  					<td width = "170">
					  					Preferred Test Date 2
					  					</td>
					  					<td width = "600">
					  					<strong><s:label value="%{preferredTestDate2}" /></strong>
					  					</td>
					  				</tr>
					  			
					  			</table>
					  	
					  			
					  	</td>
					  </tr>
					 </s:if><s:else></s:else>
					 
					 <s:if test="%{preferredTestDate3!=null}">
					 	<tr>
					  	<td colspan="2">
					  			<table width="100%" class="contenttable">
					  				<tr>
					  					<td width = "170">
					  					Preferred Test Date 3
					  					</td>
					  					<td width = "600">re
					  					<strong><s:label value="%{preferredTestDate3}" /></strong>
					  					</td>
					  				</tr>
					  			
					  			</table>
					  	
					  			
					  	</td>
					  </tr>
					 </s:if><s:else></s:else>
					</td></tr>
					 </table>
					 <br/>
					 <div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Uploaded Document">Uploaded Document</h1></div>
<div class="hr-underline2"></div>
				<table class="contenttable">
				<tr>
				<td>
					<s:if test='%{documentFileName != null}'>
						&nbsp;&nbsp;<strong> <s:property value="docLabel1" /> : </strong><s:property value="documentFileName" />
					</s:if>
					<s:else>
						&nbsp;&nbsp;<strong> <s:property value="docLabel1" /> : NA</strong>
					</s:else>
				</td>
				<td>
					<s:if test='%{documentFileName1 != null}'>
			    	<strong> <s:property value="docLabel2" /> : </strong><s:property value="documentFileName1" />
				</s:if>
				</td>
				<td>
					<s:if test='%{documentFileName2 != null}'>
			    	<strong> <s:property value="docLabel3" /> : </strong><s:property value="documentFileName2" />
				</s:if>
				</td>
				</tr>
				<tr>	 
   					<td colspan="3" align="right" valign="bottom"><img id = "signature" src="SignatureImage.jpg" width="200" height="50"  onerror="onErrorMessage('signature')"/></td>
				 </tr>
			  	<tr>
				   <td colspan="3">&nbsp;
				   </td>
			   </tr>

			   <tr>
			   		<td colspan="3" align="center">
			   			<input type="button" value="Close" onclick="window.close()" class="submitBtn button-gradient">
			   		</td>
			   </tr>
			  </table>
			  </div>
	</div>
</div>
</s:form>
</div>
