package com.teslasoft.jarvis.ads;

import android.os.*;
import android.app.*;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClient.Builder;
import com.android.billingclient.api.BillingClient.*;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.BillingResults;
import com.android.billingclient.api.BillingResult.*;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.*;
import android.content.*;
import com.teslasoft.libraries.support.R;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.util.*;
import android.net.*;
import android.support.v7.appcompat.*;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.JavascriptInterface;
import android.view.LayoutInflater;
import java.lang.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import android.app.Activity;
import android.transition.Scene;
import android.app.Instrumentation;
import android.webkit.*;
import android.annotation.*;
import android.view.animation.Animation.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class AdWideActivity extends Activity
{
	// Buy adfree version
	private BillingClient billingClient;

	// "intent" is redirector
	private Intent intent;

	// "adid" is extra string with value of Extras adId
	private String adid;

	// "ads" is ad
	private WebView ads;
	
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);

		// Lagfix for Andriod 9
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
		{
            String processName = getProcessName(this);

			try {
				ads.setDataDirectorySuffix(processName);
			}

			catch (Exception e)
			{

			}

            /*if (!"com.teslasoft.libraries.support:adc".equals(processName))
			 {
			 ads.setDataDirectorySuffix(processName);
			 }*/
		}
		
		setContentView(R.layout.adwide);
		ads = (WebView) findViewById(R.id.ad_banner);
		
		ads.setBackgroundColor(0x00000000);

		ads.getSettings().setLoadWithOverviewMode(true);
		//ads.getSettings().setUseWideViewPort(true);
		ads.setVerticalScrollBarEnabled(false);
		ads.setHorizontalScrollBarEnabled(false);
		ads.setVisibility(View.GONE);
		ads.setAlpha(0);
		
		if (NetStat.getInstance(this).isOnline())
		{
			try {
				Intent intent = getIntent();
				Bundle extras = intent.getExtras();
				adid = extras.getString("adId");
				
				ads.getSettings().setJavaScriptEnabled(true);
				final Activity activity = this;

				ads.setWebViewClient(new WebViewClient()
				{
					public boolean shouldOverrideUrlLoading(WebView view, String url)
					{
						if (url != null && (url.startsWith("http://") || url.startsWith("https://"))) {
							view.getContext().startActivity(
									new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
							return true;
						} else {
							return false;
						}
					}
						
					// Combatibility with different Android versions
					@SuppressWarnings("deprecation")
					@Override
					public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
					{
						// If connection ckeck failed close ad
						finish();
						overridePendingTransition(0, 0);
					}

					// Combatibility with different Android versions
					@TargetApi(android.os.Build.VERSION_CODES.M)
					@Override
					public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr)
					{
						// If connection ckeck failed close ad
						finish();
						overridePendingTransition(0, 0);
					}

					public void onPageFinished(WebView view, String url)
					{
						// If connection ckeck successfull open ad
						ads.setVisibility(View.VISIBLE);

						// Debug gui
						final Handler handler = new Handler();
						handler.postDelayed(new Runnable()
						{
							@Override
							public void run()
							{
								ads.setAlpha(1.f);
							}
						}, 100);
					}
				});

				// Load advertisement
				ads.loadUrl("https://s2---sd-r56v7-g-ad.jarvis.studio/ad/mobile/wide_horizontal.php?id=".concat(adid));
			} catch (Exception e) {
				finish();
				overridePendingTransition(0, 0);
			}
		} else {
			finish();
			overridePendingTransition(0, 0);
		}
	}
	
	public void openAd(View v) {
		
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
