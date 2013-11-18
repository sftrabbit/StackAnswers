package uk.co.sftrabbit.stackanswers;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AuthInfoFragment extends Fragment {
	public AuthInfoFragment() {
		// Do nothing
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_auth_info, container, false);
	}
}
