package com.hassanadeola.mattire.viewmodels;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.models.Products;
import com.hassanadeola.mattire.utils.CartItems;
import com.hassanadeola.mattire.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.regex.Pattern;

public class DetailsActivity extends AppCompatActivity {
    MaterialTextView product_name, product_description, product_rating, product_price;

    LinearLayout product_image_layout;

    Products product;

    ShapeableImageView product_image, product_rating_icon;
    FloatingActionButton addToCart;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Utils.changeStatusBarColor(getWindow());

        product = (Products) getIntent().getSerializableExtra("product");

        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.product_price);
        product_description = findViewById(R.id.product_description);
        product_rating = findViewById(R.id.product_rating);
        product_rating_icon = findViewById(R.id.product_rating_icon);

        addToCart = findViewById(R.id.addToCart);



        product_image_layout = findViewById(R.id.product_image_layout);


        product_name.setText(product.getName());
        product_description.setText(product.getDescription());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            product_description.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        String price = "$" + product.getPrice();
        Double rating = product.getRating();
        product_rating.setText(String.valueOf(rating));
        product_price.setText(price);

        product_rating_icon.setImageResource(rating >= 4.0 ? R.drawable.outline_star_24 : R.drawable.outline_star_border_24);
        ;
        populateImages(product.getImage());

        addToCart.setOnClickListener((View view)-> addToCart(product));
    }

    public void populateImages(String images) {
        String[] imageArray = images.split(Pattern.quote("|"));

       int width = 1500,
        height = 1500;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);

       for (String img : imageArray) {
            if (img != null) {
                ShapeableImageView imageView = new ShapeableImageView(this);
                Picasso.get().load(img).into(imageView);
                imageView.setLayoutParams(params);
                product_image_layout.addView(imageView);
            }

        }


    }

    public void addToCart(Products product) {
        CartItems cartItems = new CartItems(this);
        cartItems.addToCart(product);
    }
}