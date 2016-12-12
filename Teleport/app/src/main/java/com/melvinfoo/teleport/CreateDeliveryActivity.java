package com.melvinfoo.teleport;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.app.ActionBar;

//database imports 
import android.view.View;
import android.widget.EditText;
import com.melvinfoo.teleport.TeleportDatabaseHelper;
import android.database.sqlite.SQLiteDatabase;

//auto complete imports 
import android.widget.AutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.util.Log;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;

public class CreateDeliveryActivity extends Activity implements OnItemClickListener
{
	
	
	private String sendToPlaceId; 
	private String sentFromPlaceId;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_delivery);
		//ActionBar myActionBar = getActionBar();
		//myActionBar.setDisplayHomeAsUpEnabled(true);
//		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//																android.R.layout.simple_dropdown_item_1line, COUNTRIES);
		AutoCompleteArray adapter = new AutoCompleteArray(this, android.R.layout.simple_dropdown_item_1line);
		AutoCompleteTextView textView = (AutoCompleteTextView)
			findViewById(R.id.create_order_send_to);
		textView.setAdapter(adapter);
		textView.setOnItemClickListener(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id)
	{
		int viewId = parent.getId();
		Log.v("selection", "Create order send to"  + String.valueOf(R.id.create_order_send_to));
		Log.v("selection" , "send to from parameter" + String.valueOf(parent.getId()));
		boolean equal = (viewId == R.id.create_order_send_to);
		Log.v("selection", String.valueOf(equal));
		AutoCompleteTextView textView = (AutoCompleteTextView)
			findViewById(R.id.create_order_send_to);
		AutoCompleteArray adapter = (AutoCompleteArray) textView.getAdapter();
		String placeId = adapter.getId(pos);
		Log.v("selection", placeId);
		Log.v("selection", adapter.getItem(pos).toString());
		
	}

	
	
	

	public void onSubmitClick(View button){
		String title = ((EditText) findViewById(R.id.create_order_title)).getText().toString();
		String from = ((EditText) findViewById(R.id.create_order_sent_from)).getText().toString();
		String sendto = ((EditText) findViewById(R.id.create_order_send_to)).getText().toString();
		Float weight = Float.valueOf(((EditText) findViewById(R.id.create_order_weight)).getText().toString());
		TeleportDatabaseHelper helper = new TeleportDatabaseHelper(this);
		SQLiteDatabase db = helper.getWritableDatabase();
		helper.insertDelivery(db, sendto, from,title, R.drawable.miband, weight);
		
	}
}
