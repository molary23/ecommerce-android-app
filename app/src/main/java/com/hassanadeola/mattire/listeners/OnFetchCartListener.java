package com.hassanadeola.mattire.listeners;



import java.util.List;

public interface OnFetchCartListener<CartItem> {

    void onFetchCartData(List<CartItem> cartItems, String message);
    void onError(String message);
}
