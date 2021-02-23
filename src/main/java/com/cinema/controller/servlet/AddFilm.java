package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.GenreDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.film.Genre;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
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
        System.out.println("GOOD1");
        System.out.println(req.getParameter("action"));
        System.out.println(req.getServletContext().getAttribute("action"));

        if ("Add movie".equals(req.getParameter("action"))) {
            System.out.println("GOOD2");

            Film film = new Film();
            film.setNameEN(req.getParameter("nameEN"));
            film.setNameUA(req.getParameter("nameUA"));
            System.out.println(req.getParameter("nameEN"));
            System.out.println("GOOD3");

            film.setDuration(Integer.parseInt(req.getParameter("duration")));
            film.setPrice(new BigDecimal(req.getParameter("price")));
            Genre genre = new GenreDAO().getGenre(Integer.parseInt(req.getParameter("genre")));
            System.out.println("GOOD4");

            film.setGenre(genre);
            System.out.println("GOOD5");
            Part part = req.getPart("poster");
            System.out.println("GOOD6");
            InputStream inputStream = part.getInputStream();
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            File file = new File("images/poster.jpg");
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(buffer);
            film.setPoster(file);
            System.out.println(file);
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
