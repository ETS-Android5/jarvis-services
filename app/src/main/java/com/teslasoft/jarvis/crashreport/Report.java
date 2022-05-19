package com.teslasoft.jarvis.crashreport;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;
import android.content.Intent;

import com.teslasoft.libraries.support.R;

public class Report extends Activity {

	private String errt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bugreport_collect);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
		try {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			errt = extras.getString("errz");
		} catch (Exception e) {
			errt = "-676767";
			finish();
			overridePendingTransition(0, 0);
		}
		
		try {
			if (errt.contains("android.app.RemoteServiceException: Context.startForegroundService() did not then call Service.startForeground():")) {
				finish();
				overridePendingTransition(0, 0);
			} else {
				final Handler handler = new Handler();
				handler.postDelayed(() -> {
					if (errt == "-676767") {
						finish();
						overridePendingTransition(0, 0);
					} else {
						try {
							Intent i = new Intent(Report.this, BugReport.class);
							Bundle extras = new Bundle();
							extras.putString("errtext", errt);
							i.putExtras(extras);
							startActivity(i);
							finish();
							overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						} catch (Exception e) {
							finish();
							overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
							Toast toast = Toast.makeText(getApplicationContext(), "Service not available", Toast.LENGTH_SHORT);
							toast.show();
						}
					}
				}, 200);
			}
		} catch (Exception errn) {
			final Handler handler = new Handler();
			handler.postDelayed(() -> {
				if (errt == "-676767") {
					finish();
					overridePendingTransition(0, 0);
				} else {
					try {
						Intent i = new Intent(Report.this, BugReport.class);
						Bundle extras = new Bundle();
						extras.putString("errtext", errt);
						i.putExtras(extras);
						startActivity(i);
						finish();
						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					} catch (Exception e) {
						finish();
						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
						Toast toast = Toast.makeText(getApplicationContext(), "Service not available", Toast.LENGTH_SHORT);
						toast.show();
					}
				}
			}, 200);
		}
	}

	public void DismissDialogActivity(View v) {
		finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	public void Ignore(View v) {}

	@Override
	public void onBackPressed() {}
	
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
