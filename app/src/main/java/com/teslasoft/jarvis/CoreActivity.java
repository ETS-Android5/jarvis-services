package com.teslasoft.jarvis;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;

import com.teslasoft.libraries.support.R;

public class CoreActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_core_stats_service);
		
		overridePendingTransition(0, 0);
		
		Intent i = new Intent(com.teslasoft.jarvis.CoreActivity.this, com.teslasoft.jarvis.core.ServicesActivity.class);
		startActivity(i);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(0, 0);
	}
}
