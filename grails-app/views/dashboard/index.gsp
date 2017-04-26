<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">
        <meta name="layout" content="main" />
    </head>

    <body>
        <div class="row carousel-holder">
            <div class="col-md-12">
                <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
                    <ol class="carousel-indicators">
                        <g:each in="${(0..coverShops.size()-1).toList()}" var="i" >
                            <g:if test="${i == 0}">
                                <li data-target="#carousel-example-generic" data-slide-to="${i}" class="active"></li>
                            </g:if>
                            <g:else>
                                <li data-target="#carousel-example-generic" data-slide-to="${i}"></li>
                            </g:else>
                        </g:each>
                    </ol>
                    <div class="carousel-inner" align="center">
                        <g:set var="first" value="true"/>
                        <g:each var="shop" in="${coverShops}">
                            <g:if test="${first == 'true'}">
                                <div class="item active">
                                <a href="${createLink(controller:'dashboard', action: 'viewShop', params: [id: shop.id])}">
                                     <img src="${resource(file: shop.image.path)}">
                                </a>
                                <g:set var="first" value="false"/>
                            </div>
                            </g:if>
                            <g:else>
                                <div class="item">
                                    <a href="${createLink(controller:'dashboard', action: 'viewShop', params: [id: shop.id])}">
                                         <img src="${resource(file: shop.image.path)}">
                                    </a>
                                </div>
                            </g:else>
                        </g:each>
                    </div>
                    <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
                        <span class="glyphicon glyphicon-chevron-left"></span>
                    </a>
                    <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
                        <span class="glyphicon glyphicon-chevron-right"></span>
                    </a>
                </div>
            </div>
        </div>

        <!-- Three Per Row -->
        <g:if test="${gridStyle == com.icosmic.GridStyle.THREE_PER_ROW}">
            <div id="products" class="row list-group">
                <g:each var="shop" in="${shops}">
                    <input type="hidden" name="id" value="${shop.id}"/>
                    <div class="col-lg-4 col-sm-6 col-xs-12">
                        <g:if test="${shop.redirect == Boolean.TRUE}">
                            <a href="${shop.url}">
                                 <img src="${resource(file: shop.image.path)}" class="thumbnail" style="width: 350px; height:260px">
                            </a>
                        </g:if>
                        <g:else>
                            <a href="${createLink(controller:'dashboard', action: 'viewShop', params: [id: shop.id])}">
                                 <img src="${resource(file: shop.image.path)}" class="thumbnail" style="width: 350px; height:260px">
                            </a>
                        </g:else>
                    </div>
                </g:each>
            </div>
        </g:if>

        <!-- Four Per Row -->
        <g:if test="${gridStyle == com.icosmic.GridStyle.FOUR_PER_ROW}">
            <div id="products" class="row list-group">
                <g:each var="shop" in="${shops}">
                    <input type="hidden" name="id" value="${shop.id}"/>
                    <div class="col-lg-3 col-sm-4 col-xs-6">
                        <g:if test="${shop.redirect == Boolean.TRUE}">
                            <a href="${shop.url}">
                                 <img src="${resource(file: shop.image.path)}" class="thumbnail" style="width: 260px; height:110px">
                            </a>
                        </g:if>
                        <g:else>
                            <a href="${createLink(controller:'dashboard', action: 'viewShop', params: [id: shop.id])}">
                                 <img src="${resource(file: shop.image.path)}" class="thumbnail" style="width: 260px; height:110px">
                            </a>
                        </g:else>
                    </div>
                </g:each>
            </div>
        </g:if>

    </body>

</html>
