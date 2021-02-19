package com.cinema.model.entity.user;

public enum Role {

    ADMIN(1), USER(2);

    int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
