package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.dao.SessionHasPlaceDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.filmSession.FilmSessionStatus;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.math.BigDecimal;
import java.util.*;

import static com.cinema.controller.servlet.Constants.*;

public class PlaceSelect extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int filmId = Integer.parseInt(req.getParameter(NAME));
        Film film = ServletUtil.getFilm(filmId);
        List<FilmSession> sessions = ServletUtil.getFilmSessions(filmId);
        FilmSession filmSession;

        if (req.getParameter(ID) != null) {
            int sessionId = Integer.parseInt(req.getParameter(ID));
            filmSession = new FilmSessionDAO().getFilmSession(sessionId);
            if (!FilmSessionStatus.AVAILABLE.equals(filmSession.getStatus()) || filmSession.getFilm().getId() != filmId) {
                filmSession = null;
            }
        } else {
            filmSession = null;
            for (FilmSession session : sessions) {
                if (FilmSessionStatus.AVAILABLE.equals(session.getStatus())) {
                    filmSession = session;
                    break;
                }
            }
        }

        if (filmSession == null) {
            resp.sendRedirect("/errorPage.jsp");
            return;
        }

        req.setAttribute(ACTIVE_SESSION, filmSession);
        req.setAttribute(FILM, film);
        req.setAttribute(FILM_SESSIONS, sessions);
        HttpSession session = req.getSession(false);
        Map<Integer, BigDecimal> placePrice = new LinkedHashMap<>();

        for (int i = 0; i < filmSession.getPlaceList().size(); i++) {
            placePrice.put(filmSession.getPlaceList().get(i).getPlace().getId(),
                    film.getPrice().add(filmSession.getPlaceList().get(i).getPlace().getType().getPrice()));
        }
        if (session != null) {
            req.setAttribute(USER, session.getAttribute(USER));
            session.setAttribute(ACTIVE_SESSION, filmSession);
            session.setAttribute("placePrice", placePrice);
        }

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/placeSelect.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        String[] stringPlaces = req.getParameterValues("place");
        if (stringPlaces == null) {
            String path = req.getServletPath() + "?name=" + req.getParameter(NAME);
            resp.sendRedirect(path);
            return;
        }
        int[] places = new int[stringPlaces.length];
        SessionHasPlaceDAO shpDAO = new SessionHasPlaceDAO();

        Map<Integer, BigDecimal> placePrice = (Map<Integer, BigDecimal>) session.getAttribute("placePrice");
        Map<Integer, BigDecimal> chosenPlaces = new LinkedHashMap<>();
        FilmSession filmSession = ((FilmSession) session.getAttribute(ACTIVE_SESSION));
        int filmSessionId = filmSession.getId();
        for (int i = 0; i < stringPlaces.length; i++) {
            places[i] = Integer.parseInt(stringPlaces[i]);
            int shpId = shpDAO.selectSHPIdBySessionAndPlaceId(filmSessionId, places[i]);
            if (shpDAO.isPlaceAvailable(shpId)) {
                shpDAO.insertBookTime(System.currentTimeMillis(), shpId);
                shpDAO.setAvailable(shpId, false);
                chosenPlaces.put(places[i], placePrice.get(places[i]));
            } else {
                String path = req.getServletPath() + "?name=" + req.getParameter(NAME);
                resp.sendRedirect(path);
                return;
            }
        }
        int available = shpDAO.selectAmountAvailablePlaces(filmSessionId);
        if (available == 0) {
            filmSession.setStatus(FilmSessionStatus.NO_PLACES);
            new FilmSessionDAO().setStatus(filmSession);
        }
        BigDecimal totalPrice = new BigDecimal(0);
        for (BigDecimal price : chosenPlaces.values()) {
            totalPrice = totalPrice.add(price);
        }
        session.setAttribute("totalPrice", totalPrice);
        session.setAttribute("chosenPlaces", chosenPlaces);

        resp.sendRedirect("/confirm");
    }

}
