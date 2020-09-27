<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
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

<script type="text/javascript">
    $(document).ready(function(){

        $('#tariffsList').on('change', function()
        {
            var selectedTariff = $(this).val();
            $.ajax({
                type: 'GET',
                url: '${pageContext.request.contextPath }/userRegistration/loadOptionByTariff/' + selectedTariff,
                success: function(result){
                    var result = JSON.parse(result);
                    var s = '';
                    for(var i = 0; i < result.length; i++){
                        s+='<option value="' + result[i].id + '">' + result[i] + '</option>';
                    }
                    $('#optionsList').html(s);
                }
            });
        });
    });

    document.addEventListener("DOMContentLoaded", function() {
            var selectedTariff = $('#tariffsList').val();
            $.ajax({
                type: 'GET',
                url: '${pageContext.request.contextPath }/userRegistration/loadOptionByTariff/' + selectedTariff,
                success: function(result){
                    var result = JSON.parse(result);
                    var s = '';
                    for(var i = 0; i < result.length; i++){
                        s+='<option value="' + result[i].id + '">' + result[i] + '</option>';
                    }
                    $('#optionsList').html(s);
                }
            });
    });

    $(document).ready(function(){
        $(".mul-select").select2({
            // placeholder: ""
            tags: true,
            tokenSeparators: ['/',',',';'," "]
        });
    })

    function revealTarifOptionsSelector() {
        var x = document.getElementById("tarifOptionsForUserRegistration");
        if (x.style.display === "none") {
            x.style.display = "block";
        } else {
            x.style.display = "none";
        }
    }

</script>

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
                <a class="dropdown-item" href="/userRegistration">Регистрация</a>
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

        <form:form method="POST" modelAttribute="userForm" class="form-signin" id="userDTOInputForm">

            <spring:bind path="firstname" >
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="firstname" class="form-control defaultForm" type="text"
                                style="width:40%; margin-top:-20px;" placeholder="Имя"></form:input>
                    <form:errors path="firstname" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="secondname">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="secondname" class="form-control defaultForm" type="text"
                                style="width:40%;" placeholder="Фамилия"></form:input>
                    <form:errors path="secondname" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="dateOfBirth">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <input path="dateOfBirth" class="form-control defaultForm" type="date" dataformatas="yyyy-MM-dd" name="dateOfBirth"
                           style="width:25%;" placeholder="Дата рождения"></input>
                    <form:errors path="dateOfBirth" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>


            <spring:bind path="passportInfo">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="passportInfo" class="form-control defaultForm" type="number"
                                style="width:40%;" placeholder="Паспортные данные"></form:input>
                    <form:errors path="passportInfo" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="address">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="address" class="form-control defaultForm" type="text"
                                style="width:57%;" placeholder="Адрес"></form:input>
                    <form:errors path="address" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="email">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="email" class="form-control defaultForm" type="text"
                                style="width:40%;" placeholder="Электронная почта"></form:input>
                    <form:errors path="email" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="login">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="login" class="form-control defaultForm" type="text"
                                style="width:40%;" placeholder="Логин"></form:input>
                    <form:errors path="login" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="password">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="password" class="form-control defaultForm" type="password"
                                style="width:40%;" placeholder="Пароль"></form:input>
                    <form:errors path="password" id="errorsLabel" class="label"></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="confirmPassword">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="confirmPassword" class="form-control defaultForm" type="password"
                                style="width:40%;" placeholder="Подтвердите пароль"></form:input>
                    <form:errors path="confirmPassword" id="errorsLabel" ></form:errors>
                </div>
            </spring:bind>

            <spring:bind path="contractNumber">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <form:input path="contractNumber" class="form-control defaultForm" type="number"
                                style="width:40%;" placeholder="+7XXXXXXXXXX"></form:input>
                    <form:errors path="contractNumber" id="errorsLabel" ></form:errors>
                </div>
            </spring:bind>


        <label class="container" id="labelCheckboxContainer" style=" margin-left:20px;">Сотрудник компании
            <input type="checkbox" name="roleCheckbox" value="true" onclick="revealTarifOptionsSelector()">
            <span class="checkmark" style="margin-top:12px; margin-left:-15px;"></span>
        </label>



        <div id="tarifOptionsForUserRegistration">
            <label class="container" style="clear:both; float:left; padding-left:0;">Выберите тариф:</label>

            <select class="form-control form-control-lg" style="clear:both; width: 60%; margin-top:10px;"
                    name="selectedTariff" id="tariffsList">
                <c:forEach items="${listOfTariffs}" var="tariff">

                    <c:choose>
                        <c:when test="${tariff.name eq selectedTariff}">
                            <option selected>${tariff.name}</option>
                        </c:when>
                        <c:otherwise>
                            <option>${tariff.name}</option>
                        </c:otherwise>
                    </c:choose>

                </c:forEach>
            </select>

            <label class="container" id="labelCheckboxContainer" style="float:left; padding-left:0;">Выберите дополнительные опции:</label>

            <select class="mul-select" multiple="true" id="optionsList" style="width:60%; font-size: 20px" name="selectedOptions">
            </select>

        </div>





        <input class="btn btn-lg btn-primary btn-block" type="submit" style="width:35%; clear:both; margin-top:20px;"></input>


        </form:form>




    </div>
    <div class="col"></div>
</div>




</body>

<footer>
    <div class="footer" id="footer">
        <p class="lead" style="font-size: 15px; ">golubevcg@gmail.com
            2020</p>
    </div>
</footer>

</html>