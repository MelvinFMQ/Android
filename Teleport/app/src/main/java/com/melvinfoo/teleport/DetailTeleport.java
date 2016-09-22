package com.melvinfoo.teleport;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTeleport extends Fragment {

    private int deliveryID;

    public DetailTeleport() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (savedInstanceState != null){
            deliveryID = savedInstanceState.getInt("deliveryId");
        }
        return inflater.inflate(R.layout.fragment_detail_teleport, container, false);
    }
    @Override
    public void onStart(){
        super.onStart();
        Delivery selectedDelivery = Delivery.Deliveries[deliveryID];
        //Gets root view
        View rootView = getView();
        if (rootView != null){
            TextView send_to = (TextView) rootView.findViewById(R.id.fragment_detail_adress_to_send_to);
            send_to.setText(selectedDelivery.getAddressToSend());
            TextView sent_from = (TextView) rootView.findViewById(R.id.fragment_detail_teleport_adress_sent_from);
            sent_from.setText(selectedDelivery.getAddressSentFrom());
            TextView weight = (TextView) rootView.findViewById(R.id.fragment_detail_teleport_weight);
            weight.setText(String.valueOf(selectedDelivery.getPackageWeight()));
            TextView title = (TextView) rootView.findViewById(R.id.fragment_detail_teleport_title);
            title.setText(String.valueOf(selectedDelivery.getTitle()));
            ImageView packageImage = (ImageView) rootView.findViewById(R.id.fragment_detail_package_image);
            packageImage.setImageResource(selectedDelivery.getImageOfPackageId());
        }

    }
    public void setDeliveryID(int deliveryID) {
        this.deliveryID = deliveryID;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("deliveryId", deliveryID);

    }


}
