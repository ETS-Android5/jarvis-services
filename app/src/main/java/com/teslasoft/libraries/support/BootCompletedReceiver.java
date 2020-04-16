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
				context.startForegroundService(new Intent(context, com.teslasoft.jarvis.core.InitService.class));
			}
				
			else
			{
				context.startService(new Intent(context, com.teslasoft.jarvis.core.InitService.class));
				// context.stopService(new Intent(context, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
			}
		}
		
		if (intent.getAction().equals(Intent.ACTION_MY_PACKAGE_REPLACED))
		{
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
			{
				context.startForegroundService(new Intent(context, com.teslasoft.jarvis.core.InitService.class));
			}

			else
			{
				context.startService(new Intent(context, com.teslasoft.jarvis.core.InitService.class));
				// context.stopService(new Intent(context, com.teslasoft.jarvis.core.CoreUpdateService.class));
			}
		}
    }
}
