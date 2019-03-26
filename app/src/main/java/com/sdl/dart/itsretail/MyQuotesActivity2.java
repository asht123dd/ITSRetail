package com.sdl.dart.itsretail;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyQuotesActivity2 extends AppCompatActivity {
    private DatabaseReference mDatabase, mStatus, mUser, mQuotes, mExactQuote, mQuotesCount;
    private String commodity, QID;
    private ProgressDialog dialog;
    EditText price, quantity;
    int quotesCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_quotes2);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mStatus=mDatabase.child("status");
        mQuotes=mDatabase.child("quotes");
        mQuotesCount=mDatabase.child("quotesCount");
        FirebaseUser currentFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        mUser=mStatus.child(currentFirebaseUser.getUid());
        Bundle extras=getIntent().getExtras();
        price=findViewById(R.id.priceText);
        quantity=findViewById(R.id.quantityText);
        if(extras!=null)
        {
            commodity=extras.getString("commodity");
        }
        mQuotesCount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quotesCount=dataSnapshot.getValue(Integer.class);
                dialog.hide();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        try {
            dialog.show();
            mUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChild(commodity)) {
                        mUser.child(commodity).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                QID = dataSnapshot.getValue(String.class);
                                mExactQuote = mQuotes.child(QID);
                                mExactQuote.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Quote quote = dataSnapshot.getValue(Quote.class);
                                        if(quote!=null)
                                        {
                                            Log.d("xyzr22","Quote not null, Price="+quote.getPrice());
                                        }
                                        else
                                        {
                                            Log.d("xyzr22","Quote null");
                                        }
                                        price.setText(Integer.toString(quote.getPrice()), TextView.BufferType.EDITABLE);
                                        quantity.setText(Double.toString(quote.getQuantity()), TextView.BufferType.EDITABLE);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                    else
                    {
                        Quote quote=new Quote(0.0,0,commodity);
                        mQuotes.child("QID"+quotesCount).setValue(quote);
                        mQuotesCount.setValue(quotesCount+1);
                        mUser.child(commodity).setValue("QID"+quotesCount);
                        QID="QID"+quotesCount;
                    }
                    dialog.hide();


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch(NullPointerException n)
        {
            Quote quote=new Quote(0.0,0,commodity);
            mQuotes.child("QID"+quotesCount).setValue(quote);
            mQuotesCount.setValue(quotesCount+1);
            mUser.child(commodity).setValue("QID"+quotesCount);
            QID="QID"+quotesCount;
        }
        mQuotesCount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                quotesCount=dataSnapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void save(View v)
    {
        int pri=Integer.parseInt(price.getText().toString());
        double quan=Double.parseDouble(quantity.getText().toString());
        Quote quote=new Quote(quan,pri,commodity);
        mExactQuote = mQuotes.child(QID);
        mExactQuote.setValue(quote);
        Toast.makeText(v.getContext(),"Save successful",Toast.LENGTH_SHORT).show();
    }
}
