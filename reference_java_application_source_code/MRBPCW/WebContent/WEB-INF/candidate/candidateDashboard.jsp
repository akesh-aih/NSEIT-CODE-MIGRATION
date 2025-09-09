<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<link rel="stylesheet" href="css/font-awesome.min.css">
<script type="text/javascript">
document.onreadystatechange = function () {
	  var state = document.readyState;
		  enableLoadingAnimation();
	  if (state == 'complete') {
		  disabledLoadingAnimation();
	  }
	} 

	$(document).ready(function() {
		var post1 = '<s:property value="%{post1}"/>';
		var post2 = '<s:property value="%{post2}"/>';
		var post3 = '<s:property value="%{personalDetailsBean.post1}"/>';
		var PopupMessage = $("#PopupMessage").val();
		if(PopupMessage !=null && PopupMessage=='success')
			{
			alert("E-Challan Payment details page form Submitted successfully");
			
			$("#PopupMessage").val("");
			}
		/* if(post1 != null){
			document.getElementById("post1").innerHTML = post1;
		} */
		
		$(".subNavBg").hide();
		var pdfCheck='<s:property value="%{candidateDocPk9}"/>';
		
		 if(pdfCheck=="true")
		 {
			 alert("Unable to download PDF. Please contact support.");
			 window.close();
		 }
	});
	function open_window_scoreCard(){
		$("#serverSideErrorMessage").hide();
		$("#dashboardForm").attr('action',"CandidateAction_downloadScoreCard.action");
		$("#dashboardForm").submit();
	}
	function open_window_admitCard()
	{
		$("#serverSideErrorMessage").hide();
		$("#dashboardForm").attr('action',"CandidateAction_downloadAdmitCard.action");
		$("#dashboardForm").submit();
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
		$("#dialog").fadeOut(300);
	}

	function open_win()
	{
	//window.open("ReportAction_downloadCallLetterMedicalTest.action")
		window.open("CandidateAction_printHallTicket.action")
	}

	/* function open_window(id,post)
	{
		var x=id;
		window.open("CandidateAction_getApplicationPDF.action?postFk="+x+"&postName="+post);


	} */
	function open_window()
	{
		enableLoadingAnimation();
		document.applicationPdf.action = "CandidateAction_getApplicationPDF.action";
		document.applicationPdf.submit();
	}
	function open_win(id)
	{
	 var x=id;
		window.open("CandidateAction_printFinalPageInJasper1.action?disciplineType="+x+"&print=print");
	}
	function downloadReceipt(id)
	{
		var x=id;
		document.applicationPdf.action = "PaymentOnlineAction_generatePaymentReceipt.action?epost="+x;
		document.applicationPdf.submit();
		//window.open("PaymentOnlineAction_generatePaymentReceipt.action?epost="+x);
	}
	
	function downloadAdmitCardPDF()
	{
		window.open("CandidateAction_printAdmitCardPDF.action");
	}
	function downloadCallLetter()
	{
		window.open("CandidateAction_printCallLetterPDF.action");
	}
	function downloadScoreCardPDF()
	{
		window.open("CandidateAction_printScoreCardPDF.action");
	}
	function openPracticeTest()
	{
		window.load("CandidateAction_openPracticeTest.action");
		return true;
	}
	
</script>
<style>
a {
	cursor: pointer !important;
	color: #FF0000;
} /* CSS link color */
}
</style>
<s:form id="applicationPdf" autocomplete="off">
	<div class="clear"></div>
	<div class="container common_dashboard ViewpageLabel">
		<div class="accordions">
			<%--	<s:form id="applicationForm" action="CandidateAction">--%>
			<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
			<s:hidden name="PopupMessage" id="PopupMessage"
				value="%{PopupMessage}" />
			<div id="downLinksDiv" style="text-align: center;">
				<s:if test='%{admitCardExist == "true"}'>
					<a style="padding-left: 0px; color: blue; font-size: 16px;"
						id="admitCard" onclick="downloadAdmitCardPDF()"> <s:label
							id="admitCardId" value="%{admitCardLabel}"
							cssStyle="cursor:pointer" />
					</a>
					<br />
				</s:if>
			</div>
			<div id="downLinksDiv" style="text-align: center;">
				<s:if test='%{scoreCardExist == "true"}'>
					<a style="padding-left: 0px; color: blue; font-size: 16px;"
						id="scoreCard" onclick="downloadScoreCardPDF()"> <s:label
							id="scoreCardId" value="%{scoreCardLabel}"
							cssStyle="cursor:pointer" />
					</a>
					<br />
				</s:if>
			</div>
			<div id="downLinksDiv" style="text-align: center;">
				<s:if test='%{callLetterExist == "true"}'>
					<a style="padding-left: 0px; color: blue; font-size: 16px;"
						id="Callletter" onclick="downloadCallLetter()"> <s:label
							id="callId" value="%{callLetterLabel}" cssStyle="cursor:pointer" />
					</a>
					<br />
				</s:if>
			</div>
			<s:if test='%{stageUpdate=="10"}'>
				<div id="downLinksDiv" style="text-align: center;">
					<b> <a style="padding-left: 0px; color: blue; font-size: 16px;"
						id="practiceTest" target="_blank" onclick="openPracticeTest()"
						href=" <%=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PRACTISE_TEST_LINK)%>">
							<s:label id="practiceTest" value="%{practiceTestLabel}"
								cssStyle="cursor:pointer" />
					</a>
					</b><br />
				</div>
			</s:if>

			<div class="row">
				<div class="col-sm-4">
					<div class="form-group">
						<label class="control-label">Candidate Status </label>
						<s:label value="%{status}" cssClass="form-control"></s:label>
					</div>
				</div>

			</div>
			<div class="row">
				<div class="col-sm-12">
					<h1 class="pageTitle">Candidate Basic Details</h1>
				</div>
			</div>
			<div class="accordion">
				<div class="row">
					<s:if
						test="%{personalDetailsBean.candidateName != null && personalDetailsBean.candidateName != ''}">
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">Candidate Name (Full Name)
									<%-- <s:text name="candName"> </s:text>--%>
								</label><br />
								<%-- <s:label value="%{personalDetailsBean.candidatePrefix}"></s:label> --%>
								<s:label cssClass="form-control wordWrap"
									value="%{personalDetailsBean.candidateName}"></s:label>
							</div>
						</div>
					</s:if>
					<%-- <div class="col-sm-4">
					<label class="control-label">Registration Number</label>
					<s:if test='%{statusID=="10"}'>
						<br />
						<s:label cssClass="form-control" value="%{registrationId}" />
						<br />
					</s:if>
					<s:else>
						<div class="row">
							<div class="col-sm-8">
								<p class="orgNote">
									<strong>Note:</strong> Registration number will be displayed
									only after successful payment <span class="tamil"> <span
										class="manadetory-fields"></span>
									</span>
								</p>
							</div>
						</div>
					</s:else>
				</div> --%>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label">User ID <%-- <s:text name="applicationForm.userId" /> --%></label>
							<br />
							<s:label cssClass="form-control" value="%{userId}" />
							<br />
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<s:if
								test='%{personalDetailsBean.dateOfBirth !=null && personalDetailsBean.dateOfBirth !=""}'>
								<label class="control-label">Date of Birth<s:text
										name="applicationForm.dateOfBirth" />
								</label>
								<s:label value="%{personalDetailsBean.dateOfBirth}"
									id="dateOfBirth" cssClass="form-control" />
							</s:if>
						</div>
					</div>
				</div>

				<div class="row">
					<s:if test="%{genderValDesc != null && genderValDesc != ''}">
						<div class="col-sm-4">
							<div class="form-group">
								<label class="control-label">Gender <s:text
										name="applicationForm.gender" /></label>
								<s:label value="%{genderValDesc}" id="gender"
									cssClass="form-control" />
							</div>
						</div>
					</s:if>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label">Email ID <s:text
									name="applicationForm.emailId" /></label>
							<s:label value="%{personalDetailsBean.email}" id="email"
								cssClass="form-control wordWrap" />
						</div>
					</div>
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label">Mobile Number <s:text
									name="applicationForm.mobileNo" /></label>
							<s:label value="%{personalDetailsBean.mobileNo}" id="mobileNo"
								cssClass="form-control" />
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-sm-4">
						<div class="form-group">
							<label class="control-label">Post Applying for<%-- <s:text name="application.post" /> --%></label>
							<%-- <s:label value="%{personalDetailsBean.candidatePrefix}"></s:label> --%>
							<s:label cssClass="form-control wordWrap"
								value="Prosthetic Craftsman"></s:label>
						</div>
					</div>
				</div>
			</div>
			<s:iterator value="candidatePostList" status="stat"
				var="currentObject">

				<s:if test='%{opd_fk==5}'>
					<s:if test='%{stage_2=="10"}'>

						<a href="javascript:void(0)" id="Dashboard"
							onclick="menuLinks(&quot;CandidateAction_showEchallanPaymentDetailsPage.action&quot;)">Click
							here to enter your E-Payment details</a>

					</s:if>
				</s:if>
			</s:iterator>

			<!-- Box Container Start -->
			<div class="clear">&nbsp;</div>
			<div class="row">
				<div class="col-sm-12">
					<h1 class="pageTitle">
						Application Status
						<%--  <s:text name="candAppStatus"></s:text> --%>
					</h1>
				</div>
			</div>
			<!-- Box Container Start -->
			<%-- --<s:property value="stageUpdate"/>-- --%>

			<s:hidden name="stageUpdate" id="stageUpdateHid" />
			<s:hidden name="isexempted" id="isexemptedHid" />
			<div class="accordion">
				<div class="row">
					<div class="col-sm-12 table-responsive">
						<table border="0" cellpadding="0" cellspacing="0"
							class="personsl-dtl table table-bordered">
							<tr>
								<th>Payment Mode <%-- <s:text name="paymentmode" /> --%></th>

								<!-- <th>Post Applied</th> -->
								<th>Application Status <%-- <s:text name="candAppStatus"></s:text> --%></th>
								<%-- 	<s:if test='%{stageUpdate=="10"}'> --%>
								<th>Application Print <%-- <s:text name="candAppPrint"></s:text> --%></th>
								<%-- </s:if> --%>
								<%-- 	<s:if test='%{stageUpdate=="10"}'> --%>
								<th>Payment Receipt <%-- <s:text name="candAppReceipt"></s:text> --%></th>
								<%-- </s:if> --%>

							</tr>

							<s:iterator value="candidatePostList" status="stat"
								var="currentObject">
								<tr>
									<td><s:label value="%{opd_fk_desc}" label="opd_fk_desc"
											id="opd_fk_desc"></s:label></td>
									<%-- <td><s:label value="%{disciplineType}"
											label="disciplineType" id="disciplineType"></s:label></td> --%>
									<td><s:if test='%{opd_fk==1}'>
											<s:label value="%{status}" label="status" id="status"></s:label>
										</s:if> <s:if test='%{opd_fk==5}'>
											<!-- for offline e challan payment only -->
											<s:if test='%{stage_2=="11"}'>
												<!-- means candidate submitted form -->
												<s:if test='%{stageUpdate !="10"}'>
													<!--  but payment not approved -->
													<s:label value="Payment Approval Awaiting" label="status"
														id="status"></s:label>
												</s:if>
												<s:else>
													<s:label value="%{status}" label="status" id="status"></s:label>
												</s:else>
											</s:if>
											<s:else>
												<s:label value="%{status}" label="status" id="status"></s:label>
											</s:else>

										</s:if></td>
									<td><s:if test='%{opd_fk==1}'>
											<s:if test='%{stageUpdate=="10"}'>

												<a id="<s:property value='%{disciplineType}'/>"
													onclick="open_window()"> <s:label
														value="Application Form"
														cssStyle="cursor:pointer; color: blue; text-decoration: underline" />
												</a>
											</s:if>
										</s:if> <s:if test='%{opd_fk==5}'>
											<s:if test='%{stage_2=="11"}'>
												<a id="<s:property value='%{disciplineType}'/>"
													onclick="open_window()"> <s:label
														value="Application Form"
														cssStyle="cursor:pointer; color: blue; text-decoration: underline" />
												</a>
											</s:if>
										</s:if></td>
									<td><s:hidden name="opd_fk" /> <s:hidden
											name="stageUpdate" /> <s:if test='%{opd_fk==1}'>
											<s:if test='%{stageUpdate==10}'>
												<a id="<s:property value='%{opd_fk}'/>"
													onclick="downloadReceipt(this.id)"> <s:label
														value="Receipt"
														cssStyle="cursor:pointer; color: blue; text-decoration: underline" />
												</a>
											</s:if>
										</s:if> <s:if test='%{opd_fk==5}'>
											<s:if test='%{stage_1=="10"}'>
												<a id="<s:property value='%{opd_fk}'/>"
													onclick="downloadReceipt(this.id)"> <s:label
														value="Receipt"
														cssStyle="cursor:pointer; color: blue; text-decoration: underline" />
												</a>
											</s:if>
										</s:if></td>
								</tr>
							</s:iterator>
							<tr>
								<s:if test="%{candidatePostList.isEmpty()}">
									<td colspan="5" align="center"><div>
											<b> No Record Found </b>
										</div></td>
								</s:if>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</s:form>
<s:token />
