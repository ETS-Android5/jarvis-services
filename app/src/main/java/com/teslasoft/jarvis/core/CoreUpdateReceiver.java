package com.teslasoft.jarvis.core;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class CoreUpdateReceiver extends BroadcastReceiver
{
    public void onReceive(Context context, Intent intent)
	{
        Intent service = new Intent(context, InitService.class);
        context.startService( service );
    }
}
