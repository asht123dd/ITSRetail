package com.sdl.dart.itsretail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class RiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice);
        Spinner spinner = (Spinner) findViewById(R.id.rice_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.rice_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }
    public void getRiceQuotes(View v){
        Log.d("xyzr22","this is getRiceQuotes");
        Intent intent = new Intent(v.getContext(), AllQuotesActivityRice.class);
        intent.putExtra("commodity","rice"+type);
        startActivity(intent);
    }
    public void getMyQuotes(View v){
        Log.d("xyzr22","this is getMyQuotes");
        Intent intent = new Intent(v.getContext(), MyQuotesActivity2.class);
        intent.putExtra("commodity","rice"+type);
        startActivity(intent);
    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        type=parent.getItemAtPosition(pos).toString();
        Log.d("xyzr22","item selected = "+type);

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


}
