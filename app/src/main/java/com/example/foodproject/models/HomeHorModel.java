package com.example.foodproject.models;

public class HomeHorModel {

    int image;
    String name;

    public HomeHorModel(String name, int image) {
        this.name = name;
        this.image = image;
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
}
