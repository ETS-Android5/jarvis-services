package com.teslasoft.jarvis.crashreport;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import com.teslasoft.libraries.support.R;
import android.widget.TextView;
import android.widget.Button;

public class BugReport extends Activity
{
	private String texterr;
	private Intent intent;
	private TextView tem;
	
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.report_info);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		
		try
		{
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			texterr = extras.getString("errtext");
		
			tem = (TextView) findViewById(R.id.tem);
			tem.setTextIsSelectable(true);
			tem.setText(texterr);
		}
		
		catch (Exception e)
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Value \"null\" is not applicable to method extras.getString()", Toast.LENGTH_SHORT); 
			toast.show();
			finishAndRemoveTask();
			overridePendingTransition(0, 0);
		}
		
	}

	public void DismissDialogActivity(View v)
	{
		finishAndRemoveTask();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}
	
	public void CopyMsg(View v)
	{
		android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE); 
        android.content.ClipData clip = android.content.ClipData.newPlainText("Error", tem.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "Error message copied to clipboard", Toast.LENGTH_SHORT).show();
	}
	
	public void Detail(View v)
	{
		try
		{
			Intent i = new Intent(com.teslasoft.jarvis.crashreport.BugReport.this, com.teslasoft.jarvis.crashreport.Detail.class);
			Bundle extras = new Bundle();
			extras.putString("detail", texterr);
			i.putExtras(extras);
			startActivity(i);
		}

		catch (Exception e)
		{
			Toast toast = Toast.makeText(getApplicationContext(), "Service not avaliable", Toast.LENGTH_SHORT); 
			toast.show();
		}
	}
	
	public void Ignore(View v)
	{
		//Do nothing
	}

	@Override
	public void onBackPressed()
	{
		finishAndRemoveTask();
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
