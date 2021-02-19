package com.cinema.controller.web;

import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.entity.filmSession.FilmSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class PlaceSelect extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FilmSession filmSession = new FilmSessionDAO().getFilmSession(Integer.parseInt(req.getParameter("name")));
        req.setAttribute("filmSession", filmSession);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/placeSelect.jsp");
        requestDispatcher.forward(req, resp);
    }

}
