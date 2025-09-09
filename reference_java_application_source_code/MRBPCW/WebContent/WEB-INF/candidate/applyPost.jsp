<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<%@ page import="com.nseit.generic.util.GenericConstants"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<script>
var value1=<s:property value='%{postName1}' />;
var value2=<s:property value='%{postName2}' />;
var value3=<s:property value='%{postName3}' />;
</script>
       
 
<script>
	$(document).ready(function() {
		$(".subNavBg").hide();
		$("#postSelect").hide();
		//Shreyas : Show link to update Educational :29-june-2017
		$("#linkToEduUpdate").hide();
		$("#linkToDocUpload").hide();
		$("#linkToaddDetailUpdate").hide();
		$("#linkToEduAndAgeRelaxUpdate").hide();
		$("#linkToDocAndAgeRelaxUpdate").hide();
		var err=$("#eduDetailRedirectFlag").val();
		var addDetailRedirect=$("#addDetailRedirectFlag").val();
		var samePreferenceValue=$("#samePreferenceValueFlag").val();
		var docUploadFlag = $("#docUploadRedirectFlag").val();

		if(err=='true' && addDetailRedirect=='true' && samePreferenceValue != 'true')
		{
			$("#linkToEduAndAgeRelaxUpdate").show();
		}
		else if(docUploadFlag == 'true' && addDetailRedirect=='true' && samePreferenceValue != 'true')
			{
				$("#linkToDocAndAgeRelaxUpdate").show();
			}
		else if(addDetailRedirect=='true' && samePreferenceValue != 'true')
		{
			$("#linkToaddDetailUpdate").show();
		}

		else if(err=='true' && samePreferenceValue != 'true')
		{
			$("#linkToEduUpdate").show();
		} 
		
		else if(docUploadFlag == 'true') {
			$("#linkToDocUpload").show();
		}	
	if($("#disciplineType").val()!=null)
	{
		populatePostmaster();

		dataString = "DisciplineTypeVal="+$("#disciplineType").val();
		
		$.ajax({
			url: "CandidateAction_getAdvertisementNumber.action",
			async: true,
			data: dataString,
			type: 'POST',
			beforeSend: function()
			{
				$('#advertisementnumber').val("");
			},
			error:function(ajaxrequest)
			{
				//window.reload();
				
			},
			success:function(responseText)
			{
				responseText = $.trim(responseText);
				if(responseText != null && responseText != "null"){
					$('#advertisementnumber').val(responseText);
				}
				else
				{
					$('#advertisementnumber').val("");
				}
			}
		});	
	}
	
	
		//End : Shreyas
		
	});
</script>

<script type="text/javascript">
//Shreyas : Check Qualification : 28-June-2017
	function eduLinkForm()
	{
		 $( "#eduValue" ).submit();
	}

	function ageRelaxLinkForm()
	{
		 $( "#ageRelxValue" ).submit();
	}

	function docUploadLinkForm()
	{
		 $( "#docUploadValue" ).submit();
	}

function checkQualification()
{
	var sslcVal=$("#register_educationDtlsList_0__specialization").val();
	var degreeVal=$("#degreeSubject3").val();
	var postName=$("#disciplineType").val();
		if(postName=='17')
		{
			if(sslcVal=='')
			{
				if (confirm("Your Education Qualification is not eligible for this Post, do you want to update your education qualification?"))
				{
					goToMenu('CandidateAction_getCandidateEducationalDetailsPage.action');
				}
			}
		}
		if(postName=='15')
		{
			if(degreeVal!='2')
			{
				if (confirm("Your Education Qualification is not eligible for this Post, do you want to update your education qualification?"))
				{
					goToMenu('CandidateAction_getCandidateEducationalDetailsPage.action');
				}
			}
			
		}
		if(postName=='14')
		{
			var diplomaCertiVal=$("#register_educationDtlsList_2__specialization").val();
			var diplomaDegree=$("#degreeSubject2").val();
			var degreeCerti=$("#register_educationDtlsList_3__specialization").val();
			if((diplomaCertiVal=='' && diplomaDegree!='1') || (degreeCerti=='' && degreeVal!='3' ))
			{
				if (confirm("Your Education Qualification is not eligible for this Post, do you want to update your education qualification?"))
				{
					goToMenu('CandidateAction_getCandidateEducationalDetailsPage.action');
				}
			}
		}

		if(postName=='10')
		{
			if(sslcVal==null)
			{
				if (confirm("Your Education Qualification is not eligible for this Post, do you want to update your education qualification?"))
				{
					goToMenu('CandidateAction_getCandidateEducationalDetailsPage.action');
				}
			}
		}

		if(postName=='10')
		{

			var degreeCerti=$("#register_educationDtlsList_3__specialization").val();
			if(degreeCerti=='')
			{
				if (confirm("Your Education Qualification is not eligible for this Post, do you want to update your education qualification?"))
				{
					goToMenu('CandidateAction_getCandidateEducationalDetailsPage.action');
				}
				
			}
			
			
		}

}

function populateAdvertisementNumber(){

	if($("#errorMsgDiv"))
		$("#errorMsgDiv").remove();
	var lDisciplineTypeElement=document.getElementsByName("disciplineType");
	var  lDisciplineTypeVal;
	var lType;
	for(var i=0;i<lDisciplineTypeElement.length;i++){
		lType=lDisciplineTypeElement[i].nodeName;
		if(lType=="SELECT"){
			//alert(lDisciplineTypeElement[i].value);
			lDisciplineTypeVal=lDisciplineTypeElement[i].value;
			}
	}
	//var  lDisciplineTypeVal= $("#disciplineType").val();
	//alert(lDisciplineTypeVal);


	dataString = "DisciplineTypeVal="+lDisciplineTypeVal
		
		$.ajax({
			url: "CandidateAction_getAdvertisementNumber.action",
			async: true,
			data: dataString,
			type: 'POST',
			beforeSend: function()
			{
				$('#advertisementnumber').val("");
			},
			error:function(ajaxrequest)
			{
				window.reload();
				
			},
			success:function(responseText)
			{
				responseText = $.trim(responseText);
				if(responseText != null && responseText != "null"){
					$('#advertisementnumber').val(responseText);
				}
				else
				{
					$('#advertisementnumber').val("");
				}
			}
		});	
		
			}


function populatePostmaster(){

	var count = $('#mainTB tr').length;
	if(count > 4)
	{
		for(var i=1 ;i<count-3;i++)
		{
			$('#PostTr'+i).remove();
			//document.getElementById("mainTB").deleteRow(i+1);	
		}
	}
	
	var lDisciplineTypeElement=document.getElementsByName("disciplineType");
	var  lDisciplineTypeVal;
	var lType;
	var count ;
	for(var i=0;i<lDisciplineTypeElement.length;i++){
		lType=lDisciplineTypeElement[i].nodeName;
		if(lType=="SELECT"){
			//alert(lDisciplineTypeElement[i].value);
			lDisciplineTypeVal=lDisciplineTypeElement[i].value;
			
			}
	}
	//var  lDisciplineTypeVal= $("#disciplineType").val();
	//alert(lDisciplineTypeVal);


	dataString = "DisciplineTypeVal="+lDisciplineTypeVal
		$.ajax({
			url: "CandidateAction_getPostMaster.action",
			async: true,
			data: dataString,
			type: 'POST',
			beforeSend: function()
			{
			$('#postName').empty();
			$('#postName').append(
			           $('<option></option>').val("").html("Select Preference")
			     );
				$('#postName').val("");
			},
			error:function(ajaxrequest)
			{
				window.reload();
				
			},
			success:function(responseText)
			{
				var  trHTML='';
				var select='<option value="-1">Select Preference</option>';
				responseText = $.trim(responseText);
				if(responseText != null && responseText != "null")
				{
					$('#postName').val(responseText);
					
					responseText = $.trim(responseText);
					if(responseText.length > 0)
					{
						element = responseText.split(",");  
						
						message = responseText.substring(2, responseText.length);
						if(element[0] == "9")
						{
							//alert(message);
							//return false;
						}
						else
						{
							var i=1;
							$.each(element, function(val) {
							  var nameAndIDArr = element[val].split("#");
								  $("#postName").removeAttr("disabled");
							  $("#postName").append(
							           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
							     );
						select +='<option value='+nameAndIDArr[1]+'>'+nameAndIDArr[0]+'</option>';
						 	}); 

							$.each(element, function(val) {
								count = $('#mainTB tr').length;
								if(i==1)
								{
								trHTML += '<tr id="PostTr'+i+'"><td>Preference '+i+'<span class="tamil"><s:text name="prefer"/></span> '+i+'<span class="manadetory-fields"> *</span></td><td><select id = "postName'+i+'" name ="postName'+i+'" value="" cssStyle="width:350px;">'+select+'<select></td></tr>';
								}else
								{
									trHTML += '<tr id="PostTr'+i+'"><td>Preference '+i+'<span class="tamil"><s:text name="prefer"/></span> '+i+'</td><td><select id = "postName'+i+'" name ="postName'+i+'" value="" cssStyle="width:350px;">'+select+'<select></td></tr>';
								}

									i++;
							 	}); 
						 	
							 $('#mainTB tr:first').after(trHTML);
							 var count1=$('#mainTB tr').length;
							 $('#mainTB').append(' <input type="hidden" name="name" value="'+count1+'"/>');
							
						}
					}
					

					$("#postSelect").show();
					
				}
				else
				{
					$('#postName').val("");
				}
				$("#postName1").val(value1);
				$("#postName2").val(value2);
				$("#postName3").val(value3);
			}
		});	

	
		
			}

function goToMenu(link)
{
	$("#register").attr('action',link);
	$("#register").submit();
}

//Shreyas : End

</script>


<title>My JSP 'candidateTestCenterPreference.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
<style>
#msg .msgg { float:left;  }
#msg li:first-child { display:none;}
#msg li { float:left; width:100%;   }
#msg br { height:1px; float:left; }
#linkToEduUpdate a,#linkToDocUpload a, #linkToaddDetailUpdate a  {text-decoration:underline; cursor:pointer; }
</style>
	<s:form id='eduValue' name="eduValue" action="CandidateAction_getCandidateEducationalDetailsPageForUpdate.action">
	<s:hidden name="eduDetailRedirectFlag" id="eduDetailRedirectFlag" value="%{eduDetailRedirectFlag}"></s:hidden>
	<s:hidden name="addDetailRedirectFlag" id="addDetailRedirectFlag" value="%{addDetailRedirectFlag}"></s:hidden>
	<s:hidden name="samePreferenceValueFlag" id="samePreferenceValueFlag" value="%{samePreferenceValueFlag}"></s:hidden>
	<s:hidden name="disciplineType" id="disciplineType" value="%{disciplineType}"></s:hidden>
	<s:hidden name="testCenterApply" id="testCenterApply" value="%{testCenterApply}"></s:hidden>
	<s:token/>
   	</s:form> 
    <s:form id='ageRelxValue' name="ageRelxValue" action="CandidateAction_getCandidateAgeRelaxation.action">
   	<s:hidden name="eduDetailRedirectFlag" id="eduDetailRedirectFlag" value="%{eduDetailRedirectFlag}"></s:hidden>
	<s:hidden name="addDetailRedirectFlag" id="addDetailRedirectFlag" value="%{addDetailRedirectFlag}"></s:hidden>
	<s:hidden name="samePreferenceValueFlag" id="samePreferenceValueFlag" value="%{samePreferenceValueFlag}"></s:hidden>
	<s:hidden name="disciplineType" id="disciplineType" value="%{disciplineType}"></s:hidden>
	<s:hidden name="testCenterApply" id="testCenterApply" value="%{testCenterApply}"></s:hidden>
	<s:token/>
   	</s:form> 
   	<s:form id='docUploadValue' name="docUploadValue" action="CandidateAction_showUploadDocument.action">
   	<s:hidden name="docUploadRedirectFlag" id="docUploadRedirectFlag" value="%{docUploadRedirectFlag}"></s:hidden>
   	<s:hidden name="eduDetailRedirectFlag" id="eduDetailRedirectFlag" value="%{eduDetailRedirectFlag}"></s:hidden>
	<s:hidden name="addDetailRedirectFlag" id="addDetailRedirectFlag" value="%{addDetailRedirectFlag}"></s:hidden>
	<s:hidden name="samePreferenceValueFlag" id="samePreferenceValueFlag" value="%{samePreferenceValueFlag}"></s:hidden>
	<s:hidden name="disciplineType" id="disciplineType" value="%{disciplineType}"></s:hidden>
	<s:hidden name="testCenterApply" id="testCenterApply" value="%{testCenterApply}"></s:hidden>
	<s:token/>
   	</s:form> 
   <div class="container" id="errorMsgDiv"><br/>
   <div id="error-massageAppForm" style="display:none;color:red;" class="error-massage">
      	<ul style="margin-left:-41px;" id="error-ulAppForm">
      	</ul>
</div>

<s:actionmessage escape="false" id="msg" cssClass="msgg"/>
<div style="clear:both; width:100%; "></div>
<s:if test='%{testCenterSuccFlag=="true"}'>
				<div style="border: #999 1px solid; padding: 3px; color: green;"
					id="successMessage">
					<s:property value="testCenterSuccMsg" />
				</div><br/>
			</s:if>
			
			
<s:if test='%{candidateDetailsSuccFlag=="true"}'>
<br />
	<div style="border:#999 1px solid;padding:3px;color:green;" id="successMessage">
	<s:property value="candidateDetailsSuccMsg"/>
	</div>
<br/>
</s:if>
<div id="linkToEduUpdate" style="  float:left;">
<a onClick="eduLinkForm();" id="eduLink"><b style="padding-left:18px;">Click here to update Educational details</b></a> <br/><br/>
</div>

<div id="linkToaddDetailUpdate" style="float:left;">

<a onClick="ageRelaxLinkForm();" class="blue" ><b style="padding-left:18px;">Click here to update age relaxation details</b></a> <br/><br/>
</div>

<div id="linkToEduAndAgeRelaxUpdate" style="float:left;">
<a onClick="eduLinkForm();" class="blue" ><b style="padding-left:18px;">Click here to update Educational details and  Age relaxation</b></a> <br/><br/>
</div>

<div id="linkToDocAndAgeRelaxUpdate" style="float:left;">
<a onClick="docUploadLinkForm();" class="blue" ><b style="padding-left:18px;">Click here to update Educational Documents and  Age relaxation</b></a> <br/><br/>
</div>

<div id="linkToDocUpload" style="  float:left;">
<a onClick="docUploadLinkForm();" id="docLink"><b style="padding-left:18px;">Click here to upload Educational Documents</b></a> <br/><br/>
</div>
   </div>
   
   <div class="container common_dashboard">
	<s:form action='CandidateAction' name='register' id='register' method='post'>
	<div class="wrapForm">
	

<div class="">
<div id="">

<s:actionerror />
<s:hidden id='hddAddressChkBox'></s:hidden>
<div align="center" style="font-size: 22px;color:  brown;"><strong>APPLICATION FORM</strong></div>
<div align="left" >
	<s:if test="%{#session['SESSION_USER'] != null}">
		<strong>User ID / <span class="tamil"><s:text name="applicationForm.userId"/></span></strong> - <s:label value="%{#session['SESSION_USER'].username}" /><br/>
	</s:if>
	<div>
	<div style="float:left;"><span class="lighttext"><s:text name="applicationForm.note"/> </span> <span class="manadetory-fields">*</span> <span class="lighttext"><s:text name="applicationForm.note1"/></span><span class="lighttext"><span class="tamil"><s:text name="applicationFormT.note"/></span>: <span class="manadetory-fields">*</span> <span class="tamil"><s:text name="applicationFormT.note1"/></span></span>  </div>
	<%--<s:if test='%{instrReqd}'>
		<div style="float:right; width:400px; text-align:right;">
			<a onclick='ShowPop("pop8");' style="cursor: pointer;" >Click here to read the instructions again</a>
		</div>
	</s:if>
	--%></div>
</div>


<br /> <br /> <br /> 


  <div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Post">Post / <span class="tamil"><s:text name="application.post"/></span></h1></div>
<div class="hr-underline2"></div>
	<table class="contenttable" width="100%" border="0"  id ="mainTB">
		<tr>
				<td width="31%">
					<s:text name="Recruitment / " /><span class="tamil"><s:text name="applicationForm.Recruitment"/></span>&nbsp;<span class="manadetory-fields">*</span>
				</td>
				<td colspan="2">
					<s:select cssStyle="width:350px;" list = "discliplineList" name = "disciplineType" label = "Name" headerKey=""
								headerValue="Select Recruitment"
							id = "disciplineType" value="%{disciplineType}" onchange="populateAdvertisementNumber();populatePostmaster();" />	<!-- onchange="populateAdvertisementNumber();" -->
				</td>
		 </tr>
			<div id="postSelect" style="display:none;"><%--
		 <tr>
				<td width="31%">
					<s:text name="Set Preference" />&nbsp;<span class="manadetory-fields">*</span>
				</td>
				<td colspan="2">
					<s:select cssStyle="width:350px;" list = "postMasterMap" name = "postName" label = "Name" headerKey=""
								headerValue="Select preferences"
							id = "postName" value="%{postName}" />	<!-- onchange="populateAdvertisementNumber();" -->
				</td>
		 </tr>
		 --%></div>
		 <tr>
				<td width="31%">
					<s:text name="Advertisement Number / " /><span class="tamil"><s:text name="application.advertisement"/></span>&nbsp;<span class="manadetory-fields">*</span>
				</td>
				<td colspan="2">
					<s:textfield cssStyle="width:350px;" id="advertisementnumber" name="advertisementnumber" readonly="true"> </s:textfield>
				</td>
		 </tr>
		 <tr>
				<td width="31%">
					<s:text name="Preferred District/City for Written Test / "/><BR/><span class="tamil"><s:text name="application.testCenter"/></span> &nbsp;<span class="manadetory-fields">*</span>
				</td>
				<td colspan="2">
					<s:select cssStyle="width:350px;" list="testCenterMasterDetails"
								name="testCenterApply" headerKey=""
								headerValue="Select Preferred District/City for Written Test" id="testCenterApply"
								value="%{testCenterApply}" />
				</td>
		 </tr>
		
		 <tr>
		 	<td>&nbsp;</td>
		 	<td><s:submit value="Apply" cssClass="submitBtn button-gradient" 
								method="saveCandidatePost" ></s:submit></td>
		 </tr>
	</table>

<br class="otherDetailsFields"/>
<br class="otherDetailsFields"/>

<table class="contenttableNew otherDetailsFields"><%--
  
  <tr>
	    <td colspan="4" align="center">
	    	<s:submit value="Save / Update" cssClass="submitBtn button-gradient"  method="saveCandidateDetails"></s:submit>      
	    </td>
	    
  </tr>
  --%><tr>
  <td colspan="5">
  <hr/>
  
  </td>
  
  </tr>
  
  <tr><%--
  <td colspan="5">
  	<div align ="right">
		<s:if test='%{candiateDetailsMandatory}'>
			<s:if test="%{candidateDataFilled}">
				<s:submit method="updateCandidateStage" value="Continue" cssClass="submitBtn button-gradient"></s:submit>
			</s:if><s:else>
				<s:submit method="updateCandidateStage" value="Continue" cssClass="submitBtn button-gradient" disabled="true"></s:submit>
			</s:else>
		</s:if>
		
		<s:else>
			<s:submit method="updateCandidateStage" value="Continue" cssClass="submitBtn button-gradient"></s:submit>
		</s:else>
		</div>
  </td>
  --%></tr>
  </table>
  
</div>





</div>
</div>

	<div class="main-body">
	
		<div id="dashboard">
<s:hidden name="handicappedValue" id="handicappedValue"/>
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:hidden name="isDataFound" value='<s:property value="candidateDataFilled"/>'/>
<s:hidden name="degreeVal" id="degreeVal"/>
<s:hidden name="cateoryValue" id="cateoryValue"/>


	   
		<s:hidden name="postGraduateValueDesc" id="postGraduateValueDesc"></s:hidden>	
			  
	    <s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
		<s:hidden name="testFlag" value='<s:property value="testFlag"/>'/>
	    <s:hidden name="isDataFound" value='<s:property value="dataFound"/>'/>
	   
	    <br/>
	    
  
	  	</div>
		<%--
			 

			<h1 class="pageTitle" title="Preferred Test City">
				Preferred Test City
			</h1>
			<div class="hr-underline2"></div>
					
				<s:hidden name="testCenterUploaded" id="testCenterUploaded" />
				<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<s:hidden name="isDataFound"
					value='<s:property value="testCenterUploaded"/>' />
				
				<s:if test="%{#attr.dataNotFound!=''}">
					<div class="error-massage-text">
						<s:property value="#attr.dataNotFound" />
					</div>
				</s:if>
				<table border="0" cellspacing="0" cellpadding="5" class="contenttableNew">
					<!-- <tr>
				<td width="300">
					<s:text name="Select Post" />&nbsp;<span class="manadetory-fields">*</span>
				</td>
				<td colspan="2">
					<s:select list = "discliplineList" name = "disciplineType" label = "Name" headerKey=""
								headerValue="Select Post"
							id = "disciplineType" value="%{disciplineType}"/>	
				</td>
				</tr>-->
					<tr>
						<td  width="31%">
							Preferred Test City 1
							<span class="manadetory-fields">*</span>
						</td>
						<td width="69" >
							<s:select list="testCenterMasterDetails"
								name="testCenter1" label="Name" headerKey=""
								headerValue="Select Test City" id="testCenter1"
								value="%{testCenter1}" />
						</td>
					</tr>
					
						<tr>
							<td>
								Preferred Test City 2
								<span class="manadetory-fields">*</span>
							</td>
							<td>
								<s:select list="testCenterMasterDetails"
									name="testCenter2" label="Name" headerKey=""
									headerValue="Select Test City" id="testCenter2"
									value="%{testCenter2}" />
							</td>
						</tr>
					
					<!--<s:if test='%{showTestCenterSecond=="A"}'>
						<tr>
							<td>
								Preferred Test Centre 3
							</td>
							<td>
								<s:select cssStyle="width:600px;" list="testCenterMasterDetails"
									name="testCenter3" label="Name" cssClass="s" headerKey=""
									headerValue="Select Test Center" id="testCenter3"
									value="%{testCenter3}" />
							</td>

						</tr>
					</s:if>

					<tr>
						<td>
							Language For Written Test
							<span class="manadetory-fields">*</span>
							<br />
						<td colspan="2">
							<s:select list="languageWrittenTestList"
								name="languageWrittenTest" label="Name" headerKey=""
								headerValue="Select Language" id="languageWrittenTest"
								value="%{languageWrittenTest}" />
					--><tr>
						<td colspan="5">
							
						</td>
					</tr>
					<tr>
						<td colspan="5">
							
						</td>
					</tr>
					</table>


					--%>
					 
					<table width="100%">
					
					<tr></tr>
					
					<tr>
<!--						<td></td>-->
						<td width="305">
						
						</td>
						<td align="left" >
							<!--<input type="button" value="Save" class="submitBtn button-gradient" onclick="saveCandiadtePreferences();"/>-->
							
						</td>
						<%--<td align="right" >
							<s:if test='%{preferenceTestCenterMandatory}'>
								<s:if test="%{testCenterUploaded}">
									<input type="button" value="Submit"
										class="submitBtn button-gradient" onclick="frmSubmit()" />
								</s:if>
								<s:else>
									<input type="button" value="Submit"
										class="submitBtn button-gradient" onclick="frmSubmit()"
										disabled="true" />
								</s:else>
							</s:if>
							<s:else>
								<input type="button" value="Submit"
									class="submitBtn button-gradient" onclick="frmSubmit()" />
							</s:else>
						</td>

					--%></tr>

				</table>
				<br />

			
		</div>
	</div>
</div>
</div>


<%--by shekharc--%>
<div id="output"></div>
		<div id="overlay" class="web_dialog_overlay_declr"></div>
		<div id="dialog" class="web_dialog_declr" style="overflow: _scroll;">
		<!-- <table style="width: 99%; margin:5px;"><tr><td> -->
			<table style="width: 100%; border: 0px;" cellpadding="3"
				cellspacing="0">
				<tr>
         			<td class="web_dialog_title_declr" colspan="4">Declaration</td>
         
      			</tr>
				<tr><td colspan="4">
				<%=ConfigurationConstants.getInstance().getapplyPostConfirmation(GenericConstants.APPLYPOSTCONFIRMATION)%>
				</td>
				
				</tr>
				<tr><td align="center" colspan="4"><input type="checkbox" name="declaration" id="declaration" />I accept the above declaration</td>
				</tr>
				<tr id="declar" style="color: red; display: none;text-align: center;">
							<td colspan="4">
								Please confirm your Declaration
							</td>
						</tr>
				<tr><td colspan="4"></td></tr>
				<tr><td colspan="4"></td></tr>
				<tr ><td align="center" colspan="4" >
				<input type="button" name="btnSubmit" value="Submit" onclick="validatePage()" class="submitBtn button-gradient"/>
				<input type="button" name="btnCancel" value="Cancel" onclick="HideDialog()" class="submitBtn button-gradient"/>
				</td>
				 
				</tr>
				</table>
				<!-- </td>
				</tr>
				</table> -->
				</div>


<s:token/>
</s:form>
</div>