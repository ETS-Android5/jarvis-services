package com.teslasoft.jarvis;

import android.os.*;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import com.teslasoft.libraries.support.R;
import android.content.*;
import android.util.*;
import android.net.*;
import android.support.v7.appcompat.*;
import android.graphics.*;
import android.webkit.*;
import android.annotation.*;
import android.support.v7.app.*;
import android.content.pm.*;
import java.text.*;
import android.support.v4.*;
import android.support.v4.view.*;
import android.support.v4.app.*;
import android.view.animation.Animation.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;

public class DialogLoadingDark extends Activity
{
	private LinearLayout shadow;
	private LinearLayout dialog;
	private Animation mFadeInAnimation, mFadeOutAnimation;
	private ProgressBar progress;
	
	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
		setContentView(R.layout.dialog_loading_dark);
		
		mFadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.dialog_fade_in);
        mFadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.dialog_fade_out);
        mFadeInAnimation.setAnimationListener(animationFadeInListener);
        mFadeOutAnimation.setAnimationListener(animationFadeOutListener);
		
		shadow = (LinearLayout) findViewById(R.id.shadow);
		dialog = (LinearLayout) findViewById(R.id.dialog);
		progress = (ProgressBar) findViewById(R.id.progress);
	}

	public void DismissDialogActivity(View v)
	{
		finishAndRemoveTask();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}

	public void Ignore(View v)
	{
		//Do nothing
	}

	@Override
	public void onBackPressed()
	{
		finishAndRemoveTask();
		overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
	}
	
	Animation.AnimationListener animationFadeOutListener = new Animation.AnimationListener()
	{
		@Override
		public void onAnimationEnd(Animation animation)
		{
			//mWebview.startAnimation(mFadeInAnimation);
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
}
