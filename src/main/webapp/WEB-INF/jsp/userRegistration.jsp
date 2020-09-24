<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<!--suppress ALL -->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/styles/registration.css">
</head>

<body>
<div class="row" style="margin-top:10px;">
    <div class="col"></div>
    <div class="col-5">

        <img src="/resources/static/logo.png" class="rounded float-left" alt="..." style="width:65px; float:left;">

        <div class="dropdown" style="float:left; ">

            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Тарифы
            </button>

            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="#">Тариф1</a>
                <a class="dropdown-item" href="#">Тариф2</a>
                <a class="dropdown-item" href="#">Тариф3</a>
            </div>

        </div>

        <div class="dropdown" style="float:left;">

            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Опции
            </button>

            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="#">Опция1</a>
                <a class="dropdown-item" href="#">Опция2</a>
                <a class="dropdown-item" href="#">Опция3</a>
            </div>

        </div>

        <div class="dropdown" style="float:right; ">

            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                    data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Личный кабинет
            </button>

            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" href="#">Личный кабинет</a>
                <a class="dropdown-item" href="<c:url value="/logout" />">Выйти</a>
            </div>

        </div>

    </div>
    <div class="col"></div>
</div>

<div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">

    <div class="row">
        <div class="col"></div>

        <div class="col-5">
            <h1 class="display-4" id="privateOfficeLabel">Регистрация Нового пользователя</h1>
        </div>

        <div class="col"></div>
    </div>

</div>

<div class="row">
    <div class="col"></div>
    <div class="col-5">

        <form:form method="POST" modelAttribute="userForm" class="form-signin">

            <spring:bind path="firstname">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="firstname" class="form-control" type="text" id="defaultForm"
                                style="width:40%; margin-top:-20px;" placeholder="Имя"></form:input>
                    <form:errors path="firstname" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="secondname">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="secondname" class="form-control" type="text" id="defaultForm"
                                style="width:40%;" placeholder="Фамилия"></form:input>
                    <form:errors path="secondname" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="dateOfBirth">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <input path="dateOfBirth" class="form-control" type="date" dataformatas="yyyy-MM-dd" id="defaultForm" name="dateOfBirth"
                           style="width:25%;" placeholder="Дата рождения"></input>
                    <form:errors path="dateOfBirth" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>


            <spring:bind path="passportInfo">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="passportInfo" class="form-control" type="number" id="defaultForm"
                                style="width:30%;" placeholder="Паспортные данные"></form:input>
                    <form:errors path="passportInfo" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="address">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="address" class="form-control" type="text" id="defaultForm"
                                style="width:57%;" placeholder="Адрес"></form:input>
                    <form:errors path="address" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="email">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="email" class="form-control" type="text" id="defaultForm"
                                style="width:40%;" placeholder="Электронная почта"></form:input>
                    <form:errors path="email" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="login">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="login" class="form-control" type="text" id="defaultForm"
                                style="width:40%;" placeholder="Логин"></form:input>
                    <form:errors path="login" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="password">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="password" class="form-control" type="text" id="defaultForm"
                                style="width:40%;" placeholder="Пароль"></form:input>
                    <form:errors path="password" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="confirmPassword">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="confirmPassword" class="form-control" type="text" id="defaultForm"
                                style="width:40%;" placeholder="Подтвердите пароль"></form:input>
                    <form:errors path="confirmPassword" id="errorsLabel"></form:errors>
                </div>
            </spring:bind>

        <label class="container" id="labelCheckboxContainer">Сотрудник компании
            <input type="checkbox" checked="checked">
            <span class="checkmark" style="margin-top:12px;"></span>
        </label>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Submit</button>

        </form:form>




        <h3 class="display-4" id="ContractLabel">Контракт</h3>

        <input class="form-control" type="text" id="defaultForm" style="width:40%; margin-bottom:15px;margin-top:0px;" placeholder="+7-ХХХ-ХХХ-ХХ-ХХ">

        <select class="form-control form-control-lg" style="clear:both; width: 60%; margin-top:10px;">
            <option>Тариф</option>
        </select>

        <select class="form-control form-control-lg" style="width: 60%; margin-top:10px; margin-bottom: 10px">
            <option>Опции</option>
        </select>

    </div>
    <div class="col"></div>
</div>




<!--for dropdown menus scripts-->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</body>

<footer>
    <div class="footer" id="footer">
        <p class="lead" style="font-size: 15px; ">golubevcg@gmail.com
            2020</p>
    </div>
</footer>

</html>