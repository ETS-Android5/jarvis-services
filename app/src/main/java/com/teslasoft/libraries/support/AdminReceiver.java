package com.teslasoft.libraries.support;

import android.os.Build;
import android.content.Intent;
import android.content.Context;
import android.content.ComponentName;
import android.app.admin.DeviceAdminReceiver;

// Antiviruses can mark this file as Android/LockScreen.Jisut.AX

public class AdminReceiver extends DeviceAdminReceiver
{
	public void onEnabled(Context context, Intent intent) 
	{
		
	}
	
	public void onDisabled(Context context, Intent intent)
	{
		
	}
	
	public static ComponentName getComponentName(Context context)
	{
		return new ComponentName(context.getApplicationContext(), AdminReceiver.class);
	}
}
