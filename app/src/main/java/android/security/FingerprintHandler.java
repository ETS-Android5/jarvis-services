package android.security;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.widget.TextView;
import com.teslasoft.libraries.support.R;
import android.graphics.Color;
import android.widget.SmartToast;

public class FingerprintHandler extends FingerprintManager.AuthenticationCallback
{
	private Context context;

	// Constructor
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
		this.update("Not recognized", false);
	}
	
	@Override
	public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
		this.update("Authentication completed", true);
	}


	public int update(String e, Boolean success){
		TextView textView = (TextView) ((Activity)context).findViewById(R.id.fingerprint_error);
		textView.setText(e);
		
		
		if(success) {
			textView.setTextColor(Color.GREEN);
			((Activity)context).setResult(Activity.RESULT_OK);
			((Activity)context).finishAndRemoveTask();
			// SmartToast.create("Success", context);
			return 1;
		} else {
			textView.setTextColor(Color.RED);
			// SmartToast.create("Fail", context);
			return 2;
		}
	}
}
