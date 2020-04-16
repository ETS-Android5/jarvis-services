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

public class Window extends Activity
{
	TextView text;
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
		setContentView(R.layout.window_theme_g);
	}

	public void DismissDialogActivity(View v)
	{
		finish();
	}

	public void Ignore(View v)
	{
		// Do nothing
	}

	@Override
	public void onBackPressed()
	{
		// super.onBackPressed();
		finish();
	}
}
