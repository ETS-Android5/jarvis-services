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
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.widget.SmartToast;

public class PreAuthActivity extends Activity
{
	TextView text;
	private String appId;
	
	/*AccountManager am = AccountManager.get(this);
	Bundle options = new Bundle();*/
	
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
		// text = (TextView) findViewById(R.id.text);
		// text.setText("Checking info...");
		
		try {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			appId = extras.getString("appId");
		} catch (Exception e) {
			PreAuthActivity.this.setResult(5);
			finishAndRemoveTask();
		}
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				try {
					Intent intent = new Intent(PreAuthActivity.this, com.teslasoft.jarvis.auth.AuthActivity.class);
					Bundle extras = new Bundle();
					extras.putString("appId", appId);
					intent.putExtras(extras);
					startActivityForResult(intent, 1);
				} catch (Exception j) {
					PreAuthActivity.this.setResult(4);
					finishAndRemoveTask();
				}
			}
		}, 3000);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK)
		{
			final Handler handler = new Handler();
			handler.postDelayed(new Runnable()
			{
				@Override
				public void run()
				{
					// SmartToast.create("Credentials created and stored in /mnt/sdcard/jarvis/auth/" + appId + "/credentials.json", PreAuthActivity.this);
					PreAuthActivity.this.setResult(Activity.RESULT_OK);
					finishAndRemoveTask();
				}
			}, 100);
		} else if (resultCode == 3) {
			this.setResult(3);
			finishAndRemoveTask();
		} else if (resultCode == 4) {
			this.setResult(4);
			finishAndRemoveTask();
		} else if (resultCode == 5) {
			this.setResult(5);
			finishAndRemoveTask();
		} else {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}

	public void DismissDialogActivity(View v)
	{
		/*this.setResult(Activity.RESULT_CANCELED);
		finishAndRemoveTask();*/
	}

	public void Ignore(View v)
	{
		// Do nothing
	}

	@Override
	public void onBackPressed()
	{
		this.setResult(Activity.RESULT_CANCELED);
		finishAndRemoveTask();
	}
}

/*private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
	@Override
	public void run(AccountManagerFuture<Bundle> result) {
		// Get the result of the operation from the AccountManagerFuture.
		Bundle bundle = result.getResult();

		// The token is a named value in the bundle. The name of the value
		// is stored in the constant AccountManager.KEY_AUTHTOKEN.
		String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
		
	}
}*/
