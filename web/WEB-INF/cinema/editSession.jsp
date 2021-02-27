<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="style/styleAddSession.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>Cinema</title>
</head>

<body>

<header class="header mb-4">
    <nav class="navbar navbar-style mt-4">
        <div class="container">
            <div class="navbar-header">
                <a href="/cinema"><img class="logo"
                                               src="https://png.pngtree.com/element_our/png_detail/20181022/movie-cinema-entertainment-logo-with-neon-sign-effect-vector-illustration-png_199458.jpg"></a>
            </div>
            <ul class="nav navbar-right gap-4">
                <ul class="nav navbar-right gap-4">
                    <li class="mt-2 text-warning">${user.details.firstNameEN} ${user.details.lastNameEN}</li>
                    <li class="mt-2 text-warning">ADMIN</li>
                    <li><a class="btn btn-outline-light" href="/addMovie">Add new film</a></li>
                    <li><a class="btn btn-outline-light" href="/cinema?name=logout">Log Out</a></li>
                </ul>
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


<ul class="list-group list-group-horizontal mb-3 d-flex justify-content-center gap-4">

    <li>
        <form class="timetable mt-4 mx-auto" style="width: 500px; height: 250px">
            <ul class="list-group list-group-horizontal">
                <li><img src="data:image/jpg;base64,${film.posterOut}"/></li>
                <li>
                    <ul>
                        <li>
                            <p class="h2 text-info">${film.nameEN}</p>
                        </li>
                        <li>
                            <p class="h5 text-white">${film.genre.genreEN}</p>
                        </li>
                        <li>
                            <p class="h5 text-white">Duration: ${film.duration} min</p>
                        </li>
                        <c:if test="${filmSessions[0] != null}">
                            <li>
                                <p class="h5 text-white">Price: $${filmSessions[0].minPrice} -
                                    $${filmSessions[0].maxPrice}</p>
                            </li>
                        </c:if>
                    </ul>
                </li>

            </ul>

        </form>
    </li>
    <li>
        <form class="mb-4 mt-4" style="width: 350px; height: 250px; margin: auto; padding: 30px;" method="post">
            <div class="container text-center">
                <h1 class="h3 mb-3 font-weight-normal">Add new session</h1>
                <input type="date" name="date" class="start form-control" required>
                <input type="time" name="time" class="finish form-control" required>
                <div>
                    <input type="submit" class="btn btn-sm btn-dark mt-4" value="Add session" style="width: 270px;">
                </div>
            </div>
        </form>
    </li>
</ul>

<form class="timetable mt-4 mb-4 mx-auto pb-2 pt-3" style="width: 500px" method="post">
    <div class="session-select mx-auto" style="width: 500px">
        <p class="h3 text-center text-white">Sessions</p>
        <c:if test="${filmSessions == []}">
            <p class="h5 text-center text-white">Film don't have sessions yet</p>
        </c:if>
        <c:if test="${filmSessions != []}">
            <div class="mt-4 mb-4 text-center">
                <c:forEach var="session" items="${filmSessions}">
                    <ul class="list-group">
                        <li class="d-flex justify-content-center">
                            <c:if test="${session.status.id == 1}">
                            <input type="checkbox" class="btn-check" name="sessionForDelete" value="${session.id}"
                                   id="${session.id}" autocomplete="off">
                            </c:if>
                            <c:if test="${session.status.id != 1}">
                                <input type="checkbox" class="btn-check" name="sessionForDelete" value="${session.id}"
                                       id="${session.id}" autocomplete="off" disabled>
                            </c:if>
                            <label class="btn btn-outline-light check d-flex justify-content-between"
                                   for="${session.id}" style="width: 400px;">${session.date} ${session.time}
                                <span class="badge bg-primary rounded-pill">${session.availablePlaces}</span>
                                <c:if test="${session.status.id != 1}">
                                    <p class="h6 text-danger">${session.status.value}</p>
                                </c:if>
                            </label>
                        </li>
                    </ul>
                </c:forEach>
                <input type="submit" name="cancel" class="btn btn-danger mt-4" value="Cancel all sessions"
                       style="width: 250px"/>
                <input type="submit" name="cancel" class="btn btn-danger mt-2" value="Cancel selected"
                       style="width: 250px"/>
            </div>
        </c:if>
    </div>
</form>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>
</body>

</html>
