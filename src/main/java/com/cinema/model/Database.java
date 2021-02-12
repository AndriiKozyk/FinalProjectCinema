package com.cinema.model;

import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public final class Database {

    private static final String CONNECTION_URL = "jdbc:mysql://127.0.0.1:3306/cinema?useSSL=false";
    private static final String USER = "root";
    private static final String PASSWORD = "vemega44";

    private static final BasicDataSource dataSource = new BasicDataSource();

    private static Database database;

    private Database() {}

    static {
        dataSource.setUrl(CONNECTION_URL);
        dataSource.setUsername(USER);
        dataSource.setPassword(PASSWORD);
    }

    public static synchronized Database getInstance() {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static void main(String[] args) throws SQLException {
        System.out.println(getInstance().getConnection());
    }

}
