package com.teslasoft.jarvis.core;

import android.os.Process;
import android.os.IBinder;
import android.os.Handler;
import android.content.Context;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class SystemLibrary extends Service
{

	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	
	public boolean isMyServiceRunning(Class<?> serviceClass)
	{
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		
		try {
			for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
			{
				if (serviceClass.getName().equals(service.service.getClassName()))
				{
					return true;
				}
			}
		}
		catch (Exception e)
		{

		}
		return false;
	}
	
	public Handler mHandler = new Handler();

	public void toast(final CharSequence text, final Context context)
	{
        mHandler.post(new Runnable()
		{
			@Override public void run()
			{
				Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
			}
		});
    }
}

