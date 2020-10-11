<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New contract</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="/resources/styles/newContract.css">
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
        <jsp:directive.include file = "headerTemplateAdmin.jsp" />

        <div>
                <script src="/resources/js/newContract.js"></script>
        </div>

        <div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">

            <div class="row">
                <div class="col"></div>

                <div class="col-5">
                    <h1 class="display-4" id="privateOfficeLabel">Registering a new Contract</h1>
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
                       type="number" placeholder="Enter contract phone number" style="width:38%;">${contractNumber}
                    <c:if test="${not empty phoneNumberEmptyError}">
                        <label style="color:red;" id="labelCheckboxContainer">${phoneNumberEmptyError}</label>

                    </c:if>
                    <c:if test="${not empty phoneNumberPatternError}">
                           <label style="color:red;" id="labelCheckboxContainer">${phoneNumberPatternError}</label>
                    </c:if>

                <div id="userSelectorContainer" style="clear:both;">
                    <label class="container" id="labelCheckboxContainer" style="clear:both; float:left; margin-top: 10px; ">Select User</label>
                    <div class="autocomplete" style="width:300px;">
                    <input class="form-control defaultForm" id="usersList" style="width:100%;" type="text"
                           name="usersList" placeholder="User login"
                    style="background-color: white;font-size: 20px; color:black;">

                    <c:if test="${not empty phoneNumberPatternError}">
                    <label style="color:red;" id="labelCheckboxContainer">${selectedUserError}</label>
                    </c:if>

                    </div>
                </div>

                <div id="tarifOptionsForUserRegistration">
                    <label class="container" id="labelCheckboxContainer" style="clear:both; float:left; margin-top: 10px; ">Select tariff:</label>

                    <select class="form-control form-control-lg" style="clear:both; width: 60%; margin-top:10px; -webkit-appearance: none;"
                            name="selectedTariff" id="tariffsList">
                        <c:forEach items="${listOfTariffs}" var="tariff">
                                <option selected>${tariff.name}</option>
                        </c:forEach>
                    </select>
                    <label class="container" id="labelCheckboxContainer" style="clear:both; float:left; margin-top: 10px; ">Select additional options:</label>
                    <select class="mul-select" multiple="true" id="optionsList" style="width:60%; font-size: 20px" name="selectedOptions">
                    </select>

                </div>

                <input class="btn btn-lg btn-primary btn-block" value="Save" type="submit" style="width:35%; clear:both; margin-top:20px;"></input>

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