<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Option details</title>
    <link type="text/css" rel="stylesheet" href="/resources/css/checkOptionPage.css">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
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
    <script src="/resources/js/checkOptionPage.js"></script>
</div>

<div id="page-container">
    <div id="content-wrap">

        <div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">
            <form:form method="POST" modelAttribute="optionDTO"  id="userDTOInputForm">

            <div class="row">
                <div class="col"></div>

                <div class="col-5">
                    <h1 class="display-4" id="privateOfficeLabel" style="float:left; width:4350%">Option details</h1>
                    <sec:authorize access="hasAuthority('ADMIN')">
                        <button type="button" class="btn btn-primary"
                            style="float:left;width:11%; font-size: 15px; background-color: white; color:black;"
                                onclick="makePageEditable()">Edit</button>
                    </sec:authorize>
                </div>

                <div class="col"></div>
            </div>

        </div>

        <div class="row">
            <div class="col"></div>
            <div class="col-5">
                <p id="headlineLabel" style="margin-top:-25px;">Name</p>
                <spring:bind path="name" >
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="name" class="form-control defaultForm inputform" id="inputFormName" type="text" disabled="true"
                                    style="margin-top:-10px; width: 54%;" name="optionNameForm"></form:input>
                    </div>
                    <label style="color:red;" id="nameFieldRequired" hidden>This field is required.</label>
                </spring:bind>


            </div>

            <div class="col"></div>
        </div>

        <label style="color:red;" id="toPullOptionIdForAjax" name="${optionDTO.option_id}"hidden></label>


        <div class="row" style="margin-top: 10px">
            <div class="col"></div>

            <div class="col-5">
                <div>
                    <p class="headlineLabel">Connection cost</p>
                </div>
                <div style="clear:both;">
                    <spring:bind path="connectionCost" >
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input path="connectionCost" class="form-control" type="number"
                                        style="float:left; width:30%; margin-top:-10px;" id="inputFormConnCost"
                                        placeholder="Connection cost" disabled="true"  name="connectionCostForm"></form:input>
                        </div>
                        <label style="color:red;" id="connectionFieldRequired" hidden>This field is required.</label>
                    </spring:bind>


                    <p id="headlineLabel" style="clear:both; width:20%; padding-top:10px;">Price</p>
                    <spring:bind path="price" >
                        <div class="form-group ${status.error ? 'has-error' : ''}">
                            <form:input path="price" class="form-control defaultForm" id="inputFormPrice" type="number" disabled="true"  placeholder="Price"
                                        style="float:left; width:30%; margin-top:-10px;" name="priceForm"></form:input>
                        </div>
                        <label style="color:red;" id="priceFieldRequired" hidden>This field is required.</label>

                    </spring:bind>

                </div>
            </div>

            <div class="col"></div>
        </div>

        <div class="row">
            <div class="col"></div>

            <div class="col-5">
                <p id="headlineLabel" style="margin-top: 10px">Short description</p>
                <spring:bind path="shortDescription">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="shortDescription" class="form-control defaultForm" id="inputFormShortDisc" type="text" disabled="true" placeholder="Short description"
                                    style="margin-top:-10px; width: 54%;  text-align: justify;" name="shortDiscriptionForm"></form:input>
                    </div>
                    <label style="color:red; float:left;" id="shortDescriptionFieldRequired" hidden>This field is required.</label>
                </spring:bind>
            </div>

            <div class="col"></div>
        </div>

        <div class="row" style="margin-top:10px;">
            <div class="col"></div>
            <div class="col-5">
                <div>
                    <p class="headlineLabel">Obligatory options</p>
                    <select class="mul-select" multiple="true"
                            style="clear:both; width: 54%; margin-top:-10px; font-size: 20px;"
                            name="selectedObligatoryOptions" id="selectedObligatoryOptions" disabled="true">
                        <c:forEach items="${listOfActiveOptions}" var="option" >
                                <option id="oblig${option.option_id}" name="obligOptionsElements">${option.name}</option>
                        </c:forEach>
                    </select>
                    <p class="headlineLabel">*All new obligatory dependencies
                        will be automatically added as available in all tariffs related to this option.</p>
                </div>

            <div style="margin-top:15px; clear:both;">
                <div>
                    <p class="headlineLabel">Incompatible options</p>
                    <select class="mul-select" multiple="true"
                            style="clear:both; width: 54%; margin-top:-10px; font-size: 20px;"
                            name="selectedIncompatibleOptions" id="selectedIncompatibleOptions" disabled="true">
                        <c:forEach items="${listOfActiveOptions}" var="option" >
                                <option id="incomp${option.option_id}" name="incompOptionsElements">${option.name}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            </div>
            <div class="col"></div>
        </div>

        <div class="row" style="margin-top:10px;">
            <div class="col"></div>
            <div class="col-5">
                <sec:authorize access="hasAuthority('ADMIN')">
                    <div class="form-check">
                        <input type="checkbox" class="form-check-input" id="exampleCheck1" name="blockConnectedContracts" hidden>
                        <label class="form-check-label" for="exampleCheck1" name="checkbox2" hidden>Block all connected with this option contracts</label>
                    </div>
                    <input class="btn btn-lg btn-primary btn-block" onclick="validateAndSubmitIfTrue()"
                           style="width:35%; clear:both; margin-top:20px;" hidden name="SaveChangesButton" value="Save changes"></input>
                    <input class="btn btn-lg btn-primary btn-block" onclick="deleteOption()"
                           style="width:35%; clear:both; float:right; color:white; background-color: red; margin-top:-48px;" hidden name="deleteOptionButton" value="Delete Option"></input>
                </sec:authorize>
            </div>
            <div class="col"></div>
        </div>
        </form:form>

    </div>
</div>
</body>


<jsp:directive.include file = "footerTemplate.jsp" />

</html>