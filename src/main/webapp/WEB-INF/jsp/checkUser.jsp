<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/styles/checkUser.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/css/select2.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.0/js/select2.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</head>

<div>
    <script src="/resources/js/checkUser.js"></script>
</div>

<body>
<div id="page-container">
    <div id="content-wrap">

    <jsp:directive.include file = "headerTemplateAdmin.jsp" />
    <div></div>

    <div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">

        <div class="row">
            <div class="col"></div>

            <div class="col-5">
                <h1 class="display-4" id="privateOfficeLabel">New user registration</h1>
                <sec:authorize access="hasAuthority('ADMIN')">
                    <button type="button" class="btn btn-primary"
                            style="float: left;width:11%; font-size: 15px; background-color: white; color:black;"
                            onclick="makePageEditable()">Edit</button>
                </sec:authorize>
            </div>

            <div class="col"></div>
        </div>

    </div>

    <div class="row">
        <div class="col"></div>
        <div class="col-5">

            <form:form method="POST" modelAttribute="userForm" class="form-signin" id="userDTOInputForm">
                <label>Firstname:</label>
                <spring:bind path="firstname" >
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="firstname" class="form-control defaultForm" type="text"
                                    style="width:40%; margin-top:-5px;" placeholder="First name" disabled="true" id="firstname"></form:input>
                        <form:errors path="firstname" id="errorsLabel" class="label"></form:errors>
                    </div>
                </spring:bind>
                <label style="color:red; clear:both; float:left;" id="firstnameFieldRequired" hidden>This field is required.</label>


                <label style="clear:both; float:left; margin-top:5px; margin-bottom:-10px;">Secondname:</label>
                <spring:bind path="secondname">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="secondname" class="form-control defaultForm" type="text"
                                    style="width:40%;" placeholder="Last name" disabled="true" id="secondname"></form:input>
                        <form:errors path="secondname" id="errorsLabel" class="label"></form:errors>
                    </div>
                </spring:bind>
                <label style="color:red; clear:both; float:left;" id="secondnameFieldRequired" hidden>This field is required.</label>


                <label style="clear:both; float:left; margin-top:5px; margin-bottom:-10px;">Date Of Birth:</label>
                <spring:bind path="dateOfBirth">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <input path="dateOfBirth" class="form-control defaultForm" type="date" dataformatas="yyyy-MM-dd" name="dateOfBirth"
                               style="width:25%;" placeholder="Date of birth" value="${userForm.dateOfBirth.toString().substring(0,10)}" disabled="true"
                        id="dateOfBirth">
                        <form:errors path="dateOfBirth" id="errorsLabel" class="label"></form:errors>
                    </div>
                </spring:bind>
                <label style="color:red; clear:both; float:left;" id="dateOfBirthFieldRequired" hidden>This field is required.</label>



                <label style="clear:both; float:left; margin-top:5px; margin-bottom:-10px;">Passport information:</label>
                <spring:bind path="passportInfo">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="passportInfo" class="form-control defaultForm" type="number"
                                    style="width:40%;" placeholder="Passport information" disabled="true"
                        id="passportInfo"></form:input>
                        <form:errors path="passportInfo" id="errorsLabel" class="label"></form:errors>
                    </div>
                </spring:bind>
                <label style="color:red; clear:both; float:left;" id="passportInfoFieldRequired" hidden>This field is required.</label>


                <label style="clear:both; float:left; margin-top:5px; margin-bottom:-10px;">Adress:</label>
                <spring:bind path="address">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="address" class="form-control defaultForm" type="text"
                                    style="width:57%;" placeholder="Adress" disabled="true" id="adress"></form:input>
                        <form:errors path="address" id="errorsLabel" class="label"></form:errors>
                    </div>
                </spring:bind>
                <label style="color:red; clear:both; float:left;" id="adressInfoFieldRequired" hidden>This field is required.</label>


                <label style="clear:both; float:left; margin-top:5px; margin-bottom:-10px;">Email:</label>
                <spring:bind path="email">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="email" class="form-control defaultForm" type="text"
                                    style="width:40%;" placeholder="E-mail" disabled="true" id="email"></form:input>
                        <form:errors path="email" id="errorsLabel" class="label"></form:errors>
                    </div>
                </spring:bind>
                <label style="color:red; clear:both; float:left;" id="emailInfoFieldRequired" hidden>This field is required.</label>


                <label style="clear:both; float:left; margin-top:5px; margin-bottom:-10px;">Login:</label>
                <spring:bind path="login">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input path="login" class="form-control defaultForm" type="text"
                                    style="width:40%;" placeholder="Login" disabled="true" id="login"></form:input>
                        <form:errors path="login" id="errorsLabel" class="label"></form:errors>
                    </div>
                </spring:bind>
                <label style="color:red; clear:both; float:left;" id="loginInfoFieldRequired" hidden>This field is required.</label>


                <label style="clear:both; float:left; margin-top:5px; margin-bottom:-10px;">Connected contracts:</label>

                <div style="margin-top: 10px; margin-bottom:20px;">
                    <c:forEach items="${userForm.listOfContracts}" var="contract" >
                        <hr style="clear:both; float:left; width: 100%;">
                        <a href="/checkContract/${contract.contract_id}">
                        <label style="clear:both; float:left; margin-top:5px; margin-bottom:5px;">${contract.contractNumber}</label>
                        </a>
                    </c:forEach>
                </div>

                <div style="margin-top:20px;">
                    <input class="btn btn-lg btn-primary btn-block" id="saveChangesButton" onclick="validateAndSubmitIfTrue()"
                           value="Save Changes" style="width:35%; clear:both; " hidden>

                    <input class="btn btn-lg btn-primary btn-block" onclick="deleteUser()"
                           style="width:35%; clear:both; float:right; color:white; background-color: red; margin-top:-48px;" hidden id="deleteUserButton" value="Delete User">
                </div>


            </form:form>

        </div>
        <div class="col"></div>
    </div>
    </div>
</div>

</body>

<jsp:directive.include file = "footerTemplate.jsp" />


</html>