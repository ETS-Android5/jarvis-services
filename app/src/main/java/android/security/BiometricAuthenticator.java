package android.security;

import android.os.Bundle;
import android.app.Activity;
import com.teslasoft.libraries.support.R;
import android.hardware.fingerprint.FingerprintManager;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import androidx.core.app.ActivityCompat;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import android.Manifest;
import android.widget.TextView;
import android.view.View;
import android.graphics.Color;
import android.view.WindowManager;
import android.widget.SmartToast;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Context;

public class BiometricAuthenticator extends Activity
{
	private KeyStore keyStore;
	// Variable used for storing the key in the Android Keystore container
	private static final String KEY_NAME = "androidHive";
	private Cipher cipher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
		
		setContentView(R.layout.activity_biometric);
		
		TextView textView = (TextView) findViewById(R.id.fingerprint_error);
		
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
		
		String android_id = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		
		// SmartToast.create(android_id, this);
		
		if (verifyInstallerId(this) || android_id.equals("d15c94372be47f06")) {
			if (android_id.equals("d15c94372be47f06")) {
				SmartToast.create("WARNING! A test device detected. Licence check skipped.", this);
			}
		try {
			boolean isSupported = fingerprintManager.isHardwareDetected();
			
			if(isSupported) {
				textView.setText("Touch the fingerprint sensor");
				textView.setTextColor(Color.GRAY);
			}
			
		} catch (Exception ee) {
			SmartToast.create("Canceled: Your device does not support fingerprint. Please select other authentication method", this);
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
		
		if(!fingerprintManager.isHardwareDetected()) {
			textView.setTextColor(Color.RED);
			textView.setText("Fingerprint is not supported on your device");
		} else {
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
				textView.setTextColor(Color.RED);
				textView.setText("The authenticator does not have permissions to use fingerprint");
				SmartToast.create("Canceled: First please enable screen lock", this);
				this.setResult(Activity.RESULT_CANCELED);
				finishAndRemoveTask();
			} else {
				if (!keyguardManager.isKeyguardSecure()) {
					textView.setTextColor(Color.RED);
					textView.setText("ERROR: Unsafe authentication method: The device does not have screenlock");
					SmartToast.create("Canceled: The app does not have permission to use fingerprint hardware", this);
					this.setResult(Activity.RESULT_CANCELED);
					finishAndRemoveTask();
				} else {
					try {
						generateKey();
					
						if (cipherInit()) {
							FingerprintManager.CryptoObject cryptoObject = new FingerprintManager.CryptoObject(cipher);
							FingerprintHandler helper = new FingerprintHandler(this);
							helper.startAuth(fingerprintManager, cryptoObject);
						}
					} catch (Exception e) {
						SmartToast.create("Canceled: No fingerprints found. Please add at least 1 fingerprint", this);
						this.setResult(Activity.RESULT_CANCELED);
						finishAndRemoveTask();
					}
				}
			}
		}
		
	} else {
		//toast("Verification failed");
		new AlertDialog.Builder(this)
			.setTitle("Verification failed")
			.setMessage("We can not check licence because this app installed from third-party source. Try to install it from Google Play. We perform this check to prevent tampering with API and security attacks. [ERR_PREFERAL_INSTALLED_BY_PACKAGE_INSTALLER]: -1")
			.setCancelable(false)
			// Specifying a listener allows you to take an action before dismissing the dialog.
			// The dialog is automatically dismissed when a dialog button is clicked.
			.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					close();
				}
			})

			// A null listener allows the button to dismiss the dialog and take no further action.
			.show();
	}
	}
	
	/* Piracy check starts */
	boolean verifyInstallerId(Context context) {
		// A list with valid installers package name
		List<String> validInstallers = new ArrayList<>(Arrays.asList("com.android.vending", "com.google.android.feedback"));

		// The package name of the app that has installed your app
		final String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());

		// true if your app has been downloaded from Play Store 
		return installer != null && validInstallers.contains(installer);
	}
	/* Piracy check ends */
	
	@TargetApi(Build.VERSION_CODES.M)
	protected void generateKey() {
		try {
			keyStore = KeyStore.getInstance("AndroidKeyStore");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		KeyGenerator keyGenerator;
		try {
			keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			throw new RuntimeException("Failed to get KeyGenerator instance", e);
		}
		
		try {
			keyStore.load(null);
			keyGenerator.init(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT)
				.setBlockModes(KeyProperties.BLOCK_MODE_CBC)
				.setUserAuthenticationRequired(true)
				.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7)
				.build());
				
				keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException | InvalidAlgorithmParameterException | CertificateException | IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	@TargetApi(Build.VERSION_CODES.M)
	public boolean cipherInit() {
		try {
			cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new RuntimeException("Failed to get Cipher", e);
		}
		
		try {
			keyStore.load(null);
			SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
														null);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			return true;
		} catch (KeyPermanentlyInvalidatedException e) {
			return false;
		} catch (KeyStoreException | CertificateException | UnrecoverableKeyException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException("Failed to init Cipher", e);
		}
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		close();
	}

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		close();
	}
	
	public void Cancel(View v) {
		close();
	}
	
	public void Password(View v) {
		this.setResult(3);
		finishAndRemoveTask();
	}
	
	public void Empty(View v) {
		
	}
	
	public void close() {
		this.setResult(Activity.RESULT_CANCELED);
		finishAndRemoveTask();
	}
}
