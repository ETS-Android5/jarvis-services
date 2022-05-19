package com.teslasoft.jarvis.core;

import android.app.ActivityManager;
import android.app.Service;
import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import java.util.Timer;
import java.util.TimerTask;

public class DataProtectorService extends Service {
	@Override
	public IBinder onBind(Intent p1) {
		return null;
	}
	
	Timer timer = new Timer();

	public void start(Context context) {
		// TODO: Implement ...
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		try {
			start(com.teslasoft.jarvis.core.DataProtectorService.this);
		} catch (Exception ignored) {}
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				PackageManager pm = getPackageManager();
				
				if (!isMyServiceRunning(com.teslasoft.jarvis.core.InitService.class)) {
					try {
						startService(new Intent(com.teslasoft.jarvis.core.DataProtectorService.this, com.teslasoft.jarvis.core.InitService.class));
					} catch (Exception ignored) {}
				}
			}
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
