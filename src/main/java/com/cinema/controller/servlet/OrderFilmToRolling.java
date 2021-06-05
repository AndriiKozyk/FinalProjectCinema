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

import static com.cinema.controller.servlet.Constants.*;

public class OrderFilmToRolling extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int filmId = Integer.parseInt(req.getParameter(NAME));

        req.setAttribute(USER_SUGGESTIONS, ServletUtil.getAmountUserSuggestions());
        req.setAttribute(VOTED_FILMS, ServletUtil.getAmountVotedFilms());
        req.setAttribute(GENRES, ServletUtil.getGenres());
        req.setAttribute(FILM, ServletUtil.getFilmToOrder(filmId));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/filmToOrderToRolling.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GenreDAO genreDAO = new GenreDAO();
        int filmId = Integer.parseInt(req.getParameter(ID));
        if ("Add movie".equals(req.getParameter("action"))) {

            FilmToOrderDAO filmToOrderDAO = new FilmToOrderDAO();
            FilmToOrder filmToOrder = filmToOrderDAO.getOrderFilm(filmId);

            filmToOrderDAO.updateOrderFilmStatus(filmToOrder.getId(), FilmStatus.APPROVED.toString());

            Film film = mapFilmToOrderToFilm(filmToOrder);

            film.setDuration(Integer.parseInt(req.getParameter("duration")));
            film.setPrice(new BigDecimal(req.getParameter("price")));
            Genre genre = genreDAO.getGenre(Integer.parseInt(req.getParameter("genre")));
            film.setGenre(genre);
            new FilmDAO().insertFilm(film);
            resp.sendRedirect("/suggestionsList?name=movies");
        } else if ("Add genre".equals(req.getParameter("action"))) {
            Genre genre = new Genre();
            genre.setGenreEN(req.getParameter("genreEN"));
            genreDAO.insertGenre(genre);
            resp.sendRedirect("/toRolling?name=" + filmId);
        }
    }

    private Film mapFilmToOrderToFilm(FilmToOrder filmToOrder) {
        Film film = new Film();
        film.setNameEN(filmToOrder.getNameEN());
        film.setPosterInput(filmToOrder.getPosterInput());
        film.setTrailer(filmToOrder.getTrailer());
        return film;
    }

}
