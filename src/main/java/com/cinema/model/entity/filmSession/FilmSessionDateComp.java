package com.cinema.model.entity.filmSession;

import java.util.Comparator;

public class FilmSessionDateComp implements Comparator<FilmSession> {

    @Override
    public int compare(FilmSession session1, FilmSession session2) {
        return (int) (session1.getDate().getTime() - session2.getDate().getTime());
    }

}
