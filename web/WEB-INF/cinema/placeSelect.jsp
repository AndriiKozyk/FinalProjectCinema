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
                <li><a class="nav-link text-center" href="">My account</a></li>
                <li><a class="btn btn-outline-light" href="/registration">Create account</a></li>
                <li><a class="btn btn-outline-light" href="/login">Log In</a></li>
                <li>
                    <div class="dropdown">
                        <button class="btn btn-outline-light dropdown-toggle" type="button" id="dropdownMenuButton"
                                data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Language
                        </button>
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
                <a class="nav-link text-center select" href="">${filmSession.date} ${filmSession.time}</a>
                <span class="badge bg-primary rounded-pill">${filmSession.availablePlaces}</span>
            </li>
            <%--<li class="list-group-item d-flex justify-content-between align-items-center">--%>
                <%--<a class="nav-link text-center select" href="">05-Feb-21 03:00 PM</a>--%>
                <%--<span class="badge bg-primary rounded-pill">30</span>--%>
            <%--</li>--%>
            <%--<li class="list-group-item d-flex justify-content-between align-items-center">--%>
                <%--<a class="nav-link text-center select" href="">07-Feb-21 11:00 AM</a>--%>
                <%--<span class="badge bg-primary rounded-pill">48</span>--%>
            <%--</li>--%>
        </ul>
    </div>
</div>

<form class="hall mt-5 mb-5 mx-auto bg-white text-center">
    <br>
    <p class="h3 text-center text-black mb-4">Screen</p>

    <c:forEach var="filmSession" items="${filmSession.placeList}">
        <c:if test="${filmSession.placeList.available == true}">
            <input type="checkbox" class="btn-check" id="${filmSession.placeList.id}" autocomplete="off">
            <label class="check btn btn-outline-success" for="${filmSession.placeList.id}">${filmSession.placeList.id}</label>
        </c:if>
        <c:if test="${filmSession.placeList.available == false}">
            <input type="checkbox" class="btn-check" id="${filmSession.placeList.id}" autocomplete="off" disabled>
            <label class="check btn btn-outline-success" for="${filmSession.placeList.id}">${filmSession.placeList.id}</label>
        </c:if>
        <c:if test="${filmSession.placeList.id % 10 == 0}">
            <br><br>
        </c:if>
    </c:forEach>


    <%--<input type="checkbox" class="btn-check" id="1" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="1">1</label>--%>






    <%--<input type="checkbox" class="btn-check" id="2" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="2">2</label>--%>
    <%--<input type="checkbox" class="btn-check" id="3" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="3">3</label>--%>
    <%--<input type="checkbox" class="btn-check" id="4" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="4">4</label>--%>
    <%--<input type="checkbox" class="btn-check" id="5" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="5">5</label>--%>
    <%--<input type="checkbox" class="btn-check" id="6" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="6">6</label>--%>
    <%--<input type="checkbox" class="btn-check" id="7" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="7">7</label>--%>
    <%--<input type="checkbox" class="btn-check" id="8" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="8">8</label>--%>
    <%--<input type="checkbox" class="btn-check" id="9" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="9">9</label>--%>
    <%--<input type="checkbox" class="btn-check" id="10" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="10">10</label>--%>

    <%--<br><br>--%>

    <%--<input type="checkbox" class="btn-check" id="11" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="11">11</label>--%>
    <%--<input type="checkbox" class="btn-check" id="12" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="12">12</label>--%>
    <%--<input type="checkbox" class="btn-check" id="13" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="13">13</label>--%>
    <%--<input type="checkbox" class="btn-check" id="14" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="14">14</label>--%>
    <%--<input type="checkbox" class="btn-check" id="15" autocomplete="off">--%>
    <%--<label class="check check btn btn-outline-success" for="15">15</label>--%>
    <%--<input type="checkbox" class="btn-check" id="16" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="16">16</label>--%>
    <%--<input type="checkbox" class="btn-check" id="17" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="17">17</label>--%>
    <%--<input type="checkbox" class="btn-check" id="18" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="18">18</label>--%>
    <%--<input type="checkbox" class="btn-check" id="19" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="19">19</label>--%>
    <%--<input type="checkbox" class="btn-check" id="20" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="20">20</label>--%>

    <%--<br><br>--%>

    <%--<input type="checkbox" class="btn-check" id="21" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="21">21</label>--%>
    <%--<input type="checkbox" class="btn-check" id="22" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="22">22</label>--%>
    <%--<input type="checkbox" class="btn-check" id="23" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="23">23</label>--%>
    <%--<input type="checkbox" class="btn-check" id="24" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="24">24</label>--%>
    <%--<input type="checkbox" class="btn-check" id="25" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="25">25</label>--%>
    <%--<input type="checkbox" class="btn-check" id="26" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="26">26</label>--%>
    <%--<input type="checkbox" class="btn-check" id="27" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="27">27</label>--%>
    <%--<input type="checkbox" class="btn-check" id="28" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="28">28</label>--%>
    <%--<input type="checkbox" class="btn-check" id="29" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="29">29</label>--%>
    <%--<input type="checkbox" class="btn-check" id="30" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="30">30</label>--%>

    <%--<br><br>--%>

    <%--<input type="checkbox" class="btn-check" id="31" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="31">31</label>--%>
    <%--<input type="checkbox" class="btn-check" id="32" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="32">32</label>--%>
    <%--<input type="checkbox" class="btn-check" id="33" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="33">33</label>--%>
    <%--<input type="checkbox" class="btn-check" id="34" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="34">34</label>--%>
    <%--<input type="checkbox" class="btn-check" id="35" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="35">35</label>--%>
    <%--<input type="checkbox" class="btn-check" id="36" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="36">36</label>--%>
    <%--<input type="checkbox" class="btn-check" id="37" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="37">37</label>--%>
    <%--<input type="checkbox" class="btn-check" id="38" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="38">38</label>--%>
    <%--<input type="checkbox" class="btn-check" id="39" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="39">39</label>--%>
    <%--<input type="checkbox" class="btn-check" id="40" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="40">40</label>--%>

    <%--<br><br>--%>

    <%--<input type="checkbox" class="btn-check" id="41" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="41">41</label>--%>
    <%--<input type="checkbox" class="btn-check" id="42" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="42">42</label>--%>
    <%--<input type="checkbox" class="btn-check" id="43" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="43">43</label>--%>
    <%--<input type="checkbox" class="btn-check" id="44" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="44">44</label>--%>
    <%--<input type="checkbox" class="btn-check" id="45" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="45">45</label>--%>
    <%--<input type="checkbox" class="btn-check" id="46" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="46">46</label>--%>
    <%--<input type="checkbox" class="btn-check" id="47" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="47">47</label>--%>
    <%--<input type="checkbox" class="btn-check" id="48" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="48">48</label>--%>
    <%--<input type="checkbox" class="btn-check" id="49" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="49">49</label>--%>
    <%--<input type="checkbox" class="btn-check" id="50" autocomplete="off">--%>
    <%--<label class="check btn btn-outline-success" for="50">50</label>--%>


    <%--<br><br>--%>
    <p class="h6">Total price: 0</p>
    <a class="btn btn-warning mt-3 mb-4" href="">Select chosen</a>
</form>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>
</body>

</html>
