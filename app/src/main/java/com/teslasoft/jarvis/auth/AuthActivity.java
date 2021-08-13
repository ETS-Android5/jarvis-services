package com.teslasoft.jarvis.auth;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import com.teslasoft.libraries.support.R;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.os.Build;
import android.content.Context;
import android.app.ActivityManager;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import android.os.AsyncTask;
import org.json.JSONException;
import org.json.JSONObject;

public class AuthActivity extends Activity
{
	private WebView content;
	private String lang;
	private String did;
	private String appId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		setContentView(R.layout.login_webview);
		
		try {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			appId = extras.getString("appId");
		} catch (Exception e) {
			AuthActivity.this.setResult(5);
			finishAndRemoveTask();
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
		{
            String processName = getProcessName(this);
			
			try {
				content.setDataDirectorySuffix(processName);
			}

			catch (Exception e)
			{
				// com.teslasoft.jarvis.auth.AuthActivity.this.setResult(4);
				// finishAndRemoveTask();
			}
		}
		
		content = (WebView) findViewById(R.id.auth_web);

		content.setBackgroundColor(0x00000000);
		content.setVisibility(View.VISIBLE);
        content.getSettings().setJavaScriptEnabled(true);
        content.getSettings().setLoadWithOverviewMode(true);
        content.getSettings().setUseWideViewPort(true);
		content.getSettings().setCacheMode(WebSettings.LOAD_NORMAL);
		
		final Activity activity = this;

        content.setWebViewClient(new WebViewClient()
		{
			@Override
			public void onPageFinished(WebView view, String url) {
				// SmartToast.create("Debug: Loaded", AuthActivity.this);
				if (content.getUrl().equals("https://teslasoft.org/antiflood/validation")) {
					content.setVisibility(View.GONE);
					// content.loadUrl("https://teslasoft.org/ServiceLogin?did=" + did + "&lang=" + lang + "&c=" + appId);
				} else if (content.getUrl().equals("https://teslasoft.org/?pli=1")) {
					content.setVisibility(View.GONE);
					content.loadUrl("https://teslasoft.org/ServiceLogin?did=" + did + "&lang=" + lang + "&c=" + appId);
				} else if (content.getUrl().contains("https://teslasoft.org/ServiceLoginComplete?")) {
					// SmartToast.create("Debug: Loaded final page", AuthActivity.this);
					content.setVisibility(View.GONE);
					try {
						String xurl = content.getUrl();
						new DownloadAccountInfo().execute(url);
						
					} catch (Exception e) {
						// SmartToast.create(e.toString(), com.teslasoft.jarvis.auth.AuthActivity.this);
						com.teslasoft.jarvis.auth.AuthActivity.this.setResult(4);
						finishAndRemoveTask();
					}
				} else {
					content.setVisibility(View.VISIBLE);
				}
				
				// SmartToast.create(content.getUrl(), AuthActivity.this);
			}
			
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				com.teslasoft.jarvis.auth.AuthActivity.this.setResult(3);
				finishAndRemoveTask();
				super.onReceivedError(view, errorCode, description, failingUrl);
			}
		});
		
		// SmartToast.create("Debug: OK", this);
        content.loadUrl("https://teslasoft.org/ServiceLogin?did=" + did + "&lang=" + lang + "&c=" + appId);
	}
	
	class DownloadAccountInfo extends AsyncTask<String, String, String> {
		private Exception exception;
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		protected String doInBackground(String... urls)
		{
			int count;
			try {
				URL url = new URL(urls[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				BufferedReader bufferedReader = null;
				bufferedReader = new BufferedReader(new
						InputStreamReader(conexion.getInputStream()));

				String result = bufferedReader.readLine();
				return result;
			} catch (Exception e) {
				AuthActivity.this.setResult(4);
				finishAndRemoveTask();
			}
			
			return null;
		}

		protected void onPostExecute(String data) {
			// SmartToast.create(data, AuthActivity.this);
			try {
				JSONObject response = new JSONObject(data);
				String email = response.getString("user_email");
				String uid = response.getString("user_id");
				String token = response.getString("auth_token");
				String username = response.getString("user_name");
				AccountManager accountManager = AccountManager.get(AuthActivity.this);
				Account account = new Account(email,"org.teslasoft.id.JARVIS_ACCOUNT");
				accountManager.addAccountExplicitly(account,null,null);
				accountManager.setAuthToken(account, "org.teslasoft.id.AUTH_TOKEN", token);
				accountManager.setPassword(account, token);
				accountManager.setUserData(account, "auth_token", token);
				accountManager.setUserData(account, "user_id", uid);
				accountManager.setUserData(account, "user_email", email);
				accountManager.setUserData(account, "user_name", username);

				AccountManager am =  (AccountManager)AuthActivity.this.getSystemService(Context.ACCOUNT_SERVICE);
				Account[] accounts = am.getAccountsByType("org.teslasoft.id.JARVIS_ACCOUNT");
				String verifyAccount = am.getUserData(account, "auth_token");
				if (token.equals(verifyAccount)) {
					// SmartToast.create("Authentication completed", AuthActivity.this);
					AuthActivity.this.setResult(Activity.RESULT_OK);
					finishAndRemoveTask();
				} else {
					AuthActivity.this.setResult(4);
					finishAndRemoveTask();
				}
			} catch (JSONException e) {
				AuthActivity.this.setResult(4);
				finishAndRemoveTask();
			}
		}
	}
	
	public void DismissDialogActivity(View v)
	{
		
	}
	
	public void Ignore(View v)
	{
		// Do nothing
	}
	
	@Override
	public void onBackPressed()
	{
		if (content.canGoBack()) {
			content.goBack();
		} else {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}
	
	public String getProcessName(Context context)
	{
        if (context == null) return null;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses())
		{
            if (processInfo.pid == android.os.Process.myPid())
			{
                return processInfo.processName;
            }
        }

		return null;
	}
}
