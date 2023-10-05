package com.hassanadeola.mattire.utils;


import static android.content.ContentValues.TAG;
import static com.hassanadeola.mattire.utils.Utils.*;

import android.content.Context;
import android.util.Log;


import com.google.gson.Gson;
import com.hassanadeola.mattire.api.RequestManager;

import com.hassanadeola.mattire.models.CartItem;
import com.hassanadeola.mattire.models.Products;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CartItems {
    private List<CartItem> cartItems;
    Context context;
    private final RequestManager requestManager;
    private final String userId;

    public CartItems(Context context) {
        this.context = context;
        this.userId = getSharedPreferences(context, "USER_ID");
        this.requestManager = new RequestManager(context);
        Gson gson = new Gson();
        String cartsString = getSharedPreferences(context, "CART_ITEMS");
        if (cartsString != null ) {
            CartItem[] cartItem = gson.fromJson(cartsString, CartItem[].class);
            this.cartItems = new ArrayList<>(Arrays.asList(cartItem));
        }


    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }


    private boolean isFound(String productId) {
        return cartItems.stream().anyMatch(item -> item.getProduct()
                .getId().equalsIgnoreCase(productId));
    }

    public void addToCart(Products product) {
        requestManager.addToCart(userId, product.getId());
        boolean isFound = isFound(product.getId());
        // Post to Database
        if (isFound) {
            for (CartItem cartItem : cartItems) {
                if (cartItem.getProduct().getId().equalsIgnoreCase(product.getId())) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                }
            }
        } else {
            cartItems.add(new CartItem(product, 1));
        }

        setSharedPreferences(context, "CART_ITEMS", createStringJson(getCartItems()));
    }


    public void removeAProductFromCart(String productId) {
        requestManager.removeProductFromCart(userId, productId);
        cartItems.removeIf(cartItem -> cartItem.getProduct().getId()
                .equalsIgnoreCase(productId));
        setSharedPreferences(context, "CART_ITEMS", createStringJson(getCartItems()));
    }

    public void clearCart() {
        requestManager.clearCart(userId);
        cartItems.clear();
        setSharedPreferences(context, "CART_ITEMS", createStringJson(getCartItems()));

    }

    public void reduceProductInCart(String productId) {
        boolean isFound = isFound(productId);

        requestManager.reduceProductInCart(productId, userId);
        if (isFound) {
            for (CartItem cartItem : cartItems) {
                //  String id = cartItem.getProduct().getId();
                if (cartItem.getProduct().getId().equalsIgnoreCase(productId)) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                }
            }
            cartItems = cartItems.stream().filter(cartItem -> cartItem.getQuantity() >= 1)
                    .collect(Collectors.toList());
        }
        setSharedPreferences(context, "CART_ITEMS", createStringJson(getCartItems()));
    }


    public void increaseProductInCart(String productId) {
        boolean isFound = isFound(productId);
        requestManager.addToCart(productId, userId);
        // Increase in DB
        if (isFound) {
            for (CartItem cartItem : cartItems) {
                //  String id = cartItem.getProduct().getId();
                if (cartItem.getProduct().getId().equalsIgnoreCase(productId)) {
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                }
            }
        }
        setSharedPreferences(context, "CART_ITEMS", createStringJson(getCartItems()));
    }

    public double getTotal() {
        double total = 0;
        for (CartItem items : cartItems) {
            total += items.getProduct().getPrice() * items.getQuantity();
        }
        return total;
    }
}
