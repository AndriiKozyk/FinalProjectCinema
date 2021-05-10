package com.cinema.model.dao;

import com.cinema.model.entity.filmToOrder.FilmStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.cinema.model.DBManager.getInstance;
import static com.cinema.model.dao.SQL.SELECT_FILM_STATUS;

public class FilmStatusDAO {

    public FilmStatus getStatus(int statusId) {
        FilmStatus status = null;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_FILM_STATUS);
            pStatement.setInt(1, statusId);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                switch (resultSet.getString(1)) {
                    case "suggestion":
                        status = FilmStatus.SUGGESTION;
                        break;
                    case "voting":
                        status = FilmStatus.VOTING;
                        break;
                    case "approved":
                        status = FilmStatus.APPROVED;
                        break;
                    case "rejected":
                        status = FilmStatus.REJECTED;
                        break;
                    default:
                        throw new SQLException("Status not found!");
                }
            } else {
                throw new SQLException("Status not found!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return status;
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
