package com.cinema.model.dao;

import com.cinema.model.entity.film.Genre;
import com.cinema.model.entity.film.GenreNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class GenreDAO {

    public Genre getGenre(int id) {
        Genre genre = new Genre();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_GENRE);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                genre.setId(resultSet.getInt("id"));
                genre.setGenreEN(resultSet.getString("genre_en"));
                genre.setGenreUA(resultSet.getString("genre_ua"));
            } else {
                throw new GenreNotFoundException();
            }
        } catch (SQLException | GenreNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return genre;
    }

    public void insertGenre(Genre genre) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(INSERT_GENRE);
            pStatement.setString(1, genre.getGenreEN());
            pStatement.setString(2, genre.getGenreUA());
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

}
