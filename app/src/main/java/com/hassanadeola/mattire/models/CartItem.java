package com.hassanadeola.mattire.models;

import java.util.List;

public class CartItem {

    private Products product;
    private int quantity;

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public CartItem(Products product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
}
