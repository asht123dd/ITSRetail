package com.sdl.dart.itsretail;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sdl.dart.itsretail.MainActivity;
import com.sdl.dart.itsretail.R;

public class Login extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mail;
    TextView signup, forget;
    EditText pass;
    TextView llg;
    TextView login;
    ProgressDialog progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        FirebaseApp.initializeApp(this);
        mAuth=FirebaseAuth.getInstance();
        mail = (EditText) findViewById(R.id.user);
        forget = findViewById(R.id.forget);

        pass = (EditText)findViewById(R.id.pass);
        login = (TextView)findViewById(R.id.login);

        signup = (TextView)findViewById(R.id.signup);

        llg = (TextView)findViewById(R.id.llg);


        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, ForgetActivity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mail.getText().toString().trim();
                String password = pass.getText().toString().trim();

                //checking if email and passwords are empty
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Login.this,"Please enter email",Toast.LENGTH_LONG).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Login.this,"Please enter password",Toast.LENGTH_LONG).show();
                    return;
                }
                progress = new ProgressDialog(Login.this);
                progress.setMessage("Loading...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setCancelable(false);
                progress.show();

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Toast.makeText(Login.this, "Login ..." + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Toast.makeText(Login.this, "UserID or password is incorrect",
                                            Toast.LENGTH_SHORT).show();
                                    progress.dismiss();

                                }else{

                                    Intent m = new Intent(Login.this , MainActivity.class);
                                    progress.dismiss();
                                    finishAffinity();
                                    startActivity(m);
                                }

                                // ...
                            }
                        });
            }
        });
        signup.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {


                Intent it = new Intent(Login.this,Registration.class);
                startActivity(it);
            }
        });

    }
    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser!=null) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
    }

}