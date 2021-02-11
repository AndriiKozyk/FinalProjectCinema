package com.cinema.model.dao;

public final class SQL {

    private SQL() {}

    public static final String SELECT_ID_FROM_USER_DETAILS = "select id from account_details where email = ?";

    public static final String INSERT_USER_DETAILS = "insert into " +
            "account_details (first_name_en, first_name_ua, last_name_en, last_name_ua, email, phone) " +
            "values (?, ?, ?, ?, ?, ?);";

    public static final String INSERT_USER = "insert into " +
            "account (login, password, role_id, account_details_id) " +
            "values (?, ?, ?, (" + SELECT_ID_FROM_USER_DETAILS + "));";

    public static final String DELETE_USER_DETAILS = "delete from account_details where phone = ?;";

    public static final String SELECT_USER = "select * from account where login = ?;";

    public static final String SELECT_USER_DETAILS = "select * from account_details where id = ?;";

    public static final String INSERT_FILM = "insert into " +
            "film (name_en, name_ua, duration, price, genre_id, poster) " +
            "values (?, ?, ?, ?, ?, ?)";

    public static final String SELECT_FILM_BY_ID = "select * from film where id = ?;";

    public static final String SELECT_GENRE = "select * from genre where id = ?;";

    public static final String SELECT_FILMS = "select * from film order by ? limit ?, ?;";

    public static final String SELECT_FILMS_BY_GENRE = "select * from film where genre_id = ? order by ? limit ?, ?;";

}
