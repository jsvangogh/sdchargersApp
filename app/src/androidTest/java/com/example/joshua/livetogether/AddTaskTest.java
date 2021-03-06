package com.example.joshua.livetogether;

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

/**
 * Created by Alan on 12/4/2015.
 */
@RunWith(AndroidJUnit4.class)
public class AddTaskTest{

    final String apartmentID = "56627022be7b7a000977f41a";
    AddTask aTask;

    @Rule
    public final ActivityRule<AddTask> login = new ActivityRule<>(AddTask.class);

    @Before
    public void setUp() {
        aTask = login.get();
    }

    @Test
    public void testBlankCreate() {
        // click create
        onView(withId(R.id.button)).perform(click());

        // check to see that we did not leave the page
        onView(withId(R.id.editText)).check(ViewAssertions.matches(isDisplayed()));
    }
}
