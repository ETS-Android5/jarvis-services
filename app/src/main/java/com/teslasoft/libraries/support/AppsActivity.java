package com.teslasoft.libraries.support;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;

public class AppsActivity extends Activity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_loading_screen);
		Intent i = getPackageManager().getLaunchIntentForPackage("com.android.jss");
		if (i == null) return;

		i.addCategory(Intent.CATEGORY_LAUNCHER);
		i.putExtra("type", "ACTION_GET_APPS");
		i.putExtra("data", "APPS_VIEW");
		startActivity(i);
		finishAffinity();
    }
}
