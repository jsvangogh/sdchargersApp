package com.example.joshua.livetogether;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * get or create an apartment for the user
 */
public class AddApartment extends AppCompatActivity {

    // UI references
    EditText apartmentNameView;

    // global variables
    private String mApartmentName = null;
    private String mUserID = null;
    private String mUserName = null;
    private String maptID = null;

    // context for creating a new activity from async task classes
    private Context mLoginThis;

    // async task references
    JoinApartment mJoinApartment;
    CreateApartment mCreateApartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment);

        // get apartment name text field
        apartmentNameView = (EditText)(findViewById(R.id.editText2));
        Intent intent = getIntent();

        // get user ID and Name from calling page
        mUserID = intent.getStringExtra("com.example.joshua.livetogether.userID");
        mUserName = intent.getStringExtra("com.example.joshua.livetogether.user");

        // get context
        mLoginThis = this;
    }

    /**
     * attempt to create or join an apartment
     */
    public void onClick(View view) {

        // only allow one task to be active
        if(mJoinApartment != null || mCreateApartment != null) {
            return;
        }

        // get apartment ApartmentName
        mApartmentName = apartmentNameView.getText().toString();

        // Check for a valid apartment name.
        if (TextUtils.isEmpty(mApartmentName)) {
            apartmentNameView.setError(getString(R.string.error_field_required));

            // put focus to apartment view
            apartmentNameView.requestFocus();
            return;
        }

        // check if user is creating or joining an apartment
        switch(view.getId()) {
            case R.id.createApt:
                // create and execute task to create apartment
                mCreateApartment = new CreateApartment(mApartmentName);
                mCreateApartment.execute((Void) null);
                break;
            case R.id.joinApt:
                // create and execute task to join apartment
                mJoinApartment = new JoinApartment(mApartmentName);
                mJoinApartment.execute((Void) null);
                break;
        }
    }

    /**
     * alert user that their selection does not exist
     */
    private String showInputDialog() {
        // build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.no_apartment_title);
        builder.setMessage(R.string.no_apartment_found);

        // set button
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddApartment.this, "Try again or create a new apartment", Toast.LENGTH_LONG).show();
            }
        });

        // show dialog
        builder.show();
        return mApartmentName;
    }

    /**
     * async task to join an apartment
     */
    class JoinApartment extends AsyncTask<Void, Void, Void> {
        private String mAptName = null;

        JoinApartment(String aptName) {
            mAptName = aptName;
        }

        @Override
        protected Void doInBackground(Void... v) {

            // get apartment ID
            maptID = ServerCom.setApartmentID(mUserID, mAptName);

            return null;
        }

        protected void onPostExecute(Void v) {

            // if apartment exists move to dash
            if (maptID != null) {
                Intent dashIntent = new Intent(mLoginThis, Dash.class);
                dashIntent.putExtra("com.example.joshua.livetogether.aptID", maptID);
                dashIntent.putExtra("com.example.joshua.livetogether.user", mUserName);
                startActivity(dashIntent);
                finish();
            }
            // alert user that apartment does not exist
            else {
                showInputDialog();
            }

            mJoinApartment = null;
        }
    }

    /**
     * async task to create an apartment
     */
    class CreateApartment extends AsyncTask<Void, Void, Void> {
        private String maptName = null;

        CreateApartment(String aptName) {
            maptName = aptName;
        }

        @Override
        protected Void doInBackground(Void... v) {

            // create apartment
            maptID = ServerCom.createApartment(mUserID, maptName);

            return null;
        }

        @Override
        protected void onPostExecute(Void v) {

            // if apartment was created, head to dash
            if (maptID != null) {
                Toast.makeText(AddApartment.this, "Apartment Created!", Toast.LENGTH_LONG).show();
                JoinApartment joinApartment = new JoinApartment(maptName);
                joinApartment.execute();
            }
            // alert user that apartment is taken
            else {
                Toast.makeText(AddApartment.this, "Apartment taken!", Toast.LENGTH_LONG).show();
            }

            mCreateApartment = null;
        }
    }
}
