package com.sdl.dart.itsretail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SetQuotesActivity extends AppCompatActivity {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mQuotesRef = mRootRef.child("quotes");

    DatabaseReference mStatusRef = mRootRef.child("status");
    DatabaseReference mRID1Ref;
    DatabaseReference mCommodityRef;
    DatabaseReference mQIDRef;
    DatabaseReference mPriceRef;
    DatabaseReference mQuantityRef;
    double previousPrice;
    int previousQuantity;
    Button button;
    EditText quantity, price;
    String quoteID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_quotes);
        quantity=findViewById(R.id.quantity);
        price=findViewById(R.id.priceText);
        final String commodity=getIntent().getStringExtra("commodity");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
            mRID1Ref=mStatusRef.child(uid);
        }
        mCommodityRef=mRID1Ref.child(commodity);
        if(mCommodityRef==null)
        {
            Log.d("xyzr22","mCommodityRef is null");

        }
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);
        button=findViewById(R.id.setButton);
        // db=new DatabaseManager();
        // s=db.getStatus("wheat");

            if (quoteID.equalsIgnoreCase("no")) {
                button.setText("Set");
            } else  {
                button.setText("Update");



            }





    }
    @Override
    protected void onStart(){
        super.onStart();
        mCommodityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("xyzr22","onDataChange executes");

                quoteID=dataSnapshot.getValue(String.class);
                Log.d("xyzr22","quoteID = "+quoteID);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    protected void setPrice(View v){
        double price2;
        int quantity2;

        Log.d("xyzr22","this is setPrice");
        mQIDRef = mQuotesRef.child(quoteID);

        Log.d("xyzr22", "quoteID = " + quoteID);
        mPriceRef=mQIDRef.child("price");
        mPriceRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                previousPrice=dataSnapshot.getValue(Double.class);
                Log.d("xyzr22",Double.toString(previousPrice));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mQuantityRef=mQIDRef.child("quantity");

        mQuantityRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                previousQuantity=dataSnapshot.getValue(Integer.class);
                Log.d("xyzr22",Integer.toString(previousQuantity));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        mQuantityRef=mQIDRef.child("quantity");
        price2=Double.parseDouble(price.getText().toString());
        quantity2=Integer.parseInt(quantity.getText().toString());
        mPriceRef.setValue(price2);
        mQuantityRef.setValue(quantity2);
        Toast.makeText(this, "Updated Quote : \n"+"Price = "+price2+"\nQuantity = "+quantity2,
                Toast.LENGTH_LONG).show();

    }

}
