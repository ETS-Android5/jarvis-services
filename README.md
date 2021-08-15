# Teslasoft Core
### Teslasoft Core is an application, that contains most of Teslasoft APIs and libraries.

## Latest update (1.25.202108151500.7.921) changelog

* Implemented account manager
* Reduced app size
* Removed CoreX

\>\>\>\>\> [DOWNLOAD HERE](https://play.google.com/store/apps/details?id=com.teslasoft.libraries.support) <<<<<

You can see other changelogs in file ***CHANGELOG.md***.

## How to implement Jarvis Account Sign In
To implement you don't need to complie additional libraries.

To implement Jarvis API just paste the next code to your app:
```java
try {
	int REQUIRED_RESPONSE = 1;
	Intent intent = new Intent();
	intent.setComponent(new ComponentName("com.teslasoft.libraries.support", "com.teslasoft.jarvis.auth.AuthEntryActivity"));
	Bundle extras = new Bundle();
	extras.putString("appId", this.getPackageName());
	intent.putExtras(extras);
	this.startActivityForResult(intent, REQUIRED_RESPONSE);
} catch (Exception e) {
	// Do something if Teslasoft Core is unavaliable
}
```
And add method ***onActivityResult***:
```java
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (resultCode == Activity.RESULT_OK) {
		AccountManager am =  (AccountManager)context.getSystemService(Context.ACCOUNT_SERVICE);
		Account[] accountsList = am.getAccountsByType("org.teslasoft.id.JARVIS_ACCOUNT");
		for(Account acct: accountsList){
			// TODO
		}
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

## Start Account picker
```java
try {
	int REQUIRED_RESPONSE = 1;
	Intent intent = new Intent();
	intent.setComponent(new ComponentName("com.teslasoft.libraries.support", "com.teslasoft.jarvis.auth.AccountPickerActivity"));
	this.startActivityForResult(intent, REQUIRED_RESPONSE);
} catch (Exception e) {
	// Do something if Teslasoft Core is unavaliable
}
```
And add method ***onActivityResult***:
```java
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	AccountManager am =  (AccountManager)context.getSystemService(Context.ACCOUNT_SERVICE);
	Account[] accountsList = am.getAccountsByType("org.teslasoft.id.JARVIS_ACCOUNT");
	Account account = accountsList[resultCode - 20];
	// Get user data or do something else
}
```

## How to implement Biometric Authentication
To implement you don't need to complie additional libraries.

To implement Jarvis API just paste the next code to your app:
```java
try {
	int REQUIRED_RESPONSE = 1;
	Intent intent = new Intent();
	intent.setComponent(new ComponentName("com.teslasoft.libraries.support", "android.security.BiometricAuthenticatorCallback"));
	this.startActivityForResult(intent, REQUIRED_RESPONSE);
} catch (Exception e) {
	// Do something if Teslasoft Core is unavaliable
}
```
And add method ***onActivityResult***:
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
To implement you don't need to complie additional libraries.

To implement Jarvis API just paste the next code to your app:
```java
...
private String appSignatureHash = "YOUR_APP_SIGNATURE_HASH";
// You can use getters to get this value
// It's strongly recommended do not declare value of this variable directly
// To protect your app from piracy please obfuscate this code

/******************************************************************************************************************************
 * To get your app's signature hash use this code:
 * import android.content.pm.PackageInfo;
 * import android.content.pm.PackageManager;
 * import android.content.pm.PackageManager.NameNotFoundException;
 * import android.content.pm.Signature;
 * 
 * ...
 * 
 * Signature sig = this.getPackageManager().getPackageInfo(this.getPackageName(), PackageManager.GET_SIGNATURES).signatures[0];
 * String signatureHash = Integer.toString(sig.hashCode());
 * 
 * Note: To protect your app from piracy remove this code before release.
 ******************************************************************************************************************************/
...
try {
	int REQUIRED_RESPONSE = 1;
	Intent intent = new Intent();
	intent.setComponent(new ComponentName("com.teslasoft.libraries.support", "com.teslasoft.jarvis.licence.PiracyCheckActivity"));
	Bundle extras = new Bundle();
	extras.putString("appId", this.getPackageName());
	extras.putString("appSign", appSignatureHash); // It's strongly reccomended to set value directly without variables. It can improve safety of your app.
	extras.putString("isNotif", "false"); // IMPORTANT: this walue is a STRING; If walue is "true" the unlicence notification will be shown
	intent.putExtras(extras);
	this.startActivityForResult(intent, REQUIRED_RESPONSE);
} catch (Exception e) {
	// Do something if Jarvis Services is unavaliable
}
```
And add method ***onActivityResult***:
```java
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	if (resultCode == Activity.RESULT_OK) {
		// Application licenced
	} else if (resultCode == 2) {
		// // Do something if API connected is not correctly
		// Extra key "appId" is required
		// Extra key "isNotif" is required
		// Extra key "appSign" is required
	} else if (resultCode == 3) {
		// Application not found
	} else if (resultCode == 4) {
		// Jarvis Services has spoofed and may be unsafe, licence check failed
	} else {
		// Application unlicenced
	}
}
```

- NOTE: For licence check Internet connection is not required (but it may required in future releases).

## How to use crash handler

```java
try {
	Intent intent = new Intent();
	intent.setComponent(new ComponentName("com.teslasoft.libraries.support", "com.teslasoft.jarvis.crashreport.Report"));
	Bundle extras = new Bundle();
	extras.putString("errz", "full stacktrace here"); // you can get it by using printStackTrace() function
	intent.putExtras(extras);
	this.startActivity(intent);
} catch (Exception e) {
	// Do something if Teslasoft Core is unavaliable
}
```