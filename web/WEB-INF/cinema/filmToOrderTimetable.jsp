<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="style/styleFilmToOrderTimetable.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">

    <title>Cinema</title>
</head>

<body>

<header class="header">
    <nav class="navbar navbar-style mt-4 mb-4">
        <div class="container">
            <div class="navbar-header">
                <ul class="nav gap-4">
                    <li>
                        <a href="/cinema">
                            <img class="logo"
                                 src="https://png.pngtree.com/element_our/png_detail/20181022/movie-cinema-entertainment-logo-with-neon-sign-effect-vector-illustration-png_199458.jpg">
                        </a>
                    </li>
                    <c:if test="${user != null}">
                        <c:if test="${user.role.id == 2}">
                            <li><a class="btn btn-outline-light mt-1" href="/suggest">Suggest movie</a></li>
                        </c:if>
                        <c:if test="${user.role.id == 1}">
                            <li>
                                <a href="/suggestionsList?name=suggestions"
                                   class="btn btn-outline-light mt-1">Suggestions <span
                                        class="badge bg-primary rounded-pill">${userSuggestions}</span></a>
                            </li>
                            <li>
                                <a href="/suggestionsList?name=movies"
                                   class="btn btn-outline-light mt-1">Movies <span
                                        class="badge bg-primary rounded-pill">${votedFilms}</span></a>
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

<c:if test="${user.role.id == 2}">
    <div class="sort d-flex justify-content-center">
        <form class="mb-4">
            <ul class="nav gap-4">
                <c:if test="${sortBy != \"voting\"}">
                    <li><a class="btn btn-outline-light" href="/suggestionsList?name=voting">Voting</a></li>
                </c:if>
                <c:if test="${sortBy != \"userVotes\"}">
                    <li><a class="btn btn-outline-light" href="/suggestionsList?name=userVotes">My votes</a></li>
                </c:if>
                <c:if test="${sortBy != \"userSuggestions\"}">
                    <li>
                        <a class="btn btn-outline-light" href="/suggestionsList?name=userSuggestions">My suggestions</a>
                    </li>
                </c:if>
            </ul>
        </form>
    </div>
</c:if>

<c:if test="${isEmpty == true}">
    <p class="h1 mt-4 mb-4 text-center text-white">No available films.</p>
</c:if>

<c:choose>
    <c:when test="${shortForm == true}">
        <c:forEach var="film" items="${filmsMap}">

            <form class="timetable mb-4" style="width: 400px; margin: auto; padding: 30px;">

                <c:choose>
                    <c:when test="${user != null && user.role.id == 1}">
                        <a href="/processing?name=${film.key.id}" style="text-decoration: none">
                            <p class="h4 text-info">${film.key.nameEN}</p>
                            <c:if test="${film.key.year != 0}">
                                <p class="h4 text-info">Year: ${film.key.year}</p>
                            </c:if>
                            <c:if test="${film.key.description != \"\"}">
                                <p class="h4 text-info mb-3">${film.key.description}</p>
                            </c:if>
                        </a>
                    </c:when>

                    <c:otherwise>
                        <p class="h4 text-info">${film.key.nameEN}</p>
                        <c:if test="${film.key.year != 0}">
                            <p class="h4 text-info">Year: ${film.key.year}</p>
                        </c:if>
                        <c:if test="${film.key.description != \"\"}">
                            <p class="h4 text-info mb-3">${film.key.description}</p>
                        </c:if>

                        <c:choose>
                            <c:when test="${film.value == \"rejected\"}">
                                <p class="h4 text-danger">Status: ${film.value}</p>
                            </c:when>
                            <c:when test="${film.value == \"approved\"}">
                                <p class="h4 text-light">Status: ${film.value}</p>
                            </c:when>
                            <c:when test="${film.value == \"voting\"}">
                                <p class="h4 text-warning">Status: ${film.value}</p>
                            </c:when>
                            <c:otherwise>
                                <p class="h4 text-info">Status: ${film.value}</p>
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>


            </form>
        </c:forEach>
    </c:when>


    <c:otherwise>

        <c:choose>
            <c:when test="${user != null && user.role.id == 1}">
                <c:forEach var="film" items="${filmsMap}">
                    <form class="timetable mb-4 mx-auto" style="width: 550px; height: 300px" method="post">
                        <a href="/toRolling?name=${film.key.id}" style="text-decoration: none">
                            <ul class="list-group list-group-horizontal">
                                <li><img src="data:image/jpg;base64,${film.key.posterOut}"></li>
                                <li>
                                    <ul>
                                        <input name="id" value="${film.key.id}" type="hidden">
                                        <br>
                                        <li><p class="h2 text-info">${film.key.nameEN}</p></li>
                                        <br>
                                        <li><p class="h5 text-info">Votes: ${film.key.vote}
                                            / ${film.key.requiredVote}</p></li>
                                    </ul>
                                </li>
                            </ul>
                        </a>
                    </form>
                </c:forEach>
            </c:when>

            <c:otherwise>
                <c:forEach var="film" items="${filmsMap}">
                    <form class="timetable mb-4 mx-auto" style="width: 550px; height: 300px" method="post">

                        <ul class="list-group list-group-horizontal">
                            <li>
                                <a href="/orderFilmDetails?name=${film.key.id}" style="text-decoration: none">
                                    <img src="data:image/jpg;base64,${film.key.posterOut}">
                                </a>
                            </li>
                            <li>
                                <ul>
                                    <input name="id" value="${film.key.id}" type="hidden">
                                    <br>
                                    <li><p class="h2 text-info">${film.key.nameEN}</p></li>
                                    <br>
                                    <li><p class="h5 text-info">Votes: ${film.key.vote} / ${film.key.requiredVote}</p>
                                    </li>
                                    <br>

                                    <li>
                                        <c:if test="${film.value == true}">
                                            <input type="submit" class="btn btn-outline-info btn-width" value="Vote for"
                                                   disabled>
                                        </c:if>
                                        <c:if test="${film.value == false}">
                                            <input type="submit" class="btn btn-outline-info btn-width"
                                                   value="Vote for">
                                        </c:if>

                                    </li>
                                    <br>
                                    <li>
                                        <a href="/orderFilmDetails?name=${film.key.id}" style="text-decoration: none">
                                            <p class="h5 text-white">Open to watch trailer</p>
                                        </a>
                                    </li>

                                </ul>
                            </li>
                        </ul>
                    </form>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </c:otherwise>

</c:choose>


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
                        <a href="/suggestionsList?page=${currentPage-1}" style="text-decoration: none">
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
                                <a href="/suggestionsList?page=${pageNumber}" style="text-decoration: none">
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
                        <a href="/suggestionsList?page=${currentPage+1}" style="text-decoration: none">
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