package com.sdl.dart.itsretail;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuotesTabbedRice extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    TabPagerAdapter adapter;
    int i=0, position;
    String RID;
    ArrayList<String> QID;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mQuotesCount=mRootRef.child("quotesCount");
    DatabaseReference mStatusRef=mRootRef.child("status");
    DatabaseReference mRIDRef;
    DatabaseReference mQuotesRef;
    DatabaseReference mNewQuoteRef;
    int quoteCount;
    boolean track;
    String newQuoteList,commodity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_tabbed_rice);
        track=true;
        mNewQuoteRef=mRootRef.child("quotes");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        commodity=getIntent().getStringExtra("commodity");
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
        if(mRIDRef!=null)
            mQuotesRef=mRIDRef.child(commodity);
        mQuotesCount.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                quoteCount = dataSnapshot.getValue(Integer.class);

                Log.d("xyzr22", "quoteCount = " + quoteCount);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tabLayout =
                (TabLayout) findViewById(R.id.tab_layout);

       /* tabLayout.addTab(tabLayout.newTab().setIcon(
                android.R.drawable.ic_dialog_email));
        tabLayout.addTab(tabLayout.newTab().setIcon(
                android.R.drawable.ic_dialog_dialer));
        tabLayout.addTab(tabLayout.newTab().setIcon(
                android.R.drawable.ic_dialog_map));
        tabLayout.addTab(tabLayout.newTab().setIcon(
                android.R.drawable.ic_dialog_info));
*/

        viewPager =
                (ViewPager) findViewById(R.id.pager);
        mRIDRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(commodity).getValue()==null)
                {
                    track=false;
                    firstPop();
                }
                while(!track);
                if(dataSnapshot.child(commodity).getValue()!=null) {
                    String[] preArr = dataSnapshot.child(commodity).getValue().toString().split(",");
                    StringBuilder postArr = new StringBuilder();
                    ArrayList<String> post = new ArrayList();
                    for (String a : preArr) {
                        if (!a.equals("null")&&!a.isEmpty()) {
                            post.add(a);
                            postArr.append("," + a);
                        }
                    }
                    mQuotesRef.setValue(postArr.toString());
                    QID = post;
                    populate();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        tabLayout.addOnTabSelectedListener(new
                                                   TabLayout.OnTabSelectedListener() {
                                                       @Override
                                                       public void onTabSelected(TabLayout.Tab tab) {
                                                           viewPager.setCurrentItem(tab.getPosition());
                                                           position=tab.getPosition();
                                                       }

                                                       @Override
                                                       public void onTabUnselected(TabLayout.Tab tab) {

                                                       }

                                                       @Override
                                                       public void onTabReselected(TabLayout.Tab tab) {

                                                       }

                                                   });
    }
    public void firstPop()
    {

        mQuotesCount.setValue(quoteCount + 1);

        // newQuote.put("QID" + (quoteCount + 1), new Quote(price, quantity, quality, commodity));

        mQuotesRef.setValue("QID"+(quoteCount+1));
        mNewQuoteRef.child("QID" + (quoteCount + 1)).child("price").setValue(0.00);
        mNewQuoteRef.child("QID" + (quoteCount + 1)).child("quantity").setValue(0);
        mNewQuoteRef.child("QID" + (quoteCount + 1)).child("quality").setValue(0);
        mNewQuoteRef.child("QID" + (quoteCount + 1)).child("commodity").setValue(commodity);
        mNewQuoteRef.child("QID" + (quoteCount + 1)).child("RID").setValue(RID);

        track=true;

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab_layout_demo, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void populate()
    {
        for(int j=0;j<QID.size();j++) {
            tabLayout.addTab(tabLayout.newTab().setText("Quote " + (i + 1)));
            i++;
        }

        adapter = new TabPagerAdapter
                (getSupportFragmentManager(),
                        tabLayout.getTabCount(),QID,commodity);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void fabulous(View v){
        Log.d("xyzr22","this is fabulous");
        if(i==5)
        {
            Toast.makeText(getApplicationContext(), "Sorry! No more useless tabs for you.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        tabLayout.addTab(tabLayout.newTab().setText("Quote "+(i+1)));
        i++;
        createTab();


    }
    public void createTab()
    {
        /*QID.add("QID"+(quoteCount+1));
        Map<String,Float> newQuote=new HashMap<>();


        Quote quote=new Quote(0.0,0,0);

        mNewQuoteRef=mQuotesRef.child("QID"+(quoteCount+1));
        mNewQuoteRef.child("price").setValue(0.0);
        mNewQuoteRef.child("quantity").setValue(0);
        mNewQuoteRef.child("quality").setValue(0);

        mQuotesCount.setValue(new Integer(quoteCount+1));
        Log.d("xyzr22","Updated quoteCount = "+(quoteCount+1));
        newQuoteList=android.text.TextUtils.join(",", QID);
        mQuotesRef.setValue(newQuoteList);*/

        adapter = new TabPagerAdapter
                (getSupportFragmentManager(),
                        tabLayout.getTabCount(),commodity);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new
                TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        TabLayout.Tab tab = tabLayout.getTabAt(i-1);
        tab.select();

    }
    public void delete(View v)
    {
        i--;
        removeTab(position);
    }
    public void removeTab(int position) {
        if (tabLayout.getTabCount() >= 1 && position<tabLayout.getTabCount()) {
            tabLayout.removeTabAt(position);
            adapter.removeTabPage(position);
        }
    }

}
