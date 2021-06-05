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

import static com.cinema.controller.servlet.Constants.*;

public class UserTickets extends HttpServlet {

    private int currentPage = 1;
    private static final TicketDAO ticketDAO = new TicketDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            User user = (User) session.getAttribute(USER);

            List<Ticket> tickets = pagination(req, user);

            req.setAttribute("tickets", tickets);

            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/userTickets.jsp");
            requestDispatcher.forward(req, resp);
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("/errorPage.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    private List<Ticket> pagination(HttpServletRequest req, User user) {
        int ticketAmount = ticketDAO.amountTickets(user.getId());
        int pageAmount = (int) Math.ceil((double) ticketAmount / TICKETS_LIMIT);

        Integer[] pages = new Integer[pageAmount];
        for (int i = 0; i < pageAmount; ++i) {
            pages[i] = i + 1;
        }

        if (req.getParameter("page") != null) {
            currentPage = Integer.parseInt(req.getParameter("page"));
            if (currentPage > pageAmount || currentPage < 1) {
                currentPage = 1;
            }
        }

        int offset = (currentPage - 1) * TICKETS_LIMIT;
        List<Ticket> tickets = ticketDAO.getUserTickets(user.getId(), offset, TICKETS_LIMIT);

        req.setAttribute(CURRENT_PAGE, currentPage);
        req.setAttribute(PAGES, pages);
        req.setAttribute(FIRST_PAGE, CONST_ONE);
        req.setAttribute(LAST_PAGE, pageAmount);

        return tickets;
    }

}
