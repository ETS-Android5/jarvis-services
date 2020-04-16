package com.teslasoft.jarvis.auth;

import android.os.Bundle;
import android.app.Service;
import android.os.IBinder;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import com.teslasoft.libraries.support.R;
import android.content.ComponentName;

public class AuthBinderService extends Service
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
		startService(new Intent(com.teslasoft.jarvis.auth.AuthBinderService.this, com.teslasoft.jarvis.auth.AuthService.class));
		stopService(new Intent(com.teslasoft.jarvis.auth.AuthBinderService.this, com.teslasoft.jarvis.auth.AuthBinderService.class));
	}

	@Override
    public void onDestroy() {
        super.onDestroy();
    }
}
