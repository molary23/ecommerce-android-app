package com.hassanadeola.mattire;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

public class Utils {


    public static Intent navigate(Context context, Class<?> activityClass) {
        return new Intent(context, activityClass);
    }

    public static boolean validateUserInput(String text) {
        return text.trim().length() > 5;
    }

    public static AlertDialog.Builder createAlertDialog(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        return builder;
    }
}
