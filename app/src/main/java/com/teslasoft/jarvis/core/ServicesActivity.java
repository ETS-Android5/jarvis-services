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
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.SmartToast;

public class ServicesActivity extends Activity
{
	TextView csstate;
	TextView dpsstate;
	TextView lcsstate;
	TextView nsstate;
	TextView smstate;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.services_list);
		
		csstate = (TextView) findViewById(R.id.csstate);
		dpsstate = (TextView) findViewById(R.id.dpsstate);
		lcsstate = (TextView) findViewById(R.id.lcsstate);
		nsstate = (TextView) findViewById(R.id.nsstate);
		smstate = (TextView) findViewById(R.id.smstate);

		try {
			Intent licenseIntent = new Intent(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class);
			startActivityForResult(licenseIntent, 1);
		} catch (Exception e) {
			// User tried to disable or bypass license checking service, exit
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}

	/* Piracy check starts */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			// License check passed
			statement();
		} else {
			// License check failed, exit
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}
	/* Piracy check ends */
	
	public void statement()
	{
		PackageManager pm = getPackageManager();
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.InitService.class)) == 1)
		{
			if (isMyServiceRunning(com.teslasoft.jarvis.core.InitService.class))
			{
				csstate.setText(R.string.state_running_s);
				csstate.setTextColor(0xFF2E8B57);
			}

			else 
			{
				csstate.setText(R.string.state_stopped_s);
				csstate.setTextColor(0xFFFFFF00);
			}
		}

		else
		{
			csstate.setText(R.string.state_disabled_s);
			csstate.setTextColor(0xFFB20021);
		}
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.DataProtectorService.class)) == 1)
		{
			if (isMyServiceRunning(com.teslasoft.jarvis.core.DataProtectorService.class))
			{
				dpsstate.setText(R.string.state_running_s);
				dpsstate.setTextColor(0xFF2E8B57);
			}

			else 
			{
				dpsstate.setText(R.string.state_stopped_s);
				dpsstate.setTextColor(0xFFFFFF00);
			}
		}

		else
		{
			dpsstate.setText(R.string.state_disabled_s);
			dpsstate.setTextColor(0xFFB20021);
		}
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.LicenceCheckService.class)) == 1)
		{
			if (isMyServiceRunning(com.teslasoft.jarvis.core.LicenceCheckService.class))
			{
				lcsstate.setText(R.string.state_running_s);
				lcsstate.setTextColor(0xFF2E8B57);
			}

			else 
			{
				lcsstate.setText(R.string.state_stopped_s);
				lcsstate.setTextColor(0xFFFFFF00);
			}
		}

		else
		{
			lcsstate.setText(R.string.state_disabled_s);
			lcsstate.setTextColor(0xFFB20021);
		}
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.NotificationStatService.class)) == 1)
		{
			if (isMyServiceRunning(com.teslasoft.jarvis.core.NotificationStatService.class))
			{
				nsstate.setText(R.string.state_running_s);
				nsstate.setTextColor(0xFF2E8B57);
			}

			else 
			{
				nsstate.setText(R.string.state_stopped_s);
				nsstate.setTextColor(0xFFFFFF00);
			}
		}

		else
		{
			nsstate.setText(R.string.state_disabled_s);
			nsstate.setTextColor(0xFFB20021);
		}
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.SmartCardService.class)) == 1)
		{
			if (isMyServiceRunning(com.teslasoft.jarvis.core.SmartCardService.class))
			{
				smstate.setText(R.string.state_running_s);
				smstate.setTextColor(0xFF2E8B57);
			}

			else 
			{
				smstate.setText(R.string.state_stopped_s);
				smstate.setTextColor(0xFFFFFF00);
			}
		}

		else
		{
			smstate.setText(R.string.state_disabled_s);
			smstate.setTextColor(0xFFB20021);
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
		Intent intent = new Intent(this, ServiceSettingActivity.class);
		intent.putExtra("serviceId", 0);
		startActivity(intent);
	}
	
	public void DPService(View v)
	{
		Intent intent = new Intent(this, ServiceSettingActivity.class);
		intent.putExtra("serviceId", 1);
		startActivity(intent);
	}
	
	public void LCService(View v)
	{
		Intent intent = new Intent(this, ServiceSettingActivity.class);
		intent.putExtra("serviceId", 2);
		startActivity(intent);
	}
	
	public void NSService(View v)
	{
		Intent intent = new Intent(this, ServiceSettingActivity.class);
		intent.putExtra("serviceId", 3);
		startActivity(intent);
	}
	
	public void SMService(View v)
	{
		Intent intent = new Intent(this, ServiceSettingActivity.class);
		intent.putExtra("serviceId", 4);
		startActivity(intent);
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
