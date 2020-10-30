package com.teslasoft.jarvis.easter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Build;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Context;
import com.teslasoft.libraries.support.R;
import android.graphics.Color;
import android.view.View.*;
import android.view.View;
import android.content.pm.PackageInfo;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceError;
import android.annotation.TargetApi;
import java.util.TimerTask;

public class EasterEgg extends Activity
{
	
	private WebView mWebview;
	private TimerTask anim;
	private float alph;
	private Animation mFadeInAnimation, mFadeOutAnimation;
	
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		alph = 0;
		
		super.onCreate(savedInstanceState);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
		{
            String processName = getProcessName(this);
			try {
				mWebview.setDataDirectorySuffix(processName);
			}

			catch (Exception e)
			{

			}
            /*if (!"com.teslasoft.libraries.support:adc".equals(processName))
			 {
			 ads.setDataDirectorySuffix(processName);
			 }*/
		}
		
		setContentView(R.layout.egg);
		
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
		mFadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        mFadeOutAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        mFadeInAnimation.setAnimationListener(animationFadeInListener);
        mFadeOutAnimation.setAnimationListener(animationFadeOutListener);
		
		mWebview = (WebView) findViewById(R.id.web);
		
		mWebview.setVisibility(View.GONE);
		mWebview.setAlpha(0.f);
		
		//mWebview.startAnimation(mFadeInAnimation);
		
        mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript
        final Activity activity = this;
        mWebview.setWebViewClient(new WebViewClient()
		{
			@SuppressWarnings("deprecation")
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
			{
				//Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
			}

			@TargetApi(android.os.Build.VERSION_CODES.M)
			@Override
			public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr)
			{
				
			}
			
			public void onPageFinished(WebView view, String url)
			{
				// do your stuff here
				mWebview.setVisibility(View.VISIBLE);
				final Handler handler = new Handler();
				handler.postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						mWebview.setAlpha(1.f);
						mWebview.startAnimation(mFadeInAnimation);
					}
				}, 1000);
			}
		});

        mWebview.loadUrl("file:///android_asset/www/index.html");
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
	
	@Override
	protected void onPause()
	{
		super.onPause();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		mWebview.clearAnimation();
	}
	
	Animation.AnimationListener animationFadeOutListener = new Animation.AnimationListener()
	{
		@Override
		public void onAnimationEnd(Animation animation)
		{
			//mWebview.startAnimation(mFadeInAnimation);
		}
		
		@Override
		public void onAnimationRepeat(Animation animation)
		{
			// TODO Auto-generated method stub
		}
		
		@Override
		public void onAnimationStart(Animation animation)
		{
			// TODO Auto-generated method stub
		}
	};

	Animation.AnimationListener animationFadeInListener = new Animation.AnimationListener()
	{
		@Override
		public void onAnimationEnd(Animation animation)
		{
			//mWebview.startAnimation(mFadeOutAnimation);
		}

		@Override
		public void onAnimationRepeat(Animation animation)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void onAnimationStart(Animation animation)
		{
			// TODO Auto-generated method stub
		}
	};

	@Override
	public void onBackPressed()
	{
		// mWebview.startAnimation(mFadeOutAnimation);
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				finish();
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		}, 200);
	}
	
	/*private boolean isMyServiceRunning(Class<?> serviceClass)
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
	}*/
}
