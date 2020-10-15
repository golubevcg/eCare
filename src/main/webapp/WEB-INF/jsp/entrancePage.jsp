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
    <link type="text/css" rel="stylesheet" href="/resources/css/entrancePage.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

</head>
<body>

<jsp:directive.include file = "headerTemplateDefault.jsp" />
<div></div>
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
                    10 Gb <br /> 100 messages <br /> Telegram, Whatsup, <br /> Viber available <br /> even at zero</p>
                <a href="#" class="btn cardButton">More details</a>
            </div>
        </div>

        <div class="card">
            <h5 class="card-header">Standart</h5>
            <div class="card-body">
                <p class="card-text">300 minutes<br />
                    20 Gb <br /> 200 messages <br /> Telegram, Whatsup, <br /> Viber available <br /> even at zero</p>
                <a href="#" class="btn cardButton">More details</a>
            </div>
        </div>

        <div class="card">
            <h5 class="card-header">Extended</h5>
            <div class="card-body">
                <p class="card-text">400 minutes<br />
                    30 Gb <br /> 300 messages <br /> Unlimited </br>video services,</br> messengers</p>
                <a href="#" class="btn cardButton">More details</a>
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
            <p class="lead" style="font-weight: bolder; font-size: 20px; margin-left:-15px;">Not enough communication?</p>
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
</body>

<jsp:directive.include file = "footerTemplate.jsp" />

</html>