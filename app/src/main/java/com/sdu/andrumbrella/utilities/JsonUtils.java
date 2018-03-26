package com.sdu.andrumbrella.utilities;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.LinkedHashMap;

/**
 * Created by Michael on 22-Mar-18.
 */

public final class JsonUtils {

    public static String[] getWeatherFromJson(String jsonString) throws JSONException{

        final String LIST = "list";
        final String TMP = "tmp";
        final String MAX_TMP = "max";
        final String MIN_TMP = "min";
        final String MESSAGE_CODE = "cod";

        String[] parsedWeatherData;

        JSONObject jweather = new JSONObject(jsonString);

        if(jweather.has(MESSAGE_CODE)){
            int errorCode = jweather.getInt(MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }


        JSONArray weatherList = jweather.getJSONArray(LIST);
        parsedWeatherData = new String[weatherList.length()];
        for(int i = 0;i < weatherList.length();i++){
            JSONObject dayWeather = weatherList.getJSONObject(i);
            String date = dayWeather.getString("dt_txt");
            JSONObject main = dayWeather.getJSONObject("main");
            String temp = main.getString("temp");
            String max_temp = main.getString("temp_max");
            String min_temp = main.getString("temp_min");
            parsedWeatherData[i] = date + " - " + temp + " - " + max_temp + " - " + min_temp;
        }
        return parsedWeatherData;
    }
}
