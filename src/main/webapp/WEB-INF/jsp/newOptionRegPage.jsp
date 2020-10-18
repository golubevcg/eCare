<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New Option</title>
    <link type="text/css" rel="stylesheet" href="/resources/styles/privateOfficeWorker.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/css/registrationPagesCss.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

</head>
<body>

<jsp:directive.include file = "headerTemplate.jsp" />

<div>
    <script src="/resources/js/newOptionRegPage.js"></script>
</div>

<div id="page-container">
    <div id="content-wrap">

        <div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">

            <div class="row">
                <div class="col"></div>

                <div class="col-5">
                    <h1 class="display-4" id="privateOfficeLabel">Registering a new option</h1>
                </div>

                <div class="col"></div>
            </div>

        </div>
        <form:form method="POST" modelAttribute="optionDTO"  id="userDTOInputForm">

        <div class="row">
            <div class="col"></div>
            <div class="col-5">
                <p id="headlineLabel" style="margin-top:-25px;">Name</p>
                <spring:bind path="name" >
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="name" class="form-control defaultForm" id="inputForm" type="text" placeholder="Name"
                                    style="margin-top:-10px; width: 54%;" name="optionNameForm"></form:input>
                        <form:errors path="name" id="errorsLabel" class="label"></form:errors>
                    </div>
                </spring:bind>


            </div>

            <div class="col"></div>
        </div>


        <div class="row" style="margin-top: 10px">
            <div class="col"></div>

            <div class="col-5">
                <div>
                    <p id="headlineLabel" style="float:left; width:41%;">Connection cost</p>
                </div>
                <div style="clear:both;">
                    <spring:bind path="connectionCost" >
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input path="connectionCost" class="form-control" type="number"
                                        style="float:left; width:30%; margin-top:-10px;" placeholder="Connection cost" name="connectionCostForm"></form:input>
                            <form:errors path="connectionCost" id="errorsLabel" class="label"></form:errors>
                        </div>
                    </spring:bind>


                    <p id="headlineLabel" style="clear:both; width:20%; padding-top:10px;">Price</p>
                    <spring:bind path="price" >
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input path="price" class="form-control defaultForm" id="inputForm" type="number" placeholder="Price"
                                        style="float:left; width:30%; margin-top:-10px;" name="priceForm"></form:input>
                            <form:errors path="price" id="errorsLabel" class="label"></form:errors>
                        </div>
                    </spring:bind>

                </div>
            </div>

            <div class="col"></div>
        </div>

        <div class="row">
            <div class="col"></div>

            <div class="col-5">
                <p id="headlineLabel" style="margin-top: 10px">Short description</p>
                <spring:bind path="shortDescription" >
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="shortDescription" class="form-control defaultForm" id="inputForm" type="text" placeholder="Short description"
                                    style="margin-top:-10px; width: 54%;" name="shortDescriptionForm"></form:input>
                        <form:errors path="shortDescription" id="errorsLabel" class="label"></form:errors>
                    </div>
                </spring:bind>
            </div>

            <div class="col"></div>
        </div>

        <div class="row" style="margin-top:10px;">
            <div class="col"></div>
            <div class="col-5">

                <p id="headlineLabel">Obligatory options</p>
                <select class="mul-select" multiple="true" style="width: 54%; margin-top:-10px; font-size: 20px;"  name="selectedObligatoryOptions" id="selectedObligatoryOptions">
                    <c:forEach items="${listOfActiveOptions}" var="option" >
                        <option>${option.name}</option>
                    </c:forEach>
                </select>

                <div style="margin-top:15px;">
                    <p id="headlineLabel" >Incompatible options</p>
                    <select class="mul-select" multiple="true"
                            style="width: 54%; margin-top:-10px; font-size: 20px;"  name="selectedIncompatibleOptions" id="selectedIncompatibleOptions">
                        <c:forEach items="${listOfActiveOptions}" var="option">
                            <option>${option.name}</option>
                        </c:forEach>
                    </select>
                </div>

            </div>
            <div class="col"></div>
        </div>

        <div class="row" style="margin-top:10px;">
            <div class="col"></div>
            <div class="col-5">
                <button type="submit" class="btn btn-primary btn-lg btn-lg" id="saveButton" >Save</button>
                </form:form>

            </div>

            <div class="col"></div>
        </div>
    </div>
</div>
</body>


<jsp:directive.include file = "footerTemplate.jsp" />

</html>