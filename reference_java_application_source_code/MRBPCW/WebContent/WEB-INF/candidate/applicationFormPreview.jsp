<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
function areYouSyre(){
	$('html, body').animate({ scrollTop: 0 }, 0);
	ShowPop('pop9');
}

</script>
<div class="container">
<div class="main-body">
	<s:form id="applicationForm" action="CandidateAction">
	<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
	<s:hidden name="isDataFound" value='<s:property value="personalDetailsBean.candidateName"/>'/>
	<div id="dashboard">
		<s:actionerror />
		<s:hidden id='hddAddressChkBox'></s:hidden>
<div align="left" >
	<s:if test="%{#session['SESSION_USER'] != null}">
		<strong>User ID</strong> - <s:label value="%{#session['SESSION_USER'].username}" /><br/>
	</s:if>
	
</div>
<br />

<h1 class="pageTitle" title="Dashboard">Discipline </h1>
		<div class="hr-underline2"></div>
		<table  class="contenttable">
		<tr>
					    <td width="190"> Discipline</td>
					    <td colspan="2" align="left">
					    	<strong><s:label value="%{disciplineTypeDesc}"  /></strong>
						</td>
		 </tr>
	</table>
		<br/>
			<br/>

		<h1 class="pageTitle" title="Dashboard">Personal Details </h1>
		<div class="hr-underline2"></div>
			
					<!-- Box Container Start -->
					<div style="display:block; min-height:560px; height:auto;">
					  <table class="contenttable">
					  <tr>
					  	<td>
					  		Prefix
					  	</td>
					  	<td>
					  		<strong>
					  			<s:label value="%{titleValue}"></s:label>
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
							      <td colspan="2">
							      	<strong><s:label value="%{personalDetailsBean.dateOfBirth}" id = "dateOfBirth"/></strong>
								  </td>
						    </tr>
						    
					    	<tr>
						    	  <td>Nationality</td>
						      	  <td colspan="2">
						      	  	<strong><s:label value="%{nationalityDesc}" id = "nationality"/></strong>
						      	  </td>
						    </tr>
					    	<tr>
							      <td>Gender</td>
							      <td colspan="2">
							      	<strong><s:label value="%{genderValDesc}" id = "gender"/></strong>
								  </td>
						    </tr>
					    	<tr>
						    	  <td>Permanent Address </td>
							      <td colspan="2">
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
							       	<br />
							        <strong><s:label value="%{addressBean.cityName}" id = "cityName"/></strong>
							      </td>
							      <td width="371"><span class="lighttext">Pincode</span> <br />
							      	<strong><s:label value="%{addressBean.pinCode}" id = "pinCode"/></strong>   
							      </td>
							      
							</tr>
					   
					<!-- For other countries -->    
						    <tr id="districtId">
								 <td>&nbsp;</td>
								 <td colspan="2"><span class="lighttext"> District </span><br />
								     <strong><s:label value="%{districtValDesc}" id = "districtVal"/></strong>
								     <strong><s:label value="%{otherDistrict}" id = "otherDistrict"/></strong>
								</td>
						    </tr>
						    
					    	<tr id = "stateDropdown">
							      <td>&nbsp;</td>
							      <td>
							      		<span class="lighttext">State / Union Territory</span><br />
							      		<strong><s:label value="%{stateValDesc}" id = "stateVal"/></strong>
										&nbsp; 
							      </td>
							</tr>
					    
					    	
					    	<tr>
							      <td>&nbsp;</td>
							      <td colspan="2"><span class="lighttext">Country</span><br />
							      	<strong><s:label value="%{countryValDesc}" id = "countryVal"/></strong>
								  </td>
						    </tr>
					    	<tr>
							      <td rowspan="2">Address for Communication  <br /></td>
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
							      <td><span class="lighttext">City / Town</span>  <br />
							      	<strong><s:label value="%{addressBean.alternateCity}" id = "alternateCity"/></strong>
							      </td>
							      <td>
							      	<span class="lighttext">Pincode</span> <br />
							      	<strong><s:label value="%{addressBean.alternatePinCode}" id = "alternatePinCode"/></strong>
							      </td>
							      
						    </tr>
						    
						    <tr>
					    	<td></td>
					    	<s:if test='%{altTalukaValDesc!=null&&altTalukaValDesc!=""}'>
						    <td ><span class="lighttext">Taluka</span> <br />
								      	<strong><s:label value="%{altTalukaValDesc}" id = "altTalukaVal"/></strong>   
							 </td>
							</s:if>
							<s:if test='%{altTalukaField != null&&altTalukaField!="" }'>
							 <td ><span class="lighttext">Taluka</span> <br />
								      	<strong><s:label value="%{altTalukaField}" id = "taluka"/></strong>   
							 </td>
							 </s:if>
					    
					    </tr>
							
					    	
					    	<tr id = "alternateDistrictDisplay">
						      	<td>&nbsp;</td>
						      	<td colspan="2"><span class="lighttext">District</span><br />
							  		<strong><s:label value="%{altDistrictValDesc}" id = "altDistrictVal"/></strong>
							  		<strong><s:label value="%{altOtherDistrict}" id = "altOtherDistrict"/></strong>
							  	</td>
							</tr>
							
						
					    	<tr id = "alternateStateDisplay">
						     	 <td>&nbsp;</td>
						      	<td>
						      		<span class="lighttext">State / Union Territory </span><br />
						      		<strong><s:label value="%{altStateValDesc}" id = "altStateVal"/></strong>
						        	&nbsp; 
						        </td>
						    </tr>
					    	
					    	
					    	<tr>
							      <td>&nbsp;</td>
								  <td colspan="2"><span class="lighttext">Country</span><br />
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
							      	<!--<strong><s:label value="%{personalDetailsBean.isdNo}" id = "conutryCode"/></strong>
							      	-
							      	<strong><s:label value="%{personalDetailsBean.stdCode}" id = "stdCode"/></strong>
							      	-
							      	--><strong><s:label value="%{personalDetailsBean.phoneNo}" id = "phoneNo"/></strong>
								  </td>
						    </tr>
					    	<tr>
						    	  <td>Category Information  </td>
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
						    </tr><%--
						   
						    <s:if test='%{personalDetailsBean.handicapped == "Yes"}'> 
						     <tr>
						      <td>&nbsp;</td>
						      <td colspan="2"><span class="lighttext">Percentage of Disability (more than 40%)</span><br />
						        	<s:label id="handicappedPercentage" name="personalDetailsBean.handicappedPercentage"></s:label>
						       </td>
						</tr>
						</s:if>
						
						
						
						--%><!--<tr>
						  	<td>Do you belong to BPL (Below Poverty Line) card holder family?</td>
						  	<td>
						  		<strong><s:label value="%{personalDetailsBean.povertyDesc}" id = "poverty"/></strong>
							</td>
					  </tr>
					  
					  --></table>
					</div><br/>
					<br/><%--
					
					<h1 class="pageTitle" title="Dashboard">Education Details</h1>
					<div class="hr-underline2"></div>
					--%><table class="contenttable"><tr>
						   <td colspan="3">
						   <table width="100%">
					  <tr>
						    <td width="350px;" ><s:submit value="Edit" cssClass="submitBtn button-gradient" method="getCandidateDetails"></s:submit>
						    
						   	</td>
					  		<td align="right"> 
					  		<!--<s:submit value="Confirm" cssClass="submitBtn button-gradient" method="updateCandidateStageAfterFinalSubmit" onclick="return confirmReg();"></s:submit>-->
					  		<s:submit method="updateCandidateStage" value="Confirm & Continue" cssClass="submitBtn button-gradient" ></s:submit>
					  		</td> 				
					  		  		
					  </tr>
					  </table>
					  </td></tr>
					  </table>

</s:form>
<div id='block7'></div>
</div>
</div>