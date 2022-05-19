package com.teslasoft.jarvis.core;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.content.pm.PackageManager;
import android.content.Intent;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.teslasoft.libraries.support.R;

public class ServicesActivity extends Activity {
	TextView csstate;
	TextView dpsstate;
	TextView lcsstate;
	TextView nsstate;
	TextView smstate;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_services_list);
		
		csstate = (TextView) findViewById(R.id.csstate);
		dpsstate = (TextView) findViewById(R.id.dpsstate);
		lcsstate = (TextView) findViewById(R.id.lcsstate);
		nsstate = (TextView) findViewById(R.id.nsstate);
		smstate = (TextView) findViewById(R.id.smstate);

		try {
			Intent licenseIntent = new Intent(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class);
			startActivityForResult(licenseIntent, 1);
		} catch (Exception e) {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			statement();
		} else {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}
	
	public void statement() {
		PackageManager pm = getPackageManager();
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, InitService.class)) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
			if (isMyServiceRunning(InitService.class)) {
				csstate.setText(R.string.state_running_s);
				csstate.setTextColor(0xFF2E8B57);
			} else {
				csstate.setText(R.string.state_stopped_s);
				csstate.setTextColor(0xFFFFFF00);
			}
		} else {
			csstate.setText(R.string.state_disabled_s);
			csstate.setTextColor(0xFFB20021);
		}

		if (pm.getComponentEnabledSetting(new ComponentName(this, DataProtectorService.class)) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
			if (isMyServiceRunning(DataProtectorService.class)) {
				dpsstate.setText(R.string.state_running_s);
				dpsstate.setTextColor(0xFF2E8B57);
			} else {
				dpsstate.setText(R.string.state_stopped_s);
				dpsstate.setTextColor(0xFFFFFF00);
			}
		} else {
			dpsstate.setText(R.string.state_disabled_s);
			dpsstate.setTextColor(0xFFB20021);
		}
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, LicenceCheckService.class)) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
			if (isMyServiceRunning(LicenceCheckService.class)) {
				lcsstate.setText(R.string.state_running_s);
				lcsstate.setTextColor(0xFF2E8B57);
			} else {
				lcsstate.setText(R.string.state_stopped_s);
				lcsstate.setTextColor(0xFFFFFF00);
			}
		} else {
			lcsstate.setText(R.string.state_disabled_s);
			lcsstate.setTextColor(0xFFB20021);
		}
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, NotificationStatService.class)) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
			if (isMyServiceRunning(NotificationStatService.class)) {
				nsstate.setText(R.string.state_running_s);
				nsstate.setTextColor(0xFF2E8B57);
			} else {
				nsstate.setText(R.string.state_stopped_s);
				nsstate.setTextColor(0xFFFFFF00);
			}
		} else {
			nsstate.setText(R.string.state_disabled_s);
			nsstate.setTextColor(0xFFB20021);
		}
		
		if (pm.getComponentEnabledSetting(new ComponentName(this, SmartCardService.class)) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED) {
			if (isMyServiceRunning(SmartCardService.class)) {
				smstate.setText(R.string.state_running_s);
				smstate.setTextColor(0xFF2E8B57);
			} else {
				smstate.setText(R.string.state_stopped_s);
				smstate.setTextColor(0xFFFFFF00);
			}
		} else {
			smstate.setText(R.string.state_disabled_s);
			smstate.setTextColor(0xFFB20021);
		}

		final Handler handler = new Handler();
		handler.postDelayed(this::statement, 500);
	}

	public void Close(View v) {
		finish();
	}

	public void CoreService(View v) {
		Intent intent = new Intent(this, ServiceSettingActivity.class);
		intent.putExtra("serviceId", 0);
		startActivity(intent);
	}
	
	public void DPService(View v) {
		Intent intent = new Intent(this, ServiceSettingActivity.class);
		intent.putExtra("serviceId", 1);
		startActivity(intent);
	}
	
	public void LCService(View v) {
		Intent intent = new Intent(this, ServiceSettingActivity.class);
		intent.putExtra("serviceId", 2);
		startActivity(intent);
	}
	
	public void NSService(View v) {
		Intent intent = new Intent(this, ServiceSettingActivity.class);
		intent.putExtra("serviceId", 3);
		startActivity(intent);
	}
	
	public void SMService(View v) {
		Intent intent = new Intent(this, ServiceSettingActivity.class);
		intent.putExtra("serviceId", 4);
		startActivity(intent);
	}

	public boolean isMyServiceRunning(Class<?> serviceClass) {
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		try {
			for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
				if (serviceClass.getName().equals(service.service.getClassName()))
					return true;
			}
		} catch (Exception ignored) {}
		return false;
	}

	public void Exec (View v) {}

	public void start (int serviceCode) {}
}
