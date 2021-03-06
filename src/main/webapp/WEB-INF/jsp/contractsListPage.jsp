<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="/resources/css/contractsListPage.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

</head>
<body>

<div id="page-container">
    <div id="content-wrap">

        <jsp:directive.include file = "headerTemplate.jsp" />

        <div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">

            <div class="row">
                <div class="col"></div>

                <div class="col-5">
                    <h1 class="display-4" id="privateOfficeLabel">Your contracts</h1>
                </div>

                <div class="col"></div>
            </div>

        </div>

        <div class="row" style="margin-top:20px;">
            <div class="col"></div>
            <div class="col-5">
                <p style="font-weight: bolder; float:left; font-size: 18px;">Search for a contract by phone number</p>

                <div style="margin-top: 20px">
                    <input class="form-control" type="text" id="searchForm" style="width:100%; background-color: white; float:left; border-color: black;" placeholder="Enter phone number">
                </div>
            </div>

            <div class="col"></div>
        </div>

        <div class="row" style="margin-top:5px;">
            <div class="col"></div>
            <div class="col-5">
                <div class="row">
                    <div class="col-4">
                        <p class="lead columnDiscriptionLabels">Phone number</p>
                    </div>

                    <div class="col-3">
                        <p class="lead columnDiscriptionLabels"> Tariff </p>
                    </div>

                </div>


            </div>

            <div class="col"></div>
        </div>

        <div class="row" style="margin-top:-10px;">
            <div class="col"></div>
            <div class="col-5">
                <div class="row">


                    <c:forEach items="${contractsSet}" var="entry">

                        <hr class="rounded"
                            style="width: 100%; clear:both; padding:2px;
                                    margin:0px; margin-top:0px; margin-bottom:0px;">

                        <div class="col-4">
                            <p class="lead" id="columnContentLabels">${entry.contractNumber}</p>
                        </div>

                        <div class="col-3">
                            <a href="/checkTariff/${entry.tariff.name}">
                            <p class="lead" id="columnContentLabels">${entry.tariff.name}</p>
                            </a>
                        </div>

                        <div class="col-2">
                            <c:choose>
                                <c:when test="${entry.blocked eq true}">
                                    <p class="lead" id="columnContentLabels" style="color:red;">Blocked</p>
                                </c:when>
                                <c:otherwise>
                                    <p class="lead" id="columnContentLabels" style="color:red;"></p>
                                </c:otherwise>
                            </c:choose>
                        </div>

                        <div class="col-3">

                            <a href="/contractDetails/${entry.contract_id}">
                                <button type="button" class="btn btn-primary btn-lg btn-lg" id="editButton">Details</button>
                            </a>
                        </div>

                    </c:forEach>

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