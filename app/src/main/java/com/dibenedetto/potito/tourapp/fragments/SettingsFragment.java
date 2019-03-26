package com.dibenedetto.potito.tourapp.fragments;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.preference.PreferenceFragmentCompat;

import com.dibenedetto.potito.tourapp.R;
import com.google.android.material.tabs.TabLayout;

import static com.dibenedetto.potito.tourapp.util.ViewUtility.findViewById;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat{


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String rootkey) {
        addPreferencesFromResource(R.xml.settings);
    }

}
