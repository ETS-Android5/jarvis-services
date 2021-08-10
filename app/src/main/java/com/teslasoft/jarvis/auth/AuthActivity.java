package com.teslasoft.jarvis.auth;

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
import java.net.URL;
import java.net.URLConnection;
import android.os.AsyncTask;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.Manifest;
import java.io.File;
import java.io.FileWriter;
import android.os.Environment;

public class AuthActivity extends Activity
{
	private WebView content;
	private String lang;
	private String did;
	private String appId;
	
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
	};
	
	public static void verifyStoragePermissions(Activity activity) {
		int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permission != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
		}
	}
	
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
		
		verifyStoragePermissions(this);
		
		String config = "/mnt/sdcard/jarvis/auth/" + appId + "/credentials.json";
		String folder = "jarvis/auth/" + appId;
		
		File root = new File(Environment.getExternalStorageDirectory(), folder);
		if (!root.exists()) {
			root.mkdirs();
		}

		try {
			File filepath = new File(root, "credentials.json");
			FileWriter writer = new FileWriter(filepath);
			writer.append("");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			AuthActivity.this.setResult(4);
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
				
				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream("/mnt/sdcard/jarvis/auth/" + appId + "/credentials.json");

				byte data[] = new byte[1024];
				
				long total = 0;
				
				while ((count = input.read(data)) != -1) {
					total += count;
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				AuthActivity.this.setResult(4);
				finishAndRemoveTask();
			}
			
			return null;
		}

		protected void onPostExecute(String data) {
			AuthActivity.this.setResult(Activity.RESULT_OK);
			finishAndRemoveTask();
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
