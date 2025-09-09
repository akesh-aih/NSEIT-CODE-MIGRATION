<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>
<script type="text/javascript" src="js/jquery-3.6.3.min.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<script src="js/bower_components/pdfmake/build/pdfmake.js"></script>
<script src="js/bower_components/pdfmake/build/vfs_fonts.js"></script>

<script type="text/javascript">

document.onreadystatechange = function () {
	var state = document.readyState;
	enableLoadingAnimation();
	if (state == 'complete') {
		disabledLoadingAnimation();
	}
}

var checked1 = false;
var checked2 = false;
var checked3 = false;
var checked4 = false;
var checked5 = false;
var checked6 = false;
var checked7 = false;
var checked8 = false;

function load3Sec(){
	enableLoadingAnimation();
	setTimeout(function(){ 
		disabledLoadingAnimation(); 
	}, 3000);
}

//  Accordion Panels
function enable(){
	var checker = document.getElementById('declaration');
	var sendbtn = document.getElementById('pay');
	 // when unchecked or checked, run the function
	checker.onchange = function(){
		if(this.checked){
			sendbtn.disabled = false;
		} else {
		    sendbtn.disabled = true;
		}
	}
}

	var result=[];
	var rowCount;
	function open_win() {
	  window.open("CandidateAction_printFinalPageInJasper.action")
	}
	
	function onErrorMessage(id){
	  $("#"+id).hide();
	}
	
	function ShowDialog(modal) {
	    $("#overlay").show();
	    $("#dialog").fadeIn(300);

	    if (modal) {
	      $("#overlay").unbind("click");
	    } else {
	      $("#overlay").click(function(e) {
	        HideDialog();
	      });
	    }
	  }
	
	function HideDialog(){
		
		$("#btnSubmitId").attr('disabled','disabled');
		$("#checkbox1").prop('checked',false);
		$("#overlay").hide();
	    $("#declar").hide();
	    document.getElementById('checkbox2').checked = false;

	    var dataString = "action=Decline Declaration&audittrail=declined Declaration";
	    $.ajax({
	    	type: 'POST',
	    	url: "CandidateAction_AuditTrailForDeclaration.action",
			async: true,
			data: dataString,
			error:function(ajaxrequest)
			{
				//window.reload();
			},
	    		success:function(responseText)
	     		{
	     		}
	   	});
		$("#dialog").fadeOut(300);
	}
	
	function ShowDialog1(){
		$("#dialog").fadeOut(300);
		enableLoadingAnimation();
		document.formDtls.submit();
	}
	  
	function validatePage(){
		var flag = true;
		// if(!$('#declaration').is(':checked')){
		// $("#declar").show();
		// }else{
		var rowData = "";
		var count = 0;
		var docFile = [];
		var docName = [];
		var k = 0;
		$('#msg').empty();
		var lMsg = "Verify Document";
		if (rowCount != 0) {
			for (var i = 0; i < rowCount; i++) {
				var IMsgTemp = $('#myTable tr').eq(i).children(":nth-child(3)").text();
				IMsgTemp = IMsgTemp.trim();
				/* console.log("Doc" + i + " :" + IMsgTemp); */

				if ($('#myTable tr').eq(i).children(":nth-child(3)").text().trim() == lMsg || $('#myTable tr').eq(i).children(":nth-child(3)").text() == null) {
					count++;
					docFile[k] = $('#myTable tr').eq(i).children(":nth-child(1)").text();
					docName[k] = $('#myTable tr').eq(i).children(":nth-child(2)").text();
					k++;
				}
			}
			if (count != 0 && rowCount != null && rowCount != "" && (count == rowCount)) {
				// alert("Please confirm the uploaded documents before submitting the form.");
				$(window).scrollTop(0);
				$('#msg').append('<span style="color:red"><li>Please verify the uploaded documents before submitting the form.</li><span>');
				flag = false;
				return flag;
			} else {
				$(window).scrollTop(0);
				if (docName.length != 0) {
					for (var i = 0; i < docName.length; i++) {
						$('#msg').append('<span style="color:red"><li>Please verify the ' + docFile[i] + ' - ' + docName[i] + ' document.</li><span>');
					}
					flag = false;
					return flag;
				}
			}
	}
		/* <s:iterator value="educationDtlsList" status="stat">
		Count='<s:property value="%{#stat.count}" />';
		</s:iterator>
		ShowDialog(true); */
		if(flag)
	    {
			ShowDialog();
			//document.formDtls.submit();
	    }
	}
		
	function redirectTpaymentPage()
	{
		document.formDtls.submit();
	}
	  
	$(document).ready(function() {
		rowCount = $('#myTable tr').length;
		$("#pay").attr('disabled','disabled');
		$("#btnSubmitId").attr('disabled','disabled');
		
		$("#checkbox2").click(function() {	
			var checkVal2 = $("#checkbox2").is(":checked");
			if(checkVal2){
				checked2 = true;
				enableContinuebtn();
			}else{
				checked2 = false;
				enableContinuebtn();
			}
		});
		
		$("#checkbox1").click(function() {	
			var checkVal = $("#checkbox1").is(":checked");
			if(checkVal)
			{
				checked1 = true;
				enableContinuebtnPopUp();
			}else{
				checked1 = false;
				enableContinuebtnPopUp();
			}
		});
	
		function enableContinuebtn(){	
			if(checked2 == true){
				$("#pay").removeAttr('disabled');
			}else{
				$("#pay").attr('disabled','disabled');
			} 
		}
		
		function enableContinuebtnPopUp(){	
			if(checked1 == true){
				$("#btnSubmitId").removeAttr('disabled');
			}else{
				$("#btnSubmitId").attr('disabled','disabled');
			} 
		}
	  
	    if($("#imageUploaded").val() == "true")
	    {
	      $("#uploadedImage").show();
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
	});
	
	function closeDiv(id){
		$("#overlay1").hide();
		var modal = document.getElementById('myModal'+id);
		if($("#veritext"+id).text()!="Confirmed")
		{
			document.getElementById("verify"+id).checked = false;
		}
		modal.style.display = "none";
	}
	
	function goToMenu(link){
		$("#applicationForm").attr('action',link);
		$("#applicationForm").submit();
	}
	  
	function displayImage(id){
		
		$("#overlay1").show();
		var modal = document.getElementById('myModal'+id);
		modal.style.display = "block";
		var modal1 = document.getElementById('imageDdiv'+id);
		/* setTimeout(function(){ 
			modal1.scrollTop = 0; 
		}, 30); */
		if($("#veritext"+id).text()=="Confirmed"){
			document.getElementById("verify"+id).checked = true;
		}
		var i=$("#verify"+id).is(':checked') ? 1 : 0;
		if (i==1){
	    	$('#savclose'+id).removeAttr('disabled'); //enable input
		}else{
	    	$('#savclose'+id).attr('disabled', true); //disable input
	  	}
		$('#savclose'+id).unbind('click').click(function(){
			$("#overlay1").hide();
	    	modal.style.display = "none";
	    	var pk=$("#candidateDocPk"+id).val();
	    	var name=$("#documentFileName"+id).val();
	    	$.ajax({
	    		type: 'POST',
	    		url: "CandidateAction_saveDocVerify.action",
	      		async: false, 
	      		cache: false,
	      		data: "candidateDocPk="+pk+"&documentFileName="+name,
	      		success:function(response){
	         		$("#hidetab"+id).hide();
	        		$("#veritext"+id).text("Confirmed");
	        		var res=response.trim();
	        		if(res!=null && res!=""){
				        result=res.split(",");
	        		}
	      		},
	      		error:function(response){
	        		window.reload();
				}
	    	});
	  	});
	  	$("#verify"+id).change(function(){
			var i=$("#verify"+id).is(':checked') ? 1 : 0;
	  		if(i==1){
	    		$('#savclose'+id).removeAttr('disabled');
	  		}
	  		if(i==0){
	    		$('#savclose'+id).attr('disabled', true);
			}
		});
	}
	
</script>

<style>
.msgg li:first-child {
	list-style: none;
}

.screenimages {
	text-align: center;
	height: 350px;
	overflow: auto;
}

.screenimages img {
	width: auto !important;
}

.subBtnDisabled {
	pointer-events: none;
	opacity: 0.56;
}

.ui-accordion .ui-accordion-header {
	border-width: 0 !important;
}

.modal-content {
	position: relative;
	background-color: #fff;
	background-clip: padding-box;
	border: 1px solid #999;
	border: 1px solid rgba(0, 0, 0, .2);
	border-radius: 6px;
	-webkit-box-shadow: 0 3px 9px rgba(0, 0, 0, .5);
	box-shadow: 0 3px 9px rgba(0, 0, 0, .5);
	outline: 0;
	width: 71% !important;
	top: 10%;
	left: 15%;
	padding: 10px;
}

#pop2 .form-group {
	border-bottom: 1px solid #ccc !important;
}

.manadetory-fields {
	font-size: 13px;
	color: #ff0000;
	font-family: 'Roboto Bold';
	font-weight: normal;
	margin-left: 1px;
}
</style>
<div class="container-fluid ViewpageLabel">
	<div id="dashboard">
		<s:hidden id='hddAddressChkBox'></s:hidden>
		<div class="titlebg container">
			<h1 class="pageTitle">
				<strong> Preview</strong> <span class="userid_txt"> <s:if
						test="%{#session['SESSION_USER'] != null}">
						<strong>User ID </strong> -	<s:label
							value="%{#session['SESSION_USER'].username}" />
					</s:if>
				</span>
			</h1>
		</div>
		<div class="padding_leftright">
			<s:form id="applicationForm" name="formDtls"
				action="CandidateAction_updateCandidateStageFinalSubmit">
				<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<s:hidden name="isDataFound"
					value='<s:property value="formConfirm"/>' />
				<div class="container common_dashboard tabDiv effect2">
					<div class="container">
						<div id="msg" class="mb20"></div>
						<div id="error-massageAppForm" style="display: none; color: red;"
							class="error-massage"></div>
						<s:actionmessage escape="false" cssClass="msgg" />
					</div>
					<%-- <div class="row">
						<div class="col-sm-12">
							<span class="orgNote"> <b>Note:</b> Candidates are advised
								to verify details on this page before submitting the form,
								candidates can use edit link against each section to edit
								details of the respective section before submitting the form.
								For Verification in hard copy form you can also take the print
								of the Preview page by using browser print option (Ctrl +P). The
								Preview page printed will not be considered as submitted
								application form, after verifications check the declaration and
								submit your form and make payment. After application fees
								payment, you can download your submitted application form from
								Candidate Dashboard.
							</span>
						</div>
					</div> --%>
					<div class="accordions">
						<div id="PersonalDiv">
							<h1 class="pageTitle" title="Personal Details">Personal
								Details</h1>
							<div class="accordion">
								<div class="row">
									<div class="col-sm-12 text-right">
										<a href="javascript:void(0)"
											onclick="goToMenu('CandidateAction_getCandidateDetails.action')">Edit</a>
									</div>
								</div>
								<div class="row">
									<s:hidden name="auditFlag" id="auditFlag" value="Preview"></s:hidden>
									<div class="col-sm-2 col-sm-push-10 photoDiv">
										<div class="row">
											<div class="col-sm-12 padleft0" id="photoImage">
												<span class="font12">Applicant Photo</span> <img
													height="100" width="100%" class="img-responsive"
													src="PhotoImage.jpg" id="inputStreamForImage"
													onerror="onErrorMessage('photoImage')" />
												<div class="clear"></div>
												<a href="javascript:void(0)"
													onclick="goToMenu('CandidateAction_showUploadPhotograph.action')">Edit
													Photo</a>
											</div>
										</div>
									</div>
									<div class="col-sm-10 col-sm-pull-2">
										<div class="row mb10">
											<div class="col-sm-4">
												<label class="control-label">Post Applying for </label>
												<s:label id="disciplinName" value="Prosthetic Craftsman"
													cssClass="form-control wordWrap"></s:label>
												<s:hidden id="courses" value="%{courses}" name="courses" />
											</div>
										</div>
										<div class="row">
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Name <s:text
															name="applicationForm.name"></s:text>
													</label>
													<s:label value="%{personalDetailsBean.candidateName}"
														cssClass="form-control wordWrap"></s:label>
												</div>
											</div>

											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label"> Nationality <s:text
															name="applicationForm.nationality" />
													</label>
													<s:label value="%{nationality}" id="nationality"
														cssClass="form-control wordWrap" />
												</div>
											</div>
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">Gender <s:text
															name="applicationForm.maritalStatus" />
													</label>
													<s:label name="genderValDesc" cssClass="form-control"
														value="%{genderVal}" id="gender" />
												</div>
											</div>
										</div>

										<div class="row">
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">Are you an
														Ex-Servicemen </label>
													<s:label name="exServiceMen" id="exServiceMen"
														value="%{exServiceMen}" cssClass="form-control"></s:label>
												</div>
											</div>
											<s:if test="%{exServiceMen=='Yes'}">
												<div class="col-sm-4" id="dischargeDiv">
													<div class="form-group">
														<label class="control-label mb8">Date of Discharge
															/ Probable Discharge <s:text
																name="applicationForm.dateOfBirth" />
														</label>
														<s:label id="dischargeDate" name="dischargeDate"
															cssClass="form-control">
														</s:label>
													</div>
												</div>
												<div class="col-sm-4" id="ppoNumberDiv">
													<div class="form-group">
														<label class="control-label">PPO Number<s:text
																name="login.ppoNumber" />
														</label>
														<s:label id="ppoNumber" name="ppoNumber"
															value="%{ppoNumber}" cssClass="form-control wordWrap">
														</s:label>
													</div>
												</div>
											</s:if>
										</div>
										<div class="row">
											<div class="col-sm-4" id="communityDiv">
												<div class="form-group">
													<label class="control-label">Do you have community
														certificate issued by Tamil Nadu Government? <s:text
															name="login.ppoNumber" />
													</label> <br />
													<s:label label="commCertYesNo" name="commCertYesNo"
														id="commCertYesNo" value="%{commCertYesNo}"
														cssClass="form-control" />
												</div>
											</div>

											<s:if test="%{community=='OC'}">
												<div class="col-sm-4 mb8" id="generalCommunity">
													<div class="form-group">
														<label class="control-label"> Community <s:text
																name="login.ppoNumber" />
														</label>
														<s:label name="categoryVal1" value="OC"
															cssClass="form-control" />
													</div>
												</div>
											</s:if>
											<s:if test="%{community!='OC'}">
												<div class="col-sm-4" id="chechCommunity">
													<div class="form-group">
														<label class="control-label">Community<s:text
																name="login.ppoNumber" />
														</label> <br />
														<s:label label="community" name="community" id="community"
															value="%{community}" cssClass="form-control" />
													</div>
												</div>
												<div class="col-sm-4" id="subCasteDiv">
													<div class="form-group">
														<label class="control-label">Sub Caste<s:text
																name="login.ppoNumber" />
														</label>
														<s:label id="subCaste" name="subCaste" value="%{subCaste}"
															cssClass="form-control wordWrap" />
													</div>
												</div>
											</s:if>
										</div>
										<s:if test="%{community!='OC'}">
											<div class="row">
												<div class="col-sm-4" id="issueAuthCommCertDiv">
													<div class="form-group">
														<label class="control-label">Issuing Authority of
															Community Certificate<s:text name="login.ppoNumber" />
														</label>
														<s:label id="issueAuthCommCert" name="issueAuthCommCert"
															cssClass="form-control wordWrap"
															value="%{issueAuthCommCert}" />
													</div>
												</div>
												<div class="col-sm-4" id="commCertNoDiv">
													<div class="form-group">
														<label class="control-label">Community Certificate
															Number <s:text name="login.ppoNumber" />
														</label>
														<s:label id="commCertNo" name="commCertNo"
															cssClass="form-control" />
													</div>
												</div>
												<div class="col-sm-4" id="commCertPlaceDiv">
													<div class="form-group">
														<label class="control-label">Community Certificate
															Place of Issue<s:text name="login.ppoNumber" />
														</label>
														<s:label id="commCertPlace" name="commCertPlace"
															cssClass="form-control wordWrap" value="%{commCertPlace}" />
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-sm-4" id="commIssueDateDiv">
													<div class="form-group">
														<label class="control-label mb8">Community
															Certificate Issuing Date<s:text
																name="applicationForm.dateOfBirth" />
														</label>
														<s:label id="commIssueDate" name="commIssueDate"
															onkeypress="return dateFormatCheck(event);"
															cssClass="form-control">
														</s:label>
													</div>
												</div>
											</div>
										</s:if>
										<div class="row">
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">Are you differently
														abled?<s:text name="login.ppoNumber" />
													</label> <br />
													<s:label name="disableYesNo" id="disableYesNo"
														value="%{disableYesNo}" cssClass="form-control" />
													<s:if test="%{disableYesNo == 'Yes'}">
														<div class="row">
															<div class="col-sm-12">
																<input type="checkbox" checked disabled /> <span
																	class="font12 english_ins"> I agree to provide
																	Differently Abled Person Certificate at the time of
																	Certificate Verification </span>
																<s:hidden id="diffAbledChkBox"
																	value="%{diffAbledChkBox}" name="diffAbledChkBox" />
															</div>
														</div>
													</s:if>
												</div>
											</div>
											<s:if test="%{disableYesNo=='Yes'}">
												<div class="col-sm-4" id="disableTypeDiv">
													<div class="form-group">
														<label class="control-label disability">
															Differently Abled Category </label>
														<s:label name="disableType" cssClass="form-control"
															id="disableType" value="%{disableType}">
														</s:label>
													</div>
												</div>
												<div class="col-sm-4" id="disablityPercentDiv">
													<div class="form-group">
														<label class="control-label mb8">Percentage of
															Disability<s:text name="applicationForm.dateOfBirth" />
														</label>
														<s:label id="disablityPercent" name="disablityPercent"
															cssClass="form-control" value="%{disablityPercent}">
														</s:label>
													</div>
												</div>
											</s:if>
										</div>
										<s:if test="%{genderVal=='Female'}">
											<div class="row">
												<div class="col-sm-4" id="widowDiv">
													<div class="form-group">
														<label class="control-label">Are you a Destitute
															Widow? <%-- <s:text name="login.community" />  --%>
														</label> <br />
														<s:label label="widowYesNo" name="widowYesNo"
															id="widowYesNo" cssClass="form-control" />
													</div>
												</div>
												<%-- <s:if test="%{widowYesNo=='Yes'}">
													<div id="widowChkBoxDiv">
														<div class="col-sm-4 ">
															<input type="checkbox" name="widowChkBox"
																id="widowChkBox" checked disabled /> <span
																class="font12 english_ins"> I agree to provide
																Destitute Widow Certificate at the time of Certificate
																Verification </span>
														</div>
													</div>
												</s:if> --%>
											</div>

											<s:if test="%{widowYesNo=='Yes'}">
												<div class="row" id="widowRowOne">
													<div class="col-sm-4">
														<div class="form-group">
															<label class="control-label">Destitute Widow
																Certificate Number <%-- <s:text name="login.candidateName" /> --%>
															</label>
															<s:label id="desWidowCertNo" name="desWidowCertNo"
																cssClass="form-control wordWrap" />
														</div>
													</div>

													<div class="col-sm-4" id="">
														<div class="form-group dateInput">
															<label class="control-label">Destitute Widow
																Certificate Issuing Date <%-- <s:text name="applicationForm.commIssueDate" /> --%>
															</label>
															<s:label id="widowIssueDate" name="widowIssueDate"
																cssClass="form-control">
															</s:label>
														</div>
													</div>
													<div class="col-sm-4" id="widowIssueAuthDiv">
														<div class="form-group">
															<label class="control-label">Issuing Authority of
																Destitute Widow Certificate <%-- <s:text name="login.candidateName" /> --%>
															</label>
															<s:label id="widowIssueAuth" name="widowIssueAuth"
																cssClass="form-control wordWrap" />
														</div>
													</div>
												</div>
												<div class="row" id="widowRowTwo">
													<div class="col-sm-4">
														<div class="form-group">
															<label class="control-label">Destitute Widow
																Certificate Issued District <%-- <span class="tamil"> <s:text name="applicationForm.stateUnion" /> </span> --%>
															</label>
															<s:label id="widowDistrict" name="widowDistrict"
																cssClass="form-control"></s:label>
														</div>
													</div>
													<s:if test="%{widowOtherDistrict!='' && widowOtherDistrict!=null}">
														<div class="col-sm-4" id="widowIssueAuthDiv">
															<div class="form-group">
																<label class="control-label">Destitute Widow
																	Certificate Issued Other District <%-- <s:text name="login.candidateName" /> --%>
																</label>
																<s:label id="widowOtherDistrict"
																	name="widowOtherDistrict"
																	cssClass="form-control wordWrap" />
															</div>
														</div>
													</s:if>
													<div class="col-sm-4" id="widowIssueAuthDiv">
														<div class="form-group">
															<label class="control-label">Destitute Widow
																Certificate Issued Sub Division <%-- <s:text name="login.candidateName" /> --%>
															</label>
															<s:label id="widowSubDivision" name="widowSubDivision"
																cssClass="form-control wordWrap" />
														</div>
													</div>
												</div>
											</s:if>
										</s:if>
										<div class="row">
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label mb8">Date of Birth (as
														per SSLC mark Sheet) <s:text
															name="applicationForm.dateOfBirth" />
													</label>
													<s:label id="dateOfBirth" name="dateOfBirth"
														onkeypress="return dateFormatCheck(event);"
														cssClass="form-control nonEditable">
													</s:label>
													<div class="clear"></div>
												</div>
											</div>
											<div class="clear visible-xs"></div>
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label mb8">Age as on <s:property
															value="%{ageAsOn}" /> <s:text
															name="applicationForm.dateOfBirth" />
													</label>
													<div class="clear"></div>
													<s:label value="%{personalDetailsBean.age}"
														id="personalDetailsBean.age"
														cssClass="form-control wordWrap" />
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Mobile Number <s:text
															name="applicationForm.mobileNo" />
													</label>
													<s:label value="%{personalDetailsBean.mobileNo}"
														id="mobileNo" cssClass="form-control wordWrap" />
												</div>
											</div>
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Email ID <s:text
															name="applicationForm.emailId" /></label>
													<s:label value="%{personalDetailsBean.email}" id="email"
														cssClass="form-control wordWrap" />
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">Would you like to give
														Father and Mother name or Guardian name </label>
													<s:label value="%{parentAndGuardianVal}"
														name="parentAndGuardian" id="parentAndGuardianVal"
														cssClass="form-control wordWrap" />

													<%-- <s:label name="genderValDesc" cssClass="form-control"
														value="%{genderVal}" id="gender" /> --%>

													<%-- <s:hidden name="parentAndGuardian" id="parentAndGuardian"
														value="%{parentAndGuardian}"
														cssClass="form-control wordWrap" /> --%>

												</div>
											</div>
											<s:if test='%{fathersName != "" && fathersName != null}'>
												<div class="col-sm-4">
													<div class="form-group">
														<div class="hidden-xs"></div>
														<label class="control-label mb8">Father's Name <s:text
																name="applicationForm.fatherName" />
														</label>
														<s:label name="fathersName" id="fathersName"
															value="%{fathersName}" cssClass="form-control wordWrap" />
													</div>
												</div>
											</s:if>
											<s:if test='%{mothersName != "" && mothersName != null}'>
												<div class="col-sm-4">
													<div class="hidden-xs"></div>
													<div class="form-group">
														<label class="control-label mb8">Mother's Name <s:text
																name="applicationForm.motherName" />
														</label>
														<s:label name="mothersName" id="mothersName"
															value="%{mothersName}" cssClass="form-control wordWrap" />
													</div>
												</div>
											</s:if>
											<s:if test='%{guardianName != "" && guardianName != null}'>
												<div class="col-sm-4">
													<div class="hidden-xs"></div>
													<div class="form-group">
														<label class="control-label mb8">Guardian's Name </label>
														<s:label name="guardianName" id="guardianName"
															value="%{guardianName}" cssClass="form-control wordWrap" />
													</div>
												</div>
											</s:if>
										</div>
										<div class="row">
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">Are you Married<s:text
															name="applicationForm.maritalStatus" /></label>
													<s:label value="%{mariatalStatus}" id="mariatalStatus"
														cssClass="form-control wordWrap" />
												</div>
											</div>
											<s:if test="%{mariatalStatus=='Yes'}">
												<div class="col-sm-4" id="spouseNameDiv">
													<div class="form-group">
														<div class="hidden-xs"></div>
														<label class="control-label">Name of Spouse<s:text
																name="applicationForm.maritalStatus" />
														</label>
														<s:label id="spouseName" cssClass="form-control wordWrap"
															value="%{spouseName}" name="spouseName" />
													</div>
												</div>
											</s:if>
										</div>
										<div class="row">
											<div class="col-sm-4">
												<div class="form-group">
													<div class="hidden-xs"></div>
													<label class="control-label mb8">Nativity<s:text
															name="applicationForm.maritalStatus" />
													</label>
													<s:label name="nativity" id="nativity" value="%{nativity}"
														cssClass="form-control nonEditable" />
												</div>
											</div>
											<s:if test="%{nativity=='Other'}">
												<div class="col-sm-4" id="otherNativityDiv">
													<div class="form-group">
														<div class="hidden-xs"></div>
														<label class="control-label">Other Native<s:text
																name="applicationForm.maritalStatus" />
														</label>
														<s:label id="otherNativity" name="otherNativity"
															cssClass="form-control nonEditable"
															value="%{otherNativity}" />
													</div>
												</div>
											</s:if>
										</div>
										<div class="row">
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label"> Religion <s:text
															name="applicationForm.religionBelief" />
													</label>
													<s:label value="%{religionBelief}" id="religionBelief"
														cssClass="form-control wordWrap" />
												</div>
											</div>
											<s:if
												test='%{religionBeliefOthers != "" && religionBeliefOthers != null}'>
												<div class="col-sm-4 col-xs-12">
													<div class="form-group">
														<label class="control-label">Other Religion <s:text
																name="applicationForm.otherReligionBelief" />
														</label>
														<s:label value="%{religionBeliefOthers}"
															id="religionBeliefOthers"
															cssClass="form-control wordWrap" />
													</div>
												</div>
											</s:if>
										</div>

										<div class="row">
											<div class="col-sm-4" id="isGovtServiceDiv">
												<div class="form-group">
													<label class="control-label">Are you already in
														Government Service? </label>
													<s:label name="isGovtService" id="isGovtService"
														value="%{isGovtService}" cssClass="form-control" />
												</div>
											</div>
											<s:if
												test="%{isGovtService != null && isGovtService != '' && isGovtService == 'Yes'}">
												<div class="row">
													<div class="col-sm-4">
														<input type="checkbox" name="govtServChkBoxDisabled"
															id="govtServChkBoxDisabled" checked disabled /> <span
															class="font12 english_ins"> I agree to provide No
															Objection Certificate from the concerned Government
															Department at the time of Certificate Verification. </span>
														<s:hidden id="govtServChkBox" value="%{govtServChkBox}"
															name="govtServChkBox" />
													</div>
												</div>
											</s:if>
										</div>
										<s:if
											test='%{isGovtService != "" && isGovtService != null && isGovtService == "Yes"}'>
											<div class="row">
												<div class="col-sm-4" id="orgNameDiv">
													<div class="form-group">
														<div class="hidden-xs"></div>
														<label class="control-label">Department Name<s:text
																name="applicationForm.religionBelief" />
														</label>
														<s:label id="orgName" cssClass="form-control wordWrap"
															value="%{orgName}" name="orgName" />
													</div>
												</div>
												<div class="col-sm-4" id="currentDesigDiv">
													<div class="form-group">
														<div class="hidden-xs"></div>
														<label class="control-label">Current Designation<s:text
																name="applicationForm.religionBelief" />
														</label>
														<s:label id="currentDesig"
															cssClass="form-control wordWrap" value="%{currentDesig}"
															name="currentDesig" />
													</div>
												</div>
												<div class="col-sm-4" id="placeOfWorkeDiv">
													<div class="form-group">
														<div class="hidden-xs"></div>
														<label class="control-label">Place of Work<s:text
																name="applicationForm.religionBelief" />
														</label>
														<s:label id="placeOfWork" cssClass="form-control wordWrap"
															value="%{placeOfWork}" name="placeOfWork" />
													</div>
												</div>
											</div>
											<div class="row" id="govtDateDiv">
												<div class="col-sm-4">
													<div class="form-group dateInput">
														<label class="control-label mb8">Date of Joining
															in the Service <%-- <s:text
													name="applicationForm.commIssueDate" /> --%>
														</label>
														<s:label id="govtDate" name="govtDate"
															cssClass="form-control">
														</s:label>
													</div>
												</div>
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Period of Service as
															on the Date of Notification <s:property value="%{notificationEndDate}" /><br /> <%-- <s:text name="login.ageason" /> --%>
														</label>
														<div class="clear"></div>
														<s:label value="%{govtAge}" id="govtAge"
															cssClass="form-control wordWrap" />
													</div>
												</div>
											</div>
										</s:if>
									</div>
								</div>
							</div>
							
							<div class="clear mt10"></div>
							<div class="accordion">
								<div id="IdProofDiv">
									<h3 title="Photo ID Proof Details">Photo ID Proof Details</h3>
									<div class="accordion">
										<div class="row">
											<div class="col-sm-6" id="photoIDProof1Div">
												<div class="form-group">
													<label class="control-label">Photo ID Proof <br>
														<span class="tamil"> <s:text
																name="applicationForm.photoidproof" />
													</span></label>
													<s:label list="photoIdProof" name="photoIDProof1"
														id="photoIDProof1" value="%{photoIDProof1}"
														cssClass="form-control" />
												</div>
											</div>
											<div class="col-sm-6" id="photoIDProof1Td">
												<div class="form-group">
													<label class="control-label"> Photo ID Proof Number</label>
													<s:label id="photoIDProof1Val" name="photoIDProof1Val"
														cssClass="form-control wordWrap"></s:label>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<div class="clear mt10"></div>
							<div class="accordion">
								<div id="AddressDiv">
									<h3 title="Address Details">
										Address Details
										<%-- <s:text name="header.AddressDetails" /> --%>
									</h3>
									<div class="accordion">
										<div class="row">
											<div class="col-sm-12">
												<p class="WeightBold">
													Permanent Address
													<%-- <s:text name="applicationForm.permanentAddress" /> --%>
												</p>
											</div>
										</div>
										<div class="row">
											<s:if
												test='%{addressBean.addressFiled1!=null && addressBean.addressFiled1!=""}'>
												<div class="col-sm-8 col-xs-12">
													<div class="form-group">
														<label class="control-label"> Address <s:text
																name="applicationForm.add1" /></label>
														<s:label value="%{addressBean.addressFiled1}"
															id="addressFiled1" cssClass="wordWrap form-control" />
													</div>
												</div>
											</s:if>
											<div class="col-sm-4 col-xs-12" id="stateDropdown">
												<div class="form-group">
													<label class="control-label">State <s:text
															name="applicationForm.stateUnion" /></label>
													<s:label value="%{stateVal}" id="stateVal"
														cssClass="form-control wordWrap" />
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-4" id="stateDropdown">
												<div class="form-group">
													<label class="control-label">District <span
														class="tamil"> <s:text name="additional.district" />
													</span>
													</label>
													<s:if test='%{districtVal!=null && districtVal!=""}'>
														<div id="districtSelect">
															<s:label id="districtList" name="districtVal"
																value="%{districtVal}" cssClass="form-control wordWrap"></s:label>
														</div>
													</s:if>
													<s:if
														test='%{districtValother!=null && districtValother!=""}'>
														<div id="districtTextarea">
															<s:label id="districtValother" name="districtValother"
																value="%{districtValother}"
																cssClass="form-control wordWrap"></s:label>
														</div>
													</s:if>
												</div>
											</div>
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">City / Village <span
														class="tamil"> <s:text name="applicationForm.city" />
													</span>
													</label>
													<div id="policeTextarea">
														<s:label id="cityName" name="addressBean.cityName"
															cssClass="form-control wordWrap"
															value="%{addressBean.cityName}" ondrop="return false;"></s:label>
													</div>
												</div>
											</div>
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Pincode <s:text
															name="applicationForm.pincode" />
													</label>
													<s:label value="%{addressBean.pinCode}"
														cssClass="form-control wordWrap" id="pinCode" />
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-12 text-left ViewpageLabel">
												<p class="WeightBold">
													Correspondence Address
													<%-- <s:text name="applicationForm.commAddress" /> --%>
												</p>
											</div>
										</div>
										<div class="">
											<div class="row">
												<div class="col-sm-8 col-xs-12">
													<div class="form-group">
														<label class="control-label">Address <%-- <s:text
																name="applicationForm.add1" /> --%>
														</label>
														<s:label value="%{addressBean.alternateAddressFiled1}"
															id="alternateAddressFiled1"
															cssClass="wordWrap form-control" />
													</div>
												</div>
												<div class="col-sm-4 col-xs-12" id="alternateStateDisplay">
													<div class="form-group">
														<label class="control-label">State <s:text
																name="applicationForm.stateUnionC" /></label>
														<s:label value="%{altStateVal}" id="altStateVal"
															cssClass="form-control wordWrap" />
													</div>
												</div>
											</div>
											<div class="row">
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">District<s:text
																name="applicationForm.districtC" /></label>
														<s:if test='%{altDistrictVal!=null && altDistrictVal!=""}'>
															<div id="districtSelect">
																<s:label id="districtList" name="altDistrictVal"
																	value="%{altDistrictVal}"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</s:if>
														<s:if
															test='%{altDistrictValOthers!=null && altDistrictValOthers!=""}'>
															<div id="districtTextarea">
																<s:label id="altDistrictValOthers"
																	name="altDistrictValOthers"
																	value="%{altDistrictValOthers}"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</s:if>
													</div>
												</div>
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">City / Village <span
															class="tamil"> <s:text name="applicationForm.city" />
														</span>
														</label>
														<div id="policeTextarea1">
															<s:label name="addressBean.alternateCity"
																value="%{addressBean.alternateCity}"
																cssClass="form-control wordWrap" id="alternateCityother"></s:label>
														</div>
													</div>
												</div>
												<div class="col-sm-4 col-xs-12">
													<div class="form-group">
														<label class="control-label">Pincode <s:text
																name="applicationForm.pincode" />
														</label>
														<s:label value="%{addressBean.alternatePinCode}"
															id="alternatePinCode" cssClass="form-control wordWrap" />
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							
							<div class="clear mt10"></div>
							<div class="accordion">
								<div id="MotherTongueDiv">
									<h3 title="Mother Tongue">
										Mother Tongue
										<%-- <s:text name="applicationForm.appliedDistTitle" /> --%>
									</h3>
									<div class="accordion">
										<div class="row">
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">Mother Tongue<%-- <s:text name="applicationForm.appliedDist" /> --%>
													</label>
													<s:label value="%{motherTongue}" id="motherTongue"
														cssClass="form-control wordWrap" />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					
					<div class="clear mt10"></div>
					<div class="accordions">
						<div id="AcademicDiv">
							<h1 class="pageTitle" title="Educational Qualification ">
								Educational Qualification</h1>
							<div class="accordion">
								<div class="row">
									<div class="col-sm-12 text-right ViewpageLabel">
										<a href="javascript:void(0)"
											onclick="goToMenu('CandidateAction_getCandidateEducationalDetailsPage.action')">Edit</a>
									</div>
								</div>
								<div id="educationformDetails">
									<s:iterator value="educationDtlsList" status="stat" var="currentObject">
										<h3>
											<s:hidden name="degreeSelected" value="56"
												label="degreeSelected" id="degreeSelectedUser%{#stat.index}" />
											<s:label cssClass="h3_%{#stat.index}" title="%{examination}"
												value="%{examination}"></s:label>
										</h3>
										<div class="accordion">
											<div class="row">
												<s:if
													test="%{examination=='10th / SSLC' || examination=='12th / HSC' || examination=='Diploma'}">
													<s:if test='%{university !=null && university!=""}'>
														<div class="col-sm-4">
															<div class="form-group">
																<s:if test="%{examination=='Diploma'}">
																	<label class="control-label">Diploma Name </label>
																</s:if>
																<s:else>
																	<label class="control-label">Name of the Board
																	</label>
																</s:else>
																<s:label value="%{university}" label="university"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</div>
													</s:if>
													<s:if test='%{universityOth!=null && universityOth!=""}'>
														<div class="col-sm-4">
															<div class="form-group">
																<s:if test="%{examination=='Diploma'}">
																	<label class="control-label">Other Equivalent
																		Diploma </label>
																</s:if>
																<s:else>
																	<label class="control-label">Other Board </label>
																</s:else>
																<s:label value="%{universityOth}" label="universityOth"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</div>
													</s:if>
												</s:if>
												<s:if test="%{examination=='Diploma'}">
													<div class="clear"></div>
													<s:if test='%{prdOfStudyFrm!=null && prdOfStudyFrm!=""}'>
														<div class="col-sm-4">
															<div class="form-group">
																<label class="control-label">Period of Study
																	From </label>
																<s:label value="%{prdOfStudyFrm}" label="prdOfStudyFrm"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</div>
													</s:if>
													<s:if test='%{prdOfStudyTo!=null && prdOfStudyTo!=""}'>
														<div class="col-sm-4">
															<div class="form-group">
																<label class="control-label">Period of Study To
																</label>
																<s:label value="%{prdOfStudyTo}" label="prdOfStudyTo"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</div>
													</s:if>
													<s:if test='%{duration!=null && duration!=""}'>
														<div class="col-sm-4">
															<div class="form-group">
																<label class="control-label">Duration of Study
																	(No. of Years) </label>
																<s:label value="%{duration}" label="duration"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</div>
													</s:if>
													<s:if test='%{institution!=null && institution!=""}'>
														<div class="col-sm-4">
															<div class="form-group">
																<label class="control-label">Name of Institution
																</label>
																<s:label value="%{institution}" label="institution"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</div>
													</s:if>
												</s:if>

												<s:if
													test="%{examination=='10th / SSLC' || examination=='12th / HSC' || examination=='Diploma'}">
													<s:if test='%{dateOfPassing!=null && dateOfPassing!=""}'>
														<div class="col-sm-4">
															<div class="form-group">
																<label class="control-label">Month & Year of
																	Passing </label>
																<s:label value="%{dateOfPassing}" label="dateOfPassing"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</div>
														<s:if
															test='%{universityOth!=null && universityOth!="" && examination!="Diploma"}'>
															<div class="clear"></div>
														</s:if>
													</s:if>

													<s:if test="%{examination=='10th / SSLC' || examination=='12th / HSC' || examination=='Diploma'}">
														<s:if test='%{dipMarksYesNo!=null && dipMarksYesNo!=""}'>
															<div class="col-sm-4">
																<div class="form-group">
																	<label class="control-label">Do you have marks
																		for the Diploma Course? </label>
																	<s:label value="%{dipMarksYesNo}" label="dipMarksYesNo"
																		cssClass="form-control wordWrap"></s:label>
																</div>
															</div>
															<div class="clear"></div>
														</s:if>
															<%-- <s:if test="%{dipMarksYesNo=='Yes'}"> --%>
																<s:if test='%{totalMarks!=null && totalMarks!=""}'>
																	<div class="col-sm-4">
																		<div class="form-group">
																			<label class="control-label">Total Maximum
																				Marks </label>
																			<s:label value="%{totalMarks}" label="totalMarks"
																				cssClass="form-control wordWrap"></s:label>
																		</div>
																	</div>
																</s:if>
																<s:if test='%{obtainedMarks!=null && obtainedMarks!=""}'>
																	<div class="col-sm-4">
																		<div class="form-group">
																			<label class="control-label">Total Obtained
																				Marks </label>
																			<s:label value="%{obtainedMarks}"
																				label="obtainedMarks"
																				cssClass="form-control wordWrap"></s:label>
																		</div>
																	</div>
																</s:if>
															<%-- </s:if> --%>
														
													</s:if>

													<s:if test='%{percentage!=null && percentage!=""}'>
														<div class="col-sm-4">
															<div class="form-group">
																<label class="control-label">Percentage of Marks
																</label>
																<s:label value="%{percentage}" label="percentage"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</div>
													</s:if>

													<s:if
														test='%{medOfInstruction!=null && medOfInstruction!=""}'>
														<div class="col-sm-4">
															<div class="form-group">
																<label class="control-label">Medium of
																	Instruction </label>
																<s:label value="%{medOfInstruction}"
																	label="medOfInstruction"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</div>
													</s:if>

													<s:if test='%{tamilLang!=null && tamilLang!=""}'>
														<div class="col-sm-4">
															<div class="form-group">
																<label class="control-label">Have you studied
																	Tamil as one of the language (Part-1) </label>
																<s:label value="%{tamilLang}" label="tamilLang"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</div>
													</s:if>
												</s:if>

												<s:if
													test="%{examination=='Under Graduate' || examination=='Post Graduate'}">
													<s:if test="%{examination=='Under Graduate'}">
														<s:if test='%{ugYesNo!=null && ugYesNo!=""}'>
															<div class="col-sm-4">
																<div class="form-group">
																	<label class="control-label">Do you have UG
																		degree? </label>
																	<s:label value="%{ugYesNo}" label="ugYesNo"
																		cssClass="form-control wordWrap"></s:label>
																</div>
															</div>
														</s:if>
													</s:if>
													<s:if test="%{examination=='Post Graduate'}">
														<s:if test='%{pgYesNo!=null && pgYesNo!=""}'>
															<div class="col-sm-4">
																<div class="form-group">
																	<label class="control-label">Do you have PG
																		degree? </label>
																	<s:label value="%{pgYesNo}" label="pgYesNo"
																		cssClass="form-control wordWrap"></s:label>
																</div>
															</div>
														</s:if>
													</s:if>
													<s:if test='%{ugYesNo =="Yes" || pgYesNo=="Yes"}'>
														<s:if test='%{specialization!=null && specialization!=""}'>
															<div class="col-sm-4">
																<div class="form-group">
																	<label class="control-label">Specialization </label>
																	<s:label value="%{specialization}"
																		label="specialization"
																		cssClass="form-control wordWrap"></s:label>
																</div>
															</div>
														</s:if>
														<s:if test='%{dateOfPassing!=null && dateOfPassing!=""}'>
															<div class="col-sm-4">
																<div class="form-group">
																	<label class="control-label">Month & Year of
																		Passing </label>
																	<s:label value="%{dateOfPassing}" label="dateOfPassing"
																		cssClass="form-control wordWrap"></s:label>
																</div>
															</div>
															<div class="clear"></div>
														</s:if>
													</s:if>
												</s:if>

												<s:if test="%{examination=='Post Graduate'}">
													<s:if test='%{pgDipYesNo!=null && pgDipYesNo!=""}'>
														<div class="col-sm-4">
															<div class="form-group">
																<label class="control-label">Do you have PG
																	Diploma </label>
																<s:label value="%{pgDipYesNo}" label="pgDipYesNo"
																	cssClass="form-control wordWrap"></s:label>
															</div>
														</div>
													</s:if>

													<s:if test='%{pgDipYesNo=="Yes"}'>
														<s:if
															test='%{pgDipSpecialization!=null && pgDipSpecialization!=""}'>
															<div class="col-sm-4">
																<div class="form-group">
																	<label class="control-label">Specialization </label>
																	<s:label value="%{pgDipSpecialization}"
																		label="pgDipSpecialization"
																		cssClass="form-control wordWrap"></s:label>
																</div>
															</div>
														</s:if>
														<s:if
															test='%{pgDipDateofpassing!=null && pgDipDateofpassing!=""}'>
															<div class="col-sm-4">
																<div class="form-group">
																	<label class="control-label">Month & Year of
																		Passing </label>
																	<s:label value="%{pgDipDateofpassing}"
																		label="pgDipDateofpassing"
																		cssClass="form-control wordWrap"></s:label>
																</div>
															</div>
														</s:if>
													</s:if>
												</s:if>

												<s:if test='%{pstmPreference!=null && pstmPreference!=""}'>
													<div class="col-sm-4">
														<div class="form-group">
															<label class="control-label">Are you eligible to
																avail PSTM preference? </label>
															<s:label value="%{pstmPreference}" label="pstmPreference"
																cssClass="form-control wordWrap"></s:label>
														</div>
													</div>
													<s:if test='%{pstmPreference=="Yes"}'>
														<s:if test='%{tamilMedium!=null && tamilMedium!=""}'>
															<div class="col-sm-4">
																<div class="form-group">
																	<label class="control-label">Have you studied
																		in Tamil medium from 1st standard to 12th standard? </label>
																	<s:label value="%{tamilMedium}" label="tamilMedium"
																		cssClass="form-control wordWrap"></s:label>
																</div>
															</div>
														</s:if>
														<s:if test='%{ugTamilMedium!=null && ugTamilMedium!=""}'>
															<div class="col-sm-4">
																<div class="form-group">
																	<label class="control-label">Have you studied
																		your Diploma in Tamil medium? </label>
																	<s:label value="%{ugTamilMedium}" label="ugTamilMedium"
																		cssClass="form-control wordWrap"></s:label>
																</div>
															</div>
														</s:if>
													</s:if>
												</s:if>
												<s:elseif test='%{pstmPreferenceVal=="NO"}'>
													<div class="col-sm-4" id="noTamilPstm">
														<div class="form-group">
															<label class="control-label"> Are you eligible to
																avail PSTM preference? </label>
															<s:label name="pstmPreferenceVal" value="NO"
																cssClass="form-control" />
														</div>
													</div>
												</s:elseif>
											</div>
										</div>
										<s:hidden name="educationDtlsList[%{#stat.index}].mandatory"
											value="%{mandatory}"></s:hidden>
									</s:iterator>
								</div>
							</div>
						</div>
					</div>

					<div class="clear mt10"></div>
					<div class="accordions">
						<div id="UploadDiv">
							<h1 class="pageTitle" title="Upload Documents">Upload
								Documents</h1>
							<div class="accordion">
								<div class="row">
									<div class="col-sm-12 text-right ViewpageLabel">
										<a href="javascript:void(0)"
											onclick="goToMenu('CandidateAction_showUploadDocument.action')">Edit</a>
									</div>
								</div>
								<div class="accordion">
									<div class="table-responsive">
										<table width="100%" id="myTable" cellspacing="3" cellpadding="3" border="0">
											<s:iterator value="uploadList" status="stat">
												<tr>
													<td width="50%"><span
														style="width: 90%; display: inline-block;"> <s:property
																value="docLabel1" />
													</span></td>

													<td width="20%" class="wordwrap"><strong><s:property
																value="documentFileName1" /></strong><br /></td>
													<s:if test='%{documentFileName1 != null}'>
														<td width="30%"><strong><a
																id="<s:property value="#stat.index"/>"
																onclick="displayImage(this.id);" class="documents">
																	<label
																	style="color: blue; cursor: pointer; float: right;"
																	id="hidetab<s:property value="#stat.index"/>">
																		Verify Document </label>
															</a> <label style="color: gray; float: right;"
																id="veritext<s:property value="#stat.index"/>">
															</label></strong></td>
													</s:if>
													<s:hidden id="imgDoc1"></s:hidden>
													<s:hidden id="documentFileName%{#stat.index}"
														value="%{documentFileName1}"></s:hidden>
													<s:hidden id="candidateDocPk%{#stat.index}"
														value="%{candidateDocPk1}"></s:hidden>
													<s:hidden id="ocdFlagValue%{#stat.index}"
														value="%{ocdFlagValue}"></s:hidden>
													<td>
														<div id="overlay1" class="web_dialog_overlay_declr"></div>
														<div id="myModal<s:property value="#stat.index"/>"
															class="modal">
															<div class="modal-content">
																<div>
																	<button type="button" class="close"
																		id="close<s:property value="#stat.index"/>"
																		onclick="closeDiv(<s:property value="#stat.index"/>);">&times;</button>
																</div>
																<br /> <br />
																<s:if
																	test='%{documentFileName1.toLowerCase().endsWith("pdf")}'>
																	<div>
																		<object
																			data="servlet/StartupServlet.do?pdf=pdf&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagValue"/>_<s:property value="documentFileName1"/>#toolbar=0&navpanes=0"
																			type="application/pdf" width="100%" height="350"></object>
																	</div>
																</s:if>
																<s:elseif
																	test='%{documentFileName1.toLowerCase().endsWith("png")}'>
																	<div>
																		<object
																			data="servlet/StartupServlet.do?pdf=pdf&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagValue"/>_<s:property value="documentFileName1"/>#toolbar=0&navpanes=0"
																			type="image/png" width="100%" height="350"></object>
																	</div>
																</s:elseif>
																<s:elseif
																	test='%{documentFileName1.toLowerCase().endsWith("jpeg")}'>
																	<div>
																		<object
																			data="servlet/StartupServlet.do?pdf=pdf&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagValue"/>_<s:property value="documentFileName1"/>#toolbar=0&navpanes=0"
																			type="image/png" width="100%" height="350"></object>
																	</div>
																</s:elseif>
																<s:elseif
																	test='%{documentFileName1.toLowerCase().endsWith("jpg")}'>
																	<div>
																		<object
																			data="servlet/StartupServlet.do?pdf=pdf&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagValue"/>_<s:property value="documentFileName1"/>#toolbar=0&navpanes=0"
																			type="image/png" width="100%" height="350"></object>
																	</div>
																</s:elseif>
																<s:else>
																	<div class="screenimages">
																		<img
																			src="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagValue"/>_<s:property value="documentFileName1"/>" />
																	</div>
																</s:else>
																<div>
																	<s:checkbox id="verify%{#stat.index}"
																		name="checkMe%{#stat.index}" fieldValue="false"
																		value="false" />
																	I confirm that the document uploaded by me is correct.
																	<span class="tamil"><s:text
																			name="confirmuploaded" /></span> <br /> <input
																		type="button" class="submitBtn button-gradient"
																		id="savclose<s:property value="#stat.index"/>"
																		value="Save" />
																</div>
															</div>
														</div>
													</td>
												</tr>
											</s:iterator>
										</table>
									</div>
								</div>
							</div>
						</div>
					</div>

					<%-- <div class="clear m10"></div>
						<h3 title="Applicable Fees (INR)" class="pageTitle">
						Applicable Fees (INR) -
						<s:label value="%{applicableFee}" />
					</h3> --%>

					<div class="clear m10"></div>
					<div class="accordions">
						<div id="DeclarationDiv">
							<h1 title="Declaration" class="pageTitle">Declaration</h1>
							<div class="accordion nonEditable">
								<ul class="declaration">
									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">I hereby declare that all the
											particulars furnished in this application are true, correct
											and complete to the best of my knowledge and believe. In the
											event of any information being found false or incorrect or
											ineligibility being detected before or after the selection,
											action can be taken against me by the MRB.</p>
									</li>
									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">I hereby declare that I will not be a
											party to any kind of canvassing on my behalf.</p>
									</li>
									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">I further declare that I fulfil all the
											eligibility conditions prescribed for admission to this post.</p>
									</li>

									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">I have informed my employer in writing
											that I am applying for this post and furnish the NOC for this
											purpose (if applicable).</p>
									</li>

									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">I have gone through the instructions
											etc. to candidates and the Board's Notification for this
											recruitment, before filling up the application form and I am
											eligible to apply for this post.</p>
									</li>

									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">I declare that I possess the Medical
											Standards prescribed for the post(s) which I am now applying.</p>
									</li>

									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">I certify that I have not been debarred
											/ disqualified by the Board or any other recruiting agency.</p>
									</li>

									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">I am not a dismissed Government
											Employee.</p>
									</li>

									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">There is no criminal case filed against
											me in the Police Station / Court.</p>
									</li>

									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">There is no Vigilance Case filed against
											me.</p>
									</li>

									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">I hereby declare that my character /
											antecedents are suitable for appointment to this post.</p>
									</li>

									<li class="declare fl-rigt" style="width: 97%">
										<p class="font14">I declare that I do not have more than
											one living spouse / I am unmarried.</p>
									</li>
								</ul>
							</div>
							<br>
							<div class="clear"></div>
							<div class="ml40" style="display: inline-block;">
								<input type="checkbox" name="declaration" id="checkbox2" />
								<div style="display: inline-block;" class="declare"
									style="width: 85%">
									<p class="font14">I accept all the above declarations.</p>
								</div>
							</div>
						</div>
					</div>

					<div class="clear mt10 mb40"></div>
					<div class="row">
						<div class="col-sm-6 col-xs-12 text-left">
							<p class="font14 WeightBold mt50">
								Date:
								<%
								String pattern = "dd-MMM-yyyy";
									SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

									String date = simpleDateFormat.format(new Date());
							%>
								<%=date%>
							</p>
						</div>
						<div class="col-sm-6 col-xs-12 text-right">
							<div class="row" id="signatureImage">
								<div class="col-sm-12 col-xs-12">
									<img class="img-responsive fl-rigt" src="SignatureImage.jpg"
										style="height: 50px;" id="inputStreamForImage1"
										onerror="onErrorMessage('signatureImage')" />
								</div>
							</div>
							<div class="row">
								<div class="col-sm-12 text-right">
									<p>(Signature of the Candidate)</p>
								</div>
							</div>
							<s:if test='%{uploadImageStatus == "A"}'>
								<div class="row">
									<div class="col-sm-12">
										<a href="javascript:void(0)"
											onclick="goToMenu('CandidateAction_showUploadSignature.action')">Edit
											Signature</a>
									</div>
								</div>
							</s:if>
						</div>
					</div>
					<div id="output"></div>
				</div>
				<s:token />
			</s:form>
		</div>
	</div>
	<div class="clear"></div>
	<div id="overlay" class="web_dialog_overlay_declr"></div>
	<div class="fullscreen-container" id="dialog">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onclick="HideDialog()">&times;</button>
					<h2 class="modal-title">
						Declaration
						<%-- <span class="tamil"> <s:text
									name="header.Declaration" />
							</span> --%>
					</h2>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12">
							<p class="font14">
								I have ensured that everything has been filled correctly, before
								proceeding to payment. I know that the data entered can't be
								changed once proceeded to payment. Hence, before proceeding
								further, the details already entered are verified by me to
								ensure that they are correct. I have found that the details are
								correct, and now proceed to make online payment towards
								application fees.
								<%-- <span class="tamil"><s:text
											name="applicationForm.declarationDB" /></span> --%>
						</div>
						<div class="clear"></div>
						<div style="display: inline-block;" class="ml20">
							<input type="checkbox" name="declaration1" id="checkbox1" />
						</div>
						<div class="declare fl-rigt" style="width: 78%">
							<p class="font14">I accept the above declaration.</p>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-4 col-sm-offset-3">
							<input type="button" id="btnSubmitId" name="btnSubmit"
								value="Submit" onclick="redirectTpaymentPage()"
								class="ripple1 btn btn-warning btn-block mt10" />
						</div>
						<div class="col-sm-4">
							<input type="button" name="btnCancel" value="Cancel"
								onclick="HideDialog();"
								class="ripple1 btn btn-default btn-block mt10" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<s:if test='%{statusID <=10 || statusID ==40}'>
		<div class="countinuebg row mt20">
			<div class="container">
				<div class="row">
					<div class="col-sm-3 col-sm-offset-9 col-xs-10 col-xs-offset-1">
						<input type="button" value="Submit Form"
							class="submitBtn btn btn-block btn-warning"
							onclick="validatePage()" id="pay" />
					</div>
				</div>
			</div>
		</div>
	</s:if>
</div>
<div id="overlayEdit" class="web_dialog_overlay_declr"></div>
<div class="fullscreen-container" id="dialogEdit">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onclick="HideDialogEdit()">&times;</button>
				<h2 class="modal-title">
					Warning 1<span class="tamil"> <s:text
							name="header.Declaration" />
					</span>
				</h2>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<p class="font14">Please ensure you have Edited/ Modified all
							the details correctly. No changes will be allowed after save.
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="row">
					<div class="col-sm-3 col-sm-offset-3">
						<input type="button" id="btnSubmitId" name="btnSubmit"
							value="Submit" onclick="ShowDialog1Edit()"
							class="ripple1 btn btn-warning btn-block mt10" />
					</div>
					<div class="col-sm-3">
						<input type="button" name="btnCancel" value="Cancel"
							onclick="HideDialogEdit();"
							class="ripple1 btn btn-default btn-block mt10" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<div class="clear"></div>
<div id="overlayWarn2" class="web_dialog_overlay_declr"></div>
<div class="fullscreen-container" id="dialogWarn2">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onclick="HideDialogWarn()">&times;</button>
				<h2 class="modal-title">
					Warning 2<span class="tamil"> <s:text
							name="header.Declaration" />
					</span>
				</h2>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<p class="font14">Are you sure you want to Save? No changes
							will be allowed after save.
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="row">
					<div class="col-sm-3 col-sm-offset-3">
						<input type="button" id="btnSubmitId" name="btnSubmit"
							value="Submit" onclick="finalWarning()"
							class="ripple1 btn btn-warning btn-block mt10" />
					</div>
					<div class="col-sm-3">
						<input type="button" name="btnCancel" value="Cancel"
							onclick="HideDialogWarn();"
							class="ripple1 btn btn-default btn-block mt10" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
