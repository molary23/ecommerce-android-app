package com.hassanadeola.mattire.viewmodels;

import static com.hassanadeola.mattire.utils.Utils.*;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
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
    private FrameLayout progressBar;
    MaterialTextView txt_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Utils.changeStatusBarColor(getWindow());


        btn_login =  findViewById(R.id.btn_login);
        txt_register =  findViewById(R.id.txt_register);

        tf_username =  findViewById(R.id.tf_username);
        tf_password =  findViewById(R.id.tf_password);

        progressBar = findViewById(R.id.progressBar);

        txt_register.setOnClickListener((View view) -> navigateToRegister());



        btn_login.setOnClickListener((View view) -> login());

    }

    public void navigateToRegister() {
        navigateToView(this, RegisterActivity.class);
    }

    public void login() {
        String username = tf_username.getText().toString().trim().toLowerCase(),
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