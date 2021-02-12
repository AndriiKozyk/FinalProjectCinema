package com.cinema.model.dao;

import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.film.Genre;
import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.filmSession.Status;

import java.io.File;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class FilmSessionDAO {

    public void insertFilmSession(FilmSession filmSession) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(INSERT_SESSION);
            java.util.Date dateTime = filmSession.getDate();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                dateTime = dateFormat.parse("2021-02-15 04:00:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            pStatement.setDate(1, new Date(dateTime.getTime()));
            pStatement.setTime(2, new Time(dateTime.getTime()));
            pStatement.setBigDecimal(3, filmSession.getMinPrice());
            pStatement.setBigDecimal(4, filmSession.getMaxPrice());
            pStatement.setInt(5, filmSession.getFilm().getId());
            pStatement.setInt(6, filmSession.getStatus().getId());
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    private void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        FilmSession session = new FilmSession();



        java.util.Date dateTime = new java.util.Date();
        Date date = new Date(dateTime.getTime());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateTime = dateFormat.parse("2021-02-15 04:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(dateTime);
        System.out.println(dateTime);
        session.setDate(dateTime);
        session.setMinPrice(new BigDecimal(150));
        session.setMaxPrice(new BigDecimal(200));
        Film film = new Film();
        Genre genre = new Genre();
        genre.setId(1);
        film.setGenre(genre);
        film.setPoster(new File("testimg/Sherlock_Holmes2.jpg"));
        film.setPrice(new BigDecimal(100));
        film.setDuration(120);
        film.setNameUA("Тест");
        film.setNameEN("Test");
        new FilmDAO().insertFilm(film);
        session.setFilm(new FilmDAO().selectFilm(4));
        Status status = Status.AVAILABLE;
        status.setId(1);
        session.setStatus(status);
        new FilmSessionDAO().insertFilmSession(session);
    }

}
