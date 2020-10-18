<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link type="text/css" rel="stylesheet" href="/resources/css/entrancePage.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <!--for dropdown menus scripts-->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.11.0/umd/popper.min.js" integrity="sha384-b/U6ypiBEHpOf/4+1nzFpr53nxSS+GLCkfwBdFNTxtclqqenISfwAzpKaMNFNmj4" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/js/bootstrap.min.js" integrity="sha384-h0AbiXch4ZDo7tp9hKZ4TsHbi047NrKGLO3SEJAg45jXxnGIfYzk4Si90RDIqNm1" crossorigin="anonymous"></script>
    <link type="text/css" rel="stylesheet" href="/resources/css/loginPage.css">
</head>
<body>

<div id="page-container">
    <div id="content-wrap">
        <jsp:directive.include file = "headerTemplate.jsp" />

        <div class="row">

                <div class="col"></div>

                <div class="col" style="font-weight: bold;"  align="center">
                    <h1 style="color:black; text-align: center; margin-top: 30px;">Log in</h1>
                    <div class="border-contrainer">
                        <div style="margin:30px;">
                            <form action="${loginUrl}" method="post">
                                <c:if test="${param.error != null}">
                                    <p style="color:red;">
                                        Invalid username and password.
                                    </p>
                                </c:if>

                                <c:if test="${param.logout != null}">
                                    <p style="color:red;">
                                        You have been logged out.
                                    </p>
                                </c:if>

                                <p>
                                    <label for="username">Username</label>
                                    <input type="text" id="username" class="inputForm" name="username"/>
                                </p>

                                <p>
                                    <label for="password">Password</label>
                                    <input type="password" id="password" class="inputForm"
                                           style="margin-left:5px;" name="password"/>
                                </p>

                                <input type="hidden"
                                       name="${_csrf.parameterName}"
                                       value="${_csrf.token}"/>
                                <button type="submit" class="btn">Log in</button>

                            </form>
                        </div>
                    </div>
                </div>

                <div class="col"></div>

        </div>
    </div>

</div>

</body>

<div>
</div>

<footer>
<jsp:directive.include file = "footerTemplate.jsp" />
</footer>

</html>

