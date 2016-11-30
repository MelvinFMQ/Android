package com.melvinfoo.teleport;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;
import android.util.Log;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.TaskStackBuilder;
import android.app.PendingIntent;
import android.content.Context;


public class NotificationService extends IntentService
{
	//id to keep track of notification , e.g updating of notification.
	public static final int NOTIFICATION_ID = 5453;
	Handler mainThread;
	public NotificationService(){
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
	protected void onHandleIntent(Intent i){
		//passes the object to lock
		synchronized(this){
			try{
				wait(10000);
			}
			catch(InterruptedException ex){
				ex.printStackTrace();
			}
		}
		makeText();
		
	}
	
	protected void makeText()
	{
		//Make toast 
		Runnable job = new Runnable(){
			@Override
			public void run(){
				Toast.makeText(getApplicationContext(), "Suppp", Toast.LENGTH_SHORT).show();
			};
		};
		mainThread.post(job);
		//logging 
		Log.v("hi", "yo");
		//Notification .
		//create an explicit intrnt
		Intent explicit = new Intent(this, MainActivity.class);
		TaskStackBuilder tsb = TaskStackBuilder.create(this);
		tsb.addParentStack(MainActivity.class);
		tsb.addNextIntent(explicit);
		//ger penidng intent
		PendingIntent pending = tsb.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		//setContentTitle etc returns a builder object that we can call subsequent methods on
		Notification test = new Notification.Builder(this)
			.setSmallIcon(R.mipmap.ic_launcher)
			.setContentTitle("YOYO")
			.setContentText("hihi")
			.setContentIntent(pending)
			.build();
		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(NOTIFICATION_ID, test);
		
		
	}
}
