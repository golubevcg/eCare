<!DOCTYPE html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/styles/privateOfficeClient.css">
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
                <a class="dropdown-item" href="<c:url value="/logout"/>">Выйти</a>
            </div>

        </div>
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

<div class="row">
    <div class="col"></div>

    <div class="col-5">
        <h1 class="display-4" id="numberLabel">${contractNumber}</h1>
        <h1 class="display-4" id="firstSecondNameLabel">${firstAndSecondNames}</h1>
    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:15px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="jumbotron" id="choosenTarifJumbotron" style="margin-top:15px;">
            <div class="row">
                <p class="lead" style="font-family: MS Shell Dig 2; font-weight: bolder;  font-size: 25px; float:left;">${selectedTariff}</p>
            </div>

            <div class="row">
                <p class="lead" style="font-family: MS Shell Dig 2; font-size: 20px; float:left;">${tariffDecription}<br>
                Viber, WhatsApp</p>
            </div>

            <div class="row">
                <p class="lead" style="font-family: MS Shell Dig 2; font-weight: bolder; font-size: 25px; float:left;">${tariffPrice}</p>
            </div>
        </div>
    </div>
    <div class="col"></div>
</div>

<div class="row" style="margin-top:-5px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="jumbotron" id="choosenTarifJumbotron" >
            <div class="row">
                <p class="lead"
                   style="font-family: MS Shell Dig 2; font-weight: bolder;  font-size: 25px; float:left;">
                    Тарифные планы</p>
            </div>

            <div class="row" >
                <div class="col-3">
                    <p class="lead" id="columnDiscriptionLabels">Тариф</p>
                </div>

                <div class="col-2">
                    <p class="lead" id="columnDiscriptionLabels">Стоимость</p>
                </div>

                <div class="col-5">
                    <p class="lead" id="columnDiscriptionLabels">Описание</p>
                </div>

                <div class="col-1">
                </div>

            </div>

            <hr style="width: 105%; clear:both; margin-left:-17px;">

            <div class="row" >
                <c:forEach items="${activeTariffsList}" var="tariff"  varStatus="status">
                    <div class="col-3">
                        <p class="lead" id="columnContentLabels">${tariff.name}</p>
                    </div>

                    <div class="col-2">
                        <p class="lead" id="columnContentLabels">${tariff.price}</p>
                    </div>

                    <div class="col-5">
                        <p class="lead" id="columnContentLabels">${tariff.shortDiscription}</p>
                    </div>


                    <div class="col-1">
                        <div class="form-check" id="group1">
                            <c:choose>
                                <c:when test="${tariff.name eq selectedTariff}">
                                    <label class="switch" style="clear:both; margin-top:5px;" name="group1">
                                        <input type="checkbox" checked name="group1">
                                        <span class="slider round" name="group1"></span>
                                    </label>
                                </c:when>
                                <c:otherwise>
                                    <label class="switch" style="clear:both; margin-top:5px;" name="group1">
                                        <input type="checkbox" name="group1">
                                        <span class="slider round" name="group1"></span>
                                    </label>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${status.last}">
                        </c:when>
                        <c:otherwise>
                            <hr class="rounded">
                        </c:otherwise>
                    </c:choose>

                </c:forEach>


            </div>


            <div class="row">
            </div>

        </div>
    </div>
    <div class="col"></div>
</div>

<div class="row" style="margin-top:-5px; clear:both;">
    <div class="col"></div>
    <div class="col-5">
        <div class="jumbotron" id="choosenTarifJumbotron" >
            <div class="row">
                <p class="lead"
                   style="font-family: MS Shell Dig 2; font-weight: bolder;  font-size: 25px; float:left;">
                    Доступные опции</p>
            </div>

            <div class="row">
                <div class="col-3">
                    <p class="lead" id="columnDiscriptionLabels">Опция</p>
                </div>

                <div class="col-2">
                    <p class="lead" id="columnDiscriptionLabels">Стоимость</p>
                </div>

                <div class="col-5">
                    <p class="lead" id="columnDiscriptionLabels">Описание</p>
                </div>

                <div class="col-1">
                </div>

            </div>

            <hr style="margin-top:-10px;">

            <div class="row" >
                <c:forEach items="${listOfOptions}" var="option"  varStatus="status">
                    <div class="col-3">
                        <p class="lead" id="columnContentLabels">${option.name}</p>
                    </div>

                    <div class="col-2">
                        <p class="lead" id="columnContentLabels">${option.price}</p>
                    </div>

                    <div class="col-5">
                        <p class="lead" id="columnContentLabels">${option.shortDiscription}</p>
                    </div>


                    <div class="col-1">
                        <div class="form-check" id="group1">
<%--                            <c:choose>--%>
<%--                                <c:when test="${tariff.name eq selectedTariff}">--%>
                                    <label class="switch" style="clear:both; margin-top:5px;" name="group1">
                                        <input type="checkbox" checked name="group1">
                                        <span class="slider round" name="group1"></span>
                                    </label>
<%--                                </c:when>--%>
<%--                                <c:otherwise>--%>
<%--                                    <label class="switch" style="clear:both; margin-top:5px;" name="group1">--%>
<%--                                        <input type="checkbox" name="group1">--%>
<%--                                        <span class="slider round" name="group1"></span>--%>
<%--                                    </label>--%>
<%--                                </c:otherwise>--%>
<%--                            </c:choose>--%>
                        </div>
                    </div>

                    <c:choose>
                        <c:when test="${status.last}">
                        </c:when>
                        <c:otherwise>
                            <hr class="rounded">
                        </c:otherwise>
                    </c:choose>

                </c:forEach>

            <div class="row">
            </div>

        </div>
    </div>
    <div class="col"></div>
</div>
    <div class="col"></div>
</div>

<div class="row" style="margin-bottom:30px">
    <div class="col"></div>

    <div class="col-4">
        <h1 class="display-4" id="blockNumber">Заблокировать номер</h1>
    </div>

    <div>
        <label class="switch" style="clear:both; margin-top:5px;" name="group1">
            <input type="checkbox" checked name="group1">
            <span class="slider round" name="group1"></span>
        </label>


    </div>



    <div class="col"></div>
</div>


<div class="row" style="margin-bottom:30px">
    <div class="col"></div>
        <input class="btn btn-lg btn-primary btn-block"
               type="submit" style="width:20%;"
               value="Сохранить изменения"></input>
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