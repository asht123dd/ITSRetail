package com.sdl.dart.itsretail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddressActivity extends AppCompatActivity {
    private DatabaseReference mDatabase, mAddress, mUser;
    EditText address;
    String addressText;
    FirebaseUser currentFirebaseUser;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        address= findViewById(R.id.editTextAddress);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mAddress=mDatabase.child("Addresses");
        currentFirebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        mUser=mAddress.child(currentFirebaseUser.getUid());
    }
    public void next(View v)
    {
        addressText=address.getText().toString();
        if(addressText.isEmpty())
        {
            Toast toast=Toast.makeText(getApplicationContext(),"Please provide your shop's address",Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            mUser.setValue(addressText);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}
