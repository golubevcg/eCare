<link type="text/css" rel="stylesheet" href="/resources/styles/mainStyles.css">


<sec:authorize access="hasAuthority('ADMIN')">
<div class="fixed-header">
    <div class="jumbotron jumbotron-fluid" id="headerJumbotron">

        <div class="row">
            <div class="col"></div>
            <div class="col-5">

                <a href="/">
                    <img src="/resources/static/logo1.png" class="rounded float-left" alt="..."
                         style="width:80px; float:left;margin-top:-6px; margin-right:15px;">
                </a>

                <div class="dropdown" style="float:left; margin-bottom:5px;">
                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        Tariffs
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
                        Options
                    </button>

                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <a class="dropdown-item" href="#">Option1</a>
                        <a class="dropdown-item" href="#">Option2</a>
                        <a class="dropdown-item" href="#">Option3</a>
                    </div>

                </div>

                <div class="dropdown" style="float:right; ">

                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                        My account
                    </button>

                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                        <a class="dropdown-item" href="/workerOffice">My account</a>
                        <a class="dropdown-item" href="/userRegistration">User registration</a>
                        <a class="dropdown-item" href="/newContract">New contract</a>
                        <a class="dropdown-item" href="/newTariff">New tariff</a>
                        <a class="dropdown-item" href="/newOption">New option</a>

                        <a class="dropdown-item" href="<c:url value="/logout"/>">Logout</a>
                    </div>

                </div>
            </div>
            <div class="col"></div>
        </div>

    </div>
</div>
</sec:authorize>

<sec:authorize access="hasAuthority('USER')">
    <div class="fixed-header">
        <div class="jumbotron jumbotron-fluid" id="headerJumbotron">

            <div class="row">
                <div class="col"></div>
                <div class="col-5">

                    <a href="/">
                        <img src="/resources/static/logo1.png" class="rounded float-left" alt="..."
                             style="width:80px; float:left;margin-top:-6px; margin-right:15px;">
                    </a>

                    <div class="dropdown" style="float:left; margin-bottom:5px;">
                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            Tariffs
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
                            Options
                        </button>

                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" href="#">Option1</a>
                            <a class="dropdown-item" href="#">Option2</a>
                            <a class="dropdown-item" href="#">Option3</a>
                        </div>

                    </div>

                    <div class="dropdown" style="float:right; ">

                        <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            My account
                        </button>


                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item" href="<c:url value="/contracts"/>" >My contracts</a>
                            <a class="dropdown-item" href="<c:url value="/logout"/>" >Logout</a>
                        </div>

                    </div>
                </div>
                <div class="col"></div>
            </div>

        </div>
    </div>
</sec:authorize>






<div style="margin-top:60px;"></div>