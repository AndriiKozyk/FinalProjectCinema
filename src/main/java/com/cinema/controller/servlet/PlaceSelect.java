package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.dao.SessionHasPlaceDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.filmSession.FilmSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.math.BigDecimal;
import java.util.*;

public class PlaceSelect extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int filmId = Integer.parseInt(req.getParameter("name"));
        Film film = new FilmDAO().selectFilm(filmId);
        List<FilmSession> sessions = new FilmSessionDAO().selectFilmSessions(filmId);

        FilmSession filmSession;
        if (req.getParameter("id") != null) {
            int sessionId = Integer.parseInt(req.getParameter("id"));
            filmSession = new FilmSessionDAO().getFilmSession(sessionId);
        } else {
            filmSession = sessions.get(0);
        }
        req.setAttribute("activeSession", filmSession);

        req.setAttribute("film", film);
        req.setAttribute("filmSessions", sessions);
        HttpSession session = req.getSession(false);
        if (session != null) {
            req.setAttribute("user", session.getAttribute("user"));
            session.setAttribute("activeSession", filmSession);
        }

        Map<Integer, BigDecimal> placePrice = new LinkedHashMap<>();
        for (int i = 0; i < filmSession.getPlaceList().size(); i++) {
            placePrice.put(filmSession.getPlaceList().get(i).getPlace().getId(),
                    film.getPrice().add(filmSession.getPlaceList().get(i).getPlace().getType().getPrice()));
        }
        req.getSession(false).setAttribute("placePrice", placePrice);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/placeSelect.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] stringPlaces = req.getParameterValues("place");
        if (stringPlaces == null) {
            String path = req.getServletPath() + "?name=" + req.getParameter("name");
            resp.sendRedirect(path);
            return;
        }
        int[] places = new int[stringPlaces.length];
        SessionHasPlaceDAO shpDAO = new SessionHasPlaceDAO();

        Map<Integer, BigDecimal> placePrice = (Map<Integer, BigDecimal>) req.getSession(false).getAttribute("placePrice");
        Map<Integer, BigDecimal> chosenPlaces = new LinkedHashMap<>();
        for (int i = 0; i < stringPlaces.length; i++) {
            places[i] = Integer.parseInt(stringPlaces[i]);
            int filmSessionId = ((FilmSession) req.getSession(false).getAttribute("activeSession")).getId();
            int shpId = shpDAO.selectSHPIdBySessionAndPlaceId(filmSessionId, places[i]);
            shpDAO.setAvailable(shpId, false);
            chosenPlaces.put(places[i], placePrice.get(places[i]));
        }
        BigDecimal totalPrice = new BigDecimal(0);
        for (BigDecimal price : chosenPlaces.values()) {
            totalPrice = totalPrice.add(price);
        }
        req.getSession(false).setAttribute("totalPrice", totalPrice);
        req.getSession(false).setAttribute("chosenPlaces", chosenPlaces);

        resp.sendRedirect("/confirm");
    }
}
