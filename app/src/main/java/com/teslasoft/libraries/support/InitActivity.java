package com.teslasoft.libraries.support;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class InitActivity extends Activity {
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.init);

		try {
			Intent i = new Intent(InitActivity.this, net.jarvis.engine.TaskCdhActivity.class);
			startActivity(i);
			finish();
		} catch (Exception e) {
			finishAndRemoveTask();
		}
    }
}
