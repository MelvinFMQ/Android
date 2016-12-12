package com.melvinfoo.teleport;

import android.widget.ArrayAdapter;

//for constructor 
import android.content.Context;

//for getFilter
import android.widget.Filter;

//debug
import android.util.Log;

//for getting predictions from the internetz
import java.util.ArrayList;
import android.app.Activity;
import android.view.*;

public class AutoCompleteArray extends ArrayAdapter
{
	private final String API_KEY = "AIzaSyBwKxHiHhywpl8ZZKaSrLGlf7RJrAP_spU";
	private final String AUTO_COMPLETE_URL=  "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=%s&types=geocode&key=%s";
	private ArrayList<PredictionFetcher.Prediction> data;
	
	AutoCompleteArray(Context context, int resource){
		super(context, resource);
	}

	@Override
	public int getCount()
	{
		return data.size();
	}

	public String getId(int pos){
		return ((PredictionFetcher.Prediction) data.get(pos)).placeId;
	}

	@Override
	public Object getItem(int position)
	{
		//Log.v("selected", "item selected" + String.valueOf(position));
		return data.get(position);
	}

	@Override
	public boolean isEmpty()
	{
		return data.isEmpty();
	}

	
	
	@Override
	public Filter getFilter()
	{
		Log.v("autocomplete", "a new filter");
		Filter filter = new Filter(){
			PredictionFetcher fetcher = new PredictionFetcher((Activity) getContext());
			@Override
			public synchronized FilterResults performFiltering(CharSequence constraints){
				FilterResults filterResults = new FilterResults();
				if (constraints != null){
					//convert constraints to string, remove all spaces.
					constraints = constraints.toString().replace(" ", "+");
					Log.v("autocomplete", constraints.toString());
					String urlAdress = String.format(AUTO_COMPLETE_URL, constraints, API_KEY);
					data = fetcher.fetchPredictions(urlAdress);
					filterResults.count = data.size();
					filterResults.values = data;
					Log.v("autocomplete", "returning filter results");
					Log.v("autocomplete", String.valueOf(data.size()));
					}
				return filterResults;
			}
			
			@Override
			public CharSequence convertResultToString(Object resultValue){
				//might want to implement a getter class
				String description = ((PredictionFetcher.Prediction) resultValue).description;
				return (CharSequence) description;
			}
			
			@Override
			public void publishResults(CharSequence constraint, FilterResults results){
				if (results.values != null && results.count > 0) {
					notifyDataSetChanged();
					Log.v("autocomplete", "notify data set changed");
				}
				else{
					notifyDataSetInvalidated();
				}
			}
		};
		return filter;
	}
}
