package com.teslasoft.jarvis.licence;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.SmartToast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.nimbusds.jose.JWSObject;
import com.teslasoft.libraries.support.R;
import android.widget.TextView;
import android.view.View;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;

import androidx.annotation.NonNull;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import android.content.SharedPreferences;

import com.google.android.vending.licensing.*;
import com.android.vending.licensing.*;

import net.minidev.json.JSONObject;

public class PiracyCheckActivity extends Activity
{
	private String appId;
	private TextView message;
	public int RESPONSE_CODE;
	private static final int SELF_SIGNATURE = -635477034;
	private static final int DEFAULT_SIGNATURE = -672009692;
	private static final int GOOGLE_APPS_SIGNATURE = -473270056;
	private static final int ANDROID_SIGNATURE = -810989147;
	private String appSignature;
	private String signatureHash;
	private String isNotification;
	private static final String BASE64_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxbBPICgYEeAL52zfqzmALqbqA80zImnBaWjhYPwL+ZmlTymSyBQT6Vwp1WuJlVGP5tBm3HR3FZCaf7DmBPH4uYRQsrmj9bLrrQgGbp0OC14BtNHBOsGHzEGM2yJPAbutjE93HB1/anxUi4o1oUyWbf73M5u6cW/ltO88muiu4NPElcywZZdlHR41cpPc8kE6q5P4c0mqfK/Ry8im8GN0hsAF6K582N6BDc3hVKITleX5O/XGBjy5wCUvsPoV4c6n/ZEDCanx1dLuNKbBhkv2ykWeysCafJH95g+Yfef6mKn689LhYjdbmoOyDtQk4xjxyhh9dllROooOdOqOXhxzEwIDAQAB";
	private static final byte[] SALT = new byte[] {20, 117, -56, 82, 49, -3, 0, 13, 56, 74, -42, 89, -101, 120, 96, 66, 82, -11, 9, 112};
	private Handler mHandler;
	private LicenseChecker mChecker;
	private LicenseCheckerCallback mLicenseCheckerCallback;
	boolean licensed;
	boolean checkingLicense;
	boolean didCheck;

	public Context context;
	public PackageManager packageManager;

	public static int LICENSED = 0; // Product is licensed
	public static int UNLICENSED_INVALID_INSTALLER_ID = 1; // Product is installed from third-party source
	public static int UNLICENSED_INVALID_PACKAGE_NAME = 2; // Package name of product is invalid
	public static int UNLICENSED_SIGNATURE_VERIFICATION_SYSTEM_TAMPERED_WITH = 3; // A software modification that prevent LicenseManager service to verify package signature
	public static int UNLICENSED_INVALID_SIGNATURE = 4; // Product package has invalid signature. Usually due to modification by user or third-party software
	public static int UNLICENSED_MALICIOUS_SOFTWARE_FOUND = 5; // Suspicious apps is installed. The apps that can modify other apps/root system/gain administrative privileges without permission
	public static int UNLICENSED_GMS_CORE_OUTDATED = 6; // A Google Play Services is necessary to perform SafetyNet check
	public static int UNLICENSED_SAFETYNET_ATTESTATION_FAILED_NOT_CERTIFIED = 7; // The device on which this product is running is not certified
	public static int UNLICENSED_SAFETYNET_ATTESTATION_FAILED_API_ERROR = 8; // An API error occurred while processing license verification request
	public static int UNLICENSED_PLAY_STORE_NOT_LICENSED = 9; // Product does not have Google Play license
	public static int UNLICENSED_PLAY_STORE_NOT_LICENSED_OLD_KEY = 10; // Product license key has expired or invalidated due to changes in the product price/version/features/etc...
	public static int UNLICENSED_PLAY_STORE_ERROR_NOT_MARKET_MANAGED = 11; // Product is installed from other market
	public static int UNLICENSED_PLAY_STORE_ERROR_SERVER_FAILURE = 12; // Google Play servers are unavailable or internal error is occurred
	public static int UNLICENSED_PLAY_STORE_ERROR_CONTACTING_SERVER = 13; // Error contacting Google Play servers to verify license
	public static int UNLICENSED_PLAY_STORE_ERROR_INVALID_PACKAGE_NAME = 14; // Google Play licensing service has detected that this product have invalid package name
	public static int UNLICENSED_PLAY_STORE_ERROR_NOT_MATCHING_UID = 15; // An error occurred while running Google Play license check
	public static int ERR_NO_INTERNET_CONNECTION = 16; // No Internet connection
	public static int UNLICENSED_INVALID_SAFETYNET_RESPONSE = 17; // SafetyNet response is corrupted or its signature is not valid
	public static int UNLICENSED_EMULATOR_DETECTED = 18; // The product is running in the emulator/sandbox
	public static int UNLICENSED_INVALID_LICENSE_KEY = 19; // The license key that saved to the app info and calculated (based on the SafetyNet response) license key don't match
	public static int UNLICENSED_CACHED_RESPONSE_MISMATCH = 20; // SafetyNet cached response have (or not have) fields with invalid values

	public static String DEFAULT_LICENSE_KEY = "e7bd492511f6defdc65d6565d522d2e6f35a2bf273d28503c1def3256021c7ad";

	public boolean is_licensed = false;

	/***************************************
	 * License check steps:
	 *
	 * 1) Checking installation source. (According to the license agreement this app can be installed only by Google Play Store)
	 * 2) Checking digital signature. (Get hash sum of each file in the package and compare it with hashes of the original app)
	 * 3) Check if any malicious software is installed. (Software that can patch/crack/emulate other apps)
	 * 4) Full hardware and software attestations. (All components of the device (including operation system, sensors, hardware) must be declared by the device manufacturer and verified (pass compatibility and safety tests) by Google LLC and Teslasoft LLC)
	 * 5) Additional checks to detect if operation system/hardware is modified and/or bootloader is unlocked (read oem state). Check if app is running on an emulator.
	 * 6) Deep server-side license check by Google Play Store. (Check if app have a license key and verify license purchase (if applicable). Check and verify all components of app (compare all files and resources of this app with files and resources in the database on the remote server))
	 * 7) Check java dalvik (ODEX/VDEX) and android runtime cache and fix it if it differs from classes.dex files. In some caches clear all data and setting of this app.
	 * 8) Check if app is opened firstly and fix (reset) its settings if it differs from default settings. Synchronize default settings with the server. Check for all data types and invalidate license if setting have invalid data type that can not be changed to correct one.
	 *
	 * *************************************/
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_loading);

		if (isDeviceRooted()) {
			// Firstly check root to prevent API abuse and repeated requests
			RESPONSE_CODE = 7;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			message.setText("UNLICENSED: This device is rooted. Please unroot it, install original ROM and lock the bootloader.");
		} else {
			SharedPreferences settings = this.getSharedPreferences("license", Context.MODE_PRIVATE);
			try {
				String have_license_key = settings.getString("license_certified_response", null);
				String license_key_signature = settings.getString("license_signature", null);
				String raw_license_key = settings.getString("license_key", null);
				String license_response = settings.getString("license_json_response", null);
				if (!have_license_key.equals("") && !license_key_signature.equals("") && !raw_license_key.equals("") && !license_response.equals("")) {
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					byte[] hash = digest.digest(have_license_key.getBytes(StandardCharsets.UTF_8));
					// Decoded license key is a 64-digit key (checksum of the safetynet response)
					String license_key_decoded = bytesToHex(hash);
					if (!license_key_decoded.equals(raw_license_key)) {
						// The app have a license key but it is invalid
						RESPONSE_CODE = 19;
						setContentView(R.layout.licence_check);
						message = (TextView) findViewById(R.id.message);
						message.setText("UNLICENSED: We re unable to verify your license. Please clear all data/cache of this app, then connect to the Internet and try again. You may see this message because this app is tampered with or license key is counterfeit or compromised. We need to revalidate your license by making signed request to the servers.");
					} else {
						if (isDeviceRooted()) {
							// Remove all license information as device state has changed and target device are no longer compatible with license requirements
							SharedPreferences settings1 = this.getSharedPreferences("license", Context.MODE_PRIVATE);
							SharedPreferences.Editor editor = settings1.edit();

							// If this app was activated on the device that met license requirements in the past but now it is rooted, invalidate license
							editor.remove("license_certified_response");
							editor.remove("license_signature");
							editor.remove("license_key");
							editor.remove("license_json_response");
							editor.apply();
							RESPONSE_CODE = 7;
							setContentView(R.layout.licence_check);
							message = (TextView) findViewById(R.id.message);
							message.setText("UNLICENSED: This device is rooted. Please unroot it, install original ROM and lock the bootloader.");
						} else {
							// The app already have a valid license key
					/*RESPONSE_CODE = 0;
					setContentView(R.layout.licence_check);
					message = (TextView) findViewById(R.id.message);
					message.setText("LICENSED: This product is licensed. This is a debug message. Please remove it before releasing. DEBUG: ".concat(raw_license_key).concat("  ").concat(license_key_decoded));
					*/
							Gson gson = new Gson();
							Map<String,Object> map = new HashMap<>();
							map = (Map<String,Object>) gson.fromJson(license_response ,map.getClass());
							String basicIntegrity = Objects.requireNonNull(map.get("basicIntegrity")).toString();
							String ctsProfileMatch = Objects.requireNonNull(map.get("ctsProfileMatch")).toString();

							if (basicIntegrity.equals("true") && ctsProfileMatch.equals("true")) {
								passLicense();
							} else {
								RESPONSE_CODE = 20;
								setContentView(R.layout.licence_check);
								message = (TextView) findViewById(R.id.message);
								message.setText("UNLICENSED: Invalid license information. Please clear cache/data then connect to the Internet and try again.");
							}
						}
					}
				} else {
					// To obtain a license key app must meet license requirements: Installed from Google Play, not modified, running on the unrooted original physical device (NOT EMULATOR NOR SANDBOX) with locked bootloader that certified by Google LLc and Teslasoft LLC
					startChecking();
				}
			} catch (Exception e) {
				// To obtain a license key app must meet license requirements: Installed from Google Play, not modified, running on the unrooted original physical device (NOT EMULATOR NOR SANDBOX) with locked bootloader that certified by Google LLc and Teslasoft LLC
				startChecking();
			}
		}
	}

	public void startChecking() {
		String android_id = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

		// Device with this ID will automatically bypass all license checks even if this device does not meet license requirements to run.
		String testDevice = "d604e107d1fea469";
		String testDevice2 = "2cea2e526b914498";
		// Checking licence
		// First step: check installation source

		if (verifyInstallerId(this) || android_id.equals(testDevice) || android_id.equals(testDevice2)) {
			// Second step: check signature hash
			try {
				@SuppressLint("PackageManagerGetSignatures") Signature sig = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES).signatures[0];
				signatureHash = Integer.toString(sig.hashCode());
				// SmartToast.create(signatureHash + Integer.toString(SELF_SIGNATURE), this);
				if (signatureHash.equals(Integer.toString(DEFAULT_SIGNATURE)) || signatureHash.equals(Integer.toString(SELF_SIGNATURE)) || android_id.equals(testDevice) || android_id.equals(testDevice2)) {
					StrictLicenceChecking();
				} else {
					InvalidateAntiPiracyProvider();
				}
			} catch (Exception e) {
				RESPONSE_CODE = 2;
				setContentView(R.layout.licence_check);
				message = (TextView) findViewById(R.id.message);
				message.setText("UNLICENSED: We are detected that you are using a modified copy of app. Signature of original app and this app does not match. Although the license is free of charge we are NOT allow use our app if it installed from third-party source or modified. We perform that checks to increase security.");
			}
		} else {
			InvalidateLicence();
		}
	}

	// WARNING: TODO: FOR DEBUG PURPOSES. REMOVE THIS FUNCTION BEFORE RELEASING APP.
	public boolean PiracyCheckException() {
		String android_id = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
		String testDevice1 = "d604e107d1fea469";
		String testDevice2 = "";
		String testDevice3 = "";
		String testDevice4 = "";
		String testDevice5 = "";

		if (android_id.equals(testDevice1) || android_id.equals(testDevice2) || android_id.equals(testDevice3) || android_id.equals(testDevice4) || android_id.equals(testDevice5)) return true;
		else {
			return false;
		}
	}

	boolean verifyInstallerId(Context context) {
		// A list with valid installers package name
		List<String> validInstallers = new ArrayList<>(Arrays.asList("com.android.vending", "com.google.android.feedback"));

		// The package name of the app that has installed your app
		final String installer = context.getPackageManager().getInstallerPackageName(context.getPackageName());

		// true if your app has been downloaded from Play Store
		return installer != null && validInstallers.contains(installer);
	}

	public void InvalidateApplicationId() {
			RESPONSE_CODE = 2;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			message.setText("UNLICENSED: We are detected that you are using a modified copy of app. Signature of original app and this app does not match. Although the license is free of charge we are NOT allow use our app if it installed from third-party source or modified. We perform that checks to increase security.");
	}

	public void InvalidateLicence() {
		String aid = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

		RESPONSE_CODE = 1;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			message.setText("UNLICENSED: Looks like you are using unlicensed copy of this app. Although the license is free of charge we are NOT allow use our app if it installed from third-party source or modified. We perform that checks to increase security. ".concat(aid));
	}
	
	public void InvalidateAntiPiracyProvider() {
		if (isNotification.equals("true")) {
			RESPONSE_CODE = 4;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			message.setText("UNLICENSED: This app has invalid signature. It may be cracked.");
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

	public static byte[] hexStringToByteArray(String s) {
		int len = s.length();
		byte[] data = new byte[len / 2];
		for (int i = 0; i < len; i += 2) {
			data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
					+ Character.digit(s.charAt(i+1), 16));
		}
		return data;
	}

	public void StrictLicenceChecking() {
		// Step third: Checking system for malicious apps (Ex Magisk, SuperSU, Xposed, etc...)

		context = com.teslasoft.jarvis.licence.PiracyCheckActivity.this;
		packageManager = context.getPackageManager();
		if (isPackageInstalled("eu.chainfire.supersu", packageManager)
				|| isPackageInstalled("com.topjohnwu.magisk", packageManager)
				|| isPackageInstalled("de.robv.android.xposed.installer", packageManager)
				|| isPackageInstalled("de.robv.android.xposed", packageManager)
				|| isPackageInstalled("cc.madkite.freedom", packageManager)
				|| isPackageInstalled("catch_.me_.if_.you_.can_", packageManager)
				|| isPackageInstalled("org.lsposed.manager", packageManager)
				|| isPackageInstalled("org.meowcat.edxposed.manager", packageManager)
				|| isPackageInstalled("com.hakiansatu.leohhf", packageManager)
				|| isPackageInstalled("com.xmodgame", packageManager)
				|| isPackageInstalled("com.creeplays.hack", packageManager)
				|| isPackageInstalled("com.dimonvideo.luckypatcher", packageManager)
				|| isPackageInstalled("com.chelpus.lackypatch", packageManager)
				|| isPackageInstalled("com.android.vending.billing.InAppBillingService.LACK", packageManager)
				|| isPackageInstalled("com.android.vending.billing.InAppBillingService.COIN", packageManager)
				|| isPackageInstalled("com.android.vending.billing.InAppBillingService.BINN", packageManager)) {
			RESPONSE_CODE = 5;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			message.setText("UNLICENSED: Your device might contain malicious software like apps crackers or root access managers. Please uninstall such apps and try again.");
		} else {
			// Step fourth: Check if device have the factory software and hardware attestation (Ex license will fail if user uses custom rom and/or root access is enabled)
			if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
					== ConnectionResult.SUCCESS) {
				// The SafetyNet Attestation API is available.
				SafetyNet.getClient(this).attest(hexStringToByteArray("d43b1ee3b9b39bef"), "AIzaSyA4IDzPfPT2BM-8Z1S_NcdW8u04fjAMG7A")
						.addOnSuccessListener(this,
								new OnSuccessListener<SafetyNetApi.AttestationResponse>() {
									@Override
									public void onSuccess(SafetyNetApi.AttestationResponse response) {
										// SmartToast.create(response.getJwsResult(), com.teslasoft.jarvis.licence.PiracyCheckActivity.this);
										// Step fourth: additional checks:
										if (isDeviceRooted()) {
											RESPONSE_CODE = 7;
											setContentView(R.layout.licence_check);
											message = (TextView) findViewById(R.id.message);
											message.setText("UNLICENSED: This device is rooted. Please unroot it, install original ROM and lock the bootloader.");
										} else {
											// Step fifth: More checks
											if (isEmulator(com.teslasoft.jarvis.licence.PiracyCheckActivity.this)) {
												RESPONSE_CODE = 18;
												setContentView(R.layout.licence_check);
												message = (TextView) findViewById(R.id.message);
												message.setTextIsSelectable(true);
												message.setText("UNLICENSED: We can not guarantee security on emulators so we block our apps from running on emulators.");

											} else {
												XVerifyLicenseID(response.getJwsResult());
											}
										}
									}
								})
						.addOnFailureListener(this, new OnFailureListener() {
							@Override
							public void onFailure(@NonNull Exception e) {
								// An error occurred while communicating with the service.
								if (e instanceof ApiException) {
									ApiException apiException = (ApiException) e;
									RESPONSE_CODE = 8;
									setContentView(R.layout.licence_check);
									message = (TextView) findViewById(R.id.message);
									message.setText("UNKNOWN: An unknown error occurred. Please try again. Maybe our servers are overloaded due large amount of users that are currently using our services.");
								} else {
									RESPONSE_CODE = 16;
									setContentView(R.layout.licence_check);
									message = (TextView) findViewById(R.id.message);
									message.setText("UNKNOWN: We need Internet connection to check license.");
								}
							}
						});
			} else {
				RESPONSE_CODE = 6;
				setContentView(R.layout.licence_check);
				message = (TextView) findViewById(R.id.message);
				message.setText("UNLICENSED: Please update Google Play Services to the latest version. It is necessary to perform security check.");
			}
		}
	}

	public void XVerifyLicenseID(String response) {
		try {
			final JWSObject jwsObject = JWSObject.parse(response);
			System.out.println("header = " + jwsObject.getHeader());
			System.out.println("header = " + jwsObject.getHeader().getX509CertChain());
			System.out.println("payload = " + jwsObject.getPayload().toJSONObject());
			System.out.println("signature = " + jwsObject.getSignature());
			System.out.println("signature = " + jwsObject.getSignature().decodeToString());
			JSONObject sr = jwsObject.getPayload().toJSONObject();
			String jsg = jwsObject.getSignature().decodeToString();
			String rp = sr.toString();
			Gson gson = new Gson();
			Map<String,Object> map = new HashMap<>();
			map = (Map<String,Object>) gson.fromJson(rp ,map.getClass());
			String basicIntegrity = Objects.requireNonNull(map.get("basicIntegrity")).toString();
			String ctsProfileMatch = Objects.requireNonNull(map.get("ctsProfileMatch")).toString();

			if (basicIntegrity.equals("true") && ctsProfileMatch.equals("true")) {
				RESPONSE_CODE = 0;
				setContentView(R.layout.licence_check);
				message = (TextView) findViewById(R.id.message);
				message.setTextIsSelectable(true);
				message.setText("LICENSED: All OK.");
				// Step sixth: Google Play server-side deep licence check

				// TODO: Implement...

				try {
					MessageDigest digest = MessageDigest.getInstance("SHA-256");
					byte[] hash = digest.digest(response.getBytes(StandardCharsets.UTF_8));
					// Decoded license key is a 64-digit key (checksum of the safetynet response)
					String license_key_decoded = bytesToHex(hash);
					SharedPreferences settings = this.getSharedPreferences("license", Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();

					String sig = Base64.getEncoder().encodeToString(jsg.getBytes());

					editor.putString("license_certified_response", response);
					editor.putString("license_signature", sig);
					editor.putString("license_key", license_key_decoded);
					editor.putString("license_json_response", rp);
					editor.apply();

					passLicense();
				} catch (NoSuchAlgorithmException e) {
					SharedPreferences settings = this.getSharedPreferences("license", Context.MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();

					String sig = Base64.getEncoder().encodeToString(jsg.getBytes());

					editor.putString("license_certified_response", response);
					editor.putString("license_signature", sig);
					editor.putString("license_key", DEFAULT_LICENSE_KEY);
					editor.putString("license_json_response", rp);
					editor.apply();

					passLicense();
				}
			} else {
				RESPONSE_CODE = 7;
				setContentView(R.layout.licence_check);
				message = (TextView) findViewById(R.id.message);
				message.setText("UNLICENSED: Please make sure that your device is running original os and bootloader is locked. If root access or custom ROM installed please uninstall it. Also please lock the bootloader. Rooted or counterfeit devices may run unsafe or modified apps and your data may be vulnerable to attackers. RUN THIS APP ON EMULATORS ARE NOT PERMITTED.");
			}

		} catch (ParseException e) {
			RESPONSE_CODE = 17;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			message.setTextIsSelectable(true);
			message.setText("UNLICENSED: Invalid SafetyNet response. Please make sure that your device is compatible with android and Google Play Services are enabled and working properly.");
		}
	}

	public static boolean isEmulator(Context context) {
		String androidId = Settings.Secure.getString(context.getContentResolver(), "android_id");
		return "sdk".equals(Build.PRODUCT) || "google_sdk".equals(Build.PRODUCT) || androidId == null || Build.MODEL.contains("sdk_gphone");
	}

	public static boolean isDeviceRooted() {
		return checkRootMethod1() || checkRootMethod2() || checkRootMethod3();
	}

	private static boolean checkRootMethod1() {
		String buildTags = android.os.Build.TAGS;
		return buildTags != null && buildTags.contains("test-keys");
	}

	private static boolean checkRootMethod2() {
		String[] paths = { "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
				"/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
		for (String path : paths) {
			if (new File(path).exists()) return true;
		}
		return false;
	}

	private static boolean checkRootMethod3() {
		Process process = null;
		try {
			process = Runtime.getRuntime().exec(new String[] { "/system/xbin/which", "su" });
			BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
			if (in.readLine() != null) return true;
			return false;
		} catch (Throwable t) {
			return false;
		} finally {
			if (process != null) process.destroy();
		}
	}

	private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
		try {
			packageManager.getPackageInfo(packageName, 0);
			return true;
		} catch (PackageManager.NameNotFoundException e) {
			return false;
		}
	}

	@Override
	public void onBackPressed()
	{
		this.setResult(Activity.RESULT_CANCELED);
		finishAndRemoveTask();
	}
	
	public void passLicense() {
		is_licensed = true;
		this.setResult(Activity.RESULT_OK);
		finishAndRemoveTask();
	}

	public void failLicense(View v) {
		is_licensed = false;
		this.setResult(Activity.RESULT_CANCELED);
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
		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
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

	private void doCheck() {

		didCheck = false;
		checkingLicense = true;
		setProgressBarIndeterminateVisibility(true);

		mChecker.checkAccess(mLicenseCheckerCallback);
	}

	private class PiracyCheckerCallback implements LicenseCheckerCallback {

		public Context context;
		public PackageManager packageManager;

		@Override
		public void allow(int reason) {
			// TODO Auto-generated method stub
			if (isFinishing()) {
				// Don't update UI if Activity is finishing.
				return;
			}
			SmartToast.create("License: Accepted", com.teslasoft.jarvis.licence.PiracyCheckActivity.this);

			//You can do other things here, like saving the licensed status to a
			//SharedPreference so the app only has to check the license once.

			licensed = true;
			checkingLicense = false;
			didCheck = true;

		}

		@SuppressWarnings("deprecation")
		@Override
		public void dontAllow(int reason) {
			// TODO Auto-generated method stub
			if (isFinishing()) {
				// Don't update UI if Activity is finishing.
				return;
			}

			SmartToast.create("License: Denied: ".concat(Integer.toString(reason)), com.teslasoft.jarvis.licence.PiracyCheckActivity.this);

			//You can do other things here, like saving the licensed status to a
			//SharedPreference so the app only has to check the license once.

			licensed = false;
			checkingLicense = false;
			didCheck = true;

			showDialog(0);

		}

		@SuppressWarnings("deprecation")
		@Override
		public void applicationError(int reason) {
			// TODO Auto-generated method stub
			SmartToast.create("License: Error: ".concat(Integer.toString(reason)), com.teslasoft.jarvis.licence.PiracyCheckActivity.this);

			if (isFinishing()) {
				// Don't update UI if Activity is finishing.
				return;
			}
			licensed = true;
			checkingLicense = false;
			didCheck = false;

			showDialog(0);
		}


	}

	protected Dialog onCreateDialog(int id) {
		// We have only one dialog.
		return new AlertDialog.Builder(this)
				.setTitle("UNLICENSED APPLICATION DIALOG TITLE")
				.setMessage("This application is not licensed, please buy it from the play store.")
				.setPositiveButton("Buy", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(
								"http://market.android.com/details?id=" + getPackageName()));
						startActivity(marketIntent);
						finish();
					}
				})
				.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				})
				.setNeutralButton("Re-Check", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						doCheck();
					}
				})

				.setCancelable(false)
				.setOnKeyListener(new DialogInterface.OnKeyListener(){
					public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
						SmartToast.create("License: KeyListener", com.teslasoft.jarvis.licence.PiracyCheckActivity.this);

						finish();
						return true;
					}
				})
				.create();

	}
}
