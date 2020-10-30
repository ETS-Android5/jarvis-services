package com.teslasoft.jarvis.licence;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.widget.SmartToast;
import com.teslasoft.libraries.support.R;
import android.widget.TextView;
import android.view.View;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import java.security.MessageDigest;
import androidx.appcompat.widget.ToolbarWidgetWrapper;

public class PiracyCheckActivity extends Activity
{
	private String appId;
	private TextView message;
	public int RESPONSE_CODE;
	private int SELF_SIGNATURE = -635477034;
	private int DEFAULT_SIGNATURE = -672009692;
	private int GOOGLE_APPS_SIGNATURE = -473270056;
	private int ANDROID_SIGNATURE = -810989147;
	private String appSignature;
	private String signatureHash;
	private String isNotification;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		try {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			appId = extras.getString("appId");
			appSignature = extras.getString("appSign");
			isNotification = extras.getString("isNotif");
		} catch (Exception e) {
			isNotification = "false";
			InvalidateApplicationId();
		}

		try {
			if (appId.equals("null") || appSignature.equals("null") || isNotification.equals("null")) {
				isNotification = "false";
				InvalidateApplicationId();
			} else {
				try {
					Signature sig = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES).signatures[0];
					signatureHash = Integer.toString(sig.hashCode());
					// DEBUG: SmartToast.create(signatureHash + Integer.toString(SELF_SIGNATURE), this);
					if (signatureHash.equals(Integer.toString(DEFAULT_SIGNATURE))) {
						CheckLicence();
					} else {
						sendRequest(4);
					}
				} catch (Exception e) {
					sendRequest(3);
				}
			}
		} catch (Exception ee) {
			isNotification = "false";
			InvalidateApplicationId();
		}
	}
	
	public void InvalidateApplicationId() {
		if (isNotification.equals("true")) {
			RESPONSE_CODE = 2;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			message.setText("Invalid Licence Manager request. Please make sure that you provided valid extras to check licence: appId, appSign, isNotif. Error code: 2");
		} else {
			this.setResult(RESPONSE_CODE);
			finishAndRemoveTask();
		}
	}
	
	public void InvalidateLicence() {
		if (isNotification.equals("true")) {
			RESPONSE_CODE = 0;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			message.setText("You don't have licence for this application. It may be modified. Error code: 0");
		} else {
			this.setResult(RESPONSE_CODE);
			finishAndRemoveTask();
		}
	}
	
	public void InvalidateAntiPiracyProvider() {
		if (isNotification.equals("true")) {
			RESPONSE_CODE = 4;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			message.setText("We can't check licence because Jarvis Service has invalid signature. Error code: 4");
		} else {
			this.setResult(RESPONSE_CODE);
			finishAndRemoveTask();
		}
	}
	
	public void InvalidateExtras() {
		if (isNotification.equals("true")) {
			RESPONSE_CODE = 3;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			message.setText("No application found to check licence. Please make sure, that you provided correct package id. Error code: 3");
		} else {
			this.setResult(RESPONSE_CODE);
			finishAndRemoveTask();
		}
	}
	
	public void return_response(View v) {
		this.setResult(RESPONSE_CODE);
		finishAndRemoveTask();
	}
	
	public void sendRequest(int request) {
		RESPONSE_CODE = request;
		if (request == 3) {
			InvalidateExtras();
		} else if (request == 2) {
			InvalidateApplicationId();
		} else if (request == 0) {
			InvalidateLicence();
		} else if (request == 4) {
			InvalidateAntiPiracyProvider();
		} 
	}
	
	public void CheckLicence() {
		try {
			Signature sig = getPackageManager().getPackageInfo(appId, PackageManager.GET_SIGNATURES).signatures[0];
			signatureHash = Integer.toString(sig.hashCode());
			if (signatureHash.equals(appSignature)) {
				passLicence();
			} else {
				sendRequest(0);
			}
		} catch (Exception e) {
			sendRequest(3);
		}
	}

	@Override
	public void onBackPressed()
	{
		this.setResult(RESPONSE_CODE);
		finishAndRemoveTask();
	}
	
	public void passLicence() {
		// SmartToast.create("Application licenced", this);
		this.setResult(Activity.RESULT_OK);
		finishAndRemoveTask();
	}
	
	public boolean validateAppSignature() throws NameNotFoundException {
		PackageInfo packageInfo = getPackageManager().getPackageInfo(appId, PackageManager.GET_SIGNATURES);
		//note sample just checks the first signature
		for (Signature signature : packageInfo.signatures) {
			String sha1 = getSHA1(signature.toByteArray());
			// check is matches hardcoded value
			return appSignature.equals(sha1);
		}
		return false;
	}
	
	public static String bytesToHex(byte[] bytes) {
		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	public static String getSHA1(byte[] sig) {
		try {
  			MessageDigest digest = MessageDigest.getInstance("SHA1");
			digest.update(sig);
			byte[] hashtext = digest.digest();
			return bytesToHex(hashtext);
		} catch (Exception e) {
			return "0";
		}
	}
}
