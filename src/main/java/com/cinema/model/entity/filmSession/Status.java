package com.cinema.model.entity.filmSession;

public enum Status {

    AVAILABLE(1), NO_PLACES(2, "No places"), CANCELED(3, "Canceled");

    int id;
    String value;

    Status() {}

    Status(int id) {
        this.id = id;
    }

    Status(String value) {
        this.value = value;
    }

    Status(int id, String value) {
        this.id = id;
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
