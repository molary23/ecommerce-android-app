package com.hassanadeola.mattire.utils;

import com.hassanadeola.mattire.models.CartItem;
import com.hassanadeola.mattire.models.Products;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CartItems {

    private List<CartItem> cartItems;


    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    private boolean isFound(String productId) {
        return cartItems.stream().anyMatch(item -> item.getProduct()
                .getId().equalsIgnoreCase(productId));
    }
/*
    public void addToCart(Products product) {
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
    }

    public void removeAProductFromCart(Products product) {
        boolean isFound = isFound(product.getId());

        // Post to DB
        if(isFound){
            for (CartItem cartItem : cartItems) {
              //  String id = cartItem.getProduct().getId();
                if (cartItem.getProduct().getId().equalsIgnoreCase(product.getId())) {
                    cartItem.setQuantity(cartItem.getQuantity() - 1);
                }
            }
            cartItems = cartItems.stream().filter(cartItem -> cartItem.getQuantity() >= 1)
                    .collect(Collectors.toList());
        }
    }

    public void removeAllInstancesOfProductFromCart(Products product) {
      //  cartItems = cartItems.stream().filter(cartItem -> cartItem.getProduct().getId()
             //   .equalsIgnoreCase(product.getId())).collect(Collectors.toList());
        cartItems.removeIf(cartItem -> cartItem.getProduct().getId()
                  .equalsIgnoreCase(product.getId()));
    }

    public void clearCart(){
        //Post to DB
        cartItems.clear();
    }*/
}
