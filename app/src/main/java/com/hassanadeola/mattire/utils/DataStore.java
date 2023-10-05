package com.hassanadeola.mattire.utils;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.preference.PreferenceDataStore;

public class DataStore extends PreferenceDataStore {

    SharedPreferences sharedPreferences;
    String uuid = null;

    String sqlId = null;
    Context context;
    SharedPreferences.Editor editor;

    public DataStore(Context context) {
        this.context = context;
    }

    @Override
    public void putString(String key, @Nullable String value) {
        if (key.equalsIgnoreCase("pref_theme_val")) {
            Utils.setSharedPreferences(context, "THEME", value);
        }
    }

    @Override
    @Nullable
    public String getString(String key, @Nullable String defValue) {
        // Retrieve the value
        return null;
    }
}
