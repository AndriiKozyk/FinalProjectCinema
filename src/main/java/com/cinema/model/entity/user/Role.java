package com.cinema.model.entity.user;

public enum Role {

    ADMIN(1), USER;

    int id;

    Role() {}

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
