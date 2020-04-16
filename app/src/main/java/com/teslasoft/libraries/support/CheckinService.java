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
import android.support.v7.appcompat.*;

public class CheckinService extends Service
{
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}
	public void onCreate(){
		super.onCreate();
		//Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
	}
	public void onDestroyed(){
		//Toast.makeText(this, "Service Destroyed", Toast.LENGTH_SHORT).show();
		super.onDestroy();
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				// Do something after 5s = 5000ms
				AlertDialog alertDialog = new AlertDialog.Builder(CheckinService.this).create();
				alertDialog.setTitle("Jarvis JCA code");
				alertDialog.setMessage("0x314705b42");
				alertDialog.setIcon(R.drawable.tick);
				alertDialog.setButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						
					}
				});

				/*alertDialog.setButton("Cancel", new DialogInterface.OnClickListener()
				{
				public void onClick(DialogInterface dialog, int which)
				{
					 
				}
				});*/

				alertDialog.show();
			}
		}, 5000);
	}
}
