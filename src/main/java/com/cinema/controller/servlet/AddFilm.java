package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.GenreDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.film.Genre;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;

import java.math.BigDecimal;

import static com.cinema.controller.servlet.Constants.*;

public class AddFilm extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(GENRES, ServletUtil.getGenres());
        req.setAttribute(USER_SUGGESTIONS, ServletUtil.getAmountUserSuggestions());
        req.setAttribute(VOTED_FILMS, ServletUtil.getAmountVotedFilms());

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/addFilm.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GenreDAO genreDAO = new GenreDAO();
        if ("Add movie".equals(req.getParameter("action"))) {
            Film film = new Film();
            film.setNameEN(req.getParameter("nameEN"));
            film.setNameUA(req.getParameter("nameUA"));
            film.setDuration(Integer.parseInt(req.getParameter("duration")));
            film.setPrice(new BigDecimal(req.getParameter("price")));
            String trailer = buildTrailerLink(req.getParameter("trailer"));
            film.setTrailer(trailer);
            Genre genre = genreDAO.getGenre(Integer.parseInt(req.getParameter("genre")));
            film.setGenre(genre);
            Part part = req.getPart("poster");
            InputStream inputStream = part.getInputStream();
            film.setPosterInput(inputStream);
            new FilmDAO().insertFilm(film);
            resp.sendRedirect("/cinema");
        } else if ("Add genre".equals(req.getParameter("action"))) {
            Genre genre = new Genre();
            genre.setGenreEN(req.getParameter("genreEN"));
            genre.setGenreUA(req.getParameter("genreUA"));
            genreDAO.insertGenre(genre);
            resp.sendRedirect("/addMovie");
        }
    }

    private String buildTrailerLink(String link) {
        StringBuilder sb = new StringBuilder();
        String code = link.substring(32);
        sb.append("https://www.youtube.com/embed/").append(code).append("?autoplay=1");
        return sb.toString();
    }

}
