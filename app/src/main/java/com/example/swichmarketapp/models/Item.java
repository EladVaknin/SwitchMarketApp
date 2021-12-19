package com.example.swichmarketapp.models;

public class Item {
    private String description ;
    private String Photo;
    private String tosWitch;
    private String price;
    private String ImageUrl;


    public Item (String description,String ImageURL){
        this.ImageUrl =ImageURL;
        this.description =description;
    }


    public Item (String description, String photo, String tosWitch, String price){
        this.description =description;
        this.Photo=photo;
        this.tosWitch=tosWitch;
        this.price=price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getTosWitch() {
        return tosWitch;
    }

    public void setTosWitch(String tosWitch) {
        this.tosWitch = tosWitch;
    }

}
