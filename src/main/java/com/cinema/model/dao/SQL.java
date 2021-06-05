package com.cinema.model.dao;

public final class SQL {

    private SQL() {}

    public static final String INSERT_USER_DETAILS = "insert into " +
            "account_details (first_name_en, first_name_ua, last_name_en, last_name_ua, email, phone) " +
            "values (?, 'a', ?, 'a', ?, ?);";

    public static final String INSERT_USER = "insert into " +
            "account (login, password, role_id, account_details_id) " +
            "values (?, ?, (select id from role where role = ?), ?);";

    public static final String SELECT_USER = "select * from account where login = ?;";

    public static final String SELECT_USER_DETAILS = "select * from account_details where id = ?;";

    public static final String INSERT_FILM = "insert into " +
            "film (name_en, name_ua, duration, price, genre_id, poster, trailer) " +
            "values (?, 'a', ?, ?, ?, ?, ?)";

    public static final String SELECT_FILM_BY_ID = "select * from film where id = ?;";

    public static final String SELECT_GENRE = "select * from genre where id = ?;";

    public static final String SELECT_FILMS = "select * from film";

    public static final String INSERT_SESSION = "insert into session (date, time, min_price, max_price, film_id, status_id) " +
            "values (?, ?, ?, ?, ?, ?);";

    public static final String SELECT_FILM_SESSION_BY_ID = "select * from session where id = ?;";

    public static final String SELECT_STATUS_BY_ID = "select * from status where id = ?;";

    public static final String SELECT_FILM_SESSIONS = "select * from session;";

    public static final String SELECT_ROLE = "select role from role where id = ?;";

    public static final String INSERT_GENRE = "insert into genre (genre_en, genre_ua) values (?, 'a');";

    public static final String SELECT_TYPE = "select * from type where id = ?;";

    public static final String SELECT_PLACE = "select * from place where id = ?;";

    public static final String SELECT_PLACES = "select * from place";

    public static final String SET_FILM_SESSION_STATUS = "update session set status_id = ? where id = ?;";

    public static final String INSERT_SESSION_HAS_PLACE = "insert into session_has_place (session_id, place_id) " +
            "values (?, ?);";

    public static final String SELECT_AMOUNT_AVAILABLE_PLACES = "select count(id) as amount from session_has_place where session_id = ? AND available = 1;";

    public static final String SELECT_SESSION_HAS_PLACE_BY_ID = "select * from session_has_place where id = ?;";

    public static final String SET_PLACE_AVAILABLE = "update session_has_place set available = ? where id = ?;";

    public static final String SELECT_SESSION_PLACES = "select * from session_has_place where session_id = ?;";

    public static final String INSERT_TICKET = "insert into ticket values (?, ?, ?);";

    public static final String SELECT_USER_TICKETS = "select * from ticket where account_id = ?;";

    public static final String SELECT_SHP_ID_BY_SESSION_ID_AND_PLACE_ID = "select id from session_has_place where session_id = ? and place_id = ?;";

    public static final String SELECT_USER_BY_ID = "select * from account where id = ?;";

    public static final String SELECT_FILM_SESSION_BY_FILM = "select * from session where film_id = ?;";

    public static final String SELECT_FILM_SESSION_BY_FILM_LIMIT = "select * from session where film_id = ? limit ?;";

    public static final String SELECT_AMOUNT_OF_FILM_SESSIONS = "select count(id) as count from session where film_id = ? " +
            "and status_id = (select id from status where status_en = \"Available\");";

    public static final String SELECT_GENRES = "select * from genre";

    public static final String SELECT_MIN_PLACE_PRICE = "select min(price) from type;";

    public static final String SELECT_MAX_PLACE_PRICE = "select max(price) from type;";

    public static final String IS_PLACE_AVAILABLE = "select available from session_has_place where id = ?;";

    public static final String INSERT_BOOKED_TIME = "update session_has_place set book_time = ? where id = ?;";

    public static final String SET_TIME_NULL = "update session_has_place set book_time = null where id = ?;";

    public static final String SELECT_BOOKED_TIME = "select book_time from session_has_place where id = ?;";

    public static final String SELECT_USER_TICKETS_LIMIT = "select * from ticket where account_id = ? limit ?, ?;";

    public static final String SELECT_AMOUNT_TICKETS = "select count(account_id) as count from ticket where account_id = ?;";

    public static final String SELECT_AMOUNT_FILMS = "select count(id) as count from film;";

    public static final String SELECT_FILMS_LIMIT = "select * from film limit ?, ?;";

    public static final String SELECT_AVAILABLE_FILM_SESSION_BY_FILM = "select * from session where film_id = ? and " +
            "status_id = (select id from status where status_en = \"Available\");";

    public static final String SELECT_FILMS_BY_GENRE = "select * from film where genre_id = " +
            "(select id from genre where genre_en = ?);";

    public static final String SELECT_AVAILABLE_FILM_SESSION_BY_FILM_LIMIT = "select * from session where film_id = ? and " +
            "status_id = (select id from status where status_en = \"Available\") limit ?;";

    public static final String GET_FILM_TO_ORDER = "select * from film_to_order where id = ?;";

    public static final String GET_ALL_FILM_TO_ORDER_BY_STATUS = "select * from film_to_order where film_status_id = " +
            "(select id from film_status where status_en = ?);";

    public static final String GET_ALL_FILM_TO_ORDER_BY_USER_VOTE = "select * from film_to_order where id " +
            "in (select order_film_id from user_vote where account_id = ?);";

    public static final String GET_ALL_FILM_TO_ORDER_BY_USER_SUGGESTION = "select * from film_to_order where account_id = ?;";

    public static final String INSERT_FILM_TO_ORDER = "insert into film_to_order (name_en, name_ua, year, description, film_status_id, account_id) " +
            "values (?, 'a', ?, ?, (select id from film_status where status_en = 'suggestion'), ?);";

    public static final String INSERT_USER_VOTE = "insert into user_vote (account_id, order_film_id) " +
            "values (?, ?);";

    public static final String UPDATE_FILM_TO_ORDER_TO_VOTING = "update film_to_order set name_en = ?, name_ua = 'a'," +
            "poster = ?, required_vote = ?, film_status_id = (select id from film_status where status_en = 'voting'), trailer = ? where id = ?;";

    public static final String UPDATE_FILM_TO_ORDER_STATUS = "update film_to_order set " +
            "film_status_id = (select id from film_status where status_en = ?) where id = ?;";

    public static final String UPDATE_FILM_TO_ORDER_VOTE = "update film_to_order set vote = " +
            "(select count(account_id) from user_vote where order_film_id = ?) where id = ?;";

    public static final String SELECT_FILM_STATUS = "select status_en from film_status where id = ?;";

    public static final String USER_HAS_VOTE = "select * from user_vote where account_id = ? and order_film_id = ?;";

    public static final String USER_SUGGESTIONS_COUNT = "select count(id) as count from film_to_order " +
            "where film_status_id = (select id from film_status where status_en = 'suggestion');";

    public static final String FILMS_READY_TO_ROLLING_COUNT = "select count(id) as count from film_to_order " +
            "where film_status_id = (select id from film_status where status_en = 'voting') and vote >= required_vote;";

    public static final String GET_ALL_FILM_TO_ORDER_BY_STATUS_READY = "select * from film_to_order where film_status_id = " +
            "(select id from film_status where status_en = 'voting') and vote >= required_vote;";

    public static final String SELECT_STATUS_BY_FILM = "select status_en from film_status " +
            "where id = (select film_status_id from film_to_order where id = ?);";

}
