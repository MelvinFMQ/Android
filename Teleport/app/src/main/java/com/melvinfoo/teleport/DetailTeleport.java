package com.melvinfoo.teleport;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.FragmentTransaction;
import android.content.Context;
import com.melvinfoo.teleport.TeleportDatabaseHelper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTeleport extends Fragment
{

    private long id;
	private TitleSetter listener;
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

	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (savedInstanceState != null){
            id = savedInstanceState.getLong("id");
        }
		else{
			Fragment stopwatch = new StopWatchFragment();
			FragmentTransaction ft = getChildFragmentManager().beginTransaction();
			ft.replace(R.id.fragment_detail_teleportFrameLayout, stopwatch);
			ft.addToBackStack(null);
			ft.commit();
		}
        return inflater.inflate(R.layout.fragment_detail_teleport, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
		try
		{
			TeleportDatabaseHelper helper = new TeleportDatabaseHelper(getContext());
			//use rradable incase writable is unavaliable (due to insufficient storage)
			SQLiteDatabase db = helper.getReadableDatabase();
			//filter by promart kry_id = user selected id. 
			Cursor cursor = db.query("DELIVERIES", 
									 new String[]{"_id", "TITLE", "SEND_TO", "SENT_FROM","IMAGE_ID", "PACKAGE_WEIGHT"}, 
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
					listener.onTeleportSelected(title);
				} }}
		catch (SQLiteException EX)
		{
			Toast error = Toast.makeText(getContext(), "database unavailable", Toast.LENGTH_SHORT);
			error.show();
		}

    }
    public void setDeliveryID(long id) {
        this.id = id;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putLong("id", id);

    }


}
