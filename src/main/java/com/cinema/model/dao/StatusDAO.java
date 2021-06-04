package com.cinema.model.dao;

import com.cinema.model.entity.filmSession.FilmSessionStatus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class StatusDAO {

    public FilmSessionStatus getStatus(int id) {
        FilmSessionStatus status = null;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_STATUS_BY_ID);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                switch (resultSet.getString("status_en")) {
                    case "Available":
                        status = FilmSessionStatus.AVAILABLE;
                        break;
                    case "No places":
                        status = FilmSessionStatus.NO_PLACES;
                        break;
                    case "Canceled":
                        status = FilmSessionStatus.CANCELED;
                        break;
                    default:
                        throw new SQLException("Status not found!");
                }
                status.setId(id);
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
