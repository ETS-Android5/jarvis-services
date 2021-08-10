package com.teslasoft.jarvis.core;

import android.os.*;
import android.app.*;
import android.content.*;
import android.widget.*;

public class UpdateCompleteService extends Service
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
		stopService(new Intent(com.teslasoft.jarvis.core.UpdateCompleteService.this, com.teslasoft.jarvis.core.CoreUpdateService.class));
		// toast("Starting Jarvis Services");

		/*Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.core.CoreUpdateActivity"));
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(intent);*/
		Sii(this);
	}

	@Override
    public void onDestroy() {
        super.onDestroy();
        // toast("Debug: AutoRun service finished");
    }

    final Handler mHandler = new Handler();

	void toast(final CharSequence text)
	{
        mHandler.post(new Runnable()
		{
			@Override public void run()
			{
				Toast.makeText(com.teslasoft.jarvis.core.UpdateCompleteService.this, text, Toast.LENGTH_SHORT).show();
			}
		});
    }
	
	public void Sii(Context context)
	{
		if (isMyServiceRunning(com.teslasoft.jarvis.core.DataProtectorService.class))
		{
			// If Data Protector Service running
		}

		else 
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			{
				context.startForegroundService(new Intent(context, com.teslasoft.jarvis.core.InitService.class));
			}

			else
			{
				context.startService(new Intent(context, com.teslasoft.jarvis.core.InitService.class));
			}
		}
	}

	private boolean isMyServiceRunning(Class<?> serviceClass)
	{
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
		{
			if (serviceClass.getName().equals(service.service.getClassName()))
			{
				return true;
			}
		}
		return false;
	}
}
