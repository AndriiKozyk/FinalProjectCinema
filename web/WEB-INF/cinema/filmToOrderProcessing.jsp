<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="style/styleFilmToOrderProcessing.css">
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

<div class="container text-center mt-5">
    <form style="width: 400px; margin: auto; padding: 30px;" method="post">
        <h1 class="h3 mb-3 font-weight-normal">User suggestion</h1>
        <p class="h5 text-primary">${film.nameEN}</p>
        <c:if test="${film.year != 0}">
            <p class="h5 text-primary">${film.year}</p>
        </c:if>
        <c:if test="${film.description != \"\"}">
            <p class="h5 text-primary mb-3">${film.description}</p>
        </c:if>
        <input name="id" value="${film.id}" type="hidden">
        <div>
            <input type="submit" name="action" class="btn mt-3 btn-sm btn-dark" value="Reject movie" style="width: 270px;">
        </div>
    </form>
</div>

<div class="container text-center mt-4 mb-4">
    <form style="width: 400px; margin: auto; padding: 30px;" enctype="multipart/form-data" method="post">
        <h1 class="h3 mb-3 font-weight-normal">Movie to order</h1>
        <input name="id" value="${film.id}" type="hidden">
        <input name="nameEN" type="text" class="start form-control" placeholder="Movie name" required>
        <input name="nameUA" type="text" class="in form-control" placeholder="Назва фільму" required>
        <input name="trailer" type="text" class="in form-control" placeholder="Link to trailer" required>
        <input name="votes" type="number" placeholder="Required votes" class="finish form-control mb-2" required>
        <label class="form-label">Poster</label>
        <input name="poster" type="file" class="form-control mb-3" required>
        <div>
            <input type="submit" name="action" class="btn btn-sm btn-dark" value="Accept movie" style="width: 270px;">
        </div>
    </form>
</div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>
</body>

</html>
