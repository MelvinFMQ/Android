package com.melvinfoo.teleport;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class NotificationService extends IntentService
{
	Handler mainThread;
	NotificationService(){
		//string passed into super is the name of the thread, used for debugging. 
		super("Notification Service");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// creates a handler in the main thread
		mainThread = new Handler();
		return super.onStartCommand(intent, flags, startId);
	}

	
	@Override
	protected void onHandleIntent(Intent p1)
	{
		Runnable job = new Runnable(){
			@Override
			public void run(){
				Toast.makeText(getApplicationContext(), "Suppp", Toast.LENGTH_SHORT).show();
			};
		};
		mainThread.post(job);
	}
}
