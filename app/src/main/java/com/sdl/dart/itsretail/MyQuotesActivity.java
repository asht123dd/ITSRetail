package com.sdl.dart.itsretail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyQuotesActivity extends AppCompatActivity {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mQuotesRef=mRootRef.child("quotes");
    DatabaseReference mQIDRef;
    DatabaseReference mPriceRef;
    DatabaseReference mQuantityRef;

    DatabaseReference mQualityRef;

    float price;
    int quantity;
    String QID, RID;
    EditText priceView, quantityView;
    RatingBar mRatingBar ;
    TextView mRatingScale ;
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
        priceView.setText("0.0");
        quantityView=findViewById(R.id.actualQuantity);
        quantityView.setText("0");
        mRatingBar= (RatingBar) findViewById(R.id.ratingBar);
        mRatingScale= (TextView) findViewById(R.id.actualQuality);
        mRatingScale.setText("");
        final String commodity=getIntent().getStringExtra("commodity");

        mQuotesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //  Log.d("xyzr22",commodity);

                    if (snapshot.child("commodity").getValue(String.class).equalsIgnoreCase(commodity)) {
                        RID = snapshot.child("RID").getValue(String.class);
                        if (RID.equals("RID1")) {
                            QID=snapshot.getKey();
                            Log.d("xyzr22","QID = "+QID);
                            mQIDRef=mQuotesRef.child(QID);
                            if(mQIDRef==null)
                            {
                                Log.d("xyzr22","mQIDRef is null");
                            }
                            initialize();
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
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                mQualityRef.setValue(ratingBar.getRating());
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Inedible");
                        break;
                    case 2:
                        mRatingScale.setText("Poor");
                        break;
                    case 3:
                        mRatingScale.setText("Average");
                        break;
                    case 4:
                        mRatingScale.setText("Good");
                        break;
                    case 5:
                        mRatingScale.setText("Excellent");
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });


    }
    public void initialize()
    {

            mPriceRef = mQIDRef.child("price");
            mQuantityRef = mQIDRef.child("quantity");
            mQualityRef = mQIDRef.child("quality");

            priceView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean focus) {
                    if (focus == false) {
                        mPriceRef.setValue(Float.parseFloat(priceView.getText().toString()));
                    }
                }
            });
            quantityView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean focus) {
                    if (focus == false) {
                        mQuantityRef.setValue(Float.parseFloat(quantityView.getText().toString()));
                    }
                }
            });

    }


}

