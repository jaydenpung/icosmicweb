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
                <div class="container-fluid">
                    <div class="wrapper row">
                        <div class="preview col-md-6">
                            <div class="preview-pic tab-content">
                                <div class="tab-pane active" id="pic-1">
                                    <img src="${createLink(action: 'renderImage', controller:'image')}/${shop.image.generatedName}" width="500px" height="350px"/>
                                </div>
                            </div>
                        </div>
                        <div class="details col-md-6">
                            <h3 class="product-title">${shop.name}</h3>
                            <g:if test="${shop.url}">
                                <a href="${shop.url}">Go to Official Website</a>
                            </g:if>
                            <p class="product-description">${shop.shortDescription}</p>
                        </div>
                    </div>
                </div>
                <br/>
                <br/>
                <div class="container-fluid">
                    ${raw(shop.description)}
                </div>
            </div>
            </div>
        </div>
    </body>

</html>
