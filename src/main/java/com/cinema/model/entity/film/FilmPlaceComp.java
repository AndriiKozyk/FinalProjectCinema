package com.cinema.model.entity.film;

import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.entity.filmSession.FilmSession;

import java.sql.SQLException;
import java.util.Comparator;

public class FilmPlaceComp implements Comparator<Film> {

    @Override
    public int compare(Film film1, Film film2) {
        FilmSessionDAO filmSessionDAO = new FilmSessionDAO();
        FilmSession session1;
        FilmSession session2;
        try {
            session1 = filmSessionDAO.getFilmSessionByFilmAndStatus(film1.getId(), 1);
        } catch (SQLException e) {
            return 1;
        }
        try {
            session2 = filmSessionDAO.getFilmSessionByFilmAndStatus(film2.getId(), 1);
        } catch (SQLException e) {
            return -1;
        }
        return session2.getAvailablePlaces() - session1.getAvailablePlaces();
    }

}
