package com.melvinfoo.teleport;
import android.os.Binder;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

//location listner
import android.location.Location;
import android.os.Bundle;
import android.location.LocationListener;
import android.location.LocationManager;

public class OdometerService extends Service
{
	private final IBinder binder = new OdometerBinder();
	private Location lastLocation;
	private double distanceInMeters;
	private LocationManager lm;
	
	
	class OdometerBinder extends Binder{
		OdometerService getOdometer(){
			return OdometerService.this;
		}
	}
	
	

	@Override
	public IBinder onBind(Intent p1)
	{
		LocationListener listener = new  LocationListener(){

			@Override
			public void onLocationChanged(Location location)
			{
				if (lastLocation == null){
					lastLocation = location;
				}
				distanceInMeters = location.distanceTo(lastLocation);
				lastLocation = location;
			}

			@Override
			public void onStatusChanged(String p1, int p2, Bundle p3)
			{
				// TODO: Implement this method
			}

			@Override
			public void onProviderEnabled(String p1)
			{
				// TODO: Implement this method
			}

			@Override
			public void onProviderDisabled(String p1)
			{
				// TODO: Implement this method
			}


		};{}
		//return OdometerBinder object
		lm = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
								1000,
								1,
								listener);
								
		return binder;
	}

	
	public double getMiles(){
		return distanceInMeters;
	}
}
