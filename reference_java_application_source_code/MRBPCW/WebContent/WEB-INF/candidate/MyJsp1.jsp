<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="com.nseit.generic.util.ConfigurationConstants" %>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<!--[if gte IE 10]>
	<link rel="stylesheet" type="text/css" href="css/IEstyle.css" />
<![endif]-->
  <style>
  #workExp td {padding:7px 0 !important; }
    strong {text-transform:uppercase; }
    .navbar-right{display: none; }
    
    .datetimeinfo{display: none;}
  </style>
    <style type="text/css" media="print">
@page {
    size: auto;   /* auto is the initial value */
    margin: 0;  /* this affects the margin in the printer settings */
}
@page {
  size: A4;
  margin: 0;
}
@media all {
	html, body {
	background: #ccc;
    width: 280mm !important;
    height: 390mm !important;
    margin: 0mm 5mm 0mm 5mm;
  }
  .navbar-nav{
  	display: none;
  }
  table tr td, th{
	   font-weight:normal;
	   word-break: break-all;
	}
	.tamil{
	 font-weight:normal;
	 font-size:12px;
	}	
	
  }
</style>
<div class="container common_dashboard">

	<div >
		<s:hidden id='hddAddressChkBox'></s:hidden>
			
		<div align="left">
			<s:if test="%{#session['SESSION_USER'] != null}">
				<strong>User ID / </strong><span class="tamil"><s:text name="applicationForm.userId"/></span> - <strong><s:label
					value="%{#session['SESSION_USER'].username}" /></strong>
				<br />
				<!--<strong>Course</strong> - <s:label cssStyle="color: black"
					value="%{disciplinName}" />

			--></s:if>
			<s:if test='%{afterApplyVeiwPayment!=null && afterApplyVeiwPayment=="true"}'>
				<strong>Recruitment / </strong><span class="tamil"><s:text name="applicationForm.Recruitment"/></span> - <strong><s:label value="%{disciplineTypeDesc}"></s:label></strong>
				<br/>
				<s:iterator value="postMasterList" status="incr">
				<strong>Preference <s:property value="%{#incr.index+1}"/><span class="tamil"><s:text name="prefer"/></span> <s:property value="%{#incr.index+1}"/></strong> - <strong><s:property /></strong>
  				<br/>	
				</s:iterator>
			</s:if>
			

		</div>
		<br />
		<script type="text/javascript">
		var result=[];
		var rowCount;
		function displayImage(id)
		{
			var modal = document.getElementById('myModal'+id);
	    	modal.style.display = "block";
	    	var modal1 = document.getElementById('imageDdiv'+id);
	    	//modal1.scrollTop = 0;
	    	setTimeout(function(){ 
        	modal1.scrollTop = 0; 
        	}, 30);
	    	if($("#veritext"+id).text()=="Confirmed")
			{
	    		document.getElementById("verify"+id).checked = true;
			}
	    	var i=$("#verify"+id).is(':checked') ? 1 : 0;
	    	if (i==1) 
	    	{
	        	$('#savclose'+id).removeAttr('disabled'); //enable input
	    	}
	    	else 
	    	{
	        	$('#savclose'+id).attr('disabled', true); //disable input
	        }
	    	$('#savclose'+id).unbind('click').click(function(){
	        	modal.style.display = "none";
	        	var pk=$("#candidateDocPk"+id).val();
	        	var name=$("#documentFileName"+id).val();
	        	$.ajax({
	        		url: "CandidateAction_saveAddDocVerify.action",
	        		async: false, 
	        		cache: false,
	        		data: "candidateDocPk="+pk+"&documentFileName="+name,
	        		success:function(response)
	        		{
	        			$("#veritext"+id).text("CONFIRMED");
	        			var res=response.trim();
	        			if(res!=null && res!="")
	        			{
	        				result=res.split(",");
	        			}
	        		},
	        		error:function(response)
	        		{
	        			window.reload();
	        		}
	        	});
	        });
	        $("#verify"+id).change(function()
	        {
	    		var i=$("#verify"+id).is(':checked') ? 1 : 0;
	     		if(i==1)
		    	{
		    		$('#savclose'+id).removeAttr('disabled');
		    	}
		    	if(i==0)
		    	{
		    		$('#savclose'+id).attr('disabled', true);
		    	}
	    	});
		}
		function open_win()
		{
			var dataString = "action=Download Application Form&audittrail=downloaded Application Form";
			$.ajax({
				url: "CandidateAction_AuditTrailForDeclaration.action",
				async: true,
				data: dataString,
				
				error:function(ajaxrequest)
				{
					window.reload();
				},
				success:function(responseText)
				{
					
				}
			});
			window.open("CandidateAction_printFinalPageInJasper.action")			
		}
		function downloadPDF2(){
			
			//request.setCharacterEncoding("UTF-8");
	       	//window.open('index_pdf.jsp');
			//console.log(r1);
			window.open("CandidateAction_downloadPdf.action")
			window.close()
			//downloadPDF(r1);
			 
			}
		function open_win1(id)
		{
			var flag=true;
			 var x=id;
			var rowData="";
			var count=0;
			var docName=[];
			var j=0;
			var k='<s:property value="%{afterApplyVeiwPayment}"/>';
			$('#msg').empty();
			if(k)
			{
				if(rowCount!=0)
				{
					for(var i=0;i<rowCount;i++)
					{
						if($('#myTable tr').eq(i).children(":nth-child(3)").text()=='' || $('#myTable tr').eq(i).children(":nth-child(3)").text()==null)
						{
							count++;
							docName[j]=$('#myTable tr').eq(i).children(":nth-child(2)").text();
							j++;
						}
					}
					
					if(count!=0 && rowCount!=null && rowCount!="" && (count==rowCount))
					{
						alert("Please verify the uploaded documents before submitting the form.");
						flag=false;
						return flag;
					}
					else
					{
						$(window).scrollTop(0);
						if(docName.length!=0)
						{	
							for(var i=0;i<docName.length;i++)
							{
								$('#msg').append('<span style="color:red"><li>Please verify the '+docName[i]+' document.</li><span>');
							}
							flag=false;
							return flag;
						}
					}
				}
			}
			else
			{
				flag=false;
			}
			if(flag)
			{
				var dataString = "action=Download Application Form&audittrail=downloaded Application Form";
				$.ajax({
					url: "CandidateAction_AuditTrailForDeclaration.action",
					async: true,
					data: dataString,
					
					error:function(ajaxrequest)
					{
						window.reload();
					},
					success:function(responseText)
					{
						
					}
				});
				//window.open("CandidateAction_printFinalPageInJasper1.action?disciplineType="+x+"&print=print");
				window.print();
			}
		}	
		function validatePage(){
			//if(!$('#declaration').is(':checked')){
			//	$("#declar").show();
			//}else{
				var flag=true;
				var rowData="";
				var count=0;
				var docName=[];
				var k=0;
				$('#msg').empty();
				if(rowCount!=0)
				{
					for(var i=0;i<rowCount;i++)
					{
						if($('#myTable tr').eq(i).children(":nth-child(3)").text()=='' || $('#myTable tr').eq(i).children(":nth-child(3)").text()==null)
						{
							count++;
							docName[k]=$('#myTable tr').eq(i).children(":nth-child(2)").text();
							k++;
						}
					}
					if(count!=0 && rowCount!=null && rowCount!="" && (count==rowCount))
					{
						alert("Please verify the uploaded documents before submitting the form.");
						flag=false;
						return flag;
					}
					else
					{
						$(window).scrollTop(0);
						if(docName.length!=0)
						{
							for(var i=0;i<docName.length;i++)
							{
								$('#msg').append('<span style="color:red"><li>Please verify the '+docName[i]+' document.</li><span>');
							}
							flag=false;
							return flag;
						}
					}
				}
				if(flag)
				{
					$("#applicationForm").attr('action',"PaymentOnlineAction_showPaymentScreen.action");
					
					$("#applicationForm").submit();
				}
			//}
		}

		function onErrorMessage(id){
			$("#"+id).hide();
		}
		function closeDiv(id)
		{
			var modal = document.getElementById('myModal'+id);
			if($("#veritext"+id).text()!="Confirmed")
			{
				document.getElementById("verify"+id).checked = false;
			}
			modal.style.display = "none";
		}
		$(document).ready(function() {
		 
				rowCount = $('#myTable tr').length;
				$(".subNavBg").hide();
				$('#block9').hide();
			//	setTimeout("checkForImageChange();",2000);
		
				if($("#imageUploaded").val() == "true")
				{
					$("#uploadedImage").show();
				}
				var idprrof='<s:property value="%{IDproof}"/>';
				var myStringArray;
				if(idprrof!=null)
				{
					var array=idprrof.split(",");
				}
				var arrayLength = array.length;
				for ( var i=0;i<array.length;i++) {
				    //console.log(val);
				
					if($.trim(array[i])=='Aadhaar Card')
					{
						$("#checkboxtable").show();
							$("#tr1").show();
					}
					if($.trim(array[i])=='Voter ID card')
					{
						 $("#checkboxtable").show();
							$("#tr2").show();
					}
					if($.trim(array[i])=='PAN Card')
					{
						 $("#checkboxtable").show();
							$("#tr3").show(); 
					}
					if($.trim(array[i])=='Driving Licence')
					{
						$("#checkboxtable").show();
						$("#tr4").show(); 
					}
					if($.trim(array[i])=='Others')
					{
						$("#checkboxtable").show();
						$("#tr5").show(); 
					}
				}
				var examName='<s:property value="%{additionalDetailsBean.examName}"/>';
				var examName1='<s:property value="%{additionalDetailsBean.examName2}"/>';
				var examName2='<s:property value="%{additionalDetailsBean.examName3}"/>';
				var examName3='<s:property value="%{additionalDetailsBean.examName4}"/>';
				var examName4='<s:property value="%{additionalDetailsBean.examName5}"/>';
				var examName5='<s:property value="%{additionalDetailsBean.examName6}"/>';
			
				if(examName!=null && examName!="" && examName1!=null && examName1!=""
					&& examName2!=null && examName2!="" && examName3!=null && examName3!=""
					&& examName4!=null && examName4!="" && examName5!=null && examName5!="")
				{
					var str=examName+", "+examName1+", "+examName2+", "+examName3+", "+examName4+", "+examName5;
					$("#examNam").text(str);
				}
				if(examName=='CR-2010')
				{
					$("#checkboxtable1").show();
					$("#tr11").show();
				}
				if(examName1=='SI-2010')
				{
					$("#checkboxtable1").show();
					$("#tr21").show();
				}
				if(examName2=='CR-2012')
				{
					$("#checkboxtable1").show();
					$("#tr31").show();
				}
				if(examName3=='TNSPYB-2013')
				{
					$("#checkboxtable1").show();
					$("#tr41").show();
				}
				if(examName4=='SI-2015')
				{
					$("#checkboxtable1").show();
					$("#tr51").show();
				}
				if(examName5=='CR-2017')
				{
					$("#checkboxtable1").show();
					$("#tr61").show();
				}
				
				 var categoryVal = $("#categoryVal").text();
						 if (categoryVal == 'General'  || categoryVal == '')
						 {
							 $("#casteTable").hide();
			 			 }
			 			 else
			 			 {
			 				 $("#casteTable").show();
			 			 }	
			function loadPImage(){
					$.ajax({
			        type: 'GET',
			        contentType: 'application/json; charset=utf-8',
			        dataType: 'json',
			        timeout: 10000,
			        url: 'PhotoImage.jpg',
			        error: function (err) {
		        			var results = err.responseText;
		        			//the results is a base64 string.  convert it to an image and assign as 'src'
		                    document.getElementById("inputStreamForImage").src = "data:image/png;base64," + results;
		    		},
			        success: function (data) {
				        var results = data;
			             //the results is a base64 string.  convert it to an image and assign as 'src'
			             document.getElementById("inputStreamForImage").src = "data:image/png;base64," + results;
			        }   	 
			    	});
		    	}
		    	//loadPImage();
		    	
		    	function loadImage(){
					$.ajax({
			        type: 'GET',
			        contentType: 'application/json; charset=utf-8',
			        dataType: 'json',
			        timeout: 10000,
			        url: 'SignatureImage.jpg',
			        error: function (err) {
		        			var results = err.responseText;
		        			//the results is a base64 string.  convert it to an image and assign as 'src'
		                    document.getElementById("inputStreamForImage1").src = "data:image/png;base64," + results;
		    		},
			        success: function (data) {
				        var results = data;
			             //the results is a base64 string.  convert it to an image and assign as 'src'
			             document.getElementById("inputStreamForImage1").src = "data:image/png;base64," + results;
			        }   	 
			    	});
		    	}
		    	//loadImage();
			});
	</script>
<style>
/* The Modal (background) */
.modal {
     display: none; /* Hidden by default */
    position: fixed; /* Stay in place */
    z-index: 1; /* Sit on top */
    padding-top: 40px; /* Location of the box */
    left: 0;
    top: 0;
    width: 100%; /* Full width */
    height: 100%; /* Full height */
    overflow: auto; /* Enable scroll if needed */
    background-color: rgb(0,0,0); /* Fallback color */
    background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}

/* Modal Content */ 
.modal-content {
    background-color: #fefefe;
    margin: auto;
    padding: 5px 20px 20px 20px;
    border: 1px solid #888; height:520px;
    width: 80%; display:block; overflow-y:hidden;
}

.modal-content img {width:100%; }

.close {
    display: block;
    position: absolute;
    width:30px;
    height:30px;
    top: -3px;
    right: 2px;
    opacity:100 !important;
}
</style>
	<div class="container"><br/><div id="msg"></div></div>
	<div class="">
		<h1 class="pageTitle">
		
		<div     align="right">
		<s:if test='%{afterApplyVeiwPayment=="true"}'>
			<%--<input type="button" value="PDF Print" id="<s:property value='%{disciplineType}'/>"
									class="printBtn button-gradient" onclick="open_win1(this.id)"/>
				--%>
				<s:if test='%{appPrint!=null && appPrint=="printApp"}'>
				<input type="button" value="Download" id="<s:property value='%{disciplineType}'/>" class="printBtn button-gradient" onclick="downloadPDF2();"/>
				</s:if>
		</s:if>
		<s:else>
			<%--<input type="button" value="PDF Print" class="printBtn button-gradient" onclick="open_win();"/>
			--%><input type="button" value="Print2" class="printBtn button-gradient" onclick="downloadPDF2();"/>
		</s:else>
		</div>
			 Personal Details / <span class="tamil"><s:text name="applicationForm.personalDetails"/></span>
			 
		</h1>
		
		
	</div>

		<div class="hr-underline2"></div>

		<s:form id="applicationForm" name="formDtls" action="CandidateAction_updateCandidateStage">
		 <s:hidden name="auditFlag" id="auditFlag" value="Applyandmakepayment"></s:hidden>
			<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
			<s:hidden name="viewFlag" value="true"></s:hidden>
			<s:hidden name="disciplineType" value="%{disciplineType}"></s:hidden>
			<s:hidden name="isDataFound"  value='<s:property value="formConfirm"/>' />
			<div style="display: block; min-height: 300px; height: auto;">
				<div style="display: block;">
					<table class="contenttable" border="0" width="100%" >
						<tr>
							<td class="col1"  ><!--
								Course
							--></td>
							<td  class="col2">
								<!--<strong><s:label value="%{disciplineTypeDesc}" />
								--></strong>
							</td>
							<td  class="col2" align="right" rowspan="5" id="photoImage" valign="top">
								<!--  <img height="100" width="100" src='PhotoImage.jpg'
									onerror="onErrorMessage('photoImage')"></img>-->
									<img src="PhotoImage.jpg" height="100" width="100" id="inputStreamForImage" 
									onerror="onErrorMessage('photoImage')"></img>
							</td>
						</tr>
						<tr>
							<td width="38%">
								Name / <span class="tamil"><s:text name="applicationForm.name"/></span>
							</td>

							<td>
								<strong> <s:label
										value="%{personalDetailsBean.candidateFirstName}"></s:label>
									&nbsp;<s:label value="%{personalDetailsBean.candidateLastName}"></s:label>
								</strong>
							</td>

						</tr>
						<s:iterator value="candiateRelationDetailsList" status="stat"
							var="currentObject">
							<tr>
								<td>
									<s:label value="%{relationDesc}"
										name="candiateRelationDetailsList[%{#stat.index}].relationDesc"></s:label>
								</td>
								<td>
									<strong><s:label
											name="candiateRelationDetailsList[%{#stat.index}].firstName"
											value="%{firstName}" label="firstName"></s:label>
									</strong>
									<strong> <s:label
											name="candiateRelationDetailsList[%{#stat.index}].middleName"
											value="%{middleName}" label="middleName"></s:label>
									</strong>
									<strong> <s:label
											name="candiateRelationDetailsList[%{#stat.index}].lastName"
											value="%{lastName}" label="lastName"></s:label>
									</strong>
								</td>

							</tr>
						</s:iterator>
						<tr>
							<td>
								Date Of Birth / <span class="tamil"><s:text name="applicationForm.dateOfBirth"/></span>
							</td>
							<td>
								<strong><s:label value="%{personalDetailsBean.dateOfBirth}" id="dateOfBirth" />
								</strong>
							</td>
						</tr>
						<tr>
							<td width="170">
								Father's Name / <span class="tamil"><s:text name="applicationForm.fatherName"/></span>
							</td>

							<td colspan="2">
								<strong> <s:label
										value="%{personalDetailsBean.fatherFirstName}"></s:label>
									&nbsp;<s:label value="%{personalDetailsBean.fatherLastName}"></s:label>
								</strong>
							</td>

						</tr>
						<tr>
							<td width="170">
								Mother's Name / <span class="tamil"><s:text name="applicationForm.motherName"/></span>
							</td>

							<td colspan="2">
								<strong> <s:label
										value="%{personalDetailsBean.motherFirstName}"></s:label>
									&nbsp;<s:label value="%{personalDetailsBean.motherLastName}"></s:label>
								</strong>
							</td>
						</tr>
						<tr>
							<td>
								Gender / <span class="tamil"><s:text name="applicationForm.gender"/></span>
							</td>
							<td colspan="2">
								<strong><s:label value="%{genderValDesc}" id="gender" />
								</strong>
							</td>
						</tr>
						<tr>
							<td>
								Marital Status / <span class="tamil"><s:text name="applicationForm.maritalStatus"/></span>
							</td>
							<td colspan="2">
								<strong><s:label
										value="%{mariatalStatus}" id="mariatalStatus" />
								</strong>
							</td>
						</tr>
						<tr>
							<td>
								Identification Marks 1 / <span class="tamil"><s:text name="applicationForm.id1"/></span>
							</td>
							<td>
								<strong><s:label
										value="%{idMarks}" id="idMarks" />
								</strong>
							</td>
						</tr>
						<s:if test="%{idMarks1!=null && idMarks1!=''}">
							<tr>
								<td>
									Identification Marks 2 / <span class="tamil"><s:text name="applicationForm.id2"/></span>
								</td>
								<td>
									<strong><s:label
											value="%{idMarks1}" id="idMarks1" />
									</strong>
								</td>
							</tr>
						</s:if>
						<tr>
								<td>
									Nationality / <span class="tamil"><s:text name="applicationForm.nationality"/></span>
								</td>
								<td>
									<strong><s:label
											value="%{personalDetailsBean.nationalityName}" id="idMarks1" />
									</strong>
								</td>
							</tr>
						<tr>
							<td>
								Personal Photo ID / <span class="tamil"><s:text name="applicationForm.altpersonalId"/></span>
							</td>
							<%--<td>
								<strong><s:label
										value="%{IDproof}" id="IDproof" />
								</strong>
							</td>
						--%></tr>
						<tr>
							<td>&nbsp;</td>
					    	 <td colspan="3">
						    	 <table width="100%" cellspacing="0" cellpadding="0" border="1" id="checkboxtable" style="display:none;" class="personsl-dtl" >
						    	 	<thead>
							    	 	<tr id="th">
							    	 		<th align="left" width="20%">Personal Photo ID Details / <span class="tamil"><s:text name="applicationForm.altpersonalID"/></span></th>
							    	 		<th align="left" width="20%">ID Number / <span class="tamil"><s:text name="applicationForm.idNumber"/></span></th>
							    	 		<th align="left" width="20%">Date of Issue / <span class="tamil"><s:text name="applicationForm.dateOfIssue"/></span></th>
							    	 		<th align="left" width="20%">Place of Issue / <span class="tamil"><s:text name="applicationForm.placeOfIssue"/></span></th>
							    	 		<th align="left" width="20%">Issuing Authority / <span class="tamil"><s:text name="applicationForm.authority"/></span></th>
							    	 	</tr>
									</thead>
									<tbody>
										<tr id="tr1" style="display:none;">
							    	 		<td><strong><s:label value="%{IDProofPersonalID2}"id="IDProofPersonalID2" /><span class="tamil"><s:text name="aadhar"/></span></strong>
							    	 		<td><strong><s:label value="%{IdNumber2}"id="IdNumber2" /></strong></td>
							    	 		<td><strong><s:label value="%{dateOfIssue2}"id="dateOfIssue2" /></strong></td>
							    	 		<td><strong><s:label value="%{placeOfIssue2}"id="placeOfIssue2" /></strong></td>
							    	 		<td><strong><s:label value="%{IssuingAuthority2}"id="IssuingAuthority2" /></strong></td>
							    	 	</tr>
							    	 	 <tr id="tr2" style="display:none;">
							    	 		<td><strong><s:label value="%{IDProofPersonalID1}"id="IDProofPersonalID1" /><span class="tamil"><s:text name="voter"/></span></strong>
							    	 		<td><strong><s:label value="%{IdNumber1}"id="IdNumber1" /></strong></td>
							    	 		<td><strong><s:label value="%{dateOfIssue1}"id="dateOfIssue1" /></strong></td>
							    	 		<td><strong><s:label value="%{placeOfIssue1}"id="placeOfIssue1" /></strong></td>
							    	 		<td><strong><s:label value="%{IssuingAuthority1}"id="IssuingAuthority1" /></strong></td>
							    	 	</tr>
							    	 	<tr id="tr3" style="display:none;">
						
							    	 		
							    	 		<td><strong><s:label value="%{IDProofPersonalID}"id="IDProofPersonalID" /><span class="tamil"><s:text name="pan"/></span></strong>
							    	 		<td><strong><s:label value="%{IdNumber}"id="IdNumber" /></strong></td>
							    	 		<td><strong><s:label value="%{dateOfIssue}"id="dateOfIssue" /></strong></td>
							    	 		<td><strong><s:label value="%{placeOfIssue}"id="placeOfIssue" /></strong></td>
							    	 		<td><strong><s:label value="%{IssuingAuthority}"id="IssuingAuthority" /></strong></td>
							    	 	</tr>
							    	 	<tr id="tr4" style="display:none;">

	    	 		
	    	 		<td><strong><s:label value="%{IDProofPersonalID3}"id="IDProofPersonalID3" /></strong><span class="tamil"><s:text name="driving"/></span></td>
	    	 		<td><strong><s:label value="%{IdNumber3}"id="IdNumber3" /></strong></td>
	    	 		<td><strong><s:label value="%{dateOfIssue3}"id="dateOfIssue3" /></strong></td>
	    	 		<td><strong><s:label value="%{placeOfIssue3}"id="placeOfIssue3" /></strong></td>
	    	 		<td><strong><s:label value="%{IssuingAuthority3}"id="IssuingAuthority3" /></strong></td>
	    	 	</tr>
	    	 	<tr id="tr5" style="display:none;">

	    	 		
	    	 		<td><strong><s:label value="%{IDProofPersonalID4}"id="IDProofPersonalID4" /></strong></td>
	    	 		<td><strong><s:label value="%{IdNumber4}"id="IdNumber4" /></strong></td>
	    	 		<td><strong><s:label value="%{dateOfIssue4}"id="dateOfIssue4" /></strong></td>
	    	 		<td><strong><s:label value="%{placeOfIssue4}"id="placeOfIssue4" /></strong></td>
	    	 		<td><strong><s:label value="%{IssuingAuthority4}"id="IssuingAuthority4" /></strong></td>
	    	 	</tr>
									</tbody>
						    	 </table>
					    	 </td>
    	 				</tr>
						<tr>
							<td>
								Permanent Address / <span class="tamil"><s:text name="applicationForm.permanentAddress"/></span>
							</td>
						</tr>
						<tr>
							<td>
								Address  / <span class="tamil"><s:text name="applicationForm.add"/></span>
							</td>
							<td colspan="2">
								<strong><s:label value="%{addressBean.addressFiled1}"
										id="addressFiled1" />
								</strong>
							</td>
						</tr>
						<s:if test='%{addressBean.alternateAddressFiled2!=null && addressBean.alternateAddressFiled2!=""}'>
							<tr>
								<td>
								</td>
								<td colspan="2">
									<strong><s:label value="%{addressBean.addressFiled2}"
											id="addressFiled2" />
									</strong>
								</td>
							</tr>
						</s:if>
						<s:if test='%{addressBean.alternateAddressFiled3!=null && addressBean.alternateAddressFiled3!=""}'>
							<tr>
								<td>
								</td>
								<td colspan="2">
									<strong><s:label value="%{addressBean.addressFiled3}"
											id="addressFiled3" />
									</strong>
								</td>
							</tr>
						</s:if>
						<tr>
							<td>
								Residential locality / <span class="tamil"><s:text name="applicationForm.resident"/></span>
							</td>
							<td colspan="2">
								<strong><s:label value="%{addressBean.addressFiled4}"
										id="addressFiled4" />
								</strong>
							</td>
						</tr>
						<%--<tr>
							<td>
								&nbsp;
							</td>
							<td colspan="2">
								<span class="lighttext">Country / <span class="tamil"><s:text name="applicationForm.country"/></span></span>
								<strong><s:label value="%{countryValDesc}"
										id="countryVal" />
								</strong>
							</td>
						</tr>
						--%><!-- For other countries -->
						<s:if test='%{stateValDesc!=null && stateValDesc=="Tamil Nadu"}'>
							<tr id="stateDropdown">
								<td>
									State / Union Territory / <span class="tamil"><s:text name="applicationForm.stateUnion"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{stateValDesc}"
											id="stateValDesc" />
									</strong>
								</td>
							</tr>
							<tr id="stateDropdown">
								<td>
									District/City / <span class="tamil"><s:text name="additional.district"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{districtValDesc}"
											id="districtVal" />
									</strong>
								</td>
							</tr>
							<tr id="stateDropdown">
								<td>
									Police Station in  whose Jurisdiction your residence falls / <span class="tamil"><s:text name="applicationForm.city"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{addressBean.cityName}"
											id="cityName" />
									</strong>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr id="stateDropdown">
								<td>
									State / Union Territory / <span class="tamil"><s:text name="applicationForm.stateUnion"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{stateValDesc}"
											id="stateVal" />
									</strong>
								</td>
							</tr>
							<tr id="stateDropdown">
								<td>
									District/City / <span class="tamil"><s:text name="additional.district"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{districtValother}"
											id="districtValother" />
									</strong>
								</td>
							</tr>
							<tr id="stateDropdown">
								<td>
									Police Station in  whose Jurisdiction your residence falls / <span class="tamil"><s:text name="applicationForm.city"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{cityNameother}"
											id="cityNameother" />
									</strong>
								</td>
							</tr>
						</s:else>
						<tr id="stateDropdown">
							<td>
								Pincode / <span class="tamil"><s:text name="applicationForm.pincode"/></span>
							</td>
							<td colspan="2">
								<%--<s:if test='%{stateValDesc!=null && stateValDesc=="Tamil Nadu"}'>
									<span class="lighttext">State / Union Territory / <span class="tamil"><s:text name="applicationForm.stateUnion"/></span></span>
									<strong><s:label value="%{stateValDesc}" id="stateVal" />
									</strong> &nbsp;<br/>
									<span class="lighttext">District/City / <span class="tamil"><s:text name="additional.district"/></span></span>
										<strong><s:label value="%{districtValDesc}" id="districtVal" />
									</strong> &nbsp;<br/>
									<span class="lighttext">Police Station in  whose Jurisdiction your residence falls / <span class="tamil"><s:text name="applicationForm.city"/></span></span>
										<strong><s:label value="%{addressBean.cityName}"id="cityName" />
										</strong>
								</s:if>
							 	<s:else>
							 		<span class="lighttext">State / Union Territory / <span class="tamil"><s:text name="applicationForm.stateUnion"/></span></span>
									<strong><s:label value="%{stateValDesc}" id="stateVal" />
									</strong> &nbsp;<br/>
									<span class="lighttext">District/City / <span class="tamil"><s:text name="additional.district"/></span></span>
										<strong><s:label value="%{districtValother}" id="districtValother" />
										</strong> &nbsp;<br/>
										<span class="lighttext">Police Station in  whose Jurisdiction your residence falls / <span class="tamil"><s:text name="applicationForm.city"/></span></span>
										<strong><s:label value="%{cityNameother}" id="cityNameother" />
										</strong>
							 	</s:else>
							 <br/>
								<span class="lighttext">Pincode / <span class="tamil"><s:text name="applicationForm.pincode"/></span></span>--%>
								<strong><s:label value="%{addressBean.pinCode}"
										id="pinCode" />
								</strong>
							</td>
						</tr>
						<tr>
							<td>
								Address for Communication / <span class="tamil"><s:text name="application.copyAdd"/></span>
							</td>
						</tr>
						<tr>
							<td  >
								 Address / <span class="tamil"><s:text name="applicationForm.add"/></span>
								<br />
							</td>
							<td colspan="2">
								<strong><s:label
										value="%{addressBean.alternateAddressFiled1}"
										id="alternateAddressFiled1" />
								</strong>
							</td>
						</tr>
						<s:if test='%{addressBean.alternateAddressFiled2!=null && addressBean.alternateAddressFiled2!=""}'>
							<tr>
							<td></td>
								<td colspan="2">
									<strong><s:label
											value="%{addressBean.alternateAddressFiled2}"
											id="alternateAddressFiled2" />
									</strong>
								</td>
							</tr>
						</s:if>
						<s:if test='%{addressBean.alternateAddressFiled3!=null && addressBean.alternateAddressFiled3!=""}'>
							<tr>
								<td>
								</td>
								<td colspan="2">
									<strong><s:label
											value="%{addressBean.alternateAddressFiled3}"
											id="alternateAddressFiled3" />
									</strong>
								</td>
							</tr>
						</s:if>
						<tr>
							<td>
								Residential locality / <span class="tamil"><s:text name="applicationForm.resident"/></span>
							</td>
							<td colspan="2">
								<strong><s:label
										value="%{addressBean.alternateAddressFiled4}"
										id="alternateAddressFiled4" />
								</strong>
							</td>
						</tr>
						<s:if test='%{altStateValDesc!=null && altStateValDesc=="Tamil Nadu"}'>
							<tr id="alternateStateDisplay">
								<td>
									State / Union Territory / <span class="tamil"><s:text name="applicationForm.stateUnion"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{altStateValDesc}"
											id="altStateVal" />
									</strong>
								</td>
							</tr>
							<tr id="alternateStateDisplay">
								<td>
									District/City / <span class="tamil"><s:text name="additional.district"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{altDistrictValDesc}"
											id="altDistrictVal" />
									</strong>
								</td>
							</tr>
							<tr id="alternateStateDisplay">
								<td>
									Police Station in  whose Jurisdiction your residence falls / <span class="tamil"><s:text name="applicationForm.city"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{addressBean.alternateCity}"
											id="alternateCity" />
									</strong>
								</td>
							</tr>
						</s:if>
						<s:else>
							<tr id="alternateStateDisplay">
								<td>
									State / Union Territory / <span class="tamil"><s:text name="applicationForm.stateUnion"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{altStateValDesc}"
											id="altStateVal" />
									</strong>
								</td>
							</tr>
							<tr id="alternateStateDisplay">
								<td>
									District/City / <span class="tamil"><s:text name="additional.district"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{altDistrictValOthers}"
											id="altDistrictValOther" />
									</strong>
								</td>
							</tr>
							<tr id="alternateStateDisplay">
								<td>
									Police Station in  whose Jurisdiction your residence falls / <br/> <span class="tamil"><s:text name="applicationForm.city"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{alternateCityother}"
											id="alternateCityOther" />
									</strong>
								</td>
							</tr>
						</s:else>
						<%--<tr>
							<td>
								&nbsp;
							</td>
							<td colspan="2">
								<span class="lighttext">Country / <span class="tamil"><s:text name="applicationForm.country"/></span></span>
								<strong><s:label value="%{altCountryValDesc}"
										id="altCountryVal" />
								</strong>
							</td>
						</tr>
						--%><tr id="alternateStateDisplay">
							<td>
								Pincode / <span class="tamil"><s:text name="applicationForm.pincode"/></span>
							</td>
							<td colspan="2"><%--
								<s:if test='%{altStateValDesc!=null && altStateValDesc=="Tamil Nadu"}'>
									 <span class="lighttext">State / Union Territory / <span class="tamil"><s:text name="applicationForm.stateUnion"/></span></span>
									<strong><s:label value="%{altStateValDesc}" id="altStateVal" />
									</strong> &nbsp; <br/>
										<span class="lighttext">District/City / <span class="tamil"><s:text name="additional.district"/></span></span>
										<strong><s:label value="%{altDistrictValDesc}"
												id="altDistrictVal" />
										</strong> &nbsp;<br/>
										<span class="lighttext">Police Station in  whose Jurisdiction your residence falls / <span class="tamil"><s:text name="applicationForm.city"/></span></span>
										<strong><s:label value="%{addressBean.alternateCity}"
												id="alternateCity" />
										</strong>
									 
								</s:if>
								<s:else>
									 	 <span class="lighttext">State / Union Territory / <span class="tamil"><s:text name="applicationForm.stateUnion"/></span></span>
									<strong><s:label value="%{altStateValDesc}" id="altStateVal" />
									</strong> &nbsp; <br/>
										<span class="lighttext">District/City / <span class="tamil"><s:text name="additional.district"/></span></span>
										<strong><s:label value="%{altDistrictValOthers}"
												id="altDistrictValOther" />
										</strong> &nbsp;<br/>
										<span class="lighttext">Police Station in  whose Jurisdiction your residence falls / <span class="tamil"><s:text name="applicationForm.city"/></span></span>
										<strong><s:label value="%{alternateCityother}"
												id="alternateCityOther" />
										</strong>
									 
								</s:else>
								<br/>
								<span class="lighttext">Pincode / <span class="tamil"><s:text name="applicationForm.pincode"/></span></span>
								--%><strong><s:label
										value="%{addressBean.alternatePinCode}" id="alternatePinCode" />
								</strong>
							</td>
						</tr>
						<tr>
							<td>
								Mobile Number / <span class="tamil"><s:text name="applicationForm.mobileNumber"/></span>
							</td>
							<td colspan="2">
								<strong><s:label
										value="%{personalDetailsBean.mobileNo}" id="mobileNo" />
								</strong>
							</td>
						</tr>
						<tr>
							<td>
								Email Id / <span class="tamil"><s:text name="applicationForm.emailId"/></span>
							</td>
							<td colspan="2">
								<strong><s:label value="%{personalDetailsBean.email}"
										id="email" />
								</strong>
							</td>
						</tr>
						<tr>
							<td>
								Religion / <span class="tamil"><s:text name="applicationForm.religionBelief"/></span>
							</td>
							<td colspan="2">
								<strong><s:label value="%{religionBelief}"
										id="religionBelief" />
								</strong>
							</td>
						</tr>
						<s:if test='%{religionBelief=="Other"}'>
							<tr>
								<td>
									Other Religion / <span class="tamil"><s:text name="applicationForm.otherreligionBelief"/></span>
								</td>
								<td colspan="2">
									<strong><s:label value="%{religionBeliefOthers}"
											id="religionBeliefOthers" />
									</strong>
							</td>
							</tr>
						</s:if>
						<tr>
							<td>
								Do you possess Community certificate issued by Tamilnadu Govt  / <span class="tamil"><s:text name="application.subCasteIssueGovt"/></span>
							</td>
							<s:if test='%{governmntTamil=="Y"}'>
								<td colspan="2">
									<strong><s:label value="Yes"
											id="governmntTamil" />
									</strong>
								</td>
							</s:if>
							<s:else>
								<td colspan="2">
									<strong><s:label value="No"
											id="governmntTamil" />
									</strong>
								</td>
							</s:else>
						</tr>
						<tr>
							<td>
								Community / <span class="tamil"><s:text name="applicationForm.category"/></span>
							</td>
							<td colspan="2">
								<strong><s:label value="%{categoryValDesc}"
										id="categoryVal" />
								</strong>
							</td>
						</tr>
						<s:if test='%{categoryValDesc!="OC"}'>
							<tr>
							<td>&nbsp;</td>
							<td colspan="3">
								<table width="100%" cellspacing="0" cellpadding="0" border="1"  id="casteTable" class="personsl-dtl" style="display:none;">
		    	 				<thead>
						    	 	<tr>
						    	 		<th align="left" width="20%">Sub Caste / <span class="tamil"><s:text name="applicationForm.subcaste"/></span></th>
						    	 		<th align="left" width="20%">Community Certificate Number / <span class="tamil"><s:text name="applicationForm.certNumber"/></span></th>
						    	 		<th align="left" width="20%">Designation Of Issuing Authority / <span class="tamil"><s:text name="applicationForm.issuingAuth"/></span></th>
						    	 		<th align="left" width="20%">Place of Issue / <span class="tamil"><s:text name="applicationForm.placeOfIssue"/></span></th>
						    	 		<th align="left" width="20%">Date Of Issue Of Certificate / <span class="tamil"><s:text name="applicationForm.DOBissue"/></span></th>
						    	 		<%--<th align="left" width="20%">Medium Of Instruction</th>
						    	 	--%></tr>
								</thead>
								<tbody>
									<tr>
						    	 		<td><strong><s:label value="%{Subcaste}"/></strong></td>
						    	 		<td><strong><s:label value="%{ComCertificateNumber}"/></strong></td>
						    	 		<td><strong><s:label value="%{DesignationIssuingAuthority}"/></strong></td>
						    	 		<td><strong><s:label value="%{placeOfIssueSubcaste}"/></strong></td>
						    	 		<td><strong><s:label value="%{DateOfCertificate}"/></strong></td>
						    	 		<%--<td><s:select list="MediumInstrutionCasteList" id="categoryVal" name="MediumInstrutionCaste" headerValue = "Select" cssClass="tbsmall1"   value="%{MediumInstrutionCaste}" ></s:select></td> 
						    	 	--%></tr>
						    	 	 
								</tbody>
				    	 	</table>
				    	 	</td>
						</tr>		
					</s:if>
					
					</table>
					<br />
					<br />
					<table>
						<s:if test='%{educationDetailsDisplayFlag}'>
							<tr>
								<td colspan="2">
									<h1 class="pageTitle" >
										Academic Details / <span class="tamil"><s:text name="academicDetails.academicDetails"/></span>
									</h1>
									<div class="hr-underline2"></div>
									<div style="width: 1110px; overflow: auto;">
									<table width="100%" border="1" class="personsl-dtl" id="educationformDetails" >

										<tr style="display:none;">
											<th bgcolor="#FFFFFF" colspan="3">
												Degree Selected
											</th>

											<th bgcolor="#FFFFFF" align="left" colspan="5">
												<s:label value="%{degreeTypeVal}" label="degreeTypeVal"
													></s:label>
											</th>
										</tr>
										 
										<tr>
										 <th width="10%" align="left" bgcolor="#FFFFFF">Examination<span class="tamil"><s:text name="academics.examination"/></span></th>
	        						<th width="10%" align="left" bgcolor="#FFFFFF">Registration Number<span class="tamil"><s:text name="academics.registration"/></span></th>
	     							  <th width="13%" align="left" bgcolor="#FFFFFF">Name Of the School / College / Institute<span class="tamil"><s:text name="academics.name"/></span></th>
	     							  <th width="10%" align="left" bgcolor="#FFFFFF">Degree Course<span class="tamil"><s:text name="academics.degree"/></span></th>
	     							 <th width="10%" align="left" bgcolor="#FFFFFF">Major Subject<span class="tamil"><s:text name="academics.major"/></span></th>
<%--	   							     <th rowspan="2"   align="left" bgcolor="#FFFFFF"><strong><span class="tamil"><s:text name="academics.result"/></span></strong></th>--%>
<%--	   							     <th rowspan="2"  align="left" bgcolor="#FFFFFF"><strong><span class="tamil"><s:text name="academics.percentage"/></span></strong></th>--%>
	   							    <th width="10%" align="left" bgcolor="#FFFFFF">Month/Year Of Passing<span class="tamil"><s:text name="academics.month"/></span></th>
<%--	   							     <th rowspan="2"   align="left" bgcolor="#FFFFFF"><strong><span class="tamil"><s:text name="academics.number"/></span></strong></th>--%>
	   							     <th width="14%" align="left" bgcolor="#FFFFFF">Name of the Board / University (Degree should be recognized by UGC)<span class="tamil"><s:text name="academics.desgination"/></span></th>
	   							      <th width="13%" align="left" bgcolor="#FFFFFF">Date Of Issue Of Marksheet / Certificate<span class="tamil"><s:text name="academics.dateCerti"/></span></th>
	   							      <th width="10%" align="left" bgcolor="#FFFFFF">Medium Of Instruction<span class="tamil"><s:text name="academics.medium"/></span></th>
										
								</tr>
							<tbody>
										<s:iterator value="educationDtlsList" status="stat"
											var="currentObject">

											<tr>
												<td>
													<s:hidden
														name="degreeSelected"
														value="%{degreeSelected}" label="degreeSelected"
														id="degreeSelectedUser" />
													<strong><s:label value="%{examination}" label="examination"></s:label>
														<%--<s:if test='%{examination=="10th / SSLC"}'>
															<span class="tamil"><s:text name="1"/></span>
														</s:if>
														<s:if test='%{examination=="XII / HSC / ITI"}'>
															<span class="tamil"><s:text name="2"/></span>
														</s:if>
														<s:if test='%{examination=="Diploma"}'>
															<span class="tamil"><s:text name="3"/></span>
														</s:if>
														<s:if test='%{examination=="Under Graduate"}'>
															<span class="tamil"><s:text name="4"/></span>
														</s:if>
														<s:if test='%{examination=="Post Graduate"}'>
															<span class="tamil"><s:text name="5"/></span>
														</s:if>
													--%></strong>

												</td>
												<td class="wordWrap">
													<strong><s:label value="%{registrationNo}" label="registrationNo"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{university}" label="university"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{degreeSubject}" label="degreeSubject"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{majorSubject}" label="majorSubject"
														></s:label></strong>
												</td>
												<%--<td class="wordWrap">
													<strong><s:label value="%{marks_grade}" label="marks_grade"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{percentage}" label="percentage"
														></s:label></strong>
												</td>
												--%><td class="wordWrap">
													<strong><s:label value="%{dateOfPassing}" label="dateOfPassing"
														></s:label></strong>
												</td>
												<%--<td class="wordWrap">
													<s:if test='%{noOfAttempts==0}'>
														<strong><s:label value="" label="noOfAttempts"
															></s:label></strong>
													</s:if>
													<s:else>
														<strong><s:label value="%{noOfAttempts}" label="noOfAttempts"
															></s:label></strong>
													</s:else>
												</td>
												--%><td class="wordWrap">
													<strong><s:label value="%{issueAuthority}" label="issueAuthority"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{dateOfIssue}" label="dateOfIssue"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{partTimeFullTimeSelected}" label="partTimeFullTimeSelected"
														></s:label></strong>
												</td>
											</tr>
										</s:iterator>
										</tbody>
									</table>
									</div>
									<br/>
								
											<span class="tamil"><s:text name="academics.studiedTamil"/></span>&nbsp;&nbsp;
											<strong><s:label value="%{sslcTamil}"></s:label></strong>
									
									<br/>
									<br/>
									<s:if test='%{addAcademicDtlsList.size()!=0}'>
									<div><h1 class="pageTitle">Additional Qualifications / <span class="tamil"><s:text name="academicDetails.additionalDetails"/></span></h1>
									<div class="hr-underline2"></div>
									<div style="width: 1110px; overflow: auto;">
									<table width="100%" border="1" class="personsl-dtl"
										id="educationformDetails">
										 
											<tr>
											<th rowspan="2" width="10%" align="left" bgcolor="#FFFFFF"> Examination<span class="tamil"><s:text name="academics.examination"/></span></th>
	        						<th rowspan="2" width="7%" align="left" bgcolor="#FFFFFF">Registration Number<span class="tamil"><s:text name="academics.registration"/></span></th>
	     							  <th rowspan="2" width="7%" align="left" bgcolor="#FFFFFF">Name Of the School / College / Institute<span class="tamil"><s:text name="academics.name"/></span></th>
	     							  <th rowspan="2" width="7%" align="left" bgcolor="#FFFFFF">Degree Course<span class="tamil"><s:text name="academics.degree"/></span></th>
	     							<th rowspan="2" width="7%" align="left" bgcolor="#FFFFFF">Major Subject<span class="tamil"><s:text name="academics.major"/></span></th>
<%--	   							     <th rowspan="2"   align="left" bgcolor="#FFFFFF"><strong><span class="tamil"><s:text name="academics.result"/></span></strong></th>--%>
<%--	   							     <th rowspan="2"  align="left" bgcolor="#FFFFFF"><strong><span class="tamil"><s:text name="academics.percentage"/></span></strong></th>--%>
	   							    <th rowspan="2" width="9%" align="left" bgcolor="#FFFFFF">Month/Year Of Passing<span class="tamil"><s:text name="academics.month"/></span></th>
<%--	   							    <th rowspan="2" width="10%" align="left" bgcolor="#FFFFFF"><strong><span class="tamil"><s:text name="academics.number"/></span></strong></th>--%>
	   							    <th rowspan="2" width="7%" align="left" bgcolor="#FFFFFF">Name of the Board / University (Degree should be recognized by UGC)<span class="tamil"><s:text name="academics.desgination"/></span></th>
	   							      <th rowspan="2" width="9%" align="left" bgcolor="#FFFFFF">Date Of Issue Of Marksheet / Certificate<span class="tamil"><s:text name="academics.dateCerti"/></span></th>
	   							      <th rowspan="2" width="7%" align="left" bgcolor="#FFFFFF">Medium Of Instruction<span class="tamil"><s:text name="academics.medium"/></span></th>
											
											
											
										
											
											
											
											
											
											</tr>
									<tbody>
										<s:iterator value="addAcademicDtlsList" status="stat"
											var="currentObject">
											<tr>
												<td>
													<s:hidden
														name="addAcademicDtlsList[%{#stat.index}].degreeSelected"
														value="%{degreeSelected}" label="degreeSelected"
														id="degreeSelectedUser" />
													<strong><s:label value="%{addExam}" label="addExam"></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{addRegNo}" label="addRegNo"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{addUniversity}" label="addUniversity"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{addDegreeCourse}" label="addDegreeCourse"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{addMajorCourse}" label="addMajorCourse"
														></s:label></strong>
												</td>
												<%--<td class="wordWrap">
													<strong><s:label value="%{addResult}" label="addResult"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{addPercentage}" label="addPercentage"
														></s:label></strong>
												</td>
												--%><td class="wordWrap">
													<strong><s:label value="%{addDateofPassing}" label="addDateofPassing"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{addAuthority}" label="addAuthority"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{addDateOfIssue}" label="addDateOfIssue"
														></s:label></strong>
												</td>
												<td class="wordWrap">
													<strong><s:label value="%{addMedium}" label="addMedium"
														></s:label></strong>
												</td>
												
											</tr>
										</s:iterator>
										</tbody>
									</table>
									</div>
									</div>
									</s:if>
								</td>
							</tr>
						</s:if>
						</table>
						<br/>
						<br/>
						<table width="100%" border="0" id="workExp">
							<tr>
								<td colspan="4">
									<h1 class="pageTitle">
										Work Experience / <span class="tamil"><s:text name="workEXP.title"/></span>
									</h1>
									<div class="hr-underline2"></div>
							    </td>
							</tr>
							<tr>
								<td style="width: 34%">
										Whether you are a Government Servant / <span class="tamil"><s:text name="applicationForm.govCert"/></span>
								</td>
								<td class="wordWrap" colspan="3" valign="top">
								<s:if test='%{Govtemp=="Y"}'>
										<strong><s:label value="Yes"></s:label></strong>
								</s:if>
								<s:if test='%{Govtemp=="N"}'>
										<strong><s:label value="No"></s:label></strong>
								</s:if>
								</td>
						    </tr>
						    <s:if test='%{Govtemp=="Y"}'>
							    <tr>
							    	<td >
											Department / <span class="tamil"><s:text name="applicationForm.department"/></span>
									</td>
									<td class="wordWrap" colspan="3" valign="top" >
<%--										<s:if test='%{policedept=="Y"}'>--%>
<%--												<strong><s:label value="Yes"></s:label></strong>--%>
<%--										</s:if>--%>
<%--										<s:if test='%{policedept=="N"}'>--%>
												<strong><s:label value="%{policedept}"></s:label></strong>
<%--										</s:if>--%>
									</td>
							    </tr>
						    	<s:if test='%{policedept=="Police"}'>
						    		<tr>
								    	<td style="width: 30%">
												GPF/CPS Number / <span class="tamil"><s:text name="applicationForm.cps"/></span>
										</td>
										<td class="wordWrap" colspan="3" style="width: 70%" valign="top">
											<strong><s:label value="%{GpfNumber}"></s:label></strong>
										</td>
								    </tr>
								    <tr>
								    	<td style="width: 30%">
												Date Of Enlistment / <span class="tamil"><s:text name="applicationForm.enlistment"/></span>
										</td>
										<td class="wordWrap" colspan="3" style="width: 70%">
											<strong><s:label value="%{Enlistment}"></s:label></strong>
										</td>
								    </tr>
								    <tr>
								    	<td style="width: 30%">
												Present Rank / <span class="tamil"><s:text name="applicationForm.preRank"/></span>
										</td>
										<td class="wordWrap" colspan="3" style="width: 70%">
											<strong><s:label value="%{PresentRank}"></s:label></strong>
										</td>
								    </tr>
								    <tr>
								    	<td style="width: 30%" colspan="4">
												Present Posting at / <span class="tamil"><s:text name="applicationForm.posting"/></span>
										</td>
									</tr>
									<tr>
										<td style="width: 30%">
												Unit / <span class="tamil"><s:text name="applicationForm.unit"/></span>
										</td>
										<td class="wordWrap" colspan="3" style="width: 70%">
											<strong><s:label value="%{presentPostingUnit}"></s:label></strong>
										</td>
								    </tr>
								    <s:if test='%{presentPostingUnit=="YOUTH BRIGADE" || presentPostingUnit=="TALUK POLICE" }'>
								    	 <tr>
								    		<td>
								    			District / City / Commissionarate / <span class="tamil"><s:text name="applicationForm.district"/></span>
								    		</td>
								    		<td class="wordWrap"  >
												<strong><s:label value="%{PresentPosting}"></s:label></strong>
											</td>
											<td width="10%">
												Police Station / <span class="tamil"><s:text name="applicationForm.pStation"/></span>
											</td>
											<td class="wordWrap"  >
												<strong><s:label value="%{policeStation}"></s:label></strong>
											</td>
								    	</tr>
								    </s:if>
								    <s:elseif test='%{presentPostingUnit=="OTHER"}'>
								    	 <tr>
								    		<td>
								    			Please Specify Other Unit / <span class="tamil"><s:text name="applicationForm.otherUnit"/></span>
								    		</td>
								    		<td class="wordWrap" colspan="3" style="width: 70%">
												<strong><s:label value="%{unitsOther}"></s:label></strong>
											</td>
								    	</tr>
								    </s:elseif>
								    <s:elseif test='%{presentPostingUnit=="POLICE HEADQUARTERS"}'>
								    </s:elseif>
								    <s:elseif test='%{presentPostingUnit!="YOUTH BRIGADE" && presentPostingUnit!="TALUK POLICE" && 
								     
								                 presentPostingUnit!="POLICE HEADQUARTERS" && presentPostingUnit!="Other"}'>
								    	 <tr>
								    		<td>
								    			District / City / Commissionarate / <span class="tamil"><s:text name="applicationForm.district"/></span>
								    		</td>
								    		<td class="wordWrap" colspan="3" style="width: 70%">
												<strong><s:label value="%{PresentPosting}"></s:label></strong>
											</td>
								    	</tr>
								    </s:elseif>
								    <tr>
								    	<td style="width: 30%">
								    		Have you won any Medal in National Police Duty Meet? / <span class="tamil"><s:text name="applicationForm.medal"/></span>
								    	</td>
								    	<td class="wordWrap" colspan="3" style="width: 70%" valign="top">
									    	<s:if test='%{policeMedalsName=="Y"}'>
													<strong><s:label value="Yes"></s:label></strong>
											</s:if>
											<s:if test='%{policeMedalsName=="N"}'>
													<strong><s:label value="No"></s:label></strong>
											</s:if>
										</td>
								    </tr>
								    <s:if test='%{policeMedalsName=="Y"}'>
								    	<tr>
								    		<td>
								    			Select Year of Duty Meet / <span class="tamil"><s:text name="applicationForm.dutyMeet"/></span>
								    		</td>
								    		<td class="wordWrap" colspan="3" style="width: 70%" valign="top"> 
												<strong><s:label value="%{dutyYear}"></s:label></strong>
											</td>
								    	</tr>
								    	<tr>
								    		<td>
								    			Event Police Duty Meet / <span class="tamil"><s:text name="applicationForm.policemeet"/></span>
								    		</td>
								    		<td class="wordWrap" colspan="3" style="width: 70%" valign="top">
												<strong><s:label value="%{event}"></s:label></strong>
											</td>
								    	</tr>
									    <tr>
									    	<td style="width: 30%">
									    		Type of Medal Won / <span class="tamil"><s:text name="applicationForm.typeWon"/></span>
									    	</td>
									    	<td class="wordWrap" colspan="3" style="width: 70%">
												<strong><s:label value="%{PoliceMedals}"></s:label></strong>
											</td>
									    </tr>
								    </s:if>
							    </s:if>
							    <s:else>
							    	<tr>
								    	<td style="width: 30%">
												Present Rank / <span class="tamil"><s:text name="applicationForm.preRank"/></span>
										</td>
										<td class="wordWrap" colspan="3">
											<strong><s:label value="%{rank}"></s:label></strong>
										</td>
								    </tr>
								    <tr>
								    	<td style="width: 30%">
												Unit / <span class="tamil"><s:text name="applicationForm.unit"/></span>
										</td>
										<td class="wordWrap" colspan="3">
											<strong><s:label value="%{unit}"></s:label></strong>
										</td>
								    </tr>
								    <tr>
								    	<td style="width: 30%">
												Department Name  / <span class="tamil"><s:text name="applicationForm.departmentName"/></span>
										</td>
										<td class="wordWrap" colspan="3">
											<strong><s:label value="%{department}"></s:label></strong>
										</td>
								    </tr>
							    </s:else>
						     </s:if>
						</table><br />
					<br /> 
					<div style="text-align: left; clear: both;">
						<h1 class="pageTitle">
							Additional Details / <span class="tamil"><s:text name="additional.details"/></span>
						</h1>
					</div>
					<div class="hr-underline2"></div>
					<table class="contenttable">
						<tr>
							<td style="width: 34%" valign="top" >
								Whether any Criminal case have been filed against you?/ <span class="tamil"><s:text name="additional.crime"/></span>
							</td>
							<td class="wordWrap" style="width: 70%" valign="top" >
								<strong><s:label value="%{additionalDetailsBean.crime}"></s:label></strong>
							</td>
						</tr>
						</table>
						<table width="100%" border="1" class="personsl-dtl">
							
								 <s:if test='%{additionalDetailsBean.crime=="Yes"}'>
										<tr>
			        						<th rowspan="2" width="6%" align="left" bgcolor="#FFFFFF">Crime Number / Year / <span class="tamil"><s:text name="additional.crimeno"/></span> / <span class="tamil"><s:text name="additional.crimeyear"/></span></th>
			        						<th rowspan="2" width="9%" align="left" bgcolor="#FFFFFF">State / Union Territory  / <span class="tamil"><s:text name="additional.state"/></span></th>
			     							<th rowspan="2" width="7%" align="left" bgcolor="#FFFFFF">District/City / <span class="tamil"><s:text name="additional.district"/></span></th>
			     							<th rowspan="2" width="7%" align="left" bgcolor="#FFFFFF">Police Station / <span class="tamil"><s:text name="additional.policestation"/></span></th>
			     							<th rowspan="2" width="7%" align="left" bgcolor="#FFFFFF">Current Status of the Case / <span class="tamil"><s:text name="additional.currentstatus"/></span></th>
			        					</tr>
										 
										<tbody>
											<s:iterator value="additionalDetailsBean.crimeDetailList" status="stat" var="currentObject">
													<tr>
														<td class="wordWrap">
		  													<strong><s:label value="%{crimeNumber}" ></s:label></strong> / <strong><s:label value="%{dateOfCrime}" ></s:label></strong>
		  												</td>
		  												<td class="wordWrap">
															<strong><s:label value="%{stateVal}"></s:label></strong>
														</td>
												<s:if test='%{stateVal!="Tamil Nadu"}'>
													
														<td class="wordWrap">
															<strong><s:label value="%{districtValother}"></s:label></strong>
														</td>
														<td class="wordWrap">
															<strong><s:label value="%{policeStationOther}"></s:label></strong>
														</td>
														
												</s:if>
												<s:else>
													
														<td class="wordWrap">
															<strong><s:label value="%{districtyDisplay}"></s:label></strong>
														</td>
														<td class="wordWrap">
														
															<strong><s:label value="%{policeStationDisplay}"></s:label></strong>
														</td>
														
													</s:else>
														<td class="wordWrap">
															<strong><s:label value="%{caseStationVal}"></s:label></strong>
														</td>
													</tr>
												</s:iterator>
										</tbody>
										</s:if>
		</table>
		<br>
					<table>
						<tr>
							<td style="width: 34%">
								Whether Participated in any Previous Recruitment at this Board?/ <span class="tamil"><s:text name="additional.board"/></span>
							</td>
							<td class="wordWrap" style="width: 70%">
								<strong><s:label value="%{additionalDetailsBean.boardName}"></s:label></strong>
							</td>
						</tr>
						<s:if test='%{additionalDetailsBean.boardName=="Yes"}'>
							<tr>
								<td style="width: 34%">
									Select the Recruitments you have Participated Previously? / <span class="tamil"><s:text name="additional.recruitments"/></span>
								</td>
								<%--<td class="wordWrap" style="width: 70%">
									<strong><s:label id="examNam" ></s:label></strong>
								</td>
							--%></tr>
							 
						    <tr>
						    	<td colspan="5">
						    		 <table width="100%" cellspacing="0" cellpadding="0" border="1" id="checkboxtable1" style="display:none;" class="personsl-dtl" >
							    	 	<thead>
								    	 	<tr id="th">
								    	 		<th rowspan="2" align="left" width="15%">Recruitment / <span class="tamil"><s:text name="additional.recruitmentName"/></span></th>
								    	 		<th rowspan="2" align="left" width="15%">Enrollment Number / <span class="tamil"><s:text name="additional.enrollment"/></span></th>
								    	 		<th rowspan="2" align="left" width="15%">Written Test Centre / <span class="tamil"><s:text name="additional.centre"/></span></th>
								    	 		<th colspan="2" align="left" width="15%">Stage Cleared in the Recruitment / <span class="tamil"><s:text name="additional.stageCleared"/></span></th>
								    	 		<th align="left" width="40%" colspan="3">Select Special Quotas Claimed or age Relaxation Claimed Under Special Categories / <span class="tamil"><s:text name="additional.specialquota"/></span></th>
								    	 	</tr>
								    	 	<tr>
								    	 		<th>Stage / <span class="tamil"><s:text name="additional.stage"/></span></th>
								    	 		<th>Status / <span class="tamil"><s:text name="additional.status"/></span></th>
								    	 		<th>Categories / <span class="tamil"><s:text name="additional.category"/></span></th>
								    	 		<th>Sub Categories / <span class="tamil"><s:text name="additional.sub_category"/></span></th>
								    	 		<th>Sub Categories 2 / <span class="tamil"><s:text name="addiotnal.sub_category2"/></span></th>
								    	 	</tr>
										</thead>
										<tbody>
											<tr id="tr11" style="display:none;">
												<td><strong><s:label value="%{additionalDetailsBean.examName}"id="additionalDetailsBean.examName" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.enrollmentNo}"id="additionalDetailsBean.enrollmentNo" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.examCenterName}"id="additionalDetailsBean.examCenterName" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageName}"id="additionalDetailsBean.stageName" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageLevelName}"id="additionalDetailsBean.stageLevelName" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryName}"id="additionalDetailsBean.categoryName" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryLevelName}"id="additionalDetailsBean.categoryLevelName" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categorySportsName}"id="additionalDetailsBean.categorySportsName" /></strong></td> 
								    	 	</tr>
								    	 	<tr id="tr21" style="display:none;">
								    	 		<td><strong><s:label value="%{additionalDetailsBean.examName2}"id="additionalDetailsBean.examName2" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.enrollmentNo2}"id="additionalDetailsBean.enrollmentNo2" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.examCenterName2}"id="additionalDetailsBean.examCenterName2" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageName2}"id="additionalDetailsBean.stageName2" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageLevelName2}"id="additionalDetailsBean.stageLevelName2" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryName2}"id="additionalDetailsBean.categoryName2" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryLevelName2}"id="additionalDetailsBean.categoryLevelName2" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categorySportsName2}"id="additionalDetailsBean.categorySportsName2" /></strong></td>  
								    	 	</tr>
								    	 	<tr id="tr31" style="display:none;">
								    	 		<td><strong><s:label value="%{additionalDetailsBean.examName3}"id="additionalDetailsBean.examName3" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.enrollmentNo3}"id="additionalDetailsBean.enrollmentNo3" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.examCenterName3}"id="additionalDetailsBean.examCenterName3" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageName3}"id="additionalDetailsBean.stageName3" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageLevelName3}"id="additionalDetailsBean.stageLevelName3" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryName3}"id="additionalDetailsBean.categoryName3" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryLevelName3}"id="additionalDetailsBean.categoryLevelName3" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categorySportsName3}"id="additionalDetailsBean.categorySportsName3" /></strong></td> 
								    	 	</tr>
								    	 	<tr id="tr41" style="display:none;">
								    	 		<td><strong><s:label value="%{additionalDetailsBean.examName4}"id="additionalDetailsBean.examName4" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.enrollmentNo4}"id="additionalDetailsBean.enrollmentNo4" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.examCenterName4}"id="additionalDetailsBean.examCenterName4" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageName4}"id="additionalDetailsBean.stageName4" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageLevelName4}"id="additionalDetailsBean.stageLevelName4" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryName4}"id="additionalDetailsBean.categoryName4" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryLevelName4}"id="additionalDetailsBean.categoryLevelName4" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categorySportsName4}"id="additionalDetailsBean.categorySportsName4" /></strong></td> 
								    	 	</tr>
								    	 	<tr id="tr51" style="display:none;">
								    	 		<td><strong><s:label value="%{additionalDetailsBean.examName5}"id="additionalDetailsBean.examName5" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.enrollmentNo5}"id="additionalDetailsBean.enrollmentNo5" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.examCenterName5}"id="additionalDetailsBean.examCenterName5" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageName5}"id="additionalDetailsBean.stageName5" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageLevelName5}"id="additionalDetailsBean.stageLevelName5" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryName5}"id="additionalDetailsBean.categoryName5" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryLevelName5}"id="additionalDetailsBean.categoryLevelName5" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categorySportsName5}"id="additionalDetailsBean.categorySportsName5" /></strong></td> 
								    	 	</tr>
								    	 	<tr id="tr61" style="display:none;">
								    	 		<td><strong><s:label value="%{additionalDetailsBean.examName6}"id="additionalDetailsBean.examName6" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.enrollmentNo6}"id="additionalDetailsBean.enrollmentNo6" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.examCenterName6}"id="additionalDetailsBean.examCenterName6" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageName6}"id="additionalDetailsBean.stageName6" /></strong></td>
												<td><strong><s:label value="%{additionalDetailsBean.stageLevelName6}"id="additionalDetailsBean.stageLevelName6" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryName6}"id="additionalDetailsBean.categoryName6" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categoryLevelName6}"id="additionalDetailsBean.categoryLevelName6" /></strong></td>
								    	 		<td><strong><s:label value="%{additionalDetailsBean.categorySportsName6}"id="additionalDetailsBean.categorySportsName6" /></strong></td> 
								    	 	</tr>
										</tbody>
							    	 </table>
						    	</td>
						    </tr>
						    
						</s:if>
					</table>
						 <br /><br />
						<table width="100%">
							<tr>
								<td colspan="2" >
									<h1 class="pageTitle">
													Documents /  <span class="tamil"><s:text name="document.document"/></span>
												</h1> 
								    <div class="hr-underline2"></div>
								<s:iterator value="uploadList" status="stat" var="uploadList">
					<table width="100%" border="0" cellspacing="3" cellpadding="3">
						<tr>
							<td width="34%"><s:text name="docLabel1" />
														&nbsp;
														<s:hidden name="ocdFlagValue" value="%{ocdFlagValue}" /></td>
														<%--<s:hidden name="docLabel1" value="%{docLabel1}"
															id="lable%{#stat.index}" /></td>
							--%>
							<td width="25%"><s:if test='%{documentFileName1 != null}'>
														 <strong>  <s:property value="documentFileName1"/></strong>
														    		&nbsp;&nbsp;&nbsp;
														    	 
														</s:if></td>
														<%--<s:if test='%{docLabel1 == "10th / SSLC Certificate"}'>
														<td width="25%">
														 <strong>   <span class="tamil"><s:text name="document.document1"/></span></strong>
														    		&nbsp;&nbsp;&nbsp;
														</td>    	 
														</s:if>
														--%>
														<td>
														<strong><label class="confirmtext"><s:property value="docVerify1"/></label></strong>
														</td>
						</tr> 
					</table>
</s:iterator>
											
									 
								</td>
							</tr>
						</table>
						<s:if test='%{afterApplyVeiwPayment!=null && afterApplyVeiwPayment=="true"}'>
						<table width="100%" id="myTable" border="0" cellspacing="3" cellpadding="3">
							<s:if test='%{additionalDetailsBean.exServiceman=="Yes"}'>
								<s:if test='%{DocumentFileNameDischarge != null}'>
									<tr>
										<s:if test='%{appPrint!=null && appPrint=="printApp"}'>
											<td width="34%"> <s:property value="DocLabelDischarge" /> <span class="tamil"><s:text name="discharge"/></span> </td>
											<td width="25%"><strong>  <s:property value="DocumentFileNameDischarge"/></strong></td>
											<td><strong><label>Confirmed</label></strong></td>
										</s:if>
										<s:else>
											<td width="34%"> <s:property value="DocLabelDischarge" /> <span class="tamil"><s:text name="discharge"/></span> </td>
											<td width="25%"><a id="1" style="color:blue" onclick="displayImage(this.id);" class="documents"><STRONG><s:property value="DocumentFileNameDischarge" /></STRONG></a></td>
											<td><label id="veritext1"></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName1" value="%{DocumentFileNameDischarge}"></s:hidden>
									    	<s:hidden id="candidateDocPk1" value="%{CandidateDocPkDischarge}"></s:hidden>
											<td>
												<div id="myModal1" class="modal">
												<div class="modal-content">
												<div><span onclick="closeDiv(1);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
												<br/>
												<div class="imageDdiv" id="imageDdiv1">
													<s:iterator value="ImgDocDischargeL" status="stat" var="ImgDocDischargeL">
														<img src="<s:property value="ImgDocDischarge"/>" alt="my image"   />
													</s:iterator>
												</div>
												<s:checkbox id="verify1" name="checkMe1" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct.
												<br/><input type="button"  id="savclose1" class="submitBtn button-gradient" value="Save/Close" />
												</div>
												</div>
											</td>
										</s:else>
										
									</tr>
								</s:if>
							</s:if>
							<s:if test='%{additionalDetailsBean.widow=="Yes"}'>
								<s:if test='%{DocumentFileNameWidow != null}'>
									<tr>
										<s:if test='%{appPrint!=null && appPrint=="printApp"}'>
											<td width="34%"><s:property value="DocLabelWidow" /><span class="tamil"><s:text name="destitue"/></span></td>
											<td width="25%"><strong><s:property value="DocumentFileNameWidow"/></strong></td>
											<td><strong><label >Confirmed</label></strong></td>
										</s:if>
										<s:else>
											<td width="34%"><s:property value="DocLabelWidow" /><span class="tamil"><s:text name="destitue"/></span></td>
											<td width="25%"><a id="2" style="color:blue" onclick="displayImage(this.id);" class="documents"><STRONG><s:property value="DocumentFileNameWidow" /></STRONG></a></td>
											<td><label id="veritext2"></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName2" value="%{DocumentFileNameWidow}"></s:hidden>
									    	<s:hidden id="candidateDocPk2" value="%{CandidateDocPkWidow}"></s:hidden>
											<td>
												<div id="myModal2" class="modal">
												<div class="modal-content">
												<div><span onclick="closeDiv(2);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
												<br/>
												<div class="imageDdiv" id="imageDdiv2">
													<s:iterator value="ImgDocWidowL" status="stat" var="ImgDocWidowL">
														<img src="<s:property value="ImgDocWidow"/>" alt="my image"   />
													</s:iterator>
												</div>
												<s:checkbox id="verify2" name="checkMe2" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct.
												<br/><input type="button"  id="savclose2" class="submitBtn button-gradient" value="Save/Close" />
												</div>
												</div>
											</td>
										</s:else>
										
									</tr>
								</s:if>
							</s:if>
							<s:if test='%{additionalDetailsBean.sportsQuaota=="Yes"}'>
								<s:if test='%{DocumentFileNameSports != null}'>
									<tr>
										<s:if test='%{appPrint!=null && appPrint=="printApp"}'>
											<td width="34%"><s:property value="DocLabelSports" /><span class="tamil"><s:text name="latest"/></span></td>
											<td width="25%"><strong><s:property value="DocumentFileNameSports"/></strong></td>
											<td><strong><label >Confirmed</label></strong></td>
										</s:if>
										<s:else>
											<td width="34%"><s:property value="DocLabelSports" /><span class="tamil"><s:text name="latest"/></span></td>
											<td width="25%"><a id="3" style="color:blue" onclick="displayImage(this.id);" class="documents"><STRONG><s:property value="DocumentFileNameSports" /></STRONG></a></td>
											<td><label id="veritext3"></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName3" value="%{DocumentFileNameSports}"></s:hidden>
									    	<s:hidden id="candidateDocPk3" value="%{CandidateDocPkSports}"></s:hidden>
											<td>
												<div id="myModal3" class="modal">
												<div class="modal-content">
												<div><span onclick="closeDiv(3);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
												<br/>
												<div class="imageDdiv" id="imageDdiv3">
													<s:iterator value="ImgDocSportsL" status="stat" var="ImgDocSportsL">
														<img src="<s:property value="ImgDocSports"/>" alt="my image"   />
													</s:iterator>
												</div>
												<s:checkbox id="verify3" name="checkMe3" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct.
												<br/><input type="button"  id="savclose3" class="submitBtn button-gradient" value="Save/Close" />
												</div>
												</div>
											</td>
										</s:else>
										
									</tr>
								</s:if>
								<s:if test='%{DocumentFileNameSports2 != null}'>
									<tr>
										<s:if test='%{appPrint!=null && appPrint=="printApp"}'>
											<td width="34%"><s:property value="DocLabelSports2" /><span class="tamil"><s:text name="form"/></span></td>
											<td width="25%"><strong><s:property value="DocumentFileNameSports2"/></strong></td>
											<td><strong><label >Confirmed</label></strong></td>
										</s:if>
										<s:else>
											<td width="34%"><s:property value="DocLabelSports2" /><span class="tamil"><s:text name="form"/></span></td>
											<td width="25%"><a id="4" style="color:blue" onclick="displayImage(this.id);" class="documents"><STRONG><s:property value="DocumentFileNameSports2" /><STRONG></a></td>
											<td><label id="veritext4"></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName4" value="%{DocumentFileNameSports2}"></s:hidden>
									    	<s:hidden id="candidateDocPk4" value="%{CandidateDocPkSports2}"></s:hidden>
											<td>
												<div id="myModal4" class="modal">
												<div class="modal-content">
												<div><span onclick="closeDiv(4);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
												<br/>
												<div class="imageDdiv" id="imageDdiv4">
													<s:iterator value="ImgDocSports2L" status="stat" var="ImgDocSports2L">
														<img src="<s:property value="ImgDocSports2"/>" alt="my image"   />
													</s:iterator>
												</div>
												<s:checkbox id="verify4" name="checkMe4" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct.
												<br/><input type="button"  id="savclose4" class="submitBtn button-gradient" value="Save/Close" />
												</div>
												</div>
											</td>
										</s:else>
									</tr>
								</s:if>
							</s:if>
							<s:if test='%{additionalDetailsBean.wardsQuaota=="Yes"}'>
								<s:if test='%{DocumentFileNameWard != null}'>
									<tr>
										<s:if test='%{appPrint!=null && appPrint=="printApp"}'>
											<td width="34%"><s:property value="DocLabelWard" /><span class="tamil"><s:text name="ward"/></span></td>
											<td width="25%"><strong><s:property value="DocumentFileNameWard"/></strong></td>
											<td><strong><label >Confirmed</label></strong></td>
										</s:if>
										<s:else>
											<td width="34%"><s:property value="DocLabelWard" /><span class="tamil"><s:text name="ward"/></span></td>
											<td width="25%"><a id="5" style="color:blue" onclick="displayImage(this.id);" class="documents"><STRONG><s:property value="DocumentFileNameWard" /></STRONG></a></td>
											<td><label id="veritext5"></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName5" value="%{DocumentFileNameWard}"></s:hidden>
									    	<s:hidden id="candidateDocPk5" value="%{CandidateDocPkWard}"></s:hidden>
											<td>
												<div id="myModal5" class="modal">
												<div class="modal-content">
												<div><span onclick="closeDiv(5);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
												<br/>
												<div class="imageDdiv" id="imageDdiv5">
													<s:iterator value="ImgDocWardL" status="stat" var="ImgDocWardL">
														<img src="<s:property value="ImgDocWard"/>" alt="my image"   />
													</s:iterator>
												</div>
												<s:checkbox id="verify5" name="checkMe5" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct.
												<br/><input type="button"  id="savclose5" class="submitBtn button-gradient" value="Save/Close" />
												</div>
												</div>
											</td>
										</s:else>
									</tr>
								</s:if>
							</s:if>
							<s:if test='%{additionalDetailsBean.nccCertificate=="Yes"}'>
								<s:if test='%{DocumentFileNameNCC != null}'>
									<tr>
										<s:if test='%{appPrint!=null && appPrint=="printApp"}'>
											<td width="34%"><s:property value="DocLabelNCC" /><span class="tamil"><s:text name="ncc"/></span></td>
											<td width="25%"><strong><s:property value="DocumentFileNameNCC"/></strong></td>
											<td><strong><label >Confirmed</label></strong></td>
										</s:if>
										<s:else>
											<td width="34%"><s:property value="DocLabelNCC" /><span class="tamil"><s:text name="ncc"/></span></td>
											<td width="25%"><a id="6" style="color:blue" onclick="displayImage(this.id);" class="documents"><STRONG><s:property value="DocumentFileNameNCC" /></STRONG></a></td>
											<td><label id="veritext6"></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName6" value="%{DocumentFileNameNCC}"></s:hidden>
									    	<s:hidden id="candidateDocPk6" value="%{CandidateDocPkNCC}"></s:hidden>
											<td>
												<div id="myModal6" class="modal">
												<div class="modal-content">
												<div><span onclick="closeDiv(6);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
												<br/>
												<div class="imageDdiv" id="imageDdiv6">
													<s:iterator value="ImgDocNCCL" status="stat" var="ImgDocNCCL">
														<img src="<s:property value="ImgDocNCC"/>" alt="my image"   />
													</s:iterator>
												</div>
												<s:checkbox id="verify6" name="checkMe6" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct.
												<br/><input type="button"  id="savclose6" class="submitBtn button-gradient" value="Save/Close" />
												</div>
												</div>
											</td>
										</s:else>
										
									</tr>
								</s:if>
							</s:if>
							<s:if test='%{additionalDetailsBean.nssCertificate=="Yes"}'>
								<s:if test='%{DocumentFileNameNSS != null}'>
									<tr>
										<s:if test='%{appPrint!=null && appPrint=="printApp"}'>
											<td width="34%"><s:property value="DocLabelNSS" /><span class="tamil"><s:text name="nss"/></span></td>
											<td width="25%"><strong><s:property value="DocumentFileNameNSS"/></strong></td>
											<td><strong><label >Confirmed</label></strong></td>
										</s:if>
										<s:else>
											<td width="34%"><s:property value="DocLabelNSS" /><span class="tamil"><s:text name="nss"/></span></td>
											<td width="25%"><a id="7" style="color:blue" onclick="displayImage(this.id);" class="documents"><STRONG><s:property value="DocumentFileNameNSS" /></STRONG></a></td>
											<td><label id="veritext7"></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName7" value="%{DocumentFileNameNSS}"></s:hidden>
									    	<s:hidden id="candidateDocPk7" value="%{CandidateDocPkNSS}"></s:hidden>
											<td>
												<div id="myModal7" class="modal">
												<div class="modal-content">
												<div><span onclick="closeDiv(7);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
												<br/>
												<div class="imageDdiv" id="imageDdiv7">
													<s:iterator value="ImgDocNSSL" status="stat" var="ImgDocNSSL">
														<img src="<s:property value="ImgDocNSS"/>" alt="my image"   />
													</s:iterator>
												</div>
												<s:checkbox id="verify7" name="checkMe7" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct.
												<br/><input type="button"  id="savclose7" class="submitBtn button-gradient" value="Save/Close" />
												</div>
												</div>
											</td>
										</s:else>
										
									</tr>
								</s:if>
							</s:if>
						</table>
						</s:if>
						<br /><br />
						<s:if test='%{afterApplyVeiwPayment!=null && afterApplyVeiwPayment=="true"}'>
							<table width="100%">
								<s:if test='%{((additionalDetailsBean.exServiceman!=null && additionalDetailsBean.exServiceman!="")|| (additionalDetailsBean.widow!=null && additionalDetailsBean.widow!="")) && additionalDetailsBean.ageflag==true}'>
									<tr>
										<td colspan="4" >
											<h1 class="pageTitle">
												Age Relaxation Details /  <span class="tamil"><s:text name="additional.age"/></span>
											</h1> 
							    			<div class="hr-underline2"></div>
										</td>
									</tr>
									<s:if test='%{additionalDetailsBean.exServiceman!=null && additionalDetailsBean.exServiceman!="" && additionalDetailsBean.ageExflag==true}'>
										<tr>
											<td style="width: 34%">
												Are you an Ex-Servicemen/Ex-Servicewomen or presently serving (going to retire within one year)? / <span class="tamil"><s:text name="additional.exserviceman"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.exServiceman}"></s:label></strong>
											</td>
										</tr>
										<s:if test='%{additionalDetailsBean.exServiceman=="Yes"}'>
										<tr>
											<td style="width: 34%">
												Service Number/ <span class="tamil"><s:text name="additional.servicenumber"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.serviceNumber}"></s:label></strong>
											</td>
										</tr>	
										<tr>
											<td style="width: 34%">
												Date Of Enlistment / <span class="tamil"><s:text name="additional.dateofenlistment"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.dateOfEnlistment}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Date of Discharge / to be discharged /  <span class="tamil"><s:text name="additional.dateofdischarge"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.dateOfDischarge}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Certificate Number/ <span class="tamil"><s:text name="additional.certificatenumber"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.exCertificateNumber}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Designation Of Issuing Authority/ <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.exCertIssuingAuthority}"></s:label></strong>
											</td>
										</tr>
										<%--<tr>
											<td style="width: 34%">
												Date Of Issue Of Certificate/ <span class="tamil"><s:text name="additional.dateofcertificate"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.dateOfDischarge}"></s:label></strong>
											</td>
										</tr>
										
									--%></s:if>
								</s:if>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<s:hidden name="genderValDesc"></s:hidden>
								
								<s:if test='%{additionalDetailsBean.widow!=null && additionalDetailsBean.ageWidowflag==true}'>
									<tr>
										<td style="width: 34%">
											Are You A Destitute Widow ?/ <span class="tamil"><s:text name="additional.widow"/></span>
										</td>
										<td class="wordWrap" >
											<strong><s:label value="%{additionalDetailsBean.widow}"></s:label></strong>
										</td>
									</tr>
									<s:if test='%{additionalDetailsBean.widow=="Yes"}'>
										<tr>
											<td style="width: 34%">
												Name Of Late Husband/ <span class="tamil"><s:text name="additional.nameofhusband"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.nameOfLateHusband}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Date Of Death / <span class="tamil"><s:text name="additional.dateofdeath"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.dateOfDeath}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Certificate Number/ <span class="tamil"><s:text name="additional.certificatenumber"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.deathCertificateNumber}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Designation Of Issuing Authority/ <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.deathCertIssuingAuthority}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Date Of Issue Of Certificate/ <span class="tamil"><s:text name="additional.dateofcertificate"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.dateOfdeathCertificate}"></s:label></strong>
											</td>
										</tr>
										
									</s:if>
								</s:if>
								</s:if>
							
							
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<tr>
								<td colspan="2">&nbsp;</td>
							</tr>
							<s:if test='%{afterApplyVeiwPayment!=null && afterApplyVeiwPayment=="true"}'>
								<tr>
									<td colspan="4" >
										<h1 class="pageTitle" >
										Quotas / Special Marks Details /  <span class="tamil"><s:text name="additional.quotaHeading"/></span>
										</h1> 
						    			<div class="hr-underline2"></div>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Would You Like To Avail Special Quota And/Or Special Marks For Sports?/ <span class="tamil"><s:text name="additional.sports"/></span>
									</td>
									<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.sportsQuaota}"></s:label></strong>
									</td>
							 </tr>
							<s:if test='%{additionalDetailsBean.sportsQuaota=="Yes"}'>
								<tr>
									<td style="width: 34%">
										Sport In Which Participated/ <span class="tamil"><s:text name="additional.sportsName"/></span>
									</td>
									<td class="wordWrap"  valign="top">
										<strong><s:label value="%{additionalDetailsBean.sportsName}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%" >
										Event /Tournament Name / <span class="tamil"><s:text name="additional.eventName"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.eventName}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Date Of Start Of Event / <span class="tamil"><s:text name="additional.eventStartDate"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.dateOfStartEvent}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Date Of Completion Of Event/ <span class="tamil"><s:text name="additional.eventEndDate"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.dateOfComplEvent}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Select Level Of Participation Level / <span class="tamil"><s:text name="additional.participationvel"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.participationvel}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Certificate Number/ <span class="tamil"><s:text name="additional.certificatenumber"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.sportsCertificateNumber}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Designation Of Issuing Authority/ <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.sportsCertIssuingAuthority}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Date Of Issue Of Certificate/ <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.dateOfsportsCertificate}"></s:label></strong>
									</td>
								</tr>
								
						</s:if>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<s:if test='%{(genderValDesc=="Female" && mariatalStatus!="Married") || genderValDesc=="Male" || genderValDesc=="Third Gender"}'>
						
						<s:if test='%{policedept!="Police"}'>
						<s:if test="%{exServicemanFlag==false && additionalDetailsBean.wardsQuaota!=null}">
							<tr>
								<td style="width: 34%">
									Will You Like To Avail Wards Quota?/ <span class="tamil"><s:text name="additional.wards"/></span>
								</td>
								<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.wardsQuaota}"></s:label></strong>
								</td>
							</tr>
							</s:if>
							</s:if>
							<s:if test='%{additionalDetailsBean.wardsQuaota=="Yes"}'>
								<tr>
									<td style="width: 34%">
										Specify The Ward Ministerial(only serving) Or Executive/ <span class="tamil"><s:text name="additional.wardname"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.wardname}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Department Type/ <span class="tamil"><s:text name="additional.departmenttype"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.departmentTypeName}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Name Of The Parent / <span class="tamil"><s:text name="additional.nameOfParent"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.nameOfParent}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Present Rank(Or Last Rank If Retired Or Deceased) / <span class="tamil"><s:text name="additional.presentRank"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.presentRank}"></s:label></strong>
									</td>
								</tr>
								<s:if test='%{additionalDetailsBean.departmentType==1}'>
								<tr>
									<td style="width: 34%">
										Present Posting (Or Last if Retired or Deceased) &#8208; Unit / <span class="tamil"><s:text name="additional.presentPostingUnit"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.presentPostingUnit}"></s:label></strong>
									</td>
								</tr>
								</s:if>
								<s:if test='%{additionalDetailsBean.departmentType==2 || additionalDetailsBean.departmentType==3}'>
								<tr>
									<td style="width: 34%">
										Unit / <span class="tamil"><s:text name="additional.unit"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.pfUnit}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Department Name / <span class="tamil"><s:text name="additional.departmentname"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.pfDistrict}"></s:label></strong>
									</td>
								</tr>
								</s:if>
								
								 <s:if test='%{additionalDetailsBean.presentPostingUnit.equalsIgnoreCase("YOUTH BRIGADE") || additionalDetailsBean.presentPostingUnit.equalsIgnoreCase("TALUK POLICE")}'>
								    	 <tr>
								    		<td style="width: 34%">
								    			Present Posting (Or Last if Retired or Deceased) &#8208; District / City / <span class="tamil"><s:text name="additional.presentPosting"/></span>
								    		</td>
								    		<td class="wordWrap" valign="top" >
												<strong><s:label value="%{additionalDetailsBean.presentPosting}"></s:label></strong>
											</td>
											<td style="width: 34%">
												Present Posting (Or Last if Retired or Deceased) &#8208; Police Station / <span class="tamil"><s:text name="additional.presentPostingPoliceStation"/></span>
											</td>
											<td class="wordWrap postingword"  valign="top">
												<strong><s:label value="%{additionalDetailsBean.policeStation1}"></s:label></strong>
											</td>
								    	</tr>
								</s:if>
								<s:if test='%{additionalDetailsBean.presentPostingUnit.equalsIgnoreCase("OTHER")}'>
								    	 <tr>
								    		<td style="width: 34%">
								    			Please Specify Other Unit / <!--<span class="tamil"><s:text name=""/> </span>-->
								    		</td>
								    		<td class="wordWrap" valign="top">
												<strong><s:label value="%{additionalDetailsBean.unitsOther}"></s:label></strong>
											</td>
								    	</tr>
								</s:if>
								<s:if test='%{additionalDetailsBean.presentPostingUnit.equalsIgnoreCase("POLICE HEADQUARTERS")}'>
								</s:if>
								<s:if test='%{!additionalDetailsBean.presentPostingUnit.equalsIgnoreCase("YOUTH BRIGADE") && !additionalDetailsBean.presentPostingUnit.equalsIgnoreCase("TALUK POLICE") && 
								                 !additionalDetailsBean.presentPostingUnit.equalsIgnoreCase("POLICE HEADQUARTERS") && !additionalDetailsBean.presentPostingUnit.equalsIgnoreCase("OTHER")}'>
								    	 <tr>
								    		<td style="width: 34%">
								    			Present Posting (Or Last if Retired or Deceased) &#8208; District / City / <span class="tamil"><s:text name="additional.presentPosting"/></span>
								    		</td>
								    		<td class="wordWrap" valign="top">
												<strong><s:label value="%{additionalDetailsBean.presentPosting}"></s:label></strong>
											</td>
								    	</tr>
								</s:if>
								<tr>
									<td style="width: 34%">
										GPF or CPS Number Of The Parent / <span class="tamil"><s:text name="additional.gpsNumber"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.gpsNumber}"></s:label></strong>
									</td>
								</tr>
								<%--<tr>
									<td style="width: 34%">
										If Any Other Sibling Selected In Wards Quota? / <span class="tamil"><s:text name="additional.sibilingWard"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.sibilingWard}"></s:label></strong>
									</td>
								</tr>
								--%><tr>
									<td style="width: 34%">
										Certificate Number/ <span class="tamil"><s:text name="additional.certificatenumber"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.wardsCertificateNumber}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Designation Of Issuing Authority/ <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>
									</td>
									<td class="wordWrap" valign="top" >
										<strong><s:label value="%{additionalDetailsBean.wardsCertIssuingAuthority}"></s:label></strong>
									</td>
								</tr>
								<tr>
									<td style="width: 34%">
										Date Of Issue Of Certificate/ <span class="tamil"><s:text name="additional.dateofcertificate"/></span>
									</td>
									<td class="wordWrap" valign="top">
										<strong><s:label value="%{additionalDetailsBean.dateOfwardsCertificate}"></s:label></strong>
									</td>
								</tr>
								
							</s:if>
						</s:if>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td style="width: 34%">
								Are You Possesing NCC A/B/C Certificates?/ <span class="tamil"><s:text name="additional.ncc"/></span>
							</td>
							<td class="wordWrap" valign="top">
								<strong><s:label value="%{additionalDetailsBean.nccCertificate}"></s:label></strong>
							</td>
						</tr>
						<s:if test='%{additionalDetailsBean.nccCertificate=="Yes"}'>
							<tr>
								<td style="width: 34%">
									Certificate (Highest) / <span class="tamil"><s:text name="additional.highestCertificate"/></span>
								</td>
								<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.nccHighestCertificate}"></s:label></strong>
								</td>
							</tr>
							<tr>
								<td style="width: 34%">
									Certificate Number / <span class="tamil"><s:text name="additional.certificatenumber"/></span>
								</td>
								<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.nccCertificateNumber}"></s:label></strong>
								</td>
							</tr>
							<tr>
								<td style="width: 34%">
									NCC Unit / <span class="tamil"><s:text name="additional.nccUnit"/></span>
								</td>
								<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.nccUnit}"></s:label></strong>
								</td>
							</tr>
							<tr>
								<td style="width: 34%">
									Date Of Issue / <span class="tamil"><s:text name="applicationForm.dateOfIssue"/></span>
								</td>
								<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.dateOfNccCertificate}"></s:label></strong>
								</td>
							</tr>
							<tr>
								<td style="width: 34%">
									Issuing Authority / <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>
								</td>
								<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.nccCertIssuingAuthority}"></s:label></strong>
								</td>
							</tr>
							
						</s:if>
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
						<tr>
							<td style="width: 34%">
								Are You Possesing NSS Certificates?/ <span class="tamil"><s:text name="additional.nss"/></span>
							</td>
							<td class="wordWrap" valign="top">
								<strong><s:label value="%{additionalDetailsBean.nssCertificate}"></s:label></strong>
							</td>
						</tr>
						<s:if test='%{additionalDetailsBean.nssCertificate=="Yes"}'>
							<tr>
								<td style="width: 34%">
									Certificate (Highest) / <span class="tamil"><s:text name="additional.highestCertificate"/></span>
								</td>
								<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.nssHighestCertificate}"></s:label></strong>
								</td>
							</tr>
							<tr>
								<td style="width: 34%">
									Certificate Number / <span class="tamil"><s:text name="additional.certificatenumber"/></span>
								</td>
								<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.nssCertificateNumber}"></s:label></strong>
								</td>
							</tr>
							<tr>
								<td style="width: 34%">
									NSS Unit / <span class="tamil"><s:text name="applicationForm.remark"/></span>
								</td>
								<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.nssUnit}"></s:label></strong>
								</td>
							</tr>
							<tr>
								<td style="width: 34%">
									Date Of Issue / <span class="tamil"><s:text name="applicationForm.dateOfIssue"/></span>
								</td>
								<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.dateOfNssCertificate}"></s:label></strong>
								</td>
							</tr>
							<tr>
								<td style="width: 34%">
									Issuing Authority / <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>
								</td>
								<td class="wordWrap" valign="top">
									<strong><s:label value="%{additionalDetailsBean.nssCertIssuingAuthority}"></s:label></strong>
								</td>
							</tr>
							
						</s:if>
						
						<tr>
							<td colspan="2">&nbsp;</td>
						</tr>
									<s:if test='%{additionalDetailsBean.exServiceman!=null && additionalDetailsBean.exServiceman!=""  &&  additionalDetailsBean.ageExflag==false}'>
										<tr>
											<td style="width: 34%">
												Are you an Ex-Servicemen/Ex-Servicewomen or presently serving (going to retire within one year)? / <span class="tamil"><s:text name="additional.exserviceman"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.exServiceman}"></s:label></strong>
											</td>
										</tr>
										<s:if test='%{additionalDetailsBean.exServiceman=="Yes"}'>
										<tr>
											<td style="width: 34%">
												Service Number/ <span class="tamil"><s:text name="additional.servicenumber"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.serviceNumber}"></s:label></strong>
											</td>
										</tr>	
										<tr>
											<td style="width: 34%">
												Date Of Enlistment / <span class="tamil"><s:text name="additional.dateofenlistment"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.dateOfEnlistment}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Date of Discharge / to be discharged /  <span class="tamil"><s:text name="additional.dateofdischarge"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.dateOfDischarge}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Certificate Number/ <span class="tamil"><s:text name="additional.certificatenumber"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.exCertificateNumber}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Designation Of Issuing Authority/ <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.exCertIssuingAuthority}"></s:label></strong>
											</td>
										</tr>
										<%--<tr>
											<td style="width: 34%">
												Date Of Issue Of Certificate/ <span class="tamil"><s:text name="additional.dateofcertificate"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.dateOfDischarge}"></s:label></strong>
											</td>
										</tr>
										
									--%></s:if>
								</s:if>
								<tr>
									<td colspan="2">&nbsp;</td>
								</tr>
								<s:if test='%{genderValDesc=="Female" && mariatalStatus!="Married" && additionalDetailsBean.widow!=null && additionalDetailsBean.widow!=""  && additionalDetailsBean.ageWidowflag==false}'>
									<tr>
										<td style="width: 34%">
											Are You A Destitute Widow ?/ <span class="tamil"><s:text name="additional.widow"/></span>
										</td>
										<td class="wordWrap" >
											<strong><s:label value="%{additionalDetailsBean.widow}"></s:label></strong>
										</td>
									</tr>
									<s:if test='%{additionalDetailsBean.widow=="Yes"}'>
										<tr>
											<td style="width: 34%">
												Name Of Late Husband/ <span class="tamil"><s:text name="additional.nameofhusband"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.nameOfLateHusband}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Date Of Death / <span class="tamil"><s:text name="additional.dateofdeath"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.dateOfDeath}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Certificate Number/ <span class="tamil"><s:text name="additional.certificatenumber"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.deathCertificateNumber}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Designation Of Issuing Authority/ <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.deathCertIssuingAuthority}"></s:label></strong>
											</td>
										</tr>
										<tr>
											<td style="width: 34%">
												Date Of Issue Of Certificate/ <span class="tamil"><s:text name="additional.dateofcertificate"/></span>
											</td>
											<td class="wordWrap" >
												<strong><s:label value="%{additionalDetailsBean.dateOfdeathCertificate}"></s:label></strong>
											</td>
										</tr>
										
									</s:if>
								</s:if>
							</s:if>
						
						
					</table>
				</s:if>
						
						<table width="100%" class="contenttable">
						<tr>
							<td colspan="3">
								<%--<strong>Place / <span class="tamil"><s:text name="tamil.place"/></span> :</strong>
							--%></td>
						</tr>
						<tr>
						<s:if test='%{afterApplyVeiwPayment!=null && afterApplyVeiwPayment=="true"}'>
							<td colspan="2">
								<strong>Declaration:</strong>
								<%=ConfigurationConstants.getInstance().getDeclarationVal1(GenericConstants.DECLARATION1)%>
								<br />
								<br />
							</td>
							</s:if>
							<s:else>
							<td colspan="2">
								<strong>Declaration:</strong>
								<%=ConfigurationConstants.getInstance().getDeclarationVal(GenericConstants.DECLARATION)%>
								<br />
								<br />
							</td>
							
							</s:else>
							
						</tr>
						<tr>
							<td colspan="3" align="right" valign="bottom" id="signatureImage">
								<!--  <img src="SignatureImage.jpg" width="200" height="50"
									onerror="onErrorMessage('signatureImage')" /> --> 
									
									<img width="200" src="SignatureImage.jpg" height="50" id="inputStreamForImage1"
									onerror="onErrorMessage('signatureImage')" />
							</td>
						</tr>
						
						<tr>
							<s:if test='%{afterApplyVeiwPayment!=null && afterApplyVeiwPayment=="true"}'>
						<td>
								<strong>Submitted Date :</strong>
								<strong><s:property value="%{additionalDetailsBean.appFormSubmitedDate}" /> </strong>

							</td>
						</s:if>
						<s:else>
						<td>
								<strong>Submitted Date :</strong>
								<strong><s:property value="regFormSubmitedDate" /> </strong>

							</td>
						</s:else>
							<td align="right">
								(Signature of the Candidate / <span class="tamil"><s:text name="tamil.sign"/></span>)
							</td>
						</tr>
                          <tr style="display:none;">
						
							<td colspan="2">
							<br/>
								<strong>Note:</strong> DD along with self attested qualification certificates to be sent to the following address, Course Coordinator, ICSI, The Institute of Company .
								<br />
					
							</td>
						</tr>
						<tr>
						<td colspan="2"></td>
						</tr>
						<tr>
							<td colspan="2">
								&nbsp;
							</td>
						</tr>
						
						<tr id="declar" style="color: red; display: none">
							<td>
								Please confirm your Declaration
							</td>
						</tr>
						<tr>
							<td align='left'>
								<strong>User ID</strong> -
								<strong><s:label value="%{#session['SESSION_USER'].username}" /></strong>
							</td>
							
							<s:if test='%{additionalDetailsBean.sportsQuaota!=null && additionalDetailsBean.sportsQuaota!=""}'>
								<s:if test='%{appPrint==null || appPrint==""}'>
								<td align='right'><!--
								
									<input type="button" value="Apply and Make Payment"
									class="submitBtn button-gradient" onclick="validatePage();" />
								--></td>
								</s:if>
							</s:if>
							

							<!--    <td colspan="2" align="right"><img src="images/barcode.png" width="187" height="45" /></td>  -->
						</tr>
					</table>
				</div>
			</div>
			<s:token/>
		</s:form>
		
	</div>
</div>
