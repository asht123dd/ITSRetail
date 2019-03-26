package com.sdl.dart.itsretail;

import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Arrays;

public class Main2Activity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;
    private static final int RC_SIGN_IN = 123;
    private String TAG="xyzr22";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            // already signed in
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }else {
            AuthUI.IdpConfig phoneConfigWithDefaultNumber = new AuthUI.IdpConfig.PhoneBuilder()
                    .setDefaultCountryIso("in")
                    .build();
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(Arrays.asList(phoneConfigWithDefaultNumber)).setIsSmartLockEnabled(true).build(), RC_SIGN_IN);
        }
    }
    public void showSnackbar(int inp)
    {
        String message = getResources().getString(inp);
        Snackbar snackbar = Snackbar.make(coordinatorLayout,message,Snackbar.LENGTH_LONG);
        snackbar.show();

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            // Successfully signed in
            if (resultCode == RESULT_OK) {
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button
                   showSnackbar(R.string.sign_in_cancelled);
                    return;
                }

                if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                    showSnackbar(R.string.no_internet_connection);
                    return;
                }

                showSnackbar(R.string.unknown_error);
                Log.e(TAG, "Sign-in error: ", response.getError());
            }
        }
    }
}
