package com.melvinfoo.teleport;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.*;
import android.app.*;
import android.content.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTeleport extends Fragment{

    private int deliveryID;
	private TitleSetter listener;
	interface TitleSetter{
		public void onTeleportSelected(String title);
	}

    public DetailTeleport() {
        // Required empty public constructor
    }

	@Override
	public void onAttach(Context context)
	{
		// TODO: Implement this method
		super.onAttach(context);
		listener = (TitleSetter) context;
		
		
	}

	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (savedInstanceState != null){
            deliveryID = savedInstanceState.getInt("deliveryId");
        }
		else{
			Fragment stopwatch = new StopWatchFragment();
			FragmentTransaction ft = getChildFragmentManager().beginTransaction();
			ft.replace(R.id.fragment_detail_teleportFrameLayout, stopwatch);
			ft.addToBackStack(null);
			ft.commit();
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
            ImageView packageImage = (ImageView) rootView.findViewById(R.id.fragment_detail_package_image);
            packageImage.setImageResource(selectedDelivery.getImageOfPackageId());
			listener.onTeleportSelected(String.valueOf(selectedDelivery.getTitle()));
			
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
