package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.dao.TypeDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.filmSession.FilmSessionStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.cinema.controller.servlet.Constants.*;

public class EditFilmSession extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int filmId = Integer.parseInt(req.getParameter(NAME));

        req.setAttribute(FILM, ServletUtil.getFilm(filmId));
        req.setAttribute(FILM_SESSIONS, ServletUtil.getFilmSessions(filmId));
        req.setAttribute(USER_SUGGESTIONS, ServletUtil.getAmountUserSuggestions());
        req.setAttribute(VOTED_FILMS, ServletUtil.getAmountVotedFilms());
        req.getSession(false).setAttribute(FILM_SESSIONS, ServletUtil.getFilmSessions(filmId));
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/editSession.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cancel = req.getParameter("cancel");
        if (cancel != null) {
            FilmSessionDAO sessionDAO = new FilmSessionDAO();
            if ("Cancel all sessions".equals(cancel)) {
                List<FilmSession> filmSessions = (List<FilmSession>) req.getSession(false).getAttribute(FILM_SESSIONS);
                for (FilmSession session : filmSessions) {
                    session.setStatus(FilmSessionStatus.CANCELED);
                    sessionDAO.setStatus(session);
                }
                resp.sendRedirect("/editSession?name=" + req.getParameter(NAME));
                return;
            } else if ("Cancel selected".equals(cancel)) {
                String[] deleteSessions = req.getParameterValues("sessionForDelete");
                FilmSession filmSession;
                for (String delete : deleteSessions) {
                    filmSession = sessionDAO.getFilmSession(Integer.parseInt(delete));
                    filmSession.setStatus(FilmSessionStatus.CANCELED);
                    sessionDAO.setStatus(filmSession);
                }
                resp.sendRedirect("/editSession?name=" + req.getParameter(NAME));
                return;
            }
        }

        Film film = new FilmDAO().selectFilm(Integer.parseInt(req.getParameter(NAME)));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        Date date = null;
        Date time = null;
        try {
            date = dateFormat.parse(req.getParameter("date"));
            time = timeFormat.parse(req.getParameter("time"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        FilmSession filmSession = new FilmSession();
        filmSession.setDate(date);
        filmSession.setTime(time);
        filmSession.setFilm(film);
        TypeDAO typeDAO = new TypeDAO();
        filmSession.setMinPrice(film.getPrice().add(typeDAO.getPrice(false)));
        filmSession.setMaxPrice(film.getPrice().add(typeDAO.getPrice(true)));
        filmSession.setStatus(FilmSessionStatus.AVAILABLE);
        new FilmSessionDAO().insertFilmSession(filmSession);
        filmSession.createPlaces();
        resp.sendRedirect("/editSession?name=" + req.getParameter(NAME));

    }

}
