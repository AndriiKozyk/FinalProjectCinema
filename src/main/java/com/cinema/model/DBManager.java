package com.cinema.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/cinema?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "vemega44";

    private static DBManager dbManager;

    private DBManager() {}

    public static synchronized DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(CONNECTION_URL, USER, PASSWORD);
    }

    public static void main(String[] args) throws SQLException {
        getInstance().getConnection();
    }

}
