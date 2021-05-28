package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.FilmToOrderDAO;
import com.cinema.model.dao.GenreDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.film.Genre;
import com.cinema.model.entity.filmToOrder.FilmStatus;
import com.cinema.model.entity.filmToOrder.FilmToOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class OrderFilmToRolling extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int filmId = Integer.parseInt(req.getParameter("name"));
        FilmToOrderDAO filmToOrderDAO = new FilmToOrderDAO();
        FilmToOrder film = filmToOrderDAO.getOrderFilm(filmId);
        List<Genre> genres = new GenreDAO().getGenres();

        req.setAttribute("genres", genres);
        req.setAttribute("film", film);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/filmToOrderToRolling.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GenreDAO genreDAO = new GenreDAO();
        if ("Add movie".equals(req.getParameter("action"))) {

            int filmId = Integer.parseInt(req.getParameter("id"));
            FilmToOrderDAO filmToOrderDAO = new FilmToOrderDAO();
            FilmToOrder filmToOrder = filmToOrderDAO.getOrderFilm(filmId);

            filmToOrderDAO.updateOrderFilmStatus(filmToOrder.getId(), FilmStatus.APPROVED.toString());

            Film film = mapFilmToOrderToFilm(filmToOrder);

            film.setDuration(Integer.parseInt(req.getParameter("duration")));
            film.setPrice(new BigDecimal(req.getParameter("price")));
            Genre genre = genreDAO.getGenre(Integer.parseInt(req.getParameter("genre")));
            film.setGenre(genre);
            new FilmDAO().insertFilm(film);
            resp.sendRedirect("/cinema");
        } else if ("Add genre".equals(req.getParameter("action"))) {
            Genre genre = new Genre();
            genre.setGenreEN(req.getParameter("genreEN"));
            genre.setGenreUA(req.getParameter("genreUA"));
            genreDAO.insertGenre(genre);
            resp.sendRedirect("/toRolling");
        }
    }

    private Film mapFilmToOrderToFilm(FilmToOrder filmToOrder) {
        Film film = new Film();
        film.setNameEN(filmToOrder.getNameEN());
        film.setNameUA(filmToOrder.getNameUA());
        film.setPosterInput(filmToOrder.getPosterInput());
        film.setTrailer(filmToOrder.getTrailer());
        return film;
    }

}
