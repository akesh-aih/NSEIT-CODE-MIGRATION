<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
 
<div class="main-body">

<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">

	function frmLogin_onsubmit()
	{
		
		$("#username").val($.trim($("#username").val()));
		$("#password").val($.trim($("#password").val()));
 
		if($("#username").val() != "" && $("#password").val() != "")
		{
			$("#encryptedusername").val(Aes.Ctr.encrypt($("#username").val(), 'pass!dt@12-!e', 256));
			$("#encryptedpassword").val(Aes.Ctr.encrypt($("#password").val(), 'pass!dt@12-!e', 256));
		}
		
		
		
		
	}

	$(document).ready(function() {
		/*dojo.event.topic.subscribe("/before", function(event, widget){
	    	alert('inside a topic event. before request');
	    	//event: set event.cancel = true, to cancel request
	    	//widget widget that published the topic
		});*/
		
		
		$("#frmLogin").validationEngine({promptPosition : "bottomLeft", scroll: false});
		$("#frmRegister").validationEngine({promptPosition : "bottomLeft", scroll: false});

		$("#frmLogin").attr("autocomplete", "off");

		$("#registerSubmitBtn").click(function() {
		
			if($("#frmRegister").validationEngine('validate')==false)
				return;
			
			var firstName = $("#registerFirstName").val();
			var lastName = $("#registerLastName").val();
			var mmName = $("#registerMMName").val();
			var dob = $("#registerdob").val();
			var gender = $('input:radio[name=gender]:checked').val();
			var mobileNumber = $("#registerMobileNumber").val();
			var emailAddress1 = $("#registerEmailAddress1").val();
			var captcha = $("#registerCaptcha").val();
			
			dataString = "firstName="+firstName+"&lastName="+lastName+"&mmName="+mmName+"&dob="+dob+"&gender="+gender+
				"&mobileNumber="+mobileNumber+"&emailAddress1="+emailAddress1+"&captcha="+captcha;
			//alert(dataString);
			
			$.ajax({
				url: "CandidateAction_registerCandidate.action",
				async: true,
				data: dataString,
				beforeSend: function(){
					//alert('before send');
				},
				error:function(ajaxrequest){
					alert('Error registering user. Server Response: '+ajaxrequest.responseText);
				},
				success:function(responseText){
					responseText = $.trim(responseText);
					message = responseText.substring(2,responseText.length)
					textMessae = responseText.split(',');
					if(textMessae[0]=="2")
					{
						alert("Error : \n"+message);
						return false;
					}
					else if(textMessae[0]=="3")
					{
						alert("Error : \n"+textMessae[1]);
						$("#loginCaptchaImage").removeAttr("src").attr("src", "captchaImage.jpg");
						$("#registerCaptcha").val("");
						return false;
					}
					else if(textMessae[0]=="1")
					{
						alert(textMessae[1]);
						window.location='LoginAction_input.action';
					}
					else if(textMessae[0]=="9")
					{
						alert(message);
						return false;
					}
				}
			});
		});
	});
	
	function hideNewRegistrationErrors()
	{
		$("#frmRegister").validationEngine('hide');
		HideAll();
	}
	
	function hideForgotRegistrationAndPassword()
	{
		$("#userId").val('');
		$("#datePicker1").val('');
		$("#mobileNo").val('');
		$("#eMail").val('');
		$("#dateOfBirth").val('');
		$("#frmLogin").validationEngine('hide');
		
		//$("#forgotRegContainer").validationEngine('hide');
		
	}
	function forgotRegistrationNumber()
	{
		$("#forgotRegContainer").validationEngine('hide');
		HideAll(); 
	}
	
	function loginSubmit()
	{
		alert("username - -"  +  username);
		alert("password - -"  +  password);
			
		if(username == null && password == null)
		{
			$("#loginError").remove();
		}
		
	}
</script> 


<div class="hr-Login"></div>

<div id='registerTarget'>
	
</div>

<s:url id="registerSubmit" value="/CandidateAction.action" />

<!-- New Registration Start -->
<jsp:include page="candidate/insertRegistration.jsp" />
<!-- New Registration End -->

<!-- Forgot Registration Start -->
<jsp:include page="forgotRegistrationNumber.jsp" />
<!-- Forgot Registration End -->

<!-- Forgot Start -->
<jsp:include page="forgotPassword.jsp" />
<!-- Forgot End -->

<!-- Training Availability Start -->
<div class="training box-gradient" id="block3">
<div><a href="javascript:void(0);" onclick="HideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox fl-left"><h1 class="pageTitle" title="Training Availibility">&nbsp;Training Availibility</h1></div>
<div class="closebtnBox fl-rigt">&nbsp;</div>
<div class="hr-underline clear"></div>
<div class="training-cont">
<form>

<div class="field-label">Select Exam</div>
<div><select class="dropdown"><option value="">Select Exam</option><option value="HSC">HSC</option></select></div>
<div class="viewExam">
<table cellpadding="0" cellspacing="0" width="743" border="0">
<tr style="height:1px;"><td width="220"></td><td width="200">i</td><td width="285"><br /><br /></td></tr>
<tr>
<td class="examSch">Beginners Course - CPT</td>
<td class="examSch">15th Nov 2011 - 17th Nov 2011</td>
<td class="examSch">Chruchgate Training Center, Mumbai</td>
</tr>

<tr>
<td class="examSch">Beginners Course - CPT</td>
<td class="examSch">15th Nov 2011 - 17th Nov 2011</td>
<td class="examSch">Chruchgate Training Center, Mumbai</td>
</tr>

<tr>
<td class="examSch">Beginners Course - CPT</td>
<td class="examSch">15th Nov 2011 - 17th Nov 2011</td>
<td class="examSch">Chruchgate Training Center, Mumbai</td>
</tr>

</table>
</div><br />


<div class="box-header" title="View Accrediated training centers">View Accrediated training centers</div>
<div class="hr-underline clear"></div>
<br class="clear"/>
<div>
<div class="fl-left width32">
<div class="field-label">Select State</div>
<div><select class="dropdown"><option value="">Select State</option><option value="Maharashtra" selected="selected">Maharashtra</option></select></div>
</div>


<div class="fl-left width32">
<div class="field-label">Select City</div>
<select class="dropdown"><option value="">Select City</option><option value="HSC" selected="selected">Mumbai</option></select>
</div>
</div>
<div class="clear height5"></div>
<div class="viewCenters">
<table cellpadding="0" cellspacing="0" width="743" border="0">
<tr>
<td class="examSch" width="740">Chruchgate Training Center, Mumbai</td>
</tr>

<tr>
<td class="examSch" width="740">Chruchgate Training Center, Mumbai</td>
</tr>

<tr>
<td class="examSch" width="740">Chruchgate Training Center, Mumbai</td>
</tr>



</table>
</div>

</form>
</div>

</div>
</div>
<!-- Training Availability End -->

<!-- Eligibility Criteria Start -->
<div class="Eligibility box-gradient" id="block4">
<div><a href="javascript:void(0);" onclick="HideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox"><h1 class="pageTitle" title="Training Availibility">&nbsp;Eligible Criteria</h1></div>
<div class="hr-underline clear"></div>
<div class="termscont">
<form>

<div class="field-label">Select Exam</div>

<div>
<div style="width:50%" class="fl-left"><select class="dropdown"><option value="">Select Exam</option><option value="HSC">HSC</option></select></div>

<div style="width:50%; text-align:right;" class="fl-rigt"><img src="images/PDF.png" alt="PDF" title="PDF"/>&nbsp;&nbsp;<img src="images/Printer.png" alt="Print" title="Print"/></div>
</div>
<br class="clear"/>
<div class="termsConditions">
<div class="pad">
<p>The minimum academic qualification for appearing in AIEEE 2011 is that the candidate must have passed in final examination of 10+2 (Class XII) or its equivalent referred to as the qualifying examination (see Appendix IX) with 50% marks for general category candidates and 40% marks for SC/ST/OBC/PH category candidates.<br /><br />

Those appearing in 10+2 (Class XII) final or equivalent examination may also appear in AIEEEprovisionally. Candidates appearing in 10+2 (Class XII) in 2012 or passed in 2008 or before are not eligible to appear in AIEEE.<br /><br />

QUALIFYING EXAMINATIONS:<br /><br />

<ol type="i">
<li>The +2 level examination in the 10+2 pattern of examination of any recognized Central/State Board ofSecondary Examination,
such as Central Board of Secondary Education, New Delhi, and Council for Indian School Certificate Examination,
New Delhi</li>

<li>Intermediate or two-year Pre-University Examination conducted by a recognized Board/University.</li>

<li>Final Examination of the two-year course of the Joint Services Wing of the National Defence Academy.</li>

<li>Any Public School/Board/University Examination in India or in foreign countries recognized by the Association of Indian
Universities as equivalent to 10+2 system.</li>

<li>H.S.C. Vocational Examination.</li>

<li>A pass grade in the Senior Secondary School Examination conducted by the National Open School with a minimum of five
subjects.</li>

<li>3 or 4-year diploma recognized by AICTE or a State Board of Technical Education.</li>

</ol></p>
</div>

</div>

</form>
</div>
</div>
</div>
<!-- Eligibility Criteria End -->

<!-- Download Start -->
<div class="downloads box-gradient"  id="block5">
<div><a href="javascript:void(0);" onclick="HideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<div class="pad12">
<div class="titleBox"><h1 class="pageTitle" title="Training Availibility">&nbsp;Downloads</h1></div>
<div class="hr-underline clear"></div>
<form>

<div class="donload-cont">

<div class="more-download">

<div class="mdownload">
<div><b>Notes and Guidelines for Online CPT</b></div>
<div>Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text Dummy Text </div><br />
<div><a href="#read-here_1">Read here</a>&nbsp;|&nbsp;<img src="images/pdf.jpg" alt="PDF" title="PDF"/>&nbsp;<!-- <a href="http://nseit.com/pdfs/CaseStudy_Tea_Auction.pdf">Download Now</a> -->Download Now</div>
<p id="#read-here_1">Dummy Text On click of Read Here</p>
</div>
</div>
</div>

</div>
</form>
</div>
</div>
<!-- Download End -->

<div class="fade" id="block7"></div>


<!-- Login Container Start -->
<div class="login-container">

<!-- Login Box Start -->
<div style="width:421px;" class="fl-left">

	<s:form name="login" method="post" action="LoginAction" id='frmLogin' onsubmit="frmLogin_onsubmit();" >
		<s:hidden name="encData" id="encData" value=""></s:hidden>
		<h1 class="pageTitle"><s:text name="login.title" /></h1>
		<div class="loginbox box-gradient">
		
			<div class="errorMessageActive" id="loginError">		
				<s:actionmessage/>
			</div>
			<div class="field-label"><s:text name="login.username" />&nbsp;<span class="manadetory-fields">*</span></div>
			<div>
				<s:textfield  name="username" id="username" maxlength="50" size="10" cssClass="inputField validate[required,maxSize[50]]" errRequired="Please enter username."/>
				<s:hidden name="encryptedusername" id="encryptedusername"/>
			</div>
			<div class="errorMessage" id="loginID"><s:text name="login.errorlabeluserid"/></div>
			
			<!-- for validation message -->
			<br/>
			
			<div class="field-label"><s:text name="login.password" />&nbsp;<span class="manadetory-fields">*</span></div>
			<div>
				<s:password name="password" id="password" maxlength="50" size="10" cssClass="inputField validate[required,maxSize[50]]" errRequired="Please enter password." />
				<s:hidden name="encryptedpassword" id="encryptedpassword"/>
			</div>
			
			<!-- for validation message -->
			<br/>
			
			<div class="errorMessage" id="loginPWD"><s:text name="login.errorlabelpassword"/></div>
			<s:if test="%{#session['LOGIN_FAILURE_COUNT'] >= #application['LOGIN_MAX_FAILURE_BEFORE_CAPTCHA']}">
				<img src="captchaLoginImage.jpg" alt="Image not found." />
				<div class="field-label"><s:text name="login.entercaptcha" />&nbsp;<span class="manadetory-fields">*</span></div>
				<div>
					<s:textfield  name="captcha" id="captcha" size="10" cssClass="inputField validate[required]" errRequired="Please type the text you see in the picture."/>
				</div>
			</s:if>
			<div>
				<a href="javascript:void(0);" onclick="ShowBlock('block0');hideForgotRegistrationAndPassword()" title="Forgot User ID"><s:text name="login.forgotuserid"/></a>
				&nbsp;|&nbsp; 
				<a href="javascript:void(0);" onclick="ShowBlock('block2');hideForgotRegistrationAndPassword()" title="Forgot your Password?"><s:text name="login.forgotyourpassword"/></a>
			</div>
			<div class="height10"></div>
			<div align="left">
				<s:submit method="authenticateUser" key="login.submit" cssClass="submitBtn button-gradient" id="loginSubmit" title="Sign In" />
			</div>
			<div class="height10"></div>
			<div align="right"><s:text name="login.newuser"/> <a href="javascript:void(0);" onclick="preLoadRegistration();ShowBlock('block1');hideForgotRegistrationAndPassword()" title="Register here"><s:text name="login.registerhere"/></a></div>
			<div class="height20"></div>
		</div>
	</s:form>
</div>
<!-- Login Box End -->

<!-- News Box Start -->
<div class="newsBox-cont fl-rigt">
<div class="news box-gradient">
<h1 class="newsTitle" title="Training Availibility">Training Availibility</h1>
<a href="CandidateAction_downloadDetails.action?description=TrainingUpload"  title="Training Available in your cities">Training Available in your cities</a>
</div>

<div class="news box-gradient">
<h1 class="newsTitle" title="Eligibility">Eligibility Criteria</h1>
<a href="CandidateAction_downloadDetails.action?description=EligibilityUpload"  title="Check out the eligibility criteria for Online Test">Check out the eligibility criteria for Online Test</a>
</div>
<!--
<div class="news box-gradient">
<h1 class="newsTitle" title="Demo">Demo</h1>
<a href="#" title="Try out the sample questionnaire">Try out the sample questionnaire</a>
</div>
-->

<div class="news box-gradient">
<h1 class="newsTitle" title="Downloads">Downloads</h1>
<a href="javascript:void(0);" onclick="ShowBlock('block5');" title="Common Proficiency Test Form">Common Proficiency Test Form</a>
</div>

<div class="newsScroller box-gradient">
<h1 class="newsTitle" title="News / Announcements">News / Updates</h1>
<!-- News Sticker Start -->
<div id="newsticker">
<ul>


<li>
The University of Mumbai (earlier known as University of Bombay) is one of the oldest and premier Universities in India.
<a href="#read-more_1">Read more</a>
</li>

<p id="read-more_1">It is one amongst the first three universities in India It was established on 18th July, 1857.</p>

<li>
The University of Mumbai has two campuses having area of 230 acres at Kalina, Santacruz (East), and 13 acres at Fort. 
<a href="#read-more_3">Read more</a>
</li>

<p id="read-more_3">Around 500 affiliated colleges and 55.</p>

<li>
Eligibility requirements, Curricular content, mode of examination and the award of degrees are on par with the colleges  affiliated to the University of Mumbai and the Departments of the University of Mumbai.
<a href="#read-more_5">Read more</a>
</li>

<p id="read-more_5">Departments of the University of Mumbai.</p>


<li>
The University of Mumbai (known earlier as University of Bombay) is one of the oldest and premier Universities in India.
<a href="#read-more_7">Read more</a>
</li>

<p id="read-more_7"> It was established in 1857 consequent upon "Wood's Education Dispatch.</p>

</ul>
 </div>
<!-- News Stiker End -->
</div>

</div>
<!-- News Box End -->

</div>
<!-- Login Container End -->

<br class="clear"/>
<div class="forgot-pass box-gradient" id="block11">
<div><a href="javascript:void(0);" onclick="HideAll();"><img src="images/Close.png" align="right" border="0" title="Close"/></a></div>
<br/>
<div class="pad12">
<div class="titleBox fl-left"><s:label id="successMsg" ></s:label></div>
</div>
<br/><br/><br/><br/>
<div align="center">
	<input type="button" value="Close" class="button-gradient" title="Close" id="btnCloseSuccessRegistration"/>
</div>
</div>