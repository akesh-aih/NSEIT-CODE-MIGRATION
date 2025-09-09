<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript" src="js/moment.min.js"></script>
<link rel="stylesheet" href="css/jquery-ui.css">
<link rel="stylesheet" href="css/bootstrap.min.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/style.css">
<script src="js/jquery-3.6.3.min.js"></script>
<script src="js/jquery-ui.js"></script>

<script type="text/javascript">
var timeOutVal;

var deletedItem1 = null;
var deletedItem2 = null;
var deletedItem3 = null;
var deletedItem4 = null;
/* prefence */
var i = 0;
/* var arrDataAdd = new Array();
var arrDataRemove = new Array();
 */
let
moveDataArray = new Array();
 
/* prefence */

	var arrayPost = new Array(); 
$(document).ready(function() {
	
	 //var testCenter1 = '<s:property value="testCenter1"/>';
	//aler(testCenter1);
	declarationCheckBoxOnload();

	jQuery(document).bind("keyup keydown", function(e){
	    if(e.ctrlKey && e.keyCode == 80){
	       // alert('fine');
	        return false;
	    }
	});
	var $testCenter1 = $("select[name='testCenter1']");
  	var $testCenter2 = $("select[name='testCenter2']");
  	var $testCenter3 = $("select[name='testCenter3']");
  	$testCenter1.change(function() {
  	    var selectedItem = $(this).val();
  	    if(deletedItem1 !== undefined && deletedItem1 !== null){	    	
  	    	$testCenter2.find('option[value="' + deletedItem1 + '"]').prop("disabled", false);
  	    	$testCenter3.find('option[value="' + deletedItem1 + '"]').prop("disabled", false);
  	    }   	 	
  	    if (selectedItem) {
  	       	 deletedItem1 = $(this).val();
  	         $testCenter2.find('option[value="' + selectedItem + '"]').prop("disabled", true);
  	         $testCenter3.find('option[value="' + selectedItem + '"]').prop("disabled", true);
  	       }
  	});
  	
  	$testCenter2.change(function() {
  	    var selectedItem = $(this).val();
  	    if(deletedItem2 !== undefined && deletedItem2 !== null){
  	    	 $testCenter1.find('option[value="' + deletedItem2 + '"]').prop("disabled", false);
  	    	 $testCenter3.find('option[value="' + deletedItem2 + '"]').prop("disabled", false);
  	    }
  	    if (selectedItem) {
  	       deletedItem2 = $(this).val();
  	       $testCenter1.find('option[value="' + selectedItem + '"]').prop("disabled", true);
  	       $testCenter3.find('option[value="' + selectedItem + '"]').prop("disabled", true);
  	    }
  	});
  	
  	$testCenter3.change(function() {
  	    var selectedItem = $(this).val();
  	    if(deletedItem3 !== undefined && deletedItem3 !== null){
  	    	 $testCenter1.find('option[value="' + deletedItem3 + '"]').prop("disabled", false);
  	    	 $testCenter2.find('option[value="' + deletedItem3 + '"]').prop("disabled", false);
  	    }
  	    if (selectedItem) {
  	       deletedItem3 = $(this).val();
  	       $testCenter1.find('option[value="' + selectedItem + '"]').prop("disabled", true);
  	       $testCenter2.find('option[value="' + selectedItem + '"]').prop("disabled", true);
  	    }
  	});
  	

  	var selectedTest1 = $("#testCenter1").val();
      if (selectedTest1) {
      	 $testCenter2.find('option[value="' + selectedTest1 + '"]').prop("disabled", true);
      	 $testCenter3.find('option[value="' + selectedTest1 + '"]').prop("disabled", true);
      	 deletedItem1 = selectedTest1;
      }
      
      var selectedTest2 = $("#testCenter2").val();
      if (selectedTest2) {
      	 $testCenter1.find('option[value="' + selectedTest2 + '"]').prop("disabled", true);
      	 $testCenter3.find('option[value="' + selectedTest2 + '"]').prop("disabled", true);
      	 deletedItem2 = selectedTest2;
      }
      
      var selectedTest3 = $("#testCenter3").val();
      if (selectedTest3) {
      	 $testCenter1.find('option[value="' + selectedTest3 + '"]').prop("disabled", true);
      	 $testCenter2.find('option[value="' + selectedTest3 + '"]').prop("disabled", true);
      	 deletedItem3 = selectedTest3;
      }
	/* prefence */
    
    //rms
    var arrSelectedData = "";
    var arrSelectedCentre = new Array();
    arrSelectedData = "<s:property value='testCentreDBList' />";
    arrSelectedData = arrSelectedData.split(',');
    if(arrSelectedData.length==1)
    	{
    		//alert(arrSelectedData.length);
    		var firstData = arrSelectedData[0].split('[');
    		var lastData = firstData[1].split(']');
    		arrSelectedData[0] = lastData[0].trim();
    		
    		
    	}
    else{
   
	var firstData = arrSelectedData[0].split('[');
					if (arrSelectedData[0] == "[]") {
						firstData[1] = "";
					}

					var lastData = arrSelectedData[arrSelectedData.length - 1]
							.split(']');
					if (arrSelectedData[0] == "[]") {
						lastData[0] = "";
					}
					arrSelectedData[0] = firstData[1].trim();
					arrSelectedData[arrSelectedData.length - 1] = lastData[0]
							.trim();
					for (var arrI = 0; arrI < arrSelectedData.length; arrI++) {
						arrSelectedData[arrI] = arrSelectedData[arrI].trim();
					}
				}

				//console.log(arrSelectedData);
				//console.log(centreList);
				/* var splittedDataArray = null;
				
				//alert(localStorage.getItem("moveDataArray"));
				splittedDataArray = localStorage.getItem("moveDataArray").split(","); */

				moveRowOnload(arrSelectedData);
				$('#table_source')
				saveList();

				//postal
				var arrSelectedData1 = "";
				var arrSelectedCentre = new Array();
				arrSelectedData1 = "<s:property value='testCentreDBList1' />";
				arrSelectedData1 = arrSelectedData1.split(',');
				
				 if(arrSelectedData1.length==1)
			    	{
			    		///alert(arrSelectedData1.length);
			    		var firstData = arrSelectedData1[0].split('[');
			    		var lastData = firstData[1].split(']');
			    		arrSelectedData1[0] = lastData[0].trim();
			    		
			    		
			    	}
			    else{
				var firstData = arrSelectedData1[0].split('[');
				if (arrSelectedData1[0] == "[]") {
					firstData[1] = "";
				}

				var lastData = arrSelectedData1[arrSelectedData1.length - 1]
						.split(']');
				if (arrSelectedData1[0] == "[]") {
					lastData[0] = "";
				}
				arrSelectedData1[0] = firstData[1].trim();
				arrSelectedData1[arrSelectedData1.length - 1] = lastData[0]
						.trim();
				for (var arrI = 0; arrI < arrSelectedData1.length; arrI++) {
					arrSelectedData1[arrI] = arrSelectedData1[arrI].trim();
				}
			    }
				moveRowOnload1(arrSelectedData1);
				$('#table_sourcee')
				saveList1();

				//admisnistrative

				var arrSelectedData2 = "";
				var arrSelectedCentre = new Array();
				arrSelectedData2 = "<s:property value='testCentreDBList2' />";
				arrSelectedData2 = arrSelectedData2.split(',');
				
				 if(arrSelectedData2.length==1)
			    	{
			    		///alert(arrSelectedData1.length);
			    		var firstData = arrSelectedData2[0].split('[');
			    		var lastData = firstData[1].split(']');
			    		arrSelectedData2[0] = lastData[0].trim();
			    		
			    		
			    	}
			    else{
				var firstData = arrSelectedData2[0].split('[');
				if (arrSelectedData2[0] == "[]") {
					firstData[1] = "";
				}

				var lastData = arrSelectedData2[arrSelectedData2.length - 1]
						.split(']');
				if (arrSelectedData2[0] == "[]") {
					lastData[0] = "";
				}
				arrSelectedData2[0] = firstData[1].trim();
				arrSelectedData2[arrSelectedData2.length - 1] = lastData[0]
						.trim();
				for (var arrI = 0; arrI < arrSelectedData2.length; arrI++) {
					arrSelectedData2[arrI] = arrSelectedData2[arrI].trim();
				}
			    }

				moveRowOnload2(arrSelectedData2);
				$('#table_sourceee')
				saveList2();
				DivisionalPreferencesShowHide();
				/*preference*/

				var arrayCurrentPost = new Array();
				//alert("Onload arrayPost :"+arrayPost);
				var di1 = '<s:property value="di1"/>';
				var di2 = '<s:property value="di2"/>';

				$.each($("input[name='disciplinName']:checked"), function() {
					arrayPost.push($(this).val());
				});

			}); //$(document).ready End
	function showRelPopup() {

		$("#overlay").show();
		$("#eligibilitydialog").fadeIn(300);

	}
	function okFunctionEligibility() {
		HideDialogForEligibility();
	}
	function HideDialogForEligibility() {
		$("#overlay").hide();
		$("#eligibilitydialog").fadeOut(300);

		// $('input[name=residentTamilNadu]').attr('checked',false);
	}
	/* prefence for RMS */
	function continueRegPage() {
		
		saveList();
	}

	function moveRowOnload(cVal) {
		
		for (var lstData = 0; lstData < cVal.length; lstData++) {
			let myVal = cVal[lstData];
			//let innerHtmlData = e.innerHTML;

			i = i + 1;
			if (myVal != null && myVal != "" && myVal != undefined) {
				moveDataArray.push(myVal);
				$('#table_source1')
						.append(
								'<tr class="item"><!--<td id="rankID'+i+'">'
										+ i
										+ '</td>--><td class="itemTd" id="preferenceID'+i+'">'
										+ myVal
										+ '</td>'
										+ '<td> <input type="button" class="up_arrow" id="btnUp'
										+ i
										+ '" value="&#8593;" class="up_arrow" onclick = "moveUp(this);"> </td>'
										+ '<td> <input type="button" class="down_arrow" id="btnDown'
										+ i
										+ '" value="&#8595;" class="down_arrow"  onclick = "moveDown(this);"> </td>'
										+ '<td> <input type="button" class="red_cross" id="removeBtn'
										+ i
										+ '" value="&times" class="red_cross"  onclick = "removeRow(this);"> </td>'
										+ '</tr>');

			}
			/* if (innerHtmlData != null && innerHtmlData != undefined && innerHtmlData != "") {
			    moveDataArray.push(innerHtmlData);
			    //alert(arrData);
			    $('#table_source1')
			            .append(
			                    '<tr class="item"><!--<td id="rankID'+i+'">'
			                            + +i
			                            + '</td>-->/<td td class="itemTd" id="preferenceID'+i+'">'
			                            + innerHtmlData
			                            + '</td>'
			                            + '<td> <input type="button" id="btnUp'
			                            + i
			                            + '" value="up" onclick = "moveUp(this);"> </td>'
			                            + '<td> <input type="button" id="btnDown'
			                            + i
			                            + '" value="down" onclick = "moveDown(this);"> </td>'
			                            + '<td> <input type="button" id="removeBtn'
			                            + i
			                            + '" value="remove" onclick = "removeRow(this);"> </td>'
			                            + '</tr>');
			    //$('#table_source').remove('<tr><td>'+tOne+'</td></tr>');
			} */
			localStorage.setItem("moveDataArray", moveDataArray);

			//$(e).closest("tr").remove(); **************
			let tbl = $('#table_source1');
			//alert(tbl[0].rows.length-2);
			let tblLength = tbl[0].rows.length - 2;

			for (j = 1; j <= tblLength; j++) {
				if (j == tblLength) {
					break;
				}
				tbl[0].rows[(j + 1)].cells[0].id = "preferenceID" + (j);
				tbl[0].rows[(j + 1)].cells[1].childNodes[1].id = "btnUp" + (j);
				tbl[0].rows[(j + 1)].cells[2].childNodes[1].id = "btnDown"
						+ (j);
				tbl[0].rows[(j + 1)].cells[3].childNodes[1].id = "removeBtn"
						+ (j);
			}

		}
	}

	function moveRow(e) {
		
		let myVal = $(e).val();
		let innerHtmlData = e.innerHTML;

		i = i + 1;
		if (myVal != null && myVal != "" && myVal != undefined) {
			moveDataArray.push(myVal);
			$('#table_source1')
					.append(
							'<tr class="item"><!--<td id="rankID'+i+'">'
									+ i
									+ '</td>--><td class="itemTd" id="preferenceID'+i+'">'
									+ myVal
									+ '</td>'
									+ '<td> <input type="button" class="up_arrow"  id="btnUp'
									+ i
									+ '" value="&#8593;" onclick = "moveUp(this);"> </td>'
									+ '<td> <input type="button" class="down_arrow"  id="btnDown'
									+ i
									+ '" value="&#8595;" class="down_arrow" onclick = "moveDown(this);"> </td>'
									+ '<td> <input type="button" class="red_cross"  id="removeBtn'
									+ i
									+ '" value="&times" class="red_cross" onclick = "removeRow(this);"> </td>'
									+ '</tr>');

		}
		if (innerHtmlData != null && innerHtmlData != undefined
				&& innerHtmlData != "") {
			moveDataArray.push(innerHtmlData);
			//alert(arrData);
			$('#table_source1')
					.append(
							'<tr class="item"><!--<td id="rankID'+i+'">'
									+ +i
									+ '</td>-->/<td td class="itemTd" id="preferenceID'+i+'">'
									+ innerHtmlData
									+ '</td>'
									+ '<td> <input type="button" class="up_arrow" id="btnUp'
									+ i
									+ '" value="&#8593;" class="up_arrow"  onclick = "moveUp(this);"> </td>'
									+ '<td> <input class="down_arrow"  type="button" id="btnDown'
									+ i
									+ '" value="&#8595;" class="down_arrow"  onclick = "moveDown(this);"> </td>'
									+ '<td> <input type="button" class="red_cross"  id="removeBtn'
									+ i
									+ '" value="&times" class="btn btn-warning red_cross" onclick = "removeRow(this);"> </td>'
									+ '</tr>');
			//$('#table_source').remove('<tr><td>'+tOne+'</td></tr>');
		}
		localStorage.setItem("moveDataArray", moveDataArray);
		$(e).closest("tr").remove();
		let tbl = $('#table_source1');
		//alert(tbl[0].rows.length-2);
		let tblLength = tbl[0].rows.length - 2;
		let j = 0;
		for (j = 1; j <= tblLength; j++) {
			if (j == tblLength) {
				break;
			}
			tbl[0].rows[(j + 1)].cells[0].id = "preferenceID" + (j);
			tbl[0].rows[(j + 1)].cells[1].childNodes[1].id = "btnUp" + (j);
			tbl[0].rows[(j + 1)].cells[2].childNodes[1].id = "btnDown" + (j);
			tbl[0].rows[(j + 1)].cells[3].childNodes[1].id = "removeBtn" + (j);
		}
		//var add = "a";

		/* $.ajax({     
		    url: "CandidateAction_preferencesDetails.action",
		    async: false,
		    data: "arrDataAdd="+arrDataAdd+"&add="+add,
		    type: 'POST',
		    error:function(ajaxrequest)
		    {
		        //window.reload();
		    },
		    success:function(responseText)
		    {
		        responseText = $.trim(responseText);
		    }
		}); */
		saveList();
	}

	function moveUp(e) {
		
		/* var tbl = $('#table_source1');
		//alert(tbl[0].rows.length-2);
		var tblLength = tbl[0].rows.length-2;
		for(var i = 1; i<=tblLength; i++){
		    if(i==tblLength.length){
		        break;
		    }
		    tbl[0].rows[(i+1)].cells[1].id = "btnUp"+(i-1);
		    
		}
		
		//alert($('#table_source1'));
		var rowCount = document.getElementById("table_source1").rows.length;
		for(var i = 0; i < rowCount; i++){
		     
		} */
		//alert(document.getElementById("table_source1").rows.length);
		let eId = e.id;
		let splitData = eId.split("btnUp");
		let count = parseInt(splitData[1]);
		//alert(count);
		let current = "";
		let previous = "";
		current = document.getElementById("preferenceID" + count).innerHTML;
		previous = document.getElementById("preferenceID" + (count - 1)).innerHTML;
		//alert(current);
		//alert(previous);
		document.getElementById("preferenceID" + (count - 1)).innerHTML = current;
		document.getElementById("preferenceID" + (count)).innerHTML = previous;

		let tbl = $('#table_source1');
		//alert(tbl[0].rows.length-2);
		let tblLength = tbl[0].rows.length - 2;
		let k = 0;

		let j = 0;
		for (k = 1; k <= tblLength; k++) {
			if (k == tblLength.length) {
				break;
			}
			tbl[0].rows[(k + 1)].cells[0].id = "preferenceID" + (j);
			tbl[0].rows[(k + 1)].cells[1].childNodes[1].id = "btnUp" + (j);
			tbl[0].rows[(k + 1)].cells[2].childNodes[1].id = "btnDown" + (j);
			tbl[0].rows[(k + 1)].cells[3].childNodes[1].id = "removeBtn" + (j);

			j = j + 1;
		}

		saveList();

	}

	function moveDown(e) {
		

		/* var tbl = $('#table_source1');
		//alert(tbl[0].rows.length-2);
		var tblLength = tbl[0].rows.length-2;
		for(var i = 1; i<=tblLength; i++){
		    if(i==tblLength.length){
		        break;
		    }
		    tbl[0].rows[(i+1)].cells[2].id = "btnDown"+(i-2);
		    
		}
		
		//alert($('#table_source1'));
		var rowCount = document.getElementById("table_source1").rows.length;
		for(var i = 0; i < rowCount; i++){
		     
		} */
		//alert(document.getElementById("table_source1").rows.length);
		let eId = e.id;
		let splitData = eId.split("btnDown");
		let count = parseInt(splitData[1]);
		//alert(count);
		let current = "";
		let previous = "";
		current = document.getElementById("preferenceID" + count).innerHTML;
		previous = document.getElementById("preferenceID" + (count + 1)).innerHTML;
		//alert(current);
		//alert(previous);
		document.getElementById("preferenceID" + (count + 1)).innerHTML = current;
		document.getElementById("preferenceID" + (count)).innerHTML = previous;

		let tbl = $('#table_source1');
		//alert(tbl[0].rows.length-2);
		let tblLength = tbl[0].rows.length - 2;
		let k = 0;

		let j = 0;
		for (k = 1; k <= tblLength; k++) {
			if (k == tblLength.length) {
				break;
			}
			tbl[0].rows[(k + 1)].cells[0].id = "preferenceID" + (j);
			tbl[0].rows[(k + 1)].cells[1].childNodes[1].id = "btnUp" + (j);
			tbl[0].rows[(k + 1)].cells[2].childNodes[1].id = "btnDown" + (j);
			tbl[0].rows[(k + 1)].cells[3].childNodes[1].id = "removeBtn" + (j);
			j = j + 1;
		}

		saveList();
	}

	/* $('tr').click(function(){
	    var cache = $(this).closest('tbody');
	    $(this).remove();
	cache.find('tr').attr('id',function(i){ return (i + 1) });
	   
	}); */

	function removeRow(e) {
		
		//alert(document.getElementById("table_source1").rows.length);
		let eId = e.id;
		let splitData = eId.split("removeBtn");
		let count = splitData[1];
		let current = document.getElementById("preferenceID" + count).innerHTML;
		//arrDataRemove.push(current);

		$('#table_source')
				.append(
						"<tr><td><input type='text' maxlength='5' value='"
								+ current
								+ "' readonly='readonly' id='tstCntrLst' class='form-control' onclick='moveRow(this);'></td></tr>");

		/* var cache = $(this).closest('tbody');
		$(this).remove();
		cache.find('tr').attr('id',function(i){ return (i + 1) }); */
		/* for(var rowLength = count+1 ; rowLength<rowCount ;rowLength++)
		{
		    
		} */
		/* var rmv = "r";
		$.ajax({     
		    url: "CandidateAction_preferencesDetails.action",
		    async: false,
		    data: "arrDataRemove="+arrDataRemove+"&remove="+rmv,
		    type: 'POST',
		    error:function(ajaxrequest)
		    {
		        //window.reload();
		    },
		    success:function(responseText)
		    {
		        responseText = $.trim(responseText);
		    }
		}); */
		$(e).closest("tr").remove();
		i = i - 1;
		let tbl = $('#table_source1');
		//alert(tbl[0].rows.length-2);
		let tblLength = tbl[0].rows.length - 2;
		let k = 0;

		let j = 0;
		for (k = 1; k <= tblLength; k++) {
			if (k == tblLength.length) {
				break;
			}
			tbl[0].rows[(k + 1)].cells[0].id = "preferenceID" + (j);
			tbl[0].rows[(k + 1)].cells[1].childNodes[1].id = "btnUp" + (j);
			tbl[0].rows[(k + 1)].cells[2].childNodes[1].id = "btnDown" + (j);
			tbl[0].rows[(k + 1)].cells[3].childNodes[1].id = "removeBtn" + (j);
			j = j + 1;
		}

		//alert($('#table_source1'));
		/* var rowCount = document.getElementById("table_source1").rows.length;
		for(var i = 0; i < rowCount; i++){
		     
		}
		
		var tbl = $('#table_source1');
		//alert(tbl[0].rows.length-2);
		var tblLength = tbl[0].rows.length-2;
		for(var i = 1; i<=tblLength; i++){
		    if(i==tblLength.length){
		        break;
		    }
		    tbl[0].rows[(i+1)].cells[3].id = "removeBtn"+(i+1);
		    
		}
		
		//alert($('#table_source1'));
		var rowCount = document.getElementById("table_source1").rows.length;
		for(var i = 0; i < rowCount; i++){
		     
		}
		
		var tbl = $('#table_source1');
		//alert(tbl[0].rows.length-2);
		var tblLength = tbl[0].rows.length-2;
		for(var i = 1; i<=tblLength; i++){
		    if(i==tblLength.length){
		        break;
		    }
		    tbl[0].rows[(i+1)].cells[3].id = "btnUp"+(i+1);
		    
		}
		
		//alert($('#table_source1'));
		var rowCount = document.getElementById("table_source1").rows.length;
		for(var i = 0; i < rowCount; i++){
		     
		}
		
		var tbl = $('#table_source1');
		//alert(tbl[0].rows.length-2);
		var tblLength = tbl[0].rows.length-2;
		for(var i = 1; i<=tblLength; i++){
		    if(i==tblLength.length){
		        break;
		    }
		    tbl[0].rows[(i+1)].cells[3].id = "btnDown"+(i-1);
		    
		}
		
		//alert($('#table_source1'));
		var rowCount = document.getElementById("table_source1").rows.length;
		for(var i = 0; i < rowCount; i++){
		     
		}
		//alert(document.getElementById("table_source1").rows.length); */
		saveList();
	}

	function saveList() {
		
		let rowCount = $('#table_source1 tr').length;
		rowCount = rowCount - 2;
		//alert(rowCount);

		let totalTdsInTable = $("#table_source1 td");
		//alert(totalTdsInTable);
		let arr = new Array();
		let l = 0;
		for (l = 0; l < totalTdsInTable.length; l++) {
			//alert(totalTdsInTable[i].textContent);
			let x = totalTdsInTable[l].textContent.trim();
			if (x != null && x != undefined && x != "") {
				//alert(x);
				var res = x.substring(0, 1);
				if (res == "[") {
					var res = x.substring(1, x.length);
					arr.push(res);
				} else {
					arr.push(x);
				}

			}
		}

		if (arr.length < 1) {
			//alert("Please select atleast one preference.");
			$("#preferredCityList").val(arr);
		} else {
			
			//validatePage();
			$("#preferredCityList").val(arr);
			//alert(arr);
		}

		/* $.ajax({
		    url : "CandidateAction_preferencesDetails.action",
		    async : false,
		    data : "arr=" + arr,
		    type : 'POST',
		    error : function(ajaxrequest) {
		        //window.reload();
		    },
		    success : function(responseText) {
		        responseText = $.trim(responseText);
		    }
		}); */
		//alert(arr);
		/* for(var i = 0; i<arr.length;i++){
		    if(arr.empty){
		        
		    }
		    else{
		    console.log(arr[i]);
		    }
		} */

	}
	/* prefence for RMS*/

	/* prefence for Postal start*/

	function moveRowOnload1(cVal) {
		
		for (var lstData = 0; lstData < cVal.length; lstData++) {
			let myVal = cVal[lstData];
			//let innerHtmlData = e.innerHTML;

			i = i + 1;
			if (myVal != null && myVal != "" && myVal != undefined) {
				moveDataArray.push(myVal);
				$('#table_source11')
						.append(
								'<tr class="item"><!--<td id="rankID1'+i+'">'
										+ i
										+ '</td>--><td class="itemTd" id="preferenceID1'+i+'">'
										+ myVal
										+ '</td>'
										+ '<td> <input type="button" class="up_arrow" id="btnUp1'
										+ i
										+ '" value="&#8593;" class="up_arrow" onclick = "moveUp1(this);"> </td>'
										+ '<td> <input type="button" class="down_arrow" id="btnDown1'
										+ i
										+ '" value="&#8595;" class="down_arrow"  onclick = "moveDown1(this);"> </td>'
										+ '<td> <input type="button" class="red_cross" id="removeBtn1'
										+ i
										+ '" value="&times" class="red_cross"  onclick = "removeRow1(this);"> </td>'
										+ '</tr>');

			}

			localStorage.setItem("moveDataArray", moveDataArray);

			//$(e).closest("tr").remove(); **************
			let tbl = $('#table_source11');
			//alert(tbl[0].rows.length-2);
			let tblLength = tbl[0].rows.length - 2;

			for (j = 1; j <= tblLength; j++) {
				if (j == tblLength) {
					break;
				}
				tbl[0].rows[(j + 1)].cells[0].id = "preferenceID1" + (j);
				tbl[0].rows[(j + 1)].cells[1].childNodes[1].id = "btnUp1" + (j);
				tbl[0].rows[(j + 1)].cells[2].childNodes[1].id = "btnDown1"
						+ (j);
				tbl[0].rows[(j + 1)].cells[3].childNodes[1].id = "removeBtn1"
						+ (j);
			}

		}
	}

	function moveRow1(e) {
		
		let myVal = $(e).val();
		let innerHtmlData = e.innerHTML;

		i = i + 1;
		if (myVal != null && myVal != "" && myVal != undefined) {
			moveDataArray.push(myVal);
			$('#table_source11')
					.append(
							'<tr class="item"><!--<td id="rankID1'+i+'">'
									+ i
									+ '</td>--><td class="itemTd" id="preferenceID1'+i+'">'
									+ myVal
									+ '</td>'
									+ '<td> <input type="button" class="up_arrow"  id="btnUp1'
									+ i
									+ '" value="&#8593;" onclick = "moveUp1(this);"> </td>'
									+ '<td> <input type="button" class="down_arrow"  id="btnDown1'
									+ i
									+ '" value="&#8595;" class="down_arrow" onclick = "moveDown1(this);"> </td>'
									+ '<td> <input type="button" class="red_cross"  id="removeBtn1'
									+ i
									+ '" value="&times" class="red_cross" onclick = "removeRow1(this);"> </td>'
									+ '</tr>');

		}
		if (innerHtmlData != null && innerHtmlData != undefined
				&& innerHtmlData != "") {
			moveDataArray.push(innerHtmlData);
			//alert(arrData);
			$('#table_source11')
					.append(
							'<tr class="item"><!--<td id="rankID1'+i+'">'
									+ +i
									+ '</td>-->/<td td class="itemTd1" id="preferenceID1'+i+'">'
									+ innerHtmlData
									+ '</td>'
									+ '<td> <input type="button" class="up_arrow" id="btnUp1'
									+ i
									+ '" value="&#8593;" class="up_arrow"  onclick = "moveUp1(this);"> </td>'
									+ '<td> <input class="down_arrow"  type="button" id="btnDown1'
									+ i
									+ '" value="&#8595;" class="down_arrow"  onclick = "moveDown1(this);"> </td>'
									+ '<td> <input type="button" class="red_cross"  id="removeBtn1'
									+ i
									+ '" value="&times" class="btn btn-warning red_cross" onclick = "removeRow1(this);"> </td>'
									+ '</tr>');
			//$('#table_source').remove('<tr><td>'+tOne+'</td></tr>');
		}
		localStorage.setItem("moveDataArray", moveDataArray);
		$(e).closest("tr").remove();
		let tbl = $('#table_source11');
		//alert(tbl[0].rows.length-2);
		let tblLength = tbl[0].rows.length - 1;//previously it was 2
		let j = 0;
		for (j = 1; j <= tblLength; j++) {
			if (j == tblLength) {
				break;
			}
			tbl[0].rows[(j + 1)].cells[0].id = "preferenceID1" + (j);
			tbl[0].rows[(j + 1)].cells[1].childNodes[1].id = "btnUp1" + (j);
			tbl[0].rows[(j + 1)].cells[2].childNodes[1].id = "btnDown1" + (j);
			tbl[0].rows[(j + 1)].cells[3].childNodes[1].id = "removeBtn1" + (j);
		}

		saveList1();
	}

	function moveUp1(e) {
		

		//alert(document.getElementById("table_source1").rows.length);
		let eId = e.id;
		let splitData = eId.split("btnUp1");
		let count = parseInt(splitData[1]);
		//alert(count);
		let current = "";
		let previous = "";
		current = document.getElementById("preferenceID1" + count).innerHTML;
		previous = document.getElementById("preferenceID1" + (count - 1)).innerHTML;
		//alert(current);
		//alert(previous);
		document.getElementById("preferenceID1" + (count - 1)).innerHTML = current;
		document.getElementById("preferenceID1" + (count)).innerHTML = previous;

		let tbl = $('#table_source11');
		//alert(tbl[0].rows.length-2);
		let tblLength = tbl[0].rows.length - 2;
		let k = 0;

		let j = 0;
		for (k = 1; k <= tblLength; k++) {
			if (k == tblLength.length) {
				break;
			}
			tbl[0].rows[(k + 1)].cells[0].id = "preferenceID1" + (j);
			tbl[0].rows[(k + 1)].cells[1].childNodes[1].id = "btnUp1" + (j);
			tbl[0].rows[(k + 1)].cells[2].childNodes[1].id = "btnDown1" + (j);
			tbl[0].rows[(k + 1)].cells[3].childNodes[1].id = "removeBtn1" + (j);

			j = j + 1;
		}

		saveList1();

	}

	function moveDown1(e) {
		

		let eId = e.id;
		let splitData = eId.split("btnDown1");
		let count = parseInt(splitData[1]);
		//alert(count);
		let current = "";
		let previous = "";
		current = document.getElementById("preferenceID1" + count).innerHTML;
		previous = document.getElementById("preferenceID1" + (count + 1)).innerHTML;
		//alert(current);
		//alert(previous);
		document.getElementById("preferenceID1" + (count + 1)).innerHTML = current;
		document.getElementById("preferenceID1" + (count)).innerHTML = previous;

		let tbl = $('#table_source11');
		//alert(tbl[0].rows.length-2);
		let tblLength = tbl[0].rows.length - 2;
		let k = 0;

		let j = 0;
		for (k = 1; k <= tblLength; k++) {
			if (k == tblLength.length) {
				break;
			}
			tbl[0].rows[(k + 1)].cells[0].id = "preferenceID1" + (j);
			tbl[0].rows[(k + 1)].cells[1].childNodes[1].id = "btnUp1" + (j);
			tbl[0].rows[(k + 1)].cells[2].childNodes[1].id = "btnDown1" + (j);
			tbl[0].rows[(k + 1)].cells[3].childNodes[1].id = "removeBtn1" + (j);
			j = j + 1;
		}

		saveList1();
	}

	function removeRow1(e) {
		
		//alert(document.getElementById("table_source1").rows.length);
		let eId = e.id;
		let splitData = eId.split("removeBtn1");
		let count = splitData[1];
		let current = document.getElementById("preferenceID1" + count).innerHTML;
		//arrDataRemove.push(current);

		$('#table_sourcee')
				.append(
						"<tr><td><input type='text' maxlength='5' value='"
								+ current
								+ "' readonly='readonly' id='tstCntrLst1' class='form-control' onclick='moveRow1(this);'></td></tr>");

		$(e).closest("tr").remove();
		i = i - 1;
		let tbl = $('#table_source11');
		//alert(tbl[0].rows.length-2);
		let tblLength = tbl[0].rows.length - 2;
		let k = 0;

		let j = 0;
		for (k = 1; k <= tblLength; k++) {
			if (k == tblLength.length) {
				break;
			}
			tbl[0].rows[(k + 1)].cells[0].id = "preferenceID1" + (j);
			tbl[0].rows[(k + 1)].cells[1].childNodes[1].id = "btnUp1" + (j);
			tbl[0].rows[(k + 1)].cells[2].childNodes[1].id = "btnDown1" + (j);
			tbl[0].rows[(k + 1)].cells[3].childNodes[1].id = "removeBtn1" + (j);
			j = j + 1;
		}

		saveList1();
	}

	function saveList1() {
		
		let rowCount = $('#table_source11 tr').length;
		rowCount = rowCount - 2;
		//alert(rowCount);

		let totalTdsInTable = $("#table_source11 td");
		//alert(totalTdsInTable);
		let arr = new Array();
		let l = 0;
		for (l = 0; l < totalTdsInTable.length; l++) {
			//alert(totalTdsInTable[i].textContent);
			let x = totalTdsInTable[l].textContent.trim();
			if (x != null && x != undefined && x != "") {
				//alert(x);
				var res = x.substring(0, 1);
				if (res == "[") {
					var res = x.substring(1, x.length);
					arr.push(res);
				} else {
					arr.push(x);
				}

			}
		}

		if (arr.length < 1) {
			//alert("Please select atleast one preference.");
			$("#preferredCityList1").val(arr);
		} else {
			
			//validatePage();
			$("#preferredCityList1").val(arr);
			//alert(arr);
		}

	}

	/* prefence for Postal end*/

	/* prefence for Administrative start*/

	function moveRowOnload2(cVal) {
		
		for (var lstData = 0; lstData < cVal.length; lstData++) {
			let myVal = cVal[lstData];
			//let innerHtmlData = e.innerHTML;

			i = i + 1;
			if (myVal != null && myVal != "" && myVal != undefined) {
				moveDataArray.push(myVal);
				$('#table_source12')
						.append(
								'<tr class="item"><!--<td id="rankID2'+i+'">'
										+ i
										+ '</td>--><td class="itemTd" id="preferenceID2'+i+'">'
										+ myVal
										+ '</td>'
										+ '<td> <input type="button" class="up_arrow" id="btnUp2'
										+ i
										+ '" value="&#8593;" class="up_arrow" onclick = "moveUp2(this);"> </td>'
										+ '<td> <input type="button" class="down_arrow" id="btnDown1'
										+ i
										+ '" value="&#8595;" class="down_arrow"  onclick = "moveDown2(this);"> </td>'
										+ '<td> <input type="button" class="red_cross" id="removeBtn2'
										+ i
										+ '" value="&times" class="red_cross"  onclick = "removeRow2(this);"> </td>'
										+ '</tr>');

			}

			localStorage.setItem("moveDataArray", moveDataArray);

			//$(e).closest("tr").remove(); **************
			let tbl = $('#table_source12');
			//alert(tbl[0].rows.length-2);
			let tblLength = tbl[0].rows.length - 2;

			for (j = 1; j <= tblLength; j++) {
				if (j == tblLength) {
					break;
				}
				tbl[0].rows[(j + 1)].cells[0].id = "preferenceID2" + (j);
				tbl[0].rows[(j + 1)].cells[1].childNodes[1].id = "btnUp2" + (j);
				tbl[0].rows[(j + 1)].cells[2].childNodes[1].id = "btnDown2"
						+ (j);
				tbl[0].rows[(j + 1)].cells[3].childNodes[1].id = "removeBtn2"
						+ (j);
			}

		}
	}

	function moveRow2(e) {
		
		let myVal = $(e).val();
		let innerHtmlData = e.innerHTML;

		i = i + 1;
		//alert(i);
		if (myVal != null && myVal != "" && myVal != undefined) {
			moveDataArray.push(myVal);
			$('#table_source12')
					.append(
							'<tr class="item"><!--<td id="rankID2'+i+'">'
									+ i
									+ '</td>--><td class="itemTd" id="preferenceID2'+i+'">'
									+ myVal
									+ '</td>'
									+ '<td> <input type="button" class="up_arrow"  id="btnUp2'
									+ i
									+ '" value="&#8593;" onclick = "moveUp2(this);"> </td>'
									+ '<td> <input type="button" class="down_arrow"  id="btnDown2'
									+ i
									+ '" value="&#8595;" class="down_arrow" onclick = "moveDown2(this);"> </td>'
									+ '<td> <input type="button" class="red_cross"  id="removeBtn2'
									+ i
									+ '" value="&times" class="red_cross" onclick = "removeRow2(this);"> </td>'
									+ '</tr>');

		}
		if (innerHtmlData != null && innerHtmlData != undefined
				&& innerHtmlData != "") {
			moveDataArray.push(innerHtmlData);
			//alert(arrData);
			$('#table_source12')
					.append(
							'<tr class="item"><!--<td id="rankID2'+i+'">'
									+ +i
									+ '</td>-->/<td td class="itemTd2" id="preferenceID2'+i+'">'
									+ innerHtmlData
									+ '</td>'
									+ '<td> <input type="button" class="up_arrow" id="btnUp2'
									+ i
									+ '" value="&#8593;" class="up_arrow"  onclick = "moveUp2(this);"> </td>'
									+ '<td> <input class="down_arrow"  type="button" id="btnDown1'
									+ i
									+ '" value="&#8595;" class="down_arrow"  onclick = "moveDown2(this);"> </td>'
									+ '<td> <input type="button" class="red_cross"  id="removeBtn1'
									+ i
									+ '" value="&times" class="btn btn-warning red_cross" onclick = "removeRow2(this);"> </td>'
									+ '</tr>');
			//$('#table_source').remove('<tr><td>'+tOne+'</td></tr>');
		}
		localStorage.setItem("moveDataArray", moveDataArray);
		$(e).closest("tr").remove();
		let tbl = $('#table_source12');
		//alert(tbl[0].rows.length-1);
		let tblLength = tbl[0].rows.length - 1;//previously it was 2 below code is assign id to right side values
		let j = 0;
		for (j = 1; j <= tblLength; j++) {
			if (j == tblLength) {
				break;
			}
			tbl[0].rows[(j + 1)].cells[0].id = "preferenceID2" + (j);
			tbl[0].rows[(j + 1)].cells[1].childNodes[1].id = "btnUp2" + (j);
			tbl[0].rows[(j + 1)].cells[2].childNodes[1].id = "btnDown2" + (j);
			tbl[0].rows[(j + 1)].cells[3].childNodes[1].id = "removeBtn2" + (j);
		}

		saveList2();
	}

	function moveUp2(e) {
		

		//alert(document.getElementById("table_source1").rows.length);
		let eId = e.id;
		let splitData = eId.split("btnUp2");
		let count = parseInt(splitData[1]);
		//alert(count);
		let current = "";
		let previous = "";
		current = document.getElementById("preferenceID2" + count).innerHTML;
		previous = document.getElementById("preferenceID2" + (count - 1)).innerHTML;
		//alert(current);
		//alert(previous);
		document.getElementById("preferenceID2" + (count - 1)).innerHTML = current;
		document.getElementById("preferenceID2" + (count)).innerHTML = previous;

		let tbl = $('#table_source12');
		//alert(tbl[0].rows.length-2);
		let tblLength = tbl[0].rows.length - 2;
		let k = 0;

		let j = 0;
		for (k = 1; k <= tblLength; k++) {
			if (k == tblLength.length) {
				break;
			}
			tbl[0].rows[(k + 1)].cells[0].id = "preferenceID2" + (j);
			tbl[0].rows[(k + 1)].cells[1].childNodes[1].id = "btnUp2" + (j);
			tbl[0].rows[(k + 1)].cells[2].childNodes[1].id = "btnDown2" + (j);
			tbl[0].rows[(k + 1)].cells[3].childNodes[1].id = "removeBtn2" + (j);

			j = j + 1;
		}

		saveList2();

	}

	function moveDown2(e) {
		

		let eId = e.id;
		let splitData = eId.split("btnDown2");
		let count = parseInt(splitData[1]);
		//alert(count);
		let current = "";
		let previous = "";
		current = document.getElementById("preferenceID2" + count).innerHTML;
		previous = document.getElementById("preferenceID2" + (count + 1)).innerHTML;
		//alert(current);
		//alert(previous);
		document.getElementById("preferenceID2" + (count + 1)).innerHTML = current;
		document.getElementById("preferenceID2" + (count)).innerHTML = previous;

		let tbl = $('#table_source12');
		//alert(tbl[0].rows.length-2);
		let tblLength = tbl[0].rows.length - 2;
		let k = 0;

		let j = 0;
		for (k = 1; k <= tblLength; k++) {
			if (k == tblLength.length) {
				break;
			}
			tbl[0].rows[(k + 1)].cells[0].id = "preferenceID2" + (j);
			tbl[0].rows[(k + 1)].cells[1].childNodes[1].id = "btnUp2" + (j);
			tbl[0].rows[(k + 1)].cells[2].childNodes[1].id = "btnDown2" + (j);
			tbl[0].rows[(k + 1)].cells[3].childNodes[1].id = "removeBtn2" + (j);
			j = j + 1;
		}

		saveList2();
	}

	function removeRow2(e) {
		
		//alert(document.getElementById("table_source1").rows.length);
		let eId = e.id;
		let splitData = eId.split("removeBtn2");
		let count = splitData[1];
		let current = document.getElementById("preferenceID2" + count).innerHTML;
		//arrDataRemove.push(current);

		$('#table_sourceee')
				.append(
						"<tr><td><input type='text' maxlength='5' value='"
								+ current
								+ "' readonly='readonly' id='tstCntrLst2' class='form-control' onclick='moveRow2(this);'></td></tr>");

		$(e).closest("tr").remove();
		i = i - 1;
		let tbl = $('#table_source12');
		//alert(tbl[0].rows.length-2);
		let tblLength = tbl[0].rows.length - 2;
		let k = 0;

		let j = 0;
		for (k = 1; k <= tblLength; k++) {
			if (k == tblLength.length) {
				break;
			}
			tbl[0].rows[(k + 1)].cells[0].id = "preferenceID2" + (j);
			tbl[0].rows[(k + 1)].cells[1].childNodes[1].id = "btnUp2" + (j);
			tbl[0].rows[(k + 1)].cells[2].childNodes[1].id = "btnDown2" + (j);
			tbl[0].rows[(k + 1)].cells[3].childNodes[1].id = "removeBtn2" + (j);
			j = j + 1;
		}

		saveList2();
	}

	function saveList2() {
		
		let rowCount = $('#table_source12 tr').length;
		rowCount = rowCount - 2;
		//alert(rowCount);

		let totalTdsInTable = $("#table_source12 td");
		//alert(totalTdsInTable);
		let arr = new Array();
		let l = 0;
		for (l = 0; l < totalTdsInTable.length; l++) {
			//alert(totalTdsInTable[i].textContent);
			let x = totalTdsInTable[l].textContent.trim();
			if (x != null && x != undefined && x != "") {
				//alert(x);
				var res = x.substring(0, 1);
				if (res == "[") {
					var res = x.substring(1, x.length);
					arr.push(res);
				} else {
					arr.push(x);
				}

			}
		}

		if (arr.length < 1) {
			//alert("Please select atleast one preference.");
			$("#preferredCityList2").val(arr);
		} else {
			
			//validatePage();
			$("#preferredCityList2").val(arr);
			//alert(arr);
		}

	}

	/* prefence for Administrative end*/
	/*
	showhide for prefencelist functiom start*/

	function DivisionalPreferencesShowHide() {
		
		/* Ass per brs 1.8 gpo 28 and 29 merged so 28 becomes 29 */
		var favorite = [];
		$.each($("input[name='disciplinName']:checked"), function() {
			favorite.push($(this).val());

		});
		if (favorite.length == 0) {
			$("#rmsDiv").hide();
			$("#postalDiv").hide();
			$("#AdministrativeDiv").hide();
		} else if (favorite.length == 1) {
			if (favorite[0] == 28) {
				$("#postalDiv").show();
				$("#rmsDiv").hide();
				$("#AdministrativeDiv").hide();

			} else if (favorite[0] == 29) {
				//$("#rmsDiv").show();
				$("#postalDiv").show();

				$("#AdministrativeDiv").hide();
				/* 
				--added from 28 section */

			

			} else if (favorite[0] == 30) {
				//$("#rmsDiv").show();
				$("#postalDiv").hide();
				$("#AdministrativeDiv").show()
				$("#adminNote").show();

			}

		} else if (favorite.length == 2) {
			if ((favorite[0] == 29 && favorite[1] == 28)
					|| (favorite[0] == 28 && favorite[1] == 29)) {
				$("#rmsDiv").show();
				$("#postalDiv").show();
				$("#AdministrativeDiv").hide();
			} else if ((favorite[0] == 30 && favorite[1] == 28)
					|| (favorite[0] == 28 && favorite[1] == 30)) {
				$("#rmsDiv").show();
				$("#postalDiv").show();
				$("#AdministrativeDiv").show()

			} else if ((favorite[0] == 30 && favorite[1] == 29)
					|| (favorite[0] == 29 && favorite[1] == 30)) {
				//$("#rmsDiv").show();
				$("#postalDiv").show();
				$("#AdministrativeDiv").show();
				$("#adminNote").show();

			}
		} else if (favorite.length == 3) {
			$("#rmsDiv").show();
			$("#postalDiv").show();
			$("#AdministrativeDiv").show()
		}

	}

	/*
	 /* prefence for Administrative end*/
	 
	 
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
	 
	 
	 function showConfirmation(modal){
	$("#overlay").show();
	   $("#confirmationDialog").fadeIn(300);

	   if (modal)
	   {
	      $("#overlay").unbind("click");
	   }
	   else
	   {
	      $("#overlay").click(function (e)
	      {
	         HideDialogForConfirmation();
	      });
	   }
}

function HideDialogForConfirmation(){
	  $("#overlay").hide();
	   $("#confirmationDialog").fadeOut(300);
}

function validatePersonalDetailsForm()
{
	
	var message = "hello";
	
document.preferenceDetailsForm.submit();
				
}
	
</script>
<style type="text/css">
.msgg li:first-child {
	list-style: none;
}

.contenttableNew td {
	padding-top: 15px;
}

.contenttableNew td table td {
	padding-top: 2px;
}

#msgg li {
	float: left;
}

#msgg br {
	height: 1px;
	float: left;
}

.postPrefDisabled {
	pointer-events: none;
	opacity: 0.56;
}
</style>
<div class="row">
	<div class="col-sm-12">
		<s:if test="%{#attr.dataNotFound!=''}">
			<div class="error-massage-text">
				<s:property value="#attr.dataNotFound" />
			</div>
		</s:if>
	</div>
</div>
<div class="container titlebg">
	<h1 class="pageTitle">
		Preferences Selection

		<div class="userid_txt">
			<s:if test="%{#session['SESSION_USER'] != null}">
				<strong>Registration Number <span class="tamil"> <s:text
							name="applicationForm.userId" />
				</span></strong> -
										<s:label value="%{#session['SESSION_USER'].username}" />
			</s:if>
			<%--<s:if test='%{instrReqd}'>
			<div style="float:right; width:400px; text-align:right;">
				<a onclick='ShowPop("pop8");' style="cursor: pointer;" >Click here to read the instructions again</a>
			</div>
		</s:if> --%>
		</div>
	</h1>
</div>
<s:form id="preference"  name ="preferenceDetailsForm" action="CandidateAction_savePreferredCentre" autocomplete="off">
	<div>
		<div class="unwrapForm">
			<s:hidden name="handicappedValue" id="handicappedValue" />
			<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
			<s:hidden name="isDataFound"
				value='<s:property value="candidateDataFilled"/>' />
			<s:hidden name="degreeVal" id="degreeVal" />
			<s:hidden name="cateoryValue" id="cateoryValue" />
			<s:hidden name="candidateStatusId" id="candidateStatusId"
				value="%{statusID}"></s:hidden>
			<s:hidden name="candidateSpecialOption" id="candidateSpecialOption"></s:hidden>
			<s:if test='%{statusID >=5}'>
				<s:hidden name="genderVal" id="genderVal" value="%{genderVal}"></s:hidden>
				<s:hidden name="religionBelief" id="religionBelief1"
					value="%{religionBelief}"></s:hidden>
				<s:hidden name="IDproof" id="IDproof" value="%{IDproof}"></s:hidden>
				<s:hidden name="categoryVal" id="categoryVal1"
					value="%{categoryVal}"></s:hidden>
				<s:hidden name="Subcaste" id="SubcategoryList1" value="%{Subcaste}"></s:hidden>
				<s:hidden name="DesignationIssuingAuthority"
					id="DesignationscategoryList1"
					value="%{DesignationIssuingAuthority}"></s:hidden>
			</s:if>
			<div id="dashboard" class="padding_leftright">
				<s:actionerror />
				<s:hidden id='hddAddressChkBox'></s:hidden>
				<%-- barnali <div id="error-massageAppForm" style="display:none;color:red;" class="error-massage">
      	<ul style="margin-left:-41px;" id="error-ulAppForm">
      	</ul>
		<s:actionmessage escape="false" cssClass="msgg" id="msgg" />
		<div id="error-massage3" style="display:none" class="error-massage"><br/>
    <div class="error-massage-text" style="margin:0;   padding:0;">
    <ul style="margin:0; margin-left:20px; padding:0;" id="error-ul3">
    </ul>       
  </div>
    </div>
</div> --%>
				<s:if test='%{candidateDetailsSuccFlag=="true"}'>
					<div class="container"
						style="border: #999 1px solid; padding: 3px; color: green;"
						id="successMessage">
						<s:property value="candidateDetailsSuccMsg" />
					</div>
				</s:if>
				<!--  <div style="text-align:left; clear:both;"><h1 class="pageTitle" title="Course">Exam</h1></div>
<div class="hr-underline2"></div>
	<table class="contenttable">
		<tr>
					    <td width="170"> Select Exam <span class="manadetory-fields">*</span></td>
					    <td colspan="2">
					    	<s:select list = "discliplineList" name = "disciplineType" label = "Name" 
							id = "disciplineType" value="%{disciplineType}"/>
						</td>
		 </tr>
	</table><br/>
-->
				<!-- Box Container Start -->
				<div class="container common_dashboard tabDiv effect2">
					<div id="error-massageAppForm" style="display: none; color: red;"
						class="error-massage">
						<!-- <ul style="margin-left:-41px;" id="error-ulAppForm">
      	</ul> -->
					</div>
					<s:actionmessage escape="false" cssClass="msgg" />
						<div class="accordions">
						
						<div id="PreferenceDiv">
							<h3 title="Preferred Test City">Preferred Test City</h3>
							<div class="accordion">
							<div class="row">
									<div class="col-sm-12">
										<p class="orgNote">
											<strong> <s:text name="applicationForm.note" /> :
											</strong><span class="manadetory-fields">*</span>
											<s:text name="applicationForm.note1" /> 
										<span class="tamil"> <strong> <s:text
														name="applicationFormT.note" />
											</strong> <span class="manadetory-fields"></span>
											</span> 
										</p>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Preferred Test City - 1 /<span class="tamil">
													<s:text name="applicationForm.testcenter1" />
											</span><span
												class="manadetory-fields">*</span></label>
											<s:select list="testCenterMasterDetails" name="testCenter1"
												headerKey="" cssClass="form-control"
												headerValue="Select Preferred Test City 1" id="testCenter1"
												value="%{testCenter1}" />
										</div>
									</div>
									<div class="col-sm-4">
										<div class="form-group">
											<label class="control-label">Preferred Test City - 2 /<span class="tamil">
													<s:text name="applicationForm.testcenter2" />
											</span><span
												class="manadetory-fields">*</span></label>
											<s:select list="testCenterMasterDetails"
												cssClass="form-control" name="testCenter2" headerKey=""
												headerValue="Select Preferred Test City 2" id="testCenter2"
												value="%{testCenter2}" />
										</div>
									</div>
									<div class="col-sm-4">
										<%-- <div class="form-group">
											<label class="control-label">Preferred Test City - 3<span class="manadetory-fields">*</span></label>
											<s:select list="testCenterMasterDetails"
												cssClass="form-control" name="testCenter3" headerKey=""
												headerValue="Select Preferred Test City 3"
												id="testCenter3" value="%{testCenter3}" />
										</div> --%>
									</div>
									<div class="col-md-12 mt10">
										<p class="orgNote">
											<strong>Note : </strong>Applicants are clearly informed that
											the allotment of Examination Centre/City is the prerogative
											of the Department and any request received for change in
											examination centre/venue will not be permitted under any
											circumstances. Examination Centre will be allotted as per the
											preferences given by the applicant. However depending upon
											volume of the candidates, the candidates will be allotted to
											other locations also./<span class="tamil">
													<s:text name="applicationForm.preferrednote" />
											</span><br></p>
											<p class="orgNote">
											The Department reserves the
											right to cancel any Centre and ask the candidates of that
											centre to appear from another centre. Department also
											reserves the right to divert candidates of any centre to some
											other Centre to take the examination./<span class="tamil">
													<s:text name="applicationForm.preferrednote2" />
											</span><span
												class="manadetory-fields"></span></span>
										</p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="accordions">
						<div id="PersonalDiv">
							<h3 title="Divisional Preferences">Divisional Preferences</h3>
							<div class="accordion">
								
								<div class="row mb10" style="display:none">
									<div class="col-sm-4">
										<label class="control-label"> <s:text name = "register.disciplinName"/> <span class="manadetory-fields">*</span></label>
									</div>
								</div>

								<div class="row" style="display:none">
									<div class="col-sm-12 col-xs-12 checkboxBtnVertical radioBtn" >
										<s:checkboxlist list="discliplineList" name="disciplinName"
											label="Name" id="disciplinName"
											value="%{discliplineSelectedList}"
											onclick="ageMatrix();hideShowWidowField();clearWidowField();resetDateAgeAsOn();mainpre(this.id);DivisionalPreferencesShowHide()" disabled="true"/>
									</div>
								</div>
								<!-- <div style="float:right; width:400px; text-align:right;">
									<a onclick='showRelPopup();' style="cursor: pointer;" >Instruction to select Preferences</a>
			                   </div> -->
								<!-- style="display:none" -->
								
								<!--     prefence code start -->

								<s:hidden name="preferredCityList" id="preferredCityList"
									value=""></s:hidden>
								<!-- //for rms -->
								<s:hidden name="preferredCityList1" id="preferredCityList1"
									value=""></s:hidden>
								<!-- //for postal -->
								<s:hidden name="preferredCityList2" id="preferredCityList2"
									value=""></s:hidden>

								<!--     prefence code start -->

								<div class="row">
									<div class="col-md-12">
										<h4>Instructions to select Preferences/ <span class="tamil"><s:text
								name="preferenceInsTop" /></span></h4>
										<ul class="instructions">	
											<li>Options available for selection are shown in &#34;List of Divisional Preferences&#34;./ <span class="tamil"><s:text
								name="preferenceIns1" /></span></li>
											<li>Select the option as per your order of preference by clicking &#34;Select&#34; against the respective preference in &#34;List of Divisional Preferences&#34;./ <span class="tamil"><s:text
								name="preferenceIns2" /></span></li>
											<li>When you select the option, it will be displayed under Selected Divisional Preferences./ <span class="tamil"><s:text
								name="preferenceIns3" /></span></li>
											<li>Candidates are advised to select as many Preferences as possible./ <span class="tamil"><s:text
								name="preferenceIns4" /></span></li>
											<li>Candidates can change the order of preferences after selection by clicking on Up and Down buttons to move the preferences respectively./ <span class="tamil"><s:text
								name="preferenceIns5" /></span></li>
											<li>The candidate should exercise preference for all Recruiting Units for consideration. 
												In case, preference is exercise for a limited number of Recruiting Units and could not be allocated to one of those units as per merit 
												vis a vis vacancies in the category, he/she belongs to, he/she will not be considered for other Recruiting Units not preferred even though, 
												candidate lower in a rank in merit to him/her is considered who has opted for that Unit./ <span class="tamil"><s:text
								name="preferenceIns6" /></span></li>
										</ul>
									</div>	
								</div>
							
							<br>
								<!-- //for adminis -->
								<div class="col-md-5 pref_Left_table">
									<h4>List of Divisional Preferences</h4>
								</div>
								<div class="col-md-7 pref_Left_table">
									<h4>Selected Preferences</h4>
								</div>

								<!-- RMS Divisional Preference table start     -->
								<div class="row" id="rmsDiv" style="display:none">
									<!-- Draw a table for test centers -->
									<div class="col-md-5 pref_Left_table">

										<table id="table_source" width="100%" border="0"
											cellpadding="0" cellspacing="0"
											class="personsl-dtl table table-bordered preferenc_tbl" >
											<tr>
												<th>RMS Divisional Preference /<span class="tamil">
														<s:text name="applicationForm.rmspref" />
												</span></th>

											</tr>
											<s:iterator value="testCenterList" status="stat"
												var="currentObject">

												<tr>
													<td><s:textfield name="testCenterList[%{#stat.index}]"
															cssClass="form-control" id="tstCntrLst" maxlength="5"
															onclick="moveRow(this);" readonly="true"></s:textfield></td>
												</tr>

											</s:iterator>
										</table>
									</div>
									<div class="col-md-7 pref_Left_table">

										<table id="table_source1" width="100%" border="0"
											cellpadding="0" cellspacing="0"
											class="personsl-dtl table table-bordered preferenc_tbl">
											<tr>
												<th>RMS Divisional Preference /<span class="tamil">
														<s:text name="applicationForm.rmspref" />
												</span></th>
												<th>Up</th>
												<th>Down</th>
												<th>Remove</th>
											</tr>

											<tr>
											</tr>
										</table>
									</div>


								</div>

								<!-- RMS Divisional Preference table end     -->

								<!-- Postal Divisional Preference table start     -->


								<div class="row" id="postalDiv">
									<!-- Draw a table for test centers -->
									<div class="col-md-5 pref_Left_table">

										<table id="table_sourcee" width="100%" border="0"
											cellpadding="0" cellspacing="0"
											class="personsl-dtl table table-bordered preferenc_tbl">
											<tr>
												<th>Postman/Mail Guard Divisional Preference /<span class="tamil"> <s:text
							name="applicationForm.postalpref" />
				</span></th>

											</tr>
											<s:iterator value="testCenterList1" status="stat"
												var="currentObject">

												<tr>
													<td><s:textfield
															name="testCenterList1[%{#stat.index}]"
															cssClass="form-control" id="tstCntrLst1" maxlength="5"
															onclick="moveRow1(this);" readonly="true"></s:textfield></td>
												</tr>

											</s:iterator>
										</table>
									</div>
									<div class="col-md-7 pref_Left_table">

										<table id="table_source11" width="100%" border="0"
											cellpadding="0" cellspacing="0"
											class="personsl-dtl table table-bordered preferenc_tbl">
											<tr>
												<th>Postman/Mail Guard Divisional Preference /<span class="tamil"> <s:text
							name="applicationForm.postalpref" />
				</span></th>
												<th>Up</th>
												<th>Down</th>
												<th>Remove</th>
											</tr>

											<tr>
											</tr>
										</table>
									</div>


								</div>


								<!-- Postal Divisional Preference table end     -->


								<!--Administrative Divisional Preference table start     -->


								<div class="row" id="AdministrativeDiv">
									<!-- Draw a table for test centers -->
									<div class="col-md-5 pref_Left_table">

										<h4 class="visible-xs mt30">List of Divisional Preferences</h4>

										<table id="table_sourceee" width="100%" border="0"
											cellpadding="0" cellspacing="0"
											class="personsl-dtl table table-bordered preferenc_tbl">
											<tr>
												<th>MTS Divisional Preference /<span class="tamil"> <s:text
							name="applicationForm.adminpref" />
				</span></th>

											</tr>
											<s:iterator value="testCenterList2" status="stat"
												var="currentObject">

												<tr>
													<td><s:textfield
															name="testCenterList2[%{#stat.index}]"
															cssClass="form-control" id="tstCntrLst2" maxlength="5"
															onclick="moveRow2(this);" readonly="true"></s:textfield></td>
												</tr>

											</s:iterator>
										</table>
									</div>
									<div class="col-md-7 pref_Left_table">

										<h4 class="visible-xs">Selected Preferences</h4>

										<table id="table_source12" width="100%" border="0"
											cellpadding="0" cellspacing="0"
											class="personsl-dtl table table-bordered preferenc_tbl">
											<tr>
												<th>MTS Divisional Preference /<span class="tamil"> <s:text
							name="applicationForm.adminpref" />
				</span></th>
												<th>Up</th>
												<th>Down</th>
												<th>Remove</th>
											</tr>

											<tr>
											</tr>
										</table>
									</div>

								</div>
<p class="orgNote" id="adminNote" style="display:none"><b>Note - Location of Postal Store Depot Nashik and Mumbai is likely to be change due to proposed merger. /<span class="tamil"><s:text
								name="preferenceNote1" /></span></b></p>
<p class="orgNote" id="adminNote1" ><b>Note - Candidate has to select at least one divisional preference. Candidate can exercise as many preferences as many he can however, 
his/her application will not be considered for the Divisions/Offices for which preferences are not submitted. Further, preferences 
once exercised will be treated as final and no correspondence will be entertained to change the same. /<span class="tamil"><s:text
								name="preferenceNote2" /></span></b></p>
								<div class="mt20 font14">
									<s:checkbox  id="declarationCkeck" onClick="declarationCheckBoxOnload();" value="%{declarationCkeck}" name="declarationCkeck"></s:checkbox> &nbsp;  
									
									I have exercised maximum preferences as per my choice and I understand that my application will 
									not be considered for Divisions/Units,  for which preference have not been submitted by me. / <span class="tamil"><s:text
								name="preferenceDeclaraton" /></span>
								</div>


								<!--    Administrative Divisional Preference table end     -->





								<!--     prefence code end -->


							</div>
						</div>
					</div>
					<div class="clear mt10"></div>
					<div class="accordions">
						
					</div>

					<div class="clear mt10"></div>
					

					<div class="clear mt10"></div>
						<div class="clear mt10"></div>
				
				</div>
			</div>
			<!-- start of popup block -->

			<div id="overlay1" class="web_dialog_overlay_declr"></div>
		
			<!-- end of popup block -->
			<div class="countinuebg row mt20">
				<div class="container">
					<div class="row">
						<div
							class="col-sm-3 col-sm-offset-9 col-xs-6 col-xs-offset-3 text-right padding0">
							<div id="applSaveCont" align="right">
								<%-- <s:submit value="Save & Continue"
									cssClass="submitBtn btn btn-warning"
									method="savePreferredCentre"></s:submit> --%>
										<input type="button" id="enableDisable" value="Save and Continue"
												class="ripple1 btn btn-sm btn-xs btn-warning"
												title="Submit" onclick="showConfirmation();"/>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="clear"></div>
		</div>
	
	</div>
	<s:token />
	<div id='block7'></div>
	</div>
</s:form>
<div class="change-pass box-gradient" id="pop8">
	<div>
		<a href="javascript:void(0);" onclick="PopHideAll();"><img
			src="images/Close.png" align="right" border="0" /></a>
	</div>
	<br />
	<div class="titleBox">
		<h1 class="pageTitle">
			&nbsp;
			<s:label value="Registration Instructions" />
		</h1>
	</div>
	<div class="hr-underline clear"></div>
	<div style="display: block; min-height: 300px; height: auto;">
		<ol>
			<li>Options available for selection are shown in "List of Divisional Preference".<br /> <br />
			</li>
			<li>Select the option as per your order of preference by clicking "Select" against the respective preference in "List of Divisional Preferences".<br /> <br />
			</li>
			<li>When you select the option, it will be displayed under Selected Divisional Preferences.<br /> <br />
			</li>
			<li>Candidates are advised to select as many Preferences as possible.<br /> <br />
			</li>
			<li>Candidates can change the order of preferences after selection by clicking on Up and Down buttons to move the preferences respectively.<br /> <br />
			</li>
		</ol>
	</div>
</div>
<div id="overlay" class="web_dialog_overlay_declr"></div>


<div class="fullscreen-container" id="confirmationDialog">
		<div class="modal-dialog modal-md">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" onClick="HideDialogForConfirmation();">&times;</button>
					<h2 class="modal-title">Confirmation</h2>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-lg-12">
							<div class="msgDiv">
								<ul>
									<li><input type="checkbox" name="declaration1"
										id="declaration1" style="display: none;" />Are you sure that you have filled all your preferences (Division/Unit) with due care? Any change in order of preferences or addition of Division/Unit not preferred or deletion of preferred Division/Unit will NOT be permitted after submission of application. /<span class="tamil"><s:text
								name="popup1" /></span> 
									</li>
								</ul>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<div class="row">
						<div class="col-sm-3 col-sm-offset-3">
							<input type="button" name="accept" id="accept" value="Ok"
								class="ripple1 btn btn-warning btn-block mt10"
								onClick="return validatePersonalDetailsForm();" />
						</div>
						<div class="col-sm-3">
							<input type="button" name="cancel" id="cancel" value="Cancel"
								class="ripple1 btn btn-warning btn-block mt10"
								onClick="HideDialogForConfirmation();" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- <div class="fullscreen-container" id="eligibilitydialog">
	<div class="modal-dialog modal-md">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close"
					onClick="okFunctionEligibility();">&#215;</button>
				<h2 class="modal-title">Instruction to select Preferences</h2>
			</div>
			<div class="modal-body">
				<div class="row">
					<div class="col-lg-12">
						<div class="msgDiv">
							<ul style="font-size:16px">
								<li><input type="checkbox" name="declaration"
									id="declaration" style="display: none;" />Options available for selection are shown in "List of Divisional Preference".<br><br>
								</li>
								<li><input type="checkbox" name="declaration"
									id="declaration" style="display: none;" />Select the option as per your order of preference by clicking "Select" against the respective preference in "List of Divisional Preferences".<br><br>
								</li>
								<li><input type="checkbox" name="declaration"
									id="declaration" style="display: none;" />When you select the option, it will be displayed under Selected Divisional Preferences.<br><br>
								</li>
								<li><input type="checkbox" name="declaration"
									id="declaration" style="display: none;" />Candidates are advised to select as many Preferences as possible.<br><br>
								</li>
								<li><input type="checkbox" name="declaration"
									id="declaration" style="display: none;" />Candidates can change the order of preferences after selection by clicking on Up and Down buttons to move the preferences respectively.<br><br>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="modal-footer">
				<div class="row">
					<div class="col-sm-6 col-sm-offset-3">
						<input type="button" name="accept" id="accept" value="Ok"
							class="ripple1 btn btn-warning btn-block"
							onClick="okFunctionEligibility();" />
					</div>
				</div>
			</div>
		</div>
	</div>
</div>		
 -->