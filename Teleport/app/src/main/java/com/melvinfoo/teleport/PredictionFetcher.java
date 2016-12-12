package com.melvinfoo.teleport;

//debug
import android.util.Log;

//for internet part
import android.os.AsyncTask;

//json parsing
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
//get ui thread to display error
import android.app.Activity;
//diplsay error as toast 
import android.widget.Toast;
import android.app.Activity;


public class PredictionFetcher
{
	Activity app;
	String url;
	//Contructor to get context 
	PredictionFetcher(Activity appInput){
		app = appInput;
		

	}
	
	private boolean sucess;
	
	private String TAG = AutoCompleteArray.class.getSimpleName();
	//Variable to store http json that can also be accessed from filter class
	private ArrayList<Prediction> predictions = new ArrayList<Prediction>();
	//inner class as this class is only used in this outerclass.
	//to keep code more maintainable
	//prediciton object to medel results returned from web api
	class Prediction{
		class Term{
			int offset;
			String value;
			//Inner class to keep code more maintanable
			Term(int offsetInput, String stringInput){
				offset = offsetInput;
				value = stringInput;
			}
		}
		//human readable 
		String description;
		//unique stable id, cannot bebused to get more info
		//depreciated
		//int Id;
		int[] matchedSubStrings;
		//unique id that can be used to get more info 
		String placeId;
		//can be used to get more info, depretiated
		//String reference;
		//each term of the returned decription 
		ArrayList<Term> terms;
		//types of results
		//geocode - is lat, lang 
		//adress - geocode with a precise address
		//establishment - business results
		ArrayList<String> types;

		//allows compiler to check for all inputs
		private void putData(String descriptionInput, int[] matchSubStringsInput, String placeIdInput, ArrayList<Term> termsInput, ArrayList<String> typesInput){
			description = descriptionInput;
			matchedSubStrings = matchSubStringsInput;
			placeId = placeIdInput;
			terms = termsInput;
			types = typesInput;
		}

		@Override
		public String toString()
		{
			return description;
		}
		
		
	}
	//http needs to be connect on a seperate thread
	private class DownloadPredictionsTask extends AsyncTask<Void, Void, Void>{
		
		@Override
		protected synchronized Void doInBackground(Void...nothing){
			predictions = new ArrayList<Prediction>();
			String webServiceURL = url;
			String jsonString = (new HTTPHandler()).getResponse(webServiceURL);
			Log.v("autocomplete", "in do inbackground");
			try {
				JSONObject message = new JSONObject(jsonString);
				Log.v("autocomplete", "passed 96");
				String sucessCode = message.getString("status");
				Log.v("autocomplete", "Sucesscode: " +sucessCode);
				Log.v("autocomplete", String.valueOf(sucessCode.equals("OK")));
				if (sucessCode.equals("OK")){
					Log.v("autocomplete", "After sucesscode ok");
					JSONArray jsonPredictions = message.getJSONArray("predictions");
					Log.v("autocomplete", "length" + String.valueOf(jsonPredictions.length()));
					for (int i = 0; i < jsonPredictions.length(); i ++){
						Prediction newPrediction = new Prediction();
						Log.v("autocomplete", "value of i" + String.valueOf(i));
						JSONObject jsonSinglePrediction = jsonPredictions.getJSONObject(i);
						//description
						String description = jsonSinglePrediction.getString("description");
						//matched substrings
						JSONArray jsonMatchedSubStrings = jsonSinglePrediction.getJSONArray("matched_substrings");
						//first element is length , second is offset
						int[] matchedSubString = new int[2];
						//TODO: make a for loop here
						matchedSubString[0] = jsonMatchedSubStrings.getJSONObject(0).getInt("length");
						matchedSubString[1] = jsonMatchedSubStrings.getJSONObject(0).getInt("offset");
						//place_id
						String placeId = jsonSinglePrediction.getString("place_id");
						//terms
						JSONArray jsonArrayTerms = jsonSinglePrediction.getJSONArray("terms");
						ArrayList<Prediction.Term> terms = new ArrayList<Prediction.Term>();
						for (int j = 0; j < jsonArrayTerms.length(); j ++){
							JSONObject jsonTerm = jsonArrayTerms.getJSONObject(j);
							int offset = jsonTerm.getInt("offset");
							String value = jsonTerm.getString("value");
							//encapsulate the term in the newpredction object as the outer class
							Prediction.Term newTerm = newPrediction.new Term(offset, value);
							terms.add(newTerm);
						}
						ArrayList<String> types = new ArrayList<String>();
						JSONArray jsonArrayTypes = jsonSinglePrediction.getJSONArray("types");
						for(int k = 0; k < jsonArrayTypes.length(); k ++){
							String type = jsonArrayTypes.getString(k);
							types.add(type);
						}
						newPrediction.putData(description, matchedSubString, placeId, terms, types);
						predictions.add(newPrediction);
						Log.v("autocomplete", "right after parsing" + String.valueOf(predictions.size()));
					}
					
				}

			}
			catch (JSONException ex){
				Log.e(TAG, ex.getMessage());
				//get outclass instance from inner class
				//variable must be final to be used in local class
				//local class is the class defined in a method
				//final only affects the variable container, the actual object can still be modified
				final Activity activityThatCreatedArray = app;
				activityThatCreatedArray.runOnUiThread(new Runnable(){
						@Override 
						public void run(){
							Toast error  = Toast.makeText(app, "Error parsing json", Toast.LENGTH_SHORT);
							error.show();
						}

					}); 
			}
			return null;
		}
		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
		}
	}
	
	protected ArrayList<Prediction> fetchPredictions(String urlInput){
		url = urlInput;
		Log.v("autocomplete", "Previous size" + String.valueOf(predictions.size()));
		ArrayList<Prediction> previousPredictions = predictions; 
		(new DownloadPredictionsTask()).execute();
		return previousPredictions;
	}
}
