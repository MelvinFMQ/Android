package com.melvinfoo.teleport;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
public class TeleportDatabaseHelper extends SQLiteOpenHelper
{
	private static final String DB_NAME = "teleport";
	private static final int DB_VERSION = 1;
	
	TeleportDatabaseHelper(Context c){
		super(c, DB_NAME, null ,DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		db.execSQL("CREATE TABLE DELIVERIES("
					+ "_id INTEGER PRIMARY AUTOINCREMENT"
				   	+ "SEND_TO TEXT,"
				   	+ "SENT_FROM TEXT,"
				   	+ "TITLE TEXT,"
				   	+ "IMAGE_ID INTEGER,"
				   	+ "PACKAGE_WEIGHT REAL)");
		insertDelivery(db, "angmokio", "gedong camp", "Miband", R.drawable.miband, 0.5f);
	}

	@Override
	public void onUpgrade(SQLiteDatabase p1, int p2, int p3)
	{
		// TODO: Implement this method
	}


	public void insertDelivery(SQLiteDatabase db, String sendTo, String sentFrom, String title, int imageID, float packageWeight){
		ContentValues newDelivery = new ContentValues();
		newDelivery.put("SEND_TO", sendTo);
		newDelivery.put("SENT_FROM", sentFrom);
		newDelivery.put("TITLE", title);
		newDelivery.put("PACKAGE_IMAGEID",imageID);
		newDelivery.put("PACKAGE_WEIGHT", packageWeight);
		db.insert("DELIVERIES", null, newDelivery);
	}
	
}
