package com.teslasoft.libraries.support;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends Activity {
	private WebView content;
	
	@SuppressLint("SetJavaScriptEnabled")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

		try {
			Intent licenseIntent = new Intent(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class);
			startActivityForResult(licenseIntent, 1);
		} catch (Exception e) {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}

		String processName = getProcessName(this);
		try {
			content.setDataDirectorySuffix(processName);
		} catch (Exception ignored) {}
		
        setContentView(R.layout.fragment_auth_webview);
		
		content = (WebView) findViewById(R.id.content);
		
		content.setBackgroundColor(0xFF212121);
        content.getSettings().setJavaScriptEnabled(true);
        content.getSettings().setLoadWithOverviewMode(true);
        content.getSettings().setUseWideViewPort(true);
		
        final Activity activity = this;
		
        content.setWebViewClient(new WebViewClient() {
			@SuppressWarnings("deprecation")
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {}

			public void onPageFinished(WebView view, String url) {}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setData(Uri.parse(url));
				startActivity(intent);
				return true;
			}
		});

        content.loadUrl("https://id.teslasoft.org/smartcard/open");
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}
	
	public String getProcessName(Context context) {
        if (context == null) return null;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == android.os.Process.myPid())
                return processInfo.processName;
        }

		return null;
	}
}
