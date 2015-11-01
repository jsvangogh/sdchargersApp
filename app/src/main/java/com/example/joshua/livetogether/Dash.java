package com.example.joshua.livetogether;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.LayoutInflater;

public class Dash extends AppCompatActivity {

    private String maptID = null;
    private String mUserID = null;
    TaskRetriever mTaskRetriever;
    ApartmentRetriever mApartmentRetriever;
    TextView mTasksView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userID;

        setContentView(R.layout.activity_dash);
        String aptName = null;
        // get the apartment ID from the login page
        Intent intent = getIntent();

        mUserID = intent.getStringExtra("com.example.joshua.livetogether.user");
        mApartmentRetriever = new ApartmentRetriever(aptName);
        mApartmentRetriever.execute((Void) null);

        while(maptID == null) {
            mApartmentRetriever = new ApartmentRetriever(aptName);
            mApartmentRetriever.execute((Void) null);
            aptName = showInputDialog();
        }

        mTasksView = (TextView) findViewById(R.id.Tasks);
    }

    // Update the task list on resume
    @Override
    protected void onResume() {
        if (mTaskRetriever != null) {return;}

        super.onResume();
        mTaskRetriever = new TaskRetriever();
        mTaskRetriever.execute((Void) null);
    }

    public void add(View view) {
        Intent addIntent = new Intent(this, AddTask.class);
        addIntent.putExtra("com.example.joshua.livetogether.aptID", "56306f75129270000ac80798");
        startActivity(addIntent);
    }

    public String showInputDialog() {
        String name = null;
        LayoutInflater layoutInflater = LayoutInflater.from(Dash.this);
        View view  = layoutInflater.inflate(R.layout.input_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(Dash.this);
        builder.setTitle("No Apartment!");
        builder.setMessage("No apartment associated with user! Please enter an apartment" +
                " name to create an apartmnet");
        builder.setView(view);
        final EditText input = (EditText) view.findViewById(R.id.editText10);
        //input.setInputType(InputType.TYPE_CLASS_TEXT);
        //builder.setView(input);

        while(name == null) {
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    name = input.getText().toString();
                }
            });
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        return name;
    }


    class TaskRetriever extends AsyncTask<Void, Void, Void> {
        String mtaskString[];
        String mText;
        Exception exception;

        @Override
        protected Void doInBackground(Void... v) {

            try {
                mtaskString = ServerCom.getTasks(maptID);
            } catch (Exception e) {
                this.exception = e;
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            if (mtaskString == null) {
                mtaskString = new String[]{"FUUUUUDGE"};
            }

            for (int i = 0; i < mtaskString.length; i++) {
                mText += mtaskString[i];
                mText += "\n";
            }

            mTasksView.setText(mText.replace( "\"", ""));

            mTaskRetriever = null;
        }
    }


    class ApartmentRetriever extends AsyncTask<Void, Void, Void> {
        private String maptName = null;
        //String mAptID;
        Exception exception;

        ApartmentRetriever(String aptName)
        {
            maptName = aptName;
        }

        @Override
        protected Void doInBackground(Void... v) {

            if(maptName == null) {
                try {
                    maptID = ServerCom.getAparmentID(mUserID);
                } catch (Exception e) {
                    this.exception = e;
                }
            }
            else{
                try {
                    ServerCom.setApartmentID(mUserID, maptName);
                    maptID = ServerCom.getApartmentID(mUserID);
                } catch (Exception e) {
                    this.exception = e;
                }
            }

            return null;
        }

    }

}