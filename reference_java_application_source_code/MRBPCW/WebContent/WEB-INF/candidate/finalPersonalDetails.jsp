<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<script type="text/javascript" src="js/moment.min.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<script src="js/bower_components/pdfmake/build/pdfmake.js"></script>
<script src="js/bower_components/pdfmake/build/vfs_fonts.js"></script>
<script type="text/javascript">
  
  function hideDialog1(){
    document.getElementById("declaration").checked = false;
    $("#declar").hide();
    $("#overlay").hide();
    $("#dialog").hide();
    $("#overlay1").hide();
    $("#dialog1").hide();
  }
  
    function  menuLinks1(link)
    {
      document.formDtls.action = link+"?popup=popup";
      document.formDtls.submit();
      //$("#applicationForm").attr('action',link);
      //$("#applicationForm").submit();
    }
    function auditEntry()
    {
      document.getElementById("auditentry").value="auditentry";
      document.formDtls.submit();
      $("#btnSubmitId").addClass('subBtnDisabled'); 
    }
    
function validatePage(){

    dataString = "action=Accept Declaration&audittrail=accepted Declaration";
    $.ajax({
    	type: 'POST',
      url: "CandidateAction_AuditTrailForDeclaration.action",
      async: true,
      data: dataString,
      
      error:function(ajaxrequest)
      {
        window.reload();
      },
      success:function(responseText)
      {
        document.formDtls.submit();
      }
    });
    

}
function onErrorMessage(id){
  $("#"+id).hide();
}

function ShowDialog1(){

    dataString = "action=Accept Declaration&audittrail=accepted Declaration";
    $.ajax({
    	type: 'POST',    	
      url: "CandidateAction_AuditTrailForDeclaration.action",
      async: true,
      data: dataString,
      
      error:function(ajaxrequest)
      {
        window.reload();
      },
      success:function(responseText)
      {
        document.formDtls.submit();
      }
    });
    
    //Commeted below code For replace Submit btn action to continue btn
    //var menuKey = document.getElementById("menuKey").value;
    //dataString = "menuKey="+menuKey;
    //$.ajax({
    //  url: "CandidateAction_updateCandidateStageFinalSubmit.action",
    //  async: true,
    //  data: dataString,
    //  error:function(ajaxrequest)
    //  {
    //    window.reload();
    //  },
    //  success:function(responseText)
    //  {
    //    //document.formDtls.submit();
    //  }
    //});
    auditEntry();
    //$("#overlay1").show();
    //$("#dialog1").fadeIn(300);
          
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

  function HideDialog() {
    $("#overlay").hide();
    $("#declar").hide();
    document.getElementById("declaration").checked = false;
    var dataString = "action=Decline Declaration&audittrail=declined Declaration";
    $.ajax({
    type: 'POST',    	
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
    $("#dialog").fadeOut(300);
  }
  
  function checkForImageChange() {
		try {
			var responseDiv = $.trim($("#divImageUploadResponse").html());

			if (responseDiv != "") {
				responseDiv = responseDiv.replace("<PRE>", "").replace("</PRE>", "");
				responseDiv = responseDiv.replace("<pre>", "").replace("</pre>", "");
				responseDiv = $.trim(responseDiv);
				responseDiv = responseDiv.substring(responseDiv.indexOf("(message)") + 9, responseDiv.indexOf("(/message)"));

				$("#divImageUploadResponse").html("");
				var responseArr = responseDiv.split(",");
				if (responseArr[0] == "0") {
					var timestamp = new Date().getTime();
					$("#imageTd").fadeOut(0);
					var imageHtml = '<img width="200" height="150" src="PhotoImage.jpg?t=' + timestamp + '" name="inputStreamForImage" id="inputStreamForImage"/>';
					$("#imageTd").html(imageHtml);
					$("#imageTd").fadeIn(1000);
					// $("#inputStreamForImage").removeAttr("src").attr("src", "PhotoImage.jpg");
					$("#uploadedImage").show();
				} else if (responseArr[0] == "1") {
					$("#test").show();
				}
			}
		} catch (ex) {

		}
		setTimeout("checkForImageChange();", 200);
	}

$(document).ready(function() {

    $('.subNavBg').hide();
    $('#block9').hide();
    setTimeout("checkForImageChange();",2000);

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
  })
  
/* jQuery(function($) {
		$(function() {
	    	$("#accordion > div").accordion({ header: "h3", collapsible: true });
		})
	}); */

</script>

<style>
.manadetory-fields {
	display: none;
}

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
</style>

<div class="container-fluid ViewpageLabel">
	<div id="dashboard" >
		<s:hidden id='hddAddressChkBox'></s:hidden>
		<div class="padding_leftright">
			<!-- Box Container Start -->
			<s:form id="applicationForm" name="formDtls"
				action="CandidateAction_updateCandidateStageFinalSubmit">
				<s:hidden name="menuKey" id="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<s:hidden name="auditentry" id="auditentry" />
				<s:hidden name="viewFlag" id="viewFlag" value="true"></s:hidden>
				<s:hidden name="isDataFound"
					value='<s:property value="formConfirm"/>' />
				<div class="container common_dashboard  tabDiv effect2">
					<div class="container">
						<div id="msg" class="mb20"></div>
						<div id="error-massageAppForm" style="display: none; color: red;"
							class="error-massage"></div>
						<s:actionmessage escape="false" cssClass="msgg" />
					</div>
					<div class="accordions">
						<div id="PersonalDiv">
							<h1 class="pageTitle" title="Personal Details">
								Personal Details
								<s:text name="header.personaldetails" />
							</h1>
							<div class="accordion">
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
											</div>
										</div>
									</div>
									<div class="col-sm-10 col-sm-pull-2">
										<%-- <div class="row mb10">
											<div class="col-sm-4">
												<label class="control-label">Post Applying for </label>
												<s:label value="%{disciplinName}" id="disciplinName"
													cssClass="form-control wordWrap"></s:label>
												<s:hidden id="disciplinNameHdn" value="%{disciplineType}"
													name="disciplinNameHdn" />
											</div>
										</div> --%>
										<div class="row">
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Name <s:text
															name="applicationForm.name"></s:text>
													</label>
													<s:label value="%{personalDetailsBean.candidateFirstName}"
														cssClass="form-control wordWrap"></s:label>
												</div>
											</div>
											<s:if
												test='%{personalDetailsBean.candidatePrefix != "" && personalDetailsBean.candidatePrefix != null}'>
												<div class="col-sm-4 col-xs-12">
													<div class="form-group">
														<label class="control-label">Initial <s:text
																name="applicationForm.title"></s:text>
														</label>
														<s:label value="%{personalDetailsBean.candidatePrefix}"
															cssClass="form-control wordWrap"></s:label>
													</div>
												</div>
											</s:if>
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label"> Nationality <s:text
															name="applicationForm.nationality" />
													</label>
													<s:label value="%{nationality}" id="nationality"
														cssClass="form-control wordWrap" />
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Email ID <s:text
															name="applicationForm.emailId" /></label>
													<s:label value="%{personalDetailsBean.email}" id="email"
														cssClass="form-control wordWrap" />
												</div>
											</div>
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Mobile Number <s:text
															name="applicationForm.mobileNo" />
													</label>
													<s:label value="%{personalDetailsBean.mobileNo}"
														id="mobileNo" cssClass="form-control wordWrap" />
												</div>
											</div>
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">Are you a domicile of
														Tamil Nadu? <s:text name="applicationForm.domicileUp" />
													</label>
													<s:label value="%{domicileUp}" id="domicileUp"
														cssClass="form-control wordWrap" />
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Date of Birth <s:text
															name="applicationForm.dateOfBirth" /></label>
													<s:label value="%{dateOfBirth}" id="dateOfBirth"
														cssClass="form-control wordWrap" />
												</div>
											</div>
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Age as on 01/06/2023 <s:text
															name="login.ageason" />
													</label>
													<s:label value="%{personalDetailsBean.age}"
														id="personalDetailsBean.age"
														cssClass="form-control wordWrap" />
													<%-- <s:checkbox name="ageChkBox" id="ageChkBox" disabled="true" />
													<span> I hereby confirm that my age displayed above
														as on 31/03/2023 is correct</span> --%>
												</div>
											</div>
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Gender <s:text
															name="applicationForm.gender" /></label>
													<s:label value="%{genderVal}"
														cssClass="form-control wordWrap"></s:label>
												</div>
											</div>
										</div>
										<div class="row">
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">Marital Status <s:text
															name="applicationForm.maritalStatus" /></label>
													<s:label value="%{mariatalStatus}" id="mariatalStatus"
														cssClass="form-control wordWrap" />
												</div>
											</div>
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Father's Name <s:text
															name="applicationForm.fatherName" />
													</label>
													<s:label value="%{fathersName}" id="fathersName"
														cssClass="form-control wordWrap" />
												</div>
											</div>
											<s:if
												test='%{fathersInitial != "" && fathersInitial != null}'>
												<div class="col-sm-4 col-xs-12">
													<div class="form-group">
														<label class="control-label">Father's Initial <s:text
																name="applicationForm.fatherInitial" />
														</label>
														<s:label value="%{fathersInitial}" id="fathersInitial"
															cssClass="form-control wordWrap" />
													</div>
												</div>
											</s:if>
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Mother's Name <s:text
															name="applicationForm.motherName" /></label>
													<s:label value="%{mothersName}" id="mothersName"
														cssClass="form-control wordWrap" />
												</div>
											</div>
											<s:if
												test='%{fathersInitial == "" || fathersInitial == null}'>
												<div class="clear"></div>
											</s:if>
											<s:if
												test='%{mothersInitial != "" && mothersInitial != null}'>
												<div class="col-sm-4 col-xs-12">
													<div class="form-group">
														<label class="control-label">Mother's Initial <s:text
																name="applicationForm.motherInitial" />
														</label>
														<s:label value="%{mothersInitial}" id="mothersInitial"
															cssClass="form-control wordWrap" />
													</div>
												</div>
											</s:if>
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
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label"> Do you possess
														Community certificate issued by Tamil Nadu Government? <s:text
															name="applicationForm.categoryCert" />
													</label>
													<s:label value="%{categorycertificate}"
														id="categorycertificate" cssClass="form-control wordWrap" />
												</div>
											</div>
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label"> Community <s:text
															name="applicationForm.category" /></label>
													<s:label value="%{categoryValDesc}" id="categoryValDesc"
														cssClass="form-control wordWrap" />
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="clear mt10"></div>
								<div id="AddressDiv">
									<h3 title="Address Details">
										Address Details
										<s:text name="header.AddressDetails" />
									</h3>
									<div class="accordion">
										<div class="row">
											<div class="col-sm-12">
												<p class="WeightBold">
													Permanent Address
													<s:text name="applicationForm.permanentAddress" />
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
											<%-- <s:if
												test='%{addressBean.addressFiled2!=null && addressBean.addressFiled2!=""}'>
												<div class="col-sm-4 col-xs-12">
													<div class="form-group">
														<label class="control-label">Permanent Address
															Line 2 </label>
														<s:label value="%{addressBean.addressFiled2}"
															id="addressFiled2" cssClass="form-control wordWrap" />
													</div>
												</div>
											</s:if> --%>
											<%-- <s:if
												test='%{addressBean.cityNameother!=null && addressBean.cityNameother!=""}'>
												<div class="col-sm-4 col-xs-12">
													<div class="form-group">
														<label class="control-label">Permanent Address -
															City / Town </label>
														<s:label value="%{addressBean.cityNameother}"
															cssClass="form-control wordWrap" id="cityNameother" />
													</div>
												</div>
											</s:if> --%>
										</div>
										<div class="row">
											<div class="col-sm-4 col-xs-12" id="stateDropdown">
												<div class="form-group">
													<label class="control-label">State <s:text
															name="applicationForm.stateUnion" /></label>
													<s:label value="%{stateVal}" id="stateVal"
														cssClass="form-control wordWrap" />
												</div>
											</div>
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">District / City <s:text
															name="applicationForm.district" /></label>
													<s:label value="%{districtVal}" id="districtList"
														cssClass="form-control wordWrap" />
												</div>
											</div>
											<div class="col-sm-4 col-xs-12">
												<div class="form-group">
													<label class="control-label">Pincode 
													<s:text name="applicationForm.pincode" /> </label>
													<s:label value="%{addressBean.pinCode}"
														cssClass="form-control wordWrap" id="pinCode" />
												</div>
											</div>
											<%-- <s:if
												test='%{addressBean.telephoneNo!=null && addressBean.telephoneNo!=""}'>
												<div class="col-sm-4 col-xs-12">
													<div class="form-group">
														<label class="control-label">Permanent Address -
															Telephone No </label>

														<s:label id="telephoneNo" cssClass="form-control wordWrap"
															value="%{addressBean.telephoneNo}" />
													</div>
												</div>
											</s:if> --%>
										</div>
										<div class="row">
											<div class="col-sm-12 text-left ViewpageLabel">
												<p class="WeightBold">
													Address for Communication
													<s:text name="applicationForm.commAddress" />
												</p>
											</div>
										</div>
										<div class="communicationAddress">
											<div class="row">
												<div class="col-sm-8 col-xs-12">
													<div class="form-group">
														<label class="control-label">Address <s:text
																name="applicationForm.add1" />
														</label>
														<s:label value="%{addressBean.alternateAddressFiled1}"
															id="alternateAddressFiled1"
															cssClass="wordWrap form-control" />
													</div>
												</div>
												<%-- <s:if
													test='%{addressBean.alternateAddressFiled2!=null && addressBean.alternateAddressFiled2!=""}'>
													<div class="col-sm-4 col-xs-12">
														<div class="form-group">
															<label class="control-label">Correspondence
																Address - Address Line 2 </label>
															<s:label value="%{addressBean.alternateAddressFiled2}"
																id="alternateAddressFiled2"
																cssClass="wordWrap form-control" />
														</div>
													</div>
												</s:if> --%>
												<%-- <s:if
													test='%{addressBean.alternateCityother!=null && addressBean.alternateCityother!=""}'>
													<div class="col-sm-4 col-xs-12">
														<div class="form-group">
															<label class="control-label">Correspondence
																Address - City / Town </label>
															<s:label value="%{addressBean.alternateCityother}"
																id="alternateCityother" cssClass="form-control wordWrap" />
														</div>
													</div>
												</s:if> --%>
											</div>
											<div class="row">
												<div class="col-sm-4 col-xs-12" id="alternateStateDisplay">
													<div class="form-group">
														<label class="control-label">State <s:text
																name="applicationForm.stateUnionC" /></label>
														<s:label value="%{altStateVal}" id="altStateVal"
															cssClass="form-control wordWrap" />
													</div>
												</div>
												<div class="col-sm-4 col-xs-12">
													<div class="form-group">
														<label class="control-label">District / City <s:text
																name="applicationForm.districtC" /></label>
														<s:label value="%{altDistrictVal}" id="altDistrictList"
															cssClass="form-control wordWrap" />
													</div>
												</div>

												<div class="col-sm-4 col-xs-12">
													<div class="form-group">
														<label class="control-label">Pincode 
													<s:text name="applicationForm.pincode" /> </label>
														<s:label value="%{addressBean.alternatePinCode}"
															id="alternatePinCode" cssClass="form-control wordWrap" />
													</div>
												</div>
												<s:if
													test='%{addressBean.altTelephoneNo!=null && addressBean.altTelephoneNo!=""}'>
													<div class="col-sm-4 col-xs-12">
														<div class="form-group">
															<label class="control-label">Correspondence
																Address - Telephone No </label>
															<s:label value="%{addressBean.altTelephoneNo}"
																cssClass="form-control wordWrap" id="altTelephoneNo" />
														</div>
													</div>
												</s:if>
											</div>
										</div>
									</div>
								</div>
								<!-- <div class="clear"></div>
								<div id="CandidateDiv">
									<h3 title="Section III">Section - III</h3>
									<div class="accordion">
										<div class="row">
											<div class="col-sm-12 text-right">
												<a href="javascript:void(0)"
													onclick="goToMenu('CandidateAction_getCandidateDetails.action')">Edit</a>
											</div>
										</div>
									</div>
								</div> -->
								<div class="clear mt10"></div>
								<div id="PreferenceDiv">
									<h3 title="Applied District for">
										Applied District for
										<s:text name="applicationForm.appliedDistTitle" />
									</h3>
									<div class="accordion">
										<div class="row">
											<div class="col-sm-4">
												<div class="form-group">
													<label class="control-label">Applied District for <s:text
															name="applicationForm.appliedDist" />
													</label>
													<s:label value="%{personalDetailsBean.appliedDist}"
														id="appliedDist" cssClass="form-control wordWrap" />
												</div>
											</div>
											<%-- <div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Preferred Exam Centre City -2 </label>
											<s:label value="%{testCenter2}" id="testCenter2"
												cssClass="form-control wordWrap" />
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Preferred Exam Centre City -3 </label>
											<s:label value="%{testCenter3}" id="testCenter3"
												cssClass="form-control wordWrap" />
										</div>
									</div> --%>
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="clear mt10"></div>
						<h1 class="pageTitle" title="Academic Details ">
							Academic Details
							<s:text name="header.EducationalDetails" />
						</h1>
						<div class="accordion">
							<div id="educationformDetails">
								<s:iterator value="educationDtlsList" status="stat"
									var="currentObject">
									<h3>
										<s:hidden name="degreeSelected" value="56"
											label="degreeSelected" id="degreeSelectedUser%{#stat.index}" />
										<s:label cssClass="h3_%{#stat.index}" title="%{examination}"
											value="%{examination}"></s:label>
									</h3>
									<s:hidden name="educationDtlsList[%{#stat.index}].examination"
										value="%{examination}" label="examination" readonly="true"
										onblur="testFunc()"></s:hidden>
									<s:hidden name="educationDtlsList[%{#stat.index}].enabled"
										id="enabled%{#stat.index}" value="%{enabled}" label="enabled"
										readonly="true"></s:hidden>
									<s:hidden name="educationDtlsList[%{#stat.index}].resultChkBox"
										id="resultChkBox%{#stat.index}" value="%{resultChkBox}"
										label="resultChkBox"></s:hidden>

									<div class="accordion">
										<div class="row">
											<s:if test='%{certNum !=null && certNum!=""}'>
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Certificate Number <s:text
																name="academic.certificate" />
														</label>
														<s:label value="%{certNum}" label="certNum"
															cssClass="form-control wordWrap"></s:label>
													</div>
												</div>
											</s:if>
											<s:if test='%{institution!=null && institution!=""}'>
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Name of School /
															College / Institute <s:text name="academics.name" />
														</label>
														<s:label value="%{institution}" label="institution"
															cssClass="form-control wordWrap"></s:label>
													</div>
												</div>
											</s:if>
											<s:if test='%{degreeSubject!=null && degreeSubject!=""}'>
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Diploma / Degree
															Course <s:text name="academics.degree" />
														</label>
														<s:label value="%{degreeSubject}" label="degreeSubject"
															cssClass="form-control wordWrap"></s:label>
													</div>
												</div>
											</s:if>
										</div>
										<div class="row">
											<s:if test='%{grade!=null && grade!=""}'>
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Major Subject <s:text
																name="academics.major" />
														</label>
														<s:label value="%{grade}" label="grade"
															cssClass="form-control wordWrap"></s:label>
													</div>
												</div>
											</s:if>
											<s:if test='%{dateOfPassing!=null && dateOfPassing!=""}'>
												<div class="col-sm-4">
													<div class="form-group dateInput">
														<label class="control-label">Month / Year of
															Passing <s:text name="academics.month" />
														</label>
														<s:label value="%{dateOfPassing}" label="dateOfPassing"
															cssClass="form-control wordWrap"></s:label>
													</div>
												</div>
											</s:if>
											<s:if test='%{university!=null && university!=""}'>
												<div class="col-sm-4">
													<div class="form-group">
														<s:if
															test="%{examination=='10th / SSLC' || examination=='XII / HSC' || examination=='Diploma'}">
															<label class="control-label">Name of the Board <s:text name="academics.boardName" />
															</label>
														</s:if>
														<s:elseif
															test="%{examination=='Under Graduate' || examination=='Post Graduate'}">
															<label class="control-label">Name of the University <s:text name="academics.nameOfUniversity" />
															</label>
														</s:elseif>
														<s:label value="%{university}" label="university"
															cssClass="form-control wordWrap"></s:label>
													</div>
												</div>
											</s:if>
											<s:if
												test="%{examination!='10th / SSLC' && examination!='XII / HSC'}">
												<div class="clear"></div>
											</s:if>
											<s:if test='%{obtndMarksAgg!=null && obtndMarksAgg!="0"}'>
												<div class="col-sm-4">
													<div class="form-group dateInput">
														<label class="control-label">% of Marks Scored <s:text
																name="academics.marks" />
														</label>
														<s:label value="%{obtndMarksAgg}" label="obtndMarksAgg"
															cssClass="form-control wordWrap"></s:label>
													</div>
												</div>
											</s:if>
										</div>
									</div>
									<s:hidden name="educationDtlsList[%{#stat.index}].mandatory"
										value="%{mandatory}"></s:hidden>
								</s:iterator>
							</div>
						</div>
						<div class="clear mt10"></div>
						<h1 title="Upload Documents" class="pageTitle">
							Upload Documents <s:text
									name="header.UploadDocument" />
						</h1>
						<div class="accordion">
							<table width="100%">
								<s:iterator value="uploadList" status="stat">
									<tr>
										<td width="50%"><span
											style="width: 90%; display: inline-block;"><s:property value="docLabel1" /> 
											<s:if test="ocdFlagValue=='CC'">
												<s:text name="communityblue" />
											</s:if>
											<s:if test="ocdFlagValue=='SSLC'">
												<s:text name="xblue" />
											</s:if>
											<s:if test="ocdFlagValue=='HSC'">
												<s:text name="xiiblue" />
											</s:if>
											<s:if test="ocdFlagValue=='DIPLOMA'">
												<s:text name="dipblue" />
											</s:if>
											<s:if test="ocdFlagValue=='DEGREE'">
												<s:text name="ugblue" />
											</s:if>
											<s:if test="ocdFlagValue=='PG'">
												<s:text name="pgblue" />
											</s:if>
											<s:if test="ocdFlagValue=='NATIVITY'">
												<s:text name="nativityblue" />
											</s:if>
											</span></td>
										<s:if test='%{documentFileName1 != null}'>
											<td width="50%">
												<!--                  <span class="check"><span class="glyphicon glyphicon-ok"></span>-->
												<div>
													<div
														style="width: 60%; display: inline-block; margin-left: 5px;"
														class="wordwrap">
														<strong><s:property value="documentFileName1" /></strong>
														&nbsp;&nbsp;&nbsp;
													</div>
													<div style="display: inline-block; float: right;">
														<strong><label><s:property
																	value="docVerify1" /></label></strong>
													</div>

												</div>
											</td>
										</s:if>
										<s:hidden id="imgDoc1"></s:hidden>
										<s:hidden id="documentFileName%{#stat.index}"
											value="%{documentFileName1}"></s:hidden>
										<s:hidden id="candidateDocPk%{#stat.index}"
											value="%{candidateDocPk1}"></s:hidden>
										<s:hidden id="ocdFlagValue%{#stat.index}"
											value="%{ocdFlagValue}"></s:hidden>

									</tr>
								</s:iterator>
							</table>
						</div>

						<div class="clear"></div>
						<!-- <h1 title="Declaration" class="pageTitle">Declaration</h1> -->
						<!-- <div class="accordion">
							<ul class="declaration">
								<li style="list-style-type: none;">
									<div style="display: inline-block; margin-right: 10px;">
										<input type="checkbox" name="declaration" id="checkbox1"
											checked disabled />
									</div>
									<div class="declare fl-rigt" style="width: 97%">
										<p class="font14">I declare that I have carefully read and
											fully understood the various instructions, eligibility
											criteria and other conditions and I hereby agree to abide by
											them.</p>
									</div>
								</li>
								<li style="list-style-type: none;">
									<div class="clear"></div>
									<div style="display: inline-block; margin-right: 10px;">
										<input type="checkbox" name="declaration" id="checkbox2"
											checked disabled />
									</div>
									<div class="declare fl-rigt" style="width: 97%">
										<p class="font14">I declare that all the entries made by
											me in this application form are true to the best of my
											knowledge and belief. If any particular furnished statements
											made by me in the application are found to be false at any
											stage, I am aware that I am liable to be disqualified.</p>
									</div>
								</li>
								<li style="list-style-type: none;">
									<div class="clear"></div>
									<div style="display: inline-block; margin-right: 10px;">
										<input type="checkbox" name="declaration" id="checkbox3"
											checked disabled />
									</div>
									<div class="declare fl-rigt" style="width: 97%">
										<p class="font14">I certify that the statements made by me
											in the application are complete and correct to the best of my
											knowledge and belief.</p>
									</div>
								</li>
								<li style="list-style-type: none;">
									<div class="clear"></div>
									<div style="display: inline-block; margin-right: 10px;">
										<input type="checkbox" name="declaration" id="checkbox4"
											checked disabled />
									</div>
									<div class="declare fl-rigt" style="width: 97%">
										<p class="font14">I further undertake that if any
											information given herein above is proved wrong then my
											services are liable to be terminated from the company, if
											appointed. I also certify that, no Criminal Proceedings are
											initiated / Pending against me and I have never been
											convicted by any Court of Law.</p>
									</div>
								</li>
								<li style="list-style-type: none;">
									<div class="clear"></div>
									<div style="display: inline-block; margin-right: 10px;">
										<input type="checkbox" name="declaration" id="checkbox5"
											checked disabled />
									</div>
									<div class="declare fl-rigt" style="width: 97%">
										<p class="font14">I understand that after executing "Final
											Submit and Confirm" Task, I will not be allowed to
											edit/update data in any task.</p>
									</div>
								</li>
								<li style="list-style-type: none;">
									<div class="clear"></div>
									<div style="display: inline-block; margin-right: 10px;">
										<input type="checkbox" name="declaration" id="checkbox6"
											checked disabled />
									</div>
									<div class="declare fl-rigt" style="width: 97%">
										<p class="font14">By Final Submitting and Confirming the
											Application, I agree that all information entered was done
											accurately & truthfully.</p>
									</div>
								</li>
							</ul>
						</div> -->
						<div class="clear"></div>
						<br> <br> <br>
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
							</div>
						</div>
						<div id="output"></div>
					</div>
				</div>
				<div id="output"></div>
				<div class="clear"></div>
		<div id="overlay" class="web_dialog_overlay_declr"></div>
		<div class="fullscreen-container" id="dialog">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" onclick="HideDialog()">&times;</button>
						<h2 class="modal-title">
							Disclaimer<%-- <span class="tamil"> <s:text
									name="header.Declaration" />
							</span> --%>
						</h2>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-lg-12">
								<p class="font14">
											I HEREBY DECLARE THAT ALL THE PARTICULARS FURNISHED BY ME IN
											THIS APPLICATION ARE TRUE, CORRECT AND COMPLETE TO THE BEST
											OF MY KNOWLEDGE AND BELIEF. I AM AWARE, IN THE EVENT OF ANY
											INFORMATION BEING DETECTED FALSE OR INCORRECT OR INCOMPLETE
											OR INELIGIBLE BEFORE OR AFTER THE EXAMINATION, MY CANDIDATURE
											WILL BE SUMMARILY REJECTED.
											<span class="tamil"><s:text
													name="preview.declaration_content" /></span></div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row">
							<div class="col-sm-3 col-sm-offset-3">
								<input type="button" id="btnSubmitId" name="btnSubmit"
									value="Submit" onclick="ShowDialog1()"
									class="ripple1 btn btn-warning btn-block mt10" />
							</div>
							<div class="col-sm-3">
								<input type="button" name="btnCancel" value="Cancel"
									onclick="HideDialog();"
									class="ripple1 btn btn-default btn-block mt10" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
				<div id="overlay" class="web_dialog_overlay_declr"></div>
				<div id="dialog" class="web_dialog_declr"
					style="overflow: _scroll; height: 293px;">
					<!-- <table style="width: 99%; margin:5px;"><tr><td> -->
					<table style="width: 100%; border: 0px;" cellpadding="3"
						cellspacing="0">
						<tr>
							<td class="web_dialog_title_declr" colspan="4">Declaration</td>

						</tr>
						<tr>
							<td colspan="4"><%=ConfigurationConstants.getInstance().getDeclarationVal(GenericConstants.DECLARATION)%>
							</td>

						</tr>
						<tr>
							<td align="center" colspan="4">
								<div
									style="display: inline-block; width: 20px; margin-top: 2px;">
									<input type="checkbox" name="declaration" id="declaration" />
								</div>
								<div style="display: inline-block; vertical-align: top;">I
									accept the above declaration</div>
							</td>
						</tr>
						<tr id="declar"
							style="color: red; display: none; text-align: center; padding-top: 10px;">
							<td colspan="4">Please confirm your Declaration</td>
						</tr>
						<tr>
							<td colspan="4"></td>
						</tr>
						<tr>
							<td colspan="4"></td>
						</tr>
						<tr>
							<td align="center" colspan="4"><input type="button"
								id="btnSubmitId" name="btnSubmit" value="Submit"
								onclick="ShowDialog1()" class="submitBtn button-gradient" /> <input
								type="button" name="btnCancel" value="Cancel"
								onclick="HideDialog();" class="submitBtn button-gradient" /></td>

						</tr>
					</table>
				</div>
				<s:token />
			</s:form>
		</div>
	</div>
</div>
<div class="countinuebg">
	<div class="container text-right">
		<input type="button" value="Submit Application Form"
			class="submitBtn button-gradient preview submitbtn"
			onclick="ShowDialog(true)" />
	</div>
</div>