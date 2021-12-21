package com.example.swichmarketapp.models;

public class PremiumUser {

    String UserName;
    String mEmail;
    String adress;
    String Phone;
    String id;
    String CardNumber;

    public PremiumUser(String id , String email, String userName, String phoneNumber, String adress , String CardNumber) {
        this.id =id;
        this.mEmail = email;
        this.UserName = userName;
        this.Phone = phoneNumber;
        this.adress =adress;
        this.CardNumber =CardNumber;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }
}
