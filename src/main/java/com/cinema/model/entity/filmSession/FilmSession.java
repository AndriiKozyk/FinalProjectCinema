package com.cinema.model.entity.filmSession;

import com.cinema.model.dao.PlaceDAO;
import com.cinema.model.dao.SessionHasPlaceDAO;
import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.place.Place;

import java.math.BigDecimal;
import java.util.*;


public class FilmSession {

    private int id;
    private Date date;
    private Date time;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private Film film;
    private Status status;

    private int availablePlaces;

    private List<SessionHasPlace> placeList;

    {
        for (Place place : new PlaceDAO().selectPlaces()) {
            SessionHasPlace shp = new SessionHasPlace();
            shp.setSessionId(id);
            shp.setPlace(place);
            shp.setAvailable(false);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<SessionHasPlace> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<SessionHasPlace> placeList) {
        this.placeList = placeList;
    }

    public int getAvailablePlaces() {
        return new SessionHasPlaceDAO().selectAmountAvailablePlaces(id);
    }

    public void setAvailablePlaces(int availablePlaces) {
        this.availablePlaces = availablePlaces;
    }

    @Override
    public String toString() {
        return "FilmSession{" +
                "id=" + id +
                ", date=" + date +
                ", time=" + time +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", film=" + film +
                ", status=" + status +
                ", placeList=" + placeList +
                '}';
    }
}
