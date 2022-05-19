package com.teslasoft.jarvis.core;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.teslasoft.libraries.support.R;

public class ErrorServiceActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_service_crashed);
	}
	
	public void Close(View v) {
		finishAndRemoveTask();
	}
	
	public void stopTask(View v) {
		stopService(new Intent(this, com.teslasoft.jarvis.core.InitService.class));
		stopService(new Intent(this, com.teslasoft.jarvis.core.DataProtectorService.class));
		stopService(new Intent(this, com.teslasoft.jarvis.core.InitService.class));
		stopService(new Intent(this, com.teslasoft.jarvis.core.DataProtectorService.class));
		Runtime.getRuntime().exit(1);
		android.os.Process.killProcess(android.os.Process.myPid());
		finishAndRemoveTask();
	}
	
	public void Ignore(View v) {}

	@Override
	protected void onPause() {
		super.onPause();
		finishAndRemoveTask();
	}
	
	public void AllServices (View v) {
		Intent serv = new Intent(this, com.teslasoft.jarvis.core.ServicesActivity.class);
		startActivity(serv);
		finish();
	}
}
