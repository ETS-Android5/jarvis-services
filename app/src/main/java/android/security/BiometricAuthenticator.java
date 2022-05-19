package android.security;


import android.os.Bundle;
import android.app.Activity;
import android.app.KeyguardManager;
import android.hardware.fingerprint.FingerprintManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.widget.SmartToast;
import android.widget.TextView;
import android.view.View;
import android.view.WindowManager;
import android.graphics.Color;
import android.Manifest;

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

import com.teslasoft.libraries.support.R;

public class BiometricAuthenticator extends Activity {
	private KeyStore keyStore;

	// Variable used for storing the key in the Android Keystore container
	private static final String KEY_NAME = "androidHive";
	private Cipher cipher;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SECURE);
		
		setContentView(R.layout.activity_biometric);
		
		TextView textView = (TextView) findViewById(R.id.fingerprint_error);
		
		KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
		FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

		try {
			Intent licenseIntent = new Intent(this, com.teslasoft.jarvis.licence.PiracyCheckActivity.class);
			startActivityForResult(licenseIntent, 1);
		} catch (Exception e) {
			// User tried to disable or bypass license checking service, exit
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}

		try {
			boolean isSupported = fingerprintManager.isHardwareDetected();
			
			if(isSupported) {
				textView.setText(R.string.fingerprint_touch_message);
				textView.setTextColor(Color.GRAY);
			}
		} catch (Exception ee) {
			SmartToast.create(this.getResources().getString(R.string.fingerprint_unsupported_device), this);
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
		
		if(!fingerprintManager.isHardwareDetected()) {
			textView.setTextColor(Color.RED);
			textView.setText(R.string.fingerprint_unsupported_device);
		} else {
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
				textView.setTextColor(Color.RED);
				textView.setText(R.string.fingerprint_permission_denied);
				this.setResult(Activity.RESULT_CANCELED);
				finishAndRemoveTask();
			} else {
				if (!keyguardManager.isKeyguardSecure()) {
					textView.setTextColor(Color.RED);
					textView.setText(R.string.fingerprint_err_unsafe_operation);
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
						SmartToast.create(this.getResources().getString(R.string.fingerprint_not_prints), this);
						this.setResult(Activity.RESULT_CANCELED);
						finishAndRemoveTask();
					}
				}
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode != Activity.RESULT_OK) {
			this.setResult(Activity.RESULT_CANCELED);
			finishAndRemoveTask();
		}
	}

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
	protected void onPause() {
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		close();
	}
	
	public void Cancel(View v) {
		close();
	}
	
	public void Password(View v) {
		this.setResult(3);
		finishAndRemoveTask();
	}
	
	public void Empty(View v) {}
	
	public void close() {
		this.setResult(Activity.RESULT_CANCELED);
		finishAndRemoveTask();
	}
}
