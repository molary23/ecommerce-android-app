package com.hassanadeola.mattire.views;

import static com.hassanadeola.mattire.Utils.navigate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hassanadeola.mattire.R;

public class RegisterActivity extends AppCompatActivity {

    EditText tf_username, tf_email, tf_password, tf_confirm_password, tf_phone;
    Button btn_register, btn_login;

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

        btn_login.setOnClickListener((View view)-> navigateToLogin());
    }

    private void navigateToLogin() {startActivity(navigate(this, LoginActivity.class));
    }
}