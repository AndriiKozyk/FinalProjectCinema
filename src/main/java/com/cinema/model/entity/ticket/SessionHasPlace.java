package com.cinema.model.entity.ticket;

import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.place.Place;

public class SessionHasPlace {

    private int id;
    private FilmSession filmSession;
    private Place place;
    private boolean available;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public FilmSession getFilmSession() {
        return filmSession;
    }

    public void setFilmSession(FilmSession filmSession) {
        this.filmSession = filmSession;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
