package com.hassanadeola.mattire.models;

public class Orders {

    private Products[] products;
    private int quantity;

    public Orders(Products[] products, int quantity) {
        this.products = products;
        this.quantity = quantity;
    }

    public Orders() {
    }

    public Products[] getProducts() {
        return products;
    }

    public void setProducts(Products[] products) {
        this.products = products;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
