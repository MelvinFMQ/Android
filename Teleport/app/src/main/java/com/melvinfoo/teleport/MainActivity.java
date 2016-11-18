package com.melvinfoo.teleport;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.app.ActionBar;
import android.widget.AdapterView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.os.PersistableBundle;
import android.content.res.Configuration;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import android.app.FragmentManager;


public class MainActivity extends Activity implements DeliveryListFragment.ListClicker,OnMapReadyCallback,DetailTeleport.TitleSetter
{
	private ShareActionProvider share;
	private String[] titles;
	private ListView drawer;
	private DrawerLayout drawerLayout;
	private ActionBarDrawerToggle toggle;
	private int currentPosition;
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.menu, menu);
		//action prpvoders handle behaviour and appearance
		MenuItem shareItem = menu.findItem(R.id.action_bar_share);
		share = (ShareActionProvider) shareItem.getActionProvider();
		setIntent("Hi");
		return super.onCreateOptionsMenu(menu);
	}
	
	private void setIntent(String message){
		Intent shareIntent = new Intent(Intent.ACTION_SEND);
		shareIntent.setType("text/plain");
		shareIntent.putExtra(Intent.EXTRA_TEXT, message);
		share.setShareIntent(shareIntent);
	}

	@Override
	public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState)
	{
		// TODO: Implement this method
		toggle.syncState();
		super.onPostCreate(savedInstanceState, persistentState);
		
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig)
	{
		// TODO: Implement this method
		super.onConfigurationChanged(newConfig);
		toggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		outState.putInt("position", currentPosition);
	}
	

	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		//this returns true of the toggle has handled being clicked.
		if (toggle.onOptionsItemSelected(item)){
			return true;
		}
		switch (item.getItemId()){
			case R.id.action_create_delivery:
				Intent create_delivery = new Intent(this, CreateDeliveryActivity.class);
				startActivity(create_delivery);
				return true;
			default:
				return super.onMenuItemSelected(featureId, item);
		}
	}

	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		titles = getResources().getStringArray(R.array.titles);
		ArrayAdapter myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, titles);
		drawer = (ListView) findViewById(R.id.drawer);
		drawer.setAdapter(myAdapter);
		//pass in a list view listener, call backs are defined in inner class 
		drawer.setOnItemClickListener(new DrawerClickListener());
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.close_drawer, R.string.open_drawer){
			//when drawer is completely closed 
			@Override
			public void onDrawerClosed(View view){
				super.onDrawerClosed(view);
				invalidateOptionsMenu();
			}
			//when drawer is completely open 
			@Override
			public void onDrawerOpened(View drawerView){
				super.onDrawerOpened(drawerView);
				invalidateOptionsMenu();
			}
		};
		if (savedInstanceState == null){
			selectFragment(0);
			changeTitle(0);
		}
		else{
			changeTitle(savedInstanceState.getInt("position", 1));
		}
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		getActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
		drawerLayout.setDrawerListener(toggle);
		FragmentManager myManager = getFragmentManager();
		myManager.addOnBackStackChangedListener(
			new FragmentManager.OnBackStackChangedListener(){
				public void onBackStackChanged(){
					 FragmentManager frgmgr = getFragmentManager();
					 Fragment myFragment = frgmgr.findFragmentByTag("visible fragment");
					 if (myFragment instanceof MapFragment){
						 currentPosition = 0;
					 }
					 else {
						 currentPosition = 1;
					 }
					 changeTitle(currentPosition);
					 drawer.setItemChecked(currentPosition, true);
				}
			}
		);
		
		
        
    }
	
	//called when invalidateOptionsMenu() is called

	@Override
	public boolean onPrepareOptionsMenu(Menu menu)
	{
		boolean isOpen = drawerLayout.isDrawerOpen(drawer);
		menu.findItem(R.id.action_bar_share).setVisible(!isOpen);
		return super.onPrepareOptionsMenu(menu);
	}
	
	
	private void selectFragment(int position){
		currentPosition = position;
		Fragment newContent = new DeliveryListFragment();
		switch (position)
		{
			case 0:
				MapFragment map = MapFragment.newInstance();
				map.getMapAsync(this);
				newContent = map;
				break;
			case 1:
				//past deliveries 
				newContent = new DeliveryListFragment();
				break;
			

		}
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
		transaction.replace(R.id.content, newContent, "visible fragment");
		transaction.addToBackStack(null);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		transaction.commit();
		DrawerLayout myDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		//pass in the view that you are using for the drawer 
		myDrawer.closeDrawer(drawer);
		changeTitle(position);
//		if (position != 1){
//			map.getMapAsync(this);
//		}
		

	}

	
	private void changeTitle(int position){
		String title = getResources().getStringArray(R.array.titles)[position];
		//try to remember get actionbar method 
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(title);
	}
	
	private void changeTitle(String title){
		//try to remember get actionbar method 
		ActionBar actionBar = getActionBar();
		actionBar.setTitle(title);
	}
	
	
	class DrawerClickListener implements ListView.OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
		{
			selectFragment(p3);
		}

	}

	@Override
	public void onItemClick(long id)
	{
		DetailTeleport newFragment = new DetailTeleport();
		newFragment.setDeliveryID(id);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.content,newFragment);
		ft.addToBackStack(null);
		ft.commit();
	}

	@Override
	public void onMapReady(GoogleMap p1)
	{
		GoogleMap mMap = p1;
		LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
	}

	@Override
	public void onTeleportSelected(String title)
	{
		changeTitle(title);
	}

}
