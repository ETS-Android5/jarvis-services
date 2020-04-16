package com.teslasoft.libraries.support;

import android.app.Application;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Toast;
import android.os.Process;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;

public class CrashHandler extends Application
{
    private UncaughtExceptionHandler uncaughtExceptionHandler;

    public void onCreate()
	{
		try
		{
        	this.uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        	Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler()
			{
				public void uncaughtException(Thread thread, Throwable th)
				{
					try
					{
						Intent intent = new Intent(CrashHandler.this.getApplicationContext(), com.teslasoft.jarvis.crashreport.Report.class);
						intent.setFlags(32768);
						intent.putExtra("errz", CrashHandler.this.getStackTrace(th));
						((AlarmManager) CrashHandler.this.getSystemService("alarm")).set(2, 1000, PendingIntent.getActivity(CrashHandler.this.getApplicationContext(), 11111, intent, 1073741824));
						Process.killProcess(Process.myPid());
						System.exit(2);
						CrashHandler.this.uncaughtExceptionHandler.uncaughtException(thread, th);
					}
					catch (Exception e)
					{
						Toast toast = Toast.makeText(getApplicationContext(), "ActivityNotFoundException: Unable to start com.teslasoft.libraries.support/com.teslasoft.jarvis.crashreport.Report: If you use Activity Manager or My Android Tools or more please enable the com.teslasoft.jarvis.crashreport.Report Activity.", Toast.LENGTH_SHORT); 
						toast.show();
					}
				}
			});
		}
		
		catch (Exception _e)
		{
			
		}
        super.onCreate();
    }

    private String getStackTrace(Throwable th)
	{
        Writer stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        while (th != null)
		{
            th.printStackTrace(printWriter);
            th = th.getCause();
        }
        String obj = stringWriter.toString();
        printWriter.close();
        return obj;
    }
}
