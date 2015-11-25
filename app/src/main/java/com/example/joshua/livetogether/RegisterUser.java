package com.example.joshua.livetogether;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

public class RegisterUser extends AppCompatActivity {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    //private UserLoginTask mAuthTask = null;
    private RegisterTask mRegTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPhoneView;
    private View mProgressView;
    private View mLoginFormView;
    private String memail;
    private String mUserID;
    private String maptID;
    private String mPhoneNum;
    private String name = null;
    private User user;
    //private String mUser;
    private Context mLoginThis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email1);
        //populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password1);
        mPhoneView = (EditText) findViewById(R.id.number);
        /*mPhoneView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });*/
        //Intent intent = getIntent();
        //mUser = intent.getStringExtra("com.example.joshua.livetogether.user");
        /*Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });*/
        Button mRegisterButton = (Button) findViewById(R.id.Register1);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
        mLoginThis = this;
        mLoginFormView = findViewById(R.id.login_form1);
        mProgressView = findViewById(R.id.login_progress1);
    }

    /*private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }*/

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }*/


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    /*private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        //if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
        //    mPasswordView.setError(getString(R.string.error_invalid_password));
        //    focusView = mPasswordView;
        //    cancel = true;
        //}

        // Check for a valid email address.
        //if (TextUtils.isEmpty(email)) {
        //    mEmailView.setError(getString(R.string.error_field_required));
        //    focusView = mEmailView;
        //    cancel = true;
        //} else if (!isEmailValid(email)) {
        //    mEmailView.setError(getString(R.string.error_invalid_email));
        //    focusView = mEmailView;
        //    cancel = true;
        //}

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
            return;
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }*/

    public void attemptRegister()
    {
        if (mRegTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPhoneView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String phoneNumber = mPhoneView.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        //if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
        //    mPasswordView.setError(getString(R.string.error_invalid_password));
        //    focusView = mPasswordView;
        //    cancel = true;
        //}

        // Check for a valid email address.
        //if (TextUtils.isEmpty(email)) {
        //    mEmailView.setError(getString(R.string.error_field_required));
        //    focusView = mEmailView;
        //    cancel = true;
        //} else if (!isEmailValid(email)) {
        //    mEmailView.setError(getString(R.string.error_invalid_email));
        //    focusView = mEmailView;
        //    cancel = true;
        //}

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mRegTask = new RegisterTask(email, password, phoneNumber);
            mRegTask.execute((Void) null);
        }

        /*Intent dashIntent = new Intent(mLoginThis, RegisterUser.class);
        //dashIntent.putExtra("com.example.joshua.livetogether.aptID", maptID);
        //dashIntent.putExtra("com.example.joshua.livetogether.user", user);
        startActivity(dashIntent);
        finish();*/
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return true;
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > -1;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    /*@Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }*/

    /*@Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }*/

    /*@Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }*/

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(RegisterUser.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    private String showInputDialog() {
        //final EditText input = new EditText(this);
        String confirmationCode = "" + user.getConfirm();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.confirmation_code);
        builder.setMessage(confirmationCode);
        //builder.setView(input);

//        while(name == null) {
        builder.setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getBaseContext(), "Add apartment next", Toast.LENGTH_SHORT).show();
                //mApartmentRetriever = new ApartmentRetriever(name, mRegTask.mEmail);
                //mApartmentRetriever.execute((Void) null);
                Intent needApartment = new Intent(mLoginThis, AddApartment.class);
                needApartment.putExtra("com.example.joshua.livetogether.userID", user.getUID());
                needApartment.putExtra("com.example.joshua.livetogether.user", memail);
                startActivity(needApartment);
                finish();
            }
        });
//        }
        AlertDialog dialog = builder.show();
        TextView messageView = (TextView) dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
        messageView.setTextSize(40);

        return name;
    }
    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    /*public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                mUserID = ServerCom.signIn(mEmail, mPassword);
            } catch (Exception e) {
                return false;
            }

            if (mUserID == null) { return false; }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;

            if (!success) {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
                showProgress(false);
            }
            else{
                String aptName = null;
                mApartmentRetriever = new ApartmentRetriever(aptName, mEmail);
                mApartmentRetriever.execute((Void) null);
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }*/

    public class RegisterTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private final String mpNumber;

        RegisterTask(String email, String password, String pNumber) {
            mEmail = email;
            mPassword = password;
            mpNumber = pNumber;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                System.out.println(mEmail);
                System.out.println(mPassword);
                System.out.println(mpNumber);
                user = ServerCom.register(mEmail, mPassword, mpNumber);
                memail = mEmail;
                System.out.println(user);
            } catch (Exception e) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            //mAuthTask = null;
            showProgress(false);
            try {
                String userID = user.getUID();
                if (userID == null) {
                    mEmailView.setError(getString(R.string.error_taken_email));
                    mEmailView.requestFocus();
                } else {
                    showInputDialog();
                }
            }
            catch(NullPointerException e) {
                mEmailView.setError(getString(R.string.error_field));
                mEmailView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            //mAuthTask = null;
            showProgress(false);
        }
    }

    /*class ApartmentRetriever extends AsyncTask<Void, Void, Void> {
        private String maptName = null;
        private String curUser;
        Exception exception;

        ApartmentRetriever(String aptName, String username)
        {
            maptName = aptName;
            curUser = username;
        }

        @Override
        protected Void doInBackground(Void... v) {

            if(maptName == null) {
                try {
                    maptID = ServerCom.getApartmentID(mUserID);
                } catch (Exception e) {
                    this.exception = e;
                }
            }
            else{
                try {
                    maptID = ServerCom.setApartmentID(mUserID, maptName);
                } catch (Exception e) {
                    this.exception = e;
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void v)
        {
            showProgress(false);

            if(maptID != null) {
                Intent dashIntent = new Intent(mLoginThis, Dash.class);
                dashIntent.putExtra("com.example.joshua.livetogether.aptID", maptID);
                dashIntent.putExtra("com.example.joshua.livetogether.user", curUser);
                startActivity(dashIntent);
                finish();
            }
            else{
                Intent needApartment = new Intent(mLoginThis, AddApartment.class);
                needApartment.putExtra("com.example.joshua.livetogether.user", user);
                startActivity(needApartment);
                finish();
            }
        }

    }*/
}
