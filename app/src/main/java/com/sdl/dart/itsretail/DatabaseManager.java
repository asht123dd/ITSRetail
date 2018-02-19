package com.sdl.dart.itsretail;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashutosh on 15/2/18.
 */

public class DatabaseManager {
    static int status;
    static String stat;
    DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference mStatusRef = mRootRef.child("status");
    DatabaseReference mRID1Ref = mStatusRef.child("RID1");
    DatabaseReference mWheatRef = mRID1Ref.child("wheat");
    DatabaseReference mPotatoRef = mRID1Ref.child("potato");

    protected int getStatus(String commodity) {
        //int status;

        if (commodity.equalsIgnoreCase("wheat")) {
            mWheatRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    stat = dataSnapshot.getValue(String.class);
                    if (stat.equalsIgnoreCase("no")) {
                        status = 0;
                    } else {
                        status = 1;
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        else
        {
            mPotatoRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    stat = dataSnapshot.getValue(String.class);
                    if (stat.equalsIgnoreCase("no")) {
                        status = 0;
                    } else {
                        status = 1;
                    }
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        return status;
    }
}


