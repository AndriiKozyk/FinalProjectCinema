package com.cinema.comparator;

import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.filmSession.FilmSession;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FilmSessionAvailableDateComparator implements Comparator<Film> {

    @Override
    public int compare(Film film1, Film film2) {

        List<FilmSession> film1Sessions = getSessions(film1.getId());
        List<FilmSession> film2Sessions = getSessions(film2.getId());

        film1Sessions = film1Sessions.stream().sorted().collect(Collectors.toList());
        film2Sessions = film2Sessions.stream().sorted().collect(Collectors.toList());

        FilmSession session1;
        FilmSession session2;

        if (film1Sessions.size() > 0) {
            if (film2Sessions.size() > 0) {
                session1 = film1Sessions.get(0);
                session2 = film2Sessions.get(0);
            } else {
                return 1;
            }
        } else {
            return -1;
        }

        return session1.compareTo(session2);
    }

    private List<FilmSession> getSessions(int filmId) {
        return new FilmSessionDAO().selectAvailableFilmSessions(filmId);
    }

}
