package com.hassanadeola.mattire.fragments;


import static com.google.gson.internal.$Gson$Types.arrayOf;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;

import androidx.preference.PreferenceFragmentCompat;

import com.hassanadeola.mattire.R;
import com.hassanadeola.mattire.models.Firebase;


public class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener,
        Preference.OnPreferenceClickListener {

    ListPreference themes, contact;
    Preference log_out;

    SharedPreferences sharedPreferences;

    String uuid = null, theme = null;


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

    }

    @Override
    public boolean onPreferenceClick(@NonNull Preference preference) {
        String preferenceKey = preference.getKey();
        Log.d("TAG", preferenceKey);
        if (preferenceKey.equalsIgnoreCase("pref_logout_val")) {
            //   Firebase firebase = new Firebase(requireActivity());
            //  firebase.logout();
        } else if (preferenceKey.equalsIgnoreCase("pref_contact_val")) {

        }
        return true;
    }


}
