package com.sdu.andrumbrella;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sdu.andrumbrella.utilities.GeneralUtils;

public class Forecast extends AppCompatActivity {

    private RecyclerView forecastRecyclerView;
    private TextView forecast_error;
    private ForecastAdapter fAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);

        forecastRecyclerView = findViewById(R.id.rv_forecast);
        forecast_error = findViewById(R.id.forecast_error_message);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        forecastRecyclerView.setLayoutManager(layoutManager);
        fAdapter = new ForecastAdapter();
        forecastRecyclerView.setAdapter(fAdapter);
        Intent dayIndex = getIntent();
        loadForecast(dayIndex);
    }

    public void loadForecast(Intent dayIndex){
        forecast_error.setVisibility(View.INVISIBLE);
        forecastRecyclerView.setVisibility(View.VISIBLE);
        if(dayIndex.hasExtra(Intent.EXTRA_TEXT)) {
           fAdapter.setForecastData(GeneralUtils.getWeatherData(dayIndex.getStringExtra(Intent.EXTRA_TEXT)));
        }
    }


}
