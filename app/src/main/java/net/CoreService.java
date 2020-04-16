package net;

import android.os.IBinder;
import android.app.Service;
import android.content.Intent;

public class CoreService extends Service
{
	@Override
	public IBinder onBind(Intent p1)
	{
		return null;
	}
}
