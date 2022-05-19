package com.teslasoft.jarvis.auth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.AsyncTask;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.SharedPreferences;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.teslasoft.libraries.support.R;

public class AuthActivity extends Activity {
	private WebView content;
	private final String lang = "en-US"; // Default lang
	private final String did = "0x005"; // Tracking code
	private String appId;
	public LinearLayout loadingScreen;
	public LinearLayout loginScreen;
	public LinearLayout loadbg;
	public String isDarkMode;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.activity_login_checkin);

		try {
			Intent licenseIntent = new Intent(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class);
			startActivityForResult(licenseIntent, 1);
		} catch (Exception e) {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
		
		try {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			appId = extras.getString("appId");
		} catch (Exception e) {
			AuthActivity.this.setResult(5);
			finish();
		}

		// Android 6/7/8 is no longer supported
		String processName = getProcessName(this);

		try {
			WebView.setDataDirectorySuffix(processName);
		} catch (Exception ignored) {}
		
		content = (WebView) findViewById(R.id.auth_web);
		loadingScreen = (LinearLayout) findViewById(R.id.loading_page);
		loginScreen = (LinearLayout) findViewById(R.id.login_page);
		loadbg = (LinearLayout) findViewById(R.id.load_bg);

		SharedPreferences settings = this.getSharedPreferences("activity_core_settings", Context.MODE_PRIVATE);
		try {
			isDarkMode = settings.getString("dark_theme", null);
			if (isDarkMode.equals("true")) {
				loadbg.setBackgroundResource(R.drawable.ui_dialog_rounded_dark_v3);
			} else {
				loadbg.setBackgroundResource(R.drawable.ui_dialog_rounded_light);
			}
		} catch (Exception e) {
			isDarkMode = "true";
			loadbg.setBackgroundResource(R.drawable.ui_dialog_rounded_dark_v3);
		}

		content.setBackgroundColor(0x00000000);
		content.setVisibility(View.VISIBLE);
        content.getSettings().setJavaScriptEnabled(true);
        content.getSettings().setLoadWithOverviewMode(true);
        content.getSettings().setUseWideViewPort(true);
		content.getSettings().setCacheMode(WebSettings.LOAD_NORMAL);
        content.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				if (content.getUrl().contains("https://teslasoft.org/antiflood/validation")) {
					content.setVisibility(View.GONE);
					loginScreen.setVisibility(View.GONE);
					loadingScreen.setVisibility(View.VISIBLE);
				} else if (content.getUrl().contains("https://teslasoft.org/?pli=1")) {
					content.setVisibility(View.GONE);
					loginScreen.setVisibility(View.GONE);
					loadingScreen.setVisibility(View.VISIBLE);
					if (isDarkMode.equals("true")) {
						content.loadUrl("https://teslasoft.org/ServiceLogin?did=" + did + "&lang=" + lang + "&c=" + appId + "&theme=dark");
					} else {
						content.loadUrl("https://teslasoft.org/ServiceLogin?did=" + did + "&lang=" + lang + "&c=" + appId + "&theme=light");
					}
				} else if (content.getUrl().contains("https://teslasoft.org/ServiceLoginComplete?")) {
					loadingScreen.setVisibility(View.VISIBLE);
					loginScreen.setVisibility(View.GONE);
					content.setVisibility(View.GONE);
					try {
						String xurl = content.getUrl();
						new DownloadAccountInfo().execute(url);
					} catch (Exception e) {
						com.teslasoft.jarvis.auth.AuthActivity.this.setResult(4);
						finish();
					}
				} else {
					loadingScreen.setVisibility(View.GONE);
					loginScreen.setVisibility(View.VISIBLE);
					content.setVisibility(View.VISIBLE);
				}
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				com.teslasoft.jarvis.auth.AuthActivity.this.setResult(3);
				finish();
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});

		if (isDarkMode.equals("true")) {
			content.loadUrl("https://teslasoft.org/ServiceLogin?did=" + did + "&lang=" + lang + "&c=" + appId + "&theme=dark");
		} else {
			content.loadUrl("https://teslasoft.org/ServiceLogin?did=" + did + "&lang=" + lang + "&c=" + appId + "&theme=light");
		}
	}
	
	class DownloadAccountInfo extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected String doInBackground(String... urls) {
			try {
				URL url = new URL(urls[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();
				BufferedReader bufferedReader;
				bufferedReader = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
				return bufferedReader.readLine();
			} catch (Exception e) {
				AuthActivity.this.setResult(4);
				finish();
			}
			
			return null;
		}

		protected void onPostExecute(String data) {
			try {
				JSONObject response = new JSONObject(data);
				String email = response.getString("user_email");
				String uid = response.getString("user_id");
				String token = response.getString("auth_token");
				String username = response.getString("user_name");
				AccountManager accountManager = AccountManager.get(AuthActivity.this);
				Account account = new Account(username,"org.teslasoft.id.JARVIS_ACCOUNT");
				accountManager.addAccountExplicitly(account,null,null);
				accountManager.setAuthToken(account, "org.teslasoft.id.AUTH_TOKEN", token);
				accountManager.setPassword(account, token);
				accountManager.setUserData(account, "auth_token", token);
				accountManager.setUserData(account, "user_id", uid);
				accountManager.setUserData(account, "user_email", email);
				accountManager.setUserData(account, "user_name", username);
				AccountManager am =  (AccountManager)AuthActivity.this.getSystemService(Context.ACCOUNT_SERVICE);
				String verifyAccount = am.getUserData(account, "auth_token");

				AuthActivity.this.setResult(token.equals(verifyAccount) ? Activity.RESULT_OK : 4);
				finish();
			} catch (JSONException e) {
				AuthActivity.this.setResult(4);
				finish();
			}
		}
	}
	
	public void DismissDialogActivity(View v) {}
	
	public void Ignore(View v) {}
	
	@Override
	public void onBackPressed() {
		if (content.canGoBack()) {
			content.goBack();
		} else {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		this.setResult(resultCode == Activity.RESULT_OK ? Activity.RESULT_OK : Activity.RESULT_CANCELED);

		if (resultCode != Activity.RESULT_OK)
			finishAndRemoveTask();
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
