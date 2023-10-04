package com.hassanadeola.mattire.viewmodels;

import static android.app.PendingIntent.getActivity;

import static com.hassanadeola.mattire.utils.Utils.createAlertDialog;
import static com.hassanadeola.mattire.utils.Utils.getSharedPreferences;
import static com.hassanadeola.mattire.utils.Utils.navigateToView;
import static com.hassanadeola.mattire.utils.Utils.removeSharedPreferences;
import static com.hassanadeola.mattire.utils.Utils.toggleDisable;
import static com.hassanadeola.mattire.utils.Utils.validateUserInput;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.gson.Gson;
import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.api.RequestManager;
import com.hassanadeola.mattire.listeners.PaymentListener;
import com.hassanadeola.mattire.models.Card;
import com.hassanadeola.mattire.models.CartItem;
import com.hassanadeola.mattire.models.Firebase;
import com.hassanadeola.mattire.models.Users;
import com.hassanadeola.mattire.utils.CartItems;
import com.hassanadeola.mattire.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

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

    double total;
    Firebase firebase;

    FrameLayout progressBar;

    Card card;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Utils.createActionBar(Objects.requireNonNull(getSupportActionBar()));

        card_number = findViewById(R.id.card_number);
        month = findViewById(R.id.month);
        year = findViewById(R.id.year);
        cvv = findViewById(R.id.cvv);
        save_card = findViewById(R.id.save_card);

        btn_pay = findViewById(R.id.btn_pay);

        progressBar = findViewById(R.id.progressBar);

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
                tax = (subTotal * 13) / 100;
        total = subTotal + tax;
        String txt_total = "$" + total,
                txt_tax = "$" + tax,
                txt_subtotal = "$" + subTotal;
        mt_subTotal_amount.setText(txt_subtotal);
        mt_tax_amount.setText(txt_tax);
        mt_total_amount.setText(txt_total);

        card = new Card();

        btn_pay.setOnClickListener((View view) -> makePayment());

        firebase = new Firebase(this);

        getCardDetails();


    }

    public void getCardDetails() {
     /*   firebase.getUserFormFirestore(userId).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    Users user = documentSnapshot.toObject(Users.class);
                    if (user == null) {
                        return;
                    }
                    setUser(user);
                    if (user.getCard() != null) {
                      //  onCreateDialog();
                    }
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CheckoutActivity.this, "Error getting card details!!!", Toast.LENGTH_LONG).show();
            }
        });*/

        Gson gson = new Gson();
        String cardString = Utils.getSharedPreferences(CheckoutActivity.this, "SAVED_CARD");
        if (cardString != null ) {
             card = gson.fromJson(cardString, Card.class);
            if(card != null) {
                onCreateDialog();
            }

        }
    }

    public void onCreateDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(CheckoutActivity.this);
        builder.setTitle(R.string.payment_method)
                .setItems(R.array.payment_methods, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position of the selected item.
                        builder.setOnDismissListener(DialogInterface::dismiss);
                        if (which == 0) {
                            String secretCardNumber = "************" + card.getNumber().substring(12);
                            save_card.setVisibility(View.GONE);
                            card_number.setText(secretCardNumber);
                            year.setText(card.getYear());
                            month.setText(card.getMonth());
                            cvv.setText(card.getCvv());

                        }
                    }
                });
        builder.show();
    }

    private final PaymentListener listener = new PaymentListener() {

        @Override
        public void onMakePayment(Boolean status) {
            toggleDisable(false, progressBar, getWindow());
            Toast.makeText(CheckoutActivity.this, "Payment Successful!", Toast.LENGTH_SHORT).show();
            removeSharedPreferences(CheckoutActivity.this, "CART_ITEMS");
            navigateToView(CheckoutActivity.this, ConfirmationActivity.class);
            finish();
        }

        @Override
        public void onError(String message) {
            toggleDisable(false, progressBar, getWindow());
            Toast.makeText(CheckoutActivity.this, "Payment unsuccessful. Try again later!", Toast.LENGTH_SHORT).show();

        }
    };

    public void makePayment() {
        String cardNumber = card_number.getText().toString(),
                cardYear = year.getText().toString(),
                cardMonth = month.getText().toString(),
                cardCVV = cvv.getText().toString();
        if (validateUserInput(cardNumber, 16) && validateUserInput(cardMonth, 2) && validateUserInput(cardYear, 2) && validateUserInput(cardCVV, 3)) {
            toggleDisable(true, progressBar, getWindow());
            if (save_card.isChecked()) {
                firebase.saveCard(userId, new Card(cardNumber, cardMonth, cardYear, cardCVV));
            }
            requestManager.makePayment(listener, userId, total, cardNumber.substring(cardNumber.length() - 4));

        } else {
            androidx.appcompat.app.AlertDialog.Builder builder = createAlertDialog(this,
                    "Missing Information", "All fields are required");
            builder.show();

        }
    }
}