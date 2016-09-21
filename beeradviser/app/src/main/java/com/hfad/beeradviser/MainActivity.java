package com.hfad.beeradviser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import com.hfad.beeradviser.BeerExpert;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onClickFindBeer(View view){
        Spinner color = (Spinner) findViewById(R.id.color);
        String choice = (String) (color.getSelectedItem());
        TextView brands = (TextView) findViewById(R.id.brands);
        String printString = new String();
        for (String reccomendation: BeerExpert.getBrands(choice)){
            printString += "\n" + reccomendation;
        }
        brands.setText(printString);
    }
}
