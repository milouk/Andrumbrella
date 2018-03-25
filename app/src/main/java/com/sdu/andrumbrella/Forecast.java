package com.sdu.andrumbrella;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

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
        loadForecast();
    }

    public void loadForecast(){

    }


}
