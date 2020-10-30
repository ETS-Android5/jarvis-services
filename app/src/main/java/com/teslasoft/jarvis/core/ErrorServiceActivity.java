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

public class ErrorServiceActivity extends Activity
{
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.error_service);
	}
	
	public void Close(View v)
	{
		finishAndRemoveTask();
	}
	
	public void stopTask(View v)
	{
		stopService(new Intent(this, com.teslasoft.jarvis.core.InitService.class));
		stopService(new Intent(this, com.teslasoft.jarvis.core.DataProtectorService.class));
		// System.exit(1);
		stopService(new Intent(this, com.teslasoft.jarvis.core.InitService.class));
		stopService(new Intent(this, com.teslasoft.jarvis.core.DataProtectorService.class));
		// System.exit(1);
		Runtime.getRuntime().exit(1);
		android.os.Process.killProcess(android.os.Process.myPid());
		finishAndRemoveTask();
	}
	
	public void Ignore(View v)
	{

	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		finishAndRemoveTask();
	}
	
	public void AllServices (View v)
	{
		Intent serv = new Intent(this, com.teslasoft.jarvis.core.ServicesActivity.class);
		startActivity(serv);
		finish();
	}
}
