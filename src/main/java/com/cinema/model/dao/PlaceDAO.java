package com.cinema.model.dao;

import com.cinema.model.entity.place.Place;
import com.cinema.model.entity.place.PlaceNotFoundException;
import com.cinema.model.entity.place.Type;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class PlaceDAO {

    public Place getPlace(int id) {
        Place place = new Place();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_PLACE);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                mapPlace(place, resultSet);
            } else {
                throw new PlaceNotFoundException();
            }
        } catch (SQLException | PlaceNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return place;
    }

    private void mapPlace(Place place, ResultSet resultSet) throws SQLException {
        place.setNumber(resultSet.getInt("number"));
        place.setRow(resultSet.getInt("row"));
        Type type = new TypeDAO().getType(resultSet.getInt("type_id"));
        place.setType(type);
    }

    public List<Place> selectPlaces() {
        List<Place> placeList = new LinkedList<>();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_PLACES);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                Place place = new Place();
                mapPlace(place, resultSet);
                placeList.add(place);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return placeList;
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
