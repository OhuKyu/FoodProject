package com.example.foodproject.models;

public class FeaturedVerModel {
    int image;
    String name, description, rating, time;

    public FeaturedVerModel(int image, String name, String description, String rating, String time) {
        this.image = image;
        this.name = name;
        this.description = description;
        this.rating = rating;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
