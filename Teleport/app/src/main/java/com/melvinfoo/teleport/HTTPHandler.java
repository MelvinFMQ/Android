package com.melvinfoo.teleport;

import java.net.URL;
import java.net.MalformedURLException;
//whne url is not correctly formed
import java.net.HttpURLConnection;
//url.oOpenConnectioj might throw an exception when it cant get an connection object 
import java.io.IOException;
import java.io.BufferedInputStream;


import android.util.Log;
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
			BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
			response = convertToString(in);
			}
		catch(MalformedURLException ex){
			ex.printStackTrace();
		}
		catch(IOException ex){
			ex.printStackTrace();
		}
		return response;
		
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
		Log.v("Response", response);
		return response;
	}
}
