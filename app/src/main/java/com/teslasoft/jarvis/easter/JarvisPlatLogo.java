package com.teslasoft.jarvis.easter;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TextView;
import android.content.Context;
import com.teslasoft.libraries.support.R;
import android.graphics.Color;
import android.view.View;
import android.view.View.*;
import android.content.pm.PackageInfo;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class JarvisPlatLogo extends Activity 
{
	private LinearLayout button;
	private int ii;
	private int lu;
	private Animation mFadeInAnimation, mFadeOutAnimation;
	
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plat_logo);
		
		mFadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        mFadeOutAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        mFadeInAnimation.setAnimationListener(animationFadeInListener);
        mFadeOutAnimation.setAnimationListener(animationFadeOutListener);
		
		button = (LinearLayout) findViewById(R.id.button);
		ii = 0;
		lu = 0;
		
		button.startAnimation(mFadeInAnimation);
		
		/*
		 android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
		 gd.setColor(Color.parseColor("#FF2C6F2C"));
		 gd.setCornerRadius(9999);
		 shad.setBackground(gd);
		 shad.setElevation(10);
		 shad.setTranslationZ(5);
		*/
		
		button.setOnClickListener(mCorkyListener);
		button.setOnLongClickListener(new View.OnLongClickListener()
		{
			@Override public boolean onLongClick(View v)
			{
				//Press and hold
				
				if (ii >= 5)
				{
					ShowVersion(com.teslasoft.jarvis.easter.JarvisPlatLogo.this);
					Intent intent = new Intent(JarvisPlatLogo.this, EasterEgg.class);
					startActivity(intent);
					
					final Handler handler = new Handler();
					handler.postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							button.setVisibility(View.INVISIBLE);
							button.startAnimation(mFadeOutAnimation);
							final Handler handler = new Handler();
							handler.postDelayed(new Runnable()
							{
								@Override
								public void run()
								{
									finish();
									overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
								}
							}, 2000);
						}
					}, 500);
				}
				return true;
			}
		});
		
		button.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				if(event.getAction() == MotionEvent.ACTION_DOWN)
				{
					//On button pressed
				}
				
				if(event.getAction() == MotionEvent.ACTION_UP)
				{
					//On button released
				}
				return false;
			}
		});
    }
	
	public void ShowVersion(Context context)
	{
		try {
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
			String vs = pInfo.versionName;
			showToast(JarvisPlatLogo.this, "💎 Teslasoft Core " + vs + " 💎");
		}
		catch (Exception e)
		{

		}
	}
	
	private OnClickListener mCorkyListener = new OnClickListener()
	{
		public void onClick(View v)
		{
			ii++;
		}
	};
	
	private void showToast(Context context, String texts)
	{
		Toast toast = Toast.makeText(context, texts, Toast.LENGTH_SHORT);
		View view = toast.getView();
		view.setBackgroundResource(android.R.drawable.toast_frame);
		view.setBackgroundColor(Color.TRANSPARENT);
		TextView text = view.findViewById(android.R.id.message);
		text.setBackground(context.getResources().getDrawable(R.drawable.custom_toast));
		text.setTextColor(context.getResources().getColor(R.color.colorPrimaryLight));
		toast.show();
	}
	
	@Override
	protected void onPause()
	{
		super.onPause();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	Animation.AnimationListener animationFadeOutListener = new Animation.AnimationListener()
	{
		@Override
		public void onAnimationEnd(Animation animation)
		{
			// mWebview.startAnimation(mFadeInAnimation);
		}

		@Override
		public void onAnimationRepeat(Animation animation)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void onAnimationStart(Animation animation)
		{
			// TODO Auto-generated method stub
		}
	};

	Animation.AnimationListener animationFadeInListener = new Animation.AnimationListener()
	{
		@Override
		public void onAnimationEnd(Animation animation)
		{
			//mWebview.startAnimation(mFadeOutAnimation);
		}

		@Override
		public void onAnimationRepeat(Animation animation)
		{
			// TODO Auto-generated method stub
		}

		@Override
		public void onAnimationStart(Animation animation)
		{
			// TODO Auto-generated method stub
		}
	};

	@Override
	public void onBackPressed()
	{
		finishAndRemoveTask();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
	
	private boolean isMyServiceRunning(Class<?> serviceClass)
	{
		ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
		{
			if (serviceClass.getName().equals(service.service.getClassName()))
			{
				return true;
			}
		}
		return false;
	}
}
