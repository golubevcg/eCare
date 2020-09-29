<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>


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
<div class="fixed-header">
    <div class="jumbotron jumbotron-fluid" id="headerJumbotron">

        <div class="row">
            <div class="col"></div>
            <div class="col-5">

                    <img src="/resources/static/logo1.png" class="rounded float-left" alt="..."
                         style="width:80px; float:left;margin-top:-6px; margin-right:15px;">

                    <div class="dropdown" style="float:left; margin-bottom:5px;">

                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Тарифы
                        </button>

                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" href="#">Tariff1</a>
                            <a class="dropdown-item" href="#">Tariff2</a>
                            <a class="dropdown-item" href="#">Tariff3</a>
                        </div>

                    </div>

                    <div class="dropdown" style="float:left; margin-bottom:5px;">

                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Опции
                        </button>

                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" href="#">Option1</a>
                            <a class="dropdown-item" href="#">Option2</a>
                            <a class="dropdown-item" href="#">Option3</a>
                        </div>

                    </div>

                    <button class="btn btn my-2 my-sm-0 mr-auto" id="ownCabinet" type="submit" style="float:right;"
                            data-toggle="modal" data-target="#exampleModal">My account</button>
            </div>
            <div class="col"></div>
        </div>

    </div>
</div>

<div class="jumbotron jumbotron-fluid" id="tryTarifsNowJumbotronFluid">
    <div class="row">
        <div class="col"></div>

        <div class="col">

            <h1 class="display-4" id="tryNewTarifsNow">Try new tariffs now</h1>
            <button type="button" id="detailsButton"
                    class="btn btn-primary btn-sm">More details</button>

            <div class="col-5" id="imageContainer"></div>

        </div>

        <div class="col"></div>

    </div>
</div>

<div class="row" style="margin-top:-20px">
    <div class="col"></div>
    <div class="col-5">
        <h4 class id="tarifs">Tariffs</h4>
    </div>
    <div class="col"></div>
</div>


<div class="row" style="margin-top:-10px; ">
    <div class="col"></div>

    <div class="col-5">
        <div class="card">
            <h5 class="card-header">Base</h5>
            <div class="card-body">
                <p class="card-text">200 minutes<br />
                    10 гигабайт <br /> 100 messages <br /> Telegram, Whatsup, <br /> Viber available <br /> even at zero</p>
                <a href="#" class="btn" id="cardButton">More details</a>
            </div>
        </div>

        <div class="card">
            <h5 class="card-header">Standart</h5>
            <div class="card-body">
                <p class="card-text">300 minutes<br />
                    20 гигабайт <br /> 200 messages <br /> Telegram, Whatsup, <br /> Viber available <br /> even at zero</p>
                <a href="#" class="btn" id="cardButton">More details</a>
            </div>
        </div>

        <div class="card">
            <h5 class="card-header">Расширеный</h5>
            <div class="card-body">
                <p class="card-text">400 minutes<br />
                    30 гигабайт <br /> 300 messages <br /> Unlimited </br>video services,</br> messengers</p>
                <a href="#" class="btn" id="cardButton">More details</a>
            </div>
        </div>

    </div>

    <div class="col"></div>
</div>


<div class="row">
    <div class="col"></div>
    <div class="col-5">

        <div class="jumbotron" id="switchWithOldNumberJumbotron">
            <div class="col" style="float:right; width: 55%">
                <h1 class="display-4"id="keepNumberLabel" >Go to us with <br> old number</h1>
                <form>
                    <div class="form-group">
                        <input type="email" class="form-control" id="inputPhone" aria-describedby="emailHelp"
                               placeholder="+7">
                    </div>
                </form>
                <button type="button" id="keepNumberButton" class="btn btn-primary btn-sm">Submit</button>
            </div>

            <div class="col" style="float:left; width: 45%">
                <img src="/resources/static/oldPhone.png" class="rounded float-left" alt="..."
                     style="width:275px; float:left; margin-left: -15px; margin-top:10px">
            </div>

        </div>

    </div>
    <div class="col"></div>
</div>

<div class="row" style="margin-top:-10px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="jumbotron" id="unlimitedCallsJumbotron" >
            <p class="lead" style="font-weight: bolder; font-size: 25px; margin-left:-15px;">Unlimited calls worldwide</p>
            <p class="lead" style="font-size: 17px; margin-left:-10px; margin-top:-15px; ">When traveling, do not forget to call<br>loved ones with the option<br>"Connected everywhere"</p>
        </div>
    </div>
    <div class="col"></div>
</div>

<div class="row" style="margin-top:-20px;">
    <div class="col"></div>
    <div class="col-5">

        <div class="jumbotron" id="jumbotron1">
            <p class="lead" style="font-weight: bolder; font-size: 20px; margin-left:-15px;">Didn't have enough communication?</p>
            <p class="lead" style="font-size: 15px; margin-top:-15px; margin-left:-13px;">Purchase additional minute packages as 100, 200 and 300 minutes options</p>
        </div>

        <div class="jumbotron" id="jumbotron2">
            <p class="lead" style="font-weight: bolder; font-size: 20px; margin-left:-15px;">10 GB \ 2$.</p>
            <p class="lead" style="font-size: 15px; margin-top:-15px; margin-left:-13px;">Lowest price for internet package</p>
        </div>
    </div>
    <div class="col"></div>
</div>

<div class="row" style="margin-top:-20px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="jumbotron" id="jumbotron3">
            <p class="lead" style="font-weight: bolder; font-size: 20px; margin-left:-15px;">Trust payment</p>
            <p class="lead" style="font-size: 15px; margin-top:-15px; margin-left:-13px;">If the balance is close to zero, you can activate the Trust payment. We will top up your account for 3 days - and you will immediately continue your communication.
                The amount of the Trustee payment and the service fee depend on your tariff and the average communication costs for the last three months.
            </p>
        </div>
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