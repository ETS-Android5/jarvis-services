package com.teslasoft.jarvis.core;

import android.os.IBinder;
import android.app.Service;
import android.content.Intent;

public class LicenceCheckService extends Service
{
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO: Implement this method
		return START_STICKY;
	}
}
