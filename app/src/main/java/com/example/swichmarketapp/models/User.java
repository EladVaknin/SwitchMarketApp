package com.example.swichmarketapp.models;

public class User {
    String UserName;
    String mEmail;
    String Adress;
    String Phone;
    String Picture;
    String id;
    double rating;
    int counter;
    String admin;

    public User(String email, String userName, String phoneNumber) {
        this.mEmail = email;
        this.UserName = userName;
        this.Phone = phoneNumber;
    }

    public User(String UserName, String Email, String Adress, String Phone, String Picture, String id, double rating, int counter, String admin) {
        this.Adress = Adress;
        this.counter = counter;
        this.id = id;
        this.Picture = Picture;
        this.UserName = UserName;
        this.mEmail = Email;
        this.Phone = Phone;
        this.rating = rating;
        this.counter = counter;
        this.admin = admin;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

}
