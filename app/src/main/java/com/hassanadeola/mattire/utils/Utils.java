package com.hassanadeola.mattire.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;

public class Utils {


    public static void navigate(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    public static boolean validateUserInput(String text) {
        return text.trim().length() > 5;
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

    public static String getSharedPreferences(Context context,String key) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("userPreference", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static void setSharedPreferences(Context context,String key,String value){
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
    }
}
