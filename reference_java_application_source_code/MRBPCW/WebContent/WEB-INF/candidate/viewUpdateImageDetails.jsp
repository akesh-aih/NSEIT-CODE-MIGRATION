<%@ taglib prefix="s" uri="/struts-tags"%>
<%
   response.setHeader( "Pragma", "no-cache" );
   response.setHeader( "Cache-Control", "no-cache" );
   response.setDateHeader( "Expires", 0 );
%>

<script type="text/javascript">

	$(document).ready(function(){

		$("#btnEditImage").click(function() {
			updateImages();
		});
		
		$("#btnImageDetailsNext").click(function() {
			updateImages();
		});

		$("#btnImageDetailsPrev").click(function() {

			$("#frmImageUpload").validationEngine('hide');
			var index = ($("#hImageDetailsDivInd").val()*1);
			index = index - 1;
			btnBack_Click(index);
		});

		$("#frmImageUpload").validationEngine({promptPosition : "bottomLeft", scroll: false});
	});

	function updateImages()
	{
		$("#frmImageUpload").validationEngine('hide');
		var dataString = "beanpropertieshere";
		var index = ($("#hImageDetailsDivInd").val()*1);
		
		$.ajax({
			url: "EnrollmentAction_updateCandidateImages.action",
			async: true,
			data: dataString,
			beforeSend: function()
			{
				
			},
			error:function(ajaxrequest)
			{
				alert('Error registering user. Server Response: '+ajaxrequest.responseText);
			},
			success:function(responseText)
			{
				responseText = $.trim(responseText);
				
				if(responseText.length > 0)
				{
					if(responseText.charAt(0) == "0")
					{
						if($("#actionNameImage").val() == "preview" || $("#actionNameImage").val() == "view")
						{
							submitPersonalDetailsForPreview();
						}
						else
						{
							index = index + 1;
							changeTabsTestEnrollments(index);
						}
					}
					else
					{
						alert("Please upload photograph/signature image then click on next.");
						return false;
					}
				}
			}
		});
	}

	function uploadImage(){
		//alert($("#uploadType").val());
		$("#uploadType").val("Image");
		//alert($("#uploadType").val());
		ajaxFileUpload("attachmentPhoto");
	}
	function uploadSign(){
		//alert($("#uploadType").val());
		$("#uploadType").val("Sign");
		//alert($("#uploadType").val());
		ajaxFileUpload("attachmentSignature");
	}

	function ajaxFileUpload(fileToUpload)
	{
		
		if($('#frmImageUpload').validationEngine('validateField', '#'+fileToUpload)==false)
		{
			$("#frmImageUpload").validationEngine('detach');
			$("#frmImageUpload").ajaxForm({
				target: '#imageError'
			}).submit();
			$("#frmImageUpload").validationEngine({promptPosition : "centerRight", scroll: false});
		}
	}

	function refreshPhoto()
	{
		$("#inputStreamForImage").removeAttr("src").attr("src", "EnrollmentAction_showAttachmt.action");
	}

	function refreshSign()
	{
		$("#inputStreamForSignature").removeAttr("src").attr("src", "EnrollmentAction_showSignature.action");
	}
	

	
</script>


<form method="post" name="register" action="EnrollmentAction_insertCandidateImagesAndSignature.action" enctype="multipart/form-data" id="frmImageUpload" >

<input type="hidden" id="hImageDetailsDivInd" value="4" />
<s:hidden name = "uploadType" id="uploadType" value="yyyyy" />
<s:hidden name="actionNameImage" id="actionNameImage" value="%{actionName}"></s:hidden>

<s:if test="%{actionName==null || actionName==''}">
	<div id="TabDiv4" class="tab_content" style="display:none">
</s:if>
<s:elseif test="%{actionName=='preview' || actionName=='view'}">
	<div id="TabDiv4" class="tab_content" style="display:block">
</s:elseif>

<div class="errorMessageActive" id="imageError">		
	
</div>
<div class="field-label"><s:text name="viewupdateimagedetails.uploadimages"/></div>
<div class="hr-dashline"></div>

<!-- Row One Start -->
<div class="padleft40 clear">
<div class="fl-left fifty">
<div class="field-label"><s:text name="viewupdateimagedetails.uploadphotograph"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div class="height10"></div>

<span class="uploadImg">
 <img width="200" height="150" src="PhotoImage.jpg" name="inputStreamForImage" id="inputStreamForImage"  />
</span>

<div><s:file name="attachmentPhoto" id="attachmentPhoto" label="Attachment File" cssClass="BrowseFile validate[required,custom[validImage]] enrollFinal"  size="25"/></div>
<div class="uploadIns"><s:text name="viewupdateimagedetails.uploadimagesinformat"/> <s:text name="viewupdateimagedetails.filesize"/></div>



<div id="UpErr1" class="errorMessage"><s:text name="viewupdateimagedetails.validphotoerrorlabel"/></div>
<!-- for validation message -->
<br/>
<br/>
<div class="height5"></div>
<div>
	<!-- 
	<s:submit type="button" title="Upload" cssClass="submitBtn button-gradient" value="Upload Photo" method="insertCandidateImagesAndSignature" onclick="uploadImage();"></s:submit>
	 -->
	<input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload Photo" onclick="uploadImage();" />
	<input type="button" title="Refresh" class="submitBtn button-gradient enrollFinal" value="View Photo" onclick="refreshPhoto();" />	
</div>

<!--
<div class="height20"></div>
<div><input type="submit" title="Save" class="submitBtn button-gradient" value="Save"/></div>-->
</div>

<div class="fl-rigt fifty">
<div class="field-label"><s:text name="viewupdateimagedetails.uploadsignature"/>&nbsp;<span class="manadetory-fields">*</span></div>
<div class="height10"></div>
<span class="uploadImg" >

<img width="200" height="150" src="SignatureImage.jpg"  name="inputStreamForSignature" id="inputStreamForSignature" />
</span>
<div><s:file name="attachmentSignature" id="attachmentSignature" label="Attachment Signature " cssClass="BrowseFile validate[required,custom[validImage]] enrollFinal"  size="25"/> </div>
<div class="uploadIns"><s:text name="viewupdateimagedetails.uploadimagesinformat"/> <s:text name="viewupdateimagedetails.filesize"/>
<br/>
</div> 
<div id="UpErr2" class="errorMessage"><s:text name="viewupdateimagedetails.validsignatureerrorlabel"/></div>

<!-- for validation message -->
<br/>
<br/>
<div class="height5"></div>
<div>
	<!-- <s:submit type="button" title="Upload" cssClass="submitBtn button-gradient" value="Upload Signature" method="insertCandidateImagesAndSignature" onclick="uploadSign();"></s:submit> -->
	
	<input type="button" title="Upload" class="submitBtn button-gradient enrollFinal" value="Upload Signature" onclick="uploadSign();" />
	
	<input type="button" title="Refresh" class="submitBtn button-gradient enrollFinal" value="View Signature" onclick="refreshSign();" />
</div>

<!--
<div class="height20"></div>
<div><input type="submit" title="Save" class="submitBtn button-gradient" value="Save"/></div>-->
</div>
</div>
<!-- Row One End -->

<div class="height20 clear"></div>
<div class="height20 clear"></div>
<!-- Row Two Start -->
<div class="clear">

<s:if test="%{actionName=='' || actionName == null}">
	<input type="button" title="Save" class="submitBtn button-gradient enrollFinal" value="<s:text name="viewupdateimagedetails.back"/>" id="btnImageDetailsPrev"/>&nbsp;&nbsp;
<input type="button" title="Save" class="submitBtn button-gradient cancelBtn" value="<s:text name="viewupdateimagedetails.cancel"/>"/>&nbsp;&nbsp;
<input type="button" title="next" class="submitBtn button-gradient enrollFinal" value="<s:text name="viewupdateimagedetails.next"/>" id="btnImageDetailsNext"/>
</s:if>
<s:elseif test="%{actionName=='preview' || actionName=='view'}">
	<input type="button" value="<s:text name="viewupdatepersonalinfo.update"/>" class="submitBtn button-gradient" id="btnEditImage"/>
</s:elseif>
</div>
<!-- Row Two End -->

</div>
</form>