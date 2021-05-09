package com.android.server;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import com.teslasoft.libraries.support.R;
import android.content.Intent;
import android.widget.Toast;
import android.content.ComponentName;

public class RestartActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_restart);

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				try {
					Process proc = Runtime.getRuntime().exec("su -c killall system_server");

					final Handler handler = new Handler();
					handler.postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							finishAndRemoveTask();
						}
					}, 100);
				} catch (Exception e) {
					Toast.makeText(RestartActivity.this, "Access denied! [-1]", Toast.LENGTH_LONG).show();

					final Handler handler = new Handler();
					handler.postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							finishAndRemoveTask();
						}
					}, 100);
				}
			}
		}, 3000);
	}

	@Override
	public void onBackPressed()
	{

	}
}
