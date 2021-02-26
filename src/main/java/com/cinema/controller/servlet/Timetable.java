package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.FilmSessionDAO;
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

public class Timetable extends HttpServlet {

    private static final int SESSION_LIMIT = 4;

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

        List<Film> films = new FilmDAO().selectFilms();

        Map<Integer, List<FilmSession>> filmMap = new LinkedHashMap<>();

        for (Film film : films) {
            List<FilmSession> filmSession = new FilmSessionDAO().selectFilmSessions(film.getId(), SESSION_LIMIT);
            int amountOfSessions = new FilmSessionDAO().selectAmountFilmSessions(film.getId());
            if (amountOfSessions > 4) {
                amountOfSessions -= 4;
                film.setAdditionalSession(amountOfSessions);
            }
            filmMap.put(film.getId(), filmSession);
            if (user == null || Role.USER.equals(user.getRole())) {
                if (filmSession.isEmpty()) {
                    films.remove(film);
                }
            }
        }

        req.setAttribute("films", films);
        req.setAttribute("filmMap", filmMap);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/timetable.jsp");
        requestDispatcher.forward(req, resp);
    }

    public static void main(String[] args) {
        List<FilmSession> filmSession = new FilmSessionDAO().selectFilmSessions();
        System.out.println(filmSession);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST");
    }
}
