package com.teslasoft.jarvis.easter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.content.pm.PackageInfo;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.SmartToast;
import android.graphics.Color;

import com.teslasoft.libraries.support.R;

public class JarvisPlatLogo extends Activity 
{
	private LinearLayout button;
	private int ii;
	private Animation mFadeOutAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plat_logo);

		Animation mFadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        mFadeOutAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        mFadeInAnimation.setAnimationListener(animationFadeInListener);
        mFadeOutAnimation.setAnimationListener(animationFadeOutListener);
		
		button = (LinearLayout) findViewById(R.id.button);
		ii = 0;
		
		button.startAnimation(mFadeInAnimation);
		button.setOnClickListener(mCorkyListener);
		button.setOnLongClickListener(v -> {
			if (ii >= 5) {
				ShowVersion(JarvisPlatLogo.this);
				Intent intent = new Intent(JarvisPlatLogo.this, EasterEgg.class);
				startActivity(intent);

				final Handler handler = new Handler();
				handler.postDelayed(() -> {
					button.setVisibility(View.INVISIBLE);
					button.startAnimation(mFadeOutAnimation);
					final Handler handler1 = new Handler();
					handler1.postDelayed(() -> {
						finish();
						overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
					}, 2000);
				}, 500);
			}
			return true;
		});
		
		/*button.setOnTouchListener((v, event) -> {
			if(event.getAction() == MotionEvent.ACTION_DOWN) {}
			if(event.getAction() == MotionEvent.ACTION_UP) {}
			return false;
		});*/
    }
	
	public void ShowVersion(Context context) {
		try {
			PackageInfo pInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
			String vs = pInfo.versionName;
			SmartToast.create("💎 Teslasoft Core " + vs + " 💎", context);
		} catch (Exception ignored) {}
	}
	
	private OnClickListener mCorkyListener = new OnClickListener() {
		public void onClick(View v) {
			ii++;
		}
	};
	
	private void showToast(Context context, String texts) {
		Toast toast = Toast.makeText(context, texts, Toast.LENGTH_SHORT);
		View view = toast.getView();
		view.setBackgroundResource(android.R.drawable.toast_frame);
		view.setBackgroundColor(Color.TRANSPARENT);
		TextView text = view.findViewById(android.R.id.message);
		text.setBackground(context.getResources().getDrawable(R.drawable.toast_transparent_background));
		text.setTextColor(context.getResources().getColor(R.color.colorPrimaryLight));
		toast.show();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}

	Animation.AnimationListener animationFadeOutListener = new Animation.AnimationListener() {
		@Override
		public void onAnimationEnd(Animation animation) {}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
	};

	Animation.AnimationListener animationFadeInListener = new Animation.AnimationListener() {
		@Override
		public void onAnimationEnd(Animation animation) {}

		@Override
		public void onAnimationRepeat(Animation animation) {}

		@Override
		public void onAnimationStart(Animation animation) {}
	};

	@Override
	public void onBackPressed() {
		finishAndRemoveTask();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	}
}
