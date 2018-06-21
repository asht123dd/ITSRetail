package com.sdl.dart.itsretail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllQuotesActivity extends AppCompatActivity {
    TextView price,qty;
    ArrayList<Float> pr;
    float f;
    int i, j;
    ArrayList<Integer> quantity;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mQuotesRef=mRootRef.child("quotes");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_quotes);
        i=0;
        j=0;
        final String commodity=getIntent().getStringExtra("commodity");
        //Log.v("xyzr22",commodity);
        pr=new ArrayList<Float>();
        quantity=new ArrayList<>();
        mQuotesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                  //  Log.d("xyzr22",commodity);
                    if(snapshot.child("commodity").getValue(String.class).equalsIgnoreCase(commodity))
                    {
                        f = snapshot.child("price").getValue(Float.class);
                        prAdd(f);
                        Log.d("xyzr22", String.valueOf(pr.get(0)));
                        // i++;
                        quantity.add(snapshot.child("quantity").getValue(Integer.class));
                        j++;
                    }
                   // TableRow row=new TableRow(this);
                }
                init();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
       Log.d("xyzr22","Value before init");
        //init();

    }
    @Override
    protected void onStart()
    {
        super.onStart();
        /*mQuotesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            f=snapshot.child("price").getValue(Float.class);
                            prAdd(f);
                           Log.d("xyzr22",String.valueOf(pr.get(0)));
                           // i++;
                            quantity.add(snapshot.child("quantity").getValue(Integer.class));
                            j++;
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        Log.d("xyzr22","Value before init");
        init();*/
    }
    public void prAdd(float f)
    {
        pr.add(f);
    }

    public void init(){
        TableLayout ll = (TableLayout) findViewById(R.id.tabLayout);
        Log.d("xyzr22","Does this execute");

        for (int i = 0; i <pr.size(); i++) {

            TableRow row= new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);
            row.setWeightSum(8);

            price = new TextView(this);
            TableRow.LayoutParams param = new TableRow.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    4.0f
            );
           price.setLayoutParams(param);
            price.setGravity(Gravity.CENTER);
            qty = new TextView(this);
           qty.setLayoutParams(param);
            qty.setGravity(Gravity.CENTER);
            price.setText(String.format("%g",pr.get(i)));
            qty.setText(String.valueOf(quantity.get(i)));

            row.addView(price);
            row.addView(qty);
            ll.addView(row,i);
        }
    }
}
