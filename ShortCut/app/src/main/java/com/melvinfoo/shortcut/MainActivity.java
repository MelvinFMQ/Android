package com.melvinfoo.shortcut;

import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.View;
import android.content.*;

public class MainActivity extends Activity 
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ListView list = (ListView) findViewById(R.id.cato_option);
		//inner class 
		AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapterview,
									View gotClickedView,
									int pos,
									long id){
				if (pos == 0){
					startActivity(new Intent(MainActivity.this, DeliveryActivity.class));
				}
			}
			
		};
		list.setOnItemClickListener(listener);
        
    }
}
