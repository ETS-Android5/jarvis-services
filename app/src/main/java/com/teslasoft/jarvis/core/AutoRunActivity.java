package com.teslasoft.jarvis.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActivityManager;
import android.os.Bundle;
import android.content.Intent;
import android.content.Context;
import android.view.View;

import com.teslasoft.libraries.support.R;

public class AutoRunActivity extends Activity {

	AlertDialog alertDialog;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_loading_screen_transparent);
		
		if (isMyServiceRunning(com.teslasoft.jarvis.core.InitService.class)) {
			finishAndRemoveTask();
		} else {
			startService(new Intent(com.teslasoft.jarvis.core.AutoRunActivity.this, com.teslasoft.jarvis.core.InitService.class));
			
			if (isMyServiceRunning(com.teslasoft.jarvis.core.InitService.class)) {
				finishAndRemoveTask();
			} else {
				alertDialog = new AlertDialog.Builder(com.teslasoft.jarvis.core.AutoRunActivity.this)
				.setMessage("Failed to start InitService. Please enable it in the settings.")
				.setIcon(R.drawable.teslasoft_logo)
				.setCancelable(false)
				.setNegativeButton("Cancel", (dialog, which) -> finishAndRemoveTask())
				.setPositiveButton("Open settings", (dialog, which) -> {
					Intent intent = new Intent(AutoRunActivity.this, ServiceSettingActivity.class);
					intent.putExtra("serviceId", 0);
					startActivity(intent);
					finish();
				})
				.create();
				
				alertDialog.show();
			}
		}
	}
	
	private boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if (serviceClass.getName().equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}

	public void Exec(View v) {}
	
	public void start(int serviceCode) {}
}
