package com.teslasoft.jarvis.vip;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;

import com.android.billingclient.api.BillingClient;

import com.teslasoft.libraries.support.R;

public class VipPurchaseActivity extends Activity {

	private BillingClient mBillingClient;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vip_purchase);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	public void Close(View v) {
		finishAndRemoveTask();
	}
	
	public void nulclick (View v) {}

	@Override
	public void onBackPressed() {
		finishAndRemoveTask();
	}
}
