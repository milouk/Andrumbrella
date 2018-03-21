package com.sdu.andrumbrella;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private WeatherAdapter mAdapter;
    public static TextView mTestNet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView =  findViewById(R.id.weather_recyclerview);
        mErrorMessage =  findViewById(R.id.error_message);
        mProgressBar =  findViewById(R.id.loading_bar);
        mTestNet = findViewById(R.id.test_net);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter =  new WeatherAdapter();
        mRecyclerView.setAdapter(mAdapter);
        loadWeather();
    }

    public void loadWeather(){
        mTestNet.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        URL weatherUrl = NetworkCon.buildUrl("Patras");
        new WeatherTask().execute(weatherUrl);
    }

    public class WeatherTask extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherResults = null;
            try{
                weatherResults = NetworkCon.getResponseFromHttpUrl(weatherUrl);
            }catch (IOException e){
                e.printStackTrace();
            }

            return weatherResults;
        }

        @Override
        protected void onPostExecute(String s) {
            if(s != null && !s.equals("")){
                mTestNet.setText(s);
            }
        }
    }


}
