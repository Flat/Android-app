package io.radio.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.Toast;

public class SettingsActivity extends PreferenceActivity{
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment())
				.commit();
	}

	public static class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
		private CountDownTimer tSleepTimer;
		public void createNewTimer(long timeInMillis) {
		       if(tSleepTimer != null) {
		         tSleepTimer.cancel();
		       }
		       tSleepTimer = new CountDownTimer(timeInMillis, 1000) {
		       @Override
		       public void onTick(final long millisUntilFinished) {
		       }

		       @Override
		       public void onFinish() {
		    	   ListPreference prefSleepTimer = (ListPreference) findPreference("pref_sleepTimer");
		    	   prefSleepTimer.setValue("Off");
		    	   Intent i = new Intent("stop");
		           getActivity().sendBroadcast(i);
		       }
		       }.start();
		       Toast.makeText(getActivity(), "Timer Set for " + String.valueOf(timeInMillis / 60 / 1000) + " Minutes",Toast.LENGTH_LONG).show();
		}
		
		
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
		            	createNewTimer(Long.parseLong(sharedPreferences.getString(key, "")) * 60 * 1000);
		            	prefSleepTimer.setSummary(sharedPreferences.getString(key, "") + " Minutes");
		            }
		            else if(sharedPreferences.getString(key, "Off").equals("Off")){
		            	prefSleepTimer.setSummary(sharedPreferences.getString(key, ""));
		            	if(tSleepTimer != null) {
		   		         tSleepTimer.cancel();
		   		       }
		            }
		        }

			
		}
	}

}
