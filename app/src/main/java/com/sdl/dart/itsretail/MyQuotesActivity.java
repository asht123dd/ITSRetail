package com.sdl.dart.itsretail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyQuotesActivity extends AppCompatActivity {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mQuotesRef=mRootRef.child("quotes");
    float price;
    int quantity;
    String QID;
    TextView priceView, quantityView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("xyzr22","my quotes activity successful!!");
        setContentView(R.layout.activity_my_quotes);
       /* Log.d("xyzr22","creation successfull, "+btnText);
        button=findViewById(R.id.button3);
        // db=new DatabaseManager();
        // s=db.getStatus("wheat");
        if(btnText.equalsIgnoreCase("no"))
        {
            button.setText("Give a quote");
        }
        else
            button.setText("Update quote");*/
       priceView=findViewById(R.id.actualPrice);
        quantityView=findViewById(R.id.actualQuantity);

        final String commodity=getIntent().getStringExtra("commodity");

        mQuotesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //  Log.d("xyzr22",commodity);
                    if (snapshot.child("commodity").getValue(String.class).equalsIgnoreCase(commodity)) {
                        QID = snapshot.child("RID").getValue(String.class);
                        if (QID.equals("RID1")) {
                            price = snapshot.child("price").getValue(Float.class);
                            priceView.setText(Float.toString(price));
                            quantity = snapshot.child("quantity").getValue(Integer.class);
                            quantityView.setText(Float.toString(quantity));


                        }
                        // TableRow row=new TableRow(this);
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}

