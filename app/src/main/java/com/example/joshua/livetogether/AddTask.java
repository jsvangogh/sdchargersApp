package com.example.joshua.livetogether;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;


public class AddTask extends AppCompatActivity {

    // global variables
    String mTaskName = "";
    String mAptID = "";
    TaskAdder mTask;
    boolean repeat;
    int weight=10;

    // UI references
    Button easyButton;
    Button mediumButton;
    Button hardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // get current apartment
        Intent intent = getIntent();
        mAptID = intent.getStringExtra("com.example.joshua.livetogether.aptID");

        // set button references
        easyButton = (Button) findViewById(R.id.add_task_easy);
        mediumButton = (Button) findViewById(R.id.add_task_medium);
        hardButton = (Button) findViewById(R.id.add_task_hard);
    }

    /**
     * prepare for easy task creation
     */
    public void setEasy(View v) {
        // change task weight
        weight=10;

        // alert user of difficulty
        easyButton.setTextColor(Color.parseColor("#b58900"));
        mediumButton.setTextColor(Color.parseColor("#111111"));
        hardButton.setTextColor(Color.parseColor("#111111"));
    }

    /**
     * prepare for medium task creation
     */
    public void setMedium(View v) {
        // change task weight
        weight=20;

        // alert user of difficulty
        easyButton.setTextColor(Color.parseColor("#b58900"));
        mediumButton.setTextColor(Color.parseColor("#b58900"));
        hardButton.setTextColor(Color.parseColor("#111111"));
    }

    /**
     * prepare for hard task creation
     */
    public void setHard(View v) {
        // change task weight
        weight=30;

        // alert user of difficulty
        easyButton.setTextColor(Color.parseColor("#b58900"));
        mediumButton.setTextColor(Color.parseColor("#b58900"));
        hardButton.setTextColor(Color.parseColor("#b58900"));
    }

    /**
     * create a new task for the apartment
     */
    public void submitForm(View view) {
        // get references for task description and repeat status
        final EditText editTextName = (EditText)(findViewById(R.id.editText));
        final CheckBox checkBox = (CheckBox) (findViewById(R.id.add_task_frequency));

        // check if repeating task
        repeat = checkBox.isChecked();

        // create a new task if there was input
        mTaskName = editTextName.getText().toString();
        if(mTaskName.equals("")) {
            editTextName.setError(getString(R.string.error_field_required));
            editTextName.requestFocus();
        }
        else {
            mTask = new TaskAdder();
            mTask.execute((Void) null);
            finish();
        }
    }

    /**
     * async task to create a new task
     */
    class TaskAdder extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... v) {

            // ask server to add task
            ServerCom.addTask(mAptID, mTaskName,weight, repeat);

            return null;
        }

        protected void onPostExecute(Void v) {
            // return to previous page
            finish();
        }
    }

}
