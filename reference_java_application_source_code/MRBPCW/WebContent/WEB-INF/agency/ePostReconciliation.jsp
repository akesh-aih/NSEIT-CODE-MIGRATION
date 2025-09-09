<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">
<script src="js/jquery-3.5.1.min.js"></script>
<SCRIPT type="text/javascript" src="js/placeholders.jquery.js"></SCRIPT>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">

	function hideGuideLines(){
	HideAll();
		$("#guidelines").hide();
		$("#pop3").hide();
		
	}
	function frm_onsubmit(){
		var uploadType = $("#uploadType").val();
		var uploadFile = $("#uploadFile").val();
		var message = "";

		if(uploadFile == ""){
			message = message + "Please select a File to Upload."+"##";
		}else{
			var extension = uploadFile.substr( (uploadFile.lastIndexOf('.') +1) );
			extension = extension.toLowerCase();
			if(extension!="csv"){
				message = message + "Please select a CSV file for upload."+"##";
			}
		}
		
		if(message != ""){
			var ulID = "error-ul1";
			var divID = "error-massage1";
			createErrorList(message, ulID, divID);
			return false;
		}else{
			return true;
		}
	}
	function submitFile()
	{
			$("#challanReconcile").attr('action',"CandidateMgmtAction_uploadEPostFile.action");
			$("#challanReconcile").submit();
	}
	function myFunction() 
  {
	  var input, filter, table, tr, td, i;
	  input = document.getElementById("myInput");
	  filter = input.value.toUpperCase();
	  table = document.getElementById("myTable");
	  tr = table.getElementsByTagName("tr");
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[2];
	    if (td) {
	      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
	    }       
	  }
	}
	function showDiv(){
		$("#unscrecDataDiv").show();
		$("#unscrecDataTable").show();
		$("#showHideData").html("<a href='#' onclick='hideDiv()'>Hide Details</a>");
	}
	function hideDiv(){
		$("#unscrecDataDiv").hide();
		$("#unscrecDataTable").hide();
		$("#showHideData").html("<a href='#' onclick='showDiv()'>Show Details</a>");
	}
	function genrateExcelReport(){

	$("#challanReconcile").attr('action',"ReportAction_exportReconileToExcel.action");
	$("#challanReconcile").submit();
	//window.open('data:application/vnd.ms-excel,' + $('#unscrecDataDiv').html());
}
</script> 
  <style>
  .container .container { width:100%; float:left; }

.onetime{
  	display: none;
  }

  </style>
  
<div class="container common_dashboard" >
<s:form  enctype="multipart/form-data" method="post" id="challanReconcile">
<div class="fade" id="pop3"></div>
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<div id="dashboard" style="display:block;  height:auto; padding:0 !important;" >
<h1 class="pageTitle" title="e-Post Reconciliation"><s:text name="reconciliation.ePost"/></h1>
<div class="hr-underline2"></div>
<s:if test="hasActionMessages()">
   <div id="error-massage1">
	<div class="error-massage-text">
		<ul style="margin:0; margin-left:-20px; padding:0;" id="error-ul1">
			<s:actionmessage cssStyle="color:red" escape="false"/>	
		</ul>
	</div>
	</div>
</s:if>
<br/>
<!-- Box Container Start -->
<div style="display:block;   height:auto;" >
	<table class="contenttable">
		<tr>
		<td><s:text name="reconciliation.ePostStatement"/></td>
		<td>
			<s:file  name="fileUpload" id = "uploadFile" cssStyle="display:inline-block;"></s:file>
			<input type="button" onclick="submitFile();" value="Reconcile" class="submitBtn button-gradient"/>
		</td>
		<td>
			<a href="TemplateDownloadAction_downloadTemplate.action?templateDownloadType=TEMPLATE_EPOST_RECONCILIATION" class="LinkAlignMiddle"><s:text name="reconciliation.downloadEPostFormat"/></a>
		</td>
		<td>
<!--			<a href="#" onclick="ShowBlock('guidelines')">Guidelines For Challan Reconciliation</a>-->
		</td>
		</tr>
		</table>
</div>
</div>
<s:token></s:token>
</s:form>
		 <div class="dashboard">
	<s:if test='%{flag == "true"}'>
		<h2>Reconciliation Report</h2>
		<s:form method="post" id="excelForm" action="CandidateMgmtAction_getReconciliationDetails.action" name="paginationForm">
			 <table width="100%">
				<tr class="box-header">
					<td align="left" valign="middle" width="30%" ><strong style="width:70px; padding-top:5px; display: inline-block; font-weight:normal;">Search : </strong><input  type="text" id="myInput" style="width:200px;" onkeyup="myFunction()" placeholder="Search By Application Number" ></input></td>
					<td align="center" width="30%" class="pagination-label">Total <font color="red"><s:property value="unSuccessfulReconcileCount"/></font> Record Found </td>
					<td width="40%">
						<s:if test='%{unSuccessfulReconcileCount=="0"}'>
						</s:if>
						<s:else><%@ include file="../pagination.jsp" %></s:else>
					</td>
				</tr>
			</table>
			<table  width="100%" >
				<tr>
					<td colspan="4">
						<div id="unscrecDataDiv" >
						 
						<div style="overflow-y:scroll; height:300px;  ">
						<section>
  <div class="contentscroll">
							<table id="myTable" cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC" >
								<thead>
								    <tr>
								    <th style="width: 5%">Sr. No.</th>
								    <th style="width: 15%; text-align:left;">Candidate Name</th>
								    <th style="width: 15%; text-align:left;">Application Number</th>
								    <th style="width: 15%; text-align:left;">Transaction Serial</th>
								    <th style="width: 50%; text-align:left;">Reconcilation Status Description</th>
								</thead>
								<tbody>
									<s:iterator value="unSuccessfulReconcileListEPost" status="stat" var="currentObject">
										<tr>
											<td align="center" style="width: 5%;">
												<s:property value="oum_user_pk"/>
											</td>
											<td  style="width: 15%;">
												<s:property value="rec_cand_name"/>
											</td>
											<td  style="width: 15%;">
												<s:property value="ocjm_applicationnumber"/>
											</td>
											<td  style="width: 15%;">
												<s:property value="rec_trans_ser"/>
											</td>
											<td  style="width: 50%;">
												<s:property value="rec_reason"/>
											</td>
										</tr>
									 </s:iterator>
								</tbody>
							</table>
						</div>
						</section>
							
							</div>
							<table>
								<tr>
									<td colspan="4" align="center">
										<div>
											<input type="button" value="Generate EXCEL Report" title="Generate EXCEL Report" Class="submitBtn button-gradient" onclick="genrateExcelReport();"/>
										</div>
									</td>
								</tr>
							</table>
						</div>
					</td>
				 </tr>
			</table>
			<s:token/>
		</s:form>
	</s:if>
</div>
</div>

