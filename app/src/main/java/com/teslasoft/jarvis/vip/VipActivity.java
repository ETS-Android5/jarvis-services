package com.teslasoft.jarvis.vip;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ScrollView;

import com.teslasoft.libraries.support.R;

public class VipActivity extends Activity {
	
	ScrollView scrollview;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_vip);
		scrollview = (ScrollView) findViewById(R.id.scrollview);
		scrollview.setVerticalScrollBarEnabled(false);
	}
	
	public void PurchaseVip(View v) {
		Intent transaction = new Intent(this, com.teslasoft.jarvis.vip.VipPurchaseActivity.class);
		startActivity(transaction);
	}
}
