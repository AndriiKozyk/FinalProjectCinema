package com.cinema.model.entity.user;

import com.cinema.model.entity.filmToOrder.FilmToOrder;
import com.cinema.model.entity.ticket.Ticket;
import java.util.*;

public class User {

    private int id;
    private String login;
    private String password;
    private Role role;
    private int userDetailsId;
    private UserDetails details;

    private List<Ticket> tickets;

    private List<FilmToOrder> userVote;
    private List<FilmToOrder> userSuggestion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public int getUserDetailsId() {
        return userDetailsId;
    }

    public void setUserDetailsId(int userDetailsId) {
        this.userDetailsId = userDetailsId;
    }

    public UserDetails getDetails() {
        return details;
    }

    public void setDetails(UserDetails details) {
        this.details = details;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", userDetailsId=" + userDetailsId +
                ", details=" + details +
                ", tickets=" + tickets +
                '}';
    }
}
