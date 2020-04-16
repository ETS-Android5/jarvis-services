package com.teslasoft.jarvis;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import com.teslasoft.libraries.support.R;

public class OptIn extends Activity
{
	private LinearLayout shadow;
	private LinearLayout dialog;
	private ProgressBar progress;

	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_loading_transparent);
				
		shadow = (LinearLayout) findViewById(R.id.shadow);
		dialog = (LinearLayout) findViewById(R.id.dialog);
		progress = (ProgressBar) findViewById(R.id.progress);
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
