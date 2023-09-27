package com.hassanadeola.mattire.fragment;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import androidx.preference.PreferenceFragmentCompat;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.models.Firebase;


public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener, Preference.OnPreferenceClickListener {
    ListPreference themes;
    Preference log_out;

    SharedPreferences sharedPreferences;

    String uuid = null, theme = null;


    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String preferenceKey = preference.getKey();
        if (preferenceKey.equalsIgnoreCase("pref_logout_val")) {
            Firebase firebase = new Firebase(requireActivity());
            firebase.logout();
        }

        return true;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        themes = findPreference("pref_theme_val");
        log_out = findPreference("pref_logout_val");

        log_out.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(@NonNull Preference preference) {
        String preferenceKey = preference.getKey();
        if (preferenceKey.equalsIgnoreCase("pref_logout_val")) {
            Firebase firebase = new Firebase(requireActivity());
            firebase.logout();
        }


        return true;
    }
}
