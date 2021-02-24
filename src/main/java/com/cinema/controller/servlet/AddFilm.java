package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.GenreDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.film.Genre;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.awt.image.BufferedImage;
import java.io.*;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.*;

public class AddFilm extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Genre> genres = new GenreDAO().getGenres();
        req.setAttribute("genres", genres);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/addFilm.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if ("Add movie".equals(req.getParameter("action"))) {
            Film film = new Film();
            film.setNameEN(req.getParameter("nameEN"));
            film.setNameUA(req.getParameter("nameUA"));
            film.setDuration(Integer.parseInt(req.getParameter("duration")));
            film.setPrice(new BigDecimal(req.getParameter("price")));
            Genre genre = new GenreDAO().getGenre(Integer.parseInt(req.getParameter("genre")));
            film.setGenre(genre);
            Part part = req.getPart("poster");
            InputStream inputStream = part.getInputStream();
            film.setPosterInput(inputStream);
            new FilmDAO().insertFilm(film);
            resp.sendRedirect("/timetableAdmin");
        } else if ("Add genre".equals(req.getParameter("action"))) {
            Genre genre = new Genre();
            genre.setGenreEN(req.getParameter("genreEN"));
            genre.setGenreUA(req.getParameter("genreUA"));
            new GenreDAO().insertGenre(genre);
            resp.sendRedirect("/addMovie");
        }

    }
}
