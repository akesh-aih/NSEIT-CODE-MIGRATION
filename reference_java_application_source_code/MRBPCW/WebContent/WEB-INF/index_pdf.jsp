<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<script src="js/bower_components/pdfmake/build/pdfmake.js"></script>
<script src="js/bower_components/pdfmake/build/vfs_fonts.js"></script>
<script src="js/jquery-3.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<div>
<h2 style="text-align: center;">Your request is being processed. Please wait for some time and do not refresh or close the tab</h2>
</div>
<form action="CandidateAction_navigateToAction.action" method="POST" name ="cheatForm">
    <s:token/>
</form>

<script type="text/javascript">
	var dataObj = {};
	dataObj =<%=request.getAttribute("JSONSTRING")%>;
	var flag="<%=request.getParameter("flag")%>";
	let checkedBox = '\u2611';
	function returnStringIfNull(string) {
		return string == null ? '-' : string
	}

	var fontSize = 8;
	pdfMake.fonts = {
		Roboto : {
			normal : 'Roboto-Regular.ttf',
			bold : 'Roboto-Medium.ttf',
			italics : 'Roboto-Italic.ttf',
			bolditalics : 'Roboto-MediumItalic.ttf'
		},
		latha : {
			normal : 'latha.ttf'
		},
		dejaVu :{
			normal : 'dejaVu.ttf'
		}
	};

	var contentCheck = [];

	contentCheck.push({
		fontSize : 14,
		absolutePosition : {
			x : 100,
			y : 14
		},
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : ''
				} ]
			} ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		image : 'logo',
		height : 62,
		width : 500
	}, {
		fontSize : 14,
		absolutePosition : {
			x : 100,
			y : 14
		},
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : ''
				} ]
			} ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		columns : [ {
			fontSize : 14,
			margin : [ 0, 20, 0, 10 ],
			table : {
				widths : [ '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ {
						text : 'Online Application Registration for Prosthetic Craftsman 2025',
						color : '#11840f',
						alignment : 'center',
						bold: true,
					}, {
						text : '',
					} ]
				}, ] ]
			},
			layout : 'noBorders'
		} ]
	});

	contentCheck.push({
		margin : [ 0, 0, 0, 10 ],
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : ' ',
				}, {
					text : '',
				} ]
			}, ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		fontSize : fontSize,
		margin : [ 0, 10, 0, 0 ],
		table : {
			widths : [ 190, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : 'User ID',
					bold: true,
				}, {
				/* text : ' / பயனர்',
				font : 'latha', */
				} ]
			}, {
				text : dataObj.userId
			} ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		table : {
			widths : [ 190, 190 ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : 'Post Applying for',
					bold: true,
				}, {
				/* text : '/ பதவி',
				font : 'latha', */
				} ]
			}, {

				text : returnStringIfNull(dataObj.disciplinName)
			} ] ]
		},
		layout : 'noBorders'
	});

	/*Start of Personal Details*/

	contentCheck.push({
		fontSize : 14,
		margin : [ 0, 20, 0, 10 ],
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : 'Personal Details',
				}, {
					text : '',
				} ]
			}, ] ]
		},
		layout : 'noBorders'
	}, {
		image : 'aadhaar',
		height : 80,
		width : 80,
		absolutePosition : {
			x : 480,
			y : 260
		},
	});

	contentCheck.push({
		table : {
			widths : [ 190, 240 ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Name',
					 {
				/* text : ' / பெயர்',
				font : 'latha', */
				} ],bold: true,
			}, {
				text : returnStringIfNull(dataObj.personalDetailsBean.candidateName)
			} ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		table : {
			widths : [ 190, 290 ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Nationality',
					 {
				/* text : ' / குடியுரிமை',
				font : 'latha', */
				} ],
				bold: true,
			}, {
				text : returnStringIfNull(dataObj.nationality)
			} ] ]
		},
		layout : 'noBorders'
	});
	
	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Gender',
					 {
				/* text : 'பாலினம்',
				font : 'latha', */
				} ], bold: true,
			}, {
				text : returnStringIfNull(dataObj.genderVal)
			} ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Are you an Ex-Servicemen',
					 {
				/* text : ' / மின்னஞ்சல் முகவரி',
				font : 'latha', */
				} ],
				bold: true,
			}, [ {
				text : returnStringIfNull(dataObj.exServiceMen)
			} ], ] ]
		},
		layout : 'noBorders'
	});

	if (dataObj.exServiceMen != null && dataObj.exServiceMen != "" && dataObj.exServiceMen == "Yes") {
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Date of Discharge / Probable Discharge ',
						 {
					/* text : ' / மின்னஞ்சல் முகவரி',
					font : 'latha', */
					} ],bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.dischargeDate)
				} ], ] ]
			},
			layout : 'noBorders'
		});

		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ 'PPO Number',
						 {
					/* text : ' / மின்னஞ்சல் முகவரி',
					font : 'latha', */
					} ],
					bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.ppoNumber)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 1,
			body : [ [ {
				text : [ 'Do you have community certificate issued by Tamil Nadu Government?',
					 {
					text : '',
				} ],bold: true,
			}, [ {
				text : returnStringIfNull(dataObj.commCertYesNo)
			} ], ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 1,
			body : [ [ {
				text : [ 'Community',
					{
					text : '',
				} ],bold: true, 
			}, [ {
				text : returnStringIfNull(dataObj.community)
			} ], ] ]
		},
		layout : 'noBorders'
	});
	if (dataObj.community != "OC") {

		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Sub Caste',
						{
						text : '',
					} ],bold: true, 
				}, [ {
					text : returnStringIfNull(dataObj.subCaste)
				} ], ] ]
			},
			layout : 'noBorders'
		});

		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Issuing Authority of Community Certificate',
						{
						text : '',
					} ],bold: true, 
				}, {
					text : dataObj.issueAuthCommCert == null ? ' ' : dataObj.issueAuthCommCert
				} ] ]
			},
			layout : 'noBorders'
		});
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Community Certificate Number',
						 {
						text : '',
					} ],bold: true,
				}, {
					text : dataObj.commCertNo == null ? ' ' : dataObj.commCertNo
				} ] ]
			},
			layout : 'noBorders'
		});
		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Community Certificate Place of Issue',
						{
						text : '',
					} ],bold: true, 
				}, {
					text : dataObj.commCertPlace == null ? ' ' : dataObj.commCertPlace
				} ] ]
			},
			layout : 'noBorders'
		});
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Community Certificate Issuing Date',
						{
						text : '',
						
					} ],bold: true, 
				}, {
					text : dataObj.commIssueDate == null ? ' ' : dataObj.commIssueDate
				} ] ]
			},
			layout : 'noBorders'
		});
	}

	contentCheck.push({
		fontSize : fontSize,
		table : {
			widths : [ 190, '*' ],
			headerRows : 1,
			body : [ [ {
				text : [ {
					text : 'Are you differently abled?',
					bold: true,
				}, {
					text : '',
					font : 'latha'
				}, ]
			}, {
				text : returnStringIfNull(dataObj.disableYesNo)
			} ] ]
		},
		layout : 'noBorders'
	});

	//physicalDisability Yes
	if (dataObj.disableYesNo != null && dataObj.disableYesNo != '' && dataObj.disableYesNo == 'Yes') {
		contentCheck.push({
			table : {
				widths : [ 210, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ {
						text : checkedBox,
						fontSize : 14,
						alignment : 'right',
						width : 20,
						font : 'dejaVu'
					}, ]
				}, [ {
					text : 'I agree to provide Differently Abled Person Certificate at the time of Certificate Verification'
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	if (dataObj.disableYesNo == "Yes") {
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Differently Abled Category',
						 {
						text : '',
						
					} ], bold: true,
				}, {
					text : dataObj.disableType == null ? ' ' : dataObj.disableType
				} ] ]
			},
			layout : 'noBorders'
		});
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Percentage of Disability',
						 {
						text : '',
						
					} ], bold: true,
				}, {
					text : dataObj.disablityPercent == null ? ' ' : dataObj.disablityPercent
				} ] ]
			},
			layout : 'noBorders'
		});
	}
	
	if (dataObj.genderVal == "Female") {
	contentCheck.push({
		fontSize : fontSize,
		table : {
			widths : [ 190, '*' ],
			headerRows : 1,
			body : [ [ {
				text : [ {
					text : 'Are you a Destitute Widow?',
					bold: true,
				}, {
					text : '',
					font : 'latha'
				}, ]
			}, {
				text : returnStringIfNull(dataObj.widowYesNo)
			} ] ]
		},
		layout : 'noBorders'
	});
	}
	
	if (dataObj.widowYesNo == "Yes") {
		
		/* contentCheck.push({
			table : {
				widths : [ 210, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ {	text: checkedBox, fontSize: 14, alignment: 'right', width: 20, font : 'dejaVu'}, 
							]
				}, [ {
					text :'I agree to provide Destitute Widow Certificate at the time of Certificate Verification.'
				} ], ] ]
			},
			layout : 'noBorders'
			}); */
		
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Destitute Widow Certificate Number',
						 {
						text : '',
						
					} ], bold: true,
				}, {
					text : dataObj.desWidowCertNo == null ? ' ' : dataObj.desWidowCertNo
				} ] ]
			},
			layout : 'noBorders'
		});
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Destitute Widow Certificate Issuing Date',
						 {
						text : '',
						
					} ], bold: true,
				}, {
					text : dataObj.widowIssueDate == null ? ' ' : dataObj.widowIssueDate
				} ] ]
			},
			layout : 'noBorders'
		});
		
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Issuing Authority of Destitute Widow Certificate',
						 {
						text : '',
						
					} ], bold: true,
				}, {
					text : dataObj.widowIssueAuth == null ? ' ' : dataObj.widowIssueAuth
				} ] ]
			},
			layout : 'noBorders'
		});
		
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Destitute Widow Certificate Issued District',
						 {
						text : '',
						
					} ], bold: true,
				}, {
					text : dataObj.widowDistrict == null ? ' ' : dataObj.widowDistrict
				} ] ]
			},
			layout : 'noBorders'
		});
		
		if (dataObj.widowDistrict == "Other") {
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Destitute Widow Certificate Issued Other District',
						 {
						text : '',
						
					} ], bold: true,
				}, {
					text : dataObj.widowOtherDistrict == null ? ' ' : dataObj.widowOtherDistrict
				} ] ]
			},
			layout : 'noBorders'
		});
		}
		
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ 'Destitute Widow Certificate Issued Sub Division',
						 {
						text : '',
						
					} ], bold: true,
				}, {
					text : dataObj.widowSubDivision == null ? ' ' : dataObj.widowSubDivision
				} ] ]
			},
			layout : 'noBorders'
		});
	}

	contentCheck.push({
		fontSize : fontSize,
		table : {
			widths : [ 190, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Date of Birth (as per SSLC mark Sheet)',
					 {
				/* text : 'பிறந்த தேதி',
				font : 'latha', */
				} ], bold: true,
			}, {
				text : returnStringIfNull(dataObj.dateOfBirth)
			} ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		fontSize : fontSize,
		table : {
			widths : [ 190, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Age as on '+dataObj.ageAsOn,
					 {
				/* text : '01/06/2023 தேதியின்படி வயது',
				font : 'latha', */
				} ], bold: true,
			}, {
				text : returnStringIfNull(dataObj.personalDetailsBean.age)
			} ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Email ID',
					{
				/* text : 'மின்னஞ்சல் முகவரி',
				font : 'latha', */
				} ],bold: true, 
			}, [ {
				text : returnStringIfNull(dataObj.personalDetailsBean.email)
			} ], ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Mobile Number',
					 {
				/* text : 'கைபேசி எண்',
				font : 'latha', */
				} ],bold: true,
			}, [ {
				text : returnStringIfNull(dataObj.personalDetailsBean.mobileNo)
			} ], ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		table : {
			widths : [ 190, 290 ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Would you like to give Father and Mother name or Guardian name',
					{
				/* text : 'தந்தை பெயர்',
				font : 'latha', */
				} ], bold: true, 
			}, [ {
				text : returnStringIfNull(dataObj.parentAndGuardianVal)
			} ], ] ]
		},
		layout : 'noBorders'
	});

	if (dataObj.guardianName != null && dataObj.guardianName != '' && dataObj.guardianName != 'undefined') {
		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Guardian\'s Name',
						{
					/* text : 'தந்தை பெயர்',
					font : 'latha', */
					} ], bold: true, 
				}, [ {
					text : returnStringIfNull(dataObj.guardianName)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	
	if (dataObj.fathersName != null && dataObj.fathersName != '' && dataObj.fathersName != 'undefined') {
		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Father\'s Name',
						 {
					/* text : 'தந்தை பெயர்',
					font : 'latha', */
					} ],bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.fathersName)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	if (dataObj.mothersName != null && dataObj.mothersName != '' && dataObj.mothersName != 'undefined') {
		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Mother\'s Name',
						 {
					/* text : 'தாயார் பெயர்',
					font : 'latha', */
					} ], bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.mothersName)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Are you Married?',
					{
				/* text : 'திருமண நிலை',
				font : 'latha', */
				} ], bold: true, 
			}, {
				text : returnStringIfNull(dataObj.mariatalStatus)
			} ] ]
		},
		layout : 'noBorders'
	});

	if (dataObj.mariatalStatus == "Yes") {
		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Name of Spouse',
						{
					/* text : 'திருமண நிலை',
					font : 'latha', */
					} ], bold: true, 
				}, {
					text : returnStringIfNull(dataObj.spouseName)
				} ] ]
			},
			layout : 'noBorders'
		});
	}

	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 1,
			body : [ [ {
				text : [ 'Nativity ',
					 {
					text : ' ',
					
				} ], bold: true,
			}, {
				text : dataObj.nativity == null ? ' ' : dataObj.nativity
			} ] ]
		},
		layout : 'noBorders'
	});
	if (dataObj.nativity != null && dataObj.nativity == "Other") {
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 1,
				body : [ [ {
					text : [ {
						text : 'Other Native',
						bold: true,
					}, {
						text : '',
						font : 'latha'
					} ]
				}, {
					text : returnStringIfNull(dataObj.otherNativity),

				} ] ]
			},
			layout : 'noBorders'
		});
	}

	

	contentCheck.push({
		table : {
			widths : [ 190, 290 ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Religion',
					{
				/* text : 'மதம்',
				font : 'latha', */
				} ], bold: true, 
			}, [ {
				text : returnStringIfNull(dataObj.religionBelief)
			} ], ] ]
		},
		layout : 'noBorders'
	});

	if (dataObj.religionBelief != null && dataObj.religionBelief != '' && dataObj.religionBelief == 'Other') {
		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Other Religion',
						{
					/* text : 'பிற மதம்',
					font : 'latha', */
					} ], bold: true, 
				}, [ {
					text : returnStringIfNull(dataObj.religionBeliefOthers)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 1,
			body : [ [ {
				text : [ 'Are you already in Government Service?',
					 {
					text : '',
					
				} ], bold: true,
			}, [ {
				text : returnStringIfNull(dataObj.isGovtService)
			} ], ] ]
		},
		layout : 'noBorders'

	});

	if (dataObj.isGovtService != null && dataObj.isGovtService != '' && dataObj.isGovtService == 'Yes') {
		contentCheck.push({
			table : {
				widths : [ 210, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ {	text: checkedBox, fontSize: 14, alignment: 'right', width: 20, font : 'dejaVu'}, 
							]
				}, [ {
					text :'I agree to provide No Objection Certificate from the concerned Government Department at the time of Certificate Verification.'
				} ], ] ]
			},
			layout : 'noBorders'
			});
	}

	if (dataObj.isGovtService != null && dataObj.isGovtService != '' && dataObj.isGovtService == 'Yes') {
		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Department Name',
						{
					/* text : 'பிற மதம்',
					font : 'latha', */
					} ], bold: true, 
				}, [ {
					text : returnStringIfNull(dataObj.orgName)
				} ], ] ]
			},
			layout : 'noBorders'
		});
		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Current Designation',
						 {
					/* text : 'பிற மதம்',
					font : 'latha', */
					} ], bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.currentDesig)
				} ], ] ]
			},
			layout : 'noBorders'
		});
		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Place of Work',
						{
					/* text : 'பிற மதம்',
					font : 'latha', */
					} ], bold: true, 
				}, [ {
					text : returnStringIfNull(dataObj.placeOfWork)
				} ], ] ]
			},
			layout : 'noBorders'
		});
		
		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Date of Joining in the Service',
						{
					/* text : 'பிற மதம்',
					font : 'latha', */
					} ], bold: true, 
				}, [ {
					text : returnStringIfNull(dataObj.govtDate)
				} ], ] ]
			},
			layout : 'noBorders'
		});
		
		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Period of Service as on the Date of Notification '+dataObj.notificationEndDate,
						{
					/* text : 'பிற மதம்',
					font : 'latha', */
					} ], bold: true, 
				}, [ {
					text : returnStringIfNull(dataObj.govtAge)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	contentCheck.push({
		fontSize : 11,
		margin : [ 0, 20, 0, 0 ],
		table : {
			widths : [ '*' ],
			headerRows : 1,
			body : [ [ {
				text : [ {
					text : 'Photo ID Proof Details'
				}, {
					text : '',
					
				} ]
			}, ] ]
		},
		layout : 'noBorders'

	});

	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 1,
			body : [ [ {
				text : [ 'Photo ID Proof',
					{
					text : " ",
					
				} ], bold: true, 
			}, [ {
				text : returnStringIfNull(dataObj.photoIDProof1)
			} ], ] ]
		},
		layout : 'noBorders'
	});
	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 1,
			body : [ [ {
				text : [ 'Photo ID Proof Number ',
					{
					text : "",
					
				} ], bold: true, 
			}, [ {
				text : returnStringIfNull(dataObj.photoIDProof1Val)
			} ], ] ]
		},
		layout : 'noBorders'
	});

	if (dataObj.addressBean.addressFiled1 != null && dataObj.addressBean.addressFiled1 != '') {
		contentCheck.push({
			fontSize : 11,
			margin : [ 0, 10, 0, 0 ],
			table : {
				widths : [ '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ {
						text : 'Permanent Address '
					}, {
					/* text : 'நிரந்தர முகவரி',
					font : 'latha', */
					}, '', ]
				}, ] ]
			},
			layout : 'noBorders'
		});

		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Address ',
						 {
					/* text : 'முகவரி',
					font : 'latha', */
					} ],bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.addressBean.addressFiled1)
				},

				], ] ]
			},
			layout : 'noBorders'
		});
	}

	if (dataObj.stateVal != null && dataObj.stateVal != '') {
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ 'State',
						 {
					/* text : 'மாநிலம்',
					font : 'latha', */
					} ], bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.stateVal)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	if (dataObj.districtVal != null && dataObj.districtVal != '') {
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ 'District ',
						 {
					/* text : 'மாவட்டம் / நகரம்',
					font : 'latha', */
					} ], bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.districtVal)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}
	if (dataObj.districtValother != null && dataObj.districtValother != '') {
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ 'District ',
						 {
					/* text : 'மாவட்டம் / நகரம்',
					font : 'latha', */
					} ], bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.districtValother)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	if (dataObj.addressBean.cityName != null && dataObj.addressBean.cityName != '') {
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ 'City / Village ',
						 {
					/* text : 'மாவட்டம் / நகரம்',
					font : 'latha', */
					} ], bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.addressBean.cityName)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	if (dataObj.addressBean.pinCode != null && dataObj.addressBean.pinCode != '') {
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Pincode',
						{
					/* text : 'அஞ்சல் குறியீடு',
					font : 'latha', */
					} ], bold: true, 
				}, [ {
					text : returnStringIfNull(dataObj.addressBean.pinCode)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	if (dataObj.addressBean.addressFiled1 != null && dataObj.addressBean.addressFiled1 != '') {
		contentCheck.push({
			fontSize : 11,
			margin : [ 0, 10, 0, 0 ],
			table : {
				widths : [ '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ {
						text : 'Correspondence Address'
					}, {
					/* text : 'தொடர்பு முகவரி',
					font : 'latha', */
					}, '', ]
				}, ] ]
			},
			layout : 'noBorders'
		});

		contentCheck.push({
			table : {
				widths : [ 190, 290 ],
				headerRows : 0,
				body : [ [ {
					text : [ 'Address',
						 {
					/* text : 'முகவரி',
					font : 'latha', */
					} ], bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.addressBean.alternateAddressFiled1)
				},

				], ] ]
			},
			layout : 'noBorders'
		});
	}

	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'State',
					 {
				/* text : 'மாநிலம்',
				font : 'latha', */
				} ], bold: true,
			}, [ {
				text : returnStringIfNull(dataObj.altStateVal)
			} ], ] ]
		},
		layout : 'noBorders'
	});

	if (dataObj.altDistrictVal != null && dataObj.altDistrictVal != '') {
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ 'District',
						 {
					/* text : 'மாவட்டம் / நகரம்',
					font : 'latha', */
					} ], bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.altDistrictVal)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}

	if (dataObj.altDistrictValOthers != null && dataObj.altDistrictValOthers != '') {
		contentCheck.push({
			table : {
				widths : [ 190, '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ 'District',
						 {
					/* text : 'மாவட்டம் / நகரம்',
					font : 'latha', */
					} ], bold: true,
				}, [ {
					text : returnStringIfNull(dataObj.altDistrictValOthers)
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}
	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'City / Village',
					 {
				/* text : 'மாவட்டம் / நகரம்',
				font : 'latha', */
				} ], bold: true,
			}, [ {
				text : returnStringIfNull(dataObj.addressBean.alternateCity)
			} ], ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		table : {
			widths : [ 190, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Pincode',
					 {
				/* text : 'அஞ்சல் குறியீடு',
				font : 'latha', */
				} ], bold: true,
			}, [ {
				text : returnStringIfNull(dataObj.addressBean.alternatePinCode)
			} ], ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		fontSize : 11,
		margin : [ 0, 10, 0, 0 ],
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : 'Mother Tongue'
				}, {
				/* text : 'விண்ணப்பித்த மாவட்டம்',
				font : 'latha', */
				}, '', ]
			}, ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		table : {
			widths : [ 190, 290 ],
			headerRows : 0,
			body : [ [ {
				text : [ 'Mother Tongue',
					 {
				/* text : 'விண்ணப்பித்த மாவட்டம்',
				font : 'latha', */
				} ], bold: true,

			}, [ {
				text : returnStringIfNull(dataObj.motherTongue)
			} ], ] ]
		},
		layout : 'noBorders'
	});

	/*End of Personal Details*/
	
	/*End of Educational Qualification*/

	contentCheck.push({
		fontSize : 14,
		margin : [ 0, 20, 0, 10 ],
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : 'Educational Qualification ',
				} ]
			}, ] ]
		},
		layout : 'noBorders'
	});

	var tableDataAcademicDetails1 = [];
	tableDataAcademicDetails1.push([ {
		text : [ {
			text : 'Examination ',
			bold: true,
		} ]
	}, {
		text : [ {
			text : 'Name of Board ',
			bold: true,
		} ]
	}, {
		text : [ {
			text : 'Month & Year of Passing ',
			bold: true,
		} ]
	}, {
		text : [ {
			text : 'Total Maximum Marks ',
			bold: true,
		} ]
	}, {
		text : [ {
			text : 'Total Obtained Marks ',
			bold: true,
		} ]
	}, {
		text : [ {
			text : 'Percentage of Marks',
			bold: true,
		} ]
	}, {
		text : [ {
			text : 'Medium of Instruction ',
			bold: true,
		} ]
	}, {
		text : [ {
			text : 'Have you studied Tamil as one of the language (Part-1) ',
			bold: true,
		} ]
	} ]);
	
	var i;

	if (typeof dataObj.educationDtlsList == 'object') {
		for (var i = 0; i < 2; i++) {

			var boardName = null; 
			
			if(dataObj.educationDtlsList[i]['examination'] == '10th / SSLC' || dataObj.educationDtlsList[i]['examination'] == '12th / HSC'
					|| dataObj.educationDtlsList[i]['examination'] == 'Diploma'){
				boardName = dataObj.educationDtlsList[i]['university'];
			} 
			
			var otherOrEqui = null;
			
			if (boardName == 'Other' || boardName == 'Other Equivalent') {
				otherOrEqui = returnStringIfNull(dataObj.educationDtlsList[i]['universityOth'] == null 
						|| dataObj.educationDtlsList[i]['universityOth'] == '' ? "" : " - " + dataObj.educationDtlsList[i]['universityOth']);	
			}
			
			tableDataAcademicDetails1.push([ {
				text : [ {
					text : returnStringIfNull(dataObj.educationDtlsList[i]['examination']),
				} ],
			}, {
				text : [ {
					text : returnStringIfNull(dataObj.educationDtlsList[i]['university'])
				}, {
					text : otherOrEqui
				} ]
			}, {
				text : [ {
					text : returnStringIfNull(dataObj.educationDtlsList[i]['dateOfPassing']),
				} ]
			}, {
				text : [ {
					text : !returnStringIfNull(dataObj.educationDtlsList[i]['totalMarks'])?'NA':returnStringIfNull(dataObj.educationDtlsList[i]['totalMarks']),
				} ]
			}, {
				text : [ {
					text : !returnStringIfNull(dataObj.educationDtlsList[i]['obtainedMarks'])?'NA':returnStringIfNull(dataObj.educationDtlsList[i]['obtainedMarks']),
				} ]
			}, {
				text : [ {
					text : returnStringIfNull(dataObj.educationDtlsList[i]['percentage']),
				} ]
			}, {
				text : [ {
					text : returnStringIfNull(dataObj.educationDtlsList[i]['medOfInstruction']),
				} ]
			}, {
				text : [ {
					text : returnStringIfNull(dataObj.educationDtlsList[i]['tamilLang']),
				} ]
			} ])
		}
	}

	contentCheck.push({
		table : {
			dontBreakRows : true,
			keepWithheaderRows : 0,
			widths : [ '12%', '15%', '15%', '10%','10%','10%', '16%', '15%' ],
			headerRows : 0,
			body : tableDataAcademicDetails1
		}
	});

	if (dataObj.educationDtlsList.length > 2 && dataObj.educationDtlsList[2]['examination'] == 'Diploma') {
		contentCheck.push({
			margin : [ 0, 0, 0, 10 ],
			table : {
				widths : [ '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ {
						text : ' ',
					}, {
						text : '',
						
					} ]
				}, ] ]
			},
			layout : 'noBorders'
		});

		var tableDataAcademicDetails2 = [];
		tableDataAcademicDetails2.push([ {
			text : [ {
				text : 'Examination ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Diploma Name ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Period of Study From ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Period of Study To ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Duration of Study (Number of Years) ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Name of Institution ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Month & Year of Passing ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Do you have marks for the Diploma Course? ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Total Maximum Marks ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Total Obtained Marks ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Percentage of Marks ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Medium of Instruction ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Have you studied Tamil as one of the language (Part-1) ',
				bold: true,
			} ]
		} ]);

		var o = 2;
		tableDataAcademicDetails2.push([ {
			text : [ {
				text : returnStringIfNull(dataObj.educationDtlsList[o]['examination']),
			} ],
		}, {
			text : [ {
				text : returnStringIfNull(dataObj.educationDtlsList[o]['university'])
			}, {
				text : otherOrEqui
			} ]
		}, {
			text : [ {

				text : returnStringIfNull(dataObj.educationDtlsList[o]['prdOfStudyFrm']),
			} ]
		}, {
			text : [ {
				text : returnStringIfNull(dataObj.educationDtlsList[o]['prdOfStudyTo']),
			} ]
		}, {
			text : [ {
				text : returnStringIfNull(dataObj.educationDtlsList[o]['duration']),
			} ]
		}, {
			text : [ {
				text : returnStringIfNull(dataObj.educationDtlsList[o]['institution']),
			} ]
		}, {
			text : [ {
				text : returnStringIfNull(dataObj.educationDtlsList[o]['dateOfPassing']),
			} ]
		}, {
			text : [ {
				text : returnStringIfNull(dataObj.educationDtlsList[o]['dipMarksYesNo']),
			} ]
		}, {
			text : [ {
				text : !returnStringIfNull(dataObj.educationDtlsList[o]['totalMarks'])?'NA':returnStringIfNull(dataObj.educationDtlsList[o]['totalMarks']),
			} ]
		}, {
			text : [ {
				text : !returnStringIfNull(dataObj.educationDtlsList[o]['obtainedMarks'])?'NA':returnStringIfNull(dataObj.educationDtlsList[o]['obtainedMarks']),
			} ]
		}, {
			text : [ {
				text : !returnStringIfNull(dataObj.educationDtlsList[o]['percentage'])?'NA':returnStringIfNull(dataObj.educationDtlsList[o]['percentage']),
			} ]
		}, {
			text : [ {
				text : returnStringIfNull(dataObj.educationDtlsList[o]['medOfInstruction']),
			} ]
		}, {
			text : [ {
				text : returnStringIfNull(dataObj.educationDtlsList[o]['tamilLang']),
			} ]
		} ])

		contentCheck.push({
			table : {
				dontBreakRows : true,
				keepWithheaderRows : 0,
				widths : [ '10%', '10%', '7%', '7%', '7%', '7%', '7%', '7%', '7%', '9%', '7%', '7%', '7%' ],
				headerRows : 0,
				body : tableDataAcademicDetails2
			}
		});

	}

	if (dataObj.educationDtlsList.length > 3) {
		contentCheck.push({
			margin : [ 0, 0, 0, 10 ],
			table : {
				widths : [ '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ {
						text : ' ',
					}, {
						text : '',
						
					} ]
				}, ] ]
			},
			layout : 'noBorders'
		});

		var tableDataAcademicDetails3 = [];
		tableDataAcademicDetails3.push([ {
			text : [ {
				text : 'Examination ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Do you have Degree/Diploma ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Specialization ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Month & Year of Passing ',
				bold: true,
			} ]
		} ]);

		var u = 3;
		tableDataAcademicDetails3.push([ {
			text : [ {
				text : returnStringIfNull(dataObj.educationDtlsList[u]['examination']),
			} ],
		}, {
			text : [ {

				text : returnStringIfNull(dataObj.educationDtlsList[u]['ugYesNo']),
			} ],
		}, {
			text : [ {

				text : !returnStringIfNull(dataObj.educationDtlsList[u]['specialization'])?'NA':returnStringIfNull(dataObj.educationDtlsList[u]['specialization']),
			} ]
		}, {
			text : [ {
				text : !returnStringIfNull(dataObj.educationDtlsList[u]['dateOfPassing'])?'NA':returnStringIfNull(dataObj.educationDtlsList[u]['dateOfPassing']),
			} ]
		} ]);
		
		var d = 4;
		tableDataAcademicDetails3.push([ {
			text : [ {
				text : 'PG Degree',
			} ],
		}, {
			text : [ {

				text : returnStringIfNull(dataObj.educationDtlsList[d]['pgYesNo']),
			} ],
		}, {
			text : [ {
				text : !returnStringIfNull(dataObj.educationDtlsList[d]['specialization'])?'NA':returnStringIfNull(dataObj.educationDtlsList[d]['specialization']),
			} ]
		}, {
			text : [ {
				text : !returnStringIfNull(dataObj.educationDtlsList[d]['dateOfPassing'])?'NA':returnStringIfNull(dataObj.educationDtlsList[d]['dateOfPassing']),
			} ]
		} ]);

		var j = 4;
		tableDataAcademicDetails3.push([ {
			text : [ {
				text : 'PG Diploma',
			} ],
		}, {
			text : [ {

				text : returnStringIfNull(dataObj.educationDtlsList[j]['pgDipYesNo']),
			} ],
		}, {
			text : [ {
				text : !returnStringIfNull(dataObj.educationDtlsList[j]['pgDipSpecialization'])?'NA':returnStringIfNull(dataObj.educationDtlsList[j]['pgDipSpecialization']),
			} ]
		}, {
			text : [ {
				text : !returnStringIfNull(dataObj.educationDtlsList[j]['pgDipDateofpassing'])?'NA':returnStringIfNull(dataObj.educationDtlsList[j]['pgDipDateofpassing']),
			} ]
		} ]);

		contentCheck.push({
			table : {
				dontBreakRows : true,
				keepWithheaderRows : 0,
				widths : [ '16%', '25%', '35%', '24%' ],
				headerRows : 0,
				body : tableDataAcademicDetails3
			}
		});

	}
 
	if (dataObj.educationDtlsList.length > 4 && dataObj.educationDtlsList[5]['pstmPreference'] == 'Yes') {

		contentCheck.push({
			margin : [ 0, 0, 0, 10 ],
			table : {
				widths : [ '*' ],
				headerRows : 0,
				body : [ [ {
					text : [ {
						text : ' ',
					}, {
						text : '',
						
					} ]
				}, ] ]
			},
			layout : 'noBorders'
		});

		var tableDataAcademicDetails4 = [];
		tableDataAcademicDetails4.push([ {
			text : [ {
				text : 'Examination ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Are you eligible to avail PSTM preference? ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Have you studied in Tamil medium from 1st standard to 12th standard? ',
				bold: true,
			} ]
		}, {
			text : [ {
				text : 'Have you studied your Diploma in Tamil medium? ',
				bold: true,
			} ]
		} ]);

		var l = 5;
		tableDataAcademicDetails4.push([ {
			text : [ {
				text : 'PSTM',
			} ],
		}, {
			text : [ {

				text : returnStringIfNull(dataObj.educationDtlsList[l]['pstmPreference']),
			} ],
		}, {
			text : [ {

				text : returnStringIfNull(dataObj.educationDtlsList[l]['tamilMedium']),
			} ]
		}, {
			text : [ {
				text : returnStringIfNull(dataObj.educationDtlsList[l]['ugTamilMedium']),
			} ]
		} ]);

		contentCheck.push({
			table : {
				dontBreakRows : true,
				keepWithheaderRows : 0,
				widths : [ '16%', '28%', '28%', '28%' ],
				headerRows : 0,
				body : tableDataAcademicDetails4
			}
		});
	} else if(dataObj.educationDtlsList[5]['pstmPreferenceVal'] == 'NO'){
		contentCheck.push({
			margin : [ 0, 10, 0, 0 ],
			table : {
				widths : [ 190, '*' ],
				headerRows : 0,
				body : [ [ {
					text :  'Are you eligible to avail PSTM preference? ',
						bold: true,
				}, [ {
					text : 'NO'
				} ], ] ]
			},
			layout : 'noBorders'
		});
	}
	/*End of Education Details*/


	/*Start of Upload Documents*/

	contentCheck.push({
		fontSize : 14,
		margin : [ 0, 10, 0, 10 ],
		table : {
			widths : [ '*' ],
			headerRows : 1,
			body : [ [ {
				text : [ {
					text : 'Upload Documents ',
				}, {
				/* text : 'ஆவணங்களை பதிவேற்றம் செய்யவும்',
				font : 'latha', */
				} ]
			}, ] ]
		},
		layout : 'noBorders'
	});

	var tableDataDOCDetails = [];
	tableDataDOCDetails.push([ {
		text : [ {
			text : ' 	Document Name ',
			bold: true,
		} ],
	}, {
		text : [ {
			text : ' 	File Name  ',
			bold: true,
		} ]
	} ])
	//console.log(typeof dataObj.uploadList);
	//console.log(dataObj.uploadList.length);
	if (typeof dataObj.uploadList == 'object') {
		for (var i = 0; i < dataObj.uploadList.length; i++) {

			tableDataDOCDetails.push([ {
				text : [ returnStringIfNull(dataObj.uploadList[i]['docLabel1']) ]
			}, {
				text : [ {
					text : returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),

				} ],
			} ])

		}
	}
	contentCheck.push({
		table : {
			dontBreakRows : true,
			keepWithHeaderRows : 1,
			widths : [ '50%', '50%' ],
			headerRows : 1,
			body : tableDataDOCDetails
		}
	});

	/*End of Upload Documents*/

	contentCheck.push({
		fontSize : 14,
		margin : [ 0, 20, 0, 10 ],
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : 'Declaration ',
				}, {
					text : '',
					
				} ]
			}, ] ]
		},
		layout : 'noBorders'
	});

	contentCheck
			.push({
				table : {
					widths : [ '*' ],
					headerRows : 0,
					body : [ [ {
						text : [ 'I hereby declare that all the particulars furnished in this application are true, correct and complete to the best of my knowledge and believe. In the event of any information being found false or incorrect or ineligibility being detected before or after the selection, action can be taken against me by the MRB.' ]
					} ] ]
				},
				layout : 'noBorders'
			});
	contentCheck.push({
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'I hereby declare that I will not be a party to any kind of canvassing on my behalf.' ]
			} ] ]
		},
		layout : 'noBorders'
	});
	contentCheck.push({
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'I further declare that I fulfil all the eligibility conditions prescribed for admission to this post.' ]
			} ] ]
		},
		layout : 'noBorders'
	});
	contentCheck.push({
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'I have informed my employer in writing that I am applying for this post and furnish the NOC for this purpose (if applicable).' ]
			} ] ]
		},
		layout : 'noBorders'
	});
	contentCheck
			.push({
				table : {
					widths : [ '*' ],
					headerRows : 0,
					body : [ [ {
						text : [ 'I have gone through the instructions etc. to candidates and the Board\'s Notification for this recruitment, before filling up the application form and I am eligible to apply for this post.' ]
					} ] ]
				},
				layout : 'noBorders'
			});
	contentCheck.push({
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'I declare that I possess the Medical Standards prescribed for the post(s) which I am now applying.' ]
			} ] ]
		},
		layout : 'noBorders'
	});
	contentCheck.push({
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'I certify that I have not been debarred / disqualified by the Board or any other recruiting agency.' ]
			} ] ]
		},
		layout : 'noBorders'
	});
	contentCheck.push({
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'I am not a dismissed Government Employee.' ]
			} ] ]
		},
		layout : 'noBorders'
	});
	contentCheck.push({
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'There is no criminal case filed against me in the Police Station / Court.' ]
			} ] ]
		},
		layout : 'noBorders'
	});
	contentCheck.push({
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'There is no Vigilance Case filed against me.' ]
			} ] ]
		},
		layout : 'noBorders'
	});
	contentCheck.push({
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'I hereby declare that my character / antecedents are suitable for appointment to this post.' ]
			} ] ]
		},
		layout : 'noBorders'
	});
	contentCheck.push({
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ 'I declare that I do not have more than one living spouse / I am unmarried.' ]
			} ] ]
		},
		layout : 'noBorders'
	});
	
	contentCheck.push({
		table : {
			widths : [ 10, '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : checkedBox,
					fontSize : 14,
					alignment : 'right',
					width : 20,
					font : 'dejaVu'
				}, ]
			}, [ {
				text : 'I accept the above declaration.'
			} ], ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		margin : [ 0, 20, 0, 0 ],
		table : {
			widths : [ '40%', '*' ],
			headerRows : 0,
			body : [ [ ' ', {
				alignment : 'right',
				image : 'signature',
				width : '90',
				height : 40
			} ], [ {
				text : 'Submitted Date : ' + (dataObj.regFormSubmitedDate != null ? dataObj.regFormSubmitedDate : ''),
			}, {
				alignment : 'right',
				text : [ [ '(Signature of the Candidate', {
					text : '',
					
				}, ')', ] ]
			} ] ]
		},
		layout : 'noBorders'
	});

	//console.log(JSON.stringify(contentCheck));
	for (var i = 0; i < contentCheck.length; i++) {
		if (typeof contentCheck[i]['table'] != "undefined") {
			contentCheck[i]['table']['dontBreakRows'] = true;
		}
	}
	//console.log("CONTENT", contentCheck);

	var isArray = function(a) {
		return (!!a) && (a.constructor === Array);
	};

	var isObject = function(a) {
		return (!!a) && (a.constructor === Object);
	};

	function toUpperCaseData(content) {
		for (var i = 0; i < content.length; i++) {
			if (isObject(content[i])) {
				iterateObject(content[i])
			}
			if (isArray(content[i])) {
				iterateArray(content[i])
			} else {

			}
		}
		return content;
	}

	function iterateObject(data) {
		for ( var key in data) {
			//console.log(data[key]);
			if (isObject(data[key])) {
				data[key] = iterateObject(data[key])
			} else if (isArray(data[key])) {
				data[key] = iterateArray(data[key])
			} else {
				if (key == "text") {
					if (typeof data[key] == "string") {
						data[key] = data[key].toUpperCase();
					}
				}
			}
		}
		return data
	}

	function iterateArray(data) {
		for (var i = 0; i < data.length; i++) {
			//console.log(data[i]);
			if (isObject(data[i])) {
				data[i] = iterateObject(data[i])
			} else if (isArray(data[i])) {
				data[i] = iterateArray(data[i])
			} else {
				//console.log(data[i]);
				if (typeof data[i] == "string") {
					data[i] = data[i].toUpperCase();
				}
			}
		}
		return data;
	}

	contentCheck = toUpperCaseData(JSON.parse(JSON.stringify(contentCheck)));

	var today = new Date().toLocaleString('en-GB', { hour12: false }).replace(new RegExp('/', 'g'), '-').replace(',', '');

	var docDefinition = {
		content : contentCheck,
		defaultStyle : {
			fontSize : fontSize
		},
		images : {
			//logo: 'data:image/jpeg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAAAPAAD/4QMpaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjAtYzA2MCA2MS4xMzQ3NzcsIDIwMTAvMDIvMTItMTc6MzI6MDAgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDUzUgV2luZG93cyIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDoyREU2MEJEMjYyRkYxMUU3QjREMzk1QTIwRDBFNjBDMyIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDoyREU2MEJEMzYyRkYxMUU3QjREMzk1QTIwRDBFNjBDMyI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjJERTYwQkQwNjJGRjExRTdCNEQzOTVBMjBEMEU2MEMzIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjJERTYwQkQxNjJGRjExRTdCNEQzOTVBMjBEMEU2MEMzIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+/+4ADkFkb2JlAGTAAAAAAf/bAIQAEw8PFxEXJRYWJS8kHSQvLCQjIyQs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',
			logo : dataObj.logoCast,
			aadhaar : dataObj.imageCast,
			signature : dataObj.signatureCast
		},
		footer : function(currentPage, pageCount) {
			return {
				margin : 10,
				columns : [ {
					fontSize : 7,
					text : [ {
						text : 'Downloaded on ' + today,
					} ],
					alignment : 'left'
				}, {
					fontSize : 9,
					text : [ {
						text : currentPage.toString(),
					} ],
					alignment : 'left'
				} ]
			};
		},
		header : function(currentPage, pageCount) {
			return {
				margin : 10,
				columns : [ {
					fontSize : 7,
					text : [ {
					//text: dataObj.jsonDate,
					} ],
					alignment : 'right'
				} ]
			};
		},
	};
	var pdfname = "Application Form.pdf";
	/*  if (dataObj.afterApplyVeiwPayment != null && dataObj.afterApplyVeiwPayment != "" && dataObj.afterApplyVeiwPayment == "true") {
	     pdfname = "Application Form View.pdf"
	 } */

	const pdfDocGenerator = pdfMake.createPdf(docDefinition);

	if (flag == "null" || flag == '' || flag == 'view') {

		pdfDocGenerator.getBuffer(function(buffer) {

			blob = new Blob([ buffer ]);
			var formData = new FormData();
			formData.append("serverPdf", blob, "filename");
			$.ajax({
				url : "CandidateAction_createServerSidePDF.action",
				type : "POST",
				data : formData,
				processData : false,
				contentType : false,
				async : false,
				success : function(response) {
					pdfDocGenerator.download("Application Form View.pdf");
					$('<input>').attr({
						type : 'hidden',
						id : 'flag',
						name : 'flag',
						value : flag
					}).appendTo('form');
					document.cheatForm.submit();
				},
				error : function(xhr, status, error) {
					pdfDocGenerator.download("Application Form View.pdf");
					$('<input>').attr({
						type : 'hidden',
						id : 'error',
						name : 'error',
						value : error
					}).appendTo('form');
					document.cheatForm.submit();
				}
			});
		});
	} else {
		pdfDocGenerator.getBuffer(function(buffer) {

			blob = new Blob([ buffer ]);
			var formData = new FormData();
			formData.append("serverPdf", blob, "filename");
			$.ajax({
				url : "CandidateAction_createServerSidePDF.action",
				type : "POST",
				data : formData,
				processData : false,
				contentType : false,
				async : false,
				success : function(response) {

					$('<input>').attr({
						type : 'hidden',
						id : 'flag',
						name : 'flag',
						value : flag
					}).appendTo('form');
					document.cheatForm.submit();
				},
				error : function(xhr, status, error) {
					$('<input>').attr({
						type : 'hidden',
						id : 'error',
						name : 'error',
						value : error
					}).appendTo('form');
					document.cheatForm.submit();
				}
			});
		});
	}
</script>