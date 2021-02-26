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
                    <%--<li><p class="text-center text-light">${user.details.firstNameEN} ${user.details.lastNameEN}</p></li>--%>
                    <c:if test="${user.role.id == 1}">
                        <li><a class="btn btn-outline-light" href="/addMovie">Add new film</a></li>
                    </c:if>
                    <c:if test="${user.role.id == 2}">
                        <li><a class="nav-link text-center" href="/myTickets">My tickets</a></li>
                    </c:if>
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

<c:forEach var="film" items="${films}">
    <form class="timetable mb-4 mx-auto" style="width: 700px; height: 360px">
        <a href="/${link}?name=${film.id}" style="text-decoration: none">
            <ul class="list-group list-group-horizontal">
                <li><img src="data:image/jpg;base64,${film.posterOut}"/></li>
                <li>
                    <ul>
                        <li><p class="h2 text-info">${film.nameEN}</p></li>
                        <li><p class="h5 text-white">${film.genre.genreEN}</p></li>
                        <li><p class="h5 text-white">Duration: ${film.duration} min</p></li>
                        <c:if test="${filmMap[film.id][0] != null}">
                            <li><p class="h5 text-white">Price: $${filmMap[film.id][0].minPrice} -
                                $${filmMap[film.id][0].maxPrice}</p>
                            </li>
                        </c:if>
                        <br>
                        <c:forEach var="session" items="${filmMap[film.id]}">
                            <li>
                                <ul class="list-group list-group-horizontal">
                                    <li>
                                        <p class="h5 text-white">${session.date} ${session.time} -
                                            <c:if test="${session.status.id == 1}">
                                                ${session.availablePlaces} places
                                            </c:if>
                                        </p>
                                    </li>
                                    <li>
                                        <p class="h5 text-danger">
                                            <c:if test="${session.status.id != 1}">
                                                &nbsp;${session.status.value}
                                            </c:if>
                                        </p></li>
                                </ul>
                            </li>
                        </c:forEach>
                        <br>
                        <c:if test="${film.additionalSession != 0}">
                            <li><p class="h5 text-info">Open to see all dates (+${film.additionalSession})</p></li>
                        </c:if>
                    </ul>
                </li>
            </ul>
        </a>
    </form>
</c:forEach>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>

</body>

</html>
