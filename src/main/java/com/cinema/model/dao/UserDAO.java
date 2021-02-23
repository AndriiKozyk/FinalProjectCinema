package com.cinema.model.dao;

import com.cinema.model.entity.user.*;

import java.sql.*;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class UserDAO {

    public void insertUser(User user) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            connection.setAutoCommit(false);
            pStatement = connection.prepareStatement(INSERT_USER_DETAILS, Statement.RETURN_GENERATED_KEYS);
            UserDetails details = user.getDetails();
            pStatement.setString(1, details.getFirstNameEN());
            pStatement.setString(2, details.getFirstNameUA());
            pStatement.setString(3, details.getLastNameEN());
            pStatement.setString(4, details.getLastNameUA());
            pStatement.setString(5, details.getEmail());
            pStatement.setString(6, details.getPhone());
            pStatement.executeUpdate();
            resultSet = pStatement.getGeneratedKeys();
            pStatement = connection.prepareStatement(INSERT_USER);
            pStatement.setString(1, user.getLogin());
            pStatement.setString(2, user.getPassword());
            pStatement.setInt(3, user.getRole().getId());
            if (resultSet.next()) {
                pStatement.setInt(4, resultSet.getInt(1));
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
            close(pStatement);
            close(connection);
        }
    }

    public User getUser(String login) throws UserNotFoundException {
        User user = new User();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_USER);
            pStatement.setString(1, login);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                mapUser(user, resultSet);
            } else {
                throw new UserNotFoundException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return user;
    }

    public User getUser(int id) {
        User user = new User();
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_USER_BY_ID);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                mapUser(user, resultSet);
            } else {
                throw new UserNotFoundException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return user;
    }

    private void mapUser(User user, ResultSet resultSet) throws SQLException {
        user.setId(resultSet.getInt("id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        int roleId = resultSet.getInt("role_id");
        Role role = new RoleDAO().getRole(roleId);
        user.setRole(role);
        user.setUserDetailsId(resultSet.getInt("account_details_id"));
    }

    public void getUserDetails(User user) {
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_USER_DETAILS);
            pStatement.setInt(1, user.getUserDetailsId());
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                UserDetails details = new UserDetails();
                details.setFirstNameEN(resultSet.getString("first_name_en"));
                details.setFirstNameUA(resultSet.getString("first_name_ua"));
                details.setLastNameEN(resultSet.getString("last_name_en"));
                details.setLastNameUA(resultSet.getString("last_name_ua"));
                details.setEmail(resultSet.getString("email"));
                details.setPhone(resultSet.getString("phone"));
                user.setDetails(details);
            } else {
                throw new UserNotFoundException();
            }
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
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

    public static void main(String[] args) {
        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        Role role = Role.USER;
        user.setRole(role);
        UserDetails userDetails = new UserDetails();
        userDetails.setFirstNameEN("User");
        userDetails.setFirstNameUA("Юзер");
        userDetails.setLastNameEN("Resu");
        userDetails.setLastNameUA("Резю");
        userDetails.setEmail("user@gmail.com");
        userDetails.setPhone("0123456789");
        user.setDetails(userDetails);
        new UserDAO().insertUser(user);
    }

}
