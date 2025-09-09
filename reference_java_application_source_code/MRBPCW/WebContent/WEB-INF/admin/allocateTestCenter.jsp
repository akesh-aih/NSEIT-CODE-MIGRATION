<script type="text/javascript">

</script>
<%@ taglib prefix="s" uri="/struts-tags"%>



<div class="fade" id="pop3"></div>
<div class="main-body">
<div class="dashboard" style="display:block; min-height:350px; height:auto;">
<!-- Box Container Start -->
<s:form name="publish" method="post" action='CandidateMgmtAction' >
<input type="hidden" name="menuKey" value='<%=request.getAttribute("menuKey")%>'/>
<br/>
<div style = "display:block; height:auto; margin-left:15px;">
	<h1 class="pageTitle" title="Allocate Test Centres"><s:text name="agencypublishdetails.title" /></h1>
</div>
<div class="hr-underline2"></div>

<div style = "display:block; height:auto; margin-left:15px;">
				Please click on Allocate Test Center button for allocating test centers for all candidates based on their preferences. 
</div>
<br/>
<div style = "margin-left:15px;">
				<s:submit cssClass="submitBtn button-gradient" title='Allocate Test Centres' key='agencypublishdetails.generates' method='allocateTestCenters' />
</div>
<br/>

<div >
<table>
	<tr>
		<td>	
			<br/>
			<br/>
		</td>
	</tr>
		<tr>
			<td align="center">
				<s:if test='%{candidateCountFlag == "true"}'>
					<table cellspacing="0" cellpadding="3" width="400" border="1" class="table_2" bordercolor="#CCCCCC" >
						<tr>
								<td width="200" >
									Total Candidates
								</td>
								<td width="200">
									<s:property value = "totalCandidateCount"/>	
								</td>
								</tr>
							<tr>
								<td width="200" >
									Allocated
								</td>
								<td width="200">
									<s:property value = "allocatedCandidateCount"/>	
								</td>
								</tr>
							<tr>
							
								<td>
									NotAllocated
								</td>
								<td>
									<s:property value = "notAllocatedCandidateCount"/>
								</td>
							</tr>
					</table>
				</s:if>
			</td>
		</tr>
	</table>
</div>


</s:form>
</div>
</div>
<!-- Box Container End -->


