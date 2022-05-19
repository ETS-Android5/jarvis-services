package com.teslasoft.jarvis.crashreport;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;

import com.teslasoft.libraries.support.R;

public class Detail extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bugreport_detail);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
		try {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			String textdet = extras.getString("detail");

			// TODO: Implement ...
		} catch (Exception e) {
			finish();
			overridePendingTransition(0, 0);
		}
	}

	public void DismissDialogActivity(View v) {
		finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	public void Ignore(View v) {}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
}
