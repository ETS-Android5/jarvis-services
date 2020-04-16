package com.teslasoft.jarvis;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import com.teslasoft.libraries.support.R;

public class CoreActivity extends Activity
{
	//AlertDialog alertDialog; // = new AlertDialog.Builder(CoreActivity.this).create();
	
	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);
		
		overridePendingTransition(0, 0);
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				/*alertDialog = new AlertDialog.Builder(com.teslasoft.jarvis.CoreActivity.this).create();
				alertDialog.setTitle("Jarvis JCA code");
				alertDialog.setMessage("0x314705b42");
				alertDialog.setIcon(R.drawable.jarvis2);

				alertDialog.setButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						alertDialog.hide();
						final Handler handler = new Handler();
						handler.postDelayed(new Runnable()
						{
							@Override
							public void run()
							{
								finish();
								overridePendingTransition(0, 0);
							}
						}, 100);
					}
				});
				
				alertDialog.show();
				*/
			}
		}, 100);
		
		Intent i = new Intent(com.teslasoft.jarvis.CoreActivity.this, com.teslasoft.jarvis.core.ServicesActivity.class);
		startActivity(i);
		finish();
	}
	
	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		// super.onBackPressed();
		// mWebview.startAnimation(mFadeOutAnimation);
		
		// alertDialog.hide();
		
		/*final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				
			}
		}, 100);
		*/
		
		finish();
		overridePendingTransition(0, 0);
		
	}
}
