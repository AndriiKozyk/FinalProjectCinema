package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.entity.filmSession.FilmSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TimetableAdmin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<FilmSession> filmSession = new FilmSessionDAO().selectFilmSessions();
        req.setAttribute("filmSession", filmSession);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/timetableAdmin.jsp");
        requestDispatcher.forward(req, resp);


    }

}
