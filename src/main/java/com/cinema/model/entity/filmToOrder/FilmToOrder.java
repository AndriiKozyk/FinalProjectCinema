package com.cinema.model.entity.filmToOrder;

import java.io.IOException;
import java.io.InputStream;

public class FilmToOrder {

    private int id;
    private String nameEN;
    private String nameUA;
    private int year;

    private String description;

    private InputStream posterInput;
    private String posterOut;

    private int vote;
    private int requiredVote;

    private FilmStatus status;

    private String trailer;

    public FilmToOrder() {
        posterInput = new InputStream() {
            @Override
            public int read() {
                return 0;
            }
        };
        status = FilmStatus.DEFAULT;
    }

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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public int getRequiredVote() {
        return requiredVote;
    }

    public void setRequiredVote(int requiredVote) {
        this.requiredVote = requiredVote;
    }

    public FilmStatus getStatus() {
        return status;
    }

    public void setStatus(FilmStatus status) {
        this.status = status;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }
}
