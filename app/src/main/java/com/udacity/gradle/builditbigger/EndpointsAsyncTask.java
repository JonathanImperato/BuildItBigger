package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.ji.displayjokesandroidlibrary.DisplayJokeActivity;
import com.ji.gradle.builditbigger.backend.jokeApi.JokeApi;

import java.io.IOException;

/**
 * Created by jonathanimperato on 23/03/18.
 */

public class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private JokeApi myApiService = null;
    private Context context;
    private TaskInterface listener;

    public interface TaskInterface {
        public void onTaskCompleted(String result);
    }

    public EndpointsAsyncTask(Context context) {
        this.context = context;
    }

    public EndpointsAsyncTask setListener(TaskInterface lis) {
        this.listener = lis;
        return this;
    }

    @Override
    protected String doInBackground(Context... contexts) {
        if (myApiService == null) {
            myApiService = buildApi();
        }

        context = contexts[0];
        try {
            return myApiService.getJoke().execute().getJokeText();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (this.listener != null)
            listener.onTaskCompleted(result);
        Intent jokeIntent = new Intent(context, DisplayJokeActivity.class);
        jokeIntent.putExtra(DisplayJokeActivity.INTENT_EXTRA_NAME, result);
        context.startActivity(jokeIntent);
    }


    public static JokeApi buildApi() { // Only do this once
        JokeApi myApiService;
        JokeApi.Builder builder = new JokeApi.Builder(AndroidHttp.newCompatibleTransport(),
                new AndroidJsonFactory(), null)
                .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
        myApiService = builder.build();

        return myApiService;


    }}