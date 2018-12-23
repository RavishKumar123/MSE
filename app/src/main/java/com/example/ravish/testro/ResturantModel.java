package com.example.ravish.testro;

import java.io.Serializable;

public class ResturantModel implements Serializable {

    String resturantName,time,homeImage,address,contactNo;
    int image;

    public ResturantModel(String resturantName, String time, String homeImage,String address,String contactNo) {
        this.resturantName = resturantName;
        this.time = time;
        this.image = image;
        this.homeImage = homeImage;
        this.address = address;
        this.contactNo = contactNo;
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

    public String getHomeImage(){
        return homeImage;
    }
    public String getAddress(){
        return address;
    }

    public String getcontact(){
        return contactNo;
    }
}
