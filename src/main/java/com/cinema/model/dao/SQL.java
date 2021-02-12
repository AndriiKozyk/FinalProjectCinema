package com.cinema.model.dao;

public final class SQL {

    private SQL() {}

    public static final String INSERT_USER_DETAILS = "insert into " +
            "account_details (first_name_en, first_name_ua, last_name_en, last_name_ua, email, phone) " +
            "values (?, ?, ?, ?, ?, ?);";

    public static final String INSERT_USER = "insert into " +
            "account (login, password, role_id, account_details_id) " +
            "values (?, ?, ?, ?);";

    public static final String SELECT_USER = "select * from account where login = ?;";

    public static final String SELECT_USER_DETAILS = "select * from account_details where id = ?;";

    public static final String INSERT_FILM = "insert into " +
            "film (name_en, name_ua, duration, price, genre_id, poster) " +
            "values (?, ?, ?, ?, ?, ?)";

    public static final String SELECT_FILM_BY_ID = "select * from film where id = ?;";

    public static final String SELECT_GENRE = "select * from genre where id = ?;";

    public static final String SELECT_FILMS = "select * from film";

    public static final String INSERT_SESSION = "insert into session (date, time, min_price, max_price, film_id, status_id) " +
            "values (?, ?, ?, ?, ?, ?);";

    public static final String SELECT_FILM_SESSION_BY_ID = "select * from session where id = ?;";

    public static final String SELECT_STATUS_BY_ID = "select * from status where id = ?;";

}
