package com.hassanadeola.mattire.viewmodels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.utils.Utils;

public class ConfirmationActivity extends AppCompatActivity {

    MaterialButton btn_shopping;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        btn_shopping = findViewById(R.id.btn_shopping);

        btn_shopping.setOnClickListener((View view) -> continueShopping());

    }

    public void continueShopping() {
        Utils.navigateToView(this, ProductActivity.class);
        finish();
    }
}