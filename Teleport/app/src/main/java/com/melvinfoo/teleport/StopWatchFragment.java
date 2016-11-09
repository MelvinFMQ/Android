package com.melvinfoo.teleport;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.app.Fragment;
import android.widget.TextView;
import android.view.*;
import android.view.View.*;
import android.widget.*;

public class StopWatchFragment extends Fragment implements View.OnClickListener{
    private boolean isRunning = false;
    private int timeInSeconds = 0;
    

	@Override
	public void onClick(View p1)
	{
		switch (p1.getId()){
			case R.id.start:
				this.onClickStart();
				break;
			case R.id.stop:
				this.onClickStop();
				break;
			case R.id.reset:
				this.onClickReset();
				break;
				
		}
	}
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putBoolean("running", isRunning);
        savedInstanceState.putInt("seconds", timeInSeconds);
    }
	

	@Override
	public void onStart()
	{
		View rootView = getView();
		Button start = (Button) rootView.findViewById(R.id.start);
		start.setOnClickListener(this);
		Button stop = (Button) rootView.findViewById(R.id.stop);
		stop.setOnClickListener(this);
		Button reset = (Button) rootView.findViewById(R.id.reset);
		reset.setOnClickListener(this);
		super.onStart();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null){
            isRunning = savedInstanceState.getBoolean("running");
            timeInSeconds = savedInstanceState.getInt("seconds");
        }
	}

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// TODO: Implement this method
		
		View layout = inflater.inflate(R.layout.stopwatch_fragment, container, false);
		runTimer(layout);
		return layout;
	}
//	
//    @Override
//    public void onPause(){
//        super.onPause();
//        if (isRunning){
//            wasRunning = isRunning;
//        }
//        else{
//            wasRunning = false;
//        }
//        isRunning = false;
//    }
//    @Override
//    public void onResume(){
//        super.onResume();
//        isRunning = wasRunning;
//    }
    public void onClickStart(){
        isRunning = true;

    }
    public void onClickStop() {
        isRunning = false;
    }

    public void onClickReset(){
        isRunning = false;
        timeInSeconds = 0;
    }
  

    private void runTimer(View layout){
        final TextView time = (TextView) layout.findViewById(R.id.time_display);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
				@Override
				public void run() {
					int hours = timeInSeconds / 3600;
					int minutes = (timeInSeconds % 3600) / 60;
					int seconds = timeInSeconds % 60;
					String timeToDisplay = String.format("%d:%02d:%02d", hours, minutes, seconds);
					time.setText(timeToDisplay);
					//time.setText(seconds);
					if (isRunning){
						timeInSeconds ++;
					}
					handler.postDelayed(this, 1000);
				}
			});


    }

}
