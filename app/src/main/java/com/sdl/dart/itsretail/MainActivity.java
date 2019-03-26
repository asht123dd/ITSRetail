package com.sdl.dart.itsretail;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public int Rid;
    //DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
   // DatabaseReference mConditionRef=mRootRef.child("condition");
    JSONObject jArray = null;
    String text;
    private FirebaseAuth mAuth;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    boolean doubleBackToExitPressedOnce = false;
    private static final int RC_SIGN_IN = 123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

            // already signed in
            recyclerView =
                    (RecyclerView) findViewById(R.id.recycler_view);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerAdapter();
            recyclerView.setAdapter(adapter);


       /* Rid=1;
        Log.v("xyzr22","getJSONcalled");
        Runnable runnable = new Runnable() {
            public void run() {
                Message msg = handler.obtainMessage();
                Bundle bundle = new Bundle();
                SimpleDateFormat dateformat =
                        new SimpleDateFormat("HH:mm:ss MM/dd/yyyy",
                                Locale.US);
                String dateString =
                        dateformat.format(new Date());
                bundle.putString("myKey", dateString);
                msg.setData(bundle);
                handler.sendMessage(msg);
                try {
                    URL myURL = new URL("http://10.0.2.2:8085/JDBCandServlet/Hello");
                    jArray=DatabaseManager.getJSONfromURL(myURL);
                }catch (Exception e)
                {
                    Log.v("log_tag",e.toString());
                }
            }
        };
        Thread myThread = new Thread(runnable);
        myThread.start();*/




    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {
            mAuth.signOut();
            Toast.makeText(MainActivity.this, "Redirecting to login...", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(MainActivity.this,Login.class);
            finishAffinity();
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
   /* @Override
    protected void onStart(){
        super.onStart();
        mConditionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                text=dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/

    public void wheatCall(View view)
    {
        Log.d("xyzr22","this is wheatCall");
        Intent intent = new Intent(view.getContext(), WheatActivity.class);
        startActivity(intent);

    }
    public void potatoCall(View view)
    {
        Log.d("xyzr22","this is potatoCall");
        Intent intent = new Intent(view.getContext(), PotatoActivity.class);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            this.finishAffinity();
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Touch BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
