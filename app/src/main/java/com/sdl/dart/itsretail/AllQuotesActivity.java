package com.sdl.dart.itsretail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class AllQuotesActivity extends AppCompatActivity {
/*                                              root(mRootRef)
                                                /
                                               /
                                            quotes(mQuotesRef)
                                         /   /   |
                                        /   /    |
                                       /   /     |
                                     QID1 QID2 QID3
                              /       /\      |     \
                             /       /  \     |      \
                            /       /    \    |       \
                     commodity     RID  price quality quantity

 */
    ArrayList<Float> price;
    float f;
    ArrayAdapter<String> adapter;
    int i, j, globalOrder=0;
    ArrayList<String> currencyPrice=new ArrayList<String>();

    ListView list;
    Button priceButton;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mQuotesRef=mRootRef.child("quotes");
    private ProgressDialog Dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_quotes);
        Dialog = new ProgressDialog(this);
        Dialog.setMessage("Loading...");
        Dialog.show();
        i=0;
        j=0;
        //store commodity name in commodity
        final String commodity=getIntent().getStringExtra("commodity");

        priceButton=findViewById(R.id.priceButton);
        //price stores a list of prices
        price = new ArrayList<>();
        list=findViewById(R.id.dynamicPrices);
        mQuotesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    if(snapshot.child("commodity").getValue(String.class).equalsIgnoreCase(commodity))//quotes with commodity=commodity
                    {
                        f = snapshot.child("price").getValue(Float.class);
                        prAdd(f);//add price to list
                       // Log.d("xyzr22", String.valueOf(price.get(0)));
                        // i++;
                     //   quantity.add(snapshot.child("quantity").getValue(Integer.class));
                        j++;
                    }
                   // TableRow row=new TableRow(this);
                }
                init();
                Dialog.hide();
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
    //add prices from quotes with passed commodity to price list
    public void prAdd(float f)
    {
        if(f>0.0)
        price.add(f);
    }

    public void init(){
//        TableLayout ll = (TableLayout) findViewById(R.id.tabLayout);
        Log.d("xyzr22","Does this execute");

        /*for (int i = 0; i <price.size(); i++) {

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
        }*/
        priceButton.setText("Price ▼");
        Collections.sort(price);
        for (int i = 0; i < price.size(); i++) {

            currencyPrice.add("₹" + Float.toString(price.get(i)));
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, currencyPrice);
        list.setAdapter(adapter);

    }
    public void change(View v){
        if(globalOrder==0) {
            globalOrder = 1;
            priceButton.setText("Price ▲");

            sort(globalOrder);
        }
        else{
            globalOrder=0;
            priceButton.setText("Price ▼");

            sort(globalOrder);
        }
    }
    public void sort(int order)
    {
        Log.d("xyzr22","sort called with order = "+order);

        if(order==0) {

            Collections.sort(price);
            currencyPrice.clear();
            for (int i = 0; i < price.size(); i++) {
                currencyPrice.add("₹" + Float.toString(price.get(i)));
            }
            adapter.notifyDataSetChanged();

        }
        else
        {
            Log.d("xyzr22","else executes");

            Collections.sort(price,Collections.<Float>reverseOrder());
            currencyPrice.clear();
            for (int i = 0; i < price.size(); i++) {
                currencyPrice.add("₹" + Float.toString(price.get(i)));
                Log.d("xyzr22","currencyPrice["+i+"]="+currencyPrice.get(i));

            }
            adapter.notifyDataSetChanged();
        }
    }

}
