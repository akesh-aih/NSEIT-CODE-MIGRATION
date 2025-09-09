<%@ taglib prefix="s" uri="/struts-tags"%>
<script type="text/javascript">
$(document).ready(function() {
		
		
	});
		</script>	

<div class="main-body">
<div id="dashboard" style="height: 300px">

		<h1 class="pageTitle" title="Book a Seat" id = "h1">
			Approve / Reject Candidate
		</h1>
		<div class="hr-underline2"></div>
	<s:form>
		<div>
			<s:hidden id = "approvalStartDate" name="approvalStartDate"></s:hidden>
			<s:hidden id = "approvalEndDate" name="approvalEndDate"></s:hidden>
		
				<div id = "divIdReg">
					This window is available between <STRONG><s:property value="approvalStartDate" /></STRONG> and <STRONG><s:property value="approvalEndDate" /></STRONG>
				</div>
							
		</div>
	</s:form>
</div>
</div>