package com.example.joshua.livetogether;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity{

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private ApartmentRetriever mApartmentRetriever = null;

    // UI references.
    private EditText mUserNameView; // username box
    private EditText mPasswordView; // password box

    // global strings
    private String mUserName; // user entered username
    private String mPassword; // user entered password
    private String mUserID; // User ID from server
    private String mAptID; // Apartment ID from server

    // used to obtain context inside wrapped classes
    private Context mLoginThis; // this

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUserNameView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        // Get username and password from register page if available
        Intent intent = getIntent();
        mUserName = intent.getStringExtra("com.example.joshua.livetogether.user");
        mPassword = intent.getStringExtra("com.example.joshua.livetogether.password");
        if(mUserName != null) {
            mUserNameView.setText(mUserName);
        }
        if(mPassword != null) {
            mPasswordView.setText(mPassword);
        }

        // set sign in button on for password box

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // set on click listener for login button
        Button mEmailSignInButton = (Button) findViewById(R.id.sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        // set global context to be used to create intents
        mLoginThis = this;
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        mUserName = mUserNameView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(mUserName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }

        // missing field
        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            return;
        }
        // attempt to login
        else {
            // perform the user login attempt.
            mAuthTask = new UserLoginTask();
            mAuthTask.execute((Void) null);
        }
    }

    /**
     * Open the register activity
     */
    public void openRegister(View view)
    {
        // Store values at the time of the login attempt.
        mUserName = mUserNameView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        // move to register page and bring current entries for username and password
        Intent registerIntent = new Intent(mLoginThis, RegisterUser.class);
        registerIntent.putExtra("com.example.joshua.livetogether.user", mUserName);
        registerIntent.putExtra("com.example.joshua.livetogether.password", mPassword);
        startActivity(registerIntent);
        finish();
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            // attempt to log in
            mUserID = ServerCom.signIn(mUserName, mPassword);

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {
            // login task finished
            mAuthTask = null;

            // alert user of failure
            if(mUserID == null) {
                mPasswordView.setError(getString(R.string.error_unsuccessful_login));
                mUserNameView.setError(getString(R.string.error_unsuccessful_login));
                mPasswordView.requestFocus();
            }
            // attempt to retrieve apartment
            else {
                mApartmentRetriever = new ApartmentRetriever(mUserName);
                mApartmentRetriever.execute((Void) null);
            }
        }
    }

    /**
     * Represents an asynchronous task to get the apartment ID of the user
     */
    class ApartmentRetriever extends AsyncTask<Void, Void, Void> {
        private String curUser;
        Exception exception;

        ApartmentRetriever(String username)
        {
            curUser = username;
        }

        @Override
        protected Void doInBackground(Void... v) {

            // get apartment
            try {
                mAptID = ServerCom.getApartmentID(mUserID);
            } catch (Exception e) {
                this.exception = e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            // apartment found, enter dash
            if(mAptID != null) {
                Intent dashIntent = new Intent(mLoginThis, Dash.class);
                dashIntent.putExtra("com.example.joshua.livetogether.aptID", mAptID);
                dashIntent.putExtra("com.example.joshua.livetogether.user", curUser);
                startActivity(dashIntent);
                finish();
            }
            // need apartment, enter apartment register page
            else{
                Intent needApartment = new Intent(mLoginThis, AddApartment.class);
                needApartment.putExtra("com.example.joshua.livetogether.userID", mUserID);
                needApartment.putExtra("com.example.joshua.livetogether.user", curUser);
                startActivity(needApartment);
                finish();
            }
        }
    }
}

