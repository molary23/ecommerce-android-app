package com.hassanadeola.mattire.controllers;

import static com.hassanadeola.mattire.utils.Utils.*;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.models.Firebase;
import com.hassanadeola.mattire.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    Button btn_login, btn_register;
    EditText tf_username, tf_password;
    private FrameLayout progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        tf_username = (EditText) findViewById(R.id.tf_username);
        tf_password = (EditText) findViewById(R.id.tf_password);

        progressBar = findViewById(R.id.progressBar);

        btn_register.setOnClickListener((View view) -> navigateToRegister());

        btn_login.setOnClickListener((View view) -> login());

    }

    public void navigateToRegister() {
        navigate(this, RegisterActivity.class);
    }

    public void login() {
        String username = tf_username.getText().toString(),
                password = tf_password.getText().toString();

        if (validateUserInput(username) && validateUserInput(password)) {
            Utils.toggleDisable(true, progressBar, getWindow());
            Firebase firebase = new Firebase(this);
            Utils.toggleDisable(false, progressBar, getWindow());
            firebase.login(username, password);
        } else {
            AlertDialog.Builder builder = createAlertDialog(this, "Wrong Credentials",
                    "Please enter a username and password at least 5 characters long");
            builder.show();
        }
    }


}