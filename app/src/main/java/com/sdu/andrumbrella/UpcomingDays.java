package com.sdu.andrumbrella;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.sdu.andrumbrella.utilities.DateUtils;
import com.sdu.andrumbrella.utilities.JsonUtils;
import com.sdu.andrumbrella.utilities.NetworkUtils;

import java.net.URL;
import java.util.LinkedHashSet;

public class UpcomingDays extends AppCompatActivity implements UpcomingDaysAdapter.DayClickListener {

    private RecyclerView mRecyclerView;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private UpcomingDaysAdapter mAdapter;
    private String countryCode;
    private String cityName;
    private boolean metric;
    public static String[] weatherData;
    public Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_days);

        mRecyclerView = findViewById(R.id.weather_recyclerview);
        mErrorMessage = findViewById(R.id.error_message);
        mProgressBar = findViewById(R.id.loading_bar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new UpcomingDaysAdapter(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

        //Divider decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        Intent searchIntent = getIntent();
        if (searchIntent.hasExtra("COUNTRY_CODE")) {
            countryCode = searchIntent.getStringExtra("COUNTRY_CODE");
        }
        if (searchIntent.hasExtra("CITY_NAME")) {
            cityName = searchIntent.getStringExtra("CITY_NAME");
        }
        if (searchIntent.hasExtra("SWITCH_STATE")) {
            metric = searchIntent.getBooleanExtra("SWITCH_STATE", metric);
        }
        loadDays();
    }


    @Override
    public void onDayClickListener(int clickedDay) {
        TextView clickedTextView = mRecyclerView.findViewHolderForAdapterPosition(clickedDay).itemView.findViewById(R.id.days);
        String clickedTextViewtoString = clickedTextView.getText().toString();
        Intent passForecast = new Intent(UpcomingDays.this, Forecast.class);
        passForecast.putExtra(Intent.EXTRA_TEXT, clickedTextViewtoString.split("\\s")[1]);
        passForecast.putExtra("SWITCH_STATE", metric);
        startActivity(passForecast);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.andrumbrella_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAdapter.removeItems();
        loadDays();
        return super.onOptionsItemSelected(item);
    }


    public void loadDays(){
        showWeather();
        URL weatherUrl = NetworkUtils.buildUrl(cityName, countryCode);
        mProgressBar.setVisibility(View.VISIBLE);
        new WeatherTask().execute(weatherUrl);
    }


    private void showWeather(){
        mErrorMessage.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);


    }

    private void showErrorMessage(){
        mRecyclerView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    public class WeatherTask extends AsyncTask<URL, Void, String[]> {


        @Override
        protected String[] doInBackground(URL... urls) {
            URL weatherUrl = urls[0];
            String weatherResults;
            try{
                weatherResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
                String[] parsedWeatherList = JsonUtils.getWeatherFromJson(weatherResults);
                return parsedWeatherList;
            }catch (Exception e){
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] parsedWeatherResults) {
            if(parsedWeatherResults != null && !parsedWeatherResults.equals("")){
                showWeather();
                LinkedHashSet<String> upcomingDays = new LinkedHashSet<>();
                for(int i = 0;i < parsedWeatherResults.length;i++) {
                    upcomingDays.add(DateUtils.getDayByName(parsedWeatherResults[i].split("\\s")[0]) + " " +

                         String.valueOf(DateUtils.getDayByNumber(parsedWeatherResults[i].split("\\s")[0])) + " "
                            + DateUtils.getMonthByName(parsedWeatherResults[i].split("\\s")[0], context));
                }
                weatherData = parsedWeatherResults;
                mProgressBar.setVisibility(View.INVISIBLE);
                mAdapter.setWeatherData(upcomingDays.toArray(new String[upcomingDays.size()]));
            }else{
                showErrorMessage();
            }
        }
    }
}
