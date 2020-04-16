package com.teslasoft.jarvis;

import android.os.*;
import android.app.*;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.*;
import com.teslasoft.libraries.support.R;
import android.support.v7.appcompat.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.*;

public class JHActivity extends Activity
{

	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hashgen);
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				finish();
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		}, 2000);
	}

	@Override
	public void onBackPressed()
	{
		// super.onBackPressed();
	}
}
