package com.teslasoft.jarvis.core;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.content.ComponentName;
import android.content.Context;
import com.teslasoft.libraries.support.R;

public class CoreUpdateActivity extends Activity
{
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_transparent);
		
		// startService(new Intent(com.teslasoft.jarvis.core.CoreUpdateActivity.this, com.teslasoft.jarvis.core.InitService.class));
		
		if (isMyServiceRunning(com.teslasoft.jarvis.core.InitService.class))
		{
			finishAndRemoveTask();
		}

		else 
		{
			/*Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.core.CoreServiceSettingsActivity"));
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			startActivity(intent);*/
			startService(new Intent(com.teslasoft.jarvis.core.CoreUpdateActivity.this, com.teslasoft.jarvis.core.InitService.class));
			finishAndRemoveTask();
		}
		
	}

	final Handler mHandler = new Handler();

	void toast(final CharSequence text)
	{
        mHandler.post(new Runnable()
		{
			@Override public void run()
			{
				Toast.makeText(com.teslasoft.jarvis.core.CoreUpdateActivity.this, text, Toast.LENGTH_SHORT).show();
			}
		});
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

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
	}

	public void Exec (View v)
	{
		return;
	}

	public void start (int serviceCode) {
		return;
	}
}
