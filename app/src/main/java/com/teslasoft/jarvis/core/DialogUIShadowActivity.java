package com.teslasoft.jarvis.core;

import android.app.Activity;
import android.os.Bundle;

import com.teslasoft.libraries.support.R;

public class DialogUIShadowActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_shadow);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finishAndRemoveTask();
	}
}
