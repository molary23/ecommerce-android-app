package com.hassanadeola.mattire.listeners;

import com.hassanadeola.mattire.models.CartItem;


public interface CartListener {

    void onRemoveFromCart(String productId);

    void onReduceProduct(String productId);
    void onIncreaseProduct(String productId);
}
