package android.security;

import android.app.Activity;
import android.os.CancellationSignal;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.widget.TextView;
import android.graphics.Color;
import android.Manifest;

import androidx.core.app.ActivityCompat;

import com.teslasoft.libraries.support.R;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {
	private final Context context;

	public FingerprintHandler(Context mContext) {
		context = mContext;
	}
	
	public void startAuth(FingerprintManager manager, FingerprintManager.CryptoObject cryptoObject) {
		CancellationSignal cancellationSignal = new CancellationSignal();
		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
			return;
		}
		
		manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
	}
	
	@Override
	public void onAuthenticationError(int errMsgId, CharSequence errString) {
		this.update(errString.toString(), false);
	}

	@Override
	public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
		this.update(helpString.toString(), false);
	}

	@Override
	public void onAuthenticationFailed() {
		this.update(context.getResources().getString(R.string.fingerprint_not_recognized), false);
	}
	
	@Override
	public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
		this.update(context.getResources().getString(R.string.fingerprint_completed), true);
	}

	public int update(String e, Boolean success){
		TextView textView = (TextView) ((Activity)context).findViewById(R.id.fingerprint_error);
		textView.setText(e);

		if(success) {
			textView.setTextColor(Color.GREEN);
			((Activity)context).setResult(Activity.RESULT_OK);
			((Activity)context).finishAndRemoveTask();
			return 1;
		} else {
			textView.setTextColor(Color.RED);
			return 2;
		}
	}
}
