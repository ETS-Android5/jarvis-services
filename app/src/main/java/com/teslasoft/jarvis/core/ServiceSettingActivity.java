package com.teslasoft.jarvis.core;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.content.Intent;
import android.content.ComponentName;
import android.content.Context;
import com.teslasoft.libraries.support.R;

public class ServiceSettingActivity extends Activity {
	TextView state;
	TextView desc;
	Button cstate;
	Button estate;
	public Class init_service = com.teslasoft.jarvis.core.InitService.class;
	public Class dp_service = com.teslasoft.jarvis.core.DataProtectorService.class;
	public Class lc_service = com.teslasoft.jarvis.core.LicenceCheckService.class;
	public Class ns_service = com.teslasoft.jarvis.core.NotificationStatService.class;
	public Class sc_service = com.teslasoft.jarvis.core.SmartCardService.class;

	public int serviceId;
	public Class selected_service;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.core_settings);
		
		state = (TextView) findViewById(R.id.state);
		cstate = (Button) findViewById(R.id.cstate);
		estate = (Button) findViewById(R.id.estate);
		desc = (TextView) findViewById(R.id.desc);

		try {
			Intent licenseIntent = new Intent(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class);
			startActivityForResult(licenseIntent, 1);
		} catch (Exception e) {
			// User tried to disable or bypass license checking service, exit
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}

		try {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			serviceId = extras.getInt("serviceId");
			if (serviceId == 0) {
				selected_service = init_service;
				desc.setText(R.string.core_desc);
			} else if (serviceId == 1) {
				selected_service = dp_service;
				desc.setText(R.string.dp_desc);
			} else if (serviceId == 2) {
				selected_service = lc_service;
				desc.setText(R.string.lc_desc);
			} else if (serviceId == 3) {
				selected_service = ns_service;
				desc.setText(R.string.ns_desc);
			} else if (serviceId == 4) {
				selected_service = sc_service;
				desc.setText(R.string.sc_desc);
			} else {
				finishAndRemoveTask();
			}
			statement(selected_service);
		} catch (Exception e) {
			finishAndRemoveTask();
		}
	}

	public void statement(Class service)
	{
		PackageManager pm = getPackageManager();
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, service)) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
		{
			if (isMyServiceRunning(service))
			{
				estate.setText(R.string.button_disable);
				state.setText(R.string.state_running);
				cstate.setText(R.string.force_stop);
				cstate.setTextColor(0xFFFF3D00);
				cstate.setEnabled(true);
			}

			else 
			{
				estate.setText(R.string.button_disable);
				state.setText(R.string.state_stopped);
				cstate.setText(R.string.force_start);
				cstate.setTextColor(0xFF2E8B57);
				cstate.setEnabled(true);
			}
		}
		
		else
		{
			estate.setText(R.string.button_enable);
			state.setText(R.string.state_disabled);
			cstate.setText(R.string.force_start);
			cstate.setTextColor(0xFF9E9E9E);
			cstate.setEnabled(false);
		}
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				statement(service);
			}
		}, 20);
	}
	
	public void Close(View v)
	{
		finishAndRemoveTask();
	}

	/* Piracy check starts */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			// License check passed
		} else {
			// License check failed, exit
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}
	/* Piracy check ends */
	
	public void AllServices(View v)
	{
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.core.ServicesActivity"));
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(intent);
		finish();
	}
	
	public void Disable(View v)
	{
		PackageManager pm = getPackageManager();
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, selected_service)) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED)
		{
			stopService(new Intent(this, selected_service));
			pm.setComponentEnabledSetting(new ComponentName(this, selected_service),
										  PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
			/*estate.setText("Включить");
			state.setText("Состояние службы: Отключена");
			cstate.setText("Запустить");
			cstate.setTextColor(0xFF9E9E9E);
			cstate.setEnabled(false);*/
			statement(selected_service);
		}
		
		else
		{
			pm.setComponentEnabledSetting(new ComponentName(this, selected_service),
										  PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
			/*estate.setText("Отключить");
			state.setText("Состояние службы: Остановлена");
			cstate.setText("Запустить");
			cstate.setTextColor(0xFF2E8B57);
			cstate.setEnabled(true);*/
			statement(selected_service);
		}
		
	}
	
	public void StopTask(View v)
	{
		if (isMyServiceRunning(selected_service))
		{
			stopService(new Intent(this, selected_service));
			/*state.setText("Состояние службы: Остановлена");
			cstate.setText("Запустить");
			cstate.setTextColor(0xFF2E8B57);*/
			statement(selected_service);
		}

		else 
		{
			startService(new Intent(this, selected_service));
			/*estate.setText("Disable");
			state.setText("State: Running");
			cstate.setText("Force stop");
			cstate.setTextColor(0xFFFF3D00);*/
			statement(selected_service);
		}
		
		/*pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.InitService.class),
									  PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);*/
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
		finishAndRemoveTask();
	}

	public void Exec (View v)
	{
		return;
	}

	public void start (int serviceCode) {
		return;
	}
	
	public void Ignore(View v)
	{

	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
	}
}
