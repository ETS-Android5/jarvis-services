package com.teslasoft.jarvis.ui;

import android.os.*;
import android.app.*;
import android.content.*;
import com.teslasoft.libraries.support.R;
import android.view.*;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.app.AlertDialog;
import android.util.*;
import android.net.*;
import android.graphics.*;
import android.webkit.*;
import android.annotation.*;
import java.util.*;
import android.view.animation.Animation.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;


public class DialogConfirm extends Activity
{
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		/* */
	}

	private TextView dialog_title;
	private TextView dialog_message;
	private String title;
	private String message;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		/* Show the dialog */
		setContentView(R.layout.ui_dialog_confirm);
		
		try {
			Intent intent = getIntent();
			Bundle extras = intent.getExtras();
			title = extras.getString("title");
			message = extras.getString("message");
		
			dialog_title = (TextView) findViewById(R.id.dialog_title);
			dialog_title.setText(title);
		
			dialog_message = (TextView) findViewById(R.id.dialog_message);
			dialog_message.setTextIsSelectable(true);
			dialog_message.setText(message);
		}
		
		catch (Exception e)
		{
			
		}
	}

	@Override
	public void onBackPressed()
	{
		/* On hw back buttom clicked */
		super.onBackPressed();
		Dissmiss();
	}
	
	public void Close(View v)
	{
		/* On background clicked */
		Dissmiss();
	}
	
	public void onBgClicked(View v)
	{
		/* Ignore clicks */
	}
	
	public void onNegativeClicked(View v)
	{
		/* On cancel button clicked */
		Dissmiss();
	}
	
	public void onPositiveClicked(View v)
	{
		/* On confirm button clicked */
		Dissmiss();
	}

	@Override
	protected void onPause()
	{
		// TODO: Implement this method
		super.onPause();
		// Dissmiss();
	}
	
	public void Dissmiss()
	{
		/* Close the dialog */
		finishAndRemoveTask();
	}
}
