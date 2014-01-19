package io.radio.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

public class SettingsActivity extends PreferenceActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment())
				.commit();
	}

	public static class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
		
		
		@Override
		public void onCreate(final Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.app_settings);
		}

		@Override
		public void onResume() {
		    super.onResume();
		    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
		}

		@Override
		public void onPause() {
		    super.onPause();
		    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
		}
		public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
				String key) {
			 if (key.equals("pref_sleepTimer")) {
		            Preference prefSleepTimer = findPreference(key);
		            if(!sharedPreferences.getString(key, "Off").equals("Off")){
		            	prefSleepTimer.setSummary(sharedPreferences.getString(key, "") + " Minutes");
		            }
		            else if(sharedPreferences.getString(key, "Off").equals("Off")){
		            	prefSleepTimer.setSummary(sharedPreferences.getString(key, ""));
		            }
		            Intent i = new Intent("update timer");
					getActivity().sendBroadcast(i);
		        }
		}
	}

}
