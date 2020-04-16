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

public class PreAuthActivity extends Activity
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
		text.setText("Sign in...");
		
		try
		{
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						Intent i = new Intent(com.teslasoft.jarvis.auth.PreAuthActivity.this, com.teslasoft.jarvis.auth.AuthActivity.class);
						startActivity(i);
					}
					
					catch (Exception j)
					{
						Toast toast = Toast.makeText(getApplicationContext(), "Service unavaliable", Toast.LENGTH_SHORT); 
						toast.show();

						try
						{
							stopService(new Intent(com.teslasoft.jarvis.auth.PreAuthActivity.this, com.teslasoft.jarvis.auth.AuthService.class));
							finish();
						}

						catch (Exception er)
						{
							finish();
						}
					}
				}
			}, 300);
		}

		catch (Exception e)
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Internal error", Toast.LENGTH_SHORT); 
			toast.show();

			try
			{
				stopService(new Intent(com.teslasoft.jarvis.auth.PreAuthActivity.this, com.teslasoft.jarvis.auth.AuthService.class));
				finish();
			}

			catch (Exception er)
			{
				finish();
			}
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
		// TODO: Implement this method
		// super.onBackPressed();
		// finishAffinity();
	}
}
