package com.melvinfoo.teleport;


import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeliveryListFragment extends ListFragment {
    interface ListClicker{
        public void onItemClick(int position);
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
        String[] current_delivery = new String[Delivery.Deliveries.length];
        for (int i = 0; i < Delivery.Deliveries.length; i ++){
            current_delivery[i] = Delivery.Deliveries[i].getTitle();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, current_delivery);
        setListAdapter(adapter);
        return super.onCreateView(inflater, container, savedInstanceState);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        listener.onItemClick(position);
    }

}
