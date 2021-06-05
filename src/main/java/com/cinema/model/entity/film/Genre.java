package com.cinema.model.entity.film;

public class Genre {

    private int id;
    private String genreEN;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreEN() {
        return genreEN;
    }

    public void setGenreEN(String genreEN) {
        this.genreEN = genreEN;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genreEN='" + genreEN + '\'' +
                '}';
    }
}
