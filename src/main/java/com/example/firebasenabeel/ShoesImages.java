package com.example.firebasenabeel;

import android.view.LayoutInflater;

public class ShoesImages {
    String url ,Type , index;
    int Price;

    public ShoesImages() {}

    public ShoesImages(String url , int Price , String type , String ind) {
        this.url = url;
        this.Price = Price;
        this.Type = type;
        this.index = ind;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getUrl() {
        return url;
    }
    public int getPrice() {
        return Price;
    }

    public String getType() {
        return Type;
    }

    public String getIndex() {
        return index;
    }
}

