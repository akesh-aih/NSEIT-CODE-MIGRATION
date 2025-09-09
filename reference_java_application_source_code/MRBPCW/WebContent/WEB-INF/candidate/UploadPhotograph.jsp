<%@page import="com.nseit.generic.models.CandidateBean"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@page import="com.nseit.generic.util.ConfigurationConstants"%>
<%@page import="com.nseit.generic.util.GenericConstants"%>
<link rel="stylesheet" href="css/font-awesome.min.css">
<script type="text/javascript" src="js/q.js"></script>
<script type="text/javascript" src="js/spark-md5.min.js"></script>

<script type="text/javascript">
	document.onreadystatechange = function() {
		var state = document.readyState;
		enableLoadingAnimation();
		if (state == 'complete') {
			disabledLoadingAnimation();
		}
	}

	var hashVal = null;
	var met = null;

	function calculateMD5Hash(file, bufferSize, method) {
		var def = Q.defer();
		var fileReader = new FileReader();
		var fileSlicer = File.prototype.slice || File.prototype.mozSlice || File.prototype.webkitSlice;
		var hashAlgorithm = null;
		var totalParts = Math.ceil(file.size / bufferSize);
		var currentPart = 0;
		var startTime = new Date().getTime();

		fileReader.onload = function(e) {
			var data = null;
			if (!e) {
				data = fileReader.content;
			} else {
				data = e.target.result;
			}
			currentPart += 1;

			def.notify({
				currentPart : currentPart,
				totalParts : totalParts
			});
			
			hashAlgorithm = new SparkMD5();
			hashAlgorithm.appendBinary(data);

			if (currentPart < totalParts) {
				processNextPart();
				return;
			}
			
			hashVal = hashAlgorithm.end();
			met = method;
			def.resolve({
				hashResult : "null",
				duration : new Date().getTime() - startTime
			});
		};

		fileReader.onerror = function(e) {
			def.reject(e);
		};

		function processNextPart() {
			var start = currentPart * bufferSize;
			var end = Math.min(start + bufferSize, file.size);
			fileReader.readAsBinaryString(fileSlicer.call(file, start, end));
		}

		processNextPart();
		return def.promise;
	}

	function calculate(input, method) {
		if (!input.files.length) {
			return;
		}

		var file = input.files[0];
		var bufferSize = Math.pow(1024, 2) * 10; // 10MB

		calculateMD5Hash(file, bufferSize, method).then(
			function(result) {
				// Success
			},
			function(err) {
				// There was an error,
			},
			function(progress) {
				// We get notified of the progress as it is executed
				if (met == "uploadImage") {
					document.getElementById("attachementPhotoHash").value = hashVal;
				}
				document.register.action = "CandidateAction_insertCandidateImage.action";
				enableLoadingAnimation();
				document.register.submit();
			});
	}

	if (!FileReader.prototype.readAsBinaryString) {
		FileReader.prototype.readAsBinaryString = function(fileData) {
			var binary = "";
			var pt = this;
			var reader = new FileReader();
			reader.onload = function(e) {
				var bytes = new Uint8Array(reader.result);
				var length = bytes.byteLength;
				for (var i = 0; i < length; i++) {
					binary += String.fromCharCode(bytes[i]);
				}
				// pt.result - readonly so assign binary
				pt.content = binary;
				pt.onload();
			}
			reader.readAsArrayBuffer(fileData);
		}
	}

	function uploadImage() {
		var attachmentPhoto = $("#attachmentPhoto").val();

		if (attachmentPhoto == "") {
			var message = "Please select file to upload.";
			var ulID = "error-ulUploadForm";
			var divID = "error-massageUploadForm";
			createErrorList(message, ulID, divID);
			$('html, body').animate({
				scrollTop : 0
			}, 0);
			return false;
		
		} else {
			var t = attachmentPhoto.split('.');

			var extn = t[t.length - 1];

			var lowrCaseExtn = extn.toLowerCase();

			if (lowrCaseExtn == "jpg" || lowrCaseExtn == "png" || lowrCaseExtn == "jpeg") {
				
				var bufferSize = Math.pow(1024, 2) * 0.06; // 60 KB
				var bufferSizeMin = Math.pow(1024, 2) * 0.02; // 20 KB
				var fileSize = $("#attachmentPhoto")[0].files[0].size;

				var fileSizeNum = parseInt(fileSize);
				var bufferSizeNum = parseInt(bufferSize);
				var bufferSizeMinNum = parseInt(bufferSizeMin);
				
				if (bufferSizeNum < fileSizeNum) {
					message = "Photo size exceeds 60 KB";
					var ulID = "error-ulUploadForm";
					var divID = "error-massageUploadForm";
					createErrorList(message, ulID, divID);
					$('html, body').animate({
						scrollTop : 0
					}, 0);
					$("#actionMsgPhoto").hide();
					return false;
					
				} else if (bufferSizeMinNum > fileSizeNum) {
					message = "Minimum photo size should be 20 KB";
					var ulID = "error-ulUploadForm";
					var divID = "error-massageUploadForm";
					createErrorList(message, ulID, divID);
					$('html, body').animate({
						scrollTop : 0
					}, 0);
					$("#actionMsgPhoto").hide();
					return false;
					
				} else {
					calculate(document.getElementById('attachmentPhoto'), "uploadImage");
					$("#error-massageUploadForm").hide();
				}

			} else {
				message = "Invalid Image Format.";
				var ulID = "error-ulUploadForm";
				var divID = "error-massageUploadForm";
				createErrorList(message, ulID, divID);
				$('html, body').animate({
					scrollTop : 0
				}, 0);
				$("#actionMsgPhoto").hide();
				return false;
			}
		}
	}

	function checkForImageChange() {
		try {
			var responseDiv = $.trim($("#divImageUploadResponse").html());

			if (responseDiv != "") {
				responseDiv = responseDiv.replace("<PRE>", "").replace("</PRE>", "");
				responseDiv = responseDiv.replace("<pre>", "").replace("</pre>", "");
				responseDiv = $.trim(responseDiv);
				responseDiv = responseDiv.substring(responseDiv.indexOf("(message)") + 9, responseDiv.indexOf("(/message)"));

				$("#divImageUploadResponse").html("");
				var responseArr = responseDiv.split(",");
				if (responseArr[0] == "0") {
					var timestamp = new Date().getTime();
					$("#imageTd").fadeOut(0);
					var imageHtml = '<img width="200" height="150" src="PhotoImage.jpg?t=' + timestamp + '" name="inputStreamForImage" id="inputStreamForImage"/>';
					$("#imageTd").html(imageHtml);
					$("#imageTd").fadeIn(1000);
					$("#uploadedImage").show();
				} else if (responseArr[0] == "1") {
					$("#test").show();
				}
			}
		} catch (ex) {

		}
		setTimeout("checkForImageChange();", 200);
	}

	function onErrorMessage(id) {
		$("#" + id).hide();
	}

	$(document).ready(function() {
		jQuery(document).bind("keyup keydown", function(e) {
			if (e.ctrlKey && e.keyCode == 80) {
				return false;
			}
		});

		$('#block9').hide();
		setTimeout("checkForImageChange();", 2000);
		
		if ($("#imageUploaded").val() == "true") {
			$("#uploadedImage").show();
		} else {
			$("#uploadedImage").hide();
		}

		function loadImage() {
			$.ajax({
				type : 'GET',
				contentType : 'application/json; charset=utf-8',
				dataType : 'json',
				timeout : 10000,
				url : 'PhotoImage.jpg',
				error : function(err) {
					var results = err.responseText;
					// the results is a base64 string. convert it to an image and assign as 'src'
					document.getElementById("inputStreamForImage").src = "data:image/png;base64," + results;
				},
				success : function(data) {
					var results = data;
					// the results is a base64 string. convert it to an image and assign as 'src'
					document.getElementById("inputStreamForImage").src = "data:image/png;base64," + results;
				}
			});
		}
		// loadImage();
	});

	function checkuploaded() {
		$.ajax({
			url : "CandidateAction_checkPhotoExist.action",
			async : true,
			type : 'POST',

			error : function(ajaxrequest) {
				/* console.log("error occured"); */
			},
			success : function(responseText) {

				responseText = $.trim(responseText);
				/* console.log("succes"); */
				if (responseText != null && responseText == "Success" && responseText != "") {
					document.register.action = "CandidateAction_updateCandidateStage.action";
					enableLoadingAnimation();
					document.register.submit();
					return true;
				} else {
					return false;
				}
			}
		});
	}
</script>
<style>
.msgg li:first-child {
	list-style: none;
}

.personsl-dtl td, table td {
	padding: 4px !important;
}
</style>

<label id="divImageUploadResponse" style="display: none;"></label>
<div class="titlebg container">
	<h1 class="pageTitle">
		<strong> Upload Photo <s:text name="uploadPhoto.uploadPhoto" /></strong>
		<span class="userid_txt"> <s:if
				test="%{#session['SESSION_USER'] != null}">
				<strong>User ID <s:text name="applicationForm.userId" />
				</strong> -	<s:label value="%{#session['SESSION_USER'].username}" />
			</s:if>
		</span>
	</h1>
</div>
<body>
	<div id="dashboard">
		<s:form method="post" name="register" action="CandidateAction"
			enctype="multipart/form-data" id="frmImageUpload">
			<div class="padding_leftright">
				<div class="container common_dashboard">
					<div class="container">
						<s:actionmessage escape="false" cssClass="msgg"
							id="actionMsgPhoto" />
						<div id="error-massageUploadForm" style="display: none"
							class="error-massage">
							<div class="error-massage-text"
								style="margin: 0; margin-left: -12px; padding: 0;">
								<ul id="error-ulUploadForm"
									style="margin: 1; margin-left: 35px; padding: 2;">
								</ul>
							</div>
						</div>

						<div class="row">
							<div class="col-sm-12">
								<%-- <s:if test="%{#attr.dataNotFound!=''}"> --%>
								<div class="error-massage-text">
									<s:property value="#attr.dataNotFound" />
								</div>
								<%-- </s:if> --%>
							</div>
						</div>
					</div>
					<s:hidden name="imageUploaded" id="imageUploaded" />
					<s:hidden name="menuKey" value="%{#session['menuKey']}"></s:hidden>
					<s:hidden name="isDataFound" value='<s:property value="imageUploaded"/>' />
						
					<div style="display: block;">
						<div class="row uploadTable">
							<div class="col-sm-9 col-xs-12 ViewpageLabel">
								<div class="row">
									<div class="col-sm-6 col-xs-6">
										<div class="form-group">
											<label class="control-label">User ID <s:text
													name="reference" /></label>
											<div class="clear"></div>
											<s:label value="%{#session['SESSION_USER'].username}"
												cssClass="form-control"></s:label>
										</div>
									</div>
									<div class="col-sm-6 col-xs-6">
										<div class="form-group">
											<label class="control-label">Name <s:text
													name="applicationForm.applicantFullName" /></label>
											<div class="clear"></div>
											<s:label cssClass="form-control wordWrap"
												value="%{personalDetailsBean.candidateFirstName}"></s:label>
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-6 col-xs-6">
										<div class="form-group">
											<label class="control-label">Gender <s:text
													name="applicationForm.gender" />
											</label>
											<div class="clear"></div>
											<s:label value="%{genderValDesc}" cssClass="form-control" />
										</div>
									</div>
									<div class="col-sm-6 col-xs-6">
										<div class="form-group">
											<label class="control-label">Date of Birth <s:text
													name="applicationForm.dateOfBirth" />
											</label>
											<div class="clear"></div>
											<s:label value="%{personalDetailsBean.dateOfBirth}"
												cssClass="form-control" />
										</div>
									</div>
								</div>
							</div>
							<div class="col-sm-3 col-xs-12 text-center">
								<div class="row">
									<div class="col-sm-12">
										<div class="form-group">
											<label class="control-label">Photo Image <s:text
													name="uploadPhoto.image" /></label>
										</div>
									</div>
								</div>
								<div id='block9' style="display: block;">
									<img src="images/Loading.gif" width="66" height="78"
										alt="GCET " />
								</div>
								<div class="row" id="imageTd" style="min-height: 90px;">
									<div id="uploadedImage" class="col-sm-12">
										<img height="150" width="100%" src="PhotoImage.jpg"
											name="inputStreamForImage" id="inputStreamForImage"
											onerror="onErrorMessage('inputStreamForImage')"
											class="img-responsive" border="1" />
									</div>
								</div>
								<div class="row mt10">
									<div class="col-sm-12">
										<s:file name="attachmentPhoto" id="attachmentPhoto"
											label="Attachment Signature" />
										<s:hidden name="attachementPhotoHash"
											id="attachementPhotoHash" />
									</div>
								</div>
							</div>
						</div>
						<div class="row mt10">
							<div class="col-xs-12 visible-xs">
								<input type="button" class="btn btn-warning btn-sm enrollFinal"
									value="Upload" onClick="uploadImage()" />
							</div>
							<div class="col-sm-9 mt10 col-xs-12">
								<p class="note">
									Click on Choose file and select photo file to be uploaded from
									your machine. After selecting your photo, please click on
									Upload button.
									<s:text name="photoNote1" />
								</p>
							</div>
							<div class="col-sm-3 text-center col-xs-12 hidden-xs">
								<input type="button" class="btn btn-warning btn-sm enrollFinal"
									value="Upload" onClick="uploadImage()" />
							</div>
						</div>
						<div class="row">
							<div class="col-sm-12 mt10">
								<strong> <s:text name="uploadPhoto.instruction1" />
								</strong>
								<ul class="note ml20">
									<li>The minimum file size is <%=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MIN_IMAGE_SIZE_IN_KB)%>
										KB, Maximum file size is <%=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.MAX_IMAGE_SIZE_IN_KB)%>
										KB and <%=ConfigurationConstants.getInstance().getPropertyVal(GenericConstants.IMAGE_TYPE).toLowerCase()%>
										file images are accepted. <s:text name="photoNote2" />
									</li>
									<li>Please upload recent photograph, not older than six
										months. <s:text name="photoNote3" />
									</li>
									<li>Kindly upload clear photo image, if photo image upload
										is not clear then your application is liable to rejection by
										board. <s:text name="photoNote4" />
									</li>
								</ul>
								<s:hidden name="auditFlag" id="auditFlag" value="Upload Photo"></s:hidden>
							</div>
						</div>
					</div>
					<s:token />
				</div>
			</div>
			<div class="countinuebg">
				<div class="container">
					<div class="row">
						<div
							class="col-sm-2 col-sm-offset-10 col-xs-6 col-xs-offset-3 text-right padding0">
							<s:if test='%{imageMandatory}'>
								<s:if test="%{imageUploaded}">
									<input type="button" value="Save & Continue"
										class="btn btn-warning btn-block" onclick="checkuploaded();" />
								</s:if>
								<s:else>
									<s:submit value="Save & Continue"
										cssClass="btn btn-warning btn-block" disabled="true"></s:submit>
								</s:else>
							</s:if>
							<s:else>
								<s:submit method="updateCandidateStage" value="Continue"
									cssClass="btn btn-warning btn-block"></s:submit>
							</s:else>
						</div>
					</div>
				</div>
			</div>
		</s:form>
	</div>
</body>