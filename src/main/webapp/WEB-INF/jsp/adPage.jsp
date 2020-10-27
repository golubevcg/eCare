<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>adPage</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="/resources/css/adPage.css">
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

<script src="/resources/js/adPage.js"></script>

<div id="page-container">
    <div id="content-wrap">
    <jsp:directive.include file = "headerTemplate.jsp" />

        <div class="row" style="padding-top: 20px;">
            <div class="col-3"></div>
            <div class="col" style="margin-left:30px;">
                <div class="card">
                            <h5 class="card-header">
                                <select class="form-control form-control-lg" name="selectedTariff" id="tariffslist1"
                                onchange="changeDescriptionAndPrice($(this))">
                                    <c:forEach items="${listOfTariffs}" var="tariff">

                                        <c:choose>
                                            <c:when test="${tariff.name eq listOfAdTariffs[0].name}">
                                                <option selected>${tariff.name}</option>
                                            </c:when>
                                            <c:otherwise>
                                                <option>${tariff.name}</option>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:forEach>
                                </select>
                            </h5>
                            <div class="card-body">
                                <label style="font-size:15px; color:#d3d3d3" id="tariffdsc">Tariff description:</label>

                                <p class="card-text" id="tariffslist1description">${listOfAdTariffs[0].shortDiscription}</p>

                                <hr>
                                <label style="font-size:20px" id="tariffslist1price">${listOfAdTariffs[0].price}$/month</label>

                            </div>
                        </div>
                <div class="card">
                    <h5 class="card-header">
                        <select class="form-control form-control-lg" name="selectedTariff" id="tariffslist2"
                                onchange="changeDescriptionAndPrice($(this))">
                            <c:forEach items="${listOfTariffs}" var="tariff">
                                <c:choose>
                                    <c:when test="${tariff.name eq listOfAdTariffs[1].name}">
                                        <option selected>${tariff.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option>${tariff.name}</option>
                                    </c:otherwise>
                                </c:choose>

                            </c:forEach>
                        </select>
                    </h5>
                    <div class="card-body">
                        <label style="font-size:15px; color:#d3d3d3">Tariff description:</label>
                        <p class="card-text" id="tariffslist2description">${listOfAdTariffs[1].shortDiscription}</p>
                        <hr>
                        <label style="font-size:20px" id="tariffslist2price">${listOfAdTariffs[1].price}$/month</label>

                    </div>
                </div>
                <div class="card">
                    <h5 class="card-header">
                        <select class="form-control form-control-lg" name="selectedTariff" id="tariffslist3"
                                onchange="changeDescriptionAndPrice($(this))">
                            <c:forEach items="${listOfTariffs}" var="tariff">

                                <c:choose>
                                    <c:when test="${tariff.name eq listOfAdTariffs[2].name}">
                                        <option selected>${tariff.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option>${tariff.name}</option>
                                    </c:otherwise>
                                </c:choose>

                            </c:forEach>
                        </select>
                    </h5>
                    <div class="card-body">
                        <label style="font-size:15px; color:#d3d3d3">Tariff description:</label>
                        <p class="card-text" id="tariffslist3description">${listOfAdTariffs[2].shortDiscription}</p>
                        <hr>
                        <label style="font-size:20px" id="tariffslist3price">${listOfAdTariffs[2].price}$/month</label>

                    </div>
                </div>
            </div>
            <div class="col-3"></div>
        </div>

        <div class="row" style="padding-top: 25px;">
            <div class="col-5"></div>
            <div class="col-3" style="margin-left:50px;">
                    <input class="btn" onclick="submitAd()" value="Save changes">
            </div>
            <div class="col-6"></div>
        </div>

    </div>
</div>




</body>

<footer>
    <jsp:directive.include file = "footerTemplate.jsp" />
</footer>


</html>

