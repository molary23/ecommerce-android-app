package com.hassanadeola.mattire.fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.models.Firebase;
import com.hassanadeola.mattire.utils.DataStore;
import com.hassanadeola.mattire.utils.Utils;

import java.util.Objects;


public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener {

    ListPreference themes, contact;
    Preference log_out;

    SharedPreferences sharedPreferences;


    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        String preferenceKey = preference.getKey(),
                choice = String.valueOf(value);

        if (preferenceKey.equalsIgnoreCase("pref_contact_val")) {
            String CALL_CENTER_NUMBER = "+14646350985",
                    CALL_CENTER_EMAIL = "contact@contact.com",
                    CALL_CENTER_CC = "info@contact.com",
                    SUBJECT = "Hello World";
            if (choice.equalsIgnoreCase("call")) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + CALL_CENTER_NUMBER));
                startActivity(dialIntent);
            } else if (choice.equalsIgnoreCase("message")) {
                Intent messageIntent = new Intent(Intent.ACTION_VIEW);
                messageIntent.setData(Uri.fromParts("sms", CALL_CENTER_NUMBER, null));
                startActivity(messageIntent);
            } else if (choice.equalsIgnoreCase("mail")) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, CALL_CENTER_CC);
                intent.putExtra(Intent.EXTRA_SUBJECT, SUBJECT);
                startActivity(intent);
            }
        }

        if (preferenceKey.equalsIgnoreCase("pref_theme_val")) {
            requireActivity().finish();
            requireActivity().overridePendingTransition(0, 0);
            startActivity(requireActivity().getIntent());
            requireActivity().overridePendingTransition(0, 0);
            themes.setSummary(choice);
            Toast.makeText(requireActivity(), "Theme Changed", Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
        themes = findPreference("pref_theme_val");

        contact = findPreference("pref_contact_val");
        log_out = findPreference("pref_logout_val");
        if (log_out != null) {
            log_out.setOnPreferenceClickListener(this);
        }

        if (contact != null) {
            contact.setOnPreferenceChangeListener(this);
        }

        DataStore dataStore = new DataStore(requireActivity());
        PreferenceManager preferenceManager = getPreferenceManager();
        preferenceManager.setPreferenceDataStore(dataStore);
        String savedTheme = Utils.getUserSharedPreference(requireActivity(), "THEME");
        if (savedTheme != null && !savedTheme.isEmpty()) {
            Objects.requireNonNull(themes).setValue(savedTheme);
            themes.setSummary(savedTheme);

        }

        themes.setOnPreferenceChangeListener(this);
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
