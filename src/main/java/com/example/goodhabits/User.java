package com.example.goodhabits;


import java.util.ArrayList;

public class User {

    public String email;
    public String password;
    public String name;
    public int ranking;
    public int points;
    public int level;
    public int currency;
    public String gender;
    public int profile;
    public int streak;
    public boolean unlockedItem1;
    public boolean unlockedItem2;
    public boolean unlockedItem3;
    public boolean unlockedItem4;

    // new empty constructor so that we can call the information
    public User(){

    }


    public User(String email, String password, String name, int ranking, int points, int level, int currency,
                String gender, int streak, int profile, boolean unlockedItem1, boolean unlockedItem2, boolean unlockedItem3, boolean unlockedItem4) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.ranking = ranking;
        this.points = points;
        this.level = level;
        this.currency = currency;
        this.gender = gender;
        this.streak = streak;
        this.profile = profile;
        this.unlockedItem1 = unlockedItem1;
        this.unlockedItem2 = unlockedItem2;
        this.unlockedItem3 = unlockedItem3;
        this.unlockedItem4 = unlockedItem4;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getCurrency() {
        return currency;
    }

    public void setCurrency(int currency) {
        this.currency = currency;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getProfile() {
        return profile;
    }

    public void setProfile(int profile) {
        this.profile = profile;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }
}