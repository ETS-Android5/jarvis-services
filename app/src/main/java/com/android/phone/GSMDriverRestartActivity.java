package com.android.phone;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import com.teslasoft.libraries.support.R;
import android.content.Intent;
import android.widget.Toast;
import android.content.ComponentName;

public class GSMDriverRestartActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gsm_driver_restart);
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				try {
					Intent intent = new Intent(Intent.ACTION_MAIN);
					intent.setComponent(new ComponentName("com.android.phone", "com.android.phone.NetworkSelectSettingActivity"));
					intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
					startActivity(intent);
					
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
					Toast.makeText(GSMDriverRestartActivity.this, "No GSM driver found", Toast.LENGTH_LONG).show();
					
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
		}, 100);
	}

	@Override
	public void onBackPressed()
	{
		
	}
}
