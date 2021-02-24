package com.cinema.model.dao;

import com.cinema.model.entity.place.Type;
import com.cinema.model.entity.place.TypeNotFoundException;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class TypeDAO {

    public Type getType(int id) {
        Type type = null;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_TYPE);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                switch (resultSet.getString("type_en")) {
                    case "Standard":
                        type = Type.STANDARD;
                        break;
                    case "Lux":
                        type = Type.LUX;
                        break;
                    case "Super lux":
                        type = Type.SUPER_LUX;
                        break;
                    default:
                        throw new TypeNotFoundException();
                }
                type.setPrice(resultSet.getBigDecimal("price"));
            } else {
                throw new TypeNotFoundException();
            }
        } catch (SQLException | TypeNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return type;
    }

    public BigDecimal getPrice(boolean max) {
        BigDecimal price = null;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            if (max) {
                pStatement = connection.prepareStatement(SELECT_MAX_PLACE_PRICE);
            } else {
                pStatement = connection.prepareStatement(SELECT_MIN_PLACE_PRICE);
            }
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                price = resultSet.getBigDecimal(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return price;
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
