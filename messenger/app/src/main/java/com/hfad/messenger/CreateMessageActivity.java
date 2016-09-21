package com.hfad.messenger;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateMessageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_message);
    }

    public void onSendMessage(View view){
        EditText editText = (EditText) findViewById(R.id.message);
        String userInput = editText.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, userInput);
        //andriod returns an explicit intent.
        Intent chosenIntent = Intent.createChooser(intent, getString(R.string.chooser_message));
        //if given a implicit intent to startActivity and no activity can be found, error will crash app.
        startActivity(chosenIntent);

    }
}
