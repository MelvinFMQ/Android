

package com.melvinfoo.teleport;

import android.app.Activity;
import android.os.Bundle;
import android.app.ActionBar;

import android.view.View;

public class DeliveryDetailActivity extends Activity {
    static String DELIVERY_ID = "DELIVERYID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_delivery_detail);
        int deliveryId = getIntent().getIntExtra(DELIVERY_ID, 0);
        DetailTeleport fragment = (DetailTeleport) getFragmentManager().findFragmentById(R.id.content_fragment);
        fragment.setDeliveryID(deliveryId);
		//ActionBar myActionBar = getActionBar();
		//myActionBar.setDisplayHomeAsUpEnabled(false);
    }

}
