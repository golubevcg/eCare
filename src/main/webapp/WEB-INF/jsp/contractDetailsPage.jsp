<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Contract details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/css/contractDetailsPage.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>

</head>

<body>
<jsp:directive.include file = "headerTemplateByRole.jsp" />


<div>
        <script src="/resources/js/contractDetailsPage.js"></script>
</div>

<div class="jumbotron jumbotron-fluid" id="privateOfficeJumbotron">

    <div class="row">
        <div class="col"></div>

        <div class="col-5">
            <h1 class="display-4" id="privateOfficeLabel">Contract details</h1>
        </div>

        <div class="col"></div>
    </div>

</div>

<div class="row">
    <div class="col"></div>

    <div class="col-5">
        <h1 class="display-4" name="numberLabel">${contractNumber}</h1>
        <h1 class="display-4" id="firstSecondNameLabel">${firstAndSecondNames}</h1>


    </div>

    <div class="col"></div>
</div>

<div class="row" style="margin-top:15px;">
    <div class="col"></div>
    <div class="col-5">
        <p class="lead"
           style="font-family: MS Shell Dig 2; font-size: 20px; float:left;
           margin-bottom:10px; margin-left:10px; margin-top:30px;">
            Сurrent tariff:</p>
        <c:if test="${isBlocked eq true}">
            <p class="lead" name="numberLabel" style="color:red; float:right; font-weight:bold; font-size: 30px; margin-top:10px;">Contract blocked</p>
        </c:if>
        <div class="jumbotron" id="choosenTarifJumbotron" style="clear:both; margin-top:15px;">
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

<div class="row" style="margin-top: -20px;">
    <div class="col"></div>
    <div class="col-5">
        <p class="lead"
           style="font-family: MS Shell Dig 2; font-size: 20px; float:left; margin-bottom:10px; margin-left:10px;">
            Сonnected options:</p>
        <div class="jumbotron choosenTariffJumbotron" style="clear:both; padding-top:20px;">

            <c:choose>
                <c:when test="${empty connectedOptions}">
                <div class="row">
                    <div class="col">
                        <p class="lead"
                           style="font-family: MS Shell Dig 2; font-size: 18px; float:left;"
                        >No options connected.</p>
                    </div>
                </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${connectedOptions}" var="option"  varStatus="status" >

                        <div class="row">
                            <div class="col">
                                <p class="lead"
                                   style="font-family: MS Shell Dig 2; font-size: 18px; float:left;"
                                >${option.name} </p>
                            </div>
                            <div class="col">
                                <p class="lead"
                                   style="font-family: MS Shell Dig 2; font-size: 18px; float:left;"
                                >${option.price} $ / month</p>
                            </div>
                            <div class="col">
                                <p class="lead"
                                   style="font-family: MS Shell Dig 2; font-size: 18px; float:left;"
                                >${option.shortDescription} </p>
                            </div>

                            <c:choose>
                                <c:when test="${status.last}">
                                </c:when>
                                <c:otherwise>
                                    <hr class="rounded" style="width: 100%; clear:both;">
                                </c:otherwise>
                            </c:choose>

                        </div>

                    </c:forEach>
                </c:otherwise>
             </c:choose>

        </div>
    </div>
    <div class="col"></div>
</div>

<div class="row" style="margin-top:-5px;">
    <div class="col"></div>
    <div class="col-5">
        <div class="jumbotron choosenTarifJumbotron">
            <div class="row">
                <p class="lead"
                   style="font-family: MS Shell Dig 2; font-weight: bolder;  font-size: 25px; float:left;">
                    Available tariffs</p>
            </div>

            <div class="row" >
                <div class="col-3">
                    <p class="lead columnDiscriptionLabels">Tariff</p>
                </div>

                <div class="col-2">
                    <p class="lead columnDiscriptionLabels">Price</p>
                </div>

                <div class="col-5">
                    <p class="lead columnDiscriptionLabels" >Description</p>
                </div>

                <div class="col-1"> </div>
            </div>


            <c:forEach items="${activeTariffsList}" var="tariff"  varStatus="status">
                <hr style="margin-top:10px; width:100%;">
                <div class="row">
                        <div class="col-3">
                            <p class="lead columnContentLabels">${tariff.name}</p>
                        </div>

                        <div class="col-2">
                            <p class="lead columnContentLabels">${tariff.price}</p>
                        </div>

                        <div class="col-5">
                            <p class="lead columnContentLabels">${tariff.shortDiscription}</p>
                        </div>

                        <div class="col-1">
                            <div class="form-check" style="clear:both; float:left;">
                                <label class="switch" style="clear:both; margin-top:5px; float:left;">
                                    <c:choose>
                                        <c:when test="${tariff.name eq selectedTariff}">
                                            <input type="checkbox" checked name="tariffCheckbox" group="tariffsGroup" id="${tariff.name}"
                                                   name="tariffCheckbox">
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" name="tariffCheckbox" group="tariffsGroup" id="${tariff.name}"
                                                   name="tariffCheckbox">
                                        </c:otherwise>
                                    </c:choose>
                                    <span class="slider round"></span>
                                </label>
                            </div>
                        </div>

                </div>

            </c:forEach>

        </div>
    </div>
    <div class="col"></div>
</div>

<form:form method="POST" class="form-signin">

<div class="row" style="margin-top:-5px; clear:both;">
    <div class="col"></div>
    <div class="col-5">
        <div class="jumbotron choosenTarifJumbotron" >
            <div class="row">
                <p class="lead"
                   style="font-family: MS Shell Dig 2; font-weight: bolder;  font-size: 25px; float:left;">
                    Available options</p>
            </div>

            <div class="row">
                <div class="col-3">
                    <p class="lead columnDiscriptionLabels">Option</p>
                </div>

                <div class="col-2">
                    <p class="lead columnDiscriptionLabels">Price</p>
                </div>

                <div class="col-5">
                    <p class="lead columnDiscriptionLabels">Description</p>
                </div>

                <div class="col-1">
                </div>

            </div>

<%--            <hr>--%>
            <div class="row" id="enabledOptionsContainer">

                        <c:forEach items="${enabledOptionsDTOMap}" var="entry">
                        <hr style="margin-top:10px; width:100%;">

                        <div class="col-3">
                            <p class="lead columnContentLabels" name="label${entry.key.option_id}">${entry.key.name}</p>
                        </div>

                        <div class="col-2">
                            <p class="lead columnContentLabels" name="label${entry.key.option_id}">${entry.key.price}</p>
                        </div>

                        <div class="col-5">
                            <p class="lead columnContentLabels" name="label${entry.key.option_id}">${entry.key.shortDescription}</p>
                        </div>


                        <div class="col-1">

                                <div class="form-check">
                                    <c:choose>
                                        <c:when test="${entry.value eq false}">
                                            <label class="switch" style="clear:both; " name="group1">
                                                <input type="checkbox" name="optionCheckbox" id="${entry.key.option_id}">
                                                <span class="slider round" id="Slider${entry.key.option_id}"></span>
                                            </label>
                                        </c:when>
                                        <c:otherwise>
                                            <label class="switch" style="clear:both; " name="group1">
                                                <input type="checkbox" checked name="optionCheckbox" id="${entry.key.option_id}">
                                                <span class="slider round" id="Slider${entry.key.option_id}"></span>
                                            </label>
                                        </c:otherwise>
                                    </c:choose>

                                </div>

                            </div>


                        </c:forEach>

            </div>
        </div>
        <div class="col"></div>
    </div>
        <div class="col"></div>
    </div>

    <div class="row" style="margin-bottom:30px">
        <div class="col"></div>



            <c:choose>
                <c:when test="${isBlocked eq false}">
                    <div class="col-4">
                        <h1 class="display-4" id="blockNumber">Block number</h1>
                    </div>

                    <div>
                    <label class="switch" style="clear:both; margin-top:5px;" name="group1">
                        <input type="checkbox" name="blockNumberCheckBox">
                        <span class="slider round" ></span>
                    </label>
                </c:when>
                <c:otherwise>
                    <div class="col-4">
                        <h1 class="display-4" id="blockNumber" style="color:#d3d3d3">Block number</h1>
                    </div>

                    <div>
                    <label class="switch" style="clear:both; margin-top:5px;" name="group1">
                        <input type="checkbox" checked disabled="disabled" name="blockNumberCheckBox">
                        <span class="slider round" style="background-color: #9acffa"></span>
                    </label>
                </c:otherwise>
            </c:choose>

        </div>



        <div class="col"></div>
    </div>

    <div class="row" style="margin-bottom:30px">
        <div class="col"></div>
            <input class="btn btn-lg btn-primary btn-block"
                   type="submit" style="width:20%;"
                   value="Save changes" onclick="onSubmitClick()"></input>
        <div class="col"></div>
    </div>

</form:form>



</body>

<div class="footer" id="footer">
    <p class="lead" style="font-size: 15px; ">golubevcg@gmail.com 2020</p>
</div>


</html>