package com.cinema.model.entity.ticket;

import java.math.BigDecimal;
import java.util.Date;

public class Ticket {

    private int userId;
    private int SessionHasPlaceId;
    private BigDecimal price;

    private String filmName;
    private String genre;
    private int duration;
    private Date date;
    private Date time;
    private int place;
    private String firstName;
    private String lastName;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSessionHasPlaceId() {
        return SessionHasPlaceId;
    }

    public void setSessionHasPlaceId(int sessionHasPlaceId) {
        SessionHasPlaceId = sessionHasPlaceId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "userId=" + userId +
                ", SessionHasPlaceId=" + SessionHasPlaceId +
                ", price=" + price +
                '}';
    }
}
