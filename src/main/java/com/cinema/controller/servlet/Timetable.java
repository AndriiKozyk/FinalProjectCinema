package com.cinema.controller.servlet;

import com.cinema.model.comparator.FilmSessionAvailableDateComparator;
import com.cinema.model.comparator.FilmSessionAvailablePlaceComparator;
import com.cinema.model.comparator.FilmSessionDateComparator;
import com.cinema.model.comparator.FilmSessionPlaceComparator;
import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.dao.FilmToOrderDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.filmSession.FilmSession;
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

import static com.cinema.controller.servlet.Constants.*;

public class Timetable extends HttpServlet {

    private int currentPage = 1;
    private final FilmDAO filmDao = new FilmDAO();
    private final FilmSessionDAO filmSessionDAO = new FilmSessionDAO();
    private String sortOrder = "ascending";
    private String sortBy = "Date / Time";
    private String showOnlyAvailable = "Only available";
    private String showOnlyGenre = "All";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String link = "placeSelect";

        if ("logout".equals(req.getParameter(NAME))) {
            String active = null;
            if (req.getSession(false) != null) {
                req.getSession(false).invalidate();
            }
            currentPage = 1;
            req.getServletContext().setAttribute("active", active);
        }

        User user = null;
        if (req.getSession(false) != null) {
            user = (User) req.getSession(false).getAttribute(USER);
            req.setAttribute(USER, user);
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

        req.setAttribute("films", films);
        req.setAttribute("filmMap", filmMap);
        req.setAttribute(GENRES, ServletUtil.getGenres());
        req.setAttribute("sortBy", sortBy);
        req.setAttribute("sortOrder", sortOrder);
        req.setAttribute("showOnlyAvailable", showOnlyAvailable);
        req.setAttribute("showOnlyGenre", showOnlyGenre);
        req.setAttribute(USER_SUGGESTIONS, ServletUtil.getAmountUserSuggestions());
        req.setAttribute(VOTED_FILMS, ServletUtil.getAmountVotedFilms());

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

        req.setAttribute(CURRENT_PAGE, currentPage);
        req.setAttribute(PAGES, pages);
        req.setAttribute(FIRST_PAGE, CONST_ONE);
        req.setAttribute(LAST_PAGE, pageAmount);

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
