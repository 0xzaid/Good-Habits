package com.example.goodhabits;


public class User {

    public String email;
    public String password;
    public String name;
    public int ranking;
    public int points;
    public int currency;
    public String gender;

    // new empty constructor so that we can call the information
    public User(){

    }


    public User(String email, String password, String name, int ranking, int points, int currency, String gender) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.ranking = ranking;
        this.points = points;
        this.currency = currency;
        this.gender = gender;
    }


}