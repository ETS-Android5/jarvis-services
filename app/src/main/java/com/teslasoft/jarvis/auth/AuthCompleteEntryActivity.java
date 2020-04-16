package com.teslasoft.jarvis.auth;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import com.teslasoft.libraries.support.R;
import android.content.ComponentName;

public class AuthCompleteEntryActivity extends Activity
{
	TextView text;
	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		setContentView(R.layout.add_accound_complete);
		text = (TextView) findViewById(R.id.text);
		text.setVisibility(View.GONE);
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				// startService(new Intent(com.teslasoft.jarvis.auth.AuthCompleteEntryActivity.this, com.teslasoft.jarvis.auth.AuthService.class));
			}
		}, 800);
	}

	public void DismissDialogActivity(View v)
	{
		// stopService(new Intent(com.teslasoft.jarvis.auth.AuthCompleteEntryActivity.this, com.teslasoft.jarvis.auth.AuthService.class));
		finishAndRemoveTask();
	}

	public void Ignore(View v)
	{
		// Do nothing
	}

	@Override
	public void onBackPressed()
	{
		// super.onBackPressed();
		// stopService(new Intent(com.teslasoft.jarvis.auth.AuthCompleteEntryActivity.this, com.teslasoft.jarvis.auth.AuthService.class));
		finishAndRemoveTask();
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		// finish();
	}
	
}
