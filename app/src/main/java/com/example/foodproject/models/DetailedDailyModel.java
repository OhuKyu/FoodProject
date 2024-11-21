package com.example.foodproject.models;

public class DetailedDailyModel {

    int image;
    String name;
    String description;
    String rating;
    String price;
    String time;

    public DetailedDailyModel(int image, String name, String rating, String description, String price, String time) {
        this.image = image;
        this.name = name;
        this.rating = rating;
        this.description = description;
        this.price = price;
        this.time = time;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}