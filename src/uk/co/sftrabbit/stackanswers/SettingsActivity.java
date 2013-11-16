package uk.co.sftrabbit.stackanswers;

import android.os.Bundle;
import uk.co.sftrabbit.stackanswers.app.DrawerActivity;

public class SettingsActivity extends DrawerActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_settings);

		setDrawerIndicatorEnabled(false);

		getFragmentManager().beginTransaction()
			.add(R.id.settings_content, new SettingsFragment())
			.commit();
	}
}
