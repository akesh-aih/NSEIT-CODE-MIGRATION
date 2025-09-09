	<div class="book-a-seat box-gradient" id="block900"
						style="display: none;">
						<div>
							<a href="javascript:void(0);"
								onclick="HideAll();callToSeatFunctionWithSeatStatus('Cancel')"><img
									src="images/Close.png" align="right" border="0" title="Close" />
							</a>
						</div>
						<div class="pad12">
							<div class="titleBox fl-left">
								<h1 class="pageTitle" title="Login">
									&nbsp;Test Details
								</h1>
							</div>
							<div class="closebtnBox fl-rigt"></div>
							<div class="hr-underline clear"></div>
							<br />

							<div class="fogot-cont">
								<div class="book-a-seat-top">
									The booking is done in real time hence you will be given ony
									ONE minute to confirm your booking
								</div>
								<table width="500" border="0" align="center" cellpadding="5"
									cellspacing="0">

									<tr>
										<td height="30" colspan="2" align="center" valign="top">
											<strong>Time remaining (sec): <span id="theTime"></span>
											</strong>
										</td>
										<td>
										</td>
									</tr>
									<tr>
										<td width="201" valign="top">
											User Id
										</td>
										<td width="329" valign="top">
											<strong><s:label value="%{userId}" /> </strong>
										</td>
									</tr>
									<tr>
										<td valign="top">
											Name
										</td>
										<td valign="top">
											<s:label value="%{candidateName}" />
										</td>
									</tr>
									<tr>
										<td valign="top">
											Test
										</td>
										<td valign="top">
											<s:property value="discipline" />
										</td>
									</tr>
									<tr>
										<td valign="top">
											Test Center
										</td>
										<td valign="top">
											<label id="lblSeatDivTstCntr"></label>
										</td>
									</tr>
									<tr>
										<td valign="top">
											Test Center Address
										</td>
										<td valign="top">
											<label id="lblSeatDivAddress"></label>
										</td>
									</tr>
									<tr>
										<td valign="top">
											Test Date
										</td>
										<td valign="top">
											<label id="lblSeatTestDate"></label>
										</td>
									</tr>
									<tr>
										<td>
											Test Time
										</td>
										<td>
											<label id="lblSeatTestTime"></label>
										</td>
									</tr>
									<tr>
										<td>
											&nbsp;
										</td>
										<td>
											&nbsp;
										</td>
									</tr>
									<tr>
										<td></td>
										<td>
											<input type="button" value="Confirm"
												class="submitBtn button-gradient" title="Submit"
												onclick="callToSeatFunctionWithSeatStatus('Confirm')" />
											&nbsp;&nbsp;
											<input type="button" value="Cancel"
												class="submitBtn button-gradient" title="Cancel"
												onclick="HideAll();callToSeatFunctionWithSeatStatus('Cancel')" />
										</td>
									</tr>
								</table>

							</div>
						</div>

					</div>