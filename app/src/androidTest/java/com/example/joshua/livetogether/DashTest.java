package com.example.joshua.livetogether;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalTo;

@RunWith(AndroidJUnit4.class)
public class DashTest {

    // test apartment ID
    final String apartmentID = "56627048be7b7a000977f41b";

    Dash dash;

    // runs before tests
    @Rule
    public final ActivityRule<Dash> login = new ActivityRule<>(Dash.class);

    // try and add necessary data before running tests
    @Before
    public void setUp() {
        dash = login.get();
    }

    @Test
    public void testAdd() {

        // click add button
        onView(withId(R.id.addButton)).perform(click());

        // check to see that we left the page
        onView(withId(R.id.addButton)).check(ViewAssertions.doesNotExist());
    }

    @Test
    public void testRemove() {
        dash.mAptID = apartmentID;
        dash.currentUser = "test";
        Task task = new Task("test", "new task");

        // add a task to the apartment
        TaskAdder firstTask = new TaskAdder(apartmentID, "new task", 20, false);
        firstTask.execute();

        // click on new task
        //onView(allOf(withText("new task"))).perform(click());
        //onData(hasToString("new task")).perform(click());
        //onData(allOf(hasToString("new task"))).inAdapterView(withId(R.id.taskListView)).atPosition(0).perform(click());
        onData(anything()).inAdapterView(withId(R.id.taskListView))
                .onChildView(withId(R.id.taskDescription))
                .perform(click());

        // assert that it disappeared
        onData(equalTo(task)).inAdapterView(withId(R.id.taskListView))
                .onChildView(withId(R.id.taskDescription))
                .check(ViewAssertions.doesNotExist());
    }

    /**
     * async task to create a new task
     */
    class TaskAdder extends AsyncTask<Void, Void, Void> {

        String mAptID;
        String mTaskName;
        int weight;
        boolean repeat;

        public TaskAdder(String aptID, String taskName, int weight, boolean repeat) {
            mAptID = aptID;
            mTaskName = taskName;
            this.weight = weight;
            this.repeat = repeat;
        }

        @Override
        protected Void doInBackground(Void... v) {

            // ask server to add task
            ServerCom.addTask(mAptID, mTaskName,weight, repeat);

            return null;
        }
    }
}