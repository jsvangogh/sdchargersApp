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

    final String apartmentID = "5661e5f9ea616c000a66163b";

    // runs before tests
    @Rule
    public final ActivityRule<Dash> login = new ActivityRule<>(Dash.class);

    @Before
    public void setUp() {
        Dash dash = login.get();
        Intent loginIntent = dash.getIntent();
        loginIntent.putExtra("com.example.joshua.livetogether.aptID", apartmentID);
        loginIntent.putExtra("com.example.joshua.livetogether.user", "test");

        dash.mAptID = apartmentID;
        dash.currentUser = "test";
    }

    @Test
    public void testAdd() {
        // click sign-in
        onView(withId(R.id.addButton)).perform(click());

        // check to see that we left the page
        onView(withId(R.id.addButton)).check(ViewAssertions.doesNotExist());
    }

    public void testRemove() {
        TaskAdder firstTask = new TaskAdder(apartmentID, "new task", 20, false);
        firstTask.execute();

        onView(allOf(withText("new task"))).check(ViewAssertions.matches(isDisplayed()));

        onView(allOf(withText("new task"))).perform(click());

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
