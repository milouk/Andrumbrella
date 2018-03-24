package com.sdu.andrumbrella;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private WeatherAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView =  findViewById(R.id.weather_recyclerview);
        mErrorMessage =  findViewById(R.id.error_message);
        mProgressBar =  findViewById(R.id.loading_bar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter =  new WeatherAdapter();
        loadWeather();
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        }



    private void loadWeather(){
        showWeather();
        URL weatherUrl = NetworkCon.buildUrl("Patras");
        new WeatherTask().execute(weatherUrl);
    }

    public void showWeather(){
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);


    }

    public void showErrorMessage(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    public class WeatherTask extends AsyncTask<URL, Void, String[]> {


        @Override
        protected String[] doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherResults = null;
            try{
                weatherResults = NetworkCon.getResponseFromHttpUrl(weatherUrl);
                String[] parsedWeatherList = JsonUtils.getWeatherFromJson(weatherResults);
                return parsedWeatherList;
            }catch (Exception e){
                showErrorMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] parsedWeatherResults) {
            if(parsedWeatherResults != null && !parsedWeatherResults.equals("")){
                showWeather();
                HashSet<String> upcomingDays = new HashSet<>();
                for(int i = 0;i < parsedWeatherResults.length;i++) {
                    upcomingDays.add(parsedWeatherResults[i].split("\\s")[0]);
                }
                mAdapter.setWeatherData(upcomingDays.toArray(new String[upcomingDays.size()]));
            }else{
                showErrorMessage();
            }
        }
    }
}
