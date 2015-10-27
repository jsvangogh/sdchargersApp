package com.example.joshua.livetogether;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Dash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dash);


    }

    public void add(View view)
    {
        Intent addIntent = new Intent(this, AddTask.class);
        startActivity(addIntent);
    }
}
