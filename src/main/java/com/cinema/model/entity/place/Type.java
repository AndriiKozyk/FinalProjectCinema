package com.cinema.model.entity.place;

import java.math.BigDecimal;

public enum Type {

    STANDARD, LUX, SUPER_LUX;

    BigDecimal price;

    Type() {
        price = new BigDecimal(0);
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
