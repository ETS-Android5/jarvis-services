package com.teslasoft.libraries.support;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

public class RateActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_view);
    }

	@Override
	public void onBackPressed() {
		super.onBackPressed();
	}
	
	public void Close(View v) {
		finishAndRemoveTask();
	}
	
	public void Rate(View v) {
		finishAndRemoveTask();
	}
}
