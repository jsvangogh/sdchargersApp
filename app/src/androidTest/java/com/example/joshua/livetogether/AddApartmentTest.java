package com.example.joshua.livetogether;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by joshua on 12/4/15.
 */
@RunWith(AndroidJUnit4.class)
public class AddApartmentTest {
    // runs before tests
    @Rule
    public final ActivityRule<AddApartment> login = new ActivityRule<>(AddApartment.class);

    @Test
    public void testBlankJoin() {
        // click join button
        onView(withId(R.id.joinApt)).perform(click());

        // check to see that we did not leave the page
        onView(withId(R.id.enterApartment)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testBlankCreate() {
        // click create
        onView(withId(R.id.createApt)).perform(click());

        // check to see that we did not leave the page
        onView(withId(R.id.enterApartment)).check(ViewAssertions.matches(isDisplayed()));
    }
}
