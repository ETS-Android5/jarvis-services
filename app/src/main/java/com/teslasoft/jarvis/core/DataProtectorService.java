package com.teslasoft.jarvis.core;

import android.os.IBinder;
import android.os.Handler;
import android.content.Context;
import android.app.Service;
import android.content.Intent;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.widget.Toast;
import java.util.Timer;
import java.util.TimerTask;

// import android.support.v4.content.ContextCompat; /* DEPRECATED API */
// import android.support.v4.app.NotificationCompat; /* DEPRECATED API */
// import android.support.v4.app.NotificationCompat.Builder; /* DEPRECATED API */


public class DataProtectorService extends Service
{
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}
	
	Timer timer = new Timer();

	public void start(Context context)
	{
		// startService(new Intent(this, com.teslasoft.jarvis.core.DataProtectorService.class));

		/*if (android.os.Build.VERSION.SDK_INT >= 21)
		{
			if (android.os.Build.VERSION.SDK_INT >= 26)
			{
				// Notification for Android 8 and higer
				int notifyID = 2; 
				String CHANNEL_ID = "CoreDebugServices"; // The id of the channel. 
				CharSequence name = "Services"; // The user-visible name of the channel.
				int importance = NotificationManager.IMPORTANCE_HIGH;
				NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
				Intent notificationIntent = new Intent(this, com.teslasoft.jarvis.core.DPSSettingsActivity.class);
				Intent alls = new Intent(this, com.teslasoft.jarvis.core.ServicesActivity.class);

				PendingIntent serv = PendingIntent.getActivity(this, 0, alls, 0);
        		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
				Notification notification = new Notification.Builder(context)
					.setContentTitle("Debug")
					.setVisibility(Notification.VISIBILITY_PUBLIC)
					.setContentText("Data Protector Service has started")
					.setSmallIcon(R.drawable.app_icon)
					.setColor(ContextCompat.getColor(context, R.color.dGreen))
					.addAction(R.drawable.app_icon, "All services", serv)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon))
					.setContentIntent(pendingIntent)
					.setWhen(System.currentTimeMillis())
					.setChannelId(CHANNEL_ID)
					.setSound(null, null)
					.setVibrate(new long[] {0})
					.build();

				NotificationManager mNotificationManager =
					(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				mNotificationManager.createNotificationChannel(mChannel);
				startForeground(notifyID, notification);
			}

			else
			{
				// Notification for Android 7 and lower

				int notifyID = 2; 
				Intent notificationIntent = new Intent(this, com.teslasoft.jarvis.core.DPSSettingsActivity.class);
				Intent alls = new Intent(this, com.teslasoft.jarvis.core.ServicesActivity.class);

				PendingIntent serv = PendingIntent.getActivity(this, 0, alls, 0);
        		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

				NotificationCompat.Builder builder =
					new NotificationCompat.Builder(this)
					.setContentTitle("Debug")
					.setVisibility(Notification.VISIBILITY_PUBLIC)
					.setContentText("Data Protector Service has started")
					.setSmallIcon(R.drawable.app_icon)
					.setColor(ContextCompat.getColor(context, R.color.dGreen))
					.addAction(R.drawable.app_icon, "All services", serv)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.jarvis2))
					.setContentIntent(pendingIntent)
					.setWhen(System.currentTimeMillis())
					.setOngoing(true)
					.setVibrate(new long[] {0});

				Notification notification = builder.build();

				NotificationManager notificationManager =
					(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				// mNotificationManager.createNotificationChannel(mChannel);
				startForeground(notifyID, notification);

				stopService(new Intent(this, com.teslasoft.jarvis.core.InitService.class));
				startService(new Intent(this, com.teslasoft.jarvis.core.InitService.class));
			}
		}

		else {
			// Detect unsupported devices and stopSelf
			Log.e("DataProtectorService", "Sorry, but your Android version is not supported. Minimal version android is Android 6.0 (Lolipop) SDK 21. Jarvis Services closed with exit code -1");
			stopSelf();
			System.exit(-1);
		}*/
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO: Implement this method
		// Timer timer = new Timer();
		
		try {
			start(com.teslasoft.jarvis.core.DataProtectorService.this);
		}
		catch (Exception e)
		{

		}
		
		timer.scheduleAtFixedRate(new TimerTask()
		{
			@Override
			public void run()
			{
				// If Data Protector Service stopped show error
				PackageManager pm = getPackageManager();

				
				if (isMyServiceRunning(com.teslasoft.jarvis.core.InitService.class))
				{
					// If Data Protector Service running
				}

				else 
				{
					/*Intent i = new Intent(Intent.ACTION_MAIN);
					i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					i.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.core.ErrorServiceActivity"));
					i.addCategory(Intent.CATEGORY_LAUNCHER);
					startActivity(i);
					// timer.cancel();
					stopSelf();*/
					try {
						startService(new Intent(com.teslasoft.jarvis.core.DataProtectorService.this, com.teslasoft.jarvis.core.InitService.class));
					}
					catch (Exception e)
					{
						
					}
				}
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
				try {
					Toast.makeText(com.teslasoft.jarvis.core.DataProtectorService.this, text, Toast.LENGTH_SHORT).show();
				}
				catch (Exception e)
				{
					
				}
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
