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

public class PotatoActivity extends AppCompatActivity {
    String btnText="";
    // DatabaseManager db;
    int s;
    Button button;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mStatusRef = mRootRef.child("status");
    DatabaseReference mRID1Ref = mStatusRef.child("RID1");
    DatabaseReference mPotatoRef = mRID1Ref.child("potato");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_potato);

        Log.d("xyzr22","creation successfull, "+btnText);

    }
    @Override
    protected void onStart(){
        super.onStart();
        mPotatoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                btnText=dataSnapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void getPotatoQuotes(View v){
        Log.d("xyzr22","this is getPotatoQuotes");
        Intent intent = new Intent(v.getContext(), AllQuotesActivity.class);
        intent.putExtra("commodity","potato");
        startActivity(intent);
    }
    protected void setPotatoQuote(View v){
        Log.d("xyzr22","this is setPotatoQuote");
        Intent intent = new Intent(v.getContext(), SetQuotesActivity.class);
        intent.putExtra("commodity","potato");
        startActivity(intent);
    }
    public void getMyQuotes(View v){
        Log.d("xyzr22","this is getMyQuotes");
        Intent intent = new Intent(v.getContext(), MyQuotesActivity2.class);
        intent.putExtra("commodity","potato");
        startActivity(intent);
    }
}
