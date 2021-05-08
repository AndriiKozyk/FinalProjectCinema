package com.cinema.controller.servlet;

import com.cinema.comparator.FilmSessionAvailableDateComparator;
import com.cinema.comparator.FilmSessionAvailablePlaceComparator;
import com.cinema.comparator.FilmSessionDateComparator;
import com.cinema.comparator.FilmSessionPlaceComparator;
import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.dao.GenreDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.film.Genre;
import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.ticket.Ticket;
import com.cinema.model.entity.user.Role;
import com.cinema.model.entity.user.User;

import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

public class Timetable extends HttpServlet {

    private static final int SESSION_LIMIT = 4;
    private static final int FILMS_LIMIT = 5;
    private int currentPage = 1;
    private static final int CONST_ONE = 1;
    private final FilmDAO filmDao = new FilmDAO();
    private final FilmSessionDAO filmSessionDAO = new FilmSessionDAO();
    private static String sortOrder = "ascending";
    private static final String ASC = "ascending";
    private static final String DESC = "descending";
    private static String sortBy = "Date / Time";
    private static String showOnlyAvailable = "Only available";
    private static String showOnlyGenre = "All";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String link = "placeSelect";

        if ("logout".equals(req.getParameter("name"))) {
            String active = null;
            if (req.getSession(false) != null) {
                req.getSession(false).invalidate();
            }
            req.getServletContext().setAttribute("active", active);
        }

        User user = null;
        if (req.getSession(false) != null) {
            user = (User) req.getSession(false).getAttribute("user");
            req.setAttribute("user", user);
            if (user != null) {
                if (Role.ADMIN.equals(user.getRole())) {
                    link = "editSession";
                }
            }
        }

        req.setAttribute("link", link);

        List<Film> films;

        if (showOnlyGenre.equals("All")) {
            films = filmDao.selectFilms();
        } else {
            films = filmDao.selectFilms(showOnlyGenre);
        }
        Map<Integer, List<FilmSession>> filmMap = new LinkedHashMap<>();
        List<Film> emptyFilms = new ArrayList<>();

        List<FilmSession> filmSessions;

        for (Film film : films) {
            if (showOnlyAvailable.equals("Only available")) {
                filmSessions = filmSessionDAO.selectAvailableFilmSessions(film.getId(), SESSION_LIMIT);
            } else {
                filmSessions = filmSessionDAO.selectFilmSessions(film.getId(), SESSION_LIMIT);
            }
            int amountOfSessions = filmSessionDAO.selectAmountFilmSessions(film.getId());
            if (amountOfSessions > 4) {
                amountOfSessions -= 4;
                film.setAdditionalSession(amountOfSessions);
            }
            if (user == null || Role.USER.equals(user.getRole())) {
                if (filmSessions.isEmpty() || !Film.haveAvailableSession(film.getId())) {
                    emptyFilms.add(film);
                }
            }
            filmSessions = filmSessions.stream().sorted().collect(Collectors.toList());
            filmMap.put(film.getId(), filmSessions);
        }

        for (Film film : emptyFilms) {
            films.remove(film);
        }

        films = sorting(films);

        films = pagination(req, films);

        List<Genre> genres = new GenreDAO().getGenres();

        req.setAttribute("films", films);
        req.setAttribute("filmMap", filmMap);
        req.setAttribute("genres", genres);
        req.setAttribute("sortBy", sortBy);
        req.setAttribute("sortOrder", sortOrder);
        req.setAttribute("showOnlyAvailable", showOnlyAvailable);
        req.setAttribute("showOnlyGenre", showOnlyGenre);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/timetable.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String temp = req.getParameter("sortBy");
        if (temp != null) {
            if (temp.equals(sortBy)) {
                sortOrder = changeSortOrder(sortOrder);
            } else {
                sortOrder = ASC;
                sortBy = temp;
            }
        }

        temp = req.getParameter("showOnlyAvailable");
        if (temp != null) {
            if (showOnlyAvailable.equals("Only available")) {
                showOnlyAvailable = "All";
            } else {
                showOnlyAvailable = "Only available";
            }
        }

        temp = req.getParameter("showOnlyGenre");
        if (temp != null) {
            showOnlyGenre = temp;
        }

        resp.sendRedirect("/cinema");
    }

    private List<Film> pagination(HttpServletRequest req, List<Film> films) {
        int filmAmount = films.size();
        int pageAmount = (int) Math.ceil((double) filmAmount / FILMS_LIMIT);

        Integer[] pages = new Integer[pageAmount];
        for (int i = 0; i < pageAmount; ++i) {
            pages[i] = i + 1;
        }

        if (req.getParameter("page") != null) {
            currentPage = Integer.parseInt(req.getParameter("page"));
            if (currentPage > pageAmount || currentPage < 1) {
                currentPage = 1;
            }
        }

        int offset = (currentPage - 1) * FILMS_LIMIT;
        int lastFilm = currentPage * FILMS_LIMIT;

        films = films.stream().skip(offset).limit(lastFilm).collect(Collectors.toList());

        req.setAttribute("currentPage", currentPage);
        req.setAttribute("pages", pages);
        req.setAttribute("firstPage", CONST_ONE);
        req.setAttribute("lastPage", pageAmount);

        return films;
    }

    private String changeSortOrder(String sortOrder) {
        if (sortOrder.equals(ASC)) {
            sortOrder = DESC;
        } else {
            sortOrder = ASC;
        }
        return sortOrder;
    }

    private List<Film> sorting(List<Film> films) {

        switch (sortBy) {
            case "Name": {
                if (sortOrder.equals(ASC)) {
                    films = films.stream().sorted().collect(Collectors.toList());
                } else {
                    films = films.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                }
                break;
            }

            case "Empty places": {
                if (showOnlyAvailable.equals("Only available")) {
                    if (sortOrder.equals(ASC)) {
                        films.sort(new FilmSessionAvailablePlaceComparator());
                    } else {
                        films.sort(new FilmSessionAvailablePlaceComparator().reversed());
                    }
                } else {
                    if (sortOrder.equals(ASC)) {
                        films.sort(new FilmSessionPlaceComparator());
                    } else {
                        films.sort(new FilmSessionPlaceComparator().reversed());
                    }
                }
                break;
            }

            default: {
                if (showOnlyAvailable.equals("Only available")) {
                    if (sortOrder.equals(ASC)) {
                        films.sort(new FilmSessionAvailableDateComparator());
                    } else {
                        films.sort(new FilmSessionAvailableDateComparator().reversed());
                    }
                } else {
                    if (sortOrder.equals(ASC)) {
                        films.sort(new FilmSessionDateComparator());
                    } else {
                        films.sort(new FilmSessionAvailableDateComparator().reversed());
                    }
                }
                break;
            }
        }

        return films;
    }

}
