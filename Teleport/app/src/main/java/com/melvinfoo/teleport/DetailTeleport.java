package com.melvinfoo.teleport;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.os.AsyncTask;
import android.app.FragmentTransaction;
import android.content.Context;

//GUI elememts
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.CheckBox;

//SQL libs
import com.melvinfoo.teleport.TeleportDatabaseHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.content.ContentValues;

//Service 
import android.content.Intent;
import android.util.Log;

//location service 
import android.content.ServiceConnection;
import android.os.IBinder;
import android.content.ComponentName;
import android.os.Handler;



public class DetailTeleport extends Fragment implements View.OnClickListener
{
	private long id;
	private TitleSetter listener;
	
	//location service
	private double totalMeters =0;
	private OdometerService odometer;
	private boolean bound;
	private ServiceConnection connection = new ServiceConnection(){

		@Override
		public void onServiceConnected(ComponentName p1, IBinder binder)
		{
			OdometerService.OdometerBinder odometerBinder = (OdometerService.OdometerBinder) binder;
			Log.v("Odometer", "in service connected");
			odometer = odometerBinder.getOdometer();
			bound = true;
		}

		@Override
		public void onServiceDisconnected(ComponentName p1)
		{
			bound = false;
		}


		
	};
	
	
	private class UpdateTelelportTask extends AsyncTask<Long, Void, Boolean>
	{
		ContentValues cv = new ContentValues();
		//updating of favourites is done on the background thread
		@Override
		protected Boolean doInBackground(Long... myLong)
		{
			try{
				Long databaseId = myLong[0];
				SQLiteDatabase db = (new TeleportDatabaseHelper(getContext())).getWritableDatabase();
				db.update("DELIVERIES",
						  cv,
						  "_id = ?",
						  new String[]{String.valueOf(databaseId)});
				db.close();

				return true;
			}
			catch (SQLiteException ex){
				return false;
			}
			
		}

		//done on tbe main thread. have access to gui 
		@Override
		protected void onPreExecute()
		{
			super.onPreExecute();
			boolean isChecked = ((CheckBox) getView().findViewById(R.id.fragment_detail_teleportCheckBox)).isChecked();
			cv.put("FAVOURITE", isChecked);
		}

		@Override
		protected void onPostExecute(Boolean result)
		{
			super.onPostExecute(result);
			if (!result){
				Toast error = Toast.makeText(getContext(), "Update of favourites failed", Toast.LENGTH_SHORT); 
				error.show();
			}
		}
		
	}

    
	interface TitleSetter{
		public void onTeleportSelected(String title);
	}

    public DetailTeleport() {
        // Required empty public constructor
    }

	@Override
	public void onAttach(Context context)
	{
		// TODO: Implement this method
		super.onAttach(context);
		listener = (TitleSetter) context;
		
		
	}
	
	//odometer service
	void watchMillage(){
		final Handler handle = new Handler();
		handle.post(new Runnable(){
				@Override
				public void run(){
					TextView odometerView = (TextView) getView().findViewById(R.id.fragment_detail_teleport_odometer);
					if (odometer != null){
						totalMeters = totalMeters +odometer.getMiles();
						odometerView.setText(String.valueOf(totalMeters));
						}
					//this refers to the new runnable object
					//calls the same handler s dont need to recreste a new handler 
					handle.postDelayed(this, 1000);
					};
				}
			);
	}

	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (savedInstanceState != null){
            id = savedInstanceState.getLong("id");
        }
		//root view 
        View layout =inflater.inflate(R.layout.fragment_detail_teleport, container, false);
		//set favourites checkbox to repspnd to clicks 
		layout.findViewById(R.id.fragment_detail_teleportCheckBox).setOnClickListener(this);
		layout.findViewById(R.id.fragment_detail_teleportButton).setOnClickListener(this);
		return layout;
    }
    @Override
    public void onStart(){
        super.onStart();
		try
		{
			Log.v("Service", "at onStart");
			TeleportDatabaseHelper helper = new TeleportDatabaseHelper(getContext());
			//use rradable incase writable is unavaliable (due to insufficient storage)
			SQLiteDatabase db = helper.getReadableDatabase();
			//filter by promart kry_id = user selected id. 
			Cursor cursor = db.query("DELIVERIES", 
									 new String[]{"_id", "TITLE", "SEND_TO", "SENT_FROM","IMAGE_ID", "PACKAGE_WEIGHT", "FAVOURITE"}, 
									 "_id = ?",
									 new String[]{String.valueOf(id)}, 
									 null,
									 null,
									 null);
			//returns true if move is sucessful,need to move to first even tho its the only record.
			if (cursor.moveToFirst())
			{
				String title = cursor.getString(1);
				String sendTo = cursor.getString(2);
				String sentFrom = cursor.getString(3);
				int imgId = cursor.getInt(4);
				float weight = cursor.getFloat(5);
				boolean isFavourite = (1 == cursor.getInt(6));
				//Gets root view
				View rootView = getView();
				if (rootView != null)
				{
					TextView send_to = (TextView) rootView.findViewById(R.id.fragment_detail_adress_to_send_to);
					send_to.setText(sendTo);
					TextView sent_from = (TextView) rootView.findViewById(R.id.fragment_detail_teleport_adress_sent_from);
					sent_from.setText(sentFrom);
					TextView weightText = (TextView) rootView.findViewById(R.id.fragment_detail_teleport_weight);
					weightText.setText(String.valueOf(weight));
					ImageView packageImage = (ImageView) rootView.findViewById(R.id.fragment_detail_package_image);
					packageImage.setImageResource(imgId);
					CheckBox favourite = (CheckBox) rootView.findViewById(R.id.fragment_detail_teleportCheckBox);
					favourite.setChecked(isFavourite);
					listener.onTeleportSelected(title);
				} }}
		catch (SQLiteException EX)
		{
			Toast error = Toast.makeText(getContext(), "database unavailable", Toast.LENGTH_SHORT);
			error.show();
		}
		
		//odometer service
		Intent intent = new Intent(getContext(), OdometerService.class);
		getContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
		watchMillage();
	

    }
    public void setDeliveryID(long id) {
        this.id = id;
    }

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if (bound){
			getContext().unbindService(connection);
			bound = false;
		}
	}
	
	

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putLong("id", id);

    }
	
	//Overrriden frok view.onclickListener

	@Override
	public void onClick(View p1)
	{
		Log.v("Service", "at button Clikc");
		//calls service
		if(p1.getId() == R.id.fragment_detail_teleportButton){
			Log.v("Service", "at if");
			Intent service = new Intent(getContext(),NotificationService.class);
			getContext().startService(service); 
			
		}
		else{
			//checkbox button, call asyntask
			new UpdateTelelportTask().execute(id);
		}
	}
}
