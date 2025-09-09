<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript" src="js/q.js"></script>
<script type="text/javascript" src="js/spark-md5.min.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {

	var r='<s:property value="%{applyNotify}"></s:property>';
	var s=r.split(" ");
	var x=s[0].split("-");
	x[0]=x[0];
	x[1]=x[1]-1;
	var appEndDate='<s:property value="%{appEndDate}"></s:property>';
	var appEndDateArr=appEndDate.split(" ");
	var aEr=new Array();
	aEr=appEndDateArr[0].split("-");
	var strDate=aEr;
	aEr[0]=parseInt(strDate[0]);
		aEr[1]=parseInt(strDate[1]);
			aEr[2]=parseInt(strDate[2]);
	
	//alert(r+"_"+strDate[0]+"-"+strDate[1]+"-"+strDate[2]);
	
	var arpdepartmentQuaota='<s:property value="%{additionalDetailsBean.arpdepartmentQuaota}"></s:property>';
	var arpdepartmentQuaota1='<s:property value="%{additionalDetailsBean.arpdepartmentQuaota1}"></s:property>';
	var disciplineType_string='<s:property value="%{disciplineType}"></s:property>';
	var arpdept_policeStation='<s:property value="%{additionalDetailsBean.arpdept_policeStation}"></s:property>';
	var arpdeptpresentPostingUnit='<s:property value="%{additionalDetailsBean.arpdeptpresentPostingUnit}"></s:property>';
	var disciplineType=parseInt(disciplineType_string);
	var deptcheckAgeAcceptance='<s:property value="%{additionalDetailsBean.deptcheckAgeAcceptance}"></s:property>';
	var deptageDocumentFileName='<s:property value="%{additionalDetailsBean.deptageDocumentFileName}"></s:property>';
	var agerelaxationFlag='<s:property value="%{addDetailRedirectFlag}"></s:property>';
	var deptquotaflag='<s:property value="%{departmentQuotaFlag}"></s:property>';
	var arpdepartmentQuotaFlag='<s:property value="%{arpdepartmentQuotaFlag}"></s:property>';
	var policefield_value=$("#wkExperienceDtlsList_police").val();
	
if(disciplineType== 14){
	//$('#departmentQuaota option[value="No"]').attr("selected",true);
	if(policefield_value=="Police"){
	$("#widowTr").hide();
	$(".widowDiv").hide();
	$("#exserviceTr").hide();
	$(".exserviceDiv").hide();
	}
	$("#deptTr").show();
	if(agerelaxationFlag== "true"){
		if(arpdepartmentQuaota=="Yes"){
			$("#deptTr").show();
			$(".deptDiv").show();
			$("#widow").attr('disabled','disabled');
			$("#exServiceman").attr('disabled','disabled');
		if($("#deptageDocumentUploaded").val() == "true")
			{
			$("#deptuploadedDocuments8").show();
			}
		if(arpdept_policeStation!=null && arpdept_policeStation!=""){
			$("#deptpoliceTR1").show();
			}
		if(arpdeptpresentPostingUnit=="POLICE HEADQUARTERS" || arpdeptpresentPostingUnit=="OTHER"){
			$("#deptpoliceTR1").hide();
			$("#deptPostingTrDistrict").hide();
			}
	
		if(arpdeptpresentPostingUnit=="YOUTH BRIGADE" || arpdeptpresentPostingUnit=="TALUK POLICE"){
			$("#deptpoliceTR1").show();
			$("#deptPostingTrDistrict").show();
		}else{
			$("#deptpoliceTR1").hide();
			$("#deptPostingTrDistrict").hide();
		}
		if(arpdeptpresentPostingUnit=="DPO/CPO" || arpdeptpresentPostingUnit=="RANGE OFFICE" || arpdeptpresentPostingUnit=="TAMIL NADU SPECIAL POLICE" || arpdeptpresentPostingUnit=="SPECIAL UNIT" || arpdeptpresentPostingUnit=="BATTALION OFFICE" || arpdeptpresentPostingUnit=="ARMED RESERVE" || arpdeptpresentPostingUnit=="OTHER")
		{
			$("#deptPostingTrDistrict").show();
			$("#deptpoliceTR1").hide();
		}
		if(arpdeptpresentPostingUnit=="OTHER"){
			$("#deptotherTR").show();
			$("#deptpoliceTR1").hide();
			$("#deptPostingTrDistrict").hide();
		}
	
		}else{
		$("#deptTrs").hide();
		$(".deptDiv").hide();
		$("#exServiceman").removeAttr('disabled');
		$("#widow").removeAttr('disabled');
		}
	}//end of agerelaxationFlag
}else{
	$("#deptTr").hide();
	$(".deptDiv").hide();
}
	$(".subNavBg").hide();

	$("#uploadDoc8").prop('value', 'Upload');
	$("#uploadDoc2").prop('value', 'Upload');


	if(($("#ageDocumentUploaded").val() == "true"))
	{
		$("#uploadedDocuments2").show();
	}
	
	if($("#ageDocumentUploaded2").val() == "true")
	{
		$("#uploadedDocuments3").show();
	}
	if($("#deptageDocumentUploaded").val() == "true")
	{
		$("#deptuploadedDocuments8").show();
	}
	
	<s:if test='%{additionalDetailsBean.exServiceman=="64"}'>
			$(".exserviceDiv").show();
			$("#widow").attr('disabled','disabled');
			$("#departmentQuaota").attr('disabled','disabled');
			
			
	</s:if>
	
	<s:if test ='%{additionalDetailsBean.widow== "Yes"}'>
			$(".widowDiv").show();
			$("#exServiceman").attr('disabled','disabled');
			$("#departmentQuaota").attr('disabled','disabled');
	</s:if>
	
	
	<s:if test="%{genderVal=='Male' || genderVal=='Third Gender'}">
		$("#widowTr").hide();
		$(".widowDiv").hide();
	</s:if>
	<s:if test="%{genderVal=='Female' && status=='Married'}">
		$("#widowTr").hide();
		$(".widowDiv").hide(); 	
	</s:if>
	<s:if test="%{disciplineType==14}">
	
	</s:if>
	<s:if test="%{additionalDetailsBean.arpdept_policeStation!=null && additionalDetailsBean.arpdept_policeStation!=''}">
	//$("#deptpoliceTR1").show();
	
	</s:if>
	<s:if test="%{additionalDetailsBean.arpdeptpresentPostingUnit=='POLICE HEADQUARTERS' || additionalDetailsBean.arpdeptpresentPostingUnit=='OTHER'}">
	//$("#deptpoliceTR1").hide();
	//$("#deptPostingTrDistrict").hide();
	</s:if>
	
	<s:if test="%{ additionalDetailsBean.arpdeptpresentPostingUnit=='OTHER'}">
	//$("#deptotherTR").show();
	//$("#deptpoliceTR1").hide();
		//$("#deptPostingTrDistrict").hide();
	</s:if>
	
	<s:if test="%{ additionalDetailsBean.arpdeptpresentPostingUnit=='YOUTH BRIGADE' || additionalDetailsBean.arpdeptpresentPostingUnit=='TALUK POLICE' }">
	//$("#deptpoliceTR1").show();
	//$("#deptPostingTrDistrict").show();
	</s:if>
	<s:else>
	//$("#deptpoliceTR1").hide();
	//$("#deptPostingTrDistrict").hide();
	</s:else>
	
	<s:if test="%{additionalDetailsBean.arpdeptpresentPostingUnit=='DPO/CPO' || additionalDetailsBean.arpdeptpresentPostingUnit=='RANGE OFFICE' || additionalDetailsBean.presentPostingUnit=='TAMIL NADU SPECIAL POLICE' || additionalDetailsBean.arpdeptpresentPostingUnit=='SPECIAL UNIT' || additionalDetailsBean.arpdeptpresentPostingUnit=='BATTALION OFFICE'}">
	//$("#deptPostingTrDistrict").show();
	</s:if>
	

	
	<s:iterator value="errorField">
	
	$("input[name='additionalDetailsBean.<s:property />']").addClass('red-border');
	$("select[name='additionalDetailsBean.<s:property />']").addClass('red-border');
	$("input[name='<s:property />']").addClass('red-border');			
	</s:iterator>
	$("#dateOfEnlistment").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("07/01/1972"),
		maxDate:  new Date(x[0],x[1],x[2]-1),
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		defaultDate: new Date('1 January 2000'),
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
				
			var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
	      
	       
	      }
    });
   $("#deptdateOfEnlistment").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("07/01/1972"),
		//maxDate: -1,
		maxDate: new Date(x[0]-5,x[1],x[2]),
		
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		//defaultDate: new Date('1 January 2000'),
		onSelect: function( selectedDate ) {
			  //$( "#dateOfDischarge" ).datepicker( "option", "maxDate", -1 );
		       // $( "#dateOfDischarge" ).datepicker( "option", "minDate", selectedDate );
		      /* var a=selectedDate.split("-");
				var months = ["jan", "feb", "mar", "apr", "may", "jun", "jul", "aug", "sep", "oct", "nov", "dec"];
			    month1 = months.indexOf(a[1].toLowerCase());
		        if(new Date(x[0]-3,x[1],x[2]) <= new Date(a[2],month1+1,a[0]))
			   		 $( "#dateOfDischarge" ).datepicker( "option", "minDate", selectedDate );
				else
					$( "#dateOfDischarge" ).datepicker( "option", "minDate",  new Date(x[0]-3,x[1],x[2]) );
		        var buttonDisabled=$('#saveUpdate').attr("disabled");
				if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
					$('#saveUpdate').removeAttr("disabled");*/
					$( "#deptdateOfEnlistment" ).datepicker( "option", "maxDate", selectedDate );
			        
			        $( "#dateOfExCertificate" ).datepicker( "option", "minDate", selectedDate );
			        //$( "#dateOfExCertificate" ).datepicker( "option", "maxDate", -1 );
			        var buttonDisabled=$('#saveUpdate').attr("disabled");
					if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
						$('#saveUpdate').removeAttr("disabled");
				}
			caliculateServiceAsOnDate(selectedDate,s);
			      }
    });
    
    $("#dateOfDischarge").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		//yearRange: '2010:2013',
		minDate: new Date(x[0]-3,x[1],x[2]),
		maxDate: new Date(aEr[0]+1,aEr[1]-1,aEr[2]),
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		 onSelect: function( selectedDate ) {
		  
	        $( "#dateOfEnlistment" ).datepicker( "option", "maxDate", selectedDate );
	        
	        $( "#dateOfExCertificate" ).datepicker( "option", "minDate", selectedDate );
	        var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
	      
	        //$( "#dateOfExCertificate" ).datepicker( "option", "maxDate", -1 );
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
	        //$( "#dateOfDischarge" ).datepicker( "option", "maxDate", selectedDate );
	        var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
	      },
	      
    });
    $("#dateOfDeath").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("07/01/1972"),
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		onSelect: function( selectedDate ) {
	        $( "#dateOfdeathCertificate" ).datepicker( "option", "minDate", selectedDate );
	        $( "#dateOfdeathCertificate" ).datepicker( "option", "maxDate", 0 );
	        var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
	       
	      }
		
    });
    $("#dateOfdeathCertificate").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("07/01/1972"),
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		 onSelect: function( selectedDate ) {
	        $( "#dateOfDeath" ).datepicker( "option", "maxDate", selectedDate );
	        var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
	      
	      
	      }
    });
    $("#dateOfStartEvent").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("July, 1989"),
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		onSelect: function( selectedDate ) {
	        $( "#dateOfComplEvent" ).datepicker( "option", "minDate", selectedDate );
	        $( "#dateOfComplEvent" ).datepicker( "option", "maxDate", -1 );
	        var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
	       
	      }
    });
    $("#dateOfComplEvent").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("July, 1989"),
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		 onSelect: function( selectedDate ) {
	        $( "#dateOfStartEvent" ).datepicker( "option", "maxDate", selectedDate );
	        $( "#dateOfsportsCertificate" ).datepicker( "option", "minDate", selectedDate );
	        $( "#dateOfsportsCertificate" ).datepicker( "option", "maxDate", -1 );
	        var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
	      }
    });
    $("#dateOfsportsCertificate").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("July, 1989"),
		maxDate: -1,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		 onSelect: function( selectedDate ) {
	        $( "#dateOfStartEvent" ).datepicker( "option", "maxDate", selectedDate );
	        $( "#dateOfComplEvent" ).datepicker( "option", "maxDate", selectedDate );
	        var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
	      }
    });
    $("#dateOfwardsCertificate").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("July, 1989"),
		maxDate: -1,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		 onSelect: function( selectedDate ) {
	        var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
	      }
    });
    $("#dateOfNccCertificate").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("July, 1989"),
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		onSelect: function( selectedDate ) {
	        var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
		}
    });
    $("#deptNocDate").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		//minDate: new Date("07/01/1972"),
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
    $("#dateOfNssCertificate").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("July, 1989"),
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		onSelect: function( selectedDate ) {
	        var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
		}
    });

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
    
    
    if($('#successMessage') && $('#successMessage').text().trim() == "Candidate Details saved successfully"){
		$('#saveUpdateAge').attr('disabled',true);
	}
	if(applicationForm){
		applicationForm.addEventListener('change', function (evt) {
			$('#saveUpdateAge').removeAttr("disabled");
		});
		applicationForm.addEventListener('input', function (evt) {
		
			var buttonDisabled=$('#saveUpdateAge').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdateAge').removeAttr("disabled");
			}
			
		});
		

		
	}
    
    

});//end of document.ready() function

function getAge(date_1, date_2)
{
  
//convert to UTC
var date2_UTC = new Date(Date.UTC(date_2.getUTCFullYear(), date_2.getUTCMonth(), date_2.getUTCDate()));
var date1_UTC = new Date(Date.UTC(date_1.getUTCFullYear(), date_1.getUTCMonth(), date_1.getUTCDate()));


var yAppendix, mAppendix, dAppendix;


//--------------------------------------------------------------
var days = date2_UTC.getDate() - date1_UTC.getDate();
if (days < 0)
{

    date2_UTC.setMonth(date2_UTC.getMonth() - 1);
    days += DaysInMonth(date2_UTC);
}
//--------------------------------------------------------------
var months = date2_UTC.getMonth() - date1_UTC.getMonth();
if (months < 0)
{
    date2_UTC.setFullYear(date2_UTC.getFullYear() - 1);
    months += 12;
}
//--------------------------------------------------------------
var years = date2_UTC.getFullYear() - date1_UTC.getFullYear();




if (years > 1) yAppendix = " years";
else yAppendix = " year";
if (months > 1) mAppendix = " months";
else mAppendix = " month";
if (days > 1) dAppendix = " days";
else dAppendix = " day";


return years + yAppendix + ", " + months + mAppendix + ", " + (days+1) + dAppendix;
}


function DaysInMonth(date2_UTC)
{
var monthStart = new Date(date2_UTC.getFullYear(), date2_UTC.getMonth(), 1);
var monthEnd = new Date(date2_UTC.getFullYear(), date2_UTC.getMonth() + 1, 1);
var monthLength = (monthEnd - monthStart) / (1000 * 60 * 60 * 24);
return monthLength;
}



function caliculateServiceAsOnDate(selectedDate,applyNotify){
	//splitting application notification sdate
	//var notify=new array();
	var notify=applyNotify[0].split("-");
	var notificationdate=notify;
	notify[0]=parseInt(notificationdate[0]);
	notify[1]=parseInt(notificationdate[1]);
	notify[2]=parseInt(notificationdate[2]);
	
	var newDate = moment(selectedDate).format("YYYY/DD/MM");
	var dateArr=newDate.split("/");
	var YYYY=parseInt(dateArr[0]);
	var DD=parseInt(dateArr[1]);
	var MM=parseInt(dateArr[2]);
	var notificationdd=	parseInt(notificationdate[2]);
	var notificationMM=	parseInt(notificationdate[1]);
	var notificationYYYY=parseInt(notificationdate[0]);
    var ageDiff=getAge(new Date(YYYY,MM,DD),new Date(notificationYYYY,notificationMM,notificationdd));
	$("#deptserviceAsOn").val(ageDiff);
	//$('#deptserviceAsOn').attr('disabled',true);
}
function showDeptQuotaDiv(){
	
	if($("#departmentQuaota").val()!=null && $("#departmentQuaota").val()!="" &&  $("#departmentQuaota option:selected").text()!="No")
	{	
		
		$(".deptDiv").show();
		$("#deptuploadDoc8").prop('value', 'Upload');
		$("#exServiceman").attr('disabled','disabled');
		$("#widow").attr('disabled','disabled');
	}
	else
	{
	
		
		// $('.deptDiv').find('input').val('');
		//$('.deptDiv').find('select').val('');
		// $('.deptDiv').find('select').val('');
		// $('.deptDiv').find('a').remove();
		//$('#deptunitList').empty();
		// $('#deptunitList').append($('<option></option>').val("").html("Select Unit") );
		 //$('#deptpresentRank').empty();
		// $('#deptpresentRank').append($('<option></option>').val("").html("Select Rank")  );
		 //$('#deptdistrictList').empty();
		 //$('#deptdistrictList').append($('<option></option>').val("").html("Select District"));
		 
		$(".deptDiv").hide();
		//$(".deptDiv select").removeClass('red-border');
		//$(".deptDiv input").removeClass('red-border');
		$("#exServiceman").removeAttr('disabled');
		 $("#widow").removeAttr('disabled');
	}
}
function alphaNumericWithSpace(){
	var regex = new RegExp("\s|^)\S\s+\S(\s|$");
            var key = String.fromCharCode(event.charCode ? event.which : event.charCode);
            if (!regex.test(key)) {
                event.preventDefault();
                return false;
            }
}


function auditEntry()
{
	document.getElementById("auditentryage").value="auditentryage";
}

function showServiceDiv()
{

	if($("#exServiceman").val()!=null && $("#exServiceman").val()!="" &&  $("#exServiceman option:selected").text()!="No")
	{	
			
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
		$("#departmentQuaota").attr('disabled','disabled');
		
	}
	else
	{
		 //var txt;
   		// var r = confirm("Changing Option To No Will Delete The  Uploaded Document Press OK Button To Continue!");
    	//if (r == true) {
        
    $("#departmentQuaota").removeAttr("disabled");
		$('.exserviceDiv').find('input').val(''); 
		$(".exserviceDiv").hide();
		$("#msgg").hide();
		$("#uploadedDocuments2").hide();

		var lAgeRelaxationType="ex";
		var lDisciplineType=$('#disciplineType').val();
	   
		dataString = "AgeRelaxationType="+lAgeRelaxationType+"&DisciplineType="+lDisciplineType

		$("#widow").removeAttr('disabled');
		$("#departmentQuaota").removeAttr('disabled');
		$(".exserviceDiv select").removeClass('red-border');
		$(".exserviceDiv input").removeClass('red-border');
		
		/*$.ajax({
			url: "CandidateAction_noOptionDeleteDoc.action",
			async: true,
			data: dataString,
			type: 'POST',
			beforeSend: function()
			{
				
			},
			error:function(ajaxrequest)
			{
				window.reload();
			},
			success:function(responseText)
			{
				//alert("Previous Doc's  Uploaded Successfully Deleted");
			}
		});	*/
	//}
		
		
		
	}	

}

function showWidowDiv()
{
	if($("#widow").val()!=null && $("#widow").val()!="" &&  $("#widow option:selected").text()!="No")
	{		
		$(".widowDiv").show();
		$("#msgg").hide();
		$("#uploadDoc3").prop('value', 'Upload');
		$("#uploadDoc2").prop('value', 'Upload');
		$("#exServiceman").attr('disabled','disabled');
		$("#departmentQuaota").attr('disabled','disabled');
	}
	else
	{
		 $('.widowDiv').find('input').val(''); 
		$(".widowDiv").hide();
		$("#msgg").hide();
		$("#uploadedDocuments3").hide();
		$("#exServiceman").removeAttr('disabled');
		$("#departmentQuaota").removeAttr('disabled');
		$(".widowDiv select").removeClass('red-border');
		$(".widowDiv input").removeClass('red-border');
	}
}







function populateCityForDistrict(){
	
	var state = $("#districtVal").val();
	dataString = "districtVal="+state
	
		
		$.ajax({
			url: "CandidateAction_getCityList.action",
			async: true,
			data: dataString,
			type: 'POST',
			beforeSend: function()
			{
					$('#policeStation').empty();
					$("#policeStation").append(
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
						  var nameAndIDArr = element[val];//.split("#");
						  
						  $("#policeStation").append(
						           $('<option></option>').val(nameAndIDArr).html(nameAndIDArr)
						     );
					 	}); 
					}
				}
			}
		});
		
		
		
			}

function enableDeptOtherUnitField()
{
 var policeField = $("#deptunitList").val();
	 if (policeField == 'YOUTH BRIGADE' || policeField == 'TALUK POLICE' )
	 {	 
		 $("#deptpoliceTR1").show();
	 }
	 else
	 {
		 $("#deptpoliceTR1").hide();
	 }

	 var UnitField = $("#deptunitList").val();
	if(policeField == "")
	 {
		//UnitField = $("#unitListHidden").val();
	 }
	 if(UnitField == 'POLICE HEADQUARTERS')
	 {
		$("#deptotherTR").hide();
		 $("#deptPostingTrDistrict").hide();
		 $("#deptpoliceTR1").hide(); 
	 }
	 
	 else if (UnitField == 'OTHER'  )
	 {
		 $("#deptotherTR").show();
		 $("#deptPostingTrDistrict").hide();
		 $("#deptpoliceTR1").hide();
	 }
	 else
	 {
		 $("#deptPostingTrDistrict").show();
		 $("#deptotherTR").hide();
		 if(UnitField == 'POLICE HEADQUARTERS')
	 	 {
	 		$("#deptotherTR").hide();
	 		 $("#deptPostingTrDistrict").hide();
			 $("#deptpoliceTR1").hide(); 
	 	 }
		 var PresentPosting = $("#deptunitList").val();
		
			var countryName = $("#deptPresentPosting option:selected").text();
			
			dataString = "PresentPosting="+PresentPosting
			
			if (PresentPosting != null && PresentPosting!="" && UnitField!=""){
				//$("#stateDropdown").show();
				//$("#otherStateVal").hide();
				if(PresentPosting == 'POLICE HEADQUARTERS')
	 	 {
	 		$("#deptotherTR").hide();
			 $("#deptPostingTrDistrict").hide();
			 $("#deptpoliceTR1").hide(); 
	 	 }
				
				$.ajax({
					url: "WorkExperienceAction_getDistrict.action",
					async: true,
					data: dataString,
					type: 'POST',
					beforeSend: function()
					{
							$('#deptdistrictList').empty();
							$("#deptdistrictList").append(
							           $('<option></option>').val("").html("Select District")
							     );

							$('#deptpoliceStationList').empty();
							$("#deptpoliceStationList").append(
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
								  $("#deptdistrictList").append(
								           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0].replace("@", ","))
								     );
							 	}); 
							 	var uList = $("#deptPresentPostingHidden").val();
							 	if(uList != "")
							 	{
							 		$("#deptdistrictList").val(uList);
							 	}
							}
						}
					}
				});
			
				
			}
			else
			{
				$('#deptdistrictList').empty();
				$("#deptdistrictList").append(
				           $('<option></option>').val("").html("Select District")
				     );

				$('#deptpoliceStationList').empty();
				$("#deptpoliceStationList").append(
				           $('<option></option>').val("").html("Select Police Station")
				     );

			}
				
	 }
}

function enableDeptPoliceStationField(){
	var policeField = $("#deptunitList").val();
	
	 if ( policeField == 'YOUTH BRIGADE' || policeField == 'TALUK POLICE' )
	 {
		 $("#deptpoliceTR1").show();
		 var PresentPosting = $("#deptdistrictList").val();
		 
		 if(PresentPosting == "")
		 {
			 PresentPosting = $("#deptPresentPostingHidden").val();
			 
		 }
			var countryName = $("#deptPresentPosting option:selected").text();
			
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
							$('#deptpoliceStationList').empty();
							$("#deptpoliceStationList").append(
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
								  $("#deptpoliceStationList").append(
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
				
				 $('#deptpoliceStationList').empty();
				 $('#deptpoliceStationList').append(
					           $('<option></option>').val("").html("Select Police Station")
					     );
				 $('#PresentPostingHidden').val("");
			}
	 }
	 else
	 {
		 $("#deptpoliceTR1").hide();
		
				
	 }
}




function loadComboBox(el)
{
	var dataString ;
	var id=el.id;
	var lastCahr=id.charAt(id.length-1);
	var URL;
	if($(el).val()!='' && $(el).val()!='Destitute Widow' && $(el).val()!='Ministerial Ward' &&  $(el).val()!='Ward')
	{
	if($(el).val()=='Sports')
	{
		URL='CandidateAction_getSportsMap.action';
		  $("#categorySportsName"+lastCahr).removeAttr("disabled");
	}
	else
	{
		URL='CandidateAction_getRefKeyValMasterMap.action';
		 $("#categorySportsName"+lastCahr).attr('disabled', 'disabled');
		 $("#categorySportsName"+lastCahr).val('');
	}
	if(lastCahr==2 || lastCahr==5)
		 dataString = "val="+ $(el).val()+"&post=SI";
	else
		 dataString = "val="+ $(el).val()+"&post=PC";
	$.ajax({
		url: URL,
		async: true,
		data: dataString,
		type: 'POST',
		beforeSend: function()
		{
			
			if(id.includes('stageName'))
			{
				$('#stageLevelName'+lastCahr).empty();
				$('#stageLevelName'+lastCahr).append(
				           $('<option></option>').val("").html("Select Stage")
				     );
			}
			if(id.includes('categoryName') )
			{
				$('#categoryLevelName'+lastCahr).empty();
				$('#categoryLevelName'+lastCahr).append(
				           $('<option></option>').val("").html("Select Category")
				     );
			}
			
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
					  if(id.includes('stageName'))
						{
						  $("#stageLevelName"+lastCahr).removeAttr("disabled");
					  $("#stageLevelName"+lastCahr).append(
					           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
					     );
						}
					  if(id.includes('categoryName') )
						{
						  $("#categoryLevelName"+lastCahr).removeAttr("disabled");
							$('#categoryLevelName'+lastCahr).append(
							           $('<option></option>').val(nameAndIDArr[1]).html(nameAndIDArr[0])
							     );
						}
				 	}); 
				}
			}
		}
	});
}
	else
	{
		if(id.includes('stageName'))
		{
			 $("#stageLevelName"+lastCahr).attr('disabled', 'disabled');
			 $('#stageLevelName'+lastCahr).empty();
				$('#stageLevelName'+lastCahr).append(
				           $('<option></option>').val("").html("Select Stage")
				     );
		}
		if(id.includes('categoryName') )
		{
			 $("#categoryLevelName"+lastCahr).attr('disabled', 'disabled');
			 $('#categoryLevelName'+lastCahr).empty();
				$('#categoryLevelName'+lastCahr).append(
				           $('<option></option>').val("").html("Select Category")
				     );
			 $("#categorySportsName"+lastCahr).attr('disabled', 'disabled');
		}
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
	      /* console.log(result); */
	    },
	    function(err) {
	      // There was an error,
	    },
	    function(progress) {
	      // We get notified of the progress as it is executed
	      /* console.log(progress.currentPart, 'of', progress.totalParts, 'Total bytes:', progress.currentPart * bufferSize, 'of', progress.totalParts * bufferSize); */
	      if(met=="uploadDocument2")
		  {
			  document.getElementById("ageDataHash").value=hashVal;
		  }
	      if(met=="uploadDocument3")
		  {
			  document.getElementById("ageData2Hash").value=hashVal;
		  }
		   if(met=="deptuploadDocument8")
		  {
			  document.getElementById("deptageDataHash").value=hashVal;
		  }
	      document.applicationForm.action="CandidateAction_insertAgerelaxationDocument.action";
		  document.applicationForm.submit();
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



function deptuploadDocument8()
{
	var attachmentDoc = $("#depteligibilityCriteriaUploadFile").val();
	var check2=$("#check8").attr('checked')?true:false;
	if(check2==false)
	{
		message = "Please select checkbox to upload Departement Certificate";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else{
		calculate(document.getElementById('depteligibilityCriteriaUploadFile'),"deptuploadDocument8");
		$("#error-massage").hide();
		//$("#checkl").attr("disabled",true);
	}
	if(attachmentDoc == ""){
		var message = "Please select file to upload Departement Certificate";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else{
		$("#error-massage").hide();
	}
	var t = attachmentDoc.split('.');
	var extn = t[t.length-1];
	var lowrCaseExtn = extn.toLowerCase();
	//if (lowrCaseExtn =="pdf" || lowrCaseExtn =="xls" || lowrCaseExtn =="xlsx" || lowrCaseExtn == "doc" || lowrCaseExtn == "docx"){
		//$("#uploadDocs2").attr('action',"CandidateAction_insertCandidateDocuments.action");
		//$("#uploadDocs2").submit();
		/*document.applicationForm.action="CandidateAction_insertQuotaDetailsDocument.action";
		document.applicationForm.submit();*/
		//$("#uploadDoc2").attr("disabled",true);
	//}else{
	//	$("#test2").show();
	//}
}

function uploadDocument2()
{
	var attachmentDoc = $("#eligibilityCriteriaUploadFile").val();
	var check2=$("#check2").attr('checked')?true:false;
	if(check2==false)
	{
		message = "Please select checkbox to upload Discharged / to be discharged certificate";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		$("#check2").addClass('red-border');
		return false;
	}else{
		calculate(document.getElementById('eligibilityCriteriaUploadFile'),"uploadDocument2");
		$("#error-massage").hide();
		//$("#checkl").attr("disabled",true);
	}
	if(attachmentDoc == ""){
		var message = "Please select file to upload Discharged / to be discharged certificate";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else{
		$("#error-massage").hide();
	}
	var t = attachmentDoc.split('.');
	var extn = t[t.length-1];
	var lowrCaseExtn = extn.toLowerCase();
	//if (lowrCaseExtn =="pdf" || lowrCaseExtn =="xls" || lowrCaseExtn =="xlsx" || lowrCaseExtn == "doc" || lowrCaseExtn == "docx"){
		//$("#uploadDocs2").attr('action',"CandidateAction_insertCandidateDocuments.action");
		//$("#uploadDocs2").submit();
		/*document.applicationForm.action="CandidateAction_insertAgerelaxationDocument.action";
		document.applicationForm.submit();*/
		//$("#uploadDoc2").attr("disabled",true);
	//}else{
	//	$("#test2").show();
	//}
}
function uploadDocument3()
{
	var attachmentDoc = $("#eligibilityCriteriaUploadFile2").val();
	var check2=$("#check3").attr('checked')?true:false;
	if(check2==false)
	{
		message = "Please select checkbox to upload Destitute Widow Certificate";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else{
		calculate(document.getElementById('eligibilityCriteriaUploadFile2'),"uploadDocument3");
		$("#error-massage").hide();
		//$("#checkl").attr("disabled",true);
	}
	if(attachmentDoc == ""){
		var message = "Please select file to upload Destitute Widow Certificate";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else{
		$("#error-massage").hide();
	}
	var t = attachmentDoc.split('.');
	var extn = t[t.length-1];
	var lowrCaseExtn = extn.toLowerCase();
	//if (lowrCaseExtn =="pdf" || lowrCaseExtn =="xls" || lowrCaseExtn =="xlsx" || lowrCaseExtn == "doc" || lowrCaseExtn == "docx"){
		//$("#uploadDocs2").attr('action',"CandidateAction_insertCandidateDocuments.action");
		//$("#uploadDocs2").submit();
		/*document.applicationForm.action="CandidateAction_insertAgerelaxationDocument.action";
		document.applicationForm.submit();*/
		//$("#uploadDoc2").attr("disabled",true);
	//}else{
	//	$("#test2").show();
	//}
}
function createErrorList(message, ulID, divID) 
{ 
	var msgArr = message.split('##');
	$("#"+ulID).children().remove();
	$.each(msgArr, function(val) {
		if(msgArr[val] != ""){
			var listTag = document.createElement("li"); 
			listTag.id = "list1"; 
			listTag.className ="dynamicSpan"; 
			listTag.innerHTML = msgArr[val];
			document.getElementById(""+ulID).appendChild(listTag);
		}
	 });
	$("#"+divID).show(); 
} 
function numbersonly(e){
	var unicode=e.charCode? e.charCode : e.keyCode
	if (unicode==9){
		return true;
	}
		
	if ((unicode < 48 || unicode > 57) && unicode!=8 && unicode != 52 ){ //if not a number
		return false; //disable key press
	}
}
function alphanumeric(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9)
		return false;
}
function alphabets(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && unicode != 8 && unicode != 9 && unicode != 46  && unicode != 39)
		return false;
}
function alphabetswithspace(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && unicode != 8 && unicode != 32  && unicode != 9)
		return false;
}
function alphaNumericwithSplChar(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9 && unicode != 46 && unicode != 32 && (unicode<44 || unicode>47) && unicode != 92  && unicode != 95)
		return false;
}
function alphaNumericwithSpace(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9  && unicode != 32)
		return false;
}
</script>

<style>
ul.msgg {padding-left:0; }
#msgg li { float:left; }
#msgg br { height:1px; float:left; }
.ui-datepicker-trigger {margin-top:7px; }
#msgg li:first-child {display:block; }

</style>
    
    
<s:if test="%{#attr.dataNotFound!=''}">
	<div class="error-massage-text">
		<s:property value="#attr.dataNotFound" />
	</div>
</s:if>
<div class="container">
<s:form id="applicationForm" action="CandidateAction" enctype="multipart/form-data"  method="post">
<div class="unwrapForm">
<s:hidden name="handicappedValue" id="handicappedValue"/>
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:hidden name="isDataFound" value='<s:property value="candidateDataFilled"/>'/>
<s:hidden name="degreeVal" id="degreeVal"/>
<s:hidden name="cateoryValue" id="cateoryValue"/>
<s:hidden name="additionalDetailsBean.paramvalue" id="paramvalue" value="ageDetailsForm"></s:hidden>
	<s:hidden name="auditentryage" id="auditentryage"  />
<div class="container common_dashboard">
<s:actionerror />
<s:hidden id='hddAddressChkBox'></s:hidden>
<div align="center" style="font-size: 22px;color:  brown;"><strong>APPLICATION FORM</strong></div>
<div align="left" >
	<s:if test="%{#session['SESSION_USER'] != null}">
		<strong>User ID / <span class="tamil"><s:text name="applicationForm.userId"/></span></strong> - <s:label value="%{#session['SESSION_USER'].username}" /><br/>
	</s:if>
	
	<div style="float:left;"><span class="lighttext"><s:text name="applicationForm.note"/> </span> <span class="manadetory-fields">*</span> <span class="lighttext"><s:text name="applicationForm.note1"/></span><span class="lighttext"><span class="tamil"><s:text name="applicationFormT.note"/></span>: <span class="manadetory-fields">*</span> <span class="tamil"><s:text name="applicationFormT.note1"/></span></span>
	</br>
	Kindly upload the documents which does not have any of the mentioned special character's in its file name  %#&+`$+={}[]|\:/"'<>,.? <span class="tamil"><s:text name="docSplChar"/></span> 
</br>&nbsp;</br>
	
	  </div>
	    
	<%--<s:if test='%{instrReqd}'>
		<div style="float:right; width:400px; text-align:right;">
			<a onclick='ShowPop("pop8");' style="cursor: pointer;" >Click here to read the instructions again</a>
		</div>
	</s:if>
	--%>
</div>


<br />


<div id="error-massageAppForm" style="display:none;color:red;" class="error-massage">
      	<ul style="margin-left:-41px;" id="error-ulAppForm">
      	</ul>
</div>
<s:actionmessage escape="false" cssClass="msgg" id="msgg" />
<div id="error-massage" style="display:none" class="error-massage">
    <div class="error-massage-text" style="margin-left:-38px;" >
    	<ul  id="errorMessages" class="msgg">
    	</ul>
    </div>
   
</div>
<br/>
<s:if test='%{candidateDetailsSuccFlag=="true"}'>
<br />
	<div style="border:#999 1px solid;padding:3px;color:green;" id="successMessage">
	<s:property value="candidateDetailsSuccMsg"/>
	</div>
<br/>
</s:if>


<div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Age Details">Age Relaxation Details / <span class="tamil"><s:text name="additional.age"/></span></h1></div>
 
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;">


<s:hidden name="exServicemanFlag" value="%{exServicemanFlag}"></s:hidden>	
   	<s:if test="%{exServicemanFlag==false}">	
    
  <table class="contenttableNew" border="0">
  	<tr id="exserviceTr">
		      <td  width="70%"> Are you an Ex-Servicemen/Ex-Servicewomen or presently serving (going to retire within one year)? / <span class="tamil"><s:text name="additional.exserviceman"/></span><span class="manadetory-fields tabWidth">*</span>
		       </td>
		       <td width="30%"  colspan="3">
	    			<s:select list = "additionalDetailsBean.exServicemanList" name = "additionalDetailsBean.exServiceman" label = "Name" 
				id = "exServiceman" value="%{additionalDetailsBean.exServiceman}"  onchange="return showServiceDiv();"
				/>	
		       </td>
	    </tr>
	    </table>
	   
	   <div class="exserviceDiv" style="display:none">
	     <table class="contenttableNew" border="0">
	    <tr class="exserviceDiv" style="display:none">
		      <td >Service  Number/ <span class="tamil"><s:text name="additional.servicenumber"/></span><span class="manadetory-fields">*</span>
		   
		       </td>
	    	
	    	  <td colspan="3">
	      		<s:textfield name="additionalDetailsBean.serviceNumber" onkeypress="return alphaNumericwithSplChar(event);"  value="%{additionalDetailsBean.serviceNumber}" id = "candidateName" maxlength="30" onpaste="return false;"></s:textfield>

	      		</td>
	      		
	    </tr>
	    
	
	    
    <tr class="exserviceDiv" style="display:none">
	      <td>Date Of Enlistment / <span class="tamil"><s:text name="additional.dateofenlistment"/></span> <span class="manadetory-fields">*</span> <br/>
	      <span class="lighttext">
	       
	      
	      </span>
	      </td>
	      
	      <td >
		      <s:textfield id="dateOfEnlistment" name="additionalDetailsBean.dateOfEnlistment" maxlength="30" value="%{additionalDetailsBean.dateOfEnlistment}" readonly="true"> </s:textfield>
		      
	      </td>
	      <%--<td colspan="2"><span class="lighttext calInstr">Please click on Calendar to select Date of Birth. / <span class="tamil"><s:text name="applicationForm.calenderInstruction"/></span></span></td>
    --%></tr>
    
     <tr class="exserviceDiv" style="display:none">
    	  <td>Date of Discharge / to be discharged / <span class="tamil"><s:text name="additional.dateofdischarge"/></span>  <span class="manadetory-fields">*</span></td>
      	
      	<td colspan="3">
      			 <s:textfield id="dateOfDischarge" value="%{additionalDetailsBean.dateOfDischarge}" name="additionalDetailsBean.dateOfDischarge" readonly="true"> </s:textfield>	
	  	</td>
    </tr >

	    
	     <tr class="exserviceDiv" style="display:none">
    	  <td>Certificate Number / <span class="tamil"><s:text name="additional.certificatenumber"/></span>  <span class="manadetory-fields">*</span></td>
      	
      	<td colspan="3">
      			<s:textfield name="additionalDetailsBean.exCertificateNumber" onkeypress="return alphaNumericwithSplChar(event);" value="%{additionalDetailsBean.exCertificateNumber}" onpaste="return false;" maxlength="30" ></s:textfield>	
	  	</td>
    </tr>
    
    
	    
	 <tr class="exserviceDiv" style="display:none">
    	  <td>Designation Of Issuing Authority / <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>  <span class="manadetory-fields">*</span></td>
      	
      	<td colspan="3">
      			<s:textfield name="additionalDetailsBean.exCertIssuingAuthority" onkeypress="return alphaNumericwithSpace(event);" value="%{additionalDetailsBean.exCertIssuingAuthority}" onpaste="return false;" maxlength="50" ></s:textfield>	
	  	</td>
    </tr><%--
    <tr class="exserviceDiv" style="display:none">
    	  <td>Date Of Issue Of Certificate / <span class="tamil"><s:text name="additional.dateofcertificate"/></span> <span class="manadetory-fields">*</span></td>
      	
      	<td colspan="3">
      			 <s:textfield id="dateOfExCertificate" name="additionalDetailsBean.dateOfExCertificate" onkeypress="return alphanumeric(event);" value="%{additionalDetailsBean.dateOfDischarge}" readonly="true"> </s:textfield>
	  	</td>
    </tr>
    
    --%><tr class="exserviceDiv" style="display:none">
    <td>Discharged / to be discharged certificate / <span class="tamil"><s:text name="additional.dischargecertificate"/></span><span class="manadetory-fields">*</span></td>
    <td >
<div style="display:block; height:auto;">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:hidden name="additionalDetailsBean.ageDocumentUploaded" id="ageDocumentUploaded"/>
<s:hidden name="additionalDetailsBean.ageDocumentFileName" />
<s:hidden name="additionalDetailsBean.candidateDocPk" />
  <table class="" width="100%" border="0" >
    <tr>
     
      <td width="25%" style="padding-left: 0px;" > 
        <s:file  name="additionalDetailsBean.ageData"  id = "eligibilityCriteriaUploadFile" cssStyle="width:250px;"></s:file>
        <s:hidden name="additionalDetailsBean.ageDataHash" id="ageDataHash"/>
        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
      </td>
       <td width="50%" align="center">
       <s:checkbox name="additionalDetailsBean.checkAgeAcceptance" id="check2" value="%{additionalDetailsBean.checkAgeAcceptance}" /> 
    	<br/>Candidate Acceptance Checkbox
      </td>
      <td colspan="2" ><input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" onclick="uploadDocument2();" id="uploadDoc2"/></td>
    </tr>
    <tr>
      
      <td colspan="4" >
      <span class="lighttext"><strong><b>Instruction :</b></strong>Pages of the Discharge Certificate showing the Name, Photo and Date of Discharge should be uploaded as a single pdf file. Serving Ex-servicemen/women should enclose a certificate as mentioned in Brochure.</span>
      <br>
      <span class="lighttext"><strong><b>Note :</b> The File Size should be between <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB to <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB. / <span class="tamil"><s:text name="document.instruction1"/></span></strong></span>
      <br>
       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files</span>
      <br/>
      <span class="tamil"><s:text name="upload.image.instruction"/></span>
      
     
      </td>
    </tr><%--
    <tr>
    	
    	<td style="color:#F00;" colspan="3" >
    		Please upload recent Document 
    	</td>
    	 <td></td>
    </tr>
    --%></table>
    <div id='block7'></div>
</div>
<div id="uploadedDocuments2" style="display: none;" >
		<table cellspacing="0" cellpadding="0" style="clear:both;" width="100%" >
		
		    <tr>
		    
		      <td  width="75%" id='docId'><br/>
		      <a class="blue" href="CandidateAction_getAdditionalDocument.action?candidateDocId=<s:property value="additionalDetailsBean.candidateDocPk"/>"><s:property value="additionalDetailsBean.ageDocumentFileName" /></a>
		      </td>
		     
		      
		    </tr>
		    <tr>
		      <td></td>
		      <td ><br/>
		      </td>
		    </tr>
	  	</table>
</div>

<%--</s:form>  
    
    --%></td>
    
    </tr>
    </table>
    </div><!-- end of exservice man div -->
    </s:if>
    <!-- DEPARTMENT QUOTA DETAILS -->
 
   	 <table class="contenttableNew" >
    <s:hidden name="exServicemanFlag" value="%{exServicemanFlag}"></s:hidden>
     
     <s:hidden name="arpdepartmentQuotaFlag" value="%{arpdepartmentQuotaFlag}"></s:hidden>
     <s:hidden name="departmentQuotaFlag" value="%{departmentQuotaFlag}"></s:hidden>
     <s:hidden name="addDetailRedirectFlag" value="%{addDetailRedirectFlag}"></s:hidden>
    <s:iterator value="wkExperienceDtlsList" status="stat" var="currentObject">
    <s:hidden name="wkExperienceDtlsList[#stat.index].policedept" value="%{policedept}" id="wkExperienceDtlsList_police"></s:hidden>
    </s:iterator>
    
   <!--  <tr id="wardsTr" >-->
    <tr id="deptTr" >
	      <td width="70%">Would You Like To Avail Department Quota? / <span class="tamil"><s:text name="additional.wards"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3" width="30%">
	      
	      	  <s:select list = "additionalDetailsBean.arpdepartmentQuaotaList" name = "additionalDetailsBean.arpdepartmentQuaota" label = "Name" 
				id = "departmentQuaota" value="%{additionalDetailsBean.arpdepartmentQuaota}" onchange="return showDeptQuotaDiv();"
				/>	
		  </td>
		  <s:hidden name="additionalDetailsBean.arpdepartmentQuaota1" value="%{additionalDetailsBean.arpdepartmentQuaota}"></s:hidden>
	  
    </tr>
    </table>
    <div  class="deptDiv" style="display:none">
    <table class="contenttableNew" >
    
    <tr class="deptDiv" style="display:none">
	      <td>Date Of Enlistment / <span class="tamil"><s:text name="additional.dept_dateofenlistment"/></span> <span class="manadetory-fields">*</span> <br/>
	      <span class="lighttext">
	       
	      
	      </span>
	      </td>
	      
	      <td >
		      <s:textfield id="deptdateOfEnlistment" name="additionalDetailsBean.arpdept_dateOfEnlistment" maxlength="30" value="%{additionalDetailsBean.arpdept_dateOfEnlistment}" readonly="true" > </s:textfield>
		      
	      </td>
	      <%--<td colspan="2"><span class="lighttext calInstr">Please click on Calendar to select Date of Birth. / <span class="tamil"><s:text name="applicationForm.calenderInstruction"/></span></span></td>
    --%></tr>
	  <tr class="deptDiv" style="display:none">
    
	      <td>Service as on(Date of Notification) / <span class="tamil"><s:text name="additional.dept_serviceAsOn"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
		   <s:textfield id="deptserviceAsOn" onpaste="return false;" name="additionalDetailsBean.arpdept_serviceAsOn" onkeypress="return alphaNumericwithSplChar(event);"
		  value="%{additionalDetailsBean.arpdept_serviceAsOn}" maxlength="30" readonly="true" ></s:textfield>
		    </td>
	  
    </tr>       
    
    
    <tr class="deptDiv" style="display:none">
    
	      <td>Present Rank / <span class="tamil"><s:text name="additional.presentRank"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      <s:select list="additionalDetailsBean.arpdept_presentRankList" id="deptpresentRank" name="additionalDetailsBean.arpdept_presentRank" headerKey="" headerValue = "Select Present Rank"
		  	 value="%{additionalDetailsBean.arpdept_presentRank}"></s:select>
		  	 
		  	 <s:hidden value="%{additionalDetailsBean.arpdept_presentRank}" id = "deptpresentRankHidden"></s:hidden>
		  </td>
	  
    </tr>
    <tr class="deptDiv" id="deptunitTr" style="display:none;">
	     <td>
	   Present Posting  &#8208; Unit / <span class="tamil"><s:text name="additional.presentPostingUnit"/></span><span class="manadetory-fields">*</span>
	     </td>
	     <td colspan="3">
	       <table width="100%" border="0" cellspacing="0" cellpadding="0">
	     	<tr>
	     		 <td width="40%" style="padding:0;">
	      <s:select list="additionalDetailsBean.arpdept_presentPostingList" name="additionalDetailsBean.arpdeptpresentPostingUnit" id="deptunitList" headerKey="" headerValue = "Select Present Posting" value="%{additionalDetailsBean.arpdeptpresentPostingUnit}" onchange="enableDeptOtherUnitField();" ></s:select>
	      <%--
	      
	      <s:hidden value="%{additionalDetailsBean.presentPostingUnit}" id = "unitListHidden"></s:hidden>
	      
	      --%></td>
	       <td  width="60%"  id="deptotherTR" style="display:none" style="padding-left:20px;"><b>Please Specify Other Unit</b>/ <span class="tamil"><s:text name="applicationForm.deptotherUnit"/></span> <span class="manadetory-fields">*</span> 
	     <s:textfield name="additionalDetailsBean.arpdept_unitsOther" value="%{additionalDetailsBean.arpdept_unitsOther}" onkeypress="return alphabetswithspace(event);"  maxlength="30" onpaste="return false;" cssStyle="width:100px;"  id="deptunitsOther"></s:textfield>
	     </td>
	      </tr>
	   
	      
	      </table>
		 </td> 
	  </tr>
	<tr  id="deptPostingTrDistrict" class="deptDiv" style="display:none;">
	     <td>
	Present Posting  &#8208; District / City / <span class="tamil"><s:text name="additional.presentPosting"/></span><span class="manadetory-fields">*</span>
	     </td>
	     <td colspan="3">
	        <s:select list="additionalDetailsBean.arpdept_districtList" name="additionalDetailsBean.arpdept_presentPosting" id="deptdistrictList" headerKey="" headerValue = "Select Present Posting" value="%{additionalDetailsBean.arpdept_presentPosting}" onchange="enableDeptPoliceStationField();"></s:select>
	     		  <%--<s:hidden value="%{additionalDetailsBean.PresentPosting}" id = "PresentPostingHidden"></s:hidden>
		 --%>
		 
		 </td> 
	</tr>
	
	     
	<tr id="deptpoliceTR1" class="deptDiv" style="display:none;">
	     <td>
	     Present Posting  &#8208; Police Station / <span class="tamil"><s:text name="additional.presentPostingPoliceStation"/> </span> <span class="manadetory-fields">*</span>
	     </td>
	     <td colspan="3">
	      <s:select list="additionalDetailsBean.arpdept_policeStationList" name="additionalDetailsBean.arpdept_policeStation" id="deptpoliceStationList" headerKey="" headerValue = "Select Police Station" value="%{additionalDetailsBean.arpdept_policeStation}"></s:select>
	    <s:hidden value="%{additionalDetailsBean.arpdept_policeStation}" id = "deptpoliceStationHidden"></s:hidden></td> 
	 </tr>

	  <tr class="deptDiv" style="display:none">
    
	      <td>GPF/CPS/RPC Number / <span class="tamil"><s:text name="additional.gpsNumber"/> </span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      
		  <s:textfield id="deptgpsNumber" name="additionalDetailsBean.arpdept_gpsNumber" value="%{additionalDetailsBean.arpdept_gpsNumber}" onkeypress="return alphaNumeric(event);" maxlength="13"  onpaste="return false;"></s:textfield>
		  </td>
	  
    </tr>
    <tr class="deptDiv" style="display:none">
    
	      <td>Certificate Number / <span class="tamil"><s:text name="additional.certificatenumber"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
		   <s:textfield id="deptCertificateNumber" onpaste="return false;" name="additionalDetailsBean.arpdept_CertificateNumber" onkeypress="return alphaNumericwithSplChar(event);"
		  value="%{additionalDetailsBean.arpdept_CertificateNumber}" maxlength="30"></s:textfield>
		  </td>
	  
    </tr>  
    
    <tr class="deptDiv" style="display:none">
    
	      <td>Designation Of Issuing Authority / <span class="tamil"><s:text name="additional.IssuingAuthority"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      
		  <s:textfield id="dept_CertIssuingAuthority" name="additionalDetailsBean.arpdept_CertIssuingAuthority" onkeypress="return alphabetswithspace(event);" value="%{additionalDetailsBean.arpdept_CertIssuingAuthority}"
		   maxlength="50"  onpaste="return false;"></s:textfield>
		  </td>
	  
    </tr>
    <tr class="deptDiv" style="display:none">
    
	      <td>NOC Date / <span class="tamil"><s:text name="additional.dept_NocDate"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
			<s:textfield id="deptNocDate" name="additionalDetailsBean.arpdept_NocDate" value="%{additionalDetailsBean.arpdept_NocDate}" readonly="true"> </s:textfield>
		  
	</td>
	  
    </tr>   
	<tr class="deptDiv" style="display:none">
		    <td> NOC document upload / <span class="tamil"><s:text name="additional.deptcertificate"/></span> <span class="manadetory-fields">*</span></td>
		    <td>
		<div style="display:block; height:auto;">
<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
<s:hidden name="additionalDetailsBean.deptageDocumentUploaded" id="deptageDocumentUploaded"/>
<s:hidden name="additionalDetailsBean.deptageDocumentFileName" />
<s:hidden name="additionalDetailsBean.deptcandidateDocPk" />
  <table class="" width="100%" border="0" >
    <tr>
     
      <td width="25%" style="padding-left: 0px;" > 
        <s:file  name="additionalDetailsBean.deptageData"  id = "depteligibilityCriteriaUploadFile" cssStyle="width:250px;"></s:file>
        <s:hidden name="additionalDetailsBean.deptageDataHash" id="deptageDataHash"/>
        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
      </td>
       <td width="50%" align="center">
       <s:checkbox name="additionalDetailsBean.deptcheckAgeAcceptance" id="check8" value="%{additionalDetailsBean.deptcheckAgeAcceptance}" /> 
    	<br/>Candidate Acceptance Checkbox
      </td>
      <td colspan="2" ><input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" onclick="deptuploadDocument8();" id="deptuploadDoc8"/></td>
    </tr>
    <tr>
      
      <td colspan="4" >
      <span class="lighttext"><strong><b>Instruction :</b></strong>Pages of the Discharge Certificate showing the Name, Photo and Date of Discharge should be uploaded as a single pdf file. Serving Ex-servicemen/women should enclose a certificate as mentioned in Brochure.</span>
      <br>
      <span class="lighttext"><strong><b>Note :</b> The File Size should be between <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB to <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB. / <span class="tamil"><s:text name="document.instruction1"/></span></strong></span>
      <br>
       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files</span>
      <br/>
      <span class="tamil"><s:text name="upload.image.instruction"/></span>
      
     
      </td>
    </tr><%--
    <tr>
    	
    	<td style="color:#F00;" colspan="3" >
    		Please upload recent Document 
    	</td>
    	 <td></td>
    </tr>
    --%></table>
    <div id='block7'></div>
</div>
<div id="deptuploadedDocuments8" style="display: none;" >
		<table cellspacing="0" cellpadding="0" style="clear:both;" width="100%" >
		
		    <tr>
		    
		      <td  width="75%" id='docId'><br/>
		      <a class="blue" href="CandidateAction_getAdditionalDocument.action?candidateDocId=<s:property value="additionalDetailsBean.deptcandidateDocPk"/>"><s:property value="additionalDetailsBean.deptageDocumentFileName" /></a>
		      </td>
		     
		      
		    </tr>
		    <tr>
		      <td></td>
		      <td ><br/>
		      </td>
		    </tr>
	  	</table>
</div>


<%--</s:form>  
    
    --%></td>
    
    
    </tr>
         
    </table>
    
    </div>
    
   
    <!--end of DEPARTMENT QUOTA DETAILS  -->
    <s:hidden name="additionalDetailsBean.widowFlag" value="%{additionalDetailsBean.widowFlag}"></s:hidden>
    <s:hidden name="testCenterApply" value="%{testCenterApply}"></s:hidden>
    <s:if test="%{additionalDetailsBean.widowFlag==true}">
      <table class="contenttableNew" border="0">
    <tr id="widowTr" >
	      <td width="70%">Are You A Destitute Widow ? / <span class="tamil"><s:text name="additional.widow"/></span>  <span class="manadetory-fields">*</span></td>
	    
	       <td width="30%"  colspan="3">
	    			<s:select list = "additionalDetailsBean.widowList" name = "additionalDetailsBean.widow" label = "Name" 
				id = "widow" value="%{additionalDetailsBean.widow}" onchange="return showWidowDiv();"  
				/>	
    </tr>
    </table>
    
    <div class="widowDiv" style="display:none">
      <table class="contenttableNew" border="0">
       <tr class="widowDiv" style="display:none">
	      <td >Name Of Late Husband / <span class="tamil"><s:text name="additional.nameofhusband"/></span>   <span class="manadetory-fields">*</span></td>
	    <td colspan="3">
	     <s:textfield name="additionalDetailsBean.nameOfLateHusband" onkeypress="return alphabetswithspace(event);" onpaste="return false;" value="%{additionalDetailsBean.nameOfLateHusband}"  maxlength="50"></s:textfield>
	     </td>
    </tr>
    
    <tr class="widowDiv" style="display:none">
	      <td>Date Of Death / <span class="tamil"><s:text name="additional.dateofdeath"/></span>   <span class="manadetory-fields">*</span></td>
	    
	      <td colspan="3">
					<s:textfield id="dateOfDeath" name="additionalDetailsBean.dateOfDeath" value="%{additionalDetailsBean.dateOfDeath}" readonly="true"> </s:textfield>
		  </td>
    </tr> 
    
    <tr class="widowDiv" style="display:none">
    
	      <td>Certificate Number / <span class="tamil"><s:text name="additional.certificatenumber"/></span>  <span class="manadetory-fields">*</span></td>
	      
	      <td colspan="3">
	      		<s:textfield id="additionalDetailsBean.deathCertificateNumber" onkeypress="return alphaNumericwithSplChar(event);" onpaste="return false;" name="additionalDetailsBean.deathCertificateNumber" maxlength="30"></s:textfield>
	      </td>
    </tr>
	      <tr class="widowDiv" style="display:none">
    
	      <td>Designation Of Issuing Authority / <span class="tamil"><s:text name="additional.IssuingAuthority"/></span>  <span class="manadetory-fields"> *</span></td>
	      
	      <td colspan="3">
	      		<s:textfield id="deathCertIssuingAuthority" name="additionalDetailsBean.deathCertIssuingAuthority" onkeypress="return alphabetswithspace(event);" onpaste="return false;" value="%{additionalDetailsBean.deathCertIssuingAuthority}" maxlength="50" ></s:textfield>
	      	
	      </td>
	    
    </tr>
    
    <tr class="widowDiv" style="display:none">
	      <td>Date Of Issue Of The Certificate / <span class="tamil"><s:text name="additional.dateofcertificate"/></span> <span class="manadetory-fields"> *</span> </td>
	      <td colspan="3">
	      
	      	 <s:textfield id="dateOfdeathCertificate" name="additionalDetailsBean.dateOfdeathCertificate" value="%{additionalDetailsBean.dateOfdeathCertificate}" readonly="true"> </s:textfield>
	       </td>
    </tr>
    
    <tr class="widowDiv" style="display:none">
    
        <td>Destitute Widow Certificate / <span class="tamil"><s:text name="additional.widowcertificate"/></span> <span class="manadetory-fields">*</span></td>
    <td colspan="3">
<div style="display:block; height:auto;">

<s:hidden name="additionalDetailsBean.ageDocumentUploaded2" id="ageDocumentUploaded2"/>
<s:hidden name="additionalDetailsBean.ageDocumentFileName2" />
<s:hidden name="additionalDetailsBean.candidateDocPk2" />
  <table class="" width="100%" border="0" >
    <tr>
     
      <td width="25%"  style="padding-left: 0px;"> 
        <s:file  name="additionalDetailsBean.ageData2"  id = "eligibilityCriteriaUploadFile2" cssStyle="width:250px;"></s:file>
        <s:hidden name="additionalDetailsBean.ageData2Hash" id="ageData2Hash"/>
        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
      </td>
      <td align="center"><div class=""><s:checkbox name="additionalDetailsBean.checkAgeAcceptance2" id="check3" value="%{additionalDetailsBean.checkAgeAcceptance2}" /></div>
      <br/>Candidate Acceptance Checkbox
      </td>
      
       <td width="10%" align="center">
      
    <input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" onclick="uploadDocument3();" id="uploadDoc3"/>
      </td>
    </tr>
    <tr>
      
      <td colspan="3" >
     
      <span class="lighttext"><strong><b>Instruction :</b></strong>Destitute Widow Certificate issued by Competent authority in TamilNadu Govt should be uploaded.</span>
       <br>
      <span class="lighttext"><strong><b>Note :</b> The File Size should be between <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB to <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB. / <span class="tamil"><s:text name="document.instruction1"/></span></strong></span>
      <br>
       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files</span>
      <br/>
      <span class="tamil"><s:text name="upload.image.instruction"/></span>
      
      </td>
      
    </tr>
    <%--<tr>
    	
    	<td style="color:#F00;" colspan="2" >
    		Please upload recent Document 
    	</td>
    	 <td></td>
    </tr>
    --%></table>
    <div id='block7'></div>
</div>
<div id="uploadedDocuments3" style="display: none;" >
		<table cellspacing="0" cellpadding="0" style="clear:both;" width="100%" >
		
		    <tr>
		    
		      <td  width="75%" id='docId'><br/>
		      <a class="blue" href="CandidateAction_getAdditionalDocument.action?candidateDocId=<s:property value="additionalDetailsBean.candidateDocPk2"/>"><s:property value="additionalDetailsBean.ageDocumentFileName2" /></a>
		      </td>
		     
		      
		    </tr>
		    <tr>
		      <td></td>
		      <td ><br/>
		      </td>
		    </tr>
	  	</table>
</div>
    
    
    
    
    </td>
    
    </tr>
  
    
    
   

    </table>
    </div>
    </s:if>
</div><br class="otherDetailsFields"/> 
<div style="display:none">
<div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Educational Details">Educational Details</h1></div>
<div class="hr-underline2"></div>

</div>

 





 
<div class="clear"></div>
<table class="contenttableNew otherDetailsFields" border="0" width="100%" >
  
  <tr>
  <td width="34%">&nbsp;</td>
	    <td colspan="3" align="left">
	    <s:hidden name="disciplineType" value="%{disciplineType}"></s:hidden>
	    	<s:submit id="saveUpdateAge" value="Save / Update" cssClass="submitBtn button-gradient"  method="saveCandidateAgeRelaxationDetails"></s:submit>      
	    </td>
	    
  </tr>
  <tr>
  <td colspan="5">
  <hr/>
  
  </td>
  
  </tr>
  
  <tr>
  <td colspan="5">
  	<div align ="right">
		<s:if test='%{candiateDetailsMandatory}'>
			
				<s:submit method="getCandidateQuota" value="Continue"  cssClass="submitBtn button-gradient" disabled="true"></s:submit>
			
		</s:if>
		
		<s:else>
			<s:submit method="getCandidateQuota" onclick="auditEntry();" value="Continue" cssClass="submitBtn button-gradient"></s:submit>
		</s:else>
		</div>
  </td>
  </tr>
  </table>
  
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
</s:form>
<div id='block7'></div>

</div>

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

