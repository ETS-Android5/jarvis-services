package com.teslasoft.jarvis.core;

import android.os.Build;
import android.os.IBinder;
import android.os.Handler;
import android.content.Context;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.widget.Toast;

public class AutoRunForegroundService extends Service
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
		startService(new Intent(com.teslasoft.jarvis.core.AutoRunForegroundService.this, com.teslasoft.jarvis.core.AutoRunService.class));
		stopService(new Intent(com.teslasoft.jarvis.core.AutoRunForegroundService.this, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
	}

	@Override
    public void onDestroy() {
        super.onDestroy();
    }
}
