package com.example.joshua.livetogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Dash extends AppCompatActivity {

    private String aptID = null;
    ArrayList<String> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash);

        // get the apartment ID from the login page
        Intent intent = getIntent();
        aptID = intent.getStringExtra("com.example.joshua.livetogether.aptID");

        // initialize the task array list
        tasks = new ArrayList<>();
    }

    // Update the task list on resume
    @Override
    protected void onResume()
    {

        super.onResume();
        String taskList = ServerCom.getTasks(aptID);
        String[] tempTasks;
        if(taskList != null) {
            tempTasks = taskList.split("*");
        }
        else
        {
            tempTasks = new String[]{"FUUUDGE"};
        }
        tasks = new ArrayList<>();

        for(String a: tempTasks)
        {
            tasks.add(a);
        }

        Iterator<String> taskIterator = tasks.iterator();
        String s = "";

        for(int i = 0; i < tasks.size(); i++)
        {
            if(taskIterator.hasNext())
            {
                s += taskIterator.next();
                s += "\n";
            }
        }

        TextView tasksView = (TextView) findViewById(R.id.Tasks);
        tasksView.setText(s);
    }

    public void add(View view)
    {
        Intent addIntent = new Intent(this, AddTask.class);
        addIntent.putExtra("com.example.joshua.livetogether.aptID", "56306f75129270000ac80798");
        startActivity(addIntent);
    }
}
