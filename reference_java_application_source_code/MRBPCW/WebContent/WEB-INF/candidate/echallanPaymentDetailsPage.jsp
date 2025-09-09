<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.*"%>
<script type="text/javascript" src="js/moment.min.js"></script>
<link rel="stylesheet" href="../css/jquery-ui.css">
<link rel="stylesheet" href="../css/bootstrap.min.css">
<link rel="stylesheet" href="../css/common.css">
<link rel="stylesheet" href="../css/style.css">

<script type="text/javascript" src="js/q.js"></script>
<script type="text/javascript" src="js/spark-md5.min.js"></script>
<script type="text/javascript"></script>
<script src="../js/jquery-1.12.4.js"></script>
<script type="text/javascript">
<!--

//-->
var hashVal = null;
var met=null;
$(document).ready(function() {
	$("#challanDate" ).datepicker({
		maxDate: 0,
		changeMonth: true,
		changeYear: true,
		yearRange: "2020:2021",
		minDate: new Date('Oct 09,2020'),
		buttonText: "Challan Date",
		showOn: "button",
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		buttonImageOnly: true,
		dateFormat: 'dd-M-yy'
	});

	$("#challanDate").datepicker( "option", "minDate", new Date($("#registrationDate").val()));
	
	declarationCheckBoxOnload();	
	});
	
function  declarationCheckBoxOnload()
{

 var i=$("#declarationCkeck").is(':checked') ? 1 : 0;
  if(i==1)
  {
    $("#enableDisable").removeAttr('disabled');
  }
  if(i==0)
  {
    $("#enableDisable").attr('disabled', true);
  }
}
	

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
	    }
	    else {
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

	
function uploadImage()
{
	
$("#test").hide();
$("#test2").hide();
var attachmentPhoto = $("#attachmentPhoto").val();

if(attachmentPhoto == ""){
	var message = "Please select file to upload payment receipt.";
	var ulID = "error-ulUploadForm";
	var divID = "error-massageUploadForm"; 
	createErrorList(message, ulID, divID); 
	$('html, body').animate({ scrollTop: 0 }, 0);
	$("#actionMsgPhoto").hide();
	return false;
}
else{
	
	var t = attachmentPhoto.split('.');
	
	var extn = t[t.length-1];
	
	var lowrCaseExtn = extn.toLowerCase();
	
	if ( lowrCaseExtn =="jpg" || lowrCaseExtn =="jpeg" || lowrCaseExtn =="png" ){
	var bufferSize = Math.pow(1024, 2) * 0.15; // 150 KB
	var bufferSizemin = Math.pow(1024, 2) * 0.03; // 150 KB
	var fileSize = $("#attachmentPhoto")[0].files[0].size;
	
	var fileSizeNum = parseInt(fileSize);
	var bufferSizeNum = parseInt(bufferSize);
	var bufferSizeminNum=parseInt(bufferSizemin)
	console.log("bufferSize Size:"+bufferSizeNum +" Photo fileSizeNum :"+fileSizeNum);
	
	if(bufferSizeNum < fileSizeNum){
		
		message = "Payment Receipt size exceeds 150KB";
		var ulID = "error-ulUploadForm";
		var divID = "error-massageUploadForm";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgPhoto").hide();
		return false;
		}else if(bufferSizeminNum>fileSizeNum ){
			message = "Payment Receipt size cannot be less than 30kb";
			var ulID = "error-ulUploadForm";
			var divID = "error-massageUploadForm";
			createErrorList(message, ulID, divID); 
			$('html, body').animate({ scrollTop: 0 }, 0);
			$("#actionMsgPhoto").hide();
			return false;
			
		}
	else{
		calculate(document.getElementById('attachmentPhoto'),"uploadImage");
		$("#error-massageUploadForm").hide();
		}
	}
	else
		{
		message = "Invalid Payment Receipt Format.";
		var ulID = "error-ulUploadForm";
		var divID = "error-massageUploadForm";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgPhoto").hide();
		return false;
		}
}
/* var t = attachmentPhoto.split('.');

var extn = t[t.length-1];

var lowrCaseExtn = extn.toLowerCase();
 */
//if (lowrCaseExtn =="jpg" || lowrCaseExtn =="jpeg" || lowrCaseExtn =="gif"){
	/*document.register.action="CandidateAction_insertCandidateImage.action";
	document.register.submit();*/
	
	/*setTimeout(function(){
		document.getElementById("inputStreamForImage").src = "data:image/png;base64,";
	},2000);*/
	//ajaxFileUpload("attachmentPhoto");
//}else{
//	$("#test2").show();
//}

}


function calculate(input,method) {
	 // var input =document.getElementById('eligibilityCriteriaUploadFile');
	  if (!input.files.length) {
	    return;
	  }

	  var file =input.files[0];
	  var bufferSize = Math.pow(1024, 2) * 10; // 10MB

	  calculateMD5Hash(file, bufferSize,method).then(
	    function(result) {
	      // Success
	      console.log(result);
	    },
	    function(err) {
	      // There was an error,
	    },
	    function(progress) {
	      // We get notified of the progress as it is executed
	      console.log(progress.currentPart, 'of', progress.totalParts, 'Total bytes:', progress.currentPart * bufferSize, 'of', progress.totalParts * bufferSize);
	      if(met=="uploadImage")
		  {
	    	  //alert("submit");
			  document.getElementById("attachementPhotoHash").value=hashVal;
		  }
	      
	      
	      alert("File processed successfully");
	      
	     
	  /*     
	    document.register.action="CandidateAction_insertCandidateChallanPaymentReceipt.action";
		  document.register.submit();  */
		  
		  
		  
		  
	    });
	}
	
function insertChallanDetails()
{
	var message = "";
	
	var attachmentPhoto = $("#attachmentPhoto").val();
	
	var challanNo = $("#challanNo").val();
	
	var applicablefeeamt = $("#applicablefeeamt").val();
	
	var challanDate = $("#challanDate").val();
	
	
	
	if ($.trim(challanNo).length == 0) {
		message = message + "Please enter Post Office invoice Number / Receipt Number."+"##";
    }
	
	if ($.trim(applicablefeeamt).length == 0) {
		message = message + "Please enter Examination Fees."+"##";
    }
	
	if ($.trim(challanDate).length == 0) {
		message = message + "Please select Challan Date."+"##";
    }


	if(attachmentPhoto == ""){
		message = message + "Please select file to upload payment receipt."+"##";
		
		
	}
	else{
		var t = attachmentPhoto.split('.');
		var extn = t[t.length-1];
		
		var lowrCaseExtn = extn.toLowerCase();
		
		if ( lowrCaseExtn =="jpg" || lowrCaseExtn =="jpeg" || lowrCaseExtn =="png" ){
		var bufferSize = Math.pow(1024, 2) * 0.15; // 150 KB
		var bufferSizemin = Math.pow(1024, 2) * 0.03; // 150 KB
		var fileSize = $("#attachmentPhoto")[0].files[0].size;
		
		var fileSizeNum = parseInt(fileSize);
		var bufferSizeNum = parseInt(bufferSize);
		var bufferSizeminNum=parseInt(bufferSizemin);
		console.log("bufferSize Size:"+bufferSizeNum +" Photo fileSizeNum :"+fileSizeNum);
		
		if(bufferSizeNum < fileSizeNum){
			
			
			message = message + "Payment Receipt size exceeds 150KB."+"##";
			}else if(bufferSizeminNum>fileSizeNum )
			{
				message = message + "Payment Receipt size cannot be less than 30kb."+"##";
				
			}
		else{
				
			var value=$("#attachementPhotoHash").val();
			if(value== null || value=='')
				{
				message = message + "Please Click on upload button to process file."+"##";
			
				}
			}
		
		}
		else
			{
			message = message + "Invalid Payment Receipt Format."+"##";
		
			}
	}
	
		
	
	if (message != "") {

			var ulID = "error-ulUploadForm";
			var divID = "error-massageUploadForm";
			createErrorList(message, ulID, divID);
			$('html, body').animate({
				scrollTop : 0
			}, 0);
			$("#actionMsgPhoto").hide();

		} else {
			//alert("hello");
			document.register.action = "CandidateAction_insertCandidateChallanPaymentReceipt.action";
			document.register.submit();
		}
	}

	function calculateTotal() {

		var applicablefeeamt = $("#applicablefeeamt").val();

		if (!isNaN(applicablefeeamt)) {

			var total = +applicablefeeamt + 0;
			$("#totalAmount").val(total);
		}
	}
	function alphaNumericOnly(e) {
		var k;
		document.all ? k = e.keyCode : k = e.which;

		return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57));
	}

	function numericOnly(e) {

		var unicode = e.charCode ? e.charCode : e.keyCode;
		if ((unicode<48 || unicode>57) && unicode != 8 && unicode != 9)
			return false;
	}
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>E-Challan Payment details page</title>
</head>
<body>

<div class="container titlebg mt">
	<h1 class="pageTitle">
		E-Challan Payment details page
		<div class="userid_txt">
			<s:if test="%{#session['SESSION_USER'] != null}">
				<strong>Registration Number<span class="tamil"> <s:text
							name="applicationForm.userId" />
				</span></strong> -
										<s:label value="%{#session['SESSION_USER'].username}" />
			</s:if>
		
		</div>
	</h1>
</div>


	<div class="container">
	<div class="row">
				<div class="col-sm-12"><s:actionmessage escape="false" cssClass="msgg" id="actionMsgPhoto"/>
					<div id="error-massageUploadForm" style="display:none" class="error-massage">
						<div class="error-massage-text" style="margin:0; margin-left:-12px; padding:0;">
							<ul  id="error-ulUploadForm" style="margin:1; margin-left:35px; padding:2;">
							</ul>
						</div>
					</div>
				</div>
			</div>
		<div id="dashboard" class="common_dashboard">
		<!-- Box Container Start -->
		
		<s:form method="post" name="register" action="CandidateAction" enctype="multipart/form-data" id="frmImageUpload">
		<%-- <s:form id="ChallanContainer" name="ChallanContainer" method="post"> --%>
		<div id="PaymentDetTbl" >
						<div class="row">
							<div class="col-sm-12">
								<h4 title="E-Challan Payment details">
									<%-- <s:text name="Payment Details"/> --%>
									<i class="glyphicon glyphicon-hand-right"></i>&nbsp;&nbsp;E-Challan Payment details
								</h4>
							</div>
						</div>
						<div class="row mt10">
							<div class="col-sm-6 mt10">
								<table width="100%" border="0" cellpadding="0" cellspacing="0" class="contenttable form-group">
								
								
								
								<tr class="form-group">
										<td  class="control-label" width="290" valign="top">Registration Number<s:text
							name="applicationForm.userId" />   
											</td>
										<td><s:textfield name="%{#session['SESSION_USER'].username}" id="scrollNumber" value="%{#session['SESSION_USER'].username}" disabled="true"  />
											</tr>
									<tr class="form-group">
										<td  class="control-label" width="290" valign="top">Post Office invoice Number / Receipt Number<s:text
							name="challanpro"/>   
											<span class="manadetory-fields">*</span></td>
										<td><s:textfield name="challanNo" id="challanNo" value="%{challanNo}" size ="75" 
				maxlength="75"  onkeypress="return alphaNumericOnly(event);"  />
											<%-- <span class="note">Please enter the Challan / Journal Number provided by MHPOST.</span> <br/> --%>
											</tr>
											
									<tr class="form-group">
										<td  style="border: 1px solid #ccc;" class="control-label">Examination Fees <s:text
							name="exampro" />   
										<span class="manadetory-fields">*</span></td>
										<td style="border: 1px solid #ccc;">
											
											<s:textfield name="applicablefeeamt" id="applicablefeeamt" value='%{applicablefeeamt}' maxlength="4"  onblur="calculateTotal();" onkeypress="return numericOnly(event);" /></td>
									</tr>
									
									<tr class="form-group">
										<td style="border: 1px solid #ccc;" class="control-label">Post Office Charges <s:text
							name="postpro" />   </td>
										<td style="border: 1px solid #ccc;">
											
											<s:textfield name="applicablefeeamt1" id="applicablefeeamt1" value='0' maxlength="4"  disabled="true" /></td>
									</tr>
									
									<tr class="form-group">
										<td style="border: 1px solid #ccc;"  class="control-label">Total<s:text
							name="totalpro" /></td>
										<td style="border: 1px solid #ccc;">
											
											<s:textfield name="totalAmount" id="totalAmount" value='%{totalAmount}' maxlength="4"  disabled="true" /></td>
									</tr>
									<tr class="form-group">
										<td  class="control-label">Challan Date <s:text
							name="datepro" />
											&nbsp;<span class="manadetory-fields">*</span></td>
										<td class="dateInput">
											<s:textfield  name="challanDate" id="challanDate" value="%{challanDate}"  
											readonly="true"  cssClass="form-control mt5"
											maxlength="11" size="20" />
										</td>
									</tr>
									
									
										<tr>
									<td  class="control-label">Upload Payment Receipt<s:text
							name="uploadPro" />
											&nbsp;<span class="manadetory-fields">*</span></td>
											<td>
																<s:file name="attachmentPhoto" id="attachmentPhoto" label="Attachment Signature"/>
																<s:hidden name="attachementPhotoHash" id="attachementPhotoHash"/></td>
																
																<td>	<input type="button"  class="btn btn-warning btn-sm enrollFinal" value="Upload" onClick="uploadImage()"  /></td>
													</tr>
								</table>
								<p class="orgNote"><strong>
								<s:text name="uploadPhoto.instruction1"/>														
								</strong>The Minimum File Size is <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_PAYMENTIMAGE_SIZE_IN_KB)%> KB,
								<s:text name="uploadPhoto.instruction2"/>
								 <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_PAYMENTIMAGE_SIZE_IN_KB)%>
								<s:text name="uploadPhoto.instruction3"/>
								<%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.PaymentReceipt_TYPE).toLowerCase()%> images are accepted. 
								
								<span class="tamil">
								<s:text name="Echallan.DOCNote"/>
								</span></p>
							</div>
						</div>
					</div>
				<div class="mt20 font14">
									<s:checkbox  id="declarationCkeck" onClick="declarationCheckBoxOnload();" value="%{declarationCkeck}" name="declarationCkeck"></s:checkbox> &nbsp;  
									
									If payment is not updated in dashboard then you will not be eligible for the exam.<s:text
							name="declarePro" />
								</div>
			
				<s:token></s:token>
			</s:form>
			
			<div class="row mt20" id="subtChallDiv"  >
				<div class="col-sm-2">
					<input type="button" id="enableDisable"  title ="Submit" name="submit" id="submit" value="Submit" class="btn btn-warning btn-block" onClick="insertChallanDetails();"/>
				</div>
			<!-- 	<div class="col-sm-2 mb10">
					<input type="reset" name="clear" id="Clear" value="Clear" class="btn btn-default btn-block" onClick="callFunc();"/>
				</div> -->
			</div>	
				
	</div>
				
									
				
</div>

</body>
</html>