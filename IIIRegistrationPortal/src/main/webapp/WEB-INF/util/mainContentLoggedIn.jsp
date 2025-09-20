<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<div class="row">
    <div class="col-sm-3">
        <div class="row">
            <div class="col-sm-12">
                <div class="panel-group" id="accordionMenu" role="tablist" aria-multiselectable="true">
                    <%-- Dynamic menu will be rendered here --%>
                    <%-- For now, a placeholder --%>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingOne">
                            <h4 class="panel-title">
                                <a role="button" data-toggle="collapse" data-parent="#accordionMenu" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                    Menu Item 1
                                </a>
                            </h4>
                        </div>
                        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                            <div class="panel-body">
                                <ul class="nav">
                                    <li><a href="#">Sub-menu 1.1</a></li>
                                    <li><a href="#">Sub-menu 1.2</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                    <div class="panel panel-default">
                        <div class="panel-heading" role="tab" id="headingTwo">
                            <h4 class="panel-title">
                                <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordionMenu" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                    Menu Item 2
                                </a>
                            </h4>
                        </div>
                        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                            <div class="panel-body">
                                <ul class="nav">
                                    <li><a href="#">Sub-menu 2.1</a></li>
                                    <li><a href="#">Sub-menu 2.2</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-9"> <%-- @ClassName defaults to col-sm-9 --%>
        <div class="row">
            <tiles:insertAttribute name="body" />
        </div>
    </div>
</div>