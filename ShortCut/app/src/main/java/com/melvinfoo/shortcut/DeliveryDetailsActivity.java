package com.melvinfoo.shortcut;

import android.app.Activity;
import android.os.*;
import android.content.Intent;
import android.widget.*;

public class DeliveryDetailsActivity extends Activity
{	
	public static final String DELIVERY_POSITION = "DELIVERY POSITION";
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Intent intentThatCalled = getIntent();
		int index = (int) intentThatCalled.getLongExtra(DELIVERY_POSITION, 0);
		Delivery selectedDelivery = Delivery.Deliveries[index];
		ImageView sampleImage = (ImageView) findViewById(R.id.sample_image_view);
	}

	
}

