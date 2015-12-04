package com.example.joshua.livetogether;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Adapter used to fill task list
 */
public class TaskAdapter extends ArrayAdapter<Task> {

    // declaring our ArrayList of items
    private ArrayList<Task> mTasks;

    public TaskAdapter(Context context, int textViewResourceId, ArrayList<Task> tasks) {
        super(context, textViewResourceId, tasks);
        this.mTasks = tasks;
    }

    /**
     * defines how each list item will look
     */
    public View getView(int position, View convertView, ViewGroup parent){

        // assign the view we are converting to a local variable
        View v = convertView;

        // first check to see if the view is null. if so, we have to inflate it.
        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }

		// get current task object
        Task i = mTasks.get(position);

        if (i != null) {

            // obtain textViews
            TextView tt = (TextView) v.findViewById(R.id.taskDescription);
            TextView ttd = (TextView) v.findViewById(R.id.assignee);

            // check to see if each individual textview is null.
            // if not, assign some text!
            if (tt != null){
                tt.setText(i.getDescription());
            }
            if (ttd != null){
                ttd.setText(i.getAssignee());
            }
        }

        return v;

    }
}
