package com.teslasoft.jarvis.core;

import android.os.Process;
import android.os.IBinder;
import android.os.Handler;
import android.content.Context;
import android.app.Service;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.NotificationChannel;
import android.app.Notification;
import android.content.ComponentName;
import android.content.Intent;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;
import android.util.Log;
import com.teslasoft.libraries.support.R;
import com.teslasoft.jarvis.core.SystemLibrary;
import android.support.v4.content.ContextCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.graphics.BitmapFactory;

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
