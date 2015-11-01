package com.example.joshua.livetogether;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.view.KeyEvent;

import java.util.ArrayList;
import java.util.Iterator;

public class AddTask extends AppCompatActivity {

    private boolean notif = false;
    private String task = "";
    private String mAptID = "";
    private TaskAdder mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        mAptID = intent.getStringExtra("com.example.joshua.livetogether.aptID");

    }

    public void submitForm(View view) {
        final EditText editTextName = (EditText)(findViewById(R.id.editText));

        task = editTextName.getText().toString();

        mTask = new TaskAdder();
        mTask.execute((Void) null);
        finish();
    }

    class TaskAdder extends AsyncTask<Void, Void, Void> {
        //String maptID;
        //String mtaskString;
        Exception exception;



        @Override
        protected Void doInBackground(Void... v) {

            try {
                ServerCom.addTask(mAptID, task);
                //System.out.println(mtaskString);
            } catch (Exception e) {
                this.exception = e;
                return null;
            }

            return null;
        }

        protected void onPostExecute(Void... v) {
            finish();
        }
    }

}
