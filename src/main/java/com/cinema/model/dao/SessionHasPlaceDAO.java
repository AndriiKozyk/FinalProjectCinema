package com.cinema.model.dao;

import com.cinema.model.entity.filmSession.SessionHasPlace;
import com.cinema.model.entity.place.Place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class SessionHasPlaceDAO {

    public void insertSessionHasPlace(SessionHasPlace shp) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(INSERT_SESSION_HAS_PLACE);
            pStatement.setInt(1, shp.getSessionId());
            pStatement.setInt(2, shp.getPlace().getId());
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    public void insertSessionHasPlace(List<SessionHasPlace> shpList) {
        for (SessionHasPlace shp : shpList) {
            insertSessionHasPlace(shp);
        }
    }

    public int selectAmountAvailablePlaces(int sessionId) {
        int amount = 0;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_AMOUNT_AVAILABLE_PLACES);
            pStatement.setInt(1, sessionId);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                amount = resultSet.getInt("amount");
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

    public SessionHasPlace getSessionHasPlace(int id) {
        SessionHasPlace shp = new SessionHasPlace();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_SESSION_HAS_PLACE_BY_ID);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                mapSessionHasPlace(shp, resultSet);
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
        return shp;
    }

    public void setAvailable(int shpId, boolean available) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SET_PLACE_AVAILABLE);
            pStatement.setBoolean(1, available);
            pStatement.setInt(2, shpId);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    public List<SessionHasPlace> getSessionPlaces(int sessionId) {
        List<SessionHasPlace> shpList = new LinkedList<>();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_SESSION_PLACES);
            pStatement.setInt(1, sessionId);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                SessionHasPlace shp = new SessionHasPlace();
                mapSessionHasPlace(shp, resultSet);
                shpList.add(shp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return shpList;
    }

    private void mapSessionHasPlace(SessionHasPlace shp, ResultSet resultSet) throws SQLException {
        shp.setId(resultSet.getInt("id"));
        shp.setSessionId(resultSet.getInt("session_id"));
        Place place = new PlaceDAO().getPlace(resultSet.getInt("place_id"));
        shp.setPlace(place);
        shp.setAvailable(resultSet.getBoolean("available"));
    }

    public int selectSHPIdBySessionAndPlaceId(int sessionId, int placeId) {
        int id = 0;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_SHP_ID_BY_SESSION_ID_AND_PLACE_ID);
            pStatement.setInt(1, sessionId);
            pStatement.setInt(2, placeId);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getInt(1);
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
        return id;
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
//        for (int i = 1; i < 51; ++i) {
//            SessionHasPlace shp = new SessionHasPlace();
//            shp.setSessionId(13);
//            shp.setPlace(new PlaceDAO().getPlace(i));
//            new SessionHasPlaceDAO().insertSessionHasPlace(shp);
//        }
    }

}
