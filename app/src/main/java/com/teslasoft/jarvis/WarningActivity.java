package com.teslasoft.jarvis;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.teslasoft.libraries.support.R;

public class WarningActivity extends Activity
{
	private LinearLayout shadow;
	private LinearLayout dialog;
	private ProgressBar progress;

	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.warning_fullscreen);
	}

	public void DismissDialogActivity(View v)
	{
		finish();
		overridePendingTransition(0, 0);
	}

	public void Ignore(View v)
	{
		//Do nothing
	}

	@Override
	public void onBackPressed()
	{
		finish();
		overridePendingTransition(0, 0);
	}
}
