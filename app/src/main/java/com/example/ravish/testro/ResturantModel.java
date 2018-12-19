package com.example.ravish.testro;

public class ResturantModel {
    String resturantName,time;
    int image;

    public ResturantModel(String resturantName, String time, int image) {
        this.resturantName = resturantName;
        this.time = time;
        this.image = image;
    }

    public String getResturantName() {
        return resturantName;
    }

    public String getTime() {
        return time;
    }

    public int getImage() {
        return image;
    }
}
