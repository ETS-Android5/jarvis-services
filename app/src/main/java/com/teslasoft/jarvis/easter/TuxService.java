package com.teslasoft.jarvis.easter;

import android.os.*;
import android.app.*;
import android.service.quicksettings.*;

public class TuxService extends Service
{
	@Override
	public IBinder onBind(android.content.Intent p1)
	{
		// TODO: Implement this method
		return null;
	}

	@Override
	public int onStartCommand(android.content.Intent intent, int flags, int startId)
	{
		// TODO: Implement this method
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onStart(android.content.Intent intent, int startId)
	{
		// TODO: Implement this method
		super.onStart(intent, startId);
	}

	public void onStartListening() {
		/*Tile tile = getQsTile();
		tile.setIcon(Icon.createWithResource(this,
											 R.drawable.tux_logo));
		tile.setLabel("???");
		tile.setContentDescription("Easter Egg");
		tile.setState(Tile.STATE_ACTIVE);
		tile.updateTile();*/
	}
}
