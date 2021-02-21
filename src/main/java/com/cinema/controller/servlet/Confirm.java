package com.cinema.controller.servlet;

import com.cinema.model.dao.SessionHasPlaceDAO;
import com.cinema.model.dao.TicketDAO;
import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.filmSession.SessionHasPlace;
import com.cinema.model.entity.ticket.Ticket;
import com.cinema.model.entity.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Enumeration;

public class Confirm extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/confirm.jsp");
        req.setAttribute("places", req.getSession(false).getAttribute("places"));
        req.setAttribute("user", req.getSession(false).getAttribute("user"));
        req.setAttribute("filmSession", req.getSession(false).getAttribute("filmSession"));
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int[] places = (int[]) req.getSession(false).getAttribute("places");
        SessionHasPlaceDAO shpDAO = new SessionHasPlaceDAO();
        if ("Cancel".equals(req.getParameter("button"))) {
            int filmSessionId = ((FilmSession) req.getSession(false).getAttribute("filmSession")).getId();
            for (int place : places) {
                int shpId = shpDAO.selectSHPIdBySessionAndPlaceId(filmSessionId, place);
                shpDAO.setAvailable(shpId, true);
            }
            String path = "/placeSelect?name=" + filmSessionId;
            resp.sendRedirect(path);
        } else if ("Confirm".equals(req.getParameter("button"))) {
            Ticket ticket;
            int filmSessionId = ((FilmSession) req.getSession(false).getAttribute("filmSession")).getId();
            for (int place : places) {
                ticket = new Ticket();
                ticket.setUserId(((User) req.getSession(false).getAttribute("user")).getId());
                int shpId = shpDAO.selectSHPIdBySessionAndPlaceId(filmSessionId, place);
                ticket.setSessionHasPlaceId(shpId);
                ticket.setPrice(new BigDecimal(30));
                new TicketDAO().insertTicket(ticket);
            }
            String path = "/cinema";
            resp.sendRedirect(path);
        } else {
            String path = "/errorPage.jsp";
            resp.sendRedirect(path);
        }
    }
}
