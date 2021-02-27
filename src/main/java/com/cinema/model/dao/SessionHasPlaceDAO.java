package com.cinema.model.dao;

import com.cinema.model.entity.filmSession.SessionHasPlace;
import com.cinema.model.entity.place.Place;

import java.sql.*;

import java.util.*;
import java.util.Date;

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

    public void insertBookTime(long time, int shpId) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(INSERT_BOOKED_TIME);
            pStatement.setTimestamp(1, new Timestamp(time));
            pStatement.setInt(2, shpId);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    public void setBookTimeNull(int shpId) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SET_TIME_NULL);
            pStatement.setInt(1, shpId);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
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

    public boolean isPlaceAvailable(int sessionId) {
        boolean available = false;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(IS_PLACE_AVAILABLE);
            pStatement.setInt(1, sessionId);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                available = resultSet.getBoolean("available");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return available;
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
        Timestamp bookTime = resultSet.getTimestamp("book_time");
        if (bookTime != null) {
            long time = System.currentTimeMillis() - bookTime.getTime();
            long oneMin = 60_000;
            if (time > oneMin) {
                setAvailable(shp.getId(), true);
                setBookTimeNull(shp.getId());
            }
        }
    }

    public boolean isTimeOut(int shpId) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_BOOKED_TIME);
            pStatement.setInt(1, shpId);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                if (resultSet.getTimestamp("book_time") == null) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return false;
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

}
