package com.cinema.model.entity.filmSession;

public enum Status {

    AVAILABLE, NO_PLACES("No places"), CANCELED("Canceled");

    int id;
    String value;

    Status() {}

    Status(int id) {
        this.id = id;
    }

    Status(String value) {
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Status{" +
                "id=" + id +
                '}';
    }
}
