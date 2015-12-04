package com.example.joshua.livetogether;

import android.support.test.runner.AndroidJUnit4;

import android.support.test.espresso.assertion.ViewAssertions;
import android.view.View;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;

import static android.support.test.espresso.action.ViewActions.typeTextIntoFocusedView;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Alan on 12/4/2015.
 */
public class RegisterUserTest {
    @Rule
    public final ActivityRule<RegisterUser> login = new ActivityRule<>(RegisterUser.class);

    @Test
    public void testRegister(){
        onView(withId(R.id.username_register)).perform(typeText("test1"));
        onView(withId(R.id.password_register)).perform(typeText("case"));
        onView(withId(R.id.number)).perform(typeText("8582078890"), closeSoftKeyboard());

        // click sign-in
        onView(withId(R.id.Register1)).perform(click());
        // check to see that we left the page
        onView(withId(R.id.Register1)).check(ViewAssertions.doesNotExist());
    }

    @Test
    public void testBlankSignIn() {
        // click sign-in
        onView(withId(R.id.Register1)).perform(click());

        // check to see that we stayed on the page
        onView(withId(R.id.email_login_form1)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void testCancel(){
        onView(withId(R.id.username_register)).perform(typeText("test1"));
        onView(withId(R.id.password_register)).perform(typeText("case"));
        onView(withId(R.id.number)).perform(typeText("8582078890"), closeSoftKeyboard());

        // click register button
        onView(withId(R.id.back_to_login)).perform(click());

        // check to see that we left the page and info moved
        onView(withId(R.id.username_register)).check(ViewAssertions.doesNotExist());
        onView(withText("Sign in")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("test")).check(ViewAssertions.matches(isDisplayed()));
        onView(withText("case")).check(ViewAssertions.matches(isDisplayed()));
    }
}
