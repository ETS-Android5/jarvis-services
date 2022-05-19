package com.teslasoft.libraries.support;

import android.app.admin.DeviceAdminReceiver;
import android.content.Intent;
import android.content.Context;
import android.content.ComponentName;

public class AdminReceiver extends DeviceAdminReceiver {
	public void onEnabled(Context context, Intent intent) {}
	
	public void onDisabled(Context context, Intent intent) {}
	
	public static ComponentName getComponentName(Context context) {
		return new ComponentName(context.getApplicationContext(), AdminReceiver.class);
	}
}
