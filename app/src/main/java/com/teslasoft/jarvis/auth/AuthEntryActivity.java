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

public class AuthEntryActivity extends Activity
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
		text.setText("Checking info...");
		
		try
		{
			startService(new Intent(com.teslasoft.jarvis.auth.AuthEntryActivity.this, com.teslasoft.jarvis.auth.AuthService.class));
			
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.setComponent(new ComponentName("com.teslasoft.jarvis.accounts","com.teslasoft.jarvis.accounts.CheckActivity"));
						intent.addCategory(Intent.CATEGORY_LAUNCHER);
						startActivity(intent);
						
						Intent i = new Intent(com.teslasoft.jarvis.auth.AuthEntryActivity.this, com.teslasoft.jarvis.auth.PreAuthActivity.class);
						startActivity(i);
						finish();
					}

					catch (Exception _e)
					{
						Toast toast = Toast.makeText(getApplicationContext(), "Service unavaliable", Toast.LENGTH_SHORT); 
						toast.show();

						try
						{
							stopService(new Intent(com.teslasoft.jarvis.auth.AuthEntryActivity.this, com.teslasoft.jarvis.auth.AuthService.class));
							finish();
						}

						catch (Exception er)
						{
							finish();
						}
					}
				}
			}, 4500);
		}
		
		catch (Exception j)
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Internal error", Toast.LENGTH_SHORT); 
			toast.show();
			finish();
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
		// super.onBackPressed();
	}
}
