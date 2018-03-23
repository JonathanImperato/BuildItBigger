package com.udacity.gradle.builditbigger;

import android.support.test.*;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;


import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class StringTest {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBackend() {
        if (android.support.test.BuildConfig.FLAVOR.equals("paid")) { //the free one has the ads view on jokebtn click

            Log.w("TEST", "STARTING TEST");
            onView(withId(R.id.jokebtn)).perform(click());
            //espresso automatically supports asynctask
            //more info https://developer.android.com/reference/android/support/test/espresso/IdlingResource.html
            onView(withId(R.id.textview_joke)).check(matches(not(withText(""))));
            Log.w("TEST", "FINISHING TEST");
        }
    }
}