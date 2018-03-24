package com.sdu.andrumbrella;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael on 19-Mar-18.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    private String[] upcomingDays;

    public void setWeatherData(String[] day){
        upcomingDays = day;
        notifyDataSetChanged();
    }



    public static class WeatherViewHolder extends RecyclerView.ViewHolder{

        final private TextView mWeather;

        public WeatherViewHolder(View view){
            super(view);
            mWeather = view.findViewById(R.id.weather);
        }
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForListDays = R.layout.weather_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutForListDays, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder holder, int position) {
        String mWeatherData = upcomingDays[position];
        holder.mWeather.setText(mWeatherData);
    }

    @Override
    public int getItemCount() {
        if(upcomingDays == null) {
            return 0;
        }else{
            return upcomingDays.length;
        }
    }
}
