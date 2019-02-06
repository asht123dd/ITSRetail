package com.sdl.dart.itsretail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;


public class Registration extends AppCompatActivity {

    EditText emailid;
    EditText pass;
    EditText mob,userName;

    Button signup;
//    UserDetails userDetails;
//    private DatabaseReference mDatabase;
    ProgressDialog progress;


    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Registration");

        Intent i= getIntent();
//        userDetails= (UserDetails) i.getSerializableExtra("userDetails");
        mAuth = FirebaseAuth.getInstance();

        pass = findViewById(R.id.pass);
        userName = findViewById(R.id.name);
        emailid = findViewById(R.id.email);

        mob = findViewById(R.id.contact);


        signup = findViewById(R.id.signup_reg);

//        mDatabase = FirebaseDatabase.getInstance().getReference();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailid.getText().toString().trim();
                String password = pass.getText().toString().trim();
                String contact= mob.getText().toString().trim();
                String userName2 = userName.getText().toString().trim();
                //checking if email and passwords are empty
                if(TextUtils.isEmpty(userName2)){
                    userName.setError("Please enter User Name");
                    return;
                }
                if(TextUtils.isEmpty(email)||!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailid.setError("Please enter valid email");
                    return;
                }
                if(TextUtils.isEmpty(contact)){
                    mob.setError("Please enter contact number");
                    return;
                }
                if(contact.length()!=10){
                    mob.setError("Enter correct contact number");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    pass.setError("Please enter password");
                    return;
                }
                if(password.length()<6){
                    pass.setError("Password length should be greater than 6 !");
                    return;
                }

//                userDetails.setContact_number(contact);
//                userDetails.setEmail_id(email);
                progress = new ProgressDialog(Registration.this);
                progress.setMessage("Loading...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                progress.show();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Registration.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Registration.this, "Authentication Failed",
                                            Toast.LENGTH_SHORT).show();
                                    progress.dismiss();

                                }
                                else{
                                    FirebaseUser user = mAuth.getCurrentUser();

//                                    userDetails.setEmployee_id(user.getUid());
                                    progress.dismiss();

                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(userName.getText().toString()).build();


                                    user.updateProfile(profileUpdates);
                                    sendVerificationEmail();
                                }

                                // ...
                            }
                        });
            }
        });




    }


    private void sendVerificationEmail()
    {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            Toast.makeText(Registration.this ,"We have sent a verification link to your personal email.", Toast.LENGTH_SHORT).show();


                            // after email is sent just logout the user and finish this activity
                            Intent i=new Intent(Registration.this , Login.class);
                            i.putExtra("contact" , mob.getText().toString().trim());
                            finish();
                            startActivity(i);
                        }
                        else
                        {
                            // email not sent, so display message and restart the activity or do whatever you wish to do

                            //restart this activity
                            overridePendingTransition(0, 0);
                            finish();
                            overridePendingTransition(0, 0);
                            startActivity(getIntent());

                        }
                    }
                });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
