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
    <link type="text/css" rel="stylesheet" href="/resources/styles/privateOfficeWorker.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <!--for dropdown menus scripts-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

</head>
<body>

<jsp:directive.include file = "headerTemplateAdmin.jsp" />

<div>
    <script src="/resources/js/workerOffice.js"></script>
</div>

<div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">

    <div class="row">
        <div class="col"></div>

        <div class="col-5">
            <h1 class="display-4" id="privateOfficeLabel">My account</h1>
        </div>

        <div class="col"></div>
    </div>

</div>

<div class="row" style="margin-top:20px;">
    <div class="col"></div>
    <div class="col-5">
        <p style="font-weight: bolder; float:left;">Search for a contract using phone number</p>

        <div style="margin-top: 20px">
                <input class="form-control searchForm" type="text" style="width:100%; background-color: white; float:left; border-color: black;"
                       id="searchByPhoneNumberInputForm" onkeyup="searchContractByPhoneNumber(0,0)" placeholder="Enter phone number">
        </div>
    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:5px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="row">
            <div class="col-4">
                <p class="lead" id="columnDiscriptionLabels">Phone number</p>
            </div>

            <div class="col-4">
                <p class="lead" id="columnDiscriptionLabels">First and Last Name</p>
            </div>

            <div class="col-2">
                <p class="lead" id="columnDiscriptionLabels"> Tariff </p>
            </div>

        </div>

    </div>
    <div class="col"></div>
</div>

<div class="row" style="margin-top:-10px;">
    <div class="col"></div>
    <div class="col-5" id="foundedContractsRow">
    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:25px;">
    <div class="col"></div>
    <div class="col-5">
        <p style="font-weight: bolder;">Search for tariff by name</p>
        <div style=" margin-top: -10px">
            <input class="form-control searchForm" type="text" onkeyup="searchTariffByName()"
                   id="searchByTariffNameInputForm" placeholder="Enter Tariff name">
        </div>
    </div>

    <div class="col"></div>
</div>


<div class="row" style="margin-top:5px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="row">
            <div class="col-5">
                <p class="lead" id="columnDiscriptionLabels">Name</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnDiscriptionLabels">Price</p>
            </div>


        </div>

    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:-10px;">
    <div class="col"></div>
    <div class="col-5" id="foundedTariffsRow">

    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:25px;">
    <div class="col"></div>
    <div class="col-5">
        <p style="font-weight: bolder;">Search option by name</p>
        <div style=" margin-top: -10px">
            <input class="form-control searchForm" type="text"
                   id="searchByOptionNameInputForm" onkeyup="searchOptionByName()" placeholder="Enter option name">
        </div>
    </div>

    <div class="col"></div>
</div>


<div class="row" style="margin-top:5px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="row">
            <div class="col-3">
                <p class="lead" id="columnDiscriptionLabels">Name</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnDiscriptionLabels">Connection price</p>
            </div>

            <div class="col-3">
                <p class="lead" id="columnDiscriptionLabels">Price</p>
            </div>

        </div>

    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:-10px;">
    <div class="col"></div>
    <div class="col-5"  id="foundedOptionsRow">

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