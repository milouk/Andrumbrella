package com.sdu.andrumbrella;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Michael on 19-Mar-18.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder> {

    String[] array = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};


    public static class WeatherViewHolder extends RecyclerView.ViewHolder{

        final private TextView mWeather;

        public WeatherViewHolder(View view){
            super(view);
            mWeather = (TextView) view.findViewById(R.id.weather);
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
        String mWeatherData = array[position];
        holder.mWeather.setText(mWeatherData);
    }

    @Override
    public int getItemCount() {
        if(array == null) {
            return 0;
        }else{
            return array.length;
        }
    }
}
