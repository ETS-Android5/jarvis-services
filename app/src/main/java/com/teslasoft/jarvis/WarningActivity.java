package com.teslasoft.jarvis;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.teslasoft.libraries.support.R;

public class WarningActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_warning);
	}

	public void DismissDialogActivity(View v) {
		finish();
		overridePendingTransition(0, 0);
	}

	public void Ignore(View v) {}

	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(0, 0);
	}
}
