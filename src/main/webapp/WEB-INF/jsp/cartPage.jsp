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
    <link type="text/css" rel="stylesheet" href="/resources/css/cartPage.css">
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
                <script src="/resources/js/cartPage.js"></script>
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
                <c:forEach items="${onlyContractsChanges}" var="entry" >
                    <div id="toRemove${entry.contractNumber}">
                        <button class="btn" type="button" onclick="remove($(this))" style="clear:both; float:right; font-size: 20px;"
                                id="toRemove${entry.contractNumber}">X</button>
                        <div class="jumbotron choosenTariffJumbotron">
                            <div class="row">
                                        <p class="lead" id="contractNumber"
                                           style="font-family: MS Shell Dig 2; font-weight: bolder;  font-size: 25px; float:left;">
                                                ${entry.contractNumber}</p>
                            </div>
                            <div style="margin-top:-10px;">
                                <c:choose>
                                    <c:when test="${empty entry.tariff}">

                                    </c:when>
                                    <c:otherwise>
                                        <div>
                                            <div class="row" style="clear:both; margin-bottom:25px;">
                                                <div class="col-4">
                                                </div>

                                                <div class="col-4">
                                                    <p class="lead columnDiscriptionLabel">Tariff:</p>
                                                </div>

                                                <div class="col-3">
                                                </div>

                                                <div class="col-1">
                                                </div>
                                            </div>

                                            <div class="row" style="margin-top:-10px;">
                                                <div class="col-4">
                                                </div>

                                                <div class="col-4">
                                                    <p class="lead columnDiscriptionLabels">${entry.tariff.name}</p>
                                                </div>

                                                <div class="col-3">
                                                </div>

                                                <div class="col-1">
                                                    <button class="btn" type="button" onclick="remove($(this))" style="clear:both; padding:0; margin:0;" >X</button>
                                                </div>
                                            </div>
                                        </div>

                                        <hr class="rounded" style="width: 65%; clear:both; padding:2px; margin-top:10px; margin-bottom: 10px; clear:both; float:right;">
                                    </c:otherwise>
                                </c:choose>

                                <c:set var="contractNumber" >${entry.contractNumber}</c:set>
                                <c:choose>
                                <c:when test="${empty mapOfOptionsEnabledDisabled[contractNumber]}">

                                </c:when>
                                <c:otherwise>
                                    <div class="row" style="clear:both; margin-bottom:25px;">
                                        <div class="col-4">
                                        </div>

                                        <div class="col-4">
                                            <p class="lead columnDiscriptionLabel">Options:</p>
                                        </div>

                                        <div class="col-3">
                                        </div>

                                        <div class="col-3">
                                        </div>
                                    </div>
                                    <c:forEach items="${mapOfOptionsEnabledDisabled[contractNumber]}" var="nentry" >
                                        <div class="row" style="margin-top:-10px;">
                                            <div class="col-4">
                                            </div>

                                            <div class="col-4">
                                                <p class="lead">${nentry.key}</p>
                                            </div>

                                            <div class="col-3">
                                                <p class="lead">${nentry.value}</p>
                                            </div>

                                            <div class="col-1">
                                                <button class="btn" type="button" onclick="remove($(this))" style="clear:both; padding:0; margin:0;">X</button>
                                            </div>
                                        </div>
                                    </c:forEach>

                                    <hr class="rounded" style="width: 65%; clear:both; padding:2px; margin-top:10px; margin-bottom: 10px; clear:both; float:right;">
                                </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${empty entry.blocked}">

                                    </c:when>
                                    <c:otherwise>
                                        <div class="row" style="clear:both;">
                                            <div class="col-4">
                                            </div>

                                            <div class="col-4">
                                                <p class="lead" id="columnContentLabels"  >Contract blocked:</p>
                                            </div>

                                            <div class="col-3">
                                                <p class="lead" id="columnContentLabels" >${entry.blocked}</p>
                                            </div>

                                            <div class="col-1">
                                                <button class="btn" type="button" style="clear:both; padding:0; margin:0;" onclick="remove($(this))">X</button>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>

                            </div>
                        </div>
                    </div>
                </c:forEach>

                <div>
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