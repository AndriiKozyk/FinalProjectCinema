package com.cinema.model.entity.place;

public enum Type {

    STANDARD, LUX, SUPER_LUX;

    int price;

    Type(int price) {
        this.price = price;
    }

    Type() {}

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
