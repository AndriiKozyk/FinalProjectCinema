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

public class SuggestMovie extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/suggestMovie.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        FilmToOrder filmToOrder = new FilmToOrder();
        FilmToOrderDAO filmToOrderDAO = new FilmToOrderDAO();

        filmToOrder.setNameEN(req.getParameter("name"));
        String year = check(req.getParameter("year"));
        filmToOrder.setYear(Integer.parseInt(year));
        filmToOrder.setDescription(req.getParameter("description"));

        User user = (User) req.getSession(false).getAttribute("user");
        filmToOrderDAO.insertOrderFilm(filmToOrder, user.getId());

        resp.sendRedirect("/suggestionsList");
    }

    private String check(String parameter) {
        if (parameter.equals("")) {
            return "0";
        }
        return parameter;
    }

}
