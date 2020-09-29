<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!--suppress ALL -->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="/resources/styles/mainStyles.css">
</head>
<body>

<div class="modal" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="false">

    <div class="modal-dialog modal-sm" style="modal-dialog:show;" aria-hidden="false">

        <div class="modal-content">

            <div class="modal-body">


                <form action="${loginUrl}" method="post">
                    <c:if test="${param.error != null}">
                        <p>
                            Invalid username and password.
                        </p>
                    </c:if>

                    <c:if test="${param.logout != null}">
                        <p>
                            You have been logged out.
                        </p>
                    </c:if>

                    <p>
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username"/>
                    </p>

                    <p>
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password"/>
                    </p>

                    <input type="hidden"
                           name="${_csrf.parameterName}"
                           value="${_csrf.token}"/>
                    <button type="submit" class="btn">Log in</button>

                </form>


            </div>

        </div>

    </div>

</div>

<div class="jumbotron jumbotron-fluid" id="headerJumbotron">

    <div class="row">
        <div class="col"></div>
        <div class="col-5">

            <img src="/resources/static/logo1.png" class="rounded float-left" alt="..." style="width:65px; float:left;">

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

            <button class="btn btn my-2 my-sm-0 mr-auto" id="ownCabinet" type="submit" style="float:right;"
                    data-toggle="modal" data-target="#exampleModal">Личный кабинет</button>
        </div>
        <div class="col"></div>
    </div>

</div>
