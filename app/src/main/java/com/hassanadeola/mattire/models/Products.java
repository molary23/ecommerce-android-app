package com.hassanadeola.mattire.models;

public class Products {

    private String name, description;

    private double price, rating;

    public Products(String name, String description, double price, double rating) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.rating = rating;
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
