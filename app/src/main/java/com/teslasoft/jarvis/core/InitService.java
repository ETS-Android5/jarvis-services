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
import androidx.core.content.ContextCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationCompat.Builder;
// import android.support.v4.content.ContextCompat; /* DEPRECATED API */
// import android.support.v4.app.NotificationCompat; /* DEPRECATED API */
// import android.support.v4.app.NotificationCompat.Builder; /* DEPRECATED API */
import android.graphics.BitmapFactory;

public class InitService extends Service
{
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
	}
	
	public void start(Context context)
	{
		// startService(new Intent(this, com.teslasoft.jarvis.core.DataProtectorService.class));
		
		if (isMyServiceRunning(com.teslasoft.jarvis.core.AutoRunService.class))
		{
			try {
				stopService(new Intent(com.teslasoft.jarvis.core.InitService.this, com.teslasoft.jarvis.core.AutoRunService.class));
			}
			catch (Exception e)
			{

			}
		}

		else 
		{
			
		}
		
		if (android.os.Build.VERSION.SDK_INT >= 21)
		{
			if (android.os.Build.VERSION.SDK_INT >= 26)
			{
				// Notification for Android 8 and higer
				int notifyID = 1; 
				String CHANNEL_ID = "CoreDebugServices"; // The id of the channel. 
				CharSequence name = "Services"; // The user-visible name of the channel.
				int importance = NotificationManager.IMPORTANCE_HIGH;
				NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
				Intent notificationIntent = new Intent(this, com.teslasoft.jarvis.core.CoreServiceSettingsActivity.class);
				Intent alls = new Intent(this, com.teslasoft.jarvis.core.ServicesActivity.class);
		
				PendingIntent serv = PendingIntent.getActivity(this, 0, alls, 0);
        		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
				Notification notification = new Notification.Builder(context)
					.setContentTitle("Debug")
					.setVisibility(Notification.VISIBILITY_PUBLIC)
					.setContentText("Jarvis Core has started")
					.setSmallIcon(R.drawable.app_icon)
					.setColor(ContextCompat.getColor(context, R.color.dGreen))
					.addAction(R.drawable.app_icon, "All services", serv)
					.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.jarvis2))
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
				
				int notifyID = 1; 
				Intent notificationIntent = new Intent(this, com.teslasoft.jarvis.core.CoreServiceSettingsActivity.class);
				Intent alls = new Intent(this, com.teslasoft.jarvis.core.ServicesActivity.class);

				PendingIntent serv = PendingIntent.getActivity(this, 0, alls, 0);
        		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
				
				NotificationCompat.Builder builder =
					new NotificationCompat.Builder(this)
					.setContentTitle("Debug")
					.setVisibility(Notification.VISIBILITY_PUBLIC)
					.setContentText("Jarvis Core has started")
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
			Log.e(TAG, "Sorry, but your Android version is not supported. Minimal version android is Android 6.0 (Lolipop) SDK 21. Jarvis Services closed with exit code -1");
			stopSelf();
			System.exit(-1);
		}
	}
	
	private static final String TAG = "InitService";
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId)
	{
		// Catch SystemDeathException: executing Jarvis Service after stopping app
		/*try {
			stopService(new Intent(com.teslasoft.jarvis.core.InitService.this, com.teslasoft.jarvis.core.UpdateCompleteService.class));
			stopService(new Intent(com.teslasoft.jarvis.core.InitService.this, com.teslasoft.jarvis.core.AutoRunService.class));
		}
		catch (Exception e)
		{
			
		}*/
		
		try {
			start(com.teslasoft.jarvis.core.InitService.this);
		}
		catch (Exception e)
		{

		}
		
        final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				// toast("Служба Jarvis Core Init перезапущена", com.teslasoft.jarvis.core.InitService.this);
				
				//mNotificationManager.notify(notifyID , notification);

				Timer timer = new Timer();
				timer.scheduleAtFixedRate(new TimerTask()
				{
					@Override
					public void run()
					{
						// If Data Protector Service stopped show error
						PackageManager pm = getPackageManager();

						if (pm.getComponentEnabledSetting(new ComponentName(com.teslasoft.jarvis.core.InitService.this, com.teslasoft.jarvis.core.DataProtectorService.class)) == 1)
						{
							if (isMyServiceRunning(com.teslasoft.jarvis.core.DataProtectorService.class))
							{
								// If Data Protector Service running
							}

							else
							{
								try {
									startService(new Intent(com.teslasoft.jarvis.core.InitService.this, com.teslasoft.jarvis.core.DataProtectorService.class));
								}
								catch (Exception e)
								{
									
								}
							}
						}
					}
				}, 500, 100);
			}
		}, 10);
		
		return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent("com.teslasoft.jarvis.RESTART_INIT_CORE_SERVICE"));
    }
	
	/*@Override
    public void onTaskRemoved(Intent rootIntent) {
        //create an intent that you want to start again.
        Intent intent = new Intent(getApplicationContext(), com.teslasoft.jarvis.core.InitService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 5000, pendingIntent);
        super.onTaskRemoved(rootIntent);
    }*/
	
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

	public Handler mHandler = new Handler();

	public void toast(final CharSequence text, final Context context)
	{
        mHandler.post(new Runnable()
		{
			@Override public void run()
			{
				try {
					Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
				}
				catch (Exception e)
				{
					
				}
			}
		});
    }
}
