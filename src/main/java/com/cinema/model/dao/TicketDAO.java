package com.cinema.model.dao;

import com.cinema.model.entity.filmSession.FilmSession;
import com.cinema.model.entity.filmSession.SessionHasPlace;
import com.cinema.model.entity.ticket.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.*;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class TicketDAO {

    public void insertTicket(Ticket ticket) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(INSERT_TICKET);
            pStatement.setInt(1, ticket.getUserId());
            pStatement.setInt(2, ticket.getSessionHasPlaceId());
            pStatement.setBigDecimal(3, ticket.getPrice());
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    public List<Ticket> getUserTickets(int userId, int offset, int limit) {
        List<Ticket> ticketList = new LinkedList<>();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_USER_TICKETS_LIMIT);
            pStatement.setInt(1, userId);
            pStatement.setInt(2, offset);
            pStatement.setInt(3, limit);
            resultSet = pStatement.executeQuery();
            while (resultSet.next()) {
                Ticket ticket = new Ticket();
                ticket.setUserId(resultSet.getInt("account_id"));
                ticket.setSessionHasPlaceId(resultSet.getInt("session_has_place_id"));
                ticket.setPrice(resultSet.getBigDecimal("price"));
                additionalInformation(ticket);
                ticketList.add(ticket);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return ticketList;
    }

    public int amountTickets(int userId) {
        int amount = 0;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_AMOUNT_TICKETS);
            pStatement.setInt(1, userId);
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

    private void additionalInformation(Ticket ticket) {
        SessionHasPlace shp = new SessionHasPlaceDAO().getSessionHasPlace(ticket.getSessionHasPlaceId());
        FilmSession filmSession = new FilmSessionDAO().getFilmSession(shp.getSessionId());
        ticket.setFilmName(filmSession.getFilm().getNameEN());
        ticket.setGenre(filmSession.getFilm().getGenre().getGenreEN());
        ticket.setDate(filmSession.getDate());
        ticket.setTime(filmSession.getTime());
        ticket.setDuration(filmSession.getFilm().getDuration());
        ticket.setPlace(shp.getPlace().getNumber());
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
