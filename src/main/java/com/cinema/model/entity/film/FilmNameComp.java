package com.cinema.model.entity.film;

import com.cinema.model.entity.film.Film;

import java.util.Comparator;

public class FilmNameComp implements Comparator<Film> {
    @Override
    public int compare(Film film1, Film film2) {
        return film1.getNameEN().compareTo(film2.getNameEN());
    }

}
