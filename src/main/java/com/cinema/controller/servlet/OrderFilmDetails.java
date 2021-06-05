package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmToOrderDAO;
import com.cinema.model.entity.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.cinema.controller.servlet.Constants.*;

public class OrderFilmDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int filmId = Integer.parseInt(req.getParameter(NAME));

        boolean userVote = true;

        if (req.getSession(false) != null) {
            User user = (User) req.getSession(false).getAttribute(USER);
            if (user != null) {
                int userId = user.getId();
                userVote = new FilmToOrderDAO().isUserVote(userId, filmId);
            }
        }

        req.setAttribute("userVote", userVote);
        req.setAttribute(FILM, ServletUtil.getFilmToOrder(filmId));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/filmToOrderDetails.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userId = ((User) req.getSession(false).getAttribute(USER)).getId();
        int filmId = Integer.parseInt(req.getParameter(ID));

        FilmToOrderDAO filmToOrderDAO = new FilmToOrderDAO();
        filmToOrderDAO.insertUserVote(userId, filmId);

        doGet(req, resp);

    }
}
