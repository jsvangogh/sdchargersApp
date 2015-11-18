package com.example.joshua.livetogether;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

public class Dash extends AppCompatActivity {

    private String maptID = null;
    private TaskRetriever mTaskRetriever;
    private TextView mTasksView;
    ArrayList<Task> tasks = new ArrayList<Task>();
    ListView mTaskList;
    TaskAdapter mTaskAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // get the apartment ID from the login page
        Intent intent = getIntent();
        maptID = intent.getStringExtra("com.example.joshua.livetogether.aptID");

        mTaskList = (ListView) findViewById(R.id.listView);

        mTaskAdapter = new TaskAdapter(this, R.layout.list_item, tasks);
        mTaskList.setAdapter(mTaskAdapter);
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
        Task tempTasks[];
        Exception exception;

        @Override
        protected Void doInBackground(Void... v) {

            try {
                tempTasks = ServerCom.getTasks(maptID);
            } catch (Exception e) {
                this.exception = e;
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            mTaskAdapter.clear();

            if (tempTasks.length == 0) {
                mTaskAdapter.add(new Task("", "No Tasks"));
            }

            for (int i = 0; i < tempTasks.length; i++) {
                mTaskAdapter.add(tempTasks[i]);
            }

            mTaskRetriever = null;
        }
    }
}