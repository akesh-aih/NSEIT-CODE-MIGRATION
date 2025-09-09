<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8" />
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link type="text/css"
	href="css/ui-lightness/jquery-ui-1.8.16.custom.css" rel="stylesheet" />
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/bootstrap-select.min.css">
<link rel="stylesheet" href="css/font-awesome.min.css">
<script src="js/jquery-3.6.3.min.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/candidateJS/education.js"></script>

<script type="text/javascript">
function errorField() {
	<s:iterator value="errorField">
	jQuery("select[id='<s:property />']").addClass('red-border');
	jQuery("input[id='<s:property />']").addClass('red-border');
	</s:iterator>
}

function datePicker() {
	var firstDate = '<s:property value="firstDate"/>';
	var hscDate = '<s:property value="hscDate"/>';
	var diplomaDate = '<s:property value="diplomaDate"/>';
	var graduationDate = '<s:property value="graduationDate"/>';
	var pgDate = '<s:property value="pgDate"/>';

	//ssc
	jQuery("#dateOfPassing0").datepicker({
		maxDate : new Date(),
		showOn : "button",
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		buttonImageOnly : true,
		buttonImage : "images/cale-img.gif",
		buttonText : "10th / SSLC Month & Year of Passing",
		dateFormat : 'MM yy',
		yearRange : firstDate + ':' + new Date().getFullYear(),
		beforeShow : function() {
			jQuery(this).datepicker("widget").addClass('hide-calendar');
			$(".ui-datepicker-month").hide();
			jQuery("#ui-datepicker-div").removeAttr("adop");
			if (jQuery(this).attr("id") == "dateOfPassing0") {
				var x = jQuery(this).attr("id")
				jQuery("#ui-datepicker-div").attr("dop", "dop0");
			}
			if (jQuery(this).attr("id") == "dateOfPassing1") {
				jQuery("#ui-datepicker-div").attr("dop", "dop1");
			}
			if (jQuery(this).attr("id") == "dateOfPassing2") {
				jQuery("#ui-datepicker-div").attr("dop", "dop2");
			}
			if (jQuery(this).attr("id") == "dateOfPassing3") {
				jQuery("#ui-datepicker-div").attr("dop", "dop3");
			}
			if (jQuery(this).attr("id") == "dateOfPassing4") {
				jQuery("#ui-datepicker-div").attr("dop", "dop4");
			}
			if (jQuery(this).attr("id") == "dateOfPassing5") {
				jQuery("#ui-datepicker-div").attr("dop", "dop5");
			}
		}
	}).focus(function() {
		var thisCalendar = $(this);
		jQuery('.ui-datepicker-calendar').detach();
		jQuery('.ui-datepicker-close').click(function() {
			var year = jQuery("#ui-datepicker-div .ui-datepicker-year :selected").val();
			var month = jQuery("#ui-datepicker-div .ui-datepicker-month :selected").val();
			thisCalendar.datepicker('setDate', new Date(year, month, 1));
			
		});
		$(this).datepicker("widget").removeClass('hide-calendar');
	});

	//hsc
	jQuery("#dateOfPassing1").datepicker({
		maxDate : new Date(),
		showOn : "button",
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		buttonImageOnly : true,
		buttonImage : "images/cale-img.gif",
		buttonText : "12th / HSC Month & Year of Passing",
		dateFormat : 'MM yy',
		//yearRange : '1988:2023',
		yearRange : hscDate + ':' + new Date().getFullYear(),
		beforeShow : function() {
			jQuery(this).datepicker("widget").addClass('hide-calendar');
			jQuery("#ui-datepicker-div").removeAttr("adop");
			if (jQuery(this).attr("id") == "dateOfPassing0") {
				var x = jQuery(this).attr("id")
				jQuery("#ui-datepicker-div").attr("dop", "dop0");
			}
			if (jQuery(this).attr("id") == "dateOfPassing1") {
				jQuery("#ui-datepicker-div").attr("dop", "dop1");
			}
			if (jQuery(this).attr("id") == "dateOfPassing2") {
				jQuery("#ui-datepicker-div").attr("dop", "dop2");
			}
			if (jQuery(this).attr("id") == "dateOfPassing3") {
				jQuery("#ui-datepicker-div").attr("dop", "dop3");
			}
			if (jQuery(this).attr("id") == "dateOfPassing4") {
				jQuery("#ui-datepicker-div").attr("dop", "dop4");
			}
			if (jQuery(this).attr("id") == "dateOfPassing5") {
				jQuery("#ui-datepicker-div").attr("dop", "dop5");
			}
		}
	}).focus(function() {
		var thisCalendar = $(this);
		jQuery('.ui-datepicker-calendar').detach();
		jQuery('.ui-datepicker-close').click(function() {
			var year = jQuery("#ui-datepicker-div .ui-datepicker-year :selected").val();
			var month = jQuery("#ui-datepicker-div .ui-datepicker-month :selected").val();
			thisCalendar.datepicker('setDate', new Date(year, month, 1));
		});
		$(this).datepicker("widget").removeClass('hide-calendar');
	});

	//dip
	jQuery("#dateOfPassing2").datepicker({
		maxDate : new Date(),
		showOn : "button",
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		buttonImageOnly : true,
		buttonImage : "images/cale-img.gif",
		buttonText : "Diploma Month & Year of Passing",
		dateFormat : 'MM yy',
		yearRange : diplomaDate + ':' + new Date().getFullYear(),
		/* yearRange : '1988:2023', */
		beforeShow : function() {
			jQuery(this).datepicker("widget").addClass('hide-calendar');
			jQuery("#ui-datepicker-div").removeAttr("adop");
			if (jQuery(this).attr("id") == "dateOfPassing0") {
				var x = jQuery(this).attr("id")
				jQuery("#ui-datepicker-div").attr("dop", "dop0");
			}
			if (jQuery(this).attr("id") == "dateOfPassing1") {
				jQuery("#ui-datepicker-div").attr("dop", "dop1");
			}
			if (jQuery(this).attr("id") == "dateOfPassing2") {
				jQuery("#ui-datepicker-div").attr("dop", "dop2");
			}
			if (jQuery(this).attr("id") == "dateOfPassing3") {
				jQuery("#ui-datepicker-div").attr("dop", "dop3");
			}
			if (jQuery(this).attr("id") == "dateOfPassing4") {
				jQuery("#ui-datepicker-div").attr("dop", "dop4");
			}
			if (jQuery(this).attr("id") == "dateOfPassing5") {
				jQuery("#ui-datepicker-div").attr("dop", "dop5");
			}
		}
	}).focus(function() {
		var thisCalendar = $(this);
		jQuery('.ui-datepicker-calendar').detach();
		jQuery('.ui-datepicker-close').click(function() {
			var year = jQuery("#ui-datepicker-div .ui-datepicker-year :selected").val();
			var month = jQuery("#ui-datepicker-div .ui-datepicker-month :selected").val();
			thisCalendar.datepicker('setDate', new Date(year, month, 1));
		});
	});
	
	$("#prdOfStudyFrm_2").datepicker({
    	maxDate:new Date(),
    	showOn: "button",
		changeYear: true,
		showButtonPanel: true,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'yy',
		yearRange: diplomaDate + ':'+ new Date().getFullYear(),
		beforeShow : function() {
			jQuery(this).datepicker("widget").addClass('hide-calendar');
			$(".ui-datepicker-month").hide();
			jQuery("#ui-datepicker-div").removeAttr("adop");
		}
	}).focus(function() {
		var thisCalendar = $(this);
		$(".ui-datepicker-month").hide();
		$('.ui-datepicker-calendar').detach();
		$('.ui-datepicker-close').click(function() {
			var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
			thisCalendar.datepicker('setDate', new Date(year, 1));
			var FrmYrId  = $(thisCalendar).attr('id').split("_")[1];
			calculateDuration(FrmYrId);
		});
	});
    
    $("#prdOfStudyTo_2").datepicker({
    	maxDate:new Date(),
    	showOn: "button",
		changeYear: true,
		showButtonPanel: true,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		buttonText:"",
		dateFormat: 'yy',
		yearRange: diplomaDate +':'+ new Date().getFullYear(),
	}).focus(function() {
		var thisCalendar = $(this);
		  $(".ui-datepicker-month").hide();
		$('.ui-datepicker-calendar').detach();
		$('.ui-datepicker-close').click(function() {
			var year = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
			thisCalendar.datepicker('setDate', new Date(year, 1));
			var ToYrId = $(thisCalendar).attr('id').split("_")[1];
			calculateDuration(ToYrId);
		});
	});

    //ug
	jQuery("#dateOfPassing3").datepicker({
		maxDate : new Date(),
		showOn : "button",
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		buttonImageOnly : true,
		buttonImage : "images/cale-img.gif",
		buttonText : "Under Graduate Month & Year of Passing",
		dateFormat : 'MM yy',
		//yearRange : '1988:2023',
		yearRange : graduationDate + ':' + new Date().getFullYear(),
		beforeShow : function() {
			jQuery(this).datepicker("widget").addClass('hide-calendar');
			jQuery("#ui-datepicker-div").removeAttr("adop");
			
		}
	}).focus(function() {
		var thisCalendar = $(this);
		jQuery('.ui-datepicker-calendar').detach();
		jQuery('.ui-datepicker-close').click(function() {
			var year = jQuery("#ui-datepicker-div .ui-datepicker-year :selected").val();
			var month = jQuery("#ui-datepicker-div .ui-datepicker-month :selected").val();
			thisCalendar.datepicker('setDate', new Date(year, month, 1));
		});
		$(this).datepicker("widget").removeClass('hide-calendar');
	});
	
    //pg
	jQuery("#dateOfPassing4").datepicker({
		maxDate : new Date(),
		showOn : "button",
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		buttonImageOnly : true,
		buttonImage : "images/cale-img.gif",
		buttonText : "Post Graduation Month & Year of Passing",
		dateFormat : 'MM yy',
		//yearRange : '1988:2023',
		yearRange : pgDate + ':' + new Date().getFullYear(),
		beforeShow : function() {
			jQuery(this).datepicker("widget").addClass('hide-calendar');
			jQuery("#ui-datepicker-div").removeAttr("adop");
			
		}
	}).focus(function() {
		var thisCalendar = $(this);
		jQuery('.ui-datepicker-calendar').detach();
		jQuery('.ui-datepicker-close').click(function() {
			var year = jQuery("#ui-datepicker-div .ui-datepicker-year :selected").val();
			var month = jQuery("#ui-datepicker-div .ui-datepicker-month :selected").val();
			thisCalendar.datepicker('setDate', new Date(year, month, 1));
		});
		$(this).datepicker("widget").removeClass('hide-calendar');
	});
	
	jQuery("#pgDipDateofpassing4").datepicker({
		maxDate : new Date(),
		showOn : "button",
		changeMonth : true,
		changeYear : true,
		showButtonPanel : true,
		buttonImageOnly : true,
		buttonImage : "images/cale-img.gif",
		buttonText : "PG Diploma Month & Year of Passing",
		dateFormat : 'MM yy',
		//yearRange : '1988:2023',
		yearRange : pgDate + ':' + new Date().getFullYear(),
		beforeShow : function() {
			jQuery(this).datepicker("widget").addClass('hide-calendar');
			jQuery("#ui-datepicker-div").removeAttr("adop");
			
		}
	}).focus(function() {
		var thisCalendar = $(this);
		jQuery('.ui-datepicker-calendar').detach();
		jQuery('.ui-datepicker-close').click(function() {
			var year = jQuery("#ui-datepicker-div .ui-datepicker-year :selected").val();
			var month = jQuery("#ui-datepicker-div .ui-datepicker-month :selected").val();
			thisCalendar.datepicker('setDate', new Date(year, month, 1));
		});
		$(this).datepicker("widget").removeClass('hide-calendar');
	});
}
	
$(document).ready(function(){
	$('.checkboxLabel').after('<div class="clear visible-xs"></div>')
})
</script>

<style>
.actionMessage li:first-child {
	list-style: none;
}

.actionMessage li {
	float: left;
	margin-left: -25px;
	width: 100%;
}

.actionMessage br {
	height: 1px;
	float: left;
}

.personsl-dtl {
	border-width: 0px;
	border: 0px;
}

.headcol {
	border-right: 0px;
}

.hide-calendar .ui-datepicker-calendar {
	display: none;
}

#ui-datepicker-div button.ui-datepicker-current {
	display: none;
}

#addtionalformDetails.ui-datepicker-trigger:first-child {
	margin-top: 5px !important;
}
</style>

<div class="titlebg container">
	<h1 class="pageTitle">
		<strong>Educational Qualification</strong> <span class="userid_txt">
			<s:if test="%{#session['SESSION_USER'] != null}">
				<strong>User ID </strong> -	<s:label
					value="%{#session['SESSION_USER'].username}" />
			</s:if>
		</span>
	</h1>
</div>

<div id="dashboard">
	<s:form action="CandidateAction_saveAcademicDetails" name="academicFrm"
		id="academicForm" autocomplete="off" method="post">
		<div class="padding_leftright">
			<div class="container common_dashboard tabDiv effect2">
				<div class="container">
					<s:if test='%{serverSideErrorMessage == "true"}'>
						<div
							style="border: #999 0px solid; padding: 3px; color: red; padding-bottom: 20px;"
							id="serverSideErrorMessage">
							<s:actionmessage escape="false" />
						</div>
					</s:if>
					<s:if test="%{#attr.dataNotFound!=''}">
						<div class="error-massage-text">
							<s:property value="#attr.dataNotFound" />
						</div>
					</s:if>
				</div>
				<div class="row">
					<div class="col-sm-12">
						<p class="orgNote">
							<strong> Note : </strong>
							<s:text name="applicationForm.note" />
							<span class="manadetory-fields">*</span>
							<s:text name="applicationForm.note1" />
						</p>
					</div>
				</div>

				<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<s:hidden name="durationOfYears" id="durationOfYears" />
				<s:hidden name="isDataFound" value='<s:property value="dataFound"/>' />
				<s:hidden name="pstmMandatory" id="pstmMandatory" />

				<div class="row">
					<div class="col-sm-12">
						<div id="error-massage3" style="display: none"
							class="error-massage">
							<div class="error-massage-text">
								<ul id="error-ul3"></ul>
							</div>
						</div>
					</div>
				</div>

				<div class="accordions">
					<div id="AcademicDiv">
						<div id="outerdiv">
							<s:iterator value="educationDtlsList" status="stat" var="currentObject">
								<div id="section<s:property value="%{#stat.index}"/>">
									<h3 title="<s:property value="examination"/>" class="h3_member">
										<s:hidden name="degreeSelected" value="56" label="degreeSelected" id="degreeSelectedUser%{#stat.index}" />
										<s:label cssClass="h3_%{#stat.index}" title="%{examination}" value="%{examination}"></s:label>
										<s:hidden name="educationDtlsList[%{#stat.index}].examination" value="%{examination}" label="examination" readonly="true"
											onblur="testFunc()"></s:hidden>
										<s:hidden name="educationDtlsList[%{#stat.index}].enabled" id="enabled%{#stat.index}" value="%{enabled}" label="enabled"
											readonly="true"></s:hidden>
										<s:hidden name="educationDtlsList[%{#stat.index}].resultChkBox" id="resultChkBox%{#stat.index}" value="%{resultChkBox}"
											label="resultChkBox"></s:hidden>
									</h3>

									<div class="accordion">
										<div class="row">
											<s:if test="%{#stat.index == 0 || #stat.index == 1 || #stat.index == 2}">
												<div class="col-sm-4">
													<div class="form-group">
														<s:if test="%{#stat.index == 0 || #stat.index == 1}">
															<label class="control-label">Name of Board <span
																class="manadetory-fields">*</span>
															</label>
														</s:if>
														<s:elseif test="%{#stat.index == 2}">
															<label class="control-label">Diploma Name <span
																class="manadetory-fields">*</span>
															</label>
														</s:elseif>

														<s:if test="%{#stat.index == 0}">
															<s:select list="sscUnivList"
																name="educationDtlsList[%{#stat.index}].university"
																onpaste="return false;" id="university%{#stat.index}"
																headerKey="" headerValue="Select Name of Board"
																class="form-control" value="%{university}"
																label="university" cssClass="form-control nonEditable"
																onchange="setDegreeNameOtherBox(%{#stat.index});">
															</s:select>
														</s:if>

														<s:elseif test="%{#stat.index == 1}">
															<s:select list="hscUnivList"
																name="educationDtlsList[%{#stat.index}].university"
																onpaste="return false;" id="university%{#stat.index}"
																headerKey="" headerValue="Select Name of Board"
																class="form-control" value="%{university}"
																label="university" cssClass="form-control nonEditable"
																onchange="setDegreeNameOtherBox(%{#stat.index});">
															</s:select>
														</s:elseif>

														<s:elseif test="%{#stat.index == 2}">
															<s:select list="UGUnivList"
																name="educationDtlsList[%{#stat.index}].university"
																onpaste="return false;" id="university%{#stat.index}"
																headerKey="" headerValue="Select Diploma Name"
																class="form-control" value="%{university}"
																label="university" cssClass="form-control nonEditable"
																onchange="setDegreeNameOtherBox(%{#stat.index});">
															</s:select>
														</s:elseif>
													</div>
												</div>

												<div class="col-sm-4"
													id="universityOther<s:property value="%{#stat.index}"/>">
													<div class="form-group">
														<s:if test="%{#stat.index == 2}">
															<label class="control-label">Other Equivalent
																Diploma </label>
														</s:if>
														<s:else>
															<label class="control-label">Other Board </label>
														</s:else>
														<span class="manadetory-fields">*</span>
														<s:textfield
															name="educationDtlsList[%{#stat.index}].universityOth"
															onpaste="return false;" id="universityOth%{#stat.index}"
															value="%{universityOth}" class="form-control"
															onkeypress="return alphanumericWithSpaceDotComma(event);"
															label="universityOth" cssClass="form-control nonEditable"
															ondrop="return false;" maxlength="100">
														</s:textfield>
													</div>
												</div>
											</s:if>

											<s:if test="%{#stat.index == 2}">
												<div class="clear"></div>
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Period of Study From 
															<span class="manadetory-fields">*</span>
														</label>

														<div class="dateInput" id="prdOfStudyFrmTd%{#stat.index}">
															<s:textfield id="prdOfStudyFrm_%{#stat.index}"
																name="educationDtlsList[%{#stat.index}].prdOfStudyFrm"
																readonly="true" cssClass="form-control"
																value="%{prdOfStudyFrm}" ondragstart="return false;"
																ondrop="return false;">
															</s:textfield>
														</div>
													</div>
												</div>

												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Period of Study To <span
															class="manadetory-fields">*</span>
														</label>
														<div class="dateInput" id="prdOfStudyToTd%{#stat.index}">
															<s:textfield id="prdOfStudyTo_%{#stat.index}"
																name="educationDtlsList[%{#stat.index}].prdOfStudyTo"
																readonly="true" cssClass="form-control"
																value="%{prdOfStudyTo}" ondragstart="return false;"
																ondrop="return false;">
															</s:textfield>
														</div>
													</div>
												</div>
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Duration of Study
															(Number of Years) <span class="manadetory-fields">*</span>
														</label>
														<s:textfield
															name="educationDtlsList[%{#stat.index}].duration"
															id="duration%{#stat.index}" onpaste="return false;"
															class="form-control" title="%{duration}"
															value="%{duration}" label="duration" maxlength="2"
															cssClass="form-control" readonly="true"
															ondragstart="return false;" ondrop="return false;"></s:textfield>
													</div>
												</div>

												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Name of Institution <span
															class="manadetory-fields">*</span>
														</label>
														<s:textfield
															name="educationDtlsList[%{#stat.index}].institution"
															onpaste="return false;" id="institution%{#stat.index}"
															value="%{institution}"
															onkeypress="return alphanumericWithSpaceDotComma(event);"
															label="institution" cssClass="form-control"
															ondrop="return false;" maxlength="100">
														</s:textfield>
													</div>
												</div>
											</s:if>

											<s:if
												test="%{#stat.index == 0 || #stat.index == 1 || #stat.index == 2}">
												<div class="col-sm-4"
													id="dateofpassing<s:property value="%{#stat.index}"/>">
													<div class="form-group dateInput">
														<label class="control-label">Month & Year of
															Passing <span class="manadetory-fields">*</span>
														</label>
														<s:textfield id="dateOfPassing%{#stat.index}"
															onkeypress="return dateFormatCheck(event);"
															name="educationDtlsList[%{#stat.index}].dateOfPassing"
															readonly="true" cssClass="form-control nonEditable">
														</s:textfield>
													</div>
												</div>
											</s:if>

											<%-- <s:if test="%{#stat.index == 0 || #stat.index == 1}">
												<div class="clear"></div>
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Percentage of Marks <span
															class="manadetory-fields">*</span>
														</label>
														<s:textfield
															name="educationDtlsList[%{#stat.index}].percentage"
															value="%{percentage}" maxlength="5" label="percentage"
															onkeypress="return numericdot(event);"
															cssClass="form-control" id="percentage%{#stat.index}"
															ondragstart="return false;" ondrop="return false;"></s:textfield>
													</div>
												</div>
											</s:if> --%>
											<s:if test="%{#stat.index == 0 || #stat.index == 1 || #stat.index == 2}">
												<s:if test="%{#stat.index == 2}">
													<div class="col-sm-4">
														<div class="form-group">
															<label class="control-label">Do you have marks for
																the Diploma Course? <span class="manadetory-fields">*</span>
															</label>
															<s:select list="yesNo"
																name="educationDtlsList[%{#stat.index}].dipMarksYesNo"
																onpaste="return false;" id="dipMarksYesNo%{#stat.index}"
																headerKey="" headerValue="Select Do you have marks for the Diploma Course?"
																value="%{dipMarksYesNo}" label="dipMarksYesNo"
																cssClass="form-control nonEditable"
																onchange="hideShowFieldsDipMarks(%{#stat.index});">
															</s:select>
														</div>
													</div>
												</s:if>
												<div class="col-sm-4" id="totalMarksDiv<s:property value="%{#stat.index}"/>">
													<div class="form-group">
														<label class="control-label">Total Maximum Marks <span
															class="manadetory-fields">*</span>
														</label>
														<s:textfield
															name="educationDtlsList[%{#stat.index}].totalMarks"
															value="%{totalMarks}" maxlength="10" label="total"
															onkeypress="return numericdot(event);"
															onblur="calcPerc(obtainedMarks%{#stat.index},totalMarks%{#stat.index},percentage%{#stat.index});"
															cssClass="form-control" id="totalMarks%{#stat.index}"
															ondragstart="return false;" ondrop="return false;"></s:textfield>
													</div>
												</div>
												<div class="col-sm-4" id="obtainedMarksDiv<s:property value="%{#stat.index}"/>">
													<div class="form-group">
														<label class="control-label">Total Obtained Marks <span
															class="manadetory-fields">*</span>
														</label>
														<s:textfield
															name="educationDtlsList[%{#stat.index}].obtainedMarks"
															value="%{obtainedMarks}" maxlength="10" label="obtained"
															onkeypress="return numericdot(event);"
															onblur="calcPerc(obtainedMarks%{#stat.index},totalMarks%{#stat.index},percentage%{#stat.index});"
															cssClass="form-control" id="obtainedMarks%{#stat.index}"
															ondragstart="return false;" ondrop="return false;"></s:textfield>
													</div>
												</div>
												<div class="col-sm-4" id="percMarksDiv<s:property value="%{#stat.index}"/>">
													<div class="form-group">
														<label class="control-label">Percentage of Marks <span
															class="manadetory-fields">*</span>
														</label>
														<s:textfield
															name="educationDtlsList[%{#stat.index}].percentage"
															value="%{percentage}" maxlength="5" label="percentage"
															onkeypress="return numericdot(event);"
															cssClass="form-control"
															id="percentage%{#stat.index}" ondragstart="return false;"
															ondrop="return false;" readonly="true"></s:textfield>
													</div>
												</div>
											</s:if>
											<s:if
												test="%{#stat.index == 0 || #stat.index == 1 || #stat.index == 2}">
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Medium of Instruction 
															<span class="manadetory-fields">*</span>
														</label>

														<s:select list="medOfInstructionList"
															name="educationDtlsList[%{#stat.index}].medOfInstruction"
															onpaste="return false;"
															id="medOfInstruction%{#stat.index}" headerKey=""
															headerValue="Select Medium of Instruction"
															value="%{medOfInstruction}" label="medOfInstruction"
															cssClass="form-control nonEditable"
															onchange="hideShowFieldsPSTM(%{#stat.index});">
														</s:select>
													</div>
												</div>

												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Have you studied
															Tamil as one of the language (Part-1) <span
															class="manadetory-fields">*</span>
														</label>
														<s:select list="yesNo"
															name="educationDtlsList[%{#stat.index}].tamilLang"
															onpaste="return false;" id="tamilLang%{#stat.index}"
															headerKey=""
															headerValue="Select Have you studied Tamil as one of the language(Part-1)"
															value="%{tamilLang}" label="tamilLang"
															cssClass="form-control nonEditable"
															onchange="setDegreeNameOtherBox(%{#stat.index});">
														</s:select>

													</div>
												</div>
											</s:if>

											<s:if test="%{#stat.index == 3}">
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Do you have UG
															degree? <span class="manadetory-fields">*</span>
														</label>
														<s:select list="yesNo"
															name="educationDtlsList[%{#stat.index}].ugYesNo"
															onpaste="return false;" id="ugYesNo%{#stat.index}"
															headerKey="" headerValue="Select Do you have UG degree?"
															value="%{ugYesNo}" label="ugYesNo"
															cssClass="form-control nonEditable"
															onchange="hideShowFieldsUG(%{#stat.index});">
														</s:select>

													</div>
												</div>

												<div class="col-sm-4"
													id="ugSpecialization<s:property value="%{#stat.index}"/>">
													<div class="form-group">
														<label class="control-label">Specialization <span
															class="manadetory-fields">*</span>
														</label>
														<s:textfield
															name="educationDtlsList[%{#stat.index}].specialization"
															onpaste="return false;" id="specialization%{#stat.index}"
															value="%{specialization}"
															onkeypress="return alphanumericWithSpaceDotComma(event);"
															label="specialization" cssClass="form-control"
															ondrop="return false;" maxlength="100">
														</s:textfield>
													</div>
												</div>

												<div class="col-sm-4"
													id="dateofpassing<s:property value="%{#stat.index}"/>">
													<div class="form-group dateInput">
														<label class="control-label">Month & Year of
															Passing <span class="manadetory-fields">*</span>
														</label>
														<s:textfield id="dateOfPassing%{#stat.index}"
															onkeypress="return dateFormatCheck(event);"
															name="educationDtlsList[%{#stat.index}].dateOfPassing"
															readonly="true" cssClass="form-control nonEditable">
														</s:textfield>
													</div>
												</div>
											</s:if>

											<s:if test="%{#stat.index == 4}">
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Do you have PG
															degree? <span class="manadetory-fields">*</span>
														</label>
														<s:select list="yesNo"
															name="educationDtlsList[%{#stat.index}].pgYesNo"
															onpaste="return false;" id="pgYesNo%{#stat.index}"
															headerKey="" headerValue="Select Do you have PG degree?"
															value="%{pgYesNo}" label="pgYesNo"
															cssClass="form-control nonEditable"
															onchange="hideShowFieldsPG(%{#stat.index});">
														</s:select>

													</div>
												</div>

												<div class="col-sm-4"
													id="pgSpecialization<s:property value="%{#stat.index}"/>">
													<div class="form-group">
														<label class="control-label">Specialization <span
															class="manadetory-fields">*</span>
														</label>
														<s:textfield
															name="educationDtlsList[%{#stat.index}].specialization"
															onpaste="return false;" id="specialization%{#stat.index}"
															value="%{specialization}"
															onkeypress="return alphanumericWithSpaceDotComma(event);"
															label="specialization" cssClass="form-control"
															ondrop="return false;" maxlength="100">
														</s:textfield>
													</div>
												</div>

												<div class="col-sm-4"
													id="dateofpassing<s:property value="%{#stat.index}"/>">
													<div class="form-group dateInput">
														<label class="control-label">Month & Year of
															Passing <span class="manadetory-fields">*</span>
														</label>
														<s:textfield id="dateOfPassing%{#stat.index}"
															onkeypress="return dateFormatCheck(event);"
															name="educationDtlsList[%{#stat.index}].dateOfPassing"
															readonly="true" cssClass="form-control nonEditable">
														</s:textfield>
													</div>
												</div>
												<div class="clear"></div>
												<div class="col-sm-4">
													<div class="form-group">
														<label class="control-label">Do you have PG
															Diploma? <span class="manadetory-fields">*</span>
														</label>
														<s:select list="yesNo"
															name="educationDtlsList[%{#stat.index}].pgDipYesNo"
															onpaste="return false;" id="pgDipYesNo%{#stat.index}"
															headerKey="" headerValue="Select Do you have PG Diploma"
															value="%{pgDipYesNo}" label="pgDipYesNo"
															cssClass="form-control nonEditable"
															onchange="hideShowFieldsPgDip(%{#stat.index});">
														</s:select>
													</div>
												</div>

												<div class="col-sm-4"
													id="pgDipSpecializ<s:property value="%{#stat.index}"/>">
													<div class="form-group">
														<label class="control-label">Specialization <span
															class="manadetory-fields">*</span>
														</label>
														<s:textfield
															name="educationDtlsList[%{#stat.index}].pgDipSpecialization"
															onpaste="return false;"
															id="pgDipSpecialization%{#stat.index}"
															value="%{pgDipSpecialization}"
															onkeypress="return alphanumericWithSpaceDotComma(event);"
															label="pgDipSpecialization" cssClass="form-control"
															ondrop="return false;" maxlength="100">
														</s:textfield>
													</div>
												</div>

												<div class="col-sm-4"
													id="pgDipDateofPass<s:property value="%{#stat.index}"/>">
													<div class="form-group dateInput">
														<label class="control-label">Month & Year of
															Passing <span class="manadetory-fields">*</span>
														</label>
														<s:textfield id="pgDipDateofpassing%{#stat.index}"
															onkeypress="return dateFormatCheck(event);"
															name="educationDtlsList[%{#stat.index}].pgDipDateofpassing"
															readonly="true" cssClass="form-control nonEditable">
														</s:textfield>
													</div>
												</div>
											</s:if>

											<s:if test="%{#stat.index != 5}">
												<div class="clear"></div>
												<div class="row">
													<div class="col-lg-2 fl-rigt">
														<input type="button" value="Reset Fields"
															onclick="clearFields(<s:property value = '%{#stat.index}'/>);"
															class="btn-fereset" />
													</div>
												</div>
											</s:if>

											<s:if test="%{#stat.index == 5}">
												<div class="col-sm-4" id="noTamilPstm" style="display: none">
													<div class="form-group">
														<label class="control-label"> Are you eligible to
															avail PSTM preference? <span class="manadetory-fields">*</span>
														</label>
														<s:textfield name="pstmPreferenceVal" value="No"
															cssClass="form-control" readonly="true" />
														<span class="orgNote"> Note: You are not eligible
															to claim PSTM Preference, because your medium of
															Instruction is other than Tamil.</span>
													</div>
												</div>
												<div class="col-sm-4">
													<div class="form-group" id="pstmPref">
														<label class="control-label">Are you eligible to
															avail PSTM preference? <span class="manadetory-fields">*</span>
														</label>
														<s:select list="yesNo"
															name="educationDtlsList[%{#stat.index}].pstmPreference"
															id="pstmPreference" value="%{pstmPreference}"
															cssClass="form-control nonEditable"
															headerValue="Select Are you eligible to avail PSTM preference?"
															headerKey="" label="pstmPreference"
															onchange="hideShowFieldsPSTMsub(%{#stat.index});"></s:select>
													</div>
												</div>
												<div class="clear"></div>
												<div class="col-sm-4">
													<div class="form-group" id="tamilMed">
														<label class="control-label">Have you studied in
															Tamil medium from 1st standard to 12th standard? <span
															class="manadetory-fields">*</span>
														</label>
														<s:select list="yesNo"
															name="educationDtlsList[%{#stat.index}].tamilMedium"
															id="tamilMedium" value="%{tamilMedium}"
															cssClass="form-control nonEditable"
															headerValue="Select Have you studied in Tamil medium from 1st standard to 12th standard?"
															headerKey=""></s:select>
													</div>
												</div>

												<div class="col-sm-4">
													<div class="form-group" id="uGtamilMedium">
														<label class="control-label mb20">Have you studied
															your Diploma in Tamil medium? <span
															class="manadetory-fields">*</span>
														</label>
														<s:select list="yesNo"
															name="educationDtlsList[%{#stat.index}].ugTamilMedium"
															id="ugTamilMedium" value="%{ugTamilMedium}"
															cssClass="form-control nonEditable"
															headerValue="Select Have you studied your Diploma in Tamil medium?"
															headerKey=""></s:select>
													</div>
												</div>

												<div id="pstmClearBtn">
													<div class="clear"></div>
													<div class="row">
														<div class="col-sm-2 fl-rigt">
															<input type="button" value="Reset Fields"
																onclick="clearFields(5);" class="btn-fereset" />
														</div>
													</div>
												</div>
											</s:if>
										</div>
									</div>
								</div>
							</s:iterator>

						</div>
						<div id="overlay" class="web_dialog_overlay_declr"></div>
						<div class="fullscreen-container" id="dialog">
							<div class="modal-dialog">
								<div class="modal-content">
									<div class="modal-header">
										<h2 class="modal-title"></h2>
									</div>
									<div class="modal-body">
										<div class="row">
											<div class="col-lg-12 text-center">
												<h4 class="form-control"
													title="You are not eligible to apply for this course">You
													are not eligible to apply for this course</h4>
											</div>
										</div>
									</div>
									<div class="modal-footer">
										<div class="row">
											<div class="col-sm-6 col-sm-offset-3">
												<input type="button" name="btnOk" value="Ok"
													onClick="HideDialog();"
													class="ripple1 btn btn-warning btn-block" />
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="text-right" style="padding: 10px;" id="saveBtn"></div>
			<s:token />
			<s:hidden name="disciplineType" value="%{disciplineType}"></s:hidden>
		</div>
		<div class="countinuebg">
			<div class="container">
				<div class="row" id="continueTR">
					<div
						class="col-sm-2 col-sm-offset-10 col-xs-6 col-xs-offset-3 text-right padding0">
						<s:submit value="Save & Continue" id="savearros"
							cssClass="submitBtn btn btn-warning btn-block"
							onClick="validateAcademicDetails();"></s:submit>
					</div>
				</div>
			</div>
		</div>
	</s:form>
</div>
</html>
<div class="clear"></div>
<div id="overlay1" class="web_dialog_overlay_declr"></div>
<div class="fullscreen-container" id="dialog1">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" onclick="HideDialog1()">&times;</button>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12 text-center">
						<h4 class="pageTitle" id="dynamicContent"></h4>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">
						<input type="button" name="btnCancel" value="OK"
							onclick="HideDialog1()"
							class="ripple1 btn btn-default btn-block mt10" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>