<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="style/stylePlaceSelect.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>Cinema</title>
</head>

<body>

<header class="header">
    <nav class="navbar navbar-style mt-4">
        <div class="container">
            <div class="navbar-header">
                <a href="/cinema"><img class="logo"
                                       src="https://png.pngtree.com/element_our/png_detail/20181022/movie-cinema-entertainment-logo-with-neon-sign-effect-vector-illustration-png_199458.jpg"></a>
            </div>
            <ul class="nav navbar-right gap-4">
                <c:if test="${user != null}">
                    <li><a class="nav-link text-center" href="/myTickets">My tickets</a></li>
                    <%--<li><p class="text-center text-light">${user.details.firstNameEN} ${user.details.lastNameEN}</p></li>--%>
                    <li><a class="btn btn-outline-light" href="/cinema?name=logout">Log Out</a></li>
                </c:if>
                <c:if test="${user == null}">
                    <li><a class="btn btn-outline-light" href="/registration">Create account</a></li>
                    <li><a class="btn btn-outline-light" href="/login">Log In</a></li>
                </c:if>
                <li>
                    <div class="dropdown">
                        <button class="btn btn-outline-light dropdown-toggle" type="button" id="dropdownMenuButton"
                                data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Language
                        </button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item text-dark" href="">English</a>
                            <a class="dropdown-item text-dark" href="">Українська</a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</header>

<form class="timetable mt-4 mx-auto" style="width: 500px; height: 250px">
    <ul class="list-group list-group-horizontal">
        <li><img src="${filmSession.film.poster}">
        </li>
        <li>
            <br>
            <ul>
                <li>
                    <p class="h2 text-info">${filmSession.film.nameEN}</p>
                </li>
                <li>
                    <p class="h5 text-white">${filmSession.film.genre.genreEN}</p>
                </li>
                <li>
                    <p class="h5 text-white">Duration: ${filmSession.film.duration} min</p>
                </li>
                <li>
                    <p class="h5 text-white">Price: $${filmSession.minPrice} - $${filmSession.maxPrice}</p>
                </li>
                <br>
            </ul>
        </li>

    </ul>


</form>

<div class="session-select mx-auto" style="width: 500px">
    <p class="h3 mt-4 text-center text-white">Session</p>
    <div class="mt-4">
        <ul class="list-group">
            <li class="list-group-item d-flex justify-content-between align-items-center">
                <a class="nav-link text-center select">${filmSession.date} ${filmSession.time}</a>
                <span class="badge bg-primary rounded-pill">${filmSession.availablePlaces}</span>
            </li>
        </ul>
    </div>
</div>

<form class="hall mt-5 mb-5 mx-auto bg-white text-center" method="post">
    <br>
    <p class="h3 text-center text-black mb-4">Screen</p>

    <c:forEach var="place" items="${filmSession.placeList}">
        <c:if test="${place.available == true}">
            <input type="checkbox" class="btn-check" name="place" value="${place.place.id}" id="${place.place.id}">
            <label class="check btn btn-outline-success" for="${place.place.id}">${place.place.id}</label>
        </c:if>
        <c:if test="${place.available == false}">
            <input type="checkbox" class="btn-check" name="place" value="${place.place.id}" id="${place.place.id}"
                   disabled>
            <label class="check btn btn-outline-success" for="${place.place.id}">${place.place.id}</label>
        </c:if>
        <c:if test="${place.place.id % 10 == 0}">
            <br><br>
        </c:if>
    </c:forEach>

    <p class="h6">Total price: 0</p>
    <c:if test="${user == null}">
        <input type="submit" class="btn btn-warning mt-3 mb-4" href="/confirm" value="Select chosen" disabled/>
    </c:if>
    <c:if test="${user != null}">
        <input type="submit" class="btn btn-warning mt-3 mb-4" href="/confirm" value="Select chosen"/>
    </c:if>
</form>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>
</body>

</html>
