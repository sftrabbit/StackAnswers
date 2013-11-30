/*
 * Copyright (C) 2013 Joseph Mansfield
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package uk.co.sftrabbit.stackanswers.fragment;

import android.annotation.SuppressLint;
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
import uk.co.sftrabbit.stackanswers.R;

public class AuthFragment extends Fragment {
	public AuthFragment() {
		// Do nothing
	}

	@Override
	@SuppressLint("SetJavaScriptEnabled")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		View contentView = inflater.inflate(R.layout.authentication,
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
