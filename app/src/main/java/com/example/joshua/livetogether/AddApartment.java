package com.example.joshua.livetogether;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddApartment extends AppCompatActivity {
    private String name = null;
    private String mUserID = null;
    private String maptID = null;
    private Context mLoginThis;
    JoinApartment mJoinApartment;
    CreateApartment mCreateApartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment);
        mLoginThis = this;
        Intent intent = getIntent();
        mUserID = intent.getStringExtra("com.example.joshua.livetogether.user");
    }

    public void onClick(View view) {
        final EditText editTextName = (EditText)(findViewById(R.id.editText2));
        name = editTextName.getText().toString();
        switch(view.getId()) {
            case R.id.button2:
                mCreateApartment = new CreateApartment(name);
                mCreateApartment.execute((Void) null);
                break;
            case R.id.button3:
                mJoinApartment = new JoinApartment(name);
                mJoinApartment.execute((Void) null);
                break;
        }
    }

    private String showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.no_apartment_title);
        builder.setMessage(R.string.no_apartment_found);

//        while(name == null) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddApartment.this, "Try again or create a new apartment", Toast.LENGTH_LONG).show();
            }
        });
//        }
        builder.show();
        return name;
    }

    private String showInputDialog2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.apartment_created_title);
        builder.setMessage(R.string.apartment_verification);

//        while(name == null) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddApartment.this, "Apartment Created!", Toast.LENGTH_LONG).show();
            }
        });
//        }
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
        return name;
    }

    class JoinApartment extends AsyncTask<Void, Void, Void> {
        private String maptName = null;
        String mText = "";
        Exception exception;

        JoinApartment(String aptName) {
            maptName = aptName;
        }

        @Override
        protected Void doInBackground(Void... v) {

            if(maptName == null) {
                try {
                    while (mUserID == null) { }
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

        protected void onPostExecute(Void v) {
            if(maptID != null) {
                Intent dashIntent = new Intent(mLoginThis, Dash.class);
                dashIntent.putExtra("com.example.joshua.livetogether.aptID", maptID);
                startActivity(dashIntent);
            }
            else{
                showInputDialog();
            }
        }
    }

    class CreateApartment extends AsyncTask<Void, Void, Void> {
        private String maptName = null;
        String mText = "";
        Exception exception;

        CreateApartment(String aptName) {
            maptName = aptName;
        }

        @Override
        protected Void doInBackground(Void... v) {

            if(maptName == null) {
                try {
                    while (mUserID == null) { }
                    maptID = ServerCom.getApartmentID(mUserID);
                } catch (Exception e) {
                    this.exception = e;
                }
            }
            else{
                try {
                    maptID = ServerCom.createApartment(mUserID, maptName);
                } catch (Exception e) {
                    this.exception = e;
                }
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            if(maptID != null) {
                Intent dashIntent = new Intent(mLoginThis, Dash.class);
                dashIntent.putExtra("com.example.joshua.livetogether.aptID", maptID);
                startActivity(dashIntent);
            }
            else{
                showInputDialog2();
            }
        }
    }
}
