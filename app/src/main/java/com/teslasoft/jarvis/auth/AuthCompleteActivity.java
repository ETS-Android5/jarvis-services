package com.teslasoft.jarvis.auth;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import android.widget.TextView;
import com.teslasoft.libraries.support.R;
import android.content.ComponentName;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import android.os.Environment;
import android.widget.SmartToast;

public class AuthCompleteActivity extends Activity
{
	TextView text;

	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		setContentView(R.layout.add_accound_complete);
		text = (TextView) findViewById(R.id.text);
		text.setText("Checkin info...");
		
		/*File root = new File(Environment.getExternalStorageDirectory(), "jarvis/auth");
		if (!root.exists()) {
			root.mkdirs();
		}
		
		try {
			File filepath = new File(root, "config.txt");
			FileWriter writer = new FileWriter(filepath);
			writer.append("Text here");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			SmartToast.create("JavaIOException", this);
		}*/
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				stopService(new Intent(com.teslasoft.jarvis.auth.AuthCompleteActivity.this, com.teslasoft.jarvis.auth.AuthService.class));
				stopService(new Intent(com.teslasoft.jarvis.auth.AuthCompleteActivity.this, com.teslasoft.jarvis.auth.AuthBinderService.class));
				finishAndRemoveTask();
			}
		}, 2000);
	}

	public void DismissDialogActivity(View v)
	{
		
	}

	public void Ignore(View v)
	{
		// Do nothing
	}

	@Override
	public void onBackPressed()
	{
		// super.onBackPressed();
	}
}
