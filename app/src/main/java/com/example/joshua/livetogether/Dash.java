package com.example.joshua.livetogether;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Activity for displaying and interacting with existing tasks
 */
public class Dash extends AppCompatActivity {

    String mAptID; // apartment ID
    TaskRetriever mTaskRetriever; // retrieve tasks
    ArrayList<Task> tasks = new ArrayList<>(); // hold tasks
    ListView mTaskList; // Holds tasks
    TaskAdapter mTaskAdapter; // adapter for listview
    String currentUser; // name of current user
    Boolean mMyTasks; // false = alltasks, true = myTasks

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Toolbar setup
        setContentView(R.layout.activity_dash);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

        // get the apartment ID from the login page
        Intent intent = getIntent();
        mAptID = intent.getStringExtra("com.example.joshua.livetogether.aptID");
        currentUser = intent.getStringExtra("com.example.joshua.livetogether.user");

        // start by showing all tasks
        mMyTasks = false;

        // set up listview and its adapter
        mTaskList = (ListView) findViewById(R.id.taskListView);
        mTaskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task task = (Task) mTaskList.getItemAtPosition(position);
                if (task.getAssignee().equals(currentUser)) {
                    TaskRemover mTaskRemover = new TaskRemover(task);
                    mTaskRemover.execute();
                }
            }
        });

        findViewById(R.id.my_tasks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myTasks();
            }
        });

        findViewById(R.id.show_tasks).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allTasks();
            }
        });

        // set adapter for the task list
        mTaskAdapter = new TaskAdapter(this, R.layout.list_item, tasks);
        mTaskList.setAdapter(mTaskAdapter);
    }

    /**
     * Update the task list on resume
     */
    @Override
    protected void onResume() {
        super.onResume();

        // Don't retrieve tasks if it's still running
        if (mTaskRetriever != null) {
            return;
        }

        // get tasks
        mTaskRetriever = new TaskRetriever();
        mTaskRetriever.execute((Void) null);
    }

    /**
     * Move to add task page
     */
    public void add(View view) {
        Intent addIntent = new Intent(this, AddTask.class);
        addIntent.putExtra("com.example.joshua.livetogether.aptID", mAptID);
        startActivity(addIntent);
    }

    /**
     * Show all tasks
     */
    public void allTasks() {
        mMyTasks = false;
        onResume();
    }

    /**
     * Show only my tasks
     */
    public void myTasks() {
        mMyTasks = true;
        onResume();
    }

    /**
     * Retrieve tasks from server and appropriately fill listview
     */
    class TaskRetriever extends AsyncTask<Void, Void, Void> {
        Task tempTasks[]; // holds tasks
        Exception exception;

        @Override
        protected Void doInBackground(Void... v) {

            // get tasks
            try {
                tempTasks = ServerCom.getTasks(mAptID);
            } catch (Exception e) {
                this.exception = e;
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            mTaskAdapter.clear();
            int numOfTasks = 0;

            if(tempTasks != null) {
                numOfTasks = tempTasks.length;
                System.err.println("Task Retrieval Error");
            }

            boolean empty = numOfTasks == 0;

            // apartment has no tasks
            if (empty) {
                mTaskAdapter.add(new Task("", "No Tasks"));
                mTaskRetriever = null;
                return;
            }

            // fill task lists
            for (int i = 0; i < tempTasks.length; i++) {
                if (mMyTasks && tempTasks[i].getAssignee().equals(currentUser)) {
                    mTaskAdapter.add(tempTasks[i]);
                } else if (mMyTasks == false) {
                    mTaskAdapter.add(tempTasks[i]);
                }
            }

            mTaskRetriever = null;
        }
    }

    /**
     * Used to complete tasks, tasks may be readded if they are repeating
     */
    class TaskRemover extends AsyncTask<Void, Void, Void> {
        Task tempTask; // task to remove
        Exception exception;
        boolean repeating = false; // re-add task

        TaskRemover(Task task) {
            tempTask = task;
        }

        @Override
        protected Void doInBackground(Void... v) {

            // tell server to remove task
            try {
                repeating = ServerCom.removeTask(mAptID, tempTask.getDescription(), tempTask.getAssignee());
            } catch (Exception e) {
                this.exception = e;
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            // let user know if task was re-added
            if(repeating) {
                Toast.makeText(Dash.this, "Task will be re-added!", Toast.LENGTH_LONG).show();
            }

            // reload task list
            mTaskRetriever = new TaskRetriever();
            mTaskRetriever.execute((Void) null);
        }
    }
}