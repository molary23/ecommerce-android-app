package com.hassanadeola.mattire.models;

import java.io.Serializable;

public class Products implements Serializable {

    private String name, description, image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private double price, rating;

    public Products(String name, String description, double price, double rating, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
        this.image = image;
    }

    public Products() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
