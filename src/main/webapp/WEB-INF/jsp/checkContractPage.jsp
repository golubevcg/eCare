<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Contract details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="/resources/styles/checkContract.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/js/standalone/selectize.min.js" integrity="sha256-+C0A5Ilqmu4QcSPxrlGpaZxJ04VjsRjKu+G82kl5UJk=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/selectize.js/0.12.6/css/selectize.bootstrap3.min.css" integrity="sha256-ze/OEYGcFbPRmvCnrSeKbRTtjG4vGLHXgOqsyLFTRjg=" crossorigin="anonymous" />

    <!--for dropdown menus scripts-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

</head>

<body>

<div id="page-container">
    <div id="content-wrap">
        <jsp:directive.include file = "headerTemplateByRole.jsp" />

        <div>
                <script src="/resources/js/checkContract.js"></script>
        </div>

        <div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">

            <div class="row">
                <div class="col"></div>

                <div class="col-5">
                    <h1 class="display-4" id="privateOfficeLabel">Contract details</h1>
                    <sec:authorize access="hasAuthority('ADMIN')">
                        <button type="button" class="btn btn-primary"
                                style="float: left;width:11%; font-size: 15px; background-color: white; color:black;"
                                onclick="makePageEditable()">Edit</button>
                    </sec:authorize>
                </div>

                <div class="col"></div>
            </div>

        </div>

        <form:form method="POST" class="form-signin">

        <div class="row">
            <div class="col"></div>

            <div class="col-5">
                <label class="container" id="labelCheckboxContainer" style="clear:both; float:left; margin-top: -20px; ">Contract phone number</label>
                <input class="form-control defaultForm" id="numberLabel" name="contractNumber"
                       type="number" disabled="true" placeholder="Enter contract phone number" style="width:38%;" value="${contractDTO.contractNumber}">
                <label style="color:red; float:left;" id="phoneNumberFieldRequired" hidden>This field is required.</label>

                <div id="userSelectorContainer" style="clear:both;">
                    <label class="container" id="userInputLabel" style="clear:both; float:left; margin-top: 10px; ">Select User</label>
                    <div class="autocomplete" style="width:300px;">
                    <input class="form-control defaultForm" id="usersList" style="width:100%;" type="text"
                           name="usersList" disabled="true" placeholder="User login" value="${contractDTO.user.login}"
                    style="background-color: white;font-size: 20px; color:black;">
                    <label style="color:red; float:left;" id="selectedUserFieldRequired" hidden>This field is required.</label>
                    </div>
                </div>
            </div>
            <div class="col"></div>
        </div>

        <div class="row">
            <div class="col"></div>

            <div class="col-5">
                    <label class="container" id="labelCheckboxContainer" style="clear:both; float:left; margin-top: 10px; ">Select tariff:</label>
                    <select class="form-control form-control-lg" style="clear:both; float:left; clear:both; width: 60%; margin-top:10px; -webkit-appearance: none;"
                            name="selectedTariff" id="tariffsList" disabled="true">
                        <c:forEach items="${listOfTariffs}" var="tariff">
                            <c:choose>
                                <c:when test="${tariff.name}==${contractDTO.tariff.name}">
                                    <option selected>${tariff.name}</option>
                                </c:when>
                                <c:otherwise>
                                    <option>${tariff.name}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                    <label style="color:red; float:left; clear:both;" id="selectedTariffFieldRequired" hidden>This field is required.</label>

                    <label class="container" id="labelCheckboxContainer" style="margin-top: 10px; ">Select additional options:</label>
                    <select class="mul-select" multiple="true" id="optionsList"
                            style="width:40%; font-size: 20px;" name="selectedOptions" disabled="true">
                    </select>
            </div>
            <div class="col"></div>
        </div>

        <div class="row" style="clear:both;">
                <div class="col"></div>
                <div class="col-5">
                    <div class="form-check">
                        <c:choose>
                            <c:when test="${contractDTO.blocked}">
                                <input type="checkbox" checked class="form-check-input" id="exampleCheck1" name="contractIsBlocked" hidden>
                            </c:when>
                            <c:otherwise>
                                <input type="checkbox" class="form-check-input" id="exampleCheck1" name="contractIsBlocked" hidden>
                            </c:otherwise>
                        </c:choose>
                        <label class="form-check-label" for="exampleCheck1" name="checkbox2" hidden>Contract is blocked</label>
                    </div>
                    <input class="btn btn-lg btn-primary btn-block" onclick="validateAndSubmitIfTrue()"
                           style="width:35%;margin-top:20px;" hidden name="SaveChangesButton" value="Save changes">
                    <input class="btn btn-lg btn-primary btn-block" onclick="deleteContract()"
                           style="width:35%; clear:both; float:right; color:white; background-color: red; margin-top:-48px;" hidden name="deleteOptionButton" value="Delete Tariff">
                </div>
                <div class="col"></div>
         </div>

        </form:form>
    </div>
</div>

</body>

<footer>
    <jsp:directive.include file = "footerTemplate.jsp" />
</footer>


</html>