package com.teslasoft.jarvis.auth;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;
import com.teslasoft.libraries.support.R;
import android.content.ComponentName;

public class AuthActivity extends Activity
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
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		setContentView(R.layout.add_accound_complete);
		
		try
		{
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setComponent(new ComponentName("com.teslasoft.jarvis.accounts","com.teslasoft.jarvis.accounts.MainActivity"));
			intent.addCategory(Intent.CATEGORY_LAUNCHER);
			intent.putExtra("appId", "com.teslasoft.libraries.support");
			intent.putExtra("GET_PUBLIC_INFO", "true");
			intent.putExtra("FULL_ACCESS", "true");
			intent.putExtra("SETTINGS_MODIFY", "true");
			intent.putExtra("READ_HISTORY", "true");
			intent.putExtra("EDIT_SECURITY_SETTINGS", "true");
			intent.putExtra("USE_DEVELOPER_FUNCTIONS", "true");
			startActivity(intent);
			reqCode(0);
			finishAffinity();
		}
		
		catch (Exception _e)
		{
			reqCode(-1);
			Toast toast = Toast.makeText(getApplicationContext(), "Error: Authenticator not found", Toast.LENGTH_SHORT); 
			toast.show();
			
			try
			{
				stopService(new Intent(com.teslasoft.jarvis.auth.AuthActivity.this, com.teslasoft.jarvis.auth.AuthService.class));
				finishAffinity();
			}
			
			catch (Exception et)
			{
				finishAffinity();
			}
		}
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
		
	}
	
	public int reqCode(int code)
	{
		return code;
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
}
