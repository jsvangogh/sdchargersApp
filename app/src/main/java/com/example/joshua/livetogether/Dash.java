package com.example.joshua.livetogether;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

public class Dash extends AppCompatActivity {

    private String maptID = null;
    TaskRetriever mTaskRetriever;
    TextView mTasksView;
    GridView mTaskGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash);
        // get the apartment ID from the login page
        Intent intent = getIntent();
        maptID = intent.getStringExtra("com.example.joshua.livetogether.aptID");

        mTaskGrid = (GridView) findViewById(R.id.gridView);
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
        addIntent.putExtra("com.example.joshua.livetogether.aptID", maptID);
        startActivity(addIntent);
    }

    class TaskRetriever extends AsyncTask<Void, Void, Void> {
        String mtaskString[];
        String mText = "";
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
                mtaskString = new String[]{"Error Loading"};
            }

            for (int i = 0; i < mtaskString.length; i++) {
                mText += mtaskString[i];
                mText += "\n";
            }

            mTasksView.setText(mText);

            mTaskRetriever = null;
        }
    }
}