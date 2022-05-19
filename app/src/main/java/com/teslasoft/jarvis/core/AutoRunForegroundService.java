package com.teslasoft.jarvis.core;

import android.app.Service;
import android.os.IBinder;
import android.content.Intent;

public class AutoRunForegroundService extends Service {
	@Override
	public IBinder onBind(Intent p1) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		startService(new Intent(com.teslasoft.jarvis.core.AutoRunForegroundService.this, com.teslasoft.jarvis.core.AutoRunService.class));
		stopService(new Intent(com.teslasoft.jarvis.core.AutoRunForegroundService.this, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
	}

	@Override
    public void onDestroy() {
        super.onDestroy();
    }
}
