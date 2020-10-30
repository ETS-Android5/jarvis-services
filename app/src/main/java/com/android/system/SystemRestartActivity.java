package com.android.system;

import android.os.Bundle;
import java.lang.Process;
import android.app.Activity;
import android.app.ActivityManager;
import android.view.View;
import android.content.Intent;
import android.content.Context;
import android.content.ComponentName;
import android.util.Log;
import java.lang.Runtime;

public class SystemRestartActivity extends Activity
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
		// setContentView(R.layout.restart);
		ForceRestart();
	}
	
	public void ForceRestart()
	{
		try {
			java.lang.Runtime.getRuntime().exec(new String[] { "su", "-c", "reboot" });
		} catch (Exception e) {
			Log.e("Jarvis Services", "Root required", e);
		}
	}
	
	public void SoftRestart()
	{
		
	}
}
