package com.sdl.dart.itsretail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        button=findViewById(R.id.button4);
        // db=new DatabaseManager();
        // s=db.getStatus("wheat");
        if(btnText.equalsIgnoreCase("no"))
        {
            button.setText("Give a quote");
        }
        else
            button.setText("Update quote");
    }
}
