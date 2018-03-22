package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;
import android.util.Log;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;



@RunWith(AndroidJUnit4.class)
@LargeTest
public class StringTest implements MainActivity.EndpointsAsyncTask.testCallback {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testBackend() {
        AsyncTask task = new MainActivity.EndpointsAsyncTask();
        task.execute(mActivityTestRule.getActivity());
        Log.w("TEST", "STARTING TEST");
    }

    @Override
    public void onTaskCompleted(String result) {
        Assert.assertFalse(TextUtils.isEmpty(result));
        Log.w("TEST", "TEST FINISHED " + result);
    }
}