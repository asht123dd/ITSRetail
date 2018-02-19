package com.sdl.dart.itsretail;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public int Rid;
    //DatabaseReference mRootRef= FirebaseDatabase.getInstance().getReference();
   // DatabaseReference mConditionRef=mRootRef.child("condition");
    JSONObject jArray = null;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

    protected void wheatCall(View view)
    {
        Log.d("xyzr22","this is wheatCall");
        Intent intent = new Intent(view.getContext(), WheatActivity.class);
        startActivity(intent);

    }
    protected void potatoCall(View view)
    {
        Log.d("xyzr22","this is potatoCall");
        Intent intent = new Intent(view.getContext(), PotatoActivity.class);
        startActivity(intent);

    }
}
