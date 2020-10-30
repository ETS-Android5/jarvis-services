package com.teslasoft.jarvis.core;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.ComponentName;
import android.content.Context;
import com.teslasoft.libraries.support.R;

public class ServicesActivity extends Activity
{
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	TextView csstate;
	TextView dpsstate;
	TextView lcsstate;
	TextView nsstate;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.services_list);
		
		csstate = (TextView) findViewById(R.id.csstate);
		dpsstate = (TextView) findViewById(R.id.dpsstate);
		lcsstate = (TextView) findViewById(R.id.lcsstate);
		nsstate = (TextView) findViewById(R.id.nsstate);
		
		statement();
	}
	
	public void statement()
	{
		PackageManager pm = getPackageManager();
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.InitService.class)) == 1)
		{
			if (isMyServiceRunning(com.teslasoft.jarvis.core.InitService.class))
			{
				csstate.setText("Running");
				csstate.setTextColor(0xFF2E8B57);
			}

			else 
			{
				csstate.setText("Stopped");
				csstate.setTextColor(0xFFFFFF00);
			}
		}

		else
		{
			csstate.setText("Disabled");
			csstate.setTextColor(0xFFB20021);
		}
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.DataProtectorService.class)) == 1)
		{
			if (isMyServiceRunning(com.teslasoft.jarvis.core.DataProtectorService.class))
			{
				dpsstate.setText("Running");
				dpsstate.setTextColor(0xFF2E8B57);
			}

			else 
			{
				dpsstate.setText("Stopped");
				dpsstate.setTextColor(0xFFFFFF00);
			}
		}

		else
		{
			dpsstate.setText("Disabled");
			dpsstate.setTextColor(0xFFB20021);
		}
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.LicenceCheckService.class)) == 1)
		{
			if (isMyServiceRunning(com.teslasoft.jarvis.core.LicenceCheckService.class))
			{
				lcsstate.setText("Running");
				lcsstate.setTextColor(0xFF2E8B57);
			}

			else 
			{
				lcsstate.setText("Stopped");
				lcsstate.setTextColor(0xFFFFFF00);
			}
		}

		else
		{
			lcsstate.setText("Disabled");
			lcsstate.setTextColor(0xFFB20021);
		}
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.NotificationStatService.class)) == 1)
		{
			if (isMyServiceRunning(com.teslasoft.jarvis.core.NotificationStatService.class))
			{
				nsstate.setText("Running");
				nsstate.setTextColor(0xFF2E8B57);
			}

			else 
			{
				nsstate.setText("Stopped");
				nsstate.setTextColor(0xFFFFFF00);
			}
		}

		else
		{
			nsstate.setText("Disabled");
			nsstate.setTextColor(0xFFB20021);
		}
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				statement();
			}
		}, 500);
	}

	final Handler mHandler = new Handler();

	void toast(final CharSequence text)
	{
        mHandler.post(new Runnable()
		{
			@Override public void run()
			{
				Toast.makeText(com.teslasoft.jarvis.core.ServicesActivity.this, text, Toast.LENGTH_SHORT).show();
			}
		});
    }

	public void Close(View v)
	{
		finish();
	}

	public void CoreService(View v)
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.core.CoreServiceSettingsActivity"));
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(intent);
		// finish();
	}
	
	public void DPService(View v)
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.core.DPSSettingsActivity"));
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(intent);
		// finish();
	}
	
	public void LCService(View v)
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.core.LCSSettingsActivity"));
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(intent);
		// finish();
	}
	
	public void NSService(View v)
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.core.NSettingsActivity"));
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(intent);
		// finish();
	}

	public boolean isMyServiceRunning(Class<?> serviceClass)
	{
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		try
		{
			for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
			{
				if (serviceClass.getName().equals(service.service.getClassName()))
				{
					return true;
				}
			}
		}
		catch (Exception e)
		{

		}
		return false;
	}

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
	}

	public void Exec (View v)
	{
		return;
	}

	public void start (int serviceCode) {
		return;
	}
}
