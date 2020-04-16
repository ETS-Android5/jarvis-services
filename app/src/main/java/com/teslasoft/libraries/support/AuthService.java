package com.teslasoft.libraries.support;

import android.os.IBinder;
import android.os.Handler;
import android.app.Service;
import android.widget.Toast;
import android.content.Intent;

public class AuthService extends Service
{
	@Override
	public IBinder onBind(Intent p1)
	{
		// TODO: Implement this method
		return null;
	}
	
	@Override
    public void onDestroy() {
        super.onDestroy();
        toast("Account added");
    }

    final Handler mHandler = new Handler();

	void toast(final CharSequence text)
	{
        mHandler.post(new Runnable()
		{
            @Override public void run()
			{
                Toast.makeText(AuthService.this, text, Toast.LENGTH_SHORT).show();
            }
    	});
    }
}
