package com.hassanadeola.mattire.views;

import static com.hassanadeola.mattire.Utils.*;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.textview.MaterialTextView;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.Utils;

public class LoginActivity extends AppCompatActivity {


    Button btn_login, btn_register;
    EditText tf_username, tf_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        btn_login = (Button) findViewById(R.id.btn_login);
        btn_register = (Button) findViewById(R.id.btn_register);

        tf_username = (EditText) findViewById(R.id.tf_username);
        tf_password = (EditText) findViewById(R.id.tf_password);

        btn_register.setOnClickListener((View view)-> navigateToRegister());

    }

    public void navigateToRegister() {
      startActivity(navigate(this, RegisterActivity.class));
    }

    public void login(){
        String username = tf_username.getText().toString(),
                password = tf_password.getText().toString();
        AlertDialog.Builder builder;
        if(validateUserInput(username) && validateUserInput(password)){
            // Login functionality
        }else{
             builder = createAlertDialog(this, "Wrong Credentials",
                    "Please enter a username and password at least 5 characters long");
            builder.show();
        }
    }


}