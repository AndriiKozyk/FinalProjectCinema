package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.dao.SessionHasPlaceDAO;
import com.cinema.model.entity.filmSession.FilmSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PlaceSelect extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("GET");
        FilmSession filmSession = new FilmSessionDAO().getFilmSession(Integer.parseInt(req.getParameter("name")));
        req.setAttribute("filmSession", filmSession);
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.setAttribute("filmSession", filmSession);
        }
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/placeSelect.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST");
        String[] stringPlaces = req.getParameterValues("place");
        if (stringPlaces == null) {
            String path = req.getServletPath() + "?name=" + req.getParameter("name");
            resp.sendRedirect(path);
            return;
        }
        int[] places = new int[stringPlaces.length];
        SessionHasPlaceDAO shpDAO = new SessionHasPlaceDAO();
        for (int i = 0; i < stringPlaces.length; i++) {
            places[i] = Integer.parseInt(stringPlaces[i]);
            int filmSessionId = ((FilmSession) req.getSession(false).getAttribute("filmSession")).getId();
            int shpId = shpDAO.selectSHPIdBySessionAndPlaceId(filmSessionId, places[i]);
            shpDAO.setAvailable(shpId, false);
        }
        req.getSession(false).setAttribute("places", places);
        resp.sendRedirect("/confirm");
    }
}
