package com.teslasoft.jarvis.updater;

import android.os.Bundle;
import android.os.Build;
import android.os.Handler;
import android.content.Context;
import android.os.Process;
import android.app.Activity;
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
import java.util.concurrent.TimeUnit;

public class DownloadUpdateActivity extends Activity
{
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}
	
	public ProgressBar mProgressBar;
	private ProgressDialog mProgressDialog;
	public TextView mTextView;
	public TextView mTextView2;
	
	private static final int REQUEST_EXTERNAL_STORAGE = 1;
	private static String[] PERMISSIONS_STORAGE = {
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_gsx);
		mProgressBar = (ProgressBar) findViewById(R.id.updater_download_progress);
		mTextView = (TextView) findViewById(R.id.mupdater_dl_per);
		mTextView2 = (TextView) findViewById(R.id.mupdater_dl_max);
		mProgressBar.setProgress(0);
		
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
		
		startDownload();
	}
	
	public static void verifyStoragePermissions(Activity activity) {
		// Check if we have write permission
		int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

		if (permission != PackageManager.PERMISSION_GRANTED) {
			// We don't have permission so prompt the user
			ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
			);
		}
	}
	
	private void startDownload() {
        String url = "https://download.jarvis.studio/jarvis/100/jarvis.apk";
        new DownloadFileAsync().execute(url);
    }
	
	@Override
    protected Dialog onCreateDialog(int id) {
		mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setMessage("Downloading update ...");
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		mProgressDialog.setCancelable(false);
		mProgressDialog.show();
		return mProgressDialog;
    }
	
	class DownloadFileAsync extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// showDialog(1);
		}

		@Override
		protected String doInBackground(String... aurl) {
			int count;

			try {

				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				// Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);
				
				InputStream input = new BufferedInputStream(url.openStream());
				OutputStream output = new FileOutputStream("/mnt/sdcard/jarvis/jsfupdate/update.apk");

				byte data[] = new byte[1024];

				long total = 0;
				
				int curlen = lenghtOfFile/1024;

				while ((count = input.read(data)) != -1) {
					total += count;
					publishProgress(""+(int)((total*100)/lenghtOfFile));
					output.write(data, 0, count);
					/*if (total%2 == 0) {
						mTextView2.setText(total/1024 + " / " + curlen);
					}*/
				}

				output.flush();
				output.close();
				input.close();
			} catch (Exception e) {
				AlertDialog mAlertDialog = new AlertDialog.Builder(com.teslasoft.jarvis.updater.DownloadUpdateActivity.this).create();
				mAlertDialog.setTitle("Filed to download update");
				mAlertDialog.setMessage("Download not finished correctly. Update file may be corrupted: " + e.toString());
				mAlertDialog.setIcon(R.drawable.tick);
				mAlertDialog.setButton("Close", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				});

				mAlertDialog.setCancelable(false);
				mAlertDialog.show();
			}
			
			return null;
		}
		
		protected void onProgressUpdate(String... progress) {
			// Log.d("ANDRO_ASYNC",progress[0]);
			// mProgressDialog.setProgress(Integer.parseInt(progress[0]));
			mProgressBar.setIndeterminate(false);
			mProgressBar.setProgress(Integer.parseInt(progress[0]));
			mTextView.setText(mProgressBar.getProgress() + "%");
			mTextView2.setText(mProgressBar.getProgress() + " / 100");
		}

		@Override
		protected void onPostExecute(String unused) {
			try {
				/*final PackageManager pm = getPackageManager();
				String apkName = "update.apk";
				String fullPath = Environment.getExternalStorageDirectory() + "/jarvis/jsfupdate/" + apkName;        
				PackageInfo info = pm.getPackageArchiveInfo(fullPath, 0);
				String vname = info.versionName;*/
				
				Intent i = new Intent(com.teslasoft.jarvis.updater.DownloadUpdateActivity.this, com.teslasoft.jarvis.updater.InstallUpdateActivity.class);
				startActivity(i);
				finish();
			} catch (Exception e) {
				AlertDialog mAlertDialog = new AlertDialog.Builder(com.teslasoft.jarvis.updater.DownloadUpdateActivity.this).create();
				mAlertDialog.setTitle("Filed to download update");
				mAlertDialog.setMessage("Download not finished correctly. Update file may be corrupted: " + e.toString());
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
