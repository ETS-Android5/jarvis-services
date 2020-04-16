package com.teslasoft.jarvis;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import com.teslasoft.libraries.support.R;

public class LoadFullscreen extends Activity
{

	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.load_f);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);

		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				Intent i = new Intent(com.teslasoft.jarvis.LoadFullscreen.this, com.teslasoft.jarvis.BootAnimation.class);
				startActivity(i);
				finish();
				overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
			}
		}, 1000);
	}
}
