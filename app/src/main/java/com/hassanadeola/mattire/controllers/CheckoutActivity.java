package com.hassanadeola.mattire.controllers;

import static android.app.PendingIntent.getActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.api.RequestManager;
import com.hassanadeola.mattire.models.Firebase;
import com.hassanadeola.mattire.models.Users;
import com.hassanadeola.mattire.utils.CartItems;
import com.hassanadeola.mattire.utils.Utils;

public class CheckoutActivity extends AppCompatActivity {

    EditText card_number, month, year, cvv;
    Button btn_pay;
    LinearLayout ln_subTotal, ln_tax, ln_total;

    MaterialTextView mt_subTotal_amount, mt_tax_amount, mt_total_amount, mt_subTotal_title, mt_total_title, mt_tax_title;
    SwitchCompat save_card;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    private Users user;

    RequestManager requestManager;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        card_number = findViewById(R.id.card_number);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        cvv = findViewById(R.id.cvv);
        save_card = findViewById(R.id.save_card);

        btn_pay = findViewById(R.id.btn_pay);

        ln_subTotal = findViewById(R.id.subTotal);
        ln_tax = findViewById(R.id.tax);
        ln_total = findViewById(R.id.total);


        mt_subTotal_amount = ln_subTotal.findViewById(R.id.amount);


        mt_tax_amount = ln_tax.findViewById(R.id.amount);


        mt_total_amount = ln_total.findViewById(R.id.amount);


        mt_subTotal_title = ln_subTotal.findViewById(R.id.title);
        mt_subTotal_title.setText(R.string.subtotal);

        mt_tax_title = ln_tax.findViewById(R.id.title);
        mt_tax_title.setText(R.string.tax);

        mt_total_title = ln_total.findViewById(R.id.title);
        mt_total_title.setText(R.string.total);

        userId = Utils.getSharedPreferences(this, "USER_ID");
        requestManager = new RequestManager(this);

        CartItems cartItems = new CartItems(this);
        double subTotal = cartItems.getTotal(),
                tax =  (subTotal * 13) / 100,
                total = subTotal + tax;
        String txt_total = "$" + total,
                txt_tax = "$" + tax,
                txt_subtotal = "$" + subTotal;
        mt_subTotal_amount.setText(txt_subtotal);
        mt_tax_amount.setText(txt_tax);
        mt_total_amount.setText(txt_total);


        getCardDetails();


    }

    public void getCardDetails() {
        Firebase firebase = new Firebase(this);

        firebase.getUserFormFirestore(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Users user = documentSnapshot.toObject(Users.class);
                    if (user == null) {
                        return;
                    }
                    setUser(user);
                    if (user.getCard() != null) {
                        onCreateDialog();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CheckoutActivity.this, "Error getting card details!!!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
        builder.setTitle(R.string.payment_method)
                .setItems(R.array.payment_methods, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position of the selected item.
                        builder.setOnDismissListener(DialogInterface::dismiss);
                        if (which == 0) {
                            save_card.setVisibility(View.GONE);
                            card_number.setText(getUser().getCard().getNumber());
                            year.setText(getUser().getCard().getYear());
                            month.setText(getUser().getCard().getMonth());
                            cvv.setText(getUser().getCard().getCvv());

                        }
                    }
                });
        builder.show();
    }

}