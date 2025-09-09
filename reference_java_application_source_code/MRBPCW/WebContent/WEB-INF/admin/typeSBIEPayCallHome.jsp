<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script type="text/javascript" src="js/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/AES.js"></script>
<script type="text/javascript">

	function changeActionBulk() {
		
		$("#SBIePayBulk").attr('action', "SBIEPayResponseAction_getDoubleVerificationResponseBulk.action");
		$("#SBIePayBulk").submit();
		
	}
	
	function changeActionSingle1(){

		var message = "";
		var taxnNumber = $.trim($("#transactionNumber").val());
		if(taxnNumber == "")
		{
			message = message + "Please enter User ID."+"";
			alert(message);
		}
		else
		{
			dataString = "transactionNumber="+taxnNumber;
			$.ajax({
				url: "SBIEPayResponseAction_connectToSBIEPayDoubleVerification.action",
				async: false,
				data: dataString,
				error:function(ajaxrequest)
				{
					alert('Error checking latest payment details: '+ajaxrequest.responseText);
				},
				success:function(responseText)
				{
					//alert("responseText:["+responseText+"]"); 
					responseText = $.trim(responseText);
					if(responseText != null && responseText != "")
					{
						
						if(responseText != "" && responseText == "TXNNONOTFOUND" ){

							alert("Pending Payment Transaction Not Found against USER ID : "+taxnNumber);
						}
						else{

							if(responseText != "" && responseText == "INVDUSERID"){

								alert("Please enter Valid User ID.");
							}
							else{

								$("#encryptQuery").val($.trim(responseText));
								
								<%String merchantId = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MERCHANT_ID);%>
								var merchantIdVal = '<%=merchantId%>';
								//$("#merchIdVal").val("1000003");
								$("#merchIdVal").val(merchantIdVal);
								
								<%String str = ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.SBIDOUBLEVERIFY_URL);%>
								var request_url = '<%=str%>';
								// request_url = https://test.sbiepay.com/secure/AggMerchantStatusQueryAction
								if(request_url != ""  )
								{
									$("#SBIePaySingle").attr('action',request_url);
									$("#SBIePaySingle").submit();
								}
								else{
										alert("Payment Process Issues,Please try again later on!!");
									}

								}
						}
					}
					else{
						alert("Please enter Valid User ID.");
					}
					
				}//success
				});
		}//else
				
	}
		
function resetPayMtSearch()
{
	//$("#error-massage_user").hide();
	//$("#error-ul_user").hide();
	$("#responseDiv").hide();
	//$("#actionMsgError").hide();
	$("#transactionNumber").val("");
}

function alphaNumeric(e)
{
	var unicode=e.charCode? e.charCode : e.keyCode
	//alert("unicode: "+unicode);	
	if((unicode < 97 || unicode > 122 ) && (unicode < 65 || unicode > 90) && (unicode<48||unicode>57) && unicode != 8 && unicode != 9 )
		return false;
}

</script>
<style>
.onetime{
  	display: none;
 }
.error-massage { 
	outline:none;
}
</style>


<div class="container"> 
<div class="container common_dashboard">

<div class="main-body" id="dashboard">
	<div class="fade" id="pop3"></div>

	 <div id="SelectBulk">

		<h1 class="pageTitle" title="SBIePay Double Verification Bulk">
			SBIePay Double Verification Bulk
		</h1>
		<div class="hr-underline2"></div>

		<div class="clear">
			<s:form action="SBIEPayResponseAction" id="SBIePayBulk">
			<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
			<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
				<div id="message" style="border: none">
					<s:if test='%{showModuleDetails == "true"}'>
						<span style="color:green"><strong><s:property value="sucMsg"/></strong></span>
					</s:if>
				</div>
   				<s:if test="errMsg!=null">
	 				<div id="error-massage_user" class="error-massage">
			      		<div class="error-massage-text">
			      			<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul_user">
			      			<s:property value="errMsg" escape="false"/>
			      			</ul>
			      	 	</div>
	      			</div>
				</s:if>
				<br/>
				
					<input type="button" value="Generate Offline Double Verification Bulk " title="Generate Offline Double Verification Bulk "
						class="submitBtn button-gradient"
						onclick="changeActionBulk();" />
					
				<s:token />
			</s:form>
		</div>
	</div>
	
	<br/><br/><br/>
	
	<div id="SelectSingle">

		<h1 class="pageTitle" title="SBIePay Double Verification Single">
			SBIePay Double Verification Single
		</h1>
		<div class="hr-underline2"></div>

		<div class="clear">
			<s:form  id="SBIePaySingle" name="SBIePaySingle"  method="post">
			<!--<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>-->
			<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
			
			<input type="hidden" id = "encryptQuery" name="encryptQuery" value="">
			<input type="hidden" id = "merchIdVal"   name="merchIdVal" value =""/>
			<input type="hidden" name="aggIdVal" value ="SBIEPAY"/>
			
				<div id="message" style="border: none">
					<s:if test='%{showModuleDetails == "true"}'>
						<span style="color:green"><strong><s:property value="sucMsg"/></strong></span>
					</s:if>
				</div>
   				<s:if test="errMsgSingle!=null">
	 				<div id="error-massage_user" class="error-massage">
			      		<div class="error-massage-text">
			      			<ul style="margin:0; margin-left:20px; padding:0;" id="error-ul_user">
			      			<s:property value="errMsgSingle" escape="false"/>
			      			</ul>
			      	 	</div>
	      			</div>
				</s:if>
				<br/>
				<table width="100%">
					<tr>
						<td width="15%">
							User ID :
						</td>
						<td>
							<s:textfield name="transactionNumber" id="transactionNumber" value="%{transactionNumber}" size="25" onkeypress="return alphaNumeric(event);" maxlength="11"></s:textfield>
						</td>
					</tr>
					<tr>
						<td colspan="2">&nbsp;</td>
					</tr>
					<tr>
					<td>&nbsp;</td>
						<td>
							<input type="button" value="Generate Single SBIePay Double Verification " title="Generate Single SBIePay Double Verification "
							class="submitBtn button-gradient"
							onclick="changeActionSingle();" />
						 	&nbsp; 
							<input type="button" value="Clear" title="Clear" class="submitBtn button-gradient" onclick="resetPayMtSearch();"/>
						</td>
							
					</tr>
					
				</table>
			</s:form>
		</div>
	</div>
	<br/>
	<s:if test='responseOFlag=="true"'>
		<div id="responseDiv">
			<br />
			<table cellspacing="0" border="1" width="100%" class="table_2" bordercolor="#CCC" >
			<thead>
				<tr>
					<th style="width: 4%" align="left">
						Status Code
					</th>
					<th style="width: 12%" align="left">
						Txn Message
					</th>
					<th style="width: 35%" align="left">
						Message Description
					</th>
					<th style="width:10%" align="left">
						User ID
					</th>
					<th style="width:10%" align="left">
						 Txn Number
					</th>
					<th style="width:4%" align="left">
						Amount
					</th>
					<th style="width: 10%" align="left">
						Txn Date and Time
					</th>
				</tr>
			</thead>
			<tbody>
			
			<s:iterator value="responseDetailsList" status="stat" var="currentObject">
				<tr>
					<td align="left">
						<s:property value="authStatus" />
					</td>
					<td align="left">
						<s:property value="transactionMessage" />
					</td>
					<td align="left">
						<s:property value="errorDEsc" />
					</td>
					<td align="left">
						<s:property value="customerId" />
					</td>
					<td align="left">
						<s:property value="txnReferenceNo" />
					</td>
					<td align="left">
						<s:property value="txnAmount" />
					</td>
					<td align="left">
						<s:property value="txnDate" />
					</td>
				</tr>
			</s:iterator>
			</tbody>
			</table>
		</div>
	</s:if>
</div>


</div>
</div>

