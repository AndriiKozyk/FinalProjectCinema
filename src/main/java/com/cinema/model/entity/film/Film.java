package com.cinema.model.entity.film;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;

public class Film {

    private int id;
    private String nameEN;
    private String nameUA;
    private int duration;
    private BigDecimal price;
    private Genre genre;

    private InputStream posterInput;
    private String posterOut;

    private int additionalSession;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameEN() {
        return nameEN;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public String getNameUA() {
        return nameUA;
    }

    public void setNameUA(String nameUA) {
        this.nameUA = nameUA;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getAdditionalSession() {
        return additionalSession;
    }

    public void setAdditionalSession(int additionalSession) {
        this.additionalSession = additionalSession;
    }

    public InputStream getPosterInput() {
        return posterInput;
    }

    public void setPosterInput(InputStream posterInput) {
        this.posterInput = posterInput;
    }

    public String getPosterOut() {
        return posterOut;
    }

    public void setPosterOut(String posterOut) {
        this.posterOut = posterOut;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", nameEN='" + nameEN + '\'' +
                ", nameUA='" + nameUA + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", genre=" + genre +
                '}';
    }
}
