package com.example.firebasenabeel;

public class Upload_price_type {
    String type ;
    int price;

    public Upload_price_type(String type, int price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }
}
