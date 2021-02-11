package com.cinema.model.dao;

import com.cinema.model.entity.film.Film;
import com.cinema.model.entity.film.FilmNotFoundException;
import com.cinema.model.entity.film.Genre;
import com.cinema.model.entity.film.GenreNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.*;
import java.util.*;

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
            Blob poster = null;
            try {
                BufferedImage img = ImageIO.read(film.getPoster());
                poster = connection.createBlob();
                try (OutputStream outputStream = poster.setBinaryStream(1)) {
                    ImageIO.write(img, "jpg", outputStream);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            pStatement.setBlob(6, poster);
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
                film.setId(resultSet.getInt("id"));
                film.setNameEN(resultSet.getString("name_en"));
                film.setNameUA(resultSet.getString("name_ua"));
                film.setDuration(resultSet.getInt("duration"));
                film.setPrice(resultSet.getBigDecimal("price"));
                Genre genre = new Genre();
                genre.setId(resultSet.getInt("genre_id"));
                updateGenre(genre);
                film.setGenre(genre);
                Blob blob = resultSet.getBlob("poster");
                BufferedImage img = ImageIO.read(blob.getBinaryStream());
                File file = new File("poster.img");
                ImageIO.write(img, "jpg", file);
                film.setPoster(file);
            } else {
                throw new FilmNotFoundException();
            }
        } catch (SQLException | FilmNotFoundException | IOException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }

        return film;
    }

    private void updateGenre(Genre genre) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_GENRE);
            pStatement.setInt(1, genre.getId());
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
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
    }

    public List<Film> selectFilms(String orderBy, int limit, int offset) {
        List<Film> filmList = new LinkedList<>();

        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_FILMS);
            pStatement.setString(1, orderBy);
            pStatement.setInt(2, offset);
            pStatement.setInt(3, limit);
            resultSet = pStatement.executeQuery();
            insertFilmsToList(filmList, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }

        return filmList;
    }

    public List<Film> selectFilmsByGenre(int genreId, String orderBy, int limit, int offset) {
        List<Film> filmList = new LinkedList<>();

        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_FILMS_BY_GENRE);
            pStatement.setInt(1, genreId);
            pStatement.setString(2, orderBy);
            pStatement.setInt(3, offset);
            pStatement.setInt(4, limit);
            resultSet = pStatement.executeQuery();
            insertFilmsToList(filmList, resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }

        return filmList;
    }

    private void insertFilmsToList(List<Film> filmList, ResultSet resultSet) throws SQLException, IOException {
        while (resultSet.next()) {
            Film film = new Film();
            film.setId(resultSet.getInt("id"));
            film.setNameEN(resultSet.getString("name_en"));
            film.setNameUA(resultSet.getString("name_ua"));
            film.setDuration(resultSet.getInt("duration"));
            film.setPrice(resultSet.getBigDecimal("price"));
            Genre genre = new Genre();
            genre.setId(resultSet.getInt("genre_id"));
            updateGenre(genre);
            film.setGenre(genre);
            Blob blob = resultSet.getBlob("poster");
            BufferedImage img = ImageIO.read(blob.getBinaryStream());
            File file = new File("poster.img");
            ImageIO.write(img, "jpg", file);
            film.setPoster(file);
            filmList.add(film);
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
