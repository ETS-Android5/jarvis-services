package com.teslasoft.libraries.support;

import android.os.*;
import android.app.*;

public class Sandbox extends Activity
{
	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sandbox);
		getWindow().getDecorView().setBackgroundColor(0x00000000);
	}
}
