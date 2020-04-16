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
