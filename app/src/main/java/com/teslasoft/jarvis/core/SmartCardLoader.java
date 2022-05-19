package com.teslasoft.jarvis.core;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ComponentName;
import android.content.Context;

import com.teslasoft.libraries.support.R;

public class SmartCardLoader extends Activity {

	AlertDialog alertDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_loading_screen_transparent);

		if (isMyServiceRunning(com.teslasoft.jarvis.core.SmartCardService.class)) {
			finishAndRemoveTask();
		} else {
			startService(new Intent(com.teslasoft.jarvis.core.SmartCardLoader.this, com.teslasoft.jarvis.core.SmartCardService.class));

			final Handler handler = new Handler();
			handler.postDelayed(() -> {
				if (isMyServiceRunning(SmartCardService.class)) {
					finishAndRemoveTask();
				} else {
					alertDialog = new AlertDialog.Builder(SmartCardLoader.this)
						.setMessage("Teslasoft SmartCard service disabled: Please enable it first")
						.setIcon(R.drawable.teslasoft_logo)
						.setCancelable(false)
						.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								finishAndRemoveTask();
							}
						}).setPositiveButton("Open settings", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								Intent intent = new Intent(Intent.ACTION_MAIN);
								intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								intent.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.core.SmartCardSettingsActivity"));
								intent.addCategory(Intent.CATEGORY_LAUNCHER);
								startActivity(intent);
								finish();
							}
						}).create();
					alertDialog.show();
				}
			},500);
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

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
}
