package uk.co.sftrabbit.stackanswers;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class AuthFragment extends Fragment {
	public AuthFragment() {
		// Do nothing
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.fragment_auth,
		                                    container, false);

		final ProgressBar progressBar =
			(ProgressBar) contentView.findViewById(R.id.authentication_progress);

		WebView webView =
			(WebView) contentView.findViewById(R.id.authentication_web_view);
		webView.setWebViewClient(new WebViewClient());
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView webview, int newProgress) {
				if (newProgress < 100) {
					progressBar.setProgress(newProgress);
					progressBar.setVisibility(View.VISIBLE);
				} else {
					progressBar.setVisibility(View.GONE);
				}
			}
		});

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		webView.loadUrl(
			"https://stackexchange.com/oauth/dialog?" +
			"client_id=2265&" +
			"scope=read_inbox,write_access,private_info&" +
			"redirect_uri=https://stackexchange.com/oauth/login_success"
		);

		return contentView;
	}


}
