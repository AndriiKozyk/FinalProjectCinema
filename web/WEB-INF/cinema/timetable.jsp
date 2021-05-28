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
                        <c:if test="${user.role.id == 1}">
                            <li>
                                <a href="/suggestionsList?name=suggestions"
                                   class="btn btn-outline-light mt-1">Suggestions <span class="badge bg-primary rounded-pill">${userSuggestions}</span></a>
                            </li>
                            <li>
                                <a href="/suggestionsList?name=movies"
                                   class="btn btn-outline-light mt-1">Movies <span class="badge bg-primary rounded-pill">${votedFilms}</span></a>
                            </li>
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
                    <c:if test="${user.role.id == 1}">
                        <li class="mt-2 text-warning">ADMIN</li>
                        <li><a class="btn btn-outline-light" href="/addMovie">Add new film</a></li>
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

<div class="sort d-flex justify-content-center">
    <ul class="nav gap-4">
        <li>
            <form class="mt-4 mb-4 timetable" style="width: 700px; height: 125px" method="post">
                <p class="h3 text-center text-light mb-2">Sort by:</p>
                <ul class="nav gap-4 d-flex justify-content-center">
                    <li>
                        <input type="submit" class="btn btn-outline-light btn-width" value="Name" name="sortBy">
                    </li>
                    <li>
                        <input type="submit" class="btn btn-outline-light btn-width" value="Empty places" name="sortBy">
                    </li>
                    <li>
                        <input type="submit" class="btn btn-outline-light btn-width" value="Date / Time" name="sortBy">
                    </li>
                </ul>
                <p class="text-light mt-2 text-center">Sorted: ${sortBy} ${sortOrder}</p>
            </form>
        </li>
        <li>
            <form class="mt-4 mb-4 timetable" style="width: 475px; height: 125px" method="post">
                <p class="h3 text-center text-light mb-2">Show only:</p>
                <ul class="nav gap-4 d-flex justify-content-center">
                    <li>
                        <input type="submit" class="btn btn-outline-light btn-width"
                               value="Available" name="showOnlyAvailable">
                        <p class="text-light mt-2 text-center">Available: ${showOnlyAvailable}</p>
                    </li>
                    <li>
                        <div class="dropdown">
                            <button class="btn btn-outline-light dropdown-toggle btn-width" type="button"
                                    id="dropdownGenre"
                                    data-bs-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Genre
                            </button>
                            <div class="dropdown-menu" aria-labelledby="dropdownGenre">
                                <input type="submit" class="btn dropdown-item text-dark btn-width"
                                       value="All" name="showOnlyGenre">
                                <c:forEach var="genre" items="${genres}">
                                    <input type="submit" class="btn dropdown-item text-dark btn-width"
                                           value="${genre.genreEN}" name="showOnlyGenre">
                                </c:forEach>
                            </div>
                        </div>
                        <p class="text-light mt-2 text-center">Genre: ${showOnlyGenre}</p>
                    </li>
                </ul>
            </form>
        </li>
    </ul>
</div>

<c:if test="${films == []}">
    <p class="h1 mt-4 mb-4 text-center text-white">No available films.</p>
</c:if>
<c:forEach var="film" items="${films}">
    <form class="timetable mb-4 mx-auto" style="width: 700px; height: 360px">
        <a href="/${link}?name=${film.id}" style="text-decoration: none">
            <ul class="list-group list-group-horizontal">
                <li>
                    <img src="data:image/jpg;base64,${film.posterOut}"/>
                </li>
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

<c:if test="${lastPage > 1}">
    <nav>
        <div class="d-flex justify-content-center mt-5 mb-4">
            <ul class="pagination">

                <c:if test="${currentPage == firstPage}">
                    <li class="page-item disabled">
                        <span class="page-link">Previous</span>
                    </li>
                </c:if>
                <c:if test="${currentPage != firstPage}">
                    <li class="page-item">
                        <a href="/cinema?page=${currentPage-1}" style="text-decoration: none">
                            <span class="page-link">Previous</span>
                        </a>
                    </li>
                </c:if>

                <c:forEach var="pageNumber" items="${pages}">

                    <c:choose>

                        <c:when test="${pageNumber.equals(currentPage)}">
                            <li class="page-item disabled">
                                <span class="page-link">${pageNumber}</span>
                            </li>
                        </c:when>

                        <c:otherwise>
                            <li class="page-item">
                                <a href="/cinema?page=${pageNumber}" style="text-decoration: none">
                                    <span class="page-link">${pageNumber}</span>
                                </a>
                            </li>
                        </c:otherwise>

                    </c:choose>
                </c:forEach>

                <c:if test="${currentPage == lastPage}">
                    <li class="page-item disabled">
                        <span class="page-link">Next</span>
                    </li>
                </c:if>
                <c:if test="${currentPage != lastPage}">
                    <li class="page-item">
                        <a href="/cinema?page=${currentPage+1}" style="text-decoration: none">
                            <span class="page-link">Next</span>
                        </a>
                    </li>
                </c:if>

            </ul>
        </div>
    </nav>
</c:if>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW"
        crossorigin="anonymous"></script>

</body>

</html>
