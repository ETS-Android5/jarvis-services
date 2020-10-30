package net.jarvis.engine;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.widget.Toast;
import android.content.Intent;
import android.content.Context;

public class TaskCdhActivity extends Activity
{	
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		return;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		if (isMyServiceRunning(com.teslasoft.jarvis.core.InitService.class))
		{
			// toast("Service state: 2");
			finishAndRemoveTask();
		}

		else 
		{
			startService(new Intent(net.jarvis.engine.TaskCdhActivity.this, com.teslasoft.jarvis.core.InitService.class));
			finishAndRemoveTask();
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
