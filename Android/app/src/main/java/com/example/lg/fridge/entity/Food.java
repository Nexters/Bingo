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
    private int imageId;
    private String name;
    private int count;
    private String date_bought;
    private String date_expired;
    //place를 어떻게할까

    public Food(int i, String n, int c, String db, String de) {
        this.imageId = i;
        this.name = n;
        this.count = c;
        this.date_bought = db;
        this.date_expired = de;
    }
    //Todo: getters and setters
}
