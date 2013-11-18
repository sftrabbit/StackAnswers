package uk.co.sftrabbit.stackanswers;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import uk.co.sftrabbit.stackanswers.app.DrawerActivity;

public class AuthenticationActivity extends DrawerActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_authentication);

		setDrawerIndicatorEnabled(false);

		final ProgressBar progressBar =
			(ProgressBar) findViewById(R.id.authentication_progress);

		WebView webView = (WebView) findViewById(R.id.authentication_web_view);
		webView.setWebViewClient(new WebViewClient());
		webView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView webview, int newProgress) {
				progressBar.setProgress(newProgress);
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
	}
}
