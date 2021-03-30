package com.cinema.model.entity.filmSession;

import java.util.Comparator;

public class FilmSessionTimeComp implements Comparator<FilmSession> {

    @Override
    public int compare(FilmSession session1, FilmSession session2) {
        return (int) (session1.getTime().getTime() - session2.getTime().getTime());
    }

}
