package com.teslasoft.jarvis.crashreport;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.teslasoft.libraries.support.R;

public class BugReport extends Activity {

	private String texterr;
	private TextView tem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bugreport_info);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		
		try {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			texterr = extras.getString("errtext");
			tem = (TextView) findViewById(R.id.tem);
			tem.setTextIsSelectable(true);
			tem.setText(texterr);
		} catch (Exception e) {
			finishAndRemoveTask();
			overridePendingTransition(0, 0);
		}
	}

	public void DismissDialogActivity(View v) {
		finishAndRemoveTask();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
	
	public void CopyMsg(View v) {
		android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE); 
        android.content.ClipData clip = android.content.ClipData.newPlainText("Error", tem.getText().toString());
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), getResources().getString(R.string.stacktrace_copied), Toast.LENGTH_SHORT).show();
	}
	
	public void Detail(View v) {
		try {
			Intent i = new Intent(com.teslasoft.jarvis.crashreport.BugReport.this, com.teslasoft.jarvis.crashreport.Detail.class);
			Bundle extras = new Bundle();
			extras.putString("detail", texterr);
			i.putExtras(extras);
			startActivity(i);
		} catch (Exception ignored) {}
	}
	
	public void Ignore(View v) {}

	@Override
	public void onBackPressed() {
		finishAndRemoveTask();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
	
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
