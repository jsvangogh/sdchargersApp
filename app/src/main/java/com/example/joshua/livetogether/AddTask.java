package com.example.joshua.livetogether;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class AddTask extends AppCompatActivity {

    private boolean notif = false;
    private String task_name = "";
    private String mAptID = "";
    private TaskAdder mTask;
    private Context mLoginThis;
    private boolean check_status;
    private int weight=10;
    private Button easyButton;
    private Button mediumButton;
    private Button hardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mLoginThis = this;
        Intent intent = getIntent();
        mAptID = intent.getStringExtra("com.example.joshua.livetogether.aptID");

        easyButton = (Button) findViewById(R.id.add_task_easy);
        mediumButton = (Button) findViewById(R.id.add_task_medium);
        hardButton = (Button) findViewById(R.id.add_task_hard);
    }

    public void setEasy(View v) {
        weight=10;
        easyButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
        mediumButton.setBackgroundColor(Color.parseColor("#EEEEEE"));
        hardButton.setBackgroundColor(Color.parseColor("#EEEEEE"));
    }

    public void setMedium(View v) {
        weight=20;
        easyButton.setBackgroundColor(Color.parseColor("#EEEEEE"));
        mediumButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
        hardButton.setBackgroundColor(Color.parseColor("#EEEEEE"));
    }

    public void setHard(View v) {
        weight=30;
        easyButton.setBackgroundColor(Color.parseColor("#EEEEEE"));
        mediumButton.setBackgroundColor(Color.parseColor("#EEEEEE"));
        hardButton.setBackgroundColor(Color.parseColor("#CCCCCC"));
    }

    public void submitForm(View view) {
        final EditText editTextName = (EditText)(findViewById(R.id.editText));
        final CheckBox checkBox = (CheckBox) (findViewById(R.id.add_task_frequency));

        check_status = checkBox.isChecked();

        task_name = editTextName.getText().toString();
        if(task_name.equals("")) {
            editTextName.setError(getString(R.string.error_field_required));
            editTextName.requestFocus();
        }
        else {
            mTask = new TaskAdder();
            mTask.execute((Void) null);
            finish();
        }
    }

    class TaskAdder extends AsyncTask<Void, Void, Void> {
        Exception exception;

        @Override
        protected Void doInBackground(Void... v) {

            try {
                ServerCom.addTask(mAptID,task_name,weight,check_status);
                //System.out.println(mtaskString);
            } catch (Exception e) {
                this.exception = e;
                return null;
            }

            return null;
        }

        protected void onPostExecute(Void v) {
            finish();
        }
    }

}
