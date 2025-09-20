<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row">
    <div class="col-sm-3">
        <div class="row">
            <div class="col-sm-12">
                <div id="pnlLoginBox" class="loginBox">
                    <form id="frmLogin" autocomplete="off">
                        <h3>Quick Login</h3>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <input id="txtUserId" name="txtUserId" type="text" class="form-control username" placeholder="User Id" required autocomplete="off" />
                                </div>
                            </div>
                        </div>
                        <div class="row mt10">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <input id="txtPassword" name="txtPassword" type="password" class="form-control password" placeholder="Password" required>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <p class="bottomLink"><a href="#" id="fp">Forgot Password?</a> | <a href="#" id="cp">Change Password?</a></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <input type="submit" id="btnLogin" class="btn btn-md btn-warning btn-block" value="Login" />
                            </div>
                        </div>
                    </form>
                </div>
                <div id="pnlForgotPassword" class="loginBox" style="display: none;">
                    <form id="frmForgotPassword" autocomplete="off">
                        <h3>Forgot Password</h3>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <input id="txtfpUserId" name="txtfpUserId" type="text" class="form-control username" placeholder="User Id" required maxlength="20" autocomplete="off" />
                                </div>
                            </div>
                        </div>
                        <div class="row mt10">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <input id="txtfpEmailId" name="txtfpEmailId" type="text" class="form-control" placeholder="Your e-mail id" required maxlength="50" autocomplete="off" />
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <p class="bottomLink"><a href="#" id="backToLogin">Back</a></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <input type="submit" id="btnResetPassword" class="btn btn-md btn-warning btn-block" value="Reset Password" />
                            </div>
                        </div>
                    </form>
                </div>
                <div id="pnlChangePassword" class="loginBox" style="display: none;">
                    <form id="frmChangePassword" autocomplete="off">
                        <h3>Change Password</h3>
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <input id="txtcpUserID" name="txtcpUserId" type="text" class="form-control username" placeholder="User Id" required autocomplete="off" />
                                </div>
                            </div>
                        </div>
                        <div class="row mt10">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <input id="txtcpOldPwd" name="txtcpOldPwd" type="password" class="form-control password" placeholder="Your old password" required>
                                </div>
                            </div>
                        </div>
                        <div class="row mt10">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <input id="txtcpNewPwd" name="txtcpNewPwd" type="password" class="form-control password" placeholder="New password" required>
                                </div>
                            </div>
                        </div>
                        <div class="row mt10">
                            <div class="col-sm-12">
                                <div class="form-group">
                                    <input id="txtcpConfirmPwd" name="txtcpConfirmPwd" type="password" class="form-control password" placeholder="Confirm password" required>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <p class="bottomLink"><a href="#" id="backToLogin2">Back</a></p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-sm-12">
                                <input type="submit" id="btnChangePassword" class="btn btn-md btn-warning btn-block" value="Change Password" />
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-6">
        <div class="row">
            <tiles:insertAttribute name="body" />
        </div>
    </div>
    <div class="col-sm-3">
        <div class="row">
            <div class="col-sm-12">
                <div class="exCenterBox">
                    <h3>Find Exam Center</h3>
                    <div class="row mt10">
                        <div class="col-sm-12">
                            <form id="frmSearchCenters" autocomplete="off">
                                <div class="input-group">
                                    <div>
                                        <input id="txtPincode" name="txtPincode" type="text" maxlength="6" class="form-control locationPin" placeholder="Enter your PIN Code" required autocomplete="off" />
                                    </div>
                                    <div class="input-group-btn" style="vertical-align:top">
                                        <button id="btnSearchCenters" class="btn btn-warning" type="submit"><img src="<s:url value="/images/searchBtn.png"/>" width="20" height="20" /></button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="row mt20">
                        <div class="col-sm-12">
                            <ul id="Nearest3Centers"></ul>
                            <p class="text-right"><!-- <a href="#">View All</a> --></p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="row mt20">
            <div class="col-sm-12">
                <div class="helpDeskBox">
                    <h3>Help Desk</h3>
                    <div class="row mt10">
                        <div class="col-sm-12">
                            <table width="100%" border="0" cellspacing="5" cellpadding="0">
                                <tr>
                                    <td width="35" align="left" valign="top"><img src="<s:url value="/images/telephone.png"/>" width="21" height="20"></td>
                                    <td>
                                        <p>
                                            022-62507740<br>
                                            022-42706500
                                        </p>
                                    </td>
                                </td>
                                <tr>
                                    <td width="35" align="left" valign="top"><img src="<s:url value="/images/mail_icon.png"/>" width="21" height="16"></td>
                                    <td><p><a href="mailto: tech.support@nseit.com" target="_blank" rel="noreferrer">tech.support@nseit.com</a></p></td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="left" valign="top"><h5>Timing</h5></td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="left" valign="top">
                                        <p>
                                            Monday To Saturday <br />
                                            9:30 am To 05:30 pm
                                        </p>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>