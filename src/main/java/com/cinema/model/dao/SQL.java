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

    public static final String SELECT_FILM_SESSIONS = "select * from session;";

    public static final String SELECT_ROLE = "select role from role where id = ?;";

    public static final String INSERT_GENRE = "insert into genre (genre_en, genre_ua) values (?, ?);";

    public static final String SELECT_TYPE = "select * from type where id = ?;";

    public static final String SELECT_PLACE = "select * from place where id = ?;";

    public static final String SELECT_PLACES = "select * from place";

    public static final String SET_FILM_SESSION_STATUS = "update session set status_id = ? where id = ?;";

    public static final String INSERT_SESSION_HAS_PLACE = "insert into session_has_place (session_id, place_id) " +
            "values (?, ?);";

    public static final String SELECT_AMOUNT_UNAVAILABLE_PLACES = "select count(id) as amount from session_has_place where session_id = ? AND available = 1;";

    public static final String SELECT_SESSION_HAS_PLACE_BY_ID = "select * from session_has_place where id = ?;";

    public static final String SET_PLACE_AVAILABLE = "update session_has_place set available = ? where id = ?;";


    public static final String SELECT_SESSION_PLACES = "select * from session_has_place where session_id = ?;";

    public static final String INSERT_TICKET = "insert into ticket values (?, ?, ?);";

    public static final String SELECT_USER_TICKETS = "select * from ticket where account_id = ?;";

    public static final String DELETE_ROLES = "delete from role;";

    public static final String INSERT_ROLES = "insert into role (id, role) values (?, ?);";

    public static final String UPDATE_SESSION_PLACES = "";

}
