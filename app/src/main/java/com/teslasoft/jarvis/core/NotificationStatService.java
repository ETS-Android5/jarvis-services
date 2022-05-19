package com.teslasoft.jarvis.core;

import android.app.Service;
import android.app.ActivityManager;
import android.os.IBinder;
import android.content.Context;
import android.content.Intent;

import java.util.Timer;
import java.util.TimerTask;

public class NotificationStatService extends Service {
	@Override
	public IBinder onBind(Intent p1) {
		return null;
	}

	Timer timer = new Timer();

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {}
		}, 100, 100);

		return START_STICKY;
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
