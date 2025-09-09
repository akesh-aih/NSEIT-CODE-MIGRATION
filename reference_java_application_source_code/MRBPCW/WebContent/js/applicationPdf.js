/*<script src="js/bower_components/pdfmake/build/pdfmake.js"></script>
<script src="js/bower_components/pdfmake/build/vfs_fonts.js"></script>
<script src="js/jquery-3.5.1.min.js"></script>*/

function generateApplicationPdf(){
	var data = $('#jsonString').val();
    var dataObj = {};
    dataObj = data;

    console.log(dataObj);
    
    
    function returnStringIfNull(string) {
        return string == null ? '' : string
    }
    
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
    var stateNewVal = "";
    var districtNewVal = "";
    var districtNewVal2 = "";
    var policeJurisdictionNewVal = "";
    var policeJurisdictionNewVal2 = "";
    var religionLabel = "";
    var religionVal = "";
    var religionTamilVal = "";
    var university="";

        contentCheck.push({
            fontSize: 14,
            absolutePosition: {x: 100, y: 14},
            table: {
                widths: ['*'],
                headerRows: 1,
                body: [
                    [
                        {
                            text: [{text: ''}]
                        }
                    ]
                ]
            },
            layout: 'noBorders'
        });
    

    contentCheck.push({
            image: 'logo',
            height: 52,
            width: 306
        }, {
            fontSize: fontSize,
            margin: [0, 10, 0, 0],
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {
                            text: [{text: 'Reference Number / '}, {
                                text: 'रेफ़रन्स संख्या', font: 'latha',
                            }]
                        },
                        {text: dataObj.userId}
                    ]
                ]
            },
            layout: 'noBorders'
        });
    contentCheck.push({
        fontSize: fontSize,
        margin: [0, 10, 0, 0],
        table: {
            widths: [170, '*'],
            headerRows: 1,
            body: [
                [
                    {
                        text: [{text: 'Registration Number / '}, {
                            text: 'पंजीकरण संख्या', font: 'latha',
                        }]
                    },
                    {text: dataObj.registrationId}
                ]
            ]
        },
        layout: 'noBorders'
    });

    contentCheck.push(
        {
       	 table: {
                widths: [170, 200],
                headerRows: 1,
                body: [
                    [
                        {
                        	text: [{text: 'Post Applying for / '}, {
                                text: 'पद के लिए आवेदन', font: 'latha',
                            }]
                        },
                        {text: 'Head Operator / Head Operator (Mechanic)'}
                    ]
                ]
            },
            layout: 'noBorders'
       });
    
     contentCheck.push
    ({
            fontSize: 14,
            margin: [0, 20, 0, 0],
            table: {
                widths: ['*'],
                headerRows: 1,
                body: [
                	  [
                          {
                              text: [{
                                  text: 'Personal Details / '
                              }, {
                                   text: 'व्यक्तिगत विवरण', font: 'latha', 
                              }]
                          },
                      ]
                ]
            },
            layout: 'noBorders'

        },
        {
            image: 'aadhaar',
            height: 80, width: 80,
            absolutePosition: {x: 480, y: 88},
        });
 
    
     contentCheck.push(
             {
            	 table: {
                     widths: [170, '*'],
                     headerRows: 1,
                     body: [
                         [
                             {text: ['Title / ', {text: 'शीर्षक', font: 'latha',}]},
                             {text: returnStringIfNull(dataObj.initials) }
                         ]
                     ]
                 },
                 layout: 'noBorders'
            });
     contentCheck.push(
             {
            	 table: {
                     widths: [170, '*'],
                     headerRows: 1,
                     body: [
                         [
                             {text: ['Applicant\'s First Name /', {text: ' आवेदक का पहला नाम', font: 'latha',}]},
                             {text: returnStringIfNull(dataObj.personalDetailsBean.candidateFirstName) }
                         ]
                     ]
                 },
                 layout: 'noBorders'
            });
     if(dataObj.personalDetailsBean.candidateMiddleName != ''){ 
     contentCheck.push(
             {
            	 table: {
                     widths: [170, '*'],
                     headerRows: 1,
                     body: [
                         [
                             {text: ['Middle Name / ', {text: 'मध्य नाम', font: 'latha',}]},
                             {text: returnStringIfNull(dataObj.personalDetailsBean.candidateMiddleName) }
                         ]
                     ]
                 },
                 layout: 'noBorders'
            });
     }
     if(dataObj.personalDetailsBean.candidateLastName != ''){ 
     contentCheck.push(
             {
            	 table: {
                     widths: [170, '*'],
                     headerRows: 1,
                     body: [
                         [
                             {text: ['Last Name / ', {text: 'उपनाम', font: 'latha',}]},
                             {text: returnStringIfNull(dataObj.personalDetailsBean.candidateLastName) }
                         ]
                     ]
                 },
                 layout: 'noBorders'
            });
     }
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                    	{text: ['Applicant Full Name  / ', {text: 'आवेदक का पूरा नाम ', font: 'latha',}]},
                    
                        {text: returnStringIfNull(dataObj.personalDetailsBean.candidateFullName)}
                    ]
                ]
            },
            layout: 'noBorders'
        });
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Gender / ', {text: 'लिंग', font: 'latha',}]},
                        {text: dataObj.genderVal == null ? ' ' : dataObj.genderVal}
                    ]
                ]
            },
            layout: 'noBorders'
        });
        contentCheck.push({
            table: {
                widths: [170, 320],
                headerRows: 1,
                body: [
                    [
                        {text: ['Nationality / ', {text: 'राष्ट्रीयता', font: 'latha',}]},
                        {text: dataObj.nationality == null ? ' ' : dataObj.nationality}
                    ]
                ]
            },
            layout: 'noBorders'
        });
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Email ID / ', {text: 'ईमेल आईडी', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.personalDetailsBean.email)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Mobile Number / ', {text: 'मोबाइल नंबर', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.personalDetailsBean.mobileNo)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        contentCheck.push
        ({
                fontSize: 10,
                margin: [0, 10, 0, 0],
                table: {
                    widths: ['*'],
                    headerRows: 1,
                    body: [
                    	  [
                              {
                                  text: [{
                                      text: 'Section - I  / '
                                  }, {
                                       text: 'अनुभाग - १', font: 'latha', 
                                  }]
                              },
                          ]
                    ]
                },
                layout: 'noBorders'

            });
        
        contentCheck.push({
                    table: {
                        widths: [170, '*'],
                        headerRows: 1,
                        body: [
                            [
                                {text: ['Are you Domicile of UP? / ', {text: 'क्या आप उत्तर प्रदेश के रहने वाले हैं?', font: 'latha',}]},
                                [
                                    {text: returnStringIfNull(dataObj.domicileUp)}
                                ],
                            ]
                        ]
                    },
                    layout: 'noBorders'
                });
        if(dataObj.domicileUp != null && dataObj.domicileUp == 'Yes'){
        contentCheck.push({
                    table: {
                        widths: [170, 320],
                        headerRows: 1,
                        body: [
                            [
                                {text: ['Domicile Certificate Issuing UP State Authority / ', {text: 'मूल निवासी प्रमाण पत्र जारी करने वाला उत्तर प्रदेश  राज्य प्राधिकारी', font: 'latha',}]},
                                [
                                    {text: returnStringIfNull(dataObj.domicileCertificateUp)}
                                ],
                            ]
                        ]
                    },
                    layout: 'noBorders'
                });
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Date of Issuing Domicile Certificate (DD/MM/YYYY) /', {text: 'मूल निवासी प्रमाण पत्र जारी करने की तिथि ', font: 'latha',},' (DD/MM/YYYY)']},
                        [
                            {text: returnStringIfNull(dataObj.domicileCertificate)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Domicile Certificate Serial No. / ', {text: 'मूल निवासी प्रमाण पत्र क्रम संख्या।', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.domicileSerial)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Application Number of Domicile Certificate / ', {text: 'निवास प्रमाण पत्र का आवेदन नंबर', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.appNoDomCert)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        }
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Category / ', {text: 'श्रेणी (जाति) ', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.categoryVal)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        if(dataObj.categoryVal != null && dataObj.categoryVal != '' && dataObj.categoryVal != "UR" ){
        		contentCheck.push({
                    table: {
                        widths: [170, 320],
                        headerRows: 1,
                        body: [
                            [
                                {text: ['Category Certificate Issuing UP State Authority / ', {text: 'श्रेणी प्रमाण पत्र जारी करने वाला उत्तर प्रदेश राज्य प्राधिकारी', font: 'latha',}]},
                                [
                                    {text: returnStringIfNull(dataObj.catCertNo)}
                                ],
                            ]
                        ]
                    },
                    layout: 'noBorders'
                });
        		contentCheck.push({
                    table: {
                        widths: [170, '*'],
                        headerRows: 1,
                        body: [
                            [
                                {text: ['Date of Issuing Category Certificate (DD/MM/YYYY) /', {text: ' वर्ग प्रमाणपत्र जारी करने की तारीख', font: 'latha',},'(DD/MM/YYYY)']},
                                [
                                    {text: returnStringIfNull(dataObj.categoryCertIssDt)}
                                ],
                            ]
                        ]
                    },
                    layout: 'noBorders'
                });
        		contentCheck.push({
                    table: {
                        widths: [170, '*'],
                        headerRows: 1,
                        body: [
                            [
                                {text: ['Category Certificate Serial Number /', {text: ' श्रेणी प्रमाणपत्र क्रम संख्या ', font: 'latha',}]},
                                [
                                    {text: returnStringIfNull(dataObj.catSerial)}
                                ],
                            ]
                        ]
                    },
                    layout: 'noBorders'
                });
        		
        		contentCheck.push({
                    table: {
                        widths: [170, '*'],
                        headerRows: 1,
                        body: [
                            [
                                {text: ['Application Number of Category Certificate / ', {text: 'श्रेणी प्रमाण पत्र का आवेदन नंबर', font: 'latha',}]},
                                [
                                    {text: returnStringIfNull(dataObj.appNoForCat)}
                                ],
                            ]
                        ]
                    },
                    layout: 'noBorders'
                });
        }
        contentCheck.push
        ({
                fontSize: 10,
                margin: [0, 10, 0, 0],
                table: {
                    widths: ['*'],
                    headerRows: 1,
                    body: [
                    	  [
                              {
                                  text: [{
                                      text: 'Section - II  /'
                                  }, {
                                       text: ' अनुभाग - २', font: 'latha', 
                                  }]
                              },
                          ]
                    ]
                },
                layout: 'noBorders'

            });
       
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Are you Dependent of Freedom Fighter? /', {text: ' क्या आप स्वतंत्रता सेनानी के आश्रित हैं?', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.freedomFighter)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        if(dataObj.freedomFighter != null && dataObj.freedomFighter == 'Yes'){
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Date of Issuing of Freedom Fighter Dependent Certificate (DD/MM/YYYY) /', {text: ' स्वतंत्रता सेनानी आश्रित प्रमाण पत्र जारी करने की तिथि ', font: 'latha',},' (DD/MM/YYYY)']},
                        [
                            {text: returnStringIfNull(dataObj.freedomFighterDt)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Dependent of Freedom Fighter Certificate Serial Number /', {text: ' स्वतंत्रता सेनानी आश्रित प्रमाणपत्र क्रम संख्या ', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.freedomFighterSerial)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        contentCheck.push({
            table: {
                widths: [170, 320],
                headerRows: 1,
                body: [
                    [
                        {text: ['Freedom Fighter Dependent Certificate Issuing Authority /', {text: ' स्वतंत्रता सेनानी आश्रित प्रमाणपत्र जारी करने वाला प्राधिकारी', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.freedomFighterAuthority)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        }
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Are you a UP Government Employee and having required certificate? /', {text: ' क्या आप यूपी सरकार के कर्मचारी हैं और आपके पास आवश्यक प्रमाण पत्र है?', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.governmentEmp)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        if(dataObj.governmentEmp != null && dataObj.governmentEmp == 'Yes'){
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {text: ['Date of Joining UP Government Service (DD/MM/YYYY) /', {text: ' उत्तर प्रदेश सरकार सेवा में शामिल होने की तिथि ', font: 'latha',},' (DD/MM/YYYY)']},
                        [
                            {text: returnStringIfNull(dataObj.governmentEmpDt)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });

        contentCheck.push
        ({
               fontSize: fontSize,
               table: {
                   widths: [170, '*'],
                   headerRows: 1,
                   body: [
                       [
                           {
                        	   text: ['NOC / Permission Letter Date (DD/MM/YYYY) /', {text: ' एनओसी ',font:'latha'},' (NOC)',{text:' / अनुमति पत्र दिनांक', font: 'latha',},' (DD/MM/YYYY)']
                           },
                           {text: returnStringIfNull(dataObj.nocDt)}
                       ]
                   ]
               },
               layout: 'noBorders'
           });
        if(dataObj.nocSerial != null && dataObj.nocSerial != '')
        {
        contentCheck.push
        ({
               fontSize: fontSize,
               table: {
                   widths: [170, '*'],
                   headerRows: 1,
                   body: [
                       [
                           {
                        	   text: ['NOC / Permission Letter Serial Number /', {text: ' एनओसी ',font:'latha'},' (NOC)',{text:' / अनुमति पत्र क्रम संख्या', font: 'latha',}]
                           },
                           {text: returnStringIfNull(dataObj.nocSerial)}
                       ]
                   ]
               },
               layout: 'noBorders'
           });
        }
        contentCheck.push
        ({
               fontSize: fontSize,
               table: {
                   widths: [170, 320],
                   headerRows: 1,
                   body: [
                       [
                           {
                        	   text: ['NOC / Permission Letter Issuing Authority /', {text: ' एनओसी ',font:'latha'},' (NOC)',{text:' / अनुमति पत्र जारी करने वाला प्राधिकारी', font: 'latha',}]
                           },
                           {text: returnStringIfNull(dataObj.nocAuthority)}
                       ]
                   ]
               },
               layout: 'noBorders'
           });
        }
        contentCheck.push
        ({
               fontSize: fontSize,
               table: {
                   widths: [170, '*'],
                   headerRows: 1,
                   body: [
                       [
                           {
                               text: ['Are you an Ex-serviceman? /', {text: ' क्या आप पूर्व सैनिक हैं?', font: 'latha',}]
                           },
                           {text: returnStringIfNull(dataObj.isExServicemen)}
                       ]
                   ]
               },
               layout: 'noBorders'
           });
        if(dataObj.isExServicemen != null && dataObj.isExServicemen == 'Yes'){
        contentCheck.push
        ({
               fontSize: fontSize,
               table: {
                   widths: [170, '*'],
                   headerRows: 1,
                   body: [
                       [
                           {
                               text: ['ESM - Date of Enlistment /', {text: ' ई एस एम - नामांकन की तारीख', font: 'latha',}]
                           },
                           {text: returnStringIfNull(dataObj.esmDateOfEnlistment)}
                       ]
                   ]
               },
               layout: 'noBorders'
           });
        contentCheck.push
        ({
               fontSize: fontSize,
               table: {
                   widths: [170, '*'],
                   headerRows: 1,
                   body: [
                       [
                           {
                               text: ['ESM - Date of Discharge / NOC /', {text: ' ई एस एम - निर्वहन की तारीख / एन ओ सी ', font: 'latha',}]
                           },
                           {text: returnStringIfNull(dataObj.esmDateOfDischarge)}
                       ]
                   ]
               },
               layout: 'noBorders'
           });
        contentCheck.push
        ({
               fontSize: fontSize,
               table: {
                   widths: [170, '*'],
                   headerRows: 1,
                   body: [
                       [
                           {
                               text: ['Duration of Service (in Years - Months - Days) /', {text: ' सेवा की अवधि  (वर्षों में - महीने - दिन)', font: 'latha',}]
                           },
                           {text: returnStringIfNull(dataObj.durationOfYears+' Years '+dataObj.durationOfMonths+' Months '+dataObj.durationOfDays+' Days')}
                       ]
                   ]
               },
               layout: 'noBorders'
           });
        }
        contentCheck.push
        ({
               fontSize: fontSize,
               table: {
                   widths: [170, '*'],
                   headerRows: 1,
                   body: [
                       [
                           {
                               text: ['Date of Birth /', {text: ' जन्म की तारीख', font: 'latha',}]
                           },
                           {text: returnStringIfNull(dataObj.personalDetailsBean.dateOfBirth)}
                       ]
                   ]
               },
               layout: 'noBorders'
           });
        contentCheck.push
        ({
               fontSize: fontSize,
               table: {
                   widths: [170, '*'],
                   headerRows: 1,
                   body: [
                       [
                           {
                               text: ['Age as on 01/07/2022 / ', {text: 'आयु 01/07/2022 तक ', font: 'latha',}]
                           },
                           {text: returnStringIfNull(dataObj.personalDetailsBean.age)}
                       ]
                   ]
               },
               layout: 'noBorders'
           });
   contentCheck.push
   ({
           fontSize: 10,
           margin: [0, 10, 0, 0],
           table: {
               widths: ['*'],
               headerRows: 1,
               body: [
               	  [
                         {
                             text: [{
                                 text: 'Section - III /'
                             }, {
                                  text: ' अनुभाग - ३', font: 'latha', 
                             }]
                         },
                     ]
               ]
           },
           layout: 'noBorders'

       });
   
   if(dataObj.fathersName != null){
       contentCheck.push({
           table: {
               widths: [170, '*'],
               headerRows: 1,
               body: [
                   [
                       {text: ['Father\'s or Husband\'s Name /', {text: ' पिता अथवा पति का नाम', font: 'latha',}]},
                       [
                           {text: returnStringIfNull(dataObj.fathersName)}
                       ],
                   ]
               ]
           },
           layout: 'noBorders'
       });
        }
        if(dataObj.mothersName != null){
       contentCheck.push({
           table: {
               widths: [170, '*'],
               headerRows: 1,
               body: [
                   [
                       {text: ['Mother\'s Name /', {text: ' माता का नाम', font: 'latha',}]},
                       [
                           {text: returnStringIfNull(dataObj.mothersName)}
                       ],
                   ]
               ]
           },
           layout: 'noBorders'
       });
        }
   
        contentCheck.push
        ({
                fontSize: 9,
                margin: [0, 10, 0, 0],
                table: {
                    widths: ['*'],
                    headerRows: 1,
                    body: [
                    	  [
                              {text: ['Permanent Address (Home) /', {text: ' स्थायी पता', font: 'latha',}]},
                          ]
                    ]
                },
                layout: 'noBorders'

            });
   
        contentCheck.push({
            table: {
                widths: [170, 320],
                headerRows: 1,
                body: [
                    [
                        {text: ['Permanent Address Line 1 /', {text: ' स्थायी पता पंक्ति 1', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.addressBean.addressFiled1)},
                            
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        }); 
        if (dataObj.addressBean.addressFiled2 != null && dataObj.addressBean.addressFiled2 != '') {
        contentCheck.push({
            table: {
                widths: [170, 320],
                headerRows: 1,
                body: [
                    [
                        {text: ['Permanent Address Line 2 /', {text: ' स्थायी पता पंक्ति 2', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.addressBean.addressFiled2)},
                            
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        }
        contentCheck.push( {
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {
                            text: ['Permanent Address - State / Union Territory / ', {text: 'स्थाई पता - राज्य / केंद्र शासित प्रदेश', font: 'latha',}]
                        },
                        [
                            {text: returnStringIfNull(dataObj.stateValDesc)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        },    {
        table: {
            widths: [170, '*'],
            headerRows: 1,
            body: [
                [
                	{	 
                        text: ['Permanent Address - District / ', {text: 'स्थाई पता - जिला', font: 'latha',}]
                    },
                    [
                        {text: returnStringIfNull(dataObj.districtVal)}
                    ],
                ]
            ]
        },
        layout: 'noBorders'
    },
    {
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {	 
                            text: ['Permanent Address - City / Village / Town /', {text: ' स्थायी पता - शहर / गांव / कस्बा ', font: 'latha',}]
                        },
                        [
                            {text: returnStringIfNull(dataObj.cityNameother)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        },  {
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {
                            text: ['Permanent Address - Pincode /', {text: 'स्थाई पता - पिनकोड', font: 'latha',}]
                        },
                        [
                            {text: returnStringIfNull(dataObj.addressBean.pinCode)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        
        contentCheck.push
        ({
                fontSize: 9,
                margin: [0, 10, 0, 0],
                table: {
                    widths: ['*'],
                    headerRows: 1,
                    body: [
                    	  [
                              {text: ['Correspondence Address /', {text: ' पत्राचार का पता', font: 'latha',}]},
                          ]
                    ]
                },
                layout: 'noBorders'

            });
        contentCheck.push({
            table: {
                widths: [170, 320],
                headerRows: 1,
                body: [
                    [
                        {text: ['Correspondence Address Line 1 /', {text: ' पत्राचार का पता - पता पंक्ति १', font: 'latha',}]},
                        [
                            {text: dataObj.addressBean.alternateAddressFiled1 == null ? ' ' : dataObj.addressBean.alternateAddressFiled1},
                            
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        }); 
        if (dataObj.addressBean.alternateAddressFiled2 != null && dataObj.addressBean.alternateAddressFiled2 != '') {
        contentCheck.push({
            table: {
                widths: [170, 320],
                headerRows: 1,
                body: [
                    [
                        {text: ['Correspondence Address Line 2 /', {text: ' पत्राचार का पता - पता पंक्ति 2', font: 'latha',}]},
                        [
                            {text: dataObj.addressBean.alternateAddressFiled2 == null ? ' ' : dataObj.addressBean.alternateAddressFiled2},
                            
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        }
        contentCheck.push({
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {
                            text: ['Correspondence Address - State / Union Territory /', {text: ' पत्राचार का पता - राज्य / केंद्र शासित प्रदेश', font: 'latha',}]
                        },
                        [
                            {text: dataObj.altStateValDesc == null ? ' ' : dataObj.altStateValDesc}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        }, 
        
        {
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                    	{	 
                            text: ['Correspondence Address - District /', {text: ' पत्राचार का पता - जिला', font: 'latha',}]
                        },
                        [
                            {text: returnStringIfNull(dataObj.altDistrictVal)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        },
        {
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                    	{	 
                            text: ['Correspondence Address - City / Village / Town /', {text: ' पत्राचार का पता - शहर / गाँव / कस्बा', font: 'latha',}]
                        },
                        [
                            {text: returnStringIfNull(dataObj.alternateCityother)}
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        }, {
            table: {
                widths: [170, '*'],
                headerRows: 1,
                body: [
                    [
                        {
                            text: ['Correspondence Address - Pincode /', {text: ' पत्राचार का पता - पिनकोड', font: 'latha',}]
                        },
                        [
                            {
                                text: dataObj.addressBean.alternatePinCode == null ? ' ' : dataObj.addressBean.alternatePinCode
                                
                            }
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
        
            contentCheck.push
            ({
                    fontSize: 14,
                    margin: [0, 20, 0, 0],
                    table: {
                        widths: ['*'],
                        headerRows: 1,
                        body: [
                        	  [
                                  {
                                      text: [{
                                          text: 'Educational Qualification / '
                                      }, {
                                           text: 'शैक्षिक योग्यता', font: 'latha', 
                                      }]
                                  },
                              ]
                        ]
                    },
                    layout: 'noBorders'

                });
    
    var tableDataAcademicDetails = [];
    tableDataAcademicDetails.push([
    	{
        	text: [{
                text: 'Educational Qualification / '
            }, {
                text: 'शैक्षिक योग्यता',
                font: 'latha',
            }]
        },
        {
        	text: [{
                text: 'Trade / '
            }, {
                text: 'ट्रेड',
                font: 'latha',
            }]
        },   
        {
        	 text: [{
                 text: 'Board Name / University Name / '
             }, {
                 text: 'बोर्ड का नाम / विश्वविद्यालय का नाम',
                 font: 'latha',
             }]
        },
        {
        	  text: [{
                  text: 'Month & Year Of Passing / '
              }, {
                  text: 'महीना और वर्ष का समय',
                  font: 'latha',
              }]
        },
        {
        	   text: [{
                   text: 'Result Status / '
               }, {
                   text: 'परिणाम स्थिति',
                   font: 'latha',
               }]
        },
        {
     	   text: [{
                text: 'Roll Number / '
            }, {
                text: 'रोल नंबर',
                font: 'latha',
            }]
     },
        {
 	   text: [{
            text: 'Certificate / Marksheet Serial No / '
        }, {
            text: 'प्रमाण पत्र / अंकपत्र क्रमांक',
            font: 'latha',
        }]
 }
    ])
    console.log(typeof dataObj.educationDtlsList);
    console.log(dataObj.educationDtlsList.length);
    var i;
    if (typeof dataObj.educationDtlsList == 'object') {
    	   for (var i = 0; i < 2; i++) {
    	if(dataObj.educationDtlsList[i]['nameOfUniv'] == 'Other'){
			 var otherUniversity = ' - '+dataObj.educationDtlsList[i]['universityOth'];
		 } else  if(dataObj.educationDtlsList[i]['nameOfUniv'] == 'Other - NA'){
			 var otherUniversity = ' - '+dataObj.educationDtlsList[i]['universityOth'];
		 } else{
			 var otherUniversity = null;
		 }
    	 tableDataAcademicDetails.push([
             {
                 text: [{
                     text: returnStringIfNull(dataObj.educationDtlsList[i]['examination']),
                     bold: true
                 }],
             },
             {
					text: [{
                     
             		text: returnStringIfNull(dataObj.educationDtlsList[i]['degreeSubject']),
							bold: true }]
             },
             {
                 text: [{
                     
             		text: returnStringIfNull(dataObj.educationDtlsList[i]['nameOfUniv']),
							bold: true },
						 {
							text: otherUniversity,
							bold: true
						}]
             },
             {
                 text: [{
                     text: dataObj.educationDtlsList[i]['dateOfPassing'],
                     bold: true
                 }]
             },
             {
                 text: [{
                     text:returnStringIfNull(dataObj.educationDtlsList[i]['resultStatus']),
                     bold: true
                 }]
             },
             {
                 text: [{
                     text: dataObj.educationDtlsList[i]['registrationNo'],
                     bold: true
                 }],
             },
             {
                 text: [{
                     text: dataObj.educationDtlsList[i]['certiSerialNum'],
                     bold: true
                 }],
             }
         ])
      }
    }
    contentCheck.push({
        table: {
            dontBreakRows: true, keepWithHeaderRows: 1,
            widths: ['15%','15%','15%','15%','15%','10%','15%'],
            headerRows: 1,
            body: tableDataAcademicDetails
        }
    });
    
    contentCheck.push
    ({
            fontSize: 10,
            margin: [0, 10, 0, 0],
            table: {
                widths: ['*'],
                headerRows: 1,
                body: [
                	  [
                          {
                              text: [{
                                  text: 'Preferential Qualification / '
                              }, {
                                   text: 'श्रेष्ठ अर्हता', font: 'latha', 
                              }]
                          },
                      ]
                ]
            },
            layout: 'noBorders'

        });
    
    contentCheck.push({
        table: {
            widths: [170, 320],
            headerRows: 1,
            body: [
                [
                    {text: ['Have you obtained "O" level certificate in Computer from DOEACC or NIELIT Society? /', 
                    		{text: ' क्या आपने ', font: 'latha',},{ text: 'DOEACC ',},{text: 'या ', font: 'latha',},{ text: 'NIELIT ', },{ text: 'सोसाइटी से कंप्यूटर में "ओ" स्तर का प्रमाणपत्र प्राप्त किया है?',font: 'latha',}]},
                    [
                        {text: returnStringIfNull(dataObj.doeacc)},
                        
                    ],
                ]
            ]
        },
        layout: 'noBorders'
    });
    contentCheck.push({
            table: {
                widths: [170, 320],
                headerRows: 1,
                body: [
                    [
                        {text: ['Have you served in the Territorial Army for a minimum period of two years? /', {text: ' क्या आपने दो साल की न्यूनतम अवधि के लिए प्रादेशिक सेना में काम किया है?', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.terriArmy)},
                            
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
    contentCheck.push({
            table: {
                widths: [170, 320],
                headerRows: 1,
                body: [
                    [
                        {text: ['Have you obtained a \'B\' certificate of National Cadet Corps? /', {text: ' क्या आपने राष्ट्रीय कैडेट कोर का "बी" प्रमाण पत्र प्राप्त किया है?', font: 'latha',}]},
                        [
                            {text: returnStringIfNull(dataObj.certiB)},
                            
                        ],
                    ]
                ]
            },
            layout: 'noBorders'
        });
       
    console.log("ACADEMIC", JSON.parse(JSON.stringify(contentCheck)));
           contentCheck.push({
        fontSize: 14,
        margin: [0, 20, 0, 0],
        table: {
            widths: ['*'],
            headerRows: 1,
            body: [
                [
                	{
                        text: [{
                            text: 'Upload Documents /'
                        }, {
                             text: 'दस्तावेज़ अपलोड करें', font: 'latha', 
                        }]
                    },
                ]
            ]
        },
        layout: 'noBorders'
    }) 
var tableDataDOCDetails = [];
           tableDataDOCDetails.push([
          
          {
              text: [{
                  text: ' 	Document Name '
              }],
          },
          {
              text: [{
                  text: ' 	File Name  '
              }]
          }
          
          ])
     console.log(typeof dataObj.uploadList);
          console.log(dataObj.uploadList.length);
          /* var tableDataDOCDetails = []; */
       if (typeof dataObj.uploadList == 'object') {
              for (var i = 0; i < dataObj.uploadList.length; i++) {
            	  
            	  if(dataObj.uploadList[i]['ocdFlagValue'] == 'SSLC'){
                	  tableDataDOCDetails.push([
                          
                		  {   text: ['10th / SSC / Other Equivalent Mark-sheet / Certificate / ', {text: '10 वीं / एसएससी / अन्य समतुल्य मार्क-शीट या प्रमाण पत्र', font: 'latha',}]
                		  },
                		  {  text: [{
                              text: returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),
                             
                          }],
            		  }
                  		])
                  }
                	  if(dataObj.uploadList[i]['ocdFlagValue'] == 'DIP'){
                    	  tableDataDOCDetails.push([
                              
                    		  {   text: ['Diploma Certificate / ', {text: 'डिप्लोमा प्रमाणपत्र', font: 'latha',}]
                    		  },
                    		  {  text: [{
                                  text: returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),
                                 
                              }],
                		  }
                      		])
                      }
                	  if(dataObj.uploadList[i]['ocdFlagValue'] == 'OC'){
                    	  tableDataDOCDetails.push([
                              
                    		  {   text: ['Category Certificate or EWS Certificate as applicable / ', {text: 'श्रेणी प्रमाण पत्र या  ईडब्ल्यूएस प्रमाणपत्र  जो भी लागू हो', font: 'latha',}]
                    		  },
                    		  {  text: [{
                                  text: returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),
                                 
                              }],
                		  }
                      		])
                      		
                      }
                	  
                	  if(dataObj.uploadList[i]['ocdFlagValue'] == 'EX'){
                    	  tableDataDOCDetails.push([
                              
                    		  {   text: ['Ex-Servicemen Discharge Certificate / NOC / ', {text: 'भूतपूर्व सैनिक निर्वहन प्रमाण पत्र / अनापत्ति प्रमाण पत्र (एनओसी)', font: 'latha',}]
                    		  },
                    		  {  text: [{
                                  text: returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),
                                 
                              }],
                		  }
                      		])
                      		
                      }
                	  
                	  if(dataObj.uploadList[i]['ocdFlagValue'] == 'NOC'){
                    	  tableDataDOCDetails.push([
                              
                    		  {   text: ['NOC from the Competent Authority / ', {text: 'सक्षम प्राधिकारी से एनओसी', font: 'latha',}]
                    		  },
                    		  {  text: [{
                                  text: returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),
                                 
                              }],
                		  }
                      		])
                      		
                      }
                	  
                	  if(dataObj.uploadList[i]['ocdFlagValue'] == 'DOM'){
                    	  tableDataDOCDetails.push([
                              
                    		  {   text: ['Domicile Certificate / ' , {text: 'मूल निवासी  प्रमाणपत्र', font: 'latha',} ]
                    		  },
                    		  {  text: [{
                                  text: returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),
                                 
                              }],
                		  }
                      		])
                      		
                      }
                	  
                	  if(dataObj.uploadList[i]['ocdFlagValue'] == 'DFF'){
                    	  tableDataDOCDetails.push([
                              
                    		  {   text: ['Dependent of Freedom Fighter / ' , {text: 'स्वतंत्रता सेनानी के आश्रित', font: 'latha',} ]
                    		  },
                    		  {  text: [{
                                  text: returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),
                                 
                              }],
                		  }
                      		])
                      		
                      }
                	  
                	  if(dataObj.uploadList[i]['ocdFlagValue'] == 'OCERT'){
                    	  tableDataDOCDetails.push([
                              
                    		  {   text: ['O level Certificate in Computer from DOEACC or NIELIT Society / ' , 
                    			  {text: 'क्या आपने ', font: 'latha',},{
                                      text: ' DOEACC / NIELIT ',
                                  },{
                                      text: ' सोसायटी से कंप्यूटर में\'ओ\' लेवल सर्टिफिकेट',
                                      font: 'latha',
                                  }]
                    		  },
                    		  {  text: [{
                                  text: returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),
                                 
                              }],
                		  }
                      		])
                      		
                      }
                	  if(dataObj.uploadList[i]['ocdFlagValue'] == 'BNCC'){
                    	  tableDataDOCDetails.push([
                              
                    		  {   text: ['B certificate of National Cadet Corps / ', {text: 'राष्ट्रीय कैडेट कोर का "बी" प्रमाण पत्र', font: 'latha',}]
                    		  },
                    		  {  text: [{
                                  text: returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),
                                 
                              }],
                		  }
                      		])
                      }
                	  if(dataObj.uploadList[i]['ocdFlagValue'] == 'TASC'){
                    	  tableDataDOCDetails.push([
                              
                    		  {   text: ['Territorial Army Service Certificate / ', {text: 'भूतपूर्व सैनिक सेवा-मुक्‍ति प्रमाणपत्र / दस्तावेज़', font: 'latha',}]
                    		  },
                    		  {  text: [{
                                  text: returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),
                                 
                              }],
                		  }
                      		])
                      }
            	  if(dataObj.uploadList[i]['ocdFlagValue'] == 'EWS'){
                	  tableDataDOCDetails.push([
                          
                		  {   text: ['EWS Self Declaration / ', {text: 'आर्थिक रूप से कमज़ोर वर्ग (ई.डबल्यू.एस.) स्व घोषणा', font: 'latha',}]
                		  },
                		  {  text: [{
                              text: returnStringIfNull(dataObj.uploadList[i]['documentFileName1']),
                             
                          }],
            		  }
                  		])
                  }
              }
          }
          contentCheck.push({
              table: {
                  dontBreakRows: true, keepWithHeaderRows: 1,
                  widths: ['40%','40%'],
                  headerRows: 1,
                  body: tableDataDOCDetails
              }
          });
    /*End of Academic details*/

 contentCheck.push
 ({
         fontSize: 14,
         margin: [0, 20, 0, 0],
         table: {
             widths: ['*'],
             headerRows: 1,
             body: [
             	  [
                       {
                           text: [{
                               text: 'Declaration /'
                           }, {
                                text: 'घोषणा', font: 'latha', 
                           }]
                       },
                   ]
             ]
         },
         layout: 'noBorders'

     });
 
 ///whole declaration is under condition
 if(dataObj.categoryVal != null && dataObj.categoryVal != '' && (dataObj.categoryVal == "OBC-NCL" || dataObj.categoryVal == "SC" || dataObj.categoryVal == "ST" )){
        contentCheck.push({
            table: {
                widths: ['*'],
                headerRows: 0,
                body: [
                    	
							[ {
								text : [ 'I hereby declare that I have read all the provisions in the detailed notice/Notification available on website  http://www.NCFE.in of the examination carefully and hereby undertake to abide by them.', 
									{text: ' / मैं एतदद्वारा घोषणा करता/करती हूं कि मैंने परीक्षा हेतु निर्गत विस्तृत सूचना /विज्ञप्ति जो कि वेबसाइट' ,font: 'latha'},' http://www.NCFE.in', {text:' पर  उपलब्ध है, मे उल्लिखित सभी प्रावधानों को ध्यान से पढ़ लिया है तथा उन प्रावधानों का पालन करने का वचन देता/देती हूँ।', font: 'latha',} ],
							} ],
							[ {
								text : [ 'I have studied and understood the notification and found myself eligible for applying to this/these posts. This is not the duplicate registration if found duplicate my entire candidature may be cancelled.' , 
									{text: ' / मैंने अधिसूचना का अध्ययन किया है और समझ लिया है और इस/इन पद/पदों के लिए आवेदन करने के लिए स्वयं को योग्य पाया है। यह अनुलिपित (डुप्लिकेट) पंजीकरण नहीं है, यदि अनुलिपित पाया गया तो मेरी पूरी उम्मीदवारी रद्द हो सकती है।', font: 'latha',} ],
							} ],
							[ {
								text : [ 'I declare that I fulfill all the conditions of eligibility for nationality, age limit, educational qualification etc. as specified.' , 
									{text: ' / मैं घोषणा करता/करती हूं कि मैं राष्ट्रीयता, आयु सीमा, शैक्षणिक योग्यता इत्यादि की यथानिरदिष्ट पात्रता की सभी शर्तो को पूर्ण करता/करती हूँ।', font: 'latha',} ],
							} ],
							[ {
								text : [ 'I declare that my claim for reservation as a Scheduled Caste Scheduled Tribe candidate is true and correct and I hold the caste certificate in the format given in the information release.' , 
									{text: ' / मैं घोषणा करता/करती हूं कि अनुसूचित जाति अनुसूचित जनजाति के उम्मीदवार के रूप में आरक्षण के लिए मेरा दावा सत्य एवं सही है तथा मै सूचना विज्ञप्ति में दिए गए प्रारूप में जाति प्रमाणपत्र धारित करता/करती हूँ।', font: 'latha',} ],
							} ],
							[ {
								text : [ 'OR' ],
							} ],
							[ {
								text : [ 'I declare that my claim as an OBC candidate is genuine and true and I hold the caste certificate in the format given in the information release issued after 01 April 2021. I also declare that as per Government of Uttar Pradesh I do not belong to the category belonging to the creamy layer of OBC mentioned in the order issued by the government.' , 
									{text: ' / मैं घोषणा करता हूं कि एक ओबीसी उम्मीदवार के रूप में मेरा दावा सही और सत्य है और मेरे पास 01 अप्रैल 2021 के बाद जारी की गई सूचना विज्ञप्ति में दिए गए प्रारूप में जाति प्रमाण पत्र है ।  मैं यह भी घोषणा करता हूं कि उत्तर प्रदेश सरकार के अनुसार, मैं उस श्रेणी में नहीं आता जो सरकार द्वारा जारी आदेशों में उल्लिखित ओबीसी क्रीमी लेयर से संबंधित है।', font: 'latha',}  ],
							} ],
							[ {
								text : [ 'I declare that I am Unmarried/widow/widower or  I have only one spouse living and I also declare that I have not married a woman/man who already has spouse living.' , 
									{text: ' / मैं घोषणा करता /करती हूं कि मैं अविवाहित / विधवा / विधुर हूं या  मेरे पास केवल एक ही जीवनसाथी है और मैं यह भी घोषणा करता /करती हूं कि मैंने उस महिला / पुरुष के साथ विवाह नहीं किया है जिसके पास पहले से ही जीवनसाथी है। ', font: 'latha',}  ],
							} ],
							[ {
								text : [ 'I declare that I have never been convicted by any court established by law.' , 
									{text: ' / मैं  घोषणा करता/करती हूं की मुझे विधि द्वारा स्थापित किसी भी न्यायालय के द्वारा कभी भी दोषी नही ठहराया गया है। ', font: 'latha',}  ],
							} ],
							[ {
								text : [ 'I declare that I have never been dismissed or removed from government service, nor have my services been terminated during probation.' , 
									{text: ' / मैं  घोषणा करता/करती हूं कि मुझे सरकारी सेवा से कभी भी बर्खास्त या हटाया नही गया है, तथा न ही परिवीक्षा के दौरान मेरी सेवाओ को समाप्त किया गया है। ', font: 'latha',}  ],
							} ],
							[ {
								text : [ 'I declare that all the entries / statements / document made in this application form are genuine, complete and correct to the best of my knowledge and belief, and I meet all the eligibility criteria as per the prescribed rules / notification. Am In case of any information being found to be false or incorrect or ineligible, I can take action against me at any time before my selection or after selection as per the rules. If it is found that I have misled the Board or the appointing authority on any issue, then I shall be fully responsible for it which will include legal proceedings along with other punitive proceedings and consequently all the punishments.' , 
									{text: ' / मैं  घोषणा करता/करती हूं कि इस आवेदन पत्र में कि गई समस्त प्रविष्टियां /कथन / अभिलेख  मेरे ज्ञान और विश्वास के अनुसार वास्तविक, पूर्ण और सही हैं, तथा मै निर्धारित नियम/अधिसूचना के अनुसार पात्रता के सभी मापदंडों  को पूरा करता/ करती हूं। किसी भी सूचना के झूठा या गलत पाये जाने अथवा अपात्रता की स्थिति में, मेरे चयन से पूर्व या चयन के उपरांत कभी भी नियमो के अनुसार मेरे विरूद्ध कार्यवाही कर सकता है, यदि यह पता चलता है कि मैने किसी भी मुद्दे पर बोर्ड या नियुक्ति प्राधिकारी को गुमराह किया है, तो इसके लिए मैं पूरी तरह से स्वयं जिम्मेदार होऊंगा जिसमे अन्य दंडात्मक कार्यवाही के साथ विधिक कार्यवाही भी सम्मिलित होगी तथा उसके परिणाम स्वरूप सभी दंडो का भागीदार होऊंगा।', font: 'latha',}  ],
							} ],
							
                	]//body
            },
            layout: 'noBorders'
        });
 }
 else if(dataObj.categoryVal != null && dataObj.categoryVal != '' && (dataObj.categoryVal == "UR" || dataObj.categoryVal == "EWS"))
	 {
	 contentCheck.push({
         table: {
             widths: ['*'],
             headerRows: 0,
             body: [
                 	
							[ {
								text : [ 'I hereby declare that I have read all the provisions in the detailed notice/Notification available on website  http://www.NCFE.in of the examination carefully and hereby undertake to abide by them.', 
									{text: ' / मैं एतदद्वारा घोषणा करता/करती हूं कि मैंने परीक्षा हेतु निर्गत विस्तृत सूचना /विज्ञप्ति जो कि वेबसाइट' ,font: 'latha'},'  http://www.NCFE.in', {text:' पर  उपलब्ध है, मे उल्लिखित सभी प्रावधानों को ध्यान से पढ़ लिया है तथा उन प्रावधानों का पालन करने का वचन देता/देती हूँ।', font: 'latha',} ],
							} ],
							[ {
								text : [ 'I have studied and understood the notification and found myself eligible for applying to this/these posts. This is not the duplicate registration if found duplicate my entire candidature may be cancelled.' , 
									{text: ' / मैंने अधिसूचना का अध्ययन  करके  समझ लिया है और इस/इन पद/पदों के लिए आवेदन करने के लिए स्वयं को योग्य पाया है। यह अनुलिपित (डुप्लिकेट) पंजीकरण नहीं है, यदि अनुलिपित पाया गया तो मेरी पूरी उम्मीदवारी रद्द हो सकती है।', font: 'latha',} ],
							} ],
							[ {
								text : [ 'I declare that I fulfill all the conditions of eligibility for nationality, age limit, educational qualification etc. as specified.' , 
									{text: ' / मैं घोषणा करता/करती हूं कि मैं राष्ट्रीयता, आयु सीमा, शैक्षणिक योग्यता इत्यादि की यथानिरदिष्ट पात्रता की सभी शर्तो को पूर्ण करता/करती हूँ।', font: 'latha',} ],
							} ],
							[ {
								text : [ 'I declare that I am Unmarried/widow/widower or  I have only one spouse living and I also declare that I have not married a woman/man who already has spouse living.' , 
									{text: ' / मैं घोषणा करता /करती हूं कि मैं अविवाहित / विधवा / विधुर हूं या  मेरे पास केवल एक ही जीवनसाथी है और मैं यह भी घोषणा करता /करती हूं कि मैंने उस महिला / पुरुष के साथ विवाह नहीं किया है जिसके पास पहले से ही जीवनसाथी है। ', font: 'latha',}  ],
							} ],
							[ {
								text : [ 'I declare that I have never been convicted by any court established by law.' , 
									{text: ' / मैं  घोषणा करता/करती हूं की मुझे विधि द्वारा स्थापित किसी भी न्यायालय के द्वारा कभी भी दोषी नही ठहराया गया है। ', font: 'latha',}  ],
							} ],
							[ {
								text : [ 'I declare that I have never been dismissed or removed from government service, nor have my services been terminated during probation.' , 
									{text: ' / मैं  घोषणा करता/करती हूं कि मुझे सरकारी सेवा से कभी भी बर्खास्त या हटाया नही गया है, तथा न ही परिवीक्षा के दौरान मेरी सेवाओ को समाप्त किया गया है। ', font: 'latha',}  ],
							} ],
							[ {
								text : [ 'I declare that all the entries / statements / document made in this application form are genuine, complete and correct to the best of my knowledge and belief, and I meet all the eligibility criteria as per the prescribed rules / notification. Am In case of any information being found to be false or incorrect or ineligible, I can take action against me at any time before my selection or after selection as per the rules. If it is found that I have misled the Board or the appointing authority on any issue, then I shall be fully responsible for it which will include legal proceedings along with other punitive proceedings and consequently all the punishments.' , 
									{text: ' / मैं  घोषणा करता/करती हूं कि इस आवेदन पत्र में कि गई समस्त प्रविष्टियां /कथन / अभिलेख  मेरे ज्ञान और विश्वास के अनुसार वास्तविक, पूर्ण और सही हैं, तथा मै निर्धारित नियम/अधिसूचना के अनुसार पात्रता के सभी मापदंडों  को पूरा करता/ करती हूं। किसी भी सूचना के झूठा या गलत पाये जाने अथवा अपात्रता की स्थिति में, मेरे चयन से पूर्व या चयन के उपरांत कभी भी नियमो के अनुसार मेरे विरूद्ध कार्यवाही कर सकता है, यदि यह पता चलता है कि मैने किसी भी मुद्दे पर बोर्ड या नियुक्ति प्राधिकारी को गुमराह किया है, तो इसके लिए मैं पूरी तरह से स्वयं जिम्मेदार होऊंगा जिसमे अन्य दंडात्मक कार्यवाही के साथ विधिक कार्यवाही भी सम्मिलित होगी तथा उसके परिणाम स्वरूप सभी दंडो का भागीदार होऊंगा।', font: 'latha',}  ],
							} ],
							
             	]//body
         },
         layout: 'noBorders'
     });
	 }
        contentCheck.push({
            margin: [0, 20, 0, 0],
            table: {
                widths: ['40%', '*'],
                headerRows: 1,
                body: [
                    [
                        ' ',
                        {
                            alignment: 'right',
                            image: 'signature',
                            width: '90',
                            height: 40
                        }
                    ],
                    [
                        {
                            text: 'Submitted Date : ' + (dataObj.regFormSubmitedDate != null ? dataObj.regFormSubmitedDate : ''),
                            bold: true
                        },
                        {
                            alignment: 'right',
                            text: [['(Signature of the Candidate', {text: ' / उम्मीदवार का हस्ताक्षर)', font: 'latha',}]]
                        }
                    ]
                ]
            },
            layout: 'noBorders'
        })
   
    /*End of additional details*/


    //console.log(JSON.stringify(contentCheck));

    for (var i = 0; i < contentCheck.length; i++) {
        if (typeof contentCheck[i]['table'] != "undefined") {
            contentCheck[i]['table']['dontBreakRows'] = true;
        }
    }
    console.log("CONTENT", contentCheck);


    var isArray = function (a) {
        return (!!a) && (a.constructor === Array);
    };

    var isObject = function (a) {
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
        for (var key in data) {
            console.log(data[key]);
            if (isObject(data[key])) {
                data[key] = iterateObject(data[key])
            }
            else if (isArray(data[key])) {
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
            console.log(data[i]);
            if (isObject(data[i])) {
                data[i] = iterateObject(data[i])
            }
            else if (isArray(data[i])) {
                data[i] = iterateArray(data[i])
            } else {
                console.log(data[i]);
                if (typeof data[i] == "string") {
                    data[i] = data[i].toUpperCase();
                }
            }
        }
        return data;
    }

    contentCheck = toUpperCaseData(JSON.parse(JSON.stringify(contentCheck)));


    var docDefinition = {
        content: contentCheck,
        defaultStyle: {
            fontSize: fontSize
        },
        images: {
            //logo: 'data:image/jpeg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAAAPAAD/4QMpaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjAtYzA2MCA2MS4xMzQ3NzcsIDIwMTAvMDIvMTItMTc6MzI6MDAgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDUzUgV2luZG93cyIgeG1wTU06SW5zdGFuY2VJRD0ieG1wLmlpZDoyREU2MEJEMjYyRkYxMUU3QjREMzk1QTIwRDBFNjBDMyIgeG1wTU06RG9jdW1lbnRJRD0ieG1wLmRpZDoyREU2MEJEMzYyRkYxMUU3QjREMzk1QTIwRDBFNjBDMyI+IDx4bXBNTTpEZXJpdmVkRnJvbSBzdFJlZjppbnN0YW5jZUlEPSJ4bXAuaWlkOjJERTYwQkQwNjJGRjExRTdCNEQzOTVBMjBEMEU2MEMzIiBzdFJlZjpkb2N1bWVudElEPSJ4bXAuZGlkOjJERTYwQkQxNjJGRjExRTdCNEQzOTVBMjBEMEU2MEMzIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+/+4ADkFkb2JlAGTAAAAAAf/bAIQAEw8PFxEXJRYWJS8kHSQvLCQjIyQs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',
            logo: dataObj.logoCast,
            aadhaar: dataObj.imageCast,
            signature: dataObj.signatureCast
        },
        footer: function (currentPage, pageCount) {
            return {
                margin: 10,
                columns: [
                    {
                        fontSize: 7,
                        text: [
                            {
                                //text: dataObj.jsonDate,
                            }
                        ],
                        alignment: 'left'
                    }, {
                        fontSize: 9,
                        text: [
                            {
                                text: currentPage.toString(),
                            }
                        ],
                        alignment: 'left'
                    }
                ]
            };
        },
        header: function (currentPage, pageCount) {
            return {
                margin: 10,
                columns: [
                    {
                        fontSize: 7,
                        text: [
                            {
                                //text: dataObj.jsonDate,
                            }
                        ],
                        alignment: 'right'
                    }
                ]
            };
        },
    };
    var pdfname = "Application Form View.pdf";
    if (dataObj.afterApplyVeiwPayment != null && dataObj.afterApplyVeiwPayment != "" && dataObj.afterApplyVeiwPayment == "true") {
        pdfname = "Application Form.pdf"
    }
    
/*     pdfMake.createPdf(docDefinition).download(pdfname, function () {
        setTimeout(function () {
            if ((navigator.userAgent.indexOf("MSIE") != -1) || !(!!window.MSInputMethodContext && !!document.documentMode)) {
                window.close()
            }
        }, 5000);
    });  */

     const pdfGenerator = pdfMake.createPdf(docDefinition);
    pdfGenerator.getBase64((data) => {
        $.ajax({ 
            url: "CandidateAction_createServerSidePDF.action",
            data: "base64Data="+data,
            type: 'POST',
            });
        setTimeout(function() {
            if ((navigator.userAgent.indexOf("MSIE") != -1) || !(!!window.MSInputMethodContext && !!document.documentMode)) {
                window.close()
            }
        }, 5000);
    }); 
}