package com.teslasoft.jarvis.core;

import android.os.IBinder;
import android.os.Handler;
import android.content.Context;
import android.app.Service;
import android.content.Intent;
import android.app.ActivityManager;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationStatService extends Service
{
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	Timer timer = new Timer();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO: Implement this method
		// Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				
			}
		}, 100, 100);

		return START_STICKY;
	}

	final Handler mHandler = new Handler();

	void toast(final CharSequence text)
	{
        mHandler.post(new Runnable()
		{
			@Override public void run()
			{
					
			}
		});
    }

	public boolean isMyServiceRunning(Class<?> serviceClass)
	{
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		try
		{
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
}
