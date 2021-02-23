package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.filmSession.Status;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EditFilmSession extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int filmId = Integer.parseInt(req.getParameter("name"));
        Film film = new FilmDAO().selectFilm(filmId);
        List<FilmSession> filmSessions = new FilmSessionDAO().selectFilmSessions(filmId);
        req.setAttribute("film", film);
        req.setAttribute("filmSessions", filmSessions);
        req.getSession(false).setAttribute("filmSessions", filmSessions);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/editSession.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String cancel = req.getParameter("cancel");
        if (cancel != null) {
            FilmSessionDAO sessionDAO = new FilmSessionDAO();
            if ("Cancel all sessions".equals(cancel)) {
                List<FilmSession> filmSessions = (List<FilmSession>) req.getSession(false).getAttribute("filmSessions");
                for (FilmSession session : filmSessions) {
                    session.setStatus(Status.CANCELED);
                    sessionDAO.setStatus(session);
                }
                resp.sendRedirect("/editSession?name=" + req.getParameter("name"));
                return;
            } else if ("Cancel selected".equals(cancel)) {
                String[] deleteSessions = req.getParameterValues("sessionForDelete");
                FilmSession filmSession;
                for (String delete : deleteSessions) {
                    filmSession = sessionDAO.getFilmSession(Integer.parseInt(delete));
                    filmSession.setStatus(Status.CANCELED);
                    sessionDAO.setStatus(filmSession);
                }
                resp.sendRedirect("/editSession?name=" + req.getParameter("name"));
                return;
            }
        }


        Film film = new FilmDAO().selectFilm(Integer.parseInt(req.getParameter("name")));

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
        System.out.println(time);
        filmSession.setFilm(film);
        filmSession.setMinPrice(new BigDecimal(100));
        filmSession.setMaxPrice(new BigDecimal(150));
        filmSession.setStatus(Status.AVAILABLE);
        new FilmSessionDAO().insertFilmSession(filmSession);
        filmSession.createPlaces();
        resp.sendRedirect("/editSession?name=" + req.getParameter("name"));
    }

}
