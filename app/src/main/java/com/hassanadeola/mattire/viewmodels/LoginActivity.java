package com.hassanadeola.mattire.viewmodels;

import static android.content.ContentValues.TAG;
import static com.hassanadeola.mattire.utils.Utils.*;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.android.material.textview.MaterialTextView;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.models.Firebase;
import com.hassanadeola.mattire.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    Button btn_login;
    EditText tf_username, tf_password;
    FrameLayout progressBar;
    MaterialTextView txt_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        changeTheme(this, getWindow(), getResources(), null);
        btn_login = findViewById(R.id.btn_login);
        txt_register = findViewById(R.id.txt_register);

        tf_username = findViewById(R.id.tf_username);
        tf_password = findViewById(R.id.tf_password);

        progressBar = findViewById(R.id.progressBar);

        txt_register.setOnClickListener((View view) -> navigateToRegister());


        btn_login.setOnClickListener((View view) -> login());

        String uId = Utils.getUserSharedPreference(this, "USER_ID");
        Log.d(TAG, uId);

    }

    public void navigateToRegister() {
        navigateToView(this, RegisterActivity.class);
    }

    public void login() {
        String username = tf_username.getText().toString().trim().toLowerCase(),
                password = tf_password.getText().toString();

        if (validateUserInput(username) && validateUserInput(password)) {
            toggleDisable(true, progressBar, getWindow());
            Firebase firebase = new Firebase(this);
            firebase.login(username, password, progressBar, getWindow());
        } else {
            AlertDialog.Builder builder = createAlertDialog(this, "Wrong Credentials",
                    "Please enter a username and password at least 5 characters long");
            builder.show();
        }
    }


}