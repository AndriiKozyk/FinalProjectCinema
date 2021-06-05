package com.cinema.model.entity.film;

import com.cinema.model.dao.FilmSessionDAO;
import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.filmSession.FilmSessionStatus;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

public class Film implements Comparable<Film> {

    private int id;
    private String nameEN;
    private int duration;
    private BigDecimal price;
    private Genre genre;
    private String trailer;

    private InputStream posterInput;
    private String posterOut;

    private int additionalSession;

    public Film() {
        price = new BigDecimal(0);
        genre = new Genre();
    }

    public static boolean haveAvailableSession(int filmId) {
        List<FilmSession> sessions = new FilmSessionDAO().selectFilmSessions(filmId);
        for (FilmSession session : sessions) {
            if (FilmSessionStatus.AVAILABLE.equals(session.getStatus())) {
                return true;
            }
        }
        return false;
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

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    @Override
    public String toString() {
        return "Film{" +
                "id=" + id +
                ", nameEN='" + nameEN + '\'' +
                ", duration=" + duration +
                ", price=" + price +
                ", genre=" + genre +
                '}';
    }

    @Override
    public int compareTo(Film film) {
        return this.getNameEN().compareTo(film.getNameEN());
    }

}
