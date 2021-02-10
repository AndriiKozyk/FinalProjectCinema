package com.cinema.model.dao;

import com.cinema.model.entity.user.IncorrectRoleException;
import com.cinema.model.entity.user.Role;
import com.cinema.model.entity.user.User;
import com.cinema.model.entity.user.UserNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class UserDAO {

    public void insertUser(User user) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            insertUserDetails(user);
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);
            pStatement = connection.prepareStatement(INSERT_USER);
            pStatement.setString(1, user.getLogin());
            pStatement.setString(2, user.getPassword());
            int roleId;
            if (Role.ADMIN.equals(user.getRole())) {
                roleId = 1;
            } else if (Role.USER.equals(user.getRole())) {
                roleId = 2;
            } else {
                throw new IncorrectRoleException();
            }
            pStatement.setInt(3, roleId);
            pStatement.setString(4, user.getEmail());
            pStatement.executeUpdate();
            connection.commit();
        } catch (SQLException | IncorrectRoleException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                deleteUserDetails(user.getPhone());
            }
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    private void insertUserDetails(User user) throws SQLException {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);
            pStatement = connection.prepareStatement(INSERT_USER_DETAILS);
            pStatement.setString(1, user.getFirstNameEN());
            pStatement.setString(2, user.getFirstNameUA());
            pStatement.setString(3, user.getLastNameEN());
            pStatement.setString(4, user.getLastNameUA());
            pStatement.setString(5, user.getEmail());
            pStatement.setString(6, user.getPhone());
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
            throw e;
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    private void deleteUserDetails(String phone) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(DELETE_USER_DETAILS);
            pStatement.setString(1, phone);
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(pStatement);
            close(connection);
        }
    }

    private User getUser(String login) {
        User user = new User();

        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            int detailsId = 0;
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_USER);
            pStatement.setString(1, login);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                int role = resultSet.getInt("role_id");
                if (role == 1) {
                    user.setRole(Role.ADMIN);
                } else if (role == 2) {
                    user.setRole(Role.USER);
                } else {
                    throw new IncorrectRoleException();
                }
                detailsId = resultSet.getInt("account_details_id");
                updateUserDetails(user, detailsId);
            } else {
                throw new UserNotFoundException();
            }

        } catch (SQLException | IncorrectRoleException | UserNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }

        return user;
    }

    private void updateUserDetails(User user, int detailsId) throws SQLException, UserNotFoundException {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_USER_DETAILS);
            pStatement.setInt(1, detailsId);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                user.setFirstNameEN(resultSet.getString("first_name_en"));
                user.setFirstNameUA(resultSet.getString("first_name_ua"));
                user.setLastNameEN(resultSet.getString("last_name_en"));
                user.setLastNameUA(resultSet.getString("last_name_ua"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
            } else {
                throw new UserNotFoundException();
            }
        } finally {
            close(resultSet);
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
