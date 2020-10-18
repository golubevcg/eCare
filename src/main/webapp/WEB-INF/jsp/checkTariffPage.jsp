<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tariff details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/css/checkTariffPage.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

</head>
<body>

<jsp:directive.include file = "headerTemplate.jsp" />

<div id="page-container">
    <div id="content-wrap">
    <div>
        <script src="/resources/js/checkTariffPage.js"></script>
    </div>

            <div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">

                <div class="row">
                    <div class="col"></div>

                    <div class="col-5">
                        <h1 class="display-4" id="privateOfficeLabel">Tariff details</h1>
                        <sec:authorize access="hasAuthority('ADMIN')">
                            <button type="button" class="btn btn-primary"
                                    style="float: left;width:11%; font-size: 15px; background-color: white; color:black;"
                                    onclick="makePageEditable()">Edit</button>
                        </sec:authorize>
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
                                <form:input path="name" class="form-control" type="text" disabled="true" id="inputFormName"
                                            style="float:left; width:30%; margin-top:-10px;" placeholder="Name" name="nameForm"></form:input>
                            </div>
                            <label style="color:red;" id="nameFieldRequired" hidden>This field is required.</label>
                        </spring:bind>
                    </div>
                </div>

                <div class="col"></div>
            </div>


            <div class="row" style="margin-top: 10px">
                <div class="col"></div>

                <div class="col-5">
                    <p id="headlineLabel">Price</p>
                    <spring:bind path="price" >
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input path="price" class="form-control defaultForm" id="inputFormPrice" type="number" placeholder="Price"
                                        style="margin-top:-10px; width: 54%;" name="tariffNameForm" disabled="true"></form:input>
                        </div>
                        <label style="color:red;" id="priceFieldRequired" hidden>This field is required.</label>
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
                            <form:input path="shortDiscription" class="form-control defaultForm" id="inputFormShortDisc" type="text" placeholder="Short description"
                                        style="margin-top:-10px; width: 65%;" name="shortDiscriptionForm" disabled="true"></form:input>
                        </div>
                        <label style="color:red; float:left;" id="shortDiscriptionFieldRequired" hidden>This field is required.</label>
                    </spring:bind>
                </div>

                <div class="col"></div>
            </div>

            <div class="row" style="margin-top:10px;">
                <div class="col"></div>
                <div class="col-5">

                    <p class="headlineLabel">Available options</p>
                    <select class="mul-select" multiple="true" style="float:left; width: 54%; margin-top:-10px; font-size: 20px;"  name="selectedOptions"
                            id="selectedOptions" disabled="true">
                        <c:forEach items="${listOfActiveOptions}" var="option" >
                                    <option>${option.name}</option>
                        </c:forEach>
                    </select>
                    <label style="color:red; float:left;" id="selectedOptionsField" hidden>At least 3 options must be selected.</label>

                </div>
                <div class="col"></div>
            </div>

            <div class="row" style="margin-top:10px;">
                <div class="col"></div>
                <div class="col-5">
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="exampleCheck1" name="blockConnectedContracts" hidden>
                        <label class="form-check-label" for="exampleCheck1" name="checkbox2" hidden>Block all connected with this tariff contracts</label>
                    </div>
                    <input class="btn btn-lg btn-primary btn-block" onclick="validateAndSubmitIfTrue()"
                           style="width:35%; clear:both; margin-top:20px;" hidden name="SaveChangesButton" value="Save changes">
                    <input class="btn btn-lg btn-primary btn-block" onclick="deleteOption()"
                           style="width:35%; clear:both; float:right; color:white; background-color: red; margin-top:-48px;" hidden name="deleteOptionButton" value="Delete Tariff">
                    </form:form>

                </div>

                <div class="col"></div>
            </div>
    </div>
</div>
</body>


<jsp:directive.include file = "footerTemplate.jsp" />

</html>