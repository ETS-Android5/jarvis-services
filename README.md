# Jarvis Services
### Jarvis service is application, that contains most of Jarvis and CoreX frameworks and libraries.

## Update 1.18.202010282315.1.187 changelog

- Merging to Jetpack (androidx)
- Added biometric authentication
- Added android.security component
- Added com.android.phone component
- Added jarvis signin page
- Removed TaskExec component
- Removed android.support.v4 component (replaced with androidx)
- Bug fix with crash handler
- Optimized for API 29 (Android 10)
- Android 5 or lower is now deprecated
- Updated AD library
- Added update component
- CoreX implementation
- Removed junk and deprecated code
- Removed apk minify
- Added multidex
- Reworked UI
- Changed color pallete
- Merging to AppCompat
- Licence ckeck added
- Anti piracy manager added

## How to implement Jarvis Account Sign In

For implementation you don't need complie additional libraries
To implement just paste the next code to your app:
```java
try {
	int REQUIRED_RESPONSE = 1;
	Intent intent = new Intent(new ComponentName("com.teslasoft.libraries.support", "com.teslasoft.jarvis.auth.AuthEntryActivity"));
	Bundle extras = new Bundle();
	extras.putString("appId", this.getPackageName());
	intent.putExtras(extras);
	this.startActivityForResult(intent, REQUIRED_RESPONSE);
} catch (Exception e) {
	// Do something if Jarvis Services is unavaliable
}
```
And add function ***onActivityResult***:
```java
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (resultCode == Activity.RESULT_OK) {
		// Get credentials, stored in /mnt/sdcard/jarvis/auth/com.your.app/credentials.json
	} else if (resultCode == 3) {
		// Do something if no Internet connection
	} else if (resultCode == 4) {
		// Do something if Jarvis Services unavaliable
	} else if (resultCode == 5) {
		// Do something if API connected is not correctly
		// Extra key "appId" is required
	} else {
		// Do something if auth was canceled
	}
}
```

## How to implement Biometric Authentication
For implementation you don't need complie additional libraries
To implement just paste the next code to your app:
```java
try {
	int REQUIRED_RESPONSE = 1;
	Intent intent = new Intent(new ComponentName("com.teslasoft.libraries.support", "android.security.BiometricAuthenticatorCallback"));
	this.startActivityForResult(intent, REQUIRED_RESPONSE);
} catch (Exception e) {
	// Do something if Jarvis Services is unavaliable
}
```
And add function ***onActivityResult***:
```java
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (resultCode == Activity.RESULT_OK) {
		// The biometric check is passed, continue executing
	} else {
		// Do something if auth was canceled
	}
}
```

## How to check licence and integrity for my app

For implementation you don't need complie additional libraries
To implement just paste the next code to your app:
```java
try {
	int REQUIRED_RESPONSE = 1;
	Intent intent = new Intent(new ComponentName("com.teslasoft.libraries.support", "com.teslasoft.jarvis.licence.PiracyCheckActivity"));
	Bundle extras = new Bundle();
	extras.putString("appId", this.getPackageName());
	extras.putString("isNotif", "false"); // IMPORTANT: this walue is a STRING; If walue is "true" the unlicence notification will be shown
	intent.putExtras(extras);
	this.startActivityForResult(intent, REQUIRED_RESPONSE);
} catch (Exception e) {
	// Do something if Jarvis Services is unavaliable
}
```
And add function ***onActivityResult***:
```java
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (resultCode == Activity.RESULT_OK) {
		// Application licenced
	} else if (resultCode == 2) {
		// // Do something if API connected is not correctly
		// Extra key "appId" is required
		// Extra key "isNotif" is required
	} else if (resultCode == 3) {
		// Application not found
	} else if (resultCode == 4) {
		// Jarvis Services has spoofed and may be unsafe, licence check failed
	} else {
		// Application unlicenced
	}
}
```

- NOTE: For licence check Internet connection is not required (but it may required in future releases)

## These libraries are required for implement Jarvis API:

```java
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.ComponentName;
import android.content.Context;
```