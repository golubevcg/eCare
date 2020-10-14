<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>New Option</title>
    <link type="text/css" rel="stylesheet" href="/resources/styles/privateOfficeWorker.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/styles/registration.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

</head>
<body>

<jsp:directive.include file = "headerTemplateByRole.jsp" />

<div>
    <script src="/resources/js/newTariff.js"></script>
</div>

<div id="page-container">
    <div id="content-wrap">

        <div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">

            <div class="row">
                <div class="col"></div>

                <div class="col-5">
                    <h1 class="display-4" id="privateOfficeLabel">Registering a new tariff</h1>
                </div>

                <div class="col"></div>
            </div>

        </div>
        <form:form method="POST" modelAttribute="tariffDTO"  id="tariffDTOInputForm">

        <div class="row">
            <div class="col"></div>

            <div class="col-5">
                <div>
                    <p id="headlineLabel" style="float:left; width:41%; margin-top: -20px;">Name</p>
                </div>
                <div style="clear:both;">
                    <spring:bind path="name" >
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input path="name" class="form-control" type="text"
                                        style="float:left; width:30%; margin-top:-10px;" placeholder="Name" name="nameForm"></form:input>
                            <form:errors path="name" id="errorsLabel" class="label"></form:errors>
                        </div>
                    </spring:bind>
                </div>
            </div>

            <div class="col"></div>
        </div>


        <div class="row" style="margin-top: 10px">
            <div class="col"></div>

            <div class="col-5">
                <p id="headlineLabel" ">Price</p>
                <spring:bind path="price" >
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="price" class="form-control defaultForm" id="inputForm" type="number" placeholder="Price"
                                    style="margin-top:-10px; width: 54%;" name="tariffNameForm"></form:input>
                        <form:errors path="price" id="errorsLabel" class="label"></form:errors>
                    </div>
                </spring:bind>
            </div>

            <div class="col"></div>
        </div>

        <div class="row">
            <div class="col"></div>

            <div class="col-5">
                <p id="headlineLabel" style="margin-top: 10px">Short description</p>
                <spring:bind path="shortDiscription" >
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="shortDiscription" class="form-control defaultForm" id="inputForm" type="text" placeholder="Short description"
                                    style="margin-top:-10px; width: 65%; height: 110px;" name="shortDiscriptionForm"></form:input>
                        <form:errors path="shortDiscription" id="errorsLabel" class="label"></form:errors>
                    </div>
                </spring:bind>
            </div>

            <div class="col"></div>
        </div>

        <div class="row" style="margin-top:10px;">
            <div class="col"></div>
            <div class="col-5">

                <p id="headlineLabel">Available options</p>
                <select class="mul-select" multiple="true" style="width: 54%; margin-top:-10px; font-size: 20px;"  name="selectedOptions" id="selectedOptions">
                    <c:forEach items="${listOfActiveOptions}" var="option" >
                        <option>${option.name}</option>
                    </c:forEach>
                </select>

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