package com.cinema.model.dao;

import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.film.Genre;
import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.filmSession.SessionNotFoundException;
import com.cinema.model.entity.filmSession.Status;

import java.io.File;
import java.math.BigDecimal;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class FilmSessionDAO {

    public void insertFilmSession(FilmSession filmSession) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(INSERT_SESSION);
            pStatement.setDate(1, new Date(filmSession.getDate().getTime()));
            pStatement.setTime(2, new Time(filmSession.getTime().getTime()));
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

    public FilmSession getFilmSession(int id) {
        FilmSession filmSession = new FilmSession();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_FILM_SESSION_BY_ID);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                mapFilmSession(filmSession, resultSet);
            } else {
                throw new SessionNotFoundException();
            }
        } catch (SQLException | SessionNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return filmSession;
    }

    private void mapFilmSession(FilmSession filmSession, ResultSet resultSet) throws SQLException {
        filmSession.setId(resultSet.getInt("id"));
        filmSession.setDate(resultSet.getDate("date"));
        filmSession.setTime(resultSet.getTime("time"));
        filmSession.setMinPrice(resultSet.getBigDecimal("min_price"));
        filmSession.setMaxPrice(resultSet.getBigDecimal("max_price"));
        Film film = new FilmDAO().selectFilm(resultSet.getInt("film_id"));
        filmSession.setFilm(film);
        Status status = new StatusDAO().getStatus(resultSet.getInt("status_id"));
        filmSession.setStatus(status);
    }

    public List<FilmSession> selectFilmSessions() {
        List<FilmSession> filmList = new LinkedList<>();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_FILM_SESSIONS);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                FilmSession filmSession = new FilmSession();
                mapFilmSession(filmSession, resultSet);
                filmList.add(filmSession);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return filmList;
    }

    public void setStatus(FilmSession filmSession) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SET_FILM_SESSION_STATUS);
            pStatement.setInt(1, filmSession.getStatus().getId());
            pStatement.setInt(2, filmSession.getId());
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

        java.util.Date date = new java.util.Date();
        java.util.Date time = new java.util.Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        try {
            date = dateFormat.parse("2021-02-20");
            time = timeFormat.parse("17:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        session.setDate(date);
        session.setTime(time);
        session.setMinPrice(new BigDecimal(170));
        session.setMaxPrice(new BigDecimal(230));
//        Film film = new Film();
//        Genre genre = new Genre();
//        genre.setId(1);
//        film.setGenre(genre);
//        film.setPoster(new File("images/Sherlock_Holmes2.jpg"));
//        film.setPrice(new BigDecimal(100));
//        film.setDuration(120);
//        film.setNameUA("Тест");
//        film.setNameEN("Test");
//        new FilmDAO().insertFilm(film);
        session.setFilm(new FilmDAO().selectFilm(7));
        Status status = Status.NO_PLACES;
        status.setId(2);
        session.setStatus(status);
        new FilmSessionDAO().insertFilmSession(session);
    }

    public static void main2(String[] args) {
        FilmSession filmSession = new FilmSessionDAO().getFilmSession(1);
        System.out.println(filmSession);
    }

}
