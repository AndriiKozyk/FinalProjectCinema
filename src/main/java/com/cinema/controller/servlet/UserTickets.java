package com.cinema.controller.servlet;

import com.cinema.model.dao.TicketDAO;
import com.cinema.model.entity.ticket.Ticket;
import com.cinema.model.entity.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import java.util.*;

public class UserTickets extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute("user");
            List<Ticket> tickets = new TicketDAO().getUserTickets(user.getId());
            req.setAttribute("tickets", tickets);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/userTickets.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/errorPage.jsp");
            requestDispatcher.forward(req, resp);

        }

    }

}
