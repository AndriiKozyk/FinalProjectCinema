<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="style/styleFilmToOrderDetails.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>Cinema</title>
</head>

<body>

<header class="header">
    <nav class="navbar navbar-style mt-4">
        <div class="container">
            <div class="navbar-header">
                <ul class="nav gap-4">
                    <li>
                        <a href="/cinema">
                            <img class="logo"
                                 src="https://png.pngtree.com/element_our/png_detail/20181022/movie-cinema-entertainment-logo-with-neon-sign-effect-vector-illustration-png_199458.jpg">
                        </a>
                    </li>
                    <li>
                        <a class="btn btn-outline-light mt-1" href="/suggestionsList?name=voting">Movies to order</a>
                    </li>
                    <c:if test="${user != null}">
                        <c:if test="${user.role.id == 2}">
                            <li><a class="btn btn-outline-light mt-1" href="/suggest">Suggest movie</a></li>
                        </c:if>
                    </c:if>
                </ul>
            </div>
            <ul class="nav navbar-right gap-4">
                <c:if test="${user != null}">
                    <li class="mt-2 text-warning">${user.details.firstNameEN} ${user.details.lastNameEN}</li>
                    <c:if test="${user.role.id == 2}">
                        <li><a class="btn btn-outline-light" href="/myTickets">My tickets</a></li>
                    </c:if>
                    <li><a class="btn btn-outline-light" href="/cinema?name=logout">Log Out</a></li>
                </c:if>
                <c:if test="${user == null}">
                    <li><a class="btn btn-outline-light" href="/registration">Create account</a></li>
                    <li><a class="btn btn-outline-light" href="/login">Log In</a></li>
                </c:if>
            </ul>
        </div>
    </nav>
</header>

<form class="timetable mt-4 mx-auto" style="width: 500px; height: 250px" method="post">
    <ul class="list-group list-group-horizontal">
        <li><img
                src="data:image/jpg;base64,${film.posterOut}">
        </li>
        <li>
            <ul>
                <input name="id" value="${film.id}" type="hidden">
                <li>
                    <p class="h2 text-info">${film.nameEN}</p>
                </li>
                <br>
                <li>
                    <p class="h5 text-info">Votes: ${film.vote} / ${film.requiredVote}</p>
                </li>
                <br>
                <c:if test="${userVote == true}">
                    <li><input type="submit" class="btn btn-outline-info btn-width" value="Vote for" disabled></li>
                </c:if>
                <c:if test="${userVote == false}">
                    <li><input type="submit" class="btn btn-outline-info btn-width" value="Vote for"></li>
                </c:if>
            </ul>
        </li>
    </ul>

</form>


<form class="mt-4 mx-auto" style="width: 475px; height: 275px">

    <p class="h2 mt-4 mb-4 text-light text-center">Trailer</p>
    <div class="container">
        <div class="row justify-content-center align-items-center">
            <iframe width="475" height="275" src="${film.trailer}" allowfullscreen>
            </iframe>
        </div>
    </div>
</form>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>
</body>
</html>
