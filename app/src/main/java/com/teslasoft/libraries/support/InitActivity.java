package com.teslasoft.libraries.support;

import android.os.*;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.util.*;
import android.net.*;
import androidx.appcompat.*;

public class InitActivity extends Activity
{	
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
		return;
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);
		
		try
		{
			/* Don not send superuser requests
			Runtime.getRuntime().exec();
			*/

			
			try
			{
				Intent i = new Intent(InitActivity.this, net.jarvis.engine.TaskCdhActivity.class);
				startActivity(i);
				finish();
			}

			catch (Exception e)
			{
				finish();
			}
		}
		
		catch (Exception e)
		{
            Toast toast = Toast.makeText(getApplicationContext(), "Error: Java Runtime Exception: Permission denied!", Toast.LENGTH_SHORT); 
			toast.show();
			Intent i = new Intent(InitActivity.this, net.jarvis.engine.TaskCdhActivity.class);
			startActivity(i);
			startService(new Intent(this, com.teslasoft.jarvis.core.InitService.class));
			finishAffinity();
		}
    }
	
	private boolean isMyServiceRunning(Class<?> serviceClass)
	{
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
		{
			if (serviceClass.getName().equals(service.service.getClassName()))
			{
				return true;
			}
		}
		return false;
	}
}
