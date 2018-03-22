package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.ji.displayjokesandroidlibrary.DisplayJokeActivity;
import com.ji.gradle.builditbigger.backend.jokeApi.JokeApi;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private InterstitialAd mInterstitialAd;
    public static ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AdView adView = findViewById(R.id.adView);
        if (BuildConfig.FLAVOR.equals("paid")) {
            adView.setVisibility(View.GONE);
        }

        spinner = (ProgressBar) findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        if (BuildConfig.FLAVOR.equals("free")) {
            mInterstitialAd.show();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    // Code to be executed when an ad finishes loading.
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    // Code to be executed when an ad request fails.
                }

                @Override
                public void onAdOpened() {
                    // Code to be executed when the ad is displayed.
                }

                @Override
                public void onAdLeftApplication() {
                    // Code to be executed when the user has left the app.
                }

                @Override
                public void onAdClosed() {
                    spinner.setVisibility(View.VISIBLE);
                    // Code to be executed when when the interstitial ad is closed.
                    new EndpointsAsyncTask().execute(MainActivity.this);
                }
            });
        } else {
            new EndpointsAsyncTask().execute(this);
        }
    }

    static class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
        private JokeApi myApiService = null;
        private Context context;
        public testCallback mCallback;

        public interface testCallback {
            public void onTaskCompleted(String result);
        }

        @Override
        protected String doInBackground(Context... contexts) {
            if (myApiService == null) {
                myApiService = buildGCE.buildApi();
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
            mCallback.onTaskCompleted(result);
            Intent jokeIntent = new Intent(context, DisplayJokeActivity.class);
            jokeIntent.putExtra(DisplayJokeActivity.INTENT_EXTRA_NAME, result);
            spinner.setVisibility(View.GONE);
            context.startActivity(jokeIntent);
        }
    }

    static class buildGCE {

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
        }

    }
}
