<!DOCTYPE html>
<html lang="en">

    <head>
        <meta name="layout" content="main" />
    </head>

    <body>
        <!-- Change link to resource file -->
        <link href="http://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.2/summernote.css" rel="stylesheet">
        <div class="row">
            <div class="col-lg-12">
                <h1 class="page-header"><g:message code="shop.create.label" /></h1>
            </div>
        </div>
        <g:render template="/shared/messages"/>
        <g:form method="post" enctype="multipart/form-data"  action="save" name="addForm" class="form-horizontal" role="form">
            <div class="panel panel-default">
                <div class="panel-heading"><g:message code="shop.create.panel.label"/></div>
                <div class="panel-body">
                    <%-- Image --%>
                    <div class="form-group required">
                        <label for="image" class="col-sm-2 control-label input-sm"><g:message code="shop.image.label" /></label>
                        <div class="col-sm-3">
                            <input type="file" name="image" />
                        </div>
                    </div>
                    <%-- Name --%>
                    <div class="form-group required">
                        <label for="name" class="col-sm-2 control-label input-sm required"><g:message code="shop.name.label" /></label>
                        <div class="col-sm-5">
                            <g:textField class="form-control input-sm" name="name" maxlength="100"/>
                        </div>
                    </div>
                    <%-- URL --%>
                    <div class="form-group">
                        <label for="url" class="col-sm-2 control-label input-sm"><g:message code="shop.url.label" /></label>
                        <div class="col-sm-5">
                           <g:textField class="form-control input-sm" name="url" maxlength="1000"/>
                        </div>
                    </div>
                    <%-- Redirect --%>
                    <div class="form-group">
                        <label for="redirect" class="col-sm-2 control-label input-sm"><g:message code="shop.redirect.label" /></label>
                        <div class="col-sm-5">
                            <g:checkBox class="input-sm" name="redirect"/>
                        </div>
                    </div>
                    <%-- Cover Image --%>
                    <div class="form-group">
                        <label for="coverImage" class="col-sm-2 control-label input-sm"><g:message code="shop.coverImage.label" /></label>
                        <div class="col-sm-5">
                            <g:checkBox class="input-sm" name="coverImage"/>
                        </div>
                    </div>
                    <%-- Short Description --%>
                    <div class="form-group">
                        <label for="shortDescription" class="col-sm-2 control-label input-sm"><g:message code="shop.shortDescription.label" /></label>
                        <div class="col-sm-5">
                            <textarea rows="4" class="form-control input-sm" name="shortDescription" maxlength="1000" row="4"></textarea>
                        </div>
                    </div>
                    <%-- Description --%>
                    <div class="form-group">
                        <label for="description" class="col-sm-2 control-label input-sm"><g:message code="shop.description.label" /></label>
                        <div class="col-sm-5">
                            <button type="button" id="editButton" class="btn btn-default hide-display"><g:message code="default.button.edit.label"/></button>
                            <button type="button" id="previewButton" class="btn btn-default"><g:message code="default.button.preview.label"/></button>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-2"></div>
                        <div class="col-sm-10" id="rawContent">
                        </div>
                        <div class="col-sm-10 pull-right" id="editContent">
                            <g:textArea id="summernote" name="description" maxlength="100000"/>
                        </div>
                    </div>
                </div>
            </div>
            <div class="form-actions">
                <a href="${createLink(controller:'shop', action: 'list')}" class="btn btn-default"><g:message code="default.button.back.label"/></a>
                <g:actionSubmit class="btn btn-default" action="save" value="${message(code: 'default.button.save.label')}" />
            </div>
        </g:form>

        <g:render template="/shared/summernote"/>

        <script>
            $(document).ready(function() {

                $('[name=addForm]').bootstrapValidator({
                    excluded: ':disabled',
                    message: 'This value is not valid',
                    feedbackIcons: {
                        valid: 'glyphicon glyphicon-ok',
                        invalid: 'glyphicon glyphicon-remove',
                        validating: 'glyphicon glyphicon-refresh'
                    },
                    fields: {
                        name: {
                            validators: {
                                notEmpty: {
                                    message: 'The shop name is required and cannot be empty'
                                }
                            }
                        }
                    }
                });

                $("#editButton").click(function() {
                    $("#rawContent").hide();
                    $("#editContent").show();
                    $("#editButton").hide();
                    $("#previewButton").show();
                });
                $("#previewButton").click(function() {
                    var updatedDescription = $('#summernote').summernote('code');
                    $("#rawContent").empty();
                    $("#rawContent").append($('#summernote').summernote('code'));

                    $("#rawContent").show();
                    $("#editContent").hide();
                    $("#editButton").show();
                    $("#previewButton").hide();
                });
            });
        </script>
    </body>

</html>
