package com.mark.visitlorrha;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class TextInformationActivity extends AppCompatActivity {

    String filename = "";

    TextView textViewOnInformationTextActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_information);

        textViewOnInformationTextActivity = (TextView) findViewById(R.id.textViewOnInformationTextActivity);

        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if(b != null){
            filename = (String) b.get("filename"); // domincanabbey
        }


        filename = "textInformation" + filename;
        int resourceId = getResources().getIdentifier(filename, "string", getPackageName()); //"com.mark.sounddemoproject"

        textViewOnInformationTextActivity.setText(getString(resourceId));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // shows the up/back button in the action bar


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home: // shows the up/back button in the action bar
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}