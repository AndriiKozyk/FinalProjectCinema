package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmDAO;
import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.dao.FilmToOrderDAO;
import com.cinema.model.dao.GenreDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.film.Genre;
import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.filmToOrder.FilmToOrder;

import java.util.List;

public final class ServletUtil {

    private static final GenreDAO GENRE_DAO = new GenreDAO();
    private static final FilmToOrderDAO FILM_TO_ORDER_DAO = new FilmToOrderDAO();
    private static final FilmDAO FILM_DAO = new FilmDAO();
    private static final FilmSessionDAO FILM_SESSION_DAO = new FilmSessionDAO();

    public static List<Genre> getGenres() {
        return GENRE_DAO.getGenres();
    }

    public static int getAmountUserSuggestions() {
        return FILM_TO_ORDER_DAO.amountUserSuggestion();
    }

    public static int getAmountVotedFilms() {
        return FILM_TO_ORDER_DAO.amountVotedFilms();
    }

    public static Film getFilm(int filmId) {
        return FILM_DAO.selectFilm(filmId);
    }

    public static List<FilmSession> getFilmSessions(int filmId) {
        return FILM_SESSION_DAO.selectFilmSessions(filmId);
    }

    public static FilmToOrder getFilmToOrder(int filmId) {
        return FILM_TO_ORDER_DAO.getOrderFilm(filmId);
    }

}
