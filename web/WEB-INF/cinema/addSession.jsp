<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="style/styleAddSession.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>Cinema</title>
</head>

<body>

<header class="header mb-4">
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


<ul class="list-group list-group-horizontal mb-3 d-flex justify-content-center gap-4">

    <li>
        <form class="timetable mt-4 mx-auto" style="width: 500px; height: 250px">
            <ul class="list-group list-group-horizontal">
                <li><img class="" src="https://festagent.com/system/tilda/tild3562-6362-4762-b036-653363663832__a01ccf32943f670ef632.jpg"></li>
                <li>
                    <ul>
                        <li>
                            <p class="h2 text-info">Film name</p>
                        </li>
                        <li>
                            <p class="h5 text-white">Genre</p>
                        </li>
                        <li>
                            <p class="h5 text-white">Duration: 90 min</p>
                        </li>
                        <li>
                            <p class="h5 text-white">Price: $30 - $60</p>
                        </li>
                        <li>
                            <a class="btn btn-danger mt-2" href="" style="width: 250px">Cancel all sessions</a>
                        </li>
                        <li>
                            <a class="btn btn-danger mt-2" href="" style="width: 250px">Cancel selected</a>
                        </li>
                    </ul>
                </li>

            </ul>

        </form>
    </li>
    <li>
        <form class="mb-4 mt-4" style="width: 350px; height: 250px; margin: auto; padding: 30px;">
            <div class="container text-center">
                <h1 class="h3 mb-3 font-weight-normal">Add new session</h1>
                <input type="date" class="start form-control">
                <input type="time" class="finish form-control">
                <div>
                    <input type="submit" class="btn btn-sm btn-dark mt-4" value="Add session" style="width: 270px;">
                </div>
            </div>
        </form>
    </li>
</ul>
<form class="timetable mt-4 mb-4 mx-auto pb-2 pt-3" style="width: 500px">
    <div class="session-select mx-auto" style="width: 500px">
        <p class="h3 text-center text-white">Sessions</p>
        <div class="mt-4">
            <ul class="list-group">
                <li class="d-flex justify-content-center">
                    <input type="checkbox" class="btn-check" id="1" autocomplete="off">
                    <label class="btn btn-outline-light check d-flex justify-content-between" for="1">
                        05-Feb-21 09:00 AM
                        <span class="badge bg-primary rounded-pill">14</span>
                    </label>
                </li>
                <li class="d-flex justify-content-center">
                    <input type="checkbox" class="btn-check" id="2" autocomplete="off">
                    <label class="btn btn-outline-light check d-flex justify-content-between" for="2">
                        05-Feb-21 03:00 PM
                        <span class="badge bg-primary rounded-pill">30</span>
                    </label>
                </li>
                <li class="d-flex justify-content-center mb-4">
                    <input type="checkbox" class="btn-check" id="3" autocomplete="off">
                    <label class="btn btn-outline-light check d-flex justify-content-between" for="3">
                        07-Feb-21 11:00 AM
                        <span class="badge bg-primary rounded-pill text-light">48</span>
                    </label>
                </li>
            </ul>
        </div>
    </div>
</form>





<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>

</html>
