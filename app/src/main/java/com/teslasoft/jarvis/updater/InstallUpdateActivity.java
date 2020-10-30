package com.teslasoft.jarvis.updater;

import android.os.Bundle;
import android.os.Build;
import android.os.Handler;
import android.content.Context;
import android.os.Process;
import android.app.Activity;
import java.util.concurrent.TimeUnit;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInstaller;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ComponentInfo;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import com.teslasoft.libraries.support.R;
import android.os.PowerManager;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import android.net.Uri;
import android.os.Environment;
import java.net.URLConnection;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.Manifest;
import android.app.ActivityManager;
import android.view.View.OnClickListener;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
// import android.support.v4.app.ActivityCompat; /* DEPRECATED API */
// import android.support.v4.content.FileProvider; /* DEPRECATED API */

public class InstallUpdateActivity extends Activity
{
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}
	
	public Intent intent;
	
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
	};
	
	public PackageManager pm;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_gsxi);
		
		if (isMyServiceRunning(com.teslasoft.jarvis.core.InitService.class))
		{
			// If Data Protector Service running
		}

		else 
		{
			try {
				startService(new Intent(this, com.teslasoft.jarvis.core.InitService.class));
			}
			catch (Exception e)
			{

			}
		}
		
		verifyStoragePermissions(this);
		
		try {
			TimeUnit.SECONDS.sleep(1);
			File toInstall = new File("/mnt/sdcard/jarvis/jsfupdate/", "update" + ".apk");
			
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
				try {
					final PackageManager pm = getPackageManager();
					String apkName = "update.apk";
					String fullPath = Environment.getExternalStorageDirectory() + "/jarvis/jsfupdate/" + apkName;        
					PackageInfo info = pm.getPackageArchiveInfo(fullPath, 0);
					String vname = info.versionName;
				
					Uri apkUri = FileProvider.getUriForFile(this, "com.teslasoft.libraries.support" + ".fileprovider", toInstall);
					intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
					intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
					intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
					startActivity(intent);
				} catch (Exception e) {
					AlertDialog mAlertDialog = new AlertDialog.Builder(this).create();
					mAlertDialog.setTitle("Filed to open update file");
					mAlertDialog.setMessage("Update file does not exists or corrupted: " + e.toString());
					mAlertDialog.setIcon(R.drawable.tick);
					mAlertDialog.setButton("Close", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});

					mAlertDialog.setCancelable(false);
					mAlertDialog.show();
				}
			} else {
				try {
					final PackageManager pm = getPackageManager();
					String apkName = "update.apk";
					String fullPath = Environment.getExternalStorageDirectory() + "/jarvis/jsfupdate/" + apkName;        
					PackageInfo info = pm.getPackageArchiveInfo(fullPath, 0);
					String vname = info.versionName;
					
					Uri apkUri = Uri.fromFile(toInstall);
					intent = new Intent(Intent.ACTION_VIEW);
					intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				} catch (Exception e) {
					AlertDialog mAlertDialog = new AlertDialog.Builder(this).create();
					mAlertDialog.setTitle("Filed to open update file");
					mAlertDialog.setMessage("Update file does not exists or corrupted: " + e.toString());
					mAlertDialog.setIcon(R.drawable.tick);
					mAlertDialog.setButton("Close", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							finish();
						}
					});

					mAlertDialog.setCancelable(false);
					mAlertDialog.show();
				}
			}
		} catch (Exception e) {
			AlertDialog mAlertDialog = new AlertDialog.Builder(this).create();
            mAlertDialog.setTitle("Updater internal error");
            mAlertDialog.setMessage("Permission denied or updater does not have right components: " + e.toString());
            mAlertDialog.setIcon(R.drawable.tick);
            mAlertDialog.setButton("Close", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
					finish();
				}
			});
			
			mAlertDialog.setCancelable(false);
            mAlertDialog.show();
		}
	}
	
	public static void verifyStoragePermissions(Activity activity) {
		int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permission != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
			);
		}
	}
	
	public Handler mHandler = new Handler();
	public void toast(final CharSequence text, final Context context)
	{
        mHandler.post(new Runnable()
		{
			@Override public void run()
			{
				try {
					Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
				}
				catch (Exception e)
				{
					
				}
			}
		});
    }
	
	public boolean isMyServiceRunning(Class<?> serviceClass)
	{
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		try
		{
			for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
			{
				if (serviceClass.getName().equals(service.service.getClassName()))
				{
					return true;
				}
			}
		}
		catch (Exception e)
		{

		}
		return false;
	}
}
