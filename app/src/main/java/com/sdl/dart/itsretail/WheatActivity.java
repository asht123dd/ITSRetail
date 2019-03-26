package com.sdl.dart.itsretail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WheatActivity extends AppCompatActivity {
    String btnText="";
   // DatabaseManager db;
    int s;
    Button button;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mStatusRef = mRootRef.child("status");
    DatabaseReference mRID1Ref = mStatusRef.child("RID1");
    DatabaseReference mWheatRef = mRID1Ref.child("wheat");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("xyzr22","before set successful!!");
        setContentView(R.layout.activity_wheat);
        Log.d("xyzr22","creation successfull, "+btnText);
        /*button=findViewById(R.id.button3);
       // db=new DatabaseManager();
       // s=db.getStatus("wheat");
        if(btnText.equalsIgnoreCase("no"))
        {
            button.setText("Give a quote");
        }
        else
            button.setText("Update quote");*/
    }
   /* @Override
    protected void onStart(){
        super.onStart();
        mWheatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                btnText=dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }*/
    public void getWheatQuotes(View v){
        Log.d("xyzr22","this is getWheatQuotes");
        Intent intent = new Intent(v.getContext(), AllQuotesActivity.class);
        intent.putExtra("commodity","wheat");
        startActivity(intent);
    }
    protected void setWheatQuote(View v){
        Log.d("xyzr22","this is setWheatQuotes");
        Intent intent = new Intent(v.getContext(), SetQuotesActivity.class);
        intent.putExtra("commodity","wheat");
        startActivity(intent);
    }
    public void getMyQuotes(View v){
        Log.d("xyzr22","this is getMyQuotes");
        Intent intent = new Intent(v.getContext(), MyQuotesActivity2.class);
        intent.putExtra("commodity","wheat");
        startActivity(intent);
    }
}
