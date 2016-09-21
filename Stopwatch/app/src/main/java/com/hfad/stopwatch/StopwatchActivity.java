package com.hfad.stopwatch;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class StopwatchActivity extends AppCompatActivity {
    private boolean isRunning = false;
    private int timeInSeconds = 0;
    private boolean wasRunning = false;

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putBoolean("running", isRunning);
        savedInstanceState.putInt("seconds", timeInSeconds);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if (savedInstanceState != null){
            isRunning = savedInstanceState.getBoolean("running");
            timeInSeconds = savedInstanceState.getInt("seconds");
        }
        runTimer();
    }
    @Override
    public void onPause(){
        super.onPause();
        if (isRunning){
            wasRunning = isRunning;
        }
        else{
            wasRunning = false;
        }
        isRunning = false;
    }
    @Override
    public void onResume(){
        super.onResume();
        isRunning = wasRunning;
    }
    public void onClickStart(View view){
        isRunning = true;

    }
    public void onClickStop(View view) {
        isRunning = false;
    }

    public void onClickReset(View view){
        isRunning = false;
        timeInSeconds = 0;
    }
    public void onClickLinear(View view){
        Intent linear = new Intent(this, LinearActivity.class);
        startActivity(linear);
    }
    public void onClickGrid(View view){
        Intent grid = new Intent(this, GridActivity.class);
        startActivity(grid);
    }

    private void runTimer(){

        final TextView time = (TextView) findViewById(R.id.time_display);
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
