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
import android.support.v7.appcompat.*;
import android.graphics.*;
import android.webkit.*;
import android.annotation.*;
import android.support.v7.app.*;
import java.util.*;
import android.view.animation.Animation.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;


public class TaskExec extends Activity
{
	@Override
	public void onPointerCaptureChanged(boolean hasCapture)
	{
		// TODO: Implement this method
	}

	@Override
	public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState, persistentState);
	}

	@Override
	public void onBackPressed()
	{
		// TODO: Implement this method
		super.onBackPressed();
	}
	
	
	
	public void Exec (View v)
	{
		
	}
	
	public int onJarvisCommandExecutionStarted(int execStatus)
	{
		return 0;
	}
	
	public int onJarvisCommandExecutionCompleted(int execStatus)
	{
		return 0;
	}
	
	public int onJarvisCommandExecutionCanceled(int execStatus)
	{
		return 0;
	}
	
	public int onErrorWhileJarvisCommandExecution(String errorText, int requestCode)
	{
		return 0;
	}
}
