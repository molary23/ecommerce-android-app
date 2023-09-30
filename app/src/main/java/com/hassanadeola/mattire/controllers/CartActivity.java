package com.hassanadeola.mattire.controllers;

import static com.hassanadeola.mattire.utils.Utils.createAlertDialog;
import static com.hassanadeola.mattire.utils.Utils.navigateToView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.adapters.BestAdapter;
import com.hassanadeola.mattire.adapters.CartAdapter;
import com.hassanadeola.mattire.adapters.DealAdapter;
import com.hassanadeola.mattire.api.RequestManager;
import com.hassanadeola.mattire.listeners.CartListener;
import com.hassanadeola.mattire.models.CartItem;
import com.hassanadeola.mattire.models.Products;
import com.hassanadeola.mattire.utils.CartItems;
import com.hassanadeola.mattire.utils.Section;
import com.hassanadeola.mattire.utils.Utils;

import java.util.List;

public class CartActivity extends AppCompatActivity implements CartListener {

    CartAdapter cartAdapter;
    RecyclerView cartRecyclerView;
    CartItems cartItems;

    MaterialButton btn_check_out;
    MaterialTextView sub_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        sub_price = findViewById(R.id.sub_price);
        btn_check_out = findViewById(R.id.btn_check_out);


        btn_check_out.setOnClickListener((View view) -> Utils.navigateToView(this, CheckoutActivity.class));

        cartItems = new CartItems(this);
        showCartItems(cartItems.getCartItems());

        String price = "$" + String.valueOf(cartItems.getTotal());
        sub_price.setText(price);
    }


    private void showCartItems(List<CartItem> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        cartRecyclerView.setHasFixedSize(true);
        cartRecyclerView.setLayoutManager(layoutManager);
        cartAdapter = new CartAdapter(this, list, this);
        cartRecyclerView.setAdapter(cartAdapter);
    }

    @Override
    public void onRemoveFromCart(String productId) {
        cartItems.removeAProductFromCart(productId);
        showCartItems(cartItems.getCartItems());
    }

    @Override
    public void onReduceProduct(String productId) {
        cartItems.reduceProductInCart(productId);
        showCartItems(cartItems.getCartItems());
    }

    @Override
    public void onIncreaseProduct(String productId) {
        cartItems.increaseProductInCart(productId);
        showCartItems(cartItems.getCartItems());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.clear_cart) {
            clearCart();

        } else if (item.getItemId() == R.id.menu_settings) {
            navigateToView(this, SettingsActivity.class);
        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return true;
    }

    public void clearCart() {
        AlertDialog.Builder builder = createAlertDialog(this, "Clear Cart",
                "Are you sure you want to remove all Items in Cart");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                cartItems.clearCart();
                showCartItems(cartItems.getCartItems());
            }
        });
        builder.show();
    }

}