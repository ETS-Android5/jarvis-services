package com.teslasoft.libraries.support;

import android.os.Bundle;
import android.app.Activity;

public class Admin extends Activity
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
		finish();
	}
}
