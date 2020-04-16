package com.teslasoft.jarvis.core;

import android.os.Process;
import android.os.Build;
import android.content.Context;
import android.content.BroadcastReceiver;
import android.content.Intent;

public class RestartServiceReceiver extends BroadcastReceiver
{
	/*public RestertServiceReceiver()
	{
		
	}*/
	
    @Override
    public void onReceive(Context context, Intent intent)
	{
		if (intent.getAction().equals("com.teslasoft.jarvis.RESTART_INIT_CORE_SERVICE"))
		{
			try
			{
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
				{
					context.startForegroundService(new Intent(context, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
				}

				else
				{
					context.startService(new Intent(context, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
				}
			}

			catch (Exception e)
			{
				try
				{
					context.startService(new Intent(context, com.teslasoft.jarvis.core.AutoRunForegroundService.class));
				}

				catch (Exception _e)
				{

				}
			}
		}
		
		// context.startService(new Intent(context.getApplicationContext(), com.teslasoft.jarvis.core.InitService.class));
    }
}
