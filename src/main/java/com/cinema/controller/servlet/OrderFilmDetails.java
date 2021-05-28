package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmToOrderDAO;
import com.cinema.model.entity.filmToOrder.FilmToOrder;
import com.cinema.model.entity.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OrderFilmDetails extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int filmId = Integer.parseInt(req.getParameter("name"));
        FilmToOrderDAO filmToOrderDAO = new FilmToOrderDAO();
        FilmToOrder film = filmToOrderDAO.getOrderFilm(filmId);

        boolean userVote = true;

        if (req.getSession(false) != null) {
            int userId = ((User) req.getSession(false).getAttribute("user")).getId();
            userVote = filmToOrderDAO.isUserVote(userId, filmId);
        }

        req.setAttribute("userVote", userVote);
        req.setAttribute("film", film);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/filmToOrderDetails.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int userId = ((User) req.getSession(false).getAttribute("user")).getId();
        int filmId = Integer.parseInt(req.getParameter("id"));

        FilmToOrderDAO filmToOrderDAO = new FilmToOrderDAO();
        filmToOrderDAO.insertUserVote(userId, filmId);

        doGet(req, resp);

    }
}
