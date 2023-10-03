package com.hassanadeola.mattire.viewmodels;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.utils.Utils;

import java.util.Objects;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Utils.createActionBar(Objects.requireNonNull(getSupportActionBar()));
    }
}