package com.hassanadeola.mattire.viewmodels;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hassanadeola.mattire.R;

public class MainActivity extends AppCompatActivity {
    Button btn_settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btn_settings = (Button) findViewById(R.id.btn_settings);

        btn_settings.setOnClickListener((View v) -> navigateToSettings());
    }


    public void navigateToSettings(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}