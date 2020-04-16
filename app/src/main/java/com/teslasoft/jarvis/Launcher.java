package com.teslasoft.jarvis;

import android.os.*;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.util.*;
import android.net.*;
import com.teslasoft.libraries.support.R;
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

public class Launcher extends Activity
{
	private LinearLayout search;
	private LinearLayout sp1;
	private LinearLayout sp2;
	private LinearLayout sp3;
	private LinearLayout sp4;
	private LinearLayout sp5;
	private ImageView sp6;
	private TextView time;
	private TextView date;
	private Calendar calend = Calendar.getInstance();
	private int ic1 = 1;
	private int ic2 = 2;
	
	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jarvis_home);
		
		/*
		 Set style for Google search box
		 search = (LinearLayout) findViewById(R.id.search);
		 android.graphics.drawable.GradientDrawable gt = new android.graphics.drawable.GradientDrawable();
		 gt.setColor(Color.parseColor("#FF202026"));
		 gt.setCornerRadius(50);
		 search.setBackground(gt);
		 search.setTranslationZ(3);
		 search.setElevation(7);
		*/
		
		// Get current date
		GetDate();
		
		// Start animation
		/*
		 sp1 = (LinearLayout) findViewById(R.id.sp1);
		 sp2 = (LinearLayout) findViewById(R.id.sp2);
		 sp3 = (LinearLayout) findViewById(R.id.sp3);
		 sp4 = (LinearLayout) findViewById(R.id.sp4);
		 sp5 = (LinearLayout) findViewById(R.id.sp5);
		*/
		
		sp6 = (ImageView) findViewById(R.id.sp6);
		
		sp6.setOnLongClickListener(new View.OnLongClickListener()
		{
			@Override public boolean onLongClick(View v)
			{
				Settings();
				return true;
			}
		});
		
		// sp2.setRotation(360);
		// sp4.setRotation(360);
		
		ic1 = 360;
		ic2 = 360;
		
		RotatorAnim();
	}

	public void showApps(View v)
	{
		Intent i = new Intent(this, AppsList.class);
		startActivity(i);
	}
	
	public void LaunchJarvis(View v)
	{
		Intent i = new Intent(this, com.teslasoft.libraries.support.LoadActivity.class);
		startActivity(i);
	}
	
	public void Settings()
	{
		Intent i = new Intent(this, com.teslasoft.jarvis.Settings.class);
		startActivity(i);
	}
	
	public void LaunchClock(View v)
	{
		Intent i = getPackageManager().getLaunchIntentForPackage("com.google.android.deskclock");
		if (i == null)
		{
			return;
		}
		
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(i);
	}
	
	public void LaunchJarvisNative(View v)
	{
		Intent l = new Intent(this, Jarvis.class);
		startActivity(l);
	}
	
	public void LaunchSettings(View v)
	{
		Intent i = getPackageManager().getLaunchIntentForPackage("com.android.settings");
		if (i == null)
		{
			return;
		}
		
		i.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(i);
	}
	
	public void LaunchCalendar(View v)
	{
		Intent i = getPackageManager().getLaunchIntentForPackage("com.google.android.deskclock");
		if (i == null)
		{
			return;
		}

		i.addCategory(Intent.CATEGORY_LAUNCHER);
		startActivity(i);
	}

	@Override
	public void onBackPressed()
	{
		// super.onBackPressed();
	}
	
	public void GetDate ()
	{
		calend = Calendar.getInstance();
		time = (TextView) findViewById(R.id.time);
		date = (TextView) findViewById(R.id.date);
		time.setText(new SimpleDateFormat("HH:mm").format(calend.getTime()));
		date.setText(new SimpleDateFormat("EEEE dd.MM").format(calend.getTime()));
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				GetDate();
			}
		}, 500);
	}
	
	public void RotatorAnim ()
	{
		sp1 = (LinearLayout) findViewById(R.id.sp1);
		sp2 = (LinearLayout) findViewById(R.id.sp2);
		sp3 = (LinearLayout) findViewById(R.id.sp3);
		sp4 = (LinearLayout) findViewById(R.id.sp4);
		sp5 = (LinearLayout) findViewById(R.id.sp5);
		sp6 = (ImageView) findViewById(R.id.sp6);
		
		sp1.setRotation(sp1.getRotation() + 1);
		sp2.setRotation(sp2.getRotation() - 2);
		sp3.setRotation(sp3.getRotation() + 3);
		sp4.setRotation(sp4.getRotation() + 1);
		sp5.setRotation(sp5.getRotation() - 5);
		sp6.setRotation(sp6.getRotation() + 2);
		
		ic1--;
		ic2--;
		
		if (ic1 == 0)
		{
			ic1 = 360;
		}
		
		if (ic2 == 0)
		{
			ic2 = 360;
		}
		
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				RotatorAnim();
			}
		}, 10);
	}
}
