<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript">
$(document).ready(function() {
	$('.showHide1').hide();
	

<s:if test='%{docLabel19=="No Records found"}'>
//alert("no record");
	$('.showHide1').hide();
	$("#block20").show();
</s:if>
<s:if test='%{docLabel19=="records found"}'>
//alert("no record");
	$('.showHide1').show();
	$("#block20").hide();
</s:if>
<s:if test='%{docLabel19=="OTR Form is not submitted yet"}'>
//alert("no record");
	$('.showHide1').hide();
	$("#block20").show();
</s:if>

<s:if test='%{bitpTrainingDispFlag=="true"}'>
	$("#bitpTrainingCompName").attr('disabled',false);
	$("#trainingPeriod").prop ('readonly',false);
	
</s:if>
<s:else>
	$("#bitpTrainingCompName").attr('disabled','disabled');
	$("#trainingPeriod").attr ('readonly','readonly');
	$("#trainingPeriod").val("0");
</s:else>
	
	 <s:if test='%{postGraduateOtherField == "true"}'>
		$("#degreeOther").removeAttr("disabled");
	 </s:if>
	
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
	
	loadPImage();
	loadImage();

	

	var uploadedDocumentsHtml = '<a href="CandidateAction_getDocument.action?candidateDocId=<s:property value="candidateDocPk1"/>"><s:property value="documentFileName" /></a>';
	$("#uploadedDocuments").html(uploadedDocumentsHtml);
	$("#uploadedDocuments").fadeIn(1000);

	
	var uploadedDocumentsHtml1 = '<a href="CandidateAction_getDocument.action?candidateDocId=<s:property value="candidateDocPk2"/>"><s:property value="documentFileName1" /></a>';
	$("#uploadedDocuments1").html(uploadedDocumentsHtml1);
	$("#uploadedDocuments1").fadeIn(1000);

	var uploadedDocumentsHtml2 = '<a href="CandidateAction_getDocument.action?candidateDocId=<s:property value="candidateDocPk3"/>"><s:property value="documentFileName2" /></a>';
	$("#uploadedDocuments2").html(uploadedDocumentsHtml2);
	$("#uploadedDocuments2").fadeIn(1000);
	
	$("#dateOfBirth").attr("disabled",true);
	//setTimeout("checkForImageChange();",2000);
	//setTimeout("checkForSignChange();",2000);
	$("#id").attr ("src","PhotoImage.jpg?userFk="+$("#test").val());
	//toggleDegreeOther();
	
	<s:if test='%{displayForEditFlag=="true"}'>
		
	</s:if>
		
		var r = $("#successFlag").val();
		
		if (r=="true"){
			$("#block10").show();
		}
		
		enableDisableTextField();
		//enableDisableHandicappedPercentage();
		
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
		minDate: new Date("July 01, 1988"),
		maxDate: new Date("July 01, 1997"),
		buttonImageOnly: true,
		disabled: true,
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

		if($("#documentUploaded").val() == "true")
		{
			$("#uploadedDocuments").show();
		}
		if($("#documentUploaded1").val() == "true")
		{
			$("#uploadedDocuments1").show();
		}
		if($("#documentUploaded2").val() == "true")
		{
			$("#uploadedDocuments2").show();
		}


		 <s:if test='%{fieldsEnableFlag == "true"}'>
		 	testFunc();	 	
		 </s:if>
			
	}
);


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
            document.getElementById("id").src = "data:image/png;base64," + results;
            
	},
    success: function (data) {
        var results = data;
         //the results is a base64 string.  convert it to an image and assign as 'src'
         document.getElementById("id").src = "data:image/png;base64," + results;
         
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
            document.getElementById("idd").src = "data:image/png;base64," + results;
	},
    success: function (data) {
        var results = data;
         //the results is a base64 string.  convert it to an image and assign as 'src'
         document.getElementById("idd").src = "data:image/png;base64," + results;
    }   	 
	});
}
	var degreeType = '56';
	var dataString = "degreeTypeVal="+degreeType;
	if(degreeType!=''){
		$.ajax({
			url: "CandidateAction_getAcademicDetailsListForNodal.action",
			async: true,
			data: dataString,
			beforeSend: function(){
				$("#educationformDetails").hide();
			},
			error:function(ajaxrequest){
				alert('Error in refreshing . Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText){
				$("#dynamicTable").html(responseText);
				
			}
		});
	}
	
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
function populateCityForDistrict()
{
	$('#utList').val("");
	currUTStateId = "#districtList";
	populateCity();
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

function populateCity(){
	
	var state = $(""+currUTStateId).val();
	dataString = "districtVal="+state
		
		$.ajax({
			url: "CandidateAction_getCityList.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
					$('#cityList').empty();
					$("#cityList").append(
					           $('<option></option>').val("").html("--Select City--")
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
							 var nameAndIDArr = element[val];//.split("#");
						  
						  $("#cityList").append(
						           $('<option></option>').val(nameAndIDArr).html(nameAndIDArr)
						     );
					 	}); 
					}
				}
			}
		});
		
		
		
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
	//$("#altCountryId").val("");
	$("#otherAlternateStateField").val("");
	$("#otherAlternateDistrictField").val("");
	$("#altUtList").val("");
	$("#altTalukaList").val("");
	$("#otherAlternateDistrictField").val("");
	$('#altStateList').val("");
	//$('#altStateList').empty();
//	$("#altStateList").append(
//	           $('<option></option>').val("").html("--Select State--")
//	     );
	
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
				ajaxEducationalDetails();
			}
			 function ajaxEducationalDetails(){
					$("#saveEducationalDetailsForNodal").ajaxForm({	beforeSend: function() {
						$('#block9').show();
			    	},
						target: '#divDocUploadResponse',
						complete: function() {
			    		$('#block9').hide();
						},
			    		success:function(responseText){
								if(responseText != "")
								{
									if(responseText.indexOf("message")!="-1"){
										$("#errorMsg2").show();
										var newText = responseText.replace("<pre>", "").replace("</pre>","");
										var newText1 = newText.replace("(message)", "").replace("(/message)","");
										var newText2 = newText1.replace("[", "").replace("]","");
										//var newText2 = newText1.replace("<br/>", "");
										responseText = $.trim(newText2);
										var abcd =responseText;
										$("#saveEducationalDetailsResponseText").html(abcd);
										$("#saveEducationalDetailsResponseText").fadeIn(1000);
										$("#saveEducationalDetailsResponseTextError").hide();
									}else{
										$("#errorMsg2").hide();
										var newText = responseText.replace("<pre>", "").replace("</pre>","");
										responseText = $.trim(newText);
										$("#saveEducationalDetailsResponseTextError").html(responseText);
										$("#saveEducationalDetailsResponseTextError").fadeIn(1000);	
										$("#saveEducationalDetailsResponseText").hide();
									}	
								}
						}
					}).submit();
				} 

			
			 function enableDisableTextField()
			 {
				 var categoryVal = $("#categoryVal").val();
				 if (categoryVal == '5')
				 {
		 		 	$("#category").attr ('disabled',false);
					//$("#category").val("");
	 			 }
	 			 else
	 			 {
	 			 	$("#category").attr ('disabled',true);
					$("#category").focus();
	 			 }
			}
			
			
function changeAction(){
	$("#applicationFormForEdit").attr('action',"CandidateAction_getCandidateDetailsForNodalForEdit.action");
	$("#applicationFormForEdit").submit();
}
function enableAllFields() {
	$("#dateOfBirth").datepicker('enable');
	$(".fieldDisable").each(function(currIndex, currElement) {
		$(currElement).removeAttr('disabled').removeClass('fieldDisable');
	}); 
		
	<s:if test='%{bitpTrainingDispFlag=="true"}'>
		$("#bitpTrainingCompName").attr ('disabled',false);
	</s:if>
	<s:else>
		$("#bitpTrainingCompName").attr ('disabled',true);
	</s:else>
		
		
		

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


<s:if test='%{districtOtherFlag=="true"}'>
$("#otherDistrict").attr ('disabled',true);
$("#otherDistrict").val("");
</s:if>
<s:else>
$("#otherDistrict").removeAttr ('disabled');
</s:else>

<s:if test='%{altDistrictOtherFlag=="true"}'>
$("#altOtherDistrict").attr ('disabled',true);
$("#altOtherDistrict").val("");
</s:if>

<s:else>
$("#altOtherDistrict").removeAttr ('disabled');
</s:else>
}





	function uploadImage(){
	$("#errorMessagesDiv2").hide();
	var msg = validateInputForImage();
	if (msg){
		setUserIdForUploadImage ();
		//$("#frmImageUpload").ajaxForm({target: '#divImageUploadResponse'}).submit();
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
			},
			success:function(responseText){
				if(responseText != "")
				{
					if(responseText.indexOf("message")!="-1"){
						$("#errorMsg").show();
						var newText = responseText.replace("<pre>", "").replace("</pre>","");
						var newText1 = newText.replace("(message)", "").replace("(/message)","");
						var newText2 = newText1.replace("[", "").replace("]","");
						var newText3 = newText1.replace("<br/>", "");
						responseText = $.trim(newText3);
						var abcd =responseText;
						$("#errorMsg").html(abcd);
						$("#errorMsg").fadeIn(1000);
					}else{
						$("#errorMsg").hide();
					}
					
					setTimeout("checkForImageChange();",2000);
<%--					--%>
<%--					if(responseText.indexOf("Success")=="-1"){--%>
<%--						$("#errorMsg").show();		--%>
<%--					}else{--%>
<%--						$("#errorMsg").hide();--%>
<%--					}--%>
				}
		}
		}).submit();
	}

	function uploadSignature(){
		$("#errorMessagesDiv2").hide();
		var msg = validateInputForSign();
		//var msg = true;
		if (msg){
			setUserIdForUploadImage ();
			//$("#frmImageUpload").ajaxForm({target: '#divImageUploadResponse'}).submit();
				ajaxSignUpload("attachmentSignature");
			}
		}

	function uploadDocument(){
		$("#errorMessagesDiv2").hide();
		//var msg = validateInputForSign();
		var msg = true;
		
		if (msg){
			setUserIdForUploadImage();
			//$("#frmImageUpload").ajaxForm({target: '#divImageUploadResponse'}).submit();
				ajaxDocumentUpload("data");
			}
		}
	function uploadDocument1(){
		$("#errorMessagesDiv2").hide();
		//var msg = validateInputForSign();
		var msg = true;
		
		if (msg){
			setUserIdForUploadImage();
			//$("#frmImageUpload").ajaxForm({target: '#divImageUploadResponse'}).submit();
				ajaxDocumentUpload1("data1");
			}
		}
	function uploadDocument2(){
		$("#errorMessagesDiv2").hide();
		//var msg = validateInputForSign();
		var msg = true;
		
		if (msg){
			setUserIdForUploadImage();
			//$("#frmImageUpload").ajaxForm({target: '#divImageUploadResponse'}).submit();
				ajaxDocumentUpload2("data2");
			}
		}
	
		function ajaxSignUpload(fileToUpload){
			$("#frmSignUpload").ajaxForm({	beforeSend: function() {
				$('#block90').show();
	    	},
				target: '#divSignUploadResponse',
				
				complete: function() {
	    		$('#block90').hide();
				},
				success:function(responseText){
					if(responseText != "")
					{
						if(responseText.indexOf("message")!="-1"){
							$("#errorMsg1").show();
							var newText = responseText.replace("<pre>", "").replace("</pre>","");
							var newText1 = newText.replace("(message)", "").replace("(/message)","");
							var newText2 = newText1.replace("[", "").replace("]","");
							var newText3 = newText1.replace("<br/>", "");
							responseText = $.trim(newText3);
							var abcd =responseText;
							$("#errorMsg1").html(abcd);
							$("#errorMsg1").fadeIn(1000);
						}else{
							$("#errorMsg1").hide();
						}
						
						setTimeout("checkForSignChange();",2000);
					}					
			}
			}).submit();
		}
		
		function ajaxDocumentUpload(fileToUpload){
			$("#uploadDocs").ajaxForm({	beforeSend: function() {
				$('#block123').show();
	    	},
				target: '#divDocUploadResponse',
				complete: function() {
	    		$('#block123').hide();
				},
	    		success:function(responseText){
						if(responseText != "")
						{
							if(responseText.indexOf("message")!="-1"){
								$("#errorMsg2").show();
								var newText = responseText.replace("<pre>", "").replace("</pre>","");
								var newText1 = newText.replace("(message)", "").replace("(/message)","");
								var newText2 = newText1.replace("[", "").replace("]","");
								//var newText2 = newText1.replace("<br/>", "");
								responseText = $.trim(newText2);
								var uploadedDocumentsErrorHtml =responseText;
								$("#errorMsg2").html(uploadedDocumentsErrorHtml);
								$("#errorMsg2").fadeIn(1000);
							}else{
								$("#errorMsg2").hide();
								var newText = responseText.replace("<pre>", "").replace("</pre>","");
								responseText = $.trim(newText);
								//var uploadedDocumentsHtml = responseText;
								var split = responseText.split('#');
								var name = split[0];
								var pk = split[1];
								var pks = pk.replace("</PRE>","");
								var uploadedDocumentsHtml = '<a href="CandidateAction_getDocument.action?candidateDocId='+pks+'">'+name+'</a>';
								$("#uploadedDocuments").html(uploadedDocumentsHtml);
								$("#uploadedDocuments").fadeIn(1000);	
							}	
						}
				}
			}).submit();
		}
		function ajaxDocumentUpload1(fileToUpload){
			$("#uploadDocs1").ajaxForm({	beforeSend: function() {
				$('#block123').show();
	    	},
				target: '#divDocUploadResponse',
				complete: function() {
	    		$('#block123').hide();
				},
	    		success:function(responseText){
						if(responseText != "")
						{
							if(responseText.indexOf("message")!="-1"){
								$("#errorMsg3").show();
								var newText = responseText.replace("<pre>", "").replace("</pre>","");
								var newText1 = newText.replace("(message)", "").replace("(/message)","");
								var newText2 = newText1.replace("[", "").replace("]","");
								//var newText2 = newText1.replace("<br/>", "");
								responseText = $.trim(newText2);
								var uploadedDocumentsErrorHtml =responseText;
								$("#errorMsg3").html(uploadedDocumentsErrorHtml);
								$("#errorMsg3").fadeIn(1000);
							}else{
								$("#errorMsg3").hide();
								var newText = responseText.replace("<pre>", "").replace("</pre>","");
								responseText = $.trim(newText);

								var split = responseText.split('#');
								var name = split[0];
								var pk = split[1];
								var pks = pk.replace("</PRE>","");
								var uploadedDocumentsHtml = '<a href="CandidateAction_getDocument.action?candidateDocId='+pks+'">'+name+'</a>';
								$("#uploadedDocuments1").html(uploadedDocumentsHtml);
								$("#uploadedDocuments1").fadeIn(1000);	
							}	
						}
				}
			}).submit();
		}
		function ajaxDocumentUpload2(fileToUpload){
			$("#uploadDocs2").ajaxForm({	beforeSend: function() {
				$('#block123').show();
	    	},
				target: '#divDocUploadResponse',
				complete: function() {
	    		$('#block123').hide();
				},
	    		success:function(responseText){
						if(responseText != "")
						{
							if(responseText.indexOf("message")!="-1"){
								$("#errorMsg4").show();
								var newText = responseText.replace("<pre>", "").replace("</pre>","");
								var newText1 = newText.replace("(message)", "").replace("(/message)","");
								var newText2 = newText1.replace("[", "").replace("]","");
								//var newText2 = newText1.replace("<br/>", "");
								responseText = $.trim(newText2);
								var uploadedDocumentsErrorHtml =responseText;
								$("#errorMsg4").html(uploadedDocumentsErrorHtml);
								$("#errorMsg4").fadeIn(1000);
							}else{
								$("#errorMsg4").hide();
								var newText = responseText.replace("<pre>", "").replace("</pre>","");
								responseText = $.trim(newText);
								var split = responseText.split('#');
								var name = split[0];
								var pk = split[1];
								var pks = pk.replace("</PRE>","");
								var uploadedDocumentsHtml = '<a href="CandidateAction_getDocument.action?candidateDocId='+pks+'">'+name+'</a>';
								$("#uploadedDocuments2").html(uploadedDocumentsHtml);
								$("#uploadedDocuments2").fadeIn(1000);	
							}	
						}
				}
			}).submit();
		}
	
	function checkForImageChange(){
		loadPImage();
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
					$("#imageTd").show();
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
		//setTimeout("checkForImageChange();",2000);
	}
	function checkForSignChange(){
		loadImage();
		try
		{
			var responseDiv = $.trim($("#divSignUploadResponse").html());
			if(responseDiv != "")
			{
				responseDiv = responseDiv.replace("<PRE>", "").replace("</PRE>","");
				responseDiv = responseDiv.replace("<pre>", "").replace("</pre>","");
				responseDiv = $.trim(responseDiv);
				responseDiv = responseDiv.substring(responseDiv.indexOf("(message)")+9, responseDiv.indexOf("(/message)"));
				//alert(responseDiv);
				$("#divSignUploadResponse").html("");
				//alert(responseDiv);
				var responseArr = responseDiv.split(",");
				if(responseArr[0]=="0")
				{
					//alert('2');
					timestamp = new Date().getTime();
					
					var imageHtml = '<img width="110" height="150" src="SignatureImage.jpg?t=' + timestamp + '&userFk=' + $.trim($("#test").val()) + '" id="id"/>';
					$("#imageSign").html(imageHtml);
					$("#imageSign").fadeIn(1000);
					//$("#inputStreamForImage").removeAttr("src").attr("src", "PhotoImage.jpg");
					$("#imageSign").show();
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
		//setTimeout("checkForSignChange();",2000);
	}
	

function validateUserID(){
	var message = "";
	
	var userId = $("#userId").val();

	if (userId ==""){
		message = message + "Please enter User ID."+"##";
	}else{
		$("#errorMessagesDiv").hide();
	}
	
	if(message != ""){
			
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

	function validateInputForSign(){
		var message = "";
		
		var attachmentPhoto = $("#attachmentSignature").val();

		if (attachmentPhoto ==""){
			message = message + "Please select a Sign for uploading "+"##";
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
		$("#applicationFormForEditDetails").attr('action',"CandidateAction_saveCandidateDetailsForNodal.action");
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

				var candidateMiddleName = $("#candidateMiddleName").val();

				var candidateLastName = $("#candidateLastName").val();

				var titleValue = $("#titleValue").val();
				
				
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
				
				var city = $("#cityList").val();
				
				var pincode = $("#pinCode").val();
				
				var sameAddChkBox = $("#addressChkBox").is(':checked');

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
				
			//	var countryCode = $("#conutryCode").val();
				
			//	var stdCode = $("#stdCode").val();
				
				var phone = $("#phoneNo").val();
				
				var categoryIssuer = $("#category").val();

				var handicappedPer = $("#handicappedPercentage").val();
				var talukaField = $("#talukaField").val();
				var altTalukaField = $("#altTalukaField").val();
				var altTalukaList = $("#altTalukaList").val();

				var message = "";

				if (titleValue==""||titleValue==''){
					message = "Please select Prefix."+"##";
				}	

				if(UserName == ""){
					message =message+"Please enter your First Name."+"##";
				}

				if (candidateLastName==""||candidateLastName==''){
					message = message+"Please enter  Candidate Last Name."+"##";
				}	

				
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
					message = message + "Permanent Pincode must be alteast 6 digits."+"##";
				
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
						message = message + "Communication pincode must be alteast 6 digits."+"##";
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
			
				//if(handicappedPer != ""){
					//if(handicappedPer.length < 2 && handicappedPer.length > 3)
				//		message = message + "Percentage of Disablity must be more than 40%"+"##";
			//		if(handicappedPer <= 40)
			//			message = message + "Percentage of Disablity must be more than 40%"+"##";
			//		if(handicappedPer.charAt(0) == "0")
			//			message = message + "Percentage of Disablity cannot be start with zero"+"##";
		//		}

				//if(countryCode == "" || stdCode == "" || phone == ""){
			//		message = message + "Please enter valid  Phone No."+"##";
			//	}
			//	if(countryCode != "" || stdCode != "" || phone != ""){
			//		if(countryCode == "")
			//			message = message + "Please enter valid Country code"+"##";
			//		if(stdCode == "")
			//			message = message + "Please enter valid STD code"+"##";
			//		if(phone == "")
			//			message = message + "Please enter valid  Phone No."+"##";
					
			//	}

			
				
			//	if($('#handicappedYes').is(':checked')){
			//		if(handicappedPer == "")
			//			message = message + "Please enter Percentage of Disability."+"##";

			//		if(handicappedPer == "0")
			////		{
			//			message = message + "Percentage of Disability should be greater than zero."+"##";
			//		}else
			//		{
			//			if (!isValidPercentage(handicappedPer)) {
			////				message = message + "Please enter valid Percentage of Disability ."+"##";
						
			//			}
			//		}
			//	}
			
				
				
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

				
				
					var testCntre1 = $("#testCenter1").val();
					var testCntre2 = $("#testCenter2").val();
					var testCntre3 = $("#testCenter3").val();


					<s:if test='%{testCenterStatus=="A"}'>
					
					</s:if>
					
				if(message != ""){
					createErrorList(message, ulID, divID); 
					$("#error-massageAppForm").focus();
					$('html, body').animate({ scrollTop: 0 }, 0);
					//$('html, body').animate({ scrollTop: 0 }, 'slow');
					return false;
				}else{
				
					$("#error-massageAppForm").hide();
					callFormFunction();
					/*var r=confirm("Are you sure?");
					if (r==true)
					{
					  return true;
					}
					else
					{
					  return false;
					}*/
					  //return true;
				}
}

function callFormFunction() {
	ajaxPersonalDetails();
}
 function ajaxPersonalDetails(){
	 	$("#applicationFormForEditDetails").ajaxForm({	beforeSend: function() {
			
    	},
			complete: function() {
    		},
    		success:function(responseText){
					if(responseText != "")
					{
						if(responseText.indexOf("message")!="-1"){
						
							var newText = responseText.replace("<pre>", "").replace("</pre>","");
							var newText1 = newText.replace("(message)", "").replace("(/message)","");
							var newText2 = newText1.replace("[", "").replace("]","");
							//var newText2 = newText1.replace("<br/>", "");
							responseText = $.trim(newText2);
							var abcd =responseText;
							$("#savePersonalDetailsResponseText").html(abcd);
							$("#savePersonalDetailsResponseText").fadeIn(1000);
							$("#savePersonalDetailsResponseTextError").hide();
							$("#savePersonalDetailsResponseText").show();
						}else{
							var newText = responseText.replace("<pre>", "").replace("</pre>","");
							responseText = $.trim(newText);
							$("#savePersonalDetailsResponseTextError").html(responseText);
							$("#savePersonalDetailsResponseTextError").fadeIn(1000);	
							$("#savePersonalDetailsResponseText").hide();
						}	
					}
			}
		}).submit();
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
	$("#academicDetails").hide();
	$("#postGraduationDetails").hide();
	$("#signDisplayDiv").hide();
	$("#uploadDocument").hide();
	$("#disciplineTable").hide();
	$("#preferenceTable").hide();
	$("#serverSideError").hide();
	$("#stipendDetailsDiv").hide();
	$("#otherDetailsDiv").hide();
	$("#educationalDetailsAppFormDiv").hide();
	$("#testCenterDetailsDiv").hide();
	$(".showHide1").hide();
		$("#block20").hide();
	
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


	function onErrorMessage(id){
		$("#"+id).hide();
	}

    
	   function setPostGraduateDegreeDesc(){
			var postGraduateValue =  $("#postGraduateValue option:selected").text();
			$("#postGraduateValueDesc").val(postGraduateValue);
			ajaxPostGraduateDegreeDesc();
	   }

	   function ajaxPostGraduateDegreeDesc(){
			$("#savePostGraduationDetailsForNodal").ajaxForm({	beforeSend: function() {
				$('#block9').show();
	    	},
				target: '#divDocUploadResponse',
				complete: function() {
	    		$('#block9').hide();
				},
	    		success:function(responseText){
						if(responseText != "")
						{
							if(responseText.indexOf("message")!="-1"){
								$("#errorMsg2").show();
								var newText = responseText.replace("<pre>", "").replace("</pre>","");
								var newText1 = newText.replace("(message)", "").replace("(/message)","");
								var newText2 = newText1.replace("[", "").replace("]","");
								//var newText2 = newText1.replace("<br/>", "");
								responseText = $.trim(newText2);
								var abcd =responseText;
								$("#postGraduateResponseText").html(abcd);
								$("#postGraduateResponseText").fadeIn(1000);
								$("#postGraduateResponseTextError").hide();
							}else{
								$("#errorMsg2").hide();
								var newText = responseText.replace("<pre>", "").replace("</pre>","");
								responseText = $.trim(newText);
								$("#postGraduateResponseTextError").html(responseText);
								$("#postGraduateResponseTextError").fadeIn(1000);	
								$("#postGraduateResponseText").hide();
							}	
						}
				}
			}).submit();
		} 

	   function calcPerc(obtId,outofId,percId){
	  		var d = $(obtId).val();
	  	var d1 = $(outofId).val();
	  	if (d1!="" && d!=""){
	  		if(d1!=0){
			  	var pec = (d/d1)*100;
			  	pec = pec.toFixed(2); 
				$(percId).val(pec);
			}else
			{
				$(percId).val("0");
			}
	  	}else{
	  	$(percId).val("");
	  	}
	}

	   function disableField(){
			var postGraduateValue =  $("#postGraduateValue option:selected").text();
			$("#postGraduateValue").val($("#postGraduateValue option:selected").val());
			
			if (postGraduateValue.toLowerCase()=='others'){
				$("#degreeOther").removeAttr("disabled");
			}else{
				$("#degreeOther").attr("disabled","disabled");
			}
		} 

	   function getEducationalDetails(){
		var degreeType = $("#degreeType").val();
		var menuKey = $("#menuKey").val();
		var dataString = "degreeTypeVal="+degreeType;
		$("#saveEducationalDetailsResponseText").val("");
		$("#saveEducationalDetailsResponseTextError").val("");
		$.ajax({
			url: "CandidateAction_getAcademicDetailsListForNodal.action",
			async: true,
			data: dataString,
			beforeSend: function(){
				$("#educationformDetails").hide();
				$("#saveEducationalDetailsResponseText").val("");
				$("#saveEducationalDetailsResponseTextError").val("");
			},
			error:function(ajaxrequest){
				alert('Error in refreshing . Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText){
				$("#dynamicTable").html(responseText);
				$("#saveEducationalDetailsResponseText").html("");
				$("#saveEducationalDetailsResponseTextError").html("");
			}
		});
 		}        


		function getCandidateData() {
			$("#saveEducationalDetailsResponseText").hide();
			$("#saveEducationalDetailsResponseTextError").hide();
			$("#saveEducationalDetailsForNodal").attr('action','CandidateAction_getAllCandidateDetails.action');
			$("#saveEducationalDetailsForNodal").submit();
		}

		function enableMarks(obtId,outofId,result,percentageId){
			if (result.checked){
				$(obtId).attr('disabled','disabled');
				$(outofId).attr('disabled','disabled');
				$(obtId).val("");
				$(outofId).val("");
				$(percentageId).val("");
			}else{
				$(obtId).removeAttr('disabled');
				$(outofId).removeAttr('disabled');
			}
		}

		
		function enableOtherDistrict(){
			var district = $("#districtList").val();
			if (district == '0'){
				 $("#otherDistrict").attr ('disabled',false);
				 $("#otherDistrict").val("");
			 }else{
				 $("#otherDistrict").attr ('disabled',true);
				 $("#otherDistrict").val("");
			 }
			}

		function enableAltOtherDistrict(){
			var district = $("#altDistrictList").val();
			//alert('district '+district);
			if (district == '0'){
				 $("#altOtherDistrict").attr ('disabled',false);
				 $("#altOtherDistrict").val("");
			 }else{
				 $("#altOtherDistrict").attr ('disabled',true);
				 $("#altOtherDistrict").val("");
			 }
		}

		function testFunc() {
			enableAllFields();
		}
		
		function enableTrainingField(){
	var bitpTraining = "";
	 $(".bitpTrainingValClass").each(function(){
			if($(this).is(':checked'))
			{
				 var id = $(this).attr("id");
				 bitpTraining = $('label[for='+id+']').text();
			}
		});
		
	$("#bitpTrainingVal").val(bitpTraining);
		
	if(bitpTraining == "Yes")
	{
		$("#bitpTrainingCompName").attr('disabled',false);
		$("#bitpTrainingCompName").focus();
		$("#trainingPeriod").val("");
		$("#trainingPeriod").prop('readonly',false);
	}
	if(bitpTraining == "No")
	{
		$("#bitpTrainingCompName").attr ('disabled',true);
		$("#bitpTrainingCompName").val("");
		$("#trainingPeriod").val("0");
		$("#trainingPeriod").attr('readonly','readonly');
	}
}

		function numberswithdot(e){
			var unicode=e.charCode? e.charCode : e.keyCode
			if (unicode!=8){
			if ((unicode<48||unicode>57) && unicode != 9 && unicode != 46) //if not a number
				return false //disable key press
			}
		}

		function maxLengthValidation(textBox, e, maxLength) {
			var unicode=e.charCode? e.charCode : e.keyCode
		    if(unicode==13){
				return false;
			}else{
				if (textBox.value.length > maxLength - 1) {        
			    	alert("More than 1000 Characters are not allowed in the Project description"); 
			        textBox.value = textBox.value.substr(0, maxLength);
			    }
			}
		}

		
		function maxLengthValidationOnPaste(field,maxChars)
		{
		      if(field.value.length > maxChars) {
		    	  alert( "More than 1000 Characters are not allowed in the Project description");
		         return false;
		      }else{
		    	  return true;
		      }
		}

			function checkSpecialKeys(e) {
				//alert(e.keyCode);
			    if (e.keyCode != 8  && e.keyCode != 46 && e.keyCode != 33 && e.keyCode != 34 && e.keyCode != 35 && e.keyCode != 37 && e.keyCode != 38 && e.keyCode != 39 && e.keyCode != 40 && e.keyCode != 36){
						//alert("e.keyCode "+e.keyCode);
				     return false;
			    }
			    else{
			        return true;
			    }
			}

			function maxLengthLimit(field,maxChars,event){
				if (event.keyCode==13){
				     return false;
				}
				//alert('this is maxLengthLimit');
				if (!checkSpecialKeys(event)) {
					//alert('checkSpecialKeys '+event.keyCode);
			    if (field.value.length >= maxChars) {
			        return false;
			    }else{
			        return true;
			        }
				}
			}  

			function maxLengthValidationOnPaste(field,maxChars){
			      if(field.value.length >maxChars) {
			         return false;
			      }else{
			    	  return true;
			      }
			} 
			function populateAltCityForDistrict()
			{
				
				$('#utList').val("");
				currUTStateId = "#altDistrictList";
				populateAltCity();
			}
			function populateAltCity(){
				
				var state = $(""+currUTStateId).val();
				dataString = "districtVal="+state
				
					
					$.ajax({
						url: "CandidateAction_getAltCityList.action",
						async: true,
						data: dataString,
						beforeSend: function()
						{
								$('#alternateCity').empty();
								$("#alternateCity").append(
								           $('<option></option>').val("").html("--Select City--")
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
									  var nameAndIDArr = element[val];//.split("#");
									  
									  $("#alternateCity").append(
									           $('<option></option>').val(nameAndIDArr).html(nameAndIDArr)
									     );
								 	}); 
								}
							}
						}
					});
					
					
					
						}
						
			function populateAltDistrict(){
				var state = $(""+currUTStateId).val();
				dataString = "stateVal="+state
				
					
					$.ajax({
						url: "CandidateAction_getAltDistrictList.action",
						async: true,
						data: dataString,
						beforeSend: function()
						{
								$('#altDistrictList').empty();
								$("#altDistrictList").append(
								           $('<option></option>').val("").html("--Select District--")
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
			function populateAltDistrictForState()
			{
				$('#utList').val("");
				currUTStateId = "#altStateList";
				populateAltDistrict();
			}			
</script>
  
  <script type="text/javascript">
		
			var JSON_STRING = [];
		function down_pdf(){
		var array = [];
	$('#applicationForm').find('label').each(function(){ 
		
	    array.push({
	         field: $(this).attr("id"),
	         value: $(this).text()
	    });
	});

	var jsonString = JSON.stringify(array);


}
		
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


		function downloadPDF1(){
		
		//request.setCharacterEncoding("UTF-8");
       	//window.open('index_pdf.jsp');
		//console.log(r1);
		window.open("CandidateAction_downloadPdf.action")
		//downloadPDF(r1);
		 
		}
		function downloadPDF2(){
			
			//request.setCharacterEncoding("UTF-8");
	       	//window.open('index_pdf.jsp');
			//console.log(r1);
			window.open("CandidateAction_downloadPdf.action")
			//downloadPDF(r1);
			 
			}
		function printORF()
		{
			window.open("CandidateAction_viewPrintFinalDetails2.action");
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
				var lMsg="Please verify and Confirm the certificate";
				if(rowCount!=0)
				{
					for(var i=0;i<rowCount;i++)
					{
						if($('#myTable tr').eq(i).children(":nth-child(3)").text()== lMsg || $('#myTable tr').eq(i).children(":nth-child(3)").text()==null)
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
					downloadPDF2();
					$("#applicationForm").attr('action',"PaymentOnlineAction_showPaymentScreen.action?Applyandmakepayment=Applyandmakepayment");
					
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
.onetime{
display: none;
}

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
    border: 1px solid #888; 
    width: 80%; 
    height: 80%;
	overflow: auto;
   
}

.modal-content img{
  width: 100%;
}
.screenimages{
	text-align: center; 
	height: 350px;  
	overflow: auto;
}

.screenimages img{
   width: auto !important;
}
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
  <style>
#msgg li { float:left; font-weight:normal; margin-left:20px; }
#msgg br { height:1px; float:left; }
.ui-datepicker-trigger {margin-top:8px !important; }
</style>  
<div class="container">

<div id="dashboard" style="display:block;   height:auto;" class="container common_dashboard">

<h1 class="pageTitle" title="View / Edit Candidate">View / Edit Candidate</h1>
<div class="hr-underline2"></div>

<s:form id="applicationFormForEdit" action="CandidateAction" onsubmit="return validateUserID();">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
 
 <div id="msgg" style="margin-bottom:20px;" >
 <span id="savePersonalDetailsResponseText" style="display:none;color:green;" escape="true"></span>
	    <span id="savePersonalDetailsResponseTextError" style="display:none;color:red;" escape="true"></span> 
	    </div>
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
	      <td width="170">
	      		User ID <span class="manadetory-fields">*</span>
	      </td>
	      
	      <td width="170" colspan="2">
	      <s:textfield name="userId" id="userId" onkeypress="return alphaNumeric(event)" onpaste="return false;"></s:textfield>
	      </td>
    </tr>
    <tr>
    	  <td>&nbsp; </td>
    	  
      	  <td colspan="2">
      	  <input type="button" value="Search" class="submitBtn button-gradient" onclick="changeAction()"/>&nbsp;
      	   <input type="button" value="Clear" class="submitBtn button-gradient" onclick="clearFields();"/> 
      	  </td>
      	  
      	  	<td align="right">
      	  		<s:if test='%{flagForEdit=="true"}'>
      	  			<s:if test='%{editBtnFlag=="true"}'>
	         			<input type="button" value="Edit" class="submitBtn button-gradient" onclick="enableAllFields()" id = "editBtn"/>
		         	</s:if>
		        	</s:if>	
	         </td>
      	  
      	  <s:if test='%{serverSideErrorMessage == "true"}'>
	 		 <div class="error-massage-text" id="serverSideError">
				  <s:actionmessage  escape="false"/>
	     		</div>
	    </s:if>
    </tr>
    </table>
     <!--<div  id="block20" style = "display:none;" >
     <div class="hr-underline2"></div>
     <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li>No record Found for <s:property value = "userId"/>.</li> 
    	</ul>
    </div>
<br />
</div>
    --></div>
    <s:token />
</s:form>

<div class="showHide1" style="display: none;">
<br>
	<div class="hr-underline2"></div>
	<br>
	<div >
		<s:hidden id='hddAddressChkBox'></s:hidden>
		
		<div align="left">
		
				<strong>User ID / </strong><span class="tamil"><s:text name="applicationForm.userId"/></span> - <strong><s:label
					value="%{UserId}" /></strong>
				<br />
				<!--<strong>Course</strong> - <s:label cssStyle="color: black"
					value="%{disciplinName}" />

			-->
			<s:if test='%{afterApplyVeiwPayment!=null && afterApplyVeiwPayment=="true"}'>
				<strong>Recruitment / </strong><span class="tamil"><s:text name="applicationForm.Recruitment"/></span> - <strong><s:label value="%{disciplineTypeDesc}"></s:label></strong>
				<br/>
			
				<strong>Application No / </strong><span class="tamil"><s:text name="applicationForm.app"/></span> - <strong><s:label value="%{additionalDetailsBean.applicationNumber}"></s:label></strong>
					<br/>
				<s:iterator value="postMasterList" status="incr">
				<strong>Preference <s:property value="%{#incr.index+1}"/><span class="tamil"><s:text name="prefer"/></span> <s:property value="%{#incr.index+1}"/></strong> - <strong><s:property /></strong>
  				<br/>	
				</s:iterator>
			</s:if>
			<s:if test='%{afterApplyVeiwPayment!=null && afterApplyVeiwPayment=="true"}'>
					
								<strong>Preferred District/City for Written Test  / <span class="tamil"><s:text name="application.testCenter"/></span></strong> -
						
							
								<strong><s:label value="%{testCenter1}"
										id="testCenter1" />
								</strong>
							
							</s:if>

		</div>
		<br />
		
	<div class="container"><br/><div id="msg"></div></div>
	<div class="">
		<h1 class="pageTitle">
		
		<div     align="right">
	
		
		</div>
			 Personal Details / <span class="tamil"><s:text name="applicationForm.personalDetails"/></span>
			
		</h1>
		<s:set var="PersonalDetails" value="%{disciplineType}" />
		
		
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
									<img src="PhotoImage.jpg?abc=<s:property value="%{UserId}"/>" height="100" width="100" id="inputStreamForImage" 
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
									<br/>
								
											<span class="tamil"><s:text name="academics.studiedTamil"/></span>&nbsp;&nbsp;
											<strong><s:label value="%{sslcTamil}"></s:label></strong>
									
									<br/>
									<br/>
									<s:if test='%{addAcademicDtlsList.size()!=0}'>
									<div><h1 class="pageTitle">Additional Qualifications / <span class="tamil"><s:text name="academicDetails.additionalDetails"/></span></h1>
									<div class="hr-underline2"></div>
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
											<td><label id="veritext1"><strong>Please verify and Confirm the certificate</strong></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName1" value="%{DocumentFileNameDischarge}"></s:hidden>
									    	<s:hidden id="candidateDocPk1" value="%{CandidateDocPkDischarge}"></s:hidden>
									    	<s:hidden id="ocdFlagValue1" value="%{ocdFlagDischarge}"></s:hidden>
											<td>
												<div id="myModal1" class="modal">
													<div class="modal-content">
														<div><span onclick="closeDiv(1);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
														<br/>
														<br/>
														<s:if test='%{DocumentFileNameDischarge.toLowerCase().endsWith("pdf")}'>
															<div>
																<object data="servlet/StartupServlet.do?pdf=pdf&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagDischarge"/>_<s:property value="DocumentFileNameDischarge"/>#toolbar=0&navpanes=0" type="application/pdf" width="100%" height="350"></object>
															</div>
														</s:if>
														<s:else>
															<div class="screenimages">
		<%--													<object data="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="DocumentFileNameDischarge"/>" type="image/jpg" width="100%" height="400"></object>--%>
																<img src="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagDischarge"/>_<s:property value="DocumentFileNameDischarge"/>"/>
															</div>	
														</s:else>
														<div>
															<s:checkbox id="verify1" name="checkMe1" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct./ <span class="tamil"><s:text name="confirmuploaded"/></span>
															<br/><input type="button"  id="savclose1" class="submitBtn button-gradient" value="Save/Close" />
														</div>
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
											<td><label id="veritext2"><strong>Please verify and Confirm the certificate</strong></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName2" value="%{DocumentFileNameWidow}"></s:hidden>
									    	<s:hidden id="candidateDocPk2" value="%{CandidateDocPkWidow}"></s:hidden>
									    	<s:hidden id="ocdFlagValue2" value="%{ocdFlagWidow}"></s:hidden>
											<td>
												<div id="myModal2" class="modal">
													<div class="modal-content">
														<div><span onclick="closeDiv(2);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
														<br/><br/>
														<s:if test='%{DocumentFileNameWidow.toLowerCase().endsWith("pdf")}'>
															<div>
																<object data="servlet/StartupServlet.do?pdf=pdf&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagWidow"/>_<s:property value="DocumentFileNameWidow"/>#toolbar=0&navpanes=0" type="application/pdf" width="100%" height="350"></object>
															</div>
														</s:if>
														<s:else>
															<div class="screenimages">
		<%--													<object data="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="DocumentFileNameWidow"/>" type="image/jpg" width="100%" height="400"></object>--%>
																<img src="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagWidow"/>_<s:property value="DocumentFileNameWidow"/>"/>
															</div>
														</s:else>
														<div>
															<s:checkbox id="verify2" name="checkMe2" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct./<span class="tamil"><s:text name="confirmuploaded"/></span>
															<br/><input type="button"  id="savclose2" class="submitBtn button-gradient" value="Save/Close" />
														</div>
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
											<td><label id="veritext3"><strong>Please verify and Confirm the certificate</strong></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName3" value="%{DocumentFileNameSports}"></s:hidden>
									    	<s:hidden id="candidateDocPk3" value="%{CandidateDocPkSports}"></s:hidden>
									    	<s:hidden id="ocdFlagValue3" value="%{ocdFlagSports}"></s:hidden>
											<td>
												<div id="myModal3" class="modal">
													<div class="modal-content">
														<div><span onclick="closeDiv(3);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
														<br/><br/>
														<s:if test='%{DocumentFileNameSports.toLowerCase().endsWith("pdf")}'>
															<div>
																<object data="servlet/StartupServlet.do?pdf=pdf&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagSports"/>_<s:property value="DocumentFileNameSports"/>#toolbar=0&navpanes=0" type="application/pdf" width="100%" height="350"></object>
															</div>
														</s:if>
														<s:else>
															<div class="screenimages">
		<%--													<object data="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="DocumentFileNameSports"/>" type="image/jpg" width="100%" height="400"></object>--%>
																<img src="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagSports"/>_<s:property value="DocumentFileNameSports"/>"/>
															</div>
														</s:else>
														<div>
															<s:checkbox id="verify3" name="checkMe3" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct./<span class="tamil"><s:text name="confirmuploaded"/></span>
															<br/><input type="button"  id="savclose3" class="submitBtn button-gradient" value="Save/Close" />
														</div>
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
											<td><label id="veritext4"><strong>Please verify and Confirm the certificate</strong></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName4" value="%{DocumentFileNameSports2}"></s:hidden>
									    	<s:hidden id="candidateDocPk4" value="%{CandidateDocPkSports2}"></s:hidden>
									    	<s:hidden id="ocdFlagValue4" value="%{ocdFlagSports2}"></s:hidden>
											<td>
												<div id="myModal4" class="modal">
													<div class="modal-content">
														<div><span onclick="closeDiv(4);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
														<br/><br/>
														<s:if test='%{DocumentFileNameSports2.toLowerCase().endsWith("pdf")}'>
															<div>
																<object data="servlet/StartupServlet.do?pdf=pdf&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagSports2"/>_<s:property value="DocumentFileNameSports2"/>#toolbar=0&navpanes=0" type="application/pdf" width="100%" height="350"></object>
															</div>
														</s:if>
														<s:else>
															<div class="screenimages">
		<%--													<object data="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="DocumentFileNameSports2"/>" type="image/jpg" width="100%" height="400"></object>--%>
																<img src="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagSports2"/>_<s:property value="DocumentFileNameSports2"/>"/>
															</div>
														</s:else>
														<div>
															<s:checkbox id="verify4" name="checkMe4" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct./<span class="tamil"><s:text name="confirmuploaded"/></span>
															<br/><input type="button"  id="savclose4" class="submitBtn button-gradient" value="Save/Close" />
														</div>
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
											<td><label id="veritext5"><strong>Please verify and Confirm the certificate</strong></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName5" value="%{DocumentFileNameWard}"></s:hidden>
									    	<s:hidden id="candidateDocPk5" value="%{CandidateDocPkWard}"></s:hidden>
									    	<s:hidden id="ocdFlagValue5" value="%{ocdFlagWard}"></s:hidden>
											<td>
												<div id="myModal5" class="modal">
													<div class="modal-content">
														<div><span onclick="closeDiv(5);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
														<br/><br/>
														<s:if test='%{DocumentFileNameWard.toLowerCase().endsWith("pdf")}'>
															<div>
															<object data="servlet/StartupServlet.do?pdf=pdf&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagWard"/>_<s:property value="DocumentFileNameWard"/>#toolbar=0&navpanes=0" type="application/pdf" width="100%" height="350"></object>
															</div>
														</s:if>
														<s:else>
		<%--														<object data="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="DocumentFileNameWard"/>" type="image/jpg" width="100%" height="400"></object>--%>
															<div class="screenimages">
																<img src="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagWard"/>_<s:property value="DocumentFileNameWard"/>"/>
															</div>
														</s:else>
														<div>
															<s:checkbox id="verify5" name="checkMe5" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct./<span class="tamil"><s:text name="confirmuploaded"/></span>
															<br/><input type="button"  id="savclose5" class="submitBtn button-gradient" value="Save/Close" />
														</div>
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
											<td><label id="veritext6"><strong>Please verify and Confirm the certificate</strong></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName6" value="%{DocumentFileNameNCC}"></s:hidden>
									    	<s:hidden id="candidateDocPk6" value="%{CandidateDocPkNCC}"></s:hidden>
									    	<s:hidden id="ocdFlagValue6" value="%{ocdFlagNCC}"></s:hidden>
											<td>
												<div id="myModal6" class="modal">
													<div class="modal-content">
														<div><span onclick="closeDiv(6);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
														<br/><br/>
														<s:if test='%{DocumentFileNameNCC.toLowerCase().endsWith("pdf")}'>
															<div>
																<object data="servlet/StartupServlet.do?pdf=pdf&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagNCC"/>_<s:property value="DocumentFileNameNCC"/>#toolbar=0&navpanes=0" type="application/pdf" width="100%" height="350"></object>
															</div>
														</s:if>
														<s:else>
				<%--											<object data="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="DocumentFileNameNCC"/>" type="image/jpg" width="100%" height="400"></object>--%>
															<div class="screenimages">
																<img src="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagNCC"/>_<s:property value="DocumentFileNameNCC"/>"/>
															</div>
														</s:else>
														<div>
															<s:checkbox id="verify6" name="checkMe6" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct./<span class="tamil"><s:text name="confirmuploaded"/></span>
															<br/><input type="button"  id="savclose6" class="submitBtn button-gradient" value="Save/Close" />
														</div>
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
											<td><label id="veritext7"><strong>Please verify and Confirm the certificate</strong></label></td>
								    		<s:hidden id="ImgDocDischarge"></s:hidden>
											<s:hidden id="documentFileName7" value="%{DocumentFileNameNSS}"></s:hidden>
									    	<s:hidden id="candidateDocPk7" value="%{CandidateDocPkNSS}"></s:hidden>
									    	<s:hidden id="ocdFlagValue7" value="%{ocdFlagNSS}"></s:hidden>
											<td>
												<div id="myModal7" class="modal">
													<div class="modal-content">
														<div><span onclick="closeDiv(7);" class="close" id="close<s:property value="#stat.index"/>"><img src="images/Close.png"/></span></div> 
														<br/><br/>
														<s:if test='%{DocumentFileNameNSS.toLowerCase().endsWith("pdf")}'>
															<div>
																<object data="servlet/StartupServlet.do?pdf=pdf&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagNSS"/>_<s:property value="DocumentFileNameNSS"/>#toolbar=0&navpanes=0" type="application/pdf" width="100%" height="350"></object>
															</div>
														</s:if>
														<s:else>
				<%--											<object data="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="DocumentFileNameNSS"/>" type="image/jpg" width="100%" height="400"></object>--%>
															<div class="screenimages">
																<img src="servlet/StartupServlet.do?img=img&user=<s:property value="#session['SESSION_USER'].username"/>&fileName=<s:property value="ocdFlagNSS"/>_<s:property value="DocumentFileNameNSS"/>"/>
															</div>
														</s:else>
														<div>
															<s:checkbox id="verify7" name="checkMe7" fieldValue="false" value="false"/>I confirm that document uploaded by me is correct./<span class="tamil"><s:text name="confirmuploaded"/></span>
															<br/><input type="button"  id="savclose7" class="submitBtn button-gradient" value="Save/Close" />
														</div>
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
									
									<img width="200" src="SignatureImage.jpg?abcd=<s:property value="%{UserId}"/>" height="50" id="inputStreamForImage1"
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
															</td>
							
						
							

							<!--    <td colspan="2" align="right"><img src="images/barcode.png" width="187" height="45" /></td>  -->
						</tr>
					</table>
				</div>
			</div>
			<s:token/>
		</s:form>

	</div>

</div>

<div  id="block20" style = "display:none;" >
<div class="hr-underline2"></div>
    
    	<ul style="margin:0; margin-left:20px; padding:0; color:red;" id="abcd">
			    <strong><s:property value="docLabel19" /> </strong>.
    	</ul>
	
</div>

