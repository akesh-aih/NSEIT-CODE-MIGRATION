<script type="text/javascript">
$(document).ready(function() { 
    	$("a:contains('Publish Details')").html('<span class="fadeSubmenu">Allocate Test Centres</span>')}); 
</script>
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="main-body">
<div class="fade" id="pop3"></div>

<div id="AgencyPublishing">

<h1 class="pageTitle" title="Allocate Test Centres"><s:text name="agencypublishdetails.title"/></h1>
<div class="hr-underline2"></div>

<!-- Box Container Start -->
<div class="AgencyPubDetail">
<s:form name="publish" method="post" action='CandidateMgmtAction'>
<s:token/>
<!--<div class="field-label"><s:text name="agencypublishdetails.activity"/>&nbsp;</div>
-->
<div class="height10"></div>
<!--<div class="hr-dashline"></div>
<div class="field-label fifty fl-left" style="padding-top:5px;"><s:text name="agencypublishdetails.admitcard"/>&nbsp;</div>
-->
<div class="fifty fl-left">
	<!-- <input type="button" class="submitBtn button-gradient" title="Generate" value="<s:text name="agencypublishdetails.generates"/>" /> -->
	<s:submit cssClass="submitBtn button-gradient" title='Generate' key='agencypublishdetails.generates' method='allocateTestCenters' />
</div>

<!-- 
<div class="clear"></div>
<div class="hr-dashline"></div>
</div>

<div>
<div class="field-label fifty fl-left"><s:text name="agencypublishdetails.scorecard"/>&nbsp;</div>
<div class="fifty fl-rigt"><input type="button" class="submitBtn button-gradient" title="Generate" value="<s:text name="agencypublishdetails.generates"/>" /></div>
<div class="clear"></div>
<div class="hr-dashline"></div>
</div>

<div>
<div class="field-label fifty fl-left"><s:text name="agencypublishdetails.meritlist"/>&nbsp;</div>
<div class="fifty fl-rigt"><input type="button" class="submitBtn button-gradient" title="Generate" value="<s:text name="agencypublishdetails.generates"/>" /></div>
<div class="clear"></div>
<div class="hr-dashline"></div>
</div>
 -->
</s:form>
</div>
<!-- Box Container End -->

</div>
</div>

