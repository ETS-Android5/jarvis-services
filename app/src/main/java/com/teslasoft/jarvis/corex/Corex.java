package com.teslasoft.jarvis.corex;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Corex extends Activity
{
	public native String stringFromJNI();
	public native String unimplementedStringFromJNI();
	
	static {
        System.loadLibrary("jarcorex");
    }
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		TextView  tv = new TextView(this);
        tv.setText(stringFromJNI());
        setContentView(tv);
	}
}
