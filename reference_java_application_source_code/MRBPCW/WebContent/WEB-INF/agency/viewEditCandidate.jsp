<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript">

$(document).ready(function() {

	$("#sscPercentage").attr("readonly","readonly");
	$("#hscPercentage").attr("readonly","readonly");
	$("#bePercentage").attr("readonly","readonly");
	
	
	
	
	<s:if test='%{talukaFlag == "true"}'>
	$("#talukaList").attr("disabled",true);
	$("#talukaField").removeAttr("disabled");
</s:if>


  <s:if test='%{altTalukaFlag == "true"}'>
	$("#altTalukaList").attr("disabled",true);
	$("#altTalukaField").removeAttr("disabled");
</s:if>


<s:if test='%{talukaFlag == "false"}'>
	$("#talukaList").removeAttr("disabled");
	$("#talukaField").attr("disabled",true);
</s:if>


  <s:if test='%{altTalukaFlag == "false"}'>
	$("#altTalukaList").removeAttr("disabled");
	$("#altTalukaField").attr("disabled",true);
</s:if>
	
	
	if ($("#beMaxMarks").val() == 0){
		$("#beMaxMarks").val("");
	}
	if ($("#beObtainedMarks").val() == 0){
		$("#beObtainedMarks").val("");
	}
	
	if ($("#bePercentage").val() == 0.0 || $("#bePercentage").val() == 0.00){
		$("#bePercentage").val("");
	}
disableGraduationMarks();
disableSpiCpiFields();



$('#block9').hide();

var timestamp = new Date().getTime();
var imageHtml = '<img width="110" height="150" src="PhotoImage.jpg?t=' + timestamp + '&userFk= + ' + $.trim($("#test").val()) + '" id="id"/>';
					$("#imageTd").html(imageHtml);
					$("#imageTd").fadeIn(1000);

$("#dateOfBirth").attr("disabled",true);
setTimeout("checkForImageChange();",2000);
$("#id").attr ("src","PhotoImage.jpg?userFk="+$("#test").val());
toggleDegreeOther();

<s:if test='%{displayForEditFlag=="true"}'>
	$("#block20").show();
</s:if>
	
	var r = $("#successFlag").val();
	
	if (r=="true"){
		$("#block10").show();
	}
	
	enableDisableTextField();
	enableDisableHandicappedPercentage();
	
	
	var minyear = "<%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DATE_OF_BIRTH_YEAR)%>";
	
	var maxyear = "<%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DATE_OF_BIRTH_YEAR)%>";
	
	var minyearnew = "-";
	
	minyearnew = minyearnew + minyear + "Y";
	
	var maxyearnew = "-";
	
	maxyearnew = maxyearnew + maxyear + "Y";
	
	$("#dateOfBirth").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: maxyearnew,
		maxDate: minyearnew,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy'
    });
    
    $(".fieldDisable").each(function(currIndex, currElement) {
		$(currElement).attr('disabled', 'disabled');
	});
	if ($('#addressChkBox').attr('checked'))
	{
		copyPermenantAddress();
	}
})


	function refreshDegree(currVal)
	{
		dataString = "educationDetailsBean.degreeType=" + currVal;
		$.ajax({
			url: "CandidateAction_getReferenceValueForDomainName.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#academic').empty();
					$("#academic").append(
					           $('<option></option>').val("").html("--Select Degree--")
					     );

					$("#degreeOther").val("");
					$("#degreeOther").attr("disabled", "disabled");
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				responseText = $.trim(responseText);
				if(responseText.length > 0)
				{
					degreeTypeArr = responseText.split("#");
					currIndex = 0;  
					while(currIndex<degreeTypeArr.length)
					{
						currDegArr = degreeTypeArr[currIndex].split(",");

						$("#academic").append(
						           $('<option></option>').val(currDegArr[0]).html(currDegArr[1])
						     );
						currIndex = currIndex+1;
					}
				}
			}
		});
	}


function calculatePerc(type){
		if (type =='ssc'){
			var sscObtMark = $("#sscObtainMarks").val();
			var sscMaxMark = $("#sscMaxMarks").val();
			
			if (sscObtMark!="" && sscMaxMark && sscMaxMark!="0"){
				var perc = (parseFloat(sscObtMark)/parseFloat(sscMaxMark))*100;
				
				var result=Math.round(perc*100)/100
				$("#sscPercentage").val(result);
			}
		}
		
		if (type =='hsc'){
			var hscObtainedMarks = $("#hscObtainedMarks").val();
			var hscMaxMarks = $("#hscMaxMarks").val();
			if (hscObtainedMarks!="" && hscMaxMarks && hscMaxMarks!="0"){
				var perc = (parseFloat(hscObtainedMarks)/parseFloat(hscMaxMarks))*100;
				
				var result=Math.round(perc*100)/100
				$("#hscPercentage").val(result);
			}
		}
		
		if (type =='graduation'){
			var beObtainedMarks = $("#beObtainedMarks").val();
			var beMaxMarks = $("#beMaxMarks").val();
			
			if (beObtainedMarks!="" && beMaxMarks && beMaxMarks!="0"){
				var perc = (parseFloat(beObtainedMarks)/parseFloat(beMaxMarks))*100;
				
				var result=Math.round(perc*100)/100
				$("#bePercentage").val(result);
			}
		}
}



function toggleDegreeOther()
	{
		if($("#academic option:selected").text()=="Other")
		{
			$("#degreeOther").removeAttr("disabled");
		}
		else
		{
			$("#degreeOther").val("");
			$("#degreeOther").attr("disabled", "disabled");
		}
	}

function enableDisableHandicappedPercentage()
			{

				var handicapped = "";
				 $(".handicappedPercentageClass").each(function(){
						if($(this).is(':checked'))
						{
							 var id = $(this).attr("id");
							 handicapped = $('label[for='+id+']').text();
						}
					});

				if(handicapped == "Yes")
				{
					$("#handicappedPercentage").attr ('disabled',false);
					$("#handicappedPercentage").focus();
				}
				if(handicapped == "No")
				{
					$("#handicappedPercentage").attr ('disabled',true);
					$("#handicappedPercentage").val("");
				}
			}
			

function setUserId(){
	$("#userPk").val($("#test").val());
}
function setUserIdForUploadImage (){
	$("#userPk").val($("#test").val());
}

function populateState() {
	var countryId = $("#countryId").val();
	var countryName = $("#countryId option:selected").text();
	
	dataString = "countryVal="+countryId
	
	if (countryName == "INDIA"){
		$("#stateDropdown").show();
		$("#otherStateVal").hide();
		$("#districtId").show();
		$("#otherDistrict").hide();
		
		$.ajax({
			url: "CandidateAction_getStateList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#stateList').empty();
					$("#stateList").append(
					           $('<option></option>').val("").html("Select State")
					     );
				
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
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
						$.each(element, function(val) {
						  var nameAndIDArr = element[val].split("#");
						  $("#stateList").append(
						           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
						     );
					 	}); 
					}
				}
			},
			complete:function()
			{
				populateUT();
			}
		});

		
	}else{
		$("#stateDropdown").hide();
		$("#otherStateVal").show();
		$("#districtId").hide();
		$("#otherDistrict").show();
	}	
		
}

function populateUT() {
	var countryId = $("#countryId").val();
	var countryName = $("#countryId option:selected").text();
	
	dataString = "countryVal="+countryId
	
	if (countryName == "INDIA"){
		$("#stateDropdown").show();
		$("#otherStateVal").hide();
		$("#districtId").show();
		$("#otherDistrict").hide();
		
		$.ajax({
			url: "CandidateAction_getUTList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#utList').empty();
					$("#utList").append(
					           $('<option></option>').val("").html("--Select Union Territory--")
					     );
				
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
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
						$.each(element, function(val) {
						  var nameAndIDArr = element[val].split("#");
						  $("#utList").append(
						           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
						     );
					 	}); 
					}
				}
			}
		});
	}else{
		$("#stateDropdown").hide();
		$("#otherStateVal").show();
		$("#districtId").hide();
		$("#otherDistrict").show();
	}	
		
}

function populateAltUT() {

	var altcountryId = $("#altCountryId").val();
	var altcountryName = $("#altCountryId option:selected").text();
	
	dataString = "countryVal="+altcountryId
	
	if (altcountryName == "INDIA"){
		$("#stateDropdown").show();
		$("#otherStateVal").hide();
		$("#districtId").show();
		$("#otherDistrict").hide();
		
		$.ajax({
			url: "CandidateAction_getUTList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#altUtList').empty();
					$("#altUtList").append(
					           $('<option></option>').val("").html("--Select Union Territory--")
					     );
				
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText1)
			{
				//alert(responseText1);
				responseText1 = $.trim(responseText1);
				if(responseText1.length > 0)
				{
					element = responseText1.split(",");  
					message = responseText1.substring(2, responseText1.length);
					if(element[0] == "9")
					{
						//alert(message);
						//return false;
					}
					else
					{
						$.each(element, function(val) {
						  var nameAndIDArr = element[val].split("#");
						  $("#altUtList").append(
						           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
						     );
					 	}); 
					}
				}
			}
		});
	}else{
		$("#stateDropdown").hide();
		$("#otherStateVal").show();
		$("#districtId").hide();
		$("#otherDistrict").show();
	}	
		
}

function populateAlternateState() {
	
	var countryId = $("#altCountryId").val();
	
	dataString = "countryVal="+countryId;

	var altCountryId = $("#altCountryId option:selected").text();

	if (altCountryId == "INDIA"){
		$("#alternateStateDisplay").show ();
		$("#alternateDistrictDisplay").show();
		$("#alternateDistrictFieldDisplay").hide();
		$("#alternateStateFieldDisplay").hide();
		
		$.ajax({
			url: "CandidateAction_getStateList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#altStateList').empty();
					$("#altStateList").append(
					           $('<option></option>').val("").html("--Select State--")
					     );
				
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
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
						$.each(element, function(val) {
						  var nameAndIDArr = element[val].split("#");
						  $("#altStateList").append(
						           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
						     );
					 	}); 
					}
				}
			},
			complete:function()
			{
				populateAltUT();
			}
			
		});

		
	}else {
		
		$("#alternateStateDisplay").hide ();
		$("#alternateDistrictDisplay").hide();
		$("#alternateDistrictFieldDisplay").show();
		$("#alternateStateFieldDisplay").show();
		
	}
	
	
}

var currUTStateId = "";
var currAltUTStateId = "";

function populateDistrictForState()
{
	$('#utList').val("");
	currUTStateId = "#stateList";
	populateDistrict();
}

function populateDistrictForUT()
{
	$("#stateList").val("");
	currUTStateId = "#utList";
	populateDistrict();
}

function populateAlternateDistrictForState()
{
	$('#altUtList').val("");
	currAltUTStateId = "#altStateList";
	populateAlternateDistrict();
}

function populateAlternateDistrictForUT()
{
	$("#altStateList").val("");
	currAltUTStateId = "#altUtList";
	populateAlternateDistrict();
}

function populateDistrict(){
	var state = $(""+currUTStateId).val();
	dataString = "stateVal="+state
	
		
		$.ajax({
			url: "CandidateAction_getDistrictList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#districtList').empty();
					$("#districtList").append(
					           $('<option></option>').val("").html("--Select District--")
					     );
					     
					       $('#talukaList').empty();
					$("#talukaList").append(
					           $('<option></option>').val("").html("--Select Taluka--")
					     );
				
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
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
						$.each(element, function(val) {
						  var nameAndIDArr = element[val].split("#");
						  $("#districtList").append(
						           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
						     );
					 	}); 
					}
				}
			}
		});
		
		
		
			}



function populateAlternateDistrict(){
	var state = $(""+currAltUTStateId).val();
	dataString = "altStateVal="+state
	
		
		$.ajax({
			url: "CandidateAction_getAlternateDistrictList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#altDistrictList').empty();
					$("#altDistrictList").append(
					           $('<option></option>').val("").html("--Select District--")
					     );
					     
					      $('#altTalukaList').empty();
					$("#altTalukaList").append(
					           $('<option></option>').val("").html("--Select Taluka--")
					     );
				
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
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
						$.each(element, function(val) {
						  var nameAndIDArr = element[val].split("#");
						  $("#altDistrictList").append(
						           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
						     );
					 	}); 
					}
				}
			}
		});
			}

		function resetAlternateAddress()
		{
		
			$("#alternateAddressFiled1").val("");
			$("#alternateAddressFiled2").val("");
			$("#alternateAddressFiled3").val("");
			$("#alternateAddressFiled4").val("");
			$("#alternateCity").val("");
			$("#alternatePinCode").val("");
			$("#alternateTaluka").val("");
			//$("#altCountryId").val("");
			$("#otherAlternateStateField").val("");
			$("#otherAlternateDistrictField").val("");
			$("#altUtList").val("");
			$("#otherAlternateDistrictField").val("");
		
			//$('#altStateList').empty();
			//$("#altStateList").append(
			//           $('<option></option>').val("").html("--Select State--")
			//     );
			
			$('#altDistrictList').empty();
			$("#altDistrictList").append(
			           $('<option></option>').val("").html("--Select District--")
			     );
		}

		function copyPermenantAddress(){
		
			
			
			if ($('#addressChkBox').attr('checked')){
		
				$(".communicationAddress").each(function() {
					$(this).hide();
				});
				
			}else{
				
				$(".communicationAddress").each(function() {
					if($(this).hasClass('otherField')==false)
					{
						$(this).show();
					}
				});
			}
		}

			function callFunc() {
				var nationality = $("#nationality").val();
			}


			
			 function enableDisableTextField()
			 {
				 
				 var categoryVal = $("#categoryVal").val();
				 if (categoryVal == '5')
				 {
		 		 	$("#category").attr ('disabled',false);
					$("#category").val("");
	 			 }
	 			 else
	 			 {
	 			 	$("#category").attr ('disabled',true);
					$("#category").focus();
	 			 }
			}
			
			
function changeAction(){
	$("#applicationFormForEdit").attr('action',"CandidateAction_getCandidateDetailsForAdminForEdit.action");
	$("#applicationFormForEdit").submit();
}
function enableAllFields() {

$(".fieldDisable").each(function(currIndex, currElement) {
		$(currElement).removeAttr('disabled');
	});
	
	
	var talukaField =$("#talukaField").val(); 
	
	if (talukaField == '' || talukaField ==""){
		$("#talukaList").removeAttr("disabled");
		$("#talukaField").attr("disabled",true);
	}else{
		$("#talukaList").attr("disabled",true);
		$("#talukaField").removeAttr("disabled");
	}


	var altTalukaField =$("#altTalukaField").val();

	if (altTalukaField == '' || altTalukaField ==""){
		$("#altTalukaList").removeAttr("disabled");
		$("#altTalukaField").attr("disabled",true);
	}else{
		$("#altTalukaList").attr("disabled",true);
		$("#altTalukaField").removeAttr("disabled");
	}

enableDisableTextField();
disableGraduationMarks();
disableSpiCpiFields();
}





	function uploadImage(){
	$("#errorMessagesDiv2").hide();
	var msg = validateInputForImage();
	
	if (msg){
		setUserIdForUploadImage ();
		$("#frmImageUpload").ajaxForm({target: '#divImageUploadResponse'}).submit();
			ajaxFileUpload("attachmentPhoto");
		}
	}
	
	function ajaxFileUpload(fileToUpload){
		$("#frmImageUpload").ajaxForm({	beforeSend: function() {
			$('#block9').show();
    	},
			target: '#divImageUploadResponse',
			
			complete: function() {
    		$('#block9').hide();
			}
		}).submit();
	}
	
	function checkForImageChange(){
		try
		{
			var responseDiv = $.trim($("#divImageUploadResponse").html());
			if(responseDiv != "")
			{
				responseDiv = responseDiv.replace("<PRE>", "").replace("</PRE>","");
				responseDiv = responseDiv.replace("<pre>", "").replace("</pre>","");
				responseDiv = $.trim(responseDiv);
				responseDiv = responseDiv.substring(responseDiv.indexOf("(message)")+9, responseDiv.indexOf("(/message)"));
				//alert(responseDiv);
				$("#divImageUploadResponse").html("");
				//alert(responseDiv);
				var responseArr = responseDiv.split(",");
				if(responseArr[0]=="0")
				{
					//alert('2');
					timestamp = new Date().getTime();
					
					var imageHtml = '<img width="110" height="150" src="PhotoImage.jpg?t=' + timestamp + '&userFk=' + $.trim($("#test").val()) + '" id="id"/>';
					$("#imageTd").html(imageHtml);
					$("#imageTd").fadeIn(1000);
					//$("#inputStreamForImage").removeAttr("src").attr("src", "PhotoImage.jpg");
					$("#uploadedImage").show();
					//alert('3');
				}
				else if(responseArr[0]=="1")
				{
					//alert(responseArr[1]);
					$("#test").show();
				}
			}
		}
		catch(ex)
		{
		
		}
		setTimeout("checkForImageChange();",2000);
	}
	

function validateUserID(){
	var message = "";
	
	var userId = $("#userId").val();

	if (userId ==""){
		message = message + "Please enter Candidate Id."+"##";
	}else{
		$("#errorMessagesDiv").hide();
	}
	
	if(message != ""){
			$("#showHide").hide();
			var ulID = "errorMessages";
			var divID = "errorMessagesDiv";
			createErrorList(message, ulID, divID); 
			return false;
		}
		else{
			return true;
		}
}


	function validateInputForImage(){
	var message = "";
	
	var attachmentPhoto = $("#attachmentPhoto").val();

	if (attachmentPhoto ==""){
		message = message + "Please select a Photo for uploading "+"##";
	}else{
		$("#errorMessagesDiv").hide();
	}
	if(message != ""){
			var ulID = "errorMessages2";
			var divID = "errorMessagesDiv2";
			createErrorList(message, ulID, divID); 
			return false;
		}
		else{
			return true;
		}
	}		 

function changeActionForCandidateDetails() {
	var msg = validateAppForm();
$('#block9').hide();
	if (msg){
		$("#userPk").val($("#test").val());
		$("#applicationFormForEditDetails").attr('action',"CandidateAction_saveCandidateDetailsForAdmin.action");
		$("#applicationFormForEditDetails").submit();
	}
}
function showPopUp()
{
	var msg = validateAppForm();
	if(msg)
	{
		$('html, body').animate({ scrollTop: 0 }, 0);
		ShowPop('pop9');
	}
}
 
function validateAppForm(){

				var ulID = "error-ulAppForm";
				var divID = "error-massageAppForm";
				var categoryVal = "";
				
				var taluka = $("#talukaList").val();
				
				var altTaluka = $("#altTalukaList").val();
				
				var UserName = $("#candidateNAme").val();
				
				var dob = $("#dateOfBirth").val();
				
				var nationality = $("#nationality").val();
				
				var gender = $("#gender").val();
				
				var addLine1 = $("#addressFiled1").val();
				
				var addLine2 = $("#addressFiled2").val();
				
				var addLine3 = $("#addressFiled3").val();
				
				var addLine4 = $("#addressFiled4").val();
				
				var country = $("#countryId").val();
				
				var state = $("#stateList").val();
				
				var unionTerr = $("#utList").val();
				
				var district = $("#districtList").val();
				
				var city = $("#cityName").val();
				
				var pincode = $("#pinCode").val();
				
				var sameAddChkBox = $("#addressChkBox").is(':checked');
				//alert(sameAddChkBox);

				var altAddLine1 = $("#alternateAddressFiled1").val();
				
				var altAddLine2 = $("#alternateAddressFiled2").val();
				
				var altAddLine3 = $("#alternateAddressFiled3").val();
				var altAddLine4 = $("#alternateAddressFiled4").val();
				
				var altCountry = $("#altCountryId").val();
				
				var altState = $("#altStateList").val();
				
				var altUnionTerr = $("#altUtList").val();
				
				var altDistrict = $("#altDistrictList").val();
				
				var altCity = $("#alternateCity").val();
				
				var altPincode = $("#alternatePinCode").val();

				var email = $("#email").val();
				
				var mobileNo = $("#mobileNo").val();
				
				var countryCode = $("#conutryCode").val();
				
				var stdCode = $("#stdCode").val();
				
				var phone = $("#phoneNo").val();
				
				
				
				var categoryIssuer = $("#category").val();

				var handicappedPer = $("#handicappedPercentage").val();
				var talukaField = $("#talukaField").val();
				var altTalukaField = $("#altTalukaField").val();
				var altTalukaList = $("#altTalukaList").val();

				var message = "";
				
				
				
				
				
				
				
				

				if(UserName == "")
					message = "Please enter your Name."+"##";
				if(dob == "")
					message = message + "Please select Date of Birth."+"##";
				if(nationality == "")
					message = message + "Please select Nationality."+"##";
				if(gender == "")
					message = message + "Please select Gender."+"##";
				if(addLine1 == "")
					message = message + "Please enter atleast First Permanent Address line."+"##";
				if(country == "")
					message = message + "Please select Permanent Address Country."+"##";
				if(state == "" && unionTerr == "")
					message = message + "Please select Permanent Address State/Union Territory."+"##";
				if(district == "")
					message = message + "Please select Permanent Address District."+"##";
					
					if (talukaField =="" && taluka ==""){
					message = message + "Please enter Permanent Taluka Information."+"##";			
				}
				
				if(city == "")
					message = message + "Please enter Permanent Address City/Town name."+"##";
				if(pincode == "")
					message = message + "Please enter Permanent Address Pincode."+"##";					
				if(pincode.length < 6)
					message = message + "Permanent Address Pincode must be alteast 6 digits."+"##";
				
				if(!$("#addressChkBox").is(':checked')){
					
					if(altAddLine1 == "")
						message = message + "Please enter atleast First Communication Address line."+"##";
					if(altCountry == "")
						message = message + "Please select Communication Country."+"##";
					if(altState == "" && altUnionTerr == "")
						message = message + "Please select Communication State/Union Territory."+"##";
					if(altDistrict == "")
						message = message + "Please select Communication District."+"##";
						//alert ("caklled");
					if (altTalukaList =="" && altTalukaField ==""){
					message = message + "Please enter Communication Taluka Information."+"##";	
				}
					if(altCity == "")
						message = message + "Please enter Communication City/Town name."+"##";
						
					if(altPincode == "")
						message = message + "Please enter Communication Pincode."+"##";
					if(altPincode.length < 6)
						message = message + "Communication Address Pincode must be alteast 6 digits."+"##";
				}
				if(email == ""){
					message = message + "Please enter Email address."+"##";
					
				}
				
				if(email != ""){
					var validEmail = /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i.test(email);
					if(validEmail == false)
						message = message + "Please enter correct Email address"+"##";
				}

				if(mobileNo == ""){
						message = message + "Please Enter 10 digit Mobile No."+"##";
				}
				if(mobileNo != ""){
					if(mobileNo.length < 10)
						message = message + "Mobile No. must be 10 digit long"+"##";
					if(mobileNo.charAt(0) == "0")
						message = message + "Mobile No. cannot be start with zero"+"##";
				}

				if (taluka == '' ||taluka == ""){
						//message = message + "Please Select Permanent  Address Taluka ."+"##";
					}
				if(handicappedPer != ""){
					if(handicappedPer.length < 2 && handicappedPer.length > 3)
						message = message + "Percentage of Disablity must be more than 40%"+"##";
					if(handicappedPer <= 40)
						message = message + "Percentage of Disablity must be more than 40%"+"##";
					if(handicappedPer.charAt(0) == "0")
						message = message + "Percentage of Disablity cannot be start with zero"+"##";
				}

				if(countryCode == "" || stdCode == "" || phone == ""){
					message = message + "Please enter valid  Phone No."+"##";
				}
				if(countryCode != "" || stdCode != "" || phone != ""){
					if(countryCode == "")
						message = message + "Please enter valid Country code"+"##";
					if(stdCode == "")
						message = message + "Please enter valid STD code"+"##";
					if(phone == "")
						message = message + "Please enter valid  Phone No."+"##";
					
				}

				if($('#handicappedYes').is(':checked')){
					if(handicappedPer == "")
						message = message + "Please enter Percentage of Disability."+"##";

					if(handicappedPer == "0")
					{
						message = message + "Percentage of Disability should be greater than zero."+"##";
					}else
					{
						if (!isValidPercentage(handicappedPer)) {
							message = message + "Please enter valid Percentage of Disability ."+"##";
						
						}
					}
				}
				var taluka = $("#taluka").val();
				
				var academic = $("#academic").val();
				
				var otherDegree = $("#degreeOther").val();
				
				var yearIdSSC = $("#yearIdSSC").val();
				
				var sscUniversity =  $("#sscUniversity").val();
				
				var sscObtainMarks = $("#sscObtainMarks").val();
				
				var sscMaxMarks =  $("#sscMaxMarks").val();
				
				var sscPercentage = $("#sscPercentage").val();
				
				var yearIdHSC = $("#yearIdHSC").val();
				
				var hscUniversity = $("#hscUniversity").val();
				
				var hscObtainedMarks = $("#hscObtainedMarks").val();
				
				var hscMaxMarks = $("#hscMaxMarks").val();
				
				var hscPercentage = $("#hscPercentage").val();
				
				var yearIdBE = $("#yearIdBE").val();
				
				var beUniversity = $("#beUniversity").val();
				
				var beObtainedMarks = $("#beObtainedMarks").val();
				
				var beMaxMarks = $("#beMaxMarks").val();
				
				var bePercentage = $("#bePercentage").val();
				
				var beSPI = $("#beSPI").val();
				
				var beCPI = $("#beCPI").val();
				
				var baseSpiCpiInfo = $("#baseSpiCpiInfo").val();
				
				var isCheckedVal = false;
				$(".degreeTypeClass").each(function(){
					if($(this).is(':checked'))
					{
						isCheckedVal = true;
					}
				});
				
				

				if(isCheckedVal == false)
				{
					message = message + "Please select your Bachelor Degree Type."+"##";
				}
				//if(academic == "")
					//message = message + "Please select Bachelor Degree."+"##";

				if(academic == "Other" || academic == "other")
					if(otherDegree == "")
						message = message + "Please enter your Degree."+"##";

				isCheckedVal = false;
				var resultStatus;
				$(".resultOfGraduationClass").each(function(){
					if($(this).is(':checked'))
					{
						isCheckedVal = true;
						 var id = $(this).attr("id");
						 resultStatus = $('label[for='+id+']').text();
						
					}
				});
				if(isCheckedVal == false)
				{
					message = message + "Please select Result Status."+"##";
				}

				isCheckedVal = false;
				$(".graduationFromUnivClass").each(function(){
					if($(this).is(':checked'))
					{
						isCheckedVal = true;
					}
				});

				if(isCheckedVal == false)
				{
					message = message +" Please mention Graduation University."+"##";
				}

				isCheckedVal = false;
				$(".schoolLocationClass").each(function(){
					if($(this).is(':checked'))
					{
						isCheckedVal = true;
					}
				});

				if(isCheckedVal == false)
				{
					message = message + "Please mention HSC/12th/DIPLOMA Institute."+"##";
				}

				isCheckedVal = false;
				$(".kashmirMigrantClass").each(function(){
					if($(this).is(':checked'))
					{
						isCheckedVal = true;
					}
				});
				if(isCheckedVal == false)
				{
					message = message + "Are your marks based on SPI/CPI? Please specify."+"##";
				}
				
				isCheckedVal = false;
				
				var categoryother = $("#category").val();

 				var categoryval = $("#categoryVal").val();
				
				if(categoryval == "" || categoryval == '')
				{
					message = message + "Please select Category."+"##";
				}
				
				if(categoryval == "5" || categoryval == '5')
				{
					if(categoryother == "" ||categoryother == '')
					{
						message = message + "Please enter name of other category"+"##";
					}
				}
				
				
				
				
				if (taluka == '' ||taluka == ""){
					message = message + "Please enter Permanent  Address Taluka ."+"##";
				}
				
				if(yearIdSSC == "")
					message = message + "Please select SSC Passing Year."+"##";

				if(sscUniversity == "")
					message = message + "Please select SSC Institute/University."+"##";

				if(sscObtainMarks == "")
					message = message + "Please enter SSC Obtained Marks."+"##";

				if(sscMaxMarks == "")
					message = message + "Please enter SSC Maximum Marks."+"##";

				if(parseInt(sscObtainMarks) > parseInt(sscMaxMarks)){
					message = message + "SSC Obtained Marks cannot be greater than SSC Maximum Marks."+"##";
				}

				if(sscPercentage == ""){
					message = message + "Please enter SSC Percentage."+"##";
				}else{
				if (!isValidPercentage(sscPercentage)) {
						message = message + "Please enter valid SSC Percentage."+"##";
					
					}
				}
					

				if(yearIdHSC == "")
					message = message + "Please select HSC/DIPLOMA Passing Year."+"##";

				if(yearIdSSC > yearIdHSC)
					message = message + "HSC/DIPLOMA passing year should be greater than SSC passing year."+"##";
				
				if(hscUniversity == "")
					message = message + "Please select HSC/DIPLOMA Institute/University."+"##";

				if(hscObtainedMarks == "")
					message = message + "Please enter HSC/DIPLOMA Obtained Marks."+"##";
				
				if(hscObtainedMarks == "0")
					message = message + "HSC/DIPLOMA Obtained Marks cannot be Zero."+"##";	

				if(hscMaxMarks == "")
					message = message + "Please enter HSC/DIPLOMA Maximum Marks."+"##";
					
				if(hscMaxMarks == "")
					message = message + "HSC/DIPLOMA Maximum Marks cannot be Zero."+"##";

				if(parseInt(hscObtainedMarks) > parseInt(hscMaxMarks)){
					message = message + "HSC/DIPLOMA Obtained Marks cannot be greater than HSC/DIPLOMA Maximum Marks."+"##";
				}

				if(hscPercentage == ""){
					message = message + "Please enter HSC/DIPLOMA Percentage."+"##";
				}else
				{
					if (!isValidPercentage(hscPercentage)) {
						message = message + "Please enter valid HSC/DIPLOMA Percentage."+"##";
					
					}
				}

				if(yearIdBE == "")
					message = message + "Please select Graduation Passing Year."+"##";

				if(yearIdHSC > yearIdBE)
					message = message + "Graduation Passing Year should be greater than HSC/DIPLOMA Passing Year."+"##";

				if(beUniversity == "")
					message = message + "Please select Graduation Institute/University."+"##";
					
				if(resultStatus != "Awaited"){
				if(beObtainedMarks == "" && beMaxMarks == "" && bePercentage == "" && beSPI == "" && beCPI == "")
					message = message + "Please enter Graduation Marks or SPI/CPI details."+"##";

				if(beObtainedMarks == "" && beMaxMarks == "" && bePercentage == ""){
					if(beSPI == "")
						message = message + "Please enter SPI."+"##";
					if(beCPI == "")
						message = message + "Please enter CPI."+"##";
				}
				else{
					if(beObtainedMarks == "")
						message = message + "Please enter Graduation Obtained Marks."+"##";
					if(beMaxMarks == "")
						message = message + "Please enter Graduation Maximum Marks."+"##";
						
					if(beObtainedMarks == "0")
							message = message + "Graduation Obtained Marks cannot be Zero."+"##";
					if(beMaxMarks == "0")
							message = message + "Graduation Maximum Marks cannot be Zero."+"##";
								
						
					if(bePercentage == ""){
						message = message + "Please enter Graduation Percentage."+"##";
					}else
					{
						if (!isValidPercentage(bePercentage)) {
							message = message + "Please enter valid Graduation Percentage."+"##";
						
						}
					}
					if(parseInt(beObtainedMarks) > parseInt(beMaxMarks)){
						message = message + "Graduation Obtained Marks cannot be greater than Graduation Maximum Marks."+"##";
					}
				}
				if(beSPI != 0 && beCPI !=0)
					if(baseSpiCpiInfo == "")
						message = message + "Please select SPI/CPI base information."+"##";

					}
				if(message != ""){
					createErrorList(message, ulID, divID); 
					$("#error-massageAppForm").focus();
					$('html, body').animate({ scrollTop: 0 }, 0);
					//$('html, body').animate({ scrollTop: 0 }, 'slow');
					return false;
				}else{
				
					$("#error-massageAppForm").hide();
					/*var r=confirm("Are you sure?");
					if (r==true)
					{
					  return true;
					}
					else
					{
					  return false;
					}*/
					  return true;
				}
}


	
			function disableGraduationMarks(){
				
				$(".resultOfGraduationClass").each(function(){
					if($(this).is(':checked'))
					{
						//isCheckedVal = true;
						 var id = $(this).attr("id");
						 resultStatus = $('label[for='+id+']').text();
						 //alert('inside    ' + resultStatus);
						if(resultStatus == "Awaited"){
							$("#beObtainedMarks").val("");
							$("#beMaxMarks").val("");
							$("#bePercentage").val("");
							$("#beSPI").val("");
							$("#beCPI").val("");
							$("#beObtainedMarks").attr("disabled", true);
							$("#beMaxMarks").attr("disabled", true);
							$("#bePercentage").attr("disabled", true);
							$("#beSPI").attr("disabled", true);
							$("#beCPI").attr("disabled", true);
							$("#baseSpiCpiInfo").attr("disabled", true);
						}
						if(resultStatus == "Declared"){
							
							$("#beSPI").removeAttr("disabled");
							$("#beCPI").removeAttr("disabled");
							$("#baseSpiCpiInfo").removeAttr("disabled");
							$("#beObtainedMarks").removeAttr("disabled");
							$("#beMaxMarks").removeAttr("disabled");
							$("#bePercentage").removeAttr("disabled");

							disableSpiCpiFields();
						}
					}
				});
			}
			
			function disableSpiCpiFields(){
				var resultStatus = "";
				var status = "";




				$(".resultOfGraduationClass").each(function(){
					if($(this).is(':checked'))
					{
						//isCheckedVal = true;
						 var id = $(this).attr("id");
						 resultStatus = $('label[for='+id+']').text();
						 //alert('inside    ' + resultStatus);
					}
				});






				if (resultStatus == "Declared"){
				$(".kashmirMigrantClass").each(function(){
					if($(this).is(':checked'))
					{
						//isCheckedVal = true;
						 var id = $(this).attr("id");
						 status = $('label[for='+id+']').text();
						 //alert('inside    ' + resultStatus);
						if(status == "No"){
							$("#beSPI").val("");
							$("#beCPI").val("");
							$("#beSPI").attr("disabled", true);
							$("#beCPI").attr("disabled", true);
							$("#baseSpiCpiInfo").attr("disabled", true);
							$("#beObtainedMarks").removeAttr("disabled");
							$("#beMaxMarks").removeAttr("disabled");
							$("#bePercentage").removeAttr("disabled");
							
						}
						if(status == "Yes"){
								$("#beSPI").removeAttr("disabled");
								$("#beCPI").removeAttr("disabled");
								$("#baseSpiCpiInfo").removeAttr("disabled");
								$("#beObtainedMarks").attr("disabled", true);
								$("#beMaxMarks").attr("disabled", true);
								$("#bePercentage").attr("disabled",true);
								$("#beObtainedMarks").val("");
								$("#beMaxMarks").val("");
								$("#bePercentage").val("");
						}
					}
				});
			}
			}
			
			
function clearFields(){
	$("#userId").val("");
	$("#errorMessagesDiv").hide();
	$("#editBtn").hide();
	$("#errorMessagesDiv2").hide();
	$("#error-massageAppForm").hide();
	$("#block20").hide();
	$("#block10").hide();
	$("#educationalDetails").hide();
	$("#header").hide();
	$("#header2").hide();
	$("#personalDetailsDiv").hide();
	$("#imageDisplayDiv").hide();


}



function populateAlternateTaluka(){
		var taluka = $("#altDistrictList").val();
		dataString = "altDistrictVal="+taluka
	
		
		$.ajax({
			url: "CandidateAction_getAlternateTalukaList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#altTalukaList').empty();
					$("#altTalukaList").append(
					           $('<option></option>').val("").html("--Select Taluka--")
					     );
				
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
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
						$.each(element, function(val) {
						  var nameAndIDArr = element[val].split("#");
						  $("#altTalukaList").append(
						           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
						     );
					 	}); 
					}
					$("#altTalukaList").removeAttr("disabled");
					$("#altTalukaField").val("");
					$("#altTalukaField").attr("disabled",true);
				}else {
					$("#altTalukaField").removeAttr("disabled");
					$("#altTalukaList").attr("disabled",true);
				}
			}
		});
		
		
		
			}
			
			
	function populateTaluka(){
		var taluka = $("#districtList").val();
		
		dataString = "districtVal="+taluka;
	
		
		$.ajax({
			url: "CandidateAction_getTalukaList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#talukaList').empty();
					$("#talukaList").append(
					           $('<option></option>').val("").html("--Select Taluka--")
					     );
				
			},
			error:function(ajaxrequest)
			{
				alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
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
						$.each(element, function(val) {
						  var nameAndIDArr = element[val].split("#");
						  $("#talukaList").append(
						           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
						     );
					 	}); 
					}
					$("#talukaList").removeAttr("disabled");
					$("#talukaField").val("");
					$("#talukaField").attr("disabled",true);
				}else{
						$("#talukaField").removeAttr("disabled");
						$("#talukaList").attr("disabled",true);
				}
				
			}
		});
		
			}



</script>
    
<div class="container">

<div id="dashboard" style="display:block; min-height:300px; height:auto;">

<h1 class="pageTitle" title="View / Edit Candidate">View / Edit Candidate</h1>
<div class="hr-underline2"></div>

<s:form id="applicationFormForEdit" action="CandidateAction" onsubmit="return validateUserID();">
<div id="errorMessagesDiv" style="display:none" class="error-massage">
    <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="errorMessages">
    	</ul>
    	
    </div>
</div>


<div id="errorMessagesDiv2" style="display:none" class="error-massage">
    <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="errorMessages2">
    	</ul>
    	
    </div>
</div>


<div id="error-massageAppForm" style="display:none">
      	<div class="error-massage-text">
      	<ul style="margin:0; margin-left:20px; padding:0;" id="error-ulAppForm">
      	
      	
      	</ul>
      	
      	 </div>
      </div>


<s:hidden id='hddAddressChkBox'></s:hidden>

<div style="display:block; min-height:90px; height:auto;">
 
 <table class="contenttable">
    <tr>
	      <td width="226">
	      		User ID <span class="manadetory-fields">*</span>
	      </td>
	      
	      <td width="674" colspan="2">
	      <s:textfield name="userId" id="userId" onkeypress="return alphaNumeric(event)" ></s:textfield>
	      </td>
    </tr>
    
    <tr>
    	  <td>&nbsp; </td>
    	  
      	  <td colspan="2">
      	  <input type="button" value="Search" class="submitBtn button-gradient" onclick="changeAction()"/>&nbsp;
      	   <input type="button" value="Clear" class="submitBtn button-gradient" onclick="clearFields();"/> 
      	  </td>
      	  
      	  	<td>
      	  		<s:if test='%{flagForEdit=="true"}'>
      	  			<s:if test='%{editBtnFlag=="true"}'>
	         			<input type="button" value="Edit" class="submitBtn button-gradient" onclick="enableAllFields()" id = "editBtn"/>
		         	</s:if>
		        	</s:if>	
	         </td>
      	  
      	  <s:actionmessage/>
    </tr>
    
   
    </table>
    
     <div  id="block20" style = "display:none;" >
     <div class="hr-underline2"></div>
     <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li>No record Found for <s:property value = "userId"/>.</li> 
    	</ul>
    	
    </div>
<br />
</div>
    </div>
    </s:form>
    
    <div id="showHide">
   
    <label id="divImageUploadResponse" style="display: none;"></label>
<form method="post" name="register" action="CandidateAction_insertCandidateImageForAdmin.action" enctype="multipart/form-data" id="frmImageUpload" >
<input type="hidden" name = "userIdForImage" value="<s:property value = "userIdForImage"/>" id = "test"/>
	<div style="display:block; min-height:300px; height:auto;" id = "imageDisplayDiv">
	
		    		<s:if test='%{flagForEdit=="true"}'>
		    		<div class="hr-underline2"></div>
			<h1 class="pageTitle" title="Dashboard">Candidate Image</h1>
		
		    		<div class="hr-underline2"></div>
						  <table class="contenttable">
						  		<tr>
						  		<td>
						  			<div id = 'block9' style="display: none; padding-left:350px;">
  	<img src="images/Loading.gif" width="66" height="78" alt="GCET " />
  	</div>
						  		</td>
						  		</tr>
							  <tr>
							  			<td width="286"></td>
							  			<td id='imageTd' >
							  				<img src="" id = "id" width="110" height="150"></img>
							  				
							  			</td>
							  			
							  </tr>
							  
							  <tr>
								      <td>&nbsp;</td>
								      <td>
								      		<s:file name="attachmentPhoto" id="attachmentPhoto" cssClass="fieldDisable" ></s:file>
								      </td>
							  </tr>
							   
       
							  <tr><td ></td>
							      <td>
							      		<span class="lighttext">After selecting your photo, please click on </span>
							      		<input type="button" title="Upload" class="submitBtn button-gradient fieldDisable disabled" value="Upload" onclick="uploadImage();" />
								      </td>
							  </tr>
							  <tr id = "test" style="color:#F00;" >
							  <td></td></td>
	      							<td >
	      								The  Maximum File Size is <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_IMAGE_SIZE_IN_KB)%>KB. 
	      							</td>
      						</tr>
      						<tr>
    	<td>&nbsp;</td>
    	<td style="color:#F00;" >
    		Please upload recent photograph, not older than six months
    	</td>
    </tr>
						  </table>
		  </s:if>
	</div>
</form>

    
    
    <s:form id="applicationFormForEditDetails" action="CandidateAction">
    <input type="hidden" name = "userIdForImage" value="<s:property value = "userIdForImage"/>" id = "test"/>
    <s:hidden name = "successFlag" id = "successFlag"></s:hidden>
    <s:if test='%{flagForEdit=="true"}'>
    
        <div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Dashboard">Discipline</h1></div>
<div class="hr-underline2"></div>
	<table class="contenttable">
		<tr>
					    <td width="286"> Select Discipline <span class="manadetory-fields">*</span></td>
					    <td colspan="2">
					    	<s:select list = "discliplineList" name = "disciplineType" label = "Name" 
							headerKey="" headerValue = "Select Discipline" id = "disciplineType" value="%{disciplineType}" cssClass="fieldDisable"/>
						</td>
		 </tr>
	</table><br/>
    		<div style="display:block; min-height:300px; height:auto;" id = "personalDetailsDiv">
    		<h1 class="pageTitle" title="Dashboard">Personal Details</h1>
    		<div class="hr-underline2"></div>
  <table class="contenttable">
	    <tr>
		      <td width="286">Name <span class="manadetory-fields">*</span>
		       </td>
	    	
	    	  <td colspan="2">
	      		<s:textfield name="personalDetailsBean.candidateName" id = "candidateNAme" cssClass="fieldDisable" onkeypress = "return nameValidator(event);"></s:textfield>
	        	<span class="lighttext">(Maximum 80 Characters)</span>
	         </td>
	    </tr>
	    
    <tr>
	      <td>Date of Birth <span class="manadetory-fields">*</span> <br/>
	      <span class="lighttext">Age should be equal or greater than <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DATE_OF_BIRTH_YEAR)%> years</span>
	      </td>
	      
	      <td colspan="2">
		      <s:textfield id="dateOfBirth" name="personalDetailsBean.dateOfBirth" readonly="true" cssClass="fieldDisable"> </s:textfield>
		      <span class="lighttext">Please click on calender to select Date of Birth.</span>
	      </td>
    </tr>
    
    <tr>
    	  <td>Nationality</td>
      	
      	<td colspan="2">
      			<s:select list = "nationalityList" name = "nationality" label = "Name" 
				headerKey="" headerValue = "Select Nationality" id = "nationality" value="%{nationality}" cssClass="fieldDisable"
				/>	
	  	</td>
    </tr>
    
    <tr>
	      <td>Gender</td>
	    
	      <td colspan="2">
					<s:select list = "genderList" name = "genderVal" label = "Name" 
					headerKey="" headerValue = "Select Gender" id = "gender" value="%{genderVal}" cssClass="fieldDisable"
					/>		
		  </td>
    </tr>
    
    <tr>
    
	      <td>Permanent Address <span class="manadetory-fields">*</span></td>
	      
	      <td colspan="2">
	      		<s:textfield id="addressFiled1" name="addressBean.addressFiled1" cssClass="fieldDisable"></s:textfield>
	      </td>
    </tr>
    
    <tr>
	      <td>&nbsp;</td>
	      
	      <td colspan="2">
	      		<s:textfield id="addressFiled2" name="addressBean.addressFiled2" cssClass="fieldDisable"></s:textfield>
	      </td>
    </tr>
    
    <tr>
	      <td>&nbsp;</td>
	      
	      <td colspan="2">
	      		<s:textfield id="addressFiled3" name="addressBean.addressFiled3" cssClass="fieldDisable"></s:textfield>
	      </td>
    </tr>
    
    <tr>
	      <td>&nbsp;</td>
	     
	      <td colspan="2">
	      		<s:textfield id="addressFiled4" name="addressBean.addressFiled4" cssClass="fieldDisable"></s:textfield>
	      </td>
    </tr>
    
    <tr>
	      <td>&nbsp;</td>
	      
	      <td colspan="2"><span class="lighttext">Country</span><br />
		       <s:select list = "countryList" name = "countryVal" label = "Name" cssClass="fieldDisable"
						headerKey="" headerValue = "Select Country" id = "countryId" value="%{countryVal}" onchange="populateState();"
						/>		
	       </td>
    </tr>
    
<!-- For other countries -->    
    <tr style="display:none;" id="otherStateVal">
    	<td>
    		&nbsp;
    	</td>
    	 
    	<td>
    		<span class="lighttext">State</span><br />
	    	<s:textfield name="addressBean.otherStateFiled" id = "otherStateFiled" cssClass="fieldDisable"></s:textfield>
    	</td>
    	
    
    </tr>
    
    <tr id = "stateDropdown">
	      <td>&nbsp;</td>
	      
	      <td>
	      		<span class="lighttext">State</span><br />
				
				<s:select list="stateList" id="stateList" name="stateVal" onchange="populateDistrictForState();" cssClass="fieldDisable" headerKey="" headerValue="--Select State--" ></s:select>
	        	&nbsp; /
	      </td>
	        
	        
	        
	      <td>
		      	<span class="lighttext">Union Territory
		        </span><br />
		       
		        <s:select list="utList" id="utList" name="unionTerritoryVal" cssClass="fieldDisable"
              		onchange="populateDistrictForUT();" headerKey="" headerValue="--Select Union Territory--" ></s:select>
	     </td>
    </tr>
    
    <tr id="otherDistrict" style="display:none;">
    	<td>
    		&nbsp;
    	</td>
    	
    	<td>
    		<span class="lighttext">District</span><br />
    		<s:textfield name="addressBean.otherDistrictField" id="otherDistrictField" cssClass="fieldDisable"></s:textfield>
    	</td>
    </tr>
    
    
    <tr id="districtId">
		 <td>&nbsp;</td>
		 <td colspan="2"><span class="lighttext">District</span><br />
				
				
			<s:select list="districtList" id="districtList" name="districtVal" headerKey="" headerValue="--Select District--"  cssClass="fieldDisable" onchange="populateTaluka()"></s:select>
		</td>
    </tr>
     <tr>
    <td></td>
        <td width="371"><span class="lighttext">Taluka</span> <span class="manadetory-fields">*</span><br />      
	       	 <!--<s:textfield id="taluka" name="addressBean.taluka" cssClass="s" maxlength="50" ></s:textfield>-->
	       	 <s:select list="talukaList" id="talukaList" name="talukaVal"  headerKey="" headerValue="--Select Taluka--"  value="%{talukaVal}" cssClass="fieldDisable"></s:select>
	      </td>
	      
	       
	      <td width="371"><span class="lighttext">Taluka</span> <span class="manadetory-fields">*</span><br />      
	       	<s:textfield id="talukaField" name="talukaField" cssClass="s fieldDisable" maxlength="50" ></s:textfield>
	       	<span class="lighttext">Please enter 'NA' if Taluka not applicable.</span>
	       	</td>
	 </tr>
    
    <tr>
	      <td>&nbsp;</td>
	    
	      <td width="227"><span class="lighttext">City / Town</span> <span class="manadetory-fields">*</span>
	       		 <br />
	        	<s:textfield id="cityName" name="addressBean.cityName" cssClass="fieldDisable"></s:textfield>
	       </td>
	       
	      <td width="371"><span class="lighttext">Pincode</span> <span class="manadetory-fields">*</span><br />      
	       	 <s:textfield id="pinCode" cssClass="s fieldDisable"   name="addressBean.pinCode" maxlength="6" onkeypress="return numbersonly(event);"></s:textfield>
	      </td>
	      
	      
	      
	      
	      
    </tr>
    
    <tr>
	      <td >Communication Address <span class="manadetory-fields">* </span><br/>
		      
	      </td>
	      
      	  <td colspan="2" >
      	  		
      			<s:checkbox name="addressChkBox" id = "addressChkBox" onclick="copyPermenantAddress();resetAlternateAddress();" cssClass="fieldDisable"/>
		      	<span class="lighttext">Same as Permanent Address</span>
      	  </td>
      		
    </tr>
    
    <tr class='communicationAddress'>
	      <td >&nbsp;
		      
	      </td>
	      
      	   <td colspan="2" >
      			<s:textfield id="alternateAddressFiled1" name="addressBean.alternateAddressFiled1"  cssClass="fieldDisable"></s:textfield>
      		</td>
      		
    </tr>
    
    <tr class='communicationAddress'>
    	<td >&nbsp;
		      
	      </td>
    	  <td colspan="2">
      		  <s:textfield id="alternateAddressFiled2" name="addressBean.alternateAddressFiled2" cssClass="fieldDisable"></s:textfield>
      	  </td>
    </tr>
    
    <tr class='communicationAddress'>
	      <td>&nbsp;</td>
	      <td colspan="2">
	      		<s:textfield id="alternateAddressFiled3"  name="addressBean.alternateAddressFiled3" cssClass="fieldDisable"></s:textfield>
	      </td>
    </tr>
    
    <tr class='communicationAddress'>
	      <td>&nbsp;</td>
	      <td colspan="2">
	    		 <s:textfield id="alternateAddressFiled4"  name="addressBean.alternateAddressFiled4" cssClass="fieldDisable"></s:textfield>
	      </td>
    </tr>
    
    <tr class='communicationAddress'>
	      <td>&nbsp;</td>

	     <td colspan="2"><span class="lighttext">Country</span><br />
	    	    		<s:select list = "countryList" name = "altCountryVal" label = "Name"  cssClass="fieldDisable"
						headerKey="" headerValue = "--Select Country--" id = "altCountryId" value="%{altCountryVal}" onchange="populateAlternateState();"
					/>		
		</td>
    </tr>
    
    <tr id = "alternateStateFieldDisplay" style="display:none;" class='communicationAddress otherField'>
    	<td>
    		&nbsp;
    	</td>
    	<td>
    		<span class="lighttext">State</span><br />
    		<s:textfield name="addressBean.otherAlternateStateField" id="otherAlternateStateField" cssClass="fieldDisable"></s:textfield>
    	</td>
    
    </tr>
    
    
    
    <tr id = "alternateStateDisplay" class='communicationAddress'>
     	 <td>&nbsp;</td>
      	
      	<td>
      		<span class="lighttext">State</span><br />
        	
			<s:select list="altStateList" id="altStateList"  cssClass="fieldDisable" name="altStateVal" onchange="populateAlternateDistrictForState();" headerKey="" headerValue="--Select State--" ></s:select>
        	&nbsp; /
        </td>
        
      <td>
      		<span class="lighttext">Union Territory </span><br />
            <s:select list="altUtList" id="altUtList" name="altUnionTerritoryVal"  cssClass="fieldDisable"
              	onchange="populateAlternateDistrictForUT();" headerKey="" headerValue="--Select Union Territory--" ></s:select>
       </td>
    </tr>
    
    <tr id = "alternateDistrictFieldDisplay" style="display:none;" class='communicationAddress otherField'>
    	<td>
    		&nbsp;
    	</td>
    	<td>
    		<span class="lighttext">District</span><br />
    		<s:textfield name="addressBean.otherAlternateDistrictField" id="otherAlternateDistrictField"  cssClass="fieldDisable"></s:textfield>
    	</td>
    
    </tr>
    
    <tr id = "alternateDistrictDisplay" class='communicationAddress'>
     
      <td>&nbsp;</td>
      <td colspan="2"><span class="lighttext">District</span><br />
			<s:select list="altDistrictList" id="altDistrictList" name="altDistrictVal" headerKey="" headerValue="--Select District--"  cssClass="fieldDisable" onchange = "populateAlternateTaluka()"></s:select>
	 </td>
	 
    </tr>
    
     <tr class='communicationAddress'>
     <td></td>
	      <td ><span class="lighttext">Taluka</span> <span class="manadetory-fields">*</span><br />      
	       	<!--   <s:textfield id="taluka" name="addressBean.alternateTaluka" cssClass="s" maxlength="50" id = "alternateTaluka"></s:textfield>-->
	       	<s:select list="altTalukaList" id="altTalukaList" name="altTalukaVal"  headerKey="" headerValue="--Select Taluka--" value="%{altTalukaVal}" cssClass="fieldDisable"></s:select>
	      </td>
	      
	       <td width="371"><span class="lighttext">Taluka</span> <span class="manadetory-fields">*</span><br />      
	       	<s:textfield id="altTalukaField" name="altTalukaField" cssClass="s fieldDisable" maxlength="50" ></s:textfield>
	       	<span class="lighttext">Please enter 'NA' if Taluka not applicable.</span>
	       	</td>
    </tr>
    
    <tr class='communicationAddress'>
	      <td>&nbsp;</td>
	      
	      <td><span class="lighttext">City / Town</span> <span class="manadetory-fields">*</span> <br />
	        	<s:textfield id="alternateCity" name="addressBean.alternateCity"  cssClass="fieldDisable"></s:textfield>
	       </td>
	      
	      <td>
	      		<span class="lighttext">Pincode</span> <span class="manadetory-fields">*</span><br />
	        	<s:textfield id="alternatePinCode" name="addressBean.alternatePinCode" maxlength="6" onkeypress="return numbersonly(event);" cssClass="fieldDisable"></s:textfield>
	      </td>
	      
    </tr>
    
    
    <tr>
	      <td>Email Id<span class="manadetory-fields"> *</span></td>
	      
	      <td colspan="2">
	     		<s:textfield id="email" name="personalDetailsBean.email" cssClass="fieldDisable"></s:textfield>
	      </td>
    </tr>
    
    <!--<tr>
    	  <td><span class="lighttext">Either Mobile No. or Phone No. is mandatory.</span></td>
    </tr>
    --><tr>
    
	      <td>Mobile Number<span class="manadetory-fields"> *</span></td>
	      
	      <td colspan="2">
	      		<s:textfield id="mobileNo" name="personalDetailsBean.mobileNo" cssClass="fieldDisable" maxlength="10" onkeypress="return numbersonly(event);"></s:textfield>
	      		<span class="lighttext">Enter 10 digits mobile number.</span>
	      		
	      </td>
      
    </tr>
    
    <tr >
	      <td>Alternate Number</td>
	      <td colspan="2">
	      
				    <s:textfield id="conutryCode" name="personalDetailsBean.isdNo" cssClass="isd fieldDisable" onkeypress="return numbersonly(event);" maxlength="4"></s:textfield>
			        -
			        
			        <s:textfield id="stdCode" name="personalDetailsBean.stdCode" cssClass="isd fieldDisable" onkeypress="return numbersonly(event);" maxlength = "5"></s:textfield>       
			          -
			        <s:textfield id="phoneNo" name="personalDetailsBean.phoneNo" cssClass="s fieldDisable" onkeypress="return numbersonly(event);" maxlength = "10"> </s:textfield> 
	       </td>
    </tr>
    
    <tr>
    
	      <td>Category Information</td>
	      <td colspan="2">
		     <s:select list="categoryList" id="categoryVal" name="categoryVal" headerKey="" headerValue="--Select Category--" onchange="enableDisableTextField();" value="%{categoryVal}" ></s:select>
		  </td>
	  
    </tr>
    
    <tr>
	      <td>If any other Category mention here</td>
	      <td colspan="2">
	      	 <s:textfield id="category" name="personalDetailsBean.category" cssClass="fieldDisable"></s:textfield>
	     </td>
     
    </tr>
    
    <tr>
     	 <td>Are you Physically Handicapped?</td>
      
      	<td colspan="2">
      	  <s:radio list="handicappedList" cssClass="fieldDisable handicappedPercentageClass" name="personalDetailsBean.handicapped" id="handicapped" onclick="enableDisableHandicappedPercentage();"/>
     	</td>
     	
    </tr>
    
    <tr>
	      <td>&nbsp;</td>
	      <!--<td colspan="2"><span class="lighttext">Percentage of Disability (more than 40%)</span><br />
	        	<s:textfield id="handicappedPercentage" cssClass="fieldDisable" maxlength="3" name="personalDetailsBean.handicappedPercentage" onkeypress="return numbersonly(event);"></s:textfield>
	       </td>
	--></tr>
	
	
	<tr>
     	 <td>Do you belong to BPL (Below Poverty Line) card holder family? <span class="manadetory-fields"> *</span></td>
      
      	<td colspan="2">
      	  <s:radio list="povertyLineList" name="personalDetailsBean.poverty" id="poverty" cssClass="fieldDisable" />
     	</td>
     	
    </tr>
	
	
  </table>
</div><br />
<br />

<h1 class="pageTitle" title="Education Details" id = "header">Education Details</h1>
<div class="hr-underline2" id = "header2"></div>

<table class="contenttable" id = "educationalDetails">
  <tr>
	    
	    <td colspan="3">Is your bachelor's Degree under minimum of 
	    	 <s:radio list="degreeType" name="educationDetailsBean.degreeType" cssClass="fieldDisable degreeTypeClass" id="degreeType" onclick="refreshDegree($(this).val());" />
	    </td>
	    
   </tr>
   
  <tr>
   	 <td width="286">Bachelor's Degree <span class="lighttext"></span> <span class="manadetory-fields">*</span>
   	 </td>
    
     <td width="602" colspan="2">
     			<s:select list = "academicDetailsMap" name = "educationDetailsBean.degree" label = "Name" cssClass="fieldDisable" 
				headerKey="" headerValue = "--Select Degree--" id = "academic" value="%{educationDetailsBean.degree}"
				onchange="toggleDegreeOther();" 
				/>	
	</td>
	
  </tr>
  
  <tr>
  
    <td>If Other, Please Specify<span class="manadetory-fields"></span></td>
    <td colspan="2">
    	<s:textfield id="degreeOther" name="educationDetailsBean.degreeOther" maxlength="30" cssClass="fieldDisable" onkeypress="return alphabets(event);"></s:textfield>
	</td>
	
  </tr>
  
  
  
  <tr>
    <td>Result of Graduation <span class="manadetory-fields">*</span></td>
    <td colspan="2">
    	<s:radio list="resultOfGraduation" name="educationDetailsBean.resultOfGraduation" id="resultOfGraduation"  cssClass="fieldDisable resultOfGraduationClass" onchange="disableGraduationMarks()"/>
    </td>
    
  </tr>
  
  <tr>
	    <td>From where you have graduated? <span class="manadetory-fields">*</span></td>
	    <td colspan="2">
	    	<s:radio list="graduationFromUniv" name="educationDetailsBean.graduationFromUniv" id="graduationFromUniv" cssClass="fieldDisable graduationFromUnivClass" />
		</td>
  </tr>
  
  <tr>
	    <td>12<sup>th</sup> Std. (HSC/DIPLOMA) School located <span class="manadetory-fields">*</span></td>
	    <td colspan="2"><s:radio list="schoolLocation" name="educationDetailsBean.schoolLocation" id="schoolLocation" cssClass="fieldDisable schoolLocationClass"/></td>
  </tr>
  
  <tr>
  
  	<td>Candidate's diploma/ degree marks is/are based on CPI <span class="manadetory-fields">*</span></td>
  		
    <td>
	  <s:radio list="kashmirMigrant" name="personalDetailsBean.jnkMigrant" id="kashmirMigrant" onchange="disableSpiCpiFields();" cssClass="fieldDisable kashmirMigrantClass"/>
	</td>
  </tr>
  
  <tr>
  	  <td colspan="3"><table width="900" class="personsl-dtl" >
    	  <tr>
	        <td rowspan="2" width="128" align="left" bgcolor="#FFFFFF"><strong>Examination </strong></td>
	        <td rowspan="2" width="171" align="left" bgcolor="#FFFFFF"><strong>Year of Passing</strong></td>
	        <td rowspan="2" width="179" align="left" bgcolor="#FFFFFF"><strong>University / Institute</strong></td>
	        <td colspan="3" align="left" bgcolor="#FFFFFF"><strong>Grand Total Marks</strong></td>
	        <td rowspan="2" width="87" align="left" bgcolor="#FFFFFF"><strong>SPI</strong></td>
	        <td rowspan="2" width="84" align="left" bgcolor="#FFFFFF"><strong>CPI</strong></td>
        </tr>
        <tr>
	        <td align="left" bgcolor="#FFFFFF"><strong>Obtained</strong></td>
	        <td  align="left" bgcolor="#FFFFFF"><strong>Out Of</strong></td>
	        <td  align="left" bgcolor="#FFFFFF"><strong>Percentage</strong></td>
	        
        </tr>
        
        <!-- start of iterator -->
              	
            	<tr>
	        		<td width="128">
		        		S.S.C / 10<sup>th</sup>
		        	</td>
		        	
		        	<td width="171">
			        	<s:select name="academicDetailsBean.sscYearOfPassing" headerKey="" headerValue="Select Year" list="yearList"  id="yearIdSSC" cssClass="s fieldDisable" value="%{academicDetailsBean.sscYearOfPassing}" />
		        	</td>	
		        	
	        		<td width="179">
		        		<s:textfield name="academicDetailsBean.sscUniversity" cssClass="fieldDisable" value="%{academicDetailsBean.sscUniversity}" maxlength="90" id="sscUniversity"></s:textfield>
		        	</td>
		        	
		        	<td>
		        		<s:textfield name="academicDetailsBean.sscObtainedMarks" onkeypress="return numbersonly(event);" cssClass="isd fieldDisable" value="%{academicDetailsBean.sscObtainedMarks}" maxlength="4" id="sscObtainMarks" onkeypress="return numbersonly(event);" onblur="calculatePerc('ssc');"></s:textfield>
		        	</td>
		        	
		        	<td>
		        		<s:textfield name="academicDetailsBean.sscMaxMarks" onkeypress="return numbersonly(event);" cssClass="isd fieldDisable" value="%{academicDetailsBean.sscMaxMarks}" maxlength="4" id="sscMaxMarks" onkeypress="return numbersonly(event);" onblur="calculatePerc('ssc');"></s:textfield>
		        	</td>
		        	
		        	<td>
		        		<s:textfield name="academicDetailsBean.sscPercentage"  cssClass="isd fieldDisable" value="%{academicDetailsBean.sscPercentage}" maxlength="5" id="sscPercentage" ></s:textfield>
		        	</td>
		        	
		        	<td width="87">
		        		&nbsp;
		        	</td>	
		        	
		        	<td width="84">
		        		&nbsp;
		        	</td>		
	        	</tr>
	    
	    		
            	<tr>
	        		<td width="128">
		        		H.S.C /  12<sup>th</sup> / DIPLOMA
		        	</td>
		        	
		        	<td width="171">
			        	<s:select name="academicDetailsBean.hscYearOfPassing" headerKey="" headerValue="Select Year" list="yearList"  id="yearIdHSC" cssClass="s fieldDisable" value="%{academicDetailsBean.hscYearOfPassing}" />
		        	</td>	
		        	
	        		<td width="179">
		        		<s:textfield name="academicDetailsBean.hscUniversity" value="%{academicDetailsBean.hscUniversity}" cssClass="fieldDisable" id="hscUniversity" maxlength="90"></s:textfield>
		        	</td>
		        	
		        	<td>
		        		<s:textfield name="academicDetailsBean.hscObtainedMarks" onkeypress="return numbersonly(event);" cssClass="isd fieldDisable" value="%{academicDetailsBean.hscObtainedMarks}" maxlength="4" id="hscObtainedMarks" onkeypress="return numbersonly(event);" onblur="calculatePerc('hsc');"></s:textfield>
		        	</td>
		        	
		        	<td>
		        		<s:textfield name="academicDetailsBean.hscMaxMarks" onkeypress="return numbersonly(event);" cssClass="isd fieldDisable" value="%{academicDetailsBean.hscMaxMarks}" id="hscMaxMarks" maxlength="4" onkeypress="return numbersonly(event);" onblur="calculatePerc('hsc');"></s:textfield>
		        	</td>
		        	
		        	<td>
		        		<s:textfield name="academicDetailsBean.hscPercentage"  cssClass="isd fieldDisable" value="%{academicDetailsBean.hscPercentage}" id="hscPercentage" maxlength="5" ></s:textfield>
		        	</td>
		        	
		        	<td width="87">
		        		&nbsp;
		        	</td>	
		        	
		        	<td width="84">
		        		&nbsp;
		        	</td>		
	        	</tr>
	    
	    		
            	<tr>
	        		<td width="128">
		        		Graduation
		        	</td>
		        	
		        	<td width="171">
			        	<s:select name="academicDetailsBean.beYearOfPassing" headerKey="" headerValue="Select Year" list="yearList"  id="yearIdBE" cssClass="s fieldDisable" value="%{academicDetailsBean.beYearOfPassing}" />
		        	</td>	
		        	
	        		<td width="179">
		        		<s:textfield name="academicDetailsBean.beUniversity" cssClass="fieldDisable" value="%{academicDetailsBean.beUniversity}" id="beUniversity" maxlength="90"></s:textfield>
		        	</td>
		        	
		        	<td>
		        		<s:textfield name="academicDetailsBean.beObtainedMarks" onkeypress="return numbersonly(event);" cssClass="isd fieldDisable" value="%{academicDetailsBean.beObtainedMarks}" id="beObtainedMarks" maxlength="4" onkeypress="return numbersonly(event);" onblur="calculatePerc('graduation');"></s:textfield>
		        	</td>
		        	
		        	<td>
		        		<s:textfield name="academicDetailsBean.beMaxMarks" onkeypress="return numbersonly(event);" cssClass="isd fieldDisable" value="%{academicDetailsBean.beMaxMarks}" id="beMaxMarks" maxlength="4" onkeypress="return numbersonly(event);" onblur="calculatePerc('graduation');"></s:textfield>
		        	</td>
		        	
		        	<td>
		        		<s:textfield name="academicDetailsBean.bePercentage"  cssClass="isd fieldDisable" value="%{academicDetailsBean.bePercentage}" id="bePercentage" maxlength="5" ></s:textfield>
		        	</td>
		        	
		        	<td width="87">
		        		<s:textfield name="academicDetailsBean.beSPI" onkeypress="return numbersonly(event);" cssClass="isd fieldDisable" value="%{academicDetailsBean.beSPI}" id="beSPI" maxlength="2"></s:textfield>
		        	</td>	
		        	
		        	<td width="84">
		        		<s:textfield name="academicDetailsBean.beCPI" onkeypress="return numbersonly(event);" cssClass="isd fieldDisable" value="%{academicDetailsBean.beCPI}" id="beCPI" maxlength="2"></s:textfield>
		        	</td>		
	        	</tr>
	    
      <tr>
        <td colspan="3">
        	Incase of SPI and CPI, mention base information
        </td>
        
        <td colspan="5">
        	<s:select  name="academicDetailsBean.baseSpiCpiInfo" cssClass="s fieldDisable" id="baseSpiCpiInfo" list="baseSpiCpiList" 
        		headerKey="" headerValue="--Select--">
        	</s:select>
        </td>
        
        </tr>
      </table>
    </td>
  </tr>
  
  <tr>
    <td>Help Centre Name</td>
    	<td colspan="2">
    		<s:select list="helpCenterMasterMap" listKey="key" listValue="value"  cssClass="fieldDisable"
    			name='helpCenterFk' id='helpCenterFk' headerKey="" headerValue="--Select Help Center--">
    		</s:select>
       </td>
  </tr>
  
  
  <tr>
	   <td colspan="3">&nbsp;
	   </td>
   </tr>
  <tr>
	   <td colspan="3">&nbsp;
	   </td>
   </tr>
   
  <tr>
	    <td colspan="2" align="right"><span class="fadeSubmenu"><strong> &nbsp;&nbsp;&nbsp;&nbsp;</strong></span>
	    	<s:if test='%{editBtnFlag=="true"}'>
	    		<!-- <input type="button" value="Save & Submit" class="submitBtn button-gradient fieldDisable" onclick="changeActionForCandidateDetails();"> -->
	    		<input type="button" value="Save & Submit" class="submitBtn button-gradient fieldDisable" onclick="showPopUp();">
	    	</s:if>
	    </td>
  </tr>
  </table>
    
    </s:if>
 
 <div class="forgot-pass box-gradient" id="block10" style = "display:none;">
<div><a href="javascript:void(0);" onclick="PopHideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox fl-left"><h1 class="pageTitle" title="Login">&nbsp;Message</h1></div>
<div class="closebtnBox fl-rigt"></div>
<div class="hr-underline clear"></div>
<div style="text-align:center" ><br/>
    <strong>Candidate  <s:property value = "userId"/>  details updated Successfully</strong>
<br />
<br />
    
 <br />
<br />
    
<input type="button" value="Close" class="submitBtn button-gradient" title="Close" onclick="HideAll();"/>
</div>
</div>
</div>
<!-- Box Container Start -->
	<div class="change-pass box-gradient" id="pop9" style="vertical-align: top" >
<div><a href="javascript:void(0);" onclick="PopHideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox fl-left"><h1 class="pageTitle" title="Login">&nbsp;<s:label value="Registration Confirmation" /></h1></div>
<div class="closebtnBox fl-rigt"></div>
<div class="hr-underline clear"></div>
<br/>
	
	<div style = "text-align:center">Are you sure?</div><br/>
	<div style="text-align:center;" >
	<input type="button" name="Yes" id="true" value="Yes" class="submitBtn button-gradient" onclick="changeActionForCandidateDetails();" />  
<input type="button" name="No" id="true" value="No" class="submitBtn button-gradient" onclick="PopHideAll()" /></div>
</div>


<br/><br/>
</div>
</s:form>
</div>
</div>
