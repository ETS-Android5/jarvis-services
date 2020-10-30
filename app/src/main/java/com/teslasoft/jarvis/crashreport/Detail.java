package com.teslasoft.jarvis.crashreport;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;
import com.teslasoft.libraries.support.R;

public class Detail extends Activity
{
	private Intent intent;
	private String textdet;
	
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_detail);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		
		try
		{
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			textdet = extras.getString("detail");
		}

		catch (Exception e)
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Value \"null\" is not applicable to method extras.getString()", Toast.LENGTH_SHORT); 
			toast.show();
			finish();
			overridePendingTransition(0, 0);
		}
	}

	public void DismissDialogActivity(View v)
	{
		finish();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}

	public void Ignore(View v)
	{
		//Do nothing
	}

	@Override
	public void onBackPressed()
	{
		finish();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}
}
