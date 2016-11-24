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

import com.melvinfoo.teleport.DeliveryListFragment;

/**
 * A simple {@link Fragment} subclass.
 */

 
public class PastDeliveryListFragment extends DeliveryListFragment
 {

	 //No filters 
	@Override
	public String getFilter(){
		return null;
	}

	@Override
	public String getFilterValue()
	{
		return null;
	}

	
}
