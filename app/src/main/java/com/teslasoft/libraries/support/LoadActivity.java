package com.teslasoft.libraries.support;

import android.os.Bundle;
import android.os.Process;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;

public class LoadActivity extends Activity
{	
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
		return;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
        setContentView(R.layout.load);
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					Intent i = new Intent(LoadActivity.this, net.jarvis.engine.TaskCdhActivity.class);
					startActivity(i);
					finish();
				}
				
				catch (Exception e)
				{
					onConfigChanged(-1);
				}
				
				final Handler handler = new Handler();
				handler.postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						onConfigChanged(1);
					}
				}, 100);
			}
		}, 1500);
    }
	
	private boolean isMyServiceRunning(Class<?> serviceClass)
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
	}
	
	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		// super.onBackPressed();
		Process.killProcess(Process.myPid());
	}
	
	public void onConfigChanged(int cc)
	{
		if (cc == 1)
		{
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setComponent(new ComponentName("com.teslasoft.jarvis","com.teslasoft.jarvis.MainActivity"));
			startActivity(intent);
			finish();
		}
		
		else if (cc == -1)
		{
			String err = "Configuration error: Can't start configuration check service. Try to reinstall this program or enable all components of this program.";
			Log.e("Jarvis Runtime", err);
			Process.killProcess(Process.myPid());
			System.exit(-1);
		}
		
		else
		{
			Process.killProcess(Process.myPid());
			System.exit(-1);
		}
	}
}
