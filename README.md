# Teslasoft Core
### Teslasoft Core is an application, that contains most of Teslasoft APIs and libraries.

## Latest update (1.27.20210831.6.990-stable) changelog

* Strict safety and license check added

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