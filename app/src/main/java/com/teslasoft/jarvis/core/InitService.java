package com.teslasoft.jarvis.core;

import android.app.Service;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.NotificationChannel;
import android.app.Notification;
import android.app.ActivityManager;
import android.os.IBinder;
import android.os.Handler;
import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;

import androidx.core.content.ContextCompat;

import java.util.Timer;
import java.util.TimerTask;

import com.teslasoft.libraries.support.R;

public class InitService extends Service {
	@Override
	public IBinder onBind(Intent p1) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
	public void start(Context context) {
		if (isMyServiceRunning(com.teslasoft.jarvis.core.AutoRunService.class)) {
			try {
				stopService(new Intent(com.teslasoft.jarvis.core.InitService.this, com.teslasoft.jarvis.core.AutoRunService.class));
			} catch (Exception ignored) {}
		}

		// Android 6/7/8 is no longer supported
		int notifyID = 1;
		String CHANNEL_ID = "CoreDebugServices";
		CharSequence name = "Services";
		int importance = NotificationManager.IMPORTANCE_HIGH;
		NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
		Intent notificationIntent = new Intent(this, com.teslasoft.jarvis.core.ServiceSettingActivity.class).putExtra("serviceId", 0);
		Intent alls = new Intent(this, com.teslasoft.jarvis.core.ServicesActivity.class);

		PendingIntent serv = PendingIntent.getActivity(this, 0, alls, PendingIntent.FLAG_IMMUTABLE);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);
		Notification notification = new Notification.Builder(context)
				.setContentTitle("Debug")
				.setVisibility(Notification.VISIBILITY_PUBLIC)
				.setContentText("Teslasoft Core is running...")
				.setSmallIcon(R.drawable.app_icon)
				.setColor(ContextCompat.getColor(context, R.color.dGreen))
				.addAction(R.drawable.app_icon, "All services", serv)
				.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.teslasoft_logo))
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
	
	private static final String TAG = "InitService";
	
	@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			start(com.teslasoft.jarvis.core.InitService.this);
		} catch (Exception ignored) {}
		
        final Handler handler = new Handler();
		handler.postDelayed(() -> {
			Timer timer = new Timer();
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					PackageManager pm = getPackageManager();

					if (pm.getComponentEnabledSetting(new ComponentName(InitService.this, DataProtectorService.class)) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
						if (!isMyServiceRunning(DataProtectorService.class)) {
							try {
								startService(new Intent(InitService.this, DataProtectorService.class));
							} catch (Exception ignored) {}
						}
					}
				}
			}, 500, 100);
		}, 10);
		
		return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sendBroadcast(new Intent("com.teslasoft.jarvis.RESTART_INIT_CORE_SERVICE"));
    }
	
	public boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		try {
			for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
				if (serviceClass.getName().equals(service.service.getClassName())) {
					return true;
				}
			}
		} catch (Exception ignored) {}
		return false;
	}
}
