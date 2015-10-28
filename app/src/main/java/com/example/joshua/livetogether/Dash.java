package com.example.joshua.livetogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class Dash extends AppCompatActivity {

    private String aptID = null;
    List<String> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash);

        Intent intent = getIntent();
        aptID = intent.getStringExtra("com.example.joshua.livetogether.aptID");
    }

    @Override
    protected void onResume()
    {
        tasks = ServerCom.getTasks(aptID);
    }

    public void add(View view)
    {
        Intent addIntent = new Intent(this, AddTask.class);
        addIntent.putExtra("com.example.joshua.livetogether.aptID", "56306f75129270000ac80798");
        startActivity(addIntent);
    }
}
