package com.melvinfoo.teleport;


import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.app.ActionBar;
import com.melvinfoo.teleport.TeleportDatabaseHelper;
import android.view.View;
import android.widget.EditText;
import com.melvinfoo.teleport.TeleportDatabaseHelper;
import android.database.sqlite.SQLiteDatabase;

;public class CreateDeliveryActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_delivery);
		//ActionBar myActionBar = getActionBar();
		//myActionBar.setDisplayHomeAsUpEnabled(true);
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
