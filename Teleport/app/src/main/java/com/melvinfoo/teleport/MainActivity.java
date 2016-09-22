package com.melvinfoo.teleport;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity implements DeliveryListFragment.ListClicker{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onItemClick(int position){
        if (findViewById(R.id.fragment_container) != null){
            DetailTeleport fragment = new DetailTeleport();
            fragment.setDeliveryID(position);
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else{
            Intent myIntent = new Intent(this, DeliveryDetailActivity.class);
            myIntent.putExtra(DeliveryDetailActivity.DELIVERY_ID, position);
            startActivity(myIntent);

        }
    }
}
