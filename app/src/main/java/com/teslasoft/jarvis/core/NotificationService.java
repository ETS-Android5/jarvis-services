package com.teslasoft.jarvis.core;

import android.os.Process;
import android.os.Build;
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
import android.graphics.BitmapFactory;


public class NotificationService extends Service {
    public static final String CHANNEL_ID = "Services";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = "Jarvis Core Initializing has started";
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, com.teslasoft.libraries.support.LoadActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
																0, notificationIntent, 0);

        Notification notification = new Notification.Builder(this, CHANNEL_ID)
			.setContentTitle("Jarvis Core")
			.setContentText(input)
			.setSmallIcon(R.drawable.jarvis2)
			.setContentIntent(pendingIntent)
			.build();

        startForeground(1, notification);

        //do heavy work on a background thread

        //stopSelf();

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
				CHANNEL_ID,
				"Jarvis Core",
				NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }
}
