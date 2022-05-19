package com.teslasoft.jarvis.core;

import android.app.Service;
import android.os.IBinder;
import android.content.Intent;

public class LicenceCheckService extends Service {
	@Override
	public IBinder onBind(Intent p1) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return START_STICKY;
	}
}
