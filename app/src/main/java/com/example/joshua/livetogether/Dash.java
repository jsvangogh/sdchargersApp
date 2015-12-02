package com.example.joshua.livetogether;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
//import android.widget.Toolbar;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;

public class Dash extends AppCompatActivity {

    private String maptID;
    private TaskRetriever mTaskRetriever;
    ArrayList<Task> tasks = new ArrayList<>();
    ListView mTaskList;
    TaskAdapter mTaskAdapter; // adapter for listview
    String currentUser; // name of current user
    Boolean mMyTasks; // false = alltasks, true = myTasks
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Toolbar setup
        setContentView(R.layout.activity_dash);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // get the apartment ID from the login page
        Intent intent = getIntent();
        maptID = intent.getStringExtra("com.example.joshua.livetogether.aptID");
        currentUser = intent.getStringExtra("com.example.joshua.livetogether.user");

        mMyTasks = false;

        // set up listview and its adapter
        mTaskList = (ListView) findViewById(R.id.listView);
        mTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) mTaskList.getItemAtPosition(position);
                if (task.getAssignee().equals(currentUser)) {
                    TaskRemover mTaskRemover = new TaskRemover(task, position);
                    mTaskRemover.execute();
                }
            }
        });
        mTaskAdapter = new TaskAdapter(this, R.layout.list_item, tasks);
        mTaskList.setAdapter(mTaskAdapter);
    }

    // Update the task list on resume
    @Override
    protected void onResume() {
        if (mTaskRetriever != null) {
            return;
        }

        super.onResume();

        mTaskRetriever = new TaskRetriever();
        mTaskRetriever.execute((Void) null);
    }

    public void add(View view) {
        Intent addIntent = new Intent(this, AddTask.class);
        addIntent.putExtra("com.example.joshua.livetogether.aptID", maptID);
        startActivity(addIntent);
    }

    public void allTasks(View view) {
        mMyTasks = false;
        onResume();
    }

    public void myTasks(View view) {
        mMyTasks = true;
        onResume();
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
            boolean empty = true;

            for (int i = 0; i < tempTasks.length; i++) {
                if (mMyTasks && tempTasks[i].getAssignee().equals(currentUser)) {
                    mTaskAdapter.add(tempTasks[i]);
                    empty = false;
                } else if (mMyTasks == false) {
                    mTaskAdapter.add(tempTasks[i]);
                    empty = false;
                }
            }

            if (empty) {
                mTaskAdapter.add(new Task("", "No Tasks"));
            }

            mTaskRetriever = null;
        }
    }

    class TaskRemover extends AsyncTask<Void, Void, Void> {
        Task tempTask;
        int listPosition;
        Exception exception;
        boolean remove = false;

        TaskRemover(Task task, int position) {
            tempTask = task;
            listPosition = position;
        }

        @Override
        protected Void doInBackground(Void... v) {

            try {
                remove = ServerCom.removeTask(maptID, tempTask.getDescription(), tempTask.getAssignee());
            } catch (Exception e) {
                this.exception = e;
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            if(remove) {
                Toast.makeText(Dash.this, "Task will be re-added!", Toast.LENGTH_LONG).show();
            }
            mTaskRetriever = new TaskRetriever();
            mTaskRetriever.execute((Void) null);
        }
    }
}