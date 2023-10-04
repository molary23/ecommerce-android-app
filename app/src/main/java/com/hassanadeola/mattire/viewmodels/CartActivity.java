package com.hassanadeola.mattire.viewmodels;

import static com.hassanadeola.mattire.utils.Utils.createAlertDialog;
import static com.hassanadeola.mattire.utils.Utils.setSharedPreferences;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.adapters.CartAdapter;
import com.hassanadeola.mattire.listeners.CartListener;
import com.hassanadeola.mattire.models.CartItem;
import com.hassanadeola.mattire.models.Firebase;
import com.hassanadeola.mattire.models.Users;
import com.hassanadeola.mattire.utils.CartItems;
import com.hassanadeola.mattire.utils.Utils;

import java.util.List;
import java.util.Objects;

public class CartActivity extends AppCompatActivity implements CartListener {

    CartAdapter cartAdapter;
    RecyclerView cartRecyclerView;
    CartItems cartItems;

    MaterialButton btn_check_out;
    MaterialTextView sub_price;
    String userId;
    List<CartItem> cartList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);


        Utils.createActionBar(Objects.requireNonNull(getSupportActionBar()));

        userId = Utils.getSharedPreferences(this, "USER_ID");

        sub_price = findViewById(R.id.sub_price);
        btn_check_out = findViewById(R.id.btn_check_out);




        cartItems = new CartItems(this);
       cartList = cartItems.getCartItems();
        checkCartList(cartList);

        String price = "$" + cartItems.getTotal();
        sub_price.setText(price);

        getCardDetails();

        btn_check_out.setOnClickListener((View view) -> Utils.navigateToView(this, CheckoutActivity.class));
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
        cartList = cartItems.getCartItems();
        checkCartList(cartList);

      //  showCartItems(cartItems.getCartItems());
    }

    @Override
    public void onReduceProduct(String productId) {
        cartItems.reduceProductInCart(productId);
       // showCartItems(cartItems.getCartItems());
        cartList = cartItems.getCartItems();
        checkCartList(cartList);
    }

    @Override
    public void onIncreaseProduct(String productId) {
        cartItems.increaseProductInCart(productId);
      //  showCartItems(cartItems.getCartItems());
        cartList = cartItems.getCartItems();
        checkCartList(cartList);
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
               // showCartItems(cartItems.getCartItems());
                cartList = cartItems.getCartItems();
                checkCartList(cartList);
            }
        });
        builder.show();
    }

    public void checkCartList(List<CartItem> cartList){
        showCartItems(cartList);
        if(cartList.size() > 0){
            btn_check_out.setVisibility(View.VISIBLE);
        }else{
            btn_check_out.setVisibility(View.INVISIBLE);
        }
    }

    public void getCardDetails() {
        Firebase firebase = new Firebase(this);
        firebase.getUserFormFirestore(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Users user = documentSnapshot.toObject(Users.class);
                    if (user == null) {
                        return;
                    }
                    Gson gson = new Gson();
                    String card = gson.toJson(user.getCard());
                    setSharedPreferences(CartActivity.this, "SAVED_CARD", card);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CartActivity.this, "Error getting card details!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

}