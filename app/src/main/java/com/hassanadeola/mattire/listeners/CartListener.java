package com.hassanadeola.mattire.listeners;

import com.hassanadeola.mattire.models.CartItem;


public interface CartListener {

    void onRemoveFromCart(CartItem cartItem);
}
