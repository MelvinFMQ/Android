package com.melvinfoo.shortcut;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.content.*;

public class DeliveryActivity extends ListActivity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		ArrayAdapter<Delivery> adapter = new ArrayAdapter<Delivery>(
										this,
										android.R.layout.simple_list_item_1,
										Delivery.Deliveries
		);
		getListView().setAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		super.onListItemClick(l, v, position, id);
		Intent launchDetail = new Intent(this, DeliveryDetailsActivity.class);
		launchDetail.putExtra(DeliveryDetailsActivity.DELIVERY_POSITION, id);
		startActivity(launchDetail);
		
	}
	
	

}
