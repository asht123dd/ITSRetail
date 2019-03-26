package com.sdl.dart.itsretail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class OnionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onion);

    }
    public void getOnionQuotes(View v){
        Log.d("xyzr22","this is getRiceQuotes");
        Intent intent = new Intent(v.getContext(), AllQuotesActivity.class);
        intent.putExtra("commodity","onion");
        startActivity(intent);
    }
    public void getMyQuotes(View v){
        Log.d("xyzr22","this is getMyQuotes");
        Intent intent = new Intent(v.getContext(), MyQuotesActivity2.class);
        intent.putExtra("commodity","onion");
        startActivity(intent);
    }
}
