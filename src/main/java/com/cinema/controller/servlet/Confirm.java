package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.dao.SessionHasPlaceDAO;
import com.cinema.model.dao.TicketDAO;
import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.filmSession.SessionHasPlace;
import com.cinema.model.entity.filmSession.Status;
import com.cinema.model.entity.ticket.Ticket;
import com.cinema.model.entity.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

public class Confirm extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/confirm.jsp");
        HttpSession session = req.getSession(false);
        req.setAttribute("places", session.getAttribute("chosenPlaces"));
        req.setAttribute("totalPrice", session.getAttribute("totalPrice"));
        req.setAttribute("user", session.getAttribute("user"));
        req.setAttribute("filmSession", session.getAttribute("activeSession"));
        if (session.getAttribute("timeOut") != null) {
            req.setAttribute("timeOut", session.getAttribute("timeOut"));
            session.setAttribute("timeOut", false);
        }
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Map<Integer, BigDecimal> places = (Map<Integer, BigDecimal>) session.getAttribute("chosenPlaces");
        SessionHasPlaceDAO shpDAO = new SessionHasPlaceDAO();
        FilmSession activeSession = ((FilmSession) session.getAttribute("activeSession"));
        int filmSessionId = activeSession.getId();
        if ("Cancel".equals(req.getParameter("button"))) {
            for (int place : places.keySet()) {
                int shpId = shpDAO.selectSHPIdBySessionAndPlaceId(filmSessionId, place);
                shpDAO.setAvailable(shpId, true);
                shpDAO.setBookTimeNull(shpId);
            }
            if (Status.NO_PLACES.equals(activeSession.getStatus())) {
                activeSession.setStatus(Status.AVAILABLE);
                new FilmSessionDAO().setStatus(activeSession);
            }
            String path = "/placeSelect?name=" + activeSession.getFilm().getId() + "&id=" + filmSessionId;
            resp.sendRedirect(path);
        } else if ("Confirm".equals(req.getParameter("button"))) {
            Ticket ticket;
            for (int place : places.keySet()) {
                int shpId = shpDAO.selectSHPIdBySessionAndPlaceId(filmSessionId, place);
                boolean timeOut = shpDAO.isTimeOut(shpId);
                if (timeOut) {
                    session.setAttribute("timeOut", true);
                    resp.sendRedirect("/confirm");
                    return;
                }
                ticket = new Ticket();
                ticket.setUserId(((User) session.getAttribute("user")).getId());
                SessionHasPlace shp = shpDAO.getSessionHasPlace(shpId);
                ticket.setSessionHasPlaceId(shpId);
                BigDecimal price = activeSession.getFilm().getPrice().add(shp.getPlace().getType().getPrice());
                ticket.setPrice(price);
                new TicketDAO().insertTicket(ticket);
                shpDAO.setBookTimeNull(shpId);
            }
            String path = "/cinema";
            resp.sendRedirect(path);
        } else {
            String path = "/errorPage.jsp";
            resp.sendRedirect(path);
        }
    }
}
