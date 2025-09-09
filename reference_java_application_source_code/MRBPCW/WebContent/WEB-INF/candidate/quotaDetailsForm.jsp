<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript" src="js/q.js"></script>
<script type="text/javascript" src="js/spark-md5.min.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	/*var reader = new FileReader();
	reader.onload = function (event) {
	  var file_sha1 = sha1(event.target.result)
	};
	var file=document.getElementById('eligibilityCriteriaUploadFile');
	reader.readAsArrayBuffer(file);*/
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
			
	$(".subNavBg").hide();
	$("#uploadDoc").prop('value', 'Upload');
	$("#uploadDoc2").prop('value', 'Upload');
	
	$("#uploadDoc3").prop('value', 'Upload');
	$("#uploadDoc4").prop('value', 'Upload');
	$("#uploadDoc5").prop('value', 'Upload');
	$("#uploadDoc6").prop('value', 'Upload');
	$("#uploadDoc7").prop('value', 'Upload');
	var arpdepartmentQuaota='<s:property value="%{additionalDetailsBean.arpdepartmentQuaota}"></s:property>';
	var arpdepartmentQuaota1='<s:property value="%{additionalDetailsBean.arpdepartmentQuaota1}"></s:property>';
	var departmentQuaota='<s:property value="%{additionalDetailsBean.departmentQuaota}"></s:property>';
	var disciplineType_string='<s:property value="%{disciplineType}"></s:property>';
	var dept_policeStation='<s:property value="%{additionalDetailsBean.dept_policeStation}"></s:property>';
	var deptpresentPostingUnit='<s:property value="%{additionalDetailsBean.deptpresentPostingUnit}"></s:property>';
	var exServiceman='<s:property value="%{additionalDetailsBean.exServiceman}"></s:property>';
	var exservicemanflag='<s:property value="%{exServicemanFlag}"></s:property>';

	var disciplineType=parseInt(disciplineType_string);
			
	var widow='<s:property value="%{additionalDetailsBean.widow}"></s:property>';
	var agerelaxationFlag='<s:property value="%{addDetailRedirectFlag}"></s:property>';
	var deptquotaflag='<s:property value="%{departmentQuotaFlag}"></s:property>';
	var policefield_value=$("#wkExperienceDtlsList_police").val();	
	var arpdepartmentQuotaFlag='<s:property value="%{arpdepartmentQuotaFlag}"></s:property>';
//<s:if test="%{disciplineType==14}">
	if(disciplineType==14){
	$("#spcialQuotaDiv").hide();
	$("#wardsTr").hide();
	if(agerelaxationFlag=="true" ){
		if(deptquotaflag!=false || widow=="Yes" || exServiceman=="64" || exservicemanflag!= false){
			$("#deptTr").hide();
			$(".deptDiv").hide();
			$("#widowTr").hide();
			$(".widowDiv").hide();
			$("#exserviceTr").hide();
			$(".exserviceDiv").hide();
			}
		}else{
			/*if(policefield_value=="Police"){
				
				if(deptquotaflag!=true){
					if(departmentQuaota=="Yes"){
						$("#deptTr").show();
						$(".deptDiv").show()
						$("#exServiceman").attr('disabled','disabled');
						$("#widow").attr('disabled','disabled');
					}
					else if(departmentQuaota=="No"){
						$("#deptTr").show();
						$(".deptDiv").hide();
					}else{
						//$("#deptTr").hide();
						//$(".deptDiv").hide();
						}
					}
				}*/	
			if(deptquotaflag!=true){
				if(departmentQuaota=="Yes"){
					$("#deptTr").show();
					$(".deptDiv").show()
					$("#exServiceman").attr('disabled','disabled');
					$("#widow").attr('disabled','disabled');

					//showing dropdowns with respect to present posting unit 
					if(dept_policeStation!=null && dept_policeStation!=""){
						$("#deptpoliceTR1").show();
						}
					if(deptpresentPostingUnit=="POLICE HEADQUARTERS" || deptpresentPostingUnit=="OTHER"){
						$("#deptpoliceTR1").hide();
						$("#deptPostingTrDistrict").hide();
						}
				
					if(deptpresentPostingUnit=="YOUTH BRIGADE" || deptpresentPostingUnit=="TALUK POLICE"){
						$("#deptpoliceTR1").show();
						$("#deptPostingTrDistrict").show();
					}else{
						$("#deptpoliceTR1").hide();
						$("#deptPostingTrDistrict").hide();
					}
					if(deptpresentPostingUnit=="DPO/CPO" || deptpresentPostingUnit=="RANGE OFFICE" || deptpresentPostingUnit=="TAMIL NADU SPECIAL POLICE" || deptpresentPostingUnit=="SPECIAL UNIT" || deptpresentPostingUnit=="BATTALION OFFICE" || deptpresentPostingUnit=="ARMED RESERVE" || deptpresentPostingUnit=="OTHER")
					{
						$("#deptPostingTrDistrict").show();
						$("#deptpoliceTR1").hide();
					}
					if(deptpresentPostingUnit=="OTHER"){
						$("#deptotherTR").show();
						$("#deptpoliceTR1").hide();
						$("#deptPostingTrDistrict").hide();
					}
				
				}
				else if(departmentQuaota=="No"){
					$("#deptTr").show();
					$(".deptDiv").hide();
				}else{
					//$("#deptTr").hide();
					//$(".deptDiv").hide();
					}
				}
		}
		}else{
			$("#spcialQuotaDiv").show();
			$("#wardsTr").show();
		}

/*
	if(arpdepartmentQuaota=="Yes" || widow=="Yes" || exServiceman=="64" ){
		$("#deptTr").hide();
		$(".deptDiv").hide();
		$("#widowTr").hide();
		$(".widowDiv").hide();
		$("#exserviceTr").hide();
		
		}*/
		
	
	/*<s:if test="%{additionalDetailsBean.arpdepartmentQuaota=='Yes'}">
		$("#deptTr").hide();
		$(".deptDiv").hide();
	</s:if>
	<s:else>
		<s:if test="%{additionalDetailsBean.departmentQuaota=='Yes'}">
		$("#deptTr").show();
		$(".deptDiv").show();
		</s:if>
		<s:else>
		$(".deptDiv").hide();
		</s:else>
	</s:else>
</s:if>
<s:else>
$("#spcialQuotaDiv").show();
$("#wardsTr").show();
</s:else>*/

	
	if(($("#ageDocumentUploaded").val() == "true"))
	{
		$("#uploadedDocuments2").show();
	}
	
	if($("#ageDocumentUploaded2").val() == "true")
	{
		$("#uploadedDocuments").show();
	}

	if($("#quotaDocumentUploaded").val() == "true")
	{
		$("#uploadedDocuments3").show();
	}
	
	if($("#quotaDocumentUploaded2").val() == "true")
	{
		$("#uploadedDocuments4").show();
	}
	if($("#quotaDocumentUploaded3").val() == "true")
	{
		$("#uploadedDocuments5").show();
	}
	
	if($("#quotaDocumentUploaded4").val() == "true")
	{
		$("#uploadedDocuments6").show();
	}
	if($("#quotaDocumentUploaded5").val() == "true")
	{
		$("#uploadedDocuments7").show();
	}
	if($("#quotaDocumentUploaded8").val() == "true")
	{
		$("#uploadedDocuments8").show();
	}
	if($("#extraQualificationDocumentUploaded").val() == "true")
	{
		$("#uploadedExtraQualDocuments").show();
	}
	
	<s:if test='%{additionalDetailsBean.sportsQuaota=="Yes"}'>
	$(".sportDiv").show();
	
	</s:if>
	<s:if test='%{additionalDetailsBean.wardsQuaota=="Yes"}'>
	$(".wardsDiv").show();
	
	</s:if>
	
	
	
	<s:if test='%{additionalDetailsBean.nccCertificate=="Yes"}'>
	$(".nccDiv").show();
	
	</s:if>
	<s:if test='%{additionalDetailsBean.nssCertificate=="Yes"}'>
	$(".nssDiv").show();
	
	</s:if>
	<s:if test='%{additionalDetailsBean.crime=="Yes"}'>
	$(".crimeDiv").show();

	</s:if>
	<s:if test='%{additionalDetailsBean.boardName=="Yes"}'>
	$(".recDiv").show();
	
	</s:if>

	<s:if test='%{additionalDetailsBean.extraQualification=="Yes"}'>
		$(".extraQualificationDiv").show();
	</s:if>
	
	<s:if test="%{additionalDetailsBean.policeStation!=null && additionalDetailsBean.policeStation!=''}">
	$("#policeTR1").show();
	
	</s:if>

	

	<s:if test="%{genderVal=='Female' && status=='Married'}">
	$("#wardsTr").hide();
	$(".wardsDiv").hide();	
	$("#policeTR1").hide();	
	</s:if>
	<s:if test="%{genderVal=='Female' && status=='Single' && additionalDetailsBean.widow=='Yes'}" >
	$("#wardsTr").hide();
	$(".wardsDiv").hide();	
	$("#policeTR1").hide();	
	</s:if>
	
	<s:if test="%{additionalDetailsBean.presentPostingUnit=='POLICE HEADQUARTERS' || additionalDetailsBean.presentPostingUnit=='OTHER'}">
	$("#policeTR1").hide();
	$("#PostingTrDistrict").hide();
	</s:if>

	<s:if test="%{additionalDetailsBean.deptpresentPostingUnit=='POLICE HEADQUARTERS' || additionalDetailsBean.deptpresentPostingUnit=='OTHER'}">
	$("#deptpoliceTR1").hide();
	$("#deptPostingTrDistrict").hide();
	</s:if>
	
	<s:if test="%{ additionalDetailsBean.presentPostingUnit=='OTHER'}">
	$("#otherTR").show();
	$("#policeTR1").hide();
		$("#PostingTrDistrict").hide();
	</s:if>

	<s:if test="%{ additionalDetailsBean.deptpresentPostingUnit=='OTHER'}">
	$("#deptotherTR").show();
	$("#deptpoliceTR1").hide();
		$("#deptPostingTrDistrict").hide();
	</s:if>

	<s:if test="%{ additionalDetailsBean.presentPostingUnit=='YOUTH BRIGADE' || additionalDetailsBean.presentPostingUnit=='TALUK POLICE' }">
	$("#policeTR1").show();
	$("#PostingTrDistrict").show();
	</s:if>
	<s:else>
	$("#policeTR1").hide();
	$("#PostingTrDistrict").hide();
	</s:else>

	<s:if test="%{additionalDetailsBean.presentPostingUnit=='DPO/CPO' || additionalDetailsBean.presentPostingUnit=='RANGE OFFICE' || additionalDetailsBean.presentPostingUnit=='TAMIL NADU SPECIAL POLICE' || additionalDetailsBean.presentPostingUnit=='SPECIAL UNIT' || additionalDetailsBean.presentPostingUnit=='BATTALION OFFICE'}">
	$("#PostingTrDistrict").show();
	</s:if>

	<s:if test="%{additionalDetailsBean.deptpresentPostingUnit=='DPO/CPO' || additionalDetailsBean.deptpresentPostingUnit=='RANGE OFFICE' || additionalDetailsBean.presentPostingUnit=='TAMIL NADU SPECIAL POLICE' || additionalDetailsBean.deptpresentPostingUnit=='SPECIAL UNIT' || additionalDetailsBean.deptpresentPostingUnit=='BATTALION OFFICE' || additionalDetailsBean.deptpresentPostingUnit=='ARMED RESERVE'}">
	$("#deptpoliceTR1").hide();
	
	</s:if>
	
	<s:if test="%{additionalDetailsBean.presentPostingUnit=='TAMIL NADU SPECIAL POLICE'}">
		$("#deptpoliceTR1").hide();
		$("#deptPostingTrDistrict").hide();
		</s:if>
	$("#uploadDoc3").prop('value', 'Upload');
	$("#uploadDoc2").prop('value', 'Upload');


	if(($("#ageDocumentUploaded").val() == "true"))
	{
		$("#uploadedDocuments2").show();
	}
	
	if($("#ageDocumentUploaded2").val() == "true")
	{
		$("#uploadedDocuments3").show();
	}

	<s:if test='%{additionalDetailsBean.exServiceman=="64" }'>
	$(".exserviceDiv").show();
	$("#widow").attr('disabled','disabled');
	$("#wardsQuaota").attr('disabled','disabled');
	$("#departmentQuaota").attr('disabled','disabled');
	</s:if>
	<s:if test='%{additionalDetailsBean.widow=="Yes" }'>
	$(".widowDiv").show();
	$("#exServiceman").attr('disabled','disabled');
	$("#wardsQuaota").attr('disabled','disabled');
	$("#departmentQuaota").attr('disabled','disabled');
	</s:if>
	<s:if test='%{additionalDetailsBean.wardsQuaota=="Yes"}'>
	
	$("#exServiceman").attr('disabled','disabled');
	$("#widow").attr('disabled','disabled');
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
		maxDate: -1,
		//minDate: new Date(x[0]-3,x[1],x[2]),
		//maxDate: new Date(aEr[0]+1,aEr[1]-1,aEr[2]),
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
	        //$( "#dateOfExCertificate" ).datepicker( "option", "maxDate", -1 );
	        var buttonDisabled=$('#saveUpdate').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdate').removeAttr("disabled");
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
			}
	        //$( "#dateOfDischarge" ).datepicker( "option", "maxDate", selectedDate );
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
	        var buttonDisabled=$('#saveUpdate').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdate').removeAttr("disabled");
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
	        var buttonDisabled=$('#saveUpdate').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdate').removeAttr("disabled");
			}
	      
	      }
    });
    $("#dateOfStartEvent").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("07/01/1972"),
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		defaultDate: new Date('1 January 1985'),
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
		minDate: new Date("07/01/1972"),
		maxDate:0,// new Date(x[0],x[1],x[2]),
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		defaultDate: new Date('1 January 1985'),
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
		minDate: new Date("07/01/1972"),
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		defaultDate: new Date('1 January 1985'),
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
    $("#dateOfNccCertificate").datepicker({
		showOn: "button",
		changeMonth: true,
		changeYear: true,
		yearRange: range,
		minDate: new Date("07/01/1972"),
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		defaultDate: new Date('1 January 1985'),
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
		minDate: new Date("07/01/1972"),
		maxDate: 0,
		buttonImageOnly: true,
		buttonImage: "images/cale-img.gif",
		dateFormat: 'dd-M-yy',
		defaultDate: new Date('1 January 1985'),
		onSelect: function( selectedDate ) {
		       
	        var buttonDisabled=$('#saveUpdate').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdate').removeAttr("disabled");
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
    //loadComboBox();
    hideUDP();
	
	if($('#successMessage') && $('#successMessage').text().trim() == "Candidate Details saved successfully"){
		$('#saveUpdate').attr('disabled',true);
	}
	if(applicationForm){
		applicationForm.addEventListener('change', function (evt) {
			$('#saveUpdate').removeAttr("disabled");
		});
		applicationForm.addEventListener('input', function (evt) {
			var buttonDisabled=$('#saveUpdate').attr("disabled");
			if (buttonDisabled != undefined && buttonDisabled != "undefined" && buttonDisabled == "disabled") {
				$('#saveUpdate').removeAttr("disabled");
			}
			
		});
		

		
	}
		
    
});
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
function auditEntry()
{
	document.getElementById("auditentryquota").value="auditentryquota";
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
		$("#wardsQuaota").attr('disabled','disabled');
		$("#departmentQuaota").attr('disabled','disabled');
	}
	else
	{
		 //var txt;
   		// var r = confirm("Changing Option To No Will Delete The  Uploaded Document Press OK Button To Continue!");
    	//if (r == true) {
        
    
		$('.exserviceDiv').find('input').val(''); 
		$(".exserviceDiv").hide();
		$("#msgg").hide();
		$("#uploadedDocuments2").hide();

		var lAgeRelaxationType="ex";
		var lDisciplineType=$('#disciplineType').val();
	   
		dataString = "AgeRelaxationType="+lAgeRelaxationType+"&DisciplineType="+lDisciplineType

		$("#widow").removeAttr('disabled');
		$("#departmentQuaota").removeAttr('disabled');
		$("#wardsQuaota").removeAttr('disabled');
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
		$("#uploadDoc").prop('value', 'Upload');
		$("#uploadDoc3").prop('value', 'Upload');
		$("#uploadDoc2").prop('value', 'Upload');
		$("#exServiceman").attr('disabled','disabled');
		$("#wardsQuaota").attr('disabled','disabled');
		$("#departmentQuaota").attr('disabled','disabled');
	}
	else
	{
		 $('.widowDiv').find('input').val(''); 
		$(".widowDiv").hide();
		$("#msgg").hide();
		$("#uploadedDocuments").hide();
		$("#exServiceman").removeAttr('disabled');
		$("#wardsQuaota").removeAttr('disabled');
		$(".widowDiv select").removeClass('red-border');
		$(".widowDiv input").removeClass('red-border');
		$("#departmentQuaota").removeAttr('disabled');
	}
}
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
function showWardsDiv()
{
	if($("#wardsQuaota").val()!=null && $("#wardsQuaota").val()!="" &&  $("#wardsQuaota option:selected").text()!="No")
	{		
		$(".wardsDiv").show();
		$("#uploadDoc3").prop('value', 'Upload');
		$("#uploadDoc4").prop('value', 'Upload');
		$("#uploadDoc5").prop('value', 'Upload');
		$("#uploadDoc6").prop('value', 'Upload');
		$("#uploadDoc7").prop('value', 'Upload');
		$("#exServiceman").attr('disabled','disabled');
		$("#widow").attr('disabled','disabled');
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
		$("#uploadedDocuments5").hide();
		$(".pfDiv").hide();
		$(".wardsDiv select").removeClass('red-border');
		$(".wardsDiv input").removeClass('red-border');
		$("#exServiceman").removeAttr('disabled');
		 $("#widow").removeAttr('disabled');
	}
}
function showDeptQuotaDiv(){
	if($("#departmentQuaota").val()!=null && $("#departmentQuaota").val()!="" &&  $("#departmentQuaota option:selected").text()!="No")
	{		
		$(".deptDiv").show();
		$("#uploadDoc3").prop('value', 'Upload');
		$("#uploadDoc4").prop('value', 'Upload');
		$("#uploadDoc5").prop('value', 'Upload');
		$("#uploadDoc6").prop('value', 'Upload');
		$("#uploadDoc7").prop('value', 'Upload');
		$("#deptuploadDoc8").prop('value', 'Upload');
		$("#exServiceman").attr('disabled','disabled');
		$("#widow").attr('disabled','disabled');
	}
	else
	{
		 //$('.deptDiv').find('input').val('');
		// $('.deptDiv').find('select').val('');
		 //$('.deptDiv').find('select').val('');
		// $('.deptDiv').find('a').remove();
		// $('#unitList').empty();
		// $('#unitList').append($('<option></option>').val("").html("Select Unit"));
		// $('#presentRank').empty();
		// $('#presentRank').append($('<option></option>').val("").html("Select Rank"));
		// $('#districtList').empty();
		// $('#districtList').append($('<option></option>').val("").html("Select District"));
		 
		$(".deptDiv").hide();
		//$("#uploadedDocuments5").hide();
		//$(".pfDiv").hide();
		//$(".deptDiv select").removeClass('red-border');
		//$(".deptDiv input").removeClass('red-border');
		$("#exServiceman").removeAttr('disabled');
		 $("#widow").removeAttr('disabled');
	}
}

function showNccDiv()
{
	if($("#nccCertificate").val()!=null && $("#nccCertificate").val()!="" &&  $("#nccCertificate option:selected").text()!="No")
	{		
		$(".nccDiv").show();
		$("#uploadDoc3").prop('value', 'Upload');
		$("#uploadDoc4").prop('value', 'Upload');
		$("#uploadDoc5").prop('value', 'Upload');
		$("#uploadDoc6").prop('value', 'Upload');
		$("#uploadDoc7").prop('value', 'Upload');
	}
	else
	{
		$('.nccDiv').find('input').val('');
		$('.nccDiv').find('select').val('');
		$('.nccDiv').find('a').remove();
		$(".nccDiv").hide();
		$("#uploadedDocuments6").hide();
		$(".nccDiv select").removeClass('red-border');
		$(".nccDiv input").removeClass('red-border');
	}
}
function showNssDiv()
{
	if($("#nssCertificate").val()!=null && $("#nssCertificate").val()!="" &&  $("#nssCertificate option:selected").text()!="No")
	{		
		$(".nssDiv").show();
		$("#uploadDoc3").prop('value', 'Upload');
		$("#uploadDoc4").prop('value', 'Upload');
		$("#uploadDoc5").prop('value', 'Upload');
		$("#uploadDoc6").prop('value', 'Upload');
		$("#uploadDoc7").prop('value', 'Upload');
	}
	else
	{
		$('.nssDiv').find('input').val('');
		$('.nssDiv').find('select').val('');
		$('.nssDiv').find('a').remove();
		$(".nssDiv").hide();
		$("#uploadedDocuments7").hide();
		$(".nssDiv select").removeClass('red-border');
		$(".nssDiv input").removeClass('red-border');
	}
}

function showExtraQualificationDiv() {
	if($("#extraQualification").val()!=null && $("#extraQualification").val()!="" &&  $("#extraQualification option:selected").text()!="No")
	{	
		// show div
		$(".extraQualificationDiv").show();
		$("#extraQualUploadButton").prop('value', 'Upload');
	} else {
		// hide div
		$('.extraQualificationDiv').find('input').val('');
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
				// $('#PresentPostingHidden').val("");
			}
	 }
	 else
	 {
		 $("#deptpoliceTR1").hide();
		
				
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

function populateUnit() {

	
	
	var presentRank = $("#presentRank").val();
	var countryName = $("#presentRank option:selected").text();
	
	dataString = "presentRank="+presentRank
	
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
	else
	{
		$('#unitList').empty();
		 $('#unitList').append(
			           $('<option></option>').val("").html("Select Unit")
			     );
		 $('#districtList').empty();
		 $('#districtList').append(
			           $('<option></option>').val("").html("Select District")
			     );
		 $('#policeStationList').empty();
		 $('#policeStationList').append(
			           $('<option></option>').val("").html("Select Police Station")
			     );
		
		 
	}
		
}

function uploadDocument3()
{
	var attachmentDoc = $("#eligibilityCriteriaUploadFile3").val();
	var check2=$("#check3").attr('checked')?true:false;
	if(check2==false)
	{
		message = "Please select checkbox to upload Latest Form I Or Form II Or Form III Certificate.";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else{
		calculate(document.getElementById('eligibilityCriteriaUploadFile3'),"uploadDocument3");
		$("#error-massage").hide();
		//$("#checkl").attr("disabled",true);
	}
	if(attachmentDoc == ""){
		var message = "Please select file to upload Latest Form I Or Form II Or Form III Certificate.";
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

function uploadDocument4()
{
	var attachmentDoc = $("#eligibilityCriteriaUploadFile4").val();
	var check2=$("#check4").attr('checked')?true:false;
	if(check2==false)
	{
		message = "Please select checkbox to upload Form I /Form II/ Form III/ District Level Participation Certificate.";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else{
		calculate(document.getElementById('eligibilityCriteriaUploadFile4'),"uploadDocument4");
		$("#error-massage").hide();
		//$("#checkl").attr("disabled",true);
	}
	if(attachmentDoc == ""){
		var message = "Please select file to upload Form I /Form II/ Form III/ District Level Participation Certificate.";
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

function uploadDocument5()
{
	var attachmentDoc = $("#eligibilityCriteriaUploadFile5").val();
	var check2=$("#check5").attr('checked')?true:false;
	if(check2==false)
	{
		message = "Please select checkbox to upload Ward Certificate.";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else{
		calculate(document.getElementById('eligibilityCriteriaUploadFile5'),"uploadDocument5");
		$("#error-massage").hide();
		//$("#checkl").attr("disabled",true);
	}
	if(attachmentDoc == ""){
		var message = "Please select file to upload Ward Certificate.";
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

function uploadDocument6()
{
	var attachmentDoc = $("#eligibilityCriteriaUploadFile6").val();
	var check2=$("#check6").attr('checked')?true:false;
	if(check2==false)
	{
		message = "Please select checkbox to upload Highest Certificate(For NCC)";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else{
		calculate(document.getElementById('eligibilityCriteriaUploadFile6'),"uploadDocument6");
		$("#error-massage").hide();
		//$("#checkl").attr("disabled",true);
	}
	if(attachmentDoc == ""){
		var message = "Please select file to upload Highest Certificate(For NCC)";
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

function uploadDocument7()
{
	var attachmentDoc = $("#eligibilityCriteriaUploadFile7").val();
	var check2=$("#check7").attr('checked')?true:false;
	if(check2==false)
	{
		message = "Please select checkbox to upload Highest Certificate(For NSS)";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else{
		calculate(document.getElementById('eligibilityCriteriaUploadFile7'),"uploadDocument7");
		$("#error-massage").hide();
		//$("#checkl").attr("disabled",true);
	}
	if(attachmentDoc == ""){
		var message = "Please select file to upload Highest Certificate(For NSS)";
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

function uploadDocument8()
{
	var attachmentDoc = $("#eligibilityCriteriaUploadFile8").val();
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
		calculate(document.getElementById('eligibilityCriteriaUploadFile8'),"uploadDocument8");
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
	      if(met=="uploadDocument")
		  {
			  document.getElementById("ageData2Hash").value=hashVal;
		  }
	      if(met=="uploadDocument3")
		  {
			 // document.getElementById("quotaDataHash").value=hashVal;
		  }
	      if(met=="uploadDocument4")
		  {
			  document.getElementById("quotaData2Hash").value=hashVal;
		  }
	      if(met=="uploadDocument5")
		  {
			  document.getElementById("quotaData3Hash").value=hashVal;
		  }
	      if(met=="uploadDocument6")
		  {
			  document.getElementById("quotaData4Hash").value=hashVal;
		  }
	      if(met=="uploadDocument7")
		  {
			  document.getElementById("quotaData5Hash").value=hashVal;
		  }
	      if(met=="uploadDocument8")
		  {
			  document.getElementById("quotaData8Hash").value=hashVal;
		  }
	       if(met=="uploadExtraQualificationDoc")
		  {
			  document.getElementById("uploadExtraQualificationHash").value=hashVal;
		  }
	      document.applicationForm.action="CandidateAction_insertQuotaDetailsDocument.action";
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
	//	document.applicationForm.action="CandidateAction_insertQuotaDetailsDocument.action";
		//document.applicationForm.submit();
		//$("#uploadDoc2").attr("disabled",true);
	//}else{
	//	$("#test2").show();
	//}
}
function uploadDocument()
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
		calculate(document.getElementById('eligibilityCriteriaUploadFile2'),"uploadDocument");
		$("#error-massage").hide();
		//$("#checkl").attr("disabled",true);
	}
	if(attachmentDoc == ""){
		var message = "Please select file to upload Destitute Widow certificate";
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

function uploadExtraQualificationDoc()
{
	var attachmentDoc = $("#extraQualificationUploadFile").val();
	var check2=$("#checkExtraQualification").attr('checked')?true:false;
	if(attachmentDoc == ""){
		var message = "Please select file to upload Additional Qualification Document";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else if(check2==false){
		message = "Please select checkbox to upload Additional Qualification Document";
		var ulID = "errorMessages";
		var divID = "error-massage";
		createErrorList(message, ulID, divID); 
		$('html, body').animate({ scrollTop: 0 }, 0);
		$("#actionMsgDoc").hide();
		$("#error-ul3").hide();
		return false;
	}else{
		calculate(document.getElementById('extraQualificationUploadFile'),"uploadExtraQualificationDoc");
		$("#error-massage").hide();
	}
}

function loadComboBox()
{
	
	var dataString ;
	if(($("#wardname").val()!=null && $("#wardname").val()!='') && ($("#departmentType").val()!=null && $("#departmentType").val()!=''))
	{
		var presentRank;
		if($("#wardname").val()=='1')
		{
			presentRank='Assistant';
		}
		if($("#wardname").val()=='2')
		{
			presentRank='Administrative Officer';
		}

		dataString = "val="+$("#wardname").val()+"&dept="+$("#departmentType").val();
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
	hideUDP();
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
function alphaNumeric(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9 )
		return false;
}
function alphaNumericwithSplChar(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9 && unicode != 46  && (unicode<44 || unicode>47) && unicode != 92  && unicode != 95)
		return false;
}
function alphaNumericwithSplCharForUnits(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9 
	 && unicode != 46  && unicode != 40  && unicode != 41 && unicode != 47 && unicode != 92 && unicode !=95 && unicode != 45)
		return false;
}
function alphaNumericwithSplCharForUnitsNss(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9 
	 && unicode != 46  && unicode != 40 && unicode != 32 && unicode != 41 && unicode != 47 && unicode != 92 && unicode !=95 && unicode != 45)
		return false;
}
function alphaNumericwithSpace(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode;
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48 || unicode>57) && unicode != 8 && unicode != 9  && unicode != 32)
		return false;
}
function hideUDP()
{
	if($("#departmentType").val() == '2' || $("#departmentType").val() == '3')
	{
		$("#unitTr").hide();
		$("#PostingTrDistrict").hide();
		$("#policeTR1").hide();
		$(".pfDiv").show();
	}
	else if($("#departmentType").val() == '1')
	{	
		
				
		$("#unitTr").show();
		if($("#unitList").val() === 'POLICE HEADQUARTERS' || $("#unitList").val() === 'OTHER'){
			$("#PostingTrDistrict").hide();
		}else{
			$("#PostingTrDistrict").show();
		}
		if($("#unitList").val() === 'ARMED RESERVE' || $("#unitList").val() === 'POLICE HEADQUARTERS' || $("#unitList").val() === 'DPO/CPO' || $("#unitList").val() === 'RANGE OFFICE' || $("#unitList").val() === 'BATTALION OFFICE' || $("#unitList").val() === 'SPECIAL UNIT' || $("#unitList").val() === 'TAMIL NADU SPECIAL POLICE' || $("#unitList").val() === 'OTHER')
		{
			$("#policeTR1").hide();
		}else{
			$("#policeTR1").show();
		}
		$(".pfDiv").hide();
	}
}

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
<div class="container">
<s:form id="applicationForm" action="CandidateAction" enctype="multipart/form-data" method="post">
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


<div style="text-align:left; clear:both;"><h1 class="pageTitle" title="">Quotas / Special Marks Details / <span class="tamil"><s:text name="additional.quotaHeading"/></span> </h1></div>
 
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div style="display:block; min-height:300px; height:auto;">



  <table class="contenttableNew" >
  
  	    <tr id="spcialQuotaDiv">
	      <td width="80%" >Would You Like To Avail  Special Quota And/Or Special Marks For Sports? / <span class="tamil"><s:text name="additional.sports"/></span> <span class="manadetory-fields"> *</span></td>
	      
	      <td width="20%" colspan="3">
	     		<s:select list = "additionalDetailsBean.sportsQuaotaList" name = "additionalDetailsBean.sportsQuaota" label = "Name" 
				id = "sportsQuaota" value="%{additionalDetailsBean.sportsQuaota}" onchange="return showSportDiv();"
				/>	
	      </td>
     </tr>
  	
  	</table>
  	
  	
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
    	  <td>Event /Tournament Name / <span class="tamil"><s:text name="additional.eventName"/></span>  <span class="manadetory-fields">*</span></td>
      	<td colspan="3">
      			 <s:textfield id="eventName" name="additionalDetailsBean.eventName" onkeypress="return alphaNumericwithSpace(event);" onpaste="return false;" value="%{additionalDetailsBean.eventName}" maxlength="50"> </s:textfield>	
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
    
    --%><tr class="sportDiv" style="display:none">
		    <td>Form I /Form II/ Form III/ District Level<br/> Participation Certificate / <span class="tamil"><s:text name="additional.sportscertificate2"/></span> <span class="manadetory-fields">*</span></td>
		    <td>
		<div style="display:block; height:auto;">
		<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
		<s:hidden name="additionalDetailsBean.quotaDocumentUploaded2" value="%{additionalDetailsBean.quotaDocumentUploaded2}" id="quotaDocumentUploaded2"/>
		<s:hidden name="additionalDetailsBean.quotaDocumentFileName2" />
		<s:hidden name="additionalDetailsBean.candidateDocPk4" />
		<s:hidden name="auditentryquota" id="auditentryquota"  />
		  <table class="" width="100%">
		    <tr>
		     
		      <td width="25%" > 
		        <s:file  name="additionalDetailsBean.quotaData2"  id = "eligibilityCriteriaUploadFile4"  cssStyle="width:250px;"></s:file>
		        <s:hidden name="additionalDetailsBean.quotaData2Hash" id="quotaData2Hash"/>
		        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
		      </td>
		       <td width="50%" align="center">
		      <div class=""><s:checkbox name="additionalDetailsBean.checkQuotaAcceptance2" id="check4" value="%{additionalDetailsBean.checkQuotaAcceptance2}" /><br/>Candidate Acceptance Checkbox</div>
		    
		      </td>
		      <td><input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" onclick="uploadDocument4();" id="uploadDoc4"/></td>
		    </tr>
		    <tr>
		      
		      <td colspan="3" >
		      <span class="lighttext"><strong><b>Instruction :</b></strong>Form-I- Represented National; Form-II- represented State; Form-III: Represented for Inter University</span>
		      <br>
		      <span class="lighttext"><strong><b>Note :</b> The File Size should be between <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB to <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB. / <span class="tamil"><s:text name="document.instruction1"/></span></strong></span>
			      <br>
			       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files</span>
			      <br/>
			      <span class="tamil"><s:text name="upload.image.instruction"/></span>
			   </td>
		      
		    </tr><%--
		    <tr>
		    	
		    	<td style="color:#F00;" colspan="3">
		    		Please upload recent Document 
		    	  
		    </tr>
		    --%></table>
		    <div id='block7'></div>
		</div>
		<div id="uploadedDocuments4" style="display: none;" >
				<table cellspacing="0" cellpadding="0" style="clear:both;" width="100%" >
				
				    <tr>
				    
				      <td  width="75%" id='docId'><br/>
				      <a class="blue" href="CandidateAction_getAdditionalDocument.action?candidateDocId=<s:property value="additionalDetailsBean.candidateDocPk4"/>"><s:property value="additionalDetailsBean.quotaDocumentFileName2" /></a>
				      </td>
				     
				      
				    </tr>
				    
			  	</table>
		</div>

<%--</s:form>  
    
    --%></td>
    
    
    </tr>
    <tr class="sportDiv" style="display:none" >
				      <td colspan="3" >
				      The uploaded Form-I,Form-II,Form-III certificates for having participated in the events within the period of 5 years before the date of notification will only be considered under Sports Quota. Other Sports Certificates including Form-I,Form-II,Form-III more than 5  years will be considered only for Special Marks.
				      </td>
				    </tr>
    <tr>
    <td>
    </td>
    </tr>
    </table>
  	</div>
  	
   	 <s:if test="%{exServicemanFlag==false &&  wardQuotaFlag==true}">
   	 <table class="contenttableNew" >
    <s:hidden name="exServicemanFlag" value="%{exServicemanFlag}"></s:hidden>
     
     <s:hidden name="wardQuotaFlag" value="%{wardQuotaFlag}"></s:hidden>
     
    
    <tr id="wardsTr" >
    
	      <td width="80%">Would You Like To Avail Ward Quota? / <span class="tamil"><s:text name="additional.wards"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3" width="20%">
	      
	      	  <s:select list = "additionalDetailsBean.wardsQuaotaList" name = "additionalDetailsBean.wardsQuaota" label = "Name" 
				id = "wardsQuaota" value="%{additionalDetailsBean.wardsQuaota}" onchange="return showWardsDiv();"
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
    
	      <td>Department Type / <span class="tamil"><s:text name="additional.departmenttype"/></span><span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
		  	<s:select list = "additionalDetailsBean.departmentTypeList" name = "additionalDetailsBean.departmentType" label = "Name" headerKey="" headerValue="Select Department Type" 
				id = "departmentType" value="%{additionalDetailsBean.departmentType}" onchange="return loadComboBox();"
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
    
	      <td>Present Rank(Or Last Rank If Retired Or Deceased) / <span class="tamil"><s:text name="additional.presentRank"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      <s:select list="additionalDetailsBean.presentRankList" id="presentRank" name="presentRank" 
		  	 value="%{presentRank}"></s:select>
		  	 
		  	 <s:hidden value="%{additionalDetailsBean.presentRank}" id = "presentRankHidden"></s:hidden>
		  </td>
	  
    </tr>

     <tr class="wardsDiv" id="unitTr" style="display:none;">
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
	       <td  width="60%"  id="otherTR" style="display:none" style="padding-left:20px;"><b>Please Specify Other Unit</b>/ <span class="tamil"><s:text name="applicationForm.otherUnit"/></span> <span class="manadetory-fields">*</span> 
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
	     
	      <tr  id="PostingTrDistrict" class="wardsDiv" style="display:none;">
	     <td>
	Present Posting (Or Last if Retired or Deceased) &#8208; District / City / <span class="tamil"><s:text name="additional.presentPosting"/></span><span class="manadetory-fields">*</span>
	     </td>
	     <td colspan="3">
	        <s:select list="additionalDetailsBean.districtList" name="additionalDetailsBean.presentPosting" id="districtList" headerKey="" headerValue = "Select Present Posting" value="%{additionalDetailsBean.PresentPosting}" onchange="enablePoliceStationField();"></s:select>
	     		  <%--<s:hidden value="%{additionalDetailsBean.PresentPosting}" id = "PresentPostingHidden"></s:hidden>
		 --%></td> 
	     </tr>
	     
	     <tr id="policeTR1" class="wardsDiv" style="display:none;background: #ececec;">
	     <td>
	     Present Posting (Or Last if Retired or Deceased) &#8208; Police Station / <span class="tamil"><s:text name="additional.presentPostingPoliceStation"/> </span> <span class="manadetory-fields">*</span>
	     </td>
	     <td colspan="3">
	      <s:select list="additionalDetailsBean.policeStationList1" name="additionalDetailsBean.policeStation" id="policeStationList" headerKey="" headerValue = "Select Police Station" value="%{additionalDetailsBean.policeStation}"></s:select>
	    <s:hidden value="%{additionalDetailsBean.policeStation}" id = "policeStationHidden"></s:hidden></td> 
	     </tr>
	     
	     
	   	 
    
     <tr class="wardsDiv" style="display:none">
    
	      <td>GPF or CPS Number Of The Parent / <span class="tamil"><s:text name="additional.gpsNumber"/> </span>  <span class="manadetory-fields">*</span></td>
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
	      <td colspan="3">
			<s:textfield id="dateOfwardsCertificate" name="additionalDetailsBean.dateOfwardsCertificate" value="%{additionalDetailsBean.dateOfwardsCertificate}" readonly="true"> </s:textfield>
		  
		  </td>
	  
    </tr>  
    
    <tr class="wardsDiv" style="display:none">
		    <td>Ward Certificate / <span class="tamil"><s:text name="additional.wardcertificate"/></span> <span class="manadetory-fields">*</span></td>
		    <td>
		<div style="display:block; height:auto;">
		<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
		<s:hidden name="additionalDetailsBean.quotaDocumentUploaded3" id="quotaDocumentUploaded3"/>
		<s:hidden name="additionalDetailsBean.quotaDocumentFileName3" />
		<s:hidden name="additionalDetailsBean.candidateDocPk5" />
		  <table class="" width="100%">
		    <tr>
		     
		      <td width="25%" > 
		        <s:file  name="additionalDetailsBean.quotaData3"  id = "eligibilityCriteriaUploadFile5" cssStyle="width:250px;"></s:file>
		        <s:hidden name="additionalDetailsBean.quotaData3Hash" id="quotaData3Hash"/>
		        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
		      </td>
		       <td width="50%" align="center">
		      <div class=""><s:checkbox name="additionalDetailsBean.checkQuotaAcceptance3" id="check5" value="%{additionalDetailsBean.checkQuotaAcceptance3}" /><br/>Candidate Acceptance Checkbox</div>
		    
		      </td>
		      <td><input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" onclick="uploadDocument5();" id="uploadDoc5"/></td>
		    </tr>
		    <tr>
		      
		      <td colspan="2" >
		      <span class="lighttext"><strong><b>Instruction :</b></strong>Ward certificate obtained from the Rank of SP and above issued after the date of Notification alone will be considered.</span>
		      <br>
		      <span class="lighttext"><strong><b>Note :</b> The File Size should be between <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB to <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB. / <span class="tamil"><s:text name="document.instruction1"/></span></strong></span>
			      <br>
			       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files / </span>
			      <br/>
			     <span class="tamil"><s:text name="upload.image.instruction"/></span>
			      

			   </td>
		      
		    </tr><%--
		    <tr>
		    	
		    	<td style="color:#F00;" >
		    		Please upload recent Document 
		    	</td>
		    	 <td></td>
		    </tr>
		    --%></table>
		    <div id='block7'></div>
		</div>
		<div id="uploadedDocuments5" style="display: none;" >
				<table cellspacing="0" cellpadding="0" style="clear:both;" width="100%" >
				
				    <tr>
				    
				      <td  width="75%" id='docId'><br/>
				      <a class="blue" href="CandidateAction_getAdditionalDocument.action?candidateDocId=<s:property value="additionalDetailsBean.candidateDocPk5"/>"><s:property value="additionalDetailsBean.quotaDocumentFileName3" /></a>
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
    
     </s:if>
    
     <table class="contenttableNew" >
  
     <tr>
    
	      <td width="80%">Are You Possesing NCC A/B/C Certificates? / <span class="tamil"><s:text name="additional.ncc"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3" width="20%">
	      
		  <s:select list = "additionalDetailsBean.nccCertificateList" name = "additionalDetailsBean.nccCertificate" label = "Name" 
				id = "nccCertificate" value="%{additionalDetailsBean.nccCertificate}" onchange="return showNccDiv();"
				/>	
		  </td>
	  
    </tr>  
    </table>
    
    
    <div class="nccDiv" style="display:none">
     <table class="contenttableNew" >
    <tr class="nccDiv" style="display:none">
    
	      <td>Certificate (Highest) / <span class="tamil"><s:text name="additional.highestCertificate"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
		  <s:select list = "additionalDetailsBean.nccHighestCertificateList" name = "additionalDetailsBean.nccHighestCertificate" label = "Name" headerKey="" headerValue="Select Status" 
				id = "nccHighestCertificate" value="%{additionalDetailsBean.nccHighestCertificate}" 
				/>	
		  </td>
	  
    </tr>  
    
    
    <tr class="nccDiv" style="display:none">
    
	      <td>Certificate Number / <span class="tamil"><s:text name="additional.certificatenumber"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	     
		   <s:textfield id="nccCertificateNumber" name="additionalDetailsBean.nccCertificateNumber" onkeypress="return alphaNumericwithSplChar(event);"  value="%{additionalDetailsBean.nccCertificateNumber}" onpaste="return false;"  maxlength="30"></s:textfield>
		  </td>
	  
    </tr>  
    
    <tr class="nccDiv" style="display:none">
    
	      <td>NCC Unit  / <span class="tamil"><s:text name="additional.nccUnit"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      
		  <s:textfield id="nccUnit" onpaste="return false;" name="additionalDetailsBean.nccUnit" onkeypress="return alphaNumericwithSplCharForUnitsNss(event);"  value="%{additionalDetailsBean.nccUnit}" maxlength="30"></s:textfield>
		  </td>
	  
    </tr>  
    
    <tr class="nccDiv" style="display:none">
    
	      <td>Date Of Issue / <span class="tamil"><s:text name="applicationForm.dateOfIssue"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
			<s:textfield id="dateOfNccCertificate" name="additionalDetailsBean.dateOfNccCertificate" value="%{additionalDetailsBean.dateOfNccCertificate}" readonly="true"> </s:textfield>      
		  </td>
	  
    </tr>  
    
    
    <tr class="nccDiv" style="display:none">
    
	      <td>Issuing Authority  / <span class="tamil"><s:text name="additional.nssIssue"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	     
		  <s:textfield id="nccCertIssuingAuthority" name="additionalDetailsBean.nccCertIssuingAuthority" onkeypress="return alphabetswithspace(event);" onpaste="return false;" value="%{additionalDetailsBean.nccCertIssuingAuthority}"
		   maxlength="50" ></s:textfield>
		  </td>
	  
    </tr>  
    
    <tr class="nccDiv" style="display:none">
		    <td>Highest Certificate(For NCC) / <span class="tamil"><s:text name="additional.ncccertificate"/></span>   <span class="manadetory-fields">*</span></td>
		    <td>
		<div style="display:block; height:auto;">
		<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
		<s:hidden name="additionalDetailsBean.quotaDocumentUploaded4" id="quotaDocumentUploaded4"/>
		<s:hidden name="additionalDetailsBean.quotaDocumentFileName4" />
		<s:hidden name="additionalDetailsBean.candidateDocPk6" />
		  <table class="" width="100%">
		    <tr>
		     
		      <td width="25%" >
		        <s:file  name="additionalDetailsBean.quotaData4"  id = "eligibilityCriteriaUploadFile6" cssStyle="width:250px;"></s:file>
		        <s:hidden name="additionalDetailsBean.quotaData4Hash" id="quotaData4Hash"/>
		        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
		      </td>
		       <td width="50%" align="center">
		      <div class=""><s:checkbox name="additionalDetailsBean.checkQuotaAcceptance4" id="check6" value="%{additionalDetailsBean.checkQuotaAcceptance4}" /><br/>Candidate Acceptance Checkbox</div>

		      </td>
		      <td>		    <input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" onclick="uploadDocument6();" id="uploadDoc6"/></td>
		    </tr>
		    <tr>
		      
		      <td colspan="2" >
		     <span class="lighttext"><strong><b>Instruction :</b></strong>Highest Certificate of NCC should be uploaded.</span>
		     <br>
		      <span class="lighttext"><strong><b>Note :</b> The File Size should be between <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB to <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB. / <span class="tamil"><s:text name="document.instruction1"/></span></strong></span>
			      <br>
			       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files</span>
			      <br/>
			      <span class="tamil"><s:text name="upload.image.instruction"/></span>
			   </td>
		      
		    </tr>
		    <%--<tr>
		    	
		    	<td style="color:#F00;" >
		    		Please upload recent Document 
		    	</td>
		    	 <td></td>
		    </tr>
		    --%></table>
		    <div id='block7'></div>
		</div>
		<div id="uploadedDocuments6" style="display: none;" >
				<table cellspacing="0" cellpadding="0" style="clear:both;" width="100%" >
				
				    <tr>
				    
				      <td  width="75%" id='docId'><br/>
				      <a class="blue" href="CandidateAction_getAdditionalDocument.action?candidateDocId=<s:property value="additionalDetailsBean.candidateDocPk6"/>"><s:property value="additionalDetailsBean.quotaDocumentFileName4" /></a>
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
    
    <table class="contenttableNew" >
    <tr >
    
	      <td width="80%">Are You Possesing NSS Certificates? / <span class="tamil"><s:text name="additional.nssPosses"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3" width="20%">
	      
	      <s:select list = "additionalDetailsBean.nssCertificateList" name = "additionalDetailsBean.nssCertificate" label = "Name"  
				id = "nssCertificate" value="%{additionalDetailsBean.nssCertificate}" onchange="return showNssDiv();"
				/>
	      
		  </td>
	  
    </tr> 
    </table> 
    
    <div class="nssDiv" style="display:none">
    <table class="contenttableNew" >
    <tr class="nssDiv" style="display:none">
    
	      <td width="80%">Certificate (Highest) / <span class="tamil"><s:text name="additional.highestCertificate"/></span>    <span class="manadetory-fields">*</span></td>
	      <td width="20%" colspan="3">
	    	
	    	<s:select list = "additionalDetailsBean.nssHighestCertificateList" name = "additionalDetailsBean.nssHighestCertificate" label = "Name" headerKey="" headerValue="Select Status" 
				id = "exServiceman1" value="%{additionalDetailsBean.nssHighestCertificate}" 
				/>
	    	  
	     </td>
	  
    </tr> 
    
    <tr class="nssDiv" style="display:none">
    
	      <td>Certificate Number / <span class="tamil"><s:text name="additional.certificatenumber"/></span>  <span class="manadetory-fields"></span></td>
	      <td colspan="3">
	      
	     <s:textfield id="nssCertificateNumber" onpaste="return false;" onkeypress="return alphaNumericwithSplChar(event);"  name="additionalDetailsBean.nssCertificateNumber" value="%{additionalDetailsBean.nssCertificateNumber}"
	      maxlength="30"></s:textfield>
	     </td>
	  
    </tr> 
    
    <tr class="nssDiv" style="display:none">
    
	      <td>NSS Unit / <span class="tamil"><s:text name="addtional.nssUnit"/></span>  <span class="manadetory-fields"></span></td>
	      <td colspan="3">
	      
	      <s:textfield id="nssUnit" onpaste="return false;" onkeypress="return alphaNumericwithSplCharForUnitsNss(event);"  maxlength="30" name="additionalDetailsBean.nssUnit" value="%{additionalDetailsBean.nssUnit}" ></s:textfield>
		  
		  </td>
	  
    </tr> 
    
     <tr class="nssDiv" style="display:none">
    
	      <td>Date Of Issue / <span class="tamil"><s:text name="applicationForm.dateOfIssue"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
		  <s:textfield id="dateOfNssCertificate" name="additionalDetailsBean.dateOfNssCertificate" value="%{additionalDetailsBean.dateOfNssCertificate}"
		   readonly="true"> </s:textfield>
		  </td>
	  
    </tr> 
    
    <tr class="nssDiv" style="display:none">
    
	      <td>Issuing Authority / <span class="tamil"><s:text name="additional.nssIssue"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      	      
		  <s:textfield id="nssCertIssuingAuthority" name="additionalDetailsBean.nssCertIssuingAuthority" value="%{additionalDetailsBean.nssCertIssuingAuthority}" maxlength="50" onkeypress="return alphabetswithspace(event);" onpaste="return false;"></s:textfield>
		  </td>
	  
    </tr> 
    
       
    <tr class="nssDiv" style="display:none">
		    <td>Highest Certificate(For NSS) / <span class="tamil"><s:text name="additional.nsscertificate"/></span> <span class="manadetory-fields">*</span></td>
		    <td>
		<div style="display:block; height:auto;">
		<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
		<s:hidden name="additionalDetailsBean.quotaDocumentUploaded5" id="quotaDocumentUploaded5"/>
		<s:hidden name="additionalDetailsBean.quotaDocumentFileName5" />
		<s:hidden name="additionalDetailsBean.candidateDocPk7" />
		  <table class="" width="100%">
		    <tr>
		     
		      <td width="25%" > 
		        <s:file  name="additionalDetailsBean.quotaData5"  id = "eligibilityCriteriaUploadFile7" cssStyle="width:250px;"></s:file>
		        <s:hidden name="additionalDetailsBean.quotaData5Hash" id="quotaData5Hash"/>
		        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
		      </td>
		       <td width="50%" align="center">
		      <div class=""><s:checkbox name="additionalDetailsBean.checkQuotaAcceptance5" id="check7" value="%{additionalDetailsBean.checkQuotaAcceptance5}" /><br/>Candidate Acceptance Checkbox</div>
		      </td>
	<td>  <input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" onclick="uploadDocument7();" id="uploadDoc7"/></td>
		    </tr>
		    <tr>
		      
		      <td colspan="2" ><span class="lighttext"><strong><b>Note :</b> The File Size should be between <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB to <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB. / <span class="tamil"><s:text name="document.instruction1"/></span></strong></span>
			      <br>
			       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files</span>
			      <br/>
			     <span class="tamil"><s:text name="upload.image.instruction"/></span>
			   </td>
		      
		    </tr>
		    <%--<tr>
		    	
		    	<td style="color:#F00;" >
		    		Please upload recent Document 
		    	</td>
		    	 <td></td>
		    </tr>
		    --%></table>
		    <div id='block7'></div>
		</div>
		<div id="uploadedDocuments7" style="display: none;" >
				<table cellspacing="0" cellpadding="0" style="clear:both;" width="100%" >
				
				    <tr>
				    
				      <td  width="75%" id='docId'><br/>
				      <a class="blue" href="CandidateAction_getAdditionalDocument.action?candidateDocId=<s:property value="additionalDetailsBean.candidateDocPk7"/>"><s:property value="additionalDetailsBean.quotaDocumentFileName5" /></a>
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

    <%-- 
    
    <tr>
    
	      <td>Wheteher Participated Any Previous Recruitment Of This Board? / <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	    
		  <s:select list="categoryList" id="categoryVal" name="categoryVal" headerKey="" headerValue="--Select Category--" onchange="enableDisableTextField();" value="%{categoryVal}" ></s:select>
		  </td>
	  
    </tr> 
    
    <tr>
    
	      <td>Select The Recruitments You Have Participated Previously? / <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      
		  <s:select list="categoryList" id="categoryVal" name="categoryVal" headerKey="" headerValue="--Select Category--" onchange="enableDisableTextField();" value="%{categoryVal}" ></s:select>
		  </td>
	  
    </tr> 
    
    <tr>
    
	      <td>Enrolment Number/Application Or Register Number Of Exam / <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      
		  <s:textfield id="enrolmentNumber" name="enrolmentNumber" maxlength="15"  onpaste="return false;"></s:textfield>
		  </td>
	  
    </tr> 
    
    <tr>
    
	      <td>Centre Chosen For The Exam/Name Of The District / <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      
		  <s:select list="categoryList" id="categoryVal" name="categoryVal" headerKey="" headerValue="--Select Category--" onchange="enableDisableTextField();" value="%{categoryVal}" ></s:select>
		  </td>
	  
    </tr> 
    
    <tr>
    
	      <td>Stage Cleared In The Recruitment / <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      <!--<s:radio list="categoryList" name = "categoryVal" onchange="enableDisableTextField();" label = "Name"  id = "categoryVal" value="%{categoryVal}" cssClass="categoryClass"></s:radio>
	      
		  -->
		  <s:select list="categoryList" id="categoryVal" name="categoryVal" headerKey="" headerValue="--Select Category--" onchange="enableDisableTextField();" value="%{categoryVal}" ></s:select>
		  </td>
	  
    </tr> 
    
        <tr>
    
	      <td>Select Special Quotas Claimed Or Age Relaxation Claimed Under Special Categories / <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      <!--<s:radio list="categoryList" name = "categoryVal" onchange="enableDisableTextField();" label = "Name"  id = "categoryVal" value="%{categoryVal}" cssClass="categoryClass"></s:radio>
	      
		  -->
		  <s:select list="categoryList" id="categoryVal" name="categoryVal" headerKey="" headerValue="--Select Category--" onchange="enableDisableTextField();" value="%{categoryVal}" ></s:select>
		  </td>
	  
    </tr> 
    
    
    --%></table>
    </div>
    





<s:hidden name="exServicemanFlag" value="%{exServicemanFlag}"></s:hidden>	
   	<s:if test="%{exServicemanFlag==false &&  wardQuotaFlag==true}">	
    
  <table class="contenttableNew" border="0">
  	<tr id="exserviceTr">
		      <td  width="80%">Are you an Ex-Servicemen/Ex-Servicewomen or presently serving (going to retire within one year)? / <span class="tamil"><s:text name="additional.exserviceman"/></span><span class="manadetory-fields tabWidth">*</span>
		       </td>
		       <td width="20%"  colspan="3">
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
		      <s:textfield id="dateOfEnlistment" name="additionalDetailsBean.dateOfEnlistment" maxlength="30" value="%{additionalDetailsBean.dateOfEnlistment}" readonly="true" > </s:textfield>
		      
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
    </div>
    </s:if>
    
   <!-- DEPARTMENT QUOTA DETAILS -->
   	 <table class="contenttableNew" >
    <s:hidden name="exServicemanFlag" value="%{exServicemanFlag}"></s:hidden>
     
     <s:hidden name="departmentQuotaFlag" value="%{departmentQuotaFlag}"></s:hidden>
     <s:hidden name="arpdepartmentQuotaFlag" value="%{arpdepartmentQuotaFlag}"></s:hidden>
    <s:hidden name="addDetailRedirectFlag" value="%{addDetailRedirectFlag}"></s:hidden> 
    
    <s:iterator value="wkExperienceDtlsList" status="stat" var="currentObject">
    <s:hidden name="wkExperienceDtlsList[#stat.index].policedept" value="%{policedept}" id="wkExperienceDtlsList_police"></s:hidden>
    </s:iterator>
    
    <s:hidden name="additionalDetailsBean.arpdepartmentQuaota1" value="%{additionalDetailsBean.arpdepartmentQuaota}"></s:hidden>
    <s:hidden name="additionalDetailsBean.departmentQuaota1" value="%{additionalDetailsBean.departmentQuaota}"></s:hidden>
   <!--  <tr id="wardsTr" >-->
    <tr id="deptTr" >
	      <td width="80%">Would You Like To Avail Department Quota? / <span class="tamil"><s:text name="additional.wards"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3" width="20%">
	      
	      	  <s:select list = "additionalDetailsBean.departmentQuaotaList" name = "additionalDetailsBean.departmentQuaota" label = "Name" 
				id = "departmentQuaota" value="%{additionalDetailsBean.departmentQuaota}" onchange="return showDeptQuotaDiv();"
				/>	
		  </td>
	  
    </tr>
    </table>
    <div  class="deptDiv" style="display:none">
    <table class="contenttableNew" >
    <tr class="deptDiv" style="display:none">
    <tr class="deptDiv" style="display:none">
    
    <tr class="deptDiv" style="display:none">
	      <td>Date Of Enlistment / <span class="tamil"><s:text name="additional.dept_dateofenlistment"/></span> <span class="manadetory-fields">*</span> <br/>
	      <span class="lighttext">
	       
	      
	      </span>
	      </td>
	      
	      <td >
		      <s:textfield id="deptdateOfEnlistment" name="additionalDetailsBean.dept_dateOfEnlistment" maxlength="30" value="%{additionalDetailsBean.dept_dateOfEnlistment}" readonly="true" > </s:textfield>
		      
	      </td>
	      <%--<td colspan="2"><span class="lighttext calInstr">Please click on Calendar to select Date of Birth. / <span class="tamil"><s:text name="applicationForm.calenderInstruction"/></span></span></td>
    --%></tr>
	  <tr class="deptDiv" style="display:none">
    
	      <td>Service as on(Date of Notification) / <span class="tamil"><s:text name="additional.dept_serviceAsOn"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
		   <s:textfield id="deptserviceAsOn" readonly="true" onpaste="return false;" name="additionalDetailsBean.dept_serviceAsOn" onkeypress="return alphaNumericwithSplChar(event);"
		  value="%{additionalDetailsBean.dept_serviceAsOn}" maxlength="30"></s:textfield>
		  </td>
	  
    </tr>       
    
    
    <tr class="deptDiv" style="display:none">
    
	      <td>Present Rank / <span class="tamil"><s:text name="additional.presentRank"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      <s:select list="additionalDetailsBean.dept_presentRankList" id="deptpresentRank" name="additionalDetailsBean.dept_presentRank" headerKey="" headerValue = "Select Present Rank"
		  	 value="%{additionalDetailsBean.dept_presentRank}"></s:select>
		  	 
		  	 <s:hidden value="%{additionalDetailsBean.dept_presentRank}" id = "deptpresentRankHidden"></s:hidden>
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
	      <s:select list="additionalDetailsBean.dept_presentPostingList" name="additionalDetailsBean.deptpresentPostingUnit" id="deptunitList" headerKey="" headerValue = "Select Present Posting" value="%{additionalDetailsBean.deptpresentPostingUnit}" onchange="enableDeptOtherUnitField();" ></s:select>
	      <%--
	      
	      <s:hidden value="%{additionalDetailsBean.presentPostingUnit}" id = "unitListHidden"></s:hidden>
	      
	      --%></td>
	       <td  width="60%"  id="deptotherTR" style="display:none" style="padding-left:20px;"><b>Please Specify Other Unit</b>/ <span class="tamil"><s:text name="applicationForm.deptotherUnit"/></span> <span class="manadetory-fields">*</span> 
	     <s:textfield name="additionalDetailsBean.dept_unitsOther" value="%{additionalDetailsBean.dept_unitsOther}" onkeypress="return alphabetswithspace(event);"  maxlength="30" onpaste="return false;" cssStyle="width:100px;"  id="deptunitsOther"></s:textfield>
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
	        <s:select list="additionalDetailsBean.dept_districtList" name="additionalDetailsBean.dept_presentPosting" id="deptdistrictList" headerKey="" headerValue = "Select Present Posting" value="%{additionalDetailsBean.dept_presentPosting}" onchange="enableDeptPoliceStationField();"></s:select>
	     		  <%--<s:hidden value="%{additionalDetailsBean.PresentPosting}" id = "PresentPostingHidden"></s:hidden>
		 --%></td> 
	</tr>
	     
	<tr id="deptpoliceTR1" class="deptDiv" style="display:none;">
	     <td>
	     Present Posting  &#8208; Police Station / <span class="tamil"><s:text name="additional.presentPostingPoliceStation"/> </span> <span class="manadetory-fields">*</span>
	     </td>
	     <td colspan="3">
	      <s:select list="additionalDetailsBean.dept_policeStationList" name="additionalDetailsBean.dept_policeStation" id="deptpoliceStationList" headerKey="" headerValue = "Select Police Station" value="%{additionalDetailsBean.dept_policeStation}"></s:select>
	    <s:hidden value="%{additionalDetailsBean.dept_policeStation}" id = "deptpoliceStationHidden"></s:hidden></td> 
	 </tr>

	  <tr class="deptDiv" style="display:none">
    
	      <td>GPF/CPS/RPC Number / <span class="tamil"><s:text name="additional.gpsNumber"/> </span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      
		  <s:textfield id="deptgpsNumber" name="additionalDetailsBean.dept_gpsNumber" value="%{additionalDetailsBean.dept_gpsNumber}" onkeypress="return alphaNumeric(event);" maxlength="13"  onpaste="return false;"></s:textfield>
		  </td>
	  
    </tr>
    <tr class="deptDiv" style="display:none">
    
	      <td>Certificate Number / <span class="tamil"><s:text name="additional.certificatenumber"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
		   <s:textfield id="deptCertificateNumber" onpaste="return false;" name="additionalDetailsBean.dept_CertificateNumber" onkeypress="return alphaNumericwithSplChar(event);"
		  value="%{additionalDetailsBean.dept_CertificateNumber}" maxlength="30"></s:textfield>
		  </td>
	  
    </tr>  
    
    <tr class="deptDiv" style="display:none">
    
	      <td>Designation Of Issuing Authority / <span class="tamil"><s:text name="additional.IssuingAuthority"/></span> <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
	      
	      
		  <s:textfield id="dept_CertIssuingAuthority" name="additionalDetailsBean.dept_CertIssuingAuthority" onkeypress="return alphabetswithspace(event);" value="%{additionalDetailsBean.dept_CertIssuingAuthority}"
		   maxlength="50"  onpaste="return false;"></s:textfield>
		  </td>
	  
    </tr>
    <tr class="deptDiv" style="display:none">
    
	      <td>NOC Date / <span class="tamil"><s:text name="additional.dept_NocDate"/></span>  <span class="manadetory-fields">*</span></td>
	      <td colspan="3">
			<s:textfield id="deptNocDate" name="additionalDetailsBean.dept_NocDate" value="%{additionalDetailsBean.dept_NocDate}" readonly="true"> </s:textfield>
		  
	</td>
	  
    </tr>   
	<tr class="deptDiv" style="display:none">
		    <td> NOC document upload / <span class="tamil"><s:text name="additional.deptcertificate"/></span> <span class="manadetory-fields">*</span></td>
		    <td>
		<div style="display:block; height:auto;">
		<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
		<s:hidden name="additionalDetailsBean.quotaDocumentUploaded8" id="quotaDocumentUploaded8"/>
		<s:hidden name="additionalDetailsBean.quotaDocumentFileName8" />
		<s:hidden name="additionalDetailsBean.candidateDocPk8" />
		  <table class="" width="100%">
		    <tr>
		     
		      <td width="25%" > 
		        <s:file  name="additionalDetailsBean.quotaData8"  id = "eligibilityCriteriaUploadFile8" cssStyle="width:250px;"></s:file>
		        <s:hidden name="additionalDetailsBean.quotaData8Hash" id="quotaData8Hash"/>
		        <span  id = "test2" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
		      </td>
		       <td width="50%" align="center">
		      <div class=""><s:checkbox name="additionalDetailsBean.checkQuotaAcceptance8" id="check8" value="%{additionalDetailsBean.checkQuotaAcceptance8}" /><br/>Candidate Acceptance Checkbox</div>
		    
		      </td>
		      <td><input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" onclick="uploadDocument8();" id="uploadDoc8"/></td>
		    </tr>
		   <tr>
		      
		      <td colspan="2" >
		     <!-- <span class="lighttext"><strong><b>Instruction :</b></strong>Ward certificate obtained from the Rank of SP and above issued after the date of Notification alone will be considered.</span>-->
		      <br>
		      <span class="lighttext"><strong><b>Note :</b> The File Size should be between <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB to <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB. / <span class="tamil"><s:text name="document.instruction1"/></span></strong></span>
			      <br>
			       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files / </span>
			      <br/>
			     <span class="tamil"><s:text name="upload.image.instruction"/></span>
			      

			   </td>
		      
		    </tr> 
		    <%--
		    <tr>
		    	
		    	<td style="color:#F00;" >
		    		Please upload recent Document 
		    	</td>
		    	 <td></td>
		    </tr>
		    --%></table>
		    <div id='block7'></div>
		</div>
		<div id="uploadedDocuments8" style="display: none;" >
				<table cellspacing="0" cellpadding="0" style="clear:both;" width="100%" >
				
				    <tr>
				    
				      <td  width="75%" id='docId'><br/>
				      <a class="blue" href="CandidateAction_getAdditionalDocument.action?candidateDocId=<s:property value="additionalDetailsBean.candidateDocPk8"/>"><s:property value="additionalDetailsBean.quotaDocumentFileName8" /></a>
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
     <s:if test="%{exServicemanFlag==false &&  wardQuotaFlag==true}">
    <s:if test="%{additionalDetailsBean.widowFlag==true}">
      <table class="contenttableNew" border="0">
    <tr id="widowTr" >
	      <td width="80%">Are You A Destitute Widow ? / <span class="tamil"><s:text name="additional.widow"/></span>  <span class="manadetory-fields">*</span></td>
	    
	       <td width="20%"  colspan="3">
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
      
    <input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" onclick="uploadDocument();" id="uploadDoc"/>
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
    --%>
    </table>
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
    </s:if>
    
<s:if test='%{disciplineType=="14"}'>
	<table class="contenttableNew" border="0">
  	<tr id="extraQualificationTr">
		      <td  width="80%">Additional marks for extra qualification.? / <span class="tamil">Tamil text to be added</span><span class="manadetory-fields tabWidth">*</span>
		       </td>
		       <td width="20%"  colspan="3">
	    			<s:select list = "additionalDetailsBean.extraQualificationList" name = "additionalDetailsBean.extraQualification" label = "Name" 
					id = "extraQualification" value="%{additionalDetailsBean.extraQualification}"  onchange="return showExtraQualificationDiv();" />	
		       </td>
	    </tr>
	</table>
	
	<div class="extraQualificationDiv" style="display:none">
	    <table class="contenttableNew" border="0">
	    <tr class="extraQualificationDiv" style="display:none">
			<td width="42%">
				B.E. degree in Electronics and Communication Engineering - 4 years/ 
				<br><span class="tamil"><s:text name="additional.servicenumber"/></span>
			</td>
			
			<td colspan="3">
				<s:checkbox name="additionalDetailsBean.qualBEExtc" id = "qualBEExtc" value="%{additionalDetailsBean.qualBEExtc}" />
			</td>
	    </tr>
	    
	    <tr class="extraQualificationDiv" style="display:none">
			<td>
				BCA/B.Sc. degree in Computer Science or B.Sc. IT - 3 years/ 
				<br><span class="tamil"><s:text name="additional.servicenumber"/></span>
			</td>
			
			<td colspan="3">
				<s:checkbox name="additionalDetailsBean.qualBscCSBscIT" id = "qualBscCSBscIT" value="%{additionalDetailsBean.qualBscCSBscIT}" />
			</td>
	    </tr>
	    
	    <tr class="extraQualificationDiv" style="display:none">
			<td>
				B.E or B.Tech degree in Computer Science or I.T - 4 years/ 
				<br><span class="tamil"><s:text name="additional.servicenumber"/></span>
			</td>
			
			<td colspan="3">
				<s:checkbox name="additionalDetailsBean.qualBEBTechCSIT" id = "qualBEBTechCSIT" value="%{additionalDetailsBean.qualBEBTechCSIT}" />
			</td>
	    </tr>
	    
	    <tr class="extraQualificationDiv" style="display:none">
			<td>
				Post Graduate Diploma in Computer application - 1 year/ 
				<br><span class="tamil"><s:text name="additional.servicenumber"/></span>
			</td>
			
			<td colspan="3">
				<s:checkbox name="additionalDetailsBean.qualPGCA" id = "qualPGCA" value="%{additionalDetailsBean.qualPGCA}" />
			</td>
	    </tr>
	    
	    <tr class="extraQualificationDiv" style="display:none">
			<td>
				M.E or M.Tech degree in Communication Systems - 2 years/ 
				<br><span class="tamil"><s:text name="additional.servicenumber"/></span>
			</td>
			
			<td colspan="3">
				<s:checkbox name="additionalDetailsBean.qualMEMTechCommSys" id = "qualMEMTechCommSys" value="%{additionalDetailsBean.qualMEMTechCommSys}" />
			</td>
	    </tr>
	    
	    <tr class="extraQualificationDiv" style="display:none">
			<td>
				M.E. or M.Tech. degree in Computer Science or I.T. - 2 years/ 
				<br><span class="tamil"><s:text name="additional.servicenumber"/></span>
			</td>
			
			<td colspan="3">
				<s:checkbox name="additionalDetailsBean.qualMEMTechCSIT" id = "qualMEMTechCSIT" value="%{additionalDetailsBean.qualMEMTechCSIT}" />
			</td>
	    </tr>
	    
	    <tr class="extraQualificationDiv" style="display:none">
			<td>
				MCA - 3 years/ 
				<span class="tamil"><s:text name="additional.servicenumber"/></span>
			</td>
			
			<td colspan="3">
				<s:checkbox name="additionalDetailsBean.qualMCA" id = "qualMCA" value="%{additionalDetailsBean.qualMCA}" />
			</td>
	    </tr>


    <tr class="extraQualificationDiv" style="display:none">
    <td>Upload of documents submitted / <span class="tamil"><s:text name="additional.dischargecertificate"/></span><span class="manadetory-fields">*</span></td>
    <td >
<div style="display:block; height:auto;">
<%--<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>--%>
<s:hidden name="additionalDetailsBean.extraQualificationDocumentUploaded" id="extraQualificationDocumentUploaded"/>
<s:hidden name="additionalDetailsBean.uploadExtraQualificationFileName" />
<s:hidden name="additionalDetailsBean.extraQualificationDocPk" />
  <table class="" width="100%" border="0" >
    <tr>
     
      <td width="25%" style="padding-left: 0px;" > 
        <s:file  name="additionalDetailsBean.uploadExtraQualification"  id = "extraQualificationUploadFile" cssStyle="width:250px;"></s:file>
       <s:hidden name="additionalDetailsBean.uploadExtraQualificationHash" id="uploadExtraQualificationHash"/>
        <span  id = "testExtraQual" style="display:none; color:#F00;" >Only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> file is accepted.</span>
      </td>
       <td width="50%" align="center">
       <s:checkbox name="additionalDetailsBean.checkExtraQualification" id="checkExtraQualification" value="%{additionalDetailsBean.checkExtraQualification}" /> 
    	<br/>Candidate Acceptance Checkbox
      </td>
      <td colspan="2" ><input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload" id="extraQualUploadButton" onclick="uploadExtraQualificationDoc();" /></td>
    </tr>
    <tr>
      
      <td colspan="4" >
      <span class="lighttext"><strong><b>Instruction :</b></strong>If you are selecting more than one additional qualification then make sure that all the selected additional qualification certificates are uploaded under one PDF document.</span>
      <br>
      <span class="lighttext"><strong><b>Note :</b> The File Size should be between <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB to <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_DOCUMENT_SIZE_IN_KB).toLowerCase()%> KB. / <span class="tamil"><s:text name="document.instruction1"/></span></strong></span>
      <br>
       <span class="lighttext">You can upload only <%= ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.DOCUMENT_TYPE).toLowerCase()%> files</span>
      <br/>
     <span class="tamil"><s:text name="upload.image.instruction"/></span>
      
     
      </td>
    </tr>
    </table>
    <div id='block7'></div>
</div>
<div id="uploadedExtraQualDocuments" style="display: none;" >
		<table cellspacing="0" cellpadding="0" style="clear:both;" width="100%" >
		
		    <tr>
		    
		      <td  width="75%" id='docId'><br/>
		      <a class="blue" href="CandidateAction_getAdditionalDocument.action?candidateDocId=<s:property value="additionalDetailsBean.extraQualificationDocPk"/>"><s:property value="additionalDetailsBean.uploadExtraQualificationFileName" /></a>
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
</div>
<br class="otherDetailsFields"/> 
<div style="display:none">
<div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Educational Details">Quota / Special Marks Details</h1></div>
<div class="hr-underline2"></div>
</div>

 





 
<div class="clear"></div>
<table class="contenttableNew otherDetailsFields" border="0" width="100%" >
  
  <tr>
  <td width="34%">&nbsp;</td>
	    <td colspan="3" align="left">
	    	<s:hidden name="disciplineType" value="%{disciplineType}"></s:hidden>
	    	<s:submit id="saveUpdate" value="Save / Update" cssClass="submitBtn button-gradient"  method="saveCandidateQuotaDetails" onclick="viewFlag1();"></s:submit>      
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
				<s:submit method="viewPrintFinalDetails" value="Continue" cssClass="submitBtn button-gradient" disabled="true"></s:submit>
			
		</s:if>
		
		<s:else>
			<s:submit method="viewPrintFinalDetails" onclick="auditEntry();" value="Continue" cssClass="submitBtn button-gradient"></s:submit>
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

