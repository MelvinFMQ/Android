package com.melvinfoo.teleport;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;
import com.melvinfoo.teleport.TeleportDatabaseHelper;
import android.database.Cursor;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */

public class DeliveryListFragment extends ListFragment
 {
	private SQLiteDatabase db;
	private Cursor cursor; 
	
    interface ListClicker{
        public void onItemClick(long id);
    }
    ListClicker listener;
    public DeliveryListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        listener = (ListClicker) activity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
		try{ 
			TeleportDatabaseHelper 	helper = new TeleportDatabaseHelper(inflater.getContext());
			db = helper.getReadableDatabase();
			cursor = db.query("DELIVERIES", new String[]{"_id", "TITLE"}, null, null, null, null, null);
			//2nd parameter is the format the text will places in 
			//4th parameter is the text view the data will be plsced in.
			CursorAdapter adapter = new SimpleCursorAdapter(inflater.getContext(), android.R.layout.simple_list_item_1, cursor, new String[]{"TITLE"}, new int[]{android.R.id.text1}, 0);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, current_delivery);
			setListAdapter(adapter);
		}
		catch (SQLiteException ex){
			Toast error = Toast.makeText(getContext(), "database not available" , Toast.LENGTH_SHORT);
			error.show();
			
		}
		return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        listener.onItemClick(id);
    }

	@Override
	public void onDestroy()
	{
		super.onDestroy();
		cursor.close();
		db.close();
		
	}
	
	

}
