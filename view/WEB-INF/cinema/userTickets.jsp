<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="style/styleUserTickets.css">
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
                        <c:if test="${user.role == \"USER\"}">
                            <li><a class="btn btn-outline-light mt-1" href="/suggest">Suggest movie</a></li>
                        </c:if>
                    </c:if>
                </ul>
            </div>
            <ul class="nav navbar-right gap-4">
                <c:if test="${user != null}">
                    <li class="mt-2 text-warning">${user.details.firstNameEN} ${user.details.lastNameEN}</li>
                    <li><a class="btn btn-outline-light" href="/cinema?name=logout">Log Out</a></li>
                </c:if>
            </ul>
        </div>
    </nav>
</header>

<p class="h3 mt-4 mb-4 text-center text-white">My tickets</p>

<c:if test="${tickets == []}">
    <p class="h1 mt-4 mb-4 text-center text-white">You don't have any tickets yet.</p>
</c:if>
<c:forEach var="ticket" items="${tickets}">
    <form class="ticket mt-5 mb-4 mx-auto bg-white p-4">
        <p class="h3 text-center text-black mb-4">Ticket</p>
        <ul>
            <li>
                <p class="h4">${ticket.filmName}</p>
            </li>
            <li>
                <p class="h5">${ticket.genre}</p>
            </li>
            <li>
                <p class="h5">Duration: ${ticket.duration} min</p>
            </li>
            <br>
            <li>
                <p class="h5">${ticket.date} ${ticket.time}</p>
            </li>
            <li>
                <p class="h5">Place: ${ticket.place}</p>
            </li>
            <br>
            <li>
                <p class="h5">Price: $${ticket.price}</p>
            </li>
        </ul>
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
                        <a href="/myTickets?page=${currentPage-1}" style="text-decoration: none">
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
                                <a href="/myTickets?page=${pageNumber}" style="text-decoration: none">
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
                        <a href="/myTickets?page=${currentPage+1}" style="text-decoration: none">
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
        crossorigin="anonymous">
</script>
</body>

</html>