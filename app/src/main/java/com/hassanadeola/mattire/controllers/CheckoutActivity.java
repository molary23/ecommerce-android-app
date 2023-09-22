package com.hassanadeola.mattire.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.textview.MaterialTextView;
import com.hassanadeola.mattire.R;

public class CheckoutActivity extends AppCompatActivity {

    EditText card_number, month, year, cvv;
    Button btn_pay;
    LinearLayout ln_subTotal, ln_tax, ln_total;

    MaterialTextView mt_subTotal_amount, mt_tax_amount, mt_total_amount, mt_subTotal_title, mt_total_title, mt_tax_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        card_number = (EditText) findViewById(R.id.card_number);
        month = (EditText) findViewById(R.id.month);
        year = (EditText) findViewById(R.id.year);
        cvv = (EditText) findViewById(R.id.cvv);

        btn_pay = (Button) findViewById(R.id.btn_pay);

        ln_subTotal = (LinearLayout) findViewById(R.id.subTotal);
        ln_tax = (LinearLayout) findViewById(R.id.tax);
        ln_total = (LinearLayout) findViewById(R.id.total);


        mt_subTotal_amount = ln_subTotal.findViewById(R.id.amount);
        mt_subTotal_amount.setText("$56.76");

        mt_tax_amount = ln_tax.findViewById(R.id.amount);
        mt_tax_amount.setText("$30.00");

        mt_total_amount = ln_total.findViewById(R.id.amount);
        mt_total_amount.setText("$86.76");


        mt_subTotal_title = ln_subTotal.findViewById(R.id.title);
        mt_subTotal_title.setText(R.string.subtotal);

        mt_tax_title = ln_tax.findViewById(R.id.title);
        mt_tax_title.setText(R.string.tax);

        mt_total_title = ln_total.findViewById(R.id.title);
        mt_total_title.setText(R.string.total);
    }
}