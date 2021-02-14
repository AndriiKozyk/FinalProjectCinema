package com.cinema.model.dao;

import com.cinema.model.entity.user.IncorrectRoleException;
import com.cinema.model.entity.user.Role;
import com.cinema.model.entity.user.RoleNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.cinema.model.DBManager.*;
import static com.cinema.model.dao.SQL.*;

public class RoleDAO {

    public Role getRole(int id) {
        Role role = null;
        Connection connection = null;
        PreparedStatement pStatement = null;
        ResultSet resultSet = null;
        try {
            connection = getInstance().getConnection();
            pStatement = connection.prepareStatement(SELECT_ROLE);
            pStatement.setInt(1, id);
            resultSet = pStatement.executeQuery();
            if (resultSet.next()) {
                switch (resultSet.getString("role")) {
                    case "admin":
                        role = Role.ADMIN;
                        break;
                    case "user":
                        role = Role.USER;
                        break;
                    default:
                        throw new IncorrectRoleException();
                }
                role.setId(id);
            } else {
                throw new RoleNotFoundException();
            }
        } catch (SQLException | RoleNotFoundException | IncorrectRoleException e) {
            e.printStackTrace();
        } finally {
            close(resultSet);
            close(pStatement);
            close(connection);
        }
        return role;
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
