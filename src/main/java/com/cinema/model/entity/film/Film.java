package com.cinema.model.entity.film;

import java.io.File;
import java.math.BigDecimal;

public class Film {

    private int id;
    private String nameEN;
    private String nameUA;
    private int duration;
    private BigDecimal price;
    private String genreEN;
    private String genreUA;
    private File poster;

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

    public String getGenreEN() {
        return genreEN;
    }

    public void setGenreEN(String genreEN) {
        this.genreEN = genreEN;
    }

    public String getGenreUA() {
        return genreUA;
    }

    public void setGenreUA(String genreUA) {
        this.genreUA = genreUA;
    }

    public File getPoster() {
        return poster;
    }

    public void setPoster(File poster) {
        this.poster = poster;
    }

}
