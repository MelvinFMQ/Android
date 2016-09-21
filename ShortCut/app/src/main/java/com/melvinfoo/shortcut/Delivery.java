package com.melvinfoo.shortcut;
import java.util.*;
import android.graphics.drawable.*;

public class Delivery
{
	private String sendTo;
	private String fromWhere;
	private int imageId;
	
	public final static Delivery[] Deliveries = {
		new Delivery("angmokio", "gedong camp", R.drawable.mapgedong),
		new Delivery("kaki bukit", "ang mo kio", R.drawable.mapkaki)
		};
	
	private Delivery(String sendTo, String fromWhere, int imageId){
		this.sendTo = sendTo;
		this.fromWhere = fromWhere;
		this.imageId= imageId;
		
	}
	String getSendTo(){
		return sendTo;
	}
	String getFromWhere(){
		return fromWhere;
	}
	int getImageId(){
		return imageId;
	}

	@Override
	public String toString()
	{
		return fromWhere + " to " + sendTo;
	}
	
}
