package uk.co.sftrabbit.stackanswers;

import android.os.Bundle;
import android.preference.PreferenceFragment;

class SettingsFragment extends PreferenceFragment {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		addPreferencesFromResource(R.xml.preferences);
	}
}
