package uk.co.sftrabbit.stackanswers;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;

public class SettingsActivity extends Activity {
	@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			setContentView(R.layout.activity_settings);

			ActionBar actionBar = getActionBar();
			assert actionBar != null : "No action bar in activity";
			actionBar.setDisplayHomeAsUpEnabled(true);

			getFragmentManager().beginTransaction()
				.add(R.id.settings_content, new SettingsFragment())
				.commit();
		}
}
