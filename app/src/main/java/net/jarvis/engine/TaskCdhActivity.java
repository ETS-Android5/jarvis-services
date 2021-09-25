package net.jarvis.engine;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.widget.Toast;
import android.content.Intent;
import android.content.Context;

import com.teslasoft.libraries.support.R;

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

		try {
			Intent licenseIntent = new Intent(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class);
			startActivityForResult(licenseIntent, 1);
		} catch (Exception e) {
			// User tried to disable or bypass license checking service, exit
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}

	/* Piracy check starts */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			// License check passed
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
		} else {
			// License check failed, exit
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}
	/* Piracy check ends */
	
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
