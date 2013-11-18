package uk.co.sftrabbit.stackanswers;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.View;
import uk.co.sftrabbit.stackanswers.app.DrawerActivity;

public class AuthenticationActivity extends DrawerActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_authentication);

		setDrawerIndicatorEnabled(false);
	}

	public void showAuthenticationPage(View view) {
		getFragmentManager().beginTransaction()
			.setCustomAnimations(R.animator.fragment_open_enter, 0)
			.replace(R.id.authentication_content, new AuthFragment())
			.addToBackStack(null)
			.commit();
	}
}
