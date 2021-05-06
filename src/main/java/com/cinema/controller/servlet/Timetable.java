package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.entity.film.Film;
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
    private static final FilmDAO filmDao = new FilmDAO();

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

        List<Film> films = filmDao.selectFilms();
        Map<Integer, List<FilmSession>> filmMap = new LinkedHashMap<>();
        List<Film> emptyFilms = new ArrayList<>();

        for (Film film : films) {
            List<FilmSession> filmSessions = new FilmSessionDAO().selectFilmSessions(film.getId(), SESSION_LIMIT);
            int amountOfSessions = new FilmSessionDAO().selectAmountFilmSessions(film.getId());
            if (amountOfSessions > 4) {
                amountOfSessions -= 4;
                film.setAdditionalSession(amountOfSessions);
            }
            if (user == null || Role.USER.equals(user.getRole())) {
                if (filmSessions.isEmpty() || !Film.haveAvailableSession(film.getId())) {
                    emptyFilms.add(film);
                }
            }
            filmMap.put(film.getId(), filmSessions);
        }

        for (Film film : emptyFilms) {
            films.remove(film);
        }

        films = pagination(req, films);

        req.setAttribute("films", films);
        req.setAttribute("filmMap", filmMap);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/timetable.jsp");
        requestDispatcher.forward(req, resp);
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

}
