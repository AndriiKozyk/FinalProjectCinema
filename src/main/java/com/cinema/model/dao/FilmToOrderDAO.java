package com.cinema.model.dao;

import com.cinema.model.entity.filmToOrder.FilmStatus;
import com.cinema.model.entity.filmToOrder.FilmToOrder;

import java.io.IOException;
import java.sql.*;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;

import static com.cinema.model.DBManager.getInstance;
import static com.cinema.model.dao.SQL.*;

public class FilmToOrderDAO {

    public void insertOrderFilm(FilmToOrder film, int userId) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);
            pStatement = connection.prepareStatement(INSERT_FILM_TO_ORDER, Statement.RETURN_GENERATED_KEYS);
            pStatement.setString(1, film.getNameEN());
            pStatement.setString(2, film.getNameUA());
            pStatement.setInt(3, film.getYear());
            pStatement.setString(4, film.getDescription());
            pStatement.executeUpdate();
            resultSet = pStatement.getGeneratedKeys();

            pStatement = connection.prepareStatement(INSERT_USER_SUGGESTION);
            pStatement.setInt(1, userId);
            if (resultSet.next()) {
                pStatement.setInt(2, resultSet.getInt(1));
            } else {
                throw new SQLException();
            }
            pStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
    }

    public FilmToOrder getOrderFilm(int id) {
        FilmToOrder film = new FilmToOrder();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(GET_FILM_TO_ORDER);
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

    public List<FilmToOrder> selectOrderFilmsByStatus(String status) {
        List<FilmToOrder> filmList = new LinkedList<>();

        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(GET_ALL_FILM_TO_ORDER_BY_STATUS);
            pStatement.setString(1, status);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                FilmToOrder film = new FilmToOrder();
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

    public List<FilmToOrder> selectOrderFilmsByUserVote(int userId) {
        return selectOrderFilmsByUser(GET_ALL_FILM_TO_ORDER_BY_USER_VOTE, userId);
    }

    public List<FilmToOrder> selectOrderFilmsByUserSuggestion(int userId) {
        return selectOrderFilmsByUser(GET_ALL_FILM_TO_ORDER_BY_USER_SUGGESTION, userId);
    }

    private List<FilmToOrder> selectOrderFilmsByUser(String query, int userId) {
        List<FilmToOrder> filmList = new LinkedList<>();

        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(query);
            pStatement.setInt(1, userId);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                FilmToOrder film = new FilmToOrder();
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

    public void insertUserVote(int userId, int filmId) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(INSERT_USER_VOTE);
            pStatement.setInt(1, userId);
            pStatement.setInt(2, filmId);
            pStatement.executeUpdate();

            pStatement = connection.prepareStatement(UPDATE_FILM_TO_ORDER_VOTE);
            pStatement.setInt(1, filmId);
            pStatement.setInt(2, filmId);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    public void updateOrderFilm(FilmToOrder film) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(UPDATE_FILM_TO_ORDER_TO_VOTING);
            pStatement.setString(1, film.getNameEN());
            pStatement.setString(2, film.getNameUA());
            pStatement.setBlob(3, film.getPosterInput());
            pStatement.setInt(4, film.getRequiredVote());
            pStatement.setInt(5, film.getId());
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    public void updateOrderFilmStatus(int filmId, String status) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(UPDATE_FILM_TO_ORDER_STATUS);
            pStatement.setString(1, status);
            pStatement.setInt(2, filmId);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    private void mapFilm(FilmToOrder film, ResultSet resultSet) throws SQLException, IOException {
        film.setId(resultSet.getInt("id"));
        film.setNameEN(resultSet.getString("name_en"));
        film.setNameUA(resultSet.getString("name_ua"));
        film.setYear(resultSet.getInt("year"));
        film.setDescription(resultSet.getString("description"));
        film.setVote(resultSet.getInt("vote"));
        film.setRequiredVote(resultSet.getInt("required_vote"));
        int statusId = resultSet.getInt("film_status_id");
        FilmStatus status = new FilmStatusDAO().getStatus(statusId);
        film.setStatus(status);
        Blob blob = resultSet.getBlob("poster");
        byte[] image = blob.getBytes(1, (int) blob.length());
        String encode = Base64.getEncoder().encodeToString(image);
        film.setPosterOut(encode);
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
