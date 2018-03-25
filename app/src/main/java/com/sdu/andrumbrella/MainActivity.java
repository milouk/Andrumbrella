package com.sdu.andrumbrella;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.sdu.andrumbrella.utilities.GeneralUtils;
import com.sdu.andrumbrella.utilities.JsonUtils;
import com.sdu.andrumbrella.utilities.NetworkUtils;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class MainActivity extends AppCompatActivity implements UpcomingDaysAdapter.DayClickListener {

    private RecyclerView mRecyclerView;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private UpcomingDaysAdapter mAdapter;
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView =  findViewById(R.id.weather_recyclerview);
        mErrorMessage =  findViewById(R.id.error_message);
        mProgressBar =  findViewById(R.id.loading_bar);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter =  new UpcomingDaysAdapter(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        loadDays();
        }


    @Override
    public void onDayClickListener(int clickedDay) {
        if(mToast != null){
            mToast.cancel();
        }
        String toastMessage = "item # " + String.valueOf(clickedDay);
        Log.d("TOAST_MESSAGE", toastMessage);
        mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);
        mToast.show();


    }

    private void loadDays(){
        showWeather();
        URL weatherUrl = NetworkUtils.buildUrl("Patras");
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
            String weatherResults;
            try{
                weatherResults = NetworkUtils.getResponseFromHttpUrl(weatherUrl);
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
                LinkedHashSet<String> upcomingDays = new LinkedHashSet<>();
                DateFormat dateFormat = new SimpleDateFormat("MM");
                Date date = new Date();
                for(int i = 0;i < parsedWeatherResults.length;i++) {
                    upcomingDays.add(GeneralUtils.getDayByName(parsedWeatherResults[i].split("\\s")[0]) + " " +
                         String.valueOf(GeneralUtils.getDayByNumber(parsedWeatherResults[i].split("\\s")[0])) + " "
                            + GeneralUtils.getMonth(dateFormat.format(date)));
                }
                mAdapter.setWeatherData(upcomingDays.toArray(new String[upcomingDays.size()]));
            }else{
                showErrorMessage();
            }
        }
    }
}
