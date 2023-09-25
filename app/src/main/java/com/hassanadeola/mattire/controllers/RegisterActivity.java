package com.hassanadeola.mattire.controllers;

import static com.hassanadeola.mattire.utils.Utils.createAlertDialog;
import static com.hassanadeola.mattire.utils.Utils.navigate;
import static com.hassanadeola.mattire.utils.Utils.validateUserInput;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.models.Firebase;
import com.hassanadeola.mattire.utils.Utils;

public class RegisterActivity extends AppCompatActivity {

    EditText tf_username, tf_email, tf_password, tf_confirm_password, tf_phone;
    Button btn_register, btn_login;
    private FrameLayout progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        tf_username = (EditText) findViewById(R.id.tf_username);
        tf_password = (EditText) findViewById(R.id.tf_password);
        tf_email = (EditText) findViewById(R.id.tf_email);
        tf_confirm_password = (EditText) findViewById(R.id.tf_confirm_password);
        tf_phone = (EditText) findViewById(R.id.tf_phone);

        progressBar = findViewById(R.id.progressBar);



        btn_login.setOnClickListener((View view) -> navigateToLogin());
        btn_register.setOnClickListener((View view) -> register());
    }

    private void navigateToLogin() {
        navigate(this, LoginActivity.class);
    }

    private void register() {
        String username = tf_username.getText().toString(),
                password = tf_password.getText().toString(),
                confirmPassword = tf_confirm_password.getText().toString(),
                phone = tf_phone.getText().toString(),
                email = tf_email.getText().toString();

        AlertDialog.Builder builder;
        if (validateUserInput(username) && validateUserInput(password)
                && validateUserInput(phone) && validateUserInput(email)
                && validateUserInput(confirmPassword)) {

            if (!password.equals(confirmPassword)) {
                builder = createAlertDialog(this, "Password Mismatched",
                        "You have entered 2 different passwords");
                builder.show();
                return;
            }
            Utils.toggleDisable(true, progressBar, getWindow());
            Firebase firebase = new Firebase(this);
            firebase.createUser(email, password, username, phone);
            Utils.toggleDisable(false, progressBar, getWindow());

        } else {
            builder = createAlertDialog(this, "Wrong Credentials",
                    "Please fill all fields");
            builder.show();
        }
    }
}