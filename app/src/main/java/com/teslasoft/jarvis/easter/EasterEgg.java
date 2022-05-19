package com.teslasoft.jarvis.easter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.teslasoft.libraries.support.R;

public class EasterEgg extends Activity {
	
	private WebView mWebview;
	private Animation mFadeInAnimation;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_IMMERSIVE |
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
				View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
				View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
				View.SYSTEM_UI_FLAG_FULLSCREEN
		);
		
		super.onCreate(savedInstanceState);

		String processName = getProcessName(this);
		try {
			mWebview.setDataDirectorySuffix(processName);
		} catch (Exception ignored) {}
		
		setContentView(R.layout.activity_easter_egg);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
		mFadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
		Animation mFadeOutAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        mFadeInAnimation.setAnimationListener(animationFadeInListener);
        mFadeOutAnimation.setAnimationListener(animationFadeOutListener);
		
		mWebview = (WebView) findViewById(R.id.web);
		
		mWebview.setVisibility(View.GONE);
		mWebview.setAlpha(0.f);
		
        mWebview.getSettings().setJavaScriptEnabled(true);
        mWebview.setWebViewClient(new WebViewClient() {
			@SuppressWarnings("deprecation")
			@Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {}
			
			public void onPageFinished(WebView view, String url)
			{
				mWebview.setVisibility(View.VISIBLE);
				final Handler handler = new Handler();
				handler.postDelayed(() -> {
					mWebview.setAlpha(1.f);
					mWebview.startAnimation(mFadeInAnimation);
				}, 1000);
			}
		});

        mWebview.loadUrl("file:///android_asset/www/index.html");
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
	
	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		mWebview.clearAnimation();
	}
	
	Animation.AnimationListener animationFadeOutListener = new Animation.AnimationListener() {
		@Override
		public void onAnimationEnd(Animation animation) {}
		
		@Override
		public void onAnimationRepeat(Animation animation) {}
		
		@Override
		public void onAnimationStart(Animation animation) {}
	};

	Animation.AnimationListener animationFadeInListener = new Animation.AnimationListener()
	{
		@Override
		public void onAnimationEnd(Animation animation) {}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
	};

	@Override
	public void onBackPressed() {
		final Handler handler = new Handler();
		handler.postDelayed(() -> {
			finish();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		}, 200);
	}
}
