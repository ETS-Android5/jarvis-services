package com.teslasoft.jarvis;

import android.os.*;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.util.*;
import android.net.*;
import com.teslasoft.libraries.support.R;
import android.support.v7.appcompat.*;
import android.support.v7.app.*;

public class WarningActivity extends Activity
{
	private TextView text;
	
	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.warning);
		
		Intent intent = getIntent();
		text = (TextView) findViewById(R.id.text);
		text.setText(intent.getStringExtra("val"));
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				finish();
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		}, 3000);
	}

	@Override
	public void onBackPressed()
	{
		
	}
}
