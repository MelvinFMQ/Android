package com.melvinfoo.userinterface;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
    @Override
    public void onResume(){
        super.onResume();
        Toast toast = Toast.makeText(this, "Welcome to melvin's app", Toast.LENGTH_SHORT);
        toast.show();
    }
    public void onClickToggle(View view){
            boolean on = ((ToggleButton) view).isChecked();
            TextView toggle_text = (TextView) findViewById(R.id.toggle_status);

            if(on){
                toggle_text.setText("Toggle is on");
            }
            else {
                toggle_text.setText("Toggle is not on");
            }
    }

    public void onClickSwitch(View view){
        boolean on = ((Switch) view).isChecked();
        TextView switch_status = (TextView) findViewById(R.id.switch_status);
        if (on){
            switch_status.setText("Swictch is on");
        }
        else{
            switch_status.setText("Switch is off");
        }
    }

    public void onClickCheck(View view){
        boolean checkBox = ((CheckBox) view).isChecked();
        switch (view.getId()){
            case R.id.hi:
                if (checkBox){
                    ((TextView) findViewById(R.id.check_status_hi)).setText("Checkbox foo is checked!");
                }
                else{
                    ((TextView) findViewById(R.id.check_status_hi)).setText("Checkbox foo is not checked!");

                }
                break;
            case R.id.yo:
                if (checkBox){
                    ((TextView) findViewById(R.id.check_status_yo)).setText("Checkbox foo is checked!");
                }
                else{
                    ((TextView) findViewById(R.id.check_status_yo)).setText("Checkbox foo is not checked!");

                }
                break;
        }
    }
    public void onClickRadio(View view){
        RadioGroup group = (RadioGroup) findViewById(R.id.radio_group);
        int id = group.getCheckedRadioButtonId();
        TextView radio_status = (TextView) findViewById(R.id.radio_status);
        switch (id){
            case R.id.option_1:
                radio_status.setText("Option 1 is selected");
                break;
            case R.id.option_2:
                radio_status.setText("Option 2 is selected");

        }
    }

}
