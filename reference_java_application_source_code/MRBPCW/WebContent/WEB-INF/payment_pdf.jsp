<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<script src="js/bower_components/pdfmake/build/pdfmake.js"></script>
<script src="js/bower_components/pdfmake/build/vfs_fonts.js"></script>
<script src="js/jquery-3.6.3.min.js"></script>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/moment.min.js"></script>
<script type="text/javascript" src="js/moment-precise-range.js"></script>
<div>
<h2 style="text-align: center;">Your request is being processed. Please wait for some time and do not refresh or close the tab</h2>
</div>

<form action="CandidateAction_navigateToAction.action" method="POST" name ="cheatForm">
    <s:token/>
</form>
<script type="text/javascript">
	var dataObj = {};
	dataObj =<%=request.getAttribute("PAYJSONSTRING")%>;
	
	var flag="<%=request.getParameter("flag")%>";

	 var fontSize = 8;
	    pdfMake.fonts = {
	        Roboto: {
	            normal: 'Roboto-Regular.ttf',
	            bold: 'Roboto-Medium.ttf',
	            italics: 'Roboto-Italic.ttf',
	            bolditalics: 'Roboto-MediumItalic.ttf'
	        },
	        latha: {
	            normal: 'latha.ttf'
	        },
	    };

	var contentCheck = [];

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
						bold : true,
						color : '#11840f',
						alignment : 'center'
					}, {
						text : '',
						font : 'latha',
					} ]
				}, ] ]
			},
			layout : 'noBorders'
		} ]
	});

	contentCheck.push({
		fontSize : 16,
		margin : [ 0, 20, 0, 20 ],
		table : {
			widths : [ '*' ],
			headerRows : 1,
			body : [ [ {
				text : [ {
					text : 'Payment Receipt',
					bold : true,
					alignment : 'center'
				}, {
					/* text : 'ஆவணங்களை பதிவேற்றம் செய்யவும்',
					font : 'latha', */
				} ]
			}, ] ]
		},
		layout : 'noBorders'
	});

	var tableDataDOCDetails = [];
	tableDataDOCDetails.push([ { text : [ 'User ID', { /* text : ' / சாதிச்சான்றிதழ்', font : 'latha', */} ]}, 
								{text : [ { text : dataObj.userId , bold : true} ] } ]);
	
	tableDataDOCDetails.push([ { text : [ 'Candidate Name', { /* text : ' / சாதிச்சான்றிதழ்', font : 'latha', */} ]}, 
		{text : [ { text : dataObj.personalDetailsBean.candidateName , bold : true} ] } ]);
	
	tableDataDOCDetails.push([ { text : [ 'Applied Post', { /* text : ' / சாதிச்சான்றிதழ்', font : 'latha', */} ]}, 
		{text : [ { text : 'Prosthetic Craftsman' , bold : true} ] } ]);
	
	tableDataDOCDetails.push([ { text : [ 'Payment Amount (Rs.)', { /* text : ' / சாதிச்சான்றிதழ்', font : 'latha', */} ]}, 
		{text : [ { text : dataObj.idproof , bold : true} ] } ]);
	
	tableDataDOCDetails.push([ { text : [ 'Payment Status', { /* text : ' / சாதிச்சான்றிதழ்', font : 'latha', */} ]}, 
		{text : [ { text : 'Successful', bold : true } ] } ]);
	
	tableDataDOCDetails.push([ { text : [ 'Payment Type', { /* text : ' / சாதிச்சான்றிதழ்', font : 'latha', */} ]}, 
		{text : [ { text : 'Online' , bold : true} ] } ]);
	
	tableDataDOCDetails.push([ { text : [ 'Payment Date', { /* text : ' / சாதிச்சான்றிதழ்', font : 'latha', */} ]}, 
		{text : [ { text : dataObj.personalDetailsBean.payment_date , bold : true} ] } ]);
	
	tableDataDOCDetails.push([ { text : [ 'Order ID', { /* text : ' / சாதிச்சான்றிதழ்', font : 'latha', */} ]}, 
		{text : [ { text : dataObj.personalDetailsBean.orderId , bold : true} ] } ]);
	
	tableDataDOCDetails.push([ { text : [ 'Payment ID', { /* text : ' / சாதிச்சான்றிதழ்', font : 'latha', */} ]}, 
		{text : [ { text : dataObj.appNumber , bold : true} ] } ]);

	
	contentCheck.push({
		fontSize : 10,
		table : {
			dontBreakRows : true,
			keepWithHeaderRows : 1,
			widths : [ '35%', '65%' ],
			headerRows : 1,
			body : tableDataDOCDetails,
		}
	});

	contentCheck.push({
		fontSize : 10,
		margin : [ 0, 10, 0, 10 ],
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : 'Authorized Signatory',
					bold : true
				}]
			}, ] ]
		},
		layout : 'noBorders'
	});

	contentCheck.push({
		fontSize : 10,
		margin : [ 0, 20, 0, 10 ],
		table : {
			widths : [ '*' ],
			headerRows : 0,
			body : [ [ {
				text : [ {
					text : 'Note : This is a computer generated receipt and does not require a signature.',
					bold : true
				}, {
					text : '',
					font : 'latha',
				} ]
			}, ] ]
		},
		layout : 'noBorders'
	});


	for (var i = 0; i < contentCheck.length; i++) {
		if (typeof contentCheck[i]['table'] != "undefined") {
			contentCheck[i]['table']['dontBreakRows'] = true;
		}
	}

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

	var today = moment().format('DD-MM-YYYY hh:mm:ss');

	var docDefinition = {
		content : contentCheck,
		defaultStyle : {
			fontSize : fontSize
		},
		images : {
			logo : dataObj.logoCast,
			//aadhaar : dataObj.imageCast,
			//signature : dataObj.signatureCast
		},
		footer : function(currentPage, pageCount) {
			return {
				margin : 10,
				columns : [ {
					fontSize : 7,
					text : [ {
						text : today,
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
	var pdfname = "Payment_Receipt.pdf";
	var transactionId = dataObj.personalDetailsBean.orderId;
	const pdfDocGenerator = pdfMake.createPdf(docDefinition);
		pdfDocGenerator.getBuffer(function(buffer) {

			blob = new Blob([ buffer ]);
			var formData = new FormData();
			formData.append("serverPdf", blob, "filename");
			formData.append("transactionId",transactionId);
			$.ajax({
				url : "CandidateAction_createServerSidePaymentPdf.action",
				type : "POST",
				data : formData,
				processData : false,
				contentType : false,
				async : false,
				success : function(response) {
					pdfDocGenerator.download("Payment_Receipt.pdf");
					$('<input>').attr({
						type : 'hidden',
						id : 'flag',
						name : 'flag',
						value : flag
					}).appendTo('form');
					document.cheatForm.submit();
				},
				error : function(xhr, status, error) {
					pdfDocGenerator.download("Payment_Receipt.pdf");
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
</script>