package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.filmSession.FilmSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TimetableAdmin extends HttpServlet {

    private static final int SESSION_LIMIT = 4;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        List<Film> films = new FilmDAO().selectFilms();
        req.setAttribute("films", films);

        Map<Integer, List<FilmSession>> filmMap = new LinkedHashMap<>();

        for (Film film : films) {
            List<FilmSession> filmSession = new FilmSessionDAO().selectFilmSessions(film.getId(), SESSION_LIMIT);
            int amountOfSessions = new FilmSessionDAO().selectAmountFilmSessions(film.getId());
            if (amountOfSessions > 4) {
                amountOfSessions -= 4;
                film.setAdditionalSession(amountOfSessions);
            }
            filmMap.put(film.getId(), filmSession);
        }

        req.setAttribute("filmMap", filmMap);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/timetableAdmin.jsp");
        requestDispatcher.forward(req, resp);


    }

}
