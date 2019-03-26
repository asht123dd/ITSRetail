package com.sdl.dart.itsretail;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MyQuotesActivity extends Fragment {
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mQuotesRef=mRootRef.child("quotes");
    DatabaseReference mQIDRef;
    String QIDString;
    DatabaseReference mPriceRef;
    DatabaseReference mQuantityRef;
    DatabaseReference mQuotesCount=mRootRef.child("quotesCount");
    int quoteCount;
    Button save;
    DatabaseReference mQualityRef;
    double price;
    int quantity, quality;
    boolean freshTab=false;
    String QID, RID;
    EditText priceView, quantityView;
    RatingBar mRatingBar ;
    TextView mRatingScale ;
    String commodity;
    DatabaseReference mStatusRef=mRootRef.child("status");
    DatabaseReference mRIDRef;
    DatabaseReference mCommodityRef;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            QID= getArguments().getString("QID");
            RID=getArguments().getString("RID");
            commodity=getArguments().getString("commodity");
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
                mRIDRef=mStatusRef.child(uid);
            }
            mCommodityRef=mRIDRef.child(commodity);
            if(QID.equals("new"))
            {
                freshTab=true;
                Log.d("xyzr22","QIDRef null");
            }
            if(!freshTab) {
                mQIDRef = mQuotesRef.child(QID);
                if(mQIDRef!=null)
                {
                    Log.d("xyzr22","QIDRef not null for QID = "+mQIDRef.getKey());
                }
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("xyzr22","my quotes fragment creation");
        View view;
        view=inflater.inflate(R.layout.activity_my_quotes, container, false);
        save=view.findViewById(R.id.button4);

        save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // do something
                Log.d("xyzr22","this is save");
                Map<String,Quote> newQuote=new HashMap<>();
                if(freshTab) {
                    mQuotesCount.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            quoteCount = dataSnapshot.getValue(Integer.class);
                            addQuote();
                            Log.d("xyzr22", "quoteCount = " + quoteCount);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else
                {
                    price=Double.parseDouble(priceView.getText().toString());
                    Log.d("xyzr22","price after parsing = "+price);
                    quantity=Integer.parseInt(quantityView.getText().toString());
                    mQIDRef.child("price").setValue(price);
                    mQIDRef.child("quantity").setValue(quantity);
                    mQIDRef.child("quality").setValue(quality);

                }
                Toast.makeText(getActivity(),"Save successful!!", Toast.LENGTH_SHORT).show();
            }

        });
       // setContentView(R.layout.activity_my_quotes);
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
        priceView=view.findViewById(R.id.actualPrice);
        priceView.setText("0.0");
        quantityView=view.findViewById(R.id.actualQuantity);
        quantityView.setText("0");
        mRatingBar= (RatingBar) view.findViewById(R.id.ratingBar);
        mRatingScale= (TextView) view.findViewById(R.id.actualQuality);
        mRatingScale.setText("");
     //   final String commodity=getIntent().getStringExtra("commodity");
    if(!freshTab) {
        mQIDRef.addListenerForSingleValueEvent(new ValueEventListener() {

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            Log.d("xyzr22","QIDRef listener");
           initialize();
                mPriceRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("xyzr22","mPriceRef listener");
                        try {
                            price = dataSnapshot.getValue(Double.class);
                        }catch(NullPointerException n)
                        {
                            price=0;
                        }
                        Log.d("xyzr22","price from firebase"+price);
                        priceView.setText(Double.toString(price));

                        //price=dataSnapshot.getValue(Double.class);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                mQuantityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        quantity = dataSnapshot.getValue(Integer.class);
                        quantityView.setText(Integer.toString(quantity));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                if(mQualityRef!=null)
                {
                    mQualityRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                quality=dataSnapshot.getValue(Integer.class);
                            mRatingBar.setRating(quality);
                            //quantityView.setText(Integer.toString(quantity));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else
                {
                    Log.d("xyzr22","mQualityref null");
                    quality=0;
                    mRatingBar.setRating(quality);
                }


           }


        @Override
        public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
       /* mQuotesRef.addListenerForSingleValueEvent(new ValueEventListener() {
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
        });*/else {
        priceView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus == false) {
                    price = Double.parseDouble(priceView.getText().toString());
                }
            }
        });
        quantityView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean focus) {
                if (focus == false && !quantityView.getText().toString().isEmpty()) {
                    quantity = Integer.parseInt(quantityView.getText().toString());
                }
            }
        });
    }
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                quality=(int)ratingBar.getRating();
//                mQualityRef.setValue(ratingBar.getRating());
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

        return view;
    }
    public void addQuote()
    {
        mQuotesCount.setValue(quoteCount + 1);


        price=Double.parseDouble(priceView.getText().toString());
        quantity=Integer.parseInt(quantityView.getText().toString());

        mQuotesRef.child("QID" + (quoteCount + 1)).child("price").setValue(price);
        mQuotesRef.child("QID" + (quoteCount + 1)).child("quantity").setValue(quantity);
        mQuotesRef.child("QID" + (quoteCount + 1)).child("quality").setValue(quality);
        mQuotesRef.child("QID" + (quoteCount + 1)).child("commodity").setValue(commodity);
        mQuotesRef.child("QID" + (quoteCount + 1)).child("RID").setValue(RID);
        mQIDRef=mQuotesRef.child("QID" + (quoteCount + 1));
        freshTab=false;

        mCommodityRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
              QIDString=dataSnapshot.getValue(String.class);
                QIDString+=",QID"+(quoteCount + 1);
                mCommodityRef.setValue(QIDString);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
                        price=Double.parseDouble(priceView.getText().toString());
                    }
                }
            });
            quantityView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean focus) {
                    if (focus == false) {
                        quantity=Integer.parseInt(quantityView.getText().toString());
                    }
                }
            });

    }

public void remove()
{
    if(mQIDRef==null)
    {
        Log.d("xyzr22","mQIDRef is null");
    }
    else {
        Log.d("xyzr22", "mQIDRef is not null");

        final String quoteID = mQIDRef.getKey();
        Log.d("xyzr22","QuoteID being deleted = "+quoteID);
        mQIDRef.removeValue();

        String newString = QIDString.replace("," + quoteID, "");
        mCommodityRef.setValue(newString);
    }
    }

}

