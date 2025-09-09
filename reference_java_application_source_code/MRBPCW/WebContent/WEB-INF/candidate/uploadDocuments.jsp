<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<link rel="stylesheet" href="css/font-awesome.min.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/common.css">
<script type="text/javascript" src="js/q.js"></script>
<script type="text/javascript" src="js/spark-md5.min.js"></script>

<script>
	document.onreadystatechange = function () {
		var state = document.readyState;
		enableLoadingAnimation();
		if (state == 'complete') {
			disabledLoadingAnimation();
		}
	}

	var hashVal = null;
	var met=null;
	
	function calculateMD5Hash(file, bufferSize,method) {
		
		var def = Q.defer();
		var fileReader = new FileReader();
		var fileSlicer = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice;
		var hashAlgorithm = null;
		var totalParts = Math.ceil(file.size / bufferSize);
		var currentPart = 0;
		var startTime = new Date().getTime();
	
		fileReader.onload = function(e) {
  			var data=null;
		  	if (!e) {
		        data = fileReader.content;
		    
		  	} else {
		        data = e.target.result;
		    }
		    currentPart += 1;
		
		    def.notify({
		      	currentPart: currentPart,
		    	totalParts: totalParts
		    });
		    
		    hashAlgorithm = new SparkMD5();
		    hashAlgorithm.appendBinary(data);
		
		    if (currentPart < totalParts) {
		    	processNextPart();
		      	return;
		    }
		    
		    hashVal = hashAlgorithm.end();
		    met=method;
		    def.resolve({
		    	hashResult: "null",
		    	duration: new Date().getTime() - startTime
		    });
		};

		fileReader.onerror = function(e) {
			def.reject(e);
		};

		function processNextPart() {
			var start = currentPart * bufferSize;
			var end = Math.min(start + bufferSize, file.size);
			fileReader.readAsBinaryString(fileSlicer.call(file, start, end));
		}
		
		processNextPart();
		return def.promise;
	}

	function calculate(input,method) {
		if (!input.files.length) {
	    	return;
	  	}

	  	var file =input.files[0];
	  	var bufferSize = Math.pow(1024, 2) * 10; // 10MB

	  	calculateMD5Hash(file, bufferSize,method).then(function(result) {
		    	// Success
	    }, function(err) {
	      // There was an error,
	    }, function(progress) {
	      // We get notified of the progress as it is executed
	    	if (met=="uploadDocument1") {
				document.getElementById("data1Hash").value=hashVal;
		  	}
	      	document.uploadDocs.action="CandidateAction_insertCandidateDocuments.action";
	      	enableLoadingAnimation();
		  	document.uploadDocs.submit();
		});
	}
		
	if (!FileReader.prototype.readAsBinaryString) {
    	FileReader.prototype.readAsBinaryString = function (fileData) {
       		var binary = "";
	    	var pt = this;
	       	var reader = new FileReader();      
	       	reader.onload = function (e) {
	        	var bytes = new Uint8Array(reader.result);
	           	var length = bytes.byteLength;
	           	for (var i = 0; i < length; i++) {
	            	binary += String.fromCharCode(bytes[i]);
	           	}
	        	//pt.result  - readonly so assign binary
	        	pt.content = binary;
	        	pt.onload(); 
    		}
    
	       	reader.readAsArrayBuffer(fileData);
    	}
	}

	function uploadDocument1() {
		var attachmentDoc = $("#eligibilityCriteriaUploadFile1").val();
		var checkl=$("#checkl").prop('checked');//?true:false;
		
		if(attachmentDoc == "") {
			var message = "Please select file to upload "+'<s:property value="docLabel1"/>';
			
			var ulID = "errorMessages";
			var divID = "error-massageTD";
			createErrorList(message, ulID, divID); 
			$('html, body').animate({ scrollTop: 0 }, 0);
			$("#actionMsgDoc").hide();
			$("#error-ul3").hide();
			$("#error-massageTD").show();
			return false;
		}
	
		if(checkl==false) {
			message = "Please select checkbox to upload "+'<s:property value="docLabel1"/>';
			var ulID = "errorMessages";
			var divID = "error-massageTD";
			createErrorList(message, ulID, divID); 
			$('html, body').animate({ scrollTop: 0 }, 0);
			$("#actionMsgDoc").hide();
			$("#error-ul3").hide();
			$("#error-massageTD").show();
			return false;
		
		} else {
			//Test code//
			
			var filepath = attachmentDoc.split('\\');
			var file = filepath[filepath.length - 1];
			var filesplit = file.split('.');
			var filename = filesplit[filesplit.length - 2];
			var regex = new RegExp('^[a-zA-Z0-9_\s-]+$');
			if(!regex.test(filename)){
				message = "Invalid File Name, Only hyphen and underscore symbols allowed in File name";
				var ulID = "errorMessages";
				var divID = "error-massageTD";
				createErrorList(message, ulID, divID); 
				$('html, body').animate({ scrollTop: 0 }, 0);
				$("#actionMsgDoc").hide();
				$("#error-ul3").hide();
				$("#error-massageTD").show();
				return false;
			}
		//end of test code//
			var t = attachmentDoc.split('.');
			var extn = t[t.length-1];
			var lowrCaseExtn = extn.toLowerCase();
			
			if (lowrCaseExtn =="pdf"){
			
			/* var bufferSize = Math.pow(1024, 2) * 0.06; // 60 KB
			var bufferSizeMin = Math.pow(1024, 2) * 0.1; // 100 KB */
			
			var bufferSize = 61440; // 60 KB
			var bufferSizeMin = 5242880; // 5 MB
			
			var fileSize = $("#eligibilityCriteriaUploadFile1")[0].files[0].size;
			
			var fileSizeNum = parseInt(fileSize);
			var bufferSizeNum = parseInt(bufferSize);
			var bufferSizeMinNum = parseInt(bufferSizeMin);
			
			if(bufferSizeMinNum < fileSizeNum){
				message = "Document Size exceeds 5 MB";
				var ulID = "errorMessages";
				var divID = "error-massageTD";
				createErrorList(message, ulID, divID); 
				$('html, body').animate({ scrollTop: 0 }, 0);
				$("#actionMsgDoc").hide();
				$("#error-ul3").hide();
				$("#error-massageTD").show();
				return false;
				
				}  else if(bufferSizeNum > fileSizeNum){
					message = "Minimum document size should be 60 KB";
					var ulID = "errorMessages";
					var divID = "error-massageTD";
					createErrorList(message, ulID, divID); 
					$('html, body').animate({ scrollTop: 0 }, 0);
					$("#actionMsgDoc").hide();
					$("#error-ul3").hide();
					$("#error-massageTD").show();
					return false;
				
				} else {
					calculate(document.getElementById('eligibilityCriteriaUploadFile1'),"uploadDocument1");
					$("#error-massageTD").hide();
				}
			
			} else {
				message = "Invalid document format. Only pdf format is supported";
				var ulID = "errorMessages";
				var divID = "error-massageTD";
				createErrorList(message, ulID, divID); 
				$('html, body').animate({ scrollTop: 0 }, 0);
				$("#actionMsgDoc").hide();
				$("#error-ul3").hide();
				$("#error-massageTD").show();
				return false;
			}
			//$("#checkl").attr("disabled",true);
		}
		
		if(attachmentDoc == "false") {
			var message = "Please select file to upload "+'<s:property value="docLabel1"/>.';
			
			var ulID = "errorMessages";
			var divID = "error-massageTD";
			createErrorList(message, ulID, divID); 
			$('html, body').animate({ scrollTop: 0 }, 0);
			$("#actionMsgDoc").hide();
			$("#error-ul3").hide();
			return false;
		}
		var t = attachmentDoc.split('.');
		var extn = t[t.length-1];
		var lowrCaseExtn = extn.toLowerCase();
			$("#uploadDoc1").attr("disabled",true);
	}

	$(document).ready(function() {
		$('#checkl').attr("disabled", true);
		$('#uploadDoc1').attr("disabled", true);
    	$('#eligibilityCriteriaUploadFile1').change(function(){
        	$('#checkl').removeAttr("disabled");
        	$('#uploadDoc1').removeAttr("disabled");
    	});
	
	 	var checkboxes = document.getElementsByTagName("INPUT");
	 
   		for(var x=0; x<checkboxes.length; x++) {
      		if(checkboxes[x].type == "checkbox") {
          		checkboxes[x].checked = false;
      		}
   		}

		var addcount=0;
		var addArray=[];
	    var total=0;
		
	    <s:iterator value="uploadList" status="stat">
			 var ocdFlagValue = $("#ocdFlagValue"+total).val();
			 addArray.push(ocdFlagValue);
			total++;
		</s:iterator>
		
		var lUploadedDocCount=0;
		var lMandatoryFieldCheckCount=$('#uploadDocs1').find("div[class='row']").find("span.manadetory-fields").length;
		$('#uploadDocs1').find("div[class='row']").find("span.manadetory-fields").each(function(){
			lUploadedDocCount =lUploadedDocCount + $(this).closest("div").next().find("span.glyphicon.glyphicon-ok").length;
		});
	  
	  	if(lUploadedDocCount == lMandatoryFieldCheckCount ){
	  		$('#btnContinue').removeAttr("disabled");
	  	}else{
	  		$('#btnContinue').attr("disabled", true);
	  	}
	});

	function checkUpDoc() {
		var lUploadedDocCount=0;
		var lMandatoryFieldCheckCount=$('#uploadDocs1').find("div[class='row']").find("span.manadetory-fields").length;
		$('#uploadDocs1').find("div[class='row']").find("span.manadetory-fields").each(function(){
			lUploadedDocCount =lUploadedDocCount + $(this).closest("div").next().find("span.glyphicon.glyphicon-ok").length;
		});
		  
		if(lUploadedDocCount == lMandatoryFieldCheckCount ){
			document.uploadDocs1.action = "CandidateAction_updateCandidateStage.action";
			enableLoadingAnimation();
			document.uploadDocs1.submit();
			return true;
		
		}else{
			$('#btnContinue').attr("disabled", true);
			return false;
		}
	}


	function ShowDialog(modal,flag,pk,lable,lableInstruc,docLable) {
		$("#overlay").show();
		$("#dialog").fadeIn(300);
	
	
		if (modal) {
			$("#overlay").unbind("click");
			$("#ocdFlagValueHidden").val(flag);
			if(flag=='PSTM') {
				$("#noteTextForPSTM").html("Kindly scan all your PSTM Certificates from 1st to 12th Standard and Diploma degree as a single file and upload for PSTM claim.");
			
			} else if (flag=='SSLC' || flag=='HSC' || flag=='DIP' || flag=='UG' || flag=='PGDEG' || flag=='PGDIP'){
				$("#noteTextForEdu").html("If more than 1 attempt, all the certificates should be uploaded as a single pdf file. ");
			
			} else {
				$("#noteTextForPSTM").html("");
				$("#noteTextForEdu").html("");
			}
			
			$("#candidateDocPkHidden").val(pk);
			$("#docLabel1Hidn").val(docLable);
			$("#docLabel").html($('#lable'+lable).val());
			$("#docLabelInstruction").html($('#lableInstruc'+lableInstruc).val());
			document.uploadDocs.action="CandidateAction_insertCandidateDocuments.action";
		
		} else {
			$("#overlay").click(function(e) {
				HideDialog();
			});
		}
	}

	function HideDialog() {
		$("#overlay").hide();
		$("#error-massage").hide();
		$("#error-massageTD").hide();
		$('#checkl').attr("checked", false);
		
		var dataString = "action=Decline Declaration&audittrail=declined Declaration";
		$.ajax({
			type: 'POST',
			url: "CandidateAction_AuditTrailForDeclaration.action",
			async: true,
			data: dataString,
			
			error:function(ajaxrequest){
				window.reload();
			},
			success:function(responseText)
			{
				
			}
		});
		$("#dialog").fadeOut(300);
	}

</script>
<style>
ul#errorMessages {
	margin: 0px;
	padding: 0;
}

#actionMsgDoc li:first-child {
	list-style: none;
}
</style>

<div class="main-body">
	<div class="fade" id="pop3"></div>
	<label id="divDocUploadResponse" style="display: none;"></label>
	<div class="titlebg container">
		<h1 class="pageTitle">
			<strong> Upload Document </strong> <span class="userid_txt">
				<s:if test="%{#session['SESSION_USER'] != null}">
					<strong>User ID <s:text name="applicationForm.userId" />
					</strong> -	<s:label value="%{#session['SESSION_USER'].username}" />
				</s:if>
			</span>
		</h1>
	</div>

	<div id="dashboard" class="">
		<div class="container common_dashboard">
			<s:if test="hasActionMessages()">
				<div id="error-massage">
					<div class="error-massage-text"
						style="margin: 0; margin-left: 10px; padding: 0;">
						<ul style="margin: 1; margin-left: -25px; padding: 0;"
							id="error-ul3">
							<s:actionmessage escape="false" />
						</ul>
					</div>
				</div>
			</s:if>
			<div class="grayBoxDiv">
				<strong style="color:grey;"> <s:text name="uploadPhoto.instruction1" />
				</strong>
				<ul class="note ml20">
					<li>Kindly verify the document before uploading. <s:text
							name="uploadDocNote1" />
					</li>
					<li>Once Confirmed, select the checkbox after selecting the
						document to upload and press "Upload" button. <s:text
							name="uploadDocNote2" />
					</li>
					<li>Kindly upload the documents which does not have any of the
						mentioned special characters in its file name
						%#&+`$+={}[]|\:/"'<>,.? <s:text name="uploadDocNote3" />
					</li>
					<li>The uploaded Document should be of size 60KB to 5MB and
						only PDF Format File are allowed to upload.</li>
				</ul>
			</div>
			<div class="clear"></div>

			<s:form action="CandidateAction" name="uploadDocs1"
				enctype="multipart/form-data" method="post" id="uploadDocs1">
				<div class="uploadDocs">
					<s:iterator value="uploadList" status="stat" var="uploadList">

						<div class="row">
							<div class="col-md-4">
								<s:hidden id="ocdFlagValue%{#stat.index}" name="ocdFlagValue"
									value="%{ocdFlagValue}" />
								<s:hidden id="uploadDocMandatory%{#stat.index}"
									name="uploadDocMandatory" value="%{uploadDocMandatory}" />
								<s:hidden name="docLabel1" value="%{docLabel1}"
									id="lable%{#stat.index}" />
								<s:hidden name="instructions" value="%{instructions}"
									id="lableInstruc%{#stat.index}" />
								<s:property value="docLabel1" />
								<!-- Below is done to show tamil text in blue -->
								<%-- <s:if test="ocdFlagValue=='CC'">
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
										</s:if> --%>
								<span class="manadetory-fields">*</span>

							</div>
							<div class="col-md-8 text-right">
								<s:if test="documentFileName1==null">
									<input type="button"
										class="submitBtn btn btn-warning enrollFinal" value="Upload"
										onclick="ShowDialog(true,'<s:property value="ocdFlagValue" />','<s:if test="candidateDocPk1!=null"><s:property value="candidateDocPk1" /></s:if><s:else>null</s:else>',<s:property value="%{#stat.index}"/>,<s:property value="%{#stat.index}"/>,'<s:property value="docLabel2"/>')"
										id="uploadDoc" />
								</s:if>
								<s:if test="documentFileName1!=null">
									<p class="check">
										<span class="glyphicon glyphicon-ok"></span> <a
											class="wordWrap"
											href="CandidateAction_getDocument.action?candidateDocId=<s:property value="candidateDocPk1"/>"><s:property
												value="documentFileName1" /> </a>
									</p>
									<input type="button"
										class="submitBtn btn btn-warning enrollFinal" value="Edit"
										onclick="ShowDialog(true,'<s:property value="ocdFlagValue" />','<s:if test="candidateDocPk1!=null"><s:property value="candidateDocPk1" /></s:if><s:else>null</s:else>',<s:property value="%{#stat.index}"/>,<s:property value="%{#stat.index}"/>,'<s:property value="docLabel2"/>')"
										id="uploadDoc" />
								</s:if>
							</div>
						</div>
					</s:iterator>
				</div>
				<s:token></s:token>
			</s:form>
		</div>
		<s:form>
			<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
			<input type="hidden" name="isDataFound"
				value='<s:property value="dataFound"/>' />
			<div class="countinuebg">
				<div class="container">
					<div class="row">
						<div
							class="col-sm-2 col-sm-offset-10 col-xs-6 col-xs-offset-3 text-right padding0">
							<s:if test='%{documentMandatory}'>
								<s:if test="%{dataFound}">
									<s:submit method="updateCandidateStage" value="Save & Continue"
										cssClass="submitBtn btn btn-warning btn-block"
										onclick="return checkUpDoc();" id="btnContinue"></s:submit>
								</s:if>
								<s:else>
									<s:submit method="updateCandidateStage" value="Save & Continue"
										cssClass="submitBtn btn btn-warning btn-block buttonDisable"
										disabled="true" id="btnContinue"></s:submit>
								</s:else>
							</s:if>
							<s:else>
								<s:submit method="updateCandidateStage" value="Save & Continue"
									cssClass="submitBtn btn btn-warning btn-block" id="btnContinue"></s:submit>
							</s:else>
						</div>
					</div>
				</div>
			</div>
			<s:token />
		</s:form>
	</div>
</div>

<style>
.web_dialog_declr {
	width: 600px !important;
	height: 350px;
	margin-left: -250px;
	margin-top: -220px;
}
</style>

<s:form action="CandidateAction" name="uploadDocs"
	enctype="multipart/form-data" method="post" id="uploadDocs">

	<div class="fullscreen-container" id="dialog">
		<s:hidden name="disciplineType" value="%{disciplineType}"></s:hidden>
		<s:hidden name="ocdFlagValue" value="%{ocdFlagValue}"
			id="ocdFlagValueHidden" />
		<s:hidden name="docLabel2" value="%{docLabel2}" id="docLabel1Hidn" />
		<s:hidden name="candidateDocPk1" value="%{candidateDocPk1}"
			id="candidateDocPkHidden" />
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h2 class="modal-title">
						<label id="docLabel"></label>
						<%-- <span class="tamil"> <s:label
										id="tamilText" /></span> --%>
					</h2>
				</div>
				<div class="modal-body">
					<div class="row mb10">
						<div class="col-sm-12">
							<div id="error-massageTD" style="display: none">
								<div class="error-massage-text"
									style="margin: 0; margin-left: 15px; padding: 0;">
									<ul style="" id="errorMessages">
									</ul>
								</div>
							</div>
						</div>
					</div>
					<div class="uploadTable">
						<div class="row mb10">
							<div class="col-sm-12">
								<s:file name="data1" id="eligibilityCriteriaUploadFile1"
									onkeypress="return restrictEnter(event);"></s:file>
								<s:hidden name="data1Hash" id="data1Hash" />
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12">
								<p id="test2" style="color: #F00;">
									<b>Note :</b> The File Size should be between
									<%=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB)
						.toLowerCase()%>
									KB to
									<%=Integer.parseInt(
						ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB))
						/ 1024%>
									MB.
									<s:text name="uploadDocPopNote1" />
								</p>
								<p class="font14 mt10" id="noteTextForPSTM" style="color: #F00;"></p>

								<p class="font14 mt10">
									You can upload only
									<%=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE)
						.toLowerCase()%>
									files.
									<s:text name="uploadDocPopNote2" />
								</p>

								<p class="font14 mt10" id="noteTextForEdu"></p>

								<p class="font14 mt10">
									If the uploaded documents are not clear, then the application
									will be rejected.
									<s:text name="uploadDocPopNote3" />
								</p>
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 grayBoxDiv">
								<p class="font14 WeightBold orgNote nomargin">
									<s:checkbox name="checkCandidateAcceptance1" id="checkl"
										value="%{checkCandidateAcceptance1}" />
									I confirm that the file selected by me is correct.
									<%-- <s:text name="uploadDocPopNote4" /> --%>
								</p>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<div class="row">
							<div class="col-sm-3 col-sm-offset-3 mt10">
								<input type="button" class="ripple1 btn btn-warning btn-block"
									value="Upload" onclick="uploadDocument1();" id="uploadDoc1" />
							</div>
							<div class="col-sm-3 mt10">
								<input type="button" name="btnCancel" value="Cancel"
									onclick="HideDialog()"
									class="ripple1 btn btn-default btn-block" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<s:token></s:token>
</s:form>