package com.cinema.model.entity.film;

import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.entity.filmSession.FilmSession;

import java.util.Comparator;

public class FilmDateComp implements Comparator<Film> {

    @Override
    public int compare(Film film1, Film film2) {
        FilmSessionDAO filmSessionDAO = new FilmSessionDAO();
        FilmSession session1 = filmSessionDAO.selectFilmSessions(film1.getId(), 1).get(0);
        FilmSession session2 = filmSessionDAO.selectFilmSessions(film2.getId(), 1).get(0);
        if (session1.getDate().equals(session2.getDate())) {
            return (int) (session1.getTime().getTime() - session2.getTime().getTime());
        }
        return (int) (session1.getDate().getTime() - session2.getDate().getTime());
    }

}
