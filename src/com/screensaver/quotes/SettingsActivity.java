package com.screensaver.quotes;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {    
	    super.onCreate(savedInstanceState);       
	    addPreferencesFromResource(R.xml.preferences);    
	    
	    ListPreference listPreference = (ListPreference) findPreference("updates_interval");
	    if(listPreference.getEntry() != null) {
	    	listPreference.setSummary(listPreference.getValue().toString());
	    }
	    
	    listPreference.setOnPreferenceChangeListener(new OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                preference.setSummary(newValue.toString());
                return true;
            }
        });
	}
}
