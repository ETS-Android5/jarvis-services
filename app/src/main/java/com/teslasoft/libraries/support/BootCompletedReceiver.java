package com.teslasoft.libraries.support;

import android.os.Build;
import android.content.Intent;
import android.content.Context;
import android.content.BroadcastReceiver;

public class BootCompletedReceiver extends BroadcastReceiver
{
	public BootCompletedReceiver()
	{
		
    }

    public void onReceive(Context context, Intent intent)
	{
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED))
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			{
				context.startForegroundService(new Intent(context, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
			} else {
				context.startService(new Intent(context, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
				// context.stopService(new Intent(context, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
			}
		} else if (intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED))
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			{
				context.startForegroundService(new Intent(context, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
			} else {
				context.startService(new Intent(context, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
				// context.stopService(new Intent(context, com.teslasoft.jarvis.core.CoreUpdateService.class));
			}
		} else if (intent.getAction().equals(Intent.ACTION_PACKAGE_REPLACED))
		{
			if(intent.getData().getSchemeSpecificPart().equals(context.getPackageName()))
			{
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
				{
					context.startForegroundService(new Intent(context, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
				} else {
					context.startService(new Intent(context, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
					// context.stopService(new Intent(context, com.teslasoft.jarvis.core.CoreUpdateService.class));
				}
			}
		}
    }
}
