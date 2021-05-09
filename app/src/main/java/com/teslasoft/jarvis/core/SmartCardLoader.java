package com.teslasoft.jarvis.core;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.app.ActivityManager;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import android.content.Intent;
import android.widget.SmartToast;
import android.content.ComponentName;
import android.content.Context;
import com.teslasoft.libraries.support.R;

public class SmartCardLoader extends Activity
{
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	AlertDialog alertDialog;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_transparent);

		/*Intent i = new Intent(this, net.jarvis.engine.TaskCdhActivity.class);
		 startActivity(i);*/

		if (isMyServiceRunning(com.teslasoft.jarvis.core.SmartCardService.class))
		{
			// toast("Служба Jarvis Core Init не была запущена: процесс с таким ID уже был запущен");
			finishAndRemoveTask();
		}

		else {
			startService(new Intent(com.teslasoft.jarvis.core.SmartCardLoader.this, com.teslasoft.jarvis.core.SmartCardService.class));

			final Handler handler = new Handler();
			handler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					if (isMyServiceRunning(com.teslasoft.jarvis.core.SmartCardService.class)) {
						// toast("Служба Jarvis Core Init запущена");
						finishAndRemoveTask();
					} else {
						// toast("Служба Jarvis Core Init не была запущена: Сначала включите ее в настройках");

						alertDialog = new AlertDialog.Builder(com.teslasoft.jarvis.core.SmartCardLoader.this)
							.setMessage("Teslasoft SmartCard service disabled: Please enable it first")
							.setIcon(R.drawable.jarvis2)
							.setCancelable(false)
							.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog, int which)
								{
									finishAndRemoveTask();
								}
							})

							.setPositiveButton("Open settings", new DialogInterface.OnClickListener()
							{
								public void onClick(DialogInterface dialog, int which)
								{
									Intent intent = new Intent(Intent.ACTION_MAIN);
									intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									intent.setComponent(new ComponentName("com.teslasoft.libraries.support","com.teslasoft.jarvis.core.SmartCardSettingsActivity"));
									intent.addCategory(Intent.CATEGORY_LAUNCHER);
									startActivity(intent);
									finish();
								}
							})
						.create();

						alertDialog.show();
					}
				}
			},500);
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

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
	}
}