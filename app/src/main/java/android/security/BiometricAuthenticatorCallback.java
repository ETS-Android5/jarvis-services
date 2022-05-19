package android.security;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Intent;
import android.widget.SmartToast;
import android.transition.TransitionInflater;
import android.transition.Fade;

import com.teslasoft.libraries.support.R;

public class BiometricAuthenticatorCallback extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_shadow);
		
		TransitionInflater inflater = TransitionInflater.from(this);
		Fade fade = new Fade();
		fade.setDuration(150);
		getWindow().setEnterTransition(fade);
		getWindow().setExitTransition(fade);
		
		final Handler handler = new Handler();
		handler.postDelayed(() -> {
			try {
				Intent intent = new Intent(BiometricAuthenticatorCallback.this, BiometricAuthenticator.class);
				startActivityForResult(intent, 1);
			} catch (Exception jj) {
				SmartToast.create("Authentication rejected: 1 [ERR_COMPONENT_DISABLED_BY_PACKAGE_MANAGER]: The target activity can not be accessed from the system", BiometricAuthenticatorCallback.this);
				BiometricAuthenticatorCallback.this.setResult(Activity.RESULT_CANCELED);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finishAndRemoveTask();
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}
		}, 100);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			final Handler handler = new Handler();
			handler.postDelayed(() -> {
				BiometricAuthenticatorCallback.this.setResult(Activity.RESULT_OK);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finishAndRemoveTask();
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}, 100);
		} else if (resultCode == 3) {
			try {
				KeyguardManager km = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

				if (km.isKeyguardSecure()) {
					Intent authIntent = km.createConfirmDeviceCredentialIntent(this.getResources().getString(R.string.authentication_title), this.getResources().getString(R.string.authentication_message));
					startActivityForResult(authIntent, 1);
				}
			} catch (Exception jj) {
				SmartToast.create("Function unavailable: 3 [ERR_SYSTEM_AUTH_INSECURE_METHOD]: The device's security settings does not have screen lock or the system uses insecure encryption algorithms for authentication", BiometricAuthenticatorCallback.this);

				try {
					Intent intent = new Intent(BiometricAuthenticatorCallback.this, android.security.BiometricAuthenticator.class);
					startActivityForResult(intent, 1);
				} catch (Exception ji) {
					SmartToast.create("Authentication rejected: 1 [ERR_COMPONENT_DISABLED_BY_PACKAGE_MANAGER] The target activity can not be accessed from the system", BiometricAuthenticatorCallback.this);
					BiometricAuthenticatorCallback.this.setResult(Activity.RESULT_CANCELED);
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					finishAndRemoveTask();
					overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				}
			}
		} else {
			final Handler handler = new Handler();
			handler.postDelayed(() -> {
				BiometricAuthenticatorCallback.this.setResult(Activity.RESULT_CANCELED);
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
				finishAndRemoveTask();
				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
			}, 100);
		}
	}

	@Override
	public void onBackPressed() {
		BiometricAuthenticatorCallback.this.setResult(Activity.RESULT_CANCELED);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finishAndRemoveTask();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
}
