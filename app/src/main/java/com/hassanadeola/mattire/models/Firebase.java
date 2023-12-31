package com.hassanadeola.mattire.models;

import static android.app.PendingIntent.getActivity;
import static android.content.ContentValues.TAG;


import static com.hassanadeola.mattire.utils.Utils.*;
import static com.hassanadeola.mattire.utils.Utils.createAlertDialog;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.hassanadeola.mattire.api.RequestManager;
import com.hassanadeola.mattire.viewmodels.LoginActivity;
import com.hassanadeola.mattire.viewmodels.ProductActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Firebase {
    private final FirebaseAuth firebaseAuth;

    FirebaseAuth.AuthStateListener firebaseAuthListener;
    FirebaseFirestore firebaseFirestore;

    String uuid;
    private final String token = null;
    Context context;

    private final RequestManager requestManager;

    private static final ArrayList<Users> users = new ArrayList<>();

    public Firebase(Context context) {
        this.context = context;
        firebaseAuth = FirebaseAuth.getInstance();
        requestManager = new RequestManager(context);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    public boolean isUserLoggedIn() {
        boolean isLoggedIn = false;
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            requestManager.getCartItemList(currentUser.getUid());
            isLoggedIn = true;
            //   currentUser.reload();
        }
        return isLoggedIn;
    }

    public void createUser(String email, String password, String username, String phone, FrameLayout progressBar, Window window) {

        // Check if user already exists
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                setSharedPreferences(context, "USER_ID", user.getUid());
                                saveUser(user.getUid(), username, email, phone);
                            }
                        } else {
                            toggleDisable(false, progressBar, window);
                            Toast.makeText(context, "Registration failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public void login(String email, String password, FrameLayout progressBar, Window window) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener((Activity) context, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user != null) {
                                setSharedPreferences(context, "USER_ID", user.getUid());
                                requestManager.getCartItemList(user.getUid());
                                navigateToView(context, ProductActivity.class);
                                ((Activity) context).finish();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            toggleDisable(false, progressBar, window);
                            AlertDialog.Builder builder = createAlertDialog(context, "Wrong Credentials",
                                    "Wrong Email Address or Password supplied.");

                            builder.show();
                        }
                    }
                });
    }

    public String getCurrentUserId() {
        String uid = "";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            uid = user.getUid();

        }
        return uid;
    }

    public void logout() {
        context.getSharedPreferences("userPreference", Context.MODE_PRIVATE).edit().clear().apply();
        firebaseAuth.signOut();
        ((Activity) context).finishAffinity();
        navigateToView(context, LoginActivity.class);
        ((Activity) context).finish();
    }

    public void saveUser(String userId, String username, String email, String phone) {
        getUserSharedPreference(context, "token");
        Users user = new Users(username, email, phone, token);

        firebaseFirestore.collection("users")
                .document(userId)
                .set(user).addOnSuccessListener((listener) -> {
                    Toast.makeText(context, "Sign up Successful!", Toast.LENGTH_SHORT).show();
                    navigateToView(context, ProductActivity.class);
                    ((Activity) context).finish();
                });
    }

    public void saveCard(String userId, Card card) {
        Map<String, Object> updates = new HashMap<String, Object>();
        updates.put("card", card);
        updates.put("updatedAt", Calendar.getInstance().getTimeInMillis());
        firebaseFirestore.collection("users")
                .document(userId).update(updates).addOnSuccessListener((listener) -> {
                    Toast.makeText(context, "Card Saved", Toast.LENGTH_SHORT).show();
                });
    }

    public Task<DocumentSnapshot> getUserFormFirestore(String userId) {
        return firebaseFirestore.collection("users").document(userId)
                .get();
    }


    public void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        return;
                    }
                    // Get new FCM registration token

                    setSharedPreferences(context, "token", task.getResult());
                });
    }
}
