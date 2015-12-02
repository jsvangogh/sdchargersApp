package com.example.joshua.livetogether;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A screen to register new users
 */
public class RegisterUser extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private RegisterTask mRegTask = null;

    // UI references.
    private EditText mUserNameView;
    private EditText mPasswordView;
    private EditText mPhoneView;

    // global strings
    private String mUserName; // user entered username
    private String mPassword; // user entered password
    private String mPNumber; // user entered phone number

    // global user object
    private User user; // received from server after registration

    // used to obtain context inside wrapped classes
    private Context mLoginThis; // this


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        /**
         * Set up the login form.
         */

        // set view variables
        mUserNameView = (EditText) findViewById(R.id.username_register);
        mPasswordView = (EditText) findViewById(R.id.password_register);
        mPhoneView = (EditText) findViewById(R.id.number);

        // retrieve fields from sign-in page
        Intent intent = getIntent();
        mUserNameView.setText(intent.getStringExtra("com.example.joshua.livetogether.user"));
        mPasswordView.setText(intent.getStringExtra("com.example.joshua.livetogether.password"));

        // set onclicklisteners
        Button mRegisterButton = (Button) findViewById(R.id.Register1);
        Button mCancelButton = (Button) findViewById(R.id.back_to_login);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnToLogin();
            }
        });

        // get context
        mLoginThis = this;
    }

    /**
     * Attempt to register a new user
     */
    public void attemptRegister()
    {
        // only have one register task running at once
        if (mRegTask != null) {
            return;
        }

        // Reset errors.
        mUserNameView.setError(null);
        mPasswordView.setError(null);
        mPhoneView.setError(null);

        // Store values at the time of the login attempt.
        mUserName = mUserNameView.getText().toString();
        mPassword = mPasswordView.getText().toString();
        mPNumber = mPhoneView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid phone number
        if(mPNumber.length() != 10) {
            mPhoneView.setError(getString(R.string.phone_number_invalid));
            focusView = mPhoneView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(mPassword)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid userName
        if (TextUtils.isEmpty(mUserName)) {
            mUserNameView.setError(getString(R.string.error_field_required));
            focusView = mUserNameView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        }
        else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            mRegTask = new RegisterTask();
            mRegTask.execute((Void) null);
        }
    }

    /**
     * return to sign-in page
     */
    public void returnToLogin() {
        // Store values at the time of the login attempt.
        mUserName = mUserNameView.getText().toString();
        mPassword = mPasswordView.getText().toString();

        // move to register page and bring current entries for username and password
        Intent loginIntent = new Intent(mLoginThis, LoginActivity.class);
        loginIntent.putExtra("com.example.joshua.livetogether.user", mUserName);
        loginIntent.putExtra("com.example.joshua.livetogether.password", mPassword);
        startActivity(loginIntent);
        finish();
    }

    /**
     * alert user of confirmation code for twilio call
     */
    private void showInputDialog() {

        // create a dialog with registration code
        String confirmationCode = "" + user.getConfirm();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirmation_code);
        builder.setMessage(confirmationCode);

        // move to apartment add page
        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Add apartment next", Toast.LENGTH_SHORT).show();
                Intent needApartment = new Intent(mLoginThis, AddApartment.class);
                needApartment.putExtra("com.example.joshua.livetogether.userID", user.getUID());
                needApartment.putExtra("com.example.joshua.livetogether.user", mUserName);
                startActivity(needApartment);
                finish();
            }
        });

        // show the dialog box
        AlertDialog dialog = builder.show();
        TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        messageView.setTextSize(40);
    }

    /**
     * Task used to ask server to register a user
     */
    public class RegisterTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {

            user = ServerCom.register(mUserName, mPassword, mPNumber);

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {


            try {
                // phone number already registered
                if(user.getConfirm() == 0){
                    Intent needApartment = new Intent(mLoginThis, AddApartment.class);
                    needApartment.putExtra("com.example.joshua.livetogether.userID", user.getUID());
                    needApartment.putExtra("com.example.joshua.livetogether.user", mUserName);
                    startActivity(needApartment);
                    finish();
                }
                // need to register phone number
                else {
                    // show user confirmation number
                    showInputDialog();
                }
            }
            // if user is null than the name is taken
            catch(NullPointerException e) {
                mUserNameView.setError(getString(R.string.error_taken_username));
                mUserNameView.requestFocus();
                mRegTask = null;
            }
        }

        @Override
        protected void onCancelled() {
            // allow register to be called again
            mRegTask = null;
        }
    }
}
