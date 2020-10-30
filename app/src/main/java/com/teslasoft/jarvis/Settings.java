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
import android.graphics.*;
import android.webkit.*;
import android.annotation.*;
import android.content.pm.*;
import java.text.*;

public class Settings extends Activity
{
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
	}
}
