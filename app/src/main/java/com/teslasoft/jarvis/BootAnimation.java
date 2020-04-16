package com.teslasoft.jarvis;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import com.teslasoft.libraries.support.R;

public class BootAnimation extends Activity
{
	private LinearLayout background;
	private LinearLayout logo_mask;
	private LinearLayout layout_main;
	public int counter;

	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(0, 0);
		setContentView(R.layout.android_logo);
		
		background = (LinearLayout) findViewById(R.id.background);
		logo_mask = (LinearLayout) findViewById(R.id.logo_mask);
		layout_main = (LinearLayout) findViewById(R.id.layout_main);
		
		//layout_main.setBackgroundColor(0xFF000000);
		
		counter = 0;
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				
				background.setAlpha(1.f);
				logo_mask.setAlpha(1.f);
				// background.setTranslationX(background.getTranslationX() - 3584);
				Animate();
			}
		}, 1200);
	}
	
	/*
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		if(keyCode == KeyEvent.KEYCODE_HOME)
		{
			Process.killProcess(Process.myPid());
		}
		
		return true;
	}
	*/
	
	public void Animate()
	{
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				background.setTranslationX(background.getTranslationX() + 2);
				counter++;
				
				if (counter == 1024 || counter > 1024)
				{
					counter = 0;
					background.setTranslationX(background.getTranslationX() - 2048);
				}
				
				Animate();
			}
		}, 1);
	}

	public void Close(View v)
	{
		finishAndRemoveTask();
		overridePendingTransition(0, 0);
	}

	public void Ignore(View v)
	{
		//Do nothing
	}

	/*@Override
	public void onBackPressed()
	{
		
	}*/

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		// Process.killProcess(Process.myPid());
		// finishAffinity();
		//finishAndRemoveTask();
		overridePendingTransition(0, 0);
	}

	@Override
	protected void onResume()
	{
		// TODO: Implement this method
		super.onResume();
		overridePendingTransition(0, 0);
	}

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
		finishAndRemoveTask();
	}
}
