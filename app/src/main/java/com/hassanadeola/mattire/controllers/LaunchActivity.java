package com.hassanadeola.mattire.controllers;

import static android.content.ContentValues.TAG;
import static com.hassanadeola.mattire.utils.Utils.navigateToView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.models.Firebase;
import com.hassanadeola.mattire.utils.Utils;

public class LaunchActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        String token = Utils.getSharedPreferences(this, "token");
     /*   sharedPreferences = getSharedPreferences("userPreference", MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");*/
        if (token.isEmpty()){
            Firebase firebase = new Firebase(this);
            firebase.getToken();
        }

        Runnable runnable = this::isLoggedIn;

        Handler handler = new android.os.Handler();
        handler.postDelayed(runnable, 5000);
    }

    public void isLoggedIn(){
        Firebase firebase = new Firebase(this);
        Class<?> authClass = LoginActivity.class;
        if(firebase.isUserLoggedIn()){
            authClass = ProductActivity.class;
        }
        navigateToView(this, authClass);
    }


}