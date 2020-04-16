package com.teslasoft.jarvis.crashreport;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import com.teslasoft.libraries.support.R;

public class Report extends Activity
{
	// private Intent intent;
	private String errt;
	
	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_collect);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		
		try
		{
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			errt = extras.getString("errz");
		}
		
		/*android.app.RemoteServiceException: Context.startForegroundService() did not then call Service.startForeground():*/
		
		catch (Exception e)
		{
			errt = "-676767";
			Toast toast = Toast.makeText(getApplicationContext(), "Value \"null\" is not applicable to method extras.getString()", Toast.LENGTH_SHORT); 
			toast.show();
			finish();
			overridePendingTransition(0, 0);
		}
		
		try
		{
			if (errt.contains("android.app.RemoteServiceException: Context.startForegroundService() did not then call Service.startForeground():"))
			{
				finish();
				overridePendingTransition(0, 0);
			}

			else
			{
				final Handler handler = new Handler();
				handler.postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						if (errt == "-676767")
						{
							finish();
							overridePendingTransition(0, 0);
						}
						
						else
						{
							try
							{
								Intent i = new Intent(com.teslasoft.jarvis.crashreport.Report.this, com.teslasoft.jarvis.crashreport.BugReport.class);
								Bundle extras = new Bundle();
								extras.putString("errtext", errt);
								i.putExtras(extras);
								startActivity(i);
								finish();
								overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
							}
					
							catch (Exception e)
							{
								finish();
								overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
								Toast toast = Toast.makeText(getApplicationContext(), "Service not avaliable", Toast.LENGTH_SHORT); 
								toast.show();
							}
						}
					}
				}, 800);
			}
		}
		
		catch (Exception errn)
		{
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					if (errt == "-676767")
					{
						finish();
						overridePendingTransition(0, 0);
					}

					else
					{
						try
						{
							Intent i = new Intent(com.teslasoft.jarvis.crashreport.Report.this, com.teslasoft.jarvis.crashreport.BugReport.class);
							Bundle extras = new Bundle();
							extras.putString("errtext", errt);
							i.putExtras(extras);
							startActivity(i);
							finish();
							overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
						}

						catch (Exception e)
						{
							finish();
							overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
							Toast toast = Toast.makeText(getApplicationContext(), "Service not avaliable", Toast.LENGTH_SHORT); 
							toast.show();
						}
					}
				}
			}, 800);
		}
	}

	public void DismissDialogActivity(View v)
	{
		finish();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}

	public void Ignore(View v)
	{
		//Do nothing
	}

	@Override
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
	}
}
