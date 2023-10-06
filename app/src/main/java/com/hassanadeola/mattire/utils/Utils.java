package com.hassanadeola.mattire.utils;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.gson.Gson;
import com.hassanadeola.mattire.R;

import java.util.List;


public class Utils {


    public static void navigateToView(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    public static boolean validateUserInput(String text) {
        return text.trim().length() >= 5;
    }

    public static boolean validateUserInput(String text, int len) {
        return text.trim().length() >= len;
    }

    public static AlertDialog.Builder createAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton("Close",
                (dialogInterface, i) -> dialogInterface.dismiss());
        return builder;
    }


    public static void toggleDisable(boolean status, FrameLayout progressBar, Window window) {
        progressBar.setVisibility(status ? View.VISIBLE : View.INVISIBLE);
        if (status) {
            window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public static String getUserSharedPreference(Context context, String key) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void setSharedPreferences(Context context, String key, String value) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static void removeSharedPreferences(Context context, String key) {
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        sharedPreferences = context.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public static String createStringJson(List<?> value) {
        Gson gson = new Gson();
        return gson.toJson(value);
    }

    public static void createActionBar(ActionBar getSupportActionBar) {
        getSupportActionBar.setHomeAsUpIndicator(R.drawable.round_arrow_back_ios_24);
        getSupportActionBar.setDisplayHomeAsUpEnabled(true);

    }


    public static void changeStatusBarColor(Window getWindow, String color) {
        getWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow.setStatusBarColor(Color.parseColor(color));
    }

    public static void changeTheme(Context context, Window window, Resources resources, View[] views_bg) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("userPreference",
                Context.MODE_PRIVATE);

        String theme = sharedPreferences.getString("THEME", "");
        if (theme.isEmpty() || theme.equalsIgnoreCase("light")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            changeStatusBarColor(window, "#FF40C4FF");
        } else if (theme.equalsIgnoreCase("dark")) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            changeStatusBarColor(window, "#FF272727");
            changeColor(views_bg, "#FF272727");
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
            switch (resources.getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                case Configuration.UI_MODE_NIGHT_YES:
                    changeStatusBarColor(window, "#FF272727");
                    break;
                case Configuration.UI_MODE_NIGHT_NO:
                    changeStatusBarColor(window, "#FF40C4FF");
                    break;
            }
        }
    }

    public static void changeColor(View[] views, String color) {
        if (views != null) {
            for (View v : views) {
                v.setBackgroundColor(Color.parseColor(color));
            }
        }
    }
}
