package com.hassanadeola.mattire.viewmodels;

import static com.hassanadeola.mattire.utils.Utils.changeTheme;
import static com.hassanadeola.mattire.utils.Utils.navigateToView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.api.RequestManager;
import com.hassanadeola.mattire.models.Firebase;
import com.hassanadeola.mattire.utils.Utils;

public class LaunchActivity extends AppCompatActivity {
    Firebase firebase;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        firebase = new Firebase(this);
        getCartItems();

        String token = Utils.getUserSharedPreference(this, "token");
        if (token.isEmpty()) {
            Firebase firebase = new Firebase(this);
            firebase.getToken();
        }

        Runnable runnable = this::isLoggedIn;

        Handler handler = new android.os.Handler();
        handler.postDelayed(runnable, 5000);
    }

    public void isLoggedIn() {
        Class<?> authClass = LoginActivity.class;
        if (getLoginStatus()) {
            authClass = ProductActivity.class;
        }
        navigateToView(this, authClass);
        finish();
    }

    public boolean getLoginStatus() {
        return firebase.isUserLoggedIn();
    }

    public void getCartItems() {
        if (getLoginStatus()) {
            RequestManager requestManager = new RequestManager(this);
            requestManager.getCartItemList(firebase.getCurrentUserId());
        }
    }

}