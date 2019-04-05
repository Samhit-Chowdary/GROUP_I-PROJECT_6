package com.example.samhi.firebasedemo;

public class UserModel {

    private  int image;
    private String desc;

    public UserModel(int image, String desc) {
        this.image = image;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
