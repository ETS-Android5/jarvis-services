package com.teslasoft.jarvis;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;

import com.teslasoft.libraries.support.R;

public class EmulatedBoot extends Activity {

	private LinearLayout background;
	private LinearLayout logo_mask;
	public int counter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_IMMERSIVE |
				View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
				View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
				View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
				View.SYSTEM_UI_FLAG_FULLSCREEN
		);

		overridePendingTransition(0, 0);
		setContentView(R.layout.bootanimation);
		
		background = (LinearLayout) findViewById(R.id.background);
		logo_mask = (LinearLayout) findViewById(R.id.logo_mask);
		LinearLayout layout_main = (LinearLayout) findViewById(R.id.layout_main);

		counter = 0;
		
		final Handler handler = new Handler();
		handler.postDelayed(() -> {
			background.setAlpha(1.f);
			logo_mask.setAlpha(1.f);
			Animate();
		}, 1200);
	}
	
	public void Animate() {
		final Handler handler = new Handler();
		handler.postDelayed(() -> {
			background.setTranslationX(background.getTranslationX() + 2);
			counter++;

			if (counter == 1024 || counter > 1024) {
				counter = 0;
				background.setTranslationX(background.getTranslationX() - 2048);
			}

			Animate();
		}, 1);
	}

	public void Close(View v) {
		finishAndRemoveTask();
		overridePendingTransition(0, 0);
	}

	public void Ignore(View v) {}

	@Override
	protected void onPause() {
		super.onPause();
		overridePendingTransition(0, 0);
	}

	@Override
	protected void onResume() {
		super.onResume();
		overridePendingTransition(0, 0);
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finishAndRemoveTask();
	}
}
