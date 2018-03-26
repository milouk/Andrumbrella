package com.sdu.andrumbrella.utilities;

import android.util.Log;

import com.sdu.andrumbrella.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Michael on 25-Mar-18.
 */

public class GeneralUtils {


    public static ArrayList<String> getWeatherData(String day){
        ArrayList<String> clickedDayData = new ArrayList<>();

        for(int i = 0;i < MainActivity.weatherData.length;i++){
            if(MainActivity.weatherData[i].split("\\s")[0].split("-")[2].equals(day)){
                clickedDayData.add(MainActivity.weatherData[i]);
            }
        }
        for(int i = 0;i < clickedDayData.size();i++){
            Log.d("debug", clickedDayData.get(i));
        }
        return clickedDayData;
    }

    //Get Name of a day
    public static String getDayByName(String date){
        SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date newDate = inFormat.parse(date);
            SimpleDateFormat outFormat = new SimpleDateFormat("EEEE");
            return outFormat.format(newDate);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    //Parse date
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }

    //Get number of a day
    public static int getDayByNumber(String date){
        Date newDate = parseDate(date);
        Calendar cal = Calendar.getInstance();
        cal.setTime(newDate);
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    //Get month name
    public static String getMonth(String month){
        String monthString;
        switch (month) {
            case "01":
                monthString = "January";
                break;
            case "02":
                monthString = "February";
                break;
            case "03":
                monthString = "March";
                break;
            case "04":
                monthString = "April";
                break;
            case "05":
                monthString = "May";
                break;
            case "06":
                monthString = "June";
                break;
            case "07":
                monthString = "July";
                break;
            case "08":
                monthString = "August";
                break;
            case "09":
                monthString = "September";
                break;
            case "10":
                monthString = "October";
                break;
            case "11":
                monthString = "November";
                break;
            case "12":
                monthString = "December";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        return monthString;
        }
}

