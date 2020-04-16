package com.teslasoft.libraries.support;

import android.os.Build;
import android.content.Intent;
import android.content.Context;
import android.content.ComponentName;
import android.widget.Toast;
import android.app.admin.DeviceAdminReceiver;

public class AdminReceiver extends DeviceAdminReceiver
{
	public void onEnabled(Context context, Intent intent) 
	{
		Toast.makeText(context.getApplicationContext(), "Device Admin enabled", Toast.LENGTH_LONG).show();
		context.startService(new Intent(context.getApplicationContext(), CheckinService.class));
	}
	
	public CharSequence onDisableRequested(Context context, Intent intent)
	{
		return "Warning: Some services will not work";
		//Intent intentt = new Intent(context.getApplicationContext(), Admin.class);
        //context.startActivity(intentt);
		//return "";
	}

	public void onDisabled(Context context, Intent intent)
	{
		Toast.makeText(context.getApplicationContext(), "Device Admin disabled", Toast.LENGTH_LONG).show();
		context.stopService(new Intent(context.getApplicationContext(), CheckinService.class));
	}

	public void onLockTaskModeEntering(Context context, Intent intent,String pkg)
	{
		
	}

	public void onLockTaskModeExiting(Context context, Intent intent)
	{
		
	}

	public static ComponentName getComponentName(Context context)
	{
		return new ComponentName(context.getApplicationContext(), AdminReceiver.class);
	}
}
