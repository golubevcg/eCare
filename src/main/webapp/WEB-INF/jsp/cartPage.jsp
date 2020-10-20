<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Cart</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="/resources/css/checkContractPage.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/js/standalone/selectize.min.js" integrity="sha256-+C0A5Ilqmu4QcSPxrlGpaZxJ04VjsRjKu+G82kl5UJk=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/css/selectize.bootstrap3.min.css" integrity="sha256-ze/OEYGcFbPRmvCnrSeKbRTtjG4vGLHXgOqsyLFTRjg=" crossorigin="anonymous" />
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

</head>

<body>

<div id="page-container">
    <div id="content-wrap">
        <jsp:directive.include file = "headerTemplate.jsp" />
        <div>
<%--                <script src="/resources/js/checkContractPage.js"></script>--%>
        </div>
        <div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">
            <div class="row">
                <div class="col"></div>
                <div class="col-5">
                    <h1 class="display-4" id="privateOfficeLabel">Cart</h1>
                </div>
                <div class="col"></div>
            </div>
        </div>

        <div class="row">
            <div class="col"></div>
            <div class="col-5" style="margin-top:-15px;">
                <c:forEach var="entry" items="${contractsOptions}">

                    <p class="lead" id="columnContentLabels" style="clear:both; float:left;">${entry.key}</p>
                    <button class="btn btn-secondary" type="button" style="float:right;" >X</button>

                    <div>
                        <c:forEach var="nentry" items="${entry.value}">
                            <hr class="rounded" style="width: 65%; clear:both; padding:2px;
                                    margin:0px; margin-top:0px; margin-bottom:0px; clear:both; float:right;">
                            <div>
                                <button class="btn btn-secondary" type="button" style="clear:both; float:right;" >X</button>
                                <p class="lead" style="float:right; margin-right: 110px;">${nentry.value}</p>
                                <p class="lead" style="float:right; margin-right: 115px;">${nentry.key}</p>
                            </div>
                        </c:forEach>
                    </div>
                    <hr class="rounded" style="width: 100%; clear:both; padding:2px;
                                    margin:0px; margin-top:0px; margin-bottom:0px;">
                </c:forEach>

                <div>
                    <hr class="rounded" style="width: 100%; clear:both; padding:2px;
                                    margin:0px; margin-top:0px; margin-bottom:0px;">
                    <button class="btn btn-secondary" type="button" style="margin-left:240px;" >Submit changes</button>
                </div>

            </div>
            <div class="col"></div>
        </div>

    </div>
</div>

</body>

<footer>
    <jsp:directive.include file = "footerTemplate.jsp" />
</footer>


</html>