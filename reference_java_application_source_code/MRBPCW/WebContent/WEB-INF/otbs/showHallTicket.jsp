<%@ taglib prefix="s" uri="/struts-tags"%>

<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<script type="text/javascript">
$(document).ready(function() {

$("#candidateImage").attr ("src","PhotoImage.jpg?userFk="+$("#userFK").val());
$("#candidateSignature").attr ("src","SignatureImage.jpg?userFk="+$("#userFK").val());

});

function onErrorMessage(id){
	$("#"+id).hide();
}

	function downloadHallticket(){
		//$("#pathName").val("BOOKING_ATTEMPT_1");
		//document.frmHallTicket.action = "SchedulingTestAction_printHallTicketJasper.action?";
		//document.frmHallTicket.submit();
		
		//$('#frmHallTicket').attr('action','SchedulingTestAction_printHallTicketJasper.action' );
	//	$('#frmHallTicket').submit();
	//	$("#frmHallTicket").attr("target","_blank");
		//window.open($('#frmHallTicket').attr('action','SchedulingTestAction_printHallTicketJasper.action' ));
		//window.open('SchedulingTestAction_printHallTicketJasper.action');
		
		
		window.open("SchedulingTestAction_printHallTicketJasper.action?testDate="+$("#testDate").val()+
		"&testCenter="+$("#testCenterName").val()+"&testTime="+$("#testTime").val()+
		"&discipline="+$("#discipline").val()+"&candidateID="+$("#userFK").val()+
		"&userFK="+$("#userFK").val()+
		"&candidateName="+$("#candidateName").val()+"&userId="+$("#userId").val()+"&enrollmentPK="+$("#enrollmentPK").val()+
		"&bookingAttempt="+$("#bookingAttempt").val()+"&tstCenterAddress="+$("#tstCenterAddress").val()+"&reportingTime="+$("#reportingTime").val());
	}
	
	
		function changeAction(){
			$('#frmHallTicket').attr('action','SchedulingTestAction_showHallticketForAttemptTwo.action');
			$('#frmHallTicket').submit();
		}
	
	

</script>
<div class="container">
<div id="dashboard" style="min-height: 300px;">
<h1 class="pageTitle" title="View Admit Card">View Admit Card</h1>
		<div class="hr-underline2"></div>

<s:form name='frmHallTicket' id ='frmHallTicket'>
 <s:if test='%{hallTicketBean.bookingAttmptStatus=="Y"}'>
<input type="hidden" name="menuKey" id="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
 <input type = "hidden" id='userFK' name="userFK" value='<s:property value="hallTicketBean.userFK"/>' />
				<s:hidden id='testDate' name="testDate" value="%{hallTicketBean.testDate}" />
					<s:hidden id='testCenterName' name="testCenterName" value="%{hallTicketBean.testCenterName}" />
					<s:hidden id='testTime' name="testTime" value="%{hallTicketBean.testStartTime}" />
					<s:hidden id='testDuration' name="testDuration" value="%{testDuration}" />
					<s:hidden id='discipline' name="discipline" value="%{hallTicketBean.disciplineType}" />
					<s:hidden id='pass' name="pass" value="%{hallTicketBean.userPassword}" />
					<s:hidden id='candidateName' name="candidateName"
						value="%{hallTicketBean.userName}" />
					<s:hidden id='enrollmentPK' name="enrollmentPK"
						value="%{hallTicketBean.enrollmetPk}" />
					<s:hidden id='rollNo' name="rollNo"
						value="%{hallTicketBean.rollNumber}" />						
						
					<s:hidden id='userId' name="userId" value="%{hallTicketBean.userId}" />
				<s:if test='%{hallTicketBean.bookingAttempt==1}'>
					<s:hidden id='bookingAttempt' name="bookingAttempt" value="1" />
				</s:if>

				<s:if test='%{hallTicketBean.bookingAttempt==2}'> 
					<s:hidden id='bookingAttempt' name="bookingAttempt" value="2" />
				</s:if>
					<s:hidden id='tstCenterAddress' name="tstCenterAddress"
						value="%{hallTicketBean.testCenterAddress}" />
						<s:hidden id='reportingTime' name="reportingTime"
						value="%{hallTicketBean.reportingTime}" />

<div style="display:block; min-height:300px; height:auto;">
  <table width="700" align="center" cellpadding="0" style="line-height:150%; text-align:justify;">
    <tr>
      <td colspan="3"><a href="#" onclick="downloadHallticket()" ><strong>Click Here</strong></a> to print this Admit Card</td>
    </tr>
    </table>
  <table width="700" align="center" cellpadding="5" style="border:#333 1px solid; line-height:140%;" >
    <tr>
      <td width="105" valign="top" ><img src="images/nseit-teg_logo.png" width="66" height="78" alt="GCET " /><br /></td>
      <td width="397" align="center" valign="top" ><strong><br/><span class="hallticket-text"><!--Gujarat  Technological University, <br />
        Ahmedabad 
        --><br />
        <s:if test='%{hallTicketBean.bookingAttempt==1}'> 
         Admit Card 
<s:hidden id = "pathName" name="pathName" value="BOOKING_ATTEMPT_1"></s:hidden>
</s:if>
<s:else> 
 Admit Card
<s:hidden id = "pathName" name="pathName" value="BOOKING_ATTEMPT_2"></s:hidden>
</s:else>
  </span></strong></td>
      <td width="108" align="right" ><span class="pageTitle"><img id = "candidateImage" height="100" width="100" border="1" onerror="onErrorMessage('candidateImage')"></img><br/><img  height="50" width="100" src='BarCode.jpg' style="margin-top:5px;"></img>	</span></td>
    </tr>
    <tr>
      <td colspan="3" ><table width="100%" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td width="26%">Programme Applied for </td>
          <td width="39%"><strong>:&nbsp;&nbsp;<s:property value="hallTicketBean.disciplineType"/>&nbsp;</strong></td>
          
          </tr>
          <tr>
          <td width="135" valign="top" >User ID</td>
      	  <td width="222" valign="top"><strong>:&nbsp;&nbsp;<s:property value="hallTicketBean.userId"/>&nbsp;</strong></td>
      	  <td width="35%">Test Date<strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :&nbsp;&nbsp;<strong> <s:property value="hallTicketBean.testDate"/></strong></strong></td>
     	 </tr>
        <tr>
          <td>Enrollment ID</td>
          <td><strong>:&nbsp;&nbsp;<strong><s:property value="hallTicketBean.enrollmetPk"/>&nbsp;</strong></strong></td>
          <td>Reporting Time&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>:&nbsp;&nbsp;<strong><s:property value="hallTicketBean.reportingTime"/></strong></strong></td>
          </tr>
          
          <s:if test='%{rollNoDisplayFlag=="true"}'>
        <tr>
          <td>Roll Number</td>
          <td><strong>:&nbsp;&nbsp;<strong><s:property value="hallTicketBean.rollNumber"/>&nbsp;</strong></strong></td>
        </tr>
        </s:if>
        <tr>
          <td>Password</td>
          <td><strong>:&nbsp;&nbsp;<strong><s:property value="hallTicketBean.userPassword"/>&nbsp;</strong></strong></td>
          </tr>
        <tr>
        <td>Name</td>
        <td><strong>:&nbsp;&nbsp;<strong><s:property value="hallTicketBean.userName"/>&nbsp;</strong></td>
        </tr>
        <tr>
        <td valign="top">Test Centre</td>
        <td><strong>:&nbsp;&nbsp;<strong><s:property value="hallTicketBean.testCenterName"/></strong><strong></td>
        </tr>
        <tr>
        <td></strong>Test Centre Address<strong></td>
        <td><strong>:&nbsp;&nbsp;<strong><s:property value="hallTicketBean.testCenterAddress"/>
                </strong>
        </td>
        <td valign="top">Exam start Time&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <strong>:&nbsp;&nbsp;<s:property value="hallTicketBean.testStartTime"/></strong></td>
        </tr>
        
        <tr>
          <td height="50" colspan="2" align="center" valign="bottom">&nbsp;</td>
          <td align="center" valign="bottom">&nbsp;</td>
          </tr>
        <tr>
          <td colspan="2"><span style="border-top:#999 1px solid;">Invigilator's Signature</span></td>
          <td align="center">
          	<img id = "candidateSignature" height="50" width="100" onerror="onErrorMessage('candidateSignature')" style="margin-bottom:5px; border:#cccccc 1px solid;"><br/>
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
		<!--<s:if test='%{hallTicketBean.bookingAttempt==1}'> 
			<td align = "right">
				<input type = "button" value = "View Hallticket for Attempt 2" onclick = "changeAction()" class="submitBtn button-gradient" />
			</td>
		</s:if>									
    --></tr>
  </table>
</div>
<br />
</s:if>
<s:if test='%{hallTicketBean.bookingAttmptStatus=="N"}'>
	Please book a seat.
</s:if>

<s:if test='%{hallTicketBean.bookingAttmptStatus=="D"}'>
	Admit Card is not generated.
</s:if>


</s:form>
</div>
</div>