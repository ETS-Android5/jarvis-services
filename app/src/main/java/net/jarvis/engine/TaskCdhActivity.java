package net.jarvis.engine;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;

public class TaskCdhActivity extends Activity {
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		return;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		try {
			Intent licenseIntent = new Intent(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class);
			startActivityForResult(licenseIntent, 1);
		} catch (Exception e) {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			if (!isMyServiceRunning(com.teslasoft.jarvis.core.InitService.class)) {
				startService(new Intent(net.jarvis.engine.TaskCdhActivity.this, com.teslasoft.jarvis.core.InitService.class));
				finishAndRemoveTask();
			}
		} else {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}
	
	private boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName()))
				return true;
		}
		return false;
	}
}
