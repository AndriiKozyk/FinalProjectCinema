package com.cinema.model.entity.ticket;

import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.place.Place;
import com.cinema.model.entity.user.User;

import java.math.BigDecimal;

public class Ticket {

    private int userId;
    private int SessionHasPlaceId;
    private BigDecimal price;

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

    @Override
    public String toString() {
        return "Ticket{" +
                "userId=" + userId +
                ", SessionHasPlaceId=" + SessionHasPlaceId +
                ", price=" + price +
                '}';
    }
}
