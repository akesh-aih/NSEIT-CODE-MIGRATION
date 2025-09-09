<%@ taglib prefix="s" uri="/struts-tags"%>

<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript">
$(document).ready(function() {

$("#candidateImage").attr ("src","PhotoImage.jpg?userFk="+$("#userFK").val());
$("#candidateSignature").attr ("src","SignatureImage.jpg?userFk="+$("#userFK").val());

});

	function downloadHallticket(){
		//$("#pathName").val("BOOKING_ATTEMPT_1");
		//document.frmHallTicket.action = "SchedulingTestAction_printHallTicketJasper.action?";
		//document.frmHallTicket.submit();
		
		//$('#frmHallTicket').attr('action','SchedulingTestAction_printHallTicketJasper.action' );
	//	$('#frmHallTicket').submit();
	//	$("#frmHallTicket").attr("target","_blank");
		window.open('SchedulingTestAction_printHallTicketJasper.action');
		//window.open($('#frmHallTicket').attr('action','SchedulingTestAction_printHallTicketJasper.action' ));
		//window.open("SchedulingTestAction_printHallTicketJasper.action?testDate="+$("#testDate").val()+
		//"&testCenter="+$("#testCenter").val()+"&testTime="+$("#testTime").val()+
		//"&testDuration="+$("#testDuration").val()+"&discipline="+$("#discipline").val()+
		//"&candidateName="+$("#candidateName").val()+"&userId="+$("#userId").val()+
		//"&bookingAttempt="+$("#bookingAttempt").val()+"&tstCenterAddress="+$("#tstCenterAddress").val()+"&reportingTime="+$("#reportingTime").val());
	}
	
	
		function changeAction(){
			$('#frmHallTicket').attr('action','SchedulingTestAction_goForSecondAttemptBokking.action');
			$('#frmHallTicket').submit();
		}
	
	

</script>
<div class="container">
<div id="dashboard" style="min-height: 300px;">
<h1 class="pageTitle" title="View Admit Card">View Admit Card</h1>
		<div class="hr-underline2"></div>

<s:form name='frmHallTicket' id ='frmHallTicket'>
<input type="hidden" name="menuKey" id="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
 <s:hidden id='userFK' name="userFK"
				value="%{userFK}" />
				<s:hidden id='testDate' name="testDate" value="%{testDate}" />
					<s:hidden id='testCenter' name="testCenter" value="%{testCenter}" />
					<s:hidden id='testTime' name="testTime" value="%{testTime}" />
					<s:hidden id='testDuration' name="testDuration" value="%{testDuration}" />
					<s:hidden id='discipline' name="discipline" value="%{discipline}" />
					<s:hidden id='candidateName' name="candidateName"
						value="%{candidateName}" />
					<s:hidden id='userId' name="userId" value="%{userId}" />
					<s:hidden id='bookingAttempt' name="bookingAttempt"
						value="%{bookingAttempt}" />
					<s:hidden id='tstCenterAddress' name="tstCenterAddress"
						value="%{tstCenterAddress}" />
						<s:hidden id='reportingTime' name="reportingTime"
						value="%{reportingTime}" />
<!-- 
<s:if test='%{bookingAttempt==1}'> 
<<s:hidden id = "pathName" name="pathName" value="BOOKING_ATTEMPT_1"></s:hidden>
</s:if>
<s:else> 
<s:hidden id = "pathName" name="pathName" value="BOOKING_ATTEMPT_2"></s:hidden>
</s:else>
  <table width="550" align="center" cellpadding="5" style="border:#999 1px solid;" >
    <tr>
      <td colspan="3" align="center" ><img src="images/nseit-teg_logo.png" width="66" height="78" alt="GCET " /><br />
        <span class="hallticket-text">Gujarat  Technological University, Ahmedabad <br />
          Common Entrance Test </span></td>
    </tr>
    <tr>
      <td height="50" colspan="3" align="center" ><span class="pageTitle">Hall Ticket</span></td>
      </tr>
     
    <tr>
      <td width="135" valign="top" ><strong>Candidate ID</strong></td>
      <td width="222" valign="top"><strong><s:label value="%{userId}" />&nbsp;</strong></td>
      <td width="153" rowspan="5" align="center" valign="top"><span class="pageTitle"><img id = "candidateImage" height="100" width="100" border="1"></img></span></td>
    </tr>
    <tr>
      <td valign="top"> <strong>Enrollment ID</strong></td>
      <td valign="top"><strong><s:label value="%{enrollmenBean.enrollmentPK}" />&nbsp;</strong></td>
      </tr>
    <tr>
      <td valign="top"><strong>Name</strong></td>
      <td valign="top"><strong><s:label value="%{candidateName}" />&nbsp;</strong></td>
      </tr>
      <tr>
      <td valign="top"><strong>Date  of Birth</strong></td>
      <td valign="top"><strong> <s:property value="dateOfBirth"/></strong></td>
      </tr>
         <tr>
      <td valign="top"><strong>Discipline </strong></td>
      <td valign="top"><strong><s:label value="%{discipline}" />&nbsp;</strong></td>
      </tr>
    <tr>
      <td valign="top"><strong>Attempt </strong></td>
      <td valign="top"><strong><s:if test='%{bookingAttempt==1}'> One </s:if><s:else> Two</s:else>&nbsp;</strong></td>
      </tr>
    
    <tr>
      <td valign="top"><strong>Test  Date</strong></td>
      <td valign="top"><strong> <s:property value="testDate"/></strong></td>
      </tr>
    <tr>
      <td valign="top"><strong>Test  Time</strong></td>
      <td colspan="2" valign="top"><strong> <s:property value="testTime"/></strong></td>
    </tr>
    <tr>
      <td valign="top"><strong>Duration </strong></td>
      <td colspan="2" valign="top"><strong> <s:property value="testDuration"/> minutes</strong></td>
    </tr>
    <tr>
      <td valign="top"><strong>Centre  Address</strong></td>
      <td colspan="3" valign="top"><strong> <s:property value="testCenter"/>,<s:property value="tstCenterAddress"/></strong></td>
    </tr>
    <tr>
      <td height="40" colspan="3">&nbsp;</td>
    </tr>
    <tr>
      <td colspan="3" bgcolor="#E9E7E7"><strong>Instruction</strong></td>
    </tr>
    <tr>
      <td height="100" colspan="3" valign="top"><ul>
        <li>Please switch off your Mobile phones before entering the  Examination hall.</li>
        <li>Please arrive 15 minutes prior to your test time.</li>
        
      </ul></td>
      </tr>
  </table>
  <br/>
  <br/>
<div align="center">
<input type="button" type="button" value="Print"
										Class="submitBtn button-gradient" title="Submit"
										onclick="downloadHallticket()" />
										</div>
 -->										
<div style="display:block; min-height:300px; height:auto;">
  <table width="700" align="center" cellpadding="0" style="line-height:150%; text-align:justify;">
    <tr>
      <td colspan="3"><a href="#" onclick="downloadHallticket()" ><strong>Click Here</strong></a> to print this Admit Card</td>
    </tr>
    </table>
  <table width="700" align="center" cellpadding="5" style="border:#333 1px solid; line-height:140%;" >
    <tr>
      <td width="105" valign="top" ><img src="images/nseit-NCFE-logo.png" width="66" height="78" alt="GCET " /><br /></td>
      <td width="397" align="center" valign="top" ><strong><br/><span class="hallticket-text">Gujarat  Technological University, <br />
        Ahmedabad 
        <br />
        <s:if test='%{bookingAttempt==1}'> 
         Admit Card - I
<s:hidden id = "pathName" name="pathName" value="BOOKING_ATTEMPT_1"></s:hidden>
</s:if>
<s:else> 
 Admit Card - II
<s:hidden id = "pathName" name="pathName" value="BOOKING_ATTEMPT_2"></s:hidden>
</s:else>
  </span></strong></td>
      <td width="108" align="right" ><span class="pageTitle"><img id = "candidateImage" height="100" width="100" border="1"></img><br/><img  height="50" width="100" src='BarCode.jpg' style="margin-top:5px;"></img>	</span></td>
    </tr>
    <tr>
      <td colspan="3" ><table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td width="26%">Programme Applied for </td>
          <td width="39%"><strong>:&nbsp;&nbsp;<s:label value="%{discipline}" />&nbsp;</strong></td>
          
          </tr>
          <tr>
          <td width="135" valign="top" >User ID</td>
      	  <td width="222" valign="top"><strong>:&nbsp;&nbsp;<s:label value="%{userId}" />&nbsp;</strong></td>
      	  <td width="35%">Test Date<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :&nbsp;&nbsp;<strong> <s:property value="testDate"/></strong></strong></td>
     	 </tr>
        <tr>
          <td>Enrollment ID</td>
          <td><strong>:&nbsp;&nbsp;<strong><s:label value="%{enrollmenBean.enrollmentPK}"/>&nbsp;</strong></strong></td>
          <td>Reporting Time&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>:&nbsp;&nbsp;<strong><s:property value="reportingTime"/></strong></strong></td>
          </tr>
          <s:if test='%{ConfigurationConstants.getInstance().getActiveStatusByMenuDesc(GenericConstants.GENERATE_ROLL_NUMBER) == "A"}'>
       <tr>
          <td>Roll Number</td>
          <td><strong>:&nbsp;&nbsp;<strong><s:property value="hallTicketBean.rollNumber"/>&nbsp;</strong></strong></td>
        </tr>
        </s:if>
        <tr>
          <td>Password</td>
          <td><strong>:&nbsp;&nbsp;<strong><s:label value="%{password}"/>&nbsp;</strong></strong></td>
          </tr>
        <tr>
          <td colspan="2">Name <strong>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp;&nbsp; :</strong>&nbsp;<strong><s:label value="%{candidateName}" />&nbsp;</strong><br />
            <br /> 
            Test Centre &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;&nbsp; &nbsp;&nbsp;<strong>: <s:property value="testCenter"/></strong><strong><br />
              </strong>Test Centre Address:<strong><br />
                <s:property value="tstCenterAddress"/>
                </strong></td>
          <td valign="top">Exam start Time&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>:&nbsp;&nbsp;<s:property value="testTime"/></strong></td>
          </tr>
        <tr>
          <td height="50" colspan="2" align="center" valign="bottom">&nbsp;</td>
          <td align="center" valign="bottom">&nbsp;</td>
          </tr>
        <tr>
          <td colspan="2"><span style="border-top:#999 1px solid;">Invigilator's Signature</span></td>
          <td align="center">
          	<img id = "candidateSignature" height="50" width="100" border="1">
          	<span style="border-top:#999 1px solid;">Candidate's Signature</span>
          </td>
          </tr>
        <tr>
          <td height="50" colspan="3" align="right">Candidate's Emergency Contact Phone No: 
            <label for="textfield"></label>
            <input name="textfield" type="text" disabled="disabled" id="textfield" style="border:0; border-bottom:1px solid #999;" readonly="readonly" /></td>
          </tr>
        </table></td>
    </tr>
    </table>
  <br />
  <table width="700" align="center" cellpadding="5" style="line-height:150%; text-align:justify;">
    <tr>
      <td colspan="3" align="center"><strong>Instructions</strong></td>
    </tr>
    <tr>
      <td colspan="3" valign="top"><ul>
        <li> Take two print outs of this admit card on A4 size paper only.</li>
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
      <td align="right" valign="top"><input type="button" type="button" value="Print"
										Class="submitBtn button-gradient" title="Submit"
										onclick="downloadHallticket()" /></td>
		<s:if test='%{bookingAttempt==1}'> 
			<td align = "right">
				<input type = "button" value = "Continue for seat bokking Attempt 2" onclick = "changeAction()" class="submitBtn button-gradient" />
			</td>
		</s:if>									
    </tr>
  </table>
</div>
<br />


</s:form>
</div>
</div>