package com.teslasoft.jarvis.auth;

import android.os.Bundle;
import android.app.Service;
import android.os.IBinder;
import android.os.Handler;
import android.content.Intent;
import android.widget.Toast;
import com.teslasoft.libraries.support.R;
import android.content.ComponentName;

public class AuthService extends Service
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
		// TODO: Implement this method
		super.onCreate();
		stopService(new Intent(com.teslasoft.jarvis.auth.AuthService.this, com.teslasoft.jarvis.auth.AuthBinderService.class));
		toast("Debug: AuthService started");
		// toast("AuthService started");
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.auth.AuthEntryActivity"));
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(intent);
	}

	@Override
    public void onDestroy() {
        super.onDestroy();
        toast("Debug: AuthService finished");
    }

    final Handler mHandler = new Handler();

	void toast(final CharSequence text)
	{
        mHandler.post(new Runnable()
		{
			@Override public void run()
			{
				Toast.makeText(com.teslasoft.jarvis.auth.AuthService.this, text, Toast.LENGTH_SHORT).show();
			}
		});
    }
}
