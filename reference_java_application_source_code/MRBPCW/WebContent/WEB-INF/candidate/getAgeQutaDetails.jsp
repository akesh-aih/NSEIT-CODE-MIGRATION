<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page language="java" import="java.text.SimpleDateFormat"%>
<script type="text/javascript" src="js/q.js"></script>
<script type="text/javascript" src="js/spark-md5.min.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>
<script type="text/javascript">

function showSportDiv()
{
	if($("#sportsQuaota").val()!=null && $("#sportsQuaota").val()!="" &&  $("#sportsQuaota option:selected").text()!="No")
	{		
		$(".sportDiv").show();
		$("#uploadDoc3").prop('value', 'Upload');
		$("#uploadDoc4").prop('value', 'Upload');
		$("#uploadDoc5").prop('value', 'Upload');
		$("#uploadDoc6").prop('value', 'Upload');
		$("#uploadDoc7").prop('value', 'Upload');
		
	}
	else
	{
		 $('.sportDiv').find('select').val('');
		 $('.sportDiv').find('input').val('');
		 $('.sportDiv').find('a').remove();
		$(".sportDiv").hide();
		$("#uploadedDocuments3").hide();
		$("#uploadedDocuments4").hide();
		$(".sportDiv select").removeClass('red-border');
		$(".sportDiv input").removeClass('red-border');
	}
}

function enablePoliceStationField()
{
	 var policeField = $("#unitList").val();

	 if ( policeField == 'YOUTH BRIGADE' || policeField == 'TALUK POLICE' )
	 {
		 $("#policeTR1").show();
		 var PresentPosting = $("#districtList").val();
		 
		 if(PresentPosting == "")
		 {
			 PresentPosting = $("#PresentPostingHidden").val();
			 
		 }
			var countryName = $("#PresentPosting option:selected").text();
			
			dataString = "PresentPosting="+PresentPosting
			
			if (PresentPosting != null && PresentPosting != "" ){
				//$("#stateDropdown").show();
				//$("#otherStateVal").hide();
				
				
				$.ajax({
					url: "WorkExperienceAction_getPoliceStationList.action",
					async: true,
					data: dataString,
					type: 'POST',
					beforeSend: function()
					{
							$('#policeStationList').empty();
							$("#policeStationList").append(
							           $('<option></option>').val("").html("Select Police Station")
							     );
						
					},
					error:function(ajaxrequest)
					{
						window.reload();
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
								  $("#policeStationList").append(
								           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
								     );
							 	}); 
							 	
							}
						}
					}
				});

				
			}
			else
			{
				
				 $('#policeStationList').empty();
				 $('#policeStationList').append(
					           $('<option></option>').val("").html("Select Police Station")
					     );
				// $('#PresentPostingHidden').val("");
			}
	 }
	 else
	 {
		 $("#policeTR1").hide();
		// $('#PresentPostingHidden').val("");
				
	 }
}

function enableOtherUnitField()
{
 var policeField = $("#unitList").val();
	 if (policeField == 'YOUTH BRIGADE' || policeField == 'TALUK POLICE' )
	 {	 
		 $("#policeTR1").show();
	 }
	 else
	 {
		 $("#policeTR1").hide();
	 }

	 var UnitField = $("#unitList").val();
	if(policeField == "")
	 {
		//UnitField = $("#unitListHidden").val();
	 }
	 if(UnitField == 'POLICE HEADQUARTERS')
	 {
		$("#otherTR").hide();
		 $("#PostingTrDistrict").hide();
		 $("#policeTR1").hide(); 
	 }
	 
	 else if (UnitField == 'OTHER'  )
	 {
		 $("#otherTR").show();
		 $("#PostingTrDistrict").hide();
		 $("#policeTR1").hide();
	 }
	 else
	 {
		 $("#PostingTrDistrict").show();
		 $("#otherTR").hide();
		 if(UnitField == 'POLICE HEADQUARTERS')
	 	 {
	 		$("#otherTR").hide();
	 		 $("#PostingTrDistrict").hide();
			 $("#policeTR1").hide(); 
	 	 }
		 var PresentPosting = $("#unitList").val();
		
			var countryName = $("#PresentPosting option:selected").text();
			
			dataString = "PresentPosting="+PresentPosting
			
			if (PresentPosting != null && PresentPosting!="" && UnitField!=""){
				//$("#stateDropdown").show();
				//$("#otherStateVal").hide();
				if(PresentPosting == 'POLICE HEADQUARTERS')
	 	 {
	 		$("#otherTR").hide();
			 $("#PostingTrDistrict").hide();
			 $("#policeTR1").hide(); 
	 	 }
				
				$.ajax({
					url: "WorkExperienceAction_getDistrict.action",
					async: true,
					data: dataString,
					type: 'POST',
					beforeSend: function()
					{
							$('#districtList').empty();
							$("#districtList").append(
							           $('<option></option>').val("").html("Select District")
							     );

							$('#policeStationList').empty();
							$("#policeStationList").append(
							           $('<option></option>').val("").html("Select Police Station")
							     );
						
					},
					error:function(ajaxrequest)
					{
						window.reload();
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
								           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0].replace("@", ","))
								     );
							 	}); 
							 	var uList = $("#PresentPostingHidden").val();
							 	if(uList != "")
							 	{
							 		$("#districtList").val(uList);
							 	}
							}
						}
					}
				});
			
				
			}
			else
			{
				$('#districtList').empty();
				$("#districtList").append(
				           $('<option></option>').val("").html("Select District")
				     );

				$('#policeStationList').empty();
				$("#policeStationList").append(
				           $('<option></option>').val("").html("Select Police Station")
				     );

			}
				
	 }
}


function loadComboBox()
{
	
	var dataString ;
	if(($("#wardname").val()!=null && $("#wardname").val()!=''))
	{
		var presentRank;
		var department;
		if($("#wardname").val()=='1')
		{
			presentRank='Assistant';
			department="1";
			$("#unitTr").hide();
			$("#PostingTrDistrict").hide();
			$("#policeTR1").hide();
			$("#postingTrMini").show();
			$("#unitList").val("");
			$("#districtList").val("");
			$("#policeStationList").val("");
		}
		if($("#wardname").val()=='2')
		{
			presentRank='Administrative Officer';
			department="4";
			$("#unitTr").show();
			$("#PostingTrDistrict").show();
			$("#policeTR1").show();
			$("#postingTrMini").hide();
			$("#presentPostingMini").val("");
			
		}

		dataString = "val="+$("#wardname").val()+"&dept="+department;
		$.ajax({
			url: 'CandidateAction_getRefKeyValMasterMap.action',
			async: true,
			data: dataString,
			type: 'POST',
			beforeSend: function()
			{
				
				
					$('#presentRank').empty();
					$('#presentRank').append(
					           $('<option></option>').val("").html("Select Rank")
					     );
				
				
				
			},
			error:function(ajaxrequest)
			{
				window.reload();
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
						  
							$("#presentRank").removeAttr("disabled");
						  $("#presentRank").append(
						           $('<option></option>').val(nameAndIDArr[0]).html(nameAndIDArr[0])
						     );
					 	}); 
						var rank = $("#presentRankHidden").val();
					 	if(rank != "")
					 	{
						 	//alert(rank);
					 		$("#presentRank").val(rank);
					 	}
					}
				}
			}
		});
		








		
		dataString = "presentRank="+presentRank;
		if (presentRank != null &&  presentRank != "" ){
			//$("#stateDropdown").show();
			//$("#otherStateVal").hide();
			
			
			$.ajax({
				url: "WorkExperienceAction_getUnit.action",
				async: true,
				data: dataString,
				type: 'POST',
				beforeSend: function()
				{
						$('#unitList').empty();
						$("#unitList").append(
						           $('<option></option>').val("").html("Select Unit")
						     );
						$('#districtList').empty();
						$("#districtList").append(
						           $('<option></option>').val("").html("Select District")
						     );
						$('#policeStationList').empty();
						$("#policeStationList").append(
						           $('<option></option>').val("").html("Select Police Station")
						     );
					
				},
				error:function(ajaxrequest)
				{
					window.reload();
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
							  $("#unitList").append(
							           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
							     );
						 	}); 
						 	
						}
					}
				}
			});
	}
	}
	else
	{
		$('#presentRank').empty();
				$('#presentRank').append(
				           $('<option></option>').val("").html("Select Rank")
				     );
	}
	//hideUDP();
}


function showWardsDiv()
{
	if($("#wardsQuaota").val()!=null && $("#wardsQuaota").val()!="" &&  $("#wardsQuaota option:selected").text()!="No")
	{		
	 	$(".wardQuotaDiv").show();
		$(".wardsDiv").show();
			
	}
	else
	{
		 $('.wardsDiv').find('input').val('');
		 $('.wardsDiv').find('select').val('');
		 $('.wardsDiv').find('select').val('');
		 $('.wardsDiv').find('a').remove();
		 $('#unitList').empty();
		 $('#unitList').append(
			           $('<option></option>').val("").html("Select Unit")
			     );
		 $('#presentRank').empty();
		 $('#presentRank').append(
			           $('<option></option>').val("").html("Select Rank")
			    );
		 $('#districtList').empty();
		 $('#districtList').append(
			           $('<option></option>').val("").html("Select District")
			     );
		 
		$(".wardsDiv").hide();
		$(".pfDiv").hide();
		$(".wardsDiv select").removeClass('red-border');
		$(".wardsDiv input").removeClass('red-border');
	}
}



$(document).ready(function() {
  var r='<s:property value="%{applyNotify}"></s:property>';
  var s=r.split(" ");
  var x=s[0].split("-");
  x[0]=x[0];
  x[1]=x[1]-1;
  x[2] = x[2];
  
  var appEndDate='<s:property value="%{appEndDate}"></s:property>';
  var appEndDateArr=appEndDate.split(" ");
  var aEr=new Array();
  aEr=appEndDateArr[0].split("-");
  var strDate=aEr;
  aEr[0]=parseInt(strDate[0]);
    aEr[1]=parseInt(strDate[1]);
      aEr[2]=parseInt(strDate[2]);
      
  var specialoption='<s:property value="%{getAgeQuotaDetailsRadioChk}"></s:property>';
  var yearOfExperience='<s:property value="%{yearOfExperience}"></s:property>';
  var genderval='<s:property value="%{genderVal}"></s:property>';
  var categoryVal='<s:property value="%{categoryVal}"></s:property>';
  var status='<s:property value="%{status}"></s:property>';
  var age='<s:property value="%{age}"></s:property>';
  var workExp ='<s:property value="%{Govtemp}"></s:property>';
  var policedeptVal ='<s:property value="%{policedeptVal}"></s:property>';
  
  console.log("specialoption :"+specialoption+"  genderval :"+genderval + "  status :"+status);
  console.log("workExp :"+workExp+"  policedeptVal :"+policedeptVal);
  
  var maximumAge_DateofEnlishment;
  if(specialoption == '' || specialoption == null || specialoption == undefined){
    //var maximumAge_DateofEnlishment=new Date();
	  maximumAge_DateofEnlishment=new Date(x[0]-5,x[1],x[2]);
    }else{
    maximumAge_DateofEnlishment=new Date(x[0]-5,x[1],x[2]);
    }
  
//age relaxtion section ends
  if(specialoption!= null && specialoption != ''){
    if(specialoption!= null && specialoption== '1'){
          if(genderval!=null && (genderval=='Male' ||  genderval=='Transgender') ){
            $("#exserviceWomanTr").hide();
            $("#widowTr").hide();
            $("#exserviceManTr").show();
            $("#exServiceman").val('64');
            showServiceDiv();
            $("#exServiceman").attr('disabled','disabled');
            $("#exserviceManTrHid").val('64');
            $("#exServiceWomanTrHid").val('63');
            
          }
   } 
    if(specialoption!= null && specialoption== '2'){
         if(age!= null && age>35 ){
           $("#exserviceManTr").hide();
           $("#widowTr").hide(); 
         }else{
           $("#widowTr").show();
           $("#widow").val('Yes');
           showWidowDiv();
           $("#widow").attr('disabled','disabled');
           $("#widowTrHid").val('Yes');
           $("#exserviceWomanTr").hide();
           $("#exserviceManTr").hide();
        }
          }
    if(specialoption!= null && specialoption== '3'){
      if(yearOfExperience != null && yearOfExperience == '1'){
            $("#nccTr").hide();
            $("#nssTr").hide();
            $("#sportsTr").hide();
            $("#widowTr").hide();
            $("#exserviceWomanTr").hide();
            $("#exserviceManTr").hide();
            }else{
            $("#exserviceManTr").hide();
            $("#exserviceWomanTr").hide();
            $("#widowTr").hide();
            $("#extraQualificationTr").hide();
            }
        
      }
    if(specialoption!= null && specialoption== '4'){
        if(genderval!=null && (genderval=='Female')){
          
          $("#exserviceManTr").hide();
          $("#widowTr").hide();
          $("#widow").val('No');
      	  showWidowDiv();
          $("#exserviceWomanTr").show();
          $("#exServiceWoman").val('64');
            showServiceDiv();
            $("#exServiceWoman").attr('disabled','disabled');
            $("#exServiceWomanTrHid").val('64');
            $("#exserviceManTrHid").val('63');
            
        }
        }
  }else{
	  //For Normal within age Candidate
	  
    if(genderval!=null && (genderval=='Male' ||  genderval=='Transgender')){
    	
    	if(workExp != null && workExp == 'Y' && policedeptVal == 'Police' ){
    		
    		$("#exserviceWomanTr").hide();
            $("#widowTr").hide();
            $("#widow").val('No');
            $("#exserviceManTr").hide();
            $("#exServiceman").val('63');
            $("#exServiceWoman").val('63');
            showServiceDiv();
            showWidowDiv();
    	}
    	else{
    		$("#exserviceWomanTr").hide();
    		$("#exServiceWoman").val('63');
            $("#widowTr").hide();
            $("#widow").val('No');
            $("#exserviceManTr").show();
            showServiceDiv();
            showWidowDiv();
    	}
      
      }
    else if(genderval!=null && (genderval=='Female')){
    	
		if(workExp != null && workExp == 'Y' && policedeptVal == 'Police' ){
    		
    		$("#exserviceWomanTr").hide();
            $("#widowTr").hide();
        	$("#widow").val('No');
            $("#exserviceManTr").hide();
            $("#exServiceWoman").val('63');
            $("#exServiceman").val('63');
            showServiceDiv();
            showWidowDiv();
    	}
    	else{
    		$("#exserviceWomanTr").show();
            $("#exserviceManTr").hide();
            $("#exServiceman").val('63');
            showServiceDiv();
            
            if(status != null && status == 'Single' ){
            	$("#widowTr").show();
            	showWidowDiv();
            }
            else{
            	$("#widowTr").hide();
            	$("#widow").val('No');
            	showWidowDiv();
            }
    	}
        
    }
    
    }
 //End of Normal within age Candidate
        
  	<s:if test='%{additionalDetailsBean.sportsQuaota=="Yes"}' >
		$(".sportDiv").show();
	</s:if>
 
  <s:if test='%{additionalDetailsBean.extraQualification=="Yes"}'>
  $(".extraQualificationDiv").show();
  </s:if>

 
  /*<s:if test='%{additionalDetailsBean.exServiceman=="64" }'>
  $("#exserviceManTr").show();
  $(".exserviceDiv").show();
  $("#widow").attr('disabled','disabled');
  </s:if>
  <s:if test='%{additionalDetailsBean.exServiceWoman=="64" }'>
  $("#exserviceWomanTr").show();
  $(".exserviceDiv").show();
  $("#widow").attr('disabled','disabled');
  </s:if>
 
  <s:if test='%{additionalDetailsBean.widow=="Yes" }'>
  $(".widowDiv").show();
  $("#exServiceman").attr('disabled','disabled');
  $("#exServiceWoman").attr('disabled','disabled');
  </s:if>*/
  
  <s:iterator value="errorField">
  
  $("input[name='additionalDetailsBean.<s:property />']").addClass('red-border');
  $("select[name='additionalDetailsBean.<s:property />']").addClass('red-border');
  $("input[name='<s:property />']").addClass('red-border');     
  </s:iterator>
  $('.stage').each(function(i, obj) {
        //test
        var id=obj.id;
        var lastCahr=id.charAt(id.length-1);
        if(($(this).val()!=null && $(this).val()!='')|| ($('#'+id+' option').length > 1))
        {
          $('#stageLevelName'+lastCahr).removeAttr("disabled");
        }
    

});
  $('.category').each(function(i, obj) {
        //test
        var id=obj.id;
        var lastCahr=id.charAt(id.length-1);
        if(($(this).val()!=null && $(this).val()!='')|| ($('#'+id+' option').length > 1))
        {
          $('#categoryLevelName'+lastCahr).removeAttr("disabled");
          
        }
    });
  $('.sportCategory').each(function(i, obj) {
        //test
        var id=obj.id;
        var lastCahr=id.charAt(id.length-1);
        if(($(this).val()!=null && $(this).val()!='')|| ($('categoryName'+lastCahr).val()=='Sports'))
        {
          $('#categorySportsName'+lastCahr).removeAttr("disabled");
          
        }
    });

 /* if($('#successMessage') && $('#successMessage').text().trim() == "Candidate Details saved successfully"){
    //$('#saveUpdate').attr('disabled',true);
  }
  if(applicationForm){
    applicationForm.addEventListener('change', function (evt) {
      $('#saveUpdate').removeAttr("disabled");
    });
    applicationForm.addEventListener('input', function (evt) {
      var buttonDisabled=$('#saveUpdate').attr("disabled");
      if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
        $('#saveUpdate').removeAttr("disabled");
        $('#continueForUpdate').attr("disabled","disabled");
        
      }
      
    });
  }*/
  
  $("#dateOfStartEvent").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: maximumAge_DateofEnlishment,
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		defaultDate: maximumAge_DateofEnlishment, //new Date('1 January 1986'),
		onSelect: function( selectedDate ) {
	       
	        var buttonDisabled=$('#saveUpdate').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdate').removeAttr("disabled");
			}
	      
	      }
		//onSelect: function( selectedDate ) {
			//alert(selectedDate);
		//	$( "#dateOfsportsCertificate" ).datepicker( "option", "minDate", selectedDate );
		//	var a=selectedDate.split("-");
		//	var months = ["jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"];
		//    month1 = months.indexOf(a[1].toLowerCase());
		//	if(new Date(x[0]-5,x[1],x[2]) <= new Date(a[2],month1+1,a[0])){
		//   		 $( "#dateOfComplEvent" ).datepicker( "option", "minDate", selectedDate );
		//	 	$( "#dateOfComplEvent" ).datepicker( "option", "maxDate", 0 );
		//	}
		//	else{
		//		$( "#dateOfComplEvent" ).datepicker( "option", "minDate",  new Date(x[0]-5,x[1],x[2]) );
	     //  		$( "#dateOfComplEvent" ).datepicker( "option", "maxDate", 0 );
		//	}
	       
	  //    }
  });
  $("#dateOfComplEvent").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: maximumAge_DateofEnlishment,
		maxDate: 0,// new Date(x[0],x[1],x[2]),
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		defaultDate: maximumAge_DateofEnlishment, //new Date('1 January 1986'),
		onSelect: function( selectedDate ) {
		       
	        var buttonDisabled=$('#saveUpdate').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdate').removeAttr("disabled");
			}
	      
	      }
  });
  $("#dateOfsportsCertificate").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: maximumAge_DateofEnlishment,
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		defaultDate: maximumAge_DateofEnlishment, // new Date('1 January 1986'),
		 onSelect: function( selectedDate ) {
	        $( "#dateOfStartEvent" ).datepicker( "option", "maxDate", selectedDate );
	        var buttonDisabled=$('#saveUpdate').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdate').removeAttr("disabled");
			}
	      
	        //$( "#dateOfComplEvent" ).datepicker( "option", "maxDate", selectedDate );
	      }
  });

  $("#dateOfwardsCertificate").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate:new Date(x[0],x[1],x[2]),
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		onSelect: function( selectedDate ) {
		       
	        var buttonDisabled=$('#saveUpdate').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdate').removeAttr("disabled");
			}
	      
	      }
  });
  
  $("#dateOfEnlistment").datepicker({
    showOn: "button",
    changeMonth: true,
    changeYear: true,
    yearRange: range,
  	minDate: new Date("July 01, 1973"),
    maxDate: maximumAge_DateofEnlishment,
    buttonImageOnly: true,
    buttonImage: "images/cale-img.gif",
    dateFormat: 'dd-M-yy',
    //defaultDate: new Date('1 January 2000'),
    onSelect: function( selectedDate ) {
        //$( "#dateOfDischarge" ).datepicker( "option", "maxDate", -1 );
           // $( "#dateOfDischarge" ).datepicker( "option", "minDate", selectedDate );
           var a=selectedDate.split("-");
        var months = ["jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"];
          month1 = months.indexOf(a[1].toLowerCase());
            if(new Date(x[0]-3,x[1],x[2]) <= new Date(a[2],month1+1,a[0]))
             $( "#dateOfDischarge" ).datepicker( "option", "minDate", selectedDate );
        else
          $( "#dateOfDischarge" ).datepicker( "option", "minDate",  new Date(x[0]-3,x[1],x[2]) );
            var buttonDisabled=$('#saveUpdate').attr("disabled");
        if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
          $('#saveUpdate').removeAttr("disabled");
           $('#continueForUpdate').attr("disabled","disabled");
          
        }
           
          }
    });

  $("#dateOfExCertificate").datepicker({
      showOn: "button",
      changeMonth: true,
      changeYear: true,
      
      minDate: new Date(parseInt(x[0])-29,x[1],x[2]) ,
      maxDate:new Date(parseInt(x[0])+1,x[1],x[2]) ,
      buttonImageOnly: true,
      buttonImage: "images/cale-img.gif",
      dateFormat: 'dd-M-yy',
       onSelect: function( selectedDate ) {
            $( "#dateOfEnlistment" ).datepicker( "option", "maxDate", selectedDate );
            var buttonDisabled=$('#saveUpdate').attr("disabled");
        if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
          $('#saveUpdate').removeAttr("disabled");
          $('#continueForUpdate').attr("disabled","disabled");
          
        }
            //$( "#dateOfDischarge" ).datepicker( "option", "maxDate", selectedDate );
          },
          
      });
  
  $("#dateOfDischarge").datepicker({
    showOn: "button",
    changeMonth: true,
    changeYear: true,
    //yearRange: '2010:2013',
    minDate: new Date(x[0]-3,x[1],x[2]),
    maxDate: new Date(aEr[0],aEr[1]-1,aEr[2]),
    //maxDate: new Date("08/09/2019"),//dd-mm-yyy
    buttonImageOnly: true,
    buttonImage: "images/cale-img.gif",
    dateFormat: 'dd-M-yy',
     onSelect: function( selectedDate ) {
      
          //$( "#dateOfEnlistment" ).datepicker( "option", "maxDate", selectedDate );
          
          $( "#dateOfExCertificate" ).datepicker( "option", "minDate", selectedDate );
          //$( "#dateOfExCertificate" ).datepicker( "option", "maxDate", -1 );
          var buttonDisabled=$('#saveUpdate').attr("disabled");
      if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
        $('#saveUpdate').removeAttr("disabled");
        $('#continueForUpdate').attr("disabled","disabled");
        
      }
        }
    });

var serverDate =" <%= new SimpleDateFormat("yyyy-MM-dd").format(new Date())%>";
     // alert(dateStr);
  //var srdate = serverDate.split(" ");
  var splitdate = serverDate.split("-");
      splitdate[0]=splitdate[0];
      splitdate[1]=splitdate[1]-1;
      splitdate[2]=splitdate[2];


   $("#dateOfDeath").datepicker({
      showOn: "button",
      changeMonth: true,
      changeYear: true,
      yearRange: range,
      minDate: new Date("07/01/1972"),
      maxDate:new Date(splitdate[0],splitdate[1],splitdate[2]),
      buttonImageOnly: true,
      buttonImage: "images/cale-img.gif",
      dateFormat: 'dd-M-yy',
      onSelect: function( selectedDate ) {
            $( "#dateOfdeathCertificate" ).datepicker( "option", "minDate", selectedDate );
            $( "#dateOfdeathCertificate" ).datepicker( "option", "maxDate", new Date(splitdate[0],splitdate[1],splitdate[2]) );
            var buttonDisabled=$('#saveUpdate').attr("disabled");
        if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
          $('#saveUpdate').removeAttr("disabled");
          $('#continueForUpdate').attr("disabled","disabled");
          
        }
           
          }
      
      });

   $("#dateOfdeathCertificate").datepicker({
      showOn: "button",
      changeMonth: true,
      changeYear: true,
      yearRange: range,
      minDate: new Date("07/01/1972"),
      maxDate:new Date(splitdate[0],splitdate[1],splitdate[2]),
      buttonImageOnly: true,
      buttonImage: "images/cale-img.gif",
      dateFormat: 'dd-M-yy',
       onSelect: function( selectedDate ) {
            $( "#dateOfDeath" ).datepicker( "option", "maxDate", selectedDate );
            var buttonDisabled=$('#saveUpdate').attr("disabled");
        if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
          $('#saveUpdate').removeAttr("disabled");
          $('#continueForUpdate').attr("disabled","disabled");
          
        }
          
          }
      });


   var wardName='<s:property value="%{additionalDetailsBean.wardname}" />';
   //alert("wardName"+wardName);

   if(wardName!=null && wardName == "1")
 	{
 		$("#unitTr").hide();
 		$("#PostingTrDistrict").hide();
 		$("#policeTR1").hide();
 		$("#postingTrMini").show();
 		$("#unitList").val("");
 		$("#districtList").val("");
 		$("#policeStationList").val("");
 	}
 	else
 	{
 		$("#unitTr").show();
 		$("#PostingTrDistrict").show();
 		$("#policeTR1").show();
 		$("#postingTrMini").hide();
 		$("#presentPostingMini").val("");
 	}
   
	//showWardsDiv();


	//this is to populate the depending drop downs value for ward quota
	<s:if test='%{additionalDetailsBean.wardsQuaota=="Yes"}'>
	//alert("Inside ward YES");
 	//$(".wardQuotaDiv").show();
	//$(".wardsDiv").show();
 	
	  var policeStation='<s:property value="%{additionalDetailsBean.policeStation}"></s:property>';
	  var presentPostingUnit='<s:property value="%{additionalDetailsBean.presentPostingUnit}"></s:property>';
	  	
	  	if(policeStation!=null && policeStation!=""){
	  						$("#policeTR1").show();
	  						}
	  					if(presentPostingUnit=="POLICE HEADQUARTERS" || presentPostingUnit=="OTHER"){
	  						$("#policeTR1").hide();
	  						$("#PostingTrDistrict").hide();
	  						}
	  				
	  					if(presentPostingUnit=="YOUTH BRIGADE" || presentPostingUnit=="TALUK POLICE"){
	  						$("#policeTR1").show();
	  						$("#PostingTrDistrict").show();
	  					}else{
	  						$("#policeTR1").hide();
	  						$("#PostingTrDistrict").hide();
	  					}
	  					if(presentPostingUnit=="DPO/CPO" || presentPostingUnit=="RANGE OFFICE" || presentPostingUnit=="TAMIL NADU SPECIAL POLICE" || presentPostingUnit=="SPECIAL UNIT" || presentPostingUnit=="BATTALION OFFICE" || presentPostingUnit=="ARMED RESERVE" || presentPostingUnit=="OTHER")
	  					{
	  						$("#PostingTrDistrict").show();
	  						$("#policeTR1").hide();
	  					}
	  					if(presentPostingUnit=="OTHER"){
	  						$("#otherTR").show();
	  						$("#policeTR1").hide();
	  						$("#PostingTrDistrict").hide();
	  					}
	  				
	  </s:if>

	  //To hide and show the ward quota Div depending upon special option ,Gender,Marital Status and Work Experience
	  if(specialoption!=null && (specialoption=="1" || specialoption=="2" || specialoption=="3" || specialoption=="4")){
			
			 $(".wardQuotaDiv").hide();
			 $("#wardsQuaota").val("");
			 $('.wardsDiv').find('input').val('');
			 $('.wardsDiv').find('select').val('');
			 $('.wardsDiv').find('select').val('');
			 $("#unitList").val("");
			 $("#districtList").val("");
			 $("#policeStationList").val("");
			 $("#presentPostingMini").val("");
			 $("#presentRank").val("");	
			 
		}
	  else if(genderval != null && (genderval == "Male" || (genderval=="Female" && status=="Single") || genderval=="Transgender") && (workExp!=null && workExp=="N"))
	  {
		  //$(".wardQuotaDiv").show();
	  }else
	  {
		 	 $(".wardQuotaDiv").hide();
		 	 $("#wardsQuaota").val("");
			 $('.wardsDiv').find('input').val('');
			 $('.wardsDiv').find('select').val('');
			 $('.wardsDiv').find('select').val('');
			 $("#unitList").val("");
			 $("#districtList").val("");
			 $("#policeStationList").val("");
			 $("#presentPostingMini").val("");
			 $("#presentRank").val("");	
	  }
	  $("#msgg").show();
});
//$(document).ready end

function calculateAge(dateText)
{ 
  var r='<s:property value="%{applyNotify}"></s:property>';
  var s=r.split(" ");
  var dateValue = dateText.split("-");
  var m_names = new Array("Jan", "Feb", "Mar", 
      "Apr", "May", "Jun", "Jul", "Aug", "Sep", 
      "Oct", "Nov", "Dec");
  var m_val = jQuery.inArray(dateValue[1],m_names)+1;
  var birth = new Date(dateText);
  //var asOnDate = "01-Jul-2017";
  var asOnDate = moment(s[0]).format("DD-MMM-YYYY");
  var dateValue1 = asOnDate.split("-");
  var m_names1 = new Array("Jan", "Feb", "Mar", 
      "Apr", "May", "Jun", "Jul", "Aug", "Sep", 
      "Oct", "Nov", "Dec");
  var m_val1 = jQuery.inArray(dateValue1[1],m_names1)+1;
  
  var today = new Date(asOnDate);
    var nowyear = dateValue1[2];
    var nowmonth = m_val1;
    var nowday = dateValue1[0];

    var birthyear = dateValue[2];
    var birthmonth = m_val;
    var birthday = dateValue[0];

    var age = nowyear - birthyear;
    var age_month = nowmonth - birthmonth;
    var age_day = nowday - birthday;  
    if(age_month < 0 || (age_month == 0 && age_day <0)) {
            age = parseInt(age);
        }
    //var course= $("#disciplineType").val();
  if(age_month<0)
   {
    var test = parseInt(age) - 1;
    var test1 = 12 + parseInt(age_month);
    if(test<0){
      $("#age").val("0");
      $("#ageInMonths").val("0");
    }else{
    $("#age").val(test);
    $("#ageInMonths").val(test1);
    }
   }else{
      if(age<0){
      $("#age").val("0");
      $("#ageInMonths").val("0");
    }else{
      $("#age").val(age);
      $("#ageInMonths").val(age_month);
    }
   }
    
    /*if(course!=''){
           
    var min = $("#minAge").html();
    var max = $("#maxAge").html();
    if(age<=max && age>=min)
    {
       
    }
    else
    {
      alert('Your age does not meet the criteria for selected course. Click on OK to continue.');
    }
    }*/
}


function showServiceDiv()
{
	
  if(($("#exServiceman").val()!=null && $("#exServiceman").val()!="" &&  $("#exServiceman option:selected").text()!="No")
     || ($("#exServiceWoman").val()!=null && $("#exServiceWoman").val()!="" &&  $("#exServiceWoman option:selected").text()!="No") )
  { 
      if($("#exServiceman option:selected").text() =="Yes"){
    	  $("#exserviceManTrHid").val('64');
    	  $("#exServiceWomanTrHid").val('63');
      }
	 if($("#exServiceWoman option:selected").text() =="Yes"){
		 $("#exServiceWomanTrHid").val('64');
		 $("#exserviceManTrHid").val('63');
      }
	  
    $(".exserviceDiv").show();
    $('#applicationForm').find('input.red-border:not("button")').each(function(){
        $(this).removeClass("red-border");
    });
    $('#applicationForm').find('select.red-border:not("button")').each(function(){
        $(this).removeClass("red-border");
    });
    $("#uploadDoc3").prop('value', 'Upload');
    $("#uploadDoc2").prop('value', 'Upload');
    
    $("#widow").attr('disabled','disabled');
    //$("#wardsQuaota").attr('disabled','disabled');
  }
  else
  {
	$("#exServiceWomanTrHid").val('63');
	$("#exserviceManTrHid").val('63');   
    
    $('.exserviceDiv').find('input').val(''); 
    $(".exserviceDiv").hide();
    $("#msgg").hide();
    $("#uploadedDocuments2").hide();

    var lAgeRelaxationType="ex";
    var lDisciplineType=$('#disciplineType').val();
     
    dataString = "AgeRelaxationType="+lAgeRelaxationType+"&DisciplineType="+lDisciplineType

    $("#widow").removeAttr('disabled');
    $("#wardsQuaota").removeAttr('disabled');
    $(".exserviceDiv select").removeClass('red-border');
    $(".exserviceDiv input").removeClass('red-border');
    
    } 


}

function showWidowDiv()
{
	
  if($("#widow").val()!=null && $("#widow").val()!="" &&  $("#widow option:selected").text()!="No")
  {   
	$("#widowTrHid").val('Yes');
    $(".widowDiv").show();
   // $("#msgg").hide();
    $("#uploadDoc").prop('value', 'Upload');
    $("#uploadDoc3").prop('value', 'Upload');
    $("#uploadDoc2").prop('value', 'Upload');
    $("#exServiceman").attr('disabled','disabled');
    $("#exServiceWoman").attr('disabled','disabled');
    $("#wardsQuaota").prop('disabled','disabled');
    $("#departmentQuaota").prop('disabled','disabled');
  }
  else
  {
	$("#widowTrHid").val('No');
    $('.widowDiv').find('input').val(''); 
    $(".widowDiv").hide();
    $("#msgg").hide();
    $("#uploadedDocuments").hide();
    $("#exServiceman").removeAttr('disabled');
    $("#exServiceWoman").removeAttr('disabled');
    $("#wardsQuaota").removeAttr('disabled');
    $(".widowDiv select").removeClass('red-border');
    $(".widowDiv input").removeClass('red-border');
    $("#departmentQuaota").removeAttr('disabled');
  }
}

  function showExtraQualificationDiv() {
    if($("#extraQualification").val()!=null && $("#extraQualification").val()!="" &&  $("#extraQualification option:selected").text()!="No")
    { 
      // show div
      $(".extraQualificationDiv").show();
     // $("#extraQualUploadButton").prop('value', 'Upload');
    } else {
      // hide div
      $('#qualBEExtc').attr('checked', false);
      $('#qualBscCSBscIT').attr('checked', false);
      $('#qualBEBTechCSIT').attr('checked', false);
      $('#qualPGCA').attr('checked', false);
      $('#qualMEMTechCommSys').attr('checked', false);
      $('#qualMEMTechCSIT').attr('checked', false);
      $('#qualMCA').attr('checked', false);
      $('#checkExtraQualification').attr('checked', false); 
      $(".extraQualificationDiv").hide();
      $("#uploadedExtraQualDocuments").hide();
      $("#msgg").hide();
    }   
  }


  function alphaNumericwithSplChar(e)
  {
    var unicode=e.charCode? e.charCode : e.keyCode;
    if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9 && unicode != 32
     && unicode != 46  && unicode != 40 && unicode != 32 && unicode != 41 && unicode != 47 && unicode != 92 && unicode !=95 && unicode != 45)
      return false;
  }
  function alphabetsWithSpacenChars(e)
  {
    var unicode=e.charCode? e.charCode : e.keyCode;
    //alert("unicode"+unicode);
    if((unicode < 97 || unicode > 122 ) &&  (unicode < 48 || unicode > 57) && (unicode < 65 || unicode > 90) && unicode != 32 && unicode != 8 && unicode != 9 && unicode != 46  && unicode != 39 && unicode != 44 && unicode != 45 && unicode != 47 && unicode != 92 && unicode != 13 )
      return false;
  }
  
  function alphabetswithspace(e)
  {
  	var unicode=e.charCode? e.charCode : e.keyCode;
  	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && unicode != 8 && unicode != 32  && unicode != 9)
  		return false;
  }
  function alphanumeric(e){

	    var k;
	    document.all ? k = e.keyCode : k = e.which;
	    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57));
	
}

function alphanumericDesignation(e){

	    var k;
	    document.all ? k = e.keyCode : k = e.which;
	    return ((k > 64 && k < 91) || (k > 96 && k < 123) || k == 8 || (k >= 48 && k <= 57) || k==32);
	
}

$(".checkbox-handle").change(function(){
	  console.log($(this).prop("checked"));
	  $(this).attr("checked",$(this).prop("checked"))
})  

</script>
<style>
#msgg li { float:left; }
#msgg br { height:1px; float:left; }
#error-massage{padding:0;}
.ui-datepicker-trigger {margin-top:7px; }
</style>
    
    
<s:if test="%{#attr.dataNotFound!=''}">
  <div class="error-massage-text">
    <s:property value="#attr.dataNotFound" />
  </div>
</s:if>

<div class="titlebg">
  <div class="container"> 
    <h1 class="pageTitle">Quota / Special Marks / <span class="tamil"><s:text name="additional.specialmarksHeading"/> </span>  &nbsp;
   <!-- & Age Relaxation / <span class="tamil"><s:text name="additional.specialmarksHeadingAgeRelaxation"/> </span>  --> 
  <div class="userid_txt">
  <s:if test="%{#session['SESSION_USER'] != null}">
    <strong>User ID / <span class="tamil"><s:text name="applicationForm.userId"/></span></strong> - <s:label value="%{#session['SESSION_USER'].username}" /><br/>
  </s:if>
  </div>
  </h1>
   </div>
</div>

<s:form id="applicationForm" action="CandidateAction" enctype="multipart/form-data" method="post" autocomplete="off">
<div id="unwrapForm" class="unwrapForm">
<s:hidden name="handicappedValue" id="handicappedValue"/>
<s:hidden name="viewFlag" id="viewFlag" value="true"></s:hidden>
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:hidden name="isDataFound" value='<s:property value="candidateDataFilled"/>'/>
<s:hidden name="degreeVal" id="degreeVal"/>
<s:hidden name="cateoryValue" id="cateoryValue"/>
<s:hidden name="additionalDetailsBean.paramvalue" id="paramvalue" value="quotaDetailsForm"></s:hidden>
<s:hidden name="testCenterApply" value="%{testCenterApply}"></s:hidden>
<div id="dashboard" class="container common_dashboard">

<s:actionerror />
<s:hidden id='hddAddressChkBox'></s:hidden>
<div align="center" style="font-size: 22px;color:  brown;"><strong></strong></div>


  <div align="left" >
  
  <s:if test='%{getAgeQuotaDetailsRadioChk == 3 || yearOfExperience == "1"}'>

  	<div style="float:left;"><span class="lighttext">NOTE: </span><span class="lighttext">Please click on Save & Continue to Proceed Furthur.</span></div>
  </s:if>
  <s:else>
  <div style="float:left;"><span class="lighttext"><s:text name="applicationForm.note"/> </span> <span class="manadetory-fields">*</span> <span class="lighttext"><s:text name="applicationForm.note1"/></span><span class="lighttext"><span class="tamil"><s:text name="applicationFormT.note"/></span>: <span class="manadetory-fields">*</span> <span class="tamil"><s:text name="applicationFormT.note1"/></span></span> 
  
   <!--
  <br>
    Kindly upload the documents which does not have any of the mentioned special character's in its file name  %#&+`$+={}[]|\:/"'<>,.? <span class="tamil"><s:text name="docSplChar"/></span> 
    <br>&nbsp;<br>
 --> 
  </div>
  </s:else> 
  
</div>
 

<br />
<br/>
<div id="error-massage" style="display:none">
    <div class="error-massage-text" style="margin:0; padding:0;">
      <ul style="margin:1; margin-left:-25px; margin-bottom:0px; padding:2;" id="errorMessages">
      </ul>
    </div>
    
</div>
<div id="error-massageAppForm" style="display:none;color:red;" class="error-massage">
        <ul style="margin-left:-41px;" id="error-ulAppForm">
        </ul>
</div>
<s:actionmessage escape="false" cssClass="msgg" id="msgg" />


<s:if test='%{candidateDetailsSuccFlag=="true"}'>
<br />
  <div style="border:#999 1px solid;padding:3px;color:green;" id="successMessage">
  <s:property value="candidateDetailsSuccMsg"/>
  </div>
<br/>
</s:if>


<br/> 


<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;">
   
  <div > 
    <h1 class="pageTitle"><b>Quota /<span class="tamil"><s:text name="additional.quota"/></span></b>
    </h1>
   </div>
   <div class="hr-underline2"></div>
   
     <table class="contenttableNew" >
     
    <tr id="sportsTr">
        <td width="80%" >Would you like to avail Sports Quota? / <span class="tamil"><s:text name="additional.sports"/></span> <span class="manadetory-fields"> *</span></td>
        
        <td width="20%" colspan="3">
          <s:select list = "additionalDetailsBean.sportsQuaotaList" name = "additionalDetailsBean.sportsQuaota" label = "Name" 
        id = "sportsQuaota" value="%{additionalDetailsBean.sportsQuaota}" onchange="return showSportDiv();"
        />  
        </td>
       </tr>
</table>

<!-- starting of sports details -->
	<div class="sportDiv" style="display:none">
  	 <table class="contenttableNew" >
  	 <tr   class="sportDiv" style="display:none">
 	 <td>Sport In Which Participated / <span class="tamil"><s:text name="additional.sportsName"/></span>  <span class="manadetory-fields">*</span></td>
      	<td colspan="3">
      			<s:select list = "additionalDetailsBean.sportsList" name = "additionalDetailsBean.sportsName" label = "Name" headerKey="" headerValue="Select Sports"
				id = "sportsList" value="%{additionalDetailsBean.sportsName}" 
				/>	
	  	</td>
      	
    </tr>
    <tr class="sportDiv" style="display:none">
    	  <td>Event / Tournament Name / <span class="tamil"><s:text name="additional.eventName"/></span>  <span class="manadetory-fields">*</span></td>
      	<td colspan="3">
      			 <s:textfield id="eventName" name="additionalDetailsBean.eventName" onkeypress="return alphaNumericwithSplChar(event);" onpaste="return false;" value="%{additionalDetailsBean.eventName}" maxlength="50"> </s:textfield>	
	  	</td>
      	
    </tr>
    <tr class="sportDiv" style="display:none">
     <td>Date Of Start Of Event / <span class="tamil"><s:text name="additional.eventStartDate"/></span>  <span class="manadetory-fields">*</span> <br/><!-- <span class="lighttext">Note:(A Departmental candidate is one who is an ONGC employee.Wards of ONGC Employees are not Departmental candidate)</span> --></td>
     
      	<td colspan="3">
      			 <s:textfield id="dateOfStartEvent" name="additionalDetailsBean.dateOfStartEvent" value="%{additionalDetailsBean.dateOfStartEvent}" readonly="true"> </s:textfield>
				
	  	</td>
      	
    </tr>
  
    
   
    <tr class="sportDiv" style="display:none">
    
	      <td>Date Of Completion Of Event / <span class="tamil"><s:text name="additional.eventEndDate"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
		   <s:textfield id="dateOfComplEvent" name="additionalDetailsBean.dateOfComplEvent" value="%{additionalDetailsBean.dateOfComplEvent}" readonly="true"> </s:textfield>
		  </td>
	  
    </tr>
    
        <tr class="sportDiv" style="display:none">
    
	      <td>Select Level Of Participation / <span class="tamil"><s:text name="additional.participationvel"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      
		  <s:select list="additionalDetailsBean.participationvelList" id="participationvelList" name="additionalDetailsBean.participationvel" headerKey="" headerValue="Select Level Of Participation" onchange="enableDisableTextField();" 
		  value="%{additionalDetailsBean.participationvel}" ></s:select>
		  </td>
	  
    </tr>
    
            <tr class="sportDiv" style="display:none">
    
	      <td>Certificate Number / <span class="tamil"><s:text name="additional.certificatenumber"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	    	<s:textfield id="sportsCertificateNumber" onpaste="return false;" onkeypress="return alphaNumericwithSplChar(event);"  name="additionalDetailsBean.sportsCertificateNumber" value="%{additionalDetailsBean.sportsCertificateNumber}" maxlength="30"></s:textfield>
		  </td>
	  
    </tr>
    
     <tr class="sportDiv" style="display:none">
    
	      <td>Designation Of Issuing Authority / <span class="tamil"><s:text name="additional.IssuingAuthority"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	     <s:textfield id="sportsCertIssuingAuthority" name="additionalDetailsBean.sportsCertIssuingAuthority" onkeypress="return alphabetswithspace(event);" value="%{additionalDetailsBean.sportsCertIssuingAuthority}" maxlength="50"  onpaste="return false;"></s:textfield>
		  </td>
	  
    </tr>
   
     <tr class="sportDiv" style="display:none">
    
	      <td>Date Of Issue Of Certificate / <span class="tamil"><s:text name="additional.DateOfIssueCertificate"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      
		  <s:textfield id="dateOfsportsCertificate" name="additionalDetailsBean.dateOfsportsCertificate" value="%{additionalDetailsBean.dateOfsportsCertificate}" readonly="true"> </s:textfield>
		  </td>
	  
    </tr><%--	
    
        <tr class="sportDiv" style="display:none">
		    <td>Latest Form I Or Form II Or<br/> Form III Certificate / <span class="tamil"><s:text name="additional.sportscertificate1"/></span>  <span class="manadetory-fields">*</span></td>
		    <td>
		<div style="display:block; height:auto;">
		<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
		<s:hidden name="additionalDetailsBean.quotaDocumentUploaded" id="quotaDocumentUploaded"/>
		<s:hidden name="additionalDetailsBean.quotaDocumentFileName" />
		<s:hidden name="additionalDetailsBean.candidateDocPk3" />
		  <table class="" width="100%" border="0">
		    <tr>
		     
		      <td width="25%" > 
		        <s:file  name="additionalDetailsBean.quotaData"  id = "eligibilityCriteriaUploadFile3"></s:file>
		        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
		      </td>
		       <td width="50%" align="center">
		      <div class=""><s:checkbox name="additionalDetailsBean.checkQuotaAcceptance" id="check3" value="%{additionalDetailsBean.checkQuotaAcceptance}" /><br/>Candidate Acceptance Checkbox</div>
		    
		      </td>
		      <td><input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" onclick="uploadDocument3();" id="uploadDoc3"/></td>
		    </tr>
		    <tr>
		      
		      <td colspan="3" ><span class="lighttext"><strong><b>Note :</b> The File Size should be between <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB).toLowerCase()%>KB to <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB).toLowerCase()%>KB. / <span class="tamil"><s:text name="document.instruction1"/></span></strong></span>
			      <br /><span class="tamil"><s:text name="document.instruction1"/></span>
			      <br>
			       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files</span>
			      <br/>
			      <span class="tamil"><s:text name="document.instruction1"/></span>
			   </td>
		      
		    </tr>
		    <tr>
		    	
		    	<td style="color:#F00;" colspan="3">
		    		Please upload recent Document 
		    	</td> 
		    </tr>
		    </table>
		    <div id='block7'></div>
		</div>
		<div id="uploadedDocuments3" style="display: none;" >
				<table cellspacing="0" cellpadding="0" style="clear:both;" width="100%" >
				
				    <tr>
				    
				      <td  width="75%" id='docId'><br/>
				      <a href="CandidateAction_getAdditionalDocument.action?candidateDocId=<s:property value="additionalDetailsBean.candidateDocPk3"/>"><s:property value="additionalDetailsBean.quotaDocumentFileName" /></a>
				      </td>
				     
				      
				    </tr>
				    <tr>
				      <td></td>
				      <td ><br/>
				      </td>
				    </tr>
			  	</table>
		</div>

</s:form>  
    
    </td>
    
    
    </tr>
    
    --%>
   
    <tr>
    <td>
    </td>
    </tr>
    </table>
  	</div>

<!-- ending of sports details -->
 
 <!-- Start of exService details -->
<table class="contenttableNew" >
    <tr id="exserviceManTr">
         
          <td  width="80%" id="exserviceManTd">Are you an Ex-Servicemen or presently serving (going to retire within one year)? / <span class="tamil"><s:text name="additional.exserviceman"/></span><span class="manadetory-fields tabWidth">*</span>
           </td>
          
          <td width="20%"  colspan="3">
            <s:select list = "additionalDetailsBean.exServicemanList" name = "additionalDetailsBean.exServiceman" label = "Name" 
        id = "exServiceman" value="%{additionalDetailsBean.exServiceman}" onchange="return showServiceDiv();" 
        />  
        
        <s:hidden name="additionalDetailsBean.exserviceManTrHid" id="exserviceManTrHid"></s:hidden>
           </td>
      </tr>
      <tr id="exserviceWomanTr">
          <td  width="80%" id="exserviceWomanTd">Are you an Ex-Service Women or presently serving (going to retire within one year)? / <span class="tamil"><s:text name="additional.exserviceman"/></span><span class="manadetory-fields tabWidth">*</span>
           </td>
          <td width="20%"  colspan="3">
            <s:select list = "additionalDetailsBean.exServiceWomanList" name = "additionalDetailsBean.exServiceWoman" label = "Name" 
              id = "exServiceWoman" value="%{additionalDetailsBean.exServiceWoman}" onchange="return showServiceDiv();"  
            /> 
            <s:hidden name="additionalDetailsBean.exserviceWomanTrHid" id="exServiceWomanTrHid"></s:hidden>
             
          </td>
      </tr>
</table>
     <div class="exserviceDiv" style="display:none">
       <table class="contenttableNew" border="0">
      <tr class="exserviceDiv" style="display:none">
          <td >Service  Number/ <span class="tamil"><s:text name="additional.servicenumber"/></span><span class="manadetory-fields">*</span>
       
           </td>
        
          <td colspan="3">
            <s:textfield name="additionalDetailsBean.serviceNumber" onkeypress="return alphanumeric(event);"  value="%{additionalDetailsBean.serviceNumber}" id = "candidateName" maxlength="30" onpaste="return false;"></s:textfield>

            </td>
            
      </tr>
      
  
      
    <tr class="exserviceDiv" style="display:none">
        <td>Date Of Enlistment / <span class="tamil"><s:text name="additional.dateofenlistment"/></span> <span class="manadetory-fields">*</span> <br/>
        <span class="lighttext">
         
        
        </span>
        </td>
        
        <td >
          <s:textfield id="dateOfEnlistment" name="additionalDetailsBean.dateOfEnlistment" maxlength="30" value="%{additionalDetailsBean.dateOfEnlistment}" readonly="true" onkeypress="return alphaNumericwithSplChar(event);"> </s:textfield>
          
        </td>
        <%--<td colspan="2"><span class="lighttext calInstr">Please click on Calendar to select Date of Birth. / <span class="tamil"><s:text name="applicationForm.calenderInstruction"/></span></span></td>
    --%></tr>
    
     <tr class="exserviceDiv" style="display:none">
        <td>Date of Discharge / to be discharged / <span class="tamil"><s:text name="additional.dateofdischarge"/></span>  <span class="manadetory-fields">*</span></td>
        
        <td colspan="3">
             <s:textfield id="dateOfDischarge" value="%{additionalDetailsBean.dateOfDischarge}" name="additionalDetailsBean.dateOfDischarge" readonly="true" onkeypress="return alphaNumericwithSplChar(event);"> </s:textfield>  
      </td>
    </tr >

      
       <tr class="exserviceDiv" style="display:none">
        <td>Certificate Number / <span class="tamil"><s:text name="additional.certificatenumber"/></span>  <span class="manadetory-fields">*</span></td>
        
        <td colspan="3">
            <s:textfield id="exCertificateNumber" name="additionalDetailsBean.exCertificateNumber" onkeypress="return alphanumeric(event);" value="%{additionalDetailsBean.exCertificateNumber}" onpaste="return false;" maxlength="30" ></s:textfield> 
      </td>
    </tr>
    
    
      
   <tr class="exserviceDiv" style="display:none">
        <td>Designation Of Issuing Authority / <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>  <span class="manadetory-fields">*</span></td>
        
        <td colspan="3">
            <s:textfield id="exCertIssuingAuthority" name="additionalDetailsBean.exCertIssuingAuthority" onkeypress="return alphanumericDesignation(event);" value="%{additionalDetailsBean.exCertIssuingAuthority}" onpaste="return false;" maxlength="50" ></s:textfield> 
      </td>
    </tr>
    </table>
    </div>
  
  <!-- Start of widow details -->
<table class="contenttableNew" >      
    <tr id="widowTr" >
        <td width="80%">Are You A Destitute Widow ? / <span class="tamil"><s:text name="additional.widow"/></span>  <span class="manadetory-fields">*</span></td>
      
         <td width="20%"  colspan="3">
            <s:select list = "additionalDetailsBean.widowList" name = "additionalDetailsBean.widow" label = "Name" 
        id = "widow" value="%{additionalDetailsBean.widow}" onchange="return showWidowDiv();" 
        />  
        <s:hidden name="additionalDetailsBean.widowTrHid" id="widowTrHid" />
    </tr>
</table>   

<div class="widowDiv" style="display:none">
      <table class="contenttableNew" border="0">
       <tr class="widowDiv" style="display:none">
        <td >Name Of Late Husband / <span class="tamil"><s:text name="additional.nameofhusband"/></span>   <span class="manadetory-fields">*</span></td>
      <td colspan="3">
       <s:textfield id="nameOfLateHusband" name="additionalDetailsBean.nameOfLateHusband" onkeypress="return alphaNumericwithSplChar(event);" onpaste="return false;" value="%{additionalDetailsBean.nameOfLateHusband}"  maxlength="50"></s:textfield>
       </td>
    </tr>
    
    <tr class="widowDiv" style="display:none">
        <td>Date Of Death / <span class="tamil"><s:text name="additional.dateofdeath"/></span>   <span class="manadetory-fields">*</span></td>
      
        <td colspan="3">
          <s:textfield id="dateOfDeath" name="additionalDetailsBean.dateOfDeath" value="%{additionalDetailsBean.dateOfDeath}" readonly="true" onkeypress="return alphaNumericwithSplChar(event);"> </s:textfield>
      </td>
    </tr> 
    
    <tr class="widowDiv" style="display:none">
    
        <td>Destitute Widow Certificate Number / <span class="tamil"><s:text name="additional.destitutewidowcertificatenumber"/></span>  <span class="manadetory-fields">*</span></td>
        
        <td colspan="3">
            <s:textfield id="deathCertificateNumber" onkeypress="return alphanumeric(event);" onpaste="return false;" name="additionalDetailsBean.deathCertificateNumber" maxlength="30"></s:textfield>
        </td>
    </tr>
        <tr class="widowDiv" style="display:none">
    
        <td>Designation Of Issuing Authority / <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>  <span class="manadetory-fields"> *</span></td>
        
        <td colspan="3">
            <s:textfield id="deathCertIssuingAuthority" name="additionalDetailsBean.deathCertIssuingAuthority" onkeypress="return alphaNumericwithSplChar(event);" onpaste="return false;" value="%{additionalDetailsBean.deathCertIssuingAuthority}" maxlength="50" ></s:textfield>
          
        </td>
      
    </tr>
    
    <tr class="widowDiv" style="display:none">
        <td>Date Of Issue Of The Certificate / <span class="tamil"><s:text name="additional.dateofcertificate"/></span> <span class="manadetory-fields"> *</span> </td>
        <td colspan="3">
        
           <s:textfield id="dateOfdeathCertificate" name="additionalDetailsBean.dateOfdeathCertificate" value="%{additionalDetailsBean.dateOfdeathCertificate}" readonly="true" onkeypress="return alphaNumericwithSplChar(event);"> </s:textfield>
         </td>
    </tr>
    
   </table>
    </div>
    
    <!-- Start of Ward Quota 
    <div class="wardQuotaDiv1" style="display:none;">
 		<table class="contenttableNew" border="0">    	  
 			<tr id="wardsTr" >
		      <td width="80%">Would You Like To Avail Ward Quota? / <span class="tamil"><s:text name="additional.wards"/></span> <span class="manadetory-fields">*</span></td>
		      <td colspan="3" width="20%">
		      
		      	  <s:select list = "additionalDetailsBean.wardsQuaotaList" name = "additionalDetailsBean.wardsQuaota" label = "Name" 
					id = "wardsQuaota" value="%{additionalDetailsBean.wardsQuaota}" onchange="return showWardsDiv1();"
					/>	
			   </td>
    		</tr>
    	</table>
    
    <div  class="wardsDiv" style="display:none">
   		 <table class="contenttableNew" >
			    <tr class="wardsDiv" style="display:none">
			    
				      <td>Specify The Ward Ministerial(only serving) / Executive / <span class="tamil"><s:text name="additional.wardname"/></span>  <span class="manadetory-fields">*</span></td>
				      <td colspan="3">
				      
					  	<s:select list = "additionalDetailsBean.wardsList" name = "additionalDetailsBean.wardname" label = "Name" headerKey="" headerValue="Select Ward" 
							id = "wardname" value="%{additionalDetailsBean.wardname}" onchange="return loadComboBox();"
							/>
					  </td>
				  
			    </tr>
			 
			
			    <tr class="wardsDiv" style="display:none">
			    
				      <td>Name Of The Parent / <span class="tamil"><s:text name="additional.nameOfParent"/></span>  <span class="manadetory-fields">*</span></td>
				      <td colspan="3">
				      
					  <s:textfield id="nameOfParent" name="additionalDetailsBean.nameOfParent" value="%{additionalDetailsBean.nameOfParent}" maxlength="50" onkeypress="return alphabetswithspace(event);"   onpaste="return false;"></s:textfield>
					  </td>
				  
			    </tr>
			    
			    <tr class="wardsDiv" style="display:none">
			    
				      <td>Present Rank(Or Last Rank If Retired Or Deceased) / <br/><span class="tamil"><s:text name="additional.presentRank"/></span>  <span class="manadetory-fields">*</span></td>
				      <td colspan="3">
				      <s:select list="additionalDetailsBean.presentRankList" id="presentRank" name="presentRank" 
					  	 value="%{additionalDetailsBean.presentRank}"></s:select>
					  	 
					  	 <s:hidden value="%{additionalDetailsBean.presentRank}" id = "presentRankHidden"></s:hidden>
					  </td>
				  
			    </tr>
			    <tr id="postingTrMini" style="display:none">
			    
				  <td> Present Posting (Or Last if Retired or Deceased) &#8208; Unit / <span class="tamil"><s:text name="additional.presentPostingUnit"/></span><span class="manadetory-fields">*</span>
				      <td colspan="3">
				    	<s:textfield name="additionalDetailsBean.presentPostingMini" value="%{additionalDetailsBean.presentPostingMini}" 
				    	onkeypress="return alphabetswithspace(event);"  maxlength="30" onpaste="return false;"  id="presentPostingMini"></s:textfield>
					  </td>
				  
			    </tr>
			
			     <tr  id="unitTr" style="display:none;">
				     <td>
				   Present Posting (Or Last if Retired or Deceased) &#8208; Unit / <span class="tamil"><s:text name="additional.presentPostingUnit"/></span><span class="manadetory-fields">*</span>
				     </td>
				     <td colspan="3">
				       <table width="100%" border="0" cellspacing="0" cellpadding="0">
				     	<tr>
				     		 <td width="40%" style="padding:0;">
				      <s:select list="additionalDetailsBean.unitList" name="additionalDetailsBean.presentPostingUnit" id="unitList" headerKey="" headerValue = "Select Present Posting" value="%{additionalDetailsBean.presentPostingUnit}"  onchange="enableOtherUnitField();"></s:select><%--
				      
				      <s:hidden value="%{additionalDetailsBean.presentPostingUnit}" id = "unitListHidden"></s:hidden>
				      
				      --%></td>
				       
				      </tr>
				      <tr id="otherTR" style="display:none">
				      	<td   id="otherTD" >Please Specify Other Unit/ <span class="tamil"><s:text name="applicationForm.otherUnit"/></span> <span class="manadetory-fields">*</span> 
				     </td>
				     <td>
				     	 <s:textfield name="additionalDetailsBean.unitsOther" value="%{additionalDetailsBean.unitsOther}" onkeypress="return alphabetswithspace(event);"  maxlength="30" onpaste="return false;" cssStyle="width:100px;"  id="unitsOther"></s:textfield>
				     </td>
				      </tr>
				      </table>
					 </td> 
				     </tr>
				     
				  <tr class="pfDiv" style="display:none;background: #ececec;">
			    
				      <td>Unit / <span class="tamil"><s:text name="additional.unit"/></span><span class="manadetory-fields">*</span></td>
				      <td colspan="3">   
					  <s:textfield id="pfUnit" name="additionalDetailsBean.pfUnit" value="%{additionalDetailsBean.pfUnit}" maxlength="100" onkeypress="return alphaNumericwithSplCharForUnitsNss(event);" onpaste="return false;"></s:textfield>
					  </td>  
			      </tr>
				     
				  <tr class="pfDiv" style="display:none;background: #ececec;">
			    
				      <td>Department Name / <span class="tamil"><s:text name="additional.departmentname"/></span><span class="manadetory-fields">*</span></td>
				      <td colspan="3">   
					  <s:textfield id="pfDistrict" name="additionalDetailsBean.pfDistrict" value="%{additionalDetailsBean.pfDistrict}" onkeypress="return alphaNumericwithSplCharForUnitsNss(event);" maxlength="100"  onpaste="return false;"></s:textfield>
					  </td>  
			      </tr>   
				     
				      <tr  id="PostingTrDistrict"  style="display:none;">
				     <td>
				Present Posting (Or Last if Retired or Deceased) &#8208; District / City / <br/><span class="tamil"><s:text name="additional.presentPosting"/></span><span class="manadetory-fields">*</span>
				     </td>
				     <td colspan="3">
				        <s:select list="additionalDetailsBean.districtList" name="additionalDetailsBean.presentPosting" id="districtList" headerKey="" headerValue = "Select Present Posting" value="%{additionalDetailsBean.PresentPosting}" onchange="enablePoliceStationField();"></s:select>
				     		  <%--<s:hidden value="%{additionalDetailsBean.PresentPosting}" id = "PresentPostingHidden"></s:hidden>
					 --%></td> 
				     </tr>
				     
				     <tr id="policeTR1"  style="display:none;background: #ececec;">
				     <td>
				     Present Posting (Or Last if Retired or Deceased) &#8208; Police Station / <br/><span class="tamil"><s:text name="additional.presentPostingPoliceStation"/> </span> <span class="manadetory-fields">*</span>
				     </td>
				     <td colspan="3">
				      <s:select list="additionalDetailsBean.policeStationList1" name="additionalDetailsBean.policeStation" id="policeStationList" headerKey="" headerValue = "Select Police Station" value="%{additionalDetailsBean.policeStation}"></s:select>
				    <s:hidden value="%{additionalDetailsBean.policeStation}" id = "policeStationHidden"></s:hidden></td> 
				     </tr>
				     
				     
				   	 
			    
			     <tr class="wardsDiv" style="display:none">
			    
				      <td>GPF or CPS Number Of The Parent / <br/><span class="tamil"><s:text name="additional.gpsNumber"/> </span>  <span class="manadetory-fields">*</span></td>
				      <td colspan="3">
				      
				      
					  <s:textfield id="gpsNumber" name="additionalDetailsBean.gpsNumber" value="%{additionalDetailsBean.gpsNumber}" onkeypress="return alphaNumeric(event);" maxlength="13"  onpaste="return false;"></s:textfield>
					  </td>
				  
			    </tr>
			    <tr class="wardsDiv" style="display:none">
			    
				      <td>Certificate Number / <span class="tamil"><s:text name="additional.certificatenumber"/></span>  <span class="manadetory-fields">*</span></td>
				      <td colspan="3">
				      
					   <s:textfield id="wardsCertificateNumber" onpaste="return false;" name="additionalDetailsBean.wardsCertificateNumber" onkeypress="return alphaNumericwithSplChar(event);"
					  value="%{additionalDetailsBean.wardsCertificateNumber}" maxlength="30"></s:textfield>
					  </td>
				  
			    </tr>  
			    
			        <tr class="wardsDiv" style="display:none">
			    
				      <td>Designation Of Issuing Authority / <span class="tamil"><s:text name="additional.IssuingAuthority"/></span> <span class="manadetory-fields">*</span></td>
				      <td colspan="3">
				      
				      
					  <s:textfield id="wardsCertIssuingAuthority" name="additionalDetailsBean.wardsCertIssuingAuthority" onkeypress="return alphabetswithspace(event);" value="%{additionalDetailsBean.wardsCertIssuingAuthority}"
					   maxlength="50"  onpaste="return false;"></s:textfield>
					  </td>
				  
			    </tr>  
			    
			    <tr class="wardsDiv" style="display:none">
			    
				      <td>Date Of Issue Of Certificate / <span class="tamil"><s:text name="additional.dateofcertificate"/></span>  <span class="manadetory-fields">*</span></td>
				      <td colspan="2">
					<s:textfield id="dateOfwardsCertificate" name="additionalDetailsBean.dateOfwardsCertificate" value="%{additionalDetailsBean.dateOfwardsCertificate}" cssStyle="width: 160px;" readonly="true"> </s:textfield>
					  
					  </td>
				  
			    </tr> 
    
   		 </table>
     </div>
   </div>
     end of Ward Quota -->
    

<br>
<div > 
    <h1 class="pageTitle"><b>Special Marks /<span class="tamil"><s:text name="additional.specialMarks"/></span></b>
    </h1>
</div>
<div class="hr-underline2"></div>
  

<table class="contenttableNew" >
  
     <tr id="nccTr">
    
        <td width="80%">Are You Possesing NCC A/B/C Certificates? / <span class="tamil"><s:text name="additional.ncc"/></span>  <span class="manadetory-fields">*</span></td>
        <td colspan="3" width="20%">
        
      <s:select list = "additionalDetailsBean.nccCertificateList" name = "additionalDetailsBean.nccCertificate" label = "Name" 
        id = "nccCertificate" value="%{additionalDetailsBean.nccCertificate}"
        />  
      </td>
    
    </tr>  
    <tr id="nssTr">
    
        <td width="80%">Are You Possesing NSS Certificates? / <span class="tamil"><s:text name="additional.nssPosses"/></span>  <span class="manadetory-fields">*</span></td>
        <td colspan="3" width="20%">
        
        <s:select list = "additionalDetailsBean.nssCertificateList" name = "additionalDetailsBean.nssCertificate" label = "Name"  
        id = "nssCertificate" value="%{additionalDetailsBean.nssCertificate}" 
        />
        
      </td>
    
    </tr>
    <tr id="sportsSpecialTr">
        <td width="80%" >Would you like to avail special marks for sports? / <span class="tamil"><s:text name="additional.sportsSpecial"/></span> <span class="manadetory-fields"> *</span></td>
        
        <td width="20%" colspan="3">
          <s:select list = "additionalDetailsBean.sportsQuaotaList" name = "additionalDetailsBean.sportsSpecial" label = "Name" 
        id = "sportsSpecial" value="%{additionalDetailsBean.sportsSpecial}"  />  
        </td>
       </tr>
</table>

    
 <!--    
<table class="contenttableNew" style="display:none">
    
    <tr id="extraQualificationTr_1">
          <td  width="80%">Would you like to avail additional marks for extra qualification.? / <span class="tamil"><s:text name="additional.extraqualification"/></span><span class="manadetory-fields tabWidth">*</span>
           </td>
           <td width="20%"  colspan="3">
            <s:select list = "additionalDetailsBean.extraQualificationList" name = "additionalDetailsBean.extraQualification" label = "Name" 
          id = "extraQualification" value="%{additionalDetailsBean.extraQualification}"  onchange="return showExtraQualificationDiv();" />  
           </td>
      </tr>
  
    <div class="extraQualificationDiv_1" style="display:none">
      <table class="contenttableNew" border="0">
      <tr class="extraQualificationDiv" style="display:none">
      <td width="42%">
        B.E. degree in Electronics and Communication Engineering - 4 years
        <br>
      </td>
      
      <td colspan="3">
        <s:checkbox  name="additionalDetailsBean.qualBEExtc" id = "qualBEExtc" fieldValue="true"  value="%{additionalDetailsBean.qualBEExtc}" />
      </td>
      </tr>
      
      <tr class="extraQualificationDiv" style="display:none">
      <td>
        BCA/B.Sc. degree in Computer Science or B.Sc. IT - 3 years
        <br>
      </td>
      
      <td colspan="3">
        <s:checkbox  name="additionalDetailsBean.qualBscCSBscIT" id = "qualBscCSBscIT" value="%{additionalDetailsBean.qualBscCSBscIT}" />
      </td>
      </tr>
      
      <tr class="extraQualificationDiv" style="display:none">
      <td>
        B.E or B.Tech degree in Computer Science or I.T - 4 years
        <br>
      </td>
      
      <td colspan="3">
        <s:checkbox name="additionalDetailsBean.qualBEBTechCSIT" id = "qualBEBTechCSIT" value="%{additionalDetailsBean.qualBEBTechCSIT}" />
      </td>
      </tr>
      
      <tr class="extraQualificationDiv" style="display:none">
      <td>
        Post Graduate Diploma in Computer application - 1 year
        <br>
      </td>
      
      <td colspan="3">
        <s:checkbox name="additionalDetailsBean.qualPGCA" id = "qualPGCA" value="%{additionalDetailsBean.qualPGCA}" />
      </td>
      </tr>
      
      <tr class="extraQualificationDiv" style="display:none">
      <td>
        M.E or M.Tech degree in Communication Systems - 2 years
        <br>
      </td>
      
      <td colspan="3">
        <s:checkbox name="additionalDetailsBean.qualMEMTechCommSys" id = "qualMEMTechCommSys" value="%{additionalDetailsBean.qualMEMTechCommSys}" />
      </td>
      </tr>
      
      <tr class="extraQualificationDiv" style="display:none">
      <td>
        M.E. or M.Tech. degree in Computer Science or I.T. - 2 years
        <br>
      </td>
      
      <td colspan="3">
        <s:checkbox name="additionalDetailsBean.qualMEMTechCSIT" id = "qualMEMTechCSIT" value="%{additionalDetailsBean.qualMEMTechCSIT}" />
      </td>
      </tr>
      
      <tr class="extraQualificationDiv" style="display:none">
      <td>
        MCA - 3 years
        <br>
      </td>
      
      <td colspan="3">
        <s:checkbox name="additionalDetailsBean.qualMCA" id = "qualMCA" value="%{additionalDetailsBean.qualMCA}" />
      </td>
      </tr>
    </table>
    </div>
    
    
    
    
</table> -->
<br>
<!--  <div id ="saveUpdateDiv" align="center" >
    <s:hidden name="disciplineType" value="%{disciplineType}"></s:hidden>
      <s:submit id="saveUpdate" value="Save / Update" cssClass="submitBtn button-gradient"  method="saveCandidateQuotaDetails" onclick="viewFlag1();"></s:submit>      
  
</div>
-->
</div>
 
  </div>

 
  

<div class="change-pass box-gradient" id="pop9" style="vertical-align: top" >
<div><a href="javascript:void(0);" onclick="PopHideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox fl-left"><h1 class="pageTitle" title="Login">&nbsp;<s:label value="Registration Confirmation" /></h1></div>
<div class="closebtnBox fl-rigt"></div>
<div class="hr-underline clear"></div>
<br/>
  
  <div style = "text-align:center">Do you want to continue to next screen?</div><br/>
  <div style="text-align:center;" ><s:submit value="Yes" cssClass="submitBtn button-gradient" method="saveCandidateDetails" onclick="return hiddenValForConfirm();"></s:submit>   
<input type="button" name="No" id="true" value="No" class="submitBtn button-gradient" onclick="PopHideAll()" /></div>

<br/><br/>
</div></div>

  </div>
  <s:token/>

<div class="row countinuebg">
      <div class="container">
      <div align ="right">
        <s:submit method="saveCandidateQuotaDetails" value="Save & Continue" cssClass="submitBtn button-gradient" ></s:submit>
    </div>
    </div>
</div>
</s:form>

<!-- End Of Form -->

<div class="change-pass box-gradient" id="pop8" >
  <div>
    <a href="javascript:void(0);" onclick="PopHideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a>
  </div>
  <br/>
  <div class="titleBox">
      <h1 class="pageTitle" title="Registration Instructions">
        &nbsp;<s:label value="Registration Instructions" />
      </h1>
    </div>
    <div class="hr-underline clear"></div>
  <div style="display:block; min-height:300px; height:auto;">
       <ol>
    <li> Candidates are advised in their own interest to keep the user-id and password confidential, to avoid misuse by another candidate.<br />
      <br />
    </li>
    <li> Candidates have to fill all the relevant details very carefully, there is provision of edit and submit button. Candidate can edit  his/her registration details as many times as he/she wishes, however once the details filled by him/her are submitted, then no further change (s) shall be permitted.<br />
      <br />
    </li>
    <li> Candidates are advised to take a Print of their registration form.<br />
      <br />
    </li>
  </ol>
      
    </div>
</div>
<s:hidden id ='hidConfirnmation' name='hidConfirnmation' value="-1"/>

