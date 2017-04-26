<!DOCTYPE html>
<html lang="en">

    <head>
        <meta name="layout" content="main" />
    </head>

    <body>
        <g:render template="/shared/messages"/>
        <div class="card">
            <input type="hidden" value="${shop.id}" id="id"/>
            <div class="panel panel-default">
            <div class="panel-heading"><g:message code="shop.view.panel.label"/></div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-lg-6 col-sm-6 col-xs-6">
                        <img src="${createLink(action: 'renderImage', controller:'image')}/${shop.image.generatedName}" class="viewImage">
                    </div>
                    <div class="col-lg-6 col-sm-6 col-xs-6">
                        <h3 class="product-title">${shop.name}</h3>
                        <g:if test="${shop.url}">
                            <a href="${shop.url}">Go to Official Website</a>
                        </g:if>
                        <p class="product-description">${shop.shortDescription}</p>
                    </div>
                </div>
                <br/>
                <br/>
                    <div class="container-fluid" style="overflow: scroll;">
                        ${raw(shop.description)}
                    </div>
            </div>
            </div>
        </div>
    </body>

</html>
