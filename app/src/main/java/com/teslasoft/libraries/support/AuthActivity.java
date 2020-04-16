package com.teslasoft.libraries.support;

import android.os.*;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.util.*;
import android.net.*;
import android.support.v7.appcompat.*;
import android.graphics.*;
import android.webkit.*;
import android.annotation.*;
import android.support.v7.app.*;
import android.content.pm.*;
import java.text.*;
import android.support.v4.*;
import android.support.v4.view.*;
import android.support.v4.app.*;

public class AuthActivity extends Activity
{
	private LinearLayout next;
	private LinearLayout prev;
	private ProgressBar status;
	private TextView title;
	private WebView content;
	
	private View.OnClickListener prev_listener = new View.OnClickListener() {
		public void onClick(View v) {
			Prev(prev);
		}
	};
	
	private View.OnClickListener next_listener = new View.OnClickListener() {
		public void onClick(View v) {
			Next(next);
		}
	};
	
	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        setContentView(R.layout.auth);
		startService(new Intent(AuthActivity.this, AuthService.class));
		
		next = (LinearLayout) findViewById(R.id.next);
		prev = (LinearLayout) findViewById(R.id.prev);
		status = (ProgressBar) findViewById(R.id.status);
		title = (TextView) findViewById(R.id.title);
		
		next.setOnClickListener(next_listener);
		prev.setOnClickListener(prev_listener);
		
		disable_prev();
		hide_prev();
		disable_next();
		
		/*// content = (WebView) findViewById(R.id.content);

		content.getSettings().setJavaScriptEnabled(true);
		// final Activity activity = this;
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
				overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
				setContentView(R.layout.auth_webview);
				overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
				
			}
		});

		content.loadUrl("https://jarvis.studio/service/login.php?gws=0");
		
		*/
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				
			}
		}, 4000);
    }
	
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
	
	public void Next(View v)
	{
		
	}
	
	public void Prev(View v)
	{
		
	}
	
	public void hide_next()
	{
		next = (LinearLayout) findViewById(R.id.next);
		next.setVisibility(View.GONE);
	}
	
	public void hide_prev()
	{
		prev = (LinearLayout) findViewById(R.id.prev);
		prev.setVisibility(View.GONE);
	}
	
	public void show_next()
	{
		next = (LinearLayout) findViewById(R.id.next);
		next.setVisibility(View.VISIBLE);
	}

	public void show_prev()
	{
		prev = (LinearLayout) findViewById(R.id.prev);
		prev.setVisibility(View.VISIBLE);
	}
	
	public void disable_next()
	{
		next = (LinearLayout) findViewById(R.id.next);
		next.setEnabled(false);
		next.setAlpha(0.4f);
	}

	public void disable_prev()
	{
		prev = (LinearLayout) findViewById(R.id.prev);
		prev.setEnabled(false);
		prev.setAlpha(0.4f);
	}
	
	public void enable_next()
	{
		next = (LinearLayout) findViewById(R.id.next);
		next.setEnabled(true);
		next.setAlpha(1.f);
	}

	public void enable_prev()
	{
		prev = (LinearLayout) findViewById(R.id.prev);
		prev.setEnabled(true);
		prev.setAlpha(1.f);
	}
}
