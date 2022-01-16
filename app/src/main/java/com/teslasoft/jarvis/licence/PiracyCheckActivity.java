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

import android.widget.SmartToast;
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


import net.minidev.json.JSONObject;

public class PiracyCheckActivity extends Activity
{
	private String appId;
	private TextView message;
	private TextView lTitle;
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
	// private LicenseChecker mChecker;
	// private LicenseCheckerCallback mLicenseCheckerCallback;
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
		SharedPreferences privacy = this.getSharedPreferences("privacy_accepted", Context.MODE_PRIVATE);
		try {
			String privacy_is_accepted = privacy.getString("privacy", null);
			// SmartToast.create(privacy_is_accepted, this);
			if (privacy_is_accepted.equals("yes")) {
				startLic();
			} else {
				RESPONSE_CODE = 3;
				setContentView(R.layout.licence_check);
				message = (TextView) findViewById(R.id.message);
				lTitle = (TextView) findViewById(R.id.license_title);
				lTitle.setText("Locked");
				message.setText("This app uses Teslasoft APIs and has been locked because you declined Privacy Policy. In order to comply with GDPR we MUST get your agreement with data processing. Please restart this app and accept Privacy Policy in order to use it.");
			}
		} catch (Exception e) {
			try {
				Intent i = new Intent(this, com.teslasoft.jarvis.Privacy.class);
				startActivityForResult(i, 1);
			} catch (Exception _e) {
				RESPONSE_CODE = 3;
				setContentView(R.layout.licence_check);
				message = (TextView) findViewById(R.id.message);
				message.setText("Something is preventing this app to show Privacy Policy. Try to reinstall app.");
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			startLic();
		} else {
			RESPONSE_CODE = 3;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			lTitle = (TextView) findViewById(R.id.license_title);
			lTitle.setText("Locked");
			message.setText("Please accept privacy policy to use this app.");
		}
	}

	public void startLic() {
		if (isDeviceRooted()) {
			// Firstly check root to prevent API abuse and repeated requests
			RESPONSE_CODE = 7;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			lTitle = (TextView) findViewById(R.id.license_title);
			lTitle.setText("Security test failed");
			message.setText("This device is rooted (your system is vulnerable now). Please unroot it, install original ROM and lock the bootloader.");
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
						String aid = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
						if (aid.equals("c34ea1c41f95c6aa")) {
							passLicense();
						} else {
							// The app have a license key but it is invalid
							RESPONSE_CODE = 19;
							setContentView(R.layout.licence_check);
							message = (TextView) findViewById(R.id.message);
							message.setText("UNLICENSED: We re unable to verify your license. Please clear all data/cache of this app, then connect to the Internet and try again. You may see this message because this app is tampered with or license key is counterfeit or compromised. We need to revalidate your license by making signed request to the servers.");
						}
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
							lTitle = (TextView) findViewById(R.id.license_title);
							lTitle.setText("Security test failed");
							message.setText("This device is rooted (your system is vulnerable now). Please unroot it, install original ROM and lock the bootloader.");
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
		String testDevice3 = "a2ce32a3907804b4";
		String testDevice4 = "c34ea1c41f95c6aa";
		// Checking licence
		// First step: check installation source

		if (verifyInstallerId(this) || android_id.equals(testDevice) || android_id.equals(testDevice2) || android_id.equals(testDevice3) || android_id.equals(testDevice4) ) {
			// Second step: check signature hash
			StrictLicenceChecking();
			/*
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
			*/
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
			lTitle = (TextView) findViewById(R.id.license_title);
			lTitle.setText("Security test failed");
			message.setText("Looks like you're using modified copy of this app. [ERR_SIGNATURE_FINGERPRINT_MISMATCH]");
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
			lTitle = (TextView) findViewById(R.id.license_title);
			lTitle.setText("Error");
			message.setText("Invalid usage of API. Please provide a valid package name.");
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
			lTitle = (TextView) findViewById(R.id.license_title);
			lTitle.setText("Security test failed");
			message.setText("Your device has malicious software installed. It may be crackers, keygens or root access managers. Please uninstall these apps and try again.");
		} else {
			// Step fourth: Check if device have the factory software and hardware attestation (Ex license will fail if user uses custom rom and/or root access is enabled)
			String aid2 = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
			if (aid2.equals("c34ea1c41f95c6aa")) {
				passDebug(DEFAULT_LICENSE_KEY);
			} else {
				if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(context)
						== ConnectionResult.SUCCESS) {
					// The SafetyNet Attestation API is available.
					/*************************************************************************************************************
					 *          !!!!! WARNING: TODO: DO NOT FORGET TO ADD GOOGLE API KEY BEFORE RELEASING THIS APP !!!!!
					 *                        !!!!! API KEY HAVE BEEN OMITTED TO PREVENT API ABUSING !!!!!
					 * **********************************************************************************************************/
					SafetyNet.getClient(this).attest(hexStringToByteArray("d43b1ee3b9b39bef"), "AIzaSyB26jTO0pXzegXMZAjslOcpr869qBScO9s") // !!!!!!!!!!!!!!!!!!!!!! REPLACE !!!!!!!!!!!!!!!!!!!!!!!
							.addOnSuccessListener(this,
									new OnSuccessListener<SafetyNetApi.AttestationResponse>() {
										@Override
										public void onSuccess(SafetyNetApi.AttestationResponse response) {
											// SmartToast.create(response.getJwsResult(), com.teslasoft.jarvis.licence.PiracyCheckActivity.this);
											// Step fourth: additional checks:
											if (isDeviceRooted()) {
												String aid = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
												if (aid.equals("c34ea1c41f95c6aa")) {
													passDebug(DEFAULT_LICENSE_KEY);
												} else {
													RESPONSE_CODE = 7;
													setContentView(R.layout.licence_check);
													message = (TextView) findViewById(R.id.message);
													lTitle = (TextView) findViewById(R.id.license_title);
													lTitle.setText("Certification failed");
													message.setText("Looks like that your device is not trusted. Please make sure that you bought it on the official store. Also make sure that system installed on this device is original. [ERR_OS_INTEGRITY_TEST_FAILED]");
												}
											} else {
												// Step fifth: More checks
												if (isEmulator(com.teslasoft.jarvis.licence.PiracyCheckActivity.this)) {
													String aid = android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
													if (aid.equals("c34ea1c41f95c6aa")) {
														XVerifyLicenseID(response.getJwsResult());
													} else {
														RESPONSE_CODE = 18;
														setContentView(R.layout.licence_check);
														message = (TextView) findViewById(R.id.message);
														message.setTextIsSelectable(true);
														lTitle = (TextView) findViewById(R.id.license_title);
														lTitle.setText("Security test failed");
														message.setText("We can not guarantee security on emulators so we block our apps from running on emulators. Device ID: " + aid);
													}
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
										lTitle = (TextView) findViewById(R.id.license_title);
										lTitle.setText("Under maintenance");
										message.setText("Our servers are currently overloaded. Please try later.");
									} else {
										RESPONSE_CODE = 16;
										setContentView(R.layout.licence_check);
										message = (TextView) findViewById(R.id.message);
										lTitle = (TextView) findViewById(R.id.license_title);
										lTitle.setText("Offline");
										message.setText("Please check your Internet connection and try again.");
									}
								}
							});
				} else {
					RESPONSE_CODE = 6;
					setContentView(R.layout.licence_check);
					message = (TextView) findViewById(R.id.message);
					lTitle = (TextView) findViewById(R.id.license_title);
					lTitle.setText("Security test failed");
					message.setText("Please update Google Play Services to the latest version. It is necessary to perform security check.");
				}
			}
		}
	}

	public void passDebug(String license_key) {
		SharedPreferences settings = this.getSharedPreferences("license", Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();

		editor.putString("license_certified_response", "eyJhbGciOiJSUzI1NiIsIng1YyI6WyJNSUlGWHpDQ0JFZWdBd0lCQWdJUWZtOGlZWnp1cTBFSkFBQUFBSDd1UlRBTkJna3Foa2lHOXcwQkFRc0ZBREJHTVFzd0NRWURWUVFHRXdKVlV6RWlNQ0FHQTFVRUNoTVpSMjl2WjJ4bElGUnlkWE4wSUZObGNuWnBZMlZ6SUV4TVF6RVRNQkVHQTFVRUF4TUtSMVJUSUVOQklERkVOREFlRncweU1UQTNNVGt4TXpFek5ESmFGdzB5TVRFd01UY3hNekV6TkRGYU1CMHhHekFaQmdOVkJBTVRFbUYwZEdWemRDNWhibVJ5YjJsa0xtTnZiVENDQVNJd0RRWUpLb1pJaHZjTkFRRUJCUUFEZ2dFUEFEQ0NBUW9DZ2dFQkFLZk5TQll4M002SnJJaTBMUURGNFVaaHRzeTgyQ280TnZ3ci9GSW43LzlnK3hzV3pDWWdSN1FzR21yeUc5dlBGckd5UXJFZGpDUXVCU1FTd29vNGdwaUhpdzFYbnFGZnJOYzJ3TFJPL1BUdStha0ZESU02Z3UzZGRnd1FXR0daclZQektFak95TlNGTUJMMFdBS2l1dVlCdjE0UXZublcxRWtZYnFKZFRoNkxXZmV2Y1dSSytUdFZhOXpzR25Fbmc3a01QV1BCSzBOMGJQZ3hiNGpueGFIcWxMeHEvQ2pEbkxrREVkdWZlVDVVZ3JsVG53OVVtWm1NeGFQdGEvdnowY2g2ZmxDd3lpdmpHajJ4VEhLVll2bVlwNFRmTGcwY1VOUEUxZEtqTkliS1lDeFFJVnpueHV4ZXBUU1ZpWXVjUEY0VnduKzZEOVp4UUpKKy9lNktMSWtDQXdFQUFhT0NBbkF3Z2dKc01BNEdBMVVkRHdFQi93UUVBd0lGb0RBVEJnTlZIU1VFRERBS0JnZ3JCZ0VGQlFjREFUQU1CZ05WSFJNQkFmOEVBakFBTUIwR0ExVWREZ1FXQkJUTXNUSTVxZ0FPUmtBZDNNUEwwNWg0NmJvVlhEQWZCZ05WSFNNRUdEQVdnQlFsNGhnT3NsZVJsQ3JsMUYyR2tJUGVVN080a2pCdEJnZ3JCZ0VGQlFjQkFRUmhNRjh3S2dZSUt3WUJCUVVITUFHR0htaDBkSEE2THk5dlkzTndMbkJyYVM1bmIyOW5MMmQwY3pGa05HbHVkREF4QmdnckJnRUZCUWN3QW9ZbGFIUjBjRG92TDNCcmFTNW5iMjluTDNKbGNHOHZZMlZ5ZEhNdlozUnpNV1EwTG1SbGNqQWRCZ05WSFJFRUZqQVVnaEpoZEhSbGMzUXVZVzVrY205cFpDNWpiMjB3SVFZRFZSMGdCQm93R0RBSUJnWm5nUXdCQWdFd0RBWUtLd1lCQkFIV2VRSUZBekEvQmdOVkhSOEVPREEyTURTZ01xQXdoaTVvZEhSd09pOHZZM0pzY3k1d2Eya3VaMjl2Wnk5bmRITXhaRFJwYm5RdlgwWlFjWEZKU0dkWU5qZ3VZM0pzTUlJQkF3WUtLd1lCQkFIV2VRSUVBZ1NCOUFTQjhRRHZBSFVBWE54RGt2N21xMFZFc1Y2YTFGYm1FRGY3MWZwSDNLRnpsTEplNXZiSERzb0FBQUY2dngyTzFnQUFCQU1BUmpCRUFpQkp1V1BSbVJNdmpjVFVwSWJyTktoOHN4Ykd4TlBNZmxicnYxZHhUakp3Q2dJZ1M5d2dMVUplUXFMTVI4WGVuR05meVloYXFsclJ4eE04c1A4VklwUUdTUzBBZGdCOVB2TDRqLytJVldna3dzREtubEtKZVN2RkRuZ0pmeTVxbDJpWmZpTHcxd0FBQVhxL0hZK0tBQUFFQXdCSE1FVUNJRDJMMnJIQmxKaTlSRm9PZkVCM2R4SGVIV1RKd3NwNDZJZklqNm9LS3BYYkFpRUEyNVNZRk04ZzFUK0dJVXJVTTB4Y05Ud2kvbHJxaFlrUU1HK0ZzMmZtRmRJd0RRWUpLb1pJaHZjTkFRRUxCUUFEZ2dFQkFENjhmeEhMeE9DK1ZsTjFSTkN5S2RUcWZIYlJBQWROWVg3N0hXL21QQm5VQzFocmVUR3hHeFNOMURoak1xNHpoOFBDbTB6L3JCM3BEd2lnbWlNdmFYUEVEazZEbGlNU0V5ZDBjNnkwOWg1V05XTi9jeGpHL3VRMDJ6REMvRWkvZmRFZ3UyMUhneHM3Q0VUdTN0ZTZCbzFSeC94R1FtK2toNXYwcHYraVl6cnhVbE8vTWRvb2lkejlCQ1hXOHZyTUo2UnNRVlJQeTR5RlcvMzcyN2x1RFpZMEh0NW1FRklKQ3BWQ2lCTHNpeDBwbVRsa1padXREaC8vTWRNNUE0NzFWQUMxU0l4ekMzT2F0dFhWTFNtSXZnd1hXYlo5azJsekppekFsbFJLVWtNTFRkc09EcDUzM25Pa2RWU1o2ZitIcnFJc1RMTnM1UVNLYkU0cnhydlZOKzQ9IiwiTUlJRmpEQ0NBM1NnQXdJQkFnSU5BZ0NPc2dJek5tV0xaTTNibXpBTkJna3Foa2lHOXcwQkFRc0ZBREJITVFzd0NRWURWUVFHRXdKVlV6RWlNQ0FHQTFVRUNoTVpSMjl2WjJ4bElGUnlkWE4wSUZObGNuWnBZMlZ6SUV4TVF6RVVNQklHQTFVRUF4TUxSMVJUSUZKdmIzUWdVakV3SGhjTk1qQXdPREV6TURBd01EUXlXaGNOTWpjd09UTXdNREF3TURReVdqQkdNUXN3Q1FZRFZRUUdFd0pWVXpFaU1DQUdBMVVFQ2hNWlIyOXZaMnhsSUZSeWRYTjBJRk5sY25acFkyVnpJRXhNUXpFVE1CRUdBMVVFQXhNS1IxUlRJRU5CSURGRU5EQ0NBU0l3RFFZSktvWklodmNOQVFFQkJRQURnZ0VQQURDQ0FRb0NnZ0VCQUt2QXFxUENFMjdsMHc5ekM4ZFRQSUU4OWJBK3hUbURhRzd5N1ZmUTRjK21PV2hsVWViVVFwSzB5djJyNjc4UkpFeEswSFdEamVxK25MSUhOMUVtNWo2ckFSWml4bXlSU2poSVIwS09RUEdCTVVsZHNhenRJSUo3TzBnLzgycWovdkdEbC8vM3Q0dFRxeGlSaExRblRMWEpkZUIrMkRoa2RVNklJZ3g2d043RTVOY1VIM1Jjc2VqY3FqOHA1U2oxOXZCbTZpMUZocUxHeW1oTUZyb1dWVUdPM3h0SUg5MWRzZ3k0ZUZLY2ZLVkxXSzNvMjE5MFEwTG0vU2lLbUxiUko1QXU0eTFldUZKbTJKTTllQjg0RmtxYTNpdnJYV1VlVnR5ZTBDUWRLdnNZMkZrYXp2eHR4dnVzTEp6TFdZSGs1NXpjUkFhY0RBMlNlRXRCYlFmRDFxc0NBd0VBQWFPQ0FYWXdnZ0Z5TUE0R0ExVWREd0VCL3dRRUF3SUJoakFkQmdOVkhTVUVGakFVQmdnckJnRUZCUWNEQVFZSUt3WUJCUVVIQXdJd0VnWURWUjBUQVFIL0JBZ3dCZ0VCL3dJQkFEQWRCZ05WSFE0RUZnUVVKZUlZRHJKWGtaUXE1ZFJkaHBDRDNsT3p1Skl3SHdZRFZSMGpCQmd3Rm9BVTVLOHJKbkVhSzBnbmhTOVNaaXp2OElrVGNUNHdhQVlJS3dZQkJRVUhBUUVFWERCYU1DWUdDQ3NHQVFVRkJ6QUJoaHBvZEhSd09pOHZiMk56Y0M1d2Eya3VaMjl2Wnk5bmRITnlNVEF3QmdnckJnRUZCUWN3QW9Za2FIUjBjRG92TDNCcmFTNW5iMjluTDNKbGNHOHZZMlZ5ZEhNdlozUnpjakV1WkdWeU1EUUdBMVVkSHdRdE1Dc3dLYUFub0NXR0kyaDBkSEE2THk5amNtd3VjR3RwTG1kdmIyY3ZaM1J6Y2pFdlozUnpjakV1WTNKc01FMEdBMVVkSUFSR01FUXdDQVlHWjRFTUFRSUJNRGdHQ2lzR0FRUUIxbmtDQlFNd0tqQW9CZ2dyQmdFRkJRY0NBUlljYUhSMGNITTZMeTl3YTJrdVoyOXZaeTl5WlhCdmMybDBiM0o1THpBTkJna3Foa2lHOXcwQkFRc0ZBQU9DQWdFQUlWVG95MjRqd1hVcjByQVBjOTI0dnVTVmJLUXVZdzNuTGZsTGZMaDVBWVdFZVZsL0R1MThRQVdVTWRjSjZvL3FGWmJoWGtCSDBQTmN3OTd0aGFmMkJlb0RZWTlDay9iK1VHbHVoeDA2emQ0RUJmN0g5UDg0bm5yd3BSKzRHQkRaSytYaDNJMHRxSnkycmdPcU5EZmxyNUlNUThaVFdBM3lsdGFrelNCS1o2WHBGMFBwcXlDUnZwL05DR3YyS1gyVHVQQ0p2c2NwMS9tMnBWVHR5QmpZUFJRK1F1Q1FHQUpLanRON1I1REZyZlRxTVd2WWdWbHBDSkJrd2x1Nys3S1kzY1RJZnpFN2NtQUxza01LTkx1RHorUnpDY3NZVHNWYVU3VnAzeEw2ME9ZaHFGa3VBT094RFo2cEhPajkrT0ptWWdQbU9UNFgzKzdMNTFmWEp5Ukg5S2ZMUlA2blQzMUQ1bm1zR0FPZ1oyNi84VDloc0JXMXVvOWp1NWZaTFpYVlZTNUgwSHlJQk1FS3lHTUlQaEZXcmx0L2hGUzI4TjF6YUtJMFpCR0QzZ1lnRExiaURUOWZHWHN0cGsrRm1jNG9sVmxXUHpYZTgxdmRvRW5GYnI1TTI3MkhkZ0pXbytXaFQ5QllNMEppK3dkVm1uUmZmWGdsb0VvbHVUTmNXemM0MWRGcGdKdThmRjNMRzBnbDJpYlNZaUNpOWE2aHZVMFRwcGpKeUlXWGhrSlRjTUpsUHJXeDFWeXRFVUdyWDJsMEpEd1JqVy82NTZyMEtWQjAyeEhSS3ZtMlpLSTAzVGdsTElwbVZDSzNrQktrS05wQk5rRnQ4cmhhZmNDS09iOUp4Lzl0cE5GbFFUbDdCMzlySmxKV2tSMTdRblpxVnB0RmVQRk9Sb1ptRnpNPSIsIk1JSUZZakNDQkVxZ0F3SUJBZ0lRZDcwTmJOczIrUnJxSVEvRThGalREVEFOQmdrcWhraUc5dzBCQVFzRkFEQlhNUXN3Q1FZRFZRUUdFd0pDUlRFWk1CY0dBMVVFQ2hNUVIyeHZZbUZzVTJsbmJpQnVkaTF6WVRFUU1BNEdBMVVFQ3hNSFVtOXZkQ0JEUVRFYk1Ca0dBMVVFQXhNU1IyeHZZbUZzVTJsbmJpQlNiMjkwSUVOQk1CNFhEVEl3TURZeE9UQXdNREEwTWxvWERUSTRNREV5T0RBd01EQTBNbG93UnpFTE1Ba0dBMVVFQmhNQ1ZWTXhJakFnQmdOVkJBb1RHVWR2YjJkc1pTQlVjblZ6ZENCVFpYSjJhV05sY3lCTVRFTXhGREFTQmdOVkJBTVRDMGRVVXlCU2IyOTBJRkl4TUlJQ0lqQU5CZ2txaGtpRzl3MEJBUUVGQUFPQ0FnOEFNSUlDQ2dLQ0FnRUF0aEVDaXg3am9YZWJPOXkvbEQ2M2xhZEFQS0g5Z3ZsOU1nYUNjZmIyakgvNzZOdThhaTZYbDZPTVMva3I5ckg1em9RZHNmbkZsOTd2dWZLajZid1NpVjZucWxLcitDTW55NlN4bkdQYjE1bCs4QXBlNjJpbTlNWmFSdzFORURQalRyRVRvOGdZYkV2cy9BbVEzNTFrS1NVakI2RzAwajB1WU9EUDBnbUh1ODFJOEUzQ3ducUlpcnU2ejFrWjFxK1BzQWV3bmpIeGdzSEEzeTZtYld3WkRyWFlmaVlhUlFNOXNIbWtsQ2l0RDM4bTVhZ0kvcGJvUEdpVVUrNkRPb2dyRlpZSnN1QjZqQzUxMXB6cnAxWmtqNVpQYUs0OWw4S0VqOEM4UU1BTFhMMzJoN00xYkt3WVVIK0U0RXpOa3RNZzZUTzhVcG12TXJVcHN5VXF0RWo1Y3VIS1pQZm1naENONkozQ2lvajZPR2FLL0dQNUFmbDQvWHRjZC9wMmgvcnMzN0VPZVpWWHRMMG03OVlCMGVzV0NydU9DN1hGeFlwVnE5T3M2cEZMS2N3WnBESWxUaXJ4WlVUUUFzNnF6a20wNnA5OGc3QkFlK2REcTZkc280OTlpWUg2VEtYLzFZN0R6a3ZndGRpemprWFBkc0R0UUN2OVV3K3dwOVU3RGJHS29nUGVNYTNNZCtwdmV6N1czNUVpRXVhKyt0Z3kvQkJqRkZGeTNsM1dGcE85S1dnejd6cG03QWVLSnQ4VDExZGxlQ2ZlWGtrVUFLSUFmNXFvSWJhcHNaV3dwYmtORmhIYXgyeElQRURnZmcxYXpWWTgwWmNGdWN0TDdUbExuTVEvMGxVVGJpU3cxbkg2OU1HNnpPMGI5ZjZCUWRnQW1EMDZ5SzU2bURjWUJaVUNBd0VBQWFPQ0FUZ3dnZ0UwTUE0R0ExVWREd0VCL3dRRUF3SUJoakFQQmdOVkhSTUJBZjhFQlRBREFRSC9NQjBHQTFVZERnUVdCQlRrcnlzbWNSb3JTQ2VGTDFKbUxPL3dpUk54UGpBZkJnTlZIU01FR0RBV2dCUmdlMllhUlEyWHlvbFFMMzBFelRTby8vejlTekJnQmdnckJnRUZCUWNCQVFSVU1GSXdKUVlJS3dZQkJRVUhNQUdHR1doMGRIQTZMeTl2WTNOd0xuQnJhUzVuYjI5bkwyZHpjakV3S1FZSUt3WUJCUVVITUFLR0hXaDBkSEE2THk5d2Eya3VaMjl2Wnk5bmMzSXhMMmR6Y2pFdVkzSjBNRElHQTFVZEh3UXJNQ2t3SjZBbG9DT0dJV2gwZEhBNkx5OWpjbXd1Y0d0cExtZHZiMmN2WjNOeU1TOW5jM0l4TG1OeWJEQTdCZ05WSFNBRU5EQXlNQWdHQm1lQkRBRUNBVEFJQmdabmdRd0JBZ0l3RFFZTEt3WUJCQUhXZVFJRkF3SXdEUVlMS3dZQkJBSFdlUUlGQXdNd0RRWUpLb1pJaHZjTkFRRUxCUUFEZ2dFQkFEU2tIckVvbzlDMGRoZW1NWG9oNmRGU1BzamJkQlpCaUxnOU5SM3Q1UCtUNFZ4ZnE3dnFmTS9iNUEzUmkxZnlKbTlidmhkR2FKUTNiMnQ2eU1BWU4vb2xVYXpzYUwreXlFbjlXcHJLQVNPc2hJQXJBb3labCt0SmFveDExOGZlc3NtWG4xaElWdzQxb2VRYTF2MXZnNEZ2NzR6UGw2L0FoU3J3OVU1cENaRXQ0V2k0d1N0ejZkVFovQ0xBTng4TFpoMUo3UUpWajJmaE10ZlRKcjl3NHozMFoyMDlmT1UwaU9NeStxZHVCbXB2dll1UjdoWkw2RHVwc3pmbncwU2tmdGhzMThkRzlaS2I1OVVodm1hU0daUlZiTlFwc2czQlpsdmlkMGxJS08yZDF4b3pjbE96Z2pYUFlvdkpKSXVsdHprTXUzNHFRYjlTei95aWxyYkNnajg9Il19.eyJub25jZSI6IjFEc2U0N216bSs4PSIsInRpbWVzdGFtcE1zIjoxNjMyNDI1NDUxNDA4LCJhcGtQYWNrYWdlTmFtZSI6ImNvbS50ZXNsYXNvZnQubGlicmFyaWVzLnN1cHBvcnQiLCJhcGtEaWdlc3RTaGEyNTYiOiJUUm8xcTRYS3RhMGhBRXNRRkdtbHBnTHVIT093ck0vWFVEM0gyZ2I1eFFZPSIsImN0c1Byb2ZpbGVNYXRjaCI6dHJ1ZSwiYXBrQ2VydGlmaWNhdGVEaWdlc3RTaGEyNTYiOlsiMUF4VzF5blJKeEZacVlsWnJkUW1GOG9MelBSV2tkeHN0dTlWTGNWNm1Xbz0iXSwiYmFzaWNJbnRlZ3JpdHkiOnRydWUsImV2YWx1YXRpb25UeXBlIjoiQkFTSUMifQ.gMLdkVF5zR-ufjjVUX14UiXXhGhFtU3PgVuVYjicee9omF8G1c7V2JlI-tv2aBxQTjuAZXX7Tq8aFTUbp46shnWFBwnRqLZ54c6zRWulN-Xksv1Cos3PEDUIdu4hCZLTDyKUpNP8ehBg7oR8K62f1SGY2BdtIJbevVI9IdGuDnkCxeTUPNnsBe_QJMsirxrfEToFvpBdJ6MbOpl6e1TZk4Nf77j-Wkyta8WhJ7Qwy2WIHlcbr9VhW-puJxA98EhFKsMStwZTf7kjAOyMiTTvqusKVrKJXaFiPenrLgQLEKZo-O6mH9wg8SYSjCFfbhmoZsOfD-JYL68mV9mCLCEYaw");
		editor.putString("license_signature", "STATE_DEBUG");
		editor.putString("license_key", DEFAULT_LICENSE_KEY);
		editor.putString("license_json_response", "{\"evaluationType\":\"BASIC\",\"ctsProfileMatch\":true,\"apkPackageName\":\"com.teslasoft.libraries.support\",\"apkDigestSha256\":\"TRo1q4XKta0hAEsQFGmlpgLuHOOwrM\\/XUD3H2gb5xQY=\",\"nonce\":\"1Dse47mzm+8=\",\"apkCertificateDigestSha256\":[\"1AxW1ynRJxFZqYlZrdQmF8oLzPRWkdxstu9VLcV6mWo=\"],\"timestampMs\":1632427229637,\"basicIntegrity\":true}");
		editor.apply();

		passLicense();
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
				message.setText("LICENSED");
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
				lTitle = (TextView) findViewById(R.id.license_title);
				lTitle.setText("Security test failed");
				message.setText("Looks like that your device is not trusted. Please make sure that you bought it on the official store. Also make sure that system installed on this device is original. [ERR_OS_INTEGRITY_TEST_FAILED]");
			}

		} catch (ParseException e) {
			RESPONSE_CODE = 17;
			setContentView(R.layout.licence_check);
			message = (TextView) findViewById(R.id.message);
			message.setTextIsSelectable(true);
			lTitle = (TextView) findViewById(R.id.license_title);
			lTitle.setText("Security test failed");
			message.setText("Invalid SafetyNet response. Please make sure that your device is compatible with android and Google Play Services are enabled and working properly.");
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

	/*private void doCheck() {

		didCheck = false;
		checkingLicense = true;
		setProgressBarIndeterminateVisibility(true);

		mChecker.checkAccess(mLicenseCheckerCallback);
	}*/

	/*private class PiracyCheckerCallback implements LicenseCheckerCallback {

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

	}*/
}
