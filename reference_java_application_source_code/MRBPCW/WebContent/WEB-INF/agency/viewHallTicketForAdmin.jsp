<%@ taglib prefix="s" uri="/struts-tags"%> 
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
    <script type="text/javascript">
		    $(document).ready(function(){
		    	<s:if test='%{hallTicket2Disp=="true"}'>
		    		$("#showTable").hide();
		    		$("#block23").show();
		    	</s:if>
		    
		    <s:if test='%{hallTicket1Disp=="true"}'>
	    		$("#showTable").hide();
	    		$("#block24").show();
	    	</s:if>
	    	
			    
		    	$(".imageDisplay").attr ("src","PhotoImage.jpg?userFk="+$("#test").val());
		    	$("#candidateSignature").attr ("src","SignatureImage.jpg?userFk="+$("#test").val());
		    	$(".barcodeimageDisplay").attr ("src","BarCode.jpg?userFk="+$("#test").val()+"&bookingAttempt="+$("#bookingAttempt").val());
		});


			function hideDiv (id){
				$("#"+id).hide(); 
			}


		function validateInputForHallticket(){
			var message = "";
			
			var userId = $("#userId").val();
			if (userId ==""){
				message = message + "Please enter Candidate Id."+"##";
			}else{
				$("#errorMessagesDiv").hide();
			}

			if(message != ""){
				$("#showTable").hide();
					var ulID = "errorMessages";
					var divID = "errorMessagesDiv";
					createErrorList(message, ulID, divID); 
					return false;
				}
				else{
					return true;

			
		}
		}

			function changeActionForAttempt1() {
				var msg = validateInputForHallticket();
				if (msg){
					$("#hallTicketForm").attr('action',"CandidateMgmtAction_getCandidateHallTicketForFirstAttempt.action");
					$("#hallTicketForm").submit();
				}
			}
			
			function changeActionForAttempt2() {
				var msg = validateInputForHallticket();
				if (msg){
					$("#hallTicketForm").attr('action',"CandidateMgmtAction_getCandidateHallTicketForSecondAttempt.action");
					$("#hallTicketForm").submit();
				}
			}
		
		function clearFields(){
		$("#userId").val("");
		$("#showTable").hide();
   		$("#block24").hide();
   		$("#block23").hide();
   		$("#errorMessagesDiv").hide();
   		
		}
	
	
	function setValueFirstAttempt() {
		$("#attemptDownload").val("");
		var value = "BOOKING_ATTEMPT_1"
		$("#attemptDownload").val(value);
	}
	
	
	function setValueSecondAttempt() {
		$("#attemptDownload").val("");
		var value2 = "BOOKING_ATTEMPT_2"
		$("#attemptDownload").val(value2);
	}
	function downloadHallticket(){
		window.open("SchedulingTestAction_printHallTicketJasperFromHelpCenter.action?testDate="+$("#testDate").val()+
		"&testCenter="+$("#testCenterName").val()+"&testTime="+$("#candidateTestStartTime").val()+
		"&discipline="+$("#discipline").val()+"&candidateID="+$("#userIdHallTicket").val()+
		"&userFK="+$("#userIdForHallticket").val()+"&rollNumberForHallTicket="+$("#rollNumberForHallTicket").val()+
		"&candidateName="+$("#candidateName").val()+"&userId="+$("#userId").val()+"&enrollmentPK="+$("#enrollmentIdForHallTicket").val()+
		"&bookingAttempt="+$("#bookingAttempt").val()+"&tstCenterAddress="+$("#candidateTestCenterAddress").val()+"&reportingTime="+$("#candidateReportingTime").val());
	}
	
	function onErrorMessage(id){
		$("#"+id).hide();
	}
    </script>
    

<s:form action="CandidateMgmtAction" id="hallTicketForm" >
<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
<s:hidden id='testDate' name="testDate" value="%{candidateTestDate}" />
<s:hidden id='testCenterName' name="testCenterName" value="%{testCenterName}" />
<s:hidden id='candidateTestStartTime' name="candidateTestStartTime" value="%{candidateTestStartTime}" />
<s:hidden id='discipline' name="discipline" value="%{testCenterValue}" />
<s:hidden id='candidateName' name="candidateName" value="%{candidateName}" />
<s:hidden id='candidateTestCenterAddress' name="candidateTestCenterAddress" value="%{candidateTestCenterAddress}" />
<s:hidden id='candidateReportingTime' name="candidateReportingTime" value="%{candidateReportingTime}" />
<s:hidden id='userIdHallTicket' name="userIdHallTicket" value="%{userIdHallTicket}" />
<s:hidden id='userIdForHallticket' name="userIdForHallticket" value="%{userIdForHallticket}" />
<s:hidden id='enrollmentIdForHallTicket' name="enrollmentIdForHallTicket" value="%{enrollmentIdForHallTicket}" />
<s:hidden id='rollNumberForHallTicket' name="rollNumberForHallTicket" value="%{rollNumberForHallTicket}" />
<s:hidden id='password' name="password" value="%{password}" />
<s:if test='%{firstAttemptFlag=="true"}'>
			<s:hidden id='bookingAttempt' name="bookingAttempt" value="1" />
</s:if>
		
<s:if test='%{secondAttemptFlag=="true"}'>
			<s:hidden id='bookingAttempt' name="bookingAttempt" value="2" />
</s:if>

		
<input type="hidden" name="userIdForHallticket" value="<s:property value = "userIdForHallticket"/>" id="test"/>
<input type="hidden" name="attemptDownload" id = "attemptDownload" />
<input type="hidden" name="userIdForHallticket" value="<s:property value = "userIdForHallticket"/>" id="test"/>
<div class="container" >
<div class="main-body">
<div class="fade" id="pop3"></div>

<div id="dashboard">

<h1 class="pageTitle" title="Print Admit Card">Print Admit Card</h1>
<div class="hr-underline2"></div>
<div id="errorMessagesDiv" style="display:none" class="error-massage">
    <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="errorMessages">
    	</ul>
    	
    </div>
</div>
<!-- Box Container Start -->	
<div style="display:block; min-height:300px; height:auto;">

  <table class="contenttable">
    <tr>
  		<td width="226">
	   			User ID
	   			<span class="manadetory-fields">*</span>
	      </td>
	  
	      <td width="670" >
	      		<s:textfield name="userId" id="userId" onkeypress="return alphaNumeric(event)" ></s:textfield>
	      </td>
	</tr>
    <tr>
	     
	      <td>
	      		&nbsp;
	      </td>
	     
	      <td>
	      <s:if test='%{buttonDispFlag=="true"}'>
	   	   	<input type="button" value="View Admit Card" onclick="changeActionForAttempt1();" class="submitBtn button-gradient" />&nbsp;
	      </s:if>
	  	 	   <s:if test='%{attmpt2Status=="A"}'>
	  	 	   		<input type="button" value=" View Admit Card Attempt 1" onclick="changeActionForAttempt1();" class="submitBtn button-gradient" />&nbsp;
	  		   		<input type="button" value=" View Admit Card Attempt 2" onclick="changeActionForAttempt2();" class="submitBtn button-gradient" />&nbsp;
	  		   </s:if>
	  		   <input type="button" value="Clear" class="submitBtn button-gradient" onclick="clearFields();"/> 
	      </td>
	      
    </tr>
    </table>
  <br />

  
<div id="block23" style="display:none;">
 <div class="hr-underline2"></div>
    <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li>  <s:property value = "userId"/> has not booked a seat for Second attempt .</li> 
    	</ul>
    </div>

</div>

<div id="block24" style="display:none;">
 <div class="hr-underline2"></div>
    <div class="error-massage-text">
    	<ul style="margin:0; margin-left:20px; padding:0;" id="abcd">
			    <li>  <s:property value = "userId"/> has not booked a seat for First attempt .</li> 
    	</ul>
    	
    </div>

</div>

  <s:if test='%{hallticketDisplayFlag=="true"}'>
  <div  id="showTable">
  <div class="hr-underline2"></div>
  
  <table width="700" align="center" cellpadding="5" style="border:#333 1px solid; line-height:140%;" >
    <tr>
      <td width="105" >
      	<img src="images/nseit-teg_logo.png" width="66" height="78" alt="GCET " />
      </td>
      
      <td width="397" align="center" >
		      <span class="hallticket-text"><!--Gujarat  Technological University, <br />
		        Ahmedabad 
		        --><br />
		        <s:if test='%{attemptVal == 1}'> 
		         Admit Card
				<s:hidden id = "pathName" name="pathName" value="BOOKING_ATTEMPT_1"></s:hidden>
				</s:if>
				<s:else> 
				 Admit Card
				<s:hidden id = "pathName" name="pathName" value="BOOKING_ATTEMPT_2"></s:hidden>
				</s:else>
			  </span>
  	  </td>
  	  
  	  
      <td width="108" align="right" >
      		<span class="pageTitle"><img id = "candidateImage" height="100" width="100" border="1" class="imageDisplay" onerror="onErrorMessage('candidateImage')"></img>	
      		<br/> <br/>
      		<img id = "barcodeImage" height="50" width="100" class="barcodeimageDisplay"></img>
      		</span>
      </td>
    </tr>

    <tr>
      <td colspan="3" ><table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td >Programme Applied for </td>
          <td colspan="3"><strong><s:label value="%{testCenterValue}" /></strong></td>
          </tr>
        <tr>
          <td width="180" valign="top" >User ID</td>
      	  <td width="250" valign="top"><strong><s:label value="%{userId}" /></strong></td>
      	  <td width="180" >Test Date</td>
      	  <td width="200" ><strong><s:property value="candidateTestDate"/></strong></td>
     	</tr>
        <tr>
          <td>Enrollment ID</td>
          <td><strong><s:label value="%{enrollmentIdForHallTicket}" /></strong></td>
          <td>Reporting Time</td>
          <td><strong><s:property value="candidateReportingTime"/></strong></td>
          </tr>
          
          
          <s:if test='%{rollNoDisplayFlag=="true"}'>
          <tr>
          <td>Roll Number</td>
          <td><strong><s:property value="%{rollNumberForHallTicket}"/></strong></td>
        </tr>
        </s:if>
        <tr>
          <td>Password</td>
          <td colspan="3"><strong><s:label value="%{password}"/></strong></td>
        </tr>
      
      	<tr>
          <td>Name</td>
          <td><strong><s:label value="%{candidateName}" /></strong></td>
          <td>Exam start Time</td>
          <td><strong><s:property value="candidateTestStartTime"/></strong></td>
        </tr>
          
        <tr>
          <td>Test Centre</td>
          <td colspan="3"><strong><s:property value="testCenterName"/></strong></td>
        </tr>
        <tr>
          <td>Test Centre Address</td>
          <td colspan="3"><strong><s:property value="candidateTestCenterAddress"/></strong></td>
       </tr>
      
        <tr>
          <td height="50" colspan="2" align="center" valign="bottom">&nbsp;</td>
          <td align="center" valign="bottom" colspan="3">&nbsp;</td>
          </tr>
        <tr>
          <td colspan="3"><span style="border-top:#999 1px solid;">Invigilator's Signature</span></td>
          <td align="center"><img id = "candidateSignature" height="50" width="100" onerror="onErrorMessage('candidateSignature')" style=" border:#CCC 1px solid; margin-bottom:5px;"><br />
          <span style="border-top:#999 1px solid;">Candidate's Signature</span></td>
          </tr>
        <tr>
          <td height="50" colspan="4" align="right">Candidate's Emergency Contact Phone No: 
            <label for="textfield"></label>
            <input name="textfield" type="text" disabled="disabled" id="textfield" style="border:0; border-bottom:1px solid #999;" readonly="readonly" /></td>
          </tr>
        </table></td>
    </tr>
    </table>
    
    <table width="700" align="center" cellpadding="5" style="line-height:150%; text-align:justify;">
    <tr>
      <td colspan="3" align="center"><strong>Instructions</strong></td>
    </tr>
    <tr>
      <td colspan="3" valign="top"><ul>
        <li> Take two print outs of this Admit Card on A4 size paper only.</li>
<li> Please ensure that all the information on this admit card is clearly visible on the print out. It is the responsibility of the candidate to check the schedule of entrance exam, exam name and category.</li>
<li> Test centre city, test centres and test slots are allotted on the first come first serve priority basis.</li>
<li> Candidate must not mutilate the admit card or change any entry. The admit card is not transferable to any other person. Impersonation is a legally punishable offence.</li> 
<li> Report to the allotted Test Centre as per the reporting time mentioned above with the two copies of the admit card and ANY ONE of the following for photo identification in original: Driving License, Passport, Voter's Card issued by election commission, Photo I-card issued by last institute attended, income Tax PAN card.</li> 
<li> No candidate will be permitted without a valid admit card and without an original valid ID card.</li> 
<li> There will be a registration process prior to the actual exam. Please report at the Reporting Time mentioned on the admit card.</li> 
<li> Candidate should retain one copy of this admit card with invigilator's signature.</li>
<li> No electronic devices (including calculators and mobile phones) are permitted. No facilities for storage will be provided.</li> 
<li> No candidate will be allowed to go outside the examination hall till the completion of the entire duration of time.</li>
        
      </ul></td>
    </tr>
    <tr>
    <td colspan="2">
		<s:if test='%{firstAttemptFlag=="true"}'>
			<!--<s:submit value="Print" cssClass="submitBtn button-gradient" onclick="downloadHallticket();"></s:submit>
		-->
			<input type="button" type="button" value="Print" Class="submitBtn button-gradient" title="Print" onclick="downloadHallticket();" />
		</s:if>
		
		<s:if test='%{secondAttemptFlag=="true"}'>
			<!--<s:submit value="Print" cssClass="submitBtn button-gradient" onclick="downloadHallticket();"></s:submit>
			-->
			<input type="button" type="button" value="Print" Class="submitBtn button-gradient" title="Print" onclick="downloadHallticket();" />
		</s:if>
			
		</td>
      <!--<td colspan="3" align="right" valign="top"><input type="button" type="button" value="Print"
										Class="submitBtn button-gradient" title="Submit"
										onclick="downloadHallticket()" /></td>
    -->
    
    </tr>
  </table>
  </div>
    
    </s:if>
</div>
</div>
</div>
</div>
</s:form>
