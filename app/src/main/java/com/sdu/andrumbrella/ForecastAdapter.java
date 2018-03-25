package com.sdu.andrumbrella;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Michael on 25-Mar-18.
 */

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>{

    private String[] forecastData;


    @Override
    public ForecastViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutForecast = R.layout.forecast_list;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutForecast, parent, false);
        return new ForecastAdapter.ForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ForecastViewHolder holder, int position) {
        String mForecastData = forecastData[position];
        holder.mForecast.setText(mForecastData);
    }

    @Override
    public int getItemCount() {
        if(forecastData == null) {
            return 0;
        }else{
            return forecastData.length;
        }
    }

    public class ForecastViewHolder extends RecyclerView.ViewHolder{

        final private TextView mForecast;

        public ForecastViewHolder(View view){
            super(view);
            mForecast = view.findViewById(R.id.forecast);
        }

    }


}
