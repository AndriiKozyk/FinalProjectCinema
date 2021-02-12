package com.cinema.model.entity.filmSession;

public enum Status {

    AVAILABLE, NO_PLACES, CANCELED;

    int id;

    Status() {}

    Status(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
