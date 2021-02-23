package com.cinema.model.entity.filmSession;

import com.cinema.model.entity.place.Place;

public class SessionHasPlace {

    private int id;
    private int sessionId;
    private Place place;
    private boolean available;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSessionId() {
        return sessionId;
    }

    public void setSessionId(int sessionId) {
        this.sessionId = sessionId;
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

    @Override
    public String toString() {
        return "SessionHasPlace{" +
                "id=" + id +
                ", sessionId=" + sessionId +
                ", place=" + place +
                ", available=" + available +
                '}';
    }
}
