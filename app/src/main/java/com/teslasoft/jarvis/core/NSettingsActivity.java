package com.teslasoft.jarvis.core;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.content.ComponentName;
import android.content.Context;
import com.teslasoft.libraries.support.R;

public class NSettingsActivity extends Activity
{
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	TextView state;
	TextView desc;
	Button cstate;
	Button estate;
	TextView notetx;

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
		notetx = (TextView) findViewById(R.id.notetx);

		notetx.setVisibility(View.GONE);
		desc.setText("Служба Notification Stats Service принимает и показывает уведомления (например при входящих сообщениях). Если ее омтановить, уведомления не будут приходить");
		statement();
	}

	public void statement()
	{
		PackageManager pm = getPackageManager();

		if (pm.getComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.NotificationStatService.class)) == 1)
		{
			if (isMyServiceRunning(com.teslasoft.jarvis.core.NotificationStatService.class))
			{
				estate.setText("Disable");
				state.setText("State: Running");
				cstate.setText("Force stop");
				cstate.setTextColor(0xFFFF3D00);
				cstate.setEnabled(true);
			}

			else 
			{
				estate.setText("Disable");
				state.setText("State: Stopped");
				cstate.setText("Force start");
				cstate.setTextColor(0xFF2E8B57);
				cstate.setEnabled(true);
			}
		}

		else
		{
			estate.setText("Enable");
			state.setText("State: Disabled");
			cstate.setText("Force start");
			cstate.setTextColor(0xFF9E9E9E);
			cstate.setEnabled(false);
		}

		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				statement();
			}
		}, 20);
	}

	final Handler mHandler = new Handler();

	void toast(final CharSequence text)
	{
        mHandler.post(new Runnable()
		{
			@Override public void run()
			{
				Toast.makeText(com.teslasoft.jarvis.core.NSettingsActivity.this, text, Toast.LENGTH_SHORT).show();
			}
		});
    }

	public void Close(View v)
	{
		finishAndRemoveTask();
	}

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

		if (pm.getComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.NotificationStatService.class)) == 1)
		{
			stopService(new Intent(this, com.teslasoft.jarvis.core.NotificationStatService.class));
			pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.NotificationStatService.class),
										  PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
			/*estate.setText("Включить");
			state.setText("Состояние службы: Отключена");
			cstate.setText("Запустить");
			cstate.setTextColor(0xFF9E9E9E);
			cstate.setEnabled(false);*/
			statement();
		}

		else
		{
			pm.setComponentEnabledSetting(new ComponentName(this, com.teslasoft.jarvis.core.NotificationStatService.class),
										  PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
			/*estate.setText("Отключить");
			state.setText("Состояние службы: Остановлена");
			cstate.setText("Запустить");
			cstate.setTextColor(0xFF2E8B57);
			cstate.setEnabled(true);*/
			statement();
		}

	}

	public void StopTask(View v)
	{
		if (isMyServiceRunning(com.teslasoft.jarvis.core.NotificationStatService.class))
		{
			stopService(new Intent(this, com.teslasoft.jarvis.core.NotificationStatService.class));
			/*state.setText("Состояние службы: Остановлена");
			cstate.setText("Запустить");
			cstate.setTextColor(0xFF2E8B57);*/
			statement();
		}

		else 
		{
			startService(new Intent(this, com.teslasoft.jarvis.core.NotificationStatService.class));
			/*state.setText("Состояние службы: Запущена");
			cstate.setText("Остановить");
			cstate.setTextColor(0xFFFF3D00);*/
			statement();
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
		finishAndRemoveTask();
	}
}
