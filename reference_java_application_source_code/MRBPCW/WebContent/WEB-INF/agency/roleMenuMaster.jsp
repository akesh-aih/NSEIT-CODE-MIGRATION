<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.resource.ResourceUtil"%>
<%@page import="com.nseit.generic.util.resource.ValidationMessageConstants"%>
<script src="js/jquery-3.5.1.min.js"></script>
<script type="text/javascript" src="js/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/AES.js"></script>

<script type="text/javascript">

	$(document).ready(function() {
	
		var roleType=$("#roleType").val();
		if(roleType!=0){
			getRole();	
		}
		
		var dataString = "cache=Y";
		function loadMenu(){
			$.ajax({
	        type: 'GET',
	        data: dataString,
	        url: 'servlet/StartupServlet.do',
	        error: function (err) {
        		
    		},
	        success: function (data) {
		        
	        }   	 
	    	});
    	}
    	loadMenu();
	});
	
	
	
	  function selectItemByValue(elmnt, value){

    for(var i=0; i < elmnt.options.length; i++)
    {
      if(elmnt.options[i].value == value)
        elmnt.selectedIndex = i;
    }
  }
	
	function changeAction() {
	
		var roleType=$("#roleType").val();
		var role=$("#role").val();
		var ulID = "error-ul_user";
		var divID = "error-massage_user";
		var message="";
		if(roleType==0){
			message = message + "Please select Role Type."+"##";
			
		}
		if(role==0){
			message = message + "Please select Role Name."+"##";
		}
		
		if(!$("input[type='checkbox']").is(":checked")){
			message = message + "Please select alteast one checkbox."+"##";
		}
		
		if(message != ""){
					createErrorList(message, ulID, divID); 
					$("#error-massage_user").focus();
					$('html, body').animate({ scrollTop: 0 }, 0);
					//$('html, body').animate({ scrollTop: 0 }, 'slow');
					return false;
		}else{
				
			$("#error-massage_user").hide();
			getRoleMenuMappingExists();
				
		}
	}


	function resetFieldValue() {
		$("#RoleMasterId")
				.attr('action', "RoleMasterAction_resetRole.action");
		$("#RoleMasterId").submit();
	}
		
	function selectChild(menuKey)
	{
		if($("#"+menuKey).is(":checked")){
			menuKey=menuKey+"_";
			$("input[type='checkbox'][id^='"+menuKey+"']").prop('checked', true);
		}else
		{
			menuKey=menuKey+"_";
			$("input[type='checkbox'][id^='"+menuKey+"']").prop('checked', false);
		}
	}
	
	function selectParent(parentMenuKey,menuKey)
	{
		var parent=parentMenuKey;
		parentMenuKey=parentMenuKey+"_";
		if(document.getElementById(parentMenuKey+menuKey).checked){
			$("input[type='checkbox'][id='"+parent+"']").prop('checked', true);
		}else
		{			
			if($("input[type='checkbox'][id^='"+parentMenuKey+"']").is(":checked")){
			}else
			{
				$("input[type='checkbox'][id='"+parent+"']").prop('checked', false);
			}
		}
	}
	
	function selectAllMenu()
	{
		if(document.getElementById("all").checked){
			$("input[type='checkbox']").prop('checked', true);
		}
		else
		{
			$("input[type='checkbox']").prop('checked', false);
		}
	}
	
	function menu()
	{
		var status=$("#role").val();
		var flag=false;
		if(status!=0){
			var element;
			var dataString="role="+status;
			$.ajax({
				url: "RoleMasterAction_getRoleMenu.action",
				data: dataString,
				error:function(ajaxrequest)
				{
					alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
				},
				success:function(responseText)
				{
					responseText = $.trim(responseText);
					if(responseText.length > 0)
					{
						
						element = responseText.split(",");  
						
						$.each(element, function( index) {
						  $("input[type='checkbox'][id='"+element[index]+"']").prop('checked', true);;
						});
						flag=true;
					}
				}
			});
		}else
		{
			flag=false;
		}
		
		if(!flag)
		{
			$("input[type='checkbox']").prop('checked', false);
		}
	}
	
	function getRole()
	{
	var dataString = "roleType="+$("#roleType").val();
	
		$.ajax({
				url: "RoleMasterAction_getRole.action",
				data: dataString,
				beforeSend: function()
				{
						$('#role').empty();
						$("#role").append(
						           $('<option></option>').val("0").html("Select")
						     );
					
				},
				error:function(ajaxrequest)
				{
					alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
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
							  
							  $("#role").append(
							           $('<option></option>').val(nameAndIDArr[0]).html(nameAndIDArr[1])
							     );
						 	}); 
						}
					}
				},
				complete:function()
				{
					var role=0;
						role='<s:property value="role"/>';
						if(role!=0){
							$("#role").val(role);
							menu();
						}
				}
			});
	}
	
	
	function getRoleMenuMappingExists()
	{
		var role=$("#role").val();
		
		if(role!=0){
			var element;
			var dataString="role="+role;
			$.ajax({
				url: "RoleMasterAction_getRoleMenuMappingExists.action",
				data: dataString,
				error:function(ajaxrequest)
				{
					alert('Error refreshing. Server Response: '+ajaxrequest.responseText);
				},
				success:function(responseText)
				{
					responseText = $.trim(responseText);
					if(responseText.length > 0)
					{
						if(responseText==0)
						{
							$("#RoleMasterId").attr('action',"RoleMasterAction_addRoleMenu.action");
							$("#RoleMasterId").submit();
						
						}else
						{
							if (confirm("Are you sure you want to modify the role menu mapping?") == true) {
								$("#RoleMasterId").attr('action',"RoleMasterAction_addRoleMenu.action");
								$("#RoleMasterId").submit();
							}
						}
					}
				}
			});
		}
	}
	
</script>

<script type="text/javascript" src="js/jquery.min.js" ></script>
<script type="text/javascript" src="js/accordion.js" ></script>
 
  <style>
  .onetime{
  	display: none;
  }
  .container .container { width:100%; float:left;   }
  </style>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="container">
<div class="container common_dashboard">
<div class="main-body">
	<div class="fade" id="pop3"></div>

	<div id="SelectTest">

		<h1 class="pageTitle" title="Role Menu Master">
			Role Menu Master
		</h1>
		<div class="hr-underline2"></div>

		<!-- Box Container Start -->


		<div class="clear">
			<s:form action="RoleMasterAction" id="RoleMasterId"
				name="RoleMasterId">
				<div id="message" style="border: none">
<s:if test='%{showModuleDetails == "true"}'>
	<span style="color:green"><strong><s:property value="sucMsg"/></strong></span>
</s:if>

</div>
				<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
				<div id="error-massage_user" style="display: none"
					class="error-massage">
					<div class="error-massage-text"
						style="margin: 0;   margin-top: 30px; padding: 0;">
						<ul style="margin: 0; margin-left: 20px; padding: 0;"
							id="error-ul_user">
						</ul>
					</div>
				</div>
				<table width="100%" align="left" class="contenttableNew" cellspacing="1" cellpadding="3" >
					<tr>
						<td class="field-label" width="15%">
							Role Type <span class="manadetory-fields">*</span>
						</td>
						<td>

							<s:select name="roleType" id="roleType" headerKey="0"
								headerValue="Select" list="#{'S':'Admin'}"
								onchange="getRole()"/>
						</td>
					</tr>
					<tr>
						<td class="field-label">
							Role Name <span class="manadetory-fields">*</span>
						</td>
						<td>

							<s:select name="role" id="role" list="#{'0':'Select'}"
							onchange="menu()"/>
						</td>
					</tr>
				</table>
				<div class="clear"></div>


				<!-- Row Four End -->




<!--			<div class="height20"></div>-->
			<s:if test="%{showGrid=='true'}">
				<!-- Dash Line Start -->
<!--				<div class="clear hr-dashline"></div>-->
				<!-- Dash Line End -->




 





				<div class="AgencyPayDrid" id="gridDiv1">
					
						<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
						<s:hidden name="syllabusValue"  />
						<s:hidden name="status"  />
						<br />
						<div class="accordion">
							<div class="accordion-section" >
							<div style="display:inline-block; background:#fff; float:left; height:34px; line-height:20px; verticle-align:middle;  border-top:1px solid #e0e0e0;    padding:8px 4px 0px 4px; color:#7c0b03; " >
									<input type="checkbox" id="all" onclick="selectAllMenu()"/>
							</div> 
								<a class="accordion-section-title" href="javascript:void(0)" style="background:#fff; color:#7c0b03; font-weight:bold;">Menu Name</a>
							</div>
				<s:iterator value="parentMenuDetailsList" status="stat" var="currentObject">
							<div class="accordion-section">
								<div style="display:inline-block; background:#fff; float:left; height:20px; line-height:21px; verticle-align:middle;  border-top:1px solid #e0e0e0; padding:8px 4px 0px 4px; " >
									<s:checkbox name="parentMenuDetailsList[%{#stat.index}].menu" fieldValue="%{menuMasterPk}" id='%{menuKey}' onclick='selectChild(%{menuKey})'/>
								</div> 
								<a class="accordion-section-title" href='#accordion-<s:property value="#stat.count"/>'>
									<s:hidden name="parentMenuDetailsList[%{#stat.index}].menuKey" value="%{menuKey}"/><s:property value="displayName" />
								</a>
								<s:if test="%{#stat.count != 1}">
								<div id='accordion-<s:property value="#stat.count"/>' class="accordion-section-content">
									<table>
										<s:iterator value="childMenuDetailsList" status="childstat">
															
											<s:if test="%{OMCM_PARENT_MENU_KEY == menuKey}">
												<tr>
													<td>
														<s:checkbox name="childMenuDetailsList[%{#childstat.index}].menu" fieldValue="%{OMCM_MENU_PK}" id='%{OMCM_PARENT_MENU_KEY}_%{OMCM_MENU_KEY}' onclick='selectParent(%{OMCM_PARENT_MENU_KEY},"%{OMCM_MENU_KEY}")'/>
													</td>
													<td>
														<s:property value="OMCM_MENU_DESC" />
													</td>
												</tr>
											</s:if>
											
										</s:iterator>
									</table>
								</div><!--end .accordion-section-content-->
								</s:if>
							</div><!--end .accordion-section-->
				</s:iterator>
<!--			<div class="accordion-section">-->
<!--				<a class="accordion-section-title" href="#accordion-2">Accordion Section #2</a>-->
<!--				<div id="accordion-2" class="accordion-section-content">-->
<!--					<p>Mauris interdum fringilla augue vitae tincidunt. Curabitur vitae tortor id eros euismod ultrices. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent nulla mi, rutrum ut feugiat at, vestibulum ut neque? Cras tincidunt enim vel aliquet facilisis. Duis congue ullamcorper vehicula. Proin nunc lacus, semper sit amet elit sit amet, aliquet pulvinar erat. Nunc pretium quis sapien eu rhoncus. Suspendisse ornare gravida mi, et placerat tellus tempor vitae.</p>-->
<!--				</div>end .accordion-section-content-->
<!--			</div>end .accordion-section-->

<!--			<div class="accordion-section">-->
<!--				<a class="accordion-section-title" href="#accordion-3">Accordion Section #3</a>-->
<!--				<div id="accordion-3" class="accordion-section-content">-->
<!--					<p>Mauris interdum fringilla augue vitae tincidunt. Curabitur vitae tortor id eros euismod ultrices. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Praesent nulla mi, rutrum ut feugiat at, vestibulum ut neque? Cras tincidunt enim vel aliquet facilisis. Duis congue ullamcorper vehicula. Proin nunc lacus, semper sit amet elit sit amet, aliquet pulvinar erat. Nunc pretium quis sapien eu rhoncus. Suspendisse ornare gravida mi, et placerat tellus tempor vitae.</p>-->
<!--				</div>end .accordion-section-content-->
<!--			</div>end .accordion-section-->
		</div><!--end .accordion-->
		
		
						<table width="50%" style="display:none;">
							<tr class="box-header">
								<td align="left" width="30%">
									Role Menu Details
								</td>
								<td align="center" width="30%" class="pagination-label">
									Total
									<font color="red"><s:property value="totalRecord" />
									</font> Records Found
								</td>
								<td width="40%">
								</td>
							</tr>
						</table>

						<br />
						
						<div>
							<table>
								<tr>
								 
									<td >
										<input type="button" value="Submit"
											class="submitBtn button-gradient" onclick="changeAction();" />
									</td>
									<td  style="display:none;">
										<input type="reset" name="button2" id="button2" value="Reset"
											class="submitBtn button-gradient"
											onclick="resetFieldValue();" />
									
								</tr>
							</table>


						</div>
					
				</div>
			</s:if>
			<s:token />
			</s:form>
		</div>
	</div>
</div>
</div></div>

