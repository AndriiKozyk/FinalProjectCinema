package com.cinema.model.dao;

import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.filmSession.SessionNotFoundException;
import com.cinema.model.entity.filmSession.Status;

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
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(INSERT_SESSION, Statement.RETURN_GENERATED_KEYS);
            pStatement.setDate(1, new Date(filmSession.getDate().getTime()));
            pStatement.setTime(2, new Time(filmSession.getTime().getTime()));
            pStatement.setBigDecimal(3, filmSession.getMinPrice());
            pStatement.setBigDecimal(4, filmSession.getMaxPrice());
            pStatement.setInt(5, filmSession.getFilm().getId());
            pStatement.setInt(6, filmSession.getStatus().getId());
            pStatement.executeUpdate();
            resultSet = pStatement.getGeneratedKeys();
            if (resultSet.next()) {
                filmSession.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
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
        filmSession.setPlaceList(new SessionHasPlaceDAO().getSessionPlaces(filmSession.getId()));
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

    public List<FilmSession> selectFilmSessions(int filmId) {
        List<FilmSession> filmList = new LinkedList<>();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_FILM_SESSION_BY_FILM);
            pStatement.setInt(1, filmId);
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

    public List<FilmSession> selectFilmSessions(int filmId, int limit) {
        List<FilmSession> filmList = new LinkedList<>();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_FILM_SESSION_BY_FILM_LIMIT);
            pStatement.setInt(1, filmId);
            pStatement.setInt(2, limit);
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

    public int selectAmountFilmSessions(int filmId) {
        int amount = 0;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_AMOUNT_OF_FILM_SESSIONS);
            pStatement.setInt(1, filmId);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getInt("count");
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return amount;
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
            date = dateFormat.parse("2021-02-22");
            time = timeFormat.parse("18:00:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        session.setDate(date);
        session.setTime(time);
        session.setMinPrice(new BigDecimal(100));
        session.setMaxPrice(new BigDecimal(200));
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
        session.setFilm(new FilmDAO().selectFilm(4));
        Status status = Status.AVAILABLE;
        status.setId(1);
        session.setStatus(status);
        new FilmSessionDAO().insertFilmSession(session);
    }

    public static void main2(String[] args) {
        FilmSession filmSession = new FilmSessionDAO().getFilmSession(1);
        System.out.println(filmSession);
    }

}
