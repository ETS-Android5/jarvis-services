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

public class AppsActivity extends Activity
{
	@Override	
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
		return;
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load);
		Intent i = getPackageManager().getLaunchIntentForPackage("com.android.jss");
		if (i == null)
		{
			// If Jarvis Services not installed

			return;
		}

		i.addCategory(Intent.CATEGORY_LAUNCHER);
		i.putExtra("type", "ACTION_GET_APPS");
		i.putExtra("data", "APPS_VIEW");
		startActivity(i);
		finishAffinity();
		
    }
}
