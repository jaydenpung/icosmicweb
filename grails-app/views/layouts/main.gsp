<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
    <head>
        <!-- FIXME: Clean up css ad js, change link resource to file -->
        <title>iCosmic</title>
        <asset:stylesheet src="bootstrap.min.css"/>
        <asset:stylesheet src="shop-homepage.css"/>
        <asset:stylesheet src="icosmic.css"/>
        <asset:stylesheet src="bootstrapValidator.css"/>

        <asset:javascript src="jquery.js"/>
        <asset:javascript src="bootstrap.min.js"/>
        <asset:javascript src="bootstrapValidator.min.js"/>
        <asset:javascript src="icosmic.js"/>
    </head>
    <body>
        <!-- Navigation -->
        <div class="wrapper">
            <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
                    <!-- Brand and toggle get grouped for better mobile display -->
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
                            <span class="sr-only">Toggle navigation</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <g:link class="navbar-brand" controller="dashboard" action="index"><g:message code="menuItem.home.label"/></g:link>
                    </div>
                    <!-- Collect the nav links, forms, and other content for toggling -->
                    <div class="collapse navbar-collapse col-md-10" id="bs-example-navbar-collapse-1">
                        <ul class="nav navbar-nav">
                            <li>
                                <a href="#">Example</a>
                            </li>
                            <li>
                                <a href="#">Usage</a>
                            </li>
                            <li>
                                <a href="#">Advanced</a>
                            </li>
                            <li>
                                <a href="#">Support</a>
                            </li>
                            <sec:ifAllGranted roles="ROLE_ADMIN">
                                <li>
                                    <g:link controller="shop" action="list"><g:message code="menuItem.maintenance.label"/></g:link>
                                </li>
                            </sec:ifAllGranted>
                            <li>
                                <sec:ifLoggedIn>
                                    <g:link controller="login" action="logout"><g:message code="menuItem.logout.label"/></g:link>
                                </sec:ifLoggedIn>
                                <sec:ifNotLoggedIn>
                                    <g:link controller="login" action="index"><g:message code="menuItem.login.label"/></g:link>
                                </sec:ifNotLoggedIn>
                            </li>
                        </ul>
                    </div>
                    <!-- /.navbar-collapse -->
                <!-- /.container -->
            </nav>

            <div class="container-fluid main">
                <div class="row">
                    <div class="auto-overflow">
                        <div class="container">
                            <div class="row">

                                <sec:ifLoggedIn>
                                    <p class="background-text pull-right loginUser">Logged in as: <sec:username></sec:username></p>
                                </sec:ifLoggedIn>
                                <!-- Page Content -->
                                <div class="col-md-12">
                                    <g:layoutBody />
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="push"></div>

        <div class="footer">
            <footer>
                <div class="row">
                    <div class="col-lg-12">
                        <p class="background-text">Copyright &copy; iCOSMIC 2017</p>
                    </div>
                </div>
            </footer>
        </div>

    </body>
</html>
