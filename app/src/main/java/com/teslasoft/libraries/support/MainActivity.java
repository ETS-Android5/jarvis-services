package com.teslasoft.libraries.support;

import android.os.*;
import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.util.*;
import android.net.*;
import android.support.v7.appcompat.*;
import android.graphics.*;
import android.graphics.drawable.*;

public class MainActivity extends Activity 
{	
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
		return;
	}
			
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		//final Button button = (Button) findViewById(R.id.button);
		//button.getBackground().setColorFilter(Color.parseColor("#00999999"), PorterDuff.Mode.MULTIPLY);
		/*button.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// your handler code here
			}
		});*/
		
		int[] colors = {Color.parseColor("#992E8B57"),Color.parseColor("#992E8B57")};
		
		LinearLayout btn = (LinearLayout) findViewById(R.id.button);
		GradientDrawable gd = new GradientDrawable(GradientDrawable.Orientation.TL_BR, colors);

		gd.setCornerRadius(16);
		//apply the button background to newly created drawable gradient
		btn.setBackground(gd);
		btn.setTranslationZ(10);
		btn.setElevation(5);
		//LinearLayout btui = (LinearLayout) findViewById(R.id.ui);
		//final WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
		//final Drawable wallpaperDrawable = wallpaperManager.getDrawable();
		//btui.setBackground(wallpaperDrawable);
		
    }
}


