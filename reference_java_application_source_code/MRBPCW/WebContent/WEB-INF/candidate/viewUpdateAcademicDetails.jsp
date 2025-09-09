<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>
<script type="text/javascript">
	
	
	
	
	function fetchSelectedValues()
	{
		var selectedYear = "";
		var qualification = "";
		var percentage = "";
		var board = "";
		var otherBoard = "";
		
		//alert('1');
		var prefixQualification = "qualificationListId=";
		var prefixYear = "&selectYear=";
		var prefixpercentage = "&markParcentage=";
		var prefixBoard = "&boardUniversityListId=";
		var prefixOtherBoard = "&otherBoard=";
		var isFirstRow = true;
		
		$("#tblAcademicDetails tr").each(function(iCurrTRIndex, currTR) {
			//alert('2');
			var rowId = $(currTR).attr("id");
			//rowId = rowId + ".inputField";
			//alert(rowId);
			$(currTR).find(".inputFieldClass").each(function(currIndex, field) {
			//alert('3');
				if($(field).hasClass('yearClass')==true)
				{
					selectedYear = selectedYear + prefixYear + $(field).val();
				}
				else if($(field).hasClass('qualificationClass')==true)
				{
					qualification = qualification + prefixQualification + $(field).val();
				}
				else if($(field).hasClass('percentageClass')==true)
				{
					percentage = percentage + prefixpercentage + $(field).val();
				}
				else if($(field).hasClass('boardUniversityClass')==true)
				{
					board = board + prefixBoard + $(field).val() ;
				}
				else if($(field).hasClass('boardUniversityOtherClass')==true)
				{
					if($(field).val()=="#$#")
					{
						value = "1234abcd6789wxyz";
					}
					else
					{
						value = $(field).val();
					}
					otherBoard = otherBoard + prefixOtherBoard + value;
				}
				
				if(isFirstRow == true){
					prefixQualification = "&"+prefixQualification
					isFirstRow = false;
				}
			});
		});
		
		$("#hQualification").val(qualification);
		$("#hYear").val(selectedYear);
		$("#hPercentage").val(percentage);
		$("#hBoard").val(board);
		$("#hOtherBoard").val(otherBoard);
	
	}
	
	function validateDuplicates()
	{
		var duplicateExists = false;
		
		$("#tblAcademicDetails").find(".qualificationClass").each(function(currIndex, curr) {
			$("#tblAcademicDetails").find(".qualificationClass").each(function(currInnerIndex, innerCurr) {
				if(currIndex != currInnerIndex)
				{
					if($(curr).val() == $(innerCurr).val())
					{
						duplicateExists = true;
					}
				}
			});
		});
		
		return duplicateExists;
	}
	var totalRows=0;
	var currTotalRows=0;
	
	$(document).ready(function(){
	
		$("#academicDetails").validationEngine({promptPosition : "bottomLeft", scroll: false});
		totalRows = $("#htotalRows").val() * 1;
		currTotalRows = $("#htotalRows").val() * 1;
		
		$("#btnEditAcademicDetails").click(function() {
			updateAcademicDetails();
		});
		
		$("#btnAcademicDetailsNext").click(function() {
			updateAcademicDetails();
		});
		
		$("#btnAcademicDetailsPrev").click(function() {

			var index = ($("#hAcademicDetailsDivInd").val()*1);
			index = index - 1;
			btnBack_Click(index);
			$("#academicDetails").validationEngine('hide');
			
		});
		
		$(".addDetailsClass").click(function() {
			if(totalRows == 0 && currTotalRows == 0)
			{
				totalRows = totalRows + 1;
				currTotalRows= currTotalRows+1;
			}	
			addDetails();
		});
		
		
		bindDeleteEvents();
		bindBoardUniversityTextEvents();
		
	});
	
	function updateAcademicDetails()
	{
		if($("#academicDetails").validationEngine('validate')==false)
				return;
			
			
			if(validateDuplicates()==true)
			{
				alert('One or more Qualified Exam has same value');
				return;
			}
			
			fetchSelectedValues();
			
			var qualificationListValue   = $("#hQualification").val();
			//alert ("0  "+qualificationListValue);
			
			var yearIdValue  = $("#hYear").val();
			//alert("1   "+yearIdValue);
			
			var percentageValue  = $("#hPercentage").val();
			//alert("2  "+percentageValue);
			
			var boardUniversityListValue  = $("#hBoard").val();
			//alert ("3  "+boardUniversityListValue)
			
			var otherBoardValue = $("#hOtherBoard").val();
			
			var	dataString = qualificationListValue + yearIdValue + percentageValue+ boardUniversityListValue + otherBoardValue;
			
			var index = ($("#hAcademicDetailsDivInd").val()*1);
			//alert(dataString);
			$.ajax({
				url: "EnrollmentAction_updateAcademicDetails.action",
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
					message = responseText.substring(2, responseText.length);
					textMessae = responseText.split(',');
					if(textMessae[0]=='9'){
						alert(message);
						return false;
					}else{
						if(responseText.length > 0)
						{
							if(responseText.charAt(0) == "0")
							{
								if($("#actionNameAcademic").val() == "preview" || $("#actionNameAcademic").val() == "view")
								{
									submitPersonalDetailsForPreview();
								}
								else
								{
									index = index + 1;
									changeTabsTestEnrollments(index);
								}
							}
						}
					}
				}
			});
	}
	
	function addDetails (){
		
		var copy = $("#toggleText").clone();
		totalRows = totalRows + 1;
		//alert ("totalRows     "+totalRows);
		currTotalRows = currTotalRows + 1;
		//alert ("currTotalRows    "+currTotalRows );
		$(copy).attr("id", "toggleText"+totalRows);
		
		$(copy).find(".qualificationClass").each(function(currIndex, currObj) {
			$(currObj).addClass("qualificationClassActive");
		});
		$(copy).find(".percentageClass").each(function(currIndex, currObj) {
			$(currObj).attr("name", "percentage"+totalRows);
		});
		$(copy).find(".boardUniversityOtherClass").each(function(currIndex, currObj) {
			$(currObj).attr("name", "boardUniversityOther"+totalRows);
		});
		var newAcademicDetails = $("<td></td>").append(copy);
		//alert ("1");
		var newRow = $("<tr></tr>").append(newAcademicDetails).attr("id", "rowid"+totalRows);
		//alert ("11");
		$(newRow).find(".deleteAcademicDetails").attr("id", "btn"+totalRows);
		//alert ("111");
		$("#tblAcademicDetails").append(newRow);
		//alert ("#toggleText"+totalRows);
		$("#toggleText"+totalRows).show();
		//alert ("11111");
		bindDeleteEvents();
		bindBoardUniversityTextEvents();
		
		$("#academicDetails").validationEngine({promptPosition : "bottomLeft"});
		$("#academicDetails").validationEngine('detach');
	} 

	
	function bindBoardUniversityTextEvents()
	{
		$(".boardUniversityClass").unbind();
		$(".boardUniversityClass").change(function(){
			
			if($(this).find("option:selected").text() =="Others")
			{
				$(this).parent().next().next().find(":nth-child(1)").val("");
				$(this).parent().next().next().find(":nth-child(1)").removeAttr("disabled");
				$(this).parent().next().next().show();
			}
			else
			{
				$(this).parent().next().next().hide();
				$(this).parent().next().next().find(":nth-child(1)").val("#$#");
				$(this).parent().next().next().find(":nth-child(1)").attr("disabled", "disabled");
			}
		});
	}

	function bindDeleteEvents()
	{
		$(".deleteAcademicDetails").unbind();
		$(".deleteAcademicDetails").bind('click', function() 
		{
			//alert($(this).attr("id"));
			if (currTotalRows > 1){
			$("#academicDetails").validationEngine('hide');
			var id = $(this).attr("id");
			id = id.replace("btn", "");
			$("#rowid"+id).remove();
			currTotalRows=currTotalRows-1;
			}else{
				alert ("Atleast One Academic Details Information Required");
			}
		});
		
	}
	 function ispercentage(obj, e, allowDecimal, allowNegative) 
 	{
	   var key;
	   var isCtrl = false;
	   var keychar;
	   var reg;
	   if (window.event) 
	   {
	     key = e.keyCode;
	     isCtrl = window.event.ctrlKey
	   }
	   else if (e.which)
	   {
	     key = e.which;
	     isCtrl = e.ctrlKey;
	   }
	   if (isNaN(key)) return true;
	   keychar = String.fromCharCode(key);
	   // check for backspace or delete, or if Ctrl was pressed
	   if (key == 8 || isCtrl)
	   {
	     return true;
	   }
	   ctemp = obj.value;
	   var index = ctemp.indexOf(".");
	   //alert("index    "+ index);
	   var length = ctemp.length;
	  // alert("length    "+ length);
	   ctemp = ctemp.substring(index, length);
	   //alert("ctemp    "+ ctemp);
	   if (index < 0 && length > 1 && keychar != '.' && keychar != '0')
	   {
	     obj.focus();
	     return false;
	   }
	   if (ctemp.length > 2)
	   {
	     obj.focus();
	     return false;
	   }
	   if (keychar != 1 && keychar != 2 && keychar != 3 && keychar != 4 && keychar != 5 && keychar != 6
	   		&& keychar != 7 && keychar != 8 && keychar != 9 && keychar != 0 && length >= 2 && keychar != '.' && ctemp != '10') {
	     obj.focus();
	     return false;
	   }
	   reg = /\d/;
	   var isFirstN = allowNegative ? keychar == '-' && obj.value.indexOf('-') == -1 : false;
	   var isFirstD = allowDecimal ? keychar == '.' && obj.value.indexOf('.') == -1 : false;
	   return isFirstN || isFirstD || reg.test(keychar);
	}
	function changer(elementId) 
	{
  		var selectedId = document.getElementById(elementId).value;
  		//alert(selectedId);
  		//.......
	}
	
</script>
<%try{ %>
<s:form action="EnrollmentAction" id="academicDetails">

<s:hidden name="hQualification" id="hQualification" />
<s:hidden name="hYear" id="hYear" />
<s:hidden name="hPercentage" id="hPercentage" />
<s:hidden name="hBoard" id="hBoard" />
<s:hidden name="hOtherBoard" id="hOtherBoard" />
<s:hidden name="actionNameAcademic" id="actionNameAcademic" value="%{actionName}"></s:hidden>

<input type="hidden" id="hAcademicDetailsDivInd" value="3" />

<s:if test="%{actionName==null || actionName==''}">
	<div id="TabDiv3" class="tab_content" style="display:none">
</s:if>
<s:elseif test="%{actionName=='preview' || actionName=='view'}">
	<div id="TabDiv3" class="tab_content" style="display:block">
</s:elseif>


<div class="field-label"><s:text name="viewupdateacademicdetails.academicInformation"/>  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="button" title="Add Details" class="submitBtn button-gradient addDetailsClass enrollFinal" value="Add Details" />
</div>



<div id="toggleText" style="display:none;"  >
<div class="hr-dashline" style="clear:both;"></div>

<!-- Row One Start -->

<div class="padleft40 clear">
	<div class="fl-left fifty">
		<div class="field-label"><s:text name="viewupdateacademicdetails.qualifiedExam"/>&nbsp;<span class="manadetory-fields">*</span></div>
		<div>
			<s:select name="qualificationListId1"
				headerKey=""   headerValue="Select Exam" list="qualificationListBean" key="referanceValue" 
				id="qualificationListBeanId1" 
				listValue="referanceValue" listKey="refernceId" value="%{qualificationListId}" 
				cssClass="qualificationClass inputFieldClass validate[required]"
				errRequired="Please select Qualified Exam"
				onchange="changer('qualificationListBeanId1')"
				errNotEqualsClass="One or more qualification has same value"
				/>
				
				<!-- for  validation message -->
	<br/>
	<br/>
		</div>
		<div id="qaerrorID1" class="errorMessage">Please select exam.</div>
	</div>

	<div class="fl-rigt fifty">
		<div class="field-label"><s:text name="viewupdateacademicdetails.yearOfQualifyingExam"/>&nbsp;<span class="manadetory-fields">*</span></div>
			<div>
				<s:select name="selectYear1" headerKey="" cssClass="valdiate[required]" 
				headerValue="Select Year" list="yearList"  id="yearId1" cssClass="yearClass inputFieldClass validate[required]" 
				errRequired="Please select qualifying year	"/>
			</div>
		<div id="qaerrorID2" class="errorMessage">Please select qualifying exam.</div>
	</div>
	
</div>
<!-- Row One End -->
<!-- for  validation message -->
	<br/>
	<br/>
<!-- Row Two Start -->
<div class="padleft40 clear">
	<div class="fl-left fifty">
	<div class="field-label"><s:text name="viewupdateacademicdetails.percentageSecured"/>&nbsp;<span class="manadetory-fields">*</span></div>
		<div>
		<s:textfield cssClass="inputField percentageClass inputFieldClass validate[required,custom[number],max[100],min[0]]"  
				  errRequired="Please type percentage" size="10" id="percentageSecure" maxlength="5" onkeypress="return ispercentage(this,event,true,false)"/>
		</div>
		<div id="qaerrorID3" class="errorMessage"></div>
	</div>
	<div class="fl-rigt fifty">

	<div class="field-label"><s:text name="viewupdateacademicdetails.boardUniversity"/>&nbsp;<span class="manadetory-fields">*</span></div>
	<div>
	 <s:select name="boardUniversityListId1"
			headerKey="" cssClass=""  headerValue="Select University" list="boardUniversityList" key="referanceValue" id="boardUniversityListId1" 
			listValue="referanceValue" listKey="refernceId" value="%{boardUniversityListId}" cssClass="boardUniversityClass inputFieldClass validate[required]" 
			errRequired="Please select university or board"/>
	</div>
	<br/>
	<div style="display: none;">
		<s:textfield  cssClass="boardUniversityOtherClass inputField inputFieldClass validate[required,maxSize[30]]" 
			disabled="disabled" value="#$#" errRequired="Please enter university or board" maxlength="30" ></s:textfield>
	</div>

	</div>
</div>
<!-- Row Two End -->
<div class="padleft40 clear" align="right">
<input type="button" title="Delete Details" class="submitBtn button-gradient deleteAcademicDetails enrollFinal" value="Delete Details" />
</div>
</div>

<table id='tblAcademicDetails' width="100%">
<%int currRow = 1; %>
<s:iterator value="academicDetailList" >
<tr id='rowid<%= currRow %>'>
	<td>
			<div id="toggleText0" style="display:block;" >
			<div class="hr-dashline"></div>
			
			<!-- Row One Start -->
			<div class="padleft40 clear">
			<div class="fl-left fifty">
			<div class="field-label"><s:text name="viewupdateacademicdetails.qualifiedExam"/>&nbsp;<span class="manadetory-fields">*</span></div>
			<div>
				<s:select name="qualificationListId"
					headerKey="" 
					 headerValue="Select Exam" list="qualificationListBean" key="refernceId" id="qualificationListBeanId" 
					listValue="referanceValue" listKey="refernceId" value="%{qualificationListId}" cssClass='qualificationClass qualificationClassActive inputFieldClass enrollFinal validate[required]'
					errRequired="Please select Qualified Exam"
					errNotEqualsClass="One or more qualification has same value" />
			</div>
			<div id="qaerrorID1" class="errorMessage">Please select exam.</div>
			</div>
			
			<div class="fl-rigt fifty">
			<div class="field-label"><s:text name="viewupdateacademicdetails.yearOfQualifyingExam"/>&nbsp;<span class="manadetory-fields">*</span></div>
			<div><s:select name="selectYear" headerKey="" errRequired="Please select qualifying year"
			 
			headerValue="Select Year" list="yearList"  id="yearId" cssClass="yearClass inputFieldClass validate[required] enrollFinal" /></div>
			<div id="qaerrorID2" class="errorMessage">Please select qualifying exam.</div>
			</div>
			</div>
			<!-- Row One End -->
			
			<!-- Row Two Start -->
			<div class="padleft40 clear">
			
			<!-- for validation message -->
			<br/>
			
			<div class="fl-left fifty">
			<div class="field-label"><s:text name="viewupdateacademicdetails.percentageSecured"/>&nbsp;<span class="manadetory-fields">*</span></div>
			<div> <s:textfield name="percentage<%= currRow %>" cssClass="inputField percentageClass inputFieldClass validate[required,custom[number],max[100],min[0]] enrollFinal" 
			 size="10" id="percentageId" maxlength="5" onkeypress="return ispercentage(this,event,true,false)" 
			 errRequired="Please type percentage" value="%{percentage}" /></div>
			<div id="qaerrorID3" class="errorMessage">Please type percentage.</div>
			</div>
			
			<div class="fl-rigt fifty">
			<div class="field-label"><s:text name="viewupdateacademicdetails.boardUniversity"/>&nbsp;<span class="manadetory-fields">*</span></div>
			<div>
				<s:select name="boardUniversityListId"
					headerKey=""  headerValue="Select University" list="boardUniversityList" key="referanceValue"
					headerValue="Select Exam" list="boardUniversityList" key="refernceId" id="boardUniversityListId" 
					listValue="referanceValue" listKey="refernceId" value="%{boardUniversityListId}" 
					errRequired="Please select university or board" cssClass='boardUniversityClass inputFieldClass validate[required] enrollFinal' />
			</div>
			<br/>
			<s:if test="%{otherBoard[0] != null}">
				<div>
					<s:textfield name='boardUniversityOther<%= currRow %>'
						cssClass="boardUniversityOtherClass inputField inputFieldClass validate[required,maxSize[30]]" 
						value="%{otherBoard[0]}" 
						errRequired="Please enter university or board">
					</s:textfield>
				</div>
			</s:if>
			<s:else>
				<div style="display: none;">
					<s:textfield name='boardUniversityOther<%= currRow %>'
						cssClass="boardUniversityOtherClass inputField inputFieldClass validate[required,maxSize[30]]" 
						disabled="disabled" value="#$#" 
						errRequired="Please enter university or board">
					</s:textfield>
				</div>
			</s:else>
			</div>
			<!-- Row Two End -->
			
			<div class="padleft40 clear" align="right">
			<input type="button" title="Delete Details" id="btn<%= currRow++ %>" class="submitBtn button-gradient deleteAcademicDetails enrollFinal" value="Delete Details" />
			</div>
			
			<!-- Row Three Start -->
			<div class="padleft40 clear">
			<div class="fl-left fifty">
			
			
			<div id="qaerrorID5" class="errorMessage">Please select university or board.</div>
			</div>
			
			<div class="fl-rigt fifty">
			</div>
			</div>
			
			
			</div>
	</td>
</tr>
</s:iterator>
</table>
<% if (currRow <2){ %>
<script>
	addDetails(); 
</script>
<%} %>
<!-- Row Three End -->
<div class="height10"></div>
<!-- Row Forth Start -->
<div class="clear">
<s:if test="%{actionName=='' || actionName == null}">
	<input type="button" title="back" class="submitBtn button-gradient enrollFinal" value="<s:text name="viewupdateacademicdetails.back"/>" id="btnAcademicDetailsPrev" />&nbsp;&nbsp;
	<input type="button" title="Save" class="submitBtn button-gradient cancelBtn" value="<s:text name="viewupdateacademicdetails.cancel"/>"/>&nbsp;&nbsp;
	<input type="button" title="next" class="submitBtn button-gradient enrollFinal" value="<s:text name="viewupdateacademicdetails.next"/>" id="btnAcademicDetailsNext" />
</s:if>
<s:elseif test="%{actionName=='preview' || actionName=='view'}">
	<input type="button" value="<s:text name="viewupdateacademicdetails.update"/>" class="submitBtn button-gradient" id="btnEditAcademicDetails"/>
</s:elseif>
</div>
<!-- Row Forth End -->

</div>

<input type='hidden' name="htotalRows" id='htotalRows' value='<%= --currRow %>'></input>
</s:form>

<%}catch(Exception e){
	e.printStackTrace();
}%>