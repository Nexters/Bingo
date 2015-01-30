package com.example.lg.fridge.entity;

/**
 * Created by LG on 2015-01-19.
 */
public final class Food {
    //아직 어케해야할지 모름
    //음성인식으로 음식의 이름을 받으면
    //그 이름에 따라 name과 imageId를 정해줘야하는데
    //그렇다면 그 많은 음식의 종류를 switch case 문에 넣어서 해야할까?
    //eg) case '토마토':
    //      food.setName('Tomato');
    //      food.setImageIdAccordingToName();
    private int imageId = 0;
    private String foodName;
    private int count = 0;
    private String boughtDate = "";
    private String expiryDate;
    //place를 어떻게할까

    public Food(){}

    public Food(int i, String n, int c, String db, String de) {
        this.imageId = i;
        this.foodName = n;
        this.count = c;
        this.boughtDate = db;
        this.expiryDate = de;
    }

    public Food(String foodName, String expiryDate) {
        this.foodName = foodName;
        this.expiryDate = expiryDate;
    }
    //Todo: getters and setters

    public void setFoodName(String n) {
        foodName = n;
    }

    public String getFoodName(){return foodName;}

    public void setExpiryDate(String d) {
        expiryDate = d;
    }

    public String getExpiryDate(){return expiryDate;}
}
