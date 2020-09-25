<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<!--suppress ALL -->
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/styles/newTarif.css">
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

        <button class="btn btn my-2 my-sm-0 mr-auto" id="ownCabinet" type="submit" style="float:right;"
                data-toggle="modal" data-target="#exampleModal">Личный кабинет</button>
    </div>
    <div class="col"></div>
</div>

<div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">

    <div class="row">
        <div class="col"></div>

        <div class="col-5">
            <h1 class="display-4" id="privateOfficeLabel">Опция 1</h1>
        </div>

        <div class="col"></div>
    </div>

</div>

<div class="row" style="margin-top: 10px">
    <div class="col"></div>

    <div class="col-5">
        <div>
            <p id="headlineLabel" style="float:left; width:13%;">Цена</p>
            <p id="headlineLabel" style="float:left; width:40%;">Название</p>

        </div>

        <div style="clear:both;">
        <input class="form-control" type="text" style="width:12%; background-color: #ffffff; float:left; border-color: black;  margin-top:-10px" placeholder="Цена">
        <input class="form-control" type="text" style="width:40%; background-color: white; float:left; border-color: black; margin-left:2%; margin-top:-10px" placeholder="Название">
        </div>
    </div>

    <div class="col"></div>
</div>

<div class="row">
    <div class="col"></div>

    <div class="col-5">
        <p id="headlineLabel" style="margin-top: 10px">Описание</p>
        <input class="form-control" type="text" style="width:50%; height:100px; background-color: white; float:left; border-color: black; margin-top:-10px;" placeholder="Описание">
    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:10px;">
    <div class="col"></div>
    <div class="col-5">
        <div style="margin-top:5px;">
            <p id="headlineLabel" >Доступные опции</p>
            <select class="form-control form-control-lg" style="width: 100%; margin-top:-10px; margin-bottom: 10px">
                <option>Доступные опции</option>
            </select>
        </div>
    </div>
    <div class="col"></div>
</div>

<div class="row" style="margin-top:10px;">
    <div class="col"></div>
    <div class="col-5">
        <button type="button" class="btn btn-primary btn-lg btn-lg" id="saveButton"> Сохранить</button>
    </div>

    <div class="col"></div>
</div>




<!--for dropdown menus scripts-->
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
</body>

<div class="footer" id="footer">
    <p class="lead" style="font-size: 15px; ">golubevcg@gmail.com 2020</p>
</div>



</html>