package com.cinema.model;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class DBManager {

    private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/cinema?allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static BasicDataSource dataSource = new BasicDataSource();

    public static DBManager dbManager;

    private DBManager() {
        dataSource.setUrl(CONNECTION_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
    }

    public static synchronized DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(getInstance().getConnection());
    }

}
