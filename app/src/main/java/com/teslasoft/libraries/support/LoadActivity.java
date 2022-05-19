package com.teslasoft.libraries.support;

import android.app.Activity;
import android.os.Bundle;
import android.os.Process;
import android.os.Handler;
import android.content.Intent;
import android.content.ComponentName;
import android.util.Log;

public class LoadActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.fragment_loading_screen);

		try {
			Intent licenseIntent = new Intent(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class);
			startActivityForResult(licenseIntent, 1);
		} catch (Exception e) {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
		
		final Handler handler = new Handler();
		handler.postDelayed(() -> {
			try{
				Intent i = new Intent(LoadActivity.this, net.jarvis.engine.TaskCdhActivity.class);
				startActivity(i);
				finish();
			} catch (Exception e) {
				onConfigChanged(-1);
			}

			final Handler handler1 = new Handler();
			handler1.postDelayed(() -> onConfigChanged(1), 100);
		}, 1500);
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}
	
	@Override
	public void onBackPressed() {
		Process.killProcess(Process.myPid());
	}
	
	public void onConfigChanged(int cc) {
		if (cc == 1) {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.setComponent(new ComponentName("com.teslasoft.jarvis","com.teslasoft.jarvis.MainActivity"));
			startActivity(intent);
			finish();
		} else if (cc == -1) {
			String err = "Configuration error: Can't start configuration check service. Try to reinstall this program or enable all components of this program.";
			Log.e("Jarvis Runtime", err);
			Process.killProcess(Process.myPid());
			System.exit(-1);
		} else {
			Process.killProcess(Process.myPid());
			System.exit(-1);
		}
	}
}
