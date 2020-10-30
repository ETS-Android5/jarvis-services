package com.android.jss;

import android.os.*;
import android.app.*;
import android.content.*;
import android.graphics.*;
import com.teslasoft.libraries.support.R;
import android.content.pm.PackageManager.*;
import android.content.pm.*;
import android.content.pm.PackageManager;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.util.*;
import android.net.*;
import android.webkit.*;
import android.annotation.*;
import java.text.*;
import java.lang.*;
import android.content.AsyncTaskLoader;

public class Connect extends Activity
{
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.prelogin);
	}

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
		finish();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onPostCreate(savedInstanceState);
	}
}
