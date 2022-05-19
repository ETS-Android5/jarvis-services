package com.teslasoft.jarvis.auth;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.teslasoft.libraries.support.R;

public class AuthEntryActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		String appId;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_verify_account);
		
		try {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			appId = extras.getString("appId");
		} catch (Exception e) {
			appId = "com.teslasoft.libraries.support";
		}
		
		try {
			if (appId.equals("")) {
				this.setResult(5);
				finishAndRemoveTask();
			} else {
				try {
					Intent intent = new Intent(this, AuthActivity.class);
					Bundle extras = new Bundle();
					extras.putString("appId", appId);
					intent.putExtras(extras);
					startActivityForResult(intent, 1);
				} catch (Exception e) {
					this.setResult(4);
					finishAndRemoveTask();
				}
			}
		} catch (Exception ee) {
			this.setResult(5);
			finishAndRemoveTask();
		}
	}

	public void DismissDialogActivity(View v) {}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setContentView(R.layout.activity_verify_account);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK) {
			this.setResult(Activity.RESULT_OK);
		} else if (resultCode == RESULT_CANCELED) {
			this.setResult(Activity.RESULT_CANCELED);
		} else if (resultCode == 3) {
			this.setResult(3);
		} else if (resultCode == 4) {
			this.setResult(4);
		} else if (resultCode == 5) {
			this.setResult(5);
		} else {
			this.setResult(Activity.RESULT_CANCELED);
		}

		finishAndRemoveTask();
	}

	public void Ignore(View v) {}

	@Override
	public void onBackPressed() {}
}
