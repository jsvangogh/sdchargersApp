package com.example.joshua.livetogether;

import android.support.test.runner.AndroidJUnit4;

import android.support.test.espresso.assertion.ViewAssertions;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;

import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

    // runs before tests
    @Rule
    public final ActivityRule<LoginActivity> login = new ActivityRule<>(LoginActivity.class);

    @Test
    public void testSignIn() {
        // enter info in text boxes
        onView(withId(R.id.email)).perform(typeText("test"));
        onView(withId(R.id.password)).perform(typeText("case"), closeSoftKeyboard());

        // click sign-in
        onView(withId(R.id.sign_in_button)).perform(click());

        // check to see that we left the page
        onView(withId(R.id.email_login_form)).check(ViewAssertions.doesNotExist());
    }

    @Test
    public void testBlankSignIn() {
        // click sign-in
        onView(withId(R.id.sign_in_button)).perform(click());

        // check to see that we left the page
        onView(withId(R.id.email_login_form)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testMoveToRegister() {
        // enter info in text boxes
        onView(withId(R.id.email)).perform(typeText("test"));
        onView(withId(R.id.password)).perform(typeText("case"), closeSoftKeyboard());

        // click register button
        onView(withId(R.id.Register)).perform(click());

        // check to see that we left the page and info moved
        onView(withText("Continue")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("test")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("case")).check(ViewAssertions.matches(isDisplayed()));
    }
}
