<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    <link rel="stylesheet" href="styleFilmToOrderToRolling.css">

    <title>Cinema</title>
</head>

<body>

<header class="header">
    <nav class="navbar navbar-style mt-4">
        <div class="container">
            <div class="navbar-header">
                <a href=""><img class="logo" src="https://png.pngtree.com/element_our/png_detail/20181022/movie-cinema-entertainment-logo-with-neon-sign-effect-vector-illustration-png_199458.jpg"></a>
            </div>
            <ul class="nav navbar-right gap-4">
                <li><a class="nav-link text-center" href="">My account</a></li>
                <li><a class="btn btn-outline-light" href="">Create account</a></li>
                <li><a class="btn btn-outline-light" href="">Log In</a></li>
                <li>
                    <div class="dropdown">
                        <button class="btn btn-outline-light dropdown-toggle" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Language</button>
                        <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <a class="dropdown-item text-dark" href="#">English</a>
                            <a class="dropdown-item text-dark" href="#">Українська</a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </nav>
</header>


<form class="timetable mt-4 mx-auto" style="width: 500px; height: 250px">
    <ul class="list-group list-group-horizontal">
        <li><img src="https://festagent.com/system/tilda/tild3562-6362-4762-b036-653363663832__a01ccf32943f670ef632.jpg"></li>
        <li>
            <ul>
                <br>
                <li>
                    <p class="h2 text-info">Film name</p>
                </li>
                <br>
                <li>
                    <p class="h5 text-info">Votes: 0 / 50</p>
                </li>
            </ul>
        </li>
    </ul>

</form>

<div class="container text-center mt-4 mb-4">
    <form style="width: 400px; margin: auto; padding: 30px;">
        <h1 class="h3 mb-3 font-weight-normal">Fill all fields</h1>
        <div class="dropdown mb-2">
            <button class="btn btn-outline-secondary dropdown-toggle btn-sm" type="button" id="dropdownMenuButton" data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style="width: 150px">Genre</button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item text-dark" href="#">Some genre</a>
            </div>
        </div>
        <label class="form-label">You can create genre below, if it do not exist.</label>
        <input type="number" placeholder="Duration (min)" class="start form-control">
        <input type="number" placeholder="Price" class="mb-3 finish form-control mb-2">

        <div>
            <input type="submit" class="btn btn-sm btn-dark" value="Add movie" style="width: 270px;">
        </div>
    </form>
</div>

<div class="container text-center mb-4">
    <form style="width: 400px; margin: auto; padding: 30px;">
        <h1 class="h3 mb-3 font-weight-normal">Add new genre</h1>
        <input type="text" class="start form-control" placeholder="Genre">
        <input type="text" class="finish form-control mb-3" placeholder="Жанр">
        <div>
            <input type="submit" class="btn btn-sm btn-dark" value="Add genre" style="width: 270px;">
        </div>
    </form>
</div>








<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body></html>
