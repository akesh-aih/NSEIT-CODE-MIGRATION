<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">

$(document).ready(function() {

	//$("#ddContainer").validationEngine({promptPosition : "bottomLeft", scroll: false});
	//$("#ChallanContainer").validationEngine({promptPosition : "bottomLeft", scroll: false});

	$("#btnPaymentSubmit").click(function() {
		var paymentType = $('input:radio[name=payment]:checked').val();
		
		if(paymentType == "DD")
		{
			var flag = validateDDDetails();

			if (flag == true)
			{
				$("#ddContainer").submit();
			}
		}
		if(paymentType == "challan")
		{
			var flag = validateChallanDetails();

			if (flag == true)
			{
				//$("#ChallanContainer").validationEngine({promptPosition : "bottomLeft", scroll: false});
				$("#ChallanContainer").attr('action',"PaymentOfflineAction_insertChallanDetails.action");
				$("#ChallanContainer").submit();
			}
		}
		if (paymentType != "DD" && paymentType != "challan")
		{
			alert ("Please select Offline Payment Type");
			return;
		}
	});
	
	$("#btnFinalSubmit").click(function() {


		var paymentType = $('input:radio[name=payment]:checked').val();
		var actionname = "PaymentOfflineAction_";
		var index = ($("#hPaymentDivInd").val()*1);
		
		var submitVal = "true";

		if( $('#radioContainer').is(':visible') ) 
		{
		
			if (paymentType!="DD" && paymentType !="challan")
			{
			alert ("Please select any one payment mode");
			return;
			}
		}
		
		var dataString = "";

		if(($("#ddContainer").validationEngine('validate')==true) && paymentType == "DD")
		{
			if($("#ddNumber").val()=="")
			{
				if(paymentType == "DD")
				{
					return false;
				}
			}
			
			if($("#ddBankCode").val()=="")
			{
			//alert("Please Select The Bank Name");
			return false;
			}
			if($("#ddCityCode").val()=="")
			{
			//alert("Please Select The City Name");
			return false;
			}
			
			var agree = true;
			
			if (agree == true)
			{

				if(paymentType == "DD")
				{
					//alert("Into DD")
					$("#ChallanContainer").validationEngine('hide');
					actionname = actionname + "insertDDDetails.action";
					var ddNumber       = $("#ddNumber").val();
					var ddDate         = $("#ddDate").val();
					var ddBankCode     = $("#ddBankCode").val();
					var ddCityCode     = $("#ddCityCode").val();
					var paymentPK      = $("#paymentPK").val();												
					var enrollmentFK   = $("#enrollmentFK").val();	
					var transactionNo  = $("#transactionNo").val();	
					var amount         = $("#amount").val();	
					var stage          = $("#stage").val();	
					var paymentModeDD    = $("#paymentModeDD").val();
					var disciplineId = $("#hddDisciplineId").val();
					dataString     = "ddNumber="+ddNumber+	"&ddDate="+ddDate+"&ddBankCode="+ddBankCode+"&ddCityCode="+ddCityCode+
						"&enrollmentFK="+enrollmentFK+"&transactionNo="+transactionNo+"&amount="+amount+"&stage="+stage+"&paymentPK="+
						paymentPK+"&paymentModeDD="+paymentModeDD+"&submitVal="+submitVal+"&disciplineId="+disciplineId;
				}

				if(paymentType == "DD")
				{
				$.ajax({
					url: actionname,
					async: true,
					data: dataString,
					beforeSend: function()
					{
						
					},
					error:function(ajaxrequest)
					{
						alert('Error registering user. Server Response: '+ajaxrequest.responseText);
					},
					success:function(responseText)
					{
						responseText = $.trim(responseText);
						
						if(responseText.length > 0)
						{
							message = responseText.substring(2, responseText.length);
							if(responseText.charAt(0) == "0")
							{
								submitPersonalDetailsForPreview();
							}
							else if(responseText.charAt(0) == "2")
							{
								alert("DD no. "+ddNumber+" already exist. Please enter another DD");
								return false;
							}
							else if(responseText.charAt(0) == "9")
							{
								alert(message);
								return false;
							}
						}
					}
				});
				}	
				
			//return true;
			}
			else
				return false;
		}
		//alert("Challan----------------------------------->"+$("#ChallanContainer").validationEngine('validate'));
		if($("#ChallanContainer").validationEngine('validate')==true)
		{
			//alert("Challan---");


			if($("#scrollNumber").val()=="")
			{
			//alert("Please Enter The Journal/Scroll Number");
			return false;
			}
			
			if($("#challanBankCode").val()=="")
			{
			//alert("Please Select  The Bank Code");
			return false;
			}

			if($("#challanBranchName").val()=="")
			{
			//alert("Please Enter The Branch Code");
			return false;
			}
			
		var ans = confirm ("Once the Enrollment form is submitted, it will not be allowed to be modified.\n Do you want to submit?");
	
		if(paymentType == "challan" && ans == true)
		{
			//alert("in challan");
			$("#ddContainer").validationEngine('hide');
			var scrollNumber     = $("#scrollNumber").val();
			var challanDate      = $("#challanDate").val();
			var challanBankCode  = $("#challanBankCode").val();
			var challanBranchName  = $("#challanBranchName").val();
			var paymentPK        = $("#paymentPK").val();
			var enrollmentFK     = $("#enrollmentFK").val();
			var transactionNo    = $("#transactionNo").val();
			var amount           = $("#amount").val();
			var stage            = $("#stage").val();
			var paymentModeCH      = $("#paymentModeCH").val();
			var disciplineId 	 = $("#hddDisciplineId").val();
			dataString = "scrollNumber="+scrollNumber+"&challanDate="+challanDate+"&challanBranchName="+challanBranchName+"&challanBankCode="+challanBankCode+
				"&enrollmentFK="+enrollmentFK+"&amount="+amount+"&paymentPK="+
				paymentPK+"&paymentMode="+paymentModeCH+"&submitVal="+submitVal+"&disciplineId="+disciplineId;		
			
			actionname = actionname + "insertChallanDetails.action";
		}
		else
			return false;
		//alert(dataString);
		$.ajax({
			url: actionname,
			async: true,
			data: dataString,
			beforeSend: function()
			{
				
			},
			error:function(ajaxrequest)
			{
				alert('Error registering user. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				//alert("All the details should get submitted successfully.");
				responseText = $.trim(responseText);
				
				if(responseText.length > 0)
				{
					message = responseText.substring(2, responseText.length);
					if(responseText.charAt(0) == "0")
					{
						submitPersonalDetailsForPreview();
					}
					else if(responseText.charAt(0) == "9")
					{
						alert(message);
						return false;
					}
				}
			}
		});
	
		}

	});
	
	});
	
	function validateChallanDetails(){
	
	$("#error-massage3").hide();

	var disciplineType = $("#challanBankCode").val();
	var message = "";

	if(disciplineType == ""){
		message = message + "Please select Bank Name"+"##";
	}
	
	var scrollNumber = $("#scrollNumber").val();

	if(scrollNumber == ""){
		message = message + "Please enter Journal/Scroll Number"+"##";
	}
	
	var challanBranchName = $("#challanBranchName").val();

	if(challanBranchName == ""){
		message = message + "Please enter Branch Code"+"##";
	}
	
	var challanDate = $("#challanDate").val();

	if(challanDate == ""){
		message = message + "Please select Payment Date"+"##";
	}
	
	if(message != ""){
		var ulID = "error-ul4";
		var divID = "error-massage4";
		createErrorList(message, ulID, divID); 
		return false;
	}
	else
		return true;
}

function validateDDDetails(){

	var ddNumber = $("#ddNumber").val();
	var message = "";

	if(ddNumber == ""){
		message = message + "Please enter DD Number"+"##";
	}
	
	var ddDate = $("#ddDate").val();

	if(ddDate == ""){
		message = message + "Please select DD Date"+"##";
	}
	
	var ddBankCode = $("#ddBankCode").val();

	if(ddBankCode == ""){
		message = message + "Please select Bank Name"+"##";
	}
	
	var ddCityCode = $("#ddCityCode").val();

	if(ddCityCode == ""){
		message = message + "Please select City"+"##";
	}
	
	if(message != ""){
		var ulID = "error-ul2";
		var divID = "error-massage2";
		createErrorList(message, ulID, divID); 
		return false;
	}
	else
		return true;
}
</script>
<div id="PersonalInfo">
<div id="error-massage4" style="display:none" class="error-massage">
      		<div class="error-massage-text">
      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul4">
      	
      		</ul>      	
      	 </div>
      </div>
      
<div id="error-massage2" style="display:none" class="error-massage">
      		<div class="error-massage-text">
      		<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul2">
      	
      		</ul>      	
      	 </div>
      </div>      
<div class="PersonalInfoCont">
<s:if test='%{paymentDisplayFlag=="true"}'>

<div id="TabDiv6" class="tab_content" style="display: block">
<div class="errorMessageActive">
	<s:actionmessage/>
</div>

<!-- Row One Start -->
<div class="padleft40 clear">
<h1 class="pageTitle" title="Offline Payment Type"><s:text name="Offline Payment Type"/></h1>
<div class="hr-underline2"></div>

<div class="field-label" id="radioContainer" align="left">
	<input type="hidden" name="amount" value="<s:property value="amount" />"/>
	<input type="radio" name="payment" value="DD" id="ddPaymentId"  class= "enrollFinal" onclick="resetValidationChallan();paymentOPT('pay2');"/><s:text name="personaldetails.ddPayment"/>&nbsp;&nbsp;&nbsp;&nbsp;
	<input type="radio" name="payment" id="challanPaymentId" class= "enrollFinal" value="challan" onclick="resetValidationDD();paymentOPT('pay3');"/><s:text name="personaldetails.challanPayment"/>
</div>
<div class="height10 clear"></div>
<br/>

<!-- DD Start -->
<jsp:include page="insertDDDetails.jsp" />
<!-- DD End -->

<!-- Challan Start -->
<jsp:include page="insertChallanDetails.jsp" />
<!-- Challan End -->


<div class="height20 clear"></div>
<div class="clear" align="center">
<input type="button" value="<s:text name="personaldetails.submit"/>" title="Submit" class="submitBtn button-gradient enrollFinal" id="btnPaymentSubmit"/>
</div>

</div>
<!-- Row One End -->
</div>	
</s:if>
<s:else>
	
	<div id="TabDiv6" class="tab_content" style="display: block">
	<div class="field-label"><s:text name="personaldetails.paymentMode"/></div>
	<div class="hr-dashline"></div>
	
	<s:text name="%{#request['PAYMENT_WINDOW_CLOSED_MESSAGE']}" />

	<div class="height20 clear"></div>
	<s:if test='%{paymentDisplayFlag=="true"}'>	
	<div class="clear">
	<input type="button" value="<s:text name="personaldetails.back"/>" title="Back" class="submitBtn button-gradient enrollFinal" id="btnPaymentBack"/>&nbsp;&nbsp;
	</div>
	</s:if>
	</div>
</s:else>
</div>
</div>