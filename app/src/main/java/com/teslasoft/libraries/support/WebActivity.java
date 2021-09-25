package com.teslasoft.libraries.support;

import android.os.*;
import android.app.*;
import android.content.*;
import android.net.*;
import android.webkit.WebView;
import java.lang.*;
import android.app.Activity;
import android.webkit.*;
import android.annotation.*;

public class WebActivity extends Activity
{
	private WebView content;
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);

		try {
			Intent licenseIntent = new Intent(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class);
			startActivityForResult(licenseIntent, 1);
		} catch (Exception e) {
			// User tried to disable or bypass license checking service, exit
			this.setResult(Activity.RESULT_CANCELED);
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

			}
            /*if (!"com.teslasoft.libraries.support:adc".equals(processName))
			 {
			 ads.setDataDirectorySuffix(processName);
			 }*/
		}
		
        setContentView(R.layout.auth_webview);
		
		content = (WebView) findViewById(R.id.content);
		
		content.setBackgroundColor(0xFF212121);
        content.getSettings().setJavaScriptEnabled(true);
        content.getSettings().setLoadWithOverviewMode(true);
        content.getSettings().setUseWideViewPort(true);
		
        final Activity activity = this;
		
        content.setWebViewClient(new WebViewClient()
		{
			@SuppressWarnings("deprecation")
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
			{
				
			}
			
			@TargetApi(android.os.Build.VERSION_CODES.M)
			@Override
			public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr)
			{
				// Redirect to deprecated method, so you can use it in all SDK versions
				// onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
			}

			public void onPageFinished(WebView view, String url)
			{
					
			}
			
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

	/* Piracy check starts */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			// License check passed
		} else {
			// License check failed, exit
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}
	/* Piracy check ends */

	/*@Override
	public void onBackPressed()
	{
		finish();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}*/
	
	private boolean isMyServiceRunning(Class<?> serviceClass)
	{
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
		{
			if (serviceClass.getName().equals(service.service.getClassName()))
			{
				return true;
			}
		}
		return false;
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
