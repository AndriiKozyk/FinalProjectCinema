<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--<%@ page isELIgnored="false" %>--%>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="style/styleTimetable.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>Cinema</title>
</head>

<body>

<header class="header">
    <nav class="navbar navbar-style mt-4">
        <div class="container">
            <div class="navbar-header">
                <img class="logo"
                     src="https://png.pngtree.com/element_our/png_detail/20181022/movie-cinema-entertainment-logo-with-neon-sign-effect-vector-illustration-png_199458.jpg">
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

<div class="sort d-flex justify-content-center">
    <form class="mt-4 mb-4">
        <ul class="nav gap-4">
            <li>
                <button type="button" class="btn btn-outline-light btn-width">Name</button>
            </li>
            <li>
                <button type="button" class="btn btn-outline-light btn-width">Empty places</button>
            </li>
            <li>
                <button type="button" class="btn btn-outline-light btn-width">Date / time</button>
            </li>
            <li>
                <button type="button" class="btn btn-outline-light btn-width">Available</button>
            </li>
            <li>
                <div class="dropdown">
                    <button class="btn btn-outline-light dropdown-toggle btn-width" type="button" id="dropdownGenre"
                            data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Genre
                    </button>
                    <div class="dropdown-menu" aria-labelledby="dropdownGenre">
                        <a class="dropdown-item text-dark btn-width" href="#">Some genre</a>
                        <a class="dropdown-item text-dark btn-width" href="#">Another genre</a>
                    </div>
                </div>
            </li>
        </ul>
    </form>
</div>

<c:forEach var="filmSession" items="${filmSession}">
    <form class="timetable mb-4 mx-auto" style="width: 700px; height: 360px">
        <a href="/placeSelect?name=${filmSession.id}" style="text-decoration: none">
            <ul class="list-group list-group-horizontal">
                <li><img src="data:image/jpg;base64,${filmSession.film.posterOut}"/></li>
                <li>
                    <ul>
                        <br>
                        <c:if test="${filmSession.status.id != 1}">
                            <li><p class="h2 text-danger">${filmSession.status.value}</p></li>
                            <br>
                        </c:if>
                        <li><p class="h2 text-info">${filmSession.film.nameEN}</p></li>
                        <li><p class="h5 text-white">${filmSession.film.genre.genreEN}</p></li>
                        <li><p class="h5 text-white">Duration: ${filmSession.film.duration} min</p></li>
                        <li><p class="h5 text-white">Price: $${filmSession.minPrice} - $${filmSession.maxPrice}</p></li>
                        <li><p class="h5 text-white">Available places: ${filmSession.availablePlaces}</p></li>
                        <br>
                        <li><p class="h5 text-white">${filmSession.date} ${filmSession.time}</p></li>
                    </ul>
                </li>
            </ul>
        </a>
    </form>
</c:forEach>

<%--<form class="timetable mb-4 mx-auto" style="width: 700px; height: 360px">--%>
<%--<ul class="list-group list-group-horizontal">--%>
<%--<li><img class=""--%>
<%--src="https://festagent.com/system/tilda/tild3562-6362-4762-b036-653363663832__a01ccf32943f670ef632.jpg">--%>
<%--</li>--%>
<%--<li>--%>
<%--<ul>--%>
<%--<li><p class="h2 text-info">Film name</p></li>--%>
<%--<li><p class="h5 text-white">Genre</p></li>--%>
<%--<li><p class="h5 text-white">Duration: 90 min</p></li>--%>
<%--<li><p class="h5 text-white">Price: $30 - $60</p></li>--%>
<%--<br>--%>
<%--<li><p class="h5 text-white">05-Feb-21 09:00 AM - 15 places</p></li>--%>
<%--<li><p class="h5 text-white">06-Feb-21 03:00 PM - 27 places</p></li>--%>
<%--<li><p class="h5 text-white">06-Feb-21 06:00 PM - 44 places</p></li>--%>
<%--<li><p class="h5 text-white">06-Feb-21 10:00 PM - 37 places</p></li>--%>
<%--<li><p class="h5 text-white">...</p></li>--%>
<%--<li><p class="h5 text-info">Open to see all dates</p></li>--%>
<%--</ul>--%>
<%--</li>--%>
<%--</ul>--%>
<%--</form>--%>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>

</body>

</html>
