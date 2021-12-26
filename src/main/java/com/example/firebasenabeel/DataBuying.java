package com.example.firebasenabeel;

public class DataBuying {
    int price , size;
    String color  , url , name;
    public DataBuying(){
    }
    public  DataBuying(int price , int size , String color , String url , String name){
        this.price  = price;
        this.size = size;
        this.color = color;
        this.url = url;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
