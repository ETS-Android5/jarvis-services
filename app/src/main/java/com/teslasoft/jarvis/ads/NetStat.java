/* This is a library file. Do not modify (except package name) */
/* This is a class, which check internet connection */
package com.teslasoft.jarvis.ads;

// Connect libraries
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

// Android connectivity check
public class NetStat
{
    private static NetStat instance = new NetStat();
    static Context context;
    ConnectivityManager connectivityManager;
    NetworkInfo wifiInfo, mobileInfo;
    boolean connected = false;

    public static NetStat getInstance(Context ctx)
	{
        context = ctx.getApplicationContext();
        return instance;
    }

	// Check if your connected to internet
    public boolean isOnline()
	{
        try
		{
            connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
			connected = networkInfo != null && networkInfo.isAvailable() &&
                networkInfo.isConnected();
				
			// Return connectivity result
			return connected;
        }
		
		catch (Exception e)
		{
			// Catch exception
			// NotInternetException
        }
		
		// Return connectivity result
        return connected;
    }
}
