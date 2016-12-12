package com.melvinfoo.teleport;

import java.net.URL;
import java.net.MalformedURLException;
//when url is not correctly formed
import java.net.HttpURLConnection;
//url.openConnection might throw an exception when it cant get an connection object 
import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.InputStream;

import android.util.Log;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class HTTPHandler
{
	//URL is a string that describes hoe to get a resourse over the internet 
	public String getResponse(String urlAddress){
		String response =  null;
		try{
			URL url = new URL(urlAddress);
			//openConnection returns an URLConnection, need to csst it to the subclass of it 
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			InputStream in = new BufferedInputStream(connection.getInputStream());
			//response = convertToString(in);
			response = convertStreamToString(in);
			}
		catch(MalformedURLException ex){
			ex.printStackTrace();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		finally{
			return response;
			}
	}
	
	public String convertStreamToString(InputStream is){
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			while ((line = reader.readLine()) != null){
				sb.append(line);
			}
		} catch (IOException ex){
			ex.printStackTrace();
		}
		finally{
			try{
				is.close();
			}catch(IOException ex){
				ex.printStackTrace();
			}
			Log.v("autocomplete", "in convertStreamToString");
			Log.v("autocomplete", sb.toString());
			return sb.toString();
		}
	}
	public String convertToString(BufferedInputStream in){
		//BufferedInputString read writes into the byte buffer given to it
		byte[] byteBuffer= new byte[1024];
		String response = "";
		try{
			while (in.read(byteBuffer) != -1){
					response = response + new String(byteBuffer);
					
				}
			}
		catch (IOException ex){
			ex.printStackTrace();
		}
		finally{
			try{
				in.close();
			}
			catch(IOException ex){
				ex.printStackTrace();
			}
		}
		Log.v("autocompelte", "in httphandler");
		Log.v("autocomplete", response);
		return response;
	}
}
