package com.cinema.model.dao;

import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.film.Genre;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.List;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class FilmDAO {

    public void insertFilm(Film film) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(INSERT_FILM);
            pStatement.setString(1, film.getNameEN());
            pStatement.setString(2, film.getNameUA());
            pStatement.setInt(3, film.getDuration());
            pStatement.setBigDecimal(4, film.getPrice());
            pStatement.setInt(5, film.getGenre().getId());
            pStatement.setBlob(6, film.getPosterInput());
            pStatement.setString(7, film.getTrailer());
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    public Film selectFilm(int id) {
        Film film = new Film();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_FILM_BY_ID);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                mapFilm(film, resultSet);
            } else {
                throw new SQLException("Film not found!");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return film;
    }

    private void mapFilm(Film film, ResultSet resultSet) throws SQLException, IOException {
        film.setId(resultSet.getInt("id"));
        film.setNameEN(resultSet.getString("name_en"));
        film.setNameUA(resultSet.getString("name_ua"));
        film.setDuration(resultSet.getInt("duration"));
        film.setPrice(resultSet.getBigDecimal("price"));
        film.setTrailer(resultSet.getString("trailer"));
        int genreId = resultSet.getInt("genre_id");
        Genre genre = new GenreDAO().getGenre(genreId);
        film.setGenre(genre);
        Blob blob = resultSet.getBlob("poster");
        byte[] image = blob.getBytes(1, (int) blob.length());
        String encode = Base64.getEncoder().encodeToString(image);
        film.setPosterOut(encode);
    }

    public List<Film> selectFilms() {
        List<Film> filmList = new LinkedList<>();

        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_FILMS);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
                mapFilm(film, resultSet);
                filmList.add(film);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }

        return filmList;
    }

    public List<Film> selectFilms(String genre) {
        List<Film> filmList = new LinkedList<>();

        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_FILMS_BY_GENRE);
            pStatement.setString(1, genre);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
                mapFilm(film, resultSet);
                filmList.add(film);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }

        return filmList;
    }

    public List<Film> selectFilms(int offset, int limit) {
        List<Film> filmList = new LinkedList<>();

        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_FILMS_LIMIT);
            pStatement.setInt(1, offset);
            pStatement.setInt(2, limit);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                Film film = new Film();
                mapFilm(film, resultSet);
                filmList.add(film);
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }

        return filmList;
    }

    public int amountFilm() {
        int amount = 0;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_AMOUNT_FILMS);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                amount = resultSet.getInt("count");
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

    private void close(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
