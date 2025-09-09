<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="%{#session != null}">
	<s:set var="loggedInUser" value="#session['SESSION_USER']"></s:set>
</s:if>
<s:set var="list" value="#session['parentList']" />
<s:set var="childList" value="#session['childList']" />
<s:set var="menuKey" value="#session['menuKey']" />
<s:set var="parentMenuKey" value="#request.parentMenuKey" />
<s:set var="paymentMenuKey" value="#request.paymentMenuKey" />
<s:set var="viewHallTicketMenuKey"
	value="#request.viewHallTicketMenuKey" />
<script>
	$('.mob_menu_btn').click(function() {
		$('#bs-example-navbar-collapse-1').slideToggle();
	});
</script>
<div class="col-sm-12">
	            <div class="marquee2">
					Online Application Registration for Prosthetic Craftsman 2025
					<p></p>
					</div></div>
			
<div class="subNavBg">
	<div class="container-fluid">
		<!-- Navigation Manu Start -->
		<div class="navigation">
			<s:if test="%{#loggedInUser!=null}">
				<div id="droplinemenu" class="droplinebar">
					<s:if test='%{#request[\'isInstPage\']}'></s:if>
					<s:else>
						<div class="submenu">
							<!-- <div   class="onetime pageHeader">Online Application for Computer Instructor Grade-I</div> -->
							<s:iterator value="childList" status="childRowstatus">
								<div class="submenu-item">
									<s:if test='%{#loggedInUser.userType == "C"}'>
										<s:if test="%{#menuKey == OMCM_MENU_KEY}">
											<div cssClass="SubnavActive">
												<span class="badge "> <s:property
														value='%{#childRowstatus.count}' />
												</span><br class="visible-xs" />
												<s:property value="OMCM_MENU_DESC" />
											</div>
										</s:if>
										<s:else>
											<s:if
												test="(#attr['parentMenuKey']).equals(#attr['paymentMenuKey'])">
												<span class="badge"> <s:property
														value='%{#childRowstatus.count}' />
												</span>
												<br class="visible-xs" />
												<a href="javascript:void(0)"
													onclick='menuLinks("<s:url value='%{OMCM_MENU_LINK}'/>")'>
													<s:property value="OMCM_MENU_DESC" />
												</a>
											</s:if>
											<s:elseif
												test="(#attr['parentMenuKey']).equals(#attr['viewHallTicketMenuKey'])">
												<span class="badge"> <s:property
														value='%{#childRowstatus.count}' />
												</span>
												<br class="visible-xs" />
												<a href="javascript:void(0)"
													onclick='menuLinks("<s:url value='%{OMCM_MENU_LINK}'/>")'>
													<s:property value="OMCM_MENU_DESC" />
												</a>
											</s:elseif>
											<s:elseif test="%{#menuKey > OMCM_MENU_KEY}">
												<span class="badge navActive"><span
													class="glyphicon glyphicon-ok"></span></span>
												<br class="visible-xs" />
												<a href="javascript:void(0)"
													onclick='menuLinks("<s:url value='%{OMCM_MENU_LINK}'/>")'
													cssClass="navactivetext"> <s:property
														value="OMCM_MENU_DESC" />
												</a>
											</s:elseif>
											<s:else>
												<span class="badge"> <s:property
														value='%{#childRowstatus.count}' />
												</span>
												<br class="visible-xs" />
												<s:property value="OMCM_MENU_DESC" />

											</s:else>
										</s:else>
									</s:if>
									<s:else>
										<s:if test="%{#menuKey == OMCM_MENU_KEY}">
											<div cssClass="SubnavActive">
												<s:property value="OMCM_MENU_DESC" />
											</div>
										</s:if>
										<s:else>
											<a href="javascript:void(0)"
												onclick='menuLinks("<s:url value='%{OMCM_MENU_LINK}'/>")'>
												<s:property value="OMCM_MENU_DESC" />
											</a>
										</s:else>
									</s:else>
								</div>
								<s:if test="%{#childList.size() == #childRowstatus.count}"></s:if>
								<s:else></s:else>
							</s:iterator>
						</div>
					</s:else>
				</div>
			</s:if>
		</div>
	</div>
	<div class="clear"></div>
	<s:form action="CandidateAction" id="menuForm">
		<s:token />
	</s:form>
</div>
<!-- Navigation Manu End -->