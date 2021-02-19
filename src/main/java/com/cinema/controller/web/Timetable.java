package com.cinema.controller.web;

import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.entity.filmSession.FilmSession;

import java.util.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Timetable extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FilmSession> filmSession = new FilmSessionDAO().selectFilmSessions();
        req.setAttribute("filmSession", filmSession);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/timetable.jsp");
        requestDispatcher.forward(req, resp);
    }

    public static void main(String[] args) {
        List<FilmSession> filmSession = new FilmSessionDAO().selectFilmSessions();
        System.out.println(filmSession);
        System.out.println(filmSession.get(0).getFilm().getPoster().getAbsolutePath());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("POST");
    }
}
