<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="style/styleRegistration.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>Cinema</title>
</head>

<body>

<header class="header">
    <nav class="navbar navbar-style mt-4">
        <div class="container">
            <div class="navbar-header">
                <a href="/cinema"><img class="logo" src="https://png.pngtree.com/element_our/png_detail/20181022/movie-cinema-entertainment-logo-with-neon-sign-effect-vector-illustration-png_199458.jpg"></a>
            </div>
            <ul class="nav navbar-right gap-4">
                <li><a class="btn btn-outline-light btn-width" href="/login">Log In</a></li>
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

<div class="container text-center mt-5 mb-4">
    <form style="width: 400px; margin: auto; padding: 30px;" method="post">
        <h1 class="h3 mb-3 font-weight-normal">Registration</h1>
        <label class="form-label">Login</label>
        <input type="login" class="form-control mb-3" placeholder="Login" name="login">
        <label class="form-label">Password</label>
        <input type="password" placeholder="Password" class="form-control mb-3" name="password">
        <input type="password" placeholder="Repeat password" class="form-control mb-3" name="repeatPassword">
        <label class="form-label">Email</label>
        <input type="email" placeholder="Email" class="form-control mb-3" name="email">
        <label class="form-label">First name and last name</label>
        <ul class="list-group list-group-horizontal mb-3">
            <li><input type="text" placeholder="First name" class="form-control first-name" name="firstNameEN"></li>
            <li><input type="text" placeholder="Last name" class="form-control last-name" name="lastNameEN"></li>
        </ul>
        <label class="form-label">Ім'я та прізвище</label>
        <ul class="list-group list-group-horizontal mb-3">
            <li><input type="text" placeholder="Ім'я" class="first-name form-control" name="firstNameUA"></li>
            <li><input type="text" placeholder="Прізвище" class="form-control last-name" name="lastNameUA"></li>
        </ul>
        <label class="form-label">Phone number</label>
        <input type="text" placeholder="Phone number" class="form-control mb-3" name="phone">
        <div class="mt-4">
            <input type="submit" class="btn btn-sm btn-dark" value="Create account" style="width: 270px;">
        </div>
    </form>
</div>



<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
</body>

</html>
