<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">

<div class="main-body">

<div class="fade" id="pop3"></div>

<form name="EligibilityCriteria">
<div id="AgencyEligibility">

<h1 class="pageTitle" title="Email/SMS Configuration"><s:text name="agencyemaismsconf.title"/></h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->

<div class="AgencyEmailCont">

<!-- Left Container Start -->
<div class="fieldCont fl-left">

<div>
<div class="field-label"><s:text name="agencyemaismsconf.test"/>&nbsp;<span class="manadetory-fields">*</span></div>
	<div>
		<select class="mid-dropdown" name="exam">
			<option value="-1">--Select--</option>
			<option value="0">All</option>
			<option value="Mechanical">Mechanical</option>
			<option value="Electrical">Electrical</option>
			<option value="Computer">Computer</option>
			<option value="Electronics">Electronics</option>
			<option value="Chemical">Chemical</option>
		</select>
	</div>
<div class="feedbackerrorMsg" id="Error1"></div>
</div>


<div>
<div class="field-label"><s:text name="agencyemaismsconf.activity"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div><select class="mid-dropdown" name="activity" onchange="addOPT();">
<option value="-1">--Select--</option>
<option value="ConfirmRegistration">ConfirmRegistration</option>
<option value="PaymentReceipt">Payment Receipt</option>
<option value="ScheduleConfirmation">ScheduleConfirmation</option>
<option value="TestReminder">Reminder for Test</option>
</select></div>
<div class="feedbackerrorMsg" id="Error2"></div>
</div>

<div class="height5"></div>

<div>
<select class="fieldDropdown" id="lbKeywords" name="kyewords" size="4">
<!--<option value="{EXAM}">{EXAM}</option><option value="{EXAM FEES}">{EXAM FEES}</option><option value="{TXN NO}">{TXN NO}</option><option value="{TXN DATE}">{TXN DATE}</option><option value="{CANDIDATE NAME}">{CANDIDATE NAME}</option>--></select>
</div>
<div class="feedbackerrorMsg" id="Error3"></div>

</div>
<!-- Left Container End -->

<!-- Button Container Start -->
<div class="buttonCont fl-left">

<div class="emailButton1"><input type="button" class="submitBtn button-gradient" title=">>" value=">>" onclick="AddEmail();"/></div>

<div class="emailButton2"><input type="button" class="submitBtn button-gradient" title=">>" value=">>" onclick="AddSMS();"/></div>

</div>
<!-- Button Container End -->


<!-- Area Container Start -->
<div class="textareaCont fl-left">

<!-- Email Start -->
<div class="emailCont">

<div><span><input type="checkbox" class="chkButton" name="emailChk" onclick="chkTrue('email');"/>&nbsp;
<span class="EmailLabel"><s:text name="agencyemaismsconf.emailcontent"/></span></span></div>
<div class="height5"></div>
<div><strong><s:text name="agencyemaismsconf.subject"/></strong>&nbsp;&nbsp;<input type="text" name="subject" class="subjectField"  disabled="disabled"/></div>
<div><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<s:text name="agencyemaismsconf.cc"/></strong>&nbsp;&nbsp;<input type="text" name="cc" class="subjectField" /></div>
<div class="height5"></div>
<div>
<textarea class="AgencyArea" rows="5" cols="45" name="email" disabled="disabled" id="email"></textarea>
</div>
<div class="feedbackerrorMsg" id="Error4"></div>

</div>
<!-- Email End -->


<!-- SMS Start -->
<div class="smsCont">

<div><span><input type="checkbox" class="chkButton" name="smsChk" onclick="chkTrue('sms');"/>&nbsp;<span class="SMSLabel"><s:text name="agencyemaismsconf.smscontent"/></span></span></div>
<div class="height5"></div>
<div>
<textarea class="AgencyArea" rows="5" cols="45" id="sms" disabled="disabled" name="sms"></textarea>
</div>
<div class="feedbackerrorMsg" id="Error5"></div>

</div>
<!-- SMS End -->

<div class="height10"></div>
<div><input type="button" class="submitBtn button-gradient" value="<s:text name="agencyemaismsconf.submit"/>" title="Submit" onclick="emailSMSVal();"/>
&nbsp;&nbsp;<input type="button" class="submitBtn button-gradient" value="<s:text name="agencyemaismsconf.reset"/>" title="Reset" /></div>
</div>
<!-- Area Container End -->



</div>
<!-- Box Container End -->
</div>
</form>







</div>


