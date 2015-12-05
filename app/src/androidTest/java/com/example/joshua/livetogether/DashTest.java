package com.example.joshua.livetogether;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@RunWith(AndroidJUnit4.class)
public class DashTest {

    // test apartment ID
    final String apartmentID = "5661e5f9ea616c000a66163b";
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
        dash.mAptID = apartmentID;
        dash.currentUser = "test";
        // click add button
        onView(withId(R.id.addButton)).perform(click());

        // check to see that we left the page
        onView(withId(R.id.addButton)).check(ViewAssertions.doesNotExist());
    }

    @Test
    public void testDisplayAndRemove() {
        dash.mAptID = apartmentID;
        dash.currentUser = "test";
        // add a task to the apartment
        TaskAdder firstTask = new TaskAdder(apartmentID, "new task", 20, false);
        firstTask.execute();

        // check if it showed up
        onView(allOf(withText("new task"))).check(ViewAssertions.matches(isDisplayed()));

        // click new task
        onView(allOf(withText("new task"))).perform(click());

        // assert that it disappeared
        onView(allOf(withText("new task"))).check(ViewAssertions.doesNotExist());
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
