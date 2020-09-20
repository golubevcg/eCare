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
    <link rel="stylesheet" href="/resources/styles/privateOfficeWorker.css">
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
            <h1 class="display-4" id="privateOfficeLabel">Личный кабинет</h1>
        </div>

        <div class="col"></div>
    </div>

</div>


<div class="row" style="margin-top:10px;">
    <div class="col"></div>
    <div class="col-5">
        <button type="button" class="btn btn-primary btn-lg btn-lg" id="addContractButton"> + Добавить новый контракт</button>
    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:20px;">
    <div class="col"></div>
    <div class="col-5">
        <p style="font-weight: bolder;">Поиск клиента по номеру телефона</p>
        <div style=" margin-top: -10px">
                <input class="form-control" type="text" style="width:65%; background-color: white; float:left; border-color: black;" placeholder="введите номер телефона">
                <button type="button" class="btn btn-primary btn-lg btn-lg" id="searchButton">Показать всё</button>
                <button type="button" class="btn btn-primary btn-lg btn-lg" id="searchButton">Поиск</button>

        </div>
    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:5px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="row">
            <div class="col-3">
                <p class="lead" id="columnDiscriptionLabels">Имя Фамилия</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnDiscriptionLabels">Номер</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnDiscriptionLabels"> Тариф </p>
            </div>

        </div>

        <hr style="margin-top:-10px;">

    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:-10px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="row">
            <div class="col-3">
                <p class="lead" id="columnContentLabels">Иван Иванов</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnContentLabels">+7-911-756-45-46</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnContentLabels"> Тариф 1</p>
            </div>

            <div class="col-3">
                <button type="button" class="btn btn-primary btn-lg btn-lg" id="editButton">редактировать</button>
            </div>
        </div>
    </div>

    <div class="col"></div>
</div>



<div class="row" style="margin-top:30px;">
    <div class="col"></div>
    <div class="col-5">
        <p style="font-weight: bolder;">Поиск тарифа по названию</p>
        <div style=" margin-top: -10px">
            <input class="form-control" type="text" style="width:65%; background-color: white; float:left; border-color: black;" placeholder="введите название тарифа">
            <button type="button" class="btn btn-primary btn-lg btn-lg" id="searchButton">Показать всё</button>
            <button type="button" class="btn btn-primary btn-lg btn-lg" id="searchButton">Поиск</button>
        </div>
    </div>

    <div class="col"></div>
</div>


<div class="row" style="margin-top:5px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="row">
            <div class="col-4">
                <p class="lead" id="columnDiscriptionLabels">Название</p>
            </div>

            <div class="col-4">
                <p class="lead" id="columnDiscriptionLabels">Цена</p>
            </div>


        </div>

        <hr style="margin-top:-10px;">

    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:-10px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="row">
            <div class="col-3">
                <p class="lead" id="columnContentLabels">Тариф 1</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnContentLabels">200 руб./месяц</p>
            </div>

            <div class="col-3">
            </div>
            <div class="col-3">
                <button type="button" class="btn btn-primary btn-lg btn-lg" id="editButton">редактировать</button>
            </div>
        </div>
    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:20px;">
    <div class="col"></div>
    <div class="col-5">
        <p style="font-weight: bolder;">Поиск опции по названию</p>
        <div style=" margin-top: -10px">
            <input class="form-control" type="text" style="width:65%; background-color: white; float:left; border-color: black;" placeholder="введите название опции">
            <button type="button" class="btn btn-primary btn-lg btn-lg" id="searchButton">Показать всё</button>
            <button type="button" class="btn btn-primary btn-lg btn-lg" id="searchButton">Поиск</button>
        </div>
    </div>

    <div class="col"></div>
</div>


<div class="row" style="margin-top:5px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="row">
            <div class="col-3">
                <p class="lead" id="columnDiscriptionLabels">Название</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnDiscriptionLabels">Стоимость подключения</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnDiscriptionLabels">Цена</p>
            </div>



        </div>

        <hr style="margin-top:-10px;">

    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:10px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="row">
            <div class="col-3">
                <p class="lead" id="columnContentLabels">Опция 1</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnContentLabels">50 рублей</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnContentLabels">100 руб./мес.</p>
            </div>

            <div class="col-3">
                <button type="button" class="btn btn-primary btn-lg btn-lg" id="editButton">редактировать</button>
            </div>
        </div>
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